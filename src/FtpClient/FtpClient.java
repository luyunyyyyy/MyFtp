package FtpClient;

import FtpServer.Command.StorCommand;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by LYY on 2017/3/1.
 */
public class FtpClient {
    private static Logger logger = Logger.getLogger(FtpClient.class);

    private static BufferedReader stdin;
    private static String serverHost;
    private static int serverContralPort = 21;
    private static String response;
    private static String command;
    private static int dataPort;
    private static Socket dataSocket;
    public static void main(String[] args) throws IOException {
        System.out.print("ftp服务器默认为localhost\n回车启动连接");
        stdin = new BufferedReader(new InputStreamReader(System.in));
        serverHost = stdin.readLine();
        if ("".equals(serverHost)) {
            serverHost = "localhost";
        }

        Socket clientControllerSocket = new Socket(serverHost, serverContralPort);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientControllerSocket.getInputStream()));
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(clientControllerSocket.getOutputStream()), true);

        response = bufferedReader.readLine();

        System.out.println(response);
        while (!clientControllerSocket.isClosed()) {
            System.out.print("请输入命令");
            command = stdin.readLine();
            command = command.trim();
            String commandUpper = command.toUpperCase();
            System.out.print("命令为:" + command);
            if (command.equals("") || command == null)
                continue;
            else if (commandUpper.startsWith("USER")) {
                System.out.print("执行user");
                System.out.print(command);
                printWriter.write(command + "\r\n");
                printWriter.flush();
                response = bufferedReader.readLine();
                System.out.print(response);
            } else if (commandUpper.startsWith("PASS")) {
//                System.out.print("执行PASS");
//                System.out.print(command);
                printWriter.write(command + "\r\n");
                printWriter.flush();
                response = bufferedReader.readLine();
                System.out.print(response);
                System.out.print(response);
            } else if (commandUpper.equals("QUIT")) {
                printWriter.println(command + "\r\n");
                printWriter.flush();
                response = bufferedReader.readLine();
                System.out.println(response);
                clientControllerSocket.close();
            } else if (commandUpper.equals("PASV")) {
                printWriter.println(command + "\r\n");
                printWriter.flush();
                response = bufferedReader.readLine();
                System.out.println("RESP:"+response);
                if (response.startsWith("227")) {
                    String[] responses;
                    responses = response.split(",");
                    dataPort = Integer.parseInt(responses[4]) * 256 +
                            Integer.parseInt(responses[5].substring(0, responses[5].length() - 1));
                    dataSocket = new Socket(serverHost, dataPort);
                    logger.info("dataport "+ dataPort);
//                    BufferedReader dataBufferedReader = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
//                    BufferedWriter dataBufferedWriter = new BufferedWriter(new OutputStreamWriter(dataSocket.getOutputStream()));
                }
                else
                    System.out.print("PASV执行错误");
            }
            else if (commandUpper.startsWith("CWD")) {
                System.out.println("执行CWD");
                System.out.println(command);
                printWriter.write(command + "\r\n");
                printWriter.flush();
                response = bufferedReader.readLine();
                System.out.println(response);
            }
            else if (commandUpper.startsWith("LIST")) {
                System.out.println("执行LIST");
                printWriter.write(command + "\r\n");
                printWriter.flush();

//                response = bufferedReader.readLine();
//                System.out.println(response);
                BufferedReader dataReader = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
                String dataResult ;
//                System.out.println("数据端口开始传送");
                while((dataResult = dataReader.readLine())!=null)
                    System.out.println(dataResult);

            }

            else if (commandUpper.startsWith("SIZE")) {
                System.out.println("执行SIZE");
                System.out.println(command);
                printWriter.write(command + "\r\n");
                printWriter.flush();
                response = bufferedReader.readLine();
                System.out.println(response);
                response = bufferedReader.readLine();
                System.out.println(response);
            }
//            else if (commandUpper.equals("Rest")) {
//                printWriter.println(command + "\r\n");
//                printWriter.flush();
//                response = bufferedReader.readLine();
//                System.out.println("RESP:"+response);
//                if (response.startsWith("227")) {
//                    String[] responses;
//                    responses = response.split(",");
//                    dataPort = Integer.parseInt(responses[4]) * 256 +
//                            Integer.parseInt(responses[5].substring(0, responses[5].length() - 1));
//                    dataSocket = new Socket(serverHost, dataPort);
//                    logger.info("dataport "+ dataPort);
////                    BufferedReader dataBufferedReader = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
////                    BufferedWriter dataBufferedWriter = new BufferedWriter(new OutputStreamWriter(dataSocket.getOutputStream()));
//                }
//                else
//                    System.out.print("Rest执行错误");
//            }
            else if (commandUpper.equals("Retr")) {
                printWriter.println(command + "\r\n");
                printWriter.flush();
                String fileName = command.split(" ")[2];
                File file = new File(fileName);
                response = bufferedReader.readLine();
                System.out.println("RESP:"+response);
                if (response.startsWith("150")) {
                    byte[] buffer=new byte[1024];
                    BufferedInputStream bufferedinputstream = new BufferedInputStream(dataSocket.getInputStream(),2048);
                    StringBuilder stringBuilder = new StringBuilder();
                    int temp;
                    while((temp = bufferedinputstream.read(buffer))!=-1){
                        stringBuilder.append(new String(buffer,0,temp));
                    }



                }

                else
                    System.out.print("Retr执行错误");
            }


            else {
//                printWriter.write(command);
//                printWriter.flush();
//                response = bufferedReader.readLine();
//                System.out.println(response);

            }
        }
    }
}

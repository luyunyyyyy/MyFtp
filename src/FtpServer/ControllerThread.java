package FtpServer;

import FtpServer.Command.Command;
import FtpServer.Command.CommandFactory;
import FtpServer.Command.PassCommand;
import FtpServer.Command.UserCommand;
import com.sun.corba.se.spi.activation.Server;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static FtpServer.FtpResponse.COMMAND_NOT_IMPLEMENTED_502;
import static FtpServer.FtpResponse.NEEDED_LOGIN_503;

/**
 * Created by LYY on 2017/3/1.
 */
public class ControllerThread extends Thread {
    public ServerSocket getDataServerSocket() {
        return dataServerSocket;
    }

    public void setDataServerSocket(ServerSocket dataServerSocket) {
        this.dataServerSocket = dataServerSocket;
    }

    Socket clientSocket;
    int connectCount = 0;
    boolean isLogin = false;
    public static final ThreadLocal<String> USER = new ThreadLocal<String>();
    private static Logger logger = Logger.getLogger(ControllerThread.class);
    private String dataPort;
    private ServerSocket dataServerSocket;

    public String getDataPort() {
        return dataPort;
    }

    public String getDataIp() {
        return dataIp;
    }

    private String dataIp;

    public void setNowDir(String nowDir) {
        this.nowDir = nowDir;
    }

    public String getNowDir() {
        return nowDir;
    }

    String nowDir = Data.rootDir;

    ControllerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader;
        try {
//            DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
//            DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
            //这些流的选择？？？
            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            //如果是刚刚连接，用户没有输入数据，这里可能会有问题
            while (true) {
                if (connectCount == 0) {
                    bufferedWriter.write("220");
                    bufferedWriter.write("\r\n");
                    bufferedWriter.flush();
                    connectCount++;
                } else {
                    //需要验证socket是否关闭 两种情况 密码错误或者quit命令
                    if (!clientSocket.isClosed()) {
                        String command = bufferedReader.readLine();
                        logger.info("用户输入:" + command);
                        if (command != null) {
                            String[] commandArgs = command.split(" ");
                            logger.info(commandArgs);
                            Command commandObj = CommandFactory.getCommand(commandArgs[0]);
                            if (alreadyLogin(commandObj)) {

                                if (commandObj == null) {
                                    bufferedWriter.write(COMMAND_NOT_IMPLEMENTED_502);
                                } else {

                                    /*
                                    因为会用到的命令都是只有两部分
                                    */
                                    String data = "";
                                    if (commandArgs.length >= 2)
                                        data = commandArgs[1];
                                    commandObj.excuteCommand(data, bufferedWriter, this);
                                }
                            } else {
                                bufferedWriter.write(NEEDED_LOGIN_503);
                                bufferedWriter.flush();
                            }

                        }
                    } else
                        break;
                    //因为socket已经关闭，所以线程可以结束
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean alreadyLogin(Command command) {
        if (command instanceof UserCommand || command instanceof PassCommand)
            return true;
        else
            return isLogin;
    }
}
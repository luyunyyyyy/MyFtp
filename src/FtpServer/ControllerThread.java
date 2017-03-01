package FtpServer;

import FtpServer.Command.Command;
import FtpServer.Command.CommandFactory;
import FtpServer.Command.PassCommand;
import FtpServer.Command.UesrCommand;

import java.io.*;
import java.net.Socket;

import static FtpServer.FtpResponse.COMMAND_NOT_IMPLEMENTED_502;
import static FtpServer.FtpResponse.NEEDED_LOGIN_503;

/**
 * Created by LYY on 2017/3/1.
 */
public class ControllerThread extends Thread{
    Socket clientSocket;
    int connectCount = 0;
    boolean isLogin = false;




    ControllerThread(Socket clientSocket){
        this.clientSocket = clientSocket;
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
            while(true){
                if(connectCount == 0){
                    bufferedWriter.write("220");
                    bufferedWriter.write("\r\n");
                    bufferedWriter.flush();
                    connectCount++;
                }else{
                    String command = bufferedReader.readLine();
                    if(command !=null){
                        String[] commandArgs = command.split(" ");

                        Command commandObj = CommandFactory.getCommand(commandArgs[0]);
                        if(alreadyLogin(commandObj)){

                            if(commandObj == null){
                                bufferedWriter.write(COMMAND_NOT_IMPLEMENTED_502);
                            }else{
                                String data = "";
                            /*
                            因为会用到的命令都是只有两部分
                             */
                                if(commandArgs.length >=2)
                                    data = commandArgs[1];
                                commandObj.excuteCommand(data,bufferedWriter,this);
                            }
                        }else{
                            bufferedWriter.write(NEEDED_LOGIN_503);
                            bufferedWriter.flush();
                        }

                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private boolean alreadyLogin(Command command){
        if(command instanceof UesrCommand || command instanceof PassCommand)
            return true;
        else
            return isLogin;
    }
}
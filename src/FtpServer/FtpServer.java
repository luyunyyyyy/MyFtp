package FtpServer;




import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import static FtpServer.ConstantArg.*;

/**
 * Created by LYY on 2017/3/1.
 */
public class FtpServer {
    ServerSocket serverSocket;
    int port;
    static short COMMAND_PORT = 21;
    static short DATA_PORT = 20;
    private static Logger logger = Logger.getLogger(FtpServer.class);

    FtpServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(COMMAND_PORT);
    }

/*
需要初始化一些系统的信息
 */
    FtpServer(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
    }

    public void  service() throws IOException {

        Socket clientSocket = null;
        logger.info("启动服务");
        while(true){
            logger.info(serverSocket);
            clientSocket = serverSocket.accept();
            logger.info("客户端接入 启动线程");
            new ControllerThread(clientSocket).start();

        }


    }

    public static void main(String[] args){
        PropertyConfigurator.configure("log4j.properties");


        try {
            FtpServer ftpServer = new FtpServer(8888);
            ftpServer.service();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

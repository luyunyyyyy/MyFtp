package FtpServer.Command;

import FtpServer.ControllerThread;
import com.sun.corba.se.spi.activation.Server;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * Created by LYY on 2017/3/4.
 */
public class PasvCommand implements Command {
    private static Logger logger = Logger.getLogger(PasvCommand.class);

    @Override
    public void excuteCommand(String data, BufferedWriter bufferedWriter, ControllerThread controllerThread) throws IOException {
        ServerSocket dataSocket = null;
        int port_high, port_low;
        Random generator = new Random();

        while (true) {
            port_high = 1 + generator.nextInt(20);
            port_low = 100 + generator.nextInt(1000);
            try {
                dataSocket = new ServerSocket(port_high * 256 + port_low);
                logger.info("创建数据端口成功:" + (port_high * 256 + port_low));
                controllerThread.setDataServerSocket(dataSocket);
                break;
            } catch (IOException e) {
                logger.info(e);
                continue;
            }
        }

        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
            logger.info("本机ip:" + address);
        } catch (UnknownHostException e) {
            logger.trace("获取本机ip失败", e);
        }

        bufferedWriter.write("227 Entering Passive Mode (" + address.getHostAddress().replace(".", ",") + "," + port_high + "," + port_low + ")");
        bufferedWriter.flush();
    }
}

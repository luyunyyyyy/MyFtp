package FtpServer.Command;

import FtpServer.ControllerThread;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;

import static FtpServer.FtpResponse.SERVICE_CLOSING_CONTROL_CONNECTION_221;

/**
 * Created by LYY on 2017/3/4.
 */
public class QuitCommand implements Command {
    private static Logger logger = Logger.getLogger(QuitCommand.class);
    @Override
    public void excuteCommand(String data, BufferedWriter bufferedWriter, ControllerThread controllerThread) throws IOException {
        logger.info("调用QUIT");
        bufferedWriter.write("excute the QUIT command");
        bufferedWriter.write(SERVICE_CLOSING_CONTROL_CONNECTION_221);
        bufferedWriter.flush();
        bufferedWriter.close();
        controllerThread.getClientSocket().close();
        logger.info("连接关闭成功");
    }
}

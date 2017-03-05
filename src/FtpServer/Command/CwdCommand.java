package FtpServer.Command;

import FtpServer.ControllerThread;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import static FtpServer.FtpResponse.DIR_ILLEGAL_555;

/**
 * Created by LYY on 2017/3/4.
 */
public class CwdCommand implements Command {
    private static Logger logger = Logger.getLogger(CwdCommand.class);
    @Override
    public void excuteCommand(String data, BufferedWriter bufferedWriter, ControllerThread controllerThread) throws IOException {
        String nowDir = controllerThread.getNowDir() + data;
        logger.info("调用CWD 目录:" + nowDir);

    }

}

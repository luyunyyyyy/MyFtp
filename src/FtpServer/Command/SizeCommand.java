package FtpServer.Command;

import FtpServer.ControllerThread;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import static FtpServer.FtpResponse.FILE_NOT_FOUND_550;
import static FtpServer.FtpResponse.SERVICE_READY_FOR_NEW_USER_220;

/**
 * Created by LYY on 2017/3/4.
 */
public class SizeCommand implements Command {
    private static Logger logger = Logger.getLogger(SizeCommand.class);

    @Override
    public void excuteCommand(String data, BufferedWriter bufferedWriter, ControllerThread controllerThread) throws IOException {
        String nowDir = controllerThread.getNowDir();
        String fileDir = nowDir + File.separator + data;
        File aimFile = new File(fileDir);
        if (aimFile.exists() && aimFile.isFile()) {
            bufferedWriter.write(SERVICE_READY_FOR_NEW_USER_220);
            bufferedWriter.write("文件:" + aimFile.getName() + "大小为:" + aimFile.length()+"\r\n");
            logger.info("文件:" + aimFile.getName() + "大小为" + aimFile.length());

        } else {
            logger.info("不是文件或文件不存在");
            bufferedWriter.write(FILE_NOT_FOUND_550);
        }
        bufferedWriter.flush();


    }
}

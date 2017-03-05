package FtpServer.Command;

import FtpServer.ControllerThread;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;

import static FtpServer.FtpResponse.FILE_NOT_FOUND_550;
import static FtpServer.FtpResponse.FILE_STATUS_OKAY_150;
import static FtpServer.FtpResponse.SERVICE_READY_FOR_NEW_USER_220;

/**
 * Created by LYY on 2017/3/4.
 */
public class RetrCommand implements Command {
    private static Logger logger = Logger.getLogger(RetrCommand.class);


    @Override
    public void excuteCommand(String data, BufferedWriter bufferedWriter, ControllerThread controllerThread) throws IOException {

        Socket dataSocket;
        String desDir = controllerThread.getNowDir()+ File.separator+data;
        File file = new File(desDir);
        logger.info(desDir);

        if(file.exists())
        {
            try {
                bufferedWriter.write(FILE_STATUS_OKAY_150);
                bufferedWriter.flush();
                dataSocket = controllerThread.getDataSocket();
                BufferedOutputStream dataOut = new BufferedOutputStream(dataSocket.getOutputStream());
                byte[] buffer = new byte[1024];
                InputStream inputStream = new FileInputStream(file);
                while(-1 != inputStream.read(buffer)) {
                    dataOut.write(buffer);
                }
                dataOut.flush();
                bufferedWriter.write(SERVICE_READY_FOR_NEW_USER_220);
                bufferedWriter.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                bufferedWriter.write(FILE_NOT_FOUND_550);
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}

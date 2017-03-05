package FtpServer.Command;

import FtpServer.ControllerThread;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import static FtpServer.FtpResponse.FILE_STATUS_OKAY_150;
import static FtpServer.FtpResponse.SERVICE_READY_FOR_NEW_USER_220;

/**
 * Created by LYY on 2017/3/4.
 */
public class ListCommand implements Command {
    private static Logger logger = Logger.getLogger(ListCommand.class);

    @Override
    public void excuteCommand(String data, BufferedWriter bufferedWriter, ControllerThread controllerThread) throws IOException {
        String nowDir = controllerThread.getNowDir() + File.separator+ data;
        logger.info("调用LIST 目录:" + nowDir);
        StringBuilder response = new StringBuilder();
        response.append("文件目录如下:" + "\n");

        ListCommand.traverseFolder(nowDir, response);
        Socket dataSocket = controllerThread.getDataSocket();
        try {
//            bufferedWriter.write(FILE_STATUS_OKAY_150);
//            bufferedWriter.flush();
            BufferedWriter dataWriter = new BufferedWriter(new OutputStreamWriter(dataSocket.getOutputStream()));
            dataWriter.write(response.toString());
            dataWriter.flush();
            dataWriter.close();
            logger.info("data:::"+response.toString());
//            bufferedWriter.write(SERVICE_READY_FOR_NEW_USER_220);
//            bufferedWriter.flush();
        } catch (NumberFormatException e) {

            logger.trace(e);
        } catch (UnknownHostException e) {

            logger.trace(e);
        } catch (IOException e) {

            logger.trace(e);
        }
    }

    private static void traverseFolder(String path, StringBuilder response) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        File file = new File(path);

        if (file.exists()) {

            File[] files = file.listFiles();
            if (files.length == 0) {
                logger.info("文件夹是空的!" + "\n");
                response.append("文件夹为空\n");

            }
            else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        logger.info("文件夹:" + file2.getName() + "\n");
                        response.append("文件夹:" + file2.getName() + "\n");
                        traverseFolder(file2.getAbsolutePath(), response);
                    }
                    else {
                        String lastModifiedTime = sdf.format(new Date(Long.parseLong(String.valueOf(file2.lastModified()))));   // 时间戳转换成时间
                        response.append("文件:" + file2.getName() + "\t" + file2.length() + "\t" + lastModifiedTime + "\n");
                        logger.info("文件:" + file2.getName() + "\t" + file2.length() + "\t" + lastModifiedTime + "\n");
                    }
                }
            }
        }
        else {
            logger.info("文件不存在!");
            response.append("文件不存在!");
        }
    }

    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        traverseFolder("C:\\Users\\LYY\\Desktop\\ShadowsocksR", stringBuilder);
        System.out.print(stringBuilder.toString());
    }

}

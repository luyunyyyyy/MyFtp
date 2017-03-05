package FtpServer.Command;

import FtpServer.ControllerThread;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.Socket;

import static FtpServer.FtpResponse.FILE_STATUS_OKAY_150;

/**
 * Created by LYY on 2017/3/4.
 */
public class StorCommand implements Command {
    private static Logger logger = Logger.getLogger(StorCommand.class);

    @Override
    public void excuteCommand(String data, BufferedWriter bufferedWriter, ControllerThread controllerThread) throws IOException {
        try{
            bufferedWriter.write(FILE_STATUS_OKAY_150);
            bufferedWriter.flush();
            RandomAccessFile inFile = new
                    RandomAccessFile(controllerThread.getNowDir()+"/"+data,"rw");
            //数据连接
            Socket tempSocket = controllerThread.getDataSocket();
            InputStream inSocket
                    = tempSocket.getInputStream();
            byte byteBuffer[] = new byte[1024];
            int amount;
            //这里又会阻塞掉，无法从客户端输出流里面获取数据？是因为客户端没有发送数据么
            while((amount =inSocket.read(byteBuffer) )!= -1){
                inFile.write(byteBuffer, 0, amount);
            }
            logger.info("传输完成，关闭连接。。。");
            inFile.close();
            inSocket.close();
            //断开数据连接

            bufferedWriter.write("226 transfer complete\r\n");
            bufferedWriter.flush();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}

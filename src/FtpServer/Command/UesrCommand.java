package FtpServer.Command;

import FtpServer.ControllerThread;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created by LYY on 2017/3/1.
 */
public class UesrCommand implements Command{


    @Override
    public void excuteCommand(String data, BufferedWriter bufferedWriter, ControllerThread controllerThread) throws IOException {
        bufferedWriter.write("excute user command");
    }
}

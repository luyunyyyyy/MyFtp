package FtpServer.Command;

import FtpServer.*;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created by LYY on 2017/3/1.
 */
public interface Command {
    void excuteCommand(String data, BufferedWriter bufferedWriter, ControllerThread controllerThread) throws IOException;
}

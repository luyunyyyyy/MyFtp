package FtpServer.Command;

import FtpServer.ControllerThread;
import FtpServer.Data;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;

import static FtpServer.FtpResponse.PASSWORD_REQURIED_FOR_331;
import static FtpServer.FtpResponse.SERVER_CANNOT_ACCEPT_ARGUMENT_501;

/**
 * Created by LYY on 2017/3/1.
 */
public class UserCommand implements Command{
    private static Logger logger = Logger.getLogger(UserCommand.class);

    @Override
    public void excuteCommand(String data, BufferedWriter bufferedWriter, ControllerThread controllerThread) throws IOException {

        logger.info("调用USER");
        bufferedWriter.write("excute USER command");
        String response = "";
        if(Data.adminUsers.containsKey(data)||Data.users.containsKey(data)){
            logger.info("用户名正确:" + data);

            response = PASSWORD_REQURIED_FOR_331;
        }

        else{
            logger.info("用户名不存在:" + data);
            response = SERVER_CANNOT_ACCEPT_ARGUMENT_501;
        }


        bufferedWriter.write(response);
        bufferedWriter.flush();
    }
}

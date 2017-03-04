package FtpServer.Command;

import FtpServer.ControllerThread;
import FtpServer.Data;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;

import static FtpServer.FtpResponse.NOT_LOGGED_IN_530;
import static FtpServer.FtpResponse.USER_LOGGED_IN_230;

/**
 * Created by LYY on 2017/3/1.
 */
public class PassCommand implements Command{
    private static Logger logger = Logger.getLogger(PassCommand.class);

    @Override
    public void excuteCommand(String data, BufferedWriter bufferedWriter, ControllerThread controllerThread) throws IOException {
        logger.info("调用PASS");
        bufferedWriter.write("excute the PASS command");
        String response = "";
        String key = controllerThread.USER.get();
        String pass = Data.adminUsers.get(key) == null ? Data.users.get(key) : Data.adminUsers.get(key);

        if(data.equals(pass)){
            logger.info("密码正确登陆成功");
            controllerThread.setLogin(true);
            response = USER_LOGGED_IN_230;
        }else{
            logger.info("登陆失败密码错误:" + data);
            response = NOT_LOGGED_IN_530;
        }

        bufferedWriter.write(response);
        bufferedWriter.flush();



    }
}

package FtpServer;

/**
 * Created by LYY on 2017/3/1.
 */
public class FtpResponse {
    public static final String NEEDED_LOGIN_503 = "532 执行该命令需要登录，请登录后再执行相应的操作\r\n";
    public static final String COMMAND_NOT_IMPLEMENTED_502 = "502  该命令不存在，请重新输入\r\n";
}

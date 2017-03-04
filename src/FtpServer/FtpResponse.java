package FtpServer;

/**
 * Created by LYY on 2017/3/1.
 */
public class FtpResponse {
    public static final String NEEDED_LOGIN_503 = "532 执行该命令需要登录，请登录后再执行相应的操作\r\n";
    public static final String COMMAND_NOT_IMPLEMENTED_502 = "502  该命令不存在，请重新输入\r\n";
    public static final String PASSWORD_REQURIED_FOR_331 = "331 用户名正确，需要口令\r\n";
    public static final String SERVER_CANNOT_ACCEPT_ARGUMENT_501= "501 命令参数错误\r\n";
    public static final String USER_LOGGED_IN_230 = "230 用户登陆成功\r\n";
    public static final String NOT_LOGGED_IN_530 = "530 登陆失败\r\n";
}

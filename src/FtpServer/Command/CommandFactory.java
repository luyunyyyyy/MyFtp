package FtpServer.Command;

/**
 * Created by LYY on 2017/3/1.
 */
public class CommandFactory {
    public static Command getCommand(String command){
        command.toUpperCase();
        switch (command){
            case "USER": return new UserCommand();
            case "PASS": return new PassCommand();
            default: return  null;
        }





    }
}

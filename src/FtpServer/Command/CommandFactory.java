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
            case "PASV": return new PasvCommand();
            case "LIST": return new ListCommand();
            case "QUIT": return new QuitCommand();
            case "RETR": return new RetrCommand();
            case "STOR": return new StorCommand();
            case "SIZE": return new SizeCommand();
            case "REST": return new RestCommand();
            case "CWD": return new CwdCommand();
            default: return  null;
        }





    }
}

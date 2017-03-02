package FtpServer;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lyy on 2017/3/1.
 */
public class Data {
    public  static  String  rootDir = "C:" + File.separator;
    public static HashMap<String,String> users = new HashMap<String,String>();

    public static HashMap<String,String> adminUsers = new HashMap<String,String>();

    public static void init() {



        File file = new File("src/FtpServer/server.xml");
        SAXBuilder builder = new SAXBuilder();
        try {
            Document parse = builder.build(file);
            Element root = parse.getRootElement();

            //配置服务器的默认目录
            rootDir = root.getChildText("rootDir");
            System.out.print("rootDir is:");
            System.out.println(rootDir);

            //允许登录的用户
            Element usersE = root.getChild("users");
            List<Element> usersEC = usersE.getChildren();
            String username = null;
            String password = null;
            System.out.println("\n所有用户的信息：");
            for (Element user : usersEC) {
                username = user.getChildText("username");
                password = user.getChildText("password");
                System.out.println("用户名：" + username);
                System.out.println("密码：" + password);
                users.put(username, password);
            }


        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }

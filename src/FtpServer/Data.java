package FtpServer;

import org.apache.log4j.Logger;
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

    private static Logger logger = Logger.getLogger(Data.class);
    public static void init() {



        File file = new File("src/FtpServer/server.xml");
        SAXBuilder builder = new SAXBuilder();
        try {
            Document parse = builder.build(file);
            Element root = parse.getRootElement();

            //配置服务器的默认目录
            rootDir = root.getChildText("rootDir");
            logger.info("rootDir is:" + rootDir);

            //允许登录的用户
            Element usersE = root.getChild("users");
            List<Element> usersEC = usersE.getChildren();
            String username = null;
            String password = null;
            logger.info("可登陆用户共:"+ usersEC.size());
            for (Element user : usersEC) {
                username = user.getChildText("username");
                password = user.getChildText("password");
                logger.info("用户名：" + username);
                logger.info("密码：" + password);
                users.put(username, password);
            }


            String rootName = root.getChildText("root_user");
            String rootPass = root.getChildText("root_pass");
            adminUsers.put(rootName,rootPass);
            logger.info("root用户名:" + rootName);
            logger.info("root密码:" + rootPass);


        } catch (JDOMException e) {
            logger.trace("xml解析错误",e);
        } catch (IOException e) {
            logger.trace("ioException",e);
        }
    }
    }

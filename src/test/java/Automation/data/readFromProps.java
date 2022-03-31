package Automation.data;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class readFromProps {

    public HashMap<String,String> PropsFile() {
        HashMap<String, String> Credentials = new HashMap<>();

        try {

            InputStream is = new FileInputStream("src/main/resources/config.properties");

            Properties Prop = new Properties();
            Prop.load(is);

            Credentials.put("Username", Prop.getProperty("Username"));
            Credentials.put("Password", Prop.getProperty("Password"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Credentials;
    }
}

package Automation.webUtilities;

import Automation.data.data;

import javax.xml.crypto.Data;
import java.sql.ResultSet;

public class DbConnection {

    public ResultSet ConnectDb(String Query) {
        data connect = new data();
        ResultSet Data;
        ResultSet FillData;

        String url = "jdbc:mysql://localhost:3306/automationdb";
        String user = "root";
        String pass = "password";

        Data = connect.ConnectAndQuerySQL(url, user, pass, Query);

        return Data;
    }
}

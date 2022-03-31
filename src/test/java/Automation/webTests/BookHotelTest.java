package Automation.webTests;

import Automation.PageObjects.webFunctions;
import Automation.data.readFromProps;
import Automation.reports.extentReport;
import Automation.webUtilities.DbConnection;
import Automation.webUtilities.webUtilities;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;


public class BookHotelTest {
    public webUtilities web = new webUtilities();
    public webFunctions functions = new webFunctions();
    public DbConnection connector = new DbConnection();
    extentReport reporting = new extentReport();
    readFromProps readData = new readFromProps();
    ResultSet FillData;
    public ExtentReports reports;

    String sUrl, sBrowser;

    @Parameters({"adactinURL", "Browser"})
    @BeforeTest
    public void init(String sadactinURL, String Browser) {
        sUrl = sadactinURL;
        sBrowser = Browser;

        web.setWebDriver(web.initializeWebDriver(sBrowser));
        FillData = connector.ConnectDb("select * from bookings");

        reports = reporting.initilizeExtentReporters("src/reports/report.html");
    }

    @Test
    public void bookHotel() throws SQLException{

        ExtentTest test = reports.createTest("Adaction Website").assignAuthor("Tshifhiwa Sinugo");
        ExtentTest node;

        HashMap<String, String> DataFromProps = readData.PropsFile();

        try{
                web.navigate(sUrl);

            while(FillData.next()){

                node = test.createNode(FillData.getString("Location"));

                functions.Login(webUtilities.getWebDriver(), DataFromProps.get("Username"),
                        DataFromProps.get("Password"), node);

                functions.Search(webUtilities.getWebDriver(), FillData.getString("Location"),
                        FillData.getString("Hotels"),
                        FillData.getString("RoomType"), FillData.getString("NumberOfRooms"),
                        FillData.getString("CheckInDate"), FillData.getString("CheckOutDate"),
                        FillData.getString("AdultsPerRoom"), FillData.getString("ChildrenPerRoom"), node);

                functions.Select(webUtilities.getWebDriver(), node);

                functions.BookNow(webUtilities.getWebDriver(), FillData.getString("Firstname"),
                        FillData.getString("Lastname"),
                        FillData.getString("BillingAddress"),
                        FillData.getString("CreditCardNumber"),
                        FillData.getString("CreditCardType"),
                        FillData.getString("ExpiryDateMonth"),
                        FillData.getString("ExpiryDateYear"),
                        FillData.getString("CVVNumber"), node);
                        functions.Logout(webUtilities.getWebDriver(), node);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterTest
    public void tearDown() throws InterruptedException {
        Thread.sleep(5000);
        webUtilities.getWebDriver().quit();
        reports.flush();
    }
}

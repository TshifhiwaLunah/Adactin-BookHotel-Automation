package Automation.PageObjects;

import Automation.reports.extentReport;
import Automation.webPageObjects.*;
import Automation.webUtilities.generateFile;
import Automation.webUtilities.webActions;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.io.IOException;

public class webFunctions extends webActions {
    public extentReport report = new extentReport();

    public void Login(WebDriver driver, String Username, String Password, ExtentTest node) {
        loginAdactin login = new loginAdactin(driver);

        try {
            passData(login.txtUsernme, driver, Username);
            passData(login.txtPassword, driver, Password);
            clickObject(login.btnLogin, driver);

            String filename = report.CaptureScreenShot(driver);

            //Validation
            if (login.txtWelcomeMessage.isDisplayed()) {
                node.pass("Login was Successful", MediaEntityBuilder.createScreenCaptureFromBase64String(filename).build());
            } else {
                node.fail("Login was Unsuccessful", MediaEntityBuilder.createScreenCaptureFromBase64String(filename).build());
                Assert.fail("Login was Unsuccessful");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void Search(WebDriver driver, String Location, String Hotel, String RoomType, String NumberOfRooms,
                       String CheckInDate, String CheckOutDate, String AdultPerRoom, String ChildrenPerRoom, ExtentTest node) {
        searchHotel search = new searchHotel(driver);

        try {

            selectObject(search.location, driver, "SELECTBYVISIBLETEXT", Location);
            selectObject(search.hotels, driver, "SELECTBYVISIBLETEXT", Hotel);
            selectObject(search.roomTypes, driver, "SELECTBYVISIBLETEXT", RoomType);
            selectObject(search.noOfRooms, driver, "SELECTBYVISIBLETEXT", NumberOfRooms);
            passData(search.checkInDate, driver, CheckInDate);
            passData(search.checkOutDate, driver, CheckOutDate);
            selectObject(search.adultsPerRoom, driver, "SELECTBYVISIBLETEXT", AdultPerRoom);
            selectObject(search.childrenPerRoom, driver, "SELECTBYVISIBLETEXT", ChildrenPerRoom);
            clickObject(search.search_Btn, driver);

            String filename = report.CaptureScreenShot(driver);

            //Validation
            if (search.continue_Btn.isDisplayed()) {
                node.pass("Login was Successful", MediaEntityBuilder.createScreenCaptureFromBase64String(filename).build());

            } else {
                node.fail("Login was Successful", MediaEntityBuilder.createScreenCaptureFromBase64String(filename).build());
                Assert.fail();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Select(WebDriver driver, ExtentTest node) {
        selectHotel select = new selectHotel(driver);

        try {
            clickObject(select.radioButton, driver);
            clickObject(select.continueButton, driver);

            String filename = report.CaptureScreenShot(driver);

            //validation
            if (select.txtbookHotel.isDisplayed()) {
                node.pass("Hotel has been selected successfully", MediaEntityBuilder.createScreenCaptureFromBase64String(filename).build());

            } else {
                node.fail("No Hotel was selected", MediaEntityBuilder.createScreenCaptureFromBase64String(filename).build());
                Assert.fail("No Hotel was selected");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void BookNow(WebDriver driver, String FirstName, String LastName, String BillingAddress, String CreditCardNo,
                        String CardType, String ExpMonth, String ExpYear, String CvvNumber, ExtentTest node) {
        confirmBooking booking = new confirmBooking(driver);
        generateFile generator = new generateFile();

        try {

            passData(booking.firstName, driver, FirstName);
            passData(booking.lastName, driver, LastName);
            passData(booking.billingAddress, driver, BillingAddress);
            passData(booking.creditCardNo, driver, CreditCardNo);
            selectObject(booking.creditCardType, driver, "SELECTBYVALUE", CardType);
            selectObject(booking.ccExpiryMonth, driver, "SELECTBYVISIBLETEXT", ExpMonth);
            selectObject(booking.ccExpiryYear, driver, "SELECTBYVISIBLETEXT", ExpYear);
            passData(booking.cvvNumber, driver, CvvNumber);
            clickObject(booking.bookNowButton, driver);

            Thread.sleep(2000);
            WebElement order_number = booking.OrderNumber;

            String orderNum = order_number.getAttribute("value");

            generator.writeToFile(orderNum);

            String filename = report.CaptureScreenShot(driver);

            //validation
            if (booking.OrderNumber.isDisplayed()) {
                node.pass("Hotel was booked successful,OrderNumber : " + orderNum, MediaEntityBuilder.createScreenCaptureFromBase64String(filename).build());

            } else {
                node.fail("Order NUmber was not generated", MediaEntityBuilder.createScreenCaptureFromBase64String(filename).build());
                Assert.fail("Order NUmber was not generated");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void Logout(WebDriver driver, ExtentTest node) throws InterruptedException {
        logoutHotel logout = new logoutHotel(driver);

        Thread.sleep(5000);
        try {
            clickObject(logout.logoutButton, driver);
            clickObject(logout.ClickToLoginAgain, driver);

            String filename = report.CaptureScreenShot(driver);

            //Validation
            if (logout.ClickToLoginAgain.isDisplayed()) {
                node.pass("Logout was Successful", MediaEntityBuilder.createScreenCaptureFromBase64String(filename).build());

            } else {
                node.fail("Logout wasUnsuccessful", MediaEntityBuilder.createScreenCaptureFromBase64String(filename).build());
                Assert.fail("Logout wasUnsuccessful");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

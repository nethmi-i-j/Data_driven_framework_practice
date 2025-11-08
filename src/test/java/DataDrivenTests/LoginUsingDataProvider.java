package DataDrivenTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginUsingDataProvider {

    WebDriver driver;

    @BeforeMethod

    public void OpenPage(){

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    @DataProvider(name = "loginData")
    public Object[][] LoginDataProvider(){
        String[][] data = {                   //object for when both string and ints are together
                {"Admin","admin123", "valid"},
                {"dummyAdmin", "admin123", "invalid"},
                {"Admin", "dummyPass", "invalid" },
                {"dummyAdmin", "dummyPass", "invalid"}
        };
        return data;
    }

    @Test(dataProvider = "loginData")

    public void LoginTestScenario(String uName, String pass, String expValidation) throws InterruptedException {

        WebElement userName = driver.findElement(By.xpath("//input[@placeholder='Username']"));
        userName.sendKeys(uName);
        WebElement password = driver.findElement(By.xpath("//input[@placeholder='Password']"));
        password.sendKeys(pass);
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        Thread.sleep(3000);

        boolean urlVerification = driver.getCurrentUrl().contains("dashboard");

        if (expValidation.equals("valid")) {
            Assert.assertTrue(urlVerification,"Expecting login success but not navigated to dashboard");
        }else{
            Assert.assertFalse(urlVerification,"Expecting login failed but navigated to dashboard");
        }
    }

    @AfterMethod

    public void closeBrowser(){

        driver.quit();
    }



}

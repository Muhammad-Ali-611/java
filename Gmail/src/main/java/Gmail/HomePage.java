package Gmail;

import Base.CommonAPI;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class HomePage extends CommonAPI {
    @Test
    public void HomePage() {
        System.setProperty("WebDriver.ChromeDriver", "C:\\Users\\chma6\\Links\\Java\\Driver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://mail.google.com/mail/u/0/#inbox");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
     //   driver.manage().window().minimize();
        driver.manage().window().maximize();
        //driver.close();

        }
    }


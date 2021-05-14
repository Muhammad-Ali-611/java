package Base;

import com.beust.jcommander.Parameter;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommonAPI {
    public WebDriver driver1 = null;
    public String browserStackUserName = "muhammadali102";
    public String browserStackAccessKey = "StrCXDyLU9h2gRPppqKU";
   // public String sauceLabUserName = "";
   // public String sauceLabAccessKey = "";
   private static WebDriver driver = null;
   // @Parameters({"useCloudEnv","envName","os","os_version","browserName","browserVersion","url"})
  //  @BeforeMethod
    public void setUp( String useCloudEnv, String envName, String os, String os_version, String browserName,
                      String browserVersion, String url) throws MalformedURLException {
        //check if you want to run test in local or in cloud
        if(useCloudEnv.equalsIgnoreCase("true")){
            //get cloud driver
            getCloudDriver(envName,os,os_version,browserName, browserVersion);

        }else if(useCloudEnv.equalsIgnoreCase("false")){
            //get local driver
            getLocalDriver(os,browserName);
        }

        driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
        driver.get(url);
        driver.manage().window().maximize();
    }

    public WebDriver getLocalDriver(String OS,String browserName){
        if(browserName.equalsIgnoreCase("chrome")){
            if(OS.equalsIgnoreCase("OS X")){
                System.setProperty("webdriver.chrome.driver", "Generic/driver/chromedriver");
                driver = new ChromeDriver();
            }else if(OS.equalsIgnoreCase("Windows")){
                System.setProperty("webdriver.chrome.driver", "Generic/driver/chromedriver.exe");
                driver = new ChromeDriver();
            }
        }else if(browserName.equalsIgnoreCase("firefox")){
            if(OS.equalsIgnoreCase("OS X")){
                System.setProperty("webdriver.gecko.driver", "Generic/driver/geckodriver");
                driver = new FirefoxDriver();
            }else if(OS.equalsIgnoreCase("Windows")){
                System.setProperty("webdriver.gecko.driver", "Generic/driver/geckodriver.exe");
                driver = new FirefoxDriver();
            }
        }else if(browserName.equalsIgnoreCase("ie")){
            System.setProperty("webdriver.ie.driver", "Generic/driver/internetexplorerdriver.exe");
            driver = new InternetExplorerDriver();

        }else if(browserName.equalsIgnoreCase("safari")){
            System.setProperty("webdriver.safari.driver", "Generic/driver/safaridriver");
            driver = new SafariDriver();
        }
        return driver;
    }

    public WebDriver getCloudDriver(String envName, String OS,String os_version,
                                    String browserName, String browserVersion) throws MalformedURLException {

        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("browser",browserName);
        cap.setCapability("browserVersion",browserVersion);
        cap.setCapability("os", OS);
        cap.setCapability("os_version", os_version);
        if(envName.equalsIgnoreCase("Browserstack")){
            driver = new RemoteWebDriver(new URL("http://"+browserStackUserName+":"+browserStackAccessKey+
                    "@hub-cloud.browserstack.com/wd/hub"),cap);
        }else if(envName.equalsIgnoreCase("Saucelabs")) {
            String sauceLabsUserName = null;
            String sauceLabsAccessKey = null;
            driver = new RemoteWebDriver(new URL("http://" + sauceLabsUserName + ":" + sauceLabsAccessKey +
                    "@ondemand.saucelabs.com:80/wd/hub"), cap);
        }

        return driver;
    }

  //  @AfterMethod
    //public void cleanUp(){
      //  driver.close();
   // }

    //common selenium api
    public static void navigateBack(){
        driver.navigate().back();
    }
    public void typeOnCss(String locator, String value){
        driver.findElement(By.cssSelector(locator)).sendKeys(value, Keys.ENTER);
    }

    public void clearInputField(String locator){
        driver.findElement(By.cssSelector(locator)).clear();
    }

    public List<String> getListOfText(String locator){
        List<WebElement> webElements = driver.findElements(By.cssSelector(locator));
        List<String> listOfText = new ArrayList<String>();
        for(WebElement element:webElements){
            listOfText.add(element.getText());
        }

        return listOfText;
    }

    public static void waitUntilVisible(WebElement locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(locator));
    }

    public static void waitUntilClickAble(WebElement locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception ex) {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        }
    }
    }

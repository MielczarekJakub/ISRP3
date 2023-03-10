import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.WebElement;

import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.firefox.FirefoxOptions;

import org.openqa.selenium.support.ui.Select;

import org.openqa.selenium.By;

import org.openqa.selenium.*;

import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.Assert;

import org.testng.annotations.*;



import java.time.Duration;





public class CreateLocationTest {



    // LOCATION PATTERN XX-00-00-00

    protected String locationName = "-01-02-03";

    protected String locationType = "JEDNA CZWARTA";

    protected String locationPattern;

    protected String username1 = "JDoe";

    protected String username2 = "JDoe2";

    protected WebDriver driver;

    protected String basicPassword = "P@ssw0rd";

    protected ChromeOptions chromeOptions;

    protected FirefoxOptions firefoxOptions;





    @Parameters("browser")

    @BeforeClass

    public void test(@Optional("browser") String browser) {



        if (browser.equals("chrome")) {



            WebDriverManager.chromedriver().setup();

            driver = new ChromeDriver();



        } else if (browser.equals("firefox")) {



            WebDriverManager.firefoxdriver().setup();

            driver = new FirefoxDriver();



        } else if (browser.equals("edge")) {







        } else {

            //System.setProperty("webdriver.firefox.marionette", "/usr/bin/geckodriver.exe");

            chromeOptions = new ChromeOptions();

            chromeOptions.addArguments("--allow-insecure-localhost");

            chromeOptions.addArguments("--ignore-ssl-errors=yes");

            chromeOptions.addArguments("--ignore-certificate-errors");

            chromeOptions.addArguments("--disable-dev-shm-usage");

            chromeOptions.addArguments("--no-sandbox");

            chromeOptions.addArguments("--disable-extensions");

            WebDriverManager.chromedriver().setup();

            driver = new ChromeDriver(chromeOptions);

            //throw new RuntimeException("Browser is not supported");

//            firefoxOptions = new FirefoxOptions();

//            firefoxOptions.addArguments("--allow-insecure-localhost");

//            firefoxOptions.addArguments("--ignore-ssl-errors=yes");

//            firefoxOptions.addArguments("--ignore-certificate-errors");

//            firefoxOptions.addArguments("--disable-dev-shm-usage");

//            firefoxOptions.addArguments("--no-sandbox");

//            firefoxOptions.addArguments("--disable-extensions");

//            WebDriverManager.firefoxdriver().setup();

//            driver = new FirefoxDriver();

        }

    }





    @Test

    public void basicTest() {







        driver.get("https://localhost:8181/faces/common/signIn.xhtml");

        // Log in

        driver.findElement(By.cssSelector("input[name='j_username']")).sendKeys(username1);

        driver.findElement(By.cssSelector("input[name='j_password']")).sendKeys(basicPassword);

        driver.findElement(By.cssSelector("input[value='Zaloguj']")).click();



        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));



        // zmiana po??o??enia do tworzenia lokalizacji

        driver.findElement(By.xpath("//a[contains(normalize-space(), 'Lokalizacja')]")).click();

        driver.findElement(By.xpath("//a[contains(normalize-space(), 'Utw??rz now?? lokalizacj??')]")).click();



        // stworzenie lokalizacji

        driver.findElement(By.cssSelector("input[id='CreateLocationForm:locationSymbol']")).sendKeys(locationPattern);

        WebElement selectElement = driver.findElement(By.cssSelector("select[id='CreateLocationForm:locationType']"));

        Select select = new Select(selectElement);

        select.selectByVisibleText(locationType);

        driver.findElement(By.cssSelector("input[name='CreateLocationForm:j_idt34']")).click();



        // zmiana po??o??enia na list?? lokalizacji

        driver.findElement(By.cssSelector("a[class='dropdown-toggle']"))

                .findElement(By.xpath("//a[contains(normalize-space(), 'Lokalizacja')]")).click();

        driver.findElement(By.xpath("//a[contains(normalize-space(), 'Lista lokalizacji')]")).click();





        // czy lokacja na liscie

        WebElement element = driver.findElement(By.xpath("//td[contains(normalize-space(), "  + locationPattern + ")]"));

        Assert.assertNotNull(element);

        // wylogowanie

        driver.findElement(By.xpath("//a[contains(normalize-space(), 'Wylogowanie')]")).click();

    }



    @AfterClass

    public void tearDown() {

        driver.quit();

    }

}


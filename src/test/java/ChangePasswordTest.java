import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.WebElement;

import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.firefox.FirefoxDriver;

import org.testng.annotations.*;



import java.time.Duration;

import org.testng.Assert;



public class ChangePasswordTest {

    protected String newPassword = "noweH@s!0";

    protected String username1 = "JDoe";

    protected String username2 = "JDoe2";

    protected String basicPassword = "P@ssw0rd";

    protected ChromeOptions chromeOptions;



    WebDriver driver;



    @Parameters({"browser"})

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

            chromeOptions = new ChromeOptions();

            chromeOptions.addArguments("--allow-insecure-localhost");

            chromeOptions.addArguments("--ignore-ssl-errors=yes");

            chromeOptions.addArguments("--ignore-certificate-errors");

            chromeOptions.addArguments("--disable-dev-shm-usage");

            chromeOptions.addArguments("--no-sandbox");

            chromeOptions.addArguments("--disable-extensions");

            WebDriverManager.chromedriver().setup();

            driver = new ChromeDriver(chromeOptions);

        }

    }



    @Test

    public void basicTest(){





        driver.get("https://localhost:8181/faces/common/signIn.xhtml");

        // Log in

        driver.findElement(By.cssSelector("input[name='j_username']")).sendKeys(username1);

        driver.findElement(By.cssSelector("input[name='j_password']")).sendKeys(basicPassword);



        driver.findElement(By.cssSelector("input[value='Zaloguj']")).click();



        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));





        //zmiana ścieżki na formularz zmiany hasła

        driver.findElement(By.xpath("//a[contains(normalize-space(), 'Ustawienia')]")).click();

        driver.findElement(By.xpath("//a[contains(normalize-space(), 'Zmiana hasła')]")).click();



        //podanie danych do zmiany hasla oraz zatwierdzenie zmiany

        driver.findElement(By.cssSelector("input[id='ChangeMyPasswordForm:oldPassword']")).sendKeys(basicPassword);

        driver.findElement(By.cssSelector("input[id='ChangeMyPasswordForm:newPassword']")).sendKeys(newPassword);

        driver.findElement(By.cssSelector("input[id='ChangeMyPasswordForm:newPasswordRepeat']")).sendKeys(newPassword);

        driver.findElement(By.cssSelector("input[value='Zmień hasło']")).click();



        //wylogowanie

        driver.findElement(By.xpath("//a[contains(normalize-space(), 'Wylogowanie')]")).click();

        driver.findElement(By.cssSelector("input[value='Wyloguj']")).click();



        //próba logowania po zmianie hasła

        driver.get("https://localhost:8181/faces/main/index.xhtml");

        driver.findElement(By.cssSelector("input[name='j_username']")).sendKeys(username1);

        driver.findElement(By.cssSelector("input[name='j_password']")).sendKeys(newPassword);



        driver.findElement(By.cssSelector("input[value='Zaloguj']")).click();



        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));



        WebElement element = driver.findElement(By.xpath("//h4[contains(normalize-space(), 'JDoe')]"));

        Assert.assertNotNull(element);



        //cofnięcie zmiany hasla

        driver.findElement(By.xpath("//a[contains(normalize-space(), 'Ustawienia')]")).click();

        driver.findElement(By.xpath("//a[contains(normalize-space(), 'Zmiana hasła')]")).click();



        driver.findElement(By.cssSelector("input[id='ChangeMyPasswordForm:oldPassword']")).sendKeys(newPassword);

        driver.findElement(By.cssSelector("input[id='ChangeMyPasswordForm:newPassword']")).sendKeys(basicPassword);

        driver.findElement(By.cssSelector("input[id='ChangeMyPasswordForm:newPasswordRepeat']")).sendKeys(basicPassword);

        driver.findElement(By.cssSelector("input[value='Zmień hasło']")).click();



        driver.findElement(By.xpath("//a[contains(normalize-space(), 'Wylogowanie')]")).click();

        driver.findElement(By.cssSelector("input[value='Wyloguj']")).click();

    }



    @AfterClass

    public void tearDown() {

        driver.quit();

    }

}

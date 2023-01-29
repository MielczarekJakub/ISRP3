import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;



import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.firefox.FirefoxDriver;

import org.testng.Assert;

import org.testng.annotations.*;



public class EditClientNameTest {



    protected String newPassword = "noweH@s!0";

    protected String username1 = "JDoe";

    protected String username2 = "JDoe2";

    protected String basicPassword = "P@ssw0rd";

    WebDriver driver;

    protected ChromeOptions chromeOptions;



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

    public void test()  {





        driver.get("https://localhost:8181/faces/common/signIn.xhtml");



//        WebElement button = driver.findElement(By.id("details-button"));

//        button.click();

//

//        WebElement button2 = driver.findElement(By.id("proceed-link"));

//

//        button2.click();



        WebElement login = driver.findElement(By.xpath("//a[@href='../common/signIn.xhtml']"));



        login.click();



        WebElement inputLogin = driver.findElement(By.xpath("//input[@name='j_username']"));



        inputLogin.sendKeys("DMitchell");



        WebElement inputPassword = driver.findElement(By.xpath("//input[@name='j_password']"));



        inputPassword.sendKeys("P@ssw0rd");



        WebElement buttonLogin = driver.findElement(By.xpath("//input[@value='Zaloguj']"));



        buttonLogin.click();



        WebElement dropdown =

                driver.findElement(By.xpath("//a[contains(text(),'Konto użytkownika')]"));



        dropdown.click();



        WebElement accounts =

                driver.findElement(By.xpath("//a[contains(text(),'Lista kont użytkowników')]"));



        accounts.click();



        WebElement inputChange = driver.findElement(By.xpath("//input[@value='Edycja konta']"));



        inputChange.click();



        WebElement inputName = driver.findElement(By.id("EditForm:name"));



        inputName.clear();

        inputName.sendKeys("Maryla");



        WebElement saveChangeName = driver.findElement(By.xpath("//input[@value='Zapisz zmiany']"));



        saveChangeName.click();





        try {

            WebElement text = driver.findElement(By.xpath("//td[contains(text(),'Maryla')]"));

            Assert.assertNotNull(text);

        } catch (

                Exception e) {

            Assert.fail("Element not found");

        }



        WebElement logout = driver.findElement(By.xpath("//a[@href='../common/logout.xhtml']"));

        logout.click();



    }



    @AfterClass

    public void tearDown() {

        driver.quit();

    }

}

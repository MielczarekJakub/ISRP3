import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.time.Duration;


public class EditRoleTest {

    protected String role;
    protected String username1 = "JDoe";
    protected String username2 = "JDoe2";
    WebDriver driver;
    protected String basicPassword = "P@ssw0rd";


    @Parameters({"browser"})
    @BeforeClass
    public void test(String browser) {

        if (browser.equals("chrome")) {

            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();

        } else if (browser.equals("firefox")) {

            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();

        } else if (browser.equals("edge")) {



        } else {
            throw new RuntimeException("Browser is not supported");
        }
    }

    @Test
    public void editRoleTest() {



        driver.get("https://localhost:8181/faces/main/index.xhtml");
        // Log in
        driver.findElement(By.cssSelector("input[name='j_username']")).sendKeys(username2);
        driver.findElement(By.cssSelector("input[name='j_password']")).sendKeys(basicPassword);
        driver.findElement(By.cssSelector("input[value='Zaloguj']")).click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // navigate to accounts page
        driver.findElement(By.xpath("//a[contains(normalize-space(), 'Konto użytkownika')]")).click();
        driver.findElement(By.xpath("//a[contains(normalize-space(), 'Lista kont użytkowników')]")).click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // edit role
        WebElement row = driver.findElement(By.xpath("//tr[contains(normalize-space(), 'NGierczak')]"));
        Assert.assertNotNull(row);
        WebElement selectElement = row.findElement(By.cssSelector("select"));
        Select select = new Select(selectElement);
        select.selectByVisibleText(role);
        row.findElement(By.cssSelector("input[name='j_idt26:j_idt27:1:j_idt39']")).click();
        //option[selected='selected']
        // check if role is on the list
        row = driver.findElement(By.xpath("//tr[contains(normalize-space(), 'NGierczak')]"));
        WebElement element = row.findElement(By.cssSelector("option[selected='selected']"));
        Assert.assertEquals(role, element.getText());

        // logout
        driver.findElement(By.xpath("//a[contains(normalize-space(), 'Wylogowanie')]")).click();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}

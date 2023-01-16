import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;


public abstract class BaseTest {
    protected WebDriver driver;
    protected String baseUrl = "https://localhost:8181/faces/";
    protected String username1 = "JDoe";
    protected String password1 = "P@ssw0rd";
    protected String username2 = "DMitchell";
    protected static ChromeOptions chromeOptions;
    protected static EdgeOptions edgeOptions;


    @BeforeAll
    static void setupClass() {

        chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--allow-insecure-localhost");
        chromeOptions.addArguments("--ignore-ssl-errors=yes");
        chromeOptions.addArguments("--ignore-certificate-errors");

        edgeOptions = new EdgeOptions();
        edgeOptions.addArguments("--allow-insecure-localhost");
        edgeOptions.addArguments("--ignore-ssl-errors=yes");
        edgeOptions.addArguments("--ignore-certificate-errors");

        WebDriverManager.chromedriver().setup();
        WebDriverManager.edgedriver().setup();

    }

    @AfterEach
    public void closeBrowser() {
        driver.close();
        driver.quit();
    }

}
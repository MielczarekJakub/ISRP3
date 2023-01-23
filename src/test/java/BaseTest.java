import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public abstract class BaseTest {
    protected String username1 = "JDoe";
    protected String username2 = "JDoe2";

    protected String basicPassword = "P@ssw0rd";

    protected WebDriver driver;

    protected String baseUrl = "https://localhost:8181/faces/";

    protected static ChromeOptions chromeOptions;
    protected static FirefoxOptions firefoxOptions;


    @BeforeAll
    static void setupClass() {

        firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("--allow-insecure-localhost");
        firefoxOptions.addArguments("--ignore-ssl-errors=yes");
        firefoxOptions.addArguments("--ignore-certificate-errors");

        chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--allow-insecure-localhost");
        chromeOptions.addArguments("--ignore-ssl-errors=yes");
        chromeOptions.addArguments("--ignore-certificate-errors");

        WebDriverManager.chromedriver().setup();
        WebDriverManager.edgedriver().setup();

    }

    @AfterEach
    public void closeBrowser() {
        driver.close();
        driver.quit();
    }
}

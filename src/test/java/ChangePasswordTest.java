import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class ChangePasswordTest extends BaseTest {
    protected String newPassword = "noweH@s!0";

    @ParameterizedTest
    @ValueSource(classes={ChromeDriver.class, FirefoxDriver.class})
    public void basicTest(Class<? extends WebDriver> webDriverClass){
        if (webDriverClass.equals(ChromeDriver.class)) {
            driver = new ChromeDriver(chromeOptions);
        } else {
            driver = new FirefoxDriver(firefoxOptions);
        }

        driver.get(baseUrl + "common/signIn.xhtml");
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
        driver.get(baseUrl + "common/signIn.xhtml");
        driver.findElement(By.cssSelector("input[name='j_username']")).sendKeys(username1);
        driver.findElement(By.cssSelector("input[name='j_password']")).sendKeys(newPassword);

        driver.findElement(By.cssSelector("input[value='Zaloguj']")).click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        WebElement element = driver.findElement(By.xpath("//h4[contains(normalize-space(), 'JDoe')]"));
        Assertions.assertNotNull(element);

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
}

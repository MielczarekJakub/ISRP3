import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.time.Duration;

public class ChangePasswordTest extends BaseTest{

    protected String newPassword = "H@selk0!";

    @ParameterizedTest
    @ValueSource(classes={ChromeDriver.class, EdgeDriver.class})
    public void basicTest(Class<? extends WebDriver> webDriverClass){
        if (webDriverClass.equals(ChromeDriver.class)) {
            driver = new ChromeDriver(chromeOptions);
        } else {
            driver = new EdgeDriver(edgeOptions);
        }

        driver.get(baseUrl + "common/signIn.xhtml");
        // Log in
        driver.findElement(By.cssSelector("input[name='j_username']")).sendKeys(username1);
        driver.findElement(By.cssSelector("input[name='j_password']")).sendKeys(password1);

        driver.findElement(By.cssSelector("input[value='Zaloguj']")).click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));


        //navigate to change password page
        driver.findElement(By.xpath("//a[contains(normalize-space(), 'Ustawienia')]")).click();
        driver.findElement(By.xpath("//a[contains(normalize-space(), 'Zmiana hasła')]")).click();

        //changing password
        driver.findElement(By.cssSelector("input[id='ChangeMyPasswordForm:oldPassword']")).sendKeys(password1);
        driver.findElement(By.cssSelector("input[id='ChangeMyPasswordForm:newPassword']")).sendKeys(newPassword);
        driver.findElement(By.cssSelector("input[id='ChangeMyPasswordForm:newPasswordRepeat']")).sendKeys(newPassword);
        driver.findElement(By.cssSelector("input[value='Zmień hasło']")).click();

        //log out
        driver.findElement(By.xpath("//a[contains(normalize-space(), 'Wylogowanie')]")).click();
        driver.findElement(By.cssSelector("input[value='Wyloguj']")).click();

        //second login attempt with changed data
        driver.get(baseUrl + "common/signIn.xhtml");
        driver.findElement(By.cssSelector("input[name='j_username']")).sendKeys(username1);
        driver.findElement(By.cssSelector("input[name='j_password']")).sendKeys(newPassword);

        driver.findElement(By.cssSelector("input[value='Zaloguj']")).click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        WebElement element = driver.findElement(By.xpath("//h4[contains(normalize-space(), 'JDoe')]"));
        Assertions.assertNotNull(element);

        //restore original state
        driver.findElement(By.xpath("//a[contains(normalize-space(), 'Ustawienia')]")).click();
        driver.findElement(By.xpath("//a[contains(normalize-space(), 'Zmiana hasła')]")).click();

        driver.findElement(By.cssSelector("input[id='ChangeMyPasswordForm:oldPassword']")).sendKeys(newPassword);
        driver.findElement(By.cssSelector("input[id='ChangeMyPasswordForm:newPassword']")).sendKeys(password1);
        driver.findElement(By.cssSelector("input[id='ChangeMyPasswordForm:newPasswordRepeat']")).sendKeys(password1);
        driver.findElement(By.cssSelector("input[value='Zmień hasło']")).click();

        driver.findElement(By.xpath("//a[contains(normalize-space(), 'Wylogowanie')]")).click();
        driver.findElement(By.cssSelector("input[value='Wyloguj']")).click();
    }
}

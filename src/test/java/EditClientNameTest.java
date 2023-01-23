import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

public class EditClientNameTest extends BaseTest {

    @Test
    public void test(Class<? extends WebDriver> webDriverClass)  {
        if (webDriverClass.equals(ChromeDriver.class)) {
            driver = new ChromeDriver(chromeOptions);
        } else {
            driver = new FirefoxDriver(firefoxOptions);
        }

        driver.get("https://localhost:8181/faces/main/index.xhtml");

        WebElement button = driver.findElement(By.id("details-button"));
        button.click();

        WebElement button2 = driver.findElement(By.id("proceed-link"));

        button2.click();

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

//        find element by text on screen

        try {
            WebElement text = driver.findElement(By.xpath("//td[contains(text(),'Maryla')]"));
            Assert.assertNotNull(text);
        } catch (
                Exception e) {
            Assert.fail("Element not found");
        }


//        Assert.assertEquals("Maryla", driver.findElement(By.id("EditForm:name")).getText());
//        assert.equals("Maryla", inputName.getText());
        WebElement logout = driver.findElement(By.xpath("//a[@href='../common/logout.xhtml']"));
        logout.click();

    }
}

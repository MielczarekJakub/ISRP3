import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.time.Duration;


public class EditRoleTest extends BaseTest {

    protected String role;

    @ParameterizedTest
    @ValueSource(classes = {ChromeDriver.class, EdgeDriver.class})
    public void editRoleTest(Class<? extends WebDriver> webDriverClass) {
        if (webDriverClass.equals(ChromeDriver.class)) {
            driver = new ChromeDriver(chromeOptions);
            role = "Magazyn";
        } else {
            driver = new EdgeDriver(edgeOptions);
            role = "Biuro";
        }


        driver.get(baseUrl + "common/signIn.xhtml");
        // Log in
        driver.findElement(By.cssSelector("input[name='j_username']")).sendKeys(username2);
        driver.findElement(By.cssSelector("input[name='j_password']")).sendKeys(password1);
        driver.findElement(By.cssSelector("input[value='Zaloguj']")).click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // navigate to accounts page
        driver.findElement(By.xpath("//a[contains(normalize-space(), 'Konto użytkownika')]")).click();
        driver.findElement(By.xpath("//a[contains(normalize-space(), 'Lista kont użytkowników')]")).click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // edit role
        WebElement row = driver.findElement(By.xpath("//tr[contains(normalize-space(), 'NGierczak')]"));
        Assertions.assertNotNull(row);
        WebElement selectElement = row.findElement(By.cssSelector("select"));
        Select select = new Select(selectElement);
        select.selectByVisibleText(role);
        row.findElement(By.cssSelector("input[name='j_idt26:j_idt27:1:j_idt39']")).click();
        //option[selected='selected']
        // check if role is on the list
        row = driver.findElement(By.xpath("//tr[contains(normalize-space(), 'NGierczak')]"));
        WebElement element = row.findElement(By.cssSelector("option[selected='selected']"));
        Assertions.assertEquals(role, element.getText());

        // logout
        driver.findElement(By.xpath("//a[contains(normalize-space(), 'Wylogowanie')]")).click();
    }
}
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


public class CreateLocationTest extends BaseTest {

    // LOCATION PATTERN XX-00-00-00
    protected String locationName = "-01-02-03";
    protected String locationType = "JEDNA CZWARTA";
    protected String locationPattern;


    @ParameterizedTest
    @ValueSource(classes = {ChromeDriver.class, EdgeDriver.class})
    public void basicTest(Class<? extends WebDriver> webDriverClass) {
        if (webDriverClass.equals(ChromeDriver.class)) {
            driver = new ChromeDriver(chromeOptions);
            locationPattern = "CH" + locationName;
        } else {
            driver = new EdgeDriver(edgeOptions);
            locationPattern = "ED" + locationName;
        }


        driver.get(baseUrl + "common/signIn.xhtml");
        // Log in
        driver.findElement(By.cssSelector("input[name='j_username']")).sendKeys(username1);
        driver.findElement(By.cssSelector("input[name='j_password']")).sendKeys(password1);
        driver.findElement(By.cssSelector("input[value='Zaloguj']")).click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // navigate to create location page
        driver.findElement(By.xpath("//a[contains(normalize-space(), 'Lokalizacja')]")).click();
        driver.findElement(By.xpath("//a[contains(normalize-space(), 'Utwórz nową lokalizację')]")).click();

        // create location
        driver.findElement(By.cssSelector("input[id='CreateLocationForm:locationSymbol']")).sendKeys(locationPattern);
        WebElement selectElement = driver.findElement(By.cssSelector("select[id='CreateLocationForm:locationType']"));
        Select select = new Select(selectElement);
        select.selectByVisibleText(locationType);
        driver.findElement(By.cssSelector("input[name='CreateLocationForm:j_idt34']")).click();

        // navigate to location list
        driver.findElement(By.cssSelector("a[class='dropdown-toggle']"))
                .findElement(By.xpath("//a[contains(normalize-space(), 'Lokalizacja')]")).click();
        driver.findElement(By.xpath("//a[contains(normalize-space(), 'Lista lokalizacji')]")).click();


        // check if location is on the list
        WebElement element = driver.findElement(By.xpath("//td[contains(normalize-space(), "  + locationPattern + ")]"));
        Assertions.assertNotNull(element);

        // logout
        driver.findElement(By.xpath("//a[contains(normalize-space(), 'Wylogowanie')]")).click();
    }
}

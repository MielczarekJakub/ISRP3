import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.time.Duration;


public class CreateLocationTest extends BaseTest {

    // LOCATION PATTERN XX-00-00-00
    protected String locationName = "-01-02-03";
    protected String locationType = "JEDNA CZWARTA";
    protected String locationPattern;


    @Test
    public void basicTest(Class<? extends WebDriver> webDriverClass) {
        if (webDriverClass.equals(ChromeDriver.class)) {
            driver = new ChromeDriver(chromeOptions);
            locationPattern = "CH" + locationName;
        } else {
            driver = new FirefoxDriver(firefoxOptions);
            locationPattern = "ED" + locationName;
        }


        driver.get("https://localhost:8181/faces/main/index.xhtml");
        // Log in
        driver.findElement(By.cssSelector("input[name='j_username']")).sendKeys(username1);
        driver.findElement(By.cssSelector("input[name='j_password']")).sendKeys(basicPassword);
        driver.findElement(By.cssSelector("input[value='Zaloguj']")).click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // zmiana położenia do tworzenia lokalizacji
        driver.findElement(By.xpath("//a[contains(normalize-space(), 'Lokalizacja')]")).click();
        driver.findElement(By.xpath("//a[contains(normalize-space(), 'Utwórz nową lokalizację')]")).click();

        // stworzenie lokalizacji
        driver.findElement(By.cssSelector("input[id='CreateLocationForm:locationSymbol']")).sendKeys(locationPattern);
        WebElement selectElement = driver.findElement(By.cssSelector("select[id='CreateLocationForm:locationType']"));
        Select select = new Select(selectElement);
        select.selectByVisibleText(locationType);
        driver.findElement(By.cssSelector("input[name='CreateLocationForm:j_idt34']")).click();

        // zmiana położenia na listę lokalizacji
        driver.findElement(By.cssSelector("a[class='dropdown-toggle']"))
                .findElement(By.xpath("//a[contains(normalize-space(), 'Lokalizacja')]")).click();
        driver.findElement(By.xpath("//a[contains(normalize-space(), 'Lista lokalizacji')]")).click();


        // czy lokacja na liscie
        WebElement element = driver.findElement(By.xpath("//td[contains(normalize-space(), "  + locationPattern + ")]"));
        Assertions.assertNotNull(element);

        // wylogowanie
        driver.findElement(By.xpath("//a[contains(normalize-space(), 'Wylogowanie')]")).click();
    }
}

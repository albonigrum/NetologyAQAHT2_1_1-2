package ru.netology;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.openqa.selenium.By.cssSelector;
import static org.junit.jupiter.api.Assertions.*;


public class DebitCardFormTest {
    WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("windows"))
            os = "win";
        else if (os.contains("mac"))
            os = "mac";
        else
            os = "linux";
        System.setProperty("webdriver.chrome.driver",
                "./driver/" + os + "/chromedriver" + (os.equals("win") ? ".exe" : ""));
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1080");

        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldHappyPath() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Иван Васильевич");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+79012345678");
        driver.findElement(cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(cssSelector("[role=button]")).click();
        String actual = driver.findElement(cssSelector("[data-test-id=order-success]")).getText().trim();

        assertTrue(actual.contains("Ваша заявка успешно отправлена!"));
    }

}

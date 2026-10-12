package ui;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SauceDemoTest {
    private static final String BASE_URL =
            "https://www.saucedemo.com/";

    @Test
    void failedLogin() {

        WebDriver driver = new ChromeDriver();

        try {

            WebDriverWait wait = new WebDriverWait(
                    driver,
                    Duration.ofSeconds(30)
            );

            System.out.println("========================================");
            System.out.println("SCENARIO 1 - FAILED LOGIN");
            System.out.println("========================================");

            // 1. Open browser
            driver.get(BASE_URL);

            // 2. Enter invalid username
            WebElement username = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.id("user-name")
                    )
            );

            username.sendKeys("invaliduser");
            System.out.println("Username : invaliduser");

            // 3. Enter invalid password
            WebElement password = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.id("password")
                    )
            );

            password.sendKeys("invalidpassword");
            System.out.println("Password      : invalidpassword");

            // 4. Click Login
            WebElement loginButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.id("login-button")
                    )
            );

            loginButton.click();
            System.out.println("Login         : CLICKED");


            // 4. Wait for failed login message
            WebElement errorMessage = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector("[data-test='error']")
                    )
            );

            String actualMessage = errorMessage.getText();
            String expectedMessage =
                    "Epic sadface: Username and password do not match any user in this service";

            System.out.println("Actual Error  : " + actualMessage);
            System.out.println("Expected Error: " + expectedMessage);

            assertTrue(
                    actualMessage.equals(expectedMessage),
                    "Failed login message is not displayed correctly"
            );

            System.out.println("Assertion     : PASS");

        } finally {
            driver.quit();
        }
    }

    @Test
    void successfulLoginHardcoded() {

        WebDriver driver = new ChromeDriver();

        try {

            WebDriverWait wait = new WebDriverWait(
                    driver,
                    Duration.ofSeconds(5)
            );

            System.out.println("========================================");
            System.out.println("SCENARIO 2 - SUCCESSFUL LOGIN HARDCODED");
            System.out.println("========================================");

            // 1. Open browser
            driver.get(BASE_URL);

            // 2. Enter hardcoded username
            WebElement username = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.id("user-name")
                    )
            );

            username.sendKeys("standard_user");
            System.out.println("Username      : standard_user");

            // 3. Enter hardcoded password
            WebElement password = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.id("password")
                    )
            );

            password.sendKeys("secret_sauce");
            System.out.println("Password      : secret_sauce");

            // 4. Click Login
            WebElement loginButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.id("login-button")
                    )
            );

            loginButton.click();
            System.out.println("Login         : CLICKED");


            // Wait until inventory page is displayed
            wait.until(
                    ExpectedConditions.urlContains("/inventory.html")
            );

            System.out.println("========================================");
            System.out.println("LOGIN RESULT");
            System.out.println("========================================");

            System.out.println(
                    "Current URL   : " + driver.getCurrentUrl()
            );

            System.out.println(
                    "Page Title    : " + driver.getTitle()
            );

            // Validate login
            assertTrue(
                    driver.getCurrentUrl().contains("/inventory.html"),
                    "Login was not successful"
            );
            System.out.println("Assertion     : PASS");

        } finally {
            driver.quit();
        }
    }

    @Test
    void successfulLoginDynamicFromExcel() throws IOException {

        WebDriver driver = new ChromeDriver();

        try {

            WebDriverWait wait = new WebDriverWait(
                    driver,
                    Duration.ofSeconds(5)
            );

            System.out.println("========================================");
            System.out.println("SCENARIO 3 - SUCCESSFUL LOGIN FROM EXCEL");
            System.out.println("========================================");

            // Read login data from Excel
            String[] loginData = readLoginData();

            String usernameData = loginData[0];
            String passwordData = loginData[1];

            System.out.println("Excel Username : " + usernameData);
            System.out.println("Excel Password : " + passwordData);

            // 1. Open browser
            driver.get(BASE_URL);

            // 2. Enter username
            WebElement username = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.id("user-name")
                    )
            );

            username.sendKeys(usernameData);

            // 3. Enter password
            WebElement password = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.id("password")
                    )
            );

            password.sendKeys(passwordData);

            // 4. Click Login
            WebElement loginButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.id("login-button")
                    )
            );

            loginButton.click();

            // Wait until inventory page is displayed
            wait.until(
                    ExpectedConditions.urlContains("/inventory.html")
            );

            System.out.println("========================================");
            System.out.println("LOGIN RESULT");
            System.out.println("========================================");

            System.out.println(
                    "Current URL : " + driver.getCurrentUrl()
            );

            System.out.println(
                    "Page Title  : " + driver.getTitle()
            );

            assertTrue(
                    driver.getCurrentUrl().contains("/inventory.html"),
                    "Login was not successful"
            );

            System.out.println("Assertion   : PASS");

        } finally {
            driver.quit();
        }
    }



    private String[] readLoginData() throws IOException {

        String filePath =
                "src/test/resources/SauceDemo_Credential.xlsx";

        try (
                FileInputStream fileInputStream =
                        new FileInputStream(filePath);

                Workbook workbook =
                        WorkbookFactory.create(fileInputStream)
        ) {

            // Match it with the worksheet name in Excel.
            Sheet sheet = workbook.getSheet("LoginData");

            if (sheet == null) {
                throw new IllegalStateException(
                        "Worksheet 'LoginData' tidak ditemukan"
                );
            }

            // Row index 0 = header
            // Row index 1 = login data
            Row row = sheet.getRow(1);

            if (row == null) {
                throw new IllegalStateException(
                        "Baris data login tidak ditemukan"
                );
            }

            String username =
                    row.getCell(0).getStringCellValue();

            String password =
                    row.getCell(1).getStringCellValue();

            return new String[]{
                    username,
                    password
            };
        }
    }
}

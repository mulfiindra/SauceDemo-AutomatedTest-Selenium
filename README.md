# SauceDemo UI Automation Testing

A UI automation testing project built with **Java, Selenium WebDriver, JUnit 5, and Maven** to validate login functionality on [SauceDemo](https://www.saucedemo.com/). This project demonstrates automated positive and negative login testing, explicit waits, assertions, and data-driven testing using Excel.

## Overview

This project automates three login test scenarios to verify that SauceDemo handles valid and invalid credentials as expected.

### Test Scenarios

| ID | Test Scenario | Test Type | Expected Result |
|---|---|---|---|
| TC-01 | Login with invalid credentials | Negative Testing | An appropriate error message is displayed. |
| TC-02 | Login with valid hardcoded credentials | Positive Testing | The user is redirected to the inventory page. |
| TC-03 | Login with valid credentials from Excel | Data-Driven Testing | Credentials are read from Excel and the user is redirected to the inventory page. |

## Tech Stack

- **Java 21** — Programming language
- **Selenium WebDriver 4.51.0** — Browser automation
- **JUnit Jupiter 5.13.4** — Test framework and assertions
- **Apache POI 5.5.1** — Reading Excel test data
- **Apache Maven** — Dependency and project management
- **Google Chrome** — Browser under test
- **IntelliJ IDEA** — Development environment

## Project Structure

```text
SauceDemo-AutomatedTest-Selenium/
├── pom.xml
├── .gitignore
├── README.md
└── src/
    ├── main/
    │   └── java/
    │       └── org/example/
    │           └── Main.java
    └── test/
        ├── java/
        │   └── ui/
        │       └── SauceDemoTest.java
        └── resources/
            └── SauceDemo_Credential.xlsx
```

## Test Implementation

### 1. Failed Login

- Opens the SauceDemo login page.
- Enters invalid username and password combinations.
- Clicks the Login button.
- Waits for the error message to appear.
- Validates the displayed message using an assertion.

### 2. Successful Login — Hardcoded Credentials

- Opens the login page.
- Enters valid credentials directly in the test script.
- Clicks the Login button.
- Waits for the inventory page to load.
- Verifies that the URL contains `/inventory.html`.

### 3. Successful Login — Excel Data

- Reads username and password from an Excel workbook using Apache POI.
- Retrieves login data from the `LoginData` worksheet.
- Enters the credentials into the login form.
- Waits for navigation to the inventory page.
- Validates the login result using an assertion.

## Test Data

The Excel file is located at:

`src/test/resources/SauceDemo_Credential.xlsx`

The workbook uses a worksheet named `LoginData` with the following structure:

| Username | Password |
|---|---|
| `standard_user` | `secret_sauce` |

The first row contains the column headers, and the second row contains the credentials used by the automated test.

The credentials are intended for the SauceDemo demonstration environment only. Do not store real account credentials or sensitive information in a public repository.

## Prerequisites

Before running the tests, ensure that you have:

- Java Development Kit (JDK) 21 installed.
- Apache Maven installed, or Maven support configured in your IDE.
- Google Chrome installed.
- Git installed if you intend to clone the repository.
- An active internet connection to access SauceDemo and download Maven dependencies.

## How to Run

### 1. Clone the repository

```bash
git clone <your-repository-url>
cd SauceDemo-AutomatedTest-Selenium
```

Replace `<your-repository-url>` with your GitHub repository URL.

### 2. Verify the Java installation

```bash
java -version
mvn -version
```

Make sure Maven is using Java 21.

### 3. Run all automated tests

```bash
mvn clean test
```

Maven will compile the project, resolve the dependencies, and execute the JUnit tests.

To run the test from IntelliJ IDEA instead, open `SauceDemoTest.java` and execute the test class or an individual `@Test` method.

## Test Validation

The tests use Selenium explicit waits to wait for relevant page elements or URL changes before continuing.

JUnit assertions verify the expected outcomes, including:

- The invalid-login error message matches the expected text.
- Successful login redirects to the inventory page.
- Credentials read from Excel can be used in the login flow.

Test execution details are printed to the console. Maven Surefire also generates test reports under:

`target/surefire-reports/`

## Key Learning Outcomes

This project demonstrates practical experience with:

- Web UI automation using Selenium WebDriver.
- Positive and negative test scenarios.
- Element identification using Selenium locators.
- Explicit waits with `WebDriverWait` and `ExpectedConditions`.
- Assertions using JUnit 5.
- Data-driven testing using Excel and Apache POI.
- Maven dependency management.
- Organizing automated tests in a Java project.

## Future Improvements

Potential enhancements for this project include:

- Implementing Page Object Model (POM) for better maintainability.
- Parameterizing multiple login test cases using Excel data.
- Adding more test scenarios, including locked-out users and invalid input validation.
- Generating more readable HTML test reports.
- Integrating automated tests into a CI/CD pipeline using GitHub Actions.
- Improving test configuration and browser compatibility management.

## Disclaimer

This project is intended for learning and portfolio demonstration purposes. Tests are executed against the publicly available SauceDemo demonstration website and may be affected by changes to the application or test environment.

---

**Author:** Mulfi Indra Gunawan

**Focus:** Software Quality Assurance | Manual Testing | UI Automation Testing

**Application Under Test:** [SauceDemo](https://www.saucedemo.com/)
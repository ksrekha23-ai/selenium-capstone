# SauceDemo Selenium Test Automation Framework

A complete test automation framework for [SauceDemo](https://www.saucedemo.com) built with Selenium WebDriver, Java, and TestNG. Implements the Page Object Model pattern with ExtentReports for HTML reporting.

## Prerequisites

- **JDK 17 or later** with `JAVA_HOME` configured
- **Maven 3.6+** installed and available on `PATH`
- **Chrome browser** installed (default) or Firefox/Edge
- **Git** for version control

## Project Structure

```
selenium-capstone/
├── pom.xml                          # Maven config with all dependencies
├── testng.xml                       # TestNG suite (smoke + regression groups)
├── src/
│   ├── main/java/com/saucedemo/
│   │   ├── config/
│   │   │   └── ConfigReader.java    # Loads config.properties
│   │   ├── pages/
│   │   │   ├── BasePage.java        # Common page interactions with waits
│   │   │   ├── LoginPage.java
│   │   │   ├── ProductsPage.java
│   │   │   ├── ProductDetailPage.java
│   │   │   ├── CartPage.java
│   │   │   ├── CheckoutStepOnePage.java
│   │   │   ├── CheckoutStepTwoPage.java
│   │   │   ├── CheckoutCompletePage.java
│   │   │   └── NavigationPage.java
│   │   └── utils/
│   │       ├── WaitHelper.java      # Explicit wait utilities
│   │       ├── ScreenshotHelper.java # Failure screenshot capture
│   │       └── ExtentReportManager.java # HTML report management
│   ├── main/resources/
│   │   └── config.properties        # Browser, URL, wait configs
│   └── test/java/com/saucedemo/tests/
│       ├── BaseTest.java            # Shared setup/teardown
│       ├── LoginTest.java           # Login positive/negative tests
│       ├── ProductListingTest.java  # Sorting and product count
│       ├── ProductDetailTest.java   # Detail page verification
│       ├── CartTest.java            # Cart add/remove/verify
│       ├── CheckoutTest.java        # Checkout flow and validation
│       ├── NavigationTest.java      # Sidebar menu tests
│       └── EndToEndTest.java        # Full happy path
├── screenshots/                     # Auto-captured on test failure
└── reports/                         # ExtentReports HTML output
```

## Setup

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd selenium-capstone
   ```

2. Verify Java and Maven:
   ```bash
   java -version
   mvn -version
   ```

3. (Optional) Edit `src/main/resources/config.properties` to change browser or wait times.

## Running Tests

**Run the full suite:**
```bash
mvn clean test
```

**Run smoke tests only:**
```bash
mvn clean test -Dgroups=smoke
```

**Run regression tests only:**
```bash
mvn clean test -Dgroups=regression
```

**Run a specific test class:**
```bash
mvn clean test -Dtest=LoginTest
```

## Test Reports

After each run, an HTML report is generated at `reports/TestReport.html`. Open it in any browser to see pass/fail/skip counts with detailed logs.

Screenshots of failed tests are saved to `screenshots/` with the format `TestName_yyyy-MM-dd_HH-mm-ss.png`.

## Test Coverage

| Module | Tests | Coverage |
|--------|-------|----------|
| Login | 6 | Valid login, invalid password, empty fields, locked user, logout + back |
| Product Listing | 5 | Product count, sort A-Z, Z-A, price low-high, price high-low |
| Product Details | 4 | Navigation, detail matching, add to cart, back button |
| Shopping Cart | 5 | Add single/multiple, remove, verify details, continue shopping |
| Checkout | 6 | Complete flow, empty form, missing fields, summary, price calc |
| Navigation | 5 | Sidebar open/close, all items, about, logout, reset |
| End-to-End | 1 | Full happy path with assertions at each step |

## Key Design Decisions

- **Page Object Model**: All locators live in page classes. Test classes only call page methods.
- **Explicit waits**: No `Thread.sleep()`. All waits use `WebDriverWait` with configurable timeout.
- **Config-driven**: Browser, URL, credentials, and wait times are externalized to `config.properties`.
- **WebDriverManager**: Automatic browser driver management, no manual driver downloads.
- **Screenshot on failure**: Automatic capture in `@AfterMethod` with test name and timestamp.

## Technologies

- Java 17
- Selenium WebDriver 4.52
- TestNG 7.10
- ExtentReports 5.1
- WebDriverManager 6.4
- Maven 3.9

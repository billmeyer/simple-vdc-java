package com.saucelabs.example.features;

import com.saucelabs.example.pages.InventoryPage;
import com.saucelabs.example.pages.LoginPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class LoginFeatures extends BaseFeature
{
    private static String username = System.getenv("SAUCE_USERNAME");
    private static String accessKey = System.getenv("SAUCE_ACCESS_KEY");

    @Test
    public void verifyValidUsersCanSignIn()
    throws MalformedURLException
    {
        URL url = new URL("https://ondemand.saucelabs.com:443/wd/hub");

        DesiredCapabilities caps = DesiredCapabilities.edge();
        caps.setCapability("version", "80.0");

//        DesiredCapabilities caps = DesiredCapabilities.firefox();
//        caps.setCapability("version", "73.0");

//        DesiredCapabilities caps = DesiredCapabilities.chrome();
//        caps.setCapability("version", "79.0");

        caps.setCapability("platform", "Windows 10");
        caps.setCapability("username", username);
        caps.setCapability("accessKey", accessKey);
        caps.setCapability("name", "Verify Valid Users Can Sign In");
        caps.setCapability("build", "build-1234");

        /**
         * Enable Sauce Performance for this test by setting capturePerformance to true.
         */
        caps.setCapability("extendedDebugging", true);
//        caps.setCapability("capturePerformance", true);

        RemoteWebDriver driver = new RemoteWebDriver(url, caps);
        JavascriptExecutor jsExec = (JavascriptExecutor)driver;
        String userAgent = (String)jsExec.executeScript("return navigator.userAgent");
        jsExec.executeScript("sauce:context=>>> Browser User-Agent is " + userAgent);

        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = new InventoryPage(driver);

        jsExec.executeScript("sauce:context=>>> Verify we are on the Inventory Page");

        loginPage.navigateTo(LoginPage.PAGE_URL);
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");

        loginPage.clickLogin();
        inventoryPage.waitForPageLoad();

        jsExec.executeScript("sauce:job-result=true");
        driver.quit();
    }
}

import io.github.bonigarcia.wdm.ChromeDriverManager
import io.github.bonigarcia.wdm.FirefoxDriverManager
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver

// Location where Geb saves the screenshots and HTML dumps at the end of each test
reportsDir = 'build/reports/geb'

// Run tests in Chrome by default
driver = {
    // Download and configure Marionette using https://github.com/bonigarcia/webdrivermanager
    ChromeDriverManager.getInstance().setup()

    new ChromeDriver()
}

environments {

    // run as "grails -Dgeb.env=chrome test-app -integration"
    // or "gradlew -Dgeb.env=chrome integrationTest"
    // ChromeDriver reference: https://sites.google.com/a/chromium.org/chromedriver/
    chrome {
        // Download and configure ChromeDriver using https://github.com/bonigarcia/webdrivermanager
        ChromeDriverManager.getInstance().setup()

        driver = { new ChromeDriver() }
    }

    firefox {
        FirefoxDriverManager.getInstance().setup()

        driver = { new FirefoxDriver() }
    }
}
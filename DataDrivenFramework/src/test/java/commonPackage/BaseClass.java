package commonPackage;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import commonJava.Utilities;

public class BaseClass {
	public static WebDriver driver;
	private static WebDriverWait wait;
	private String browserToLaunch = "";
	private String url="";

	@BeforeMethod
	public void launchURL() {
		String baseDir = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources" + File.separator + "executables" + File.separator;
		String executablePath;

		browserToLaunch = Utilities.getInstance().getBrowser();
		url=Utilities.getInstance().readJsonResources("DataList.json").getJSONObject(Utilities.getInstance().getEnvironment()).getString("url");
		System.out.println("got the url from datalist.json -->"+url);
				
		if (driver == null) {
			if (browserToLaunch.equalsIgnoreCase("chrome")) {
				executablePath = baseDir + "chromedriver.exe";
				System.out.println(executablePath);
				System.setProperty("webdriver.chrome.driver", executablePath);
				driver = new ChromeDriver();
			} else if (browserToLaunch.equalsIgnoreCase("fireFox")) {
				executablePath = baseDir + "geckodriver.exe";
				System.out.println(executablePath);
				System.setProperty("webdriver.gecko.driver", executablePath);
				driver = new FirefoxDriver();
			} else if (browserToLaunch.equalsIgnoreCase("ie")) {
				executablePath = baseDir + "IEDriverServer.exe";
				System.out.println(executablePath);
				System.setProperty("webdriver.ie.driver", executablePath);
				driver = new InternetExplorerDriver();
			} else if (browserToLaunch.equalsIgnoreCase("edge")) {
				executablePath = baseDir + "msedgedriver.exe";
				System.out.println(executablePath);
				System.setProperty("webdriver.edge.driver", executablePath);
				driver = new EdgeDriver();
			}
		}

		if(url.equalsIgnoreCase("")) {
			driver.get("about:blank");
		}else {
			driver.get(url);
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		System.out.println("************Browser launched --> " + browserToLaunch + "**************");

	}

	@AfterMethod
	public void tearDown() {
		System.out.println("************Calling TearDown*************");
		if (driver != null) {
			driver.quit();
			driver = null;
			System.out.println("============Test finished Browser Closed -->" + browserToLaunch + "=========");
		}
	}

	public static WebDriverWait explicitWait(int waitTimeInSeconds) {
		wait = new WebDriverWait(driver, waitTimeInSeconds);
		return wait;

	}

}

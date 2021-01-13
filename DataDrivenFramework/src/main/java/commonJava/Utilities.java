package commonJava;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

import org.apache.maven.surefire.testng.TestNGReporter;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.yaml.snakeyaml.Yaml;

import com.paypal.selion.platform.dataprovider.DataProviderFactory;
import com.paypal.selion.platform.dataprovider.SeLionDataProvider;
import com.paypal.selion.platform.dataprovider.impl.FileSystemResource;
import com.thoughtworks.qdox.tools.QDoxTester.Reporter;

public class Utilities {
	private static Utilities utilitiesInstance;

	private Utilities() {

	}

	public static Utilities getInstance() {
		if (utilitiesInstance == null) {
			utilitiesInstance = new Utilities();
		}
		return utilitiesInstance;
	}

	public JSONObject readJsonResources(String filename) {
		File datalistFile = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources" + File.separator + filename);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(datalistFile);
		} catch (FileNotFoundException e) {
			System.out.println("DataList.json file not found");
			e.printStackTrace();
		}
		JSONTokener tokener = new JSONTokener(fis);
		JSONObject resourceJsonObject = new JSONObject(tokener);
		return resourceJsonObject;
	}

	public String getBrowser() {
		JSONObject runnerObject = readJsonResources("runner.json");
		String browser = runnerObject.getString("runner").split(",")[0];
		System.out.println("browser mentioned in runner.json -->" + browser);
		return browser;

	}

	public String getEnvironment() {
		JSONObject runnerObject = readJsonResources("runner.json");
		String environment = runnerObject.getString("runner").split(",")[1];
		System.out.println("Environment mentioned in runner.json -->" + environment);
		return environment;

	}

	public WebElement getElementFromProperties(WebDriver driver, String element) {
		FileReader reader = null;

		try {
			reader = new FileReader(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
					+ File.separator + "resources" + File.separator + "WebElements.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}

		Properties prop = new Properties();
		try {
			prop.load(reader);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		String locatorString = prop.getProperty(element);
		String[] locatorStringSplitArray = locatorString.split("=", 2);
		String locatorStrategyString = locatorStringSplitArray[0].trim();
		String findByString = locatorStringSplitArray[1].trim();
		System.out.println("locatorStrategyString-->" + locatorStrategyString);
		System.out.println("findByString-->" + findByString);

		switch (locatorStrategyString) {
		case "id":
			return driver.findElement(By.id(findByString));
		case "xpath":
			return driver.findElement(By.xpath(findByString));
		default:
			System.out.println("locator strategy not found");
			return null;
		}
	}

	public Map<Object, Object> getDataFromYamlFil(String fileName) {
		String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources" + File.separator + fileName;
		FileInputStream fi = null;
		try {
			fi = new FileInputStream(new File(filePath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Yaml yaml = new Yaml();
		Map<Object, Object> dataFromYaml = yaml.load(fi);
		System.out.println("yaml-->" + dataFromYaml.toString());
		return dataFromYaml;
	}

	public String getExpectedString(String toLookFor) {
		FileReader reader = null;

		try {
			reader = new FileReader(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
					+ File.separator + "resources" + File.separator + "ExpectedStrings.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}

		Properties prop = new Properties();
		try {
			prop.load(reader);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		String expectedString = prop.getProperty(toLookFor);
		return expectedString;
	}
}

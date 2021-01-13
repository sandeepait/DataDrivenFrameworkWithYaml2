package tests;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import commonJava.Utilities;
import commonPackage.BaseClass;

public class LoginTest extends BaseClass{
	
	@Test(dataProvider="login_data")
	public void loginTest(String username, String pwd) {
		Utilities.getInstance().getElementFromProperties(BaseClass.driver, "homepage.usernameInputBox").sendKeys(username);;
		Utilities.getInstance().getElementFromProperties(BaseClass.driver, "homepage.pwdInputBox").sendKeys(pwd);;
		Utilities.getInstance().getElementFromProperties(BaseClass.driver, "homepage.loginButton").click();
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String actualError=Utilities.getInstance().getElementFromProperties(BaseClass.driver, "homepage.usernameError").getText();
		String expectedError=Utilities.getInstance().getExpectedString("homepage.username.error");
		
		Assert.assertEquals(actualError, expectedError, "Username error is not as expected:-");
		Reporter.log("Test passed for username-"+username+" and pwd-"+pwd+".", true);
		
		
		
	}
	
	@DataProvider(name = "login_data")
    public static Object[][] getData() {
		
		Map<Object, Object> dataFromYaml= Utilities.getInstance().getDataFromYamlFil("LoginData.yaml");
		Object[][] array = new Object[dataFromYaml.size()][2];
		
		Iterator it = dataFromYaml.entrySet().iterator();
		int i=0;
		while(it.hasNext()) {
			Entry entry = (Entry) it.next();
			array[i][0]=entry.getKey();
			array[i][1]=entry.getValue();
			i++;
		}
		
		
		System.out.println(Arrays.deepToString(array));
       
		return array;
    }

}

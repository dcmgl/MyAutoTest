package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import pages.AmrPage;
import pages.LoginPage;
import pages.UnknowAsset;

@Test
public class MyBrowser {
	private static org.apache.log4j.Logger log = TestLogger.getLogger(MyBrowser.class);
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public void loginAmr() throws InterruptedException{
		WebDriver driver = SetUpProAndBrowser.getDriver();
		driver.get("http://192.168.186.66");
		try{
			LoginPage lp = new LoginPage(driver);
			AmrPage amr = lp.loginAmr("root", "ultrasafe", driver);
			amr.goToUnknowAsset();
			Thread.sleep(2000);
			UnknowAsset unknowAsset = new UnknowAsset(driver);
			unknowAsset.queryBtn.click();
			unknowAsset.resetBtn.click();
			Thread.sleep(5000);
			
			amr.goToAssetManager();
			Thread.sleep(5000);
			amr.goToTaskManager();
			Thread.sleep(5000);
		} catch (Exception e){
			e.printStackTrace();
			driver.quit();
		}
		
		driver.quit();
	}
	

}

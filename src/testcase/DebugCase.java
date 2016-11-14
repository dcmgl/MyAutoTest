package testcase;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.Zip;
import org.testng.annotations.Test;

import pages.AmrPage;
import pages.AssetManagerView;
import pages.LoginPage;
import pages.UnknowAsset;
import base.MyRetryListener;
import base.SetUpProAndBrowser;

public class DebugCase extends TestCase{
	@Test
	public void loginAmr() throws InterruptedException{
			driver.get("http://192.168.186.66");
			LoginPage lp = new LoginPage(driver);
			AmrPage amr = lp.loginAmr("root", "ultrasafe", driver);
			amr.goToAssetManager();
			AssetManagerView amv = new AssetManagerView(driver);
			amv.delBtn.click();
			amv.Dialog.findElement(By.linkText("È¡Ïû")).click();
			amr.goToTaskManager();
	}
	
}

package testcase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import util.ScreenShot;

import base.InitBrowser;

public class DebugCase2 extends TestCase {
	@Test
	public void test() {
//		try {
			
			System.out.println("当前线程是"+Thread.currentThread().getId());
			driver.get("https://www.baidu.com");
			WebElement ele = driver.findElement(By.xpath("//input[@value='百度一下']"));
			Assert.assertEquals(ele.getAttribute("value"), "百度一下");
			
	}
	
	@Test(dependsOnMethods={"testcase.DebugCase2.test"} )
	public void test2(){
		WebElement ele = driver.findElement(By.xpath("//input[@value='百度一下']"));
		ele.click();
	}
}

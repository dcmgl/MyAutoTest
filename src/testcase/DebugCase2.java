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
			
			System.out.println("��ǰ�߳���"+Thread.currentThread().getId());
			driver.get("https://www.baidu.com");
			WebElement ele = driver.findElement(By.xpath("//input[@value='�ٶ�һ��']"));
			Assert.assertEquals(ele.getAttribute("value"), "�ٶ�һ��");
			
	}
	
	@Test(dependsOnMethods={"testcase.DebugCase2.test"} )
	public void test2(){
		WebElement ele = driver.findElement(By.xpath("//input[@value='�ٶ�һ��']"));
		ele.click();
	}
}

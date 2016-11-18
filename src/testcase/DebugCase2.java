package testcase;

import java.util.Map;

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

		System.out.println("��ǰ�߳���" + Thread.currentThread().getName());
		driver.get("https://www.baidu.com");
		WebElement ele = driver.findElement(By.xpath("//input[@value='�ٶ�һ��']"));
		Assert.assertEquals(ele.getAttribute("value"), "�ٶ�һ��");

	}

	@Test(dataProvider = "searchData")
	public void test2(Map<String, String> searchData) {
		driver.get("https://www.baidu.com");
		WebElement ele1 = driver.findElement(By
				.xpath("//form[@id='form']/span/input"));
		ele1.sendKeys(searchData.get("from"));
		WebElement ele = driver.findElement(By.xpath("//input[@value='�ٶ�һ��']"));
		ele.click();
		driver.navigate().to(searchData.get("url"));
	}
}

package testcase;

import org.testng.annotations.BeforeClass;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import base.InitBrowser;
import base.SetUpProAndBrowser;
import base.TestLogger;


public class TestCase {
	Logger log = TestLogger.getLogger(getClass());
	public WebDriver driver = null;
	private Throwable throwAble = null;
	
	/**
	 * 测试之前一系列初始化
	 */
	@BeforeTest
	void caseBeforeTest(){
		System.out.println("------------caseBeforeTest-----------------");
		System.out.println("当前执行的类名称是："+this.getClass());
		driver = new InitBrowser().getDriver();
	}
	
	
	@AfterTest(alwaysRun=true)
	void caseAfterTest(){
		log.info("broswer is tear down!");
//		SetUpProAndBrowser.tearDownBroswer();
		driver.quit();
	}


	public Throwable getThrowAble() {
		return throwAble;
	}


	public void setThrowAble(Throwable throwAble) {
		this.throwAble = throwAble;
	}
	
}

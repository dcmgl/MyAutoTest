package testcase;

import java.util.Date;
import java.util.Iterator;

import org.testng.annotations.BeforeClass;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import util.TestData;

import base.InitBrowser;
import base.SetUpProAndBrowser;
import base.TestLogger;


public class TestCase {
	Logger log = TestLogger.getLogger(getClass());
	public WebDriver driver = null;
	private Throwable throwAble = null;
	
	/**
	 * ����֮ǰһϵ�г�ʼ��
	 */
	@BeforeTest
	void caseBeforeTest(){
//		long date = new Date().getTime();
		int num =(int) (Math.random()*1000);
		Thread.currentThread().setName(String.valueOf(num));
		log.info("------------caseBeforeTest-----------------");
		log.info("��ǰִ�е��������ǣ�"+this.getClass()+",��Ӧ�����ǣ�"+Thread.currentThread().getName());
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
	
	@DataProvider(name = "searchData")
	public Iterator<Object[]> provider(){
		TestData testData = new TestData("C:\\TestNG_WORKSPACE\\Test1\\data\\userData.xls", "�ڶ���");
		return testData.iterator();
	}
	
	@DataProvider(name = "searchData2")
	public Iterator<Object[]> provider2(){
		TestData testData = new TestData("C:\\TestNG_WORKSPACE\\Test1\\data\\userData.xls", "��һ��");
		return testData.iterator();
	}
}

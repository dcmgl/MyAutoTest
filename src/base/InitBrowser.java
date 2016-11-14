package base;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class InitBrowser {
	private static Logger log = TestLogger.getLogger(SetUpProAndBrowser.class);
	private static Properties pro = null ;
	private   WebDriver driver = null;
	InitBrowser initBrowser = null;
	
	public InitBrowser(){
		initDriverPro();
	}
	
	public  WebDriver getDriver(){
		EventFiringWebDriver eDriver = new EventFiringWebDriver(driver);
//		MyWebDriverEventListener listener = new MyWebDriverEventListener();
		TestCaseListener listener = new TestCaseListener();
		driver = eDriver.register(listener);
		return driver;
	}
	
	public  void tearDownBroswer(){
		if(driver == null){
			return;
		}else {
			log.info("ä¯ÀÀÆ÷¼´½«¹Ø±Õ");
			driver.quit();
		}
	}
	
	private  void initDriverPro(){
		loadPro();
		if(driver!=null){
			log.error("driver is not null");
			return;
		}
		if("chrome".equals(pro.getProperty("browser.used"))){
			System.setProperty("webdriver.chrome.driver", pro.getProperty("webdriver.chrome.driver"));
			driver = new ChromeDriver();
			log.info("ChromeDriver has been started!");
		}else if("ie".equals(pro.getProperty("browser.used"))){
			System.setProperty("webdriver.ie.driver", pro.getProperty("webdriver.ie.driver"));
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			driver = new InternetExplorerDriver(cap);
			log.info("IEDriver has been started!");
		}else {
			System.setProperty("webdriver.chrome.driver", pro.getProperty("webdriver.chrome.driver"));
			driver = new ChromeDriver();
			log.info("Default driver(chrome) has been started!");
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	private static void loadPro(){
		pro = TestProperties.getProperties();
	}
	
}

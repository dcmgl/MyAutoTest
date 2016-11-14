package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class SetUpProAndBrowser {
	private static Logger log = TestLogger.getLogger(SetUpProAndBrowser.class);
	private static Properties pro = null ;
	private static File proFile = null;
	private static  WebDriver driver = null;
	
	public static WebDriver getDriver(){
		initDriverPro();
		EventFiringWebDriver eDriver = new EventFiringWebDriver(driver);
		MyWebDriverEventListener listener = new MyWebDriverEventListener();
		driver = eDriver.register(listener);
		return driver;
	}
	
	public static void tearDownBroswer(){
		if(driver == null){
			return;
		}else {
			log.info("浏览器即将关闭");
			driver.quit();
		}
	}
	
	private static void initDriverPro(){
		loadPro();
		if(driver!=null){
			log.error("driver is not null");
			return;
		}
		DesiredCapabilities cap = new DesiredCapabilities();
		//遇到弹窗的时候自动确认
		cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
		if("chrome".equals(pro.getProperty("browser.used"))){
			System.setProperty("webdriver.chrome.driver", pro.getProperty("webdriver.chrome.driver"));
			driver = new ChromeDriver(cap);
			log.info("ChromeDriver has been started!");
		}else if("ie".equals(pro.getProperty("browser.used"))){
			System.setProperty("webdriver.ie.driver", pro.getProperty("webdriver.ie.driver"));
			//解决IE的安全默认问题
			cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			driver = new InternetExplorerDriver(cap);
			log.info("IEDriver has been started!");
		}else {
			System.setProperty("webdriver.chrome.driver", pro.getProperty("webdriver.chrome.driver"));
			driver = new ChromeDriver(cap);
			log.info("Default driver(chrome) has been started!");
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	private static void loadPro(){
		proFile = new File("./conf/system.properties");
		if(proFile.exists()){
			pro = new Properties();
			try {
				pro.load(new FileInputStream(proFile));
				log.info("property file "+proFile.getAbsolutePath()+" is loaded!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error(e.getMessage());
			}
			
		}else {
			log.error("File system.properties is not exist!!!");
			return;
		}
		
	}
	

}

package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.seleniumhq.jetty7.util.log.Log;

import base.SetUpProAndBrowser;
import base.TestLogger;

public class LoginPage {
	public WebDriver driver = null;
	public AmrPage amrPage = null;
	private static Logger log = TestLogger.getLogger(LoginPage.class);
	@FindBy(id = "loginName")
	public WebElement loginUser ; 
	
	@FindBy(id = "savePath")
	public WebElement userPass;
	
	@FindBy(className = "btn")
	public WebElement loginButton;
	
	public AmrPage loginAmr(String userName , String passWord ,WebDriver driver){
		this.driver = driver;
		loginUser.sendKeys(userName);
		userPass.sendKeys(passWord);
		loginButton.click();
		try{
			if(loginUser.isDisplayed()){
				log.info("登录失败,窗口提示信息是："+driver.findElement(By.id("spanIpId")).getText());
//				SetUpProAndBrowser.tearDownBroswer();
				return null;
			}
		}catch (NoSuchElementException e){
			log.info("登录成功！");
		}
		return new AmrPage(driver);
	}
	public  LoginPage(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
}

package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HideMenu {
	WebDriver driver;
	
	public HideMenu(WebDriver driver){
		this.driver = driver;
	}
	
	@FindBy(xpath = "//ul[id='mainNaveMenu']/li[1]/ul")
	public static  WebElement problemCenter;
	
	@FindBy(xpath = "//ul[id='mainNaveMenu']/li[2]/ul")
	public static  WebElement taskManager ;
	
	@FindBy(xpath = "//ul[id='mainNaveMenu']/li[3]/ul")
	public static  WebElement assetManager;
	
	@FindBy(xpath = "//ul[id='mainNaveMenu']/li[4]/ul")
	public static  WebElement collectManager;
	
	@FindBy(xpath = "//ul[id='mainNaveMenu']/li[5]/ul")
	public static  WebElement systemManager;
	
}

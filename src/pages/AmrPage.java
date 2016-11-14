package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import util.JsTool;

import base.TestLogger;

public class AmrPage {
	private static Logger log = TestLogger.getLogger(AmrPage.class);
	public WebDriver driver = null;
	private static String baseJs = "var par = document.getElementById(\"mainNaveMenu\");"+
									"var target = par.getElementsByTagName(\"ul\");";
	
	public HideMenu hideMenu;
	
	@FindBy(linkText = "首页")
	public WebElement homePage;
	
	
	@FindBy(id = "amr_unasset_view")
	public WebElement unknowAsset;
	
	@FindBy(id = "amr_problem_asset")
	public WebElement proBlemAsset;
	
	@FindBy(id = "amr_task_view")
	public WebElement taskManagerView;
	
	@FindBy(id = "amr_asset_view")
	public WebElement assetManagerView;
	
	@FindBy(xpath = "//ul[@id='mainNaveMenu']/li[2]/ul")
	public static  WebElement problemCenter;
	
	@FindBy(xpath = "//ul[@id='mainNaveMenu']/li[3]/ul")
	public static  WebElement taskManager ;
	
	@FindBy(xpath = "//ul[@id='mainNaveMenu']/li[4]/ul")
	public static  WebElement assetManager;
	
	@FindBy(xpath = "//ul[@id='mainNaveMenu']/li[5]/ul")
	public static  WebElement collectManager;
	
	@FindBy(xpath = "//ul[@id='mainNaveMenu']/li[6]/ul")
	public static  WebElement systemManager;
	
	public AmrPage(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	private void goToMenuByName(String menuName){
		try {
			waitElement();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		((JavascriptExecutor)driver).executeScript(baseJs+"target[0].setAttribute(\"style\",\"display: block\");");
		
	}
	
	private void waitElement() throws Exception{
		WebDriverWait wait = new  WebDriverWait(driver,10,1000);
		try {
			wait.until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d){
					try {
						return d.findElement(By.id("amr_home_view")).isDisplayed();
					} catch (Exception e) {
						// TODO: handle exception
						return false;
					}
				}
			});
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error("上方菜单没有展示！！！",e);
			throw new Exception("上方菜单没有展示！！！");
		}
	}
	
	public void goToUnknowAsset(){
		try {
			waitElement();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JsTool.displayMenu(driver, problemCenter);
		unknowAsset.click();
		
	}
	
	public void goToTaskManager(){
		try {
			waitElement();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JsTool.displayMenu(driver, taskManager);
		taskManagerView.click();
	}
	
	public void goToAssetManager(){
		try {
			waitElement();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JsTool.displayMenu(driver, assetManager);
		assetManagerView.click();
	}

}

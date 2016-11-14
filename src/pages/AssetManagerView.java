package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AssetManagerView {
	private WebDriver driver ;
	
	@FindBy(xpath="//tbody/tr[1]/td[10]/a[2]")
	public WebElement delBtn;
	
	@FindBy(xpath="//div[@type='dialog']")
	public WebElement Dialog;
	
	public AssetManagerView(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
}

package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class UnknowAsset {
	public WebDriver driver;
	
	@FindBy(id = "res-queryBtn")
	public WebElement queryBtn;
	
	@FindBy(id = "res-restBtn")
	public WebElement resetBtn;
	
	@FindBy(id = "ipName")
	public WebElement ipNameInput;
	
	@FindBy(id = "geo")
	public WebElement location;
	
	@FindBy(id = "findType")
	public WebElement insertStatusSelect;
	
	@FindBy(id = "dateType")
	public WebElement lastFindTimeSelect;
	
	public UnknowAsset(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void changeInsertStatus(String value){
		Select select = new Select(insertStatusSelect);
		select.selectByIndex(1);
	}
	
}

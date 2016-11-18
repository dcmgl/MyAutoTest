package util;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JsTool {

	public  static void displayMenu(WebDriver driver, WebElement element) {
		((JavascriptExecutor) driver)
				.executeScript(
						"element = arguments[0];"
								+ "original_style = element.getAttribute('style');"
//								+ "element.setAttribute('style','display: block;');"
								+ "element.style.display='block';"
								+ "setTimeout(function(){element.style.display='none';}, 800);",
						element);
	}
	/**
	 * �������ز˵�
	 * @param driver
	 * @param element
	 */
	public  static void hideMenu(WebDriver driver, WebElement element) {
		((JavascriptExecutor) driver)
				.executeScript(
						"element = arguments[0];"
								+ "element.style.display='none';",
						element);
	}
	
	/**
	 * 
	 * @param driver	ʵ����driver����
	 * @param element	Ҫ���и���չʾ��Ԫ��
	 * @param timeOut	����չʾʱ�䣨��λ�����룩
	 */
	public  static void highLighElement(WebDriver driver, WebElement element ,long timeOut) {
		((JavascriptExecutor) driver)
				.executeScript(
						"element = arguments[0];"
								+ "original_style = element.getAttribute('style');"
								+ "element.setAttribute('style', original_style + \";"
								+ "border: 2px solid red;\");"
								+ "setTimeout(function(){element.setAttribute('style', original_style);}, "+timeOut+");",
						element);
	}
	
	/**
	 * �˷�������չʾelementԪ�أ�Ĭ��չʾʱ��1��
	 * @param driver	ʵ����driver����
	 * @param element	Ҫ���и���չʾ��Ԫ��
	 */
	public  static void highLighElement(WebDriver driver, WebElement element ) {
		((JavascriptExecutor) driver)
				.executeScript(
						"element = arguments[0];"
								+ "original_style = element.getAttribute('style');"
								+ "element.setAttribute('style', original_style + \";"
								+ "border: 2px solid red;\");"
								+ "setTimeout(function(){element.setAttribute('style', original_style);}, 1000);",
						element);
	}
	
	public static String getInputValue(WebDriver driver, WebElement element){
		if("input".equals(element.getTagName())){
			JavascriptExecutor je = (JavascriptExecutor)driver;
			String js = "return arguments[0].value;";
			return ((String)je.executeScript(js, element));
		}else{
			return "";
		}
	}

}

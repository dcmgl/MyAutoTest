package util;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JsTool {

	public static void displayMenu(WebDriver driver, WebElement element) {
		((JavascriptExecutor) driver)
				.executeScript(
						"element = arguments[0];"
								+ "original_style = element.getAttribute('style');"
								+ "element.setAttribute('style','display: block;');"
								+ "setTimeout(function(){element.setAttribute('style', original_style);}, 1200);",
						element);
	}
	
	/**
	 * 
	 * @param driver	实例的driver对象
	 * @param element	要进行高亮展示的元素
	 * @param timeOut	高亮展示时间（单位：毫秒）
	 */
	public static void highLighElement(WebDriver driver, WebElement element ,long timeOut) {
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
	 * 此方法高亮展示element元素，默认展示时间1秒
	 * @param driver	实例的driver对象
	 * @param element	要进行高亮展示的元素
	 */
	public static void highLighElement(WebDriver driver, WebElement element ) {
		((JavascriptExecutor) driver)
				.executeScript(
						"element = arguments[0];"
								+ "original_style = element.getAttribute('style');"
								+ "element.setAttribute('style', original_style + \";"
								+ "border: 2px solid red;\");"
								+ "setTimeout(function(){element.setAttribute('style', original_style);}, 1000);",
						element);
	}

}

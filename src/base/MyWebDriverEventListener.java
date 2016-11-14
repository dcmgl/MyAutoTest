package base;


import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

import testcase.TestCase;
import util.JsTool;

public class MyWebDriverEventListener extends AbstractWebDriverEventListener {
	Logger log = TestLogger.getLogger(getClass());
	Exception e = null;
	TestCase testCase = null;
	
	@Override
	public void beforeChangeValueOf(WebElement arg0, WebDriver arg1) {
		// TODO Auto-generated method stub
		arg0.clear();
		log.info("beforeChangeValueOf is worked!");
		JsTool.highLighElement(arg1, arg0,500);
	}
	
	@Override
	public void afterChangeValueOf(WebElement element,WebDriver driver){
		try {
			Thread.sleep(700);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void beforeClickOn(WebElement arg0, WebDriver arg1) {
		// TODO Auto-generated method stub
		if(arg0.getAttribute("value")!=null){
			log.info("即将点击按钮："+arg0.getAttribute("value"));
		}else if("button".equals(arg0.getTagName()) || arg0.getText()!=null){
			log.info("即将点击按钮："+arg0.getText());
		}else{
			log.info("没有获取到点击的元素"+arg0.getTagName()+"的名称");
		}
		JsTool.highLighElement(arg1, arg0,5000);
	}
	
	@Override
	public void afterClickOn(WebElement element,WebDriver driver){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("afterClickOn is worked!");
	}
	
	
	@Override
	public void afterNavigateTo(String url,WebDriver driver){
		log.info("Navigate to the url is :"+url);
		
	}

	@Override
	public void onException(java.lang.Throwable throwable, WebDriver driver){
		StackTraceElement[] stackTraceElement= throwable.getStackTrace();
		for(StackTraceElement st : stackTraceElement){
			if(st.getClassName().contains("testcase.")){
				log.error("获取到的错误方法是："+st.getClassName()+"."+st.getMethodName());
			}
		}
		e = (Exception)throwable;
		log.error(e.getMessage());
		
	}
	
	
}

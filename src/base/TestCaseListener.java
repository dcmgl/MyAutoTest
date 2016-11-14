package base;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.annotations.ITestAnnotation;

import testcase.TestCase;
import util.JsTool;
import util.ScreenShot;
/**
 * 此类继承了TestListenerAdapter抽象类并且实现了WebDriverEventListener,IRetryAnalyzer,IAnnotationTransformer接口
 * 可以监听testng事件和webdriver事件,实现失败重试
 * @author Administrator
 *
 */
public class TestCaseListener extends TestListenerAdapter implements WebDriverEventListener,IRetryAnalyzer,IAnnotationTransformer{
	Logger log = TestLogger.getLogger(getClass());
	private static Properties pro = TestProperties.getProperties();
	private TestCase testCase = null;
	Exception e = null;
	private int retryCount = 0;
	private int maxRetryCount = Integer.parseInt(pro.getProperty("listener.retry.maxCount"));

	@Override
	public void beforeChangeValueOf(WebElement arg0, WebDriver arg1) {
		// TODO Auto-generated method stub
		arg0.clear();
		this.
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
				log.error("获取到的错误方法是："+st.getClassName()+"."+st.getMethodName()+",行数是："+st.getLineNumber());
			}
		}
		e = (Exception)throwable;
		log.error(e.getMessage());
		
	}
	
	
	@Override
	public void onTestFailure(ITestResult tr){
		if(tr.getThrowable().getMessage().contains("alert")){
            TestCase tb = (TestCase) tr.getInstance();
            WebDriver driver = tb.driver; 
            Alert alert = null;
            boolean flag = false;
            try {
                
                new WebDriverWait(driver,2).until(ExpectedConditions.alertIsPresent());
                alert = driver.switchTo().alert();
                flag = true;
                //alert.accept();
            } catch (NoAlertPresentException NofindAlert) {
                // TODO: handle exception
                NofindAlert.printStackTrace();
                //throw NofindAlert;
            }
            
            if(flag) {
                alert.accept();
                tr.setStatus(ITestResult.SUCCESS);
    			return;
            }
			
		}
		super.onTestFailure(tr);
		log.error(tr.getThrowable().getMessage());
		String outputImg = pro.getProperty("listener.output.errorImg");
		if(outputImg==null){
			outputImg = "./test-output/errorImage/";
		}
		testCase = (TestCase) tr.getInstance();
		ScreenShot ss = null;
		try {
			ss = new ScreenShot(testCase.driver, outputImg);
			ss.ImgShot("用例"+tr.getInstanceName()+"的错误截图",tr.getThrowable());
			log.info("用例"+testCase.getClass().getName()+"执行失败，截图路径："+ss.getFileFullPath());
		} catch (Exception e) {
			// TODO: handle exception
			log.error("用例"+testCase.getClass().getName()+"出错，截图失败",e);
		
		}
	}
	
	
	@Override
	public void onTestSuccess(ITestResult tr){
		log.info("用例"+tr.getMethod()+"执行成功了");
		log.info(this.getPassedTests());
	}

	@Override
	public void afterFindBy(By arg0, WebElement arg1, WebDriver arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterNavigateBack(WebDriver arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterNavigateForward(WebDriver arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterScript(String arg0, WebDriver arg1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void beforeFindBy(By arg0, WebElement arg1, WebDriver arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeNavigateBack(WebDriver arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeNavigateForward(WebDriver arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeNavigateTo(String arg0, WebDriver arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeScript(String arg0, WebDriver arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transform(ITestAnnotation annotation, Class testCase, Constructor constructor,
			Method testMethod) {
		// TODO Auto-generated method stub
		IRetryAnalyzer retry = annotation.getRetryAnalyzer();
		if(retry == null){
			annotation.setRetryAnalyzer(this.getClass());
		}
	}

	@Override
	public boolean retry(ITestResult arg0) {
		// TODO Auto-generated method stub
		if(retryCount<maxRetryCount){
			log.info(arg0.getName()+" will be retried!!!Current retry number is "+(retryCount+1));
			Reporter.setCurrentTestResult(arg0);
			retryCount ++;
			return true;
		}
		return false;
	}

}

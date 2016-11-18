package base;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.IAnnotationTransformer;
import org.testng.IResultMap;
import org.testng.IRetryAnalyzer;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.annotations.ITestAnnotation;

import testcase.TestCase;
import util.JsTool;
import util.ScreenShot;
import util.SendMail;
/**
 * ����̳���TestListenerAdapter�����ಢ��ʵ����WebDriverEventListener,IRetryAnalyzer,IAnnotationTransformer�ӿ�
 * ���Լ���testng�¼���webdriver�¼�,ʵ��ʧ������
 * @author Administrator
 *
 */
public class TestCaseListener extends TestListenerAdapter implements WebDriverEventListener,IRetryAnalyzer,IAnnotationTransformer{
	Logger log = TestLogger.getLogger(getClass());
	private static Properties pro = TestProperties.getProperties();
	private TestCase testCase = null;
	Exception e = null;
	private int retryCount = 0;
	private int maxRetryCount = Integer.parseInt(pro.getProperty("listener.retry.maxCount"));//��ȡ������Դ���

	@Override
	public void beforeChangeValueOf(WebElement arg0, WebDriver arg1) {
		// TODO Auto-generated method stub
		arg0.clear();
//		log.info("beforeChangeValueOf is worked!");
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
		log.info("�������Ϣ�ǣ�"+JsTool.getInputValue(driver, element));
		
	}

	@Override
	public void beforeClickOn(WebElement arg0, WebDriver arg1) {
		// TODO Auto-generated method stub
		if(arg0.getAttribute("value")!=null){
			log.info("���������ť��"+arg0.getAttribute("value"));
		}else if("button".equals(arg0.getTagName()) || arg0.getText()!=null){
			log.info("���������ť��"+arg0.getText());
		}else{
			log.info("û�л�ȡ�������Ԫ��"+arg0.getTagName()+"������");
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
				log.error("��ȡ���Ĵ��󷽷��ǣ�"+st.getClassName()+"."+st.getMethodName()+",�����ǣ�"+st.getLineNumber());
			}
		}
		e = (Exception)throwable;
		log.error(e.getMessage(),e);
		
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
			ss.ImgShot("����"+tr.getInstanceName()+"."+tr.getMethod().getMethodName()+"�Ĵ����ͼ",tr.getThrowable());
			log.info("����"+tr.getInstanceName()+"."+tr.getMethod().getMethodName()+"ִ��ʧ�ܣ���ͼ·����"+ss.getFileFullPath());
		} catch (Exception e) {
			// TODO: handle exception
			log.error("����"+testCase.getClass().getName()+"������ͼʧ��",e);
		
		}

	}
	
	
	@Override
	public void onTestSuccess(ITestResult tr){
		log.info("����"+tr.getMethod().toString().split("\\[")[0]+"ִ�гɹ���");
	}
	
	@Override
	public void onFinish(org.testng.ITestContext testContext) {
		super.onFinish(testContext);
		IResultMap passResult = testContext.getPassedTests();
		IResultMap failedResult = testContext.getFailedTests();
		IResultMap skipedResult = testContext.getSkippedTests();
		for(ITestNGMethod testMethod : passResult.getAllMethods()){
			log.info(testMethod.getRealClass().toString().split(" ")[1]+"."+testMethod.getMethodName()+"   PASSED!");
		}
		for(ITestNGMethod testMethod : failedResult.getAllMethods()){
			log.info(testMethod.getRealClass().toString().split(" ")[1]+"."+testMethod.getMethodName()+"   FAILED!");
		}
		for(ITestNGMethod testMethod : skipedResult.getAllMethods()){
			log.info(testMethod.getRealClass().toString().split(" ")[1]+"."+testMethod.getMethodName()+"   SKIPED!");
		}
		try {
			SendMail mail = new SendMail("c:\\system.properties");
			mail.sendMail();
			log.info("�ʼ����ͳɹ�");
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}

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
		arg1.switchTo().window(arg1.getWindowHandle());
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

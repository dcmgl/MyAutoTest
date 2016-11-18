package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import testcase.TestCase;
import util.ScreenShot;

public class MyTestngListener extends TestListenerAdapter {
	private static Logger log = TestLogger.getLogger(MyTestngListener.class);
	private static Properties pro =  new Properties();
	private TestCase testCase = null;
	@Override
	public void onTestFailure(ITestResult tr){
		super.onTestFailure(tr);
		log.error(tr.getThrowable().getMessage());
		try {
			pro.load(new FileInputStream(new File("./conf/system.properties")));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String outputImg = pro.getProperty("output.img.error");
		if(outputImg==null){
			outputImg = "./test-output/errorImage/";
		}
		testCase = (TestCase) tr.getInstance();
		ScreenShot ss = null;
		try {
			ss = new ScreenShot(testCase.driver, outputImg);
			ss.ImgShot("ÓÃÀý"+tr.getInstanceName()+"."+tr.getMethod().getMethodName()+"µÄ´íÎó½ØÍ¼",tr.getThrowable());
			log.info("ÓÃÀý"+testCase.getClass().getName()+"Ö´ÐÐÊ§°Ü£¬½ØÍ¼Â·¾¶£º"+ss.getFileFullPath());
		} catch (Exception e) {
			// TODO: handle exception
			log.error("ÓÃÀý"+testCase.getClass().getName()+"³ö´í£¬½ØÍ¼Ê§°Ü",e);
		
		}
	}

}

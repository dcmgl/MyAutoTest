package base;

import org.apache.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

public class MyTestngRetry implements IRetryAnalyzer {
	private static Logger log = TestLogger.getLogger(MyTestngRetry.class);
	private int retryCount = 1;
	private int maxRetryCount = 1;
	
	@Override
	public boolean retry(ITestResult arg0) {
		// TODO Auto-generated method stub
		if(retryCount<=maxRetryCount){
			log.info(arg0.getName()+" will be retried!!!");
			Reporter.setCurrentTestResult(arg0);
			retryCount ++;
			return true;
		}
		return false;
	}
	
}

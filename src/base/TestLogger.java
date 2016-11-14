package base;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * �����Ǽ̳���org.apache.log4j.Logger�࣬��ȡ�����ļ�Ϊ./conf/log4j.properties
 * @author Administrator
 *
 */
public class TestLogger extends Logger{

	protected TestLogger(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public static Logger getLogger(Class<?> clazz) {
		// TODO Auto-generated method stub
		PropertyConfigurator.configure("./conf/log4j.properties");
		return  Logger.getLogger(clazz);
	}
	
	@Override
	public void info(Object message) {
		// TODO Auto-generated method stub
		super.info(message);
	}
	
	@Override
	public void info(Object message ,Throwable t) {
		// TODO Auto-generated method stub
		super.info(message,t);
	}
	
	@Override
	public void debug(Object message) {
		// TODO Auto-generated method stub
		super.debug(message);
	}
	
	@Override
	public void debug(Object message ,Throwable t) {
		// TODO Auto-generated method stub
		super.debug(message,t);
	}
	
	
	@Override
	public void error(Object message) {
		// TODO Auto-generated method stub
		super.error(message);
	}
	
	@Override
	public void error(Object message ,Throwable t) {
		// TODO Auto-generated method stub
		super.error(message,t);
	}
	

}

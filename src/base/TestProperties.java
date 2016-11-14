package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class TestProperties {
	private static Properties pro = new Properties();
	static Logger log = TestLogger.getLogger(TestProperties.class);
	
	public static Properties getProperties(){
		File proFile = new File("./conf/system.properties");
		if(proFile.exists()){
			pro = new Properties();
			try {
				pro.load(new FileInputStream(proFile));
				log.info("property file "+proFile.getAbsolutePath()+" is loaded!!");
				return pro;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error(e.getMessage());
				return null;
			}
			
		}else {
			log.error("File system.properties is not exist!!!");
			return null;
		}
		
	}
	
}

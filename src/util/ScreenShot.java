package util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.TakesScreenshot;

public class ScreenShot {
	private WebDriver driver = null;
	private String fileSavePath = null;
	private String fileFullPath = null;
	
	public String getFileFullPath(){
		return fileFullPath;
	}
	
	public ScreenShot(WebDriver driver , String fileSavePath){
		this.driver = driver;
		this.fileSavePath = fileSavePath;
	}
	
	/**
	 * 简单的截图工具，没有出错元素高亮和说明信息
	 */
	public void simpleShot(){
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		if(fileSavePath.endsWith(".png") || fileSavePath.endsWith(".jpg")){
			try {
				FileUtils.copyFile(srcFile, new File(fileSavePath));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			if(!fileSavePath.endsWith(File.separator)){
				SimpleDateFormat sf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
				String fileName = sf.format(new Date())+".png";
				try {
					FileUtils.copyFile(srcFile, new File(fileSavePath+File.separator+fileName));
					fileFullPath = fileSavePath+File.separator+fileName;
				} catch (Exception e) {
					// TODO: handle exception
				}
			}else{
				SimpleDateFormat sf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
				String fileName = sf.format(new Date())+".png";
				try {
					FileUtils.copyFile(srcFile, new File(fileSavePath+fileName));
					fileFullPath = fileSavePath+fileName;
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
	
		}
			
	}
	
	/**
	 * 
	 * @param text	需要写入图片的文本
	 */
	public void ImgShot(String text ,Throwable t){
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		Image image = null;
		if(fileSavePath.endsWith(".png") || fileSavePath.endsWith(".jpg")){
			fileFullPath = fileSavePath;
		}else{
			if(!fileSavePath.endsWith(File.separator)){
				SimpleDateFormat sf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
				String fileName = sf.format(new Date())+".jpg";
				try {
					fileFullPath = fileSavePath+File.separator+fileName;
				} catch (Exception e) {
					// TODO: handle exception
				}
			}else{
				SimpleDateFormat sf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
				String fileName = sf.format(new Date())+".jpg";
				try {
					fileFullPath = fileSavePath+fileName;
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		try {
			image = ImageIO.read(srcFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int width = image.getWidth(null);
		int heigth = image.getHeight(null);
		BufferedImage bi = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bi.createGraphics();
		g.drawImage(image,0, 0, width, heigth, null);
		g.setBackground(Color.red);
		Font font = new Font("宋体",Font.PLAIN,20);
		g.setFont(font);
		g.setColor(Color.red);
		FontMetrics fm = new FontMetrics(font){};
		Rectangle2D rectangel = fm.getStringBounds(text, null);
		double textHeigth = rectangel.getHeight();
		double textWidth = rectangel.getWidth();
		int textX;
		int textY;
		textX =(int) (width - textWidth - 5);
		textY = (int)(heigth - 2*textHeigth - 3);
		if(textX<0 || textY<0){
			System.out.println("文字过长，截图文字展示不完整");
		}
		g.drawString(text, textX, textY);
		Rectangle2D rectangelT = fm.getStringBounds(t.getMessage(), null);
		textHeigth = rectangelT.getHeight();
		textWidth = rectangelT.getWidth();
		textX =(int) (width - textWidth - 5);
		textY = (int)(heigth - textHeigth - 3);
		g.drawString(t.getMessage(), textX, textY);
		FileOutputStream out;
		try {
			out = new FileOutputStream(fileFullPath);
			ImageIO.write(bi, "JPEG", out);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void ImgShot(String text){
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		Image image = null;
		if(fileSavePath.endsWith(".png") || fileSavePath.endsWith(".jpg")){
			fileFullPath = fileSavePath;
		}else{
			if(!fileSavePath.endsWith(File.separator)){
				SimpleDateFormat sf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
				String fileName = sf.format(new Date())+".jpg";
				try {
					fileFullPath = fileSavePath+File.separator+fileName;
				} catch (Exception e) {
					// TODO: handle exception
				}
			}else{
				SimpleDateFormat sf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
				String fileName = sf.format(new Date())+".jpg";
				try {
					fileFullPath = fileSavePath+fileName;
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		try {
			image = ImageIO.read(srcFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int width = image.getWidth(null);
		int heigth = image.getHeight(null);
		BufferedImage bi = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bi.createGraphics();
		g.drawImage(image,0, 0, width, heigth, null);
		g.setBackground(Color.red);
		Font font = new Font("宋体",Font.PLAIN,20);
		g.setFont(font);
		g.setColor(Color.red);
		FontMetrics fm = new FontMetrics(font){};
		Rectangle2D rectangel = fm.getStringBounds(text, null);
		double textHeigth = rectangel.getHeight();
		double textWidth = rectangel.getWidth();
		int textX;
		int textY;
		textX =(int) (width - textWidth - 5);
		textY = (int)(heigth - textHeigth - 3);
		if(textX<0 || textY<0){
			System.out.println("文字过长，截图文字展示不完整");
		}
		g.drawString(text, textX, textY);
		FileOutputStream out;
		try {
			out = new FileOutputStream(fileFullPath);
			ImageIO.write(bi, "JPEG", out);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	
	
}

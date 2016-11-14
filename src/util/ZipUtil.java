package util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private static void zip(String srcRootDir, File file, ZipOutputStream zos)
			throws Exception {
		if (file == null) {
			return;
		}
		// 如果是文件就直接压缩
		if (file.isFile()) {
			int count, bufferLen = 1024;
			byte[] data = new byte[bufferLen];

			String subPath = file.getAbsolutePath();
			int index = subPath.indexOf(srcRootDir);
			if (index != -1) {
				subPath = subPath.substring(srcRootDir.length()
						+ File.separator.length());
			}
			ZipEntry entry = new ZipEntry(subPath);
			zos.putNextEntry(entry);
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(file));
			while ((count = bis.read(data, 0, bufferLen)) != -1) {
				zos.write(data, 0, count);
			}
			bis.close();
			zos.close();
		}else{
			File[]childFileList = file.listFiles();
			for(int n=0;n<childFileList.length;n++){
				zip(srcRootDir,childFileList[n],zos);
			}
		}
	}

}

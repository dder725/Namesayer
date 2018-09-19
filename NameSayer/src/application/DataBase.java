package application;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;

public class DataBase {
	
	public static void unzip(String filePath, String destDir) {
		File dir = new File(destDir);
		if(!dir.exists()) dir.mkdirs(); // Create output directory if it does not exist
		FileInputStream fis = null;
		ZipInputStream zipIs = null;
		ZipEntry zEntry = null;
		byte[] buffer = new byte[1024];
		Map<String, Integer> names = new HashMap<String, Integer>();
		String entryName;
		
		try {
			fis = new FileInputStream(filePath);
			zipIs = new ZipInputStream(new BufferedInputStream(fis));
			zEntry = zipIs.getNextEntry();
			while(zEntry != null) {
				int index = zEntry.getName().lastIndexOf("_");
				entryName = zEntry.getName().substring(index+1, zEntry.getName().lastIndexOf(".")).toUpperCase();
				
				if(names.containsKey(entryName)) {
					names.put(entryName, names.get(entryName) + 1); //This name has existing versions
				} else {
					names.put(entryName, 1);
				}
				File newFile = new File(destDir + File.separator + entryName + "_" + names.get(entryName).toString()); //Create a file with an appropriate name [Name]_[Version]
				System.out.println("Unzipping to " + newFile.getAbsolutePath());
				FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				while((len = zipIs.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
				zEntry = zipIs.getNextEntry();
				//Create a new Name object
			}
			zipIs.closeEntry();
			zipIs.close();
			fis.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	private void open() {
		
	}
}

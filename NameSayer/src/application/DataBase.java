package application;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DataBase {
	private static volatile DataBase instance = null;
	
	private DataBase() {
		unzip(System.getProperty("user.dir") + "/names.zip", System.getProperty("user.dir") + "/names");
	}
	public static void instantiateDataBase() { //Implement Database as a singleton (can't create use more than two databases at once)
        if (instance == null) {
            synchronized(DataBase.class) {
                if (instance == null) {
                    instance = new DataBase();
                }
            }
        }
    }
	
	private static void unzip(String filePath, String destDir) {
		HashMap<String, Name> names = new HashMap<String, Name>();
		File dir = new File(destDir);
		if(!dir.exists()) dir.mkdirs(); // Create output directory if it does not exist
		FileInputStream fis = null;
		ZipInputStream zipIs = null;
		ZipEntry zEntry = null;
		byte[] buffer = new byte[1024];
		Map<String, Integer> occurencesOfName = new HashMap<String, Integer>();
		String entryName;
		
		try {
			fis = new FileInputStream(filePath);
			zipIs = new ZipInputStream(new BufferedInputStream(fis));
			zEntry = zipIs.getNextEntry();
			while(zEntry != null) {
				int index = zEntry.getName().lastIndexOf("_");
				entryName = zEntry.getName().substring(index+1, zEntry.getName().lastIndexOf(".")).toUpperCase();
				
				if(occurencesOfName.containsKey(entryName)) {
					occurencesOfName.put(entryName, occurencesOfName.get(entryName) + 1); //This name has existing versions
				} else {
					occurencesOfName.put(entryName, 1);
					names.put(entryName, new Name(entryName));
				}
				File newFile = new File(destDir + File.separator + entryName + "_" + occurencesOfName.get(entryName).toString()); //Create a file with an appropriate name [Name]_[Version]
				names.get(entryName).addRecordingFile(newFile.getAbsolutePath()); //add a new recording to the name
				
				System.out.println("Unzipping to " + newFile.getAbsolutePath());
				System.out.println(names.get(entryName).getName());
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

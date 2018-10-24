package application;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DataBase {
	private static volatile DataBase instance = null;
	private static HashMap<String, Name> _names = new HashMap<String, Name>();
	private static File _badRecordings;

	private DataBase() {
		File file = new File(System.getProperty("user.dir") + "/names");
		if (!file.exists()) {
			unzip(System.getProperty("user.dir") + "/names.zip", System.getProperty("user.dir") + "/names");
		}
		traverse(file.getAbsolutePath());

	}

	//Instantiate database on start
	public static void instantiateDataBase() {
		instance = new DataBase();
	}

	// Reinstantiate the database with a new file
	public static void reinstantiateDataBase(File dataBaseFile) {
		File file = new File(System.getProperty("user.dir") + dataBaseFile.getName());
		if (!file.exists()) {
			unzip(dataBaseFile.getAbsolutePath(), System.getProperty("user.dir") + dataBaseFile.getName());
		}
		traverse(file.getAbsolutePath());
	}

	private static void unzip(String filePath, String destDir) {
		File dir = new File(destDir);
		if (!dir.exists())
			dir.mkdirs(); // Create output directory if it does not exist
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
			while (zEntry != null) {
				int index = zEntry.getName().lastIndexOf("_");
				entryName = zEntry.getName().substring(index + 1, zEntry.getName().lastIndexOf(".")).toUpperCase();

				if (occurencesOfName.containsKey(entryName)) {
					occurencesOfName.put(entryName, occurencesOfName.get(entryName) + 1); // This name has existing
																							// versions
				} else {
					occurencesOfName.put(entryName, 1);
					_names.put(entryName, new Name(entryName));
				}
				// Create a file with appropriate [Name]_[Version]
				File newFile = new File(
						destDir + File.separator + entryName + "_" + occurencesOfName.get(entryName).toString());
				_names.get(entryName).addRecordingFile(newFile.getAbsolutePath()); // add a new recording to the name

				FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				while ((len = zipIs.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
				zEntry = zipIs.getNextEntry();
			}
			zipIs.closeEntry();
			zipIs.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void traverse(String destDir) {
		// Clear the previous list of names
		_names.clear();

		String entryName;
		// Count occurences of each name to number the versions
		Map<String, Integer> occurencesOfName = new HashMap<String, Integer>();
		File dir = new File(destDir);

		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				int index = child.getAbsolutePath().lastIndexOf('/');
				int lastIndex = child.getAbsolutePath().lastIndexOf('_');
				entryName = child.getAbsolutePath().substring(index + 1, lastIndex).toUpperCase();
				if (occurencesOfName.containsKey(entryName)) {
					occurencesOfName.put(entryName, occurencesOfName.get(entryName) + 1); // This name has existing
																							// versions
				} else {
					occurencesOfName.put(entryName, 1);
					_names.put(entryName, new Name(entryName));
				}
				_names.get(entryName).addRecordingFile(child.getAbsolutePath());
			}
		}
		;
	}

	// Get all Name objects in the database
	public static Collection<Name> getNamesList() {
		return _names.values();
	}

	// Find a name object based on a name
	public static Name findName(String name) {
		return _names.get(name);
	}

}

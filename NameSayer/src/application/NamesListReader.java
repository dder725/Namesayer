package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class NamesListReader {
	private File _fileToParse;
	private ArrayList<ArrayList<String>> _names;
	private Integer _index = 0;

	public NamesListReader(File file) {
		_fileToParse = file;
	}

	public ArrayList<ArrayList<String>> getListedNames() {
		FileReader input;
		try {
			input = new FileReader(_fileToParse);

		BufferedReader bufRead = new BufferedReader(input);
		String myLine = null;
		//Read the file line by line
			while ((myLine = bufRead.readLine()) != null) {
				//Each line is added is a new array of names
				_names.add(new ArrayList());
				String[] array1 = myLine.split(" ");
				//Add each word to the array of a name
				for (int i = 0; i < array1.length; i++) {
					_names.get(_index).add(array1[i]);
				}
				System.out.println(_names.get(0));
				_index++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return _names;
	}
}

package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.ObservableList;

public class NamesListReader {
	private File _fileToParse;
	private ArrayList<ArrayList<String>> _namesAsString;
	private ArrayList<ArrayList<Name>> _names;
	private Integer _index = 0;

	public NamesListReader(File file) {
		_fileToParse = file;
	}

	public ArrayList<ArrayList<Name>> getListedNames(ObservableList<Name> database) {
		FileReader input;
		try {
			input = new FileReader(_fileToParse);
			_namesAsString = new ArrayList<ArrayList<String>>();
			_names = new ArrayList<ArrayList<Name>>();

			BufferedReader bufRead = new BufferedReader(input);
			String myLine = null;
			// Read the file line by line
			while ((myLine = bufRead.readLine()) != null) {
				// Each line is added is a new array of names
				_namesAsString.add(new ArrayList());
				String[] array1 = myLine.split(" ");
				// Add each word to the array of a name
				for (int i = 0; i < array1.length; i++) {
					_namesAsString.get(_index).add(array1[i]);
				}
				_index++;
			}
			_index = 0;
			// Find matching names in the database
			for (ArrayList<String> name : _namesAsString) {
				_names.add(new ArrayList<Name>() {
					@Override
					// Display the name in a formatted way in the table by overriding toString()
					// method
					public String toString() {
						String nameString = new String();
						for (Name name : this) {
							nameString += name.getName() + " ";
						}
						;
						return nameString;
					}
				});
				// Add the names to the list only if they exist in the database
				for (String word : name) {
					for (Name dataName : database) {
						if (dataName.getName().equalsIgnoreCase(word)) {
							_names.get(_index).add(dataName);
						}
					}
				}
				_index++;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return _names;
	}
}

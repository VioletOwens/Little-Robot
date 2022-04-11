package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Violet Owens
 */
public class Brain {

	private static String userInput;
	protected static String phraseDirectory = "";
	protected static String dataDirectory = "";
	protected static String fxmlDirectory = "";
	protected static int tempInt = 0;
	protected static String[] tempArr = { "" };
	protected static String tempString = "";
	// used liberally, able to be used as a temporary string
	// static protected String[] CategoryManagerPanelLastActionInfo = {""};

	// these need to be removed through code revision

	// need to make a status manager
	// need to also make a command manager
	// fix | problem in interpretInput at top

	protected static String[] listOfStatuses = { "Any", "Normal", "WILD!" };

	// how are you doing how
	// the above line doesn't work in code fix it

	// need to write the stuff below
	protected static String response = "";

	// need to figure a way to store this information in another accessible java
	// file?
	// group of lists for brain to use below.
	protected static String[] UIUnderstanding = { "" };
	protected static String[] UnknownPartsOfUI = { "" };
	protected static String[] listOfPhrases = { "" };
	protected static String[] listOfCategories = { "" };
	private static String[] listOfCommands = { "" };
	protected static String[] listOfCommandPhrases = { "" };
	protected static String[][] listOfCommandGrouping = { { "" } };
	protected static String[] fullListOfFiles = { "" };
	protected static String[] listOfPhraseFileNameCombo = { "" };
	protected static String[] listOfCategoryFileNameCombo = { "" };
	protected static String[] filesToIncludeInSearches = { "" };
	protected static String[] filesToExcludeFromSearches = { "" };
	protected static String[][] listOfCommandLinesByCategory = { { "" } };
	protected static String[][] ListOfPhrasesCommandsAndTheirCategories = { { "" } };
	protected static String[][] listOfCategoriesAndTheirPhrases = { { "" } };
	protected static Map<String, Boolean> phraseFileValidity = new HashMap<>();
	protected static Map<String, String[]> categoryFileGrouping = new HashMap<>();
	protected static Map<String, String[]> categoryPhraseGrouping = new HashMap<>();
	protected static Map<String, String[]> indivComGrouping = new HashMap<>();
	protected static Map<String, Map<String, String[]>> totalCommandGrouping = new HashMap<>();// trying out this method
																								// of storing command
																								// information
	protected static Map<String, Map<String, String[]>> commandGrouping = new HashMap<>();
	// THIS WONT WORK FOR COMMANDS! NEED A NEW STRUCTURE!!

	public static void startUp(String input) throws IOException {

		setUI(input);
		buildDirectory();
		updatePhrases();
		updateData();

		interpretInput(userInput);

		// remove this for sure later
		if (UIUnderstanding != null && UIUnderstanding[0].equals("OpenWindow(ControlPanel.fxml)")) {
			URL url = new File("fxmls\\ControlPanel.fxml").toURI().toURL();
			Parent root = FXMLLoader.load(url);
			Stage stage = new Stage();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Control Panel");
			stage.setResizable(false);
			stage.show();
		}
	}

	private static void interpretInput(String UI) throws FileNotFoundException {
		/*
		 * should be versatile and allow for user input of any type of thing in file,
		 * examples may include wanting to break sentences down to adjective, noun, verb
		 * etc. compared to recognizing whole sentences/phrases
		 * 
		 * best way to accomplish this would be to include
		 * checkIfInfoFromFile(keywordFileName,part of UI), attempt word by word, back
		 * to front? every time a UI string is unknown it is added to unknown strings,
		 * vice versa with resultingUnderstanding
		 */
		String[] elementaryFunctions = { "+", "-", "*", "/", "(", ")", "π", "^", "ln", "log", "√", "|", "sqrt", "abs",
				"sin", "cos", "tan", "arcsin", "arccos", "arctan", // this line...
				"asin", "acos", "atan", // means the same as this line, just alt spelling.
				"sinh", "cosh", "tanh", "arsinh", "arcosh", "artanh",// this is inverse of line above.
		};
		UI = UI.toLowerCase().replace("'", "").trim();
		String[] UIByWord = UI.split(" ");
		String[] unknownStrings = new String[1];
		String[] resultingUnderstanding = new String[UIByWord.length];
		String[][] FullList = ListOfPhrasesCommandsAndTheirCategories;
		String tempString = "";
		int understandingCntr = 0;
		int boolCtr = 0;
		Boolean[] UIByWordBoolean = new Boolean[UIByWord.length];
		Boolean isFound = false;

		for (int x = 0, total = 1, addVar = 2; x < UIByWord.length - 1; x++) {
			total = total + addVar;
			addVar++;
			if (x + 1 == UIByWord.length - 1) {
				unknownStrings = new String[total];
			}
		}
		unknownStrings = new String[UIByWord.length];
		if (!Tools.doesStrContainArr(UI, elementaryFunctions)) {
			if (UI.contains(" ")) {
				/*
				 * Case of UI having more than one word. The process is explained below. first
				 * checking all words together, then slowly taking it apart from the end until
				 * only beginning word left (the left-most word) if not able to be found. if
				 * found though, remove that portion of the string, trim(), then loop in trying
				 * to understand string. if we really can't understand down to the word itself,
				 * add it to compendium of unknown words/gibberish, remove from string, then
				 * continue trying to understand the rest of the string. repeat until nothing
				 * left. if words are in a continuous row that we don't understand, append the
				 * new words to the end of the last one with a space, else move to next index in
				 * unknown words array
				 */

				for (int numberOfSkippedWords = 0; numberOfSkippedWords < UIByWord.length;) {
					// operates the unknown word index
					for (int backwardCounter = UIByWord.length; backwardCounter > numberOfSkippedWords; backwardCounter--) {
						// operates the backward counter index for the identification of UIByWord
						tempString = "";
						isFound = false;
						for (int tempStringBuilderIndex = numberOfSkippedWords; tempStringBuilderIndex < backwardCounter; tempStringBuilderIndex++) {
							// builds the new string to attempt to identify
							if (tempStringBuilderIndex == numberOfSkippedWords) {
								tempString = UIByWord[tempStringBuilderIndex];
							} else {
								tempString = tempString + " " + UIByWord[tempStringBuilderIndex];
							}
						} // by here stringToBuild is built
							// must be able to match a string already in system, else stripped of trailing
							// word and tried again until either something is found, or nothing left.
						for (int outerIndex = 0; outerIndex < FullList.length; outerIndex++) {
							for (int innerIndex = 1; innerIndex < FullList[outerIndex].length; innerIndex++) {
								// both for operates searching through listOfCategoriesAndTheirPhrases
								if (tempString.equalsIgnoreCase(FullList[outerIndex][innerIndex])) {
									// if a match is found, remove backwards counter number
									// of indexes from UIByWord from the front, and build the next
									// string by adding
									// backwards counter number to numberOfSkippedWords
									resultingUnderstanding[understandingCntr] = FullList[outerIndex][0];
									understandingCntr++;
									numberOfSkippedWords = backwardCounter;// UIByWord.length-
									sopln("the number of words we know is:" + numberOfSkippedWords);
									isFound = true;
									innerIndex = FullList[outerIndex].length - 1;
									outerIndex = FullList.length - 1;
								}
							}

							if (backwardCounter - 1 == numberOfSkippedWords) {
								numberOfSkippedWords++;
							}
							if (outerIndex + 1 == FullList.length && numberOfSkippedWords == backwardCounter
									&& isFound) {
								// this needs to be called multiple times for multi-word recognition
								// need new number of known
								/*
								 * //numberOfSkippedWords will be 1+ always //need the number of words that we
								 * previously don't know //Boolean[] tempArr =
								 * Tools.removeNullInArray(UIByWordBoolean); //int numberOfUnknown=0; //for(int
								 * x=0; x<tempArr.length;x++) { // if(!tempArr[x])numberOfUnknown++; //}
								 * //for(int x=0;x<numberOfSkippedWords-numberOfUnknown;x++) { //}
								 * 
								 */
								UIByWordBoolean[boolCtr] = true;
								boolCtr++;
							} else if (outerIndex + 1 == FullList.length && numberOfSkippedWords == backwardCounter) {

								if (boolCtr > 0 && UIByWordBoolean[boolCtr - 1] == true) {
									// finishes line of trues if need be
									for (int x = 0, y = boolCtr; x < numberOfSkippedWords - y - 1; x++) {
										UIByWordBoolean[boolCtr] = true;
										boolCtr++;
									}
								}

								UIByWordBoolean[boolCtr] = false;
								boolCtr++;
							}
							// how are you doing how borken
						} // backwards counter measures the number of words being considered
							// floor measures the number of unknown words
					}
					// listOfPhraseFileNameCombo
				}
			} else {// one word
				for (int outerIndex = 0; outerIndex < FullList.length; outerIndex++) {
					for (int innerIndex = 1; innerIndex < FullList[outerIndex].length; innerIndex++) {
						if (FullList[outerIndex][innerIndex].equalsIgnoreCase(UI)) {
							resultingUnderstanding[understandingCntr] = FullList[outerIndex][0];
							isFound = true;
						}
					}
					if (outerIndex + 1 == FullList.length && isFound) {
						UIByWordBoolean[boolCtr] = true;
						boolCtr++;
					} else if (outerIndex + 1 == FullList.length) {
						UIByWordBoolean[boolCtr] = false;
						boolCtr++;
					}
				}
			}
			if (UIByWordBoolean != null) {
				UIByWordBoolean = Tools.removeNullInArray(UIByWordBoolean);
				for (int x = 0, indexOfFirstFalse = 0; x < UIByWordBoolean.length; x++) {

					if (x >= 1 && !UIByWordBoolean[x - 1] && !UIByWordBoolean[x]) {
						// if both the last one and this one are false, append this to unknownStrings[x]
						unknownStrings[indexOfFirstFalse] = unknownStrings[indexOfFirstFalse].concat(" " + UIByWord[x]);
					} else if (x >= 1 && UIByWordBoolean[x - 1] && !UIByWordBoolean[x]) {
						// if changing into a false
						indexOfFirstFalse = x;
						unknownStrings[x] = UIByWord[x];
					} else if (!UIByWordBoolean[x]) {
						unknownStrings[x] = UIByWord[x];
					}
				}
			}
		} else if (Tools.doesStrContainArr(UI, elementaryFunctions)) {
			resultingUnderstanding[0] = Tools.solveExpression(UI);
		} else {
			sopln("We dont know how to deal with whatever that is yet :(");
		}

		if (resultingUnderstanding != null) {
			sopln("The understanding of UI is as follows:");
			resultingUnderstanding = Tools.removeNullInArray(resultingUnderstanding);
			for (int x = 0; x < resultingUnderstanding.length; x++) {
				sopln(resultingUnderstanding[x]);
			}
		}
		unknownStrings = Tools.removeNullInArray(unknownStrings);
		if (unknownStrings != null) {
			sopln("The unknown parts of UI is as follows:");
			for (int x = 0; x < unknownStrings.length; x++) {
				sopln(unknownStrings[x]);
			}
		}

		UIUnderstanding = Tools.removeNullInArray(resultingUnderstanding);
		UnknownPartsOfUI = Tools.removeNullInArray(unknownStrings);
		/*
		 * keywordOrganizer(UI);//organizes keyword list into suitable matching
		 * identifySentenceStructure(UI);//comprehending the string
		 * organizeSentStructArr();//used to reduce clutter in this method String
		 * response = responseCenter(shortSentenceStructArr);
		 * if(!response.equals(""))sopln("Response formed, that being:" + response);
		 */

	}

	private static void interpretInput() {
		/*
		 * the real interpretInput is this one, needs to be rewritten using the new data
		 * structures in Info.java to help reduce clutter and complexity of old
		 * interpretInput.
		 */

	}

	public static void buildDirectory() throws IOException {
		/*
		 * this method is very versatile and wont create extra files if they already
		 * exist. It also initializes the items required by brain.java in info.java
		 * 
		 */

		File thisFile = new File(Brain.class.getName() + ".java");
		String directoryPath = thisFile.getAbsolutePath().replace(thisFile.getName(), "");
		// sopln("Directory being set, it is:" + directoryPath);
		phraseDirectory = directoryPath + "txt files\\phrases\\";
		dataDirectory = directoryPath + "txt files\\data\\";
		fxmlDirectory = directoryPath + "fxmls\\";
		File fxml = new File(directoryPath + "fxmls");
		if (!fxml.exists()) {
			fxml.mkdirs();
		}

		File phrase = new File(directoryPath + "txt files\\phrases");
		File keywords = new File(directoryPath + "txt files\\phrases\\keywords.txt");
		if (!phrase.exists()) {
			phrase.mkdirs();
			keywords.createNewFile();
		} else if (!keywords.exists()) {
			keywords.createNewFile();
		}
		Info preemptive = new Info();
		preemptive.createList("categories");
		preemptive.createList("phrases");
		preemptive.createList("categoryFileNames");
		preemptive.createList("phraseFileNames");
		preemptive.createList("categoriesAndPhrases");
		preemptive.createList("commandTriggers");
		preemptive.createList("commandName");
		preemptive.createList("commandsAndTheTriggers");
		preemptive.createList("commandLocation");
		preemptive.createList("commandGroup");
		preemptive.createList("groupName");
		preemptive.createList("rawResponseGroup");
		preemptive.createList("categoryGrouping");
		preemptive.createList("allFileNames");
		preemptive.createList("dataFileNames");
		preemptive.createList("phraseFileNames");
		preemptive.createList("mathData");
		/*
		 * 
		 * preemptive.createList("excludedPhraseFileNames");
		 * preemptive.createList("excludedCommandFileNames");
		 * preemptive.createList("allExcludedFileNames");
		 */

	}

	public static void updatePhrases() {
		/*
		 * things to record and add onto if needed: categories ✓, phrases ✓,
		 * category-fileNames ✓, phrase-fileNames ✓, categories and their phrases ✓
		 */

		String categories = "categories";
		String phrases = "phrases";
		String phraseFileNames = "phraseFileNames";
		String allFileNames = "allFileNames";
		File file;
		Scanner scan;

		File dir = new File(phraseDirectory);
		String[] listOfPhraseFiles = dir.list();
		if (listOfPhraseFiles == null || listOfPhraseFiles.length == 0) {
			System.out.println("THE PHRASE FILES ARE GONE!!!");
		} else {
			for (String fileName : listOfPhraseFiles) {
				if (!Tools.isStringInArr(fileName, Info.listOfLists.get(phraseFileNames))) {
					Info.listOfLists.replace(phraseFileNames,
							Tools.appendToArray(Info.listOfLists.get(phraseFileNames), fileName));
				}
				if (!Tools.isStringInArr(fileName, Info.listOfLists.get(allFileNames))) {
					Info.listOfLists.replace(phraseFileNames,
							Tools.appendToArray(Info.listOfLists.get(allFileNames), fileName));
				}
				try {
					file = new File(phraseDirectory + fileName);
					scan = new Scanner(file);
					// used for debugging int ctr= 0;
					String line = "";
					String category = "";
					String phrase = "";
					/*
					 * reading through each file here.
					 */
					while (scan.hasNextLine()) {
						line = scan.nextLine();
						category = line.substring(line.indexOf('|'), line.length());
						phrase = line.substring(0, line.indexOf('|') - 1);
						if (!Tools.isStringInArr(category, Info.listOfLists.get(categories))) {
							// records categories
							Info.listOfLists.replace(categories,
									Tools.appendToArray(Info.listOfLists.get(categories), category));
						}
						if (!Tools.isStringInArr(phrase, Info.listOfLists.get(phrases))) {
							// records phrases
							Info.listOfLists.replace(phrases,
									Tools.appendToArray(Info.listOfLists.get(phrases), phrase));
						}
						if (!Tools.isStringInArr(phrase, Info.listOfCategoryNPhrases.get(category))) {
							// records categoriesAndPhrases
							Info.listOfCategoryNPhrases.replace(category,
									Tools.appendToArray(Info.listOfCategoryNPhrases.get(category), phrase));
						}
						if (!Tools.isStringInArr(fileName, Info.listCategoryLocations.get(category))) {
							// records categoriesfileLocations
							Info.listCategoryLocations.replace(category,
									Tools.appendToArray(Info.listCategoryLocations.get(category), fileName));
						}
						/*
						 * useful for debugging System.out.println("line " + ctr + " is:" + line);
						 * ctr++;
						 */
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

			}
		}
	}

	public static void updateData() {
		/*
		 * things to record and add onto if needed: command triggers, commands, commands
		 * and their triggers, all fileNames, data file names, phrase file names,
		 * excluded phrase file names, excluded command file names, all excluded file
		 * names, mathematical data, unknown strings
		 * 
		 * 
		 */
		String commandTriggers = "commandTriggers";
		String commandName = "commandName";
		String commandGroup = "commandGroup";
		String commandsAndTheTriggers = "commandsAndTheTriggers";// ✓
		String commandLocation = "commandLocation";
		String groupName = "groupName";
		String groupAction = "groupAction";
		String categoryGrouping = "categoryGrouping";
		String rawResponseGroup = "rawResponseGroup";
		String allFileNames = "allFileNames";
		String dataFileNames = "dataFileNames";

		String mathData = "mathData";
		File file;
		Scanner scan;

		File dir = new File(dataDirectory);
		String[] listOfDataFiles = dir.list();

		if (listOfDataFiles == null || listOfDataFiles.length == 0) {
			System.out.println("THE DATA FILES ARE GONE!!!");
		} else {
			System.out.println("Printing data files: ");
			for (String fileName : listOfDataFiles) {
				System.out.println(fileName);
				if (!Tools.isStringInArr(fileName, Info.listOfLists.get(dataFileNames))) {
					Info.listOfLists.replace(dataFileNames,
							Tools.appendToArray(Info.listOfLists.get(dataFileNames), fileName));
				}
				if (!Tools.isStringInArr(fileName, Info.listOfLists.get(allFileNames))) {
					Info.listOfLists.replace(dataFileNames,
							Tools.appendToArray(Info.listOfLists.get(allFileNames), fileName));
				}
				try {
					file = new File(dataDirectory + fileName);
					scan = new Scanner(file);
					String line = "";
					String cLocation = "";// what the command does (at end of triggers)
					String cName = "";// command name
					String trigger = "";// trigger words
					String gName = "";// group title
					String gAction = "";// group action
					String mathName = "";

					/*
					 * reading through each data file here.
					 */
					while (scan.hasNextLine()) {
						line = scan.nextLine();
						switch (fileName) {
						case "commandsgrouping.txt":
							if (line.contains(":") && cName != "") {
								gAction = line.substring(line.indexOf('['), line.indexOf(']'));
								gName = line.substring(0, line.indexOf(']') - 1);

							} else if (line.contains(":")) {
								// inputing command information before moving on
								if (!Tools.isStringInArr(trigger, Info.listOfLists.get(commandTriggers))) {
									// assigning trigger info here
									Info.listOfLists.replace(commandTriggers,
											Tools.appendToArray(Info.listOfLists.get(commandTriggers), trigger));
								}
								if (!Tools.isStringInArr(cName, Info.listOfLists.get(commandName))) {
									// assigning commandName info here
									Info.listOfLists.replace(commandName,
											Tools.appendToArray(Info.listOfLists.get(commandName), cName));
								}
								if (!Tools.isStringInArr(cLocation, Info.listOfLists.get(commandLocation))) {
									// assigning command location info here
									Info.listOfLists.replace(commandLocation,
											Tools.appendToArray(Info.listOfLists.get(commandLocation), cLocation));
								}
								if (!Tools.isStringInArr(gAction, Info.listOfLists.get(groupAction))) {
									// assigning group action info here
									Info.listOfLists.replace(groupAction,
											Tools.appendToArray(Info.listOfLists.get(groupAction), gAction));
								}
								if (!Tools.isStringInArr(gName, Info.listOfLists.get(groupName))) {
									// assigning group name info here
									Info.listOfLists.replace(groupName,
											Tools.appendToArray(Info.listOfLists.get(groupName), gName));
								}

								if (!Info.listOfCommandNTriggers.containsKey(cName)) {
									// assigning command trigger info here
									Info.listOfCommandNTriggers.put(cName, trigger.split(","));
								}

								if (!Tools.isStringInArr(cName, Info.listOfCommandGroups.get(gName))) {
									// assigning commandGroup info here
									Info.listOfCommandGroups.replace(gName,
											Tools.appendToArray(Info.listOfCommandGroups.get(gName), cName));
								}

								gAction = line.substring(line.indexOf('['), line.indexOf(']'));
								gName = line.substring(0, line.indexOf(']') - 1);
							} else {
								System.out.println(line);
								cLocation = line.substring(line.indexOf('('), line.indexOf(')') - 1);
								cName = line.substring(0, line.indexOf('[') - 1);
								trigger = line.substring(line.indexOf('['), line.indexOf(']') - 1);
							}

							break;
						case "categorygrouping.txt":
							if (!Tools.isStringInArr(line, Info.listOfLists.get(categoryGrouping))) {
								// assigning category group info here
								Info.listOfLists.replace(categoryGrouping,
										Tools.appendToArray(Info.listOfLists.get(categoryGrouping), line));
							}

							break;

						case "rawresponsegroup.txt":
							if (!Tools.isStringInArr(line, Info.listOfLists.get(rawResponseGroup))) {
								// assigning raw response group info here
								Info.listOfLists.replace(rawResponseGroup,
										Tools.appendToArray(Info.listOfLists.get(rawResponseGroup), line));
							}

							break;
						case "mathinfo.txt":
							mathName = line.substring(0, line.indexOf(':') - 1);
							trigger = line.substring(line.indexOf('[') + 1, line.indexOf(']') - 1);
							if (!Info.listOfMathInfo.containsKey(mathName)) {
								// assigning math info here
								Info.listOfMathInfo.put(mathName, trigger.split(","));
							}
						}

						/*
						 * useful for debugging System.out.println("line " + ctr + " is:" + line);
						 * ctr++;
						 */
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

			}
		}

	}

	public static void setUI(String str) {
		userInput = str;
	}

	public static void sopln(Object obj) {
		System.out.println(obj);
	}

	public static void sopln() {
		System.out.println("");

	}

	public static void sop() {
		System.out.print("");

	}

	public static void sop(Object obj) {
		System.out.print(obj);

	}

}// useful for memory https://www.w3spoint.com/filereader-and-filewriter-in-java

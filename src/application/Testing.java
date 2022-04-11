package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Testing {
	static File file;
	static Scanner scan;
	static String phraseDirectory;
	static String dataDirectory;
	static String fxmlDirectory;

	public static void main(String[] args) throws IOException {
		Info i = new Info();
		i.createList("List1");
		i.addItem("List1", "item1");
		i.addItem("List1", "item2");
		i.addItem("List1", "item3");
		i.showList("List1");
		i.showListNames();
		i.removeItem("List1", "item2");
		i.showList("List1");
		i.returnList("List1");

		CTier command = new CTier();
		command.addNode("root", "N/A", 0);
		command.addNode("group1", "root", 1);
		command.addNode("group2", "root", 1);
		command.addNode("command1", "group2", 2);
		command.addNode("command2", "group1", 2);
		command.displayNode("command2");
		command.addNode("Phrase 1", "command1", 3);
		command.showTree();

		buildDirectory();
		updatePhrases();
	}

	public static void buildDirectory() throws IOException {
		/*
		 * this method is very versatile and wont create extra files if they already
		 * exist. It also initializes the items required by brain.java in info.java
		 * 
		 */

		File thisFile = new File(Tools.class.getName() + ".java");
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

}

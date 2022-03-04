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
public class Brain{
	
	private static String userInput;
	protected static String directory = "";
	protected static int tempInt=0;
	protected static String[] tempArr = {""};
	protected static String tempString = "";
	//used liberally, able to be used as a temporary string 
	static protected String[] CategoryManagerPanelLastActionInfo = {""};
	
	
	//these need to be removed through code revision
	
    //need to make a status manager
    //need to also make a command manager
	//fix | problem in interpretInput at top
	
	protected static String[] listOfStatuses = {"Any","Normal", "WILD!"};

	//how are you doing how 
	//the above line doesn't work in code fix it
    
    //need to write the stuff below
    protected static String response = "";
    
    
	//need to figure a way to store this information in another accessible java file?
	//group of lists for brain to use below.
    protected static String[] UIUnderstanding = {""};
    protected static String[] UnknownPartsOfUI = {""};
    protected static String[] listOfPhrases = {""};
    protected static String[] listOfCategories = {""};
    private static String[] listOfCommands = {""};
    protected static String[] listOfCommandPhrases = {""};
    protected static String[][] listOfCommandGrouping = {{""}};
    protected static String[] fullListOfFiles = {""};
    protected static String[] listOfPhraseFileNameCombo = {""};
    protected static String[] listOfCategoryFileNameCombo = {""};
    protected static String[] filesToIncludeInSearches = {""};
    protected static String[] filesToExcludeFromSearches = {""};
    protected static String[][] listOfCommandLinesByCategory = {{""}};
    protected static String[][] ListOfPhrasesCommandsAndTheirCategories = {{""}};
    protected static String[][] listOfCategoriesAndTheirPhrases = {{""}};
    public static Map<String,String[]> indivComGrouping = new HashMap<>();
    public static Map<String,Map<String,String[]>> totalCommandGrouping = 
    		new HashMap<>();//trying out this method of storing command information

    
	public static void startUp(String input) throws IOException {
		setUI(input);
		buildDirectory();
		updateLists();
		setUserInput(userInput);
		interpretInput(userInput);
		formulateResponse();
		respondToUser();

		//remove this for sure later
		if(UIUnderstanding!=null&&
				UIUnderstanding[0].equals("OpenWindow(ControlPanel.fxml)")) {
			URL url = new File("fxmls\\ControlPanel.fxml").toURI().toURL();
			Parent root = FXMLLoader.load(url);
			Stage stage = new Stage();
			Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.setTitle("Control Panel");
	        stage.show();
		}
	}
	
	
	private static void interpretInput(String UI) throws FileNotFoundException {
		/* should be versatile and allow for user input of any type of thing in file,
		 * examples may include wanting to break sentences down to adjective, noun, verb etc. 
		 * compared to recognizing whole sentences/phrases 
		 * 
		 * best way to accomplish this would be to include
		 * checkIfInfoFromFile(keywordFileName,part of UI), attempt word by word, back to front?
		 * every time a UI string is unknown it is added to unknown strings, vice versa with
		 * resultingUnderstanding
		 */
		String[] elementaryFunctions= {
				"+","-","*","/","(",")",
			"π","^","ln","log","√","|","sqrt","abs",
			"sin","cos","tan",
			"arcsin","arccos","arctan",//this line...
			"asin","acos","atan",//means the same as this line,  just alt spelling.
			"sinh","cosh","tanh",
			"arsinh","arcosh","artanh",//this is inverse of line above.
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
		
		for(int x=0, total = 1, addVar = 2; x<UIByWord.length-1;x++) {
			total = total + addVar;
			addVar++;	
			if(x+1==UIByWord.length-1) {
				unknownStrings = new String[total];
			}
		}
		unknownStrings = new String[UIByWord.length];
		if(!doesStrContainArr(UI,elementaryFunctions)) {
		if(UI.contains(" ")) {
			/* Case of UI having more than one word. The process is explained below.
			 * first checking all words together, then slowly taking it apart from the end
			 * until only beginning word left (the left-most word) if not able to be found.
			 * if found though, remove that portion of the string, trim(), then
			 * loop in trying to understand string. if we really can't understand down to the
			 * word itself, add it to compendium of unknown words/gibberish, remove from string,
			 * then continue trying to understand the rest of the string. repeat until nothing
			 * left. if words are in a continuous row that we don't understand, append
			 * the new words to the end of the last one with a space, else move to next index
			 * in unknown words array
			 */
			
			for(int numberOfSkippedWords = 0; numberOfSkippedWords<UIByWord.length;) {
				//operates the unknown word index
				for(int backwardCounter=UIByWord.length;backwardCounter>numberOfSkippedWords;
						backwardCounter--) {
					//operates the backward counter index for the identification of UIByWord
					tempString = "";
					isFound = false;
					for(int tempStringBuilderIndex = numberOfSkippedWords; 
							tempStringBuilderIndex<backwardCounter;tempStringBuilderIndex++) {
						//builds the new string to attempt to identify
						if(tempStringBuilderIndex==numberOfSkippedWords) {
							tempString = UIByWord[tempStringBuilderIndex];
						}else{
							tempString = tempString + " " + UIByWord[tempStringBuilderIndex];
						}
					}//by here stringToBuild is built
					//must be able to match a string already in system, else stripped of trailing
					//word and tried again until either something is found, or nothing left.
					for(int outerIndex = 0; outerIndex < FullList.length;
							outerIndex++) {
						for(int innerIndex = 1; innerIndex<FullList
								[outerIndex].length; innerIndex++) {
							//both for operates searching through listOfCategoriesAndTheirPhrases
							if(tempString.equalsIgnoreCase(
									FullList[outerIndex][innerIndex])) {
								//if a match is found, remove backwards counter number
								//of indexes from UIByWord from the front, and build the next 
								//string by adding
								//backwards counter number to numberOfSkippedWords 
								resultingUnderstanding[understandingCntr] = 
										FullList[outerIndex][0];
								understandingCntr++;
								numberOfSkippedWords = backwardCounter;//UIByWord.length-
								sopln("the number of words we know is:" +
										numberOfSkippedWords);
								isFound = true;
								innerIndex = FullList
										[outerIndex].length-1;
								outerIndex = FullList.length-1;
							}
						}
						
					if(backwardCounter-1==numberOfSkippedWords){
						numberOfSkippedWords++;
					}
					if(outerIndex+1==FullList.length
							&&numberOfSkippedWords==backwardCounter&&isFound) {
						//this needs to be called multiple times for multi-word recognition
						//need new number of known
						/*
						//numberOfSkippedWords will be 1+ always
						//need the number of words that we previously don't know
						//Boolean[] tempArr = removeNullInArray(UIByWordBoolean);
						//int numberOfUnknown=0;
						//for(int x=0; x<tempArr.length;x++) {
						//	if(!tempArr[x])numberOfUnknown++;
						//}
						//for(int x=0;x<numberOfSkippedWords-numberOfUnknown;x++) {
						//}
						 * 
						 */
						UIByWordBoolean[boolCtr] = true;
						boolCtr++;
					}else if(outerIndex+1==FullList.length
							&&numberOfSkippedWords==backwardCounter) {
						
						if(boolCtr>0&&UIByWordBoolean[boolCtr-1]==true) {
							//finishes line of trues if need be
						for(int x=0,y=boolCtr; x<numberOfSkippedWords-y-1;x++) {
							UIByWordBoolean[boolCtr]=true;
							boolCtr++;
						}
						}
						
						UIByWordBoolean[boolCtr] = false;
						boolCtr++;
					}
					//how are you doing how borken
			}	//backwards counter measures the number of words being considered
				//floor measures the number of unknown words
			}
			//listOfPhraseFileNameCombo
			}	
		}else {//one word
			for(int outerIndex = 0; outerIndex < FullList.length;
					outerIndex++) {
				for(int innerIndex = 1; innerIndex<FullList
						[outerIndex].length; innerIndex++) {
					if(FullList[outerIndex][innerIndex].equalsIgnoreCase(UI)) {
						resultingUnderstanding[understandingCntr] = 
								FullList[outerIndex][0];
						isFound=true;
					} 
			}
				if(outerIndex+1==FullList.length&&isFound) {
					UIByWordBoolean[boolCtr] = true;
					boolCtr++;
				}else if(outerIndex+1==FullList.length) {
					UIByWordBoolean[boolCtr] = false;
					boolCtr++;
				}
		}
		}
		if(UIByWordBoolean!=null) {
			UIByWordBoolean = removeNullInArray(UIByWordBoolean);
			for(int x=0,indexOfFirstFalse = 0;x<UIByWordBoolean.length;x++) {
				
			if(x>=1&&!UIByWordBoolean[x-1]&&!UIByWordBoolean[x]) {
				//if both the last one and this one are false, append this to unknownStrings[x]
				unknownStrings[indexOfFirstFalse] = unknownStrings[indexOfFirstFalse].concat
						(" "+UIByWord[x]);
			}else if(x>=1&&UIByWordBoolean[x-1]&&!UIByWordBoolean[x]){
				//if changing into a false
				indexOfFirstFalse=x;
				unknownStrings[x] = UIByWord[x];
			}else if(!UIByWordBoolean[x]) {
				unknownStrings[x] = UIByWord[x];
			}
		}
		}
		}else if(doesStrContainArr(UI,elementaryFunctions)){
			resultingUnderstanding[0]=solveExpression(UI);
		}else {
			sopln("We dont know how to deal with whatever that is yet :(");
		}
		
		if(resultingUnderstanding!=null) {
		sopln("The understanding of UI is as follows:");
		resultingUnderstanding = removeNullInArray(resultingUnderstanding);
		for(int x = 0; x<resultingUnderstanding.length;x++) {
			sopln(resultingUnderstanding[x]);
		}
		}
		unknownStrings = removeNullInArray(unknownStrings);
		if(unknownStrings!=null) {
			sopln("The unknown parts of UI is as follows:");
			for(int x = 0; x<unknownStrings.length;x++) {
				sopln(unknownStrings[x]);
			}	
		}
		
		UIUnderstanding=removeNullInArray(resultingUnderstanding);
		UnknownPartsOfUI=removeNullInArray(unknownStrings);
		/*
		keywordOrganizer(UI);//organizes keyword list into suitable matching
		identifySentenceStructure(UI);//comprehending the string
		organizeSentStructArr();//used to reduce clutter in this method
		String response = responseCenter(shortSentenceStructArr);
		if(!response.equals(""))sopln("Response formed, that being:" + response);
		*/
		
	}
		
	public static String[] appendToArray(String[] arr, String str) {
		for(int x=0;x<arr.length;x++) {
			if(arr[x]==null||arr[x]=="") {
				arr[x]=str;
				break;
			}
		}
		return arr;
	}
	
	public static String[] removeNullInArray(String[] arr) {
		int numberOfNull=0;
		String[] nullessArr;
		if(arr!=null&&arr.length>0) {
		for(int x=0;x<arr.length; x++) {
			if(arr[x]==null||arr[x].equals("")) {
				numberOfNull++;
			}
		}
		if(numberOfNull==0) {
			return arr;
		}else if(arr.length-numberOfNull<=1) {
			nullessArr = new String[1];
		}else {
			nullessArr = new String[arr.length-numberOfNull];
		}
		for(int x=0, y=0;x<arr.length;x++) {
			if(arr[x]!=null&&!arr[x].equals("")) {
				nullessArr[y]=arr[x];
				y++;
			}
		}
		if(nullessArr[0]==null) {//used to have nullessArr.length<2&&
			nullessArr[0] = "";
		}
		return nullessArr;
	}else {
		return arr;
	}
	}
	
	public static Boolean[] removeNullInArray(Boolean[] arr) {
		if(arr!=null&&arr.length>0) {
		int nonNullArrIndexes = 0;
		for(int x=0; x<arr.length;x++) {
			if(arr[x]!=null)nonNullArrIndexes++;
		}
		Boolean[] newArr = new Boolean[nonNullArrIndexes];
		for(int x=0,y=0; x<arr.length;x++) {
			if(arr[x]!=null) {
				newArr[y]=arr[x];
				y++;
			}
		}
			return newArr;
		}else {
			return arr;
		}
	}
	
	
	public static void appendToFile(String fileName, String str){
		if(!fileName.contains(directory)) {
			fileName = directory + fileName;
		}
		File file = new File (fileName); 
		FileWriter output = null;
		Scanner scanner = null;
		try {
			scanner = new  Scanner (file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String tempString = "";
		String wholeFile = "";
		if(!scanner.hasNextLine()) {
			try {
				output = new FileWriter(fileName);
			    output.write(str);
			    output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
        while(scanner.hasNextLine()) {
        	tempString = scanner.nextLine();
        	if(wholeFile.equals("")&&scanner.hasNextLine()) {
        		wholeFile = tempString;
        	}else if(wholeFile.equals("")&&!scanner.hasNextLine()) {
        		wholeFile = tempString + "\n" + str;
        	}
        	else if(scanner.hasNextLine()){
            	wholeFile = wholeFile + "\n" + tempString;
        	}else {
        		wholeFile = wholeFile + "\n" + tempString + "\n" + str;
        	}
        }
	    try {
			output = new FileWriter(fileName);
		    output.write(wholeFile);
		    output.close();
		    scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	}
	
	public static void removeFromFile(String fileName, String str) {
		if(!fileName.contains(directory)) {
			fileName = directory + fileName;
		}
		Scanner scanner = null;
		 FileWriter output = null;
		 File file;
		try {
		file = new File (fileName); 
		scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String tempString = "";
		String wholeFile = "";
        while(scanner.hasNextLine()) {
        	tempString = scanner.nextLine();
        	if(!tempString.equals(str)) {
        	if(wholeFile.equals("")) {
        		wholeFile = tempString;
        	}else {
            	wholeFile = wholeFile + "\n" + tempString;
        	}
        	}
        }
	    try {
			output = new FileWriter(fileName);
		    output.write(wholeFile);
		    output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String replaceLast(String UI, String searchFor, String replaceWith) {
		if(UI.contains(searchFor)) {
		UI = UI.substring(0,UI.lastIndexOf(searchFor)) + replaceWith
				+ UI.substring(UI.lastIndexOf(searchFor) + searchFor.length(), UI.length());}
		return UI;
	}
	
	public static int countChars(String str, char targetChar) {
		int counter = 0;
		for(int x=0; x<str.length();x++) {
			if(str.charAt(x)==targetChar){
				counter++;
			}
		}
		return counter;
	}

	public static int countNumberOfStringsInFile(String fileName, String str) {
		if(!fileName.contains(directory)) {
			fileName = directory + fileName;
		}
		int counter = 0;
		File file = new File(fileName);
		Scanner sc = null;
		String line = "";
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(sc.hasNextLine()) {
			line = sc.nextLine();
			if(line.equals(str)) counter++;
		}
		sc.close();
		return counter;
	}
	
	

	
	public static void updateLists() throws IOException {
		String[] categoryList = null;
		String[] phraseList = null;
		String[] commandCategoryList = null;
		String[] commandPhraseList = null;
		String[] commandList = null;
		String[][] commandFileList=null;
		String[][] phrasesCommandsAndTheirCategoriesList = null;
		String[] categoryFileNameComboList = null;
		String[] phraseFileNameList = null;
		String[][] phraseAndCategoryList = null;
		String[] fileNameList = null;
		String[] tempStringArr=null;
		String[] excludedFileNameList = {
				"keywords.txt","filelist.txt","categorygrouping.txt","inputresponsegrouping.txt"
				,"commands.txt", "commandsgrouping.txt"};
		//i know this is hardcoded im trying to make a manager for it
		String line = "";
		String tempString="";
		String groupTitle = "";
		int tempCtr = 0;
		int indexCtr=0;
		int groupNum = 0;
		Boolean validFile=false;
		File file;
		Scanner sc;


        //need to search every phrase .txt file for categories list (for categoryManager)
		//need to search for every phrase and record it in phrase list (for categoryManager)
		//need to search for every status and record it in status list (for categoryManager)
		//may as well include the corresponding phrase-category (for ???)
		//may as well include the corresponding phrase-fileName (for keywords.txt)
		//may as well fix end of file, or append "unknown" if needed
		//
        File dir = new File(directory);
        String[] children = dir.list();
        if (children == null||children.length==0) {
        	sopln("THE PHRASE FILES ARE GONE!!!");
        }else {
        	//finding fileNameList length
        	for(int x=0, z=0; x<children.length;x++) {
        		validFile=true;//ensuring all the files added are valid
        		for(int y=0;y<excludedFileNameList.length;y++) {
        			if(excludedFileNameList[y].equals(children[x])) {
        				validFile=false;
        				break;
        			}
        		}
        		if(validFile) z++;
        		if(x==children.length-1) {
        			fileNameList = new String[z];
        		}
        	}
        	
        	//Assigning fileNameList
        	for(int x=0, z=0; x<children.length;x++) {
        		validFile=true;//ensuring all the files added are valid
        		for(int y=0;y<excludedFileNameList.length;y++) {
        			if(excludedFileNameList[y].equals(children[x])) {
        				validFile=false;
        				break;
        			}
        		}
        		if(validFile) {
        			fileNameList[z] = children[x];
        			z++;
        		}
        	}
        	//fileNameList is filled with valid file names
        	//counting the number of total lines in all files & number of total categories
        	//and helps remove any empty spaces in the files
        	for(int x=0, y=0, z=0; x<fileNameList.length;x++) {
    			removeEmptySpaceFromFile(fileNameList[x]);
        		y = y + numOfLinesInFile(fileNameList[x]);
        		if(numOfLinesInFile(fileNameList[x])!=0) {//avoiding empty .txt files
            		z = z + toWholeFileAroundCategories(fileNameList[x]).split("\n").length;
        		}
        		if (x==fileNameList.length-1) {//triggers at the end of the final for run 
                	phraseList = new String[y];
            		phraseFileNameList = new String[y];
            		categoryList = new String[z];
            		commandPhraseList = new String[numOfLinesInFile("commands.txt")];
        			categoryFileNameComboList = new String[z];
        			phrasesCommandsAndTheirCategoriesList = new String[z+1][];
        			//will include command category
        		}
        	}
			//now finding List of phrases and its variation as well as categories
            for(int x=0, z=0 , i=0; x<fileNameList.length;x++) {
            		file = new File(directory + fileNameList[x]);
					sc = new Scanner(file);

					//this further vets categories for any empty indexes
					for(int y=0;y<toWholeFileAroundCategories(fileNameList[x]).split("\n").length;y++) {
						if(numOfLinesInFile(fileNameList[x])!=0) {//avoiding empty files
						categoryList[i]=removeNullInArray(
								toWholeFileAroundCategories(fileNameList[x]).split("\n"))[y];
						
						categoryFileNameComboList[i]=
								removeNullInArray(toWholeFileAroundCategories(fileNameList[x])
										.split("\n"))[y] + "|" + fileNameList[x];
						i++;
					}
					}
					
				while(sc.hasNextLine()) {//first find phrase and fileName
					line=sc.nextLine();
		    		//sopln("tempString is: " + tempString);
					if(line.contains("|")) {
						phraseList[z] = line.substring(0, line.indexOf('|'));
						phraseFileNameList[z]=line.substring(0, line.indexOf('|'))
								+ "//" + fileNameList[x];
						z++;
					}
				}
	            sc.close();
           }
            
            //now reading and assigning info to commandFileList
            //first, need # of categories and # of phrases under each
            //can make array of each category and assign it
            tempString="";
            indexCtr=0;
            commandFileList = new String[toWholeFileAroundCategories("commands.txt")
    		.split("\n").length][];
    		file = new File(directory + "commands.txt");
			sc = new Scanner(file);
            while(sc.hasNextLine()) {
            	line=sc.nextLine();
            	
            	if(!tempString.contains(line.substring(line.indexOf('|')+1,
            			line.length()))) {
            		//new category and a new phrase for it, old one must be stored first
            		if(commandCategoryList!=null) {
                		commandFileList[indexCtr] = commandCategoryList;
                		indexCtr++;
            		}
            		tempCtr=1;
            		commandCategoryList = new String[numOfLinesInFileOfCat("commands.txt",
            				line.substring(line.indexOf('|')+1,line.length()))+1];
            		commandCategoryList[0]=line.substring(line.indexOf('|')+1, line.length());
            		commandCategoryList[tempCtr]=line.substring(0, line.indexOf('|'));
            		tempCtr++;
            		tempString = tempString.concat(
            				line.substring(line.indexOf('|')+1,line.length()));
            	}else {
            		//a phrase that's apart of the category already
            		commandCategoryList[tempCtr] = line.substring(0, line.indexOf('|'));
            		tempCtr++;	
            	}
        		if(!sc.hasNextLine()&&commandFileList[indexCtr]==null) {
            		commandFileList[indexCtr] = commandCategoryList;
            		indexCtr++;
        		}
            }
            //at this point commandFileList is fully filled with commands.txt info
			
            
			//now need to initialize listOfCategoriesAndTheirPhrases, so need # of categories
        	//and number of phrases under each
            //now assigning listOfCategoriesAndTheirPhrases
            
            phraseAndCategoryList = new String[categoryList.length][];
            for(int x=0, z=0; x<categoryList.length;x++) {
            	z=0;
        	for(int y=0; y<fileNameList.length;y++) {
        		file = new File(directory + fileNameList[y]);
				sc = new Scanner(file);
				while(sc.hasNextLine()) {
					line=sc.nextLine();
					if(line.contains("|")&&
							line.substring(line.indexOf("|")+1,line.length())
							.equals(categoryList[x])&&line.substring(0, line
									.indexOf("|")).length()>0) {//change here 
						z++;
					}
				}
        	}
        	//the length of each will be the number of lines corresponding to that category 
        	//in the files plus one to accommodate the category name
        	tempStringArr = new String[z+1];
        	phraseAndCategoryList[x] = tempStringArr;
        	phraseAndCategoryList[x][0]=categoryList[x];
        	}
            
            //now assigning the phrases to the empty array indexes
            for(int x=0, z=0; x<categoryList.length;x++) {
            	z=0;
        	for(int y=0; y<fileNameList.length;y++) {
        		file = new File(directory + fileNameList[y]);
				sc = new Scanner(file);
				while(sc.hasNextLine()) {
					tempString=sc.nextLine();
					if(tempString.contains("|")&&
							tempString.substring(tempString.indexOf("|")+1,tempString.length())
							.equals(categoryList[x])&&
							tempString.substring(0,tempString.indexOf("|")).length()>0) {
						phraseAndCategoryList[x][z+1]=tempString.substring(0, tempString.indexOf("|"));
						z++;
					}
				}
        	}
            }
			sc.close();
    		file = new File(directory + "commands.txt");
			sc = new Scanner(file);
			tempCtr=0;
			tempInt = 0;
			commandList = new String[commandPhraseList.length];
			while(sc.hasNextLine()) {
				line = sc.nextLine();
				if(line.contains("|")) {
					commandPhraseList[tempCtr]=line.substring(0,line.indexOf('|'));
					if(!doesStrContainArr(line.substring(line.indexOf('|')+1,line.indexOf('(')),
							removeNullInArray(commandList))){
						commandList[tempInt] = line.substring(line.indexOf('|')+1,line.indexOf('('));
					}
					tempCtr++;
				}
			}
			
			
			//collecting list of command grouping in commandGrouping hashmap for first time!!
			sc.close();
    		file = new File(directory + "commandsgrouping.txt");
			sc = new Scanner(file);
			tempCtr=0;
			//tempInt = 0;
			//tempString = "";
			groupTitle = "";
			while(sc.hasNextLine()) {
				indivComGrouping = new HashMap<>();
				line = sc.nextLine();
				//tempString = line.replace(":", "");
				if(line.charAt(line.length()-1)==':') {
					//assigning title of group of category
					groupTitle = line.substring(0, line.length()-1);
					//tempCtr = 0;
					groupNum = 0;
				}else if(line.contains(",")) {
					//tempArr = new String[countChars(line,',')+1];
					tempArr = line.substring(line.indexOf("[")+1, line.lastIndexOf("]")).
							split(",");
					if(!groupTitle.equals("")&&tempArr!=null) {
						//line.substring(0,line.indexOf("[")) = the name of command
						if(line.contains("(")&&line.contains(")")) {
							tempArr = addStringToArr(tempArr,
									line.substring(line.indexOf("("),line.length())
									,tempArr.length);
						}
						
						//using tempString as the tell for whether a new groupTitle is read
						if(groupNum==0) {
							groupTitle = groupTitle.concat("["+groupNum + "]");
						}else {
							groupTitle = replaceLast(groupTitle,
									"["+(groupNum-1)+"]","["+groupNum+"]");
						}
						groupNum++;
						//not adding both groups as it should. overrites the first one fir some reason
						//could do index counter at end of tempString like this
						//groupName[0] = hashmap of all commands within this subgroup
						if(!totalCommandGrouping.containsKey(groupTitle)) {
						indivComGrouping.put(line.substring(0,line.indexOf("["))
								, tempArr);
						totalCommandGrouping.put(groupTitle, indivComGrouping);
						}
					}
				}
			}
			
			
			
			
			
			commandList = removeNullInArray(commandList);
			
			
            //assigning phrasesCommandsAndTheirCategoriesList
            phrasesCommandsAndTheirCategoriesList = 
            		stackArrays(commandFileList,phraseAndCategoryList);
            sopln();
            sopln("indiv grouping keysets are:"+indivComGrouping.keySet().toString());
            sopln("totalCommandGrouping is:"+totalCommandGrouping.keySet().toString());
            sopln("secondly," + 
            totalCommandGrouping.get("Testing Command Group[0]").get("testingCommand1")
            );
            for(int x=0; x<totalCommandGrouping.get("Testing Command Group[0]").
            		get("testingCommand1").length; x++) {
            	sopln("uhh of" +"["+ x+ "]"+totalCommandGrouping.get
            			("Testing Command Group[0]").get("testingCommand1")[x].toString());
            }

            
            
            listOfCommands = commandList;
            listOfCommandLinesByCategory=commandFileList;
            ListOfPhrasesCommandsAndTheirCategories= phrasesCommandsAndTheirCategoriesList;
            listOfCommandPhrases = commandCategoryList;
            listOfCategoryFileNameCombo = categoryFileNameComboList;
            filesToIncludeInSearches = fileNameList;
            filesToExcludeFromSearches = excludedFileNameList;
            listOfCategoriesAndTheirPhrases = phraseAndCategoryList;
            listOfPhrases = phraseList;
            listOfPhraseFileNameCombo = phraseFileNameList;
    		listOfCategories = categoryList;
    		fullListOfFiles = children;
        }
	}
	
	public static int numOfLinesInFile(String fileName) throws FileNotFoundException {
		if(!fileName.contains(directory)) {
			fileName = directory + fileName;
		}
		int counter = 0;
		File file = new File(fileName);
		Scanner sc = new Scanner(file);
		while(sc.hasNextLine()) {
			sc.nextLine();
			counter++;
		}
		sc.close();
		return counter;
	}
	
	public static String stringArrToWholeString(String[] strArr){
		String str = "";
		for(int x=0; x<strArr.length;x++) {
			if(x==strArr.length-1) {
				str = str.concat(strArr[x]);
			}else {
				str = str.concat(strArr[x] + "\n");
			}	
		}
		return str;
	}
	
	public static String toWholeFileAroundCategories(String fileName)
			throws FileNotFoundException {
		if(!fileName.contains(directory)) {
			fileName = directory + fileName;
		}
		String superStringCategory= "";
		String category = "";
		String line = "";
		File file = new File(fileName);
		Scanner sc = new Scanner(file);
		while(sc.hasNextLine()) {
			line=sc.nextLine();
			category = line.substring(line.indexOf('|')+1, line.length());
			
			if(sc.hasNextLine()&&!superStringCategory.contains(category)) {
			superStringCategory=superStringCategory.concat(category)+"\n";
			
			}else if(!superStringCategory.contains(category)){
				superStringCategory=superStringCategory.concat(category);
			}
		}
		sc.close();
		return superStringCategory;
	}
	
	public static String toWholeFileAroundPhrasesAndCategories(String fileName)
			throws FileNotFoundException{
		if(!fileName.contains(directory)) {
			fileName = directory + fileName;
		}
		String superStringCategoryAndPhrase= "";
		File file = new File(fileName);
		Scanner sc = new Scanner(file);
		while(sc.hasNextLine()) {
			if(sc.hasNextLine()) {
				superStringCategoryAndPhrase=superStringCategoryAndPhrase.concat(sc.nextLine()
						+"\n");
			}else {
				superStringCategoryAndPhrase=superStringCategoryAndPhrase.concat(sc.nextLine());
			}
		}
		sc.close();
		return superStringCategoryAndPhrase.trim();
	}
	
	public static String getWholeFileToString(String fileName) {
		if(!fileName.contains(directory)) {
			fileName = directory + fileName;
		}
		File file = new File (fileName); 
    	Scanner scanner = null;
		try {
			scanner = new  Scanner (file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String tempString = "";
		String wholeFile = "";
        while(scanner.hasNextLine()) {
        	tempString = scanner.nextLine();
        	if(wholeFile.equals("")) {
        		wholeFile = tempString;
        	}else {
            	wholeFile = wholeFile + "\n" + tempString;
        	}
        }
		return wholeFile;
	}
	
	public static void removeEmptySpaceFromFile(String fileName) throws IOException{
	
		if(!fileName.contains(directory)) {
			fileName = directory + fileName;
		}
		File file = new File(fileName);
		Scanner sc = new Scanner(file);
	    FileWriter output;
		String line = "";
		String str = "";
		while(sc.hasNextLine()) {

			line = sc.nextLine();
			if(sc.hasNextLine()&&!line.equals("")) {
				str = str.concat(line + "\n");
			}else if(!line.equals("")){
				str = str.concat(line);
			}
		}
		output = new FileWriter(fileName);
		if(str!="") {
			output.write(str);
		}
		output.close();
		sc.close();
	}
	
	public static Boolean isSurroundedByBrackets(String str) {
		//objective of this method is to test whether the format is correct and ready to read,
		//that is to have a pair of '[' and ']' surrounding the whole string
		if((!str.contains("[")&&!str.contains("]"))
				||!str.replace(str.substring(str.indexOf('['),str.lastIndexOf("]")+1)
				, "").equals("")) {
			//if removing substring between [] makes str != "" then return false 
			//basically vetting bad cases of "hello + [stuff]" and things with no brackets
			return false;
		}else if(str.charAt(0)=='['&&str.charAt(str.length()-1)==']') {
			return true;
		}
		return false;
	}
	
	public static Boolean isStringAvailable(String str) {
		//this tests whether a string is already in the system 
		//or whether it can be added without problem
		for(int x=0; x<fullListOfFiles.length;x++) {
			if(str.equals(fullListOfFiles[x].substring(0, fullListOfFiles[x].indexOf(".")-1))) {
				return false;
			}
		}
		for(int x=0; x<listOfCategories.length;x++) {
			if(str.equals(listOfCategories[x])) {
				return false;
			}	
		}
		for(int x=0; x<listOfPhrases.length;x++) {
			if(str.equals(listOfPhrases[x])) {
				return false;
			}	
		}
		return true;
	}
	
	public static Boolean isStringInFile(String fileName, String str) {
		//checks whether a string is equal to any line from given file
		if(!fileName.contains(directory)) {
			fileName = directory + fileName;
		}
		File file = new File(fileName);
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line = "";
		while(sc.hasNextLine()) {
			line = sc.nextLine();
			if(line.equals(str)) {
				return true;
			}	
		}
		return false;
	}
	
	public static void inputNewCategoryManagerPanelLastActionInfo(String action, String location,
			String str) {
		action = action.toLowerCase().trim();
		String[] lastActionInfo = {action,location,str};
		CategoryManagerPanelLastActionInfo = lastActionInfo;
	}
	
	public static void inputNewCategoryManagerPanelLastActionInfo(String action, String location,
			String str1, String str2) {
		action = action.toLowerCase().trim();

		if(str2.equals("")) {
			inputNewCategoryManagerPanelLastActionInfo(action, location, str1);
		}else if(!action.equals("")&&!location.equals("")&&!str1.equals("")){
			String[] lastActionInfo = {action,location,str1,str2};
			CategoryManagerPanelLastActionInfo = lastActionInfo;
			/*
			 * 		really useful 
			 *  sopln("action is:" + action + "\n" +
				"location is:" + location + "\n" +
				"string1 is:" + str1 + "\n" +
				"string2 is:" + str2 + "\n");
			 */
		}
	}
	
	
	public static void buildDirectory() throws IOException {
		//this method is very versatile and wont create extra files if they already exist.
		//extra files are expected to be added here, namely the strings located
		//in filesToExcludeFromSearches, and remove an aspect of hard coding in this code
			File thisFile = new File(Brain.class.getName()+".java");
			String directoryPath = thisFile.getAbsolutePath().replace(
					thisFile.getName(), "");
			//sopln("Directory being set, it is:" + directoryPath);
			directory = directoryPath + "phrases\\";
			
			File fxml = new File(directoryPath + "fxmls");
			if(!fxml.exists()) {
				fxml.mkdirs();
			}
			
			File phrase = new File(directoryPath + "phrases");
			File keywords = new File(directoryPath+"\\phrases\\keywords.txt");
			if(!phrase.exists()) {
				phrase.mkdirs();
				keywords.createNewFile();
			}else if(!keywords.exists()) {
				keywords.createNewFile();
			}
			
	}

	public String getUserInput() {
		return userInput;
	}
	
	private static void setUserInput(String UI) {
		userInput = UI;
	}
	
	public static Boolean doesStrContainArr(String str, String[] arr) {
		for(int x=0; x<arr.length;x++) {
			if(!arr[x].equals("")&&!str.equals("")&&str.contains(arr[x])) {
				return true;
			}
		}
		return false;
	}
	

	public static String[] stackArrays(String[] arr1, String[] arr2) {
		/* the purpose of this method is to allow users the ability to 'stack' arrays atop each
		 * other. This will entail merging the two arrays into one larger array, putting arr1
		 * information before inputing arr2 information
		 */
		int tmpCtr=0;
		String[] stackedArr = new String[arr1.length+arr2.length];
		for(String str:arr1) {
			stackedArr[tmpCtr]=str;
			tmpCtr++;
		}
		for(String str:arr2) {
			stackedArr[tmpCtr]=str;
			tmpCtr++;
		}
		return stackedArr;
	}
	
	public static String[][] stackArrays(String[][] arr1, String[][] arr2){
		/* the purpose of this method is to allow users the ability to 'stack' arrays atop each
		 * other. This will entail merging the two arrays into one larger array, putting arr1
		 * information before inputing arr2 information
		 */
		int tmpCtr=0;
		String[][] stackedArr = new String[arr1.length+arr2.length][];
		for(String[] str:arr1) {
			stackedArr[tmpCtr]=str;
			tmpCtr++;
		}
		for(String[] str:arr2) {
			stackedArr[tmpCtr]=str;
			tmpCtr++;
		}
		return stackedArr;
	}
	



	private static String solveExpression(String str) {
		//supposed to intake any expression, assuming it is able to be processed
		
		String[] elementaryFunctions= {
				"+","-","*","/","(",")",
			"π","^","ln","log","√","|","sqrt","abs",
			"sin","cos","tan",
			"arcsin","arccos","arctan",//this line...
			"asin","acos","atan",//means the same as this line,  just alt spelling.
			"sinh","cosh","tanh",
			"arsinh","arcosh","artanh",//this is inverse of line above.
		};
		String[] emdas = {
				"^","*","/","+","-"
		};
		String[] parenth = {
				"(",")"	
		};
		String[] otherNonTrig= {
				"ln","log","√","|","sqrt","abs"
		};
		String[] trig = {
				"sin","cos","tan",
				"arcsin","arccos","arctan",//this line...
				"asin","acos","atan",//means the same as this line,  just alt spelling.
				"sinh","cosh","tanh",
				"arsinh","arcosh","artanh",
		};
		
		/* this process of expression solving will go as follows:
		 * first, run it through simplifyExp to 'clean' the expression.
		 * then, if there are any parenthesis, check to see within about whether more than one
		 * non-parenthesis elementary function exists, if so call identifyNextOperation to place
		 * parenthesis around the appropriate part, further isolating the simplest operation.
		 * once simplest operation* is found, send it to solveSimpleExp expecting a 
		 * double answer. once answer is formulated, replace the string with the double answer.
		 * 
		 * Repeating the above steps should lead to the answer to any expression.
		 * *The simplest operation mentioned here is string with only 1 operator
		 * 
		 * case where √ or - could be answer to a number. 
		 * maybe give option to see total worked out version after seeing just the √?
		 * 
		 */
		
		
		
		
		str = simplifyExp(str);
		
		
		//only simplifies right now
		
		
		return str;
	}

	public static int numOfLinesInFileOfCat(String fileName, String targetCategory)
			throws FileNotFoundException {
		File file;
		if(fileName.contains(directory)) {
			file = new File(fileName);
		}else {
			file = new File(directory + fileName);
		}
		int counter = 0;
		Scanner sc = new Scanner(file);
		String line = "";
		while(sc.hasNextLine()) {
			line = sc.nextLine();
			if(targetCategory.equals(line.substring(line.indexOf('|')+1, line.length()))) {
				if(counter==-1) {
					counter = 1;
				}else {
					counter++;
				}
			}
		}
		sc.close();
		return counter;
	}
	
	
	private static String formulateResponse() {
		
		
		/*if(UIUnderstanding!=null&&!doesStrContainLetters(UIUnderstanding[0])&&
				doesStrContainNumber(UIUnderstanding[0])){
			return UIUnderstanding[0];
		}
		
		
		
		if(doesExpContainElementary(userInput)) {
			response = solveExpression(userInput);
			sopln("The result of '" +userInput+"' is:" + response);
		}
		
		*/
		
		return response;
	}
	
	private static String respondToUser() {
		
		
		
		return "";
	}
	
	private static String simplifyExp(String str) {
		/* the purpose of this method is to 'clean' the expression of unnecessary clutter
		 * 1. trims unnecessary operators and empty parenthesis, 
		 * 2. inserts * between number & parenthesis, & parenthesis & | if applicable, 
		 * 2.1 inserts floating emdas after cleaning parenthesis up
		 * 3. inserts parenthesis before and after number following nonPemdas functions, 
		 * 4. fixes abs and |, sqrt and √, and ** to ^
		 * 5. removes parenthesis around singular doubles/ints if no trig function is prior, 
		 * 6. else work out trig func
		 * 7. any parenthesis, trig, digits and dots,non-trig all require the * between each
		 * 8. replace .1 with 0.1
		 * 8+3() = 8+3
		 * 8(9)=8*(9) or (8)(9)=(8)*(9)
		 * 8+3-(1) = 8+3-1
		 * sin0 cos0 tan0 = sin(0) cos(0) tan(0)
		 * abs-1 = |-1|
		 * sqrt2 = √2
		 * NEED TO MAKE AN ALL INCLUSIVE EXCEPTION THROW AROUND WHATS WRONG WITH STRING
		 * for warning throws:
		 * too many/not enough, Parenthesis, '|',
		 * non-elementary function detected
		 * dividing by 0
		 * 
		 */
		
		sopln("Before cleaning:" + str);

		String[] elementaryFunctions= {
				"+","-","*","/","(",")",
			"π","^","ln","log","√","|","sqrt","abs",
			"sin","cos","tan",
			"arcsin","arccos","arctan",//this line...
			"asin","acos","atan",//means the same as this line,  just alt spelling.
			"sinh","cosh","tanh",
			"arsinh","arcosh","artanh",//this is inverse of line above.
		};
		String[] emdas = {"^","*","/","+","-"};
		String[] emda = {"^","*","/","+"};
		String[] parenth = {"(",")"	};
		String[] otherNonTrig= {"ln","log","√","|","sqrt","abs"};
		String[] trig = {
				"sin","cos","tan",
				"arcsin","arccos","arctan",//this line...
				"asin","acos","atan",//means the same as this line,  just alt spelling.
				"sinh","cosh","tanh","arsinh","arcosh","artanh",
		};
		String[] nonPemdas = {
						"ln","log","√","sqrt","abs","sin","cos","tan",
					"arcsin","arccos","arctan","sinh","cosh","tanh","arsinh","arcosh","artanh"
		};
		String[] digits = {"0","1","2","3","4","5","6","7","8","9","."};
		
		String[] allButP = {
				"^","*","/","+","-","ln","log","√","|","sqrt","abs","sin","cos","tan","arcsin",
				"arccos","arctan","asin","acos","atan","sinh","cosh","tanh","arsinh","arcosh",
				"artanh"
};
		
		String strReducer = str;
        String strBuilder = "";
        //String inBetwStr = "";
        String ogStr = str;
        Boolean cleanRun = false;
        Boolean removeParenth = false;
        String innerStr;
        String tempString;
				
		//1. first remove unnecessary empty parenthesis
		str = str.replace(" ", "");
		str = str.replace("()", "");
		str = str.replace(")(", ")*(");
		str = str.replace(")|", ")*|").replace("|(", "|*(").replace("||", "|*|");
		str = str.replace("**", "^");

		//digit fixing
		str = str.replace("sqrt", "√");
		str = str.replace("π", "3.14159");
		str = str.replace("pi", "3.14159");
		
		//1. removing trailing all but parenthesis
		for(int x=0; x<allButP.length;x++) {
			if(str.contains(allButP[x])) {
				str = removeTrailingStr(str, allButP[x]);
			}
		}
		for(int x=0; x<emda.length;x++) {
			if(str.contains(emda[x])) {
				str = removeLeadingStr(str,emda[x]);
			}
		}
		
		
		//2. inserting parenthesis where needed
		for(int x=0; x<nonPemdas.length;x++) {
			if(str.contains(")"+nonPemdas[x])) {
				//nonpemdas next to parenthesis
				str = str.replace(")"+nonPemdas[x], ")*"+nonPemdas[x]);
			}
			for(int y=0; y<digits.length;y++) {
				if(str.contains(digits[y]+nonPemdas[x])) {
					//digit next to nonpemdas 9sin 
					str = str.replace(digits[y]+nonPemdas[x], digits[y]+"*"+nonPemdas[x]);
				}else if(str.contains(digits[y]+"(")||str.contains(")"+digits[y])) {
					//digit next to parenthesis 9(10-1)8
					str = str.replace(digits[y]+"(", digits[y]+"*(");
					str = str.replace(")"+digits[y], ")*"+digits[y]);
				}
			}
		}
		
		/* Good strings to test: 
		 * "(7)(8)(9)", "(1-(sin(10)))","(10.1)"
		 * (sin7*sin8)sin9, (1-sin8)-1(1-1)
		 * ((8)-1)
		 * (1-(8)-1)
		 * Below is a continuation of 2 and 2.1
		 */
		
		
		for(int x=0, pCount = countChars(strReducer,'('); x<pCount; x++) {
			//using this to check innermost P content for emdas and area right before for
			//nonPemdas
			if(doesStrContainArr(innerPContent(str, x),emdas)
					&&//need to work on prior checking part
					doesStrContainArr(innerPContent(str, x),nonPemdas,str.indexOf
							(innerPContent(str, x))-6,str.indexOf
									(innerPContent(str, x)))) {
				
				
				
				
			}
			
			
			
			
			
			
			
			
			
			
			
		}
		
		
		/*
	tempString = str;
	strReducer = str;
        for(int x=0,pCount = countChars(strReducer,'('); x<pCount;x++) {
        	inBetwStr = "";
        	lastParenthContent=strReducer.substring(strReducer.lastIndexOf("(")+1,
            		strReducer.lastIndexOf("(")+strReducer.substring(strReducer
    					.lastIndexOf("("),
    					strReducer.lastIndexOf("(")+strReducer.substring(strReducer.
    							lastIndexOf("("), strReducer.length()).indexOf(")")).length());
        	

        	if(tempString.contains(inBetwStr+"("+lastParenthContent+")")) {
            	tempString = replaceLast(tempString,inBetwStr+"("+lastParenthContent+")", "");
        	}else if(tempString.contains("("+lastParenthContent+")"+ inBetwStr)) {
            	tempString = replaceLast(tempString,"("+lastParenthContent+")"+ inBetwStr, "");
        	}else {
            	tempString = replaceLast(tempString,"("+lastParenthContent+")", "");
        	}
        	
        	//collecting in between stuff if it exists after
        	//(1-sin8)-1(1-1)
        	//
        	if(tempString.length()>0&&
        			tempString.charAt(tempString.length()-1)!=')'&&!checkForPriorStr(tempString,")",
        			"(",tempString.length()).equals("")){
        		inBetwStr = checkForPriorStr(tempString,")",
            			"(",tempString.length());
        		tempString = replaceLast(tempString, inBetwStr, "");
        	}
        	

        	
        	//strReducer = tempString;
        	
        	//checking for preemptive nonPemdas
        	for(int nopem = 0; nopem<nonPemdas.length;nopem++) {
        		removeParenth=true;
        		//testing for preemptive nonPemdas here
            	if(strReducer.length()-lastParenthContent.length()-4-nonPemdas[nopem].length()>0
            			&&strReducer.substring(strReducer.length()-lastParenthContent.length()-4
            					-nonPemdas[nopem].length(), strReducer.length()-
            					lastParenthContent.length()-4).contains(nonPemdas[nopem])) {
            		removeParenth=false;
            		break;
            	}
        		
        	}
        	//building string here	
        	//(sin7*sin8)sin9 need to make sure that * in between the sin dont get dropped
        	//(1-sin8)-1(1-1)
        	if(removeParenth&&!doesStrContainArr(lastParenthContent,emdas)) {
        		//build without parenthesis if no emdas within and no prior nonPemdas
        		strBuilder =inBetwStr+lastParenthContent + strBuilder;
        	}else if(!doesStrContainArr(strReducer.substring(1, 
					2),emdas)&&
        			!doesStrContainArr(strReducer.substring(strReducer.length()-2, 
        					strReducer.length()-1),emdas)&&
        			strReducer.substring(strReducer.length()-1, strReducer.length()).equals(")")){
        		//else, rebuild with parenthesis
        		strBuilder =  inBetwStr + "(" + lastParenthContent +")" + strBuilder;
        	}else if(doesStrContainArr(strReducer.substring(1, 
					2),emdas)) {
        		strBuilder =  inBetwStr + "(" + strBuilder + lastParenthContent +")" ;
        	}
        	
        	else {
        		strBuilder =  inBetwStr + "(" + lastParenthContent + strBuilder +")";
        	}
        	//replacing to avoid infinite recursion
        	
        	strReducer = replaceLast(strReducer,inBetwStr+"("+lastParenthContent+")", "");
        	
        }
        
        if(!tempString.equals("")) {
        	//str = str + tempString;
        }
        
        if(!strBuilder.equals("")&&strReducer.equals("")) {
            str = strBuilder;
        }else if(!strBuilder.equals("")&&!strReducer.equals("")) {
        	ogStr = ogStr.replace(strReducer, "");
        	//ogStr set to what was originally changed in str
        	
        	//str.substring(str.indexOf(ogStr), str.indexOf(ogStr)+ogStr.length());
        	
        	//str = str.replaceLast(ogStr,strBuilder);
        	str = replaceLast(str, ogStr, strBuilder);
        	//str = str.replace("","";)
        			//include part that replaces what was in str to new organized form
        	
        }

        //3. inserts parenthesis before and after number following nonPemdas functions, 
        //tempString will occupy the number within
        cleanRun = false;
        
        while(!cleanRun) {
        cleanRun = true;

       	for(int y=0; y<nonPemdas.length;y++) {
       		
       		for(int x=0; x<digits.length;x++) {
        	
                if(str.contains(nonPemdas[y]+digits[x])&&cleanRun) {
                	
                	
                	for(int i=str.lastIndexOf(nonPemdas[y]+digits[x])+nonPemdas[y].length()+0
                			, iniaIndex = i; i<str.length();i++) {
                		tempString = str.substring(i, i+1);
                		if(!doesStrContainArr(tempString,digits)){
                			sopln("Parenthesis needed to be add to str:"+
                					str.substring(
                					str.lastIndexOf(nonPemdas[y]+digits[x])+
                					nonPemdas[y].length(), i));
                			//if(i==str.length()-1) {
                    			tempString = str.substring(
                    					str.lastIndexOf(nonPemdas[y]+digits[x])+
                    					nonPemdas[y].length(), i);
                			//}
                			//make substring replacement work here, replacing for sin8to sin(8)
                			str = replaceLast(str,nonPemdas[y]+tempString,
                					nonPemdas[y]+"("+tempString+")");
                			cleanRun = false;
                			y=nonPemdas.length-1;
                			x=digits.length-1;
                			break;
                		}else if(i==str.length()-1-countChars(str.substring(
                				str.lastIndexOf(nonPemdas[y]+tempString),
                				str.length()),')')) {
                 			sopln("Parenthesis needed to be add to str:"+
                					str.substring(
                					str.lastIndexOf(nonPemdas[y]+digits[x])+
                					nonPemdas[y].length(), i));
                			str = replaceLast(str,nonPemdas[y]+tempString,
                					nonPemdas[y]+"("+tempString+")");
                			cleanRun = false;
                			y=nonPemdas.length-1;
                			x=digits.length-1;
                			break;
                			
                		}
                		
                	}
                	
                }
        	}
       		
        	if(y==nonPemdas.length-1&&!cleanRun) {
        		//if at the end, we've ran it once, then run again until a cleanRun occurs
                cleanRun = false;

        	}
        }
        }
        */
        
        
        
        

		sopln("After cleaning:" + str);

		return str;
	}
	
	public static Boolean doesStrContainArr(String str, String[] strArr, int beginIndex,
			int endIndex) {
		
		
		return false;
	}
	
	private static void setCommandGrouping(String[] strArr,String str) {
		indivComGrouping.put(str, strArr);
	}
	
	
	public static String removeTrailingStr(String str, String target) {
		if(str.length()>target.length()&&
				str.substring(str.length()-target.length(),str.length()).equals(target)) {
			//checking last bit of string
			str=str.substring(0,str.length()-target.length());
		}
		return str;
	}
	
	public static String removeLeadingStr(String str, String target) {
		//checking first bit
		if(str.substring(0, target.length()).equals(target)) {
			str=str.substring(target.length(),str.length());
		}
		
		
		return str;
	}
	
	
	public static String insertString(String str, String target, int index) {

		String strBuilder = "";
		for(int x=0; x<str.length();x++) {
			if(index==0&&x==0) {
				strBuilder = target;
			}
				strBuilder = strBuilder+str.charAt(x);

			if(index!=0&&x+1==index) {
				//includes indexes 1 over length to be inserted at end
				strBuilder = strBuilder+target;
			}
		}
		return strBuilder;
	}
	public static String[] addStringToArr(String[] strArr, String str, int index) {
		String[] newArr = new String[strArr.length+1];
		
		for(int x=0, y=0; x<newArr.length;x++) {
			if(x==index) {
				newArr[x] = str;
			}else {
				newArr[x] = strArr[y];
				y++;
			}
		}
		return newArr;
	}
	
	public static String[] subArr(String[] strArr, int indexBegin, int indexEnd) {
		if(indexEnd>indexBegin) {
			String[] newArr = new String[indexEnd-indexBegin];
			for(int x=0,y=indexBegin; x<indexEnd-indexBegin;x++) {
				newArr[x] = strArr[y];
				y++;
			}
			return newArr;
		}else {
			return null;
		}
	}
	
	public static String innerPContent(String str, int i) {
	//i is the index of parenthesis (it counts up from 0 starting from innermost one)
		int strLastI = str.lastIndexOf("(");
		String lastPContent = str.substring(str.lastIndexOf("("),str.lastIndexOf("(")
				+str.substring(strLastI,str.length()).indexOf(")")+1);
				//need to make this
		
		
		
		
		
		sopln(lastPContent);		
		
		
		return str;
	}
	
	public static void setUI(String str) {
		userInput=str;
	}
	/* The two ways to swap a scene is by using the stages below. 
	 * If you want to create a new scene,
	 * use the second one. else, have root, stage, and scene static at top and use first one.
	 * stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	 * Stage stage = new Stage();
	 */
	//new command is OpenWindow(controlPanel) , formerly Action(controlPanelOpen)


	public static String[] getListOfCommands() {
		return listOfCommands;
	}

	public static String[] getCommandGroupingKeys() {
		tempInt = 0;
		String[] strArr = new String[indivComGrouping.keySet().size()];
		for(String s:indivComGrouping.keySet()) {
			strArr[tempInt] = s;
			tempInt++;
		}
		return strArr;
	}
	
	public static String[] getCommandGroupingValue(String str) {
		return indivComGrouping.get(str);
	}
	
	public static void sopln(String str) {
		System.out.println(str);

	}
	
	public static void sopln() {
		System.out.println("");

		
	}
	
	public static void sop() {
		System.out.print("");
		
	}
	
	public static void sop(String str) {
		System.out.print(str);

	}

	
}//useful for memory https://www.w3spoint.com/filereader-and-filewriter-in-java

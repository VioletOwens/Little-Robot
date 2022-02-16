import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
	
/**
 * @author Violet Owens
 */
public class Brain{
	
	private String userInput;
	static String directory = "";
	static String tempString = "";//used liberally, able to be used as a temporary string 
	static private String[] CategoryManagerPanelLastActionInfo = {""};
	
	
	//these need to be removed through code revision
	
    //need to make a status manager
    //need to also make a command manager
	//fix | problem in interpretInput at top
	//fix button swapping ability in control panel
	/* 
	 * can choose to swap windows with a unique subWindow full of the buttons, including back
	 * which would reopen original control panel window
	 */
    static String[] listOfStatuses = {"Any","Normal", "WILD!"};

	
    
    //need to write the stuff below
    static String response = "";
    
    
	//need to figure a way to store this information in another accessible java file?
	//group of lists for brain to use below.
    static String[] UIUnderstanding = {""};
    static String[] UnknownPartsOfUI = {""};
    static String[] listOfPhrases = {""};
    static String[] listOfCategories = {""};
    static String[] listOfCommandPhrases = {""};
    static String[] fullListOfFiles = {""};
    static String[] listOfPhraseFileNameCombo = {""};
    static String[] listOfCategoryFileNameCombo = {""};
    static String[] filesToIncludeInSearches = {""};
    static String[] filesToExcludeFromSearches = {""};
    static String[][] listOfCommandLinesByCategory = {{""}};
    static String[][] ListOfPhrasesCommandsAndTheirCategories = {{""}};
    static String[][] listOfCategoriesAndTheirPhrases = {{""}};
    
    
    
	Brain(String userInput) throws IOException {
		buildDirectory();
		updateLists();
		setUserInput(userInput);
		interpretInput(userInput);
		formulateResponse();
		respondToUser();
		
		//remove this for sure later
		if(UIUnderstanding!=null&&UIUnderstanding[0].equals("OpenWindow(ControlPanel.fxml)")) {
			//perhaps a better way exists to call open control panel
			//nvm this method of opening is perfect :)
			//Controller controller = new Controller();
			//controller.openControlPanel();

			
			Parent root = FXMLLoader.load(getClass().getResource("ControlPanel.fxml"));
			Stage stage = new Stage();
			Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.setTitle("Control Panel");
	        stage.show();
		}else {
			System.out.println("UIUnderstanding[0]="+UIUnderstanding[0]);

		}
	}
	
	private static void interpretInput(String UI) throws FileNotFoundException {
		/* should be versatile and allow for user input of any type of thing in file,
		 * examples may include wanting to break sentences down to adjective, noun, verb etc. 
		 * compared to recognizing whole sentences/phrases 
		 * 
		 * best way to accomplish this would be to include
		 * checkIfInfoFromFile(keywordFileName,part of UI), attempt word by word, back to front?
		 */
		UI = UI.toLowerCase().replace("'", "").trim();
		String[] UIByWord = UI.split(" ");
		String[] unknownStrings = new String[1];//every time a string is unknown it is added
		String[] resultingUnderstanding = new String[UIByWord.length];
		String[] operands = {"+","-","*","/","sin","cos","tan"};
		//commands should already be apart of phraseList,so need to make sure it is included
		//also make sure commands arent peaking through in the categoryManager and categorygroup
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
		if(UI.contains("|")) {
			UnknownPartsOfUI[0]=UI;
			//this needs to be able to disallow UI to be entered due to the "|"
			//and warn the user because of it
			
			//delete portion below this except for return statement to pretty up
			unknownStrings[0]=UI;
			if(resultingUnderstanding!=null) {
				System.out.println("The understanding of UI is as follows:");
				resultingUnderstanding = removeNullInArray(resultingUnderstanding);
				for(int x = 0; x<resultingUnderstanding.length;x++) {
					System.out.println(resultingUnderstanding[x]);
				}
				}
				unknownStrings = removeNullInArray(unknownStrings);
				if(unknownStrings!=null) {
					System.out.println("The unknown parts of UI is as follows:");
					for(int x = 0; x<unknownStrings.length;x++) {
						System.out.println(unknownStrings[x]);
					}	
				}
			
			return;
		}
		unknownStrings = new String[UIByWord.length];
		if(!doesStrContainArr(UI, operands)) {
		if(UI.contains(" ")) {//multiple words
			/*can just search through listOfCategoriesAndTheirPhrases for the phrase
			 * first checking all words together, then slowly taking it apart from the end
			 * until only beginning word left (the left-most word) if not able to be found.
			 * if found though, remove that portion of the string, trim(), then
			 * loop in trying to understand string. if we really can't understand down to the
			 * word itself, add it to compendium of unknown words/gibberish, remove from string,
			 * then continue trying to understand the rest of the string. repeat until nothing
			 * left. if words are in a continuous row that we don't understand, append
			 * the new words to the end of the last one with a space, else move to next index
			 * 
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
								System.out.println("the number of words we know is:" +
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
						//numberOfSkippedWords will be 1+ always
						//need the number of words that we previously don't know
						Boolean[] tempArr = removeNullInArray(UIByWordBoolean);
						int numberOfUnknown=0;
						for(int x=0; x<tempArr.length;x++) {
							if(!tempArr[x])numberOfUnknown++;
						}
						for(int x=0;x<numberOfSkippedWords-numberOfUnknown;x++) {
						UIByWordBoolean[boolCtr] = true;
						boolCtr++;
						}
					}else if(outerIndex+1==FullList.length
							&&numberOfSkippedWords==backwardCounter) {
						UIByWordBoolean[boolCtr] = false;
						boolCtr++;
					}
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
		if(resultingUnderstanding!=null) {
		System.out.println("The understanding of UI is as follows:");
		resultingUnderstanding = removeNullInArray(resultingUnderstanding);
		for(int x = 0; x<resultingUnderstanding.length;x++) {
			System.out.println(resultingUnderstanding[x]);
		}
		}
		unknownStrings = removeNullInArray(unknownStrings);
		if(unknownStrings!=null) {
			System.out.println("The unknown parts of UI is as follows:");
			for(int x = 0; x<unknownStrings.length;x++) {
				System.out.println(unknownStrings[x]);
			}	
		}
		for(int x=0; x<ListOfPhrasesCommandsAndTheirCategories.length;x++) {	
			for(int y=0; y<ListOfPhrasesCommandsAndTheirCategories[x].length;y++) {
				/*System.out.println("ListOfPhrasesCommandsAndTheirCategories[" + x + "]["
						+y+"] is:"+ListOfPhrasesCommandsAndTheirCategories[x][y]);
			*/
			}
		}
		}else if(!doesStrContainNonDigitsOrLetters(UI)&&doesStrContainArr(UI, operands)){
			System.out.println("We dont know how to deal with operands yet :(");
		}else {
			System.out.println("We dont know how to deal with whatever that is yet :(");
		}
		
		UIUnderstanding = resultingUnderstanding;
		UnknownPartsOfUI=unknownStrings;
		/*
		keywordOrganizer(UI);//organizes keyword list into suitable matching
		identifySentenceStructure(UI);//comprehending the string
		organizeSentStructArr();//used to reduce clutter in this method
		String response = responseCenter(shortSentenceStructArr);
		if(!response.equals(""))System.out.println("Response formed, that being:" + response);
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
		if(nullessArr.length<2&&nullessArr[0]==null) {
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
	
	public static String stringArrToString(String[] arr, int startingIndex) {
		String wholeFile = "";
		
		for(int x=startingIndex; x<arr.length;x++) {
			if(!arr[x].equals("")&&!(arr[x].contains("]")||arr[x].contains("["))) {
			if(x<=0) {
				wholeFile="[" + arr[x] + "]";
			}else {
				wholeFile = wholeFile + "\n"+ "[" + arr[x] + "]";
			}
		}else {
			if(x<=0) {
				wholeFile=arr[x];
			}else {
				wholeFile = wholeFile + "\n"+ arr[x];
			}
		}
		}
		return wholeFile;
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
	
	public static String replaceFirstChar(String str, char target, String replacement) {
		for(int x=0;x<str.length();x++) {
			if(str.charAt(x)==target) {
				if(x==0) {
					return replacement + str.substring(1, str.length());
				}else {
					return str.substring(0, x-1) + replacement + str.substring(x+1
							,str.length());
				}
			}
		}
		return str;
	}
	
	public static int firstIndexOfChar(String str, char target) {
		for(int x=0;x<str.length();x++) {
			if(str.charAt(x)==target) {
				return x;
			}
		}
		return -1;
	}
	
	public static String[] sortStringArr(String target, String[] arr) {
    	//test whole file arr against what is in search bar to know what to display
		//return string arr of files to be displayed
		String[] newArr = new String[arr.length];
		int counter = 0;
		for(int i=0; i<arr.length;i++) {
			if(arr[i].contains(target)) {
				newArr[counter]=arr[i];
				counter++;
			}
		}
		newArr = removeNullInArray(newArr);
		return newArr;
	}
	
	public static void updateLists() throws IOException {
		String[] categoryList = null;
		String[] phraseList = null;
		String[] commandCategoryList = null;
		String[] commandPhraseList = null;
		String[][] commandFileList=null;
		String[][] phrasesCommandsAndTheirCategoriesList = null;
		String[] categoryFileNameComboList = null;
		String[] phraseFileNameList = null;
		String[][] phraseAndCategoryList = null;
		String[] fileNameList = null;
		String[] tempStringArr=null;
		String[] excludedFileNameList = {
				"keywords.txt","filelist.txt","categorygrouping.txt","inputresponsegrouping.txt"
				,"commands.txt"};//i know this is hardcoded im trying to make a manager for it
		String line = "";
		String tempString="";
		int tempCtr = 0;
		int indexCtr=0;
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
        	System.out.println("THE PHRASE FILES ARE GONE!!!");
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
		    		//System.out.println("tempString is: " + tempString);
					phraseList[z] = line.substring(0, line.indexOf('|'));
					phraseFileNameList[z]=line.substring(0, line.indexOf('|'))
							+ "//" + fileNameList[x];
					z++;
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
					if(line.substring(line.indexOf("|")+1,line.length())
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
					if(tempString.substring(tempString.indexOf("|")+1,tempString.length())
							.equals(categoryList[x])&&
							tempString.substring(0,tempString.indexOf("|")).length()>0) {
						phraseAndCategoryList[x][z+1]=tempString.substring(0, tempString.indexOf("|"));
						z++;
					}
				}
        	}
            }
			sc.close();
            //need to write commandPhraseList
    		file = new File(directory + "commands.txt");
			sc = new Scanner(file);
			tempCtr=0;
			while(sc.hasNextLine()) {
				line = sc.nextLine();
				commandPhraseList[tempCtr]=line.substring(0,line.indexOf('|'));
				tempCtr++;
			}

            //assigning phrasesCommandsAndTheirCategoriesList
            phrasesCommandsAndTheirCategoriesList = 
            		stackArrays(commandFileList,phraseAndCategoryList);
            
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
	
	public static String toWholeFileAroundCategories(String fileName) throws FileNotFoundException {
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
	
	public static String toWholeFileAroundPhrasesAndCategories(String fileName) throws FileNotFoundException{
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
				||!str.replace(str.substring(firstIndexOfChar(str,'['),str.lastIndexOf("]")+1)
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
			 *  System.out.println("action is:" + action + "\n" +
				"location is:" + location + "\n" +
				"string1 is:" + str1 + "\n" +
				"string2 is:" + str2 + "\n");
			 */
		}
	}
	
	public static String[] getCategoryManagerPanelLastActionInfo() {
		return CategoryManagerPanelLastActionInfo;
	}
	
	public static void buildDirectory() throws IOException {
		//this method is very versatile and wont create extra files if they already exist.
		//extra files are expected to be added here, namely the strings located
		//in filesToExcludeFromSearches, and remove an aspect of hard coding in this code
			File thisFile = new File(Brain.class.getName()+".java");
			String directoryPath = thisFile.getAbsolutePath().replace(
					thisFile.getName(), "");
			//System.out.println("Directory being set, it is:" + directoryPath);
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
	
	private void setUserInput(String userInput) {
		this.userInput = userInput;
	}
	
	public static Boolean doesStrContainArr(String str, String[] arr) {
		for(int x=0; x<arr.length;x++) {
			if(str.contains(arr[x])) {
				return true;
			}
		}
		return false;
	}
	
	public static Boolean doesStrContainDigitOrLetter(String str) {
		for(int x=0; x<str.length();x++) {
			if(Character.isLetterOrDigit(str.charAt(x))) {
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
	
	public static Boolean doesStrContainNonDigitsOrLetters(String str) {
		Character c;
		for(int x=0; x<str.length();x++) {
			c=str.charAt(x);
			if(!Character.isLetterOrDigit(c)&&!Character.isWhitespace(c)) {
				return true;
			}
		}
		return false;
	}

	public static Boolean isExpressionOnlyElementary(String str) {
		/*supposed to tell whether expression only contains elementary functions such as
		 * constants and variables, sin, sqrt, log, any operands that work with variables, etc.
		 * https://en.wikipedia.org/wiki/Elementary_function
		 */
		return false;
	}
	
	private static String solveExpression(String str) {
		//supposed to intake any expression, assuming it is able to be processed
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
		
		
		return "";
	}
	
	private static String respondToUser() {
		
		
		
		return "";
	}
	
	//new command is OpenWindow(controlPanel) , formerly Action(controlPanelOpen)
	
}//useful for memory https://www.w3spoint.com/filereader-and-filewriter-in-java

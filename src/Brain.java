import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
	
/**
 * @author Violet Owens
 */
public class Brain{
	
	private String userInput; 
	final String fileListFileName = "filelist.txt";
	final static String keywordFileName = "C:\\Users\\chris\\Desktop\\CS\\CS Software\\Workspace\\Little Robot\\phrases\\keywords.txt";
	final static String directory = "C:\\Users\\chris\\Desktop\\CS\\CS Software\\Workspace\\Little Robot\\phrases\\";
	static String tempString = "";
	static String[] longSentenceStructArr = null;
	static String[] shortSentenceStructArr = null;
	static String[] commandArray = {"Action"};
    static String[] listOfStatuses = {"Any","Normal", "WILD!"};
    static String[] listOfCategories = {""};
    static String[] listOfPhrases = {""};
    static String[][] listOfCategoriesAndTheirPhrases = {{""}};
    static String[] listOfFiles = {""};
    static String[] listOfFileNames = {""};
    static String[] listOfPhraseFileNameCombo = {""};
    static String[] listOfCategoryFileNameCombo = {""};
    static String[] filesToIncludeInSearches = {""};//not currently set up
    static String[] filesToExcludeFromSearches = {""};
	Brain(String userInput) throws IOException {
		updateLists();
		this.userInput = userInput;		
		interpretInput(userInput);
	}
	
	private static void interpretInput(String UI) throws FileNotFoundException {//attempts to comprehend the user input
		//check if each string input is equal to anything from keyword list
		//first check each word in string for keyword/phrases. when found,
		//check if following/prior part is anything that we know in files
		//if not, say "I do not know anything about that. Should I?"
		//checkIfInfoFromFile(keywordFileName,part of UI);//trying word by word
		UI = UI.toLowerCase().replace("'", "");//avoids a lot of trouble to lowercase and ' here
		String[] UIWords = UI.split(" ");
		longSentenceStructArr = new String[UIWords.length + 1];//need to make this
		keywordOrganizer(UI);//organizes keyword list into suitable matching
		identifySentenceStructure(UI);//comprehending the string
		organizeSentStructArr();//used to reduce clutter in this method
		String response = responseCenter(shortSentenceStructArr);
		if(!response.equals(""))System.out.println("Response formed, that being:" + response);
	}
	
	private static void organizeSentStructArr() {
		int counter = 0;
		for(int y=0; y<longSentenceStructArr.length;y++) {
			if(longSentenceStructArr[y]==null) {
				break;
			}
			counter++;
		}
		shortSentenceStructArr = new String[counter];
		for(int x=0;x<shortSentenceStructArr.length;x++) {
			shortSentenceStructArr[x]=longSentenceStructArr[x];
			//System.out.println("shortSentenceStructArr of " + x + " is:" + shortSentenceStructArr[x]);
		}		
	}
		
	private static void keywordOrganizer(String UI) {
		//reads through file first to get number of lines,
		//then make array of exact size, assigning each line to array index
		//then make simple sorting algorithm to sort in order of these variables:
		//first, organize the arrays in order of first character in UI,
		//then longest string (cant decide between word count or string length but latter easier)
		//so we try length of string
		
		//making array of array of strings, the outer array with names of file
		//and inner arrays are list of strings 
		
		File file = new File (keywordFileName); 
		Scanner firstScanner = null;
		Scanner secondScanner = null;
		Boolean hadToSort = false;
		String wholeFile= "";
		String[] tempArr;
		int firstCounter = 0;
		try  { 
			firstScanner =  new  Scanner (file);

			// Read the file line by line 
			while  (firstScanner.hasNextLine())  { 
					firstScanner.nextLine();  	 
					firstCounter++;
			}
			firstScanner.close();
			secondScanner = new Scanner(file);
			tempArr = new String[firstCounter];
			firstCounter=0;
			while(secondScanner.hasNextLine()) {//simply add all lines to array of perfect size
				tempArr[firstCounter]=secondScanner.nextLine();
				firstCounter++;
			}
			/*
			for(int xy=0; xy<tempArr.length;xy++) {
				System.out.println("before: tempArr[" + xy + "]=" + tempArr[xy]);
			} //great for troubleshooting
			*/
			firstCounter=0;
			hadToSort=true;
			String currentString="";
			String nextString="";
			while  (hadToSort)  { //will be sorting while for tempArr
				hadToSort=false;
				for(int x=0; x<tempArr.length;x++) {
				currentString=tempArr[x];
				if(!(x+1==tempArr.length)) {//to make sure we arent at end of array
					nextString=tempArr[x+1];
				if(currentString.charAt(0)==UI.charAt(0)
					&&nextString.charAt(0)==UI.charAt(0)
					&&currentString.substring(0,currentString.lastIndexOf("/")).length()
					<nextString.substring(0,nextString.lastIndexOf("/")).length()) {
					//next line and this line have same first char, sort by length
					tempArr[x]=nextString;
					tempArr[x+1]=currentString;
					hadToSort=true;
				}else if(currentString.charAt(0)!=UI.charAt(0)
						&&nextString.charAt(0)!=UI.charAt(0)
						&&currentString.substring(0,currentString.lastIndexOf("/")).length()
						<nextString.substring(0,nextString.lastIndexOf("/")).length()) {
					//if nextline and this line dont have same first char, sort by length
					tempArr[x]=nextString;
					tempArr[x+1]=currentString;
					hadToSort=true;
				}else if(currentString.charAt(0)!=UI.charAt(0)
						&&nextString.charAt(0)==UI.charAt(0)) {
					//if nextline has the first char but this line doesnt, swap.
					tempArr[x]=nextString;
					tempArr[x+1]=currentString;
					hadToSort=true;
				}
				}//end of if checking if at end of array
				}//end of for
			}//end of while
			/*
			for(int u=0; u<tempArr.length;u++) {
				System.out.println("after: tempArr[" + u + "]=" + tempArr[u]);
			} //great for troubleshooting
			*/			
			//System.out.println("After sorting, tempArr contents:");
			for(int x = 0; x<tempArr.length; x++) {
					if(!(x+1==tempArr.length)) {
						wholeFile=wholeFile.concat(tempArr[x]+"\n");
					}else{//doesn't append last line with a newLine.
						wholeFile=wholeFile.concat(tempArr[x]);
					}
				}
			//stringArr completely sorted, now can reassign it to the file
		      FileWriter output = new FileWriter(directory + "keywords.txt");
		      output.write(wholeFile);
		      output.close();
		      
		    //need to mainly make a category list that corresponds with the subsequent
		    //necessary response. Each category/phrase should have at least 1 response
		    //doing all this via the extra window to open and create
		    //call this interface category manager
		      
		      
		    //can add automatic detection system that detects if keywords contains a filename
		    //not in directory, can create new file with this info in it, but can't
		    //have categories since its auto generated (include that word
		    //and understanding of it (category) is "Unknown" with warning in file)
		    //maybe also implement warning ability into the response (maybe when asked about 
		    //status!!)
		    //can also implement ability to check directories for files not contained in
		    //keywords.txt, and add them if the format checks out.



		      if (firstScanner!=null) firstScanner.close (); 
		      if (secondScanner!=null) secondScanner.close();
		}  catch (Exception ex)  { 
			ex.printStackTrace();
			}
	}
	
	private static void generalOrganizer(String fileName, String UI) {
		//used to organize most files and clean up any messes, if any.
		if(!fileName.contains(".txt")){//if it doesnt include .txt, append it at end.
			fileName = fileName + ".txt";
		}
		File file = new File (directory + fileName); 
		String[] stringArr;
		String wholeFile="";
		String currentLine = "";
		String nextLine = "";
		Boolean didWeSort = false;
		int counterOne=0;
		Scanner scannerOne = null ;
		Scanner preempScanner = null;

		
		try  { 
			preempScanner = new Scanner(file);
			while(preempScanner.hasNextLine()) {//while to preemptively clean file and count lines
				String line = preempScanner.nextLine();
				if(line.length()<1) {
					//skip line if first char isnt digit or letter (empty)
				}else {
				if(!line.contains("|")) {
					//if the line doesnt contains | 
					line = line.concat("|Unknown");
				}else if(line.substring(0, line.indexOf("|")).length()
						==line.length()) {
					//since line contains |, there must not be text past it
					line = line.concat("Unknown");					
				}
				counterOne++;
				if(preempScanner.hasNextLine()) {//mechanism to build wholeFile
				wholeFile=wholeFile+line+"\n";
				}else {
					wholeFile=wholeFile+line;
				}
			}
			}//end of while
		    FileWriter output = new FileWriter(directory + fileName);
		    output.write(wholeFile);
		    output.close();
		      
			stringArr = new String[counterOne];			
			scannerOne =  new  Scanner (file);
			counterOne=0;
			while  (scannerOne.hasNextLine())  { 
				stringArr[counterOne]=scannerOne.nextLine();
				counterOne++;
			}
			//below, stringArr content is sorted in this order: exact copy, 
			//same first char (as UI) second by length, different first char second by length
			counterOne=0;
			didWeSort=true;
			while(didWeSort) {
				didWeSort=false;
				for(int x=0; x<stringArr.length;x++) {
				currentLine = stringArr[x];
				if(!(x+1==stringArr.length)) {
					nextLine = stringArr[x+1];

				if(currentLine.substring(0,currentLine.indexOf("|")).equals(UI)) {
					//if very first is matching word, leave it alone!
				}
				else if(nextLine.substring(0,nextLine.indexOf("|")).equals(UI)) {
					stringArr[x]=nextLine;
					stringArr[x+1]=currentLine;
					didWeSort=true;
				}else if(nextLine.charAt(0)==UI.charAt(0)
						&&currentLine.charAt(0)==UI.charAt(0)
						&&nextLine.substring(0,nextLine.indexOf("|")).length()
						>currentLine.substring(0,currentLine.indexOf("|")).length()) {
					stringArr[x]=nextLine;
					stringArr[x+1]=currentLine;	
					didWeSort=true;
				}else if(nextLine.charAt(0)!=UI.charAt(0)
						&&currentLine.charAt(0)!=UI.charAt(0)
						&&nextLine.substring(0,nextLine.indexOf("|")).length()
						>currentLine.substring(0,currentLine.indexOf("|")).length()) {
					stringArr[x]=nextLine;
					stringArr[x+1]=currentLine;
					didWeSort=true;
				}else if(nextLine.charAt(0)==UI.charAt(0)
						&&currentLine.charAt(0)!=UI.charAt(0)) {
					stringArr[x]=nextLine;
					stringArr[x+1]=currentLine;
					didWeSort=true;
				}
				
			}//end of if testing if at last index of array
			}//end of for of stringArr.length
			}//end of if checking if were at end of array
			wholeFile = "";
			for(int x=0; x<stringArr.length;x++) {
				if(x+1!=stringArr.length) {
					wholeFile = wholeFile + stringArr[x] + "\n";
				}
				else {
					wholeFile = wholeFile + stringArr[x];
				}
			}
		    FileWriter outputTwo = new FileWriter(directory + fileName);
		    outputTwo.write(wholeFile);
		    outputTwo.close();
		      

			if (scannerOne!=null) scannerOne.close(); 
		}  catch (Exception ex)  { 
			ex.getMessage();
		} 
		
	}
		
	private static String identifySentenceStructure(String userInput) throws FileNotFoundException {
		String str = userInput.replace(",", " ").replace("  ", " ").strip();//string priming
		//System.out.println("Input at first in identifySentenceStructure(str) is:" + str);
		String line = "";
		String category = "";
		String tempString="";
		File file = new File (keywordFileName); 
		int tempInt=0;
		String tempFileName = "";
		Boolean found = false;
		try {
		Scanner scannerOne = new  Scanner (file);
		// Read the file line by line 
		while  (scannerOne.hasNextLine()&&!found)  { 
			line = scannerOne.nextLine(); 
			tempFileName=line.substring(line.lastIndexOf("/")+1,line.length());
			line=line.substring(0,line.indexOf("/"));
			tempString=str;
			/*
			System.out.println("looking for match in keyword.txt. "
					+ "tempString is:" + tempString
					+" while corresponding line is:" + line);//very useful for troubleshooting
					*/
			while(tempString!=""&&tempString.contains(line)) {
				if(tempString!=""&&(tempString.equals(line))) {
					found = true;
					break;
				}else if(tempString.contains(" ")){//used to remove last word
						tempInt=tempString.length()-tempString.split(" ")[tempString.split(" ").length-1].length();
						tempString = tempString.substring(0,tempInt-1);
				}
				else {//case of last word, which has alrdy been tested
					tempString="";
				}
		}//end of tempString while
		}//end of outer while
		scannerOne.close();
		line = "";
				//System.out.println("Out of first while (keyword identified)");
				if(found) {//tempFileName contains destination of word and tempString is the word
					generalOrganizer(tempFileName,tempString);//used to organize most files
					file = new File(directory + tempFileName);
				Scanner scannerTwo = new  Scanner (file);
				while  (scannerTwo.hasNextLine()&&str.trim()!="")  { 
					line = scannerTwo.nextLine();  	 
					category = line.substring(line.indexOf("|")+1, line.length());
					line = line.substring(0,line.indexOf("|"));
					if(str.contains(line)) {//if line can be found in string, get category
						str = str.replace(line, "").trim();
						found=false;
						for(int h=0;h<commandArray.length;h++) {//checking for command words
							if(category.contains(commandArray[h])) {
								found=true;
								tempString=commandArray[h];
								switch (tempString){
								case "Action":
									actionCommand(category.substring(
											category.indexOf("(")+1,category.indexOf(")")));
									break;
									
								}//out of switch case
								break;
							}
						}//out of for loop
						if(!found) {//only append category if it IS NOT a command phrase
							longSentenceStructArr=appendToArray(longSentenceStructArr,category);
						}
					}//out of if str.contains(line)
					if(str.replace(" ","")=="") {//if we run out of words
						break;
					}else {
						keywordOrganizer(str);//organize keywords.txt around new str
						//Recursion until entire string is understood
						str = identifySentenceStructure(str);
					}
				}
				scannerTwo.close();
				}
			} catch (IOException e) {
				//System.out.println("ERROR: NO STREAM!! (Gone one too many times"
				//		+ " through recurssion without closing stream and read is attempted)");
				e.printStackTrace();//useful for troubleshooting
			}
	        return str;
	}//end of method
	
	private static void actionCommand(String str) throws IOException {
		Controller controller = new Controller();//used to allow calls to controller methods
		System.out.println("actionCommand method called, str:" + str);
		switch (str){
		case "controlPanelOpen":
			controller.openControlPanel();
			break;
		}
		
	}
	
	private static String responseCenter(String[] sentStructArr) {
		
		return "";
	}
	
	private static String[] appendToArray(String[] arr, String str) {
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
	
	public static String[] sortCustomGroupList(String target, String[] arr) {
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
		 //currentStatusComboBox.getItems().addAll(statusList);
		String[] categoryList = null;
		String[] phraseList = null;
		String[] categoryFileNameComboList = null;
		String[] phraseFileNameList = null;
		String[][] phraseAndCategoryList = null;
		String[] fileNameList = null;
		String[] tempStringArr=null;
		String[] excludedFileNameList = {"keywords.txt","filelist.txt","categorygrouping.txt",
				"categorylist.txt","customcategorygrouping.txt","commands.txt"};
		String[] IncludeFileForSearches = {""};
		String tempString = "";
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
        if (children == null)System.out.println("THE PHRASE FILES ARE GONE!!!");
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
        			IncludeFileForSearches = new String[children.length-z];
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
        		y = y + countLinesInFile(fileNameList[x]);
        		if(countLinesInFile(fileNameList[x])!=0) {//avoiding empty .txt files
            		z = z + toWholeFileAroundCategories(fileNameList[x]).split("\n").length;
        		}
        		if (x==fileNameList.length-1) {//triggers at the end of the final for run 
                	phraseList = new String[y];
            		phraseFileNameList = new String[y];
            		categoryList = new String[z];
        			categoryFileNameComboList = new String[z];
        		}
        	}

        
        	

			//now finding List of phrases and its variation as well as categories
            for(int x=0, z=0 , i=0; x<fileNameList.length;x++) {
            		file = new File(directory + fileNameList[x]);
					sc = new Scanner(file);

					//this further vets categories for any empty indexes
					for(int y=0;y<toWholeFileAroundCategories(fileNameList[x]).split("\n").length;y++) {
						if(countLinesInFile(fileNameList[x])!=0) {//avoiding empty files
						categoryList[i]=removeNullInArray(
								toWholeFileAroundCategories(fileNameList[x]).split("\n"))[y];
						
						categoryFileNameComboList[i]=
								removeNullInArray(toWholeFileAroundCategories(fileNameList[x])
										.split("\n"))[y] + "|" + fileNameList[x];
						i++;
					}
					}
					
				while(sc.hasNextLine()) {//first find phrase and fileName
					tempString=sc.nextLine();
		    		//System.out.println("tempString is: " + tempString);
					phraseList[z] = tempString.substring(0, tempString.indexOf('|'));
					phraseFileNameList[z]=tempString.substring(0, tempString.indexOf('|'))
							+ "//" + fileNameList[x];
					z++;
				}
	            sc.close();
           }
            
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
					tempString=sc.nextLine();
					if(tempString.substring(tempString.indexOf("|")+1,tempString.length())
							.equals(categoryList[x])&&tempString.substring(0, tempString
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
            
            //now assigning the phrases to the empty array spots
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
            }//CategoryFileNameComboList
            listOfCategoryFileNameCombo = categoryFileNameComboList;
            filesToIncludeInSearches = fileNameList;
            filesToExcludeFromSearches = excludedFileNameList;
            listOfCategoriesAndTheirPhrases=phraseAndCategoryList;
            listOfPhrases = phraseList;
            listOfPhraseFileNameCombo = phraseFileNameList;
    		listOfCategories = categoryList;
    		listOfFileNames=children;
        }
	
	public static int countLinesInFile(String fileName) throws FileNotFoundException {
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
	
	public static Boolean isStringAvailable(String str) {
		//this tests whether a string is already in the system 
		//or whether it can be added without problem
		for(int x=0; x<listOfFileNames.length;x++) {
			if(str.equals(listOfFileNames[x].substring(0, listOfFileNames[x].indexOf(".")-1))) {
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
	//public static String 
	/*
	 * phrase fixer for files below
	 * 
        file = new File (Brain.keywordFileName);
    	scanner.close();
		scanner =  new  Scanner (file);
		tempString="";
		counterOne=0;
        while(scanner.hasNextLine()) {
        	tempString = scanner.nextLine();
        	if(scanner.hasNextLine()) {
            	keywordSuperString = keywordSuperString.concat(tempString) + "\n";
        	}
        	else {
            	keywordSuperString = keywordSuperString.concat(tempString);
        	}
        	counterOne++;
        }
        scanner.close();
        
        //first checking if keywordPhraseFileNameList contains anything that keywords needs
        //then going to check if keywords contains anything we dont know (cant tell in first
        //check)
        for(int x=0;x<keywordPhraseFileNameList.length; x++) {
        	if(!keywordSuperString.contains(keywordPhraseFileNameList[x])) {
    			keywordSuperString =keywordSuperString + 
    					"\n" + keywordPhraseFileNameList[x];
        	}
        }        
	      outputToFile = new FileWriter(Brain.keywordFileName);
	      outputToFile.write(keywordSuperString);
	      outputToFile.close();//applying first contribution to keywords.txt
	        file = new File (Brain.keywordFileName);
			scanner =  new  Scanner (file);
			tempString="";
			keywordSuperString="";//remaking file without oddities 
        	while(scanner.hasNextLine()) {
        		tempString = scanner.nextLine();
                for(int x=0; x<keywordPhraseFileNameList.length;x++) {
                	if(keywordPhraseFileNameList[x].equals(tempString)) {
                		if(!keywordSuperString.equals("")) {
                    		keywordSuperString=keywordSuperString+"\n"+
                    				keywordPhraseFileNameList[x];
                    		break;
                		}else {
                			keywordSuperString=keywordPhraseFileNameList[x];
                			break;
                		}
                	}	
                }
        	}
            	outputToFile = new FileWriter(Brain.keywordFileName);
  	      outputToFile.write(keywordSuperString);
  	      outputToFile.close();//applying second contribution to keywords.txt (removing oddities)
	 * 
	 * 
	 * 
	 */
	
	//private static void errorAttributer() {//infers any errors in userInput	
	//}
	//types of words:greetings, question words, queeries (lol) like hows ur current status,
	//filler,		
	//when special keywords are called, group up info in array containing special	
//in file.txt, can have flag at end of line of trigger to signal collection of rest of info
//so that we can collect information in the commands/triggers (eg. open [insert file name] need
//to know info of file name to open a file!)
//need to also make flag reader in file below (just after it replaces str? and before it assigns final
//category to shortSentenceStructArr, since goal is for category to
//also include the additional info)

	
}//useful for memory https://www.w3spoint.com/filereader-and-filewriter-in-java

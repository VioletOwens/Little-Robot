import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Violet Owens
 */
public class Brain {
	
	 private String userInput; 
	 final String fileListFileName = "filelist.txt";
	 final static String keywordFileName = "C:\\Users\\chris\\Desktop\\CS\\CS Software\\Workspace\\Little Robot\\phrases\\keywords.txt";
	final static String directory = "C:\\Users\\chris\\Desktop\\CS\\CS Software\\Workspace\\Little Robot\\phrases\\";
	static String[] longSentenceStructArr = null;
	static String[] shortSentenceStructArr=null;
	Brain(String userInput) throws FileNotFoundException {
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
		System.out.println("Response formed, that being:" + response);
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
			System.out.println("shortSentenceStructArr of " + x + " is:" + shortSentenceStructArr[x]);
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
		Boolean reverse = false;
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
					}
					longSentenceStructArr=appendToArray(longSentenceStructArr,category);
					if(str.replace(" ","")=="") {//if we run out of words
						break;
					}else {
						keywordOrganizer(str);//organize keywords.txt around new str
						str = identifySentenceStructure(str);
					}
				}
				scannerTwo.close();
				}
			} catch (IOException e) {
				System.out.println("ERROR: NO STREAM!! (Gone one too many times"
						+ " through recurssion without closing stream and read is attempted)");
				//e.printStackTrace();//useful for troubleshooting
			}
	        return str;
	}//end of method
	
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

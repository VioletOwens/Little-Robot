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
	//private static int numOfCategories = 0;//number of categories
	Brain(String userInput) throws FileNotFoundException {
		this.userInput = userInput;		
		interpretInput(userInput);
	}
	
	
	//when special keywords are called, group up info in array containing special
	//keyword
	
//a file of filenames for ordering and storing file names...?
	//could order in an index sort of fashion?
	//different ways to organize said files can be organized in other files containing groups of file names 
	//an example would be: FileListAdject.txt (contains list of adjectives) this itself can be put into lists for
	//complicated flags
	
	//need to conquer cases of editing file line, reading file, appending file, checks file for info,
	//deleting line from file, and deleting file
	
	
	//can organize into language(containing all .txt names of relevant .txts about language and sentence structure)
	
	//can do the word recognition by checking first:
	//send off genFileCleaner(to organize strings longest to shortest)
	//
	//copying string beginning of string to end,
	//then check:beginning of string to end-1 word
	//then check:beginning of string to end-2 words
	//until there are no words left. if none are found, progress beginning of string by 1 word
	//if one is found, replace that part with category and add -> to end (unless no words left)
	//resulting string should contain in order the categories we need to comprehend
	
	private static void interpretInput(String UI) throws FileNotFoundException {//attempts to comprehend the user input
		//check if each string input is equal to anything from keyword list
		//first check each word in string for keyword/phrases. when found,
		//check if following/prior part is anything that we know in files
		//if not, say "I do not know anything about that. Should I?"
		//checkIfInfoFromFile(keywordFileName,part of UI);//trying word by word
		String[] UIWords = UI.split(" ");
		longSentenceStructArr = new String[UIWords.length + 1];//need to make this
		for(int x=0;x<UIWords.length;x++) {//takes out lower case and replaces '
			UIWords[x]=UIWords[x].toLowerCase().replace("'", "");
		}
		
		
		//identifySentenceStructure(UI.toLowerCase()); //main line here undo when rdy
		//String sentenceStructureString;
		keywordOrganizer(UI);
		
		
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
		String response = responseCenter(shortSentenceStructArr);
		
		System.out.println("Response formed, that being:" + response);
		//types of words:greetings, question words, queeries (lol) like hows ur current status,
		//filler,
		
		//when special keywords are called, group up info in array containing special	
		
	//in file.txt, can have flag at end of line of trigger to signal collection of rest of info
	//so that we can collect information in the commands/triggers (eg. open [insert file name] need
	//to know info of file name to open a file!)
	//need to also make flag reader in file below (just after it replaces str? and before it assigns final
	//category to shortSentenceStructArr, since goal is for category to
	//also include the additional info)
	//these flag lines need to be put first with the pre-runner in keywords.txt because the flags are critical 
	}
	
	//private static void errorAttributer() {//infers any errors in userInput	
	//}
		
	
	
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
		Scanner thirdScanner = null;
		Boolean hadToSort = false;
		String tempString = "";
		String wholeFile= "";
		String[][] stringArr;
		String[] tempArr;
		int[] intArr;
		int firstCounter = 0;
		int secondCounter = 0;
		
		
		try  { 
			firstScanner =  new  Scanner (file);

			// Read the file line by line 
			while  (firstScanner.hasNextLine())  { 

				String line = firstScanner.nextLine();  	 
				if(line.contains("//")) firstCounter++;
				//System.out.println(firstCounter + " " + line);    
				
				//if(line)
			}
			firstScanner.close();
			intArr = new int[firstCounter];
			secondScanner = new Scanner(file);
			firstCounter = 0;
			while  (secondScanner.hasNextLine())  { 


				String line = secondScanner.nextLine();  	
				if(line.contains("//")&&firstCounter>1){//at new file, reset counter and increase secondCounter
					intArr[secondCounter] = firstCounter;
					secondCounter++;
					firstCounter=0;
				}else if(line.replace(" ", "")==""||!secondScanner.hasNextLine()){
					firstCounter++;
					intArr[secondCounter] = firstCounter;
					break;
				}
				firstCounter++;
				//System.out.println(firstCounter + " " + line);       
			}
			stringArr = new String[intArr.length][];

			for(int x = 0; x<intArr.length;x++) {
				tempArr = new String[intArr[x]];
				stringArr[x]= tempArr;
			}//stringArr set up to be perfect for file input.
			//string array contains [filename #] [# of lines]
			secondScanner.close();
			thirdScanner = new Scanner(file);
			firstCounter = 0;
			secondCounter = 0;
			//secondcounter is category # firstCounter is line#
			while(thirdScanner.hasNextLine()) {//goes through assigning stringArr with file
				String line = thirdScanner.nextLine();
				//check this line for // for names and otherwise for inner string
				if(line.contains("//")&&firstCounter!=0) {
					firstCounter=0;
					secondCounter++;
					stringArr[secondCounter][firstCounter]=line;
					firstCounter++;
				}else {
					stringArr[secondCounter][firstCounter]=line;
					firstCounter++;
				}
				
				
			}
			System.out.println("stringArr content is:");
			for(int x=0; x<stringArr.length;x++) {
				for(int y=0; y<stringArr[x].length;y++) {
					System.out.println("stringArr[" + x + "]["+ y +"]=" + stringArr[x][y]);
				}
			}
			
			//sorting stringArr content now
			
			tempString = stringArr[0][1];
			//nextString = stringArr[0][2];
			
			//have while that checks if full runthrough is true, full runthrough only triggers
			//true if it can make it through full stringArr without swapping
			
			//in order, we only care about the char at 0 of UI matching,
			//then the length of the string
			hadToSort=true;
			while(hadToSort) {
			hadToSort = false;
			for(int x=0; x<stringArr.length;x++) {
				
				for(int y=1; y<stringArr[x].length;y++) {//starts at 1, since 0 is filename
					
					if(!(y+1>=stringArr[x].length)) {//if we havent reached last index
						//currentString = stringArr[x][y];
						//nextString = stringArr[x][y+1];
						if((stringArr[x][y].charAt(0)==UI.charAt(0))
								&&stringArr[x][y+1].charAt(0)==UI.charAt(0)
								&&stringArr[x][y+1].length()>stringArr[x][y].length()) {
							//if both contain char, swap by length
							tempString = stringArr[x][y];
							stringArr[x][y] = stringArr[x][y+1];
							stringArr[x][y+1] = tempString;
							hadToSort = true;
							
						}else if(stringArr[x][y].charAt(0)!=UI.charAt(0)
								&& stringArr[x][y+1].charAt(0)==UI.charAt(0)) {
							//if next array only matches with char, swap
							tempString = stringArr[x][y];
							stringArr[x][y] = stringArr[x][y+1];
							stringArr[x][y+1] = tempString;
							hadToSort = true;
							
							
							
						}else if(stringArr[x][y].charAt(0)!=UI.charAt(0)
								&&stringArr[x][y+1].charAt(0)!=UI.charAt(0)
								&&stringArr[x][y+1].length()>stringArr[x][y].length()){
							//if neither match first char, order by length
							tempString = stringArr[x][y];
							stringArr[x][y] = stringArr[x][y+1];
							stringArr[x][y+1] = tempString;
							hadToSort = true;
							
						}
					}else {//case where last index is reached
												
					}
				}//end of first for
			}//end of second for
			}//end of while (hadtosort)

			
			System.out.println("After sorting, stringArr contents:");
			for(int x = 0; x<stringArr.length; x++) {
				for(int y =0; y<stringArr[x].length; y++) {
					System.out.println("string Arr[" + x + "][" 
							+ y + "]=" + stringArr[x][y]);
					if(!(x+1==stringArr.length&&y+1==stringArr[x].length)) {
						wholeFile=wholeFile.concat(stringArr[x][y]+"\n");
					}else{//doesn't append last line with a newLine.
						wholeFile=wholeFile.concat(stringArr[x][y]);
					}
				}
			}
			System.out.println("wholeFile is:" + wholeFile);
			
			//stringArr completely sorted, now can reassign it to the file
		      FileWriter output = new FileWriter(directory + "keywords.txt");
		      output.write(wholeFile);
		      output.close();
			
			//replaces the information in the file with string inputted into write.
			//finishes sorting keywords here.
		      
		      
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

		}  catch (Exception ex)  { 
			ex.printStackTrace();
			//System.out.println("Message:" + ex.getMessage()); 
		}  finally  { 
			try  { 
				if (firstScanner!=null) firstScanner.close (); 
				if (secondScanner!=null) secondScanner.close();
			}  catch(Exception ex2)  { 
				ex2.printStackTrace();
				//System.out.println( "Message 2:"  + ex2 . getMessage()); 
			} 
		}
	}
	
	private static void generalOrganizer(String fileName) {
		
		if(!fileName.contains(".txt")){//if it doesnt include .txt, append it at end.
			fileName = fileName + ".txt";
		}
		
		File file = new File (fileName); 
		Scanner s = null ;
		String newFile = "";
		String newLine = "";
		try  { 
			s =  new  Scanner (file);

			// Read the file line by line 
			while  (s.hasNextLine())  { 
				
				
				
				
				
				String line = s.nextLine();  	// We keep the line on a String 
				System.out.println(line);       // We print the line 
			}

		}  catch (Exception ex)  { 
			System.out.println("Message:" + ex.getMessage()); 
		}  finally  { 
			// Close the file whether the reading was successful or not 
			try  { 
				if (s!=null) 
					s.close (); 
			}  catch(Exception ex2)  { 
				System.out.println( "Message 2:"  + ex2 . getMessage()); 
			} 
		}
	}
	
	
	
	private static String identifySentenceStructure(String userInput) throws FileNotFoundException {
		String str = userInput.replace(",", " ").replace("  ", " ").strip();//string priming
		System.out.println("Input at first in identifySentenceStructure(str) is:" + str);
		String wholeFile = "";
		String line = "";
		String newLine = "";
		String sentenceStructure = str;
		String category = "";
		String tempString="";
		Boolean reverse = false;
		int tempInt=0;
		String tempFileName = "";
		Boolean found = false;
		
		FileReader kwFileReader = new FileReader(directory + "keywords.txt");
	        int i;//loop var
	        try {
				while (((i = kwFileReader.read()) != -1)&&!found) {//continues until nothing to read
					if((char)i!='\n'&&i!=13){//avoids newlines (both beginning and end of string)
						line = line + (char)i;
					}else if(line.contains("//")) {//filename identifier
						tempFileName = line.replace("/","");
					}else if(str.contains(line)&&((i==13)||(line.length()==str.length()))){
						wholeFile=wholeFile+"\n"+ line;
						tempString =str;
						
						while(tempString!="") {
							System.out.println("tempString is:" + tempString);
							if(tempString!=""&&(tempString.equals(line))) {
								found = true;
								break;
							}else if(tempString.contains(" ")){//replace last word in string to "" if last word exists
								//can use length of removed word to substring tempString into smaller string, only
								//removing the end of the string.
								//the below int subtracts the length of the total string length by the length of the word,
								if(reverse) {//used to remove first word
									tempInt=Math.abs(tempString.length()-tempString.split(" ")[0].length()-tempString.length());
									tempString = tempString.substring(tempInt,tempString.length()).trim();
								}
								else {//used to remove last word
									tempInt=tempString.length()-tempString.split(" ")[tempString.split(" ").length-1].length();
									tempString = tempString.substring(0,tempInt-1);
								}
							}else if(str.contains(" ")){//last word in tempString, but not in str
								//need to set tempString= to itself-first word, so
								//tempString=str.replaceFirst(tempString, "").strip();
								tempString=str;
								reverse=!reverse;
							}else {//case of last word, which has alrdy been tested
								tempString="";
							}
						}
						System.out.println("just out of while, tempString is:" + tempString);

						line="";
					}
					if(line!=""&&i==13) {
						line="";
					}
				}
				kwFileReader.close();
				
				System.out.println("out of first while");
				FileReader genFileReader = null;
				
				
				if(found) {
					genFileReader = new FileReader(directory + tempFileName);
					
					int g;
					
					System.out.println("almost in second while");
					

					while (((g = genFileReader.read()) != -1)&&str.replace(" ", "")!="") {
						//exclude only in case of noticing word in str, then want to read category
						//so only if str contains newline do we read the category
						if((char)g!='\n'&&g!=13&&g!=124){//avoiding newline begin and newline end 124 is # of |
							newLine = (String)(newLine + (char)g);
							//can also exclude case where after "|" doesnt need to be read?
						}
						if((str.contains(newLine)&&g==124)||g==124) {//if we get to | and str doesnt contain newLine,
							while(!str.contains(newLine)&&(g = genFileReader.read()) != -1&&g!=13){
								//SKIP THROUGH WHOLE CATEGORY!!
							}
							if(str.contains(newLine)) {//purpose for this is to add extra function to end of while
							while(str.contains(newLine)&&(g = genFileReader.read()) != -1&&g!=13) {
								category = (String)(category + (char)g);//read the category
							}//watch here for what effect the change to this will ahve on category
							str = str.replace(tempString, "").trim();
							longSentenceStructArr=appendToArray(longSentenceStructArr,category);
							if(str.replace(" ","")=="") {
							genFileReader.close();
							System.out.println("str stopping, string:" + str);
							break;//stops while before breaking into while conditions
							}else {
								System.out.println("str continuing, string:" + str);
								str = identifySentenceStructure(str);
								genFileReader.close();
							}
							
							}
						}
						if((char)g==13&&str.replace(" ", "")!="") {//when new lines occur, empty the line
							newLine="";
						}
					}//end of while				
				}//end of if(found)
				if(genFileReader!=null) {
					genFileReader.close();
				}
			} catch (IOException e) {
				System.out.println("ERROR: NO STREAM!! (Gone one too many times"
						+ " through recurssion without closing stream and read is attempted)");
				//e.printStackTrace();
			}
			        
	        return str;
	}//end of method
	
	
	private static String responseCenter(String[] sentStructArr) {
		
		
		
		return "";
	}

	//useful for memory https://www.w3spoint.com/filereader-and-filewriter-in-java
	
	private static String[] appendToArray(String[] arr, String str) {
		for(int x=0;x<arr.length;x++) {
			if(arr[x]==null||arr[x]=="") {
				arr[x]=str;
				break;
			}
		}
		return arr;
	}
	
}

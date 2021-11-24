import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
	private static int numOfCategories = 0;//number of categories
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
		
		
		identifySentenceStructure(UI.toLowerCase());
		//String sentenceStructureString;
		
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
	
	private static void errorAttributer() {//infers any errors in userInput
		
		
	}
	
	private static void appendInfoToFile(){//append info to file
		
	}
	
	
	private static void checkIfInfoFromFile(String fileName, String info) {//checks if info is within file
		//goes line to line checking if string matches line, if not move on to next until end of file
		
	}

	
	
	private static void keywordOrganizer() {
		//reads through file first to get number of lines,
		//then make array of exact size, assigning each line to array index
		//then make simple sorting algorithm to sort in order of these variables:
		//first, 
		
		
		
		File file = new File (keywordFileName); 
		Scanner s = null ;
		String newFile = "";
		String newLine = "";
		String keywordCategory = "";
		
		
		try  { 
			s =  new  Scanner (file);

			// Read the file line by line 
			while  (s.hasNextLine())  { 
				String line = s.nextLine();  	// We keep the line on a String 
				System.out.println(line);       // We print the line 
				
				//if(line)
				
				
				
				
				
				
				
				
				
				
				
				
				
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
								//the below string subtracts the length of the string by the length of the word,
								//effectively always removing last word and 
								//tempString.lenth()-tempString.split(" ")[tempString.split(" ").length-1].length()
								tempInt=tempString.length()-tempString.split(" ")[tempString.split(" ").length-1].length();
								tempString = tempString.substring(0,tempInt-1);
							}else {//case of last word, which has alrdy been tested
								tempString = "";
							}
						}
						System.out.println("just out of while, tempLine is:" + tempString);

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
						else if(newLine!=""&&g!=124){
							if(newLine.contains("|")&&str.contains(newLine.substring(0, newLine.indexOf("|")))) {//the substring= keyword/phrase
								category=newLine.substring(newLine.indexOf("|")+1, newLine.length());
								sentenceStructure=sentenceStructure.replace(newLine.substring(0, newLine.indexOf("|")), category);
								str=str.replace(newLine.substring(0, newLine.indexOf("|")), "");
							//swaps between category and setnence structure
							//setnece structure contains mix between category and str
							//slowly turn sentence structure into only categorys
							
							//str shrinks, category swaps to whatever category the string is,
							//and sentenceStructure swaps the string in str with the category
							//which ultimately forms the simple response structure we need in response center
							
							} 
						}//end of elseif
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
				System.out.println("I assume no stream");
				e.printStackTrace();
			}
			        
	        return str;
	}//end of method
	
	
	private static String responseCenter(String[] sentStructArr) {
		
		
		
		return "";
	}
	
	//identifySentenceStructure
	//responseCenter
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

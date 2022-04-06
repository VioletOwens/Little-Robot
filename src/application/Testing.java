package application;

import java.io.File;
import java.util.Scanner;

public class Testing{
	
	
	public static void main(String[] args) {
		Info i = new Info();
		i.createList("List1");
		i.addItem("List1", "item1");
		i.addItem("List1", "item2");
		i.addItem("List1", "item3");
		i.showList("List1");
		i.showListName();
		i.removeItem("List1","item2");
		i.showList("List1");
		i.returnList("List1");
		
		CTier command = new CTier();
		command.addNode("root","N/A", 0);
		command.addNode("group1","root", 1);
		command.addNode("group2","root", 1);
		command.addNode("command1","group2", 2);
		command.addNode("command2","group1", 2);
		command.displayNode("command2");
		command.addNode("Phrase 1","command1", 3);
		command.showTree();
		

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
		String[] tempArr=null;
		String[] phraseArr = null;
		String[] fileArr = null;
		String[] excludedFileNameList = {
				"keywords.txt","filelist.txt","categorygrouping.txt","inputresponsegrouping.txt"
				,"commands.txt", "commandsgrouping.txt"};
		//i know this is hardcoded im trying to make a manager for it
		String line = "";
		String tempString="";
		String groupTitle = "";
		String category = "";
		String phrase = "";
		int tempCtr = 0;
		int indexCtr=0;
		int groupNum = 0;
		Boolean validFile=false;
		File file;
		Scanner sc;
		
		
		/* 
		 * best form of reading the files is to split up phrases and information used
		 * so we can read through the files in one move for phrases
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
		
		
	}
	
	public static void updatePhrases() {
		/* things to record and add onto if needed:
		 * categories, phrases, category-fileNames, phrase-fileNames, categories and
		 * their phrases
		 */
		
		
	}
	
	
	public static void updateData() {
		/* things to record and add onto if needed:
		 * command triggers, commands, commands and their triggers, all fileNames, data
		 * file names, phrase file names, excluded phrase file names, excluded command
		 * file names, all excluded file names, mathematical data, unknown strings
		 * 
		 * 
		 */
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

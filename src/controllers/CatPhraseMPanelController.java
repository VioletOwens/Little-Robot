package controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Brain;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CatPhraseMPanelController extends Brain implements Initializable {

	@FXML
	ListView<String> ListOfCategories;
	@FXML
	ListView<String> ListOfPhrases;// ListAtBottomOfCategoryManager now ListOfPhrases
	@FXML
	TextField CatManagerTextField;//Main text field
	@FXML
	TextField CatSearchTextfield;
	@FXML
	TextField CatPhraseSearchTextField;
	@FXML
	Label CurrentCatLabel;// AltCategoryManagerPanelLabelTwo now CurrentCatLabel
	@FXML
	Label PhraseLabel;// CategoryManagerPanelLabelFour now PhraseLabel
	@FXML
	AnchorPane CatPhraseMPAnchorPane;
	@FXML
	Rectangle Rectangle;
	@FXML
	Text hoverInfoText;

	private Parent root;
	private Scene scene;
	private Stage stage;
	private String[] lastAction = { "Action", "CategoryInfo", "PhraseInfo", "FileInfo" };

	@FXML
	void addCategory() throws IOException {
		/*
		 * When adding a category make sure it isn't already a category name, or file name
		 * PhraseLabel
		.getText().equals("Select a source file for the category from the list below.")
		 * 
		 */
        File dir = new File(phraseDirectory);
        String[] fullFileList = dir.list();
		if(!CatManagerTextField.getText().equals("")&&
				!Brain.categoryPhraseGrouping.containsKey(CatManagerTextField.getText())
				&&!Brain.isStringInArr(CatManagerTextField.getText(), fullFileList)) {
		Rectangle.setVisible(true);
		ListOfPhrases.getItems().clear();
        for(String fileName:fullFileList) {
        	if(Brain.phraseFileValidity.get(fileName)) {
        		ListOfPhrases.getItems().add(fileName);
        	}
        }
		Brain.updateLists();
		PhraseLabel.setText("Select a source file for the category from the list below.");
		lastAction[0] = "add";
		lastAction[1] = CatManagerTextField.getText();
		lastAction[2] = "PhraseInfo";
		lastAction[3] = "FileInfo";
		}
	}

	@FXML
	void deleteCategory() throws IOException {
		/* when deleting a category, make sure that the category actually exists,
		 * is empty of ALL phrases, isn't an empty string
		 * and remove it from ALL locations, but record where those locations are in 
		 * FileInfo
		 */
		
		if(!CatManagerTextField.getText().equals("")&&
			Brain.categoryPhraseGrouping.containsKey(CurrentCatLabel.getText())&&
			Brain.categoryPhraseGrouping.get(CurrentCatLabel.getText())[0].equals("")) {
			//double check that update lists adds a null array when the phrasearr is empty
			
		
		lastAction[3] = "";
		for(String fileName:Brain.categoryFileGrouping.get(CurrentCatLabel.getText())) {
			lastAction[3] = lastAction[3].concat(fileName) + ",";
			Brain.removeFromFile(fileName, "|"+CatManagerTextField.getText());
		}
		lastAction[0] = "remove";
		lastAction[1] = CurrentCatLabel.getText();
		lastAction[2] = "PhraseInfo";
		lastAction[3] = Brain.replaceLast(lastAction[3], ",", "");
		ListOfCategories.getItems().remove(lastAction[1]);
		Brain.categoryPhraseGrouping.remove(CurrentCatLabel.getText());
		Brain.updateLists();
		}
	}

	@FXML
	void undo() throws IOException {
		if (!lastAction[1].equals("CategoryInfo") && !lastAction[0].equals("Action")) {
			if (lastAction[2].equals("PhraseInfo")) {
				// case of category being added or removed
				switch (lastAction[0]) {
				case "add":
					lastAction[3] = "";
					for(String fileName:Brain.categoryFileGrouping.get(lastAction[1])) {
						lastAction[3] = lastAction[3].concat(fileName) + ",";
						Brain.removeFromFile(fileName, "|"+lastAction[1]);
					}
					lastAction[0] = "remove";
					//lastAction[1] = CatManagerTextField.getText();
					lastAction[2] = "PhraseInfo";
					lastAction[3] = Brain.replaceLast(lastAction[3], ",", "");
					ListOfCategories.getItems().remove(lastAction[1]);
					Brain.updateLists();
					break;
				case "remove":
			        for(String fileName:lastAction[3].split(",")) {
						Brain.appendToFile(fileName, "|"+lastAction[1]);
			        }
					lastAction[0] = "add";
					//lastAction[1] = CurrentCatLabel.getText();
					lastAction[2] = "PhraseInfo";
					//lastAction[3] = "FileInfo";
					ListOfCategories.getItems().add(lastAction[1]);
					Brain.updateLists();
					break;

				}

			} else {
				// case of phrase being added or removed
				switch (lastAction[0]) {
				case "add":
					lastAction[3] = "";
					for (String fileName : Brain.categoryFileGrouping.get(lastAction[1])) {
						if(Brain.countNumberOfStringsInFile(fileName, "|" + lastAction[1])==1) {
							//case of only 1 copy of this category exists and must be perserved
							Brain.removeFromFile(fileName, lastAction[2]);
						}else {
							Brain.removeFromFile(fileName, lastAction[2] + "|" + lastAction[1]);
						}
						lastAction[3] = lastAction[3].concat(fileName + ",");
					}
					Brain.updateLists();
					ListOfPhrases.getItems().remove(lastAction[2]);
					lastAction[0] = "remove";
					lastAction[3] = Brain.replaceLast(lastAction[3], ",", "");
					break;
				case "remove":
					for (String fileName : lastAction[3].split(",")) {
						
						// Brain.appendToFile(fileName,CatManagerTextField.getText() +"|"+
						// CurrentCatLabel.getText());
						Brain.appendToFile(fileName, lastAction[2] + "|" + lastAction[1]);
					}
					Brain.updateLists();
					ListOfPhrases.getItems().add(lastAction[2]);
					lastAction[0] = "add";
					lastAction[3] = "FileInfo";
					break;

				}
			}

		}
	}

	@FXML
	void addPhrase() throws IOException {
		/*
		 * when a phrase is added, things to do is: add phrase to appropriate file, add
		 * phrase to category phrase manager list, run updateLists() in brain to update
		 * lists and see if updateLists() works correctly when called from another
		 * method. also record the action in lastAction[] so undo will functions
		 */
		// need to set up hashmap categoryPhraseGrouping
		// categoryPhraseGrouping and listOfCategories[]
		// Brain.isStringAvailable(tempString)
		if (CatManagerTextField != null && !CatManagerTextField.getText().equals("")
				&& !Brain.isStringInArr(Brain.categoryPhraseGrouping.get(CurrentCatLabel.getText()), CatManagerTextField.getText())) {
			for (String fileName : Brain.categoryFileGrouping.get(CurrentCatLabel.getText())) {
				Brain.appendToFile(fileName, CatManagerTextField.getText() + "|" + CurrentCatLabel.getText());

			}
			Brain.updateLists();
			ListOfPhrases.getItems().add(CatManagerTextField.getText());
			lastAction[0] = "add";
			lastAction[1] = CurrentCatLabel.getText();
			lastAction[2] = CatManagerTextField.getText();
			lastAction[3] = "FileInfo";
			// i need more information here the category isn't understood
			// the undo button will vaguely work soon but ill do it more tomorrow
		}
	}

	@FXML
	void removePhrase() throws IOException {
		if (CatManagerTextField != null && !CatManagerTextField.getText().equals("")
				&&Brain.isStringInArr(Brain.categoryPhraseGrouping.get(CurrentCatLabel.getText()), CatManagerTextField.getText())) {
			lastAction[3] = "";
			for (String fileName : Brain.categoryFileGrouping.get(CurrentCatLabel.getText())) {
				if(Brain.countNumberOfStringsInFile(fileName, "|" + CurrentCatLabel.getText())==1) {
					//case of only 1 copy of this category exists and must be perserved
					Brain.removeFromFile(fileName, CatManagerTextField.getText());
				}else {
					Brain.removeFromFile(fileName, CatManagerTextField.getText() + "|" + CurrentCatLabel.getText());
				}
				lastAction[3] = lastAction[3].concat(fileName + ",");
			}
			
			Brain.categoryPhraseGrouping.get(CatManagerTextField.getText());
			ListOfPhrases.getItems().remove(CatManagerTextField.getText());
			lastAction[0] = "remove";
			lastAction[1] = CurrentCatLabel.getText();
			lastAction[2] = CatManagerTextField.getText();
			lastAction[3] = Brain.replaceLast(lastAction[3], ",", "");
			Brain.updateLists();
			Brain.categoryPhraseGrouping.replace(CurrentCatLabel.getText(), 
					Brain.categoryPhraseGrouping.get(CurrentCatLabel.getText()), 
					Brain.removeLastStrFromArr(Brain.categoryPhraseGrouping.get(CurrentCatLabel.getText()),
							CatManagerTextField.getText()));
		}
	}

	@FXML
	void openCatGroupEditor(ActionEvent event) throws IOException {
		URL url = new File("fxmls\\CatGroupManagerPanel.fxml").toURI().toURL();
		root = FXMLLoader.load(url);
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Category Phrase Manager");
		stage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		if (CurrentCatLabel != null) {
			CurrentCatLabel.setText("Commendation");
		}

		if (ListOfCategories != null) {
			ListOfCategories.getItems().setAll(Brain.listOfCategories);
			ListOfCategories.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent click) {
					if (click.getClickCount() == 2
							&& !ListOfCategories.getSelectionModel().getSelectedItem().equals("")) {
						// finding the phrases around the category
						ListOfPhrases.getItems().clear();
						CurrentCatLabel.setText(ListOfCategories.getSelectionModel().getSelectedItem());
						ListOfPhrases.getItems().addAll(categoryPhraseGrouping.get(CurrentCatLabel.getText()));
					}
				}
			});
		}

		if (ListOfPhrases != null) {
			// assigning the initial list for ListOfPhrases
			for (int x = 0; x < listOfCategoriesAndTheirPhrases.length; x++) {
				if (listOfCategoriesAndTheirPhrases[x][0].equals(CurrentCatLabel.getText())) {
					for (int y = 1; y < listOfCategoriesAndTheirPhrases[x].length; y++) {
						ListOfPhrases.getItems().add(listOfCategoriesAndTheirPhrases[x][y]);
					}
					break;
				}
			}

			ListOfPhrases.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent click) {
					// case where we are looking at filenames and selecting where a category goes
					String selectedItem = ListOfPhrases.getSelectionModel().getSelectedItem();
					if (selectedItem != null && click.getClickCount() == 2 && !selectedItem.equals("") && PhraseLabel
							.getText().equals("Select a source file for the category from the list below.")) {
						try {
							if (selectedItem.equals("Double click here to add it under a new file.")) {
								//case of new file being created (if need be)
								File newFile = new File(phraseDirectory + "tempfilename");
								if (!newFile.exists()) {
									newFile.createNewFile();
								}
								Brain.appendToFile(newFile.getPath(), "|" + CatManagerTextField.getText());
								lastAction[0] = "add";
								lastAction[1] = CatManagerTextField.getText();
								lastAction[2] = "PhraseInfo";
								lastAction[3] = newFile.getName();
								System.out.println("Printing newfiles name:" + newFile.getName());
								System.out.println("Printing newfiles path:" + newFile.getPath());

								PhraseLabel.setText("Phrases relating to the category are below.");
								ListOfPhrases.getItems().clear();
								ListOfPhrases.getItems().addAll(Brain.categoryPhraseGrouping.get(CurrentCatLabel.getText()));
								/*
								 * for(int x=0;x<Brain.listOfCategoriesAndTheirPhrases.length;x++) {
								 * if(Brain.listOfCategoriesAndTheirPhrases[x][0].equals(
								 * CurrentCatLabel.getText())) { //found the correct category and going through
								 * list for(int y=1; y<Brain.listOfCategoriesAndTheirPhrases[x].length;y++) {
								 * ListOfPhrases.getItems().add( Brain.listOfCategoriesAndTheirPhrases[x][y]); }
								 * } }
								 */
							} else {
								// case of a known file selected and adding category to it

								lastAction[0] = "add";
								lastAction[1] = CatManagerTextField.getText();
								lastAction[2] = "PhraseInfo";
								lastAction[3] = selectedItem;


								Brain.appendToFile(selectedItem, "|" + CatManagerTextField.getText());
								ListOfCategories.getItems().add(CatManagerTextField.getText());
								PhraseLabel.setText("Phrases relating to the category are below.");
								ListOfPhrases.getItems().clear();
								//ListOfPhrases.getItems().addAll(Brain.categoryPhraseGrouping.get(CurrentCatLabel.getText()));
								/*
								 * for(int x=0;x<Brain.listOfCategoriesAndTheirPhrases.length;x++) {
								 * if(Brain.listOfCategoriesAndTheirPhrases[x][0].equals(
								 * CurrentCatLabel.getText())) { //found the correct category and going through
								 * list for(int y=1; y<Brain.listOfCategoriesAndTheirPhrases[x].length;y++) {
								 * ListOfPhrases.getItems().add( Brain.listOfCategoriesAndTheirPhrases[x][y]); }
								 * break; } }
								 */
							}
							Brain.updateLists();
							Rectangle.setVisible(false);
						} catch (IOException e) {
							e.printStackTrace();
						}
						// else if the adding category part because we don't want the double
						// click feature to interfere with operations
					} else if (click.getClickCount() == 2 && !selectedItem.equals(null) &&
							!selectedItem.equals("")) {
						// setting the textfield equal to the item that was double clicked
						CatManagerTextField.setText(selectedItem);
					}
				}
			});
		}

		if (CatPhraseMPAnchorPane != null) {
			CatPhraseMPAnchorPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent click) {
					CatPhraseMPAnchorPane.requestFocus();
				}
			});
		}

		if (Rectangle != null) {

			Rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent click) {
					Rectangle.setVisible(false);
					PhraseLabel.setText("Phrases relating to the category are below.");
					ListOfPhrases.getItems().clear();
					ListOfPhrases.getItems().addAll(Brain.categoryPhraseGrouping.get(CurrentCatLabel.getText()));
					/*
					for (int x = 0; x < Brain.listOfCategoriesAndTheirPhrases.length; x++) {
						if (Brain.listOfCategoriesAndTheirPhrases[x][0].equals(CurrentCatLabel.getText())) {
							// found the correct category and going through list
							for (int y = 1; y < Brain.listOfCategoriesAndTheirPhrases[x].length; y++) {
								ListOfPhrases.getItems().add(Brain.listOfCategoriesAndTheirPhrases[x][y]);
							}
						}
					}
					*/
				}
			});
		}
		if (hoverInfoText != null) {
			/*
			 * You can double click a group of connected categories to instantly enter it.
			 */
			Tooltip t = new Tooltip(
					"You can double click a category to open" + "\n" + "it's phrases below. You can also double click a"
							+ "\n" + "phrase to instantly enter it into the textbox.");
			t.setShowDuration(new Duration(50000.0));
			t.setHideOnEscape(true);
			t.setAutoHide(true);
			Tooltip.install(hoverInfoText, t);

		}
	}

}

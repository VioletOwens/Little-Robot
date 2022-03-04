package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Brain;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CatGMPanelController extends Brain implements Initializable {

	@FXML
	AnchorPane CatGMAnchorPane;
	@FXML
	ComboBox<String> UICategoryComboBox;
	@FXML
	ComboBox<String> CurrentStatusComboBox;
	@FXML
	ComboBox<String> ResponseCategoryComboBox;
	@FXML
	ListView<String> ListOfConCatGroup;
	@FXML
	ListView<String> ListOfCategories;
	@FXML
	Text hoverInfoText;
	@FXML
	TextField catSearchTextfield;
	@FXML
	TextField catGroupSearchTextField;
	
	private Parent root;
	private Scene scene;
	private Stage stage;
	private String[] lastAction = {"Action","Info"};
	//action can be either add or remove, it can only add things to "categorygrouping.txt"

	
	@FXML
	void addGroup() {
		try {
			String UICatSelection = UICategoryComboBox.getSelectionModel().getSelectedItem();
			String statusSelection = CurrentStatusComboBox.getSelectionModel().getSelectedItem();
			String ResponseSelection = ResponseCategoryComboBox.getSelectionModel().getSelectedItem();
			String builtString = "[" + UICatSelection + "]" + "[" + statusSelection + "]" + "[" + ResponseSelection + "]";
			if(!UICatSelection.equals(" ")&&!ResponseSelection.equals(" ")&&
					!statusSelection.equals(" ")&&!Brain.isStringInFile("categorygrouping.txt", builtString)) {
				ListOfConCatGroup.getItems().add(builtString);
				Brain.appendToFile("categorygrouping.txt", builtString);
				newLastAction("add",builtString);
			
			}
		}catch(NullPointerException e) {
			
		}

	}

	@FXML
	void removeGroup() {
		try {
		String UICatSelection = UICategoryComboBox.getSelectionModel().getSelectedItem();
		String statusSelection = CurrentStatusComboBox.getSelectionModel().getSelectedItem();
		String ResponseSelection = ResponseCategoryComboBox.getSelectionModel().getSelectedItem();
		String builtString = "[" + UICatSelection + "]" + "[" + statusSelection + "]" + "[" + ResponseSelection + "]";
		if(!UICatSelection.equals(" ")&&!ResponseSelection.equals(" ")&&
				!statusSelection.equals(" ")&&Brain.isStringInFile("categorygrouping.txt", builtString)) {
			ListOfConCatGroup.getItems().remove(builtString);
			Brain.removeFromFile("categorygrouping.txt", builtString);
			newLastAction("remove",builtString);
		}
	}catch(NullPointerException e) {
		
	}
	}

	@FXML
	void undo() {
		switch (lastAction[0]) {
		case "Action":
			break;
		case "add":
			ListOfConCatGroup.getItems().remove(lastAction[1]);
			Brain.removeFromFile("categorygrouping.txt", lastAction[1]);
			newLastAction("remove",lastAction[1]);
			break;
		case "remove":
			ListOfConCatGroup.getItems().add(lastAction[1]);
			Brain.appendToFile("categorygrouping.txt", lastAction[1]);
			newLastAction("add",lastAction[1]);
			break;
		case "":
			break;
		}
			
		
		
	}

	@FXML
	void openPhraseEditor(ActionEvent event) throws IOException{
		URL url = new File("fxmls\\CatPhraseManagerPanel.fxml").toURI().toURL();
		root = FXMLLoader.load(url);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Category Phrase Manager");
        stage.show();
	}
	
	private void newLastAction(String action, String info) {
		lastAction[0] = action;
		lastAction[1] = info;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		if (this.CurrentStatusComboBox != null) {
			CurrentStatusComboBox.getItems().addAll(Brain.listOfStatuses);
		}
		if (this.UICategoryComboBox != null) {
			UICategoryComboBox.getItems().addAll(Brain.listOfCategories);
		}
		if (this.ResponseCategoryComboBox != null) {
			ResponseCategoryComboBox.getItems().addAll(Brain.listOfCategories);
			ResponseCategoryComboBox.getItems().add("Current Status");
		}

		if (ListOfCategories != null) {
			ListOfCategories.getItems().setAll(Brain.listOfCategories);
			// below is the drag and drop feature
			ListOfCategories.setOnDragDetected(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent click) {
					ListOfCategories.setCursor(Cursor.CLOSED_HAND);
				}
			});
			ListOfCategories.setOnMouseReleased(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent click) {
					try {
						String clickedNode = click.getPickResult().getIntersectedNode().getId();
						String selectedCatItem = ListOfCategories.getSelectionModel().getSelectedItem();
						ListOfCategories.setCursor(Cursor.DEFAULT);
						if (!clickedNode.equals(null)) {
							if (clickedNode.equals(UICategoryComboBox.getId())
									&& UICategoryComboBox.getItems().contains(selectedCatItem)) {
								UICategoryComboBox.getSelectionModel().select(selectedCatItem);
								ListOfCategories.getSelectionModel().clearSelection();
							}
							if (clickedNode.equals(ResponseCategoryComboBox.getId())
									&& ResponseCategoryComboBox.getItems().contains(selectedCatItem)) {
								ResponseCategoryComboBox.getSelectionModel().select(selectedCatItem);
								ListOfCategories.getSelectionModel().clearSelection();
							}
						}
					} catch (NullPointerException e) {
						// System.out.println("error caught.");
						ListOfCategories.getSelectionModel().clearSelection();
					}
				}

			});
		}

		if (ListOfConCatGroup != null) {
			ListOfConCatGroup.getItems().setAll(Brain.getWholeFileToString("categorygrouping.txt").split("\n"));
			// the double click feature is below.
			ListOfConCatGroup.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent click) {
					if (click.getClickCount() == 2
							&& !ListOfConCatGroup.getSelectionModel().getSelectedItem().equals("")
							&& !ListOfConCatGroup.getSelectionModel().getSelectedItem().equals(" ")) {
						String tempString = "";

						tempString = ListOfConCatGroup.getSelectionModel().getSelectedItem();

						UICategoryComboBox.setValue(tempString.substring(1, tempString.indexOf("]")));

						tempString = tempString.substring(tempString.indexOf("]") + 1, tempString.length());

						CurrentStatusComboBox
								.setValue(tempString.substring(tempString.indexOf("[") + 1, tempString.indexOf("]")));

						tempString = tempString.substring(tempString.indexOf("]") + 1, tempString.length());

						ResponseCategoryComboBox
								.setValue(tempString.substring(tempString.indexOf("[") + 1, tempString.indexOf("]")));
					}
				}
			});
		}

		if (CatGMAnchorPane != null) {
			CatGMAnchorPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent click) {
					CatGMAnchorPane.requestFocus();
					ListOfConCatGroup.getSelectionModel().clearSelection();
				}
			});
		}

		if (catSearchTextfield != null) {
			FilteredList<String> filteredCatList = new FilteredList<>(
					FXCollections.observableList(ListOfCategories.getItems()), b -> true);
			catSearchTextfield.setOnKeyReleased(e -> {
				String searchText = catSearchTextfield.getText();
				if (searchText.equals(null) || searchText.equals("")) {
					filteredCatList.setPredicate(b -> true);
				} else {
					filteredCatList.setPredicate(b -> b.contains(searchText));
				}
				if (filteredCatList.size() == 0) {
					ListView<String> h = new ListView<String>();
					h.getItems().add(" ");
					ListOfCategories.setItems(h.getItems());
				} else {
					ListOfCategories.setItems(filteredCatList);
				}
			});
		}

		if (catGroupSearchTextField != null) {
			FilteredList<String> filteredCatList = new FilteredList<>(
					FXCollections.observableList(ListOfConCatGroup.getItems()), b -> true);
			catGroupSearchTextField.setOnKeyReleased(e -> {
				String searchText = catGroupSearchTextField.getText();
				if (searchText.equals(null) || searchText.equals("")) {
					filteredCatList.setPredicate(b -> true);
				} else {
					filteredCatList.setPredicate(b -> b.contains(searchText));
				}
				if (filteredCatList.size() == 0) {
					ListView<String> h = new ListView<String>();
					h.getItems().add(" ");
					ListOfConCatGroup.setItems(h.getItems());
				} else {
					ListOfConCatGroup.setItems(filteredCatList);
				}
			});
		}

		if (hoverInfoText != null) {
			/*
			 * You can double click a group of connected categories to instantly enter it.
			 */
			Tooltip t = new Tooltip("You can double click a group of connected" + "\n"
					+ "categories to instantly enter it. You can also drag" + "\n"
					+ "a category from the list of categories to an applicable" + "\n"
					+ "drop-down to instantly enter it.");
			t.setShowDuration(new Duration(50000.0));//50 second time out
			t.setHideOnEscape(true);
			t.setAutoHide(true);
			Tooltip.install(hoverInfoText, t);

		}
	}

}

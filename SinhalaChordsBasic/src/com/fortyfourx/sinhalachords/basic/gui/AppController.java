package com.fortyfourx.sinhalachords.basic.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import com.fortyfourx.sinhalachords.basic.DBHandler;
import com.fortyfourx.sinhalachords.basic.Song;

public class AppController {

    @FXML
    private TextField textFieldSearchQuery;

    @FXML
    private ListView<ListItem> listViewResults;

    private static ObservableList<ListItem> items;
    
    @FXML
    private void initialize() {
    	items = FXCollections.observableArrayList(DBHandler.getAllItems());
    	
    	FilteredList<ListItem> filtereditems = new FilteredList<>(items, p -> false); // true to show all, false to show empty
    	
    	textFieldSearchQuery.textProperty().addListener((observable, oldValue, newValue) -> {
    		filtereditems.setPredicate(item -> {
    			if (newValue == null || newValue.isEmpty()) {
                    return false; // true to show all, false to show empty
                }
    			
    			String lowerCaseFilter = newValue.toLowerCase();
    			if (item.getText().toLowerCase().contains(lowerCaseFilter)) {
    				return true;
                }
                return false;
    		});
    	});
    	
    	listViewResults.setItems(filtereditems);
    	
    	
    	listViewResults.setCellFactory(new Callback<ListView<ListItem>, ListCell<ListItem>>() {
			
			@Override
			public ListCell<ListItem> call(ListView<ListItem> param) {
				return new ListItemListCell(listViewResults);
			}
		});
    	
    	listViewResults.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int id = listViewResults.getSelectionModel().getSelectedItem().getId();                
                Song song = DBHandler.getSong(id);
                
                // TODO: TEST (REMOVE)
                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(null);
                VBox dialogVbox = new VBox(20);
                dialogVbox.getChildren().add(new ImageView(song.getLyrics()));
                Scene dialogScene = new Scene(dialogVbox);
                dialog.setScene(dialogScene);
                dialog.show();
            }
        });
    	
	}

}

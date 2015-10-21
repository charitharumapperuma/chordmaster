package com.fortyfourx.sinhalachords.basic.gui;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

import com.fortyfourx.sinhalachords.basic.DBHandler;
import com.fortyfourx.sinhalachords.basic.Song;

public class AppController {
	
	public static final int PREFERRED_WIDTH = 360;
	public static final int DEFAULT_IMAGEVIEW_LEFT_PADDING = 10;

	private Stage stage;
	private Scene scene;
	
    @FXML
    private TextField textFieldSearchQuery;

    @FXML
    private ListView<ListItem> listViewResults;
    
    @FXML
    private HBox sidePane;
    
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
            	sidePane.getChildren().clear();
            	
                int id = listViewResults.getSelectionModel().getSelectedItem().getId();
                
                // since the marker is set to identify songs and artists in the list,
                // where both of them are displayed as same type of entities.
                // marker is DBHandler.MAX_ARTIST_COUNT
                if (id > DBHandler.MAX_ARTISTS_COUNT) {
                	id = id - DBHandler.MAX_ARTISTS_COUNT;
	                Song song = DBHandler.getSong(id);
	                
	                int sceneH = scene.heightProperty().intValue();
	                Image img = song.getLyrics();
	                List<Image> imagesList = new ArrayList<>();
	                Image bottom = img;
	                Image top;
	                while(bottom.heightProperty().intValue() > sceneH) {
	                	PixelReader reader = bottom.getPixelReader();
	                	
	                	top = new WritableImage(reader, 0, 0, bottom.widthProperty().intValue(), sceneH);
	                	bottom = new WritableImage(reader, 0, sceneH, bottom.widthProperty().intValue(), bottom.heightProperty().intValue()-sceneH);
	                	
	                	imagesList.add(top);
	                }
	                imagesList.add(bottom);
	                
	                double newWidth = PREFERRED_WIDTH + song.getLyrics().getWidth() * imagesList.size() + DEFAULT_IMAGEVIEW_LEFT_PADDING * imagesList.size();

	                sidePane.setStyle("-fx-background-color: white; -fx-padding: 0 0 0 10");
	                
	                ImageView iv;
	                Pane p;
					for (Image i : imagesList) {
						p = new Pane();
						iv = new ImageView(i);
						p.getChildren().add(iv);
						p.setStyle("-fx-padding: 0 0 0 " + DEFAULT_IMAGEVIEW_LEFT_PADDING);
						sidePane.getChildren().add(p);
					}
					
	                stage.setWidth(newWidth);
	            }
            }
        });
    	
    	listViewResults.setStyle("-fx-focus-color: transparent;");
	}
    
    public void setStage(Stage stage) {
    	this.stage = stage;
    }
    
    public void setScene(Scene scene) {
    	this.scene = scene;
    }
}

/*// TODO: TEST (REMOVE) POPUP CHORDS
final Stage dialog = new Stage();
dialog.initModality(Modality.APPLICATION_MODAL);
dialog.initOwner(null);
VBox dialogVbox = new VBox(20);
dialogVbox.getChildren().add(new ImageView(song.getLyrics()));
Scene dialogScene = new Scene(dialogVbox);
dialog.setScene(dialogScene);
dialog.show();*/

package com.fortyfourx.sinhalachords.basic.gui;

import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ListItemListCell extends ListCell<ListItem> {

	private Text text;
	private Text subText;
	private GridPane grid;
	
	public ListItemListCell(ListView<ListItem> lv) {
		text = new Text();
		subText = new Text();
		grid = new GridPane();
		
		grid.setHgap(5);
		grid.setPadding(new Insets(5, 20, 5, 20));
		text.setFont(new Font(14));
		text.setFill(Paint.valueOf("#666666"));
		subText.setFont(new Font(10));
		subText.setFill(Paint.valueOf("#999999"));
		
		grid.add(text, 0, 0);
		grid.add(subText, 0, 1);
	}
	
	@Override
	protected void updateItem(ListItem item, boolean empty) {
		super.updateItem(item, empty);
		
		if(!empty) {
			text.setText(item.getText());
			subText.setText(item.getSubText());
			
			setGraphic(grid);
		}

	}
}

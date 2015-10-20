package com.fortyfourx.sinhalachords.basic.gui;

public class ListItem {
	private int id;
	private String text;
	private String subText;

	public ListItem() {
		this.id = -1;
		this.text = null;
		this.subText = null;
	}
	
	public ListItem(int id, String txt) {
		this.id = id;
		this.text = txt;
		this.subText = null;
	}
	
	public ListItem(int id, String txt, String stxt) {
		this.id = id;
		this.text = txt;
		this.subText = stxt;
	}

	@Override
	public String toString() {
		if (subText == null) {
			return text;
		}
		return text + "\n" + subText;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSubText() {
		return subText;
	}

	public void setSubText(String subText) {
		this.subText = subText;
	}
}

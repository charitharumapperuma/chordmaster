package com.fortyfourx.sinhalachords.basic;

import javafx.scene.image.Image;

public class Song {
	private int id;
	private String title;
	private String artist;
	private String key;
	private String beat;
	private Image lyrics;

	public Song(int id, String title, String artist, String key, String beat, Image lyrics) {
		this.id = id;
		this.title = title;
		this.artist = artist;
		this.key = key;
		this.beat = beat;
		this.lyrics = lyrics;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getBeat() {
		return beat;
	}

	public void setBeat(String beat) {
		this.beat = beat;
	}

	public Image getLyrics() {
		return lyrics;
	}

	public void setLyrics(Image lyrics) {
		this.lyrics = lyrics;
	}
}

package com.fortyfourx.sinhalachords.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

import com.fortyfourx.sinhalachords.basic.gui.ListItem;

public class DBHandler {
	public static final int MAX_ARTISTS_COUNT = 1000;
	private static Connection conn;
	private static Statement stmt;
	private static ResultSet rs;
	
	private static void initiateConnection() {
		if (conn == null) {
			try {
				Class.forName("org.sqlite.JDBC");
				conn = DriverManager.getConnection("jdbc:sqlite:sinhalachords-basic-v1.db");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static List<ListItem> getAllItems() {
		List<ListItem> list = new ArrayList<>();
		
		initiateConnection();
		try {
			stmt = conn.createStatement();

			// adding songs
			String query = "SELECT song.id AS song_id, song.title AS song_title, artist.name AS artist_name FROM song, artist WHERE song.artist = artist.id;";
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				list.add(new ListItem(
						rs.getInt("song_id") + DBHandler.MAX_ARTISTS_COUNT,
						rs.getString("song_title"), 
						rs.getString("artist_name")
					));
			}
			
			// adding artists
			query = "SELECT id, name FROM artist;";
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				list.add(new ListItem(
						rs.getInt("id"), 
						rs.getString("name")
					));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static Song getSong(int id) {		
		initiateConnection();
		try {
			stmt = conn.createStatement();
			
			// get song by id
			String query = "SELECT * FROM song, artist WHERE song.artist = artist.id AND song.id = " + id + " LIMIT 1;";
			rs = stmt.executeQuery(query);
			if(rs.next()) {
				String title = rs.getString("title");
				String artist = rs.getString("name");
				String key = rs.getString("keynote");
				String beat = rs.getString("beat");
				Image lyrics = new Image(rs.getBinaryStream("lyrics"));
				
				return new Song(id, title, artist, key, beat, lyrics);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
}


/* TEST OBJECTS
list.add(new ListItem(1,"A"));
list.add(new ListItem(2,"simple"));
list.add(new ListItem(3,"example"));
list.add(new ListItem(4,"of"));
list.add(new ListItem(5,"how"));
list.add(new ListItem(6,"to"));
list.add(new ListItem(7,"create"));
list.add(new ListItem(8,"and"));
list.add(new ListItem(9,"populate"));
list.add(new ListItem(10,"a"));
list.add(new ListItem(11,"ListView"));
list.add(new ListItem(12,"of"));
list.add(new ListItem(13,"names"));
list.add(new ListItem(14,"You"));
list.add(new ListItem(15,"can"));
list.add(new ListItem(16,"find"));
list.add(new ListItem(17,"these"));
list.add(new ListItem(18,"pre-built"));
list.add(new ListItem(19,"cell"));
list.add(new ListItem(20,"factories"));
list.add(new ListItem(21,"in"));
list.add(new ListItem(22,"the"));
list.add(new ListItem(23,"javafx"));
list.add(new ListItem(24,"scene"));
list.add(new ListItem(25,"control"));
list.add(new ListItem(26,"cell"));
list.add(new ListItem(27,"package"));
*/
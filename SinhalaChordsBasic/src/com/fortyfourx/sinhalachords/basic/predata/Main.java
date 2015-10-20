package com.fortyfourx.sinhalachords.basic.predata;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Main {

	public static void main(String[] args) {
		Connection conSqlite = null;
		Connection conMysql = null;
		PreparedStatement stmtSqlite = null;
		PreparedStatement stmtMysql = null;
		ResultSet rs = null;
		
		FileInputStream fis = null;
		ByteArrayOutputStream baos = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			conSqlite = DriverManager.getConnection("jdbc:sqlite:sinhalachords-basic-v1.db");

			Class.forName("com.mysql.jdbc.Driver");
			conMysql = DriverManager.getConnection("jdbc:mysql://localhost:3306/chordmaster", "root", "");

			// ENABLE FORREIGN KEYS FOR SQLITE
			String sql = "PRAGMA foreign_keys = ON;";
			stmtSqlite = conSqlite.prepareStatement(sql);
			stmtSqlite.executeUpdate();
			
			// READ ARTISTS
			sql = "CREATE TABLE IF NOT EXISTS artist("
					+ "id INT PRIMARY KEY NOT NULL, "
					+ "name TEXT NOT NULL);";
			stmtSqlite = conSqlite.prepareStatement(sql);
			stmtSqlite.executeUpdate();
			
			sql = "SELECT * FROM artist;";
			stmtMysql = conSqlite.prepareStatement(sql);
			rs = stmtMysql.executeQuery();
			int artistId;
			String artistName;
			HashMap<Integer, String> artists = new HashMap<>();
			while (rs.next()) {
				artistId = rs.getInt("id");
				artistName = rs.getString("name");
				
				// cache artists.
				artists.put(artistId, artistName);
				
				// store artists to sqlite.
				sql = "INSERT OR IGNORE INTO artist (id, name) VALUES (" + artistId +", '" + artistName + "');";
				stmtSqlite = conSqlite.prepareStatement(sql);
				stmtSqlite.executeUpdate();
			}
			
			
			// READ SONGS
			sql = "CREATE TABLE IF NOT EXISTS song ("
					+ "id INT PRIMARY KEY NOT NULL, "
					+ "title TEXT NOT NULL, "
					+ "artist INT NOT NULL, "
					+ "keynote TEXT NOT NULL, "
					+ "beat TEXT NOT NULL, "
					+ "lyrics BLOB NOT NULL, "
					+ "FOREIGN KEY(artist) REFERENCES artist(id));";
			
			stmtSqlite = conSqlite.prepareStatement(sql);
			stmtSqlite.executeUpdate();
			
			sql = "SELECT * FROM song;";
			stmtMysql = conMysql.prepareStatement(sql);
			rs = stmtMysql.executeQuery();
			
			int songId;
			String songTitle;
			String songKey;
			String songBeat;
			int songArtist;
			File songLyrics;
			byte[] songLyricsByteArray;
			int eCount = 0; // error count.

			byte[] buf = new byte[1024];
			while(rs.next()) {
				songId = rs.getInt("id");
				songTitle = rs.getString("title");
				songKey = rs.getString("keynote");
				songBeat = rs.getString("beat");
				songArtist = rs.getInt("artist");
				
				try {
					songLyrics = new File("E:/eclipse-common/chordmaster-program/song/lyrics/" + songId + ".png");
					fis = new FileInputStream(songLyrics);
					baos = new ByteArrayOutputStream();
					
					try {
						for (int readNum; (readNum = fis.read(buf)) != -1;) {
							baos.write(buf, 0, readNum);
							System.out.println("read " + readNum + " bytes,");
						}
					} catch (IOException ex) {
						System.err.println(ex.getMessage());
					}
					songLyricsByteArray = baos.toByteArray();
					
					sql = "INSERT INTO song VALUES(?, ?, ?, ?, ?, ?)";
					stmtSqlite = conSqlite.prepareStatement(sql);
					stmtSqlite.setInt(1, songId);
					stmtSqlite.setString(2, songTitle);
					stmtSqlite.setInt(3, songArtist);
					stmtSqlite.setString(4, songKey);
					stmtSqlite.setString(5, songBeat);
					stmtSqlite.setBytes(6, songLyricsByteArray);
					
					stmtSqlite.executeUpdate();
				} catch (FileNotFoundException e) {
					System.out.println(eCount++);
				}
			}
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} finally {
			try {
				conMysql.close();
				conSqlite.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}

	}
}

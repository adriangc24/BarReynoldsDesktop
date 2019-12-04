package com.example.barreynoldsapp1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertarFotosMySQL {
	private static Connection connection;
	static String db = "barreynolds";
	static String url = "jdbc:mysql://localhost/" + db
			+ "?useUnicode=true&useJDBCCompliantTimeZoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	static String user = "root", psswd = "";

	public static void conexionJDBC() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			System.out.println("Error al registrar el driver de MySQL: " + ex);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(url, user, psswd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Conexion a base dedatos " + db + " correcta.");
	}

	public static void writeBlob(int candidateId, String filename) {
		String updateSQL = "UPDATE productes " + "SET Imatge = ? " + "WHERE id=?";

		conexionJDBC();
		try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
			// read the file
			File file = new File(filename);
			FileInputStream input = new FileInputStream(file);

			// set parameters
			pstmt.setBinaryStream(1, input);
			pstmt.setInt(2, candidateId);

			// store the resume file in database
			System.out.println("Reading file " + file.getAbsolutePath());
			System.out.println("Store file in the database.");
			pstmt.executeUpdate();

		} catch (SQLException | FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	public static void writeBlobCamarero(int candidateId, String filename) {
		String updateSQL = "INSERT INTO Cambrer (Nom_Cambrer,Contrasenya,fotoCamarero) VALUES ? ? ?";

		conexionJDBC();
		try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
			// read the file
			File file = new File(filename);
			FileInputStream input = new FileInputStream(file);

			// Blob imagen camarero
			pstmt.setBinaryStream(3, input);
			// Contrasenya
			pstmt.setString(2, "");
			// Nom Cambrer
			pstmt.setString(1, "");

			

			// store the resume file in database
			System.out.println("Reading file " + file.getAbsolutePath());
			System.out.println("Store file in the database.");
			pstmt.executeUpdate();

		} catch (SQLException | FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {

		//writeBlob(27, "C:\\Users\\alumlocal\\Bar Reynolds\\BarReynoldsDesktop\\Imatges\\entrepa_de_truita_espanyola.jpg");

	}

}

package com.example.barreynoldsapp1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AccesSQL implements ConexionServer {
	private static Connection connection;

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

	public static void generarListaCambrers(ArrayList<Cambrer> listaCambrers) {
		try {
			conexionJDBC();
			Statement stmnt = connection.createStatement();
			ResultSet rsst = stmnt.executeQuery("SELECT * FROM cambrer");
			while (rsst.next()) {
				int id = (int) rsst.getObject("id");
				String nom = rsst.getObject("Nom_Cambrer").toString();
				listaCambrers.add(new Cambrer(id, nom));
				System.out.println(id + nom);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static int cargarMesasBBDD() {
		int numTaules = 0;
		conexionJDBC();
		try {
			Statement stmnt = connection.createStatement();
			ResultSet rsst = stmnt.executeQuery("SELECT NumeroTaules FROM taula_mestra_configuracio;");
			while (rsst.next()) {
				numTaules = rsst.getInt("NumeroTaules");
				System.out.println(numTaules);
			}
			// System.out.println(numTaules);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return numTaules;
	}

	public static ArrayList<Producto> cargarProductos(String categoria) throws SQLException {
		conexionJDBC();
		Statement stmnt = connection.createStatement();
		ArrayList<Producto> arrayProductos = new ArrayList<>();
		int id = 0;

		ResultSet rsst = stmnt.executeQuery("SELECT id FROM categoria where '" + categoria + "'=Nom_Categoria;");
		while (rsst.next()) {
			id = (int) rsst.getObject("ID");

		}

		ResultSet rsst2 = stmnt.executeQuery("SELECT * FROM productes where '" + id + "'=ID_Categoria;");
		while (rsst2.next()) {
			Producto p1 = new Producto(rsst2.getInt("id"), rsst2.getString("Nom_Producte"), rsst2.getFloat("preu"),
					rsst2.getString("descripcio"), rsst2.getInt("id_categoria"));
			arrayProductos.add(p1);

		}
		return arrayProductos;

	}

	public static ArrayList<String> cargarCategorias()  {
		ArrayList<String> categorias = new ArrayList<>();
		conexionJDBC();
		Statement stmnt;
		try {
			stmnt = connection.createStatement();
			ResultSet rsst = stmnt.executeQuery("SELECT * FROM categoria");
			while (rsst.next()) {
				int id = rsst.getInt("id");
				String nom = rsst.getString("Nom_Categoria");
				categorias.add(nom);
				System.out.println(nom);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return categorias;

	}

}

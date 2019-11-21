package com.example.barreynoldsapp1;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AccesSQL {

	public static void generarListaCambrers(Statement stmnt, ArrayList<Cambrer> listaCambrers) {
		try {
			ResultSet rsst = stmnt.executeQuery("SELECT * FROM cambrer");
			while (rsst.next()) {
				int id = (int) rsst.getObject("id");
				String nom = rsst.getObject("Nom_Cambrer").toString();
				listaCambrers.add(new Cambrer(id, nom));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Producto> cargarProductos(Statement stmnt, String categoria) {
		ArrayList<Producto>arrayProductos=new ArrayList<>();
		int id=0;
		try {
			ResultSet rsst = stmnt.executeQuery("SELECT id FROM categoria where '"+categoria+"'=Nom_Categoria;");
			while (rsst.next()) {
				id = (int) rsst.getObject("ID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			ResultSet rsst2 = stmnt.executeQuery("SELECT * FROM productes where '"+id+"'=ID_Categoria;");
			while (rsst2.next()) {
				Producto p1=new Producto(rsst2.getInt("id"), rsst2.getString("Nom_Producte"), rsst2.getFloat("preu"), rsst2.getString("descripcio"), rsst2.getInt("id_categoria"));
				arrayProductos.add(p1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrayProductos;
		
		
	}
public static ArrayList<String> cargarCategorias(Statement stmnt, String categoria) {
	ArrayList<String> categorias=new ArrayList<>();
	try {
		ResultSet rsst = stmnt.executeQuery("SELECT * FROM categoria");
		while (rsst.next()) {
			int id = rsst.getInt("id");
			String nom = rsst.getString("Nom_Categoria");
			categorias.add(nom);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return categorias;
		
	}


}

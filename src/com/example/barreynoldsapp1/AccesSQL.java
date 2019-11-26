package com.example.barreynoldsapp1;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.mysql.jdbc.PreparedStatement;

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
				String password = rsst.getObject("Contrasenya").toString();
				listaCambrers.add(new Cambrer(id, nom, password));
				System.out.println(id + nom);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Producto>arrayTodosProductos(){
		ArrayList<Producto>ap=new ArrayList<>();
		try {
			conexionJDBC();
			Statement stmnt = connection.createStatement();
			ResultSet rsst = stmnt.executeQuery("SELECT * FROM productes");
			while (rsst.next()) {
				Producto p1 = new Producto(rsst.getInt("id"), rsst.getString("Nom_Producte"), rsst.getFloat("preu"),
						rsst.getString("descripcio"), rsst.getInt("id_categoria"));
				
				ap.add(p1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ap;
		
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

	public static ArrayList<String> cargarCategorias() {
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

	public static void pujarComanda(String taula) {
		File file = new File("Comandes" + File.separatorChar + "ComandaTaula" + taula + ".xml");
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(file);
			NodeList cambrer = doc.getElementsByTagName("camarero");
			NodeList data = doc.getElementsByTagName("fecha");
			int idCambrer = Integer.parseInt(cambrer.item(0).getTextContent().substring(0, 1));
			String dataCreacio = data.item(0).getTextContent();
			String insert = "INSERT INTO comanda (`ID_Cambrer`, `hora_creacio`) VALUES (?, ?)";
			try (PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(insert)) {
				pstmt.setInt(1, idCambrer);
				pstmt.setString(2, dataCreacio);
				pstmt.executeUpdate();
				System.out.println("Taula actualitzada.");
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} catch (Exception e) {
			System.out.println("Esto peta");
		}
	}

	public static void pujarRelacioComandaProducte(int quantitat, String producte, String dataCreacio) {
		Statement stmnt;
		try {
			stmnt = connection.createStatement();
			int ID_Comanda = 0;
			int ID_Producte = 0;
			ResultSet rsst = stmnt
					.executeQuery("SELECT `ID` FROM `productes` WHERE `Nom_Producte` = '" + producte + "'");
			if (rsst.next()) {
				ID_Producte = rsst.getInt("ID");
			}

			rsst = stmnt.executeQuery("SELECT `ID` FROM `comanda` WHERE `hora_creacio` = '" + dataCreacio + "'");
			if (rsst.next()) {
				ID_Comanda = rsst.getInt("ID");
			}

			String insert = "INSERT INTO relacio_comanda_producte (`ID_Comanda`, `ID_Producte`, `Quantitat`) VALUES (?, ?, ?)";
			if (!(ID_Producte == 0 && ID_Comanda == 0)) {

				try (PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(insert)) {
					pstmt.setInt(1, ID_Comanda);
					pstmt.setInt(2, ID_Producte);
					pstmt.setInt(3, quantitat);
					pstmt.executeUpdate();
					System.out.println("Taula actualitzada.");
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void actualitzarEstatProducte(int row, String producte, String dataCreacio) {
		conexionJDBC();
		Statement stmnt;
		try {
			stmnt = connection.createStatement();
			ResultSet rsst = stmnt
					.executeQuery("SELECT `ID` FROM `productes` WHERE `Nom_Producte` = '" + producte + "'");
			ResultSet rsst2 = stmnt
					.executeQuery("SELECT `ID` FROM `comanda` WHERE `hora_creacio` = '" + dataCreacio + "'");

			stmnt.executeUpdate("UPDATE `relacio_comanda_producte` SET `Estat`= " + 1 + " WHERE `ID_Producte` = "
					+ rsst.getInt("ID") + " AND ID_Comanda = " + rsst2.getInt("ID"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void pujadaFactura(String hora1, String minut, String segon)
			throws ParserConfigurationException, SAXException {
		conexionJDBC();
		try {
			File factura = new File(
					"Factures" + File.separatorChar + "Factura_" + hora1 + "_" + minut + "_" + segon + ".xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(factura);
			NodeList listaPreuFinal = doc.getElementsByTagName("preciofinal");
			double precioFinal = Double.parseDouble(listaPreuFinal.item(0).getTextContent());
			String insert = "INSERT INTO factura (`Preu Final`) VALUES (?)";
			try (PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(insert)) {
				pstmt.setDouble(1, precioFinal);
				pstmt.executeUpdate();
				System.out.println("Taula actualitzada.");
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		try {
			String query = "SELECT max(ID) FROM factura";
			Statement stmnt = connection.createStatement();
			ResultSet rsst = stmnt.executeQuery(query);
			if (rsst.next()) {
				int ID_Factura = rsst.getInt("max(ID)");
				pujadaDetallFactura(hora1, minut, segon, ID_Factura);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void pujadaDetallFactura(String hora1, String minut, String segon, int ID_Factura)
			throws ParserConfigurationException, SAXException {
		conexionJDBC();

		String nomProducte;
		double preu;
		int quantitat;
		File factura = new File(
				"Factures" + File.separatorChar + "Factura_" + hora1 + "_" + minut + "_" + segon + ".xml");
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(factura);
			NodeList listaNomProducte = doc.getElementsByTagName("nombre");
			NodeList listaPreus = doc.getElementsByTagName("precio");
			NodeList listaCantitats = doc.getElementsByTagName("cantidad");
			String insert = "INSERT INTO detall_factura(`Nom_Prod`, `Preu Prod`, `Quantitat`, `ID_Factura`) VALUES (?, ?, ?, ?)";
			for (int i = 0; i < listaNomProducte.getLength(); i++) {
				nomProducte = listaNomProducte.item(i).getTextContent();
				preu = Double.parseDouble(listaPreus.item(i).getTextContent());
				quantitat = Integer.parseInt(listaCantitats.item(i).getTextContent());
				try (PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(insert)) {
					pstmt.setString(1, nomProducte);
					pstmt.setDouble(2, preu);
					pstmt.setInt(3, quantitat);
					pstmt.setInt(4, ID_Factura);
					pstmt.executeUpdate();
					System.out.println("Taula actualitzada.");
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

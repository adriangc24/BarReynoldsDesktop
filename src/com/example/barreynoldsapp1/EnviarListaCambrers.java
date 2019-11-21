package com.example.barreynoldsapp1;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

public class EnviarListaCambrers {

	/*public static void main(String[] args) {
		EnviarListaCambrers sv = new EnviarListaCambrers();
	}*/

	public EnviarListaCambrers() {
		try {
			System.out.println("LocalHost = " + InetAddress.getLocalHost().toString());
		} catch (UnknownHostException uhe) {
			System.err.println("No puedo saber la dirección IP local : " + uhe);
		}
		// Abrimos un "Socket de Servidor" TCP en el puerto 4445.
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(4445);
		} catch (IOException ex) {
			System.out.println("No se ha podido crear el servidor en este puerto.");
		}

		Socket socket = null;
		ArrayList<Cambrer> listaCambrers = new ArrayList<Cambrer>();
		AccesSQL.generarListaCambrers(listaCambrers);
		while (true) {
			try {
				socket = serverSocket.accept();
				ObjectOutputStream salidaCambrers = new ObjectOutputStream(socket.getOutputStream());
				salidaCambrers.writeObject(listaCambrers);
				System.out.println("lista enviada");
				salidaCambrers.close();
				socket.close();
			} catch (IOException ex) {
				System.out.println("No se pudo aceptar la conexion.");
			}
		}
	}

	/*public static void generarListaCambrers(ArrayList<Cambrer> listaCambrers) {
		Connection conn;
		try {
			String url = "jdbc:mysql://localhost/barreynolds?useUnicode=true&useJDBCCompliantTimeZoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, "root", "");
			Statement stmnt = conn.createStatement();
			AccesSQL.generarListaCambrers( listaCambrers);
			for (Cambrer cambrer : listaCambrers) {
				System.out.println(cambrer.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}

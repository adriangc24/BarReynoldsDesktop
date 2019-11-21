package com.example.barreynoldsapp1;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;

public class EnviarListaProductos {

/*public static void main(String[] args) throws SQLException {
	EnviarListaProductos e=new EnviarListaProductos();
}*/
	public EnviarListaProductos() throws SQLException  {
		enviarCategorias();
		
	}
	
public void enviarCategorias() throws SQLException {
	try {
		System.out.println("LocalHost = " + InetAddress.getLocalHost().toString());
	} catch (UnknownHostException uhe) {
		System.err.println("No puedo saber la dirección IP local : " + uhe);
	}
	// Abrimos un "Socket de Servidor" TCP en el puerto 4445.
	ServerSocket serverSocket = null;
	try {
		serverSocket = new ServerSocket(4446);
	} catch (IOException ex) {
		System.out.println("No se ha podido crear el servidor en este puerto.");
	}

	Socket socket = null;
	ArrayList<String> listaCategoria = AccesSQL.cargarCategorias();
	
	while (true) {
		try {
			socket = serverSocket.accept();
			ObjectOutputStream salidaCategoria = new ObjectOutputStream(socket.getOutputStream());
			salidaCategoria.writeObject(listaCategoria);
			System.out.println("categorias enviadas");
			salidaCategoria.close();
			socket.close();
		} catch (IOException ex) {
			System.out.println("No se pudo aceptar la conexion.");
		}
	}
}
}

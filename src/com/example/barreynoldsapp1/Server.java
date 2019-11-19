package com.example.barreynoldsapp1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

public class Server {

	public static void main(String[] args) {
		Server sv = new Server();
	}

	public Server() {
		try {
			System.out.println("LocalHost = " + InetAddress.getLocalHost().toString());
		} catch (UnknownHostException uhe) {
			System.err.println("No puedo saber la dirección IP local : " + uhe);
		}
		// Abrimos un "Socket de Servidor" TCP en el puerto 4444.
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(4444);
		} catch (IOException ex) {
			System.out.println("No se ha podido crear el servidor en este puerto.");
		}

		Socket socket = null;
		InputStream in = null;
		OutputStream out = null;
		boolean repetir = true;
		while (repetir) {
			try {
				socket = serverSocket.accept();
			} catch (IOException ex) {
				System.out.println("No se pudo aceptar la conexion.");
			}

			try {
				in = socket.getInputStream();
			} catch (IOException ex) {
				System.out.println("No se ha podido recibir el input del cliente.");
			}

			try {
				out = new FileOutputStream("Comandes" + File.separatorChar + "ArchivoRecibido.xml");
			} catch (FileNotFoundException ex) {
				System.out.println("Archivo no encontrado.");
			}

			byte[] bytes = new byte[16 * 1024];

			int count;
			try {
				while ((count = in.read(bytes)) > 0) {
					out.write(bytes, 0, count);
				}
				System.out.println("Comanda recibida");
				out.close();
				in.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

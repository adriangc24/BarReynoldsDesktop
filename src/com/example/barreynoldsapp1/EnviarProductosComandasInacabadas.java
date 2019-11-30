package com.example.barreynoldsapp1;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class EnviarProductosComandasInacabadas {
		public EnviarProductosComandasInacabadas() {
			EnviarProductosComandasInacabadas1();
		}


			public void EnviarProductosComandasInacabadas1() {
				try {
					System.out.println("LocalHost = " + InetAddress.getLocalHost().toString());
				} catch (UnknownHostException uhe) {
					System.err.println("No puedo saber la dirección IP local : " + uhe);
				}
				// Abrimos un "Socket de Servidor" TCP en el puerto 4444.
				ServerSocket serverSocket = null;
				try {
					serverSocket = new ServerSocket(4448);
				} catch (IOException ex) {
					System.out.println("No se ha podido crear el servidor en este puerto.");
				}

				Socket socket = null;
				ObjectInputStream in = null;
				ObjectOutputStream salidaDatos = null;
				while (true) {
					try {
						socket = serverSocket.accept();
						  in = new ObjectInputStream(socket.getInputStream());
						 int mesa= in.readInt();
						 in.close();
						  salidaDatos = new ObjectOutputStream(socket.getOutputStream());
						 salidaDatos.writeObject(AccesSQL.recuperarComandaInacabada(mesa, 0));
						 salidaDatos.close();
						System.out.println("Productos de comanda inacabada enviada de la mesa "+mesa);

						socket.close();
					} catch (IOException ex) {
						System.out.println("No se pudo aceptar la conexion.");
					}
				}
			}
}

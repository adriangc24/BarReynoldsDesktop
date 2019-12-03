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
				int mesa=AccesSQL.cargarMesasBBDD();
				while (true) {
					try {
						ArrayList<ArrayList<Producto>>comandasInacab=new ArrayList<>();
						socket = serverSocket.accept();
						 // in = new ObjectInputStream(socket.getInputStream());
						// int mesas= in.readInt();
						
						for(int i=1;i<=mesa;i++) {
							ArrayList<Producto> ap=new ArrayList<>();
							
							if(AccesSQL.recuperarComandaInacabada(i, 0) != null) {
								ap=AccesSQL.recuperarComandaInacabada(i, 0);
								comandasInacab.add(i-1,ap);
								//System.out.println(ap.get(0).toString());
								System.out.println("Productos de comanda inacabada enviada de la mesa "+i);
							}
						}
						/*if( AccesSQL.recuperarComandaInacabada(mesas, 0)!=null) {
							salidaDatos = new ObjectOutputStream(socket.getOutputStream());
							 salidaDatos.writeObject(AccesSQL.recuperarComandaInacabada(mesas, 0));
						}*/
						  salidaDatos = new ObjectOutputStream(socket.getOutputStream());
						 salidaDatos.writeObject(comandasInacab);
						 salidaDatos.close();
						 //in.close();

						socket.close();
					} catch (IOException ex) {
						System.out.println("No se pudo aceptar la conexion.");
					}
				}
			}
}

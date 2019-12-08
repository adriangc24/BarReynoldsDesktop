package com.example.barreynoldsapp1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;

public class EnviarDatos {

	public EnviarDatos() {
		enviarCamareros_Mesas_Categorias();

	}

	public void enviarCamareros_Mesas_Categorias() {
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
		ArrayList<String> listaCategoria = AccesSQL.cargarCategorias();
		int numMes = AccesSQL.cargarMesasBBDD();
		while (true) {
			try {
				socket = serverSocket.accept();

				ObjectOutputStream salidaDatos = new ObjectOutputStream(socket.getOutputStream());
				// ObjectOutputStream salidaCategorias = new
				// ObjectOutputStream(socket.getOutputStream());
				salidaDatos.writeObject(listaCambrers);
				salidaDatos.writeObject(numMes);
				salidaDatos.writeObject(listaCategoria);
				salidaDatos.writeObject(AccesSQL.arrayTodosProductos());
				for (int i=0;i<listaCategoria.size();i++)
				{
					salidaDatos.writeObject(AccesSQL.cargarProductos(listaCategoria.get(i)));
				}
				 ObjectOutputStream numMesas=new ObjectOutputStream(socket.getOutputStream());
				 numMesas.writeObject(numMesas);
				 //salidaCategorias.writeObject(listaCategoria);

				System.out.println("listas enviadas");
				salidaDatos.close();
				socket.close();
			} catch (IOException ex) {
				System.out.println("No se pudo aceptar la conexion.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void enviarProductos() {
		try {
			System.out.println("LocalHost = " + InetAddress.getLocalHost().toString());
		} catch (UnknownHostException uhe) {
			System.err.println("No puedo saber la dirección IP local : " + uhe);
		}
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(4446);
		} catch (IOException ex) {
			System.out.println("No se ha podido crear el servidor en este puerto.");
		}

		Socket socket = null;
		ArrayList<Producto> listaProducto = null;

		while (true) {
			try {
				socket = serverSocket.accept();
				/*
				 * aqui llamamos al metodo de recoger productos de la clase
				 * accesql(cargarProductos(string categoriapasada por el cliente)) para rellenar
				 * la array de productos pero necesitamos que el cliente nos envie la categoria
				 * seleccionada
				 
				BufferedReader entrada = new BufferedReader(new ObjectInputStream(socket.getInputStream()));

	            while((mensajeServidor = entrada.readLine()) != null) //Mientras haya mensajes desde el cliente
	            {
	                //Se muestra por pantalla el mensaje recibido
	                System.out.println(mensajeServidor);
	            }
	            */


				
				ObjectOutputStream salidaProducto = new ObjectOutputStream(socket.getOutputStream());
				salidaProducto.writeObject(listaProducto);
				System.out.println("productos enviadas");
				salidaProducto.close();
				socket.close();
			} catch (IOException ex) {
				System.out.println("No se pudo aceptar la conexion.");
			}
		}
	}
}

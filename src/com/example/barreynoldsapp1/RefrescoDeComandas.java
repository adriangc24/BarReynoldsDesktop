package com.example.barreynoldsapp1;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class RefrescoDeComandas {
public RefrescoDeComandas() {
	RefrescoDeComandas1();
}


	public void RefrescoDeComandas1() {
		String camareroMesa;
		ArrayList<Integer>mesasInacabadas=new ArrayList<Integer>();
		int numMes = AccesSQL.cargarMesasBBDD();
		for(int i=0;i<numMes;i++) {
			if(AccesSQL.saberMesasConComandasInacabadas(i+1)) {
				mesasInacabadas.add(i+1);
			}
		}
		try {
			System.out.println("LocalHost = " + InetAddress.getLocalHost().toString());
		} catch (UnknownHostException uhe) {
			System.err.println("No puedo saber la dirección IP local : " + uhe);
		}
		// Abrimos un "Socket de Servidor" TCP en el puerto 4444.
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(4447);
		} catch (IOException ex) {
			System.out.println("No se ha podido crear el servidor en este puerto.");
		}

		Socket socket = null;
		
		while (true) {
			try {
				socket = serverSocket.accept();

				ObjectOutputStream salidaDatos = new ObjectOutputStream(socket.getOutputStream());
				salidaDatos.writeObject(mesasInacabadas);
				//ArrayList<Producto>arp=AccesSQL.recuperarComandaInacabada(i+1, 0);
				//if(arp!=null) {
			//	salidaDatos.writeInt(i+1);
				//salidaDatos.writeObject(arp);
				System.out.println("Comanda inacabada enviadas de las mesas ");
				//}

				salidaDatos.close();
				socket.close();
			} catch (IOException ex) {
				System.out.println("No se pudo aceptar la conexion.");
			}
		}
	}
}

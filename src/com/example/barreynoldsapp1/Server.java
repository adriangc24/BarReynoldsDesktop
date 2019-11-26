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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Server {

	/*
	 * public static void main(String[] args) { Server sv = new Server(); }
	 */

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
		while (true) {
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
				File file = new File("Comandes" + File.separatorChar + "ArchivoRecibido.xml");
				try {
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					factory.setNamespaceAware(true);
					DocumentBuilder builder = factory.newDocumentBuilder();
					Document doc = builder.parse(file);
					NodeList nList = doc.getElementsByTagName("mesa");
					String taula = nList.item(0).getTextContent();
					byte[] fileContent = Files.readAllBytes(file.toPath());
					file.delete();
					Document docComanda = builder.parse(new ByteArrayInputStream(fileContent));
					DOMSource source = new DOMSource(docComanda);
					FileWriter writer = new FileWriter(
							new File("Comandes" + File.separatorChar + "ComandaTaula" + taula + ".xml"));
					StreamResult result = new StreamResult(writer);
					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					transformer.transform(source, result);
					AccesSQL.pujarComanda(taula);

					NodeList nomProducte = doc.getElementsByTagName("nombre");
					NodeList listaQuantitat = doc.getElementsByTagName("cantidad");
					NodeList data = doc.getElementsByTagName("fecha");
					String producte;
					int quantitat;
					for (int i = 0; i < nomProducte.getLength(); i++) {
						producte = nomProducte.item(i).getTextContent();
						quantitat = Integer.parseInt(listaQuantitat.item(i).getTextContent());
						AccesSQL.pujarRelacioComandaProducte(quantitat, producte, data.item(0).getTextContent());

					}
				} catch (Exception e) {
					System.out.println("Esto peta");
				}
				out.close();
				in.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

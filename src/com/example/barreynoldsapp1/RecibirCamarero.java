package com.example.barreynoldsapp1;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class RecibirCamarero implements Serializable {
	private static final long serialVersionUID = 1733521708430895847L;

	public RecibirCamarero() {
		main();
	}

	public void main() {
		Socket socket = null;
		ObjectInputStream in;
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(4545);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		while (true) {
			try {

				System.out.println("LocalHost = " + InetAddress.getLocalHost().toString());
				socket = serverSocket.accept();
				in = new ObjectInputStream(socket.getInputStream());
				// Cambrer c = (Cambrer) in.readObject();
				// System.out.println(c.toString());

				in.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}

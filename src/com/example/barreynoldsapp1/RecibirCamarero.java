package com.example.barreynoldsapp1;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.file.Files;

import javax.imageio.ImageIO;
import javax.imageio.spi.ImageInputStreamSpi;
import javax.imageio.stream.ImageInputStream;
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
		try {
			main();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void main() throws ClassNotFoundException {
		Socket socket = null;
		ObjectInputStream in,in2;
		ServerSocket serverSocket = null;
		ServerSocket serverSocket2 = null;
		RenderedImage fotoCamarero;
		BufferedImage bImage;

		try {
			serverSocket = new ServerSocket(4545);
			serverSocket2 = new ServerSocket(4546);

			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		while (true) {
			try {

				System.out.println("LocalHost = " + InetAddress.getLocalHost().toString());
				socket = serverSocket.accept();

				in = new ObjectInputStream(socket.getInputStream());
				Cambrer c = (Cambrer) in.readObject();
				System.out.println(c.toString());

				in.close();
				socket.close();
				
				// Abrimos 2o Socket para recibir la foto
				socket = serverSocket2.accept();
				in2 = new ObjectInputStream(socket.getInputStream());				
				try {
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
				      byte [] data = bos.toByteArray();
					ByteArrayInputStream bis = new ByteArrayInputStream(data);
					ObjectInputStream inputStreamn = null;
					try {
						inputStreamn = new ObjectInputStream(bis);
					  Object o = inputStreamn.readObject(); 
					} finally {
					  try {
					    if (inputStreamn != null) {
					    	inputStreamn.close();
					    }
					  } catch (IOException ex) {
					    // ignore close exception
					  }
					}
					
				      System.out.println("image created");
				}
				catch(Exception e) {
					// Default Image de camarero para cuando no pasen foto
					 bImage = ImageIO.read(new File("moon.jpg"));
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
				      ImageIO.write(bImage, "jpg", bos );
				      byte [] data = bos.toByteArray();
				      ByteArrayInputStream bis = new ByteArrayInputStream(data);
				      BufferedImage bImage2 = ImageIO.read(bis);
				      ImageIO.write(bImage2, "jpg", new File("output.jpg") );
				      System.out.println("Default image created");
				}
				
				in2.close();
				socket.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
	}
	public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
	    ByteArrayInputStream in = new ByteArrayInputStream(data);
	    ObjectInputStream is = new ObjectInputStream(in);
	    return is.readObject();
	}

}

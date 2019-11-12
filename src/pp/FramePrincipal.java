package pp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.InternalFrameUI;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.swing.JTabbedPane;

public class FramePrincipal extends JFrame {

	private JPanel contentPane;
	private static int numeroTaules = leerMesas();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System.out.println(numeroTaules);
					FramePrincipal frame = new FramePrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public FramePrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		for (int i = 1; i < numeroTaules + 1; i++) {
			FrameInterno intFrame = new FrameInterno() {
				public void setUI(InternalFrameUI ui) {
					super.setUI(ui);
					BasicInternalFrameUI frameUI = (BasicInternalFrameUI) getUI();
					if (frameUI != null)
						frameUI.setNorthPane(null);
				}
			};
			intFrame.setTitle("Taula" + i);
			introducirComanda(intFrame);
			Component tab = intFrame;
			tabbedPane.addTab("Taula" + i, tab);
		}

		// SERVER
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
			System.out.println("Can't setup server on this port number. ");
		}

		Socket socket = null;
		InputStream in = null;
		OutputStream out = null;

		try {
			socket = serverSocket.accept();
		} catch (IOException ex) {
			System.out.println("Can't accept client connection. ");
		}

		try {
			in = socket.getInputStream();
		} catch (IOException ex) {
			System.out.println("Can't get socket input stream. ");
		}

		try {
			out = new FileOutputStream("Comandes" + File.separatorChar + "aaa.txt");
		} catch (FileNotFoundException ex) {
			System.out.println("File not found. ");
		}

		byte[] bytes = new byte[16 * 1024];

		int count;
		try {
			while ((count = in.read(bytes)) > 0) {
				out.write(bytes, 0, count);
			}
			out.close();
			in.close();
			socket.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int leerMesas() {
		try {
			File file = new File("config.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			NodeList nList = doc.getElementsByTagName("numeroTaulas");
			Element element = (Element) nList.item(0);
			return Integer.parseInt(element.getTextContent());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void introducirComanda(FrameInterno intFrame) {
		for (int i = 1; i < numeroTaules + 1; i++) {
			if (intFrame.getTitle().equals("Taula" + i)) {
				try {
					File file = new File("Comandes" + File.separatorChar + "ComandaTaula" + i + ".xml");
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(file);
					NodeList nList = doc.getElementsByTagName("producto");
					for (int j = 0; j < nList.getLength(); j++) {
						Element element = (Element) nList.item(j);
						String nombre = element.getElementsByTagName("nombre").item(0).getTextContent();
						String precio = element.getElementsByTagName("precio").item(0).getTextContent();
						String cantidad = element.getElementsByTagName("cantidad").item(0).getTextContent();
						intFrame.model.addRow(new Object[] { cantidad, nombre, precio, false });
					}
				} catch (Exception e) {
				}
			}
		}
	}
}
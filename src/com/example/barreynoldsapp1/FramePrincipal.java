package com.example.barreynoldsapp1;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.InternalFrameUI;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.swing.JTabbedPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JInternalFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class FramePrincipal extends JFrame {

	static JPanel contentPane;
	static FramePrincipal frame;
	static JTabbedPane tabbedPane;
	static JMenuBar menuBar;
	static JInternalFrame internalFrame;
	static boolean registrado = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new FramePrincipal();
					frame.setVisible(true);
					//arrancarServer();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public FramePrincipal() {
		// int numeroTaules = leerMesas();
		int numeroTaules = AccesSQL.cargarMesasBBDD();
		System.out.println(numeroTaules);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setVisible(false);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		menuBar = new JMenuBar();
		menuBar.setVisible(true);
		contentPane.add(menuBar, BorderLayout.NORTH);

		JMenu mnConfiguracio = new JMenu("Configuracio");
		menuBar.add(mnConfiguracio);

		JMenuItem mntmModifTaules = new JMenuItem("Cambiar total de taules");
		mntmModifTaules.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FrameConfigTaules frameConfig = new FrameConfigTaules();
				frameConfig.setVisible(true);
			}
		});
		mnConfiguracio.add(mntmModifTaules);

		JMenu mnPantalla = new JMenu("Pantalla");
		menuBar.add(mnPantalla);

		JMenuItem mntmPrincipal = new JMenuItem("Principal");
		mntmPrincipal.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setVisible(false);
			}
		});
		mnPantalla.add(mntmPrincipal);

		JMenuItem mntmTaules = new JMenuItem("Taules");
		mntmTaules.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setVisible(true);
			}
		});
		mnPantalla.add(mntmTaules);
		generarTaules(tabbedPane, numeroTaules);
		/*if (!registrado) {
			generarLogin(contentPane);
		}*/
	}

	public static void refreshFrame() {
		frame.dispose();
		frame = new FramePrincipal();
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

	public static void arrancarServer() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Server s1 = new Server();
				// EnviarListaCambrers elc=new EnviarListaCambrers();
			}
		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// EnviarListaCambrers elc = new EnviarListaCambrers();
				EnviarDatos ed = new EnviarDatos();
			}
		}).start();

		/*
		 * new Thread(new Runnable() {
		 * 
		 * @Override public void run() { // TODO Auto-generated method stub try {
		 * EnviarListaProductos elp = new EnviarListaProductos(); } catch (SQLException
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); } } }).start();
		 */

	}

	public static void introducirComanda(FrameInterno intFrame, int numeroTaules) {
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

	public static void introducirCambrer(FrameInterno intFrame, int numeroTaules) {
		for (int i = 1; i < numeroTaules + 1; i++) {
			if (intFrame.getTitle().equals("Taula" + i)) {
				try {
					File file = new File("Comandes" + File.separatorChar + "ComandaTaula" + i + ".xml");
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(file);

					NodeList nList = doc.getElementsByTagName("camarero");
					intFrame.lblCambrer.setText("Cambrer: " + nList.item(0).getTextContent());
				} catch (Exception e) {
				}
			}
		}
	}

	public static void introducirNumTaula(FrameInterno intFrame, int numeroTaules) {
		for (int i = 1; i < numeroTaules + 1; i++) {
			if (intFrame.getTitle().equals("Taula" + i)) {
				try {
					File file = new File("Comandes" + File.separatorChar + "ComandaTaula" + i + ".xml");
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(file);

					NodeList nList = doc.getElementsByTagName("mesa");
					intFrame.lblTaula.setText("Taula: " + nList.item(0).getTextContent());
				} catch (Exception e) {
				}
			}
		}
	}

	public static void introducirData(FrameInterno intFrame, int numeroTaules) {
		for (int i = 1; i < numeroTaules + 1; i++) {
			if (intFrame.getTitle().equals("Taula" + i)) {
				try {
					File file = new File("Comandes" + File.separatorChar + "ComandaTaula" + i + ".xml");
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(file);

					NodeList nList = doc.getElementsByTagName("fecha");
					String[] parts = nList.item(0).getTextContent().split(" ");
					String data = parts[0];
					String hora = parts[1];
					intFrame.lblData.setText("Data: " + data + " Hora: " + hora);
				} catch (Exception e) {
				}
			}
		}
	}

	public static void generarTaules(JTabbedPane tabbedPane, int numeroTaules) {
		for (int i = 1; i < numeroTaules + 1; i++) {
			FrameInterno intFrame = new FrameInterno("Taula" + i) {
				public void setUI(InternalFrameUI ui) {
					super.setUI(ui);
					BasicInternalFrameUI frameUI = (BasicInternalFrameUI) getUI();
					if (frameUI != null)
						frameUI.setNorthPane(null);
				}
			};
			intFrame.setTitle("Taula" + i);
			introducirCambrer(intFrame, numeroTaules);
			introducirNumTaula(intFrame, numeroTaules);
			introducirData(intFrame, numeroTaules);
			introducirComanda(intFrame, numeroTaules);
			generarArxiusComanda(numeroTaules);
			Component tab = intFrame;
			intFrame.lblPrecioTotal.setText(String.valueOf(intFrame.sumarPrecioProductos()));
			
			tabbedPane.addTab("Taula" + i, tab);
		}
	}

	public static void generarLogin(JPanel contentPane) {
		internalFrame = new JInternalFrame("New JInternalFrame") {
			public void setUI(InternalFrameUI ui) {
				super.setUI(ui);
				BasicInternalFrameUI frameUI = (BasicInternalFrameUI) getUI();
				if (frameUI != null)
					frameUI.setNorthPane(null);
			}
		};
		contentPane.add(internalFrame, BorderLayout.CENTER);
		internalFrame.setVisible(true);
		internalFrame.setBorder(null);
		Login login = new Login();
		internalFrame.add(login);
	}

	public static void generarArxiusComanda(int numeroTaules) {
		for (int i = 1; i < numeroTaules + 1; i++) {
			try {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.newDocument();
				Element rootElement = doc.createElement("comanda");
				doc.appendChild(rootElement);
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				File file = new File("Comandes" + File.separatorChar + "ComandaTaula" + i + ".xml");
				StreamResult result = new StreamResult(file);
				if (!file.exists()) {
					transformer.transform(source, result);
				}
			} catch (ParserConfigurationException pce) {
				pce.printStackTrace();
			} catch (TransformerException tfe) {
				tfe.printStackTrace();
			}
		}
	}
}
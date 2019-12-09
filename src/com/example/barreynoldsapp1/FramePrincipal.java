package com.example.barreynoldsapp1;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

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
import javax.swing.JLayeredPane;
import java.awt.GridBagLayout;

public class FramePrincipal extends JFrame {

	static JPanel contentPane, panelBarra;
	static FramePrincipal frame;
	static JTabbedPane tabbedPaneTaulas;
	static JMenuBar menuBar;
	static JInternalFrame internalFrame;
	static boolean registrado = false;
	static int numeroTaules;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new FramePrincipal();
					frame.setVisible(true);
					arrancarServer();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public FramePrincipal() {
		int numeroTaules = AccesSQL.cargarMesasBBDD();
		System.out.println(numeroTaules);
		for (int i = 0; i < numeroTaules; i++) {
			ArrayList<Producto> arp = AccesSQL.recuperarComandaInacabada(i + 1, 0);
			if (arp != null) {

			}
		}
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		menuBar = new JMenuBar();
		menuBar.setBounds(5, 5, 784, 21);
		menuBar.setVisible(true);
		contentPane.add(menuBar);

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

		JMenuItem mntmPrincipal = new JMenuItem("Barra");
		mntmPrincipal.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPaneTaulas.setVisible(false);
				panelBarra.setVisible(true);
			}
		});
		mnPantalla.add(mntmPrincipal);

		JMenuItem mntmTaules = new JMenuItem("Cuina");
		mntmTaules.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPaneTaulas.setVisible(true);
				panelBarra.setVisible(false);
			}
		});
		mnPantalla.add(mntmTaules);

		tabbedPaneTaulas = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneTaulas.setBounds(5, 26, 784, 541);
		tabbedPaneTaulas.setVisible(false);
		contentPane.add(tabbedPaneTaulas);
		generarTaulesCuina(tabbedPaneTaulas, numeroTaules);

		panelBarra = new JPanel();
		panelBarra.setBounds(5, 26, 784, 541);
		panelBarra.setVisible(false);
		contentPane.add(panelBarra);
		panelBarra.setLayout(new BorderLayout(0, 0));

		FrameBarra frameBarra = new FrameBarra() {
			public void setUI(InternalFrameUI ui) {
				super.setUI(ui);
				BasicInternalFrameUI frameUI = (BasicInternalFrameUI) getUI();
				if (frameUI != null)
					frameUI.setNorthPane(null);
			}
		};
		panelBarra.add(frameBarra);

		
		//  if (!registrado) { generarLogin(contentPane); }
		 
	}

	public static void refreshFrame() {
		frame.dispose();
		frame = new FramePrincipal();
	}

	public static void arrancarServer() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Server s1 = new Server();
			}
		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				EnviarDatos ed = new EnviarDatos();
			}
		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				RefrescoDeComandas rfc = new RefrescoDeComandas();
			}
		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				EnviarProductosComandasInacabadas epci = new EnviarProductosComandasInacabadas();
			}
		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				RecibirCamarero rec = new RecibirCamarero();
			}
		}).start();
		
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
					String data = nList.item(0).getTextContent();
					intFrame.lblData.setText("Data: " + data);
				} catch (Exception e) {
				}
			}
		}
	}

	public static void generarTaulesCuina(JTabbedPane tabbedPane, int numeroTaules) {
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
			//introducirNumTaula(intFrame, numeroTaules);
			intFrame.lblTaula.setText("Taula: "+i);
			introducirData(intFrame, numeroTaules);
			introducirComanda(intFrame, numeroTaules);
			generarArxiusComanda(numeroTaules);
			Component tab = intFrame;
			// intFrame.lblPrecioTotal.setText(String.valueOf(intFrame.sumarPrecioProductos()));

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
		internalFrame.getContentPane().add(login);
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
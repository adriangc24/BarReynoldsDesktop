
package com.example.barreynoldsapp1;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Component;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.plaf.InternalFrameUI;
import javax.swing.plaf.basic.BasicInternalFrameUI;

public class FrameBarra extends JInternalFrame implements ActionListener {
	static JPanel begudes, tapes, plats, entrepans;
	ArrayList<String> listaCategorias;
	static ArrayList<JPanel> llistaPanelProductes;
	static JTabbedPane tabbedPane;

	public FrameBarra() {
		listaCategorias = AccesSQL.cargarCategorias();
		llistaPanelProductes = new ArrayList<>();
		setBorder(null);
		setBounds(100, 100, 633, 412);
		getContentPane().setLayout(null);
		setVisible(true);

		JPanel panelCategoria = new JPanel();
		panelCategoria.setBounds(-66, 292, 200, 268);
		getContentPane().add(panelCategoria);
		GridBagLayout gbl_panelCategoria = new GridBagLayout();
		panelCategoria.setLayout(gbl_panelCategoria);

		GridBagConstraints constraints = new GridBagConstraints();

		// Introducir botones de categoria
		int contador = 0;
		int valorColumna = 0;

		JPanel panelProductes = new JPanel();
		panelProductes.setBounds(144, 337, 479, 268);
		getContentPane().add(panelProductes);
		panelProductes.setLayout(new CardLayout(0, 0));

		/*
		 * String stringBegudes = "Card amb begudes"; String stringTapes =
		 * "Card amb tapes"; String stringPlats = "Card amb plats"; String
		 * stringEntrepans = "Card amb entrepans";
		 */
		for (int i = 0; i < listaCategorias.size(); i++) {
			JPanel jpc = new JPanel();
			jpc.setName(listaCategorias.get(i));
			FlowLayout flowLayout = (FlowLayout) jpc.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			llistaPanelProductes.add(jpc);
			panelProductes.add(jpc, "Card amb " + listaCategorias.get(i).toLowerCase());
		}
		/*
		 * begudes = new JPanel(); begudes.setName("begudes"); FlowLayout flowLayout =
		 * (FlowLayout) begudes.getLayout(); flowLayout.setAlignment(FlowLayout.LEFT);
		 * 
		 * tapes = new JPanel(); tapes.setName("tapes"); FlowLayout flowLayout_1 =
		 * (FlowLayout) tapes.getLayout(); flowLayout_1.setAlignment(FlowLayout.LEFT);
		 * 
		 * plats = new JPanel(); plats.setName("plats"); FlowLayout flowLayout_2 =
		 * (FlowLayout) plats.getLayout(); flowLayout_2.setAlignment(FlowLayout.LEFT);
		 * 
		 * entrepans = new JPanel(); entrepans.setName("entrepans"); FlowLayout
		 * flowLayout_3 = (FlowLayout) entrepans.getLayout();
		 * flowLayout_3.setAlignment(FlowLayout.LEFT);
		 * 
		 * ArrayList<JPanel> llistaPanelProductes = new ArrayList<JPanel>();
		 * llistaPanelProductes.add(begudes); llistaPanelProductes.add(tapes);
		 * llistaPanelProductes.add(plats); llistaPanelProductes.add(entrepans);
		 * 
		 * panelProductes.add(begudes, stringBegudes); panelProductes.add(tapes,
		 * stringTapes); panelProductes.add(plats, stringPlats);
		 * panelProductes.add(entrepans, stringEntrepans);
		 */

		for (int i = 0; i < listaCategorias.size(); i++) {
			BufferedImage img = null;
			try {
				img = ImageIO.read(new File("Imatges" + File.separatorChar + listaCategorias.get(i) + ".png"));
				Image newimg = img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
				Icon icon = new ImageIcon(newimg);
				JButton botonCategoria = new JButton(icon);
				// botonCategoria.setText(listaCategorias.get(i));
				botonCategoria.addActionListener(this);
				botonCategoria.setBackground(Color.BLACK);
				botonCategoria.setForeground(Color.BLACK);
				botonCategoria.setToolTipText(listaCategorias.get(i));
				constraints.gridy = i;
				panelCategoria.add(botonCategoria, constraints);
				contador++;
				if (contador == 4) {
					valorColumna++;
					constraints.gridx = valorColumna;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/*
		 * JPanel panelProductes = new JPanel(); panelProductes.setBounds(144, 279, 479,
		 * 281); getContentPane().add(panelProductes); panelProductes.setLayout(new
		 * CardLayout(0, 0)); String begudes = "Card amb begudes"; String tapes =
		 * "Card amb tapes"; String plats = "Card amb plats"; String entrepans =
		 * "Card amb entrepans"; JPanel card1 = new JPanel(); JPanel card2 = new
		 * JPanel(); JPanel card3 = new JPanel(); JPanel card4 = new JPanel();
		 * panelProductes.add(card1, begudes); panelProductes.add(card2, tapes);
		 * panelProductes.add(card3, plats); panelProductes.add(card4, entrepans);
		 */

		// cuando pongamos la tabla de productos donde esta el 50 habra que poner
		// el precio de la comanda
		NumberPanel numberPanel = new NumberPanel(50);
		numberPanel.setBounds(347, 0, 448, 289);
		getContentPane().add(numberPanel);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 337, 281);
		generarTaulesBarra(tabbedPane, AccesSQL.cargarMesasBBDD());
		insertarImatgesProductes("begudes", 1);
		insertarImatgesProductes("tapes", 2);
		insertarImatgesProductes("plats", 3);
		insertarImatgesProductes("entrepans", 4);
		getContentPane().add(tabbedPane);

	}

	public static void generarTaulesBarra(JTabbedPane tabbedPane, int numeroTaules) {
		for (int i = 1; i < numeroTaules + 1; i++) {
			FrameInternoBarra intFrameBarra = new FrameInternoBarra("Taula" + i) {
				public void setUI(InternalFrameUI ui) {
					super.setUI(ui);
					BasicInternalFrameUI frameUI = (BasicInternalFrameUI) getUI();
					if (frameUI != null)
						frameUI.setNorthPane(null);
				}
			};
			intFrameBarra.setTitle("Taula" + i);
			introducirComanda(intFrameBarra, numeroTaules);
			Component tab = intFrameBarra;

			tabbedPane.addTab("Taula" + i, tab);
		}
	}

	public static void introducirComanda(FrameInternoBarra intFrameBarra, int numeroTaules) {
		for (int i = 1; i < numeroTaules + 1; i++) {
			if (intFrameBarra.getTitle().equals("Taula" + i)) {
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
						intFrameBarra.model.addRow(new Object[] { cantidad, nombre, precio });
					}
				} catch (Exception e) {
				}
			}
		}
	}

	public static void insertarImatgesProductes(String categoria, int ID_Categoria) {
		File file = new File("PRODUCTES.xml");
		// esta funcion pilla la ruta de el xml de productos, tenemos que cogerlo de la
		// bbdd
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(file);
			NodeList nList = doc.getElementsByTagName("imatge");
			for (int i = 0; i < nList.getLength(); i++) {
				if (nList.item(i).getParentNode().getParentNode().getNodeName().equalsIgnoreCase(categoria)) {
					BufferedImage img = ImageIO
							.read(new File("Imatges" + File.separatorChar + nList.item(i).getTextContent()));
					Image newimg = img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
					Icon icon = new ImageIcon(newimg);
					JButton botonProducte = new JButton(icon);
					for (int l = 0; l < llistaPanelProductes.size(); l++) {
						if (llistaPanelProductes.get(l).getName().equalsIgnoreCase(categoria)) {
							llistaPanelProductes.get(l).add(botonProducte);
						}
					}
					/*
					 * switch (ID_Categoria) { case 1: begudes.add(botonProducte); break; case 2:
					 * tapes.add(botonProducte); break; case 3: plats.add(botonProducte); break;
					 * case 4: entrepans.add(botonProducte); break; }
					 */
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b1 = (JButton) e.getSource();
		// System.out.println(b1.getToolTipText());
		for (int j = 0; j < listaCategorias.size(); j++) {
			if (listaCategorias.get(j).equals(b1.getToolTipText())) {
				// System.out.println(b1.getToolTipText()+" true");
				llistaPanelProductes.get(j).setVisible(true);
			} else {
				// System.out.println(b1.getToolTipText()+" else");
				llistaPanelProductes.get(j).setVisible(false);
			}
		}
	}
}

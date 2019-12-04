
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
import java.awt.CardLayout;
import java.awt.FlowLayout;

public class FrameBarra extends JInternalFrame {
	static JPanel begudes, tapes, plats, entrepans;

	public FrameBarra() {
		final ArrayList<String> listaCategorias = AccesSQL.cargarCategorias();
		setBorder(null);
		setBounds(100, 100, 633, 412);
		getContentPane().setLayout(null);
		setVisible(true);

		JPanel panelComanda = new JPanel();
		panelComanda.setBounds(0, 0, 293, 248);
		getContentPane().add(panelComanda);
		
		
		
		JScrollPane scrollPane = new JScrollPane();
		panelComanda.add(scrollPane);

		JPanel panelCategoria = new JPanel();
		panelCategoria.setBounds(-66, 279, 200, 281);
		getContentPane().add(panelCategoria);
		GridBagLayout gbl_panelCategoria = new GridBagLayout();
		panelCategoria.setLayout(gbl_panelCategoria);

		GridBagConstraints constraints = new GridBagConstraints();

		// Introducir botones de categoria
		int contador = 0;
		int valorColumna = 0;

		JPanel panelProductes = new JPanel();
		panelProductes.setBounds(144, 279, 479, 281);
		getContentPane().add(panelProductes);
		panelProductes.setLayout(new CardLayout(0, 0));

		String stringBegudes = "Card amb begudes";
		String stringTapes = "Card amb tapes";
		String stringPlats = "Card amb plats";
		String stringEntrepans = "Card amb entrepans";

		begudes = new JPanel();
		begudes.setName("begudes");
		FlowLayout flowLayout = (FlowLayout) begudes.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);

		tapes = new JPanel();
		tapes.setName("tapes");
		FlowLayout flowLayout_1 = (FlowLayout) tapes.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);

		plats = new JPanel();
		plats.setName("plats");
		FlowLayout flowLayout_2 = (FlowLayout) plats.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);

		entrepans = new JPanel();
		entrepans.setName("entrepans");
		FlowLayout flowLayout_3 = (FlowLayout) entrepans.getLayout();
		flowLayout_3.setAlignment(FlowLayout.LEFT);

		ArrayList<JPanel> llistaPanelProductes = new ArrayList<JPanel>();
		llistaPanelProductes.add(begudes);
		llistaPanelProductes.add(tapes);
		llistaPanelProductes.add(plats);
		llistaPanelProductes.add(entrepans);

		panelProductes.add(begudes, stringBegudes);
		panelProductes.add(tapes, stringTapes);
		panelProductes.add(plats, stringPlats);
		panelProductes.add(entrepans, stringEntrepans);

		for (int i = 0; i < listaCategorias.size(); i++) {
			BufferedImage img = null;
			try {
				img = ImageIO.read(new File("Imatges" + File.separatorChar + listaCategorias.get(i) + ".png"));
				Image newimg = img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
				Icon icon = new ImageIcon(newimg);
				JButton botonCategoria = new JButton(icon);
				botonCategoria.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						for (int j = 0; j < llistaPanelProductes.size(); j++) {
							if (llistaPanelProductes.get(j).getName().equals(listaCategorias.get(j))) {
								llistaPanelProductes.get(j).setVisible(true);
							} else {
								llistaPanelProductes.get(j).setVisible(false);
							}
						}
					}
				});
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

		/*JPanel panelProductes = new JPanel();
		panelProductes.setBounds(144, 279, 479, 281);
		getContentPane().add(panelProductes);
		panelProductes.setLayout(new CardLayout(0, 0));
		String begudes = "Card amb begudes";
		String tapes = "Card amb tapes";
		String plats = "Card amb plats";
		String entrepans = "Card amb entrepans";
		JPanel card1 = new JPanel();
		JPanel card2 = new JPanel();
		JPanel card3 = new JPanel();
		JPanel card4 = new JPanel();
		panelProductes.add(card1, begudes);
		panelProductes.add(card2, tapes);
		panelProductes.add(card3, plats);
		panelProductes.add(card4, entrepans);*/
		
		JPanel panel = new JPanel();
		NumberPanel numberPanel=new NumberPanel();
		numberPanel.setBounds(305, 0, 448, 289);
		getContentPane().add(numberPanel);
		//insertarImatgesProductes("begudes");
		insertarImatgesProductes("begudes", 1);
		insertarImatgesProductes("tapes", 2);
		insertarImatgesProductes("plats", 3);
		insertarImatgesProductes("entrepans", 4);
	}

	public static void insertarImatgesProductes(String categoria, int ID_Categoria) {
		File file = new File("PRODUCTES.xml");
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(file);
			NodeList nList = doc.getElementsByTagName("imatge");
			for (int i = 0; i < nList.getLength(); i++) {
				if (nList.item(i).getParentNode().getParentNode().getNodeName() == categoria) {
					BufferedImage img = ImageIO
							.read(new File("Imatges" + File.separatorChar + nList.item(i).getTextContent()));
					Image newimg = img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
					Icon icon = new ImageIcon(newimg);
					JButton botonProducte = new JButton(icon);
					switch (ID_Categoria) {
					case 1:
						begudes.add(botonProducte);
						break;
					case 2:
						tapes.add(botonProducte);
						break;
					case 3:
						plats.add(botonProducte);
						break;
					case 4:
						entrepans.add(botonProducte);
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

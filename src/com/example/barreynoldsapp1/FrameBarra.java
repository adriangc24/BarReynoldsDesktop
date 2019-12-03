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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.CardLayout;

public class FrameBarra extends JInternalFrame {

	public FrameBarra() {
		ArrayList<String> listaCategorias = new ArrayList<String>();
		setBorder(null);
		listaCategorias = AccesSQL.cargarCategorias();
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
		for (int i = 0; i < listaCategorias.size(); i++) {
			BufferedImage img = null;
			try {
				img = ImageIO.read(new File("Imatges" + File.separatorChar + listaCategorias.get(i) + ".png"));
				Image newimg = img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
				Icon icon = new ImageIcon(newimg);
				JButton botonCategoria = new JButton(icon);
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

		JPanel panelProductes = new JPanel();
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
		panelProductes.add(card4, entrepans);
		insertarImatgesProductes("begudes");
	}

	public static void insertarImatgesProductes(String categoria) {
		File file = new File("PRODUCTES.xml");
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(file);
			NodeList nList = doc.getElementsByTagName("imatge");
			for (int i = 0; i < nList.getLength(); i++) {
				if (nList.item(i).getParentNode().getParentNode().getNodeName() == categoria) {
					//Ahora seria sacar la imagen de cada uno y pimpam crear botones con la imagen en el card
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package com.example.barreynoldsapp1;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;

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
		
		JLayeredPane panelProductes = new JLayeredPane();
		panelProductes.setBounds(144, 279, 477, 281);
		getContentPane().add(panelProductes);
		
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
		
		// Introducir botones de productos
		
	}
}

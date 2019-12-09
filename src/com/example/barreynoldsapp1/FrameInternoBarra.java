package com.example.barreynoldsapp1;

import javax.swing.JInternalFrame;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class FrameInternoBarra extends JInternalFrame {
	DefaultTableModel model = new DefaultTableModel();
	String nom;
	JTable tabla;
		
	public FrameInternoBarra(String nom) {
		setBounds(100, 100, 450, 300);
		this.nom = nom;
		setBorder(null);
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		model.addColumn("Quantitat");
		model.addColumn("Producte");
		model.addColumn("Preu");

		tabla = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(tabla);
		panel.add(scrollPane, BorderLayout.NORTH);
	}

}

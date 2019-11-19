package com.example.barreynoldsapp1;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.awt.event.ActionEvent;

public class FrameInterno extends JInternalFrame {
	JLabel lblCambrer, lblTaula, lblData;
	JTable table;
	JButton btnCobrar;
	String nom;
	DefaultTableModel model = new DefaultTableModel() {
		public Class getColumnClass(int column) {
			switch (column) {
			case 0:
				return String.class;
			case 1:
				return String.class;
			case 2:
				return String.class;
			default:
				return Boolean.class;
			}
		}
	};

	public FrameInterno(String nom) {
		this.nom = nom;
		setBorder(null);
		model.addColumn("Quantitat");
		model.addColumn("Producte");
		model.addColumn("Preu");
		model.addColumn("Preparat");
		JPanel panel = new JPanel();

		JPanel panel_1 = new JPanel();

		lblCambrer = new JLabel("Cambrer: ");
		lblCambrer.setFont(lblCambrer.getFont().deriveFont(13.0f));
		lblCambrer.setHorizontalAlignment(SwingConstants.CENTER);

		lblTaula = new JLabel("Taula: ");
		lblTaula.setFont(lblTaula.getFont().deriveFont(13.0f));
		lblTaula.setHorizontalAlignment(SwingConstants.CENTER);

		lblData = new JLabel("Data: Hora:");
		lblData.setFont(lblData.getFont().deriveFont(13.0f));
		lblData.setHorizontalAlignment(SwingConstants.CENTER);

		btnCobrar = new JButton("Cobrar");
		btnCobrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean entregats = true;
				for (int i = 0; i < table.getModel().getRowCount(); i++) {
					if (table.getModel().getValueAt(i, 3).equals(false)) {
						entregats = false;
					}
				}
				if (entregats == false) {
					JOptionPane.showMessageDialog(FrameInterno.this, "ERROR: Els productes no estan preparats", "ERROR",
							JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						generarFactura(nom);
					} catch (ParserConfigurationException e1) {
						e1.printStackTrace();
					} catch (SAXException e1) {
						e1.printStackTrace();
					} catch (TransformerException e1) {
						e1.printStackTrace();
					}
				}

			}
		});

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 448, Short.MAX_VALUE).addGap(10))
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)));

		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
						.addGroup(gl_panel_1
								.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createSequentialGroup().addContainerGap().addComponent(lblCambrer)
										.addGap(123)
										.addComponent(lblTaula, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGap(196).addComponent(lblData))
								.addComponent(btnCobrar, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE))
						.addContainerGap()));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(btnCobrar)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE).addComponent(lblCambrer)
								.addComponent(lblTaula).addComponent(lblData))));
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(new BorderLayout(0, 0));

		table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		panel.add(scrollPane, BorderLayout.NORTH);

		getContentPane().setLayout(groupLayout);

	}

	public static void generarFactura(String nombre)
			throws ParserConfigurationException, SAXException, TransformerException {
		File file = new File("Comandes" + File.separatorChar + "Comanda" + nombre + ".xml");
		try {
			byte[] fileContent = Files.readAllBytes(file.toPath());
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new ByteArrayInputStream(fileContent));
			DOMSource source = new DOMSource(doc);
			FileWriter writer = new FileWriter(new File("Factures" + File.separatorChar + "nuevaFactura.xml"));
			StreamResult result = new StreamResult(writer);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.transform(source, result);
		} catch (IOException e) {
			e.printStackTrace();
		}

		File factura = new File("Factures" + File.separatorChar + "nuevaFactura.xml");
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(factura);
			NodeList nList = doc.getElementsByTagName("fecha");
			String[] parts = nList.item(0).getTextContent().split(" ");
			String var = parts[1];
			String[] hora = var.split(":");
			String hora1 = hora[0];
			String minut = hora[1];
			String segon = hora[2];
			byte[] fileContent = Files.readAllBytes(factura.toPath());
			Document docFactura = builder.parse(new ByteArrayInputStream(fileContent));
			DOMSource source = new DOMSource(docFactura);
			FileWriter writer = new FileWriter(new File(
					"Factures" + File.separatorChar + "Factura_" + hora1 + "_" + minut + "_" + segon + ".xml"));
			StreamResult result = new StreamResult(writer);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.transform(source, result);
			file.delete();
			FramePrincipal.refreshFrame();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
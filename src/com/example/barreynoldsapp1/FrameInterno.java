package com.example.barreynoldsapp1;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.ScrollPane;
import java.awt.Component;
import javax.swing.table.TableModel;

public class FrameInterno extends JInternalFrame {
	JLabel lblCambrer, lblTaula, lblData;
	JTable table, table2;
	JButton btnCobrar;
	ArrayList<Float> precio;
	String nom;
	static float sumaTotal = 0;
	DefaultTableModel model2 = new DefaultTableModel() {
		public Class getColumnClass(int column) {
			switch (column) {
			case 0:
				return String.class;
			case 1:
				return String.class;
			default:
				return Boolean.class;
			}
		}
	};
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
	private JScrollPane scrollPane_1;

	public FrameInterno(String nom) {
		precio = new ArrayList<>();
		this.nom = nom;
		setBorder(null);
		model.addColumn("Quantitat");
		model.addColumn("Producte");
		model.addColumn("Preu");
		model.addColumn("Preparat");
		model2.addColumn("Qty");
		model2.addColumn("Producte");
		model2.addColumn("Retornat");
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
				if (sumaTotal == 0) {
					for (int i = 0; i < model.getRowCount(); i++) {
						sumaTotal = sumaTotal + Float.parseFloat(table.getValueAt(i, 0).toString())
								* Float.parseFloat(table.getValueAt(i, 2).toString());
					}
				}

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

		JPanel panel_2 = new JPanel();

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 840, Short.MAX_VALUE).addGap(10))
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 509, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
				.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_2.setLayout(null);

		JButton button_servido = new JButton("+");
		button_servido.setBounds(0, 11, 41, 16);
		button_servido.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (model.getRowCount() >= 1) {
					for (int i = model.getRowCount() - 1; i >= 0; i--) {

						if (model.getValueAt(i, 3).equals(true)) {

							model2.addRow(new Object[] { model.getValueAt(i, 0), model.getValueAt(i, 1), false });

							precio.add(model2.getRowCount() - 1, Float.parseFloat(model.getValueAt(i, 2).toString()));
							model.removeRow(i);
						}

					}
				}
			}
		});
		panel_2.add(button_servido);

		JButton button_devuelto = new JButton("-");
		button_devuelto.setBounds(0, 38, 41, 16);
		button_devuelto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (model2.getRowCount() >= 1) {
					for (int i = model2.getRowCount() - 1; i >= 0; i--) {

						if (model2.getValueAt(i, 2).equals(true)) {

							model.addRow(new Object[] { model2.getValueAt(i, 0), model2.getValueAt(i, 1), precio.get(i),
									false });
							precio.remove(i);
							model2.removeRow(i);
						}

					}
				}
			}
		});
		panel_2.add(button_devuelto);

		JButton buttonPrecio = new JButton("");
		buttonPrecio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sumaTotal = 0;
				for (int i = 0; i < model.getRowCount(); i++) {
					sumaTotal = sumaTotal + Float.parseFloat(table.getValueAt(i, 0).toString())
							* Float.parseFloat(table.getValueAt(i, 2).toString());
				}
				buttonPrecio.setText(String.valueOf(sumaTotal));
			}
		});
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup().addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup().addContainerGap().addComponent(lblCambrer)
								.addGap(123).addComponent(lblTaula, GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
								.addGap(196).addComponent(lblData))
						.addGroup(gl_panel_1.createSequentialGroup()
								.addComponent(btnCobrar, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(buttonPrecio)))
						.addContainerGap()));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE).addComponent(btnCobrar)
								.addComponent(buttonPrecio, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE).addComponent(lblCambrer)
								.addComponent(lblTaula).addComponent(lblData))));
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(new BorderLayout(0, 0));
		table = new JTable(model);
		table2 = new JTable(model2);

		scrollPane_1 = new JScrollPane(table2);
		scrollPane_1.setBounds(45, 0, 224, 402);
		panel_2.add(scrollPane_1);
		JScrollPane scrollPane = new JScrollPane(table);
		panel.add(scrollPane, BorderLayout.NORTH);

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JTable target = (JTable) e.getSource();
				int row = target.getSelectedRow();
				int column = target.getSelectedColumn();
				if (column == 3) {
					String producte = table.getModel().getValueAt(row, 1).toString();
					String data = lblData.getText().substring(6, lblData.getText().length());
					AccesSQL.actualitzarEstatProducte(producte, data);
				}
			}
		});
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
			AccesSQL.pujadaFactura(hora1, minut, segon);
			file.delete();
			FramePrincipal.refreshFrame();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
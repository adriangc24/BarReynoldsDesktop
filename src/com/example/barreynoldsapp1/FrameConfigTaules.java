package com.example.barreynoldsapp1;

import java.awt.EventQueue;
import java.awt.TextField;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.InputMismatchException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class FrameConfigTaules extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameConfigTaules frame = new FrameConfigTaules();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrameConfigTaules() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JTextField textField = new JTextField();
		textField.setColumns(10);

		JButton btnAcceptar = new JButton("Acceptar");
		btnAcceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!esNumero(textField.getText())) {
					JOptionPane.showMessageDialog(FrameConfigTaules.this, "ERROR: Aixo no es un enter", "ERROR", JOptionPane.ERROR_MESSAGE);
				} else {
					cambiarTaules(textField.getText());
					dispose();
					FramePrincipal.refreshFrame();
				}
			}
		});

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		JLabel lblNumTaules = new JLabel("Introdueix el numero de taules:");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addGap(95).addComponent(btnAcceptar).addGap(78)
								.addComponent(btnCancelar))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(136).addComponent(lblNumTaules))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(166).addComponent(textField,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap(101, Short.MAX_VALUE)));
		gl_contentPane
				.setVerticalGroup(
						gl_contentPane
								.createParallelGroup(
										Alignment.LEADING)
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
										.addContainerGap(74, Short.MAX_VALUE).addComponent(lblNumTaules).addGap(18)
										.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addGap(50).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
												.addComponent(btnAcceptar).addComponent(btnCancelar))
										.addGap(52)));
		contentPane.setLayout(gl_contentPane);
	}

	public static void cambiarTaules(String taules) {
		try {
			File file = new File("config.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			NodeList nList = doc.getElementsByTagName("numeroTaulas");
			Element element = (Element) nList.item(0);
			element.setTextContent(taules);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("config.xml"));
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean esNumero(String texto) {
		if (texto.equals(""))
			return false;
		try {
			Integer.parseInt(texto);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}

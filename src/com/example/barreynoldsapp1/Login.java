package com.example.barreynoldsapp1;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

public class Login extends JInternalFrame {
	private JTextField textFieldNomCambrer;
	private JTextField textFieldPassword;
	private JPanel panel;
	private static ArrayList<Cambrer> listaCambrers;
	int var;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		listaCambrers = new ArrayList<Cambrer>();
		AccesSQL.generarListaCambrers(listaCambrers);
		setVisible(true);
		setBounds(100, 100, 450, 300);

		JLabel lblNomCambrer = new JLabel("Cambrer:");

		JLabel lblPassword = new JLabel("Contrasenya:");

		textFieldNomCambrer = new JTextField();
		textFieldNomCambrer.setColumns(10);
		textFieldNomCambrer.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				var = 0;
			}

			@Override
			public void focusLost(FocusEvent e) {
			}
		});

		textFieldPassword = new JTextField();
		textFieldPassword.setColumns(10);
		textFieldPassword.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				var = 1;
			}

			@Override
			public void focusLost(FocusEvent e) {
			}
		});

		panel = new JPanel();
		panel.setBorder(null);
		GridBagLayout gbl_panel = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 33;
		constraints.weighty = 20;
		constraints.gridwidth = constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.BOTH;
		panel.setLayout(gbl_panel);

		String row1 = "qwertyuiop";
		String row2 = "asdfghjklñ";
		String row3 = "zxcvbnm-";
		int contadorFilas = 0;
		int contadorColumnas = 0;
		String[] rows = { row1, row2, row3 };
		for (int i = 0; i < rows.length; i++) {
			char[] keys = rows[i].toCharArray();
			for (int j = 0; j < keys.length; j++) {
				JButton button = new JButton(Character.toString(keys[j]));
				button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (var == 0) {
							textFieldNomCambrer.setText(textFieldNomCambrer.getText() + button.getText());
						} else if (var == 1) {
							textFieldPassword.setText(textFieldPassword.getText() + button.getText());
						}
					}
				});
				constraints.gridx = contadorColumnas;
				constraints.gridy = contadorFilas;
				panel.add(button, constraints);
				contadorColumnas++;
				if (contadorColumnas == 10) {
					contadorColumnas = 0;
					contadorFilas++;
					if (contadorFilas == 2) {
						contadorColumnas = 1;
					}
				}
			}
		}

		JButton btnEntrar = new JButton("Entrar");

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addGap(10)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblNomCambrer, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
								.addGap(18).addComponent(textFieldNomCambrer, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
								.addGap(18).addComponent(textFieldPassword, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
				.addPreferredGap(ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
				.addComponent(btnEntrar, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE).addGap(59))
				.addGroup(groupLayout.createSequentialGroup().addGap(93)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE).addGap(110)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
						.createSequentialGroup().addGap(11)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addGap(3).addComponent(lblNomCambrer))
								.addComponent(textFieldNomCambrer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(14)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addGap(2).addComponent(lblPassword,
										GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
								.addComponent(textFieldPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup().addGap(28).addComponent(btnEntrar)))
				.addGap(56).addComponent(panel, GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE).addGap(94)));
		getContentPane().setLayout(groupLayout);
	}
}
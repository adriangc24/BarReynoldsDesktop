package com.example.barreynoldsapp1;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Login extends JInternalFrame {
	private JTextField textFieldNomCambrer;
	private JTextField textFieldPassword;
	private JPanel panel;

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
		setVisible(true);
		setBounds(100, 100, 450, 300);

		JLabel lblNomCambrer = new JLabel("Cambrer:");
		lblNomCambrer.setBounds(10, 14, 75, 14);

		JLabel lblPassword = new JLabel("Contrasenya:");
		lblPassword.setBounds(10, 47, 76, 17);

		textFieldNomCambrer = new JTextField();
		textFieldNomCambrer.setBounds(103, 11, 86, 20);
		textFieldNomCambrer.setColumns(10);

		textFieldPassword = new JTextField();
		textFieldPassword.setBounds(104, 45, 86, 20);
		textFieldPassword.setColumns(10);

		panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setVgap(50);
		flowLayout.setAlignment(FlowLayout.TRAILING);
		panel.setBounds(0, 75, 434, 196);
		
		String row1 = "qwertyuiop";
		String row2 = "asdfghjkl";
		String row3 = "zxcvbnm";
		String[] rows = { row1, row2, row3 };
		for (int i = 0; i < rows.length; i++) {
			char[] keys = rows[i].toCharArray();
			for (int j = 0; j < keys.length; j++) {
				JButton button = new JButton(Character.toString(keys[j]));
				panel.add(button);
			}
		}

		getContentPane().setLayout(null);
		getContentPane().add(lblNomCambrer);
		getContentPane().add(textFieldNomCambrer);
		getContentPane().add(lblPassword);
		getContentPane().add(textFieldPassword);
		getContentPane().add(panel);
	}
}
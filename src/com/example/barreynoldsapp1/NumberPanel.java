package com.example.barreynoldsapp1;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class NumberPanel extends JPanel implements ActionListener {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Create the panel.
	 */
	public NumberPanel() {
		setLayout(null);
		
		JButton button_7 = new JButton("7");
		button_7.setBounds(12, 13, 59, 57);
		add(button_7);
		
		JButton button_8 = new JButton("8");
		button_8.setBounds(83, 13, 59, 57);
		add(button_8);
		
		JButton button_9 = new JButton("9");
		button_9.setBounds(154, 13, 59, 57);
		add(button_9);
		
		JButton btnX = new JButton("x");
		btnX.setBounds(225, 13, 59, 57);
		add(btnX);
		
		JButton button_4 = new JButton("4");
		button_4.setBounds(12, 83, 59, 57);
		add(button_4);
		
		JButton button_1 = new JButton("1");
		button_1.setBounds(12, 153, 59, 57);
		add(button_1);
		
		JButton btnC = new JButton("C");
		btnC.setBounds(12, 223, 59, 57);
		add(btnC);
		
		JButton button_5 = new JButton("5");
		button_5.setBounds(83, 83, 59, 57);
		add(button_5);
		
		JButton button_2 = new JButton("2");
		button_2.setBounds(83, 153, 59, 57);
		add(button_2);
		
		JButton button_0 = new JButton("0");
		button_0.setBounds(83, 223, 59, 57);
		add(button_0);
		
		JButton button_6 = new JButton("6");
		button_6.setBounds(154, 83, 59, 57);
		add(button_6);
		
		JButton button_3 = new JButton("3");
		button_3.setBounds(154, 153, 59, 57);
		add(button_3);
		
		JButton button_punto = new JButton(".");
		button_punto.setBounds(154, 223, 59, 57);
		add(button_punto);
		
		JButton button_div = new JButton("/");
		button_div.setBounds(225, 83, 59, 57);
		add(button_div);
		
		JButton button_plus = new JButton("+");
		button_plus.setBounds(225, 153, 59, 57);
		add(button_plus);
		
		JButton button_menos = new JButton("-");
		button_menos.setBounds(225, 223, 59, 57);
		add(button_menos);
		
		textField = new JTextField();
		textField.setBounds(316, 48, 116, 22);
		add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(316, 118, 116, 22);
		add(textField_1);
		
		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setBounds(316, 13, 82, 22);
		add(lblTotal);
		
		JLabel lblPagado = new JLabel("Pagado:");
		lblPagado.setBounds(316, 83, 82, 22);
		add(lblPagado);
		
		JButton btnNewButton = new JButton("=");
		btnNewButton.setBounds(296, 223, 102, 57);
		add(btnNewButton);
		setLayout(null);
		
		JLabel labelCambio = new JLabel("Cambio: ");
		labelCambio.setBounds(316, 153, 82, 22);
		add(labelCambio);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(316, 188, 116, 22);
		add(textField_2);
		

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton b1=(JButton)e.getSource();
		String opcion=b1.getText().substring(b1.getText().length()-1,b1.getText().length());
		System.out.print(opcion);
	/*	switch () {
		case value:
			
			break;

		default:
			break;
		}*/
		
		
	}
}

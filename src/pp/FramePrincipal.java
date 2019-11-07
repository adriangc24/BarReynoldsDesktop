package pp;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JMenuItem;
import java.awt.Dimension;

public class FramePrincipal extends JFrame {

	static int nTaules=3;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FramePrincipal frame = new FramePrincipal();
					// frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
					// frame.setUndecorated(true);
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
	public FramePrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGap(0, 434, Short.MAX_VALUE));
		groupLayout
				.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGap(0, 241, Short.MAX_VALUE));
		getContentPane().setLayout(groupLayout);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menuTaula = new JMenu("Taula");
		menuBar.add(menuTaula);
		
		InternalFrame taula1 = new InternalFrame();
		taula1.setSize(435, 240);
		add(taula1);
		InternalFrame taula2 = new InternalFrame();
		taula2.setSize(435, 240);
		add(taula2);
		InternalFrame taula3 = new InternalFrame();
		taula3.setSize(435, 240);
		add(taula3);

		JMenuItem ItemTaula1 = new JMenuItem("Taula 1");
		ItemTaula1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		menuTaula.add(ItemTaula1);

		JMenuItem ItemTaula2 = new JMenuItem("Taula 2");
		menuTaula.add(ItemTaula2);

		JMenuItem ItemTaula3 = new JMenuItem("Taula 3");
		menuTaula.add(ItemTaula3);

	}
}

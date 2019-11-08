package pp;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;

public class FrameInterno extends JInternalFrame {
	private JTable table;
	DefaultTableModel model = new DefaultTableModel() {
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

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameInterno frame = new FrameInterno();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public FrameInterno() {
		model.addColumn("Producte");
		model.addColumn("Preu");
		model.addColumn("Preparat");
		table = new JTable(model);
		getContentPane().add(table, BorderLayout.WEST);
		add(new JScrollPane(table));

		setClosable(true);
		setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		// setSize(310, 240);
		setVisible(true);
	}
}

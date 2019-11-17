package pp;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

public class FrameInterno extends JInternalFrame {
	JLabel lblCambrer, lblTaula, lblData;
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
	private JTable table;

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
		setBorder(null);
		model.addColumn("Quantitat");
		model.addColumn("Producte");
		model.addColumn("Preu");
		model.addColumn("Preparat");
		JPanel panel = new JPanel();

		JPanel panel_1 = new JPanel();

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

		lblCambrer = new JLabel("Cambrer: ");
		lblCambrer.setFont(lblCambrer.getFont().deriveFont(13.0f));
		lblCambrer.setHorizontalAlignment(SwingConstants.CENTER);

		lblTaula = new JLabel("Taula: ");
		lblTaula.setFont(lblTaula.getFont().deriveFont(13.0f));
		lblTaula.setHorizontalAlignment(SwingConstants.CENTER);

		lblData = new JLabel("Data: Hora:");
		lblData.setFont(lblData.getFont().deriveFont(13.0f));
		lblData.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup().addContainerGap().addComponent(lblCambrer).addGap(123)
						.addComponent(lblTaula, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGap(196).addComponent(lblData).addContainerGap()));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup().addContainerGap(122, Short.MAX_VALUE)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE).addComponent(lblCambrer)
								.addComponent(lblTaula).addComponent(lblData))));
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(new BorderLayout(0, 0));

		table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		panel.add(scrollPane, BorderLayout.NORTH);

		getContentPane().setLayout(groupLayout);

	}
}
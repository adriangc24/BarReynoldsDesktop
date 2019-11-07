package pp;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;

public class FrameInterno extends JInternalFrame {

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
		//setMaximizable(true);
		//setIconifiable(true);
		//setResizable(true);
		setClosable(true);
		setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		
		setSize(320,240);
		setVisible(true);
	}

}
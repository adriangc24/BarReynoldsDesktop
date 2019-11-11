package pp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JTabbedPane;

public class FramePrincipal extends JFrame {

	private JPanel contentPane;
	private static int numeroTaules = leerMesas();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System.out.println(numeroTaules);
					FramePrincipal frame = new FramePrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public FramePrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		for (int i = 1; i < numeroTaules + 1; i++) {
			FrameInterno intFrame = new FrameInterno();
			intFrame.setTitle("Taula" + i);
			introducirComanda(intFrame);
			Component tab = intFrame;
			tabbedPane.addTab("Taula" + i, tab);
		}
	}

	public static int leerMesas() {
		try {
			File file = new File("config.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			NodeList nList = doc.getElementsByTagName("numeroTaulas");
			Element element = (Element) nList.item(0);
			return Integer.parseInt(element.getTextContent());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void introducirComanda(FrameInterno intFrame) {
		if (intFrame.getTitle().equals("Taula1")) {
			try {
				File file = new File("Comandes" + File.separatorChar + "ComandaTaula1.xml");
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(file);
				NodeList nList = doc.getElementsByTagName("producto");
				for (int i = 0; i < nList.getLength(); i++) {
					Element element = (Element) nList.item(i);
					NodeList childList = element.getChildNodes();
					for (int j = 0; j < childList.getLength(); j++) {
						System.out.println(childList.item(i).getNodeName());
					}
					//String nombre = childList.item(0).getNodeName();
					//String precio = childList.item(1).getNodeName();
					//String cantidad = childList.item(2).getNodeName();
					//intFrame.model.addRow(new Object[] { cantidad, nombre, precio, false });
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

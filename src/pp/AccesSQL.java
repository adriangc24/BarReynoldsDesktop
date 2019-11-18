package pp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AccesSQL {

	public static void generarListaCambrers(Statement stmnt, ArrayList<Cambrer> listaCambrers) {
		try {
			ResultSet rsst = stmnt.executeQuery("SELECT * FROM cambrer");
			while (rsst.next()) {
				int id = (int) rsst.getObject("id");
				String nom = rsst.getObject("Nom_Cambrer").toString();
				listaCambrers.add(new Cambrer(id, nom));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

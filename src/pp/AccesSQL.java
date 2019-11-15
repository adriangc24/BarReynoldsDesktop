package pp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class AccesSQL {

	public static void main(String[] args) {
		Connection conn;
		try {
			String url = "jdbc:mysql://localhost/barreynolds?useUnicode=true&useJDBCCompliantTimeZoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, "root", "");
			Statement stmnt = conn.createStatement();
			System.out.println("Conexion establecida (creo xd)");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

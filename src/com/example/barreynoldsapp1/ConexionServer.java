package com.example.barreynoldsapp1;

public interface ConexionServer {
	String db = "barreynolds";
	String url = "jdbc:mysql://localhost/"+db+"?useUnicode=true&useJDBCCompliantTimeZoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	String user = "root", psswd = "";
}

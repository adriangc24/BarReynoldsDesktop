package com.example.barreynoldsapp1;

import java.io.Serializable;

public class Cambrer implements Serializable {
	int id;
	String nom_Cambrer;
	String password;
	private static final long serialVersionUID = 1733521708430895847L;
	
	public Cambrer(int id, String nom_Cambrer, String password) {
		super();
		this.id = id;
		this.nom_Cambrer = nom_Cambrer;
		this.password = password;
	}

	@Override
	public String toString() {
		return "Cambrer [id=" + id + ", nom_Cambrer=" + nom_Cambrer + "]";
	}
}

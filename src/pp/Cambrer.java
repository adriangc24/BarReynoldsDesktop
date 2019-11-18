package pp;

public class Cambrer {
	int id;
	String nom_Cambrer;

	public Cambrer(int id, String nom_Cambrer) {
		super();
		this.id = id;
		this.nom_Cambrer = nom_Cambrer;
	}

	@Override
	public String toString() {
		return "Cambrer [id=" + id + ", nom_Cambrer=" + nom_Cambrer + "]";
	}
}

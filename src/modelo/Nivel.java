package modelo;

public enum Nivel {
	BLACK(8000), ORO(12000), PLATINUM(15000);

	private int i;

	Nivel(int i) {
		this.i = i;
	}

	public int getPrecioMembresia() {
		return i;
	}

}

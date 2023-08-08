package modelo;

public class Emplazamiento {
	@Override
	public String toString() {
		return "Emplazamiento [tipoEmplazamiento=" + tipoEmplazamiento + ", superficie=" + superficie + "]";
	}

	private TipoEmplazamiento tipoEmplazamiento;
	private double superficie;

	public Emplazamiento(TipoEmplazamiento tipoEmplazamiento, double superficie) {
		this.tipoEmplazamiento = tipoEmplazamiento;
		this.superficie = superficie;
	}

	public TipoEmplazamiento getTipoEmplazamiento() {
		return tipoEmplazamiento;
	}

	public double getSuperficie() {
		return superficie;
	}

}

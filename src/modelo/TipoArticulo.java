package modelo;


public class TipoArticulo {

	private static int contador = 0;
	private int idTipoArticulo;
	private String nombre;
	private int numeroUsos;
	private TipoAmortizacion tipoAmortizacion;

	public TipoArticulo(String nombre, int numeroUsos, TipoAmortizacion tipoAmortizacion) {
		this.nombre = nombre;
		this.numeroUsos = numeroUsos;
		this.tipoAmortizacion = tipoAmortizacion;
		this.idTipoArticulo = contador;
		contador++;
	}

	@Override
	public String toString() {
		return "TipoArticulo [idTipoArticulo=" + idTipoArticulo + ", nombre=" + nombre + ", numeroUsos=" + numeroUsos
				+ ", tipoAmortizacion=" + tipoAmortizacion + "]";
	}

	public String getNombre() {
		return nombre;
	}

	public int getIdTipoArticulo() {
		return idTipoArticulo;
	}


	public int getNumeroUsos() {
		return numeroUsos;
	}

	public TipoAmortizacion getTipoAmortizacion() {
		return tipoAmortizacion;
	}

}

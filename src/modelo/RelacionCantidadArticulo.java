package modelo;

public class RelacionCantidadArticulo {
	private Articulo articulo;
	/* TODO
	 * Acorde a lo que dijo el profe, revisar si hay que diferenciar cantidad por socio
	 * y profesor
	 */
	private int cantidadSocio;
	private int cantidadProfesor;
	
	public RelacionCantidadArticulo(Articulo articulo, int cantidadSocio, int cantidadProfesor) {
		super();
		this.articulo = articulo;
		this.cantidadSocio = cantidadSocio;
		this.cantidadProfesor = cantidadProfesor;
	}
	public Articulo getArticulo() {
		return articulo;
	}
	public int getCantidadSocio() {
		return cantidadSocio;
	}
	public int getCantidadProfesor() {
		return cantidadProfesor;
	}
	
}

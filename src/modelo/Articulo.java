package modelo;

import java.time.LocalDate;
import java.util.Date;

public class Articulo {
	private static int contador = 0;
	private int idArticulo;
	private int idTipoArticulo;
	private boolean desgastado;
	private String nombre, descripcion;
	private double precio;
	private int numeroUsos;
	private Date fechaDeFabricacion;
	private TipoAmortizacion tipoAmortizacion;
	private double costoPorUso;

	public Articulo(TipoArticulo tipoArticulo, String descripcion, double precio) {
		this.desgastado = false;
		this.idArticulo = Articulo.contador;
		this.idTipoArticulo = tipoArticulo.getIdTipoArticulo();
		this.nombre = tipoArticulo.getNombre();
		this.precio = precio;
		this.numeroUsos = tipoArticulo.getNumeroUsos();
		this.fechaDeFabricacion = new Date();
		this.tipoAmortizacion = tipoArticulo.getTipoAmortizacion();
		this.descripcion = descripcion;
		this.costoPorUso = precio / tipoArticulo.getNumeroUsos();
		Articulo.contador++;
	}

	
	public void darBaja() {
		this.desgastado = true;
		this.numeroUsos = 0;
		System.out.println(this);
	}
	
	public int getIdArticulo() {
		return idArticulo;
	}

	public static int getContador() {
		return contador;
	}

	public int getIdTipoArticulo() {
		return idTipoArticulo;
	}

	public boolean isDesgastado() {
		return desgastado;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public double getPrecio() {
		return precio;
	}

	public int getNumeroUsos() {
		return numeroUsos;
	}

	public Date getFechaDeFabricacion() {
		return fechaDeFabricacion;
	}

	public double getCostoPorUso() {
		return costoPorUso;
	}



	@Override
	public String toString() {
		return "Articulo [idArticulo=" + idArticulo + ", idTipoArticulo=" + idTipoArticulo + ", desgastado="
				+ desgastado + ", nombre=" + nombre + ", descripcion=" + descripcion + ", precio=" + precio
				+ ", numeroUsos=" + numeroUsos + ", fechaDeFabricacion=" + fechaDeFabricacion + ", tipoAmortizacion="
				+ tipoAmortizacion + ", costoPorUso=" + costoPorUso + "]";
	}


	public TipoAmortizacion getTipoAmortizacion() {
		return tipoAmortizacion;
	}

	public void usarArticulo() {
		numeroUsos--;
		if (numeroUsos == 0) {
			desgastado = true;
		}
	}

}

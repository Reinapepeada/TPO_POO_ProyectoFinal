package modelo;

public class ArticuloCantidadDetalle {
	private int idTipoArticulo, cantidadTotal;
	private String detalle;

	public ArticuloCantidadDetalle(int key, int cantidadTotal, String detalle) {
		this.idTipoArticulo = key;
		this.cantidadTotal = cantidadTotal;
		this.detalle = detalle;
	}

	

	@Override
	public String toString() {
		return "ArticuloCantidadDetalle [idTipoArticulo=" + idTipoArticulo + ", cantidadTotal=" + cantidadTotal
				+ ", detalle=" + detalle + "]";
	}



	public int getIdTipoArticulo() {
		return idTipoArticulo;
	}

	public int getCantidadTotal() {
		return cantidadTotal;
	}

	public String getDetalle() {
		return detalle;
	}
	
}

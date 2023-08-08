package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import excepciones.ArticulosInsuficientesException;

public class Sede {
	private String barrio;
	private double precioAlquiler;
	private Nivel nivel;
	private ArrayList<Articulo> articulos = new ArrayList<>();;
	private ArrayList<Clase> clases = new ArrayList<>();
	private ArrayList<Clase> clasesAlmacenadas = new ArrayList<>();
	private ArrayList<Emplazamiento> emplazamientos = new ArrayList<>();

	public Sede(String barrio, double precioAlquiler, Nivel nivel) {
		this.barrio = barrio;
		this.precioAlquiler = precioAlquiler;
		this.nivel = nivel;
	}

	public void setArticulos(ArrayList<Articulo> articulos) {
		this.articulos = articulos;
	}

	public void agregarEmplazamiento(Emplazamiento emplazamiento) {
		emplazamientos.add(emplazamiento);
	}

	// ready
	public void incorporarArticulo(TipoArticulo tipoArticulo, String descripcion, double precio, int cantidad) {
		for (int i = cantidad; i > 0; i--) {
			articulos.add(new Articulo(tipoArticulo, descripcion, precio));
		}
	}

	// ready
	public void desgastarArticulosFinalDia() { // Funcion que se ejecuta a las 00:00 de cada dia
		for (Articulo articulo : articulos) {
			if (articulo.getTipoAmortizacion() == TipoAmortizacion.FECHA_DE_FABRICACION) {
				articulo.usarArticulo();
			}
		}
	}

	// ready
	public void agendarClase(TipoClase tipoClase, Emplazamiento emplazamiento, LocalDateTime dateInicio,
			LocalDateTime dateFin) throws ArticulosInsuficientesException {
		ArrayList<ArticuloCantidadDetalle> detalleCantidadesTotal = new ArrayList<>();
		HashMap<Integer, ArrayList<CantidadDetalle>> mapa = tipoClase.getCantidadArticulo();
		for (Entry<Integer, ArrayList<CantidadDetalle>> entry : mapa.entrySet()) {
			Integer idTipoArticulo = entry.getKey();
			List<CantidadDetalle> values = entry.getValue();
			for (CantidadDetalle estruct : values) {
				int cantTotal = estruct.getCantidadPorAlummo() * 30 + estruct.getCantidadPorProfesor();
				detalleCantidadesTotal
						.add(new ArticuloCantidadDetalle(idTipoArticulo, cantTotal, estruct.getDetalle()));
			}
		}
		if (verificarArticulosDisponibles(detalleCantidadesTotal)) {
			Clase nuevaClase = new Clase(this, tipoClase, emplazamiento, dateInicio, dateFin);
			clases.add(nuevaClase);
		} else {
			throw new ArticulosInsuficientesException("No hay articulos suficientes en stock para agendar la clase");
		}
	}

	// ready
	public void almacenarClase(Clase clase) {
		if (clase.getTipo().getNombre().equals("Yoga")) {
			var i = 0;
			for (Clase claseOnline : clasesAlmacenadas) {
				if (claseOnline.getTipo().getNombre().equals("Yoga")) {
					i++;
				}
			}
			if (i == 10) {
				for (Clase claseOnline : clasesAlmacenadas) {
					if (claseOnline.getTipo().getNombre().equals("Yoga")) {
						clasesAlmacenadas.remove(clasesAlmacenadas.indexOf(claseOnline));
						break;
					}
				}
			}
			clasesAlmacenadas.add(clase);
		}
		if (clase.getTipo().getNombre().equals("Gimnasia Postural")) {
			var i = 0;
			for (Clase claseOnline : clasesAlmacenadas) {
				if (claseOnline.getTipo().getNombre().equals("Gimnasia Postural")) {
					i++;
				}
			}
			if (i == 15) {
				for (Clase claseOnline : clasesAlmacenadas) {
					if (claseOnline.getTipo().getNombre().equals("Gimnasia Postural")) {
						clasesAlmacenadas.remove(clasesAlmacenadas.indexOf(claseOnline));
						break;
					}
				}
			}
			clasesAlmacenadas.add(clase);
		}

		// clasesAlmacenadas.add(clase); <= Para "en un futuro" poder almacenar otro
		// tipos de clase
	}

	// READY
	private boolean verificarArticulosDisponibles(ArrayList<ArticuloCantidadDetalle> detalleCantidadesTotal) {
		for (ArticuloCantidadDetalle articuloCantidadDetalle : detalleCantidadesTotal) {
			int cantidadNecesaria = articuloCantidadDetalle.getCantidadTotal();
			for (Articulo item : articulos) {
				if (item.getIdTipoArticulo() == articuloCantidadDetalle.getIdTipoArticulo()
						&& item.getDescripcion().equals(articuloCantidadDetalle.getDetalle()) && !item.isDesgastado()) {
					cantidadNecesaria--;
				}
				if (cantidadNecesaria == 0) {
					break;
				}
			}
			if (cantidadNecesaria > 0) {
				System.out.println("Para el articulo [ID Tipo Articulo: " + articuloCantidadDetalle.getIdTipoArticulo()
						+ " - Detalle: " + articuloCantidadDetalle.getDetalle() + "] Faltan " + cantidadNecesaria
						+ " unidades en el stock de la Sede");
				return false;
			}
		}

		return true;
	}

	public String getBarrio() {
		return barrio;
	}

	public double getPrecioAlquiler() {
		return precioAlquiler;
	}

	public Nivel getNivel() {
		return nivel;
	}

	public ArrayList<Articulo> getArticulos() {
		return articulos;
	}

	public ArrayList<Clase> getClases() {
		return clases;
	}

	public ArrayList<Clase> getClasesAlmacenadas() {
		return clasesAlmacenadas;
	}

	public ArrayList<Emplazamiento> getEmplazamientos() {
		return emplazamientos;
	}

	@Override
	public String toString() {
		return "Sede [barrio=" + barrio + ", precioAlquiler=" + precioAlquiler + ", nivel=" + nivel + ", articulos="
				+ articulos + ", clases=" + clases + ", clasesAlmacenadas=" + clasesAlmacenadas + ", emplazamientos="
				+ emplazamientos + "]";
	}

}

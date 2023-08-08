package modelo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import excepciones.InscripcionNoDisponibleException;
import excepciones.ProfesorNoDisponibleException;

public class Clase {

	private Sede sede;
	private ArrayList<Socio> sociosInscriptos = new ArrayList<>();
	private ArrayList<Articulo> articulosDeClase = new ArrayList<>();
	private EstadoClase estado;
	private TipoClase tipoClase;
	private Profesor profesor;
	private Emplazamiento lugar;
	private LocalDateTime horaInicio;
	private LocalDateTime horaFinal;
	private double costoClase, ingresoClase;

	public double getCostoClase() {
		return costoClase;
	}

	public double getIngresoClase() {
		return ingresoClase;
	}

	public Clase(Sede sede, TipoClase tipo, Emplazamiento lugar, LocalDateTime horaInicio, LocalDateTime horaFinal) {
		this.sede = sede;
		this.estado = EstadoClase.AGENDADA;
		this.tipoClase = tipo;
		this.lugar = lugar;
		this.horaInicio = horaInicio;
		this.horaFinal = horaFinal;
	}

	public void finalizarClase() {
		for (Articulo articulo : articulosDeClase) { // Amortizar articulos
			if (articulo.getTipoAmortizacion() == TipoAmortizacion.USO) {
				articulo.usarArticulo();
			}
		}
		if (this.tipoClase.esOnline()) { // Almacenar clase si corresponde
			sede.almacenarClase(this);
		}
		this.estado = EstadoClase.FINALIZADA;
	}

	public void asignarProfesor(Profesor profesor) throws ProfesorNoDisponibleException {
		if (profesor.aptoParaDictarClase(horaInicio, horaFinal)) {
			this.profesor = profesor;

			profesor.addClase(this);
		} else {
			throw new ProfesorNoDisponibleException(
					"Profesor no cuenta con disponibilidad horaria para dictar la clase");
		}
	}

	private void calcularCostos() {
		var duracion = Duration.between(horaInicio, horaFinal).toHours();
		double costoEmplazamiento = 0;
		double costoAmortizacion = 0;
		double costoProfesor = 0;
		if (lugar.getTipoEmplazamiento() == TipoEmplazamiento.SALON) {
			costoEmplazamiento = sede.getPrecioAlquiler() / 300;
		} else if (lugar.getTipoEmplazamiento() == TipoEmplazamiento.PILETA) {
			costoEmplazamiento = sede.getPrecioAlquiler() / 150;
		} else {
			costoEmplazamiento = lugar.getSuperficie() * duracion * 500;
		}
		for (Articulo articulo : articulosDeClase) { // TODO
			if (articulo.getTipoAmortizacion() == TipoAmortizacion.USO) {
				costoAmortizacion = costoAmortizacion + articulo.getCostoPorUso();
			} else {
				var aux = (duracion / 8) * articulo.getCostoPorUso();
				costoAmortizacion = costoAmortizacion + aux;
			}
		}
		costoProfesor = profesor.getSueldo() / 90;
		costoClase = costoAmortizacion + costoEmplazamiento + costoProfesor;
	}

	private void calcularIngresos() {
		double ingresos = 0;
		for (Socio socio : sociosInscriptos) {
			ingresos = ingresos + (socio.getNivel().getPrecioMembresia() / 30);
		}
		ingresoClase = ingresos;
	}

	public void calcularRentabilidad() {
		if (ingresoClase > costoClase) {
			cambiarEstado(EstadoClase.CONFIRMADA);
		}
	}

	public void inscribirse(Usuario socio) throws InscripcionNoDisponibleException {
		Socio aux = (Socio) socio;
		if (sociosInscriptos.size() >= 30) {
			throw new InscripcionNoDisponibleException("La clase alcanzó el máximo de socios inscriptos");
		}
		if (lugar.getSuperficie() / (sociosInscriptos.size() + 1) < 2) {
			throw new InscripcionNoDisponibleException("La clase no cuenta con el espacio disponible para inscribirse");
		}
		if (!aux.claseDiariaDisponible(horaFinal)) {
			throw new InscripcionNoDisponibleException("Ya está inscripto a una clase para el dia de hoy");
		}
		if (!aux.getIsActive()) {
			throw new InscripcionNoDisponibleException("El socio se encuentra dado de baja");
		}
		if (profesor == null) {
			throw new InscripcionNoDisponibleException("La clase no cuenta con profesor agendado al momento");
		}

		sociosInscriptos.add(aux);
		aux.agendarClase(this);
		incorporarArticulos();
		calcularCostos();
		calcularIngresos();
		calcularRentabilidad();

	}

	public void cambiarEstado(EstadoClase nuevoEstado) {
		if (nuevoEstado != EstadoClase.FINALIZADA) {
			this.estado = nuevoEstado;
		} else {
			finalizarClase();
		}
	}

	private void incorporarArticulos() {
		ArrayList<ArticuloCantidadDetalle> detalleCantidadesTotal = new ArrayList<>();
		HashMap<Integer, ArrayList<CantidadDetalle>> mapa = tipoClase.getCantidadArticulo();
		ArrayList<Articulo> articulosClaseAux = new ArrayList<>();
		for (Entry<Integer, ArrayList<CantidadDetalle>> entry : mapa.entrySet()) {
			Integer idTipoArticulo = entry.getKey();
			List<CantidadDetalle> values = entry.getValue();
			for (CantidadDetalle estruct : values) {
				int cantTotal = estruct.getCantidadPorAlummo() * sociosInscriptos.size()
						+ estruct.getCantidadPorProfesor();
				detalleCantidadesTotal
						.add(new ArticuloCantidadDetalle(idTipoArticulo, cantTotal, estruct.getDetalle()));
			}
		}
		for (ArticuloCantidadDetalle articuloCantidadDetalle : detalleCantidadesTotal) {
			int cantidadNecesaria = articuloCantidadDetalle.getCantidadTotal();
			for (Articulo item : sede.getArticulos()) {
				if (item.getIdTipoArticulo() == articuloCantidadDetalle.getIdTipoArticulo()
						&& item.getDescripcion().equals(articuloCantidadDetalle.getDetalle()) && !item.isDesgastado()) {
					articulosClaseAux.add(item);
					cantidadNecesaria--;
				}
				if (cantidadNecesaria == 0) {
					break;
				}
			}
		}
		articulosDeClase = articulosClaseAux;
	}

	public Sede getSede() {
		return sede;
	}

	public ArrayList<Socio> getSocios() {
		return sociosInscriptos;
	}

	@Override
	public String toString() {
		return "Clase [sociosInscriptos=" + sociosInscriptos + ", articulosDeClase=" + articulosDeClase + ", estado="
				+ estado + ", tipo=" + tipoClase + ", profesor=" + profesor + ", lugar=" + lugar + ", horaInicio="
				+ horaInicio + ", horaFinal=" + horaFinal + ", costoClase=" + costoClase + ", ingresoClase="
				+ ingresoClase + "]";
	}

	public EstadoClase getEstado() {
		return estado;
	}

	public TipoClase getTipo() {
		return tipoClase;
	}

	public Profesor getProfesor() {
		return profesor;
	}

	public Emplazamiento getLugar() {
		return lugar;
	}

	public LocalDateTime getHoraInicio() {
		return horaInicio;
	}

	public LocalDateTime getHoraFinal() {
		return horaFinal;
	}

}

package modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Socio extends Usuario {

	private Nivel nivel;
	private boolean isActive;
	private ArrayList<Clase> clasesInscriptas = new ArrayList<Clase>();

	public Socio(String nombre, String apellido, String dni, Nivel nivel) {
		super(nombre, apellido, dni);
		this.nivel = nivel;
		this.isActive = true;
	}

	@Override
	public String toString() {
		return "Socio [nivel=" + nivel + ", isActive=" + isActive + "]";
	}

	public boolean claseDiariaDisponible(LocalDateTime horaFinal) {
		LocalDate horaFinalLocalDate = horaFinal.toLocalDate();
		for (Clase clase : clasesInscriptas) {
			if (clase.getHoraFinal().toLocalDate().isEqual(horaFinalLocalDate)) {
				return false;
			}
		}
		return true;
	}

	public boolean inscripto(Clase clase) {
		return clasesInscriptas.contains(clase) ? true : false;
	}

	public ArrayList<Sede> recuperarSedesDisponibles() {
		ArrayList<Sede> sedesDisponibles = new ArrayList<>();
		ArrayList<Sede> sedes = SupertlonSingleton.getInstance().getSedes();
		for (Sede sede : sedes) {
			if (this.nivel == Nivel.BLACK && sede.getNivel() == Nivel.BLACK) {
				sedesDisponibles.add(sede);
			} else if (this.nivel == Nivel.ORO && (sede.getNivel() == Nivel.BLACK || sede.getNivel() == Nivel.ORO)) {
				sedesDisponibles.add(sede);
			} else if (this.nivel == Nivel.PLATINUM) {
				sedesDisponibles.add(sede);
			}
		}
		return sedesDisponibles;
	}

	public void agendarClase(Clase clase) {
		clasesInscriptas.add(clase);
	}

	public boolean soySocio() {
		return true;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public String getDni() {
		return dni;
	}

	public Nivel getNivel() {
		return nivel;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

}

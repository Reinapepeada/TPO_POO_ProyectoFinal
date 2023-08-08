package modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Usuario {
	protected String nombre, apellido, dni;

	protected Usuario(String nombre, String apellido, String dni) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
	}

	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", apellido=" + apellido + ", dni=" + dni + "]";
	}

	public boolean soySoporteTecnico() {
		return false;
	}

	public boolean soyAdministrativo() {
		return false;
	}

	public boolean soySocio() {
		return false;
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

}

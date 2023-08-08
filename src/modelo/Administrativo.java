package modelo;

import java.util.ArrayList;
import java.util.List;

public class Administrativo extends Usuario {
	@Override
	public String toString() {
		return "Administrativo [sedes=" + sedes + ", nombre=" + nombre + ", apellido=" + apellido + ", dni=" + dni
				+ "]";
	}

	private ArrayList<Sede> sedes;

	public Administrativo(String nombre, String apellido, String numeroDeDocumento, ArrayList<Sede> sedes) {
		super(nombre, apellido, numeroDeDocumento);
		this.sedes = sedes;
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

	public ArrayList<Sede> getSedes() {
		return sedes;
	}

	public ArrayList<Clase> getClasesAdministradas() {
		ArrayList<Clase> clasesAdministradas = new ArrayList<Clase>();
		for (Sede sede : sedes) {
			var clasesSede = sede.getClases();
			for (Clase clase : clasesSede) {
				clasesAdministradas.add(clase);
			}
		}
		return clasesAdministradas;
	}

	public ArrayList<Clase> getClasesAlmacenadas() {
		ArrayList<Clase> clasesAlmacenadas = new ArrayList<Clase>();
		for (Sede sede : sedes) {
			var clasesSede = sede.getClasesAlmacenadas();
			for (Clase clase : clasesSede) {
				clasesAlmacenadas.add(clase);
			}
		}
		return clasesAlmacenadas;
	}

	public boolean soyAdministrativo() {
		return true;
	}

}

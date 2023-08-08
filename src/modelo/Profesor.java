package modelo;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Profesor {
	private String nombre;
	private String apellido;
	private List<Clase> clases = new ArrayList<Clase>();
	private double sueldo;

	public Profesor(String nombre, String apellido, double sueldo) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.sueldo = sueldo;
	}

	public boolean aptoParaDictarClase(LocalDateTime horaInicio, LocalDateTime horaFinal) {
		ArrayList<Clase> clasesDelDia = new ArrayList<Clase>();
		LocalDate horaFinalLocalDate = horaFinal.toLocalDate();

		for (Clase clase : clases) {
			if (clase.getHoraFinal().toLocalDate().isEqual(horaFinalLocalDate)) {
				clasesDelDia.add(clase);
			}
		}
		if (clasesDelDia.size() == 0) {
			return true;
		} else if (clasesDelDia.size() == 1) {
			Clase clase = clasesDelDia.get(0);
			if (clase.getHoraFinal().isBefore(horaInicio)) {
				var duracion = Duration.between(clase.getHoraFinal(), horaInicio).toHours();
				if (duracion >= 3) {
					return true;
				}
			} else {
				var duracion = Duration.between(horaFinal, clase.getHoraInicio()).toHours();
				if (duracion >= 3) {
					return true;
				}
			}
		} else if (clasesDelDia.size() == 2) {
			Clase[] claseDelDiaArray = clasesDelDia.toArray(new Clase[clasesDelDia.size()]);
			Arrays.sort(claseDelDiaArray, Comparator.comparing(Clase::getHoraInicio));
			if (claseDelDiaArray[0].getHoraInicio().isAfter(horaFinal)) {
				var duracion = Duration.between(horaFinal, claseDelDiaArray[0].getHoraInicio()).toHours();
				if (duracion >= 3) {
					return true;
				}
			} else if (claseDelDiaArray[0].getHoraFinal().isBefore(horaInicio)
					&& claseDelDiaArray[1].getHoraInicio().isAfter(horaFinal)) {
				var duracion1 = Duration.between(claseDelDiaArray[0].getHoraFinal(), horaInicio).toHours();
				var duracion2 = Duration.between(horaFinal, claseDelDiaArray[1].getHoraInicio()).toHours();
				if (duracion1 >= 3 && duracion2 >= 3) {
					return true;
				}
			} else {
				var duracion = Duration.between(claseDelDiaArray[1].getHoraFinal(), horaInicio).toHours();
				if (duracion >= 3) {
					return true;
				}
			}
		}
		return false;
	}

	public void addClase(Clase clase) {
		clases.add(clase);
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public List<Clase> getClases() {
		return clases;
	}

	public double getSueldo() {
		return sueldo;
	}

	public void setClases(List<Clase> clases) {
		this.clases = clases;
	}

	public void setSueldo(double sueldo) {
		this.sueldo = sueldo;
	}

}

package controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import excepciones.SedeExistenteException;
import excepciones.UsuarioDuplicadoException;
import modelo.CantidadDetalle;
import modelo.Nivel;
import modelo.Sede;
import modelo.SupertlonSingleton;
import modelo.TipoAmortizacion;
import modelo.Usuario;

public class SoporteTecnicoControlador {

	SupertlonSingleton supertlonSingleton = SupertlonSingleton.getInstance();

	public void agregarSede(String barrio, Nivel nivel, double precioAlquiler) throws SedeExistenteException {
		supertlonSingleton.agregarSede(barrio, nivel, precioAlquiler);
	};

	public void agregarTipoArticulo(String nombre, int numeroUsos, TipoAmortizacion tipoAmortizacion) {
		SupertlonSingleton supertlonSingleton = SupertlonSingleton.getInstance();
		supertlonSingleton.agregarTipoArticulo(nombre, numeroUsos, tipoAmortizacion);
	}

	public void agregarTipoClase(String nombreClase, boolean esOnline,
			HashMap<Integer, ArrayList<CantidadDetalle>> cantidadPorTipoArticulo) {
		supertlonSingleton.agregarTipoClase(nombreClase, esOnline, cantidadPorTipoArticulo);
	}

	public void crearAdministrativo(String nombre, String apellido, String dni, ArrayList<Sede> sedes)
			throws UsuarioDuplicadoException {
		supertlonSingleton.crearAdministrativo(nombre, apellido, dni, sedes);
	}

	public void crearSocio(String nombre, String apellido, String dni, Nivel nivel) throws UsuarioDuplicadoException {
		supertlonSingleton.crearSocio(nombre, apellido, dni, nivel);
	}

	public void crearSoporteTecnico(String nombre, String apellido, String dni) throws UsuarioDuplicadoException {
		supertlonSingleton.crearSoporteTecnico(nombre, apellido, dni);
	}

}

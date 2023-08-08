package controlador;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import excepciones.ArticulosInsuficientesException;
import excepciones.ProfesorNoDisponibleException;
import excepciones.UsuarioDuplicadoException;
import modelo.Administrativo;
import modelo.Articulo;
import modelo.Clase;
import modelo.Emplazamiento;
import modelo.EstadoClase;
import modelo.Nivel;
import modelo.Profesor;
import modelo.Sede;
import modelo.Socio;
import modelo.SupertlonSingleton;
import modelo.TipoArticulo;
import modelo.TipoClase;
import modelo.TipoEmplazamiento;
import modelo.Usuario;
import modelo.UsuarioSingleton;

public class AdministrativoControlador {

	public void agregarEmplazamiento(Sede sede, TipoEmplazamiento tipoEmplazamiento, double superficie) {
		SupertlonSingleton supertlonSingleton = SupertlonSingleton.getInstance();
		supertlonSingleton.agregarEmplazamiento(sede, tipoEmplazamiento, superficie);
	}

	public void agendarClase(TipoClase tipoClase, Sede sede, Emplazamiento emplazamiento, LocalDateTime dateInicio,
			LocalDateTime dateFin) throws ArticulosInsuficientesException {
		SupertlonSingleton supertlonSingleton = SupertlonSingleton.getInstance();
		supertlonSingleton.agendarClase(tipoClase, sede, emplazamiento, dateInicio, dateFin);

	}

	public void asginarProfesor(Clase clase, Profesor profesor) throws ProfesorNoDisponibleException {
		SupertlonSingleton supertlonSingleton = SupertlonSingleton.getInstance();
		supertlonSingleton.asginarProfesor(clase, profesor);

	}

	public void cambiarEstadoClase(Clase clase, EstadoClase nuevoEstado) {
		SupertlonSingleton supertlonSingleton = SupertlonSingleton.getInstance();
		supertlonSingleton.cambiarEstadoClase(clase, nuevoEstado);
	}

	public void darBajaArticulo(Articulo articulo) {
		SupertlonSingleton supertlonSingleton = SupertlonSingleton.getInstance();
		supertlonSingleton.darBajaArticulo(articulo);
	}

	public void incorporarArticulos(Sede sede, TipoArticulo tipoArticulo, String descripcion, double precio,
			int cantidad) {
		SupertlonSingleton supertlonSingleton = SupertlonSingleton.getInstance();
		supertlonSingleton.incorporarArticulos(sede, tipoArticulo, descripcion, precio, cantidad);
	}

	public void modificarSocio(Socio socio, String nombre, String apellido, Nivel nivel, boolean isActive) {
		SupertlonSingleton supertlonSingleton = SupertlonSingleton.getInstance();
		supertlonSingleton.modificarSocio(socio, nombre, apellido, nivel, isActive);
	}

	public void crearSocio(String nombre, String apellido, String dni, Nivel nivel) {
		SupertlonSingleton supertlonSingleton = SupertlonSingleton.getInstance();
		try {
			supertlonSingleton.crearSocio(nombre, apellido, dni, nivel);
		} catch (UsuarioDuplicadoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<Sede> recuperarSedesAdministradas() {
		Usuario user = UsuarioSingleton.getInstance().getUsuarioActual();
		if (user.soyAdministrativo()) {
			Administrativo administrativo = (Administrativo) user;
			return administrativo.getSedes();
		}
		return null;
	}

	public ArrayList<Clase> recuperarClasesAdministradas() {
		Usuario user = UsuarioSingleton.getInstance().getUsuarioActual();
		if (user.soyAdministrativo()) {
			Administrativo administrativo = (Administrativo) user;
			return administrativo.getClasesAdministradas();
		}
		return null;
	}

	public ArrayList<Clase> recuperarClasesAlmacenadas() {
		Usuario user = UsuarioSingleton.getInstance().getUsuarioActual();
		if (user.soyAdministrativo()) {
			Administrativo administrativo = (Administrativo) user;
			return administrativo.getClasesAlmacenadas();
		}
		return null;
	}

	public ArrayList<Socio> recuperarSocios() {
		Usuario user = UsuarioSingleton.getInstance().getUsuarioActual();
		ArrayList<Socio> socios = new ArrayList<Socio>();
		if (user.soyAdministrativo()) {
			var usuarios = SupertlonSingleton.getInstance().getUsuarios();
			for (Usuario item : usuarios) {
				if (item.soySocio()) {
					socios.add((Socio) item);
				}
			}
			return socios;
		}
		return null;
	}
}

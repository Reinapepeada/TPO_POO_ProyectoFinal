package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import excepciones.ArticulosInsuficientesException;
import excepciones.InscripcionNoDisponibleException;
import excepciones.ProfesorNoDisponibleException;
import excepciones.SedeExistenteException;
import excepciones.UsuarioDuplicadoException;
import excepciones.UsuarioInexistenteException;

public class SupertlonSingleton {
	private static SupertlonSingleton instancia;
	private ArrayList<Usuario> usuarios;
	private ArrayList<Profesor> profesores;
	public ArrayList<Profesor> getProfesores() {
		return profesores;
	}

	public void setProfesores(ArrayList<Profesor> profesores) {
		this.profesores = profesores;
	}

	private ArrayList<TipoClase> tiposClases;
	private ArrayList<TipoArticulo> tiposArticulos;
	private ArrayList<Sede> sedes;

	private SupertlonSingleton() {
		this.usuarios = new ArrayList<Usuario>();
		this.tiposClases = new ArrayList<TipoClase>();
		this.tiposArticulos = new ArrayList<TipoArticulo>();
		this.sedes = new ArrayList<Sede>();
	};

	public static SupertlonSingleton getInstance() {
		if (instancia == null) {
			instancia = new SupertlonSingleton();
		}
		return instancia;
	}

	// Acceso - READY
	public void ingresar(String dni) throws UsuarioInexistenteException {
		for (Usuario usuario : usuarios) {
			if (usuario.dni.equals(dni)) {
				UsuarioSingleton usuarioSingleton = UsuarioSingleton.getInstance();
				usuarioSingleton.setUsuarioActual(usuario);
				System.out.println("Ingresado con exito!");
				return;
			}
		}
		throw new UsuarioInexistenteException("No existe usuario con el DNI ingresado: " + dni);
	}

	// METODOS SOPORTE TECNICO

	public void agregarUsuario(Usuario nuevoUsuario) throws UsuarioDuplicadoException {
		for (Usuario usuario : usuarios) {
			if (usuario.dni.equals(nuevoUsuario.getDni())) {
				throw new UsuarioDuplicadoException("El usuario ya existe en el sistema");
			}
		}
		usuarios.add(nuevoUsuario);
	}

	// ready
	public void crearAdministrativo(String nombre, String apellido, String dni, ArrayList<Sede> sedes)
			throws UsuarioDuplicadoException {
		UsuarioSingleton usuarioSingleton = UsuarioSingleton.getInstance();
		Usuario usuarioActual = usuarioSingleton.getUsuarioActual();
		if (usuarioActual.soySoporteTecnico()) {
			Administrativo nuevoUsuarioAdministrativo = new Administrativo(nombre, apellido, dni, sedes);
			agregarUsuario(nuevoUsuarioAdministrativo);
		}
	}

	// ready
	public void crearSoporteTecnico(String nombre, String apellido, String dni) throws UsuarioDuplicadoException {
		UsuarioSingleton usuarioSingleton = UsuarioSingleton.getInstance();
		Usuario usuarioActual = usuarioSingleton.getUsuarioActual();
		if (usuarioActual.soySoporteTecnico()) {
			SoporteTecnico nuevoUsuarioSoporteTecnico = new SoporteTecnico(nombre, apellido, dni);
			agregarUsuario(nuevoUsuarioSoporteTecnico);
		}
	}

	// ready
	public void crearSocio(String nombre, String apellido, String dni, Nivel nivel) throws UsuarioDuplicadoException {
		UsuarioSingleton usuarioSingleton = UsuarioSingleton.getInstance();
		Usuario usuarioActual = usuarioSingleton.getUsuarioActual();
		if (usuarioActual.soySoporteTecnico() || usuarioActual.soyAdministrativo()) {
			Socio nuevoSocio = new Socio(nombre, apellido, dni, nivel);
			agregarUsuario(nuevoSocio);
		}
	}

	// ready
	public void agregarSede(String barrio, Nivel nivel, double precioAlquiler) throws SedeExistenteException {
		UsuarioSingleton usuarioSingleton = UsuarioSingleton.getInstance();
		Usuario usuarioActual = usuarioSingleton.getUsuarioActual();
		if (usuarioActual.soySoporteTecnico()) {
			Sede nuevaSede = new Sede(barrio, precioAlquiler, nivel);
			for (Sede sede : sedes) {
				if (sede.getBarrio().equals(nuevaSede.getBarrio())) {
					throw new SedeExistenteException("Ya existe una sede para el barrio: " + barrio);
				}
			}
			System.out.println("Sede creada con exito");
			System.out.println(nuevaSede);
			sedes.add(nuevaSede);
		}
	}

	// ready
	public void agregarTipoArticulo(String nombre, int numeroUsos, TipoAmortizacion tipoAmortizacion) {
		UsuarioSingleton usuarioSingleton = UsuarioSingleton.getInstance();
		Usuario usuarioActual = usuarioSingleton.getUsuarioActual();
		if (usuarioActual.soySoporteTecnico()) {
			TipoArticulo nuevoTipoArticulo = new TipoArticulo(nombre, numeroUsos, tipoAmortizacion);
			System.out.println(nuevoTipoArticulo);
			tiposArticulos.add(nuevoTipoArticulo);
		}
	}

	// ready
	public void agregarTipoClase(String nombreClase, boolean online,
			HashMap<Integer, ArrayList<CantidadDetalle>> cantidadArticulo) {
		UsuarioSingleton usuarioSingleton = UsuarioSingleton.getInstance();
		Usuario usuarioActual = usuarioSingleton.getUsuarioActual();
		if (usuarioActual.soySoporteTecnico()) {
			TipoClase nuevoTipoClase = new TipoClase(nombreClase, online, cantidadArticulo);
			System.out.println(nuevoTipoClase);
			tiposClases.add(nuevoTipoClase);
		}
	}

	// METODOS ADMINISTRATIVO

	public void darBajaArticulo(Articulo articulo) {
		UsuarioSingleton usuarioSingleton = UsuarioSingleton.getInstance();
		Usuario usuarioActual = usuarioSingleton.getUsuarioActual();
		if (usuarioActual.soyAdministrativo()) {
			articulo.darBaja();
		}
	}

	// ready
	public void agendarClase(TipoClase tipoClase, Sede sede, Emplazamiento emplazamiento, LocalDateTime dateInicio,
			LocalDateTime dateFin) throws ArticulosInsuficientesException {
		UsuarioSingleton usuarioSingleton = UsuarioSingleton.getInstance();
		Usuario usuarioActual = usuarioSingleton.getUsuarioActual();
		if (usuarioActual.soyAdministrativo()) {
			sede.agendarClase(tipoClase, emplazamiento, dateInicio, dateFin);
		}
	}

	// ready
	public void asginarProfesor(Clase clase, Profesor profesor) throws ProfesorNoDisponibleException {
		clase.asignarProfesor(profesor);
	}

	// ready
	public void cambiarEstadoClase(Clase clase, EstadoClase nuevoEstado) {
		UsuarioSingleton usuarioSingleton = UsuarioSingleton.getInstance();
		Usuario usuarioActual = usuarioSingleton.getUsuarioActual();
		if (usuarioActual.soyAdministrativo()) {
			clase.cambiarEstado(nuevoEstado);
		}
	}

	public void agregarEmplazamiento(Sede sede, TipoEmplazamiento tipoEmplazamiento, double superficie) {
		UsuarioSingleton usuarioSingleton = UsuarioSingleton.getInstance();
		Usuario usuarioActual = usuarioSingleton.getUsuarioActual();
		if (usuarioActual.soyAdministrativo()) {
			sede.agregarEmplazamiento(new Emplazamiento(tipoEmplazamiento, superficie));
			System.out.println(sede.getEmplazamientos());
		}
	}

	// ready
	public void incorporarArticulos(Sede sede, TipoArticulo tipoArticulo, String descripcion, double precio,
			int cantidad) {
		UsuarioSingleton usuarioSingleton = UsuarioSingleton.getInstance();
		Usuario usuarioActual = usuarioSingleton.getUsuarioActual();
		if (usuarioActual.soyAdministrativo()) {
			sede.incorporarArticulo(tipoArticulo, descripcion, precio, cantidad);
		}
	}

	public void modificarSocio(Socio socio, String nombre, String apellido, Nivel nivel, boolean isActive) {
		UsuarioSingleton usuarioSingleton = UsuarioSingleton.getInstance();
		Usuario usuarioActual = usuarioSingleton.getUsuarioActual();
		if (usuarioActual.soyAdministrativo()) {
			socio.setNombre(nombre);
			socio.setApellido(apellido);
			socio.setIsActive(isActive);
			socio.setNivel(nivel);
		}
	}

	// METODOS DE USUARIO

	public void inscribirseClase(Clase clase) throws InscripcionNoDisponibleException {
		UsuarioSingleton usuarioSingleton = UsuarioSingleton.getInstance();
		Usuario usuarioActual = usuarioSingleton.getUsuarioActual();
		if (usuarioActual.soySocio()) { // Verifica que el usuario es un socio
			clase.inscribirse(usuarioActual);
		}
	}

	// METODOS PARA MANEJO DE INFO EN VISTAS

	// Getters and Setter

	public ArrayList<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setSedes(ArrayList<Sede> sedes) { // eliminar
		this.sedes = sedes;
	}

	public ArrayList<TipoClase> getTiposClase() {
		return tiposClases;
	}

	public ArrayList<TipoArticulo> getTiposArticulos() {
		return tiposArticulos;
	}

	public ArrayList<Articulo> getArticulosUnicos() {
		ArrayList<Articulo> articulosUnicosSedes = new ArrayList<Articulo>();
		for (Sede sede : sedes) {
			ArrayList<Articulo> articulosSede = sede.getArticulos();
			for (Articulo articulo : articulosSede) {
				boolean flag = true;
				for (Articulo articulo2 : articulosUnicosSedes) {
					if (articulo.getNombre().equals(articulo2.getNombre())
							&& articulo.getDescripcion().equals(articulo2.getDescripcion())) {
						flag = false;
						break;
					}
				}
				if (flag) {
					articulosUnicosSedes.add(articulo);
				}
			}
		}
		return articulosUnicosSedes;
	}

	public ArrayList<Sede> getSedes() {
		return sedes;
	}

}

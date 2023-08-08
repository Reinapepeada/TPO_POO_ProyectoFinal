package modelo;

import vista.Login;
import vista.TablaArticuloInterfaz;

import java.awt.GraphicsEnvironment;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;

import controlador.WindowManagerSingleton;
import excepciones.UsuarioDuplicadoException;

public class Main {

	public static void main(String[] args) {

		SupertlonSingleton supertlonSingleton = SupertlonSingleton.getInstance();

		ArrayList<Profesor> profesores = new ArrayList<Profesor>();
		var profe1 = new Profesor("Juan", "Perez", 4000);
		var profe2 = new Profesor("Lua", "Gomez", 2000);
		var profe3 = new Profesor("Nicolas", "Suarez", 7000);
		profesores.add(profe1);
		profesores.add(profe2);
		profesores.add(profe3);
		supertlonSingleton.setProfesores(profesores);
		
		/*
		ArrayList<Sede> sedes = new ArrayList<Sede>();
		ArrayList<Articulo> articulos = new ArrayList<Articulo>();



		var arti = new TipoArticulo("Colchoneta Gadnic", 2, TipoAmortizacion.USO);

		for (int i = 32; i > 0; i--) {
			articulos.add(new Articulo(arti, "Pro", 500));
		}

		var palermo = new Sede("Palermo", 100, Nivel.BLACK);
		palermo.setArticulos(articulos);

		var flores = new Sede("Flores", 50, Nivel.ORO);

		sedes.add(palermo);
		sedes.add(flores);
		sedes.add(new Sede("Recoleta", 1000, Nivel.PLATINUM));
		sedes.add(new Sede("Caballito", 1000, Nivel.BLACK));
		sedes.add(new Sede("Abasto", 1100, Nivel.ORO));
		sedes.add(new Sede("Villa Urquiza", 200, Nivel.BLACK));
		sedes.add(new Sede("Chacarita", 5000, Nivel.PLATINUM));
		sedes.add(new Sede("Microcentro", 800, Nivel.PLATINUM));
		sedes.add(new Sede("San Nicolas", 1000, Nivel.ORO));

		supertlonSingleton.setSedes(sedes);

		var usuario6 = new Administrativo("Mison", "Lezagon", "2", sedes);
		var usuario7 = new Socio("Pedro", "Paul", "3", Nivel.ORO);
		
*/
		
		var soporteTecnicoDefault = new SoporteTecnico("Simon", "Gonzalez", "1");
		try {
			supertlonSingleton.agregarUsuario(soporteTecnicoDefault);
		} catch (UsuarioDuplicadoException e) {
			e.printStackTrace();
		}

		WindowManagerSingleton windowManager = WindowManagerSingleton.getInstance();
		windowManager.switchWindow(new Login());

	}

}

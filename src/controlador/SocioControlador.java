package controlador;

import java.util.ArrayList;

import excepciones.InscripcionNoDisponibleException;
import modelo.Clase;
import modelo.Sede;
import modelo.Socio;
import modelo.SupertlonSingleton;
import modelo.UsuarioSingleton;

public class SocioControlador {

	public void inscribirseClase(Clase clase) throws InscripcionNoDisponibleException {
		SupertlonSingleton supertlonSingleton = SupertlonSingleton.getInstance();
		if (UsuarioSingleton.getInstance().getUsuarioActual().soySocio()) {
			supertlonSingleton.inscribirseClase(clase);
		}

	}

	public boolean inscripto(Clase clase) {
		if (UsuarioSingleton.getInstance().getUsuarioActual().soySocio()) {
			Socio socio = (Socio) UsuarioSingleton.getInstance().getUsuarioActual();
			return socio.inscripto(clase);
		}
		return false;
	}

	public ArrayList<Sede> recuperarSedesDisponibles() {
		if (UsuarioSingleton.getInstance().getUsuarioActual().soySocio()) {
			Socio socio = (Socio) UsuarioSingleton.getInstance().getUsuarioActual();
			return socio.recuperarSedesDisponibles();
		}
		return null;
	}

}

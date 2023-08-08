package controlador;

import excepciones.UsuarioInexistenteException;
import modelo.SupertlonSingleton;
import modelo.UsuarioSingleton;
import vista.HomeAdministrativo;
import vista.HomeSocio;
import vista.HomeSoporteTecnico;
import vista.Login;

public class AccesoControlador {

	SupertlonSingleton supertlonSingleton = SupertlonSingleton.getInstance();
	WindowManagerSingleton windowManager = WindowManagerSingleton.getInstance();

	public void ingresar(String dni) throws UsuarioInexistenteException {
		supertlonSingleton.ingresar(dni);
		if (UsuarioSingleton.getInstance().getUsuarioActual().soySoporteTecnico()) {
			System.out.println("Soporte Tecnico");
			windowManager.switchWindow(new HomeSoporteTecnico());
		}
		if (UsuarioSingleton.getInstance().getUsuarioActual().soyAdministrativo()) {
			System.out.println("Administrativo");
			 windowManager.switchWindow(new HomeAdministrativo());
		}
		if (UsuarioSingleton.getInstance().getUsuarioActual().soySocio()) {
			System.out.println("Socio");
			windowManager.switchWindow(new HomeSocio());
		}
	}

	public void cerrarSesion() {
		windowManager.switchWindow(new Login());
	}

}

package vista;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controlador.AccesoControlador;
import controlador.WindowManagerSingleton;
import modelo.SupertlonSingleton;

public class HomeAdministrativo extends JPanel {

	public HomeAdministrativo() {
		setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		
		JButton gestionarSedesMenu = LibUI.crearBotonStandar("Administrar Sedes");
		JButton gestionarClasesMenu = LibUI.crearBotonStandar("Administrar Clases");
		JButton gestionarSociosMenu = LibUI.crearBotonStandar("Administrar Socios");
		JButton logoutButton = LibUI.crearBotonStandar("Cerrar sesión");

		gestionarSedesMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowManagerSingleton windowManager = WindowManagerSingleton.getInstance();
				windowManager.switchWindow(new SedesMenu());
			}
		});

		gestionarClasesMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowManagerSingleton windowManager = WindowManagerSingleton.getInstance();
				windowManager.switchWindow(new ClasesMenu());

			}
		});

		gestionarSociosMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowManagerSingleton windowManager = WindowManagerSingleton.getInstance();
				windowManager.switchWindow(new SociosMenu());

			}
		});
		
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AccesoControlador().cerrarSesion();
			}
		});
		
		add(gestionarSedesMenu, gbc);

		gbc.gridx = 1;
		add(gestionarClasesMenu, gbc);

		gbc.gridx = 2;
		add(gestionarSociosMenu, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		add(logoutButton, gbc);

	}
}

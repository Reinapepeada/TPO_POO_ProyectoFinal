package vista;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controlador.AccesoControlador;
import controlador.WindowManagerSingleton;
import modelo.SupertlonSingleton;

public class HomeSoporteTecnico extends JPanel {

	public HomeSoporteTecnico() {
		setLayout(new GridBagLayout());
        setBackground(Color.decode("#14213d"));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.decode("#14213d"));

        JButton tipoArticuloMenu = LibUI.crearBotonStandar("Agregar Tipo Articulo");
        JButton sedeMenu = LibUI.crearBotonStandar("Agregar Sede");
        JButton tipoClaseMenu = LibUI.crearBotonStandar("Crear Tipo Clase");
        JButton administrativoMenu = LibUI.crearBotonStandar("Crear Administrativo");
        JButton socioMenu = LibUI.crearBotonStandar("Crear Socio");
        JButton soporteTecnicoMenu = LibUI.crearBotonStandar("Crear Soporte Tecnico");
        JButton logoutButton = LibUI.crearBotonStandar("Cerrar sesión");

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        buttonPanel.add(tipoArticuloMenu, gbc);

        gbc.gridx = 1;
        buttonPanel.add(sedeMenu, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        buttonPanel.add(tipoClaseMenu, gbc);

        gbc.gridx = 1;
        buttonPanel.add(administrativoMenu, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        buttonPanel.add(socioMenu, gbc);

        gbc.gridx = 1;
        buttonPanel.add(soporteTecnicoMenu, gbc);

        panel.add(buttonPanel, BorderLayout.CENTER);

        panel.add(logoutButton, BorderLayout.SOUTH);

        GridBagConstraints gbcPanel = new GridBagConstraints();
        gbcPanel.gridx = 0;
        gbcPanel.gridy = 0;
        gbcPanel.weightx = 1.0;
        gbcPanel.weighty = 1.0;
        gbcPanel.anchor = GridBagConstraints.CENTER;
        add(panel, gbcPanel);

        UIManager.put("Button.background", Color.decode("#fca311"));
        UIManager.put("Button.foreground", Color.decode("#14213d"));
        UIManager.put("Button.font", new Font("Dubai", Font.BOLD, 14));
        
		sedeMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowManagerSingleton windowManager = WindowManagerSingleton.getInstance();
				windowManager.switchWindow(new CreacionSede());
			}
		});

		tipoArticuloMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowManagerSingleton windowManager = WindowManagerSingleton.getInstance();
				windowManager.switchWindow(new CreacionTipoArticulo());

			}
		});

		tipoClaseMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowManagerSingleton windowManager = WindowManagerSingleton.getInstance();
				windowManager.switchWindow(new CreacionTipoClase());

			}
		});

		socioMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowManagerSingleton windowManager = WindowManagerSingleton.getInstance();
				windowManager.switchWindow(new CreacionSocio());
			}
		});

		soporteTecnicoMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowManagerSingleton windowManager = WindowManagerSingleton.getInstance();
				windowManager.switchWindow(new CreacionSoporteTecnico());
			}
		});

		administrativoMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowManagerSingleton windowManager = WindowManagerSingleton.getInstance();
				windowManager.switchWindow(new CreacionAdministrativo(SupertlonSingleton.getInstance().getSedes()));
			}
		});

		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AccesoControlador().cerrarSesion();
			}
		});

	}
	

}

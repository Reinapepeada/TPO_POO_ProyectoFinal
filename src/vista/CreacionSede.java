package vista;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controlador.SoporteTecnicoControlador;
import controlador.WindowManagerSingleton;
import excepciones.SedeExistenteException;
import modelo.Nivel;

public class CreacionSede extends JPanel {

	public CreacionSede() {

		this.setLayout(new BorderLayout());

		this.setLayout(new GridBagLayout());

		JPanel formPanel = new JPanel(new GridLayout(3, 2));
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		JLabel label1 = LibUI.crearLabelStandar("Barrio");
		JLabel label2 = LibUI.crearLabelStandar("Nivel");
		JLabel label3 = LibUI.crearLabelStandar("Precio de Alquiler");

		JTextField textField1 = LibUI.crearTextfieldStandar();
		JComboBox<Nivel> comboBox = new JComboBox<>(Nivel.values());
		JTextField textField3 = LibUI.crearTextfieldStandar();

		formPanel.add(label1);
		formPanel.add(textField1);
		formPanel.add(label2);
		formPanel.add(comboBox);
		formPanel.add(label3);
		formPanel.add(textField3);

		JButton createButton = LibUI.crearBotonStandar("Crear");
		JButton volverButton = LibUI.crearBotonStandar("Volver");

		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new SoporteTecnicoControlador().agregarSede(textField1.getText(),
							(Nivel) comboBox.getSelectedItem(), Double.parseDouble(textField3.getText()));
					LibUI.mostrarMensajeOk(CreacionSede.this, "Sede creada con éxito");
				} catch (SedeExistenteException e1) {
					LibUI.mostrarMensajeError(CreacionSede.this, e1.getMessage());
				} catch (Exception e2) {
					LibUI.mostrarMensajeError(CreacionSede.this, e2.getMessage());
				}

			}
		});

		volverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowManagerSingleton windowManager = WindowManagerSingleton.getInstance();
				windowManager.switchWindow(new HomeSoporteTecnico());
			}
		});

		buttonPanel.add(createButton);
		buttonPanel.add(volverButton);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weighty = 0.5;
		this.add(Box.createVerticalGlue(), gbc);

		gbc.gridy = 1;
		gbc.weighty = 0;
		this.add(formPanel, gbc);

		gbc.gridy = 2;
		gbc.weighty = 0.5;
		this.add(Box.createVerticalGlue(), gbc);

		gbc.gridy = 3;
		gbc.weighty = 0;
		this.add(buttonPanel, gbc);
	}

}

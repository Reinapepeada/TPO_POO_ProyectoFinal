package vista;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import controlador.SoporteTecnicoControlador;
import controlador.WindowManagerSingleton;
import modelo.TipoAmortizacion;

public class CreacionTipoArticulo extends JPanel {

	public CreacionTipoArticulo() {

		setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.insets = new Insets(5, 5, 5, 5);

		JLabel label1 = LibUI.crearLabelStandar("Articulo & Marca");
		JLabel label2 = LibUI.crearLabelStandar("Cantidad de usos/dias");
		JLabel label4 = LibUI.crearLabelStandar("Tipo Amortización");

		JTextField textField1 = LibUI.crearTextfieldStandar();
		JTextField textField2 = LibUI.crearTextfieldStandar();
		JComboBox<TipoAmortizacion> comboBox = new JComboBox<>(TipoAmortizacion.values());

		add(label1, gbc);
		add(textField1, gbc);
		add(label2, gbc);
		add(textField2, gbc);
		add(label4, gbc);
		add(comboBox, gbc);

		JPanel buttonPanel = new JPanel();

		JButton createButton = LibUI.crearBotonStandar("Crear");
		JButton volverButton = LibUI.crearBotonStandar("Volver");

		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowManagerSingleton windowManager = WindowManagerSingleton.getInstance();
				try {
					new SoporteTecnicoControlador().agregarTipoArticulo(textField1.getText(),
							Integer.parseInt(textField2.getText()), (TipoAmortizacion) comboBox.getSelectedItem());
					LibUI.mostrarMensajeOk(CreacionTipoArticulo.this, "Tipo de Articulo creado con éxito");
				} catch (NumberFormatException e1) {
					// TODO: handle exception
					LibUI.mostrarMensajeError(CreacionTipoArticulo.this, "Ocurrió un error: " + e1.getMessage());
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

		add(buttonPanel, gbc);
	}

}

package vista;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controlador.SoporteTecnicoControlador;
import controlador.WindowManagerSingleton;
import excepciones.UsuarioDuplicadoException;
import modelo.Nivel;

public class CreacionSoporteTecnico extends JPanel {

	public CreacionSoporteTecnico() {

		setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.insets = new Insets(5, 5, 5, 5);

		JLabel label1 = LibUI.crearLabelStandar("Nombre");
		JLabel label2 = LibUI.crearLabelStandar("Apellido");
		JLabel label3 = LibUI.crearLabelStandar("DNI");

		JTextField textField1 = LibUI.crearTextfieldStandar();
		JTextField textField2 = LibUI.crearTextfieldStandar();
		JTextField textField3 = LibUI.crearTextfieldStandar();

		add(label1, gbc);
		add(textField1, gbc);
		add(label2, gbc);
		add(textField2, gbc);
		add(label3, gbc);
		add(textField3, gbc);

		JPanel buttonPanel = new JPanel();

		JButton createButton = LibUI.crearBotonStandar("Crear");
		JButton volverButton = LibUI.crearBotonStandar("Volver");

		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new SoporteTecnicoControlador().crearSoporteTecnico(textField1.getText(), textField2.getText(),
							textField3.getText());
					LibUI.mostrarMensajeOk(CreacionSoporteTecnico.this, "Soporte Tecnico dado de alta con éxito");
				} catch (UsuarioDuplicadoException e1) {
					LibUI.mostrarMensajeError(CreacionSoporteTecnico.this, e1.getMessage());
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

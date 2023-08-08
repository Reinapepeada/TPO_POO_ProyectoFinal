package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import controlador.AdministrativoControlador;
import controlador.SoporteTecnicoControlador;
import controlador.WindowManagerSingleton;
import excepciones.UsuarioDuplicadoException;
import modelo.Nivel;
import modelo.Socio;
import modelo.UsuarioSingleton;

public class ModifiacionSocio extends JPanel {

	public ModifiacionSocio(Socio socio) {
		setLayout(new BorderLayout(10, 10));

		JPanel formPanel = new JPanel(new GridBagLayout());
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

		JLabel label1 = LibUI.crearLabelStandar("Nombre");
		JLabel label2 = LibUI.crearLabelStandar("Apellido");
		JLabel label4 = LibUI.crearLabelStandar("Nivel");
		JLabel label5 = LibUI.crearLabelStandar("Activo");

		JTextField textField1 = LibUI.crearTextfieldStandar();
		textField1.setText(socio.getNombre());
		JTextField textField2 = LibUI.crearTextfieldStandar();
		textField2.setText(socio.getApellido());
		JComboBox<Nivel> comboBox = new JComboBox<>(Nivel.values());
		JRadioButton yesRadioButton = new JRadioButton("Si");
		JRadioButton noRadioButton = new JRadioButton("No");
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(yesRadioButton);
		buttonGroup.add(noRadioButton);
		yesRadioButton.setSelected(true);
		
		JButton guardarButton = LibUI.crearBotonStandar("Guardar");
		JButton volverButton = LibUI.crearBotonStandar("Volver");
		
		guardarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean seleccionado = yesRadioButton.isSelected();
				new AdministrativoControlador().modificarSocio(socio, textField1.getText(), textField2.getText(),
						(Nivel) comboBox.getSelectedItem(), seleccionado);
				LibUI.mostrarMensajeOk(ModifiacionSocio.this, "Socio modificado con éxito");
			}
		});

		volverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowManagerSingleton windowManager = WindowManagerSingleton.getInstance();
				windowManager.switchWindow(new SociosMenu());
			}
		});
		

		JPanel labelFieldPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 10, 5, 10);
		labelFieldPanel.add(label1, gbc);

		gbc.gridy++;
		labelFieldPanel.add(label2, gbc);

		gbc.gridy++;
		labelFieldPanel.add(label4, gbc);

		gbc.gridy++;
		labelFieldPanel.add(label5, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		labelFieldPanel.add(textField1, gbc);

		gbc.gridy++;
		labelFieldPanel.add(textField2, gbc);

		gbc.gridy++;
		labelFieldPanel.add(comboBox, gbc);

		gbc.gridy++;
		JPanel radioButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		radioButtonPanel.add(yesRadioButton);
		radioButtonPanel.add(noRadioButton);
		labelFieldPanel.add(radioButtonPanel, gbc);
		
		

		buttonPanel.add(guardarButton);
		buttonPanel.add(volverButton);

		formPanel.add(labelFieldPanel);

		add(formPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
	}
}

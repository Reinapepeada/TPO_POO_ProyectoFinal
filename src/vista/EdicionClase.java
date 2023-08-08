package vista;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controlador.AdministrativoControlador;
import controlador.WindowManagerSingleton;
import excepciones.ProfesorNoDisponibleException;
import modelo.Clase;
import modelo.EstadoClase;
import modelo.Profesor;
import modelo.SupertlonSingleton;

public class EdicionClase extends JPanel {

	private ArrayList<Profesor> profesores = new ArrayList<>();
	private JComboBox<Profesor> profesorComboBox;
	private JComboBox<EstadoClase> estadoComboBox;
	private Clase clase;
	private boolean flag;

	public EdicionClase(Clase claseSelected) {
		this.clase = claseSelected;
		flag = clase.getProfesor() == null ? false : true;

		this.setLayout(new GridBagLayout());

		JPanel formPanel = new JPanel(new GridLayout(4, 2));
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		JLabel label1 = LibUI.crearLabelStandar("Profesor");
		JLabel label2 = LibUI.crearLabelStandar("Estado");

		profesores = SupertlonSingleton.getInstance().getProfesores();

		profesorComboBox = new JComboBox<>();
		for (Profesor profesor : profesores) {
			profesorComboBox.addItem(profesor);
		}
		profesorComboBox.setRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				if (value instanceof Profesor) {
					Profesor profesor = (Profesor) value;
					value = profesor.getNombre() + " " + profesor.getApellido();
				}
				return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			}
		});

		estadoComboBox = new JComboBox<>(EstadoClase.values());

		formPanel.add(label1);
		formPanel.add(profesorComboBox);
		formPanel.add(label2);
		formPanel.add(estadoComboBox);

		JButton asignarProfesorButton = LibUI.crearBotonStandar("Asignar Profesor");
		JButton guardarEstadoButton = LibUI.crearBotonStandar("Guardar Estado");
		JButton volverButton = LibUI.crearBotonStandar("Volver");

		asignarProfesorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Profesor selectedProfesor = (Profesor) profesorComboBox.getSelectedItem();
				try {
					new AdministrativoControlador().asginarProfesor(claseSelected, selectedProfesor);
					flag = true;
					LibUI.mostrarMensajeOk(EdicionClase.this, "Profesor Asignado");
				} catch (ProfesorNoDisponibleException e1) {
					LibUI.mostrarMensajeError(EdicionClase.this, e1.getMessage());
				}
			}
		});

		guardarEstadoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EstadoClase selectedEstado = (EstadoClase) estadoComboBox.getSelectedItem();
				if (selectedEstado == EstadoClase.FINALIZADA && !flag) {
					LibUI.mostrarMensajeError(EdicionClase.this,
							"La clase debe tener un profesor asignado para poder ejecutarse");
				} else {
					new AdministrativoControlador().cambiarEstadoClase(claseSelected, selectedEstado);
					LibUI.mostrarMensajeOk(EdicionClase.this, "Estado de clase guardado con éxito");
				}

			}
		});

		volverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowManagerSingleton.getInstance().switchWindow(new ClasesMenu());
			}
		});

		buttonPanel.add(asignarProfesorButton);
		buttonPanel.add(guardarEstadoButton);
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

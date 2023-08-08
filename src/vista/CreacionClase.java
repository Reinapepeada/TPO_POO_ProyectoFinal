package vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controlador.AdministrativoControlador;
import controlador.WindowManagerSingleton;
import excepciones.ArticulosInsuficientesException;
import modelo.Emplazamiento;
import modelo.Sede;
import modelo.SupertlonSingleton;
import modelo.TipoClase;

public class CreacionClase extends JPanel {
	private ArrayList<TipoClase> tipoClases;
	private ArrayList<Sede> sedesAdministradas;
	private JComboBox<TipoClase> tipoClaseComboBox;
	private JComboBox<Sede> sedeComboBox;
	private JComboBox<Emplazamiento> emplazamientoComboBox;
	private JTextField horaInicioTextField;
	private JTextField horaFinalizacionTextField;

	public CreacionClase() {
		tipoClases = SupertlonSingleton.getInstance().getTiposClase();
		sedesAdministradas = new AdministrativoControlador().recuperarSedesAdministradas();

		setLayout(new BorderLayout());

		JPanel formPanel = new JPanel(new GridLayout(5, 2));
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		JLabel tipoClaseLabel = LibUI.crearLabelStandar("Tipo de Clase");
		JLabel sedeLabel = LibUI.crearLabelStandar("Sede");
		JLabel emplazamientoLabel = LibUI.crearLabelStandar("Emplazamiento");
		JLabel horaInicioLabel = LibUI.crearLabelStandar("Hora de Inicio (yyyy-MM-dd HH:mm)");
		JLabel horaFinalizacionLabel = LibUI.crearLabelStandar("Hora de Finalización (yyyy-MM-dd HH:mm)");

		tipoClaseComboBox = new JComboBox<>();
		sedeComboBox = new JComboBox<>();
		emplazamientoComboBox = new JComboBox<>();
		horaInicioTextField = LibUI.crearTextfieldStandar();
		horaFinalizacionTextField = LibUI.crearTextfieldStandar();

		// Populate Tipo de Clase ComboBox
		DefaultComboBoxModel<TipoClase> tipoClaseComboBoxModel = new DefaultComboBoxModel<>();
		for (TipoClase tipoClase : tipoClases) {
			tipoClaseComboBoxModel.addElement(tipoClase);
		}
		tipoClaseComboBox.setModel(tipoClaseComboBoxModel);
		tipoClaseComboBox.setRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				if (value instanceof TipoClase) {
					TipoClase tipoClase = (TipoClase) value;
					value = tipoClase.getNombre();
				}
				return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			}
		});

		// Populate Sede ComboBox
		DefaultComboBoxModel<Sede> sedeComboBoxModel = new DefaultComboBoxModel<>();
		for (Sede sede : sedesAdministradas) {
			sedeComboBoxModel.addElement(sede);
		}
		sedeComboBox.setModel(sedeComboBoxModel);
		sedeComboBox.setRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				if (value instanceof Sede) {
					Sede sede = (Sede) value;
					value = sede.getBarrio();
				}
				return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			}
		});
		sedeComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Sede selectedSede = (Sede) sedeComboBox.getSelectedItem();
				updateEmplazamientoComboBox(selectedSede);
			}
		});

		formPanel.add(tipoClaseLabel);
		formPanel.add(tipoClaseComboBox);
		formPanel.add(sedeLabel);
		formPanel.add(sedeComboBox);
		formPanel.add(emplazamientoLabel);
		formPanel.add(emplazamientoComboBox);
		formPanel.add(horaInicioLabel);
		formPanel.add(horaInicioTextField);
		formPanel.add(horaFinalizacionLabel);
		formPanel.add(horaFinalizacionTextField);

		JButton createButton = LibUI.crearBotonStandar("Crear Clase");
		JButton volverButton = LibUI.crearBotonStandar("Volver");

		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TipoClase selectedTipoClase = (TipoClase) tipoClaseComboBox.getSelectedItem();
				Sede selectedSede = (Sede) sedeComboBox.getSelectedItem();
				Emplazamiento selectedEmplazamiento = (Emplazamiento) emplazamientoComboBox.getSelectedItem();
				String horaInicioText = horaInicioTextField.getText();
				String horaFinalizacionText = horaFinalizacionTextField.getText();

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

				try {
					LocalDateTime horaInicio = LocalDateTime.parse(horaInicioText, formatter);
					LocalDateTime horaFinalizacion = LocalDateTime.parse(horaFinalizacionText, formatter);
					new AdministrativoControlador().agendarClase(selectedTipoClase, selectedSede, selectedEmplazamiento,
							horaInicio, horaFinalizacion);
					LibUI.mostrarMensajeOk(CreacionClase.this, "Clase creada con éxito");
				} catch (ArticulosInsuficientesException e1) {
					LibUI.mostrarMensajeError(CreacionClase.this, e1.getMessage());
				} catch (Exception e2) {
					LibUI.mostrarMensajeError(CreacionClase.this, e2.getMessage());
				}
				
			}
		});

		volverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowManagerSingleton windowManager = WindowManagerSingleton.getInstance();
				windowManager.switchWindow(new ClasesMenu());
			}
		});

		buttonPanel.add(createButton);
		buttonPanel.add(volverButton);

		add(formPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
	}

	private void updateEmplazamientoComboBox(Sede selectedSede) {
		DefaultComboBoxModel<Emplazamiento> emplazamientoComboBoxModel = new DefaultComboBoxModel<>();
		ArrayList<Emplazamiento> emplazamientos = selectedSede.getEmplazamientos();
		for (Emplazamiento emplazamiento : emplazamientos) {
			emplazamientoComboBoxModel.addElement(emplazamiento);
		}
		emplazamientoComboBox.setModel(emplazamientoComboBoxModel);
		emplazamientoComboBox.setRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				if (value instanceof Emplazamiento) {
					Emplazamiento emplazamiento = (Emplazamiento) value;
					value = emplazamiento.getTipoEmplazamiento();
				}
				return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			}
		});
	}
}

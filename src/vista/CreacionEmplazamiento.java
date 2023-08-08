package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import controlador.AdministrativoControlador;
import controlador.SoporteTecnicoControlador;
import controlador.WindowManagerSingleton;
import excepciones.SedeExistenteException;
import modelo.Emplazamiento;
import modelo.Nivel;
import modelo.Sede;
import modelo.TipoEmplazamiento;

public class CreacionEmplazamiento extends JPanel {
	private Sede sede;
	private ArrayList<Emplazamiento> emplazamientos = new ArrayList<Emplazamiento>();

	public CreacionEmplazamiento(Sede sedeSelected) {
		this.sede = sedeSelected;
		this.emplazamientos = sede.getEmplazamientos();

		setLayout(new BorderLayout());

		JPanel mainPanel = new JPanel(new BorderLayout());

		// Emplazamientos table
		String[] columnNames = { "Tipo de Emplazamiento", "Superficie (m2)" };
		Object[][] rowData = new Object[emplazamientos.size()][2];

		for (int i = 0; i < emplazamientos.size(); i++) {
			Emplazamiento emplazamiento = emplazamientos.get(i);
			rowData[i][0] = emplazamiento.getTipoEmplazamiento();
			rowData[i][1] = emplazamiento.getSuperficie();
		}

		JTable emplazamientosTable = new JTable(rowData, columnNames);
		JScrollPane tableScrollPane = new JScrollPane(emplazamientosTable);

		JPanel tableTitlePanel = new JPanel();
		tableTitlePanel.setBackground(Color.WHITE);
		JLabel tableTitleLabel = LibUI.crearLabelStandar("Emplazamientos de la sede " + sede.getBarrio());

		mainPanel.add(tableTitlePanel, BorderLayout.NORTH);
		mainPanel.add(tableTitleLabel, BorderLayout.NORTH);
		mainPanel.add(tableScrollPane, BorderLayout.CENTER);

		JPanel formPanel = new JPanel(new GridLayout(3, 2));

		JLabel label2 = LibUI.crearLabelStandar("Emplazamiento");
		JLabel label3 = LibUI.crearLabelStandar("Superficie (m2)");

		JComboBox<TipoEmplazamiento> comboBox = new JComboBox<>(TipoEmplazamiento.values());
		JTextField textField3 = LibUI.crearTextfieldStandar();

		formPanel.add(label2);
		formPanel.add(comboBox);
		formPanel.add(label3);
		formPanel.add(textField3);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		JButton createButton = LibUI.crearBotonStandar("Crear");
		JButton volverButton = LibUI.crearBotonStandar("Volver");

		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new AdministrativoControlador().agregarEmplazamiento(sede,
							(TipoEmplazamiento) comboBox.getSelectedItem(), Double.parseDouble(textField3.getText()));
					LibUI.mostrarMensajeOk(CreacionEmplazamiento.this, "Emplazamiento agregado con éxito");
					WindowManagerSingleton.getInstance().switchWindow(new CreacionEmplazamiento(sede));
				} catch (Exception e2) {
					LibUI.mostrarMensajeError(CreacionEmplazamiento.this, e2.getMessage());
				}

			}
		});

		volverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowManagerSingleton windowManager = WindowManagerSingleton.getInstance();
				windowManager.switchWindow(new SedeSelectedMenu(sede));
			}
		});

		buttonPanel.add(createButton);
		buttonPanel.add(volverButton);

		add(mainPanel, BorderLayout.CENTER);
		add(formPanel, BorderLayout.NORTH);
		add(buttonPanel, BorderLayout.SOUTH);
	}

}

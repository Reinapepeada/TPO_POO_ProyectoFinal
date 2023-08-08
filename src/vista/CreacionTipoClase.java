package vista;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import controlador.SoporteTecnicoControlador;
import controlador.WindowManagerSingleton;
import excepciones.UsuarioDuplicadoException;
import modelo.Articulo;
import modelo.CantidadDetalle;
import modelo.SupertlonSingleton;

public class CreacionTipoClase extends JPanel {

	private JTable table;
	private JScrollPane scrollPane;
	private HashMap<Integer, ArrayList<CantidadDetalle>> cantidadPorTipoArticulo = new HashMap<>();

	public CreacionTipoClase() {
		ArrayList<Articulo> articulosUnicos = SupertlonSingleton.getInstance().getArticulosUnicos();

		// Obtiene los ID unicos para Tipo de Articulo
		HashSet<Integer> setIdTipoArticulo = new HashSet<>();
		for (Articulo art : articulosUnicos) {
			setIdTipoArticulo.add(art.getIdTipoArticulo());
		}
		ArrayList<Integer> intList = new ArrayList<>(setIdTipoArticulo);
		for (Integer integer : intList) {
			cantidadPorTipoArticulo.put(integer, new ArrayList<>());
		}

		setLayout(new BorderLayout());

		// Form panel
		JPanel formPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 5, 5);

		JLabel label1 = LibUI.crearLabelStandar("Ejercicio");
		JLabel label2 = LibUI.crearLabelStandar("Es online");

		JTextField textField1 = LibUI.crearTextfieldStandar();
		JRadioButton yesRadioButton = new JRadioButton("Yes");
		JRadioButton noRadioButton = new JRadioButton("No");
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(yesRadioButton);
		buttonGroup.add(noRadioButton);

		gbc.gridx = 0;
		gbc.gridy = 0;
		formPanel.add(label1, gbc);

		gbc.gridx = 1;
		formPanel.add(textField1, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		formPanel.add(label2, gbc);

		gbc.gridx = 1;
		formPanel.add(yesRadioButton, gbc);

		gbc.gridx = 2;
		formPanel.add(noRadioButton, gbc);

		add(formPanel, BorderLayout.NORTH);

		// Table panel
		JPanel tablePanel = new JPanel(new BorderLayout());

		String[] columnNames = { "Articulo", "Cantidad por Alumno", "Cantidad por Profesor" };
		Object[][] data = new Object[articulosUnicos.size()][3];

		for (int i = 0; i < articulosUnicos.size(); i++) {
			Articulo articulo = articulosUnicos.get(i);
			String nombreDescripcion = articulo.getNombre() + " " + articulo.getDescripcion();
			data[i][0] = articulo;
			data[i][1] = "";
			data[i][2] = "";
		}

		ArticuloTableModel tableModel = new ArticuloTableModel(columnNames, data);
		table = new JTable(tableModel) {
			@Override
			public TableCellRenderer getCellRenderer(int row, int column) {
				if (column == 0) {
					return new DefaultTableCellRenderer() {
						public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
							setText(((Articulo) value).getNombre() + " " + ((Articulo) value).getDescripcion());
							return this;
						}
					};
				} else {
					return super.getCellRenderer(row, column);
				}
			}
		};
		scrollPane = new JScrollPane(table);
		tablePanel.add(scrollPane, BorderLayout.CENTER);

		add(tablePanel, BorderLayout.CENTER);

		// Bottom buttons panel
		JPanel buttonPanel = new JPanel();

		JButton createButton = LibUI.crearBotonStandar("Crear");
		JButton volverButton = LibUI.crearBotonStandar("Volver");

		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean seleccionado = yesRadioButton.isSelected();
				if (table.isEditing()) {
					TableCellEditor cellEditor = table.getCellEditor();
					if (cellEditor != null) {
						cellEditor.stopCellEditing();
					}
				}
				// Fill hashmap with info
				for (int row = 0; row < table.getRowCount(); row++) {
					Articulo articulo = (Articulo) table.getValueAt(row, 0);
					String cantidadAlumno = (String) table.getValueAt(row, 1);
					String cantidadProfesor = (String) table.getValueAt(row, 2);
					String detalle = articulo.getDescripcion();
					int idTipoArticulo = articulo.getIdTipoArticulo();

					if (cantidadAlumno.equals("") && cantidadProfesor.equals("")) { // No agrega items vacios en ambos
																					// campos
						continue;
					}

					try {
						if (cantidadAlumno.equals("")) {
							cantidadAlumno = "0";
						}
						if (cantidadProfesor.equals("")) {
							cantidadProfesor = "0";
						}

						cantidadPorTipoArticulo.get(idTipoArticulo).add(new CantidadDetalle(
								Integer.parseInt(cantidadAlumno), Integer.parseInt(cantidadProfesor), detalle));
					} catch (Exception e1) {
						LibUI.mostrarMensajeError(CreacionTipoClase.this, e1.getMessage());
						WindowManagerSingleton.getInstance().switchWindow(new CreacionTipoClase());
						return;
					}

				}

				new SoporteTecnicoControlador().agregarTipoClase(textField1.getText(), seleccionado,
						cantidadPorTipoArticulo);
				LibUI.mostrarMensajeOk(CreacionTipoClase.this, "Tipo de Clase creada con exito");
				WindowManagerSingleton.getInstance().switchWindow(new CreacionTipoClase());
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

		add(buttonPanel, BorderLayout.SOUTH);
	}

	private class ArticuloTableModel extends AbstractTableModel {
		private String[] columnNames;
		private Object[][] data;

		public ArticuloTableModel(String[] columnNames, Object[][] data) {
			this.columnNames = columnNames;
			this.data = data;
		}

		@Override
		public int getRowCount() {
			return data.length;
		}

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public Object getValueAt(int row, int col) {
			return data[row][col];
		}

		@Override
		public void setValueAt(Object value, int row, int col) {
			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			// Make the "Cantidad por Alumno" and "Cantidad por Profesor" cells editable
			return col == 1 || col == 2;
		}

		@Override
		public String getColumnName(int col) {
			return columnNames[col];
		}

		@Override
		public Class<?> getColumnClass(int col) {
			return getValueAt(0, col).getClass();
		}
	}

}

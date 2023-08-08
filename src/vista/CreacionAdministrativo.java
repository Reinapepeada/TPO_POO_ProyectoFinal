package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import controlador.SoporteTecnicoControlador;
import controlador.WindowManagerSingleton;
import excepciones.UsuarioDuplicadoException;
import modelo.Sede;

public class CreacionAdministrativo extends JPanel {

	private JTable table;
	private JScrollPane scrollPane;
	private JCheckBox checkBox;
	private ArrayList<Sede> sedesSeleccionadas = new ArrayList<Sede>();

	public CreacionAdministrativo(ArrayList<Sede> sedes) {

		setLayout(new GridLayout(1, 2));

		JPanel formPanel = new JPanel();
		formPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(5, 5, 5, 5);

		JLabel label1 = LibUI.crearLabelStandar("Nombre");
		JLabel label2 = LibUI.crearLabelStandar("Apellido");
		JLabel label3 = LibUI.crearLabelStandar("DNI");

		JTextField textField1 = LibUI.crearTextfieldStandar();
		JTextField textField2 = LibUI.crearTextfieldStandar();
		JTextField textField3 = LibUI.crearTextfieldStandar();

		JButton createButton = LibUI.crearBotonStandar("Crear");
		JButton volverButton = LibUI.crearBotonStandar("Volver");

		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new SoporteTecnicoControlador().crearAdministrativo(textField1.getText(), textField2.getText(),
							textField3.getText(), sedesSeleccionadas);
					LibUI.mostrarMensajeOk(CreacionAdministrativo.this,
							"Administrativo dado de alta con éxito");
				} catch (UsuarioDuplicadoException e1) {
					LibUI.mostrarMensajeError(CreacionAdministrativo.this, e1.getMessage());
				}
			}
		});

		volverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowManagerSingleton windowManager = WindowManagerSingleton.getInstance();
				windowManager.switchWindow(new HomeSoporteTecnico());
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 0;
		formPanel.add(label1, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		formPanel.add(textField1, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		formPanel.add(label2, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		formPanel.add(textField2, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		formPanel.add(label3, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		formPanel.add(textField3, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(20, 5, 5, 5);
		formPanel.add(createButton, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		formPanel.add(volverButton, gbc);

		createTable(sedes);

		add(formPanel);
		add(scrollPane);
	}

	private void createTable(ArrayList<Sede> sedes) {
		SedeTableModel model = new SedeTableModel(sedes);
		table = new JTable(model);
		initializeCheckbox();
		table.setRowHeight(25);
		table.setFont(new Font("Dubai", Font.PLAIN, 14));
		table.getTableHeader().setFont(new Font("Dubai", Font.BOLD, 14));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		// Customize table header
		JTableHeader header = table.getTableHeader();
		header.setBackground(new Color(240, 240, 240));
		header.setOpaque(false);
		header.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.GRAY));
		header.setPreferredSize(new Dimension(0, 30));

		// Set custom cell renderer for center-aligned checkbox column
		TableColumnModel columnModel = table.getColumnModel();
		TableColumn column = columnModel.getColumn(2);
		column.setCellRenderer(new CheckBoxRenderer());
		column.setCellEditor(new CheckBoxEditor(checkBox));

		// Set custom cell renderer for center-aligned text columns
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		columnModel.getColumn(0).setCellRenderer(centerRenderer);
		columnModel.getColumn(1).setCellRenderer(centerRenderer);

		// Set column widths
		columnModel.getColumn(0).setPreferredWidth(200);
		columnModel.getColumn(1).setPreferredWidth(100);
		columnModel.getColumn(2).setPreferredWidth(80);

		scrollPane = new JScrollPane(table);
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
	}

	private void initializeCheckbox() {
		checkBox = new JCheckBox();
		TableColumnModel columnModel = table.getColumnModel();
		TableColumn column = columnModel.getColumn(2);
		column.setCellRenderer(new CheckBoxRenderer());
		column.setCellEditor(new CheckBoxEditor(checkBox));
	}

	// Clase interna para manejo tabla

	class SedeTableModel extends javax.swing.table.AbstractTableModel {
		private ArrayList<Sede> sedeList;
		private String[] columnNames = { "Barrio", "Nivel", "Selección" };
		private boolean[] selectedRows;

		public SedeTableModel(ArrayList<Sede> sedeList) {
			this.sedeList = sedeList;
			this.selectedRows = new boolean[sedeList.size()];
		}

		@Override
		public int getRowCount() {
			return sedeList.size();
		}

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public String getColumnName(int column) {
			return columnNames[column];
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			if (columnIndex == 2) {
				return Boolean.class;
			}
			return super.getColumnClass(columnIndex);
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return columnIndex == 2;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Sede sede = sedeList.get(rowIndex);
			if (columnIndex == 0) {
				return sede.getBarrio();
			} else if (columnIndex == 1) {
				return sede.getNivel();
			} else if (columnIndex == 2) {
				return selectedRows[rowIndex];
			}
			return null;
		}

		@Override
		public void setValueAt(Object value, int rowIndex, int columnIndex) {
			// Existing code...

			if (columnIndex == 2 && value instanceof Boolean) {
				selectedRows[rowIndex] = (Boolean) value;

				// Update the sedesSeleccionadas ArrayList based on the selected rows
				sedesSeleccionadas.clear();
				for (int i = 0; i < selectedRows.length; i++) {
					if (selectedRows[i]) {
						sedesSeleccionadas.add(sedeList.get(i));
					}
				}
			}
		}

		public boolean[] getSelectedRows() {
			return selectedRows;
		}
	}

}

// Manejo de checkboxes para seleccion de clases

class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {
	public CheckBoxRenderer() {
		setHorizontalAlignment(JCheckBox.CENTER);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		setSelected((value != null && (boolean) value));
		return this;
	}
}

class CheckBoxEditor extends AbstractCellEditor implements TableCellEditor, ItemListener {
	private JCheckBox checkBox;

	public CheckBoxEditor(JCheckBox checkBox) {
		this.checkBox = checkBox;
		checkBox.addItemListener(this);
	}

	@Override
	public Object getCellEditorValue() {
		return checkBox.isSelected();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		checkBox.setSelected((value != null && (boolean) value));
		return checkBox;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		super.fireEditingStopped();
	}

}

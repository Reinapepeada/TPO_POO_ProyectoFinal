package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractCellEditor;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import controlador.AdministrativoControlador;
import controlador.SocioControlador;
import controlador.WindowManagerSingleton;
import modelo.Sede;

public class HomeSocio extends JPanel {

	private ArrayList<Sede> sedesDisponibles;
	private String[] columnNames = { "Barrio", "Nivel", "Accion" };
	private DefaultTableModel tableModel;
	private JTable table;

	public HomeSocio() {
		sedesDisponibles = new SocioControlador().recuperarSedesDisponibles();

		initializeTable();
		addComponents();
	}

	private void initializeTable() {
		tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 2;
			}
		};

		for (Sede sede : sedesDisponibles) {
			Object[] rowData = { sede.getBarrio(), sede.getNivel(), "Seleccionar" };
			tableModel.addRow(rowData);
		}

		table = new JTable(tableModel);
		table.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());
		table.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor());
	}

	private void addComponents() {
		setLayout(new BorderLayout());
		JLabel titleLabel = new JLabel("Sedes Disponibles");
		titleLabel.setFont(new Font("Dubai", Font.BOLD, 16));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(titleLabel);
		panel.add(new JScrollPane(table));
		add(panel, BorderLayout.CENTER);
		JButton volverButton = LibUI.crearBotonStandar("Volver");
		volverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowManagerSingleton windowManager = WindowManagerSingleton.getInstance();
				windowManager.switchWindow(new Login());
			}
		});
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(volverButton);
		add(buttonPanel, BorderLayout.SOUTH);
	}

	private class ButtonRenderer extends DefaultTableCellRenderer {
		private JButton button;

		public ButtonRenderer() {
			button = new JButton("Seleccionar");
		}

		public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
				boolean hasFocus, int row, int column) {
			return button;
		}
	}

	private class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
		private JButton button;
		private Sede selectedSede;

		public ButtonEditor() {
			button = new JButton("Seleccionar");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int row = table.convertRowIndexToModel(table.getEditingRow());
					selectedSede = sedesDisponibles.get(row);
					fireEditingStopped();
					WindowManagerSingleton.getInstance().switchWindow(new SedeSelectedMenuSocio(selectedSede));

				}
			});
		}

		public java.awt.Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			return button;
		}

		public Object getCellEditorValue() {
			return button.getText();
		}
	}

}

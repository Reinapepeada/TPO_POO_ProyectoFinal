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
import controlador.WindowManagerSingleton;
import modelo.Articulo;
import modelo.Sede;
import modelo.TipoAmortizacion;
import modelo.TipoArticulo;

public class SedesMenu extends JPanel {

	private ArrayList<Sede> sedesAdministradas;
	private String[] columnNames = { "Barrio", "Accion" };
	private DefaultTableModel tableModel;
	private JTable table;

	public SedesMenu() {
		sedesAdministradas = new AdministrativoControlador().recuperarSedesAdministradas();

		JButton volverButton = LibUI.crearBotonStandar("Volver");
		volverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowManagerSingleton windowManager = WindowManagerSingleton.getInstance();
				windowManager.switchWindow(new HomeAdministrativo());
			}
		});

		initializeTable();
		addComponents();
	}

	private void initializeTable() {
		tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 1;
			}
		};

		for (Sede sede : sedesAdministradas) {
			Object[] rowData = { sede.getBarrio(), "Seleccionar" };
			tableModel.addRow(rowData);
		}

		table = new JTable(tableModel);
		table.getColumnModel().getColumn(1).setCellRenderer(new ButtonRenderer());
		table.getColumnModel().getColumn(1).setCellEditor(new ButtonEditor());
	}

	private void addComponents() {
		setLayout(new BorderLayout());
		JLabel titleLabel = new JLabel("Sedes Administradas");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
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
				windowManager.switchWindow(new HomeAdministrativo());
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
					selectedSede = sedesAdministradas.get(row);
					WindowManagerSingleton.getInstance().switchWindow(new SedeSelectedMenu(selectedSede));
					fireEditingStopped();
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



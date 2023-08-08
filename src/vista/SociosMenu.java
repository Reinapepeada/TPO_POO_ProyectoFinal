package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import controlador.AdministrativoControlador;
import controlador.WindowManagerSingleton;
import modelo.Clase;
import modelo.EstadoClase;
import modelo.Nivel;
import modelo.Socio;
import modelo.TipoEmplazamiento;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class SociosMenu extends JPanel {

	private ArrayList<Socio> socios;
	private String[] columnNames = { "Nombre", "Apellido", "DNI", "Nivel", "Estado", "Accion" };
	private DefaultTableModel tableModel;
	private JTable table;

	public SociosMenu() {
		this.socios = new AdministrativoControlador().recuperarSocios();

		initializeTable();
		addComponents();
	}

	private void initializeTable() {
		tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 5;
			}
		};

		for (Socio socio : socios) {
			String nombre = socio.getNombre();
			String apellido = socio.getApellido();
			String dni = socio.getDni();
			Nivel nivel = socio.getNivel();
			String estado = socio.getIsActive() ? "Activo" : "Dado de baja";
			Object[] rowData = { nombre, apellido, dni, estado, nivel, "Modificar Datos" };
			tableModel.addRow(rowData);
		}

		table = new JTable(tableModel);
		table.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
		table.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor());
	}

	private void addComponents() {
		setLayout(new BorderLayout());

		JLabel titulo = LibUI.crearLabelStandar("Listado de Socios");
		JButton altaSocio = LibUI.crearBotonStandar("Alta Nuevo Socio");
		JButton volverButton = LibUI.crearBotonStandar("Volver");
		JPanel topPanel = new JPanel();

		altaSocio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowManagerSingleton windowManager = WindowManagerSingleton.getInstance();
				windowManager.switchWindow(new CreacionSocio());
			}
		});

		volverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowManagerSingleton windowManager = WindowManagerSingleton.getInstance();
				windowManager.switchWindow(new HomeAdministrativo());
			}
		});
		topPanel.add(titulo);
		topPanel.add(altaSocio);
		topPanel.add(volverButton);
		add(topPanel, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(new JScrollPane(table));
		add(panel, BorderLayout.CENTER);
	}

	private class ButtonRenderer extends DefaultTableCellRenderer {
		private JButton button;

		public ButtonRenderer() {
			button = new JButton("Modificar Datos");
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			return button;
		}
	}

	private class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
		private JButton button;
		private Socio socioSeleccionado;

		public ButtonEditor() {
			button = new JButton("Modificar Datos");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int row = table.convertRowIndexToModel(table.getEditingRow());
					socioSeleccionado = socios.get(row);
					WindowManagerSingleton.getInstance().switchWindow(new ModifiacionSocio(socioSeleccionado));
					fireEditingStopped();
				}
			});
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			return button;
		}

		public Object getCellEditorValue() {
			return button.getText();
		}
	}

}

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
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import controlador.AdministrativoControlador;
import controlador.WindowManagerSingleton;
import modelo.Articulo;
import modelo.Sede;
import modelo.TipoAmortizacion;
import modelo.TipoArticulo;

public class SedeSelectedMenu extends JPanel {

	private ArrayList<Articulo> articulosSede;
	private String[] columnNames = { "Articulo", "Tipo de Amortizacion", "Usos / Dias restantes", "Estado", "Accion" };
	private DefaultTableModel tableModel;
	private JTable table;
	private Sede sedeSelected;

	public SedeSelectedMenu(Sede sedeSeleccionada) {
		this.sedeSelected = sedeSeleccionada;

		articulosSede = sedeSelected.getArticulos();

		renderGUI();
	}

	private void renderGUI() {
		if (articulosSede != null) {
			initializeTable();
		}
		addComponents();
	}

	private void initializeTable() {
		tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 4;
			}
		};

		for (Articulo articulo : articulosSede) {
			String nombreDescripcion = articulo.getNombre() + " " + articulo.getDescripcion();
			String tipoAmortizacion = articulo.getTipoAmortizacion().toString();
			String usosDiasRestantes = articulo.getNumeroUsos() < 1 ? "-" : String.valueOf(articulo.getNumeroUsos());
			String estado = articulo.isDesgastado() ? "Desgastado" : "En condiciones";
			Object[] rowData = { nombreDescripcion, tipoAmortizacion, usosDiasRestantes, estado, "Baja" };
			tableModel.addRow(rowData);
		}

		table = new JTable(tableModel);
		table.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
		table.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor());
	}

	private void addComponents() {
		setLayout(new BorderLayout());

		JLabel titulo = LibUI.crearLabelStandar("Sede " + sedeSelected.getBarrio());
		JButton emplazamientoMenu = LibUI.crearBotonStandar("Agregar Emplazamiento");
		JButton ingresoArticulosMenu = LibUI.crearBotonStandar("Incorporar Articulos");
		JButton volverButton = LibUI.crearBotonStandar("Volver");
		JPanel topPanel = new JPanel();

		volverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowManagerSingleton windowManager = WindowManagerSingleton.getInstance();
				windowManager.switchWindow(new SedesMenu());
			}
		});

		emplazamientoMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowManagerSingleton windowManager = WindowManagerSingleton.getInstance();
				windowManager.switchWindow(new CreacionEmplazamiento(sedeSelected));
			}
		});

		ingresoArticulosMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowManagerSingleton windowManager = WindowManagerSingleton.getInstance();
				windowManager.switchWindow(new CreacionArticulo(sedeSelected));
			}
		});

		topPanel.add(titulo);
		topPanel.add(emplazamientoMenu);
		topPanel.add(ingresoArticulosMenu);
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
			button = new JButton("Baja");
		}

		public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
				boolean hasFocus, int row, int column) {
			return button;
		}
	}

	private class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
		private JButton button;
		private Articulo articuloSeleccionado;

		public ButtonEditor() {
			button = new JButton("Baja");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int row = table.convertRowIndexToModel(table.getEditingRow());
					articuloSeleccionado = articulosSede.get(row);
					if (!articuloSeleccionado.isDesgastado()) {
						new AdministrativoControlador().darBajaArticulo(articuloSeleccionado);
						WindowManagerSingleton.getInstance().switchWindow(new SedeSelectedMenu(sedeSelected));
					} else {
						LibUI.mostrarMensajeError(SedeSelectedMenu.this, "El articulo ya se encuentra dado de baja");
					}
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

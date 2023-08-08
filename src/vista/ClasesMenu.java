package vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.AbstractCellEditor;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import controlador.AdministrativoControlador;
import controlador.WindowManagerSingleton;
import modelo.Clase;
import modelo.EstadoClase;
import modelo.Sede;
import modelo.TipoEmplazamiento;

public class ClasesMenu extends JPanel {

	private ArrayList<Clase> clasesAdministradas;
	private ArrayList<Clase> clasesAlmacenadas = new ArrayList<>();
	private String[] columnNames = { "Nombre", "Sede", "Socios Inscriptos", "Estado", "Lugar", "Hora de Inicio",
			"Hora de Finalizacion", "Costo de la clase", "Ingresos de la clase", "Profesor", "Accion" };
	private DefaultTableModel tableModel;
	private JTable table;

	public ClasesMenu() {
		this.clasesAdministradas = new AdministrativoControlador().recuperarClasesAdministradas();
		this.clasesAlmacenadas = new AdministrativoControlador().recuperarClasesAlmacenadas();
		initializeTable();
		addComponents();
	}

	private void initializeTable() {
		tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 10;
			}
		};

		for (Clase clase : clasesAdministradas) {
			String nombre = clase.getTipo().getNombre();
			String sede = clase.getSede().getBarrio();
			int cantidadSocios = clase.getSocios().size();
			EstadoClase estado = clase.getEstado();
			TipoEmplazamiento lugar = clase.getLugar().getTipoEmplazamiento();
			LocalDateTime horaInicio = clase.getHoraInicio();
			LocalDateTime horaFinalizacion = clase.getHoraFinal();
			double costoClase = clase.getCostoClase();
			double ingresoClase = clase.getIngresoClase();
			String profesor = clase.getProfesor() == null ? "No asignado"
					: clase.getProfesor().getNombre() + " " + clase.getProfesor().getApellido();
			Object[] rowData = { nombre, sede, cantidadSocios, estado, lugar, horaInicio, horaFinalizacion, costoClase,
					ingresoClase, profesor, "Administrar" };
			tableModel.addRow(rowData);
		}

		table = new JTable(tableModel);
		table.getColumnModel().getColumn(10).setCellRenderer(new ButtonRenderer());
		table.getColumnModel().getColumn(10).setCellEditor(new ButtonEditor());
	}

	private void addComponents() {
		setLayout(new BorderLayout());

		JLabel titulo = LibUI.crearLabelStandar("Clases Administradas");
		JButton verClasesButton = LibUI.crearBotonStandar("Ver Clases Almacenadas");
		JButton crearClaseButton = LibUI.crearBotonStandar("Crear Clase");
		JButton volverButton = LibUI.crearBotonStandar("Volver");
		JPanel topPanel = new JPanel();

		verClasesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				WindowManagerSingleton.getInstance().switchWindow(new ClasesAlmacenadas(clasesAlmacenadas));

			}
		});

		crearClaseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowManagerSingleton.getInstance().switchWindow(new CreacionClase());
			}
		});

		volverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowManagerSingleton windowManager = WindowManagerSingleton.getInstance();
				windowManager.switchWindow(new HomeAdministrativo());
			}
		});

		topPanel.add(titulo);
		topPanel.add(verClasesButton);
		topPanel.add(crearClaseButton);
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
			button = new JButton("Administrar");
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			return button;
		}
	}

	private class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
		private JButton button;
		private Clase claseSeleccionada;

		public ButtonEditor() {
			button = new JButton("Administrar");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int row = table.convertRowIndexToModel(table.getEditingRow());
					claseSeleccionada = clasesAdministradas.get(row);
					WindowManagerSingleton.getInstance().switchWindow(new EdicionClase(claseSeleccionada));
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

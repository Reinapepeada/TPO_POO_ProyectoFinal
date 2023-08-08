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
import controlador.SocioControlador;
import controlador.WindowManagerSingleton;
import excepciones.InscripcionNoDisponibleException;
import modelo.Clase;
import modelo.EstadoClase;
import modelo.Sede;
import modelo.TipoEmplazamiento;
import modelo.UsuarioSingleton;

public class SedeSelectedMenuSocio extends JPanel {

	private ArrayList<Clase> clasesSede;
	private Sede sede;
	private String[] columnNames = { "Nombre", "Inscripto", "Cantidad Alumnos", "Estado", "Lugar", "Hora de Inicio",
			"Hora de Finalizacion", "Profesor", "Accion" };
	private DefaultTableModel tableModel;
	private JTable table;

	public SedeSelectedMenuSocio(Sede selectedSede) {
		this.sede = selectedSede;
		this.clasesSede = selectedSede.getClases();
		initializeTable();
		addComponents();
	}

	private void initializeTable() {
		tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 8;
			}
		};

		for (Clase clase : clasesSede) {
			String nombre = clase.getTipo().getNombre();
			String inscripto = new SocioControlador().inscripto(clase) ? "Inscripto" : "-";
			int cantidadSocios = clase.getSocios().size();
			EstadoClase estado = clase.getEstado();
			TipoEmplazamiento lugar = clase.getLugar().getTipoEmplazamiento();
			LocalDateTime horaInicio = clase.getHoraInicio();
			LocalDateTime horaFinalizacion = clase.getHoraFinal();
			String profesor = clase.getProfesor() == null ? "No asignado"
					: clase.getProfesor().getNombre() + " " + clase.getProfesor().getApellido();
			Object[] rowData = { nombre, inscripto, cantidadSocios, estado, lugar, horaInicio, horaFinalizacion,
					profesor, "Inscribirse" };
			tableModel.addRow(rowData);
		}

		table = new JTable(tableModel);
		table.getColumnModel().getColumn(8).setCellRenderer(new ButtonRenderer());
		table.getColumnModel().getColumn(8).setCellEditor(new ButtonEditor());
	}

	private void addComponents() {
		setLayout(new BorderLayout());

		JLabel titulo = LibUI.crearLabelStandar("Clases sede " + sede.getBarrio());
		JButton volverButton = LibUI.crearBotonStandar("Volver");
		JPanel topPanel = new JPanel();

		volverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowManagerSingleton windowManager = WindowManagerSingleton.getInstance();
				windowManager.switchWindow(new HomeSocio());
			}
		});

		topPanel.add(titulo);
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
			button = new JButton("Inscribirse");
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
			button = new JButton("Inscribirse");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int row = table.convertRowIndexToModel(table.getEditingRow());
					claseSeleccionada = clasesSede.get(row);
					try {
						new SocioControlador().inscribirseClase(claseSeleccionada);
						LibUI.mostrarMensajeOk(SedeSelectedMenuSocio.this, "Inscripto a clase exitosamente");
						WindowManagerSingleton.getInstance().switchWindow(new SedeSelectedMenuSocio(sede));
					} catch (InscripcionNoDisponibleException e1) {
						LibUI.mostrarMensajeError(SedeSelectedMenuSocio.this, e1.getMessage());
					}
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

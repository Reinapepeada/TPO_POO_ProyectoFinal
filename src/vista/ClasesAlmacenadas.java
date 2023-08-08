package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controlador.WindowManagerSingleton;
import modelo.Articulo;
import modelo.Clase;
import modelo.EstadoClase;
import modelo.TipoEmplazamiento;

public class ClasesAlmacenadas extends JPanel {

	private JTable table;
	private JScrollPane scrollPane;

	public ClasesAlmacenadas(ArrayList<Clase> clases) {
		this.setLayout(new BorderLayout());

		var title = LibUI.crearLabelStandar("Clases en DB Streaming");
		JButton volverButton = LibUI.crearBotonStandar("Volver");

		String[] columnNames = { "Nombre", "Sede", "Estado", "Cantidad Alumnos", "Fecha Finalizacion", "Profesor" };
		Object[][] data = new Object[clases.size()][6];

		for (int i = 0; i < clases.size(); i++) {
			Clase clase = clases.get(i);

			data[i][0] = clase.getTipo().getNombre();
			data[i][1] = clase.getSede().getBarrio();
			data[i][2] = clase.getEstado();
			data[i][3] = clase.getSocios().size();
			data[i][4] = clase.getHoraFinal();
			data[i][5] = clase.getProfesor().getNombre() + " " + clase.getProfesor().getApellido();
		}

		volverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowManagerSingleton windowManager = WindowManagerSingleton.getInstance();
				windowManager.switchWindow(new ClasesMenu());
			}
		});

		table = new JTable(data, columnNames);
		scrollPane = new JScrollPane(table);

		JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		titlePanel.add(title);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(volverButton);

		this.add(titlePanel, BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}
}

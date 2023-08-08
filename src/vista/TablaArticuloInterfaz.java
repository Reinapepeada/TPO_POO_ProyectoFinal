package vista;

import java.awt.*;
import javax.swing.*;

import modelo.Articulo;

import java.util.ArrayList;

public class TablaArticuloInterfaz extends JFrame {
	private JTable table;
	private JScrollPane scrollPane;

	public TablaArticuloInterfaz(ArrayList<Articulo> articulos) {
		ArrayList<Articulo> articulosAux = new ArrayList<>();

		for (Articulo articulo : articulos) {
			boolean flag = true;
			for (Articulo articulo2 : articulosAux) {
				if (articulo.getNombre()
						.equals(articulo2.getNombre())
						&& articulo.getDescripcion().equals(articulo2.getDescripcion())) {
					flag = false;
				}
			}
			if (flag) {
				articulosAux.add(articulo);
			}

		}

		// Set the frame properties
		setTitle("Table Example");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create the table model and set the data
		String[] columnNames = { "Articulo", "Cantidad por Alumno", "Cantidad por Profesor" };
		Object[][] data = new Object[articulosAux.size()][3];

		for (int i = 0; i < articulosAux.size(); i++) {
			Articulo articulo = articulosAux.get(i);
			String nombreDescripcion = articulo.getNombre() + " " + articulo.getDescripcion();
			data[i][0] = nombreDescripcion;
			data[i][1] = "";
			data[i][2] = "";
		}

		// Create the table and scroll pane
		table = new JTable(data, columnNames);
		scrollPane = new JScrollPane(table);

		// Add the scroll pane to the frame
		add(scrollPane);
	}
}

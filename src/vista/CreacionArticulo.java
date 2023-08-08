package vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controlador.AdministrativoControlador;
import controlador.SoporteTecnicoControlador;
import controlador.WindowManagerSingleton;
import excepciones.SedeExistenteException;
import modelo.Nivel;
import modelo.Sede;
import modelo.SupertlonSingleton;
import modelo.TipoArticulo;

public class CreacionArticulo extends JPanel {

	private Sede sede;
	private ArrayList<TipoArticulo> tipoArticulos = new ArrayList<>();

	public CreacionArticulo(Sede sedeSelected) {
		this.sede = sedeSelected;
		this.tipoArticulos = SupertlonSingleton.getInstance().getTiposArticulos();

		this.setLayout(new BorderLayout());

		this.setLayout(new GridBagLayout());

		JPanel formPanel = new JPanel(new GridLayout(4, 3));
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		JLabel label1 = LibUI.crearLabelStandar("Tipo de Articulo");
		JLabel label2 = LibUI.crearLabelStandar("Detalle");
		JLabel label3 = LibUI.crearLabelStandar("Precio unitario");
		JLabel label4 = LibUI.crearLabelStandar("Cantidad");

		JComboBox<TipoArticulo> comboBox = new JComboBox<>();
		


		DefaultComboBoxModel<TipoArticulo> comboBoxModel = new DefaultComboBoxModel<>();
		for (TipoArticulo tipoArticulo : tipoArticulos) {
		    comboBoxModel.addElement(tipoArticulo);
		}
		comboBox.setModel(comboBoxModel);

		comboBox.setRenderer(new DefaultListCellRenderer() {
		    @Override
		    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
		            boolean isSelected, boolean cellHasFocus) {
		        if (value instanceof TipoArticulo) {
		            TipoArticulo tipoArticulo = (TipoArticulo) value;
		            value = tipoArticulo.getNombre();
		        }
		        return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		    }
		});

		JTextField textField1 = LibUI.crearTextfieldStandar();
		JTextField textField3 = LibUI.crearTextfieldStandar();
		JTextField textField4 = LibUI.crearTextfieldStandar();

		formPanel.add(label1);
		formPanel.add(comboBox);
		formPanel.add(label2);
		formPanel.add(textField1);
		formPanel.add(label3);
		formPanel.add(textField3);
		formPanel.add(label4);
		formPanel.add(textField4);

		JButton createButton = LibUI.crearBotonStandar("Incorporar a la sede");
		JButton volverButton = LibUI.crearBotonStandar("Volver");

		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new AdministrativoControlador().incorporarArticulos(sede, (TipoArticulo) comboBox.getSelectedItem(),
							textField1.getText(), Double.parseDouble(textField3.getText()),
							Integer.parseInt(textField4.getText()));
					LibUI.mostrarMensajeOk(CreacionArticulo.this, "Articulos incorporados con éxito");
				} catch (Exception e2) {
					LibUI.mostrarMensajeError(CreacionArticulo.this, e2.getMessage());
				}

			}
		});

		volverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowManagerSingleton windowManager = WindowManagerSingleton.getInstance();
				windowManager.switchWindow(new SedeSelectedMenu(sede));
			}
		});

		buttonPanel.add(createButton);
		buttonPanel.add(volverButton);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weighty = 0.5;
		this.add(Box.createVerticalGlue(), gbc);

		gbc.gridy = 1;
		gbc.weighty = 0;
		this.add(formPanel, gbc);

		gbc.gridy = 2;
		gbc.weighty = 0.5;
		this.add(Box.createVerticalGlue(), gbc);

		gbc.gridy = 3;
		gbc.weighty = 0;
		this.add(buttonPanel, gbc);

	}
}

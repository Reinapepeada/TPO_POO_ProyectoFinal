package vista;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LibUI {
	
	// Simula una librería de componentes, para mantener homogenica la app
	
	public static void mostrarMensajeError(JPanel parent, String msg) {
		JLabel label = new JLabel(msg);
		label.setFont(new Font("Dubai", Font.PLAIN, 16));
		label.setForeground(Color.BLACK);
		label.setForeground(Color.decode("#e5e5e5"));
		JOptionPane.showMessageDialog(parent, label, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static void mostrarMensajeOk(JPanel parent, String msg) {
		JLabel label = new JLabel(msg);
		label.setFont(new Font("Dubai", Font.PLAIN, 16));
		label.setForeground(Color.BLACK);
		label.setForeground(Color.decode("#e5e5e5"));
		JOptionPane.showMessageDialog(parent, label, "Error", JOptionPane.INFORMATION_MESSAGE);
	}

	public static JButton crearBotonStandar(String text) {
		JButton button = new JButton(text);
		button.setBackground(Color.decode("#fca311"));
		button.setForeground(Color.decode("#14213d"));
		button.setFont(new Font("Dubai", Font.BOLD, 20));
		return button;
	}

	public static JLabel crearLabelStandar(String text) {
		JLabel label = new JLabel(text);
		label.setFont(new Font("Dubai", Font.BOLD, 18));
		label.setForeground(Color.WHITE);
		return label;
	}

	public static JTextField crearTextfieldStandar() {
		JTextField textField = new JTextField(20);
		textField.setFont(new Font("Dubai", Font.BOLD, 18));
		return textField;
	}
}

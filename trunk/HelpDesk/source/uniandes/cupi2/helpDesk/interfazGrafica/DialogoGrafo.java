package uniandes.cupi2.helpDesk.interfazGrafica;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Representa la parte grafica de
 * un grafo (valga la redundancia)
 * @author imac
 *
 */
public class DialogoGrafo extends JDialog {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -5257681133158714098L;
	
	/**
	 * Imagen de fondo
	 */
	private Image imagen;
	
	/**
	 * Panel principal
	 */
	private JPanel panel;
	
	/**
	 * Constructor de la clase
	 */
	public DialogoGrafo()
	{
		panel = new JPanel() {
			imagen = new ImageIcon("../data/Grafo.png").getImage();
			{setOpaque(false);}
			public void paintComponent (Graphics g) {
				g.drawImage(imagen, 0, 0, this);
				super.paintComponent(g);
			}
		};
		add(panel);
	}
}

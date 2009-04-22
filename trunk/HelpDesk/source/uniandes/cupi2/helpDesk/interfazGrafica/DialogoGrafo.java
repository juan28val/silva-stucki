package uniandes.cupi2.helpDesk.interfazGrafica;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Representa la parte grafica de
 * un grafo (valga la redundancia)
 * @author imac
 *
 */
public class DialogoGrafo extends JDialog {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 453156331302706700L;
	
	/**
	 * Imagen de fondo
	 */
	private Image img;
	
	/**
	 * Constructor de la clase
	 */
	public DialogoGrafo()
	{
	//	super();
	//	setSize(1070, 530);
	//	setResizable(false);
	//	setLayout(new GridLayout(1,1));
	//	add(new JLabel(new ImageIcon("/Users/imac/Documents/workspace/estructuras/HelpDesk/data/actividades.png")));
	    ImagePanel panel = new ImagePanel(new ImageIcon("data/iconos/actividades.png").getImage());

	    getContentPane().add(panel);
	    pack();
	    setVisible(true);

	}
	
	/**
	 * Se encarga de pintar la imagen de fondo
	 */
	public void paintComponent(Graphics g) {
	    g.drawImage(img, 0, 0, null);
	  }
}

class ImagePanel extends JPanel {

	  private Image img;

	  public ImagePanel(String img) {
	    this(new ImageIcon(img).getImage());
	  }

	  public ImagePanel(Image img) {
	    this.img = img;
	    Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
	    setPreferredSize(size);
	    setMinimumSize(size);
	    setMaximumSize(size);
	    setSize(size);
	    setLayout(null);
	  }

	  public void paintComponent(Graphics g) {
	    g.drawImage(img, 0, 0, null);
	  }

	}

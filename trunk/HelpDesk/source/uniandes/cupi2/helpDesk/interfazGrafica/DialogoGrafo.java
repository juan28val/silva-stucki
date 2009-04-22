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
	private Image img;
	
	/**
	 * Constructor de la clase
	 */
	public DialogoGrafo()
	{

        // Create some GUI
        JPanel foregroundPanel = new JPanel(new BorderLayout(10, 10));
        foregroundPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        foregroundPanel.setOpaque(false);
        
      /* foregroundPanel.add(new JLabel("Comment:"), BorderLayout.NORTH);
        foregroundPanel.add(new JScrollPane(new JTextArea(5,30)),
                BorderLayout.CENTER);
        foregroundPanel.add(
                new JLabel(
                "Please enter your comments in text box above." +
                " HTML syntax is allowed."), BorderLayout.SOUTH);*/
        
        setContentPane(wrapInBackgroundImage(foregroundPanel,new ImageIcon("." + File.separator + "data" + File.separator + "Grafo.gif")));
        
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

	}
	
	/**
	 * Se encarga de pintar la imagen de fondo
	 */
	public void paintComponent(Graphics g) {
	    g.drawImage(img, 0, 0, null);
	  }
	
	
	// ------------------------------------------------
	
	
	// Set up contraints so that the user supplied component and the
    // background image label overlap and resize identically
    private static final GridBagConstraints gbc;
    static {
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTHWEST;
    }
    
    /**
     * Wraps a Swing JComponent in a background image. Simply invokes the overloded
     * variant with Top/Leading alignment for background image.
     *
     * @param component - to wrap in the a background image
     * @param backgroundIcon - the background image (Icon)
     * @return the wrapping JPanel
     */
    public static JPanel wrapInBackgroundImage(JComponent component,
            Icon backgroundIcon) {
        return wrapInBackgroundImage(
                component,
                backgroundIcon,
                JLabel.TOP,
                JLabel.LEADING);
    }
    
    /**
     * Wraps a Swing JComponent in a background image. The vertical and horizontal
     * alignment of background image can be specified using the alignment
     * contants from JLabel.
     *
     * @param component - to wrap in the a background image
     * @param backgroundIcon - the background image (Icon)
     * @param verticalAlignment - vertical alignment. See contants in JLabel.
     * @param horizontalAlignment - horizontal alignment. See contants in JLabel.
     * @return the wrapping JPanel
     */
    public static JPanel wrapInBackgroundImage(JComponent component,
            Icon backgroundIcon,
            int verticalAlignment,
            int horizontalAlignment) {
        
        // make the passed in swing component transparent
        component.setOpaque(false);
        
        // create wrapper JPanel
        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        
        // add the passed in swing component first to ensure that it is in front
        backgroundPanel.add(component, gbc);
        
        // create a label to paint the background image
        JLabel backgroundImage = new JLabel(backgroundIcon);
        
        // set minimum and preferred sizes so that the size of the image
        // does not affect the layout size
        backgroundImage.setPreferredSize(new Dimension(1,1));
        backgroundImage.setMinimumSize(new Dimension(1,1));
        
        // align the image as specified.
        backgroundImage.setVerticalAlignment(verticalAlignment);
        backgroundImage.setHorizontalAlignment(horizontalAlignment);
        
        // add the background label
        backgroundPanel.add(backgroundImage, gbc);
        
        // return the wrapper
        return backgroundPanel;
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

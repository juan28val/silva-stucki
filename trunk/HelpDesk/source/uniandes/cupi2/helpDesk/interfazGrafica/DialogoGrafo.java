package uniandes.cupi2.helpDesk.interfazGrafica;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uniandes.cupi2.helpDesk.interfazMundo.IActividad;
import uniandes.cupi2.helpDesk.interfazMundo.IGrafo;
import uniandes.cupi2.helpDesk.interfazMundo.IHelpDesk;

/**
 * Representa la parte grafica de
 * un grafo (valga la redundancia)
 * @author imac
 *
 */
public class DialogoGrafo extends JDialog implements Observer {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -5257681133158714098L;
	
	/**
	 * Panel principal
	 */
	private JPanel panel;
	
	/**
	 * representa la clase principal
	 */
	private IInterfaz principal;
	
	
	// --------------------------
	// atributos de actividades
	// --------------------------
	
	private JLabel nuevo1 = new JLabel("");
	private JLabel nuevo2 = new JLabel("");
	private JLabel nuevo3 = new JLabel("");

	private JLabel cifrar1 = new JLabel("");

	private JLabel cifrar2 = new JLabel("");

	private JLabel cifrar3 = new JLabel("");

	private JLabel asignar1 = new JLabel("");

	private JLabel asignar2 = new JLabel("");

	private JLabel asignar3 = new JLabel("");

	private JLabel atender1 = new JLabel("");

	private JLabel atender3 = new JLabel("");

	private JLabel atender2 = new JLabel("");

	private JLabel cerrar1 = new JLabel("");

	private JLabel cerrar2 = new JLabel("");

	private JLabel cerrar3 = new JLabel("");

	private JLabel notificar1 = new JLabel("");

	private JLabel notificar2 = new JLabel("");

	private JLabel notificar3 = new JLabel("");

	private JLabel reabrir1 = new JLabel("");

	private JLabel reabrir2 = new JLabel("");

	private JLabel reabrir3 = new JLabel("");
	
	/**
	 * Constructor de la clase
	 */
	public DialogoGrafo(IInterfaz principal, Observable o)
	{
		o.addObserver(this);
		this.principal = principal;
		setSize(960, 740);
		setResizable(false);
		
		actualizar();
	}
	public void actualizar()
	{
		panel = new JPanel() {
			private static final long serialVersionUID = 1L;
			Image imagen = new ImageIcon("../data/Grafo.png").getImage();
			{setOpaque(false);}
			public void paintComponent (Graphics g) {
				g.drawImage(imagen, 0, 0, this);
				super.paintComponent(g);
			}
		};
		IGrafo grafo = principal.darDigiturno();
		IActividad act = grafo.darActividad(IHelpDesk.ACTIVIDAD_NUEVA_SOLICITUD);
		if(act != null) {
			nuevo1 = new JLabel("" + act.darNumeroVecesEjecutada());
			nuevo2 = new JLabel("" + act.darPromedioTiempo());
			nuevo3 = new JLabel("" + act.darTiempoPromedioEspera());
		}
		act = grafo.darActividad(IHelpDesk.ACTIVIDAD_CIFRAR);
		if(act != null) {
			cifrar1 = new JLabel("" + act.darNumeroVecesEjecutada());
			cifrar2 = new JLabel("" + act.darPromedioTiempo());
			cifrar3 = new JLabel("" + act.darTiempoPromedioEspera());
		}
		act = grafo.darActividad(IHelpDesk.ACTIVIDAD_ASIGNAR_TICKET);
		if(act != null) {
			asignar1 = new JLabel("" + act.darNumeroVecesEjecutada());
			asignar2 = new JLabel("" + act.darPromedioTiempo());
			asignar3 = new JLabel("" + act.darTiempoPromedioEspera());
		}
		act = grafo.darActividad(IHelpDesk.ACTIVIDAD_ATENDER);
		if(act != null) {
			atender1 = new JLabel("" + act.darNumeroVecesEjecutada());
			atender2 = new JLabel("" + act.darPromedioTiempo());
			atender3 = new JLabel("" + act.darTiempoPromedioEspera());
		}
		act = grafo.darActividad(IHelpDesk.ACTIVIDAD_CERRAR);
		if(act != null) {
			cerrar1 = new JLabel("" + act.darNumeroVecesEjecutada());
			cerrar2 = new JLabel("" + act.darPromedioTiempo());
			cerrar3 = new JLabel("" + act.darTiempoPromedioEspera());
		}
		act = grafo.darActividad(IHelpDesk.ACTIVIDAD_NOTFICAR);
		if(act != null) {
			notificar1 = new JLabel("" + act.darNumeroVecesEjecutada());
			notificar2 = new JLabel("" + act.darPromedioTiempo());
			notificar3 = new JLabel("" + act.darTiempoPromedioEspera());
		}
		act = grafo.darActividad(IHelpDesk.ACTIVIDAD_REABRIR);
		if(act != null) {
			reabrir1 = new JLabel("" + act.darNumeroVecesEjecutada());
			reabrir2 = new JLabel("" + act.darPromedioTiempo());
			reabrir3 = new JLabel("" + act.darTiempoPromedioEspera());
		}
		
		
		panel.setLayout(new GridBagLayout());
		GridBagConstraints coord = new GridBagConstraints();
		coord.gridx = 0;
		coord.gridy = 0;
		coord.gridwidth = 1;
		coord.gridheight=7;
		coord.weightx = 0.5;
		coord.weighty = 1.0;
		coord.anchor = GridBagConstraints.CENTER;
		panel.add(new JLabel(""), coord);
		coord.gridx = 1;
		coord.gridy = 0;
		coord.gridwidth = 1;
		coord.gridheight=1;
		coord.weightx = 0.6;
		coord.weighty = 0.1;
		coord.anchor = GridBagConstraints.SOUTHWEST;
		panel.add(nuevo1, coord);
		coord.gridx = 1;
		coord.gridy = 1;
		coord.gridwidth = 1;
		coord.gridheight=1;
		coord.weightx = 0.6;
		coord.weighty = 0.1;
		coord.anchor = GridBagConstraints.WEST;
		panel.add(nuevo2, coord);
		coord.gridx = 1;
		coord.gridy = 2;
		coord.gridwidth = 1;
		coord.gridheight=1;
		coord.weightx = 0.6;
		coord.weighty = 0.1;
		coord.anchor = GridBagConstraints.NORTHWEST;
		panel.add(nuevo3, coord);
		coord.gridx = 1;
		coord.gridy = 3;
		coord.gridwidth = 1;
		coord.gridheight=1;
		coord.weightx = 0.6;
		coord.weighty = 0.1;
		coord.anchor = GridBagConstraints.SOUTHWEST;
		panel.add(asignar1, coord);
		coord.gridx = 1;
		coord.gridy = 4;
		coord.gridwidth = 1;
		coord.gridheight=1;
		coord.weightx = 0.6;
		coord.weighty = 0.1;
		coord.anchor = GridBagConstraints.WEST;
		panel.add(asignar2, coord);
		coord.gridx = 1;
		coord.gridy = 5;
		coord.gridwidth = 1;
		coord.gridheight=1;
		coord.weightx = 0.6;
		coord.weighty = 0.1;
		coord.anchor = GridBagConstraints.NORTHWEST;
		panel.add(asignar3, coord);
		coord.gridx = 1;
		coord.gridy = 6;
		coord.gridwidth = 1;
		coord.gridheight=1;
		coord.weightx = 0.6;
		coord.weighty = 0.1;
		coord.anchor = GridBagConstraints.WEST;
		panel.add(new JLabel(""), coord);
		coord.gridx = 2;
		coord.gridy = 0;
		coord.gridwidth = 1;
		coord.gridheight=1;
		coord.weightx = 0.55;
		coord.weighty = 1.05;
		coord.anchor = GridBagConstraints.SOUTHWEST;
		panel.add(reabrir1, coord);
		coord.gridx = 2;
		coord.gridy = 1;
		coord.gridwidth = 1;
		coord.gridheight=1;
		coord.weightx = 0.5;
		coord.weighty = 0.1;
		coord.anchor = GridBagConstraints.WEST;
		panel.add(reabrir2, coord);
		coord.gridx = 2;
		coord.gridy = 2;
		coord.gridwidth = 1;
		coord.gridheight=1;
		coord.weightx = 0.5;
		coord.weighty = 0.35;
		coord.anchor = GridBagConstraints.NORTHWEST;
		panel.add(reabrir3, coord);
		coord.gridx = 2;
		coord.gridy = 3;
		coord.gridwidth = 1;
		coord.gridheight=1;
		coord.weightx = 0.5;
		coord.weighty = 0.35;
		coord.anchor = GridBagConstraints.SOUTHWEST;
		panel.add(cifrar1, coord);
		coord.gridx = 2;
		coord.gridy = 4;
		coord.gridwidth = 1;
		coord.gridheight=1;
		coord.weightx = 0.5;
		coord.weighty = 0.1;
		coord.anchor = GridBagConstraints.WEST;
		panel.add(cifrar2, coord);
		coord.gridx = 2;
		coord.gridy = 5;
		coord.gridwidth = 1;
		coord.gridheight=1;
		coord.weightx = 0.5;
		coord.weighty = 0.4;
		coord.anchor = GridBagConstraints.NORTHWEST;
		panel.add(cifrar3, coord);
		coord.gridx = 2;
		coord.gridy = 6;
		coord.gridwidth = 1;
		coord.gridheight=1;
		coord.weightx = 0.5;
		coord.weighty = 0.4;
		coord.anchor = GridBagConstraints.SOUTHWEST;
		panel.add(atender1, coord);
		coord.gridx = 2;
		coord.gridy = 7;
		coord.gridwidth = 1;
		coord.gridheight=1;
		coord.weightx = 0.5;
		coord.weighty = 0.1;
		coord.anchor = GridBagConstraints.WEST;
		panel.add(atender2, coord);
		coord.gridx = 2;
		coord.gridy = 8;
		coord.gridwidth = 1;
		coord.gridheight=1;
		coord.weightx = 0.5;
		coord.weighty = 1.5;
		coord.anchor = GridBagConstraints.NORTHWEST;
		panel.add(atender3, coord);
		coord.gridx = 3;
		coord.gridy = 0;
		coord.gridwidth = 1;
		coord.gridheight=1;
		coord.weightx = 0.3;
		coord.weighty = 1.0;
		coord.anchor = GridBagConstraints.SOUTHWEST;
		panel.add(new JLabel(""), coord);
		coord.gridx = 3;
		coord.gridy = 3;
		coord.gridwidth = 1;
		coord.gridheight=1;
		coord.weightx = 0.3;
		coord.weighty = 0.35;
		coord.anchor = GridBagConstraints.SOUTHWEST;
		panel.add(cerrar1, coord);
		coord.gridx = 3;
		coord.gridy = 4;
		coord.gridwidth = 1;
		coord.gridheight=1;
		coord.weightx = 0.3;
		coord.weighty = 0.1;
		coord.anchor = GridBagConstraints.WEST;
		panel.add(cerrar2, coord);
		coord.gridx = 3;
		coord.gridy = 5;
		coord.gridwidth = 1;
		coord.gridheight=1;
		coord.weightx = 0.3;
		coord.weighty = 0.4;
		coord.anchor = GridBagConstraints.NORTHWEST;
		panel.add(cerrar3, coord);
		coord.gridx = 3;
		coord.gridy = 6;
		coord.gridwidth = 1;
		coord.gridheight=1;
		coord.weightx = 0.3;
		coord.weighty = 0.4;
		coord.anchor = GridBagConstraints.SOUTHWEST;
		panel.add(notificar1, coord);
		coord.gridx = 3;
		coord.gridy = 7;
		coord.gridwidth = 1;
		coord.gridheight=1;
		coord.weightx = 0.3;
		coord.weighty = 0.1;
		coord.anchor = GridBagConstraints.WEST;
		panel.add(notificar2, coord);
		coord.gridx = 3;
		coord.gridy = 8;
		coord.gridwidth = 1;
		coord.gridheight=1;
		coord.weightx = 0.3;
		coord.weighty = 1.5;
		coord.anchor = GridBagConstraints.NORTHWEST;
		panel.add(notificar3, coord);
		add(panel);
		validate();
		repaint();
	}

	public void update(Observable o, Object nombre) {
		removeAll();
		actualizar();
	}
}

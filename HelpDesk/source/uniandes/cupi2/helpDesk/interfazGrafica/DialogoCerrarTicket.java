package uniandes.cupi2.helpDesk.interfazGrafica;



import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;



import javax.swing.JDialog;

import java.awt.GridBagLayout;




import javax.swing.JTextArea;


import java.awt.GridBagConstraints;

import javax.swing.JButton;



import uniandes.cupi2.helpDesk.interfazMundo.ITicket;



public class DialogoCerrarTicket extends JDialog implements ActionListener {



	private static final long serialVersionUID = 1L;



	private JPanel jContentPane = null;



	private JPanel panelBotones = null;



	private JTextArea areaComentario = null;




	private JButton botonAceptar = null;



	private JButton botonCancelar = null;



	private InterfazHelpDesk principal;



	private PanelEmpleado padre;



	private ITicket ticket;



	private JScrollPane barra;



	public void actionPerformed(ActionEvent evento) {

		if(evento.getActionCommand().equals("Aceptar"))

			principal.cerrarTicket(ticket, areaComentario.getText());

		principal.darJFrame().setEnabled(true);

		padre.actualizar();

		dispose();

	}



	/**

	 * @param owner

	 */

	public DialogoCerrarTicket(InterfazHelpDesk owner, PanelEmpleado panel, ITicket ticket) {

		super(owner.darJFrame());

		principal = owner;

		padre = panel;

		this.setLocationRelativeTo(panel);
		this.ticket = ticket;
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);

		initialize();

	}





	/**

	 * This method initializes this

	 * 

	 * @return void

	 */

	private void initialize() {

		this.setSize(300, 200);

		this.setTitle("Cerrar ticket");

		this.setContentPane(getJContentPane());

	}

	



	/**

	 * This method initializes jContentPane

	 * 

	 * @return javax.swing.JPanel

	 */

	private JPanel getJContentPane() {

		if (jContentPane == null) {

			jContentPane = new JPanel();

			jContentPane.setLayout(new BorderLayout());
			jContentPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

			jContentPane.add(getPanelBotones(), BorderLayout.SOUTH);
			barra = new JScrollPane(getAreaComentario());
			jContentPane.add(barra, BorderLayout.CENTER);


		}

		return jContentPane;

	}



	/**

	 * This method initializes panelBotones	

	 * 	

	 * @return javax.swing.JPanel	

	 */

	private JPanel getPanelBotones() {

		if (panelBotones == null) {

			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();

			gridBagConstraints3.gridy = 1;

			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();

			gridBagConstraints2.gridx = 1;

			gridBagConstraints2.gridy = 1;

			panelBotones = new JPanel();

			panelBotones.setLayout(new GridBagLayout());

			panelBotones.add(getBotonAceptar(), gridBagConstraints3);

			panelBotones.add(getBotonCancelar(), gridBagConstraints2);

		}

		return panelBotones;

	}



	/**

	 * This method initializes areaComentario	

	 * 	

	 * @return javax.swing.JTextArea	

	 */

	private JTextArea getAreaComentario() {

		if (areaComentario == null) {

			areaComentario = new JTextArea();
			areaComentario.setLineWrap(true);

		}

		return areaComentario;

	}





	/**

	 * This method initializes botonAceptar	

	 * 	

	 * @return javax.swing.JButton	

	 */

	private JButton getBotonAceptar() {

		if (botonAceptar == null) {

			botonAceptar = new JButton();

			botonAceptar.setText("Aceptar");

			botonAceptar.addActionListener(this);

			botonAceptar.setActionCommand("Aceptar");

		}

		return botonAceptar;

	}



	/**

	 * This method initializes botonCancelar	

	 * 	

	 * @return javax.swing.JButton	

	 */

	private JButton getBotonCancelar() {

		if (botonCancelar == null) {

			botonCancelar = new JButton();

			botonCancelar.setText("Cancelar");

			botonCancelar.addActionListener(this);

		}

		return botonCancelar;

	}



}


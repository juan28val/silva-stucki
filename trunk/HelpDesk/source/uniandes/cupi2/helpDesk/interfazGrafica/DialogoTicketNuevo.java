package uniandes.cupi2.helpDesk.interfazGrafica;



import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;



import javax.swing.JDialog;

import java.awt.GridBagLayout;



import javax.swing.ButtonGroup;

import javax.swing.JTextArea;

import javax.swing.JRadioButton;

import java.awt.GridBagConstraints;

import javax.swing.JButton;



import uniandes.cupi2.helpDesk.interfazMundo.ITicket;



public class DialogoTicketNuevo extends JDialog implements ActionListener {



	private static final long serialVersionUID = 1L;



	private JPanel jContentPane = null;



	private JPanel panelTipo = null;



	private JPanel panelBotones = null;



	private JTextArea areaComentario = null;



	private JRadioButton radioQueja = null;



	private JRadioButton radioReclamo = null;



	private JRadioButton radioSolicitud = null;

	

	private ButtonGroup grupo = null; 



	private JButton botonAceptar = null;



	private JButton botonCancelar = null;



	private IInterfaz principal;



	private PanelCliente padre;



	private JScrollPane barra;



	private JCheckBox cifrar;



	public void actionPerformed(ActionEvent evento) {

		if(evento.getActionCommand().equals("Aceptar"))

			principal.nuevaSolicitud( radioQueja.isSelected()?ITicket.TIPO_QUEJA:(radioReclamo.isSelected()?ITicket.TIPO_RECLAMO:ITicket.TIPO_SOLICITUD), areaComentario.getText(), cifrar.isSelected());

		principal.darJFrame().setEnabled(true);

		padre.actualizar();

		dispose();

	}



	/**

	 * @param owner

	 */

	public DialogoTicketNuevo(IInterfaz owner, PanelCliente panel) {

		super(owner.darJFrame());

		principal = owner;

		padre = panel;

		this.setLocationRelativeTo(panel);
		
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

		this.setTitle("Generar un nuevo ticket");

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

			jContentPane.add(getPanelTipo(), BorderLayout.NORTH);

			jContentPane.add(getPanelBotones(), BorderLayout.SOUTH);
			barra = new JScrollPane(getAreaComentario());
			jContentPane.add(barra, BorderLayout.CENTER);

		}

		return jContentPane;

	}



	/**

	 * This method initializes panelTipo	

	 * 	

	 * @return javax.swing.JPanel	

	 */

	private JPanel getPanelTipo() {

		if (panelTipo == null) {

			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();

			gridBagConstraints1.gridx = 2;

			gridBagConstraints1.gridy = 0;

			GridBagConstraints gridBagConstraints = new GridBagConstraints();

			gridBagConstraints.gridx = 1;

			gridBagConstraints.gridy = 0;

			panelTipo = new JPanel();

			grupo = new ButtonGroup();

			panelTipo.setLayout(new GridBagLayout());

			panelTipo.add(getRadioQueja(), new GridBagConstraints());

			panelTipo.add(getRadioReclamo(), gridBagConstraints);

			panelTipo.add(getRadioSolicitud(), gridBagConstraints1);

		}

		return panelTipo;

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
			
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			
			gridBagConstraints1.gridy = 1;
			
			gridBagConstraints1.gridx = 2;

			panelBotones = new JPanel();

			panelBotones.setLayout(new GridBagLayout());
			
			panelBotones.add(getCifrar(), gridBagConstraints3);

			panelBotones.add(getBotonAceptar(), gridBagConstraints2);

			panelBotones.add(getBotonCancelar(), gridBagConstraints1);

		}

		return panelBotones;

	}



	private JCheckBox getCifrar() {
		if(cifrar == null)
		{
			cifrar = new JCheckBox("Cifrar");
		}
		return cifrar;
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
			areaComentario.setWrapStyleWord(true);
		}

		return areaComentario;

	}



	/**

	 * This method initializes radioQueja	

	 * 	

	 * @return javax.swing.JRadioButton	

	 */

	private JRadioButton getRadioQueja() {

		if (radioQueja == null) {

			radioQueja = new JRadioButton();

			radioQueja.setText("Queja");

			radioQueja.setSelected(true);

			grupo.add(radioQueja);

		}

		return radioQueja;

	}



	/**

	 * This method initializes radioReclamo	

	 * 	

	 * @return javax.swing.JRadioButton	

	 */

	private JRadioButton getRadioReclamo() {

		if (radioReclamo == null) {

			radioReclamo = new JRadioButton();

			radioReclamo.setText("Reclamo");

			grupo.add(radioReclamo);

		}

		return radioReclamo;

	}



	/**

	 * This method initializes radioSolicitud	

	 * 	

	 * @return javax.swing.JRadioButton	

	 */

	private JRadioButton getRadioSolicitud() {

		if (radioSolicitud == null) {

			radioSolicitud = new JRadioButton();

			radioSolicitud.setText("Solicitud");

			grupo.add(radioSolicitud);

		}

		return radioSolicitud;

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


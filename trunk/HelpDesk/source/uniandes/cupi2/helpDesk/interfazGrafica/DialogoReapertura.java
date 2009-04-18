package uniandes.cupi2.helpDesk.interfazGrafica;



import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;



import javax.swing.JDialog;

import java.awt.GridBagLayout;



import javax.swing.JTextArea;


import java.awt.GridBagConstraints;
import java.util.ArrayList;

import javax.swing.JButton;



import uniandes.cupi2.helpDesk.interfazMundo.IIterador;
import uniandes.cupi2.helpDesk.interfazMundo.ITicket;
import uniandes.cupi2.helpDesk.interfazMundo.IUsuario;



public class DialogoReapertura extends JDialog implements ActionListener {



	private static final long serialVersionUID = 1L;



	private JPanel jContentPane = null;



	private JPanel panelTipo = null;



	private JPanel panelBotones = null;



	private JTextArea areaComentario = null;



	private JComboBox comboEmpleados = null;


	private JButton botonAceptar = null;



	private JButton botonCancelar = null;



	private IInterfaz principal;



	private PanelCliente padre;



	private JScrollPane barra;



	private ITicket ticketActual;



	private ArrayList<IUsuario> listaEmpleados;



	public void actionPerformed(ActionEvent evento) {

		if(evento.getActionCommand().equals("Aceptar"))

			principal.reapertura(ticketActual, areaComentario.getText(), listaEmpleados.get(comboEmpleados.getSelectedIndex()));

		principal.darJFrame().setEnabled(true);

		padre.actualizar();

		dispose();

	}



	/**

	 * @param owner

	 */

	public DialogoReapertura(IInterfaz owner,PanelCliente panel,ITicket ticket) {

		super(owner.darJFrame());

		listaEmpleados = new ArrayList<IUsuario>();
		
		principal = owner;

		padre = panel;
		
		ticketActual =ticket;

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

		this.setTitle("Reabrir un ticket");

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

			jContentPane.add(getPanelEmpleado(), BorderLayout.NORTH);

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

	private JPanel getPanelEmpleado() {

		if (panelTipo == null) {

			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();

			gridBagConstraints1.gridx = 2;

			gridBagConstraints1.gridy = 0;

			GridBagConstraints gridBagConstraints = new GridBagConstraints();

			gridBagConstraints.gridx = 1;

			gridBagConstraints.gridy = 0;

			panelTipo = new JPanel();

			panelTipo.setLayout(new GridBagLayout());

			panelTipo.add(getComboEmpleados(), gridBagConstraints1);
			
			panelTipo.add(new JLabel("Asignar: "), gridBagConstraints);

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
			areaComentario.setWrapStyleWord(true);
		}

		return areaComentario;

	}



	/**

	 * This method initializes radioQueja	

	 * 	

	 * @return javax.swing.JRadioButton	

	 */

	private JComboBox getComboEmpleados() {

		if (comboEmpleados == null) {

			comboEmpleados = new JComboBox();
			
			IIterador iterador = principal.darEmpleados();
			
			while(iterador.haySiguiente())
			{
				IUsuario empleado = (IUsuario) iterador.darSiguiente();
				listaEmpleados.add(empleado);
				comboEmpleados.addItem(empleado.darNombre() + " - " + (empleado.darTipo() == IUsuario.EMPLEADO_QUEJA ? "Queja": empleado.darTipo() == IUsuario.EMPLEADO_RECLAMO ?"Reclamo":"Solicitud"));
			}
			comboEmpleados.setSelectedItem(ticketActual.darEmpleado());
		

		}

		return comboEmpleados;

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


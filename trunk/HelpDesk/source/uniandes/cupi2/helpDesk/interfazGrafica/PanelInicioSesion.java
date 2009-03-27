package uniandes.cupi2.helpDesk.interfazGrafica;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.BorderFactory;

import uniandes.cupi2.helpDesk.interfazMundo.IUsuario;

import java.awt.Color;

public class PanelInicioSesion extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JLabel etiquetaTitulo = null;
	private JLabel etiquetaNombre = null;
	private JLabel etiquetaVacia1 = null;
	private JLabel etiquetaVacia2 = null;
	private JLabel etiquetaVacia3 = null;
	private JLabel etiquetaVacia4 = null;
	private JLabel etiquetaVacia5 = null;
	private JLabel etiquetaTipo = null;
	private JButton botonIniciarSesion = null;
	private JLabel etiquetaTitulo2 = null;
	private JTextField textoNombre = null;
	private JRadioButton radioCliente = null;
	private JRadioButton radioEmpleado = null;
	private JRadioButton radioAdministrador = null;
	private ButtonGroup grupoBotones = null;  //  @jve:decl-index=0:
	private InterfazHelpDesk principal = null;

	/**
	 * This is the default constructor
	 */
	public PanelInicioSesion( InterfazHelpDesk iHD ) {
		super();
		initialize();
		principal  = iHD;
	}


	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		
		etiquetaVacia1 = new JLabel("");
		etiquetaVacia2 = new JLabel("");
		etiquetaVacia3 = new JLabel("");
		etiquetaVacia4 = new JLabel("");
		etiquetaVacia5 = new JLabel("");
		
		etiquetaTitulo = new JLabel("Iniciar ");
		etiquetaTitulo.setHorizontalAlignment(SwingConstants.RIGHT);
		etiquetaTitulo.setHorizontalTextPosition(SwingConstants.RIGHT);
		etiquetaTitulo2 = new JLabel();
		etiquetaTitulo2.setText(" Sesión");
		etiquetaTipo = new JLabel();
		etiquetaTipo.setText("Tipo:");
		etiquetaTipo.setHorizontalTextPosition(SwingConstants.RIGHT);
		etiquetaTipo.setHorizontalAlignment(SwingConstants.RIGHT);
		etiquetaNombre = new JLabel();
		etiquetaNombre.setText("Nombre:");
		etiquetaNombre.setHorizontalTextPosition(SwingConstants.RIGHT);
		etiquetaNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		
		grupoBotones = new ButtonGroup();
		
		grupoBotones.add(getRadioCliente());
		grupoBotones.add(getRadioEmpleado());
		grupoBotones.add(getRadioAdministrador());
		GridLayout gridLayout = new GridLayout();
		gridLayout.setRows(7);
		gridLayout.setColumns(2);
		this.setLayout(gridLayout);
		this.setSize(300, 200);
		this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		
		this.add(etiquetaTitulo, null);
		this.add(etiquetaTitulo2, null);
		
		this.add(etiquetaVacia1, null);
		this.add(etiquetaVacia2, null);
		
		this.add(etiquetaNombre, null);
		this.add(getTextoNombre(), null);

		this.add(etiquetaVacia3, null);
		this.add(getRadioCliente(), null);
	
		this.add(etiquetaTipo, null);
		this.add(getRadioEmpleado(), null);
		
		this.add(etiquetaVacia4, null);
		this.add(getRadioAdministrador(), null);
		
		this.add(etiquetaVacia5, null);
		this.add(getBotonIniciarSesion(), null);
	}

	/**
	 * This method initializes botonIniciarSesion	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBotonIniciarSesion() {
		if (botonIniciarSesion == null) {
			botonIniciarSesion = new JButton();
			botonIniciarSesion.addActionListener(this);
			botonIniciarSesion.setText("Iniciar Sesión");
		}
		return botonIniciarSesion;
	}

	/**
	 * This method initializes textoNombre	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTextoNombre() {
		if (textoNombre == null) {
			textoNombre = new JTextField();
			textoNombre.addActionListener(this);
		}
		return textoNombre;
	}

	/**
	 * This method initializes radioCliente	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRadioCliente() {
		if (radioCliente == null) {
			radioCliente = new JRadioButton();
			radioCliente.setText("Cliente");
			radioCliente.setActionCommand( String.valueOf(IUsuario.CLIENTE_ESTUDIANTE) );
			radioCliente.setSelected(true);
		}
		return radioCliente;
	}

	/**
	 * This method initializes radioEmpleado	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRadioEmpleado() {
		if (radioEmpleado == null) {
			radioEmpleado = new JRadioButton();
			radioEmpleado.setActionCommand( String.valueOf(IUsuario.EMPLEADO_QUEJA) );
			radioEmpleado.setText("Empleado");
		}
		return radioEmpleado;
	}

	/**
	 * This method initializes radioAdministrador	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRadioAdministrador() {
		if (radioAdministrador == null) {
			radioAdministrador = new JRadioButton();
			radioAdministrador.setActionCommand( String.valueOf(IUsuario.ADMINISTRADOR) );
			radioAdministrador.setText("Administrador");
		}
		return radioAdministrador;
	}
	
	public void actionPerformed(ActionEvent e) {
		IUsuario temp = principal.darUsuario( textoNombre.getText(), grupoBotones.getSelection().getActionCommand() );

		if( radioAdministrador.isSelected() )
		{
			principal.iniciarSesionAdministrador( textoNombre.getText() );
		}
		else if(temp!=null)
		{
			principal.iniciarSesion(temp);
		}
		else if( radioCliente.isSelected() )
		{
			principal.crearCliente( textoNombre.getText() );
		}
		else
		{
			JOptionPane.showMessageDialog(principal.darJFrame(), "Este empleado no existe.");
		}
	}

}  //  @jve:decl-index=0:visual-constraint="100,28"

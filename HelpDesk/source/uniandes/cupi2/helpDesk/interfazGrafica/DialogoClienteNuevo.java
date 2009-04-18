package uniandes.cupi2.helpDesk.interfazGrafica;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.GridLayout;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;

public class DialogoClienteNuevo extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JLabel etiquetaTitulo = null;

	private JPanel panelDatos = null;

	private JLabel etiquetaNombre = null;

	private JTextField textoNombre = null;

	private JLabel etiquetaEmail = null;

	private JTextField textoEmail = null;

	private JLabel etiquetaTipo = null;

	private JComboBox listaTipo = null;

	private JButton botonAceptar = null;

	private JButton botonCancelar = null;

	private JLabel etiquetaVacia1 = null;

	private JLabel etiquetaVacia2 = null;

	private String nombre;
	
	private IInterfaz principal;

	public void actionPerformed(ActionEvent evento) {
		if(evento.getActionCommand().equals("Aceptar"))
			principal.iniciarSesion(principal.nuevoCliente(nombre, textoEmail.getText(), (String)listaTipo.getSelectedItem()));
		principal.darJFrame().setEnabled(true);
		dispose();
	}

	/**
	 * @param owner
	 */
	public DialogoClienteNuevo(IInterfaz owner, String nombre) {
		super(owner.darJFrame());
		this.nombre = nombre;
		principal = owner;
		initialize();
		setResizable(false);
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(350, 200);
		this.setTitle("Crear un nuevo usuario");
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			etiquetaTitulo = new JLabel();
			etiquetaTitulo.setText("Bienvenido");
			etiquetaTitulo.setHorizontalTextPosition(SwingConstants.CENTER);
			etiquetaTitulo.setHorizontalAlignment(SwingConstants.CENTER);
			etiquetaTitulo.setFont(new Font("Dialog", Font.BOLD, 18));
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(etiquetaTitulo, BorderLayout.NORTH);
			jContentPane.add(getPanelDatos(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes panelDatos	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelDatos() {
		if (panelDatos == null) {
			etiquetaVacia2 = new JLabel();
			etiquetaVacia2.setText("");
			etiquetaVacia1 = new JLabel();
			etiquetaVacia1.setText("");
			etiquetaTipo = new JLabel();
			etiquetaTipo.setText("Tipo de usuario:   ");
			etiquetaTipo.setHorizontalTextPosition(SwingConstants.RIGHT);
			etiquetaTipo.setHorizontalAlignment(SwingConstants.RIGHT);
			etiquetaEmail = new JLabel();
			etiquetaEmail.setText("Cuenta de correo:   ");
			etiquetaEmail.setHorizontalTextPosition(SwingConstants.RIGHT);
			etiquetaEmail.setHorizontalAlignment(SwingConstants.RIGHT);
			etiquetaNombre = new JLabel();
			etiquetaNombre.setText("Nombre:   ");
			etiquetaNombre.setHorizontalAlignment(SwingConstants.RIGHT);
			etiquetaNombre.setHorizontalTextPosition(SwingConstants.RIGHT);
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(5);
			gridLayout.setColumns(2);
			panelDatos = new JPanel();
			panelDatos.setLayout(gridLayout);
			panelDatos.add(etiquetaNombre, null);
			panelDatos.add(getTextoNombre(), null);
			panelDatos.add(etiquetaEmail, null);
			panelDatos.add(getTextoEmail(), null);
			panelDatos.add(etiquetaTipo, null);
			panelDatos.add(getListaTipo(), null);
			panelDatos.add(etiquetaVacia1, null);
			panelDatos.add(etiquetaVacia2, null);
			panelDatos.add(getBotonCancelar(), null);
			panelDatos.add(getBotonAceptar(), null);
			this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		}
		return panelDatos;
	}


	/**
	 * This method initializes textoNombre	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTextoNombre() {
		if (textoNombre == null) {
			textoNombre = new JTextField();
			textoNombre.setEditable(false);
			textoNombre.setText(nombre);
			textoNombre.addActionListener(this);
			textoNombre.setActionCommand("Aceptar");
		}
		return textoNombre;
	}

	/**
	 * This method initializes textoEmail	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTextoEmail() {
		if (textoEmail == null) {
			textoEmail = new JTextField();
			textoEmail.addActionListener(this);
			textoEmail.setActionCommand("Aceptar");
		}
		return textoEmail;
	}

	/**
	 * This method initializes listaTipo	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getListaTipo() {
		if (listaTipo == null) {
			listaTipo = new JComboBox();
			listaTipo.addItem("Estudiante");
			listaTipo.addItem("Docente");
			listaTipo.addItem("Personal administrativo");
		}
		return listaTipo;
	}

	/**
	 * This method initializes botonAceptar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBotonAceptar() {
		if (botonAceptar == null) {
			botonAceptar = new JButton();
			botonAceptar.setText("Iniciar Sesion");
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
			botonCancelar.setActionCommand("Cancelar");
		}
		return botonCancelar;
	}
	
}

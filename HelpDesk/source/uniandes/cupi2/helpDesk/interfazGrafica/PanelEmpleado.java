package uniandes.cupi2.helpDesk.interfazGrafica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import uniandes.cupi2.helpDesk.interfazMundo.*;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;

public class PanelEmpleado extends JPanel implements ActionListener, TreeSelectionListener {

	private static final long serialVersionUID = 1L;
	private JPanel panelIzquierda = null;
	private JPanel panelDerecha = null;
	private JScrollPane listaTickets = null;
	private JTextArea areaDescripcion = null;
	private JPanel panelOpciones = null;
	private JButton botonAtender = null;
	private JPanel panelPropiedades = null;
	private JButton botonCerrar = null;
	private JButton botonOpcion1 = null;
	private JButton botonOpcion2 = null;
	private JLabel etiquetaVacia1 = null;
	private JLabel etiquetaVacia2 = null;
	private JPanel panelNuevo = null;
	private IInterfaz principal;
	private IIterador iterador;
	private ITicket ticketActual;
	private JScrollPane barra;
	private JButton botonDescifrar;
	private JTree jArbol;
	
	public void actionPerformed(ActionEvent evento) {
		if(evento.getActionCommand().equals("atras"))
		{
			iterador.darGrupoAnterior();
			actualizar();
		}
		if(evento.getActionCommand().equals("adelante"))
		{
			iterador.darGrupoSiguiente();
			actualizar();
		}
		
		if(evento.getActionCommand().equals("descifrar"))
		{
			JOptionPane.showMessageDialog(principal.darJFrame(), principal.descifrar(ticketActual), "Comentario descifrado", JOptionPane.INFORMATION_MESSAGE);
		}
			
		if(evento.getActionCommand().equals("atender"))
		{
			principal.atender(ticketActual);
			actualizar();
			actualizarDescripcion();
		}
		if(evento.getActionCommand().equals("cerrar"))
		{
			DialogoCerrarTicket cerrar = new DialogoCerrarTicket(principal, this, ticketActual);
			cerrar.setVisible(true);
			principal.darJFrame().setEnabled(false);
			actualizar();
			actualizarDescripcion();
		}
		if(evento.getActionCommand().equals("opcion1"))
			principal.opcion1();
		if(evento.getActionCommand().equals("opcion2"))
			principal.opcion2();
	}

	public void actualizar() {
		
		DefaultMutableTreeNode raizTickets = new DefaultMutableTreeNode("Empleados");
		iterador = principal.darListaTickets();
		while(iterador.hayGrupoSiguiente())
		{
			iterador.darGrupoSiguiente();
			if(iterador.haySiguiente())
			{
				ITicket ticket = (ITicket) iterador.darSiguiente();
				DefaultMutableTreeNode nuevoCliente = new DefaultMutableTreeNode(ticket.darCliente());
				nuevoCliente.add(new DefaultMutableTreeNode(ticket));
				while(iterador.haySiguiente())
				{
					nuevoCliente.add(new DefaultMutableTreeNode(iterador.darSiguiente()));
				}
				raizTickets.add(nuevoCliente);
			}			
		}
		panelIzquierda.remove(listaTickets);
		jArbol = new JTree(raizTickets);
		ImageIcon leafIcon = new ImageIcon("./data/iconos/hoja.gif");
		ImageIcon empleadoIcon = new ImageIcon("./data/iconos/empleado.gif");
		if(leafIcon!=null)
		{
			DefaultTreeCellRenderer render = new DefaultTreeCellRenderer();
			render.setLeafIcon(leafIcon);
			render.setClosedIcon(empleadoIcon);
			render.setOpenIcon(empleadoIcon);
			jArbol.setCellRenderer(render);
		}
		jArbol.addTreeSelectionListener(this);
		jArbol.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		for(int i=raizTickets.getChildCount();i>0;i--)
			jArbol.expandRow(i);
		listaTickets = new JScrollPane(jArbol);
		listaTickets.setPreferredSize(new Dimension(80,300));
		panelIzquierda.add(listaTickets, BorderLayout.CENTER);
		areaDescripcion.setText("");
		panelIzquierda.validate();
		botonCerrar.setEnabled(false);
		botonAtender.setEnabled(false);
	}

	/**
	 * This is the default constructor
	 */
	public PanelEmpleado(IInterfaz principal, IIterador iterador) {
		super();
		this.principal = principal;
		this.iterador = iterador;
		initialize();
		ticketActual = null;
		actualizar();
		
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridLayout gridLayout = new GridLayout();
		gridLayout.setRows(1);
		gridLayout.setColumns(2);
		this.setLayout(gridLayout);
		this.setSize(530,530);
		this.add(getPanelIzquierda(), null);
		this.add(getPanelDerecha(), null);
	}

	/**
	 * This method initializes panelIzquierda	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelIzquierda() {
		if (panelIzquierda == null) {
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setVgap(10);
			borderLayout.setHgap(0);
			panelIzquierda = new JPanel();
			panelIzquierda.setLayout(borderLayout);
			panelIzquierda.setBorder(BorderFactory.createTitledBorder(null, "Lista de tickets", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			panelIzquierda.add(getListaTickets(), BorderLayout.CENTER);
		}
		return panelIzquierda;
	}

	/**
	 * This method initializes panelDerecha	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelDerecha() {
		if (panelDerecha == null) {
			GridLayout gridLayout2 = new GridLayout(2,1);
			panelDerecha = new JPanel();
			panelDerecha.setPreferredSize(new Dimension(300,400));
			panelDerecha.setBorder(BorderFactory.createTitledBorder(null, "Ticket seleccionado", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			panelDerecha.setLayout(gridLayout2);
			barra = new JScrollPane(getAreaDescripcion());
			panelDerecha.add(barra, null);
			panelDerecha.add(getPanelOpciones(), null);
		}
		return panelDerecha;
	}

	/**
	 * This method initializes areaTickets	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JScrollPane getListaTickets() {
		if (listaTickets == null) {
			listaTickets = new JScrollPane();
		}
		return listaTickets;
	}

	/** This method initializes areaDescripcion	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getAreaDescripcion() {
		if (areaDescripcion == null) {
			areaDescripcion = new JTextArea();
			areaDescripcion.setEditable(false);
			areaDescripcion.setPreferredSize(new Dimension(300,200));
			areaDescripcion.setLineWrap(true);
			areaDescripcion.setWrapStyleWord(true);
		}
		return areaDescripcion;
	}

	/**
	 * This method initializes panelOpciones	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelOpciones() {
		if (panelOpciones == null) {
			panelOpciones = new JPanel();
			panelOpciones.setLayout(new BorderLayout());
			panelOpciones.add(getPanelNuevo(), BorderLayout.CENTER);
			panelOpciones.add(getPanelPropiedades(), BorderLayout.SOUTH);
		}
		return panelOpciones;
	}

	/**
	 * This method initializes botonNuevo	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBotonAtender() {
		if (botonAtender == null) {
			botonAtender = new JButton();
			botonAtender.setText("Atender");
			botonAtender.addActionListener(this);
			botonAtender.setActionCommand("atender");
		}
		return botonAtender;
	}

	/**
	 * This method initializes panelPropiedades	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelPropiedades() {
		if (panelPropiedades == null) {
			etiquetaVacia2 = new JLabel();
			etiquetaVacia2.setText("");
			etiquetaVacia1 = new JLabel();
			etiquetaVacia1.setText("");
			GridLayout gridLayout3 = new GridLayout();
			gridLayout3.setRows(2);
			gridLayout3.setColumns(2);
			panelPropiedades = new JPanel();
			panelPropiedades.setLayout(gridLayout3);
			panelPropiedades.add(etiquetaVacia2, null);
			panelPropiedades.add(etiquetaVacia1, null);
			panelPropiedades.add(getBotonOpcion1(), null);
			panelPropiedades.add(getBotonOpcion2(), null);
		}
		return panelPropiedades;
	}

	/**
	 * This method initializes botonCalificar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBotonCerrar() {
		if (botonCerrar == null) {
			botonCerrar = new JButton();
			botonCerrar.setEnabled(false);
			botonCerrar.setText("Cerrar");
			botonCerrar.addActionListener(this);
			botonCerrar.setActionCommand("cerrar");
		}
		return botonCerrar;
	}

	/**
	 * This method initializes botonOpcion1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBotonOpcion1() {
		if (botonOpcion1 == null) {
			botonOpcion1 = new JButton();
			botonOpcion1.setText("Opcion 1");
			botonOpcion1.addActionListener(this);
			botonOpcion1.setActionCommand("opcion1");
		}
		return botonOpcion1;
	}

	/**
	 * This method initializes botonOpcion2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBotonOpcion2() {
		if (botonOpcion2 == null) {
			botonOpcion2 = new JButton();
			botonOpcion2.setText("Opcion 2");
			botonOpcion2.addActionListener(this);
			botonOpcion2.setActionCommand("opcion2");
		}
		return botonOpcion2;
	}

	/**
	 * This method initializes panelNuevo	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelNuevo() {
		if (panelNuevo == null) {
			panelNuevo = new JPanel();
			panelNuevo.setLayout(new GridLayout(3,1));
			panelNuevo.setBorder(BorderFactory.createEmptyBorder(20, 55, 20, 55));
			panelNuevo.add(getBotonDescifrar());
			panelNuevo.add(getBotonAtender());
			panelNuevo.add(getBotonCerrar());
		}
		return panelNuevo;
	}

	private JButton getBotonDescifrar() {
		if (botonDescifrar == null) {
			botonDescifrar = new JButton();
			botonDescifrar.setText("Descifrar");
			botonDescifrar.addActionListener(this);
			botonDescifrar.setActionCommand("descifrar");
			botonDescifrar.setEnabled(false);
		}
		return botonDescifrar;
	}

	public void valueChanged(TreeSelectionEvent evento) {
		DefaultMutableTreeNode nodo = (DefaultMutableTreeNode)jArbol.getLastSelectedPathComponent();
		
		if(nodo==null)
			return;
		else if(nodo.isLeaf() && nodo.getLevel()==2)
		{
			ticketActual = (ITicket)nodo.getUserObject();
			if(ticketActual.darFechaAtencion() == null )
				botonAtender.setEnabled(true);
			else if(ticketActual.darFechaAtencion() != null && ticketActual.darFechaCierre() == null)
				botonCerrar.setEnabled(true);
			else
			{
				botonAtender.setEnabled(false);
				botonCerrar.setEnabled(false);
			}
			if(ticketActual.estaCifrado())
				getBotonDescifrar().setEnabled(true);
			else
				getBotonDescifrar().setEnabled(false);
		}
		else
		{
			ticketActual = null;
		}
		actualizarDescripcion();
	}
	
	public void actualizarDescripcion()
	{
		if(ticketActual!=null)
		{
			areaDescripcion.setText("Creado por " + ticketActual.darNombreCliente() + "\nCalificacion: " + ticketActual.darCalificacion()
				+ "\nCreacion: " + ticketActual.darFechaCreacion().toString() + "\nAtencion: " + (ticketActual.darFechaAtencion() == null ? "" : ticketActual.darFechaAtencion().toString()) +
				"\nCierre: " + (ticketActual.darFechaCierre() == null ? "" : ticketActual.darFechaCierre().toString()) + "\nComentario del cliente: " + ticketActual.darComentarioCliente() +
				"\nMi comentario: " + (ticketActual.darComentarioEmpleado()==null?"":ticketActual.darComentarioEmpleado()));
		}
		else
			areaDescripcion.setText("Seleccione un ticket.");
		areaDescripcion.setPreferredSize(new Dimension(300, areaDescripcion.getText().length() / 2));
		areaDescripcion.revalidate();
	}

}

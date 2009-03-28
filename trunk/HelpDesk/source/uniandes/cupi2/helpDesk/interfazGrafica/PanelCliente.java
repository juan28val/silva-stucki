package uniandes.cupi2.helpDesk.interfazGrafica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import uniandes.cupi2.helpDesk.interfazMundo.*;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.util.ArrayList;

public class PanelCliente extends JPanel implements ActionListener, TreeSelectionListener {

	private static final long serialVersionUID = 1L;
	private JPanel panelIzquierda = null;
	private JPanel panelDerecha = null;
	private JPanel panelNavegacion = null;
	private JScrollPane listaTickets = null;
	private JButton botonAtras = null;
	private JButton botonAdelante = null;
	private JTextArea areaDescripcion = null;
	private JPanel panelOpciones = null;
	private JButton botonNuevo = null;
	private JPanel panelPropiedades = null;
	private JComboBox listaCalificacion = null;
	private JButton botonCalificar = null;
	private JButton botonOpcion1 = null;
	private JButton botonOpcion2 = null;
	private JLabel etiquetaVacia1 = null;
	private JLabel etiquetaVacia2 = null;
	private JPanel panelAcciones = null;
	private JScrollPane barra = null;
	private InterfazHelpDesk principal;
	private IIterador iterador;
	private ITicket ticketActual;
	ArrayList<ITicket> tickets;
	private JButton botonReabrir;

	
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
			
		if(evento.getActionCommand().equals("nuevo"))
		{
			int i = listaTickets.getSelectedIndex();
			DialogoTicketNuevo nuevo = new DialogoTicketNuevo(principal, this);
			nuevo.setVisible(true);
			principal.darJFrame().setEnabled(false);
			if(i != -1)
			{
				ticketActual = tickets.get(i);
				listaTickets.setSelectedIndex(i);
				actualizarDescripcion();
			}

		}
		if(evento.getActionCommand().equals("calificar"))
		{
			
			if(ticketActual != null && ticketActual.darFechaCierre() != null)
			{
				principal.calificar(ticketActual, (String)listaCalificacion.getSelectedItem());
				actualizarDescripcion();
			}
			
		}
		
		if(evento.getActionCommand().equals("reabrir"))
		{
			if(ticketActual != null && ticketActual.darFechaCierre() != null)
			{
				int i = listaTickets.getSelectedIndex();
				DialogoReapertura nuevo = new DialogoReapertura(principal, this, ticketActual);
				nuevo.setVisible(true);
				principal.darJFrame().setEnabled(false);
				if(i != -1)
				{
					ticketActual = tickets.get(i);
					listaTickets.setSelectedIndex(i);
					actualizarDescripcion();
				}
			}
		}
		if(evento.getActionCommand().equals("opcion1"))
			principal.opcion1();
		if(evento.getActionCommand().equals("opcion2"))
			principal.opcion2();
	}

	public void actualizar() {
		DefaultMutableTreeNode raizTickets = new DefaultMutableTreeNode("Empleados");
		while(iterador.hayGrupoSiguiente())
		{
			iterador.darGrupoSiguiente();
			if(iterador.haySiguiente())
			{
				ITicket ticket = (ITicket) iterador.darSiguiente();
				DefaultMutableTreeNode nuevoEmpleado = new DefaultMutableTreeNode(ticket.darEmpleado().darNombre());
				nuevoEmpleado.add(new DefaultMutableTreeNode(ticket.darId() + " - " + ticket.darFechaAtencion()));
				while(iterador.haySiguiente())
				{
					ticket = (ITicket) iterador.darSiguiente();
					nuevoEmpleado.add(new DefaultMutableTreeNode(ticket.darId() + " - " + ticket.darFechaAtencion()));
				}
				raizTickets.add(nuevoEmpleado);
			}			
		}
		panelIzquierda.remove(listaTickets);
		JTree jArbol = new JTree(raizTickets);
		jArbol.addTreeSelectionListener(this);
		listaTickets = new JScrollPane(jArbol);
		listaTickets.setPreferredSize(new Dimension(80,300));
		panelIzquierda.add(listaTickets, BorderLayout.CENTER);
		areaDescripcion.setText("");
		panelIzquierda.validate();
	}

	/**
	 * This is the default constructor
	 */
	public PanelCliente(InterfazHelpDesk principal, IIterador iterador) {
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
		setPreferredSize(new Dimension(630, 400));
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
			panelIzquierda.add(getPanelNavegacion(), BorderLayout.SOUTH);
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
			panelDerecha.setPreferredSize(new Dimension(200,200));
			panelDerecha.setBorder(BorderFactory.createTitledBorder(null, "Ticket seleccionado", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			panelDerecha.setLayout(gridLayout2);
			barra = new JScrollPane(getAreaDescripcion());
			panelDerecha.add(barra, null);
			panelDerecha.add(getPanelOpciones(), null);
		}
		return panelDerecha;
	}

	/**
	 * This method initializes panelNavegacion	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelNavegacion() {
		if (panelNavegacion == null) {
			GridLayout gridLayout1 = new GridLayout();
			gridLayout1.setRows(1);
			panelNavegacion = new JPanel();
			panelNavegacion.setLayout(gridLayout1);
			panelNavegacion.add(getBotonAtras(), null);
			panelNavegacion.add(getBotonAdelante(), null);
		}
		return panelNavegacion;
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

	/**
	 * This method initializes botonAtras	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBotonAtras() {
		if (botonAtras == null) {
			botonAtras = new JButton();
			botonAtras.setText("<<");
			botonAtras.addActionListener(this);
			botonAtras.setActionCommand("atras");
		}
		return botonAtras;
	}

	/**
	 * This method initializes botonAdelante	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBotonAdelante() {
		if (botonAdelante == null) {
			botonAdelante = new JButton();
			botonAdelante.setText(">>");
			botonAdelante.addActionListener(this);
			botonAdelante.setActionCommand("adelante");
		}
		return botonAdelante;
	}

	/**
	 * This method initializes areaDescripcion	
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
			panelOpciones.add(getPanelAcciones(), BorderLayout.CENTER);
			panelOpciones.add(getPanelPropiedades(), BorderLayout.SOUTH);
		}
		return panelOpciones;
	}

	/**
	 * This method initializes botonNuevo	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBotonNuevo() {
		if (botonNuevo == null) {
			botonNuevo = new JButton();
			botonNuevo.setText("Crear");
			botonNuevo.addActionListener(this);
			botonNuevo.setActionCommand("nuevo");
		}
		return botonNuevo;
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
			gridLayout3.setRows(3);
			gridLayout3.setColumns(2);
			panelPropiedades = new JPanel();
			panelPropiedades.setLayout(gridLayout3);
			panelPropiedades.add(getListaCalificacion(), null);
			panelPropiedades.add(getBotonCalificar(), null);
			panelPropiedades.add(etiquetaVacia2, null);
			panelPropiedades.add(etiquetaVacia1, null);
			panelPropiedades.add(getBotonOpcion1(), null);
			panelPropiedades.add(getBotonOpcion2(), null);
		}
		return panelPropiedades;
	}

	/**
	 * This method initializes listaCalificacion	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getListaCalificacion() {
		if (listaCalificacion == null) {
			listaCalificacion = new JComboBox();
			listaCalificacion.addItem("Muy malo");
			listaCalificacion.addItem("Podria mejorar");
			listaCalificacion.addItem("Satisfecho");
			listaCalificacion.addItem("Muy satisfecho");
		}
		return listaCalificacion;
	}

	/**
	 * This method initializes botonCalificar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBotonCalificar() {
		if (botonCalificar == null) {
			botonCalificar = new JButton();
			botonCalificar.setText("Calificar");
			botonCalificar.addActionListener(this);
			botonCalificar.setActionCommand("calificar");
		}
		return botonCalificar;
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
	private JPanel getPanelAcciones() {
		if (panelAcciones == null) {
			panelAcciones = new JPanel();			
			panelAcciones.setLayout(new GridLayout(2,1));
			panelAcciones.setBorder(BorderFactory.createEmptyBorder(20, 55, 20, 55));
			panelAcciones.add(getBotonNuevo());
			panelAcciones.add(getBotonReabrir());
		}
		return panelAcciones;
	}

	private Component getBotonReabrir() {
		if (botonReabrir == null) {
			botonReabrir = new JButton();
			botonReabrir.setText("Reabrir");
			botonReabrir.addActionListener(this);
			botonReabrir.setActionCommand("reabrir");
			botonReabrir.setEnabled(false);
		}
		return botonReabrir;
	}

	public void valueChanged(ListSelectionEvent evento) {
		ticketActual = tickets.get( listaTickets.getSelectedIndex());
		if(ticketActual == null)
			return;
		if(ticketActual.darFechaCierre() != null)
		{
			botonCalificar.setEnabled(true);
			botonReabrir.setEnabled(true);
		}
		else
		{
			botonCalificar.setEnabled(false);
			botonReabrir.setEnabled(false);
		}
		actualizarDescripcion();
	}
	
	public void actualizarDescripcion()
	{
		
		areaDescripcion.setText("Asignado a " + ticketActual.darNombreEmpleado() + (ticketActual.atendidoPorExperto() ? "" : " (inexperto)") + "\nCalificacion: " + ticketActual.darCalificacion()
				+ "\nCreacion: " + ticketActual.darFechaCreacion().toString() + "\nAtencion: " + (ticketActual.darFechaAtencion() == null ? "" : ticketActual.darFechaAtencion().toString()) +
						"\nCierre: " + (ticketActual.darFechaCierre() == null ? "" : ticketActual.darFechaCierre().toString()) + "\nMi comentario: " + ticketActual.darComentarioCliente() +
								"\nComentario del funcionario: " + (ticketActual.darComentarioEmpleado()==null?"":ticketActual.darComentarioEmpleado()));
			areaDescripcion.setPreferredSize(new Dimension(300, areaDescripcion.getText().length() / 2));
		areaDescripcion.revalidate();
	}

	public void valueChanged(TreeSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

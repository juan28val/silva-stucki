package uniandes.cupi2.helpDesk.interfazGrafica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import uniandes.cupi2.helpDesk.interfazMundo.IIncidente;
import uniandes.cupi2.helpDesk.interfazMundo.IIterador;
import uniandes.cupi2.helpDesk.interfazMundo.ITicket;
import uniandes.cupi2.helpDesk.interfazMundo.IUsuario;

import com.toedter.calendar.*;

public class PanelAdmin extends JPanel implements ActionListener, PropertyChangeListener {

	private static final long serialVersionUID = 1L;
	private JPanel panelTickets = null;
	private JPanel panelClientes = null;
	private JPanel panelDerecha = null;
	private JList listaTickets = null;
	private JPanel navTickets = null;
	private JButton atrasTickets = null;
	private JButton adelanteTickets = null;
	private JList listaClientes = null;
	private JPanel navClientes = null;
	private JButton atrasClientes = null;
	private JButton adelanteClientes = null;
	private JList listaEmpleados = null;
	private JPanel navEmpleados = null;
	private JButton atrasEmpleados = null;
	private JButton adelanteEmpleados = null;
	private JLabel vacia1 = null;
	private JLabel vacia2 = null;
	private JButton opcion1 = null;
	private JButton opcion2 = null;
	private IIterador iteradorTickets = null;
	private IIterador iteradorEmpleados = null;
	private IIterador iteradorClientes = null;
	private InterfazHelpDesk principal;
	private JDateChooser fecha1;
	private JDateChooser fecha2;
	private JRadioButton radioIncidentes1;
	private JRadioButton radioIncidentes2;
	private ButtonGroup grupo;
	private JPanel navIncidentes;
	private JDateChooser fecha3;
	private JButton atrasIncidentes;
	private JButton adelanteIncidente;
	private IIterador iteradorIncidentes;
	private JPanel panelIncidentes;
	private JList listaIncidentes;
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("adelanteClientes"))
		{
			iteradorClientes.darGrupoSiguiente();
			actualizarClientes();
		}
		else if(e.getActionCommand().equals("atrasClientes"))
		{
			iteradorClientes.darGrupoAnterior();
			actualizarClientes();
		}	
		else if(e.getActionCommand().equals("adelanteEmpleados"))
		{
			iteradorEmpleados.darGrupoSiguiente();
			actualizarEmpleados();
		}	
		else if(e.getActionCommand().equals("atrasEmpleados"))
		{
			iteradorEmpleados.darGrupoAnterior();
			actualizarEmpleados();
		}		
		else if(e.getActionCommand().equals("adelanteTickets"))
		{
			iteradorTickets.darGrupoSiguiente();
			actualizarTickets();
		}	
		else if(e.getActionCommand().equals("atrasTickets"))
		{
			iteradorTickets.darGrupoAnterior();
			actualizarTickets();
		}	
		else if(e.getActionCommand().equals("adelanteIncidentes"))
		{
			iteradorIncidentes.darGrupoSiguiente();
			actualizarIncidentes();
		}	
		else if(e.getActionCommand().equals("atrasIncidentes"))
		{
			iteradorIncidentes.darGrupoAnterior();
			actualizarIncidentes();
		}
		else if(e.getActionCommand().equals("radio"))
		{
			iteradorIncidentes = principal.darListaIncidentes(radioIncidentes1.isSelected(), fecha3.getDate());
			actualizarIncidentes();
		}
		else if(e.getActionCommand().equals("opcion1"))
			principal.opcion1();
		else if(e.getActionCommand().equals("opcion2"))
			principal.opcion2();
	}

	private void actualizarTickets() {
		iteradorTickets.darGrupoActual();
		ArrayList<String> datos = new ArrayList<String>();
		while(iteradorTickets.haySiguiente())
		{
			ITicket ticket = (ITicket) iteradorTickets.darSiguiente();
			datos.add(ticket.darId() + " - " + ticket.darFechaAtencion());
		}
		panelTickets.remove(listaTickets);
		listaTickets = new JList(datos.toArray());
		listaTickets.setPreferredSize(new Dimension(80,300));
		panelTickets.add(listaTickets, BorderLayout.CENTER);
		panelTickets.validate();
	}
	
	private void actualizarIncidentes() {
		iteradorIncidentes.darGrupoActual();
		ArrayList<String> datos = new ArrayList<String>();
		while(iteradorIncidentes.haySiguiente())
		{
			IIncidente incidente = (IIncidente) iteradorIncidentes.darSiguiente();
			datos.add(incidente.toString());
		}
		panelIncidentes.remove(listaIncidentes);
		listaIncidentes = new JList(datos.toArray());
		listaIncidentes.setPreferredSize(new Dimension(80,300));
		panelIncidentes.add(listaIncidentes, BorderLayout.CENTER);
		panelIncidentes.validate();
	}

	private void actualizarEmpleados() {
		iteradorEmpleados.darGrupoActual();
		ArrayList<String> datos = new ArrayList<String>();
		while(iteradorEmpleados.haySiguiente())
		{
			IUsuario empleado = (IUsuario) iteradorEmpleados.darSiguiente();
			datos.add(empleado.darNombre() + " - " + empleado.darSumaCalificacion());
		}
		panelDerecha.remove(listaEmpleados);
		listaEmpleados = new JList(datos.toArray());
		panelDerecha.add(listaEmpleados, BorderLayout.CENTER);
		panelDerecha.validate();
	}

	private void actualizarClientes() {
		iteradorClientes.darGrupoActual();
		ArrayList<String> datos = new ArrayList<String>();
		while(iteradorClientes.haySiguiente())
		{
			IUsuario cliente = (IUsuario) iteradorClientes.darSiguiente();
			datos.add(cliente.darNombre() + " - " + cliente.darFechaAtencion());
		}
		panelClientes.remove(listaClientes);
		listaClientes = new JList(datos.toArray());
		panelClientes.add(listaClientes, BorderLayout.CENTER);
		panelClientes.validate();
	}

	/**
	 * This is the default constructor
	 * @param iteradorUsuario 
	 * @param iteradorTicket 
	 */
	public PanelAdmin(InterfazHelpDesk principal, IIterador iteradorTicket, IIterador iteradorEmpleados,IIterador iteradorClientes,IIterador iteradorIncidentes) {
		super();
		principal.darJFrame().setSize(900,600);
		initialize();
		
		this.principal = principal;
		this.iteradorTickets = iteradorTicket;
		this.iteradorEmpleados = iteradorEmpleados;
		this.iteradorClientes = iteradorClientes;
		this.iteradorIncidentes = iteradorIncidentes;

		actualizarTickets();
		actualizarClientes();
		actualizarEmpleados();
		actualizarIncidentes();
		
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridLayout gridLayout = new GridLayout();
		gridLayout.setRows(1);
		gridLayout.setColumns(3);
		this.setLayout(gridLayout);
		this.setSize(1000, 600);
		this.add(getPanelTickets(), null);
		this.add(getPanelClientes(), null);
		this.add(getPanelEmpleados(), null);
		this.add(getPanelIncidentes(), null);
	}

	/**
	 * This method initializes panelTickets	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelTickets() {
		if (panelTickets == null) {
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setHgap(0);
			borderLayout.setVgap(0);
			panelTickets = new JPanel();
			panelTickets.setLayout(borderLayout);
			panelTickets.setBorder(new TitledBorder("Lista de tickets a fecha"));
			panelTickets.add(getListaTickets(), BorderLayout.CENTER);
			panelTickets.add(getNavTickets(), BorderLayout.SOUTH);
		}
		return panelTickets;
	}

	/**
	 * This method initializes panelClientes	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelClientes() {
		if (panelClientes == null) {
			panelClientes = new JPanel();
			panelClientes.setLayout(new BorderLayout());
			panelClientes.setBorder(new TitledBorder("Lista de clientes atendidos"));
			panelClientes.add(getListaClientes(), BorderLayout.CENTER);
			panelClientes.add(getNavClientes(), BorderLayout.SOUTH);
		}
		return panelClientes;
	}

	/**
	 * This method initializes panelDerecha	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelEmpleados() {
		if (panelDerecha == null) {
			panelDerecha = new JPanel();
			panelDerecha.setLayout(new BorderLayout());
			panelDerecha.setBorder(new TitledBorder("Empleados del mes"));
			panelDerecha.add(getListaEmpleados(), BorderLayout.CENTER);
			panelDerecha.add(getNavEmpleados(), BorderLayout.SOUTH);
		}
		return panelDerecha;
	}

	private JPanel getPanelIncidentes() {
		if (panelIncidentes == null) {
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setHgap(0);
			borderLayout.setVgap(0);
			panelIncidentes = new JPanel();
			panelIncidentes.setLayout(borderLayout);
			panelIncidentes.setBorder(new TitledBorder("Lista incidentes a fecha:"));
			panelIncidentes.add(getListaIncidentes(), BorderLayout.CENTER);
			panelIncidentes.add(getNavIncidentes(), BorderLayout.SOUTH);
		}
		return panelIncidentes;
	}
	
	/**
	 * This method initializes listaTickets	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getListaTickets() {
		if (listaTickets == null) {
			listaTickets = new JList();
			listaTickets.setPreferredSize(new Dimension(80,300));
		}
		return listaTickets;
	}

	private JList getListaIncidentes() {
		if (listaIncidentes == null) {
			listaIncidentes = new JList();
			listaIncidentes.setPreferredSize(new Dimension(80,300));
		}
		return listaIncidentes;
	}
	
	/**
	 * This method initializes navTickets	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getNavTickets() {
		if (navTickets == null) {
			GridLayout gridLayout1 = new GridLayout();
			gridLayout1.setRows(3);
			gridLayout1.setColumns(1);
			navTickets = new JPanel();
			navTickets.setLayout(gridLayout1);
			navTickets.add(getAtrasTickets());
			navTickets.add(getAdelanteTickets());
			navTickets.add(new JLabel(""));
			navTickets.add(new JLabel(""));
			fecha1 = new JDateChooser(new Date());
			fecha1.addPropertyChangeListener(this);
			navTickets.add(fecha1);
			fecha2 = new JDateChooser(new Date());
			fecha2.addPropertyChangeListener(this);
			navTickets.add(fecha2);
		}
		return navTickets;
	}

	private JPanel getNavIncidentes() {
		if (navIncidentes == null) {
			GridLayout gridLayout1 = new GridLayout();
			gridLayout1.setRows(3);
			gridLayout1.setColumns(1);
			navIncidentes = new JPanel();
			navIncidentes.setLayout(gridLayout1);
			navIncidentes.add(getAtrasIncidentes());
			navIncidentes.add(getAdelanteIncidentes());
			navIncidentes.add(new JLabel(""));
			navIncidentes.add(new JLabel(""));
			fecha3 = new JDateChooser(new Date());
			fecha3.addPropertyChangeListener(this);
			JPanel radios = new JPanel();
			radios.setLayout(new BorderLayout());
			radioIncidentes1 = new JRadioButton("Antes de ");
			radioIncidentes2 = new JRadioButton("Despues de ");
			radioIncidentes1.addActionListener(this);
			radioIncidentes2.addActionListener(this);
			radioIncidentes1.setActionCommand("radio");
			radioIncidentes2.setActionCommand("radio");
			grupo = new ButtonGroup();
			grupo.add(radioIncidentes1);
			grupo.add(radioIncidentes2);
			radioIncidentes1.setSelected(true);
			radios.add(radioIncidentes1, BorderLayout.NORTH);
			radios.add(radioIncidentes2, BorderLayout.SOUTH);
			navIncidentes.add(radios);
			navIncidentes.add(fecha3);
		}
		return navIncidentes;
	}
	
	/**
	 * This method initializes atrasTickets	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAtrasTickets() {
		if (atrasTickets == null) {
			atrasTickets = new JButton();
			atrasTickets.setText("<<");
			atrasTickets.addActionListener(this);
			atrasTickets.setActionCommand("atrasTickets");
		}
		return atrasTickets;
	}

	/**
	 * This method initializes adelanteTickets	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAdelanteTickets() {
		if (adelanteTickets == null) {
			adelanteTickets = new JButton();
			adelanteTickets.setText(">>");
			adelanteTickets.addActionListener(this);
			adelanteTickets.setActionCommand("adelanteTickets");
		}
		return adelanteTickets;
	}

	private JButton getAtrasIncidentes() {
		if (atrasIncidentes == null) {
			atrasIncidentes = new JButton();
			atrasIncidentes.setText("<<");
			atrasIncidentes.addActionListener(this);
			atrasIncidentes.setActionCommand("atrasIncidentes");
		}
		return atrasIncidentes;
	}

	/**
	 * This method initializes adelanteTickets	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAdelanteIncidentes() {
		if (adelanteIncidente == null) {
			adelanteIncidente = new JButton();
			adelanteIncidente.setText(">>");
			adelanteIncidente.addActionListener(this);
			adelanteIncidente.setActionCommand("adelanteIncidentes");
		}
		return adelanteIncidente;
	}
	
	/**
	 * This method initializes listaClientes	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getListaClientes() {
		if (listaClientes == null) {
			listaClientes = new JList();
		}
		return listaClientes;
	}

	/**
	 * This method initializes navClientes	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getNavClientes() {
		if (navClientes == null) {
			GridLayout gridLayout11 = new GridLayout();
			gridLayout11.setRows(1);
			gridLayout11.setColumns(2);
			navClientes = new JPanel();
			navClientes.setLayout(gridLayout11);
			navClientes.add(getAtrasClientes(), null);
			navClientes.add(getAdelanteClientes(), null);
		}
		return navClientes;
	}

	/**
	 * This method initializes atrasClientes	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAtrasClientes() {
		if (atrasClientes == null) {
			atrasClientes = new JButton();
			atrasClientes.setText("<<");
			atrasClientes.addActionListener(this);
			atrasClientes.setActionCommand("atrasClientes");
		}
		return atrasClientes;
	}

	/**
	 * This method initializes adelanteClientes	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAdelanteClientes() {
		if (adelanteClientes == null) {
			adelanteClientes = new JButton();
			adelanteClientes.setText(">>");
			adelanteClientes.addActionListener(this);
			adelanteClientes.setActionCommand("adelanteClientes");
		}
		return adelanteClientes;
	}

	/**
	 * This method initializes listaEmpleados	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getListaEmpleados() {
		if (listaEmpleados == null) {
			listaEmpleados = new JList();
		}
		return listaEmpleados;
	}

	/**
	 * This method initializes navEmpleados	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getNavEmpleados() {
		if (navEmpleados == null) {
			vacia2 = new JLabel();
			vacia2.setText("");
			vacia1 = new JLabel();
			vacia1.setText("");
			GridLayout gridLayout12 = new GridLayout();
			gridLayout12.setRows(3);
			gridLayout12.setColumns(2);
			navEmpleados = new JPanel();
			navEmpleados.setLayout(gridLayout12);
			navEmpleados.add(getAtrasEmpleados(), null);
			navEmpleados.add(getAdelanteEmpleados(), null);
			navEmpleados.add(vacia2, null);
			navEmpleados.add(vacia1, null);
			navEmpleados.add(getOpcion1(), null);
			navEmpleados.add(getOpcion2(), null);
		}
		return navEmpleados;
	}

	/**
	 * This method initializes atrasEmpleados	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAtrasEmpleados() {
		if (atrasEmpleados == null) {
			atrasEmpleados = new JButton();
			atrasEmpleados.setText("<<");
			atrasEmpleados.addActionListener(this);
			atrasEmpleados.setActionCommand("atrasEmpleados");
		}
		return atrasEmpleados;
	}

	/**
	 * This method initializes adelanteEmpleados	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAdelanteEmpleados() {
		if (adelanteEmpleados == null) {
			adelanteEmpleados = new JButton();
			adelanteEmpleados.setText(">>");
			adelanteEmpleados.addActionListener(this);
			adelanteEmpleados.setActionCommand("adelanteEmpleados");
		}
		return adelanteEmpleados;
	}

	/**
	 * This method initializes opcion1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOpcion1() {
		if (opcion1 == null) {
			opcion1 = new JButton();
			opcion1.setText("Opción 1");
			opcion1.addActionListener(this);
			opcion1.setActionCommand("opcion1");
		}
		return opcion1;
	}

	/**
	 * This method initializes opción2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOpcion2() {
		if (opcion2 == null) {
			opcion2 = new JButton();
			opcion2.setText("Opción 2");
			opcion2.addActionListener(this);
			opcion2.setActionCommand("opcion2");
		}
		return opcion2;
	}

	public void propertyChange(PropertyChangeEvent evento) {
		iteradorTickets = principal.darListaTicketsEntreFechas(fecha1.getDate(), fecha2.getDate());
		actualizarTickets();
		
		iteradorIncidentes = principal.darListaIncidentes(radioIncidentes1.isSelected(), fecha3.getDate());
		actualizarIncidentes();
	}

}

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
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

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
	private JScrollPane listaTickets = null;
	private JPanel navTickets = null;
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
	private IInterfaz principal;
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
		DefaultMutableTreeNode raizTickets = new DefaultMutableTreeNode("Empleados");
		while(iteradorTickets.hayGrupoSiguiente())
		{
			iteradorTickets.darGrupoSiguiente();
			if(iteradorTickets.haySiguiente())
			{
				ITicket ticket = (ITicket) iteradorTickets.darSiguiente();
				DefaultMutableTreeNode nuevoEmpleado = new DefaultMutableTreeNode(ticket.darEmpleado().darNombre());
				nuevoEmpleado.add(new DefaultMutableTreeNode(ticket.darId() + " - " + ticket.darFechaAtencion()));
				while(iteradorTickets.haySiguiente())
				{
					ticket = (ITicket) iteradorTickets.darSiguiente();
					nuevoEmpleado.add(new DefaultMutableTreeNode(ticket.darId() + " - " + ticket.darFechaAtencion()));
				}
				raizTickets.add(nuevoEmpleado);
			}			
		}
		panelTickets.remove(listaTickets);
		JTree jArbol = new JTree(raizTickets);
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
		jArbol.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		for(int i=raizTickets.getChildCount();i>0;i--)
			jArbol.expandRow(i);
		listaTickets = new JScrollPane(jArbol);
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
	public PanelAdmin(IInterfaz principal, IIterador iteradorTicket, IIterador iteradorEmpleados,IIterador iteradorClientes,IIterador iteradorIncidentes) {
		super();
		principal.getJFrame().setSize(900,600);
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
			panelTickets.setBorder(new TitledBorder("Tickets abiertos entre las fechas:"));
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
			panelClientes.setBorder(new TitledBorder("Clientes atendidos"));
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
			panelIncidentes.setBorder(new TitledBorder("Incidentes ocurridos"));
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
	private JScrollPane getListaTickets() {
		if (listaTickets == null) {
			DefaultMutableTreeNode raizTickets = new DefaultMutableTreeNode("Empleados");
			listaTickets = new JScrollPane(new JTree(raizTickets));
			listaTickets.setPreferredSize(new Dimension(100,300));
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
	 * This method initializes atrasIncidentes	
	 */

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

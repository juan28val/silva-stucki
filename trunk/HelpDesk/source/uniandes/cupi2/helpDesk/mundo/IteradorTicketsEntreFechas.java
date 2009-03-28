package uniandes.cupi2.helpDesk.mundo;

import java.util.ArrayList;
import java.util.Date;

import uniandes.cupi2.collections.tablaHashing.tablaHashingDinamica.TablaHashingDinamica;
import uniandes.cupi2.helpDesk.interfazMundo.IIterador;
import uniandes.cupi2.helpDesk.interfazMundo.ITicket;

public class IteradorTicketsEntreFechas implements IIterador {

	public static final int LIMITE = 17;
	
	private int pos;
	private IteradorEmpleados iteradorEmpleados;
	ArrayList<ITicket> listaTicketsEnFechaDelEmpleado;
	private Date fecha1;
	private Date fecha2;
	private TablaHashingDinamica<Integer, Ticket> tabla;



	public IteradorTicketsEntreFechas(IIterador iteradorEmpleados, Date fecha1, Date fecha2, TablaHashingDinamica<Integer, Ticket> tabla) {
		pos = 0;
		this.iteradorEmpleados = (IteradorEmpleados)iteradorEmpleados;
		this.fecha1 = fecha1;
		this.fecha2 = fecha2;
		this.tabla = tabla;	
		listaTicketsEnFechaDelEmpleado = new ArrayList<ITicket>();
	}

	public void darGrupoAnterior() {
		// no se usa
	}

	@SuppressWarnings("deprecation")
	public void darGrupoSiguiente() {
		listaTicketsEnFechaDelEmpleado = new ArrayList<ITicket>();
		if( iteradorEmpleados.haySiguiente() )
		{
			Empleado empleado = (Empleado) iteradorEmpleados.darSiguiente();
			
			for (int i = 0; i < empleado.darListaTickets().size(); i++) 
			{
				ITicket ticketActual = tabla.dar(empleado.darListaTickets().get(i));
				fecha1.setHours(0);
				fecha1.setMinutes(0);
				fecha2.setHours(23);
				fecha2.setMinutes(59);
			
				if( ticketActual.darFechaAtencion() != null && ticketActual.darFechaAtencion().after(fecha1) && ticketActual.darFechaAtencion().before(fecha2) )
					listaTicketsEnFechaDelEmpleado.add(ticketActual);
			}
		}
		pos = 0;
	}

	public ITicket darSiguiente() {
		pos++;
		return listaTicketsEnFechaDelEmpleado.get(pos-1);
	}

	public boolean haySiguiente() {

		return pos<listaTicketsEnFechaDelEmpleado.size()-1;
	}

	public void darGrupoActual() {
		// no se usa
	}

	public boolean hayGrupoSiguiente() {
		return iteradorEmpleados.haySiguiente();		
	}
}

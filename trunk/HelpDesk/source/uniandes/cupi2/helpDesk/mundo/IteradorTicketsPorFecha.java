package uniandes.cupi2.helpDesk.mundo;

import java.util.ArrayList;
import java.util.Date;

import uniandes.cupi2.collections.tablaHashing.tablaHashingDinamica.TablaHashingDinamica;
import uniandes.cupi2.helpDesk.interfazMundo.IIterador;
import uniandes.cupi2.helpDesk.interfazMundo.ITicket;

public class IteradorTicketsPorFecha implements IIterador {

	public static final int LIMITE = 17;
	
	ArrayList<ITicket> listaTicketsEnFecha;
	private int pos;
	private int posGrupo;
	
	@SuppressWarnings("deprecation")
	public IteradorTicketsPorFecha(ArrayList<Integer> listaTickets, Date fecha1, Date fecha2, TablaHashingDinamica<Integer, Ticket> tabla) {
		pos = 0;
		posGrupo = 0;
		listaTicketsEnFecha = new ArrayList<ITicket>();
		for (int i = 0; i < listaTickets.size(); i++) {
			
			ITicket ticketActual = tabla.dar(listaTickets.get(i));
			fecha1.setHours(0);
			fecha1.setMinutes(0);
			fecha2.setHours(23);
			fecha2.setMinutes(59);
			
			if( ticketActual.darFechaAtencion() != null && ticketActual.darFechaAtencion().after(fecha1) && ticketActual.darFechaAtencion().before(fecha2) )
				listaTicketsEnFecha.add(ticketActual);
		}
		
		
	}

	public void darGrupoAnterior() {
		if( posGrupo >= LIMITE )
			posGrupo = posGrupo-LIMITE;
		pos = posGrupo;
	}

	public void darGrupoSiguiente() {
		if( posGrupo + LIMITE < listaTicketsEnFecha.size() )
			posGrupo = posGrupo+LIMITE;
		pos = posGrupo;
	}

	public ITicket darSiguiente() {
		pos++;
		return listaTicketsEnFecha.get(pos-1);
	}

	public boolean haySiguiente() {

		return pos<LIMITE + posGrupo && pos < listaTicketsEnFecha.size();
	}

	public void darGrupoActual() {
		pos = posGrupo;
	}
}

package uniandes.cupi2.helpDesk.mundo;

import java.io.Serializable;
import java.util.Date;

import uniandes.cupi2.helpDesk.interfazMundo.IIncidente;
import uniandes.cupi2.helpDesk.interfazMundo.ITicket;
import uniandes.cupi2.helpDesk.interfazMundo.IUsuario;

public class Incidente implements Serializable, IIncidente, Comparable<Incidente> {

	private static final long serialVersionUID = -5390927906866469L;

	private Date fechaIncidente;
	
	private Empleado empleadoAsociado;
	
	private Cliente clienteAsociado;
	
	private String comentarioIncidente;

	private Ticket ticket;

	
	public Incidente( Date fechaIncidente,Empleado empleadoAsociado,Cliente clienteAsociado, Ticket ticket, String comentarioIncidente)
	{
		this.fechaIncidente = fechaIncidente;
		this.empleadoAsociado = empleadoAsociado;
		this.clienteAsociado = clienteAsociado;
		this.comentarioIncidente = comentarioIncidente;
		this.ticket = ticket;
	}
	public IUsuario darCliente() {
		return clienteAsociado;
	}

	public String darComentario() {
		return comentarioIncidente;
	}

	public IUsuario darEmpleado() {
		return empleadoAsociado;
	}

	public Date darFecha() {
		return fechaIncidente;
	}
	
	public ITicket darTicket() {
		return ticket;
	}
	public int compareTo(Incidente arg) {
		return fechaIncidente.compareTo(((Incidente)arg).darFecha());
	}
	
	public String toString()
	{
		return hashCode()+" - "+fechaIncidente.toString();
	}

}

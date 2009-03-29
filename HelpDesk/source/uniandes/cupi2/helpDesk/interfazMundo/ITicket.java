package uniandes.cupi2.helpDesk.interfazMundo;

import java.util.Date;

public interface ITicket
{
    // -------------------------------------------------------------------
    // Constantes
    // -------------------------------------------------------------------
	
	public static final int TIPO_QUEJA = IUsuario.EMPLEADO_QUEJA;
	
	public static final int TIPO_RECLAMO = IUsuario.EMPLEADO_RECLAMO;
	
	public static final int TIPO_SOLICITUD = IUsuario.EMPLEADO_SOLICITUD;
   
	public static final int SIN_CALIFICACION= 0;
	
	public static final int CALIFICACION_DEFINITIVAMENTE_MUY_MALO= 1;
	
	public static final int CALIFICACION_PODRIA_MEJORAR= 2;
	
	public static final int CALIFICACION_SATISFECHO= 4;
	
	public static final int CALIFICACION_MUY_SATISFECHO= 8;
	
	public static final int INCIDENTE = -2;
	
	
    // -------------------------------------------------------------------
    // Métodos
    // -------------------------------------------------------------------
	/**
	 * El empleado al que le fue asignado un ticket lo marca como cerrado generando
	 * una fecha de cierre, y guarda la solucion al problema hecha por el empleado.
	 * pre: el ticket no puede ser null
	 * post: la fecha de cierre del ticket fue asignada
	 * @param ticket: el ticket a cerrar
	 * @param comentario: la solucion del empleado
	 */
	public String darNombreEmpleado();
	
	/**
	 * El empleado al que le fue asignado un ticket lo marca como cerrado generando
	 * una fecha de cierre, y guarda la solucion al problema hecha por el empleado.
	 * pre: el ticket no puede ser null
	 * post: la fecha de cierre del ticket fue asignada
	 * @param ticket: el ticket a cerrar
	 * @param comentario: la solucion del empleado
	 */
	public String darNombreCliente();
	
	/**
	 * El empleado al que le fue asignado un ticket lo marca como cerrado generando
	 * una fecha de cierre, y guarda la solucion al problema hecha por el empleado.
	 * pre: el ticket no puede ser null
	 * post: la fecha de cierre del ticket fue asignada
	 * @param ticket: el ticket a cerrar
	 * @param comentario: la solucion del empleado
	 */
	public Date darFechaCreacion();
	
	/**
	 * El empleado al que le fue asignado un ticket lo marca como cerrado generando
	 * una fecha de cierre, y guarda la solucion al problema hecha por el empleado.
	 * pre: el ticket no puede ser null
	 * post: la fecha de cierre del ticket fue asignada
	 * @param ticket: el ticket a cerrar
	 * @param comentario: la solucion del empleado
	 */
	public Date darFechaAtencion();
	
	/**
	 * El empleado al que le fue asignado un ticket lo marca como cerrado generando
	 * una fecha de cierre, y guarda la solucion al problema hecha por el empleado.
	 * pre: el ticket no puede ser null
	 * post: la fecha de cierre del ticket fue asignada
	 * @param ticket: el ticket a cerrar
	 * @param comentario: la solucion del empleado
	 */
	public Date darFechaCierre();
	
	/**
	 * El empleado al que le fue asignado un ticket lo marca como cerrado generando
	 * una fecha de cierre, y guarda la solucion al problema hecha por el empleado.
	 * pre: el ticket no puede ser null
	 * post: la fecha de cierre del ticket fue asignada
	 * @param ticket: el ticket a cerrar
	 * @param comentario: la solucion del empleado
	 */
	public boolean atendidoPorExperto();
	
	/**
	 * El empleado al que le fue asignado un ticket lo marca como cerrado generando
	 * una fecha de cierre, y guarda la solucion al problema hecha por el empleado.
	 * pre: el ticket no puede ser null
	 * post: la fecha de cierre del ticket fue asignada
	 * @param ticket: el ticket a cerrar
	 * @param comentario: la solucion del empleado
	 */
	public String darComentarioCliente();
	
	/**
	 * El empleado al que le fue asignado un ticket lo marca como cerrado generando
	 * una fecha de cierre, y guarda la solucion al problema hecha por el empleado.
	 * pre: el ticket no puede ser null
	 * post: la fecha de cierre del ticket fue asignada
	 * @param ticket: el ticket a cerrar
	 * @param comentario: la solucion del empleado
	 */
	public String darComentarioEmpleado();

	/**
	 * El empleado al que le fue asignado un ticket lo marca como cerrado generando
	 * una fecha de cierre, y guarda la solucion al problema hecha por el empleado.
	 * pre: el ticket no puede ser null
	 * post: la fecha de cierre del ticket fue asignada
	 * @param ticket: el ticket a cerrar
	 * @param comentario: la solucion del empleado
	 */
	public int darCalificacion();
	
	/**
	 * El empleado al que le fue asignado un ticket lo marca como cerrado generando
	 * una fecha de cierre, y guarda la solucion al problema hecha por el empleado.
	 * pre: el ticket no puede ser null
	 * post: la fecha de cierre del ticket fue asignada
	 * @param ticket: el ticket a cerrar
	 * @param comentario: la solucion del empleado
	 */
	public int darId();	
	
	/**
	 * El empleado al que le fue asignado un ticket lo marca como cerrado generando
	 * una fecha de cierre, y guarda la solucion al problema hecha por el empleado.
	 * pre: el ticket no puede ser null
	 * post: la fecha de cierre del ticket fue asignada
	 * @param ticket: el ticket a cerrar
	 * @param comentario: la solucion del empleado
	 */
	public int darTipo();
	
	/**
	 * Retorna el empleado al que fue asignado.
	 * @return Empleado: el empleado al que fue asignado.
	 */
	public IUsuario darEmpleado();
	

	/**
	 * Informa acerca del estado de ciframiento
	 * del ticket.
	 * @return boolean: si esta cifrado.
	 */
	public boolean estaCifrado();

	/**
	 * Retorna el cliente que creo el ticket.
	 * @return Cliente: el cliente que creo el ticket.
	 */
	public IUsuario darCliente();
	
}

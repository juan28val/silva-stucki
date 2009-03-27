package uniandes.cupi2.helpDesk.interfazMundo;

import java.util.ArrayList;
import java.util.Date;

public interface IUsuario {
	
	public final static int EMPLEADO_QUEJA = 1;
	
	public final static int EMPLEADO_RECLAMO = 2;
	
	public final static int EMPLEADO_SOLICITUD = 3;
	
	public final static int CLIENTE_ESTUDIANTE = 4;
	
	public final static int CLIENTE_PROFESOR = 5;
	
	public final static int CLIENTE_PERSONAL_ADMINISTRATIVO = 6;

	public static final int ADMINISTRADOR = 7;

	public static final String NOMBRE_ADMIN = "admin";
	
	/**
	 * El empleado al que le fue asignado un ticket lo marca como cerrado generando
	 * una  el empleado.
	 * pre: el ticket no puede ser null
	 * post: la fecha de cierre del ticket fue asignada
	 * @param ticket: el ticket a cerrar
	 * @param comentario: la solucion del empleado
	 */
	public String darNombre();

	/**
	 * El empleado al que le fue asignado un ticket lo marca como cerrado generando
	 * una fecha de cierre, y guarda la solucion al problema hecha por el empleado.
	 * pre: el ticket no puede ser null
	 * post: la fecha de cierre del ticket fue asignada
	 * @param ticket: el ticket a cerrar
	 * @param comentario: la solucion del empleado
	 */
	public boolean esEmpleado();
	
	/**
	 * El empleado al que le fue asignado un ticket lo marca como cerrado generando
	 * una fecha de cierre, y guarda la solucion al problema hecha por el empleado.
	 * pre: el ticket no puede ser null
	 * post: la fecha de cierre del ticket fue asignada
	 * @param ticket: el ticket a cerrar
	 * @param comentario: la solucion del empleado
	 */
	public int darSumaCalificacion();
	
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
	public ArrayList<Integer> darListaTickets();

	public int darTipo();
		
}

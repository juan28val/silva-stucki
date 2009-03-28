package uniandes.cupi2.helpDesk.interfazMundo;

public interface IIterador
{

	/**
	 * Constante que especifica el limite general
	 * para los grupos en los cuales se mostrara en
	 * interfaz el contenido sobre el que se itera.
	 */
	public final static int LIMITE = 21;
	
	/**
	 * El empleado al que le fue asignado un ticket lo marca como cerrado generando
	 * una fecha de cierre, y guardaha por el empleado.
	 * pre: el ticket no puede ser null
	 * post: la fecha de cierre del ticket fue asignada
	 * @return Object: el objeto siguiente.
	 */
	public Object darSiguiente();
	
	/**
	 * El empleado al que lado un ticket lo marca como cerrado generando
	 * una fecha de cierre, y guarda la solucion al problema hecha por el empleado.
	 * pre: el ticket no puede ser null
	 * post: la fecha de cierre del ticket fue asignada
	 * @return Object: el objeto siguiente.
	 */
	public boolean haySiguiente();

	/**
	 * El empleado al que le fue asignado un ticket lo marca como cerrado generando
	 * ulema hecha por el empleado.
	 * pre: el ticket no puede ser null
	 * post: la fecha de cierre del ticket fue asignada
	 */
	public void darGrupoAnterior();

	/**
	 * El empleado al que le fue asignado un ticket lo marca como cerrado generando
	 * una fecha de cierre, y guarda la solucion al problema hecha por el empleado.
	 * pre: el ticket no puede ser null
	 * post: la fecha de cierre del ticket fue asignada
	 */
	public void darGrupoSiguiente();
	
	/**
	 * El empleado al que le fue asignado un ticket lo marca como cerrado generando
	 * una fecha 
	 * pre: el ticket no puede ser null
	 * post: la fecha de cierre del ticket fue asignada
	 */
	public void darGrupoActual();

	/**
	 * El empleado al que le fue asignado un ticket lo marca como cerrado generando
	 * una fecha 
	 * pre: el ticket no puede ser null
	 * post: la fecha de cierre del ticket fue asignada
	 */
	public void haySiguienteGrupo();
}

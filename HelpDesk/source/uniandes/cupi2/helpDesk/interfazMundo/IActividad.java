package uniandes.cupi2.helpDesk.interfazMundo;

public interface IActividad {

	/**
	 * Da el numero de veces que se ha ejecutado la actividad.
	 * pre: (no hay precondiciones)
	 * post: (no hay postcondiciones)
	 * @return int: el numero de veces que se ha ejecutado la actividad.
	 */
	public int darNumeroVecesEjecutada();

	/**
	 * Da el tiempo promedio de ejecucion de la actividad.
	 * pre: (no hay precondiciones)
	 * post: (no hay postcondiciones)
	 * @return int: el tiempo promedio de ejecucion de la actividad.
	 */
	public float darPromedioTiempo();
	
	/**
	 * Da el tiempo promedio de ejecucion de la actividad mas todas las que siguen.
	 * pre: (no hay precondiciones)
	 * post: (no hay postcondiciones)
	 * @return int: el tiempo promedio de ejecucion de la actividad  mas todas las que siguen.
	 */
	public float darTiempoPromedioEspera();
	
	/**
	 * Retorna el nombre de la actividad.
	 */
	public String darNombre();

}

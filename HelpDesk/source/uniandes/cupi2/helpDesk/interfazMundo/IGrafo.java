package uniandes.cupi2.helpDesk.interfazMundo;



public interface IGrafo {

	/**
	 * Da una actividad dado su nombre.
	 * pre: (no hay precondiciones)
	 * post: (no hay postcondiciones)
	 * @return IActividad: la actividad con ese nombre o null si no existe.
	 */
	public IActividad darActividad(String nombre);
	
	/**
	 * Da las 2 actividades criticas.
	 * pre: (no hay precondiciones)
	 * post: (no hay postcondiciones)
	 * @return IActividad[]: Da las 2 actividades criticas.
	 */
	public IActividad[] darActividadesCriticas();
	
	/**
	 * Da las 3 actividades que mas se demoran en promedio.
	 * pre: (no hay precondiciones)
	 * post: (no hay postcondiciones)
	 * @return IActividad[]: Da las 2 actividades  que mas se demoran en promedio.
	 */
	public IActividad[] darActividadesMasLentas();
	
	/**
	 * Da un iterador sobre todas las acrividades ordenados descendentemente
	 * por tiempo promedio.
	 * pre: (no hay precondiciones)
	 * post: (no hay postcondiciones)
	 * @return IIterador: el iterador sobre las actividades. 
	 */
	public IIterador darListaActividadesPorTiempo();
}

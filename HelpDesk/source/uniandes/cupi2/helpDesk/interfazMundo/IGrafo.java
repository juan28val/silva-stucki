package uniandes.cupi2.helpDesk.interfazMundo;

public interface IGrafo {

	public IActividad darActividad(String nombre);
	
	public IActividad[] darActividadesCriticas();
	
	public IActividad[] darActividadesMasLentas();
	
	public IIterador darListaActividadesPorTiempo();
	
	public float darTiempoPromedioEspera(String nombre);
	
}

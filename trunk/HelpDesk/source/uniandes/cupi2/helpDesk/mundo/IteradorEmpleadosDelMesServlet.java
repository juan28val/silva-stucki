package uniandes.cupi2.helpDesk.mundo;


import uniandes.cupi2.helpDesk.interfazMundo.IIterador;
import uniandes.cupi2.helpDesk.interfazMundo.IUsuario;

public class IteradorEmpleadosDelMesServlet implements IIterador {
	
	private Empleado empleadoActual;
	
	public IteradorEmpleadosDelMesServlet(Empleado empleado)
	{
		empleadoActual = empleado;
	}

	public void darGrupoAnterior() {
		
	}

	public void darGrupoSiguiente() {
	
	}

	public IUsuario darSiguiente() {
		return empleadoActual;
	}

	public boolean haySiguiente() {

		return empleadoActual!=null;
	}

	public void darGrupoActual() {
		
	}

	public boolean hayGrupoSiguiente() {
		return true;
		// no se usa
		
	}


}

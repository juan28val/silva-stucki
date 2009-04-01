package uniandes.cupi2.helpDesk.mundo;


import uniandes.cupi2.helpDesk.interfazMundo.IIterador;
import uniandes.cupi2.helpDesk.interfazMundo.IUsuario;

public class IteradorEmpleados implements IIterador {
	

	private Empleado empleadoActual;
	
	public IteradorEmpleados(Empleado empleado)
	{
		empleadoActual = empleado;
	}

	public void darGrupoAnterior() {
		// No se usa
	}

	public void darGrupoSiguiente() {
		// No se usa
	}

	public IUsuario darSiguiente() {
		Empleado temp = empleadoActual;
		empleadoActual = empleadoActual.darSiguiente();
		return temp;
	}

	public boolean haySiguiente() {

		return empleadoActual!=null;
	}

	public void darGrupoActual() {
		// No se usa
		
	}

	public boolean hayGrupoSiguiente() {
		return true;
		// no se usa		
	}
}

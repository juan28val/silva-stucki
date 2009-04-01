package uniandes.cupi2.helpDesk.mundo;

import uniandes.cupi2.helpDesk.interfazMundo.IIterador;
import uniandes.cupi2.helpDesk.interfazMundo.IUsuario;

public class IteradorClientes implements IIterador {

	private Cliente clienteActual;
	
	public IteradorClientes(Cliente cliente)
	{
		clienteActual = cliente;
	}

	public void darGrupoAnterior() {
		// No se usa
	}

	public void darGrupoSiguiente() {
		// No se usa
	}

	public IUsuario darSiguiente() {
		Cliente temp = clienteActual;
		clienteActual = clienteActual.darSiguiente();
		return temp;
	}

	public boolean haySiguiente() {

		return clienteActual!=null;
	}

	public void darGrupoActual() {
		// No se usa
		
	}

	public boolean hayGrupoSiguiente() {
		return true;
		// no se usa		
	}
}
package uniandes.cupi2.helpDesk.mundo;

import uniandes.cupi2.helpDesk.interfazMundo.IIterador;
import uniandes.cupi2.helpDesk.interfazMundo.IUsuario;

public class IteradorClientes implements IIterador {


	public static final int LIMITE = 20;
	private Cliente primerClienteGrupoActual;
	private Cliente clienteActual;
	private int pos;
	private Cliente primerClienteGrupoAnterior;
	private Cliente primerClienteGrupoSiguiente;
	
	public IteradorClientes(Cliente cliente)
	{
		clienteActual = cliente;
		primerClienteGrupoActual = cliente;
		primerClienteGrupoAnterior = cliente;
		primerClienteGrupoSiguiente = darClienteSiguienteGrupo();
		pos = 0;
	}

	private Cliente darClienteSiguienteGrupo()
	{
		int cont = 0;
		Cliente temp = primerClienteGrupoActual;
		
		while( cont<LIMITE && temp!=null)
		{
			if(temp.darSiguienteAtendido()==null)
				return primerClienteGrupoActual;
				
			temp = temp.darSiguienteAtendido();
			cont++;
		}
		
		return temp;
	}
	
	private Cliente darClienteAnteriorGrupo()
	{
		int cont = 0;
		Cliente temp = primerClienteGrupoActual;
		
		while( cont<LIMITE && temp!=null)
		{
			if(temp.darAnteriorAtendido()==null)
				return primerClienteGrupoActual;
				
			temp = temp.darAnteriorAtendido();
			cont++;
		}
		
		return temp;
	}

	public void darGrupoAnterior() {
		if(primerClienteGrupoActual != primerClienteGrupoAnterior)
			primerClienteGrupoSiguiente = primerClienteGrupoActual;
		primerClienteGrupoActual = primerClienteGrupoAnterior;
		primerClienteGrupoAnterior = darClienteAnteriorGrupo();
		clienteActual = primerClienteGrupoActual;
		pos = 0;
		
	}

	public void darGrupoSiguiente() {
		if(primerClienteGrupoActual != primerClienteGrupoSiguiente)
			primerClienteGrupoAnterior = primerClienteGrupoActual;
		primerClienteGrupoActual = primerClienteGrupoSiguiente;
			primerClienteGrupoSiguiente = darClienteSiguienteGrupo();
		clienteActual = primerClienteGrupoActual;
		pos = 0;
	}

	public IUsuario darSiguiente() {
		Cliente temp = clienteActual;
		clienteActual = clienteActual.darSiguienteAtendido();
		pos++;
		return temp;
	}

	public boolean haySiguiente() {

		return pos<LIMITE && clienteActual!=null;
	}

	public void darGrupoActual() {
		pos = 0;
		clienteActual = primerClienteGrupoActual;
		
	}

	public boolean hayGrupoSiguiente() {
		return true;
		// no se usa
		
	}


}

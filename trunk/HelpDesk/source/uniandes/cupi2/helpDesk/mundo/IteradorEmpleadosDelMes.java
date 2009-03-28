package uniandes.cupi2.helpDesk.mundo;


import uniandes.cupi2.helpDesk.interfazMundo.IIterador;
import uniandes.cupi2.helpDesk.interfazMundo.IUsuario;

public class IteradorEmpleadosDelMes implements IIterador {
	

	public static final int LIMITE = 17;
	private Empleado primerEmpleadoGrupoActual;
	private Empleado empleadoActual;
	private int pos;
	private Empleado primerEmpleadoGrupoAnterior;
	private Empleado primerEmpleadoGrupoSiguiente;
	
	public IteradorEmpleadosDelMes(Empleado empleado)
	{
		empleadoActual = empleado;
		primerEmpleadoGrupoActual = empleado;
		primerEmpleadoGrupoAnterior = empleado;
		primerEmpleadoGrupoSiguiente = darEmpleadoSiguienteGrupo();
		pos = 0;
	}

	private Empleado darEmpleadoSiguienteGrupo()
	{
		int cont = 0;
		Empleado temp = primerEmpleadoGrupoActual;
		
		while( cont<LIMITE && temp!=null )
		{
			if( temp.darSiguienteDelMes()==null)
				return primerEmpleadoGrupoActual;
				
			temp = temp.darSiguienteDelMes();
			cont++;
		}
		
		return temp;
	}
	
	private Empleado darEmpleadoAnteriorGrupo()
	{
		int cont = 0;
		Empleado temp = primerEmpleadoGrupoActual;
		
		while( cont<LIMITE && temp != null)
		{
			if(temp.darAnteriorDelMes()==null)
				return primerEmpleadoGrupoActual;
				
			temp = temp.darAnteriorDelMes();
			cont++;
		}
		
		return temp;
	}

	public void darGrupoAnterior() {
		if(primerEmpleadoGrupoActual != primerEmpleadoGrupoAnterior)
			primerEmpleadoGrupoSiguiente = primerEmpleadoGrupoActual;
		primerEmpleadoGrupoActual = primerEmpleadoGrupoAnterior;
		primerEmpleadoGrupoAnterior = darEmpleadoAnteriorGrupo();
		empleadoActual = primerEmpleadoGrupoActual;
		pos = 0;
		
	}

	public void darGrupoSiguiente() {
		if(primerEmpleadoGrupoActual != primerEmpleadoGrupoSiguiente)
			primerEmpleadoGrupoAnterior = primerEmpleadoGrupoActual;
		primerEmpleadoGrupoActual = primerEmpleadoGrupoSiguiente;
		primerEmpleadoGrupoSiguiente = darEmpleadoSiguienteGrupo();
		empleadoActual = primerEmpleadoGrupoActual;
		pos = 0;
	}

	public IUsuario darSiguiente() {
		Empleado temp = empleadoActual;
		empleadoActual = empleadoActual.darSiguienteDelMes();
		pos++;
		return temp;
	}

	public boolean haySiguiente() {

		return pos<LIMITE && empleadoActual!=null;
	}

	public void darGrupoActual() {
		pos = 0;
		empleadoActual = primerEmpleadoGrupoActual;
		
	}

	public void haySiguienteGrupo() {
		// no se usa
		
	}


}

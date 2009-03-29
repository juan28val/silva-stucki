package uniandes.cupi2.helpDesk.mundo;

import java.util.ArrayList;

import uniandes.cupi2.collections.tablaHashing.tablaHashingDinamica.TablaHashingDinamica;
import uniandes.cupi2.helpDesk.interfazMundo.IIterador;
import uniandes.cupi2.helpDesk.interfazMundo.ITicket;
import uniandes.cupi2.helpDesk.interfazMundo.IUsuario;

public class IteradorTickets implements IIterador {

	
	private IUsuario usuarioActual;
	private int pos;
	private TablaHashingDinamica<Integer, Ticket> tabla;
	private IIterador listaUsuarios;
	private ArrayList<Integer> listaTicketsUsuarioActual;

	public IteradorTickets(IUsuario usuarioActual,IIterador listaUsuarios, TablaHashingDinamica<Integer, Ticket> tabla) {
		this.usuarioActual = usuarioActual;
		this.listaUsuarios = listaUsuarios;
		listaTicketsUsuarioActual = new ArrayList<Integer>();
		pos = 0;
		this.tabla = tabla;
	}

	public void darGrupoAnterior() {
		// no se usa
	}

	public void darGrupoSiguiente() {
		pos = 0;
		listaTicketsUsuarioActual = new ArrayList<Integer>();
		
		if(usuarioActual.esEmpleado() )
		{
			Cliente usuario = (Cliente) listaUsuarios.darSiguiente();
			for(int i=0;i<usuarioActual.darListaTickets().size();i++)
			{
				if(tabla.dar(usuarioActual.darListaTickets().get(i)).darClient().darId()==usuario.darId())
				{
					listaTicketsUsuarioActual.add(usuarioActual.darListaTickets().get(i));
				}
			}			
		}
		else
		{
			Empleado usuario = (Empleado)listaUsuarios.darSiguiente();
			for(int i=0;i<usuarioActual.darListaTickets().size();i++)
			{
				if(tabla.dar(usuarioActual.darListaTickets().get(i)).darEmpleado().darId()==usuario.darId())
				{
					listaTicketsUsuarioActual.add(usuarioActual.darListaTickets().get(i));
				}
			}			
		}
	}

	public ITicket darSiguiente() {
		pos++;
		return tabla.dar(listaTicketsUsuarioActual.get(pos-1));
	}

	public boolean haySiguiente() {
		return pos<listaTicketsUsuarioActual.size();
	}

	public void darGrupoActual() {
		// no se usa
		
	}

	public boolean hayGrupoSiguiente() {
		return listaUsuarios.haySiguiente();		
	}


}

package uniandes.cupi2.helpDesk.mundo;

import uniandes.cupi2.collections.tablaHashing.tablaHashingDinamica.TablaHashingDinamica;
import uniandes.cupi2.helpDesk.interfazMundo.IIterador;
import uniandes.cupi2.helpDesk.interfazMundo.ITicket;
import uniandes.cupi2.helpDesk.interfazMundo.IUsuario;

public class IteradorTickets implements IIterador {

	
	private IUsuario usuarioActual;
	private int pos;
	private int posGrupo;
	private TablaHashingDinamica<Integer, Ticket> tabla;

	public IteradorTickets(IUsuario usuarioActual, TablaHashingDinamica<Integer, Ticket> tabla) {
		this.usuarioActual = usuarioActual;
		pos = 0;
		posGrupo = 0;
		this.tabla = tabla;
	}

	public void darGrupoAnterior() {
		
		if( posGrupo >= LIMITE )
			posGrupo = posGrupo-LIMITE;
		pos = posGrupo;
	}

	public void darGrupoSiguiente() {
		if( posGrupo + LIMITE < usuarioActual.darListaTickets().size() )
			posGrupo = posGrupo+LIMITE;
		pos = posGrupo;
		
	}

	public ITicket darSiguiente() {
		pos++;
		return tabla.dar(usuarioActual.darListaTickets().get(pos-1));
	}

	public boolean haySiguiente() {

		return pos<LIMITE + posGrupo && pos < usuarioActual.darListaTickets().size();
	}

	public void darGrupoActual() {
		pos= posGrupo;
		
	}

	public boolean hayGrupoSiguiente() {
		return true;
		// no se usa
		
	}


}

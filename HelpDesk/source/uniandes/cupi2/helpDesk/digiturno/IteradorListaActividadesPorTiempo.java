package uniandes.cupi2.helpDesk.digiturno;

import java.util.ArrayList;

import uniandes.cupi2.collections.iterador.Iterador;
import uniandes.cupi2.collections.listaEncadenadaOrdenada.ListaEncadenadaOrdenada;
import uniandes.cupi2.collections.tablaHashing.tablaHashingDinamica.TablaHashingDinamica;
import uniandes.cupi2.helpDesk.interfazMundo.IActividad;
import uniandes.cupi2.helpDesk.interfazMundo.IIterador;

public class IteradorListaActividadesPorTiempo implements IIterador {

	private Iterador<Actividad> vertice;

	public IteradorListaActividadesPorTiempo(TablaHashingDinamica<String, Actividad> tablaActividades, ArrayList<String> listaActividadesSinPadre, Grafo grafo) {

	}

	public IteradorListaActividadesPorTiempo(ListaEncadenadaOrdenada<Actividad> listaVertices, Grafo grafo) {
		vertice = listaVertices.darIterador();
	}

	public void darGrupoAnterior() {
		// no se usa
	}

	public void darGrupoSiguiente() {
		// no se usa
	}

	public IActividad darSiguiente() {
		return vertice.darSiguiente();
	}

	public boolean haySiguiente() {

		return vertice.haySiguiente();
	}

	public void darGrupoActual() {
		// no se usa
	}

	public boolean hayGrupoSiguiente() {
		return false;		
	}
}
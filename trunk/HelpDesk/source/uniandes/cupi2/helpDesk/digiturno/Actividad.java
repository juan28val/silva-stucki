package uniandes.cupi2.helpDesk.digiturno;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import uniandes.cupi2.collections.tablaHashing.tablaHashingDinamica.TablaHashingDinamica;
import uniandes.cupi2.helpDesk.interfazMundo.IActividad;

public class Actividad extends Vertice<String> implements IActividad{

	/**
	 * 
	 */
	private static final long serialVersionUID = 364546531L;

	boolean marcado;
	
	float promedioTiempo;
	
	int numeroVecesEjecutada;
	
	public Actividad(String nombreActividad, float promedioTiempo, int numeroVecesEjecutada)
	{
		this.elem = nombreActividad;
		this.promedioTiempo = promedioTiempo;
		this.numeroVecesEjecutada = numeroVecesEjecutada;		
		marcado = false;
	}
	
	public void marcar(boolean marcado)
	{
		this.marcado = marcado;
	}
	
	public int darNumeroVecesEjecutada()
	{
		return numeroVecesEjecutada;
	}

	public float darPromedioTiempo()
	{
		return promedioTiempo;
	}
	
	public void guardar(Element elementoActividades, Document documento, TablaHashingDinamica<String, Actividad> tablaActividades)
	{
		marcado = true;
		
		Element e = documento.createElement("actividad");
		e.setAttribute("nombre", String.valueOf(elem));
		e.setAttribute("promedioTiempo", String.valueOf(promedioTiempo));
		e.setAttribute("numeroVecesEjecutada", String.valueOf(numeroVecesEjecutada));
	
		for(int i=0; i<hijos.size(); i++)
		{
			Element t = documento.createElement("hijo");
			t.setAttribute("nombre", hijos.get(i));
			e.appendChild(t);
		}
		elementoActividades.appendChild(e);
		for(int i=0; i<hijos.size(); i++)
		{
			if(!tablaActividades.dar(hijos.get(i)).estaMarcado())
				tablaActividades.dar(hijos.get(i)).guardar(elementoActividades, documento,tablaActividades);
	
		}
	}

	private boolean estaMarcado() {
		return marcado;
	}
}

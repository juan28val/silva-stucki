package uniandes.cupi2.helpDesk.digiturno;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import uniandes.cupi2.collections.tablaHashing.tablaHashingDinamica.TablaHashingDinamica;
import uniandes.cupi2.helpDesk.digiturno.Vertice;
import uniandes.cupi2.helpDesk.interfazMundo.IActividad;

public class Actividad extends Vertice<String> implements IActividad, Comparable<Actividad>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 364546531L;
	
	private long promedioTiempo;
	
	private int numeroVecesEjecutada;

	TablaHashingDinamica<String, Actividad> tablaActividades;
	
	public Actividad(String nombreActividad, long promedioTiempo, int numeroVecesEjecutada, TablaHashingDinamica<String, Actividad> tablaVertices)
	{
		hijos = new ArrayList<String>();
		this.elem = nombreActividad;
		this.promedioTiempo = promedioTiempo;
		this.numeroVecesEjecutada = numeroVecesEjecutada;
		this.tablaActividades = tablaVertices;
	}
	
	public int darNumeroVecesEjecutada()
	{
		return numeroVecesEjecutada;
	}

	public float darPromedioTiempo()
	{
		return promedioTiempo;
	}
	
	public void guardar(Element elementoActividades, Document documento)
	{
		
		Element e = documento.createElement("actividad");
		e.setAttribute("nombre", String.valueOf(elem));
		e.setAttribute("promedioTiempo", String.valueOf(promedioTiempo));
		e.setAttribute("numeroVecesEjecutada", String.valueOf(numeroVecesEjecutada));
	
		for(int i=0; i<hijos.size(); i++)
		{
			Element t = documento.createElement("arcoCon");
			t.setAttribute("nombre", hijos.get(i));
			e.appendChild(t);
		}
		
		elementoActividades.appendChild(e);
	}

	public void agregarDato(long tiempo)
	{
		promedioTiempo = (promedioTiempo*numeroVecesEjecutada +tiempo)/(numeroVecesEjecutada+1);
		numeroVecesEjecutada++;
		
	}

	public float darTiempoPromedioEspera() {

		float promedio = 0;
		int numVecesEjecutado = 0;
		
		for(int i=0;i<hijos.size();i++)
		{
			promedio += tablaActividades.dar(hijos.get(i)).darTiempoPromedioEspera()*tablaActividades.dar(hijos.get(i)).darNumeroVecesEjecutada();
			numVecesEjecutado += tablaActividades.dar(hijos.get(i)).darNumeroVecesEjecutada();
		}
		
		return promedioTiempo + (numVecesEjecutado!=0 ? promedio/numVecesEjecutado : 0);
	}

	public boolean equals(Actividad otra)
	{
		return otra.darId().equals(elem);
	}
	
	public int compareTo(Actividad otro) {
		return otro.darPromedioTiempo()>promedioTiempo?1:otro.darPromedioTiempo()>promedioTiempo?0:-1;
	}
}

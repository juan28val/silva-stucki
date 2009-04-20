package uniandes.cupi2.helpDesk.digiturno;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import uniandes.cupi2.collections.tablaHashing.tablaHashingDinamica.TablaHashingDinamica;
import uniandes.cupi2.helpDesk.digiturno.Vertice;
import uniandes.cupi2.helpDesk.interfazMundo.IActividad;

public class Actividad extends Vertice<String> implements IActividad{

	/**
	 * 
	 */
	private static final long serialVersionUID = 364546531L;

	private boolean marcado;
	
	private float promedioTiempo;
	
	private int numeroVecesEjecutada;

	
	public Actividad(String nombreActividad, float promedioTiempo, int numeroVecesEjecutada)
	{
		hijos = new ArrayList<String>();
		this.elem = nombreActividad;
		this.promedioTiempo = promedioTiempo;
		this.numeroVecesEjecutada = numeroVecesEjecutada;		
		marcado = false;
	}
	
	public Actividad(String nombreActividad) 
	{
		hijos = new ArrayList<String>();
		this.elem = nombreActividad;
		promedioTiempo = 0;
		numeroVecesEjecutada = 0;		
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
			Element t = documento.createElement("arcoCon");
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

	public void quitarMarcas(TablaHashingDinamica<String, Actividad> tablaActividades) {

		marcado = true;
		
		for(int i=0; i<hijos.size(); i++)
		{
			tablaActividades.dar(hijos.get(i)).quitarMarcas(tablaActividades);
	
		}
	}

	public void agregarDato(float tiempo)
	{
		promedioTiempo = (promedioTiempo*numeroVecesEjecutada +tiempo)/(numeroVecesEjecutada+1);
		numeroVecesEjecutada++;
		
	}

	public float darTiempoPromedioEspera(TablaHashingDinamica<String, Actividad> tablaActividades) {

		float promedio = 0;
		int numVecesEjecutado = 0;
		
		for(int i=0;i<hijos.size();i++)
		{
			promedio += tablaActividades.dar(hijos.get(i)).darTiempoPromedioEspera(tablaActividades)*tablaActividades.dar(hijos.get(i)).darNumeroVecesEjecutada();
			numVecesEjecutado += tablaActividades.dar(hijos.get(i)).darNumeroVecesEjecutada();
		}
		
		return promedioTiempo+promedio/numVecesEjecutado;
	}
}

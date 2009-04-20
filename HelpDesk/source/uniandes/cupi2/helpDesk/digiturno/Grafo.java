package uniandes.cupi2.helpDesk.digiturno;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import uniandes.cupi2.collections.tablaHashing.tablaHashingDinamica.TablaHashingDinamica;
import uniandes.cupi2.helpDesk.digiturno.Actividad;
import uniandes.cupi2.helpDesk.interfazMundo.IActividad;
import uniandes.cupi2.helpDesk.interfazMundo.IGrafo;
import uniandes.cupi2.helpDesk.interfazMundo.IIterador;

public class Grafo implements IGrafo {

	private TablaHashingDinamica<String, Actividad> tablaActividades;
	
	private ArrayList<String> listaActividadesSinPadre;

	public Grafo()
	{
		tablaActividades = new TablaHashingDinamica<String, Actividad>();
		listaActividadesSinPadre = new ArrayList<String>();
	}
	
	public void agregarVertice(Actividad actividad) throws Exception
	{
		if(tablaActividades.dar(actividad.darId())==null)
		{
			tablaActividades.agregar(actividad.darId(), actividad);
			listaActividadesSinPadre.add(actividad.darId());
		}
		else
		{
			throw new Exception("La actividad ya existe.");
		}
	}
	
	public void agregarArco(String nombrePadre, String nombreHijo) throws Exception
	{
		if(tablaActividades.dar(nombrePadre)!=null)
		{
			tablaActividades.dar(nombrePadre).agregarHijo(nombreHijo);
			for(int i=0;i<listaActividadesSinPadre.size();i++)
			{
				if( listaActividadesSinPadre.get(i).equals(nombreHijo) )
					listaActividadesSinPadre.remove(i);
			}
		}
		else
		{
			throw new Exception("Una de las actividades no existe.");
		}
	}

	public IActividad[] darActividadesCriticas() {
		// TODO Auto-generated method stub
		return null;
	}

	public IActividad darActividad(String nombre) {
		return tablaActividades.dar(nombre);
	}

	public IActividad[] darActividadesMasLentas() {
		// TODO Auto-generated method stub
		return null;
	}

	public IIterador darListaActividadesPorTiempo() {
		// TODO Auto-generated method stub
		return null;
	}

	public float darTiempoPromedioEspera() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void agregarDatoAActividad(String nombre, float tiempo)
	{
		tablaActividades.dar(nombre).agregarDato(tiempo);	
	}
	
	public void guardar(Element elementoActividades, Document documento) {
		
		for(int i=0;i<listaActividadesSinPadre.size();i++)
		{
			tablaActividades.dar(listaActividadesSinPadre.get(i)).guardar(elementoActividades, documento, tablaActividades);
		}
		quitarMarcas();
	}

	private void quitarMarcas() {
		for(int i=0;i<listaActividadesSinPadre.size();i++)
		{
			tablaActividades.dar(listaActividadesSinPadre.get(i)).quitarMarcas(tablaActividades);
		}
	}
}
package uniandes.cupi2.helpDesk.digiturno;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import uniandes.cupi2.collections.iterador.Iterador;
import uniandes.cupi2.collections.listaEncadenadaOrdenada.ListaEncadenadaOrdenada;
import uniandes.cupi2.collections.tablaHashing.tablaHashingDinamica.TablaHashingDinamica;
import uniandes.cupi2.helpDesk.digiturno.Actividad;
import uniandes.cupi2.helpDesk.interfazMundo.IActividad;
import uniandes.cupi2.helpDesk.interfazMundo.IGrafo;
import uniandes.cupi2.helpDesk.interfazMundo.IIterador;

public class Grafo implements IGrafo {

	private TablaHashingDinamica<String, Actividad> tablaVertices;
	
	private ArrayList<String> listaVerticesSinPadre;

	private ListaEncadenadaOrdenada<Actividad> caminoPorTiempo;
	
	public Grafo()
	{
		tablaVertices = new TablaHashingDinamica<String, Actividad>();
		listaVerticesSinPadre = new ArrayList<String>();
		caminoPorTiempo = new ListaEncadenadaOrdenada<Actividad>();
	}
	
	public void agregarVertice(Actividad actividad) throws Exception
	{
		if(tablaVertices.dar(actividad.darId())==null)
		{
			tablaVertices.agregar(actividad.darId(), actividad);
			listaVerticesSinPadre.add(actividad.darId());
			caminoPorTiempo.insertar(actividad);
		}
		else
		{
			throw new Exception("La actividad ya existe.");
		}
	}
	
	public void agregarArco(String nombrePadre, String nombreHijo) throws Exception
	{
		if(tablaVertices.dar(nombrePadre)!=null)
		{
			tablaVertices.dar(nombrePadre).agregarHijo(nombreHijo);
			for(int i=0;i<listaVerticesSinPadre.size();i++)
			{
				if( listaVerticesSinPadre.get(i).equals(nombreHijo) )
					listaVerticesSinPadre.remove(i);
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
		return tablaVertices.dar(nombre);
	}

	public IActividad[] darActividadesMasLentas() {
		
		IActividad[] masLentas = new IActividad[3];
		IIterador it = darListaActividadesPorTiempo();
		for(int i=0; i<3 && it.haySiguiente(); i++)
		{
			masLentas[i] = (IActividad)it.darSiguiente();
		}
		
		return masLentas;
	}
	
	public IIterador darListaActividadesPorTiempo() {
		return new IteradorListaActividadesPorTiempo(caminoPorTiempo, this);
	}
	
	public float darTiempoPromedioEspera(String nombre) {
		return tablaVertices.dar(nombre).darTiempoPromedioEspera(tablaVertices);
	}

	public void agregarDatoAActividad(String nombre, float tiempo)
	{
		tablaVertices.dar(nombre).agregarDato(tiempo);	
	}
	
	public void guardar(Element elementoActividades, Document documento) {
		
		for(int i=0;i<listaVerticesSinPadre.size();i++)
		{
			tablaVertices.dar(listaVerticesSinPadre.get(i)).guardar(elementoActividades, documento, tablaVertices);
		}
		quitarMarcas();
	}

	private void quitarMarcas() {
		for(int i=0;i<listaVerticesSinPadre.size();i++)
		{
			tablaVertices.dar(listaVerticesSinPadre.get(i)).quitarMarcas(tablaVertices);
		}
	}
}
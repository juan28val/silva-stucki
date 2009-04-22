package uniandes.cupi2.helpDesk.digiturno;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import uniandes.cupi2.collections.listaEncadenadaOrdenada.ListaEncadenadaOrdenada;
import uniandes.cupi2.collections.listaEncadenadaOrdenada.NoExisteException;
import uniandes.cupi2.collections.tablaHashing.tablaHashingDinamica.TablaHashingDinamica;
import uniandes.cupi2.helpDesk.digiturno.Actividad;
import uniandes.cupi2.helpDesk.interfazMundo.IActividad;
import uniandes.cupi2.helpDesk.interfazMundo.IGrafo;
import uniandes.cupi2.helpDesk.interfazMundo.IIterador;

public class GrafoAciclico implements IGrafo {

	private TablaHashingDinamica<String, Actividad> tablaVertices;
	
	private ArrayList<String> listaVerticesSinPadre;

	private ListaEncadenadaOrdenada<Actividad> caminoPorTiempo;
	
	private String[] verticesCriticos;
	
	public GrafoAciclico()
	{
		tablaVertices = new TablaHashingDinamica<String, Actividad>();
		listaVerticesSinPadre = new ArrayList<String>();
		caminoPorTiempo = new ListaEncadenadaOrdenada<Actividad>();
		verticesCriticos = new String[2];
	}
	
	public void agregarVertice(Actividad actividad) throws Exception
	{
		if(tablaVertices.dar(actividad.darId())==null)
		{
			tablaVertices.agregar(actividad.darId(), actividad);
			listaVerticesSinPadre.add(actividad.darId());
			caminoPorTiempo.insertar(actividad);
			if(verticesCriticos[0]==null)
				verticesCriticos[0]=actividad.darId();
			else if(verticesCriticos[1]==null)
				verticesCriticos[1]=actividad.darId();
			
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

		IActividad[] actividades = new IActividad[2];
		
		if(verticesCriticos[1]!=null)
		{
			actividades[0] = tablaVertices.dar(verticesCriticos[0]).darNumeroVecesEjecutada()>tablaVertices.dar(verticesCriticos[1]).darNumeroVecesEjecutada()?tablaVertices.dar(verticesCriticos[0]):tablaVertices.dar(verticesCriticos[1]);
			actividades[1] = tablaVertices.dar(verticesCriticos[0]).darNumeroVecesEjecutada()>tablaVertices.dar(verticesCriticos[1]).darNumeroVecesEjecutada()?tablaVertices.dar(verticesCriticos[1]):tablaVertices.dar(verticesCriticos[0]);		            
		}
		else if(verticesCriticos[0]!=null)
			actividades[0] = tablaVertices.dar(verticesCriticos[0]);
		
		return actividades;
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
		
		try 
		{
			caminoPorTiempo.eliminar(tablaVertices.dar(nombre));
			caminoPorTiempo.insertar(tablaVertices.dar(nombre));
		} 
		catch (NoExisteException e) {
			
			e.printStackTrace();
		}
		
		if(tablaVertices.dar(verticesCriticos[0]).darNumeroVecesEjecutada()<tablaVertices.dar(nombre).darPromedioTiempo())
		{
			verticesCriticos[1] = verticesCriticos[0];
			verticesCriticos[0] = nombre;
		}
		else if(tablaVertices.dar(verticesCriticos[1]).darNumeroVecesEjecutada()<tablaVertices.dar(nombre).darPromedioTiempo()) 
			verticesCriticos[1] = nombre;
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
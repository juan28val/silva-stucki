package uniandes.cupi2.helpDesk.digiturno;

import java.util.ArrayList;

import uniandes.cupi2.collections.grafo.*;

public class Vertice<K> implements IVertice<K> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5764764L;

	protected K elem;
	
	protected ArrayList<K> hijos;
	
	public ArrayList<K> darListaHijos() {
		return hijos;
	}
	
	public K darId() {
		return elem;
	}

	public void agregarHijo(K nombreHijo)
	{
		for(int i=0;i<hijos.size();i++)
		{
			if(hijos.get(i).equals(nombreHijo))
				return;
		}
		
		hijos.add(nombreHijo);
	}
}
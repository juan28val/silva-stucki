package uniandes.cupi2.helpDesk.mundo;

import java.util.ArrayList;
import java.util.Date;

import uniandes.cupi2.collections.arbol.arbol2_3.Arbol2_3;
import uniandes.cupi2.collections.arbol.arbol2_3.Nodo2_3;
import uniandes.cupi2.helpDesk.interfazMundo.IIncidente;
import uniandes.cupi2.helpDesk.interfazMundo.IIterador;

public class IteradorIncidentes implements IIterador {

	ArrayList<IIncidente> listaIncidentes;
	private int pos;
	private int posGrupo;
	
	public IteradorIncidentes( boolean antes, Date fecha, Arbol2_3<Incidente> arbol ) {
		
		listaIncidentes = new ArrayList<IIncidente>();
		pos = 0;
		posGrupo = 0;
		
		if( arbol.darRaiz()!=null )
			if( antes )
			{
				if( arbol.darRaiz().darHijoIzq()!=null )
					agregarALista( antes, listaIncidentes, arbol.darRaiz().darHijoIzq(), fecha );
				if( ((IIncidente)arbol.darRaiz().darRaizIzq()).darFecha().before(fecha) )
					listaIncidentes.add((IIncidente)arbol.darRaiz().darRaizIzq());
				if( arbol.darRaiz().darHijoCent()!=null )
					agregarALista( antes, listaIncidentes, arbol.darRaiz().darHijoCent(), fecha );
				if(arbol.darRaiz().darRaizDer() != null && ((IIncidente)arbol.darRaiz().darRaizDer()).darFecha().before(fecha))
				{
					listaIncidentes.add((IIncidente)arbol.darRaiz().darRaizDer());
					if( arbol.darRaiz().darHijoDer()!=null )
						agregarALista( antes, listaIncidentes, arbol.darRaiz().darHijoDer(), fecha );
				}
			}
			else
			{
				if(((IIncidente)arbol.darRaiz().darRaizIzq()).darFecha().after(fecha))
				{	
					if( arbol.darRaiz().darHijoIzq()!=null )
						agregarALista( antes, listaIncidentes, arbol.darRaiz().darHijoIzq(), fecha );
						listaIncidentes.add((IIncidente)arbol.darRaiz().darRaizIzq());
				}
				if( arbol.darRaiz().darHijoCent()!=null )
					agregarALista( antes, listaIncidentes, arbol.darRaiz().darHijoCent(), fecha );
				if(arbol.darRaiz().darRaizDer() != null && ((IIncidente)arbol.darRaiz().darRaizDer()).darFecha().after(fecha))
					listaIncidentes.add((IIncidente)arbol.darRaiz().darRaizDer());
				if( arbol.darRaiz().darHijoDer()!=null )
					agregarALista( antes, listaIncidentes, arbol.darRaiz().darHijoDer(), fecha );
		}
	}
	
	private void agregarALista( boolean antes, ArrayList<IIncidente> lista, Nodo2_3<Incidente> nodo, Date fecha ) {
		
		if( antes )
		{
			if( nodo.darHijoIzq()!=null )
				agregarALista( antes, listaIncidentes, nodo.darHijoIzq(), fecha );
		
			if( ((IIncidente)nodo.darRaizIzq()).darFecha().before(fecha) )
				listaIncidentes.add((IIncidente)nodo.darRaizIzq());

			if( nodo.darHijoCent()!=null )
				agregarALista( antes, listaIncidentes, nodo.darHijoCent(), fecha );
			
			if(nodo.darRaizDer() != null && nodo.darRaizDer().darFecha().before(fecha))
			{
				listaIncidentes.add(nodo.darRaizDer());
				if(nodo.darHijoDer() != null)
					agregarALista(antes, listaIncidentes, nodo.darHijoDer(), fecha);
			}
		}
		else
		{
			if( ((IIncidente)nodo.darRaizIzq()).darFecha().after(fecha) )
			{
				if( nodo.darHijoIzq()!=null )
					agregarALista( antes, listaIncidentes, nodo.darHijoIzq(), fecha );
		
				listaIncidentes.add((IIncidente)nodo.darRaizIzq());
				
			}
			if( nodo.darHijoCent()!=null )
				agregarALista( antes, listaIncidentes, nodo.darHijoCent(), fecha );
			
			if( nodo.darRaizDer() != null && ((IIncidente)nodo.darRaizDer()).darFecha().after(fecha) )
					listaIncidentes.add((IIncidente)nodo.darRaizDer());
		
			if( nodo.darHijoDer()!=null )
				agregarALista( antes, listaIncidentes, nodo.darHijoDer(), fecha );
		}
	}
		
	public void darGrupoAnterior() {
		if( posGrupo >= LIMITE )
			posGrupo = posGrupo-LIMITE;
		pos = posGrupo;
	}

	public void darGrupoSiguiente() {
		if( posGrupo + LIMITE < listaIncidentes.size() )
			posGrupo = posGrupo+LIMITE;
		pos = posGrupo;
	}

	public IIncidente darSiguiente() {
		pos++;
		return listaIncidentes.get(pos-1);
	}

	public boolean haySiguiente() {

		return pos<LIMITE + posGrupo && pos < listaIncidentes.size();
	}

	public void darGrupoActual() {
		pos = posGrupo;
	}

	public boolean hayGrupoSiguiente() {
		return true;
		// no se usa
		
	}

}

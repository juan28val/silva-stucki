package uniandes.cupi2.helpDesk.test;

import uniandes.cupi2.helpDesk.digiturno.*;
import uniandes.cupi2.helpDesk.interfazMundo.IIterador;

import junit.framework.TestCase;

/**
 * Esta es la clase usada para verificar que los métodos de la clase HelpDesk estén correctamente implementados
 */
public class GrafoAciclicoTest extends TestCase
{
    //-----------------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------------

    /**
     * Es la clase donde se harán las pruebas
     */
    private GrafoAciclico grafo;

    //-----------------------------------------------------------------
    // Métodos
    //-----------------------------------------------------------------

    /**
     * Construye una nueva HelpDesk vacía
     *  
     */
    private void setupEscenario1( )
    {
        grafo = new GrafoAciclico();
    }
    
    private void setupEscenario2( ) throws Exception
    {
    	setupEscenario1();

    	grafo.agregarVertice("actividad1",10,5);
    	grafo.agregarVertice("actividad2",3,6);
    	grafo.agregarVertice("actividad3",11,7);
    	grafo.agregarVertice("actividad4",1,8);
    }
    
    private void setupEscenario3( ) throws Exception
    {
    	setupEscenario2();
    	
    	grafo.agregarArco("actividad1", "actividad2");
    	grafo.agregarArco("actividad2", "actividad3");
    	grafo.agregarArco("actividad1", "actividad3");
    	grafo.agregarArco("actividad3", "actividad4");
    }
    
    /**
     * Prueba que se inisialize bien el grafo
     */
    public void testEscenario1( )
    {
    	setupEscenario1();
    	
    	assertFalse("El constructor no inicializo el grafo sin vertices.",grafo.darListaActividadesPorTiempo().haySiguiente());
    	assertTrue("El constructor no inicializo el grafo sin vertices.",grafo.darActividadesCriticas()[0]==null && grafo.darActividadesCriticas()[1]==null);
    	assertTrue("El constructor no inicializo el grafo sin vertices.",grafo.darActividadesMasLentas()[0]==null && grafo.darActividadesMasLentas()[1]==null && grafo.darActividadesMasLentas()[2]==null);
    }

    
    /**
     * Prueba que se agregue bien los vertices.
     */
    public void testAgregarVertices( )
    {
    	try 
    	{
			setupEscenario2();
		} 
    	catch (Exception e) 
    	{
    		fail("Fallo al agregar un vertice no repetido.");
		}
    	
    	try 
    	{
    		grafo.agregarVertice("actividad1",2,3);
    		fail("No salio una excepcion al intentar insertar un vertice que ya existe.");
		} 
    	catch (Exception e) 
    	{
			// Tiene que pasar por aqui.
		}
    }
    
    /**
     * Prueba que se agregue bien los arcos.
     */
    public void testAgregarArco( )
    {
    	try 
    	{
			setupEscenario3();
		} 
    	catch (Exception e) 
    	{
    		fail("Fallo al agregar un arco o un vertice.");
		}
    	
    	try 
    	{
    		grafo.agregarArco("actividad8","actividad1");
    		fail("No salio una excepcion al intentar insertar un arco a un nodo que no existe.");
		} 
    	catch (Exception e) 
    	{
			// Tiene que pasar por aqui.
		}
    }

    /**
     * Prueba el metodo darActividad y la clase Actividad.
     */
    public void testDarActividad()
    {
    	try 
    	{
			setupEscenario3();
		} 
    	catch (Exception e) 
    	{
    		fail("Fallo al agregar un arco o un vertice.");
		}

    	assertTrue("No devolvio la actividad correcta.", grafo.darActividad("actividad1").darNumeroVecesEjecutada()==5);
    	assertTrue("No devolvio la actividad correcta.", grafo.darActividad("actividad1").darPromedioTiempo()==10);

    	assertTrue("No devolvio la actividad correcta.", grafo.darActividad("actividad2").darNumeroVecesEjecutada()==6);
    	assertTrue("No devolvio la actividad correcta.", grafo.darActividad("actividad2").darPromedioTiempo()==3);

    	assertTrue("No devolvio la actividad correcta.", grafo.darActividad("actividad3").darNumeroVecesEjecutada()==7);
    	assertTrue("No devolvio la actividad correcta.", grafo.darActividad("actividad3").darPromedioTiempo()==11);

    	assertTrue("No devolvio la actividad correcta.", grafo.darActividad("actividad4").darNumeroVecesEjecutada()==8);
    	assertTrue("No devolvio la actividad correcta.", grafo.darActividad("actividad4").darPromedioTiempo()==1);

    	assertNull("El grafo no deberia tener ese vertice nada.",grafo.darActividad("actividad5"));
    }
    
    /**
     * Prueba que devuelva las actividades criticas.
     */
    public void testDarActividadesCriticas()
    {
    	try 
    	{
			setupEscenario3();
		} 
    	catch (Exception e) 
    	{
    		fail("Fallo al agregar un arco o un vertice.");
		}

    	assertTrue("La primera actividad critica es incorrecta.", grafo.darActividadesCriticas()[0].equals(grafo.darActividad("actividad4")));
    
    	assertTrue("La segunda actividad critica es incorrecta.", grafo.darActividadesCriticas()[1].equals(grafo.darActividad("actividad3")));

    	assertTrue("No devolvio el numero correcto de actividades criticas.", grafo.darActividadesCriticas().length==2);
    }
    
    /**
     * Prueba que devuelva las actividades mas lentas.
     */
    public void testDarActividadesMasLentas()
    {
    	try 
    	{
			setupEscenario3();
		} 
    	catch (Exception e) 
    	{
    		fail("Fallo al agregar un arco o un vertice.");
		}

    	assertTrue("La primera actividad mas lenta es incorrecta.", grafo.darActividadesMasLentas()[0].equals(grafo.darActividad("actividad3")));
    
    	assertTrue("La segunda actividad mas lenta es incorrecta.", grafo.darActividadesMasLentas()[1].equals(grafo.darActividad("actividad1")));

    	assertTrue("La tercera actividad mas lenta es incorrecta.", grafo.darActividadesMasLentas()[2].equals(grafo.darActividad("actividad2")));

    	assertTrue("No devolvio el numero correcto de actividades mas lentas.", grafo.darActividadesMasLentas().length==3);
    }
    
    /**
     * Prueba el iterador sobre las actividades.
     */
    public void testDarListaActividadesPorTiempo()
    {
    	try 
    	{
			setupEscenario3();
		} 
    	catch (Exception e) 
    	{
    		fail("Fallo al agregar un arco o un vertice.");
		}

    	IIterador it = grafo.darListaActividadesPorTiempo();
    	
    	assertTrue("El iterador devolvio una actividad incorrecta.", it.darSiguiente().equals(grafo.darActividad("actividad3")));

    	assertTrue("El iterador devolvio una actividad incorrecta.", it.darSiguiente().equals(grafo.darActividad("actividad1")));

    	assertTrue("El iterador devolvio una actividad incorrecta.", it.darSiguiente().equals(grafo.darActividad("actividad2")));

    	assertTrue("El iterador devolvio una actividad incorrecta.", it.darSiguiente().equals(grafo.darActividad("actividad4")));

    	assertFalse("El iterador no deberia seguir.", it.haySiguiente());
    }
    
    /**
     * Prueba el metodo darTiempoPromedioEspera.
     */
    public void testDarTiempoPromedioEspera()
    {
    	try 
    	{
			setupEscenario3();
		} 
    	catch (Exception e) 
    	{
    		fail("Fallo al agregar un arco o un vertice.");
		}
    	
    	// La respuesta esta entre 23 y 24, no se cuantos decimales usar.
    	assertTrue("El tiempo promedio de espera esta mal.", grafo.darActividad("actividad1").darTiempoPromedioEspera()<24 && grafo.darActividad("actividad1").darTiempoPromedioEspera()>23);
    
    	assertTrue("El tiempo promedio de espera esta mal.", grafo.darActividad("actividad2").darTiempoPromedioEspera()==15);

    	assertTrue("El tiempo promedio de espera esta mal.", grafo.darActividad("actividad3").darTiempoPromedioEspera()==12);

    	assertTrue("El tiempo promedio de espera esta mal.", grafo.darActividad("actividad4").darTiempoPromedioEspera()==1);

    }
}
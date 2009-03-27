/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: Test.java,v 1.2 2005/12/06 20:21:17 pa-barvo Exp $
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n12_helpDesk
 * Autor: cupi2 - 23-ene-2009
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.cupi2.helpDesk.test;

import java.io.File;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import uniandes.cupi2.helpDesk.interfazMundo.IUsuario;
import uniandes.cupi2.helpDesk.mundo.*;

import junit.framework.TestCase;

/**
 * Esta es la clase usada para verificar que los métodos de la clase HelpDesk estén correctamente implementados
 */
public class HelpDeskTest extends TestCase implements Observer
{
    //-----------------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------------

    /**
     * Es la clase donde se harán las pruebas
     */
    private HelpDesk helpDesk;
    
    /**
     * Numeros para probar que el MVC este bien
     */
	private int numeroSinAtender;
	private int numeroSiendoAtendidos;
	private int numeroCerrados;

    //-----------------------------------------------------------------
    // Métodos
    //-----------------------------------------------------------------

    /**
     * Construye una nueva HelpDesk vacía
     *  
     */
    private void setupEscenario1( )
    {
        helpDesk = (HelpDesk)FabricaHelpDesk.getInstance().fabricarImplementacion();
    }
    
    private void setupEscenario2( )
    {
    	setupEscenario1();
    	
    	helpDesk.crearCliente("Juan", 4, "email");
    	helpDesk.crearCliente("John", 5, "email2");
    	helpDesk.crearCliente("Judas", 6, "email3");
    }

    private void setupEscenario3( ) throws Exception
    {
    	setupEscenario2();
    	
    	helpDesk.cargarListaEmpleados( new File("test/data/datosTest.properties") );
    }
    /**
     * Prueba la cliente la clase cliente y intenta agregar uno es estos a la intefaz
     */
    public void testHelpDesk1( )
    {
        setupEscenario1( );
    
        Cliente cliente = new Cliente("Juan",1,"email@hotmail.com",null);
        Cliente cliente2 = new Cliente("Carlos",3,"email2",cliente);
    	helpDesk.iniciarSesion(cliente);

        assertEquals("El cliente no se crea con el nombre correcto.", "Juan", cliente.darNombre());
        assertEquals("El cliente no se crea con el tipo correcto.", 1, cliente.darTipo());
        assertEquals("El cliente no se crea con el email correcto.", "email@hotmail.com", cliente.darEmail());
        assertEquals("El cliente no se crea con el siguiente correcto.", null, cliente.darSiguiente());
        assertEquals("El cliente2 no se crea con el siguiente correcto.", cliente.darNombre(), cliente2.darSiguiente().darNombre());
        assertEquals("El cliente no inicio bien la sesion.", "Juan", helpDesk.darUsuarioActual().darNombre());
    }

    
    /**
     * Prueba que los clientes se agreguen correctamente al helpDesk
     */
    public void testAgregarClientes( )
    {
    	setupEscenario2();

    	assertTrue("No se encontro Juan", helpDesk.darUsuario( "Juan", 4)!=null );
    	assertTrue("No se encontro John", helpDesk.darUsuario( "John", 5)!=null );
    	assertTrue("No se encontro Judas", helpDesk.darUsuario( "Judas", 6)!=null );
    	
    }
    
    /**
     * Prueba que se carguen los empleados correctamente y luego prueba que se agreguen nuevos tickets correctamente.
     */
    public void testCargarEmpleados_NuevaSolicitud_Incidentes_Y_MVC( )
    {
    	try
    	{
    		setupEscenario3();  
    		
    		Ticket nuevoTicket1 = null;
    		
    		try{
    			helpDesk.nuevaSolicitud(3, "primera queja", false);
    			fail("No intento enviar el email");
    		}
    		catch(Exception e)
			{
				//Tiene que pasar por aqui
			}
    		try{
        		nuevoTicket1 = (Ticket) helpDesk.darTicket(helpDesk.darUsuario("Danilo", Empleado.EMPLEADO_RECLAMO).darListaTickets().get(0));
    			fail("No intento enviar el email");
    		}
    		catch(Exception e)
			{
				//Tiene que pasar por aqui
			}
    		assertNotNull("La nueva solicitud no se agrego", nuevoTicket1 );
    		assertNotNull("El nuevo ticket no se inicio correctamente.", nuevoTicket1.darFechaCreacion() );
    		
    		try{
    			helpDesk.atenderTicket(nuevoTicket1);
    			fail("No intento enviar el email");
    		}
    		catch(Exception e)
    		{
    			//Tiene que pasar por aqui
    		}
    		assertNotNull("El ticket no se atiende correctamente", nuevoTicket1.darFechaAtencion());
    		
    		try{
    			helpDesk.cerrarTicket(nuevoTicket1, "este ticket esta cerrado");
    			fail("No intento enviar el email");
    		}
    		catch(Exception e)
			{
				//Tiene que pasar por aqui
			}
    		assertNotNull("El ticket no se cierra correctamente", nuevoTicket1.darFechaCierre());
    		assertEquals("El ticket no se cierra correctamente", "este ticket esta cerrado", nuevoTicket1.darComentarioEmpleado());
    	
    		assertTrue("No deberia haber incidentes", !helpDesk.darListaIncidentes(true, new Date()).haySiguiente() );
    		
    		try{
    			helpDesk.reapertura(nuevoTicket1, helpDesk.darUsuario( "Danilo", IUsuario.EMPLEADO_RECLAMO), "fue reabierto para el test");
    			fail("No intento enviar el email");
    		}
    		catch(Exception e)
			{
				//Tiene que pasar por aqui
			}
    		assertNull("El ticket no se reabre correctamente", nuevoTicket1.darFechaAtencion());
        	assertNull("El ticket no se reabre correctamente", nuevoTicket1.darFechaCierre());

        	assertTrue("No se agrego el incidente", helpDesk.darListaIncidentes(true, new Date()).haySiguiente() );
        	assertTrue("Se agrego un algun incidente adicional", !helpDesk.darListaIncidentes(false, new Date()).haySiguiente() );

        	assertTrue("El numero de tickets cerrados no se inicio correcramente", helpDesk.darNumeroCerrados()==0 );
        	assertTrue("El numero de tickets siendo atendidos no se inicio correcramente", helpDesk.darNumeroSiendoAtendidos()==0 );
        	assertTrue("El numero de tickets sin atender no se inicio correcramente", helpDesk.darNumeroSinAtender()==0 );
        	
        	helpDesk.addObserver(this);
        	
        	helpDesk.cambiarNumeroTicketesCerrados(2);
        	helpDesk.cambiarNumeroTicketsSiendoAtendidos(3);
        	helpDesk.cambiarNumeroTicketsSinAtender(4);
        	
        	assertTrue("El numero de tickets cerrados no se inicio correcramente", helpDesk.darNumeroCerrados()==numeroCerrados );
        	assertTrue("El numero de tickets siendo atendidos no se inicio correcramente", helpDesk.darNumeroSiendoAtendidos()==numeroSiendoAtendidos );
        	assertTrue("El numero de tickets sin atender no se inicio correcramente", helpDesk.darNumeroSinAtender()==numeroSinAtender );
        	
    	}
    	catch(Exception e)
    	{
    		fail("No logro cargar el archivo de emleados");
    	}
    	
    }
    
	public void update(Observable arg0, Object arg1) {
		numeroSinAtender = helpDesk.darNumeroSinAtender();
		numeroSiendoAtendidos = helpDesk.darNumeroSiendoAtendidos();
		numeroCerrados = helpDesk.darNumeroCerrados();	
	}
}

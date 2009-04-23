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
    	
    	helpDesk.cargarListaEmpleados( new File("./test/data/datosTest.properties") );
    }
    
    /**
     * Prueba la cliente la clase cliente y intenta agregar uno es estos a la intefaz
     */
    public void testHelpDesk1( )
    {
        setupEscenario1( );
    
        Cliente cliente = new Cliente(123, "Juan",1,"email@hotmail.com",null, null);
        Cliente cliente2 = new Cliente(321, "Carlos",3,"email2",cliente, null);
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
    		helpDesk.iniciarSesion(helpDesk.darUsuario("Juan", 4));
    		
    		try{
    			helpDesk.nuevaSolicitud(3, "primera queja", false);
    		}
    		catch(Exception e)
			{
    			fail("No pudo agregar el primer ticket.");
			}
    		nuevoTicket1 = (Ticket) helpDesk.darTicket(10001);
    		assertNotNull("La nueva solicitud no se agrego", nuevoTicket1 );
    		assertNotNull("El nuevo ticket no se inicio correctamente.", nuevoTicket1.darFechaCreacion() );
    		
    		helpDesk.atenderTicket(nuevoTicket1);
    		
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
    		
   			helpDesk.reapertura(nuevoTicket1, helpDesk.darUsuario( "Danilo", IUsuario.EMPLEADO_RECLAMO), "fue reabierto para el test");

    		assertNotNull("El ticket no se reabre correctamente", nuevoTicket1.darFechaAtencion());
        	assertNull("El ticket no se reabre correctamente", nuevoTicket1.darFechaCierre());

        	assertTrue("Se agrego un algun incidente adicional", !helpDesk.darListaIncidentes(false, new Date()).haySiguiente() );

        	assertTrue("El numero de tickets cerrados no se inicio correcramente", helpDesk.darNumeroCerrados()==0 );
        	assertTrue("El numero de tickets siendo atendidos no se inicio correcramente", helpDesk.darNumeroSiendoAtendidos()==0 );
        	assertTrue("El numero de tickets sin atender no se inicio correcramente", helpDesk.darNumeroSinAtender()==1 );
        	
        	helpDesk.addObserver(this);
        	
        	helpDesk.cambiarNumeroTicketesCerrados(2);
        	helpDesk.cambiarNumeroTicketsSiendoAtendidos(3);
        	helpDesk.cambiarNumeroTicketsSinAtender(4);
        	
        	assertTrue("El numero de tickets cerrados no se inicio correcramente", helpDesk.darNumeroCerrados()==numeroCerrados );
        	assertTrue("El numero de tickets siendo atendidos no se inicio correcramente", helpDesk.darNumeroSiendoAtendidos()==numeroSiendoAtendidos );
        	assertTrue("El numero de tickets sin atender no se inicio correcramente", helpDesk.darNumeroSinAtender()==numeroSinAtender );
        	
        	try
        	{
        		helpDesk.guardar("./test/data/persistenciaTest1.xml");
        	}
        	catch (Exception e) {
				fail("No se pudo guardar en un archovi XML.");
			}
    	}
    	catch(Exception e)
    	{
    		fail("No logro cargar el archivo de emleados");
    	}
    	
    }

    /**
     * Prueba 
     */
    public void testCargarXML( )
    {
    	try
    	{
    		helpDesk = new HelpDesk("./test/data/persistenciaTest2.xml");
    	}
    	catch (Exception e) {
    		helpDesk = new HelpDesk();
    		fail("No se pudo construir el mundo a partir de un XML");
    	}
    	
    	assertTrue("El numero de tickets cerrados no se cargo correcramente", helpDesk.darNumeroCerrados()==1 );
    	assertTrue("El numero de tickets siendo atendidos no se cargo correcramente", helpDesk.darNumeroSiendoAtendidos()==2 );
    	assertTrue("El numero de tickets sin atender no se cargo correcramente", helpDesk.darNumeroSinAtender()==4 );
    	
    	for(int i=10001; i<10008; i++)
		{
			assertNotNull("No se cargo un ticket con id:"+i, helpDesk.darTicket(i) );
		}
		assertNull("Se cargo un ticket adicional", helpDesk.darTicket(10000) );
		assertNull("Se cargo un ticket adicional", helpDesk.darTicket(10008) );
		
		for(int i=25001; i<25009;i++)
		{
			assertTrue("El usuario deberia ser empleado", helpDesk.darUsuario(i).esEmpleado());
		}

		for(int i=25009; i<25012;i++)
		{
			IUsuario empleado = helpDesk.darUsuario(i);
			assertFalse("El usuario deberia ser empleado",empleado.esEmpleado());
		}
    }
    
	public void update(Observable arg0, Object arg1) {
		numeroSinAtender = helpDesk.darNumeroSinAtender();
		numeroSiendoAtendidos = helpDesk.darNumeroSiendoAtendidos();
		numeroCerrados = helpDesk.darNumeroCerrados();
	}
}

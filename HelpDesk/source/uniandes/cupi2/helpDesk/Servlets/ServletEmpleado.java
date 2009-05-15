package uniandes.cupi2.helpDesk.Servlets;

import java.io.IOException;
import java.io.PrintWriter;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uniandes.cupi2.helpDesk.interfazMundo.ITicket;
import uniandes.cupi2.helpDesk.mundo.HelpDesk;

/**
 * Servlet para el inicio de sesion y el manejo de usuarios
 * @author Nicolas Alexandre Stucki Borgeaud
 * @author Chayanne Emilio Silva Schlenker
 */
@SuppressWarnings("serial")
public class ServletEmpleado extends ServletLogin
{

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------
   
    
	/**
     * Devuelve el título de la página para el Header
     * @param request Pedido del cliente
     * @return Título de la página para el Header
     */
    public String darTituloPagina( HttpServletRequest request )
    {
        return "Help Desk - Empleado";
    }

    /**
     * Escribe el contenido de la página
     * @param request Pedido del cliente
     * @param response Respuesta
     * @throws IOException Excepción de error al escribir la respuesta
     */
    public void escribirContenido( HttpServletRequest request, HttpServletResponse response ) throws IOException
    {
    	HelpDesk mundo = HelpDesk.getInstance(null);
    	PrintWriter respuesta = response.getWriter();

    	ITicket ticketVisto = mundo.darTicketActual();
    	
    	if(ticketVisto.darFechaAtencion()==null)
    	{
    		mundo.atenderTicket(ticketVisto);
    		try {
				mundo.guardar(HelpDesk.RUTA_ARCHIVO);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	else if(ticketVisto.darFechaCierre()==null)
    	{
    		mundo.cerrarTicket(ticketVisto, request.getParameter("comentario2"));
    		try {
				mundo.guardar(HelpDesk.RUTA_ARCHIVO);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	
    	mundo.cambiarTicketActual(null);
        
    	paginaEmpleado(respuesta);
    }
}

package uniandes.cupi2.helpDesk.iterfazServlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uniandes.cupi2.helpDesk.mundo.*;

/**
 * Servlet para el manejo 
 */
@SuppressWarnings("serial")
public class ServletLogin extends ServletTemplate
{

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------
		
    private void paginaError(PrintWriter respuesta, String mensaje) {
		respuesta.write("<html><head><title>Error...</title></head><body></body></html>");
		
	}

    private void paginaCliente(PrintWriter out) {
		// TODO Auto-generated method stub
		
	}

    private void paginaEmpleado(PrintWriter out) {
		// TODO Auto-generated method stub
		
	}
    
    private void paginaAdministrador(PrintWriter out) {
		// TODO Auto-generated method stub
		
	}
    
	/**
     * Devuelve el título de la página para el Header
     * @param request Pedido del cliente
     * @return Título de la página para el Header
     */
    public String darTituloPagina( HttpServletRequest request )
    {
        return "Help Desk";
    }

    /**
     * Escribe el contenido de la página
     * @param request Pedido del cliente
     * @param response Respuesta
     * @throws IOException Excepción de error al escribir la respuesta
     */
    public void escribirContenido( HttpServletRequest request, HttpServletResponse response ) throws IOException
    {
        PrintWriter respuesta = response.getWriter( );
		
		HelpDesk mundo = HelpDesk.getInstance();
		
		try 
		{
			int llave = mundo.validar(request.getParameter("login"), request.getParameter("password"), Integer.parseInt(request.getParameter("tipo")));
		
			if(!mundo.darUsuario(llave).esEmpleado())
			{
				paginaCliente(respuesta);
			}
			else if(mundo.darUsuario(llave).esEmpleado())
			{
				paginaEmpleado(respuesta);
			}
			else 
			{
				paginaAdministrador(respuesta);
			}
		} 
		catch (Exception e) {
		
			paginaError(respuesta, e.getMessage());
		}
    }
}

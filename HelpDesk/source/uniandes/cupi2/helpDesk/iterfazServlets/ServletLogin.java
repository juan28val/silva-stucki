package uniandes.cupi2.helpDesk.iterfazServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uniandes.cupi2.helpDesk.interfazMundo.IIterador;
import uniandes.cupi2.helpDesk.interfazMundo.ITicket;
import uniandes.cupi2.helpDesk.interfazMundo.IUsuario;
import uniandes.cupi2.helpDesk.mundo.*;

/**
 * Servlet para el inicio de sesion y el manejo de usuarios
 * @author Nicolas Alexandre Stucki Borgeaud
 * @author Chayanne Emilio Silva Schlenker
 */
@SuppressWarnings("serial")
public class ServletLogin extends ServletTemplate
{

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------
	

    private void paginaCliente(PrintWriter respuesta) 
    {
    	respuesta.write("<tr>\n");
    	respuesta.write("    <form method=\"post\" action=\"login.htm\">\n");
    	respuesta.write("        <td width=\"34%\"><select size=10 name=\"tickets\">\n");
    	respuesta.write("                <option id=\"-1\" onClick=\"seleccionar()\"> ___________ * --- Lista de Tickets --- * ___________ </option>\n");
    	ArrayList<Integer> tickets = HelpDesk.getInstance(null).darUsuarioActual().darListaTickets();
    	for(int ticket : tickets)
    		respuesta.write("<option>" + HelpDesk.getInstance(null).darTicket(ticket).toString() + "</option>");
    	respuesta.write("            </select></td>\n");
    	respuesta.write("        <td width=\"33%\" align=\"center\"><input type=\"button\" name=\"info\" onClick=\"info()\" value=\"Ver informacion asociada\" >\n");
    	respuesta.write("            <br>\n");
    	respuesta.write("            <br>\n");
    	respuesta.write("            <input type=\"button\" name=\"calificar\" onClick=\"calificar()\" value=\"       Calificar ticket      \">\n");
    	respuesta.write("            <br>\n");
    	respuesta.write("            <br>\n");
    	respuesta.write("            <form action='login.htm' method='post'><input type=\"submit\" name=\"salir\" onClick=\"salir()\" value=\"Guardar cambios y salir\"><input type=hidden name=salir></form></td>\n");
    	respuesta.write("        <td width=\"33%\" align=\"right\"><textarea name=\"mensaje\" rows='8' cols='30' onClick=\"javascript:value=''\">Crear un nuevo ticket...</textarea>\n");
    	respuesta.write("            <br>\n");
    	respuesta.write("            <input type=\"radio\" name=\"tipo\" value=\"queja\">\n");
    	respuesta.write("            Queja\n");
    	respuesta.write("            <input type=\"radio\" name=\"tipo\" value=\"reclamo\">\n");
    	respuesta.write("            Reclamo\n");
    	respuesta.write("            <input type=\"radio\" name=\"tipo\" value=\"solicitud\">\n");
    	respuesta.write("            Solicitud\n");
    	respuesta.write("            <br>\n");
    	respuesta.write("            <br>\n");
    	respuesta.write("            <input type=\"submit\" name=\"crear\" value=\"          Crear ticket         \"><input type=hidden name=nuevoTicket value=''\n");
    	respuesta.write("    </form>\n");
    	respuesta.write("</tr>\n");

	}
    
    private void paginaAdministrador(PrintWriter respuesta) {

    	HelpDesk mundo = HelpDesk.getInstance(null);
    	
        respuesta.write( "                              <table border=\"0\" width=\"710\" id=\"table3\">\r\n" );
        respuesta.write( "                              <tr>\r\n" );
        respuesta.write( "                              <td width=\"696\" colspan=\"4\" bgcolor=\"#E2E2E2\">\r\n" );
        respuesta.write( "                              <table border=\"0\" width=\"614\" id=\"table4\">\r\n" );
        respuesta.write( "                              <tr>\r\n" );
        respuesta.write( "                              <td width=\"40\">&nbsp;</td>\r\n" );
        respuesta.write( "                              <td width=\"564\">Help Desk - Administrador</td>\r\n" );
        respuesta.write( "                              </tr>\r\n" );
        respuesta.write( "                              </table>\r\n" );
        respuesta.write( "                              </td>\r\n" );
        respuesta.write( "                              </tr>\r\n" );
        respuesta.write( "                              <tr>\r\n" );
        respuesta.write( "                              <td width=\"42\">&nbsp;</td>\r\n" );
        respuesta.write( "                              <td width=\"572\" colspan=\"2\">&nbsp;</td>\r\n" );
        respuesta.write( "                              <td width=\"82\">&nbsp;</td>\r\n" );
        respuesta.write( "                              </tr>\r\n" );
        respuesta.write( "                              <tr>\r\n" );
        respuesta.write( "                              <td width=\"42\">&nbsp;</td>\r\n" );
        respuesta.write( "                              <td width=\"25\">&nbsp;</td>\r\n" );
        respuesta.write( "                              <td width=\"543\"><table width=\"545\" border=\"0\">\r\n" );
        respuesta.write( "                              <tr>\r\n" );
        respuesta.write( "                              <th width=\"506\" height=\"35\" align=\"left\" scope=\"row\">Lista Empleados por calificacion:</th>\r\n" );
        respuesta.write( "                              </tr>\r\n" );
        respuesta.write( "                              <tr>\r\n" );
        respuesta.write( "                              <th height=\"121\" align=\"left\" scope=\"row\"><select name=\"empleados\" size=\"10\" class=\"normal\">\r\n" );
        
        IIterador it = mundo.darEmpleadosDelMesServlet();
        while( it.haySiguiente() )
        {
        	IUsuario usuario = (IUsuario)it.darSiguiente();
        	   respuesta.write( "                              <option selected>" + usuario.darNombre() + " - " + usuario.darSumaCalificacion() + "</option>\r\n" );
        	        	
        }
        
        respuesta.write( "                              </select></th>\r\n" );
        respuesta.write( "                              </tr>\r\n" );
        respuesta.write( "                              <tr>\r\n" );
        respuesta.write( "                              <th height=\"31\" align=\"left\" scope=\"row\">&nbsp;</th>\r\n" );
        respuesta.write( "                              </tr>\r\n" );
        respuesta.write( "                              </table></td>\r\n" );
        respuesta.write( "                              <td width=\"82\">&nbsp;</td>\r\n" );
        respuesta.write( "                              </tr>\r\n" );
        respuesta.write( "                              <tr>\r\n" );
        respuesta.write( "                              <td width=\"42\">&nbsp;</td>\r\n" );
        respuesta.write( "                              <td width=\"25\">&nbsp;</td>\r\n" );
        respuesta.write( "                              <td width=\"543\"><form method=\"POST\" action=\"index.html\">\r\n" );
        respuesta.write( "                              <input type=\"submit\" value=\"Cerrar sesion\" name=\"B2\" class=\"normal\">\r\n" );
        respuesta.write( "                              </form>\r\n" );
        respuesta.write( "                              </td>\r\n" );
        respuesta.write( "                              <td width=\"82\">&nbsp;</td>\r\n" );
        respuesta.write( "                              </tr>\r\n" );
        respuesta.write( "                              </table>\r\n" );
	
	}

    private void paginaEmpleado(int llaveEmpleado, PrintWriter respuesta) {
    	HelpDesk mundo = HelpDesk.getInstance(null);
    	Empleado empleado = (Empleado) mundo.darUsuario(llaveEmpleado);
    	ArrayList<Integer> tickets = empleado.darListaTickets();
		respuesta.write("<tr><form method=\"post\" action=\"\"><td width=\"50%\"><select size=10 name=\"tickets\"><option id=\"0\" onClick=\"seleccionar()\"> ___________ * --- Lista de Tickets --- * ___________ </option>");
		for(Integer id : tickets)
		{
			ITicket ticket = mundo.darTicket(id);
			String info = ticket.toString();
			int estado = ticket.darFechaAtencion() == null ? 0 : ticket.darFechaCierre() == null ? 1 : 2;
			respuesta.write("<option value='" + id + "' id='" + estado + "' onClick='seleccionar()'>" + info + "</option>");
		}
		respuesta.write("</select></td><td width=\"50%\" align=\"center\"><input type=\"button\" name=\"info\" onClick='info()' value=\"Ver informacion asociada\" ><br><br><input type=\"button\" name=\"atender\" onClick=\"atender()\" value=\"         Atender ticket        \"><br><br><input type=\"button\" name=\"cerrar\" onClick=\"cerrar()\" value=\"           Cerrar ticket         \"><br><br><input type=\"button\" name=\"salir\" onClick=\"salir()\" value=\"Guardar cambios y salir\"></td></form></tr>");
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
		
		HelpDesk mundo = HelpDesk.getInstance(null);
      
		
		try 
		{
			int llave = 0;
			if(request.getParameter("login") != null && request.getParameter("password") != null && request.getParameter("tipoUsuario") != null)
			{
				llave = mundo.validar(request.getParameter("login"), request.getParameter("password"), Integer.parseInt(request.getParameter("tipoUsuario")));
			mundo.iniciarSesion(mundo.darUsuario(llave));
			
			if(mundo.darUsuario(llave)==null)
			{
				paginaAdministrador(respuesta);
			}
			else if(!mundo.darUsuario(llave).esEmpleado())
			{
				paginaCliente(respuesta);
			}
			else if( mundo.darUsuario(llave).esEmpleado() )
			{
				paginaEmpleado(llave, respuesta);
			}
			else imprimirMensajeError(respuesta, "Algo raro paso...");
			}
			int tipo;
			if(request.getParameter("nuevoTicket") != null) {
				tipo = request.getParameter("tipo").equals("queja") ? 1 : request.getParameter("tipo").equals("reclamo") ? 2 : 3;
				mundo.nuevaSolicitud(tipo, request.getParameter("mensaje"), false);
				paginaCliente(respuesta);
			}
			if(request.getParameter("salir") != null)
			{
				mundo.guardar(HelpDesk.RUTA_ARCHIVO);
				respuesta.write("<html><head><meta http-equiv='refresh' content='0;url=index.html' /></head></html>");
			}


		} 
		catch (Exception e) {
		
			imprimirMensajeError(respuesta, e.getMessage());
		}
    }
}

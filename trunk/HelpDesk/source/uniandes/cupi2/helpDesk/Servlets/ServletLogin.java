package uniandes.cupi2.helpDesk.Servlets;

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
	

    public void paginaCliente(PrintWriter respuesta) 
    {
    	HelpDesk mundo = HelpDesk.getInstance(null);
    	
    	respuesta.write("<table border=\"0\" width=\"710\" id=\"table3\">");
    	respuesta.write("<tr>");
    	respuesta.write("<td width=\"696\" colspan=\"4\" bgcolor=\"#E2E2E2\">");
    	respuesta.write("<table border=\"0\" width=\"614\" id=\"table4\">");
    	respuesta.write("<tr>");
    	respuesta.write("<td width=\"40\">&nbsp;</td>");
    	respuesta.write("<td width=\"564\">Help Desk - "+mundo.darUsuarioActual().darNombre()+"</td>");
    	respuesta.write("</tr>");
    	respuesta.write("</table>");
    	respuesta.write("</td>");
    	respuesta.write("</tr>");
    	respuesta.write("<tr>");
    	respuesta.write("<td width=\"42\">&nbsp;</td>");
    	respuesta.write("<td width=\"572\" colspan=\"2\">&nbsp;</td>");
    	respuesta.write("<td width=\"82\">&nbsp;</td>");
    	respuesta.write("</tr>");
    	respuesta.write("<tr>");
    	respuesta.write("<td width=\"42\" height=\"301\">&nbsp;</td>");
    	respuesta.write("<td width=\"25\">&nbsp;</td>");
    	respuesta.write("<td width=\"543\"><table width=\"545\" border=\"0\">");
    	respuesta.write("<tr>");
    	respuesta.write("<th width=\"335\" scope=\"col\"><form method=\"POST\" action=\"verTicketCliente.htm\">");
    	respuesta.write("<p>");
    	respuesta.write("<select name=\"tickets\" size=\"10\" class=\"normal\">");
        	
    	ArrayList<Integer> tickets = mundo.darUsuarioActual().darListaTickets();
    	
    	if(tickets.size()==0)
    		respuesta.write("<option value=\"\">Usted no ha generado tickets.</option>");
    	else
    	{
    		ITicket ticket = mundo.darTicket(tickets.get(0));
			respuesta.write("<option value=\"" + ticket.darId() + "\" selected>" + ticket.toString() + "</option>");
    	}
    		
		for(int i=1; i<tickets.size();i++)
		{
			ITicket ticket = mundo.darTicket(tickets.get(i));
			respuesta.write("<option value=\"" + ticket.darId() + "\">" + ticket.toString() + "</option>");
		}
    	
    	respuesta.write("</select>");
    	respuesta.write("</p>");
    	respuesta.write("<input type=\"submit\""+(tickets.size()==0?" disabled":"")+" value=\"Ver Ticket\" name=\"B3\" class=\"normal\">");
    	respuesta.write("</form></th>");
    	respuesta.write("<th width=\"200\" scope=\"col\">");
    	respuesta.write("<p>&nbsp;</p>");
    	respuesta.write("<table width=\"200\" border=\"0\">");
    	respuesta.write("<tr>");
    	respuesta.write("<th scope=\"row\"><form method=\"POST\" action=\"nuevoTicket.htm\">");
    	respuesta.write(" <p>");
    	respuesta.write("<input type=\"submit\" value=\"Nueva solicitud\" name=\"B2\" class=\"normal\">");
    	respuesta.write("</p>");
    	respuesta.write("</form></th>");
    	respuesta.write("</tr>");
    	respuesta.write("<tr>");
    	respuesta.write("<th height=\"74\" scope=\"row\"><form method=\"POST\" action=\"ticketsALaFecha.htm\">");
    	respuesta.write("</form><p>");
    	respuesta.write("<form action='ticketsALaFecha.htm' method='post'>\n");
    	respuesta.write("Seleccione la fecha:<br><select name='mes'>" + formMes() + "</select><select name='dia'>" + formDia() + "</select><br>\n");
    	respuesta.write("<input type=\"submit\" value=\"Ver Tickets a la fecha\" name=\"B4\" class=\"normal\">");
    	respuesta.write("</p>");
    	respuesta.write("</form></th>");
    	respuesta.write("</tr>");
    	respuesta.write("</table>");
    	respuesta.write("<p>&nbsp; </p>");
    	respuesta.write("</th>");
    	respuesta.write("</tr>");
    	respuesta.write("</table>");
    	respuesta.write("<p>&nbsp; </p>");
    	respuesta.write("</th>");
    	respuesta.write("</tr>");
    	respuesta.write("</table></td><td width=\"82\">&nbsp;</td></tr><tr>");
    	respuesta.write("<td width=\"42\">&nbsp;</td><td width=\"25\">&nbsp;</td>");
    	respuesta.write("<td width=\"543\">");
    	respuesta.write("<form method=\"POST\" action=\"index.htm\">");
    	respuesta.write("<input type=\"submit\" value=\"Cerrar sesion\" name=\"B\" class=\"normal\">");
    	respuesta.write("</form></td><td width=\"82\">&nbsp;</td></tr></table>");      
    }
    
    private String formMes() {
    	return 	"<option name='mes' value='0''>Mes</option>\n" +
    			"<option name='mes' value='1'>Enero</option>\n" +
				"<option name='mes' value='2'>Febrero</option>\n" +
				"<option name='mes' value='3'>Marzo</option>\n" +
				"<option name='mes' value='4'>Abril</option>\n" +
				"<option name='mes' value='5'>Mayo</option>\n" +
				"<option name='mes' value='6'>Junio</option>\n" +
				"<option name='mes' value='7'>Julio</option>\n" +
				"<option name='mes' value='8'>Agosto</option>\n" +
				"<option name='mes' value='9'>Septiembre</option>\n" +
				"<option name='mes' value='10'>Octubre</option>\n" +
				"<option name='mes' value='11'>Noviembre</option>\n" +
				"<option name='mes' value='12'>Diciembre</option>\n";
	}
    
    private String formDia() {
    	return "<option name='dia' value='0'>Día</option>\n" +
    			"<option name='dia' value='1'>01</option>\n" +
    			"<option name='dia' value='2'>02</option>\n" +
    			"<option name='dia' value='3'>03</option>\n" +
    			"<option name='dia' value='4'>04</option>\n" +
    			"<option name='dia' value='5'>05</option>\n" +
    			"<option name='dia' value='6'>06</option>\n" +
    			"<option name='dia' value='7'>07</option>\n" +
    			"<option name='dia' value='8'>08</option>\n" +
    			"<option name='dia' value='9'>09</option>\n" +
    			"<option name='dia' value='10'>10</option>\n" +
    			"<option name='dia' value='11'>11</option>\n" +
    			"<option name='dia' value='12'>12</option>\n" +
    			"<option name='dia' value='13'>13</option>\n" +
    			"<option name='dia' value='14'>14</option>\n" +
    			"<option name='dia' value='15'>15</option>\n" +
    			"<option name='dia' value='16'>16</option>\n" +
    			"<option name='dia' value='17'>17</option>\n" +
    			"<option name='dia' value='18'>18</option>\n" +
    			"<option name='dia' value='19'>19</option>\n" +
    			"<option name='dia' value='20'>20</option>\n" +
    			"<option name='dia' value='21'>21</option>\n" +
    			"<option name='dia' value='22'>22</option>\n" +
    			"<option name='dia' value='23'>23</option>\n" +
    			"<option name='dia' value='24'>24</option>\n" +
    			"<option name='dia' value='25'>25</option>\n" +
    			"<option name='dia' value='26'>26</option>\n" +
    			"<option name='dia' value='27'>27</option>\n" +
    			"<option name='dia' value='28'>28</option>\n" +
    			"<option name='dia' value='29'>29</option>\n" +
    			"<option name='dia' value='30'>30</option>\n" +
    			"<option name='dia' value='31'>31</option>\n";
    }

	public void paginaAdministrador(PrintWriter respuesta) {

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
        	   respuesta.write( "                              <option selected>" + usuario.darNombre() + " - " + usuario.darSumaCalificacion() + " puntos</option>\r\n" );
        	        	
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
        respuesta.write( "                              <td width=\"543\"><form method=\"POST\" action=\"index.htm\">\r\n" );
        respuesta.write( "                              <input type=\"submit\" value=\"Cerrar sesion\" name=\"B2\" class=\"normal\">\r\n" );
        respuesta.write( "                              </form>\r\n" );
        respuesta.write( "                              </td>\r\n" );
        respuesta.write( "                              <td width=\"82\">&nbsp;</td>\r\n" );
        respuesta.write( "                              </tr>\r\n" );
        respuesta.write( "                              </table>\r\n" );
	
	}

    public void paginaEmpleado(PrintWriter respuesta) {
    	
    	HelpDesk mundo = HelpDesk.getInstance(null);
    	
    	respuesta.write("<table border=\"0\" width=\"710\" id=\"table3\">");
    	respuesta.write("<tr>");
    	respuesta.write("<td width=\"696\" colspan=\"4\" bgcolor=\"#E2E2E2\">");
    	respuesta.write("<table border=\"0\" width=\"614\" id=\"table4\">");
    	respuesta.write("<tr>");
    	respuesta.write("<td width=\"40\">&nbsp;</td>");
    	respuesta.write("<td width=\"564\">Help Desk - "+mundo.darUsuarioActual().darNombre()+"(Empleado con "+mundo.darUsuarioActual().darSumaCalificacion()+" puntos)</td>");
    	respuesta.write("</tr>");
    	respuesta.write("</table>");
    	respuesta.write("</td>");
    	respuesta.write("</tr>");
    	respuesta.write("<tr>");
    	respuesta.write("<td width=\"42\">&nbsp;</td>");
    	respuesta.write("<td width=\"572\" colspan=\"2\">&nbsp;</td>");
    	respuesta.write("<td width=\"82\">&nbsp;</td>");
    	respuesta.write("</tr>");
    	respuesta.write("<tr>");
    	respuesta.write("<td width=\"42\" height=\"301\">&nbsp;</td>");
    	respuesta.write("<td width=\"25\">&nbsp;</td>");
    	respuesta.write("<td width=\"543\"><table width=\"545\" border=\"0\">");
    	respuesta.write("<tr>");
    	respuesta.write("<th scope=\"col\"><form method=\"POST\" action=\"verTicketEmpleado.htm\">");
    	respuesta.write("<p>");
    	respuesta.write("<select name=\"tickets\" size=\"10\" class=\"normal\">");
        	
    	ArrayList<Integer> tickets = mundo.darUsuarioActual().darListaTickets();
    	
    	if(tickets.size()==0)
    		respuesta.write("<option value=\"\">El sistema no le ha asignado tickets.</option>");
    	else
    	{
    		ITicket ticket = mundo.darTicket(tickets.get(0));
			respuesta.write("<option value=\"" + ticket.darId() + "\" selected>" + ticket.toString() + "</option>");
    	}
    		
		for(int i=1; i<tickets.size();i++)
		{
			ITicket ticket = mundo.darTicket(tickets.get(i));
			respuesta.write("<option value=\"" + ticket.darId() + "\">" + ticket.toString() + "</option>");
		}
    	
    	respuesta.write("</select>");
    	respuesta.write("</p>");
    	respuesta.write("<input type=\"submit\""+(tickets.size()==0?" disabled":"")+" value=\"Ver Ticket\" name=\"B3\" class=\"normal\">");
    	
    	respuesta.write("</form></th>");
    	respuesta.write("</tr>");
    	respuesta.write("<tr>");
    	respuesta.write("<th height=\"31\" align=\"left\" scope=\"row\">&nbsp;</th>");
    	respuesta.write("</tr>");
    	respuesta.write("<tr>");
    	respuesta.write("<th height=\"41\" align=\"left\" scope=\"row\"><table width=\"540\" border=\"0\">");
    	respuesta.write("</table></th>");
    	respuesta.write("</tr>");
    	respuesta.write("<td width=\"42\">&nbsp;</td>");
    	respuesta.write("<td width=\"25\">&nbsp;</td>");
    	respuesta.write("<td width=\"543\" align=\"center\"><form method=\"POST\" action=\"index.htm\">");
    	respuesta.write("<input type=\"submit\" value=\"Cerrar sesion\" name=\"B\" class=\"normal\">");
    	respuesta.write("</form>");
    	respuesta.write("</td>");
    	respuesta.write("<td width=\"82\">&nbsp;</td>");
    	respuesta.write("</tr>");
    	respuesta.write("</table>");
    	
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
				else 
				{
					paginaEmpleado(respuesta);
				}
			}
			else
				imprimirMensajeError(respuesta, "Error de login o password");
			
		} 
		catch (Exception e) {
		
			imprimirMensajeError(respuesta, e.getMessage());
		}
    }
}

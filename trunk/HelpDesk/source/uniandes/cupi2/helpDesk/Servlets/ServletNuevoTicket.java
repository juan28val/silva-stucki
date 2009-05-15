package uniandes.cupi2.helpDesk.Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet para la creacion de tickets
 */
@SuppressWarnings("serial")
public class ServletNuevoTicket extends ServletTemplate
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
        return "Nuevo Ticket";
    }

    /**
     * Escribe el contenido de la página
     * @param request Pedido del cliente
     * @param response Respuesta
     * @throws IOException Excepción de error al escribir la respuesta
     */
    public void escribirContenido( HttpServletRequest request, HttpServletResponse response ) throws IOException
    {
    	PrintWriter respuesta = response.getWriter();
       
    	respuesta.write("<table border=\"0\" width=\"710\" id=\"table3\">");
    	respuesta.write("<tr>");
    	respuesta.write("<td width=\"696\" colspan=\"4\" bgcolor=\"#E2E2E2\">");
    	respuesta.write("<table border=\"0\" width=\"614\" id=\"table4\">");
    	respuesta.write("<tr>");
    	respuesta.write("<td width=\"40\">&nbsp;</td>");
    	respuesta.write("<td width=\"564\">Nueva solicitud</td>");
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
    	respuesta.write("<td width=\"42\" height=\"2\">&nbsp;</td>");
    	respuesta.write("<td width=\"25\">&nbsp;</td>");	
    	respuesta.write("<td width=\"543\"><form method=\"POST\" action=\"cliente.htm\">");
    	respuesta.write("<table border=\"0\" width=\"100%\" id=\"table5\">");
    	respuesta.write("<tr>");
    	respuesta.write("<td width=\"67\" height=\"30\">");
    	respuesta.write("<p align=\"right\"><b>Comentario:</b></td>");
    	respuesta.write("<td width=\"466\"><textarea name=\"comentario\" cols=\"60\" rows=\"6\" class=\"normal\">Escriba su comentario aqui.</textarea></td>");
    	respuesta.write("</tr>");
    	respuesta.write("<tr>");
    	respuesta.write("<td width=\"67\" height=\"20\" valign=\"top\">");
    	respuesta.write("<p align=\"right\"><b>tipo:</b></td>");
    	respuesta.write("<td valign=\"top\"><select size=\"1\" name=\"tipo\" class=\"normal\">");
    	respuesta.write("<option value=\"1\">Queja</option>");
    	respuesta.write("<option value=\"2\">Reclamo</option>");
    	respuesta.write("<option value=\"3\">Solicitud</option>");
    	respuesta.write("</select></td>");
    	respuesta.write("</tr>");
    	respuesta.write("<tr>");
    	respuesta.write("<td height=\"20\" align=\"right\" valign=\"top\"><strong>cifrado:</strong></td>");
    	respuesta.write(" <td valign=\"top\"><input type=\"checkbox\" name=\"cifrado\"></td>");
    	respuesta.write("</tr>");
    	respuesta.write("</table>");
    	respuesta.write("<p>");
    	respuesta.write("<input type=\"submit\" value=\"Aceptar\" name=\"B1\" class=\"normal\">");
    	respuesta.write("<input type=\"reset\" value=\"Restablecer\" name=\"Borrar\" class=\"normal\">");
    	respuesta.write("</p>"); 
    	respuesta.write("</form>");
    	respuesta.write(" <p>&nbsp; ");
    	respuesta.write(" <form method=\"POST\" action=\"cliente.htm\">");
    	respuesta.write("<input type=\"submit\" value=\"Cancelar\" name=\"B3\" class=\"normal\">");
    	respuesta.write("</form>");
    	respuesta.write(" <p>&nbsp;</p></td>");
    	respuesta.write("<td width=\"82\">&nbsp;</td>");
    	respuesta.write("</tr>");
    	respuesta.write("<tr>");    
    	respuesta.write("<td width=\"42\">&nbsp;</td>");
    	respuesta.write("<td width=\"25\">&nbsp;</td>");
    	respuesta.write("<td width=\"543\">");			
    	respuesta.write("<form method=\"POST\" action=\"index.htm\">");
    	respuesta.write("<input type=\"submit\" value=\"Cerrar sesion\" name=\"B2\" class=\"normal\">");
    	respuesta.write("</form></td>");
    	respuesta.write("<td width=\"82\">&nbsp;</td>");
    	respuesta.write("</tr>");
    	respuesta.write("</table>");
    }
}

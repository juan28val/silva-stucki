package uniandes.cupi2.helpDesk.Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uniandes.cupi2.helpDesk.interfazMundo.ITicket;
import uniandes.cupi2.helpDesk.mundo.HelpDesk;

/**
 * Servlet para la creacion de tickets
 */
@SuppressWarnings("serial")
public class ServletVerTicketCliente extends ServletTemplate
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
        return "Info Ticket";
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
       
    	HelpDesk mundo = HelpDesk.getInstance(null);
    	ITicket ticket = mundo.darTicket(Integer.parseInt(request.getParameter("tickets")));
    	mundo.cambiarTicketActual(ticket);
    	
    	respuesta.write("<table border=\"0\" width=\"710\" id=\"table3\">");
   		respuesta.write("<tr>");
    	respuesta.write("	<td width=\"696\" colspan=\"4\" bgcolor=\"#E2E2E2\">");
   		respuesta.write("		<table border=\"0\" width=\"614\" id=\"table4\">");
    	respuesta.write("		<tr>");
    	respuesta.write("			<td width=\"40\">&nbsp;</td>");
    	respuesta.write("					<td width=\"564\">"+ticket.toString2()+"</td>");
    	respuesta.write("		</tr>");
    	respuesta.write("			</table>");
    	respuesta.write("	</td>");
    	respuesta.write("</tr>");
    	respuesta.write("<tr>");
    	respuesta.write("	<td width=\"42\">&nbsp;</td>");
    	respuesta.write("	<td width=\"572\" colspan=\"2\">&nbsp;</td>");
    	respuesta.write("	<td width=\"82\">&nbsp;</td>");
    	respuesta.write("</tr>");
		respuesta.write("<tr>");
		respuesta.write("	<td width=\"42\" height=\"2\">&nbsp;</td>");
		respuesta.write("	<td width=\"25\">&nbsp;</td>");
		respuesta.write(" <td width=\"543\"><form method=\"POST\" action=\"cliente.htm\">");
		respuesta.write("   <table border=\"0\" width=\"100%\" id=\"table5\">");
		respuesta.write("			<tr>");
		respuesta.write("				<td width=\"70\" height=\"30\" valign=\"top\">");
		respuesta.write("				<p align=\"right\"><b>Comentario</b></td>");
		respuesta.write("	  <td width=\"335\"><textarea name=\"comentario\" cols=\"60\" rows=\"6\" disabled class=\"normal\">"+ mundo.darComentarioTicket(ticket) +"</textarea></td>");
		respuesta.write(" <td width=\"124\"><p><b><strong>id</strong>:");
		respuesta.write(String.valueOf(ticket.darId()));
		respuesta.write("</b></p>");
		respuesta.write("<p><b>tipo:</b>"+(ticket.darTipo()==1?"Queja":(ticket.darTipo()==2?"Reclamo":"Solicitud"))+"</p>");
		respuesta.write("<p><strong>cifrado</strong>:"+(ticket.estaCifrado()?"Si":"No")+"</p></td>");
		respuesta.write("	</tr>");
		
		if(ticket.darFechaCierre()!=null)
		{
			respuesta.write("	<tr>");
			respuesta.write("			<td width=\"70\" height=\"20\" align=\"right\" valign=\"top\">");
			respuesta.write("		  <p align=\"right\"><b>Comentario emplead ("+ticket.darEmpleado().darNombre()+")</b></td>");
			respuesta.write("	  <td valign=\"top\"><textarea name=\"comentario2\" cols=\"60\" rows=\"6\" disabled class=\"normal\">"+ticket.darComentarioEmpleado()+"</textarea></td>");
			respuesta.write("  <td valign=\"top\">&nbsp;</td>");
			respuesta.write("</tr>");
		
		
		respuesta.write("<tr>");
		respuesta.write(" <td height=\"20\" align=\"right\" valign=\"top\"><b>Calificar</b></td>");
		respuesta.write(" <td valign=\"top\"><p><select name=\"calificacion\" class=\"normal\">");

		if(ticket.darCalificacion()==1)
		{
			respuesta.write(" <option value=\"8\">Muy satisfecho</option>");
			respuesta.write(" <option value=\"4\">Satisfecho</option>");
			respuesta.write(" <option value=\"2\">Podria mejorar</option>");
			respuesta.write(" <option value=\"1\" selected>Definitivamente muy malo</option>");
		}
		else if(ticket.darCalificacion()==2)
		{
			respuesta.write(" <option value=\"8\">Muy satisfecho</option>");
			respuesta.write(" <option value=\"4\">Satisfecho</option>");
			respuesta.write(" <option value=\"2\" selected>Podria mejorar</option>");
			respuesta.write(" <option value=\"1\">Definitivamente muy malo</option>");
		}
		else if(ticket.darCalificacion()==4)
		{
			respuesta.write(" <option value=\"8\">Muy satisfecho</option>");
			respuesta.write(" <option value=\"4\" selected>Satisfecho</option>");
			respuesta.write(" <option value=\"2\">Podria mejorar</option>");
			respuesta.write(" <option value=\"1\">Definitivamente muy malo</option>");
		}
		else if(ticket.darCalificacion()==8)
		{
			respuesta.write(" <option value=\"8\" selected>Muy satisfecho</option>");
			respuesta.write(" <option value=\"4\">Satisfecho</option>");
			respuesta.write(" <option value=\"2\">Podria mejorar</option>");
			respuesta.write(" <option value=\"1\">Definitivamente muy malo</option>");
		}
		else
		{
			respuesta.write(" <option value=\"0\" selected>*** Calificar ***</option>");
			respuesta.write(" <option value=\"8\">Muy satisfecho</option>");
			respuesta.write(" <option value=\"4\">Satisfecho</option>");
			respuesta.write(" <option value=\"2\">Podria mejorar</option>");
			respuesta.write(" <option value=\"1\">Definitivamente muy malo</option>");
		}
		respuesta.write("  </select>");
		respuesta.write("  <input type=\"submit\" value=\"Calificar\" name=\"B1\" class=\"normal\">");
		respuesta.write(" </td>");
		respuesta.write(" <td valign=\"top\">&nbsp;</td>");
		respuesta.write(" </tr>");
		}
		respuesta.write("</table>");
		respuesta.write("</form>");
		respuesta.write(" <form method=\"POST\" action=\"cliente.htm\">");
		respuesta.write("<input type=\"submit\" value=\"Volver\" name=\"B3\" class=\"normal\">");
		respuesta.write("  </form>");
		respuesta.write(" <p>&nbsp;</p></td>");
		respuesta.write("	<td width=\"82\">&nbsp;</td>");
		respuesta.write("</tr>");
		respuesta.write("<tr>");
		respuesta.write("<td width=\"42\">&nbsp;</td>");
		respuesta.write("<td width=\"25\">&nbsp;</td>");
		respuesta.write("<td width=\"543\">");
		respuesta.write(" <form method=\"POST\" action=\"index.htm\">");
        respuesta.write("  <input type=\"submit\" value=\"Cerrar sesion\" name=\"B2\" class=\"normal\">");
        respuesta.write(" </form></td>");
        respuesta.write("<td width=\"82\">&nbsp;</td>");
        respuesta.write("</tr>");
		respuesta.write("</table>");
    }
}

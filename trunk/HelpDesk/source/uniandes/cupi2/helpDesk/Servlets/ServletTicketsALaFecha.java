package uniandes.cupi2.helpDesk.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uniandes.cupi2.helpDesk.interfazMundo.ITicket;
import uniandes.cupi2.helpDesk.mundo.HelpDesk;


/**
 * Servlet para la creacion de tickets
 */
@SuppressWarnings("serial")
public class ServletTicketsALaFecha extends ServletTemplate
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
        return "Tickets a la fecha";
    }

    /**
     * Escribe el contenido de la página
     * @param request Pedido del cliente
     * @param response Respuesta
     * @throws IOException Excepción de error al escribir la respuesta
     */
    @SuppressWarnings("deprecation")
	public void escribirContenido( HttpServletRequest request, HttpServletResponse response ) throws IOException
    {
    	
    	int mes = 0;
		int dia = 0;
		Date fecha1;
		Date fecha2;
		
    	try
    	{
    		mes = Integer.parseInt(request.getParameter("mes"));
    		dia = Integer.parseInt(request.getParameter("dia"));
    		
    		if(mes==0 || dia==0)
    			throw new Exception();
    			
    		fecha1 = new Date(109, mes, dia);
    		fecha2 = (Date)fecha1.clone();
    	}
    	catch(Exception e)
    	{
    		fecha1 = new Date();
    		fecha2 = new Date();
    		dia = fecha1.getDate();
    		mes = fecha1.getMonth()+1;
    	}
		
    	fecha1.setHours(0);
		fecha1.setMinutes(0);
		fecha2.setHours(23);
		fecha2.setMinutes(59);
		
		PrintWriter out = response.getWriter();
		HelpDesk mundo = HelpDesk.getInstance(null);
				
		ArrayList<Integer> tickets = mundo.darUsuarioActual().darListaTickets();
		out.write("<tr>\n");
		if(tickets.size()==0)
			out.write("<td>No hay tickets activos en la fecha citada ( "+dia+"/"+mes+" )<br><br><br><ul>\n");
		
		else
		{
			out.write("<td>Los tickets activos en la fecha citada ( "+dia+"/"+mes+" ) son:<br><br><br><ul>\n");
		
			for(int i=0;i<tickets.size();i++)
			{				
				ITicket ticketActual = mundo.darTicket(tickets.get(i));
				if( ticketActual.darFechaAtencion() != null && fecha1.before(ticketActual.darFechaAtencion()) && ticketActual.darFechaAtencion().before(fecha2) )
					out.write("<li>" + ticketActual.toString() + "</li>\n");
			}
		}
    	out.write("\n");
    	out.write(" <form method=\"POST\" action=\"cliente.htm\">");
    	out.write("<input type=\"submit\" value=\"Volver\" name=\"B3\" class=\"normal\">");
    	out.write("</form>");
    	out.write("</td>\n");
    	out.write("</tr>\n");
     }
}
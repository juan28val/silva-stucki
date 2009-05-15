package uniandes.cupi2.helpDesk.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uniandes.cupi2.helpDesk.interfazMundo.IIterador;
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
    	int mes = Integer.parseInt(request.getParameter("mes"));
    	int dia = Integer.parseInt(request.getParameter("dia"));
    	Date fecha2 = new Date(109, mes, dia);
    	PrintWriter out = response.getWriter();
    	HelpDesk mundo = HelpDesk.getInstance(null);
    	Date fecha1 = (Date) fecha2.clone();
    	fecha1.setHours(0);
    	fecha1.setMinutes(0);
    	fecha2.setHours(23);
    	fecha2.setMinutes(59);    	
    	IIterador i = mundo.darListaTicketsEntreFechas(fecha1, fecha2);
    	out.write("<tr>\n");
    	out.write("<td>Los tickets activos en la fecha citada son:<br><br><br><ul>\n");
    	while(i.haySiguiente())
    		out.write("<li>" + i.darSiguiente().toString() + "</li>\n");
    	out.write("\n");
    	out.write("</td>\n");
    	out.write("</tr>\n");
    }
}

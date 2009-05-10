package uniandes.cupi2.helpDesk.iterfazServlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uniandes.cupi2.helpDesk.interfazMundo.IIterador;
import uniandes.cupi2.helpDesk.interfazMundo.IUsuario;
import uniandes.cupi2.helpDesk.mundo.*;

/**
 * Servlet para el manejo 
 */
@SuppressWarnings("serial")
public class ServletRegistrarCliente2 extends ServletTemplate
{

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------	

    private void paginaError(PrintWriter respuesta, String mensaje) {
		respuesta.write("<h1>Error</h1><br><br>Ha ocurrido un error: " + mensaje + "<br><br><form action='inicio.html'><input type=button text='Volver...'></form>");
		
	}

    private void paginaCliente(PrintWriter respuesta) 
    {
		HelpDesk mundo = HelpDesk.getInstance();
    	
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
        respuesta.write( "                              <td width=\"543\"><form method=\"POST\" action=\"inicio.htm\">\r\n" );
        respuesta.write( "                              <input type=\"submit\" value=\"Cerrar sesion\" name=\"B2\" class=\"normal\">\r\n" );
        respuesta.write( "                              </form>\r\n" );
        respuesta.write( "                              </td>\r\n" );
        respuesta.write( "                              <td width=\"82\">&nbsp;</td>\r\n" );
        respuesta.write( "                              </tr>\r\n" );
        respuesta.write( "                              </table>\r\n" );
	
	}

    private void paginaEmpleado(PrintWriter respuesta) {
		respuesta.write("<tr><form method=\"post\" action=\"\"><td width=\"50%\"><select size=10 name=\"tickets\"><option id=\"-1\" onClick=\"seleccionar()\"> ___________ * --- Lista de Tickets --- * ___________ </option>");
		
		respuesta.write("</select></td><td width=\"50%\" align=\"center\"><input type=\"button\" name=\"info\" disabled value=\"Ver informacion asociada\" ><br><br><input type=\"button\" name=\"atender\" disabled onClick=\"atender()\" value=\"         Atender ticket        \"><br><br><input type=\"button\" name=\"cerrar\" disabled onClick=\"cerrar()\" value=\"           Cerrar ticket         \"></td></form></tr>");
	}
    
	/**
     * Devuelve el título de la página para el Header
     * @param request Pedido del cliente
     * @return Título de la página para el Header
     */
    public String darTituloPagina( HttpServletRequest request )
    {
        return "Registrar Cliente";
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
		
        String nombre = request.getParameter("nombre");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        String email = request.getParameter("email");
        int tipo = Integer.parseInt(request.getParameter("tipo"));
    
        HelpDesk mundo = HelpDesk.getInstance();
        
        if( nombre.equals("") || login.equals("") || password.equals("") || !password.equals(password2))
        {
        	imprimirMensajeError(respuesta, "Error de formato: alguno de los campos no fue llenado correctamente.");
        }
        else if( mundo.existeUsuario(login) )
        {
        	imprimirMensajeError(respuesta, "Login no disponible: el login que escribio ya se encuentra en nuestra base de datos.");
        }
        else
        {
        	mundo.crearCliente(nombre, login, password, tipo+3, email);
        	
        	respuesta.write( "                              <table border=\"0\" width=\"710\" id=\"table3\">\r\n" );
        	respuesta.write( "                              <tr>\r\n" );
        	respuesta.write( "                              <td width=\"696\" colspan=\"4\" bgcolor=\"#E2E2E2\">\r\n" );
        	respuesta.write( "                              <table border=\"0\" width=\"614\" id=\"table4\">\r\n" );
        	respuesta.write( "                              <tr>\r\n" );
        	respuesta.write( "                              <td width=\"40\">&nbsp;</td>\r\n" );
        	respuesta.write( "                              <td width=\"564\">   Registraro nuevo cliente Help Desk</td>\r\n" );
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
        	respuesta.write( "                             <td width=\"42\" height=\"103\">&nbsp;</td> \r\n" );
        	respuesta.write( "                             <td width=\"25\">&nbsp;</td> \r\n" );
        	respuesta.write( "                             <td width=\"543\"> \r\n" );
        	respuesta.write( "                              <table border=\"0\" width=\"543\" id=\"table5\">\r\n" );
        	respuesta.write( "                              <tr>\r\n" );
        	respuesta.write( "                              <td width=\"121\" height=\"20\">\r\n" );
        	respuesta.write( "                              <p align=\"right\"><b>nombre completo:</b></td>\r\n" );
        	respuesta.write( "                              <td width=\"412\">" + nombre + "</td>\r\n" );
        	respuesta.write( "                             </tr>\r\n" );
        	respuesta.write( "                              <tr>\r\n" );
        	respuesta.write( "                              <td width=\"121\" height=\"28\">\r\n" );
        	respuesta.write( "                              <p align=\"right\"><b>login:</b></td>\r\n" );
        	respuesta.write( "                              <td>" + login + "</td>\r\n" );
        	respuesta.write( "                              </tr>\r\n" );
        	respuesta.write( "                              <tr>\r\n" );
        	respuesta.write( "                              <td align=\"right\"><b>tipo:</b></td>\r\n" );
        	respuesta.write( "                             <td>" + (tipo==4?"Estudiante":tipo==5?"Profesor":"Personal Administrativo") + "</td> \r\n" );
        	respuesta.write( "                              </tr>\r\n" );
        	respuesta.write( "                              </table></td>\r\n" );
        	respuesta.write( "                              <td width=\"82\">&nbsp;</td>\r\n" );
        	respuesta.write( "                              </tr>\r\n" );
        	respuesta.write( "                               <tr>\r\n" );
        	respuesta.write( "                              <td width=\"42\">&nbsp;</td>\r\n" );
        	respuesta.write( "                              <td width=\"25\">&nbsp;</td>\r\n" );
        	respuesta.write( "                              <td width=\"543\">\r\n" );
        	respuesta.write( "                              <td width=\"543\">\r\n" );
        	respuesta.write( "                              <form method=\"POST\" action=\"index.htm\"><input type=\"submit\" value=\"Finalizar\" name=\"B2\" class=\"normal\"></form></td>\r\n" );
        	respuesta.write( "                              <td width=\"82\"></td>\r\n" );
        	respuesta.write( "                              </tr>\r\n" );
        	respuesta.write( "                               </table>\r\n" );
        }
    }
}

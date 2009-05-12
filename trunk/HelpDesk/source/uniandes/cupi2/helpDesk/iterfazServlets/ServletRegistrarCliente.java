package uniandes.cupi2.helpDesk.iterfazServlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uniandes.cupi2.helpDesk.mundo.HelpDesk;

/**
 * Servlet para el manejo 
 */
@SuppressWarnings("serial")
public class ServletRegistrarCliente extends ServletTemplate
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
        return "Registro Exitoso";
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
    
        HelpDesk mundo = HelpDesk.getInstance(null);
        
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
        	try {
				mundo.crearCliente(nombre, login, password, tipo, email);
				mundo.guardar(HelpDesk.RUTA_ARCHIVO);
			} catch (Exception e) {
				imprimirMensajeError(respuesta, "Error al crear el cliente.");
				
			}
        	
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
        	respuesta.write( "                              <form method=\"POST\" action=\"index.html\"><input type=\"submit\" value=\"Finalizar\" name=\"B2\" class=\"normal\"></form></td>\r\n" );
        	respuesta.write( "                              <td width=\"82\"></td>\r\n" );
        	respuesta.write( "                              </tr>\r\n" );
        	respuesta.write( "                               </table>\r\n" );
        }
    }
}

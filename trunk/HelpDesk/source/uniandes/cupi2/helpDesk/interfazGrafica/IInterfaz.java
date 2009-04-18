package uniandes.cupi2.helpDesk.interfazGrafica;

import java.util.Date;

import javax.swing.JFrame;

import uniandes.cupi2.helpDesk.interfazMundo.IIterador;
import uniandes.cupi2.helpDesk.interfazMundo.ITicket;
import uniandes.cupi2.helpDesk.interfazMundo.IUsuario;

public interface IInterfaz {

	IUsuario darUsuario(String text, String actionCommand);

	void iniciarSesionAdministrador(String text);

	void iniciarSesion(IUsuario temp);

	void crearCliente(String text);

	JFrame darJFrame();

	IIterador darListaIncidentes(boolean selected, Date date);

	void opcion1();

	void opcion2();

	IIterador darListaTicketsEntreFechas(Date date, Date date2);

	int darNumeroSinAtender();

	int darNumeroSiendoAtendidos();

	int darNumeroCerrados();

	IIterador darListaTickets();

	String descifrar(ITicket ticketActual);

	void atender(ITicket ticketActual);

	void cerrarTicket(ITicket ticket, String text);

	void calificar(ITicket ticketActual, String selectedItem);

	IUsuario nuevoCliente(String nombre, String text, String selectedItem);

	void reapertura(ITicket ticketActual, String text, IUsuario usuario);

	IIterador darEmpleados();

	void nuevaSolicitud(int i, String text, boolean selected);

}

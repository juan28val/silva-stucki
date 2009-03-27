package uniandes.cupi2.helpDesk.interfazMundo;

import java.util.Date;

public interface IIncidente {

	public Date darFecha();
	
	public IUsuario darEmpleado();
	
	public IUsuario darCliente();
	
	public ITicket darTicket();
	
	public String darComentario();
}

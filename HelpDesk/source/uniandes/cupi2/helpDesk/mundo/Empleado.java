package uniandes.cupi2.helpDesk.mundo;

import java.util.ArrayList;
import java.util.Date;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import uniandes.cupi2.helpDesk.interfazMundo.ITicket;
import uniandes.cupi2.helpDesk.interfazMundo.IUsuario;

public class Empleado implements IUsuario {
	
	private int id;
	private String nombre;
	private String login;
	private String password;
	private Empleado siguiente;
	private Empleado anteriorDelMes;
	private Empleado siguienteDelMes;
	private int tipo;
	private ArrayList<Integer> listaTickets;
	private int sumaCalificacion;
	private int incidentes;
	private byte clave;

	public Empleado(int id, String nombre, String login, String password, Empleado siguiente, int tipo, int sumaCalificacion, byte clave, int incidentes)
	{
		this.id = id;
		this.nombre = nombre;
		this.login = login;
		this.password = password;
		this.siguiente = siguiente;
		listaTickets = new ArrayList<Integer>();
		this.tipo = tipo; 
		this.sumaCalificacion = sumaCalificacion;
		this.clave = clave;
		this.incidentes = incidentes;
		
		verificarInvariante();
	}
	
	public byte darClave()
	{
		return clave;
	}
	
	public void cambiarSiguiente(Empleado siguiente)
	{
		this.siguiente = siguiente;
		verificarInvariante();

	}
	
	public Empleado darSiguiente()
	{
		return siguiente;
	}

	public String darNombre() {
		return nombre;
	}

	public int darTipo() {
		return tipo;
	}
	
	public String toString()
	{
		return nombre;
	}

	
	public Empleado asignarTicket(ITicket ticket)
	{
		if(ticket.darTipo() == tipo)
		{
			listaTickets.add(ticket.darId());
			return this;
		}
		if(siguiente == null)
			return null;
		Empleado encargado = siguiente.asignarTicket(ticket);
		if(encargado != null && encargado.darNombre().equals(siguiente.darNombre()))
			siguiente = siguiente.darSiguiente();
		return encargado;
	}
	
	public void agregarTicket(int ticket) {
		listaTickets.add(ticket);
		verificarInvariante();		
	}
	
	/**
	 * cambiarCalificacion
	 * Suma la diferencia de calificacion del ticket, y
	 * revisa si esta suma modifica su posicion en la 
	 * lista de empleados del mes, en una u otra direccion,
	 * y dado tal caso, cambia las referencias acordemente.
	 * @param valor: diferencia de calificacion
	 */
	public void cambiarCalificacion(int valor, Empleado primerEmpleadoDelMes)
	{
		Empleado posicion = null;
		sumaCalificacion += valor;
		
		if(siguienteDelMes != null && siguienteDelMes.darCalificacion() > sumaCalificacion)
		{
			posicion = buscarPosicionAdelante(valor);
			if(anteriorDelMes != null)
				anteriorDelMes.cambiarSiguienteDelMes(siguienteDelMes);
			else
				primerEmpleadoDelMes = siguienteDelMes;
			siguienteDelMes.cambiarAnteriorDelMes(anteriorDelMes);
			anteriorDelMes = posicion;
			siguienteDelMes = posicion.darSiguienteDelMes();
			posicion.cambiarSiguienteDelMes(this);
			if(siguienteDelMes != null)
				siguienteDelMes.cambiarAnteriorDelMes(this);
		}
		else if(anteriorDelMes != null && anteriorDelMes.darCalificacion() < sumaCalificacion)
		{
			posicion = buscarPosicionAtras(valor);
			if(siguienteDelMes != null)
				siguienteDelMes.cambiarAnteriorDelMes(anteriorDelMes);
			anteriorDelMes.cambiarSiguienteDelMes(siguienteDelMes);
			siguienteDelMes = posicion;
			anteriorDelMes = posicion.darAnteriorDelMes();
			if(posicion.darAnteriorDelMes()==null)
				primerEmpleadoDelMes = this;
			posicion.cambiarAnteriorDelMes(this);
			if(anteriorDelMes != null)
				anteriorDelMes.cambiarSiguienteDelMes(this);		
		}
	}
	
	public Empleado darEmpleadoDelMes()
	{
		if(anteriorDelMes == null)
			return this;
		return anteriorDelMes.darEmpleadoDelMes();
	}
	
	public Empleado darAnteriorDelMes()
	{
		return anteriorDelMes;
	}
	
	public Empleado darSiguienteDelMes()
	{
		return siguienteDelMes;
	}
	
	public void cambiarSiguienteDelMes(Empleado empleado)
	{
		siguienteDelMes = empleado;
	}
	
	public void cambiarAnteriorDelMes(Empleado empleado)
	{
		anteriorDelMes = empleado;
	}
	
	public int darCalificacion()
	{
		return sumaCalificacion;
	}
	
	public Empleado buscarPosicionAdelante(int valor)
	{
		if(siguienteDelMes == null || siguienteDelMes.darCalificacion() <= valor)
			return this;
		return siguienteDelMes.buscarPosicionAdelante(valor);
	}
	
	public Empleado buscarPosicionAtras(int valor)
	{
		if(anteriorDelMes == null || anteriorDelMes.darCalificacion() >= valor)
			return this;
		return anteriorDelMes.buscarPosicionAtras(valor);
	}

	public int darSumaCalificacion()
	{
		return sumaCalificacion;
	}
	
	public boolean esEmpleado() {
		return true;
	}
	
	private void verificarInvariante()
	{
		assert nombre != null && !nombre.equals("") : "Nombre inv‡lido";
		assert siguiente != null : "Siempre deber’a tener un siguiente";
		assert tipo == EMPLEADO_QUEJA || tipo == EMPLEADO_RECLAMO || tipo == EMPLEADO_SOLICITUD : "El tipo es incorrecto";
	}

	public Date darFechaAtencion() {
		// nunca se usa
		return null;
	}

	public IUsuario darEmpleado(String nombre) {
		
		if( this.nombre.equalsIgnoreCase(nombre) )
			return this;
		
		return siguiente==null?null:siguiente.darEmpleado(nombre);
	}
	
	public ArrayList<Integer> darListaTickets()
	{
		return listaTickets;
	}

	public void incidente(Empleado primerEmpleadoDelMes) {
		incidentes ++;
		cambiarCalificacion(ITicket.INCIDENTE, primerEmpleadoDelMes);
	}

	public int darNumeroIncidentes() {
		return incidentes;
	}

	public Empleado menosIncidentes() {
		if(siguiente!=null){
			Empleado empleado = siguiente.menosIncidentes();
			if(empleado.darNumeroIncidentes()<incidentes)
				return empleado;
		}
		return this;
	}

	public void guardar(Element elementoEmpleados,Document documento) {
		Element e = documento.createElement("empleado");
		e.setAttribute("id", String.valueOf(id));
		e.setAttribute("nombre", nombre);
		e.setAttribute("login", login);
		e.setAttribute("password", password);
		e.setAttribute("clave", String.valueOf(clave));
		e.setAttribute("tipo", String.valueOf(tipo));
		e.setAttribute("calificacion", String.valueOf(sumaCalificacion));
		e.setAttribute("incidentes", String.valueOf(incidentes));
		for(int i=0; i<listaTickets.size(); i++)
		{
			Element t = documento.createElement("ticket");
			t.setAttribute("id", String.valueOf(listaTickets.get(i)));
			e.appendChild(t);
		}
		elementoEmpleados.appendChild(e);
		if(siguiente != null)
			siguiente.guardar(elementoEmpleados, documento);
	}

	public int darId() {
		return id;
	}

}

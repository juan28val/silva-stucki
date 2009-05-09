package uniandes.cupi2.helpDesk.mundo;

import java.util.ArrayList;
import java.util.Date;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import uniandes.cupi2.collections.tablaHashing.tablaHashingDinamica.TablaHashingDinamica;
import uniandes.cupi2.helpDesk.interfazMundo.IUsuario;


public class Cliente implements IUsuario {
	
	private int id;
	private String nombre;
	private String login;
	private String password;
	private int tipo;
	private String email;
	private ArrayList<Integer> listaTickets;
	private Cliente siguiente;
	private Cliente anteriorAtendido;
	private Cliente siguienteAtendido;
	private Date fechaPrimeraAtencion;
	
	public Cliente(int id, String nombre, String login, String password, int tipo, String email, Cliente siguiente, Date fechaPrimeraAtencion)
	{
		this.id = id;
		this.nombre = nombre;
		this.login = login;
		this.password = password;
		this.tipo = tipo;
		this.email = email;
		this.siguiente = siguiente;
		this.fechaPrimeraAtencion = fechaPrimeraAtencion;
		listaTickets = new ArrayList<Integer>();
		
		verificarInvariante();
	}
	
	public int darId()
	{
		return id;
	}

	public String darNombre() {
		return nombre;
	}
	public ArrayList<Integer> darListaTickets()
	{
		return listaTickets;
	}


	public int darTipo() {
		return tipo;
	}
	
	public String darEmail()
	{
		return email;
	}

	public void agregarTicket(int ticket) {
		listaTickets.add(ticket);
		verificarInvariante();
	}

	private void verificarInvariante()
	{
		assert nombre != null && !nombre.equals("") && email != null && !email.equals("") : "Nombre o E-mail inv‡lidos";
		assert tipo == CLIENTE_ESTUDIANTE || tipo == CLIENTE_PROFESOR || tipo == CLIENTE_PERSONAL_ADMINISTRATIVO : "Tipo de usuario incorrecto";
	}

	public void cambiarSiguienteAtendido(Cliente cliente) {
		siguienteAtendido = cliente;
		
	}

	public void cambiarSiguiente(Cliente siguiente) {
		this.siguiente = siguiente;
	}

	public boolean esEmpleado() {
		return false;
	}
	
	public int darSumaCalificacion()
	{
		return Integer.MAX_VALUE;
	}

	public Date darFechaAtencion() {
		return fechaPrimeraAtencion;
	}

	public void cambiarFechaAtencion(Date darFechaAtencion) {
		fechaPrimeraAtencion =	darFechaAtencion;	
	}

	public Cliente darSiguienteAtendido() {
		
		return siguienteAtendido;
	}

	public Cliente darAnteriorAtendido() {
		return anteriorAtendido;
	}
	
	public Cliente darSiguiente() {
		return siguiente;
	}
	
	public void cambiarAnteriorAtendido(Cliente anteriorAtendido) {
		this.anteriorAtendido=anteriorAtendido;
	}

	public IUsuario darCliente(String nombre) {
		
		if( this.nombre.equalsIgnoreCase(nombre) )
			return this;
		
		return siguiente==null?null:siguiente.darCliente(nombre);
	}

	public void agregarClienteAtendido(Cliente cliente) {
		if(siguienteAtendido.darFechaAtencion().after(cliente.darFechaAtencion()))
		{
			cliente.cambiarSiguienteAtendido(siguienteAtendido);
			cliente.cambiarAnteriorAtendido(this);
			siguienteAtendido.cambiarAnteriorAtendido(cliente);
			siguienteAtendido = cliente;
		}
		else siguienteAtendido.agregarClienteAtendido(cliente);
	}

	public void guardar(Element elementoClientes, Document documento, TablaHashingDinamica<Integer,Ticket> tablaTickets) {
		Element e = documento.createElement("cliente");
		e.setAttribute("id", String.valueOf(id));
		e.setAttribute("nombre", nombre);
		e.setAttribute("login", login);
		e.setAttribute("password", password);
		e.setAttribute("tipo", String.valueOf(tipo));
		e.setAttribute("email", email);
		e.setAttribute("fechaAtencion", fechaPrimeraAtencion == null ? "" : String.valueOf(fechaPrimeraAtencion.getTime()));
		for(int i=0; i<listaTickets.size(); i++)
		{
			Element t = documento.createElement("ticket");
			Date fechaAtencion = tablaTickets.dar(listaTickets.get(i)).darFechaAtencion();
			Date fechaCierre = tablaTickets.dar(listaTickets.get(i)).darFechaCierre();
			t.setAttribute("tipo", String.valueOf(tablaTickets.dar(listaTickets.get(i)).darTipo()));
			t.setAttribute("comentarioCliente", tablaTickets.dar(listaTickets.get(i)).darComentarioCliente());
			t.setAttribute("calificacion", String.valueOf(tablaTickets.dar(listaTickets.get(i)).darCalificacion()));
			t.setAttribute("comentarioEmpleado", tablaTickets.dar(listaTickets.get(i)).darComentarioEmpleado());
			t.setAttribute("fechaCreacion", String.valueOf(tablaTickets.dar(listaTickets.get(i)).darFechaCreacion().getTime()));
			t.setAttribute("fechaAtencion", fechaAtencion == null ? "" : String.valueOf(fechaAtencion.getTime()));
			t.setAttribute("fechaCierre", fechaCierre == null ? "" : String.valueOf(fechaCierre.getTime()));
			t.setAttribute("experto", String.valueOf(tablaTickets.dar(listaTickets.get(i)).atendidoPorExperto()));
			t.setAttribute("reabierto", String.valueOf(tablaTickets.dar(listaTickets.get(i)).reabierto()));
			t.setAttribute("cifrado", String.valueOf(tablaTickets.dar(listaTickets.get(i)).estaCifrado()));
			t.setAttribute("id", String.valueOf(tablaTickets.dar(listaTickets.get(i)).darId()));
			e.appendChild(t);
		}
		elementoClientes.appendChild(e);
		if(siguiente != null)
			siguiente.guardar(elementoClientes, documento, tablaTickets);
	}
	
	public String toString()
	{
		return nombre;
	}
}

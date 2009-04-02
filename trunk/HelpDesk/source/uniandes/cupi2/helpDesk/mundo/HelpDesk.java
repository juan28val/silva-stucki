package uniandes.cupi2.helpDesk.mundo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.Observable;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xerces.parsers.DOMParser;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import uniandes.cupi2.collections.arbol.arbol2_3.Arbol2_3;
import uniandes.cupi2.collections.iterador.Iterador;
import uniandes.cupi2.collections.lista.Lista;
import uniandes.cupi2.collections.tablaHashing.tablaHashingDinamica.TablaHashingDinamica;
import uniandes.cupi2.collections.trie.ElementoExisteException;
import uniandes.cupi2.collections.trie.PalabraInvalidaException;
import uniandes.cupi2.collections.trie.Trie;
import uniandes.cupi2.helpDesk.interfazMundo.IHelpDesk;
import uniandes.cupi2.helpDesk.interfazMundo.IIterador;
import uniandes.cupi2.helpDesk.interfazMundo.ITicket;
import uniandes.cupi2.helpDesk.interfazMundo.IUsuario;
import uniandes.cupi2.mailer.*;

/**
 *  Clase principal de la implementación.
 */
@SuppressWarnings("deprecation")
public class HelpDesk extends Observable implements IHelpDesk {

	//-----------------------------------------------------------------
    // Constantes
    //-----------------------------------------------------------------
    
	private static final String EMAIL_EMPLEADO_HELPDESK = "empleadohelpdesk@lavabit.com";

	private static final String EMAIL_LOGIN = "empleadohelpdesk";

	private static final String EMAIL_SERVIDOR = "smtp.lavabit.com";

	private static final String EMAIL_PASSWORD = "nicolas";

	
    //-----------------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------------
	
	/**
	 * Lista de tickets, ordenados por fecha de creación
	 */
	private TablaHashingDinamica<Integer, Ticket> tablaTickets;
	

	
	private IUsuario usuarioActual;
	
	private Empleado primerEmpleado;
	
	private Empleado ultimoEmpleado;
	
	private Empleado empleadoDelMes;
	
	private Cliente primerCliente;	
	
	private Cliente primerClienteAtendido;
	
	private Cliente ultimoClienteAtendido;
	
	private TablaHashingDinamica<Integer, IUsuario> tablaUsuarios;
	
	private Arbol2_3<Incidente> arbolIncidentes;
	
	private int numeroTicketsSinAtender;
	
	private int numeroTicketsSiendoAtendidos;
	
	private int numeroTicketsCerrados;
	
	/**
	 * Estructura Trie que permite referenciar los nombres en funcion de sus prefijos
	 */
	private Trie<Empleado> prefijosEmpleados;

	private int idTickets;
	
	private int idUsuarios;
	

    //-----------------------------------------------------------------
    // Constructores
    //-----------------------------------------------------------------

    /**
     *  Metodo constructor de la clase principal de la presente implementacion.
     *  Inicializa en ceros los atributos correspondientes.
     *  Este metodo es invocado unicamente si ocurre algun error abriendo el
     *  archivo de persistencia (si este no existe)
     */
	public HelpDesk( )
    {
    		tablaTickets = new TablaHashingDinamica<Integer, Ticket>();
    		tablaUsuarios = new TablaHashingDinamica<Integer, IUsuario>();
    		arbolIncidentes = new Arbol2_3<Incidente>();
    		prefijosEmpleados = new Trie<Empleado>();
    		numeroTicketsSinAtender = 0;
    		numeroTicketsSiendoAtendidos = 0;
    		numeroTicketsCerrados = 0;
    		idTickets = 10000;
    		idUsuarios = 25000;
    }
	
	public HelpDesk( String ruta ) throws Exception
	{
		tablaTickets = new TablaHashingDinamica<Integer, Ticket>();
		tablaUsuarios = new TablaHashingDinamica<Integer, IUsuario>();
		arbolIncidentes = new Arbol2_3<Incidente>();
		prefijosEmpleados = new Trie<Empleado>();
		
		BufferedReader lector = new BufferedReader(new FileReader(ruta));
		String cadena;
		StringBuffer bufete = new StringBuffer();
		while((cadena = lector.readLine()) != null)
			bufete.append(cadena);
		cadena = bufete.toString();
		DOMParser parce = new DOMParser();
		parce.parse(new InputSource(new StringReader(cadena)));
		Document doc = parce.getDocument();
		Element raiz = doc.getDocumentElement();
		idTickets = Integer.parseInt(raiz.getAttribute("idTickets"));
		idUsuarios = Integer.parseInt(raiz.getAttribute("idUsuarios"));
		numeroTicketsSinAtender = Integer.parseInt(raiz.getAttribute("ticketsSinAtender"));
		numeroTicketsSiendoAtendidos = Integer.parseInt(raiz.getAttribute("ticketsSiendoAtendidos"));
		numeroTicketsCerrados = Integer.parseInt(raiz.getAttribute("ticketsCerrados"));
		NodeList hijos = raiz.getChildNodes();
		Node clientes = hijos.item(0);
		Node empleados = hijos.item(1);
		Node incidentes = hijos.item(2);
		
		cargarClientes(clientes);
		cargarEmpleados(empleados);
		cargarIncidentes(incidentes);
	}

 	private void cargarClientes(Node clientes) {
		NodeList hijos = clientes.getChildNodes();
		for(int i=0; i<hijos.getLength(); i++)
		{
			Element hijo = (Element)hijos.item(i);
			Cliente cliente = new Cliente(Integer.parseInt(hijo.getAttribute("id")), hijo.getAttribute("nombre"), Integer.parseInt(hijo.getAttribute("tipo")), hijo.getAttribute("email"), primerCliente, hijo.getAttribute("fechaAtencion").equals("") ? null : new Date(Long.parseLong(hijo.getAttribute("fechaAtencion"))));
			tablaUsuarios.agregar(cliente.darId(), cliente);
			primerCliente = cliente;
			if(cliente.darFechaAtencion() != null)
				if(primerClienteAtendido == null)
				{
					primerClienteAtendido = cliente;
					ultimoClienteAtendido = cliente;
				}
				else
				{
					if(ultimoClienteAtendido.darFechaAtencion().before(cliente.darFechaAtencion()))
					{
						ultimoClienteAtendido.cambiarSiguienteAtendido(cliente);
						cliente.cambiarAnteriorAtendido(ultimoClienteAtendido);
						ultimoClienteAtendido = cliente;
					}
					else if(primerClienteAtendido.darFechaAtencion().after(cliente.darFechaAtencion()))
					{
						primerClienteAtendido.cambiarAnteriorAtendido(cliente);
						cliente.cambiarSiguienteAtendido(primerClienteAtendido);
						primerClienteAtendido = cliente;
					}
					else
					{
						primerClienteAtendido.agregarClienteAtendido(cliente);
					}
				}
			for(int j=0; j<hijo.getChildNodes().getLength(); j++)
			{
				Element nieto = (Element) ((NodeList)hijo.getChildNodes()).item(j);
				Ticket ticket = new Ticket(Integer.parseInt(nieto.getAttribute("tipo")), cliente, nieto.getAttribute("comentarioCliente"), Integer.parseInt(nieto.getAttribute("calificacion")), nieto.getAttribute("comentarioEmpleado"), new Date(Long.parseLong(nieto.getAttribute("fechaCreacion"))), nieto.getAttribute("fechaAtencion").equals("") ? null : new Date(Long.parseLong(nieto.getAttribute("fechaAtencion"))), nieto.getAttribute("fechaCierre").equals("") ? null : new Date(Long.parseLong(nieto.getAttribute("fechaCierre"))), Boolean.valueOf(nieto.getAttribute("experto")), Boolean.valueOf(nieto.getAttribute("reabierto")), Boolean.valueOf(nieto.getAttribute("cifrado")), Integer.parseInt(nieto.getAttribute("id")));
				cliente.agregarTicket(Integer.parseInt(nieto.getAttribute("id")));
				tablaTickets.agregar(ticket.darId(), ticket);				
			}
		}
	}

	private void cargarEmpleados(Node empleados) throws ElementoExisteException, PalabraInvalidaException {
		NodeList hijos = empleados.getChildNodes();
		for(int i=0; i<hijos.getLength(); i++)
		{
			Element hijo = (Element)hijos.item(i);
			Empleado empleado = new Empleado(Integer.parseInt(hijo.getAttribute("id")), hijo.getAttribute("nombre"), primerEmpleado, Integer.parseInt(hijo.getAttribute("tipo")), Integer.parseInt(hijo.getAttribute("calificacion")), Byte.valueOf(hijo.getAttribute("clave")), Integer.parseInt(hijo.getAttribute("incidentes")));
			tablaUsuarios.agregar(empleado.darId(), empleado);
			empleado.cambiarSiguienteDelMes(primerEmpleado);
			if(primerEmpleado!=null)
				primerEmpleado.cambiarAnteriorDelMes(empleado);
			else ultimoEmpleado = empleado;
   			empleadoDelMes = empleado;
   			primerEmpleado = empleado;
			primerEmpleado.cambiarCalificacion(0, empleadoDelMes);
			for(int j=0; j<hijo.getChildNodes().getLength(); j++)
			{
				Element nieto = (Element) ((NodeList)hijo.getChildNodes()).item(j);
				empleado.agregarTicket(Integer.parseInt(nieto.getAttribute("id")));
				tablaTickets.dar(Integer.parseInt(nieto.getAttribute("id"))).cambiarEmpleado(empleado);
			}
			prefijosEmpleados.insertar(empleado);
		}
	}
	
 	private void cargarIncidentes(Node incidentes) throws Exception {
 		NodeList hijos = incidentes.getChildNodes();
 		for(int i=0; i<hijos.getLength(); i++)
 		{
 			Element hijo = (Element)hijos.item(i);
 			Incidente incidente = new Incidente(new Date(Long.parseLong(hijo.getAttribute("fecha"))), (Empleado)tablaUsuarios.dar(Integer.parseInt(hijo.getAttribute("empleado"))), (Cliente)tablaUsuarios.dar(Integer.parseInt(hijo.getAttribute("cliente"))), tablaTickets.dar(Integer.parseInt(hijo.getAttribute("ticket"))), hijo.getAttribute("comentario"));
 			arbolIncidentes.insertar(incidente);
 		}
 	}
 	//-----------------------------------------------------------------
    // Métodos
    //-----------------------------------------------------------------
    
	public void guardar( String ruta ) throws Exception
    {  	
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( ); 
    	DocumentBuilder db = dbf.newDocumentBuilder( );
      	Document documento = db.newDocument( ); 

    	Element elementoRaiz = documento.createElement( "helpDesk" );
    	
    	elementoRaiz.setAttribute("idTickets", String.valueOf(idTickets));
    	elementoRaiz.setAttribute("idUsuarios", String.valueOf(idUsuarios));
    	elementoRaiz.setAttribute("ticketsSinAtender", String.valueOf(numeroTicketsSinAtender));
    	elementoRaiz.setAttribute("ticketsSiendoAtendidos", String.valueOf(numeroTicketsSiendoAtendidos));
    	elementoRaiz.setAttribute("ticketsCerrados", String.valueOf(numeroTicketsCerrados));
    	
    	Element elementoClientes = documento.createElement("clientes");
    	if(primerCliente != null)
    		primerCliente.guardar(elementoClientes,documento, tablaTickets);
    	elementoRaiz.appendChild(elementoClientes);
    	
    	Element elementoEmpleados = documento.createElement("empleados");
    	if(primerEmpleado != null)
    		primerEmpleado.guardar(elementoEmpleados,documento);
    	elementoRaiz.appendChild(elementoEmpleados);
    	
    	Element elementoIncidentes = documento.createElement("incidentes");
    	Iterador<Incidente> iterador = arbolIncidentes.inorden();
    	while(iterador.haySiguiente())
    	{
    		Incidente incidente = iterador.darSiguiente();
    		Element elementoIncidente = documento.createElement("incidente");
    		elementoIncidente.setAttribute("fecha", incidente.darFecha() == null ? "" : String.valueOf(incidente.darFecha().getTime()));
    		elementoIncidente.setAttribute("empleado", String.valueOf(incidente.darEmpleado().darId()));
    		elementoIncidente.setAttribute("cliente", String.valueOf(incidente.darCliente().darId()));
    		elementoIncidente.setAttribute("ticket", String.valueOf(incidente.darTicket().darId()));
    		elementoIncidente.setAttribute("comentario", incidente.darComentario());
    		elementoIncidentes.appendChild(elementoIncidente);
    	}
    	elementoRaiz.appendChild(elementoIncidentes);
    	
    	documento.appendChild( elementoRaiz );
    	StringWriter stringWriter = new StringWriter( ); 
    	OutputFormat format = new OutputFormat( documento); 
    	XMLSerializer serial = new XMLSerializer( stringWriter, format ); 
    	serial.serialize( documento ); 
    	String xml = stringWriter.toString( ); 
    	FileOutputStream fos = new FileOutputStream( ruta ); 
    	fos.write( xml.getBytes( ) ); 
    	fos.close( );
    }
    
	/**
	 * pre: el usuario actual es un cliente
	 */
	public void nuevaSolicitud(int tipo, String comentarioCliente, boolean cifrado) throws Exception {
		idTickets++;
		Ticket ticket = new Ticket(tipo, (Cliente)usuarioActual, comentarioCliente, 0, null, new Date(), null, null, true, false, cifrado, idTickets);
		asignarTicket(ticket);
		((Cliente) usuarioActual).agregarTicket(ticket.darId());
		tablaTickets.agregar(ticket.darId(), ticket);
		cambiarNumeroTicketsSinAtender(numeroTicketsSinAtender+1);
	}

	/**
	 * Metodo que contiene el algoritmo de asignacion te tickets.
	 * Llama al homonimo metodo recursivo de la clase Empleado, y se
	 * encarga de trasladar al empleado seleccionado al final de la lista.
	 * @param ticket el ticket que sera asignado
	 */
	public void asignarTicket(Ticket ticket)
	{
		Empleado encargado = primerEmpleado.asignarTicket(ticket);
		if(encargado == null)
		{
			encargado = primerEmpleado;
			encargado.agregarTicket(ticket.darId());
		}
		if(encargado == primerEmpleado)
			primerEmpleado = encargado.darSiguiente();
		ultimoEmpleado.cambiarSiguiente(encargado);
		encargado.cambiarSiguiente(null);
		ultimoEmpleado = encargado;
		ticket.asignar(encargado);
		if(ticket.estaCifrado())
			ticket.cifrar(encargado.darClave());
	}

	/**
	 * pre: el usuario actual es un empleado
	 */
	public void atenderTicket(ITicket ticket) throws Exception {
		((Ticket)ticket).atender();
		cambiarNumeroTicketsSinAtender(numeroTicketsSinAtender-1);
		cambiarNumeroTicketsSiendoAtendidos(numeroTicketsSiendoAtendidos+1);
		if(primerClienteAtendido == null )
		{
			primerClienteAtendido = ((Ticket)ticket).darCliente();
			ultimoClienteAtendido = primerClienteAtendido;
		}
		else if(((Ticket)ticket).darCliente().darFechaAtencion() == null)
		{
			ultimoClienteAtendido.cambiarSiguienteAtendido(((Ticket)ticket).darCliente());
			((Ticket)ticket).darCliente().cambiarAnteriorAtendido(ultimoClienteAtendido);
			ultimoClienteAtendido = ((Ticket)ticket).darCliente();
		}
		((Ticket)ticket).darCliente().cambiarFechaAtencion(ticket.darFechaAtencion());
		enviarEmail(ticket, ticket.darFechaAtencion().toString() + "\n\n\nEstimado " + ticket.darNombreCliente() + ":\n\nSu ticket esta siendo atendido por "+ticket.darNombreEmpleado() + ", quien en breve resolverá sus inquietudes.\n\n\nGracias por preferirnos.\n\n\n\n\n\nCupi2HelpDesk");
		
	}

	/**
	 * calificarTicket
	 * LLama al método calificar del ticket, y dado el caso de que
	 * haya un nuevo empleado del mes, llama al metodo recursivo
	 * darEmpleadoDelMes para que encuentre al nuevo empleado del mes,
	 * y lo asigne como tal.
	 * @param ticket: el ticket que esta siendo calificado, no es nulo
	 * @param calificacion: la calificacion dada segun las constantes
	 * definidas en ITicket
	 */
	public void calificarTicket(ITicket ticket, int calificacion) {
		((Ticket)ticket).calificar(calificacion);
		if(empleadoDelMes.darAnteriorDelMes() != null)
			empleadoDelMes = empleadoDelMes.darEmpleadoDelMes();
	}

	public void cerrarTicket(ITicket ticket, String comentario) throws Exception {
		
		cambiarNumeroTicketsSiendoAtendidos(numeroTicketsSiendoAtendidos-1);
		cambiarNumeroTicketesCerrados(numeroTicketsCerrados+1);
		((Ticket)ticket).cerrar(comentario);
		enviarEmail(ticket, ticket.darFechaAtencion().toString() + "\n\n\nEstimado " + ticket.darNombreCliente() + ":\n\nSu ticket ha sido cerrado por " + ticket.darNombreEmpleado()+", quien le remite estas humildes palabras: \n\n"+ticket.darComentarioEmpleado() + "\n\n\nGracias por preferirnos.\n\n\n\n\n\nCupi2HelpDesk");
		
	}

	/**
	 * pre: el nombre del cliente no existe
	 */
	public IUsuario crearCliente(String nombreCliente, int tipoCliente, String email) {
		idUsuarios++;
		Cliente cliente = new Cliente(idUsuarios, nombreCliente, tipoCliente, email, primerCliente, null);
		tablaUsuarios.agregar(idUsuarios, cliente);
		primerCliente = cliente;
		
		return cliente;
	
	}

	public IIterador darEmpleados() {
		
		return new IteradorEmpleados(primerEmpleado);
	}
	
	public IIterador darEmpleadosDelMes() {
	
		return new IteradorEmpleadosDelMes(empleadoDelMes);
	}

	public IIterador darListaPersonasAtendidas() {
	
		return new IteradorClientesAtendidos(primerClienteAtendido);
	}

	public IIterador darListaTicketsUsuarioActual()
	{
		return new IteradorTickets( usuarioActual,usuarioActual.esEmpleado()?new IteradorClientes(primerCliente):new IteradorEmpleados(primerEmpleado) ,tablaTickets );
	}
	
	public IIterador darListaTicketsEntreFechas(Date fecha1,Date fecha2) {
		return new IteradorTicketsEntreFechas(darEmpleados(), fecha1, fecha2, tablaTickets);
	}
	
	public IIterador darListaIncidentes(boolean antes, Date fecha) {
		return new IteradorIncidentes(antes, fecha, arbolIncidentes);
	}

	public IUsuario darUsuario(String nombre, int tipoUsuario) {
		
		if( tipoUsuario == IUsuario.EMPLEADO_QUEJA || tipoUsuario == IUsuario.EMPLEADO_RECLAMO || tipoUsuario == IUsuario.EMPLEADO_SOLICITUD)
		{
			if( primerEmpleado==null )
				return null;
			
			return primerEmpleado.darEmpleado( nombre );
		}
		
		else
		{
			if( primerCliente==null )
				return null;
			
			return primerCliente.darCliente( nombre );
		}
	}

	public void iniciarSesion( IUsuario usuario ) {

		usuarioActual = usuario;
	
	}

	/**
	 * Se encarga de efectuar los procedimientos necesarios para el correcto
	 * envio de correo electronico de notificacion.
	 * @param ticket: el ticket del cual es necesario saber el tipo
	 * @param mensage: el mensaje a ser enviado al usuario
	 * @throws Exception: se dispara si ocurre un error de envio
	 */
	public void enviarEmail(ITicket ticket, String mensage) throws Exception
	{
        Email email = new Email( );
        int tipo = ((Ticket)ticket).darTipo();
        email.cambiarTitulo( "El estado de su " + (tipo==Ticket.TIPO_QUEJA?"queja":(tipo==Ticket.TIPO_RECLAMO?"reclamo":"solicitud" )) + " ha cambiado" );
        email.cambiarRemitente( EMAIL_EMPLEADO_HELPDESK );
        email.agregarDestinatario( ((Ticket)ticket).darCliente().darEmail() );
        email.cambiarMensaje( mensage );
        try
        {
            email.enviar( EMAIL_SERVIDOR, EMAIL_LOGIN, EMAIL_PASSWORD );
        }
        catch( Exception e )
        {
	            throw new Exception( "Error al enviar el mensaje a la cuenta de correo del cliente", e );
        }
	}
	
	public IUsuario darUsuarioActual() {
	
		return usuarioActual;
	}
	public void cargarListaEmpleados(File archivo) throws Exception
	{
		Properties lista = new Properties();
    	FileInputStream entrada = new FileInputStream(archivo);
   		lista.load(entrada);
   		int tamano = Integer.parseInt(lista.getProperty("NumeroEmpleados"));
   		if(tamano > 0)
   		{
   			idUsuarios++;
   			primerEmpleado = new Empleado(idUsuarios, lista.getProperty("Empleado0.nombre"), null, (lista.getProperty("Empleado0.tipo").equals("queja") ? IUsuario.EMPLEADO_QUEJA : (lista.getProperty("Empleado0.tipo").equals("reclamo") ? IUsuario.EMPLEADO_RECLAMO : IUsuario.EMPLEADO_SOLICITUD)), 0, (byte) (Math.random()*254+1), 0);
   			empleadoDelMes = primerEmpleado;
   			ultimoEmpleado = primerEmpleado;
   			prefijosEmpleados.insertar(primerEmpleado);
   			tablaUsuarios.agregar(idUsuarios, primerEmpleado);
   		}
   		if(tamano > 1)
   			for(int i=1; i<tamano; i++)
   			{
   				idUsuarios++;
   				Empleado empleado = new Empleado(idUsuarios, lista.getProperty("Empleado" + i + ".nombre"), primerEmpleado, lista.getProperty("Empleado" + i + ".tipo").equals("queja") ? IUsuario.EMPLEADO_QUEJA : (lista.getProperty("Empleado" + i + ".tipo").equals("reclamo") ? IUsuario.EMPLEADO_RECLAMO : IUsuario.EMPLEADO_SOLICITUD), 0, (byte) (Math.random()*254+1), 0);
   				empleado.cambiarSiguienteDelMes(primerEmpleado);
   				primerEmpleado.cambiarAnteriorDelMes(empleado);
   				empleadoDelMes = empleado;
   				primerEmpleado = empleado;
   				prefijosEmpleados.insertar(empleado);
   				tablaUsuarios.agregar(idUsuarios, empleado);
   			}
   		entrada.close();
	}
	
	public void reapertura(ITicket ticket, IUsuario empleado, String comentarioReapertura) throws Exception {
		Incidente incidente = new Incidente(new Date(), (Empleado)empleado, ((Ticket)ticket).darCliente(), (Ticket)ticket, comentarioReapertura); 
		((Ticket)ticket).cifrar(((Empleado)ticket.darEmpleado()).darClave());
		((Ticket)ticket).cifrar(((Empleado)empleado).darClave());
		ticket.darEmpleado().darListaTickets().remove((Integer)ticket.darId());
		((Ticket)ticket).reabrir();
		arbolIncidentes.insertar(incidente);
		((Ticket)ticket).cambiarFechaAtencion(new Date());
		((Ticket)ticket).cambiarFechaCierre(null);
		((Ticket)ticket).cambiarExperto(ticket.darTipo() == ((Empleado)empleado).darTipo());
		((Empleado)ticket.darEmpleado()).incidente(empleadoDelMes);
		((Ticket)ticket).cambiarEmpleado((Empleado)empleado);
		empleado.darListaTickets().add(ticket.darId());
		cambiarNumeroTicketesCerrados(numeroTicketsCerrados-1);
		cambiarNumeroTicketsSinAtender(numeroTicketsSinAtender+1);
		enviarEmail(ticket, ticket.darFechaAtencion().toString() + "\n\n\nEstimado " + ticket.darNombreCliente() + ":\n\nSu ticket ha sido reabierto a solicitud suya. Sera atendido por " + ticket.darNombreEmpleado()+", quien le envia sus mejores deseos.\n\n\nGracias por preferirnos.\n\n\n\n\n\nCupi2HelpDesk");
	}
	
	public String darEmpleadoMenosIncidentes() {
		if(primerEmpleado!=null){
			Empleado empleado = primerEmpleado.menosIncidentes();
			return empleado.darNombre()+": " + empleado.darNumeroIncidentes() + " incidentes.";
		}
		else
			return "No hay empleados en la empresa.";
	}
	
	public String descifrar(ITicket ticket) {	
		return ((Ticket)ticket).descifrar(((Empleado)usuarioActual).darClave());
	}
	
	public String darInfoTicket(int id) {
		Ticket ticket = tablaTickets.dar(id);
		if(ticket == null)
			return "No existe el ticket buscado.";
		String info = (ticket.darTipo() == 1 ? "Reclamo" : ticket.darTipo() == 2 ? "Queja" : "Solicitud") + " # " + ticket.darId()
				+ "\nCreado por " + ticket.darCliente().darNombre() + " en " + ticket.darFechaCreacion() + "\nAsignado a "
				+ ticket.darEmpleado().darNombre() + (ticket.atendidoPorExperto() ? "" : " (inexperto) " + "\nAtendido: "
				+ ticket.darFechaAtencion() + "\nCerrado: " + ticket.darFechaCierre() + "\nCalificacion: " + ticket.darCalificacion()
				+ (ticket.estaCifrado() ? "" : "\nComentario del cliente: " + ticket.darComentarioCliente() + "\nComentario del empleado: "
				+ ticket.darComentarioEmpleado() + "\n"));
		return info;
	}
	
	/**
	 * Devuelve un ticket, dado su id
	 * @param id: el id unico que identifica al ticket
	 * @return El ticket en cuestion
	 */
	public ITicket darTicket(int id)
	{
		return tablaTickets.dar(id);
	}
	
	/*
	 * Cambia el numero de tickets sin atender, y notifica de
	 * esto a los observadores.
	 */
    public void cambiarNumeroTicketsSinAtender( int nuevoNumero )
    {
        // Cambia el número
        numeroTicketsSinAtender = nuevoNumero;
        // Notifica a los observadores, informando el nuevo número
        setChanged( );
        notifyObservers( );
    }
    
    /**
     * Cambia el numero de tickets que estan presentemente siendo
     * atendidos, y notifica a los observadores.
     * @param nuevoNumero
     */
    public void cambiarNumeroTicketsSiendoAtendidos( int nuevoNumero )
    {
        // Cambia el número
    	numeroTicketsSiendoAtendidos = nuevoNumero;
        // Notifica a los observadores, informando el nuevo número
        setChanged( );
        notifyObservers( );
    }
    
    /**
     * Cambia el numero de tickets cerrados, y nuevamente notifica
     * de este cambio a los observadores.
     * @param nuevoNumero
     */
    public void cambiarNumeroTicketesCerrados( int nuevoNumero )
    {
        // Cambia el número
    	numeroTicketsCerrados = nuevoNumero;
        // Notifica a los observadores, informando el nuevo número
        setChanged( );
        notifyObservers( );
    }
	
	//-----------------------------------------------------------------
    // Puntos de Extensión
    //-----------------------------------------------------------------

    /**
     * Método para la extensión 1
     * @return respuesta1
     */
    public String metodo1( )
    {
        return "Respuesta 1";
    }

    /**
     * Método para la extensión2
     * @return respuesta2
     */
    public String metodo2( )
    {
        return "Respuesta 2";
    }

	public int darNumeroCerrados() {
		return numeroTicketsCerrados;
	}

	public int darNumeroSiendoAtendidos() {
		return numeroTicketsSiendoAtendidos;
	}

	public int darNumeroSinAtender() {
		return numeroTicketsSinAtender;
	}

	public String darPrefijos(String prefijo) {
		Lista<Empleado> lista = new Lista<Empleado>();
		if(prefijo.charAt(prefijo.length()-1) == '*')
		{
			prefijo = prefijo.substring(0, prefijo.length()-1);
			lista = prefijosEmpleados.buscarPorPrefijo(prefijo);
		}
		else
		{
			Empleado empleado = prefijosEmpleados.buscar(prefijo);
			if(empleado != null)
				lista.agregar(empleado);
			
		}
		String tickets = "";
		for(int i=0; i<lista.darLongitud();i++)
			for(int j=0; j<((Empleado)lista.darElemento(i)).darListaTickets().size(); j++)
				if(tablaTickets.dar(((Empleado)lista.darElemento(i)).darListaTickets().get(j)).reabierto())
					tickets += tablaTickets.dar(((Empleado)lista.darElemento(i)).darListaTickets().get(j)).darId() + " asignado a " + tablaTickets.dar(((Empleado)lista.darElemento(i)).darListaTickets().get(j)).darEmpleado().darNombre() + "\n";
		return tickets.equals("") ? "No se encontro ningun empleado cuyo nombre correspondiera con la cadena de texto introducida por usted." : tickets;
	}

	public IUsuario darUsuario(int llave) {
		return tablaUsuarios.dar(llave);
	}
}
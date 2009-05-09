package uniandes.cupi2.helpDesk.interfazMundo;

import java.io.File;
import java.util.Date;

import uniandes.cupi2.helpDesk.digiturno.GrafoAciclico;


/**
 * Interfaz que expone los contratos funcionales de un intérprete del help desk
 */
public interface IHelpDesk 
{
	 // -------------------------------------------------------------------
    // Constantes
    // -------------------------------------------------------------------
	
	static final String ACTIVIDAD_NUEVA_SOLICITUD = "Nueva solicitud (Cliente)";

	static final String ACTIVIDAD_CIFRAR = "Cifrar ticket (Sistema)";

	static final String ACTIVIDAD_ASIGNAR_TICKET = "Asignar ticket(Sistema)";

	static final String ACTIVIDAD_ATENDER = "Atender ticket (Empleado)";

	static final String ACTIVIDAD_CERRAR = "Cerrar ticket (Empleado)";

	static final String ACTIVIDAD_NOTFICAR = "Notificar al cliente (Sistema)";

	static final String ACTIVIDAD_REABRIR = "Reabrir ticket (Cliente)";
	
	
    // -------------------------------------------------------------------
    // Métodos
    // -------------------------------------------------------------------

	/**
	 * Crea un nuevo Ticket y lo agrega al cliente, al empleado, y a la lista.
	 * pre: El cliente existe, el tipo es valido, y el comentario es diferente de null.
	 * post: se agrega un nuevo Ticket, si no hay empleados genera una excepcion.
	 * @param cifrado 
	 * @param tipo: uno de queja, reclamo, o solicitud
	 * @param comentarioCliente: la razon de la solicitud
	 * @throws Exception si no hay empleados en el helpdesk
	 */
	public void nuevaSolicitud(int tipo, String comentarioCliente, boolean cifrado) throws Exception;
		
	/**
	 * El empleado al que le fue asignado un ticket lo marca como atendido generando
	 * una fecha de atencion.
	 * pre: el ticket no puede ser null
	 * post: la fecha de atencion del ticket fue asignada
	 * @param ticket: el ticket a atender
	 * @throws Exception 
	 */
	public void atenderTicket( ITicket ticket ) throws Exception;
	
	/**
	 * El empleado al que le fue asignado un ticket lo marca como cerrado generando
	 * una fecha de cierre, y guarda la solucion al problema hecha por el empleado.
	 * pre: el ticket no puede ser null
	 * post: la fecha de cierre del ticket fue asignada
	 * @param ticket: el ticket a cerrar
	 * @param comentario: la solucion del empleado
	 * @throws Exception 
	 */
	public void cerrarTicket( ITicket ticket, String comentario ) throws Exception;
	
	/**
	 * Da un iterador sobre todos los tickets ordenados por fecha de atencion, que esten
	 * abiertos entre las fechas dadas.
	 * pre: fecha es distinto de null, fecha1 es antes de que fecha2
	 * post: (no hay postcondiciones)
	 * @param fecha: la fecha a evaluar como condicion
	 * @return IIteradorTicket: el iterador sobre la lista de tickets
	 */
	public IIterador darListaTicketsEntreFechas( Date fecha1, Date fecha2 );
	
	/**
	 * Da un iterador sobre todos los clientes ordenados por fecha de atencion.
	 * Si no han sido atendidos, no figuran en la lista.
	 * pre: (no hay precondiciones)
	 * post: (no hay postcondiciones)
	 * @return IIteradorUsuario: el iterador sobre la lista de clientes
	 */
	public IIterador darListaPersonasAtendidas();
	
	/**
	 * El cliente que hizo un ticket califica la atencion recibida.
	 * pre: el ticket no puede ser null, y la calificacion debe ser valida
	 * post: el atributo calificacion del ticket fue modificado
	 * @param ticket: el ticket a cerrar
	 * @param calificacion: la puntuacion hecha por el cliente
	 */
	public void calificarTicket(ITicket ticket, int calificacion );
	
	/**
	 * Da un iterador sobre todos los empleados ordenados por puntaje de
	 * calificacion total.
	 * pre: (no hay precondiciones)
	 * post: (no hay postcondiciones)
	 * @return IIteradorUsuario: el iterador sobre la lista de empleados
	 */
	public IIterador darEmpleadosDelMes();
	
	/**
	 * Retorna un objeto que implemente la interfaz IUsuario dados su nombre
	 * y su tipo.
	 * pre: (no hay precondiciones)
	 * post: (no hay postcondiciones)
	 * @param nombre: el nombre del usuario
	 * @param tipo: el tipo del usuario
	 * @return el usuario encontrado, o null si no existe
	 */
	public IUsuario darUsuario(String nombre, int tipo);
	
	/**
	 * Este metodo hace las operaciones necesarias para iniciar la sesion.
	 * pre: la sesion no esta iniciada
	 * post: la sesion esta iniciada
	 * @param usuario el usuario que inicia sesion
	 */
	public void iniciarSesion(IUsuario usuario);
	
	/**
	 * Crea un cliente dados sus datos.
	 * pre: tal cliente no existe
	 * post: se creo el cliente y es retornado
	 * @param nombre: el nombre del cliente
	 * @param login: el login del cliente
	 * @param password: el password del cliente
	 * @param tipoCliente: el tipo del cliente
	 * @param email: el email del cliente
	 * @return IUsuario el usuario que se acaba de crear
	 */
	public IUsuario crearCliente(String nombre, String login, String password, int tipoCliente, String email);
	
	/**
	 * Da un iterador sobre todos los tickets ordenados por fecha de creacion, que 
	 * pertenezcan al usuario actual.
	 * pre: (no hay precondiciones)
	 * post: (no hay postcondiciones)
	 * @return IIteradorTicket: el iterador sobre la lista de tickets
	 */
	public IIterador darListaTicketsUsuarioActual();
	
    /**
     * Punto de extensión 1
     * @return String mensaje generico
     */
    public String metodo1( );
    
    /**
     * Punto de extensión 2
     * @return String mensaje generico
     */
    public String metodo2( );

    /**
     * Da el usuario actual
     * pre: (no hay)
     * post: (no hay)
     * @return IUsuario el usuario activo
     */
	public IUsuario darUsuarioActual();
	
	/**
	 * carga una lista de empleados a partir de un archivo Properties.
	 * pre: el archivo existe y esta bien construido
	 * post: se han agregado los empleados
	 * @param archivo: el archivo a examinar
	 * @throws Exception: si hay un problema en el proceso de apertura
	 */
	public void cargarListaEmpleados(File archivo) throws Exception;

	/**
	 * Reabre el ticket a solicitud del cliente, quien al parecer
	 * quedo insatisfecho con la actuacion del funcionario
	 * que le atendio.
	 * @param ticket: ticket en cuestion
	 * @param empleado: el nuevo empleado al que se le asignara
	 * el ticket.
	 * @param comentarioReapertura: la razon que da el cliente por la
	 * cual decidio reabrir su ticket
	 * @throws Exception: Se dispara en caso en que haya ocurrido un
	 * error en el envio del correo electronico.
	 */
	public void reapertura(ITicket ticket, IUsuario empleado, String comentarioReapertura) throws Exception;

	/**
	 * Retorna la lista de los empleados activos en el momento.
	 * @return IIterador: el objeto que itera sobre la lista de empleados.
	 */
	public IIterador darEmpleados();

	/**
	 * Retorna el nombre y la descripcion del empleado que ha tenido
	 * menos incidentes en la empresa. De haber varios, retorna el
	 * primero de ellos.
	 * @return String: el nombre y la descripcion del empleado.
	 */
	public String darEmpleadoMenosIncidentes();

	/**
	 * Provee informacion valiosa acerca de un ticket especifico.
	 * @param id: el id unico en el sistema del ticket
	 * @return String: la informacion del ticket.
	 */
	public String darInfoTicket(int id);

	/**
	 * Se encarga de efectuar el desciframiento de un
	 * ticket especifico para que el empleado a quien
	 * esta asignado pueda leerlo.
	 * @param ticketActual: el ticket a descifrar
	 * @return String: el comentario descifrado del cliente.
	 */
	public String descifrar(ITicket ticketActual);


	/**
	 * Reabre el ticket a solicitud del cliente, quien al parecer
	 * quedo insatisfecho con la actuacion del funcionario
	 * que le atendio.
	 * @param ticket: ticket en cuestion
	 * @param empleado: el nuevo empleado al que se le asignara
	 * el ticket.
	 * @param comentarioReapertura: la razon que da el cliente por la
	 * cual decidio reabrir su ticket
	 * @throws Exception: Se dispara en caso en que haya ocurrido un
	 * error en el envio del correo electronico.
	 */
	public int darNumeroSinAtender();


	/**
	 * Yuxtapone el ticket a solicitud del cliente, quien al parecer
	 * quedo insatisfecho con la actuacion del funcionario
	 * que le atendio.
	 * @param ticket: ticket en cuestion
	 * @param empleado: el nuevo empleado al que se le asignara
	 * el ticket.

	 */
	public int darNumeroSiendoAtendidos();


	/**
	 * Reabre el ticket a solicitud del cliente, quien al parecer
	 * @param empleado: el nuevo empleado al que se le asignara
	 * el ticket.
	 * @param comentarioReapertura: la razon que da el cliente por la
	 * cual decidio reabrir su ticket
	 * @throws Exception: Se dispara en caso en que haya ocurrido un
	 * error en el envio del correo electronico.
	 */
	public int darNumeroCerrados();


	/**
	 * quedo insatisfecho con la actuacion del funcionario
	 * que le atendio.
	 * @throws Exception: Se dispara en caso en que haya ocurrido un
	 * error en el envio del correo electronico.
	 */
	public IIterador darListaIncidentes(boolean antes, Date fecha);


	/**
	 * Retorna una lista de Tickets, dado un prefijo de nombre de cliente.
	 * @param prefijo. El prefijo en cuestion
	 * @return String. La lista de tickets.
	 */
	public String darPrefijos(String prefijo);

	/**
	 * Guarda toda la informacion requerida para que la aplicacion
	 * suponga medios efectivos de almacenar la persistencia
	 * @param file: el archivo donde se guardara la informacion
	 */
	public void guardar(String ruta) throws Exception;

	public GrafoAciclico darDigiturno();

}

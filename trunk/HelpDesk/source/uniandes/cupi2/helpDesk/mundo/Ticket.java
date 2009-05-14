package uniandes.cupi2.helpDesk.mundo;

import java.util.Date;

import uniandes.cupi2.helpDesk.interfazMundo.ITicket;
import uniandes.cupi2.helpDesk.interfazMundo.IUsuario;

/**
 * Representa un ticket generado en el momento
 * en que un cliente hace una amonestacion de
 * cualquiera de los tres tipos: queja, reclamo,
 * o solicitud.
 * @author Emilio Silva, Nicolas Stucki
 *
 */
public class Ticket implements ITicket {

	private int tipo;
	private Cliente cliente;
	private String comentarioCliente;
	private int calificacion;
	private Empleado empleado;
	private String comentarioEmpleado;
	private Date fechaCreacion;
	private Date fechaAtencion;
	private Date fechaCierre;
	private boolean atendidoPorExperto;
	private int id;
	private boolean cifrado;
	private boolean reabierto;
		
	public Ticket(int tipo, Cliente cliente, String comentarioCliente, int calificacion, String comentarioEmpleado, Date fechaCreacion, Date fechaAtencion, Date fechaCierre, boolean atendidoPorExperto, boolean reabierto, boolean cifrado,int id)
	{
		empleado = null;
		this.tipo = tipo;
		this.cliente = cliente;
		this.comentarioCliente = comentarioCliente;
		this.comentarioEmpleado = comentarioEmpleado;
		this.calificacion = calificacion;
		this.fechaCreacion = fechaCreacion;
		this.fechaAtencion = fechaAtencion;
		this.fechaCierre = fechaCierre;
		this.atendidoPorExperto = atendidoPorExperto;
		this.id = id;
		this.reabierto = reabierto;
		this.cifrado = cifrado;
		verificarInvariante();
	}
	
	public boolean estaCifrado()
	{
		return cifrado;
	}
	
	public void cifrar(byte clave)
	{
		comentarioCliente = descifrar(clave);
	}
	
	public String descifrar(byte clave)
	{
		byte[] comentario = comentarioCliente.getBytes();
		byte[] descifrado = new byte[comentario.length];
		for(int i=0; i<comentario.length; i++)
			descifrado[i] = (byte) (clave ^ comentario[i]);

		return new String(descifrado);
	}

	public boolean atendidoPorExperto() {
		return atendidoPorExperto;
	}

	public int darCalificacion() {
		return calificacion;
	}

	public String darComentarioCliente() {
		return comentarioCliente;
	}

	public String darComentarioEmpleado() {
		return comentarioEmpleado;
	}

	public String darEmail() {
		return cliente.darEmail();
	}


	public Date darFechaAtencion() {
		return fechaAtencion;
	}

	public Date darFechaCierre() {
		return fechaCierre;
	}

	public Date darFechaCreacion() {
		return fechaCreacion;
	}

	public int darTipo() {
		return tipo;
	}

	public String darNombreCliente() {
		return cliente.darNombre();
	}

	public String darNombreEmpleado() {
		return empleado.darNombre();
	}

	public void cambiarFechaAtencion(Date fecha) {
		fechaAtencion = fecha;
		verificarInvariante();
	}

	public void cambiarFechaCierre(Date fecha) {
		fechaCierre = fecha;
		verificarInvariante();

	}

	public void cambiarCalificacion(int calificacion) {
		this.calificacion = calificacion;
		verificarInvariante();

	}
	
	public Cliente darClient()
	{
		return cliente;
	}

	public void cambiarComentarioEmpleado(String comentario) {
		comentarioEmpleado = comentario;		
		verificarInvariante();

	}
	
	public void cambiarEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
	
	public int darId()
	{
		return id;
	}
	
	public void atender()
	{
		fechaAtencion = new Date();
	}
	
	public void asignar(Empleado encargado)
	{
		empleado = encargado;
		if(empleado.darTipo() != tipo)
			atendidoPorExperto = false;
	}
	
	public void reabrir()
	{
		reabierto = true;
	}
	
	private void verificarInvariante()
	{
		assert tipo == TIPO_QUEJA || tipo == TIPO_RECLAMO || tipo == TIPO_SOLICITUD : "Tipo inv‡lido";
		assert comentarioCliente != null && !comentarioCliente.equals("") : "Comentario del cliente inv‡lido";
		assert calificacion == CALIFICACION_DEFINITIVAMENTE_MUY_MALO || calificacion == CALIFICACION_MUY_SATISFECHO || calificacion == CALIFICACION_PODRIA_MEJORAR || calificacion == CALIFICACION_SATISFECHO || calificacion == SIN_CALIFICACION : "Calificaci—n incorrecta.";
		assert fechaCreacion != null : "Fecha de creaci—n incorrecta";
		
	}

	public Cliente darCliente() {
		return cliente;
	}
	
	/**
	 * calificar
	 * Cambia la suma de puntos del empleado que atiende este ticket
	 * enviando la diferencia entre la nueva y la antigua calificacion
	 * y cambia el propio parametro calificacion
	 * @param calificacion 
	 */
	public void calificar(int calificacion, Empleado primerEmpleadoDelMes) {
		empleado.cambiarCalificacion(calificacion - this.calificacion,primerEmpleadoDelMes);
		this.calificacion = calificacion;
				
	}

	public void cerrar(String comentario) {
		fechaCierre = new Date();
		cambiarComentarioEmpleado(comentario);
	}
	

	public String toString()
	{
		return (tipo==1? "Queja" :tipo==2? "Reclamo" : "Solicitud") + " # " + id + " - " + (fechaAtencion == null ? "Sin atender" : fechaCierre == null ? (!reabierto ? "Abierto" : "Reabierto") : "Cerrado");// + "asignado a " + empleado.darNombre();
	}

	public IUsuario darEmpleado() {
		
		return empleado;
	}

	public void cambiarExperto(boolean estado) {
		atendidoPorExperto = estado;
		verificarInvariante();
	}

	public boolean reabierto() {
		return reabierto;
	}
}

package uniandes.cupi2.Autenticador;

public interface IAutenticador {

	/**
	 * Constante que representa el tipo cliente.
	 */
	public static int TIPO_CLIENTE = 452;
	
	/**
	 * Constante que representa el tipo empleado.
	 */
	public static int TIPO_EMPLEADO = 785;
	
	/**
	 * Constante que representa el tipo administrador.
	 */
	public static int TIPO_ADMINISTRADOR = 932;

	
	/**
	 * Valida un usuario dado su clave, loginy tipo.
	 * @param login login del usuario
	 * @param password	password del usuario
	 * @param tipo tipo del usuario
	 * @return la llave del usuario
	 * @throws Exception si no se valido el usuario
	 */
	public int validar(String login, String password, int tipo) throws Exception;
	
	/**
	 * Agrega un nuevo usuario al Autenticador.
	 * pre: el usuario no existe
	 * @param login login del nuevo usuario
	 * @param password password del nuevo usuario
	 * @param llave llave del nuevo usuario
	 * @param tipo tipo del nuevo usuario
	 * @throws Exception si alguno de los parametros es invalido
	 */
	public void agregarUsuario(String login, String password, int llave, int tipo) throws Exception;
	
/**
 * Comprueba si un usuario ya existe.
 * @param login login del usuario
 * @return true si existe el usuario o false si no existe
 */
	public boolean existeUsuario(String login);
}
package uniandes.cupi2.Autenticador;

public interface IAutenticador {

	public static int TIPO_CLIENTE = 452;
	
	public static int TIPO_EMPLEADO = 785;
	
	public static int TIPO_ADMINISTRADOR = 932;

	
	/**
	 * 
	 * @param login
	 * @param password
	 * @param tipo
	 * @return la llave del usuario
	 * @throws Exception
	 */
	public String validar(String login, String password, int tipo) throws Exception;
	
	/**
	 * 
	 * @param login
	 * @param password
	 * @param llave
	 * @param tipo
	 * @throws Exception
	 */
	public void agregarUsuario(String login, String password, String llave, int tipo) throws Exception;
	
/**
 * 
 * @param login
 * @return
 */
	public boolean existeUsuario(String login);
}
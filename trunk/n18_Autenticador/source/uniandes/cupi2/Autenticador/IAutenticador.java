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
	
}
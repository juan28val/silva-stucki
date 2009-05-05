package uniandes.cupi2.Autenticador;

public interface IAutenticador {

	public static int TIPO_CLIENTE = 452;
	
	public static int TIPO_EMPLEADO = 785;
	
	public static int TIPO_ADMINISTRADOR = 932;

	/**
	 * 
	 * @return la llave del usuario.
	 */
	public String validar();
	
}
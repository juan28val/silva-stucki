package uniandes.cupi2.Autenticador;

public class Usuario {

	/**
	 * login del usuario
	 */
	@SuppressWarnings("unused")
	private String login;
	
	/**
	 * password del usuario
	 */
	private String password;
	
	/**
	 * llave del usuario
	 */
	int llave;
	
	/**
	 * tipo del usuario
	 */
	private int tipo;
	
	/**
	 * Crea un nuevo usuario
	 * @param login login del nuevo usuario
	 * @param password password del nuevo usuario
	 * @param llave llave del nuevon usuario
	 * @param tipo tipo del nuevo usuario
	 */
	public Usuario(String login, String password, int llave, int tipo)
	{
		this.login = login;
		this.password = password;
		this.tipo = tipo;
		this.llave = llave;
	}
	
	/**
	 * Valida el password y el tipo
	 * @param password password que se va a validar
	 * @param tipo tipo que se va a validar
	 * @return la llave del usuario
	 * @throws Exception si no se valida
	 */
	public int validar(String password, int tipo) throws Exception
	{
		if( !this.password.equals(password) || this.tipo!=tipo )
			throw new Exception("password o tipo incorrecto.");
		
		return llave;
	}
}
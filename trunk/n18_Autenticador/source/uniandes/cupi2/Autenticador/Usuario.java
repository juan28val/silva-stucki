package uniandes.cupi2.Autenticador;

public class Usuario {

	String login;
	
	String password;
	
	String llave;
	
	int tipo;
	
	
	public Usuario(String login, String password, String llave, int tipo)
	{
		this.login = login;
		this.password = password;
		this.tipo = tipo;
		this.llave = llave;
	}
	
	public String validar(String password, int tipo) throws Exception
	{
		if( !this.password.equals(password) || this.tipo!=tipo )
			throw new Exception("password o tipo incorrecto.");
		
		return llave;
	}
}

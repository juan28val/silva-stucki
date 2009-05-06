package uniandes.cupi2.Autenticador;

import java.util.HashMap;

public class Autenticador implements IAutenticador {

	HashMap<String, Usuario> usuarios;
	
	private Autenticador()
	{
		usuarios = new HashMap<String, Usuario>();
	}
	
	public void agregarUsuario(String login, String password, String llave, int tipo)
	{
		
	}
	
	public String validar(String login, String password, int tipo) throws Exception {
		if(usuarios.get(login)==null)
			throw new Exception("usuario no existe.");
		
		return usuarios.get(login).validar(password, tipo);
	}

}
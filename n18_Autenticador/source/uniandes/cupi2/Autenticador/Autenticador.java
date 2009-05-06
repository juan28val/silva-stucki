package uniandes.cupi2.Autenticador;

import java.util.HashMap;

public class Autenticador implements IAutenticador {

	private static Autenticador instancia = null;
	
	private HashMap<String, Usuario> usuarios;
	
	private Autenticador()
	{
		usuarios = new HashMap<String, Usuario>();
	}
	
	public static Autenticador getInstance()
	{
		if( instancia == null )
			instancia = new Autenticador();
		
		return instancia;
	}
	
	/**
	 * pre: el usuario no existe
	 */
	public void agregarUsuario(String login, String password, String llave, int tipo) throws Exception
	{
		if( password==null )
			throw new Exception("password invalido.");
		else if( llave==null )
			throw new Exception("llave invalida.");
		else if( tipo!=TIPO_ADMINISTRADOR || tipo!=TIPO_CLIENTE || tipo!=TIPO_EMPLEADO )
			throw new Exception("tipo invalido.");
	
		usuarios.put(login, new Usuario(login,password,llave,tipo));
	}
	
	public boolean existeUsuario(String login)
	{
		return usuarios.get(login)==null?false:true;
	}
	
	public String validar(String login, String password, int tipo) throws Exception {
		if(usuarios.get(login)==null)
			throw new Exception("usuario no existe.");
		
		return usuarios.get(login).validar(password, tipo);
	}

}
package uniandes.cupi2.Autenticador;

import java.util.HashMap;

public class Autenticador implements IAutenticador {

	/**
	 * instancia de esta clase.
	 */
	private static Autenticador instancia = null;
	
	/**
	 * tabla de Hashing con todos los usuarios con su login como llave
	 */
	private HashMap<String, Usuario> usuarios;
	
	/**
	 * Constructor: inicializa el HashMap de usuarios
	 */
	private Autenticador()
	{
		usuarios = new HashMap<String, Usuario>();
	}
	
	/**
	 * Da la instancia (segun el formato de singleton)
	 * @return instancia
	 */
	public static Autenticador getInstance()
	{
		if( instancia == null )
			instancia = new Autenticador();
		
		return instancia;
	}
	
	public void agregarUsuario(String login, String password, int llave, int tipo) throws Exception
	{
		if( password==null )
			throw new Exception("password invalido.");
		else if( tipo!=TIPO_ADMINISTRADOR || tipo!=TIPO_CLIENTE || tipo!=TIPO_EMPLEADO )
			throw new Exception("tipo invalido.");
	
		usuarios.put(login, new Usuario(login,password,llave,tipo));
	}
	
	public boolean existeUsuario(String login)
	{
		return usuarios.get(login)==null?false:true;
	}
	
	public int validar(String login, String password, int tipo) throws Exception {
		if(usuarios.get(login)==null)
			throw new Exception("usuario no existe.");
		
		return usuarios.get(login).validar(password, tipo);
	}

}
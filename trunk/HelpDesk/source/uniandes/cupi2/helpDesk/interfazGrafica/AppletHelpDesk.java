package uniandes.cupi2.helpDesk.interfazGrafica;


import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Event;
import java.awt.BorderLayout;

import javax.swing.SwingConstants;
import javax.swing.KeyStroke;
import java.awt.Point;
import java.io.File;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JFrame;
import javax.swing.JDialog;

import uniandes.cupi2.helpDesk.interfazMundo.IActividad;
import uniandes.cupi2.helpDesk.interfazMundo.IGrafo;
import uniandes.cupi2.helpDesk.interfazMundo.IHelpDesk;
import uniandes.cupi2.helpDesk.interfazMundo.IIterador;
import uniandes.cupi2.helpDesk.interfazMundo.ITicket;
import uniandes.cupi2.helpDesk.interfazMundo.IUsuario;
import uniandes.cupi2.helpDesk.mundo.FabricaHelpDesk;


public class AppletHelpDesk extends JApplet implements IInterfaz, ActionListener {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 15465L;

	public static final String RUTA_ARCHIVO = ".." + File.separator + "data" + File.separator + "persistencia.xml";
	
	private JFrame jFrame = null;
	private JPanel jContentPane = null;
	private JMenuBar jJMenuBar = null;
	private JMenu fileMenu = null;
	private JMenu menuInfo = null;
	private JMenuItem exitMenuItem = null;
	private JMenuItem aboutMenuItem = null;
	private JMenuItem iniciarSesionMenuItem = null;
	private JDialog aboutDialog = null;
	private JPanel aboutContentPane = null;
	private JLabel aboutVersionLabel = null;
	private IHelpDesk mundo;
	private JMenuItem menuCargar = null;
	private JMenuItem itemTicket;
	private JMenuItem itemEmpleado;
	private JMenuItem prefijos;

	private JMenuItem grafo;

	private JMenuItem criticas;

	private JMenuItem lentas;

	private JMenuItem actividad;

	private JMenuItem listaActividades;

	private JMenuItem tiempoActividad;
	
	private DialogoGrafo dialogoGrafo;

	
	// ------------------------------------------
	//  METODOS 
	// ------------------------------------------
	
	/**
	 * Metodo llamado cuando se ejecuta una accion
	 */
	public void actionPerformed(ActionEvent evento)
	{
		try
		{
			if(evento.getActionCommand().equals("prefijos"))
			{
				String prefijo = JOptionPane.showInputDialog(this, "Introduzca el nombre del empleado.\nPuede usar wildcards (*) para buscar nombres incompletos.");
				JOptionPane.showMessageDialog(this, mundo.darPrefijos(prefijo));
			}
			if(evento.getActionCommand().equals("ticket"))
			{
				String id = JOptionPane.showInputDialog(this, "Introduzca el ID del ticket: ");
				JOptionPane.showMessageDialog(this, mundo.darInfoTicket(Integer.parseInt(id)));
			}
			if(evento.getActionCommand().equals("empleado"))
				JOptionPane.showMessageDialog(this, mundo.darEmpleadoMenosIncidentes());
			if(evento.getActionCommand().equals("guardar"))
				mundo.guardar(RUTA_ARCHIVO);
			if(evento.getActionCommand().equals("cargar"))
			{
				if(JOptionPane.showConfirmDialog(this, "Si importa una nomina nueva, todos sus datos se perderan.\nEsta seguro de que desea continuar?") != JOptionPane.YES_OPTION)
					return;
				JFileChooser cargar = new JFileChooser();
				cargar.setCurrentDirectory(new File("data"));
				cargar.showOpenDialog(this);
				if(cargar.getSelectedFile() != null)
				{
					reiniciar();
					mundo = FabricaHelpDesk.getInstance( ).fabricarImplementacion( );
					cerrarSesion();
					mundo.cargarListaEmpleados(cargar.getSelectedFile());
				}
				cerrarSesion();
			}
			if(evento.getActionCommand().equals("acercaDe"))
			{
				JDialog aboutDialog = getAboutDialog();
				aboutDialog.pack();
				Point loc = getLocation();
				loc.translate(20, 20);
				aboutDialog.setLocation(loc);
				aboutDialog.setVisible(true);
			}
			if(evento.getActionCommand().equals("cerrar"))
				cerrarSesion();
			if(evento.getActionCommand().equals("grafo"))
			{
				dialogoGrafo.setVisible(true);
			}
			if(evento.getActionCommand().equals("criticas"))
			{
				IActividad[] act = mundo.darDigiturno().darActividadesCriticas();
				String lista = act[0].darNombre()+" - ejecutada "+act[0].darNumeroVecesEjecutada()+" veces.";
				for(int i=1; i<act.length; i++)
					lista += "\n" + act[i].darNombre()+" - ejecutada "+act[i].darNumeroVecesEjecutada()+" veces.";
				JOptionPane.showMessageDialog(this, lista);
			}
			if(evento.getActionCommand().equals("lentas"))
			{
				IActividad[] act = mundo.darDigiturno().darActividadesMasLentas();
				String lista = act[0].darNombre()+" - dura "+act[0].darPromedioTiempo()+" milisegundos en promedio.";
				for(int i=1; i<act.length; i++)
					lista += "\n" + act[i].darNombre()+" - dura "+act[i].darPromedioTiempo()+" milisegundos en promedio.";
				JOptionPane.showMessageDialog(this, lista);
			}
			if(evento.getActionCommand().equals("actividad"))
			{
				String id = JOptionPane.showInputDialog(this, "Introduzca el nombre de la actividad: ");
				IActividad act = mundo.darDigiturno().darActividad(id);
				String respuesta = act.darNombre()+"\n  Ejecuciones: " + act.darNumeroVecesEjecutada() + "\n  Tiempo promedio: " + 
				act.darPromedioTiempo() ;
				JOptionPane.showMessageDialog(this, respuesta);
			}
			if(evento.getActionCommand().equals("listaActividades"))
			{
				IIterador i = mundo.darDigiturno().darListaActividadesPorTiempo();
				String lista = ((IActividad)i.darSiguiente()).darNombre();
				while(i.haySiguiente())
					lista += "\n" + ((IActividad) i.darSiguiente()).darNombre();
				JOptionPane.showMessageDialog(this, lista);
			}
			if(evento.getActionCommand().equals("tiempoActividad"))
			{
				String id = JOptionPane.showInputDialog(this, "Introduzca el nombre de la actividad: ");
				IActividad act = mundo.darDigiturno().darActividad(id);
				String respuesta = act.darNombre()+"\n  Tiempo promedio acumulado: " + act.darTiempoPromedioEspera();
				JOptionPane.showMessageDialog(this, respuesta);
			}
				
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
		}
			
	}
	/**
	 * Init - inicializa el applet
	 * @param args
	 */
	public void init() {
		super.init();
		escogerImplementacion( );
		actualizar(new PanelInicioSesion(this));
		dialogoGrafo = new DialogoGrafo(this, mundo.darDigiturno());
	}
	
	private void actualizar(JPanel panel) {
		jContentPane = new JPanel();
		jContentPane.removeAll();
		jContentPane.add(panel);
		jContentPane.setBorder(BorderFactory.createEmptyBorder(100,250,100,100));
		jContentPane.add( new BarraEstado(this,mundo));
		jContentPane.validate();
		add(jContentPane);
		setSize(800,600);
		setJMenuBar(getJJMenuBar());
	}

	/**
	 * This method initializes jFrame
	 * 
	 * @return javax.swing.JFrame
	 */
	public JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			jFrame.setJMenuBar(getJJMenuBar());
			jFrame.setSize(670, 500);
			jFrame.setLocationByPlatform(true);
			jFrame.setResizable(true);
			jFrame.setContentPane(getJContentPane());
			jFrame.setTitle("HelpDesk");
		}
		return jFrame;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
		}
		return jContentPane;
	}

	/**
	 * This method initializes jJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getMenuArchivo());
			jJMenuBar.add(getMenuInfo());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getMenuArchivo() {
		if (fileMenu == null) {
			fileMenu = new JMenu();
			fileMenu.setText("Archivo");
			fileMenu.add(getLogoutMenuItem());
			fileMenu.add(menuCargar());
			fileMenu.add(getExitMenuItem());
		}
		return fileMenu;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getMenuInfo() {
		if (menuInfo == null) {
			menuInfo = new JMenu();
			menuInfo.setText("Info");
			menuInfo.add(getEmpleado());
			menuInfo.add(getTicket());
			menuInfo.add(getAboutMenuItem());
			menuInfo.add(getPrefijos());
			menuInfo.add(getGrafo());
			menuInfo.add(getCriticas());
			menuInfo.add(getLentas());
			menuInfo.add(getActividad());
			menuInfo.add(getListaActividades());
			menuInfo.add(getTiempoActividad());
		}
		return menuInfo;
	}



	private JMenuItem getTiempoActividad() {
		if(tiempoActividad == null)
		{
			tiempoActividad = new JMenuItem();
			tiempoActividad.setText("Ver el tiempo promedio de una actividad...");
			tiempoActividad.addActionListener(this);
			tiempoActividad.setActionCommand("tiempoActividad");
		}
		return tiempoActividad;
	}
	private JMenuItem getListaActividades() {
		if(listaActividades == null)
		{
			listaActividades = new JMenuItem();
			listaActividades.setText("Ver lista de actividades...");
			listaActividades.addActionListener(this);
			listaActividades.setActionCommand("listaActividades");
		}
		return listaActividades;
	}
	private JMenuItem getActividad() {
		if(actividad == null)
		{
			actividad = new JMenuItem();
			actividad.setText("Estadisticas de una actividad...");
			actividad.addActionListener(this);
			actividad.setActionCommand("actividad");
		}
		return actividad;
	}
	private JMenuItem getLentas() {
		if(lentas == null)
		{
			lentas = new JMenuItem();
			lentas.setText("Ver actividades lentas...");
			lentas.addActionListener(this);
			lentas.setActionCommand("lentas");
		}
		return lentas;
	}
	private JMenuItem getCriticas() {
		if(criticas == null)
		{
			criticas = new JMenuItem();
			criticas.setText("Ver actividades criticas...");
			criticas.addActionListener(this);
			criticas.setActionCommand("criticas");
		}
		return criticas;
	}
	private JMenuItem getGrafo() {
		if(grafo == null)
		{
			grafo = new JMenuItem();
			grafo.setText("Ver grafo de actividades...");
			grafo.addActionListener(this);
			grafo.setActionCommand("grafo");
		}
		return grafo;
	}
	private JMenuItem getPrefijos() {
		if(prefijos == null)
		{
			prefijos = new JMenuItem();
			prefijos.setText("Buscar Tickets por empleado...");
			prefijos.addActionListener(this);
			prefijos.setActionCommand("prefijos");
		}
		return prefijos;
	}

	private JMenuItem getTicket() {
		if (itemTicket == null) {
			itemTicket = new JMenuItem();
			itemTicket.setText("Ver ticket...");
			itemTicket.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,
					Event.CTRL_MASK, true));
			itemTicket.addActionListener(this);
			itemTicket.setActionCommand("ticket");
		}
		return itemTicket;
	}

	private JMenuItem getEmpleado() {
		if (itemEmpleado == null) {
			itemEmpleado = new JMenuItem();
			itemEmpleado.setText("Ver empleado con menos incidentes...");
			itemEmpleado.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
					Event.CTRL_MASK, true));
			itemEmpleado.addActionListener(this);
			itemEmpleado.setActionCommand("empleado");
		}
		return itemEmpleado;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getExitMenuItem() {
		if (exitMenuItem == null) {
			exitMenuItem = new JMenuItem();
			exitMenuItem.setText("Guardar...");
			exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,
					Event.CTRL_MASK, true));
			exitMenuItem.addActionListener(this);
			exitMenuItem.setActionCommand("guardar");
		}
		return exitMenuItem;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem menuCargar() {
		if (menuCargar == null) {
			menuCargar = new JMenuItem();
			menuCargar.setText("Importar nómina...");
			menuCargar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
					Event.CTRL_MASK, true));
			
			menuCargar.addActionListener(this);
			menuCargar.setActionCommand("cargar");
			}
			
		return menuCargar;
	}

	protected void reiniciar() {
		File persistencia = new File(RUTA_ARCHIVO);
		persistencia.delete();
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getAboutMenuItem() {
		if (aboutMenuItem == null) {
			aboutMenuItem = new JMenuItem();
			aboutMenuItem.setText("Acerca de...");
			aboutMenuItem.addActionListener(this);
			aboutMenuItem.setActionCommand("acercaDe");
		}
		return aboutMenuItem;
	}

	/**
	 * This method initializes aboutDialog	
	 * 	
	 * @return javax.swing.JDialog
	 */
	private JDialog getAboutDialog() {
		if (aboutDialog == null) {
			aboutDialog = new JDialog(getJFrame(), true);
			aboutDialog.setTitle("Acerca de...");
			aboutDialog.setContentPane(getAboutContentPane());
		}
		return aboutDialog;
	}

	/**
	 * This method initializes aboutContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getAboutContentPane() {
		if (aboutContentPane == null) {
			aboutContentPane = new JPanel();
			aboutContentPane.setLayout(new BorderLayout());
			aboutContentPane.add(getAboutVersionLabel(), BorderLayout.CENTER);
		}
		return aboutContentPane;
	}

	/**
	 * This method initializes aboutVersionLabel	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	private JLabel getAboutVersionLabel() {
		if (aboutVersionLabel == null) {
			aboutVersionLabel = new JLabel();
			aboutVersionLabel.setText("Version Beta");
			aboutVersionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return aboutVersionLabel;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getLogoutMenuItem() {
		if (iniciarSesionMenuItem == null) {
			iniciarSesionMenuItem = new JMenuItem();
			iniciarSesionMenuItem.setText("Cerrar Sesion");
			iniciarSesionMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
					Event.CTRL_MASK, true));
			iniciarSesionMenuItem.addActionListener(this);
			iniciarSesionMenuItem.setActionCommand("cerrar");
		}
		return iniciarSesionMenuItem;
	}

	public JFrame darJFrame() {
		return jFrame;
	}

	public void nuevaSolicitud(int tipo, String comentario, boolean cifrado) {
		try {
			mundo.nuevaSolicitud( tipo, comentario, cifrado);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(jFrame, "Ha ocurrido un error: " + e.getMessage());
		}
	}

	public IUsuario nuevoCliente(String nombre, String email, String tipo) {
		return mundo.crearCliente(nombre, tipo.equals("Estudiante")?IUsuario.CLIENTE_ESTUDIANTE:tipo.equals("Docente")?IUsuario.CLIENTE_PROFESOR:IUsuario.CLIENTE_PERSONAL_ADMINISTRATIVO, email);
		
	}

	public void calificar(ITicket ticket, String calificacion) {
		mundo.calificarTicket(ticket, calificacion.equals("Muy satisfecho")?ITicket.CALIFICACION_MUY_SATISFECHO:calificacion.equals("Satisfecho")?ITicket.CALIFICACION_SATISFECHO
				:calificacion.equals("Podria mejorar")?ITicket.CALIFICACION_PODRIA_MEJORAR:ITicket.CALIFICACION_DEFINITIVAMENTE_MUY_MALO);	
	}

	public void opcion1() {
		JOptionPane.showMessageDialog(jFrame, mundo.metodo1());
		
	}

	public void opcion2() {
		JOptionPane.showMessageDialog(jFrame, mundo.metodo2());
		
	}

	public void atender(ITicket ticketActual) {
		try {
			mundo.atenderTicket(ticketActual);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(jFrame, "Ha ocurrido un error enviando el mensaje.");
		}
		
	}

	public void cerrarTicket(ITicket ticketActual, String comentario) {
		try {
			mundo.cerrarTicket(ticketActual, comentario);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(jFrame, "Ha ocurrido un error enviando el mensaje.");
		}
		
	}
	
    private void escogerImplementacion( )
    {
    	try
    	{
    		mundo = FabricaHelpDesk.getInstance( ).fabricarImplementacion( RUTA_ARCHIVO );
    	}
    	catch(Exception e)
    	{
      		mundo = FabricaHelpDesk.getInstance( ).fabricarImplementacion( );
    	}
    }
    

	public IUsuario darUsuario(String nombre, String tipoUsuario) {
		
		return mundo.darUsuario(nombre, Integer.parseInt(tipoUsuario));
	}
	
	public void iniciarSesionAdministrador( String nombre ){
		
		if( IUsuario.NOMBRE_ADMIN.equals(nombre) )
		{
			setSize(1200, 500);
			jContentPane.removeAll();
			jContentPane.add( new PanelAdmin( this,mundo.darListaTicketsEntreFechas(new Date(), new Date()), mundo.darEmpleadosDelMes(),mundo.darListaPersonasAtendidas(), mundo.darListaIncidentes(true, new Date())), BorderLayout.CENTER);
			jContentPane.add( new BarraEstado(this,mundo));
			jContentPane.validate();
			jContentPane.setBorder(null);
			add(jContentPane);
			setJMenuBar(getJJMenuBar());
		}
		else JOptionPane.showMessageDialog(jFrame, "Por favor no trate de violar nuestra \"seguridad\".");
		validate();
	}

	public void iniciarSesion(IUsuario usuario)
	{
		mundo.iniciarSesion(usuario);
		
		setSize(670, 500);
		jContentPane.removeAll();
		
		if( usuario.esEmpleado() )
			jContentPane.add( new PanelEmpleado(this,mundo.darListaTicketsUsuarioActual()) );
		else
			jContentPane.add(new PanelCliente(this,mundo.darListaTicketsUsuarioActual()));
		jContentPane.add( new BarraEstado(this,mundo));
		jContentPane.validate();
		jContentPane.setBorder(null);	
		
		setJMenuBar(getJJMenuBar());
		add(jContentPane);
		validate();

	}

	public void cerrarSesion() {
		
		mundo.iniciarSesion(null);
		
		jContentPane.removeAll();
		setSize(670, 500);
		jContentPane.add( new PanelInicioSesion(this) );
		jContentPane.validate();
		jContentPane.setBorder(BorderFactory.createEmptyBorder(100,250,100,100));
		validate();
	}
	
	public void crearCliente(String text) {
		
		JDialog dialogo = new DialogoClienteNuevo( this,text );
		dialogo.setVisible(true);
		dialogo.setLocationRelativeTo(this);
		validate();			
	}

	public IIterador darListaTicketsEntreFechas(Date date1, Date date2) {
		return mundo.darListaTicketsEntreFechas(date1, date2);
	}

	public IIterador darEmpleados() {
		return mundo.darEmpleados();
	}

	public void reapertura(ITicket ticketActual, String comentario,IUsuario empleado) {
		try {
			mundo.reapertura(ticketActual, empleado, comentario);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}

	public String descifrar(ITicket ticketActual) {
		return mundo.descifrar(ticketActual);
	}

	public int darNumeroSinAtender() {
		return mundo.darNumeroSinAtender();
	}

	public int darNumeroSiendoAtendidos() {
		return mundo.darNumeroSiendoAtendidos();
	}

	public int darNumeroCerrados() {
		return mundo.darNumeroCerrados();
	}

	public IIterador darListaIncidentes(boolean antes, Date fecha) {
		return mundo.darListaIncidentes(antes, fecha);
	}

	public IIterador darListaTickets() {
		return mundo.darListaTicketsUsuarioActual();
	}
	public IGrafo darDigiturno() {
		return mundo.darDigiturno();
	}

}

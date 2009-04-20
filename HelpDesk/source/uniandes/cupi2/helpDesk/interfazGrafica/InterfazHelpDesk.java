package uniandes.cupi2.helpDesk.interfazGrafica;


import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Event;
import java.awt.BorderLayout;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.KeyStroke;
import java.awt.Point;
import java.io.File;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JFrame;
import javax.swing.JDialog;

import uniandes.cupi2.helpDesk.interfazMundo.IHelpDesk;
import uniandes.cupi2.helpDesk.interfazMundo.IIterador;
import uniandes.cupi2.helpDesk.interfazMundo.ITicket;
import uniandes.cupi2.helpDesk.interfazMundo.IUsuario;
import uniandes.cupi2.helpDesk.mundo.FabricaHelpDesk;

public class InterfazHelpDesk implements IInterfaz {

	public static final String RUTA_ARCHIVO = "data/persistencia.xml";
	
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				InterfazHelpDesk application = new InterfazHelpDesk();
				application.getJFrame().setVisible(true);
			}
		});
	}

	private InterfazHelpDesk()
	{
		escogerImplementacion( );
		inicializar( );
	}
	
	private void inicializar() {
		jContentPane = new JPanel();
		jContentPane.add(new PanelInicioSesion(this));
		jContentPane.setBorder(BorderFactory.createEmptyBorder(100,250,100,100));
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
			fileMenu.add(getSaveMenuItem());
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
			
		}
		return menuInfo;
	}



	private JMenuItem getPrefijos() {
		if(prefijos == null)
		{
			prefijos = new JMenuItem();
			prefijos.setText("Buscar Tickets por empleado...");
			prefijos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String prefijo = JOptionPane.showInputDialog(jFrame, "Introduzca el nombre del empleado.\nPuede usar wildcards (*) para buscar nombres incompletos.");
					try {
						JOptionPane.showMessageDialog(jFrame, mundo.darPrefijos(prefijo));
					}
					catch(Exception excepcion)
					{}
				}
			});
		}
		return prefijos;
	}

	private JMenuItem getTicket() {
		if (itemTicket == null) {
			itemTicket = new JMenuItem();
			itemTicket.setText("Ver ticket...");
			itemTicket.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,
					Event.CTRL_MASK, true));
			itemTicket.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String id = JOptionPane.showInputDialog(jFrame, "Introduzca el ID del ticket: ");
					try {
						JOptionPane.showMessageDialog(jFrame, mundo.darInfoTicket(Integer.parseInt(id)));
					}
					catch(Exception excepcion)
					{}
				}
			});
		}
		return itemTicket;
	}

	private JMenuItem getEmpleado() {
		if (itemEmpleado == null) {
			itemEmpleado = new JMenuItem();
			itemEmpleado.setText("Ver empleado con menos incidentes...");
			itemEmpleado.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
					Event.CTRL_MASK, true));
			itemEmpleado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(jFrame, mundo.darEmpleadoMenosIncidentes());
				}
			});
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
			exitMenuItem.setText("Salir");
			exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
					Event.CTRL_MASK, true));
			exitMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					try {
						mundo.guardar(RUTA_ARCHIVO);
					}
					catch(Exception e)
					{
						JOptionPane.showMessageDialog(jFrame, e.getMessage());
					}
					System.exit(0);
				}
			});
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
			
			menuCargar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						if(JOptionPane.showConfirmDialog(jFrame, "Si importa una nomina nueva, todos sus datos se perderan.\nEsta seguro de que desea continuar?") != JOptionPane.YES_OPTION)
							return;
						JFileChooser cargar = new JFileChooser();
						cargar.setCurrentDirectory(new File("data"));
						cargar.showOpenDialog(jFrame);
						if(cargar.getSelectedFile() != null)
						{
							reiniciar();
							mundo = FabricaHelpDesk.getInstance( ).fabricarImplementacion( );
							cerrarSesion();
							mundo.cargarListaEmpleados(cargar.getSelectedFile());
						}
					}
					catch(Exception e1)
					{
						JOptionPane.showMessageDialog(jFrame, e1.getMessage());
					}
				}
			});
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
			aboutMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JDialog aboutDialog = getAboutDialog();
					aboutDialog.pack();
					Point loc = getJFrame().getLocation();
					loc.translate(20, 20);
					aboutDialog.setLocation(loc);
					aboutDialog.setVisible(true);
				}
			});
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
	private JMenuItem getSaveMenuItem() {
		if (iniciarSesionMenuItem == null) {
			iniciarSesionMenuItem = new JMenuItem();
			iniciarSesionMenuItem.setText("Cerrar Sesion");
			iniciarSesionMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
					Event.CTRL_MASK, true));
			iniciarSesionMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cerrarSesion();
				}
			});
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
			jFrame.setSize(800, 500);
			jContentPane.removeAll();
			jContentPane.add( new PanelAdmin( this,mundo.darListaTicketsEntreFechas(new Date(), new Date()), mundo.darEmpleadosDelMes(),mundo.darListaPersonasAtendidas(), mundo.darListaIncidentes(true, new Date())), BorderLayout.CENTER);
			jContentPane.add( new BarraEstado(this,mundo));
			jContentPane.validate();
			jContentPane.setBorder(null);
			jFrame.setTitle("HelpDesk - Administrador");
		}
		else JOptionPane.showMessageDialog(jFrame, "Por favor no trate de violar nuestra \"seguridad\".");
		jFrame.validate();
	}

	public void iniciarSesion(IUsuario usuario) {
		
		mundo.iniciarSesion(usuario);
		
		jFrame.setSize(670, 500);
		jContentPane.removeAll();
		
		if( usuario.esEmpleado() )
		{
			jContentPane.add( new PanelEmpleado(this,mundo.darListaTicketsUsuarioActual()) );
		}
		else
		{
			jContentPane.add(new PanelCliente(this,mundo.darListaTicketsUsuarioActual()));
		}
		jContentPane.add( new BarraEstado(this,mundo));
		jContentPane.validate();
		jContentPane.setBorder(null);
		jFrame.setTitle("HelpDesk - " + usuario.darNombre());		
		jFrame.validate();
	}

	public void cerrarSesion() {
		
		mundo.iniciarSesion(null);
		
		jContentPane.removeAll();
		jFrame.setSize(670, 500);
		jContentPane.add( new PanelInicioSesion(this) );
		jContentPane.validate();
		jContentPane.setBorder(BorderFactory.createEmptyBorder(100,250,100,100));
		jFrame.setTitle("HelpDesk" );	
		jFrame.validate();
	}
	
	public void crearCliente(String text) {
		
		JDialog dialogo = new DialogoClienteNuevo( this,text );
		dialogo.setVisible(true);
		dialogo.setLocationRelativeTo(jFrame);
		jFrame.setEnabled(false);
		jFrame.validate();			
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
			JOptionPane.showMessageDialog(jFrame, e.getMessage());
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

}

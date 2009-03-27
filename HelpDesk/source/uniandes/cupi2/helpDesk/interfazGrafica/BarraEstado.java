package uniandes.cupi2.helpDesk.interfazGrafica;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import uniandes.cupi2.helpDesk.interfazMundo.IHelpDesk;
import uniandes.cupi2.helpDesk.mundo.HelpDesk;

public class BarraEstado extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 354341L;
	private JLabel numeroSinAtender;
	private JLabel numeroSiendoAtendidos;
	private JLabel numeroCerrados;
	private InterfazHelpDesk padre;
	
	public BarraEstado(InterfazHelpDesk padre, IHelpDesk mundo)
	{
		((HelpDesk)mundo).addObserver( this );
		this.padre = padre;
		
		numeroSinAtender = new JLabel();
		numeroSiendoAtendidos = new JLabel();
		numeroCerrados = new JLabel();
		
		actualizarNumeros();
		
		add(new JLabel("Tickets sin atender:"));
		add(numeroSinAtender);
		add(new JLabel("   Tickets siendo atendidos:"));
		add(numeroSiendoAtendidos);
		add(new JLabel("   Tickets cerrados:"));
		add(numeroCerrados);
	}
	
	private void actualizarNumeros()
	{
		numeroSinAtender.setText( String.valueOf(padre.darNumeroSinAtender()));
		numeroSiendoAtendidos.setText( String.valueOf(padre.darNumeroSiendoAtendidos()));
		numeroCerrados.setText( String.valueOf(padre.darNumeroCerrados()));
	}
	
	public void update(Observable arg0, Object arg1) {
		actualizarNumeros();	
	}

}

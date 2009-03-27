package uniandes.cupi2.helpDesk.interfazMundo;

/**
 * Es la representación abstracta de la fábrica
 */
public abstract class FabricaAbstracta
{
    // --------------------------------------------------
    // Métodos
    // --------------------------------------------------
    
    /**
     * Crea una implementación del helpDesk
     */
    public abstract IHelpDesk fabricarImplementacion( );

}

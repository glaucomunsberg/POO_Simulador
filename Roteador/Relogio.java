/**
 * O relógio é um singleton 
 *  que visa ter apenas um relógio disponível
 */
package Roteador;
public class Relogio {
    private static final Relogio relogio = new Relogio();
    private static double time;
    
    /**
     * Construtor padrão private
     *  para evitar mais de uma instanciação
     */
    private Relogio(){
        time = 0;
    }
    
    
    /**
     * Método getRelogio serve para retornar
     *  a unica instancia do relogio
     * @return Relogio relogio
     */
    public static Relogio getRelogio(){
        return relogio;
    }
    
    /**
     * Insere uma tempo
     * @param double newTime 
     */
    public static void increaseTime(double newTime){
        time += newTime;
    }
    
}

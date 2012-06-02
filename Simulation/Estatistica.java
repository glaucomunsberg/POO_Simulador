
package Simulation;
public class Estatistica {
    protected static int totalDeEventos = 0; 
    protected static int[] numeroDeMensagens;
    protected static int[] numeroDeErros;
    protected static int[] numeroDeSucesso;
    
    /**
     * Inicia arrays de contagens de erros,sucessos e mensagens de alerta.
     * 
     * @param int numDeComputadores 
     */
    public Estatistica(int numDeComputadores)
    {
        numeroDeMensagens = new int[numDeComputadores];
        numeroDeErros = new int[numDeComputadores];
        numeroDeSucesso = new int[numDeComputadores];
    }
    
    /**
     * Recebe o IP do computador, exibe a mensagem e contabiliza
     * @param int IPComputador
     * @param String mensagem 
     */
   public static void mensagens(int IPComputador, String mensagem)
   {
       numeroDeMensagens[IPComputador-1] +=1;
       totalDeEventos++;
       System.out.println("Alert : " + mensagem);
   }
   
    /**
     * Recebe o IP do computador, exibe o erro e contabiliza
     * @param int IPComputador
     * @param String mensagem 
     */
   public static void erro(int IPComputador, String mensagem)
   {
       numeroDeErros[IPComputador-1] +=1;
       totalDeEventos++;
       System.out.println("Error : " + mensagem);
   }

    /**
     * Recebe o IP do computador, exibe o sucesso e contabiliza
     * @param int IPComputador
     * @param String mensagem 
     */
   public static void sucesso(int IPComputador, String mensagem)
   {
       numeroDeSucesso[IPComputador-1] +=1;
       totalDeEventos++;
       System.out.println("Sucess: " + mensagem);
   }
   
   /**
    * Método que formula o relatório final
    */
   public static void finalizar()
   {
       
   }
}

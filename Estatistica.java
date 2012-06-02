public class Estatistica {
    protected static int totalDeEventos = 0; 
    
    /**
     * Inicia arrays de contagens de erros,sucessos e mensagens de alerta.
     * 
     * @param int numDeComputadores 
     */
    public Estatistica(int numDeComputadores)
    {
    }
    
    /**
     * Recebe o IP do computador, exibe a mensagem e contabiliza
     * @param int IPComputador
     * @param String mensagem 
     */
   public static void mensagens(int IPComputador, String mensagem)
   {
       totalDeEventos++;
   }
   
    /**
     * Recebe o IP do computador, exibe o erro e contabiliza
     * @param int IPComputador
     * @param String mensagem 
     */
   public static void erro(int IPComputador, String mensagem)
   {
       totalDeEventos++;
   }

    /**
     * Recebe o IP do computador, exibe o sucesso e contabiliza
     * @param int IPComputador
     * @param String mensagem 
     */
   public static void sucesso(int IPComputador, String mensagem)
   {
       totalDeEventos++;
   }
   
   /**
    * Método que formula o relatório final
    */
   public static void finalizar()
   {
   }
}


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
       int pc=1;
       System.out.printf("*******************************************************\n");
       System.out.printf("*                      Relatório                      *\n");
       System.out.printf("*******************************************************\n");
       for(int ip =0; ip < numeroDeSucesso.length; ip++)
       {
           System.out.printf("*Computador IP %d                                      *\n", pc);
           System.out.printf("* Erros: %d                                            *\n",numeroDeErros[ip]);
           System.out.printf("* Sucesso: %d                                          *\n",numeroDeSucesso[ip]);
           System.out.printf("* Alerta: %d                                           *\n",numeroDeMensagens[ip]);
           if(ip+1 < numeroDeSucesso.length )
                System.out.printf("-------------------------------------------------------\n");
       }
       System.out.printf("*******************************************************\n");
       System.out.printf("*Total de mensagens: %d\n                              *\n",totalDeEventos);
       System.out.printf("*******************************************************\n");
   }
}

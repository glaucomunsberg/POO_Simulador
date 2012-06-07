package Roteador;
import java.util.Random;
import java.util.Scanner;

public class Estatistica {
    private static int totalDeEventos = 0;
    private static int numDeComputadores = 0;
    private static boolean pcs[];
    private static Double velocidadeDaInternet = 0.0;
    private static Double velocidadeDaIntranet = 0.0;
    private static Double chanceDeErro = 0.0;
    private static Double chanceDeDesligar=0.0;
    private static Double tamanhoMaximoPacote = 0.0;
    private static Double desvioPadrao = 0.0;
    public static Scanner leitor;
    private static int erro[];
    private static int desligado[];
    private static int acerto[];
    private static Double duracaoTotal = 0.0;
    
    
    /**
     * Contem todas as informações necessárias para os eventos e do próprio Simulador
     * 
     * @param int numDeComputadores 
     */
    
    public Estatistica()
    {
        leitor = new Scanner(System.in);
        String comandosDeConfig = leitor.nextLine();
        String[] config = comandosDeConfig.split(",");
        switch(config.length)
        {
            case 1:
                numDeComputadores = Integer.parseInt( String.format("%s", config[0] ) );
                break;
            case 2:
                numDeComputadores = Integer.parseInt( String.format("%s", config[0] ) );
                velocidadeDaInternet = Double.parseDouble( String.format("%s", config[1] ) );
                break;
            case 3:
                numDeComputadores = Integer.parseInt( String.format("%s", config[0] ) );
                velocidadeDaInternet = Double.parseDouble( String.format("%s", config[1] ) );
                velocidadeDaIntranet = Double.parseDouble( String.format("%s", config[2] ) );
                break;
            case 4:
                numDeComputadores = Integer.parseInt( String.format("%s", config[0] ) );
                velocidadeDaInternet = Double.parseDouble( String.format("%s", config[1] ) );
                velocidadeDaIntranet = Double.parseDouble( String.format("%s", config[2] ) );
                chanceDeErro = Double.parseDouble( String.format("%s", config[3] ) );
                break;
            case 5:
                numDeComputadores = Integer.parseInt( String.format("%s", config[0] ) );
                velocidadeDaInternet = Double.parseDouble( String.format("%s", config[1] ) );
                velocidadeDaIntranet = Double.parseDouble( String.format("%s", config[2] ) );
                chanceDeErro = Double.parseDouble( String.format("%s", config[3] ) );
                chanceDeDesligar = Double.parseDouble( String.format("%s", config[4] ) );
                break;
            case 6:
                numDeComputadores = Integer.parseInt( String.format("%s", config[0] ) );
                velocidadeDaInternet = Double.parseDouble( String.format("%s", config[1] ) );
                velocidadeDaIntranet = Double.parseDouble( String.format("%s", config[2] ) );
                chanceDeErro = Double.parseDouble( String.format("%s", config[3] ) );
                chanceDeDesligar = Double.parseDouble( String.format("%s", config[4] ) );
                tamanhoMaximoPacote = Double.parseDouble( String.format("%s", config[5] ) );
                break;
            case 7:
                numDeComputadores = Integer.parseInt( String.format("%s", config[0] ) );
                velocidadeDaInternet = Double.parseDouble( String.format("%s", config[1] ) );
                velocidadeDaIntranet = Double.parseDouble( String.format("%s", config[2] ) );
                chanceDeErro = Double.parseDouble( String.format("%s", config[3] ) );
                chanceDeDesligar = Double.parseDouble( String.format("%s", config[4] ) );
                tamanhoMaximoPacote = Double.parseDouble( String.format("%s", config[5] ) );
                desvioPadrao = Double.parseDouble( String.format("%s", config[6] ) );
                break;
            default:
                System.out.println("Número de parâmetros de entrada não condiz com o esperado.");
                System.out.println("Padrão: <nº de pcs>,<velocidade internet>,"
                                    + "<velocidade interna>,<chance erro>,<chance desligado>,"
                                    + "<tamanho máximo da pacotes>,<desvio padrão>.");
                System.out.println("Execução irá abortar.");
                System.exit(1);
        }
        pcs = new boolean[numDeComputadores];
        erro = new int[numDeComputadores];
        desligado = new int[numDeComputadores];
        acerto = new int[numDeComputadores];
        pcs[0] = true;
        pcs[1] = true;
        System.out.printf("Tanho MAXIMO: %f\n", tamanhoMaximoPacote);
    }
    
    public static void setDesligaComputador(int desl)
    {
        if(desl >= 0 && desl <= numDeComputadores)
        {
            pcs[desl-1] = false;
        }
    }
    
    public static void setLigarComputador(int lig)
    {
        if(lig > 0 && lig <= numDeComputadores)
        {
            pcs[lig-1] = true;
        }
    }
    
    public static boolean checkStatus(int computador)
    {
        Random random = new Random();
        
        if(computador > 0 && computador <= numDeComputadores)
        {
            if (pcs[computador-1] == true){
                if (! ((random.nextGaussian()*chanceDeDesligar) < chanceDeDesligar) ){
                   return true;
                }
            }
        }
        return false;

        
    }
    
    public static int getNumDeComputadores()
    {
        return numDeComputadores;
    }
    
    public static Double getVelocidadeDaInternet()
    {
        return velocidadeDaInternet;
    }
    
    public static Double getVelocidadeDaIntranet()
    {
        return velocidadeDaIntranet;
    }
    
    public static Double getChanceDeErro()
    {
        return chanceDeErro;
    }
    
    public static Double getChanceDeDesligar()
    {
        return chanceDeDesligar;
    }
    
    public static Double getTamanhoMaximoPacote()
    {
        return tamanhoMaximoPacote;
    }
    
    public static Double getDesvioPadrao()
    {
        return desvioPadrao;
    }
    
    public static void erro(int ipOrigem, int ipDestino[], double duracao, int op)
   {
       //numeroDeErros[IPComputador] +=1;
       erro[ipOrigem - 1] += 1;
       totalDeEventos++;
       
       String mensagem = "";
       String mensagem2 = "";
             
        switch(op){
            case 0: mensagem = "enviar";
                    mensagem2 = "à";
                break;
            case 1: mensagem = "receber";
                    mensagem2 = "de";
                break;
        }

        System.out.println("Error: Falha ao " + mensagem + " pacote de 127.0.0."+ ipOrigem +" "+mensagem2+" "
                +ipDestino[0]+"."+ipDestino[1]+"."+ipDestino[2]+"."+ipDestino[4]);
    
       duracaoTotal += duracao;
   }
    
    public static void desligado(int ipOrigem, int ipDestino[], double duracao, int op)
    {
       //numeroDeErros[IPComputador] +=1;
       erro[ipOrigem - 1] += 1;
       totalDeEventos++;
       
       String mensagem = "";
       String mensagem2 = "";
             
        switch(op){
            case 0: mensagem = "enviar";
                    mensagem2 = "à";
                break;
            case 1: mensagem = "receber";
                    mensagem2 = "de";
                break;
        }

        System.out.println("Error: Falha ao " + mensagem + " pacote de 127.0.0."+ ipOrigem +" "+mensagem2+" "
                +ipDestino[0]+"."+ipDestino[1]+"."+ipDestino[2]+"."+ipDestino[4]);
        System.out.println("\tComputador Desligado");
    
       duracaoTotal += duracao;
    }
    
    public static void acerto(int ipOrigem, int ipDestino[], double duracao, int op)
    {
       //numeroDeErros[IPComputador] +=1;
       acerto[ipOrigem - 1] += 1;
       totalDeEventos++;
       
       String mensagem = "";
             
        switch(op){
            case 0: mensagem = "enviar";
                break;
            case 1: mensagem = "receber";
                break;
        }

        System.out.println("Sucess: Pacote "+mensagem+" com sucesso.");
    
       duracaoTotal += duracao;
   }
    
    
    public static void finalizar()
    {
       System.out.printf("*******************************************************\n");
       System.out.printf("*              Características do Sistema             *\n");
       System.out.printf("*******************************************************\n");
       
       System.out.printf("* Número de computadores na rede: %d computadores     *\n", numDeComputadores);
       System.out.printf("* Velocidade da Internet: %.2f Mb/s                   *\n", velocidadeDaInternet);
       System.out.printf("* Velocidade conexão interna: %.2f Mb/s               *\n", velocidadeDaIntranet);
       System.out.printf("* Tamanho máximo de Pacote: %.2f MB                   *\n", tamanhoMaximoPacote);
       System.out.printf("-------------------------------------------------------\n");
       System.out.printf("* Chance de erro: %.2f %%                             *\n", chanceDeErro);
       System.out.printf("* Chance de computador desligado: %.2f %%             *\n", chanceDeDesligar);
       System.out.printf("* Desvio Padrão: %.2f                                 *\n", desvioPadrao);
       System.out.printf("*******************************************************\n");
       
       System.out.println();
       
       System.out.printf("*******************************************************\n");
       System.out.printf("*                      Relatório                      *\n");
       System.out.printf("*******************************************************\n");
       
       for(int ip =0; ip < acerto.length; ip++)
       {
           System.out.printf("*Computador IP 127.0.0.%d                              *\n", ip+1);
           System.out.printf("* Sucesso: %d                                          *\n",acerto[ip]);
           System.out.printf("* Erros: %d                                            *\n",erro[ip]);
           System.out.printf("*   Desligado: %d                                      *\n",desligado[ip]);
           if(ip+1 < acerto.length )
                System.out.printf("-------------------------------------------------------\n");
       }
       System.out.printf("*******************************************************\n");
       System.out.printf("*Total de mensagens: %d                               *\n",totalDeEventos);      
       System.out.printf("*Duração Total: %.2f                                  *\n",duracaoTotal);
       System.out.printf("*******************************************************\n");
    }
    
}

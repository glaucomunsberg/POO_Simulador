/**
 * A classe Estatisca tem o propósito de fazer o intermediário entre a classe Simulador
 *  e evento quanto se trata de Mensagens e estatistica
 */

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
     * Construtor padrão que faz a leitura dos parametros que servirão de base
     *  para a execução do simulador
     */
    public Estatistica()
    {
        leitor = new Scanner(System.in);
        String comandosDeConfig = leitor.nextLine();
        
           /* Apos fazer a leitura e dividir a string em substrins separando-as pelas virgulas
            *  então manda para o switch fazer as conversões e atribuiçãoes, caso não seja passado
            *   parametros então este terá valores padrões
            */
        
        if( comandosDeConfig != null )
        {
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
        }
        else
        {
            numDeComputadores = 5;
            velocidadeDaInternet = 10.0;
            velocidadeDaIntranet = 100.0;
            chanceDeErro = 3.0;
            chanceDeDesligar = 1.0;
            tamanhoMaximoPacote = 20.0;
            desvioPadrao = 3.0;
        }
        chanceDeErro = chanceDeErro/100;
        chanceDeDesligar = chanceDeDesligar/100;
        pcs = new boolean[numDeComputadores];
        erro = new int[numDeComputadores];
        desligado = new int[numDeComputadores];
        acerto = new int[numDeComputadores];
    }
    
    /**
     * Método que desliga o computador desde que este
     *  esteja dentro da rede
     * 
     * @param int desl 
     */
    public static void setDesligaComputador(int desl)
    {
        if(desl >= 0 && desl <= numDeComputadores)
        {
            pcs[desl-1] = false;
        }
    }
    
    /**
     * Método para ligar o computador desde que este
     *  esteja dentro da rede
     * 
     * @param int lig 
     */
    public static void setLigarComputador(int lig)
    {
        if(lig > 0 && lig <= numDeComputadores)
        {
            pcs[lig-1] = true;
        }
    }
    
    /**
     * Método checkStatus, usado para verificar se o computador
     *  está ligado ou não, com isso antes é gerado aleatóriamente
     *  a chance desde mesmo se ligar ou desligar
     * 
     * @param int computador
     * @return boolean {true - se ligado | false - se desligado}
     */
    public static boolean checkStatus(int computador)
    {
        Random random = new Random();
        
        
        try
        {
            if(computador > 0 && computador <= numDeComputadores)
            {
              if (pcs[computador-1] == true){
                  if (! ((random.nextDouble()) < chanceDeDesligar) ){
                     setLigarComputador(computador-1);
                  }
              }
               return pcs[computador-1];
            }
            return pcs[computador-1];
        }
        catch(ArrayIndexOutOfBoundsException e ){
            return false;
        }
        
    }
    
    /**
     * Método que retorna o número de computadores
     *  que pertencem a rede
     * 
     * @return int num numDeComputadores
     */
    public static int getNumDeComputadores()
    {
        return numDeComputadores;
    }
    
    /**
     * Método que retorna a velocidade da Internet
     *  que é simulada
     * 
     * @return double velocidadeDaInternet
     */
    public static double getVelocidadeDaInternet()
    {
        return velocidadeDaInternet;
    }
    
    /**
     * Método que retorna o valor da intranet, ou seja
     *  da velocidade interna do aparelho
     * 
     * @return double velocidadeDaIntranet
     */
    public static double getVelocidadeDaIntranet()
    {
        return velocidadeDaIntranet;
    }
    
    /**
     * Método que retorna a chance de erro
     *  que tem roteador de dar
     * 
     * @return double chanceDeErro
     */
    public static double getChanceDeErro()
    {
        return chanceDeErro;
    }
    
    /**
     * Método que retorna a chace do computador
     *  se desligar durante a execução
     * 
     * @return double chanceDeDesligar
     */
    public static double getChanceDeDesligar()
    {
        return chanceDeDesligar;
    }
    
    /**
     * Método que retorna o tamanho máximo que
     *  um pacote dentro do roteador pode ter
     * 
     * @return double tamanhoMaximoPacote
     */
    public static double getTamanhoMaximoPacote()
    {
        return tamanhoMaximoPacote;
    }
    
    /**
     * Método que retorna o desvio padrão usado
     *  no roteador
     * @return double desvioPadrao
     */
    public static double getDesvioPadrao()
    {
        return desvioPadrao;
    }
    
    /**
     * Aqui começa uma série de janelas de mensagens que podem ser usado
     *  como parte do processamento de estatisca e de troca de mensagens
     *  relativas a sua utilização
     */ 
    
    
    /**
     * Método genérico de mensagem
     * @param int ipOrigem - ip, ultima parte de um pc interno
     * @param int[] ipDestino - ip completo, computador destino
     * @param double duracao - duração
     * @param int op - 1 enviar e 2 à
     */
    public static void erro(int[] ipOrigem, int[] ipDestino, double duracao, int op)
    {
       //numeroDeErros[IPComputador] +=1;
       if (op == 0){
            erro[ipOrigem[3] - 1] += 1;
       } else {
            erro[ipDestino[3] - 1] += 1;
       }
       totalDeEventos++;
       
       String mensagem = "";
       String mensagem2 = "";
             
        switch(op){
            case 0: mensagem = "enviado";
                    mensagem2 = "à";
                break;
            case 1: mensagem = "recebido";
                    mensagem2 = "de";
                break;
        }
        System.out.println("Error: Falha ao " + mensagem + " pacote " + " "+mensagem2+" "
                +ipDestino[0]+"."+ipDestino[1]+"."+ipDestino[2]+"."+ipDestino[3]);
    
       duracaoTotal += duracao;
   }
    
    /**
     * Método para intormar que o computador está desligado
     * @param int ipOrigem - ip, ultima parte de um pc interno
     * @param int[] ipDestino - ip completo, computador destino
     * @param double duracao - duração
     * @param int op - 1 enviar e 2 à
     */
    public static void desligado(int[] ipOrigem, int[] ipDestino, double duracao, int op)
    {
       //numeroDeErros[IPComputador] +=1;
       if (op == 0){
            desligado[ipOrigem[3] - 1] += 1;
       } else {
            desligado[ipDestino[3] - 1] += 1;
       }
       totalDeEventos++;
       
       String mensagem = "";
       String mensagem2 = "";
             
        switch(op){
            case 0: mensagem = "enviado";
                    mensagem2 = "à";
                break;
            case 1: mensagem = "recebido";
                    mensagem2 = "de";
                break;
        }

        System.out.println("Error: Falha ao " + mensagem + " pacote "+ mensagem2+" "
                +ipDestino[0]+"."+ipDestino[1]+"."+ipDestino[2]+"."+ipDestino[3]);
        System.out.println("\tComputador Desligado");
    
       duracaoTotal += duracao;
    }
    
    /**
     * Método usado para dizer que se acertou o pacote
     * @param int ipOrigem - ip, ultima parte de um pc interno
     * @param int[] ipDestino - ip completo, computador destino
     * @param double duracao - duração
     * @param int op - 1 enviar e 2 à
     */
    public static void acerto(int[] ipOrigem, int ipDestino[], double duracao, int op)
    {
       //numeroDeErros[IPComputador] +=1;
       if (op == 0){
            acerto[ipOrigem[3] - 1] += 1;
       } else {
            acerto[ipDestino[3] - 1] += 1;
       }
       totalDeEventos++;
       
       String mensagem = "";
             
        switch(op){
            case 0: mensagem = "enviado";
                break;
            case 1: mensagem = "recebido";
                break;
        }

        System.out.println("Sucess: Pacote "+mensagem+" com sucesso.");
    
       duracaoTotal += duracao;
   }
    
    /**
     * Método mais genérico, sem propósito estatistico
     * @param String mensagem 
     */
    public static void mensagemGenerica(String mensagem){
        System.out.printf("Mensage: %s\n",mensagem);
    }
    
    /**
     * Método de 
     * @param ipDestino
     * @param tipo 
     */
    public static void mensagemDeComando(int[] ipDestino, int tipo){
        switch(tipo){
            case 1:
                    System.out.println("Turn on "+ipDestino[0]+"."+ipDestino[1]+"."+ipDestino[2]+"."+ipDestino[3]);
                    break;
            case 2:
                    System.out.println("Turn off "+ipDestino[0]+"."+ipDestino[1]+"."+ipDestino[2]+"."+ipDestino[3]);
                    break;
            case 3:
                    System.out.println("Ping     "+ipDestino[0]+"."+ipDestino[1]+"."+ipDestino[2]+"."+ipDestino[3]);
                    break;
        }
    }
    /**
     * Método Finalizar tem o propósido de gerar relatório
     *  da execução do programa
     */
    public static void gerarRelatorio()
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
       System.out.printf("* Total de Pacotes: %d                                *\n", Evento.totalDePacotes);
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

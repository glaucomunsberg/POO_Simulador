/**
 * O evento aqui trata-se apenas de um, pois 
 *  sua execução no roteador, simula a troca de pacotes
 *  dentro de um roteador de rede, este opera sobre um tipo
 *  único de pacote que tem o ip de origem, destino e tamanho
 *  do seu pacote interno
 */

package Roteador;
import java.util.Random;

public class Evento {
    private double data;
    private double duracao;
    private int[] ipOriginem;
    private int[] ipDestino;
    static private int totalDePacotes;
    private int idPacote;
    private double tamanhoPacote;
    private static Random geradorAleatorio;
    
    /**
     * Construtor padrão
     * com todo os valores igual a zero
     */
    public Evento(){
        totalDePacotes+=1;
        idPacote = totalDePacotes;
        this.data = 0.0;
        this.duracao = 0.0;
        this.tamanhoPacote = 0.0;
        this.ipOriginem = new int[4];
        this.ipDestino = new int[4];
    }
    
    /**
     * Contrutor que
     *  copia o evento
     * @param Evento E 
     */
    public Evento(Evento E){
        totalDePacotes+=1;
        idPacote = totalDePacotes;
        this.data = E.data;
        this.duracao = E.duracao;
        this.ipOriginem = E.ipOriginem;
        this.ipDestino = E.ipDestino;
        this.tamanhoPacote = E.tamanhoPacote;
    }
    
    /**
     * Este método é realmente o motor do evento,
     * pois está incumbido dentro dele como deve ser executado
     *  e como deve ser o comportamento. Para isso o evento 
     *  analiza quais são suas informações como IP de origem e de destino
     *  e através dessas informações, juntamente com o seu tamanho, pode
     *  ser feito o seu tratamento
     */
    public void execucao(){
        geradorAleatorio = new Random();
        System.out.printf("ID: %d\nTamanho %.2f\n",this.idPacote, this.tamanhoPacote);
        /**
         * se for tudo zero, ou seja um evento limpo então este deve ser descartado
         */
        if(ipOriginem[0]+ipOriginem[1]+ipOriginem[2]+ipOriginem[3] == 0 || ipDestino[0]+ipDestino[1]+ipDestino[2]+ipDestino[3] == 0)
                return;
        
        if(ipOriginem[0] == 127 && Estatistica.checkStatus(ipDestino[3]) == false){
            Estatistica.desligado(ipOriginem[3], ipDestino, duracao, 0);
            ipOriginem[0] =0;
            ipOriginem[1] =0;
            ipOriginem[2] =0;
            ipOriginem[3] =0;
            this.setTamanhoPacote(0.0);
            return;
        }
            
        /**
         * Se ele começar diferente de 0, então este é um ip de origem de fora ou de dentro, mas não é um 
         *  ip usado como comando interno como de ligar, desligar ou ping
         */
        if(ipOriginem[0] != 0)
        {
            
            int desvio =  geradorAleatorio.nextInt(2);
            double aux;
            double velocidadePacote;
            
   
            /**
             * Se for destino 127, logo é para dentro e a 
             * velocidade é maior do que se fosse para fora
             */
            double tamanhoLocal = 0; //tamanho real de pacote gerenciado por este evento
            
            if (this.tamanhoPacote >= Estatistica.getTamanhoMaximoPacote()){
                tamanhoLocal = Estatistica.getTamanhoMaximoPacote();
            } else {
                tamanhoLocal = this.tamanhoPacote;
            }
            
            if( this.ipDestino[0] == 127 && this.ipOriginem[0] == 127  ) //true: transferência interna
            {
                aux = (Estatistica.getDesvioPadrao() / 100) * Estatistica.getVelocidadeDaIntranet();
                velocidadePacote = tamanhoLocal / (Estatistica.getVelocidadeDaIntranet()*0.1);
            }
            else
            {
                aux = (Estatistica.getDesvioPadrao() / 100) * Estatistica.getVelocidadeDaInternet();
                if (this.ipOriginem[0] == 127) { //true: upload
                    velocidadePacote = tamanhoLocal / (Estatistica.getVelocidadeDaInternet()*0.05);
                } else { //download
                    velocidadePacote = tamanhoLocal / (Estatistica.getVelocidadeDaInternet()*0.1);
                }
            }
                        
            /**
             * Sendo gerado um desvio
             *  este pode ser para menos, para mais ou não tendo desvio
             *  e isso é feito no switch
             */
            double aux2;
            do{
                   aux2 = (int) aux * geradorAleatorio.nextGaussian();
              } while(aux2 > aux || aux2 < 0);
            
            double desvioPacote;
            switch(desvio)
            {
                case 0:
                    desvioPacote = velocidadePacote - aux2;
                    break;
                case 1:
                    desvioPacote = velocidadePacote + aux2;
                    break;
                default:
                    desvioPacote = velocidadePacote;
            }
            this.setDuracao(desvioPacote);
            
            /**
             * deve ser executado
             *  para isso verifica-se se ele é maior, se é maior ele será processado a parte correspondende
             *  ao maximoDoPacote e o restante é enviado para gerarProximoPacote que enviará ao simulador
             *  para colocar na fica..assim ele vai até não ter mais nada e o gerar próximo for nulo
             *  e a fila começar a nadar e cada vez vai decremetando o tamanho e o tempo vai dominuindo
             * (isso simula os pedaços que chegam)
             * 
             */
            if( this.getTamanhoPacote() >= Estatistica.getTamanhoMaximoPacote())
            {
                
                //Relogio.setTime( this.getDuracao() );
                this.setTamanhoPacote( this.getTamanhoPacote() - Estatistica.getTamanhoMaximoPacote() );
            }
        
        }
        //Trexo do código que está dedicado a comandos do tipo "desligar",ligar e ping
        else
        {
            /**
             * Pega a ultima parte do 0.0.0.? se 1 liga computador destinho
             *                                se 2 desliga computador destino
             *                                se 3 manda um ping para o computador
             */
                        
            switch(ipOriginem[3])
            {
                case 1://Liga computador
                        Estatistica.mensagemDeComando(ipDestino, this.ipOriginem[3]);
                        if(ipDestino[0] == 127 && ipDestino[1] == 1 && ipDestino[2] == 1){
                            if(ipDestino[3] > 0 && ipDestino[3] <= Estatistica.getNumDeComputadores()){
                                Estatistica.setLigarComputador(ipDestino[3]);
                            }
                        }
                    
                    break;
                case 2://Desliga Computador
                        Estatistica.mensagemDeComando(ipDestino, this.ipOriginem[3]);
                        if(ipDestino[0] == 127 && ipDestino[1] == 1 && ipDestino[2] == 1){
                            if(ipDestino[3] > 0 && ipDestino[3] <= Estatistica.getNumDeComputadores()){
                                Estatistica.setDesligaComputador(ipDestino[3]);
                            }
                            else
                            {
                                Estatistica.erro(this.ipOriginem[3], ipDestino, duracao, 0);
                            }
                        }
                    break;
                case 3:
                        /**
                         * Ping se for 127.1.1.? é para um computador interno e deve ser testado
                         *  se não for então é para a web e por padrão tem um pong de resposta
                         */
                        Estatistica.mensagemDeComando(ipDestino, this.ipOriginem[3]);
                        if(ipDestino[0] == 127 && ipDestino[1] == 1 && ipDestino[2] == 1){
                            if(ipDestino[3] > 0 && ipDestino[3] <= Estatistica.getNumDeComputadores()){
                                if( Estatistica.checkStatus(ipDestino[3]) ){
                                    System.out.printf("Pong!\n");
                                }
                                else
                                {
                                    Estatistica.desligado(ipDestino[3], ipDestino, duracao, 0);
                                    Estatistica.mensagemGenerica(String.format("error server %d.%d.%d.%d not found or server may be down\n", ipDestino[0],ipDestino[1],ipDestino[2],ipDestino[3]));
                                }
                            }
                        }
                        else
                        {
                            Estatistica.acerto(ipDestino[3], ipDestino, duracao, 0);
                            Estatistica.mensagemGenerica("Pong!\n");
                        }
                    break;
            }
        
        }
        
    }
    
   /**
    * Método gerar o próximo tem importância ao ver que o seu tamanho
    *   é maior do que a do tido como máximo no simulador
    *   isso faz com que o próprio evento seja retornado, sendo que uma
    *   parte já foi processada no executar().
    * 
    * @return Evento ou null
    */
    public Evento gerarProximo(){
        if( this.getTamanhoPacote() >= Estatistica.getTamanhoMaximoPacote() )
        {
            Estatistica.acerto(ipDestino[3], ipDestino, duracao, 0);
            return this;
        }
        else
        {
            return null;
        }

    }
    
    /**
     * Método que insere a data
     * 
     * @param double data 
     */
    public void setData(double data){
        this.data = data;
    }
    
    /**
     * Métdo que inser o tamanho do pacote
     * 
     * @param double tamanho 
     */
    public void setTamanhoPacote(double tamanho)
    {
        tamanhoPacote = tamanho;
    }
    
    /**
     * Método que insere a duração que o pacote terá
     * 
     * @param double duracao 
     */
    public void setDuracao(double duracao){
        this.duracao = duracao;
    }
    
    /**
     * Método que insere o Id do pacotr
     *  caso esse seja passivel de numeração
     * 
     * @param int id 
     */
    public void setIDPacote(int id){
        this.idPacote = id;
    }
    
    /**
     * Método que insere um ip de origem para o pacote
     *  note que esse valor deve respeitar a tabela
     *  'comandos'
     *  Exemplo: 127.1.1.1
     * 
     * @param ip[] 
     */
    public void setIpOrigem(int[] ip){
        this.ipOriginem = ip;   
    }
    
    /**
     * Método que insere um ip de destino para o pacote
     *  note que esse valor deve respeitar a tabela
     *  'comandos'
     *  Exemplo: 127.1.1.1
     * 
     * @param ip[] 
     */
    public void setIpDestino(int[] ip){
        this.ipDestino = ip;     
    }
    
    /**
     * Retorna a data do pacote
     * 
     * @return double data
     */
    public double getData(){
        return this.data;
    }
    
    /**
     * Método que retorna a duração
     * do pacote atual
     * 
     * @return double duracao
     */
    public double getDuracao(){
        return this.duracao;
    }
    
    /**
     * Retorna o IP origem deste pacote
     * 
     * @return int[] ipOrigem
     */
    public int[] getIPOrigem(){
        return this.ipOriginem;
    }
    
   /**
     * Retorna o IP destino deste pacote
     * 
     * @return int[] ipDestino
     */
    public int[] getIPDestino(){
        return this.ipDestino;
    }
    
    /**
     * Retorna o ID deste pacote
     * @return int idPacote
     */
    public int getIDPacote(){
        return this.idPacote;
    }

    /**
     * Retorna o tamanho do pacote
     * @return double tamanhoPacote
     */
    public double getTamanhoPacote()
    {
        return tamanhoPacote;   
    }
}

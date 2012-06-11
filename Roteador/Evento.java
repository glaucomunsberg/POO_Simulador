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
    static private int idPacote;
    private double tamanhoPacote;
    private static Random geradorAleatorio;
    
    /**
     * Construtor padrão
     * com todo os valores igual a zero
     */
    public Evento(){
        idPacote+=1;
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
        idPacote+=1;
        this.data = E.data;
        this.duracao = E.duracao;
        this.ipOriginem = E.ipOriginem;
        this.ipDestino = E.ipDestino;
        this.tamanhoPacote = E.tamanhoPacote;
    }
    
    /**
     * Construtor especial que não apenas cria um evento
     *  todo zerado, como também o retorna
     * @return Evento Temp
     */
    public Evento eventoClear()
    {
        Evento temp = new Evento();
        return temp;
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
        /**
         * se for tudo zero, ou seja um evento limpo então este deve ser descartado
         */
        if(ipOriginem[0]+ipOriginem[1]+ipOriginem[2]+ipOriginem[3] == 0 && ipDestino[0]+ipDestino[1]+ipDestino[2]+ipDestino[3] == 0)
                return;
        
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
            if( this.ipDestino[0] == 127 )
            {
                aux = (Estatistica.getDesvioPadrao() / 100) * Estatistica.getVelocidadeDaIntranet();
                velocidadePacote = Estatistica.getTamanhoMaximoPacote() / (Estatistica.getVelocidadeDaIntranet()*1);
            }
            else
            {
                aux = (Estatistica.getDesvioPadrao() / 100) * Estatistica.getVelocidadeDaInternet();
                velocidadePacote = Estatistica.getTamanhoMaximoPacote() / (Estatistica.getVelocidadeDaInternet()*1);
            }
            
            /**
             * Sendo gerado um desvio
             *  este pode ser para menos, para mais ou não tendo desvio
             *  e isso é feito no switch
             */
            double aux2;
            do{
                   aux2 = (int) aux + geradorAleatorio.nextGaussian();
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
            Estatistica.mensagemGenerica(String.format(" tamanho pacote: %f tamanho maximo: %f ", getTamanhoPacote(), Estatistica.getTamanhoMaximoPacote()));
            if( this.getTamanhoPacote() >= Estatistica.getTamanhoMaximoPacote())
            {
                
                Relogio.increaseTime( this.getDuracao() );
                this.setTamanhoPacote( this.getTamanhoPacote() - Estatistica.getTamanhoMaximoPacote() );
                Estatistica.mensagemGenerica(String.format("Executando,gerará um de tamanho %.2f\n",this.getTamanhoPacote()));
            }
            else
            {
                Estatistica.mensagemGenerica("Executado\n");
                Relogio.increaseTime( this.getDuracao() );
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
                        System.out.printf("Liga comnputador!\n");
                        if(ipDestino[0] == 127 && ipDestino[1] == 1 && ipDestino[2] == 1){
                            if(ipDestino[3] > 0 && ipDestino[3] <= Estatistica.getNumDeComputadores()){
                                Estatistica.setLigarComputador(ipDestino[3]);
                            }
                        }
                    
                    break;
                case 2://Desliga Computador
                        Estatistica.mensagemGenerica("Desligando Computador");
                        if(ipDestino[0] == 127 && ipDestino[1] == 1 && ipDestino[2] == 1){
                            if(ipDestino[3] > 0 && ipDestino[3] <= Estatistica.getNumDeComputadores()){
                                Estatistica.setDesligaComputador(ipDestino[3]);
                            }
                            else
                            {
                                Estatistica.erro(idPacote, ipDestino, duracao, idPacote);
                            }
                        }
                    break;
                case 3:
                        /**
                         * Ping se for 127.1.1.? é para um computador interno e deve ser testado
                         *  se não for então é para a web e por padrão tem um pong de resposta
                         */
                        Estatistica.mensagemGenerica(String.format("ping no computador %d.%d.%d.%d\n", ipDestino[0],ipDestino[1],ipDestino[2],ipDestino[3]));
                        if(ipDestino[0] == 127 && ipDestino[1] == 1 && ipDestino[2] == 1){
                            if(ipDestino[3] > 0 && ipDestino[3] <= Estatistica.getNumDeComputadores()){
                                if( Estatistica.checkStatus(ipDestino[3]) == true){
                                    System.out.printf("Pong!\n");
                                }
                                else
                                {
                                    Estatistica.mensagemGenerica(String.format("error server %d.%d.%d.%d not found or server may be down\n", ipDestino[0],ipDestino[1],ipDestino[2],ipDestino[3]));
                                }
                            }
                        }
                        else
                        {
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
            System.out.printf("proximo terá %.2f\n", this.getTamanhoPacote());
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
        System.out.printf("SetData\n");
        this.data = data;
    }
    
    /**
     * Métdo que inser o tamanho do pacote
     * 
     * @param double tamanho 
     */
    public void setTamanhoPacote(double tamanho)
    {
        System.out.printf("iniciou %.2f\n", this.getTamanhoPacote());
        tamanhoPacote = tamanho;
        System.out.printf("virou %.2f\n", this.getTamanhoPacote());
    }
    
    /**
     * Método que insere a duração que o pacote terá
     * 
     * @param double duracao 
     */
    public void setDuracao(double duracao){
        System.out.printf("setDuracao\n");
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
        System.out.printf("ID origem %d.%d.%d.%d\n", ipOriginem[0],ipOriginem[1],ipOriginem[2],ipOriginem[3]);
        
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
        System.out.printf("ID destino %d.%d.%d.%d\n", ipDestino[0],ipDestino[1],ipDestino[2],ipDestino[3]);
        
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

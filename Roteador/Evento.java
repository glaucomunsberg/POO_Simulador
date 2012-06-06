package Roteador;

import java.util.Random;

public class Evento {
    private double data;
    private double duracao;
    private int[] ipOriginem;
    private int[] ipDestino;
    private int idPacote;
    private double tamanhoPacote;
    private static Random geradorAleatorio;
    
    public Evento(){
        this.data = 0.0;
        this.duracao = 0.0;
        this.tamanhoPacote = 0.0;
        this.ipOriginem = new int[4];
        
        this.ipDestino = new int[4];
        
        geradorAleatorio = new Random();
    }
    
    public Evento(Evento E){
        this.data = E.data;
        this.duracao = E.duracao;
        this.ipOriginem = E.ipOriginem;
        this.ipDestino = E.ipDestino;
        this.tamanhoPacote = E.tamanhoPacote;
    }
    
    public Evento eventoClear()
    {
        Evento temp = new Evento();
        return temp;
    }
    
    public void execucao(){
        /**
         * Se a primeira parte do ip destino for igual a 0 é um comando interno do tipo ping, desliga,liga
         *  que deve ser capturado pelo rotiador e processado por ele
         * Os demais devem ser processados aqui 
         */
        if(ipOriginem[0]+ipOriginem[1]+ipOriginem[2]+ipOriginem[3] == 0 && ipDestino[0]+ipDestino[1]+ipDestino[2]+ipDestino[3] == 0)
                return;
        
        if(ipOriginem[0] != 0)
        {
            /**
             * aqui se dá o se quebrar ou erro ou coisa o tipo, Então ele gera a si mesmo no gerarProximo
             *  envia para a fila através do simulador
             * CODIGO AQUI
             */
            
            
            /**
             * Aqui está a modificação do tempo de duração segunda o tempo de execução de cada
             * pacote, assim o seu tempo aumenta ou diminui aleatóriamente mas deacordo com a velocidade
             *  interna ou externa do pacote
             */
            int desvio =  geradorAleatorio.nextInt(2);
            double aux;
            double velocidadePacote;
            
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
            System.out.printf(" tamanho pacote: %f tamanho maximo: %f ", getTamanhoPacote(), Estatistica.getTamanhoMaximoPacote() );
            if( this.getTamanhoPacote() >= Estatistica.getTamanhoMaximoPacote())
            {
                
                Relogio.increaseTime( this.getDuracao() );
                this.setTamanhoPacote( this.getTamanhoPacote() - Estatistica.getTamanhoMaximoPacote() );
                System.out.printf("Executando,gerará um de tamanho %.2f\n",this.getTamanhoPacote());
            }
            else
            {
                System.out.printf("Executado\n");
                Relogio.increaseTime( this.getDuracao() );
            }
        
        }
        //Trexo do código que está dedicado a comandos do tipo "desligar",ligar e ping
        else
        {
            /**
             * Pega a ultima parte do 0.0.0.? se 1 liga computador destinho
             *                                se 2 desliga computador destino
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
                        System.out.printf("Desliga!\n");
                        if(ipDestino[0] == 127 && ipDestino[1] == 1 && ipDestino[2] == 1){
                            if(ipDestino[3] > 0 && ipDestino[3] <= Estatistica.getNumDeComputadores()){
                                Estatistica.setDesligaComputador(ipDestino[3]);
                            }
                        }
                    break;
                case 3:
                        /**
                         * Ping se for 127.1.1.? é para um computador interno e deve ser testado
                         *  se não for então é para a web e por padrão tem um pong de resposta
                         */
                        System.out.printf("ping no computador %d.%d.%d.%d\n", ipDestino[0],ipDestino[1],ipDestino[2],ipDestino[3]);
                        if(ipDestino[0] == 127 && ipDestino[1] == 1 && ipDestino[2] == 1){
                            if(ipDestino[3] > 0 && ipDestino[3] <= Estatistica.getNumDeComputadores()){
                                if( Estatistica.checkStatus(ipDestino[3]) == true){
                                    System.out.printf("Pong!\n");
                                }
                                else
                                {
                                    System.out.printf("error server %d.%d.%d.%d not found or server may be down\n", ipDestino[0],ipDestino[1],ipDestino[2],ipDestino[3]);
                                }
                            }
                        }
                        else
                        {
                            System.out.printf("Pong!\n");
                        }
                    break;
            }
        
        }
        
    }
    
    public Evento gerarProximo(Double tamanhoMaximo){
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
    
    public void setData(double data){
        System.out.printf("SetData\n");
        this.data = data;
    }
    public void setTamanhoPacote(double tamanho)
    {
        System.out.printf("iniciou %.2f\n", this.getTamanhoPacote());
        tamanhoPacote = tamanho;
        System.out.printf("virou %.2f\n", this.getTamanhoPacote());
    }
    public void setDuracao(double duracao){
        System.out.printf("setDuracao\n");
        this.duracao = duracao;
    }
    public void setIDPacote(int id){
        this.idPacote = id;
    }
    
    public void setIpOrigem(int[] ip){
        this.ipOriginem = ip;
        System.out.printf("ID origem %d.%d.%d.%d\n", ipOriginem[0],ipOriginem[1],ipOriginem[2],ipOriginem[3]);
        
    }
    public void setIpDestino(int[] ip){
        this.ipDestino = ip;
        System.out.printf("ID destino %d.%d.%d.%d\n", ipDestino[0],ipDestino[1],ipDestino[2],ipDestino[3]);
        
    }
    
    public double getData(){
        return this.data;
    }
    
    public double getDuracao(){
        return this.duracao;
    }
    
    public int[] getIPOrigem(){
        return this.ipOriginem;
    }
    
    public int[] getIPDestino(){
        return this.ipDestino;
    }
    
    public int getIDPacote(){
        return this.idPacote;
    }

    public final double getTamanhoPacote()
    {
        return tamanhoPacote;   
    }
}

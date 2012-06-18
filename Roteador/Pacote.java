/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Roteador;
import java.util.Random;

/**
 *
 * @author marlon
 */
public class Pacote extends Evento {
    
    public Pacote(){
        super();
    }
    
    public Pacote(Evento E){
        super(E);
    }
    
    @Override
    final public void execucao(){
        if(ipOriginem[0]+ipOriginem[1]+ipOriginem[2]+ipOriginem[3] == 0 || ipDestino[0]+ipDestino[1]+ipDestino[2]+ipDestino[3] == 0)
                return;
        
        if(ipOriginem[0] == 127 && Estatistica.checkStatus(ipDestino[3]) == false){
            Estatistica.desligado(ipOriginem, ipDestino, duracao, 0);
            ipOriginem[0] =0;
            ipOriginem[1] =0;
            ipOriginem[2] =0;
            ipOriginem[3] =0;
            this.setTamanhoPacote(0.0);
            return;
        }
        
        if(ipOriginem[0] != 0)
        {
            geradorAleatorio = new Random();
            int desvio =  geradorAleatorio.nextInt(2);
            double aux;
            double velocidadePacote;
            
   
            /**
             * Se for destino 127, logo é para dentro e a 
             * velocidade é maior do que se fosse para fora
             */
            double tamanhoLocal = 0; //tamanho real de pacote gerenciado por este evento
            int op = 0;
            
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
                    op = 0;
                } else { //download
                    velocidadePacote = tamanhoLocal / (Estatistica.getVelocidadeDaInternet()*0.1);
                    op = 1;
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
                Random miss = new Random();
                if (miss.nextDouble() < Estatistica.getChanceDeErro())
                {
                    Estatistica.erro(ipOriginem, ipDestino, duracao, op);
                }
                else 
                {
                    int pc = ipDestino[3];
                    
                    if (ipOriginem[0] == 127)
                    {
                        pc = ipOriginem[3];
                    }
                    
                    if (Estatistica.checkStatus(pc))
                    {
                        this.setTamanhoPacote( this.getTamanhoPacote() - Estatistica.getTamanhoMaximoPacote() );
                        Estatistica.acerto(ipOriginem, ipDestino, duracao, op);
                    }
                    else
                    {
                        Estatistica.desligado(ipOriginem, ipDestino, duracao, op);
                        this.setTamanhoPacote(0);
                    }
                }
            }
        
        }
    }
}

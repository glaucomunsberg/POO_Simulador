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
public class LigarComputador extends Evento {
    
    public LigarComputador(){
        super();
    }
    
    public LigarComputador(Evento E){
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
        
        Estatistica.mensagemDeComando(ipDestino, this.ipOriginem[3]);
        if(ipDestino[0] == 127 && ipDestino[1] == 1 && ipDestino[2] == 1){
            if(ipDestino[3] > 0 && ipDestino[3] <= Estatistica.getNumDeComputadores()){
                Estatistica.setLigarComputador(ipDestino[3]);
            }
        }
    }
}

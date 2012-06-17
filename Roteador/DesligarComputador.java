/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Roteador;

/**
 *
 * @author marlon
 */
public class DesligarComputador extends Evento{
    
    DesligarComputador(){
        super();
    }
    
    DesligarComputador(Evento E){
        super(E);
    }
    
    @Override
    public void execucao(){
        Estatistica.mensagemDeComando(ipDestino, this.ipOriginem[3]);
        if(ipDestino[0] == 127 && ipDestino[1] == 1 && ipDestino[2] == 1){
            if(ipDestino[3] > 0 && ipDestino[3] <= Estatistica.getNumDeComputadores()){
                Estatistica.setDesligaComputador(ipDestino[3]);
            }
            else
            {
                Estatistica.erro(this.ipOriginem, ipDestino, duracao, 0);
            }
        }
    }
}

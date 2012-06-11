/**
 *  Gataway padrão 127.1.1.0
 *  Ip's Internos 127.1.1.x | 0 > x < 5 
 * 
 */ 
package Roteador;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Simulador extends JPanel {
    
    /**
     * O método deve carregar a lista de ações,
     * 	ou seja deve trazer de um arquivo todas as
     * 	acoes e as organizar de forma a adicioná-las
     * 	a lista "listaDeAcoes" para ser executada
     * 	pelo simulador
     *  @return ListaDeAcoes
     */
    public static ListaDeAcoes carregarListaDeSimulacao(){
        ListaDeAcoes temp = new ListaDeAcoes();
        String comando;
        
        /**
         *  Carrega os primeiros comandos para preencher a lista
         *  passa diretamente uma string para a lista processar 
         *  e criar os eventos, até que seja inserido um exit
         */
        
        do
        {
            comando = Estatistica.leitor.nextLine();
            if( !comando.equals("exit") )
            {
                temp.setAcaoPorComando(comando);//garante que os comandos são em minusculas
            }

        }while( !comando.equals("exit") );
        return temp;
    }
	
    /**
     * Método que recebe o objeto que deve ser manipulado segundo a sua
     * 	especificação
     * @param Acao acao
     * @return Boolean {true - se executou com sucesso | false - se não conseguiu executar }
     */
    public static boolean executarAcao(Evento acao){
        acao.execucao();
        return true;
    }
    
    /**
     * Execução de todas as ações
     * @param args the command line arguments
     */
    public void simular()
    {  
        Relogio relogio = Relogio.getRelogio();
        Estatistica estatistica = new Estatistica();  
        ListaDeAcoes lista = carregarListaDeSimulacao();
        Evento tempAcao;
        
        if(lista == null) {
            System.out.printf("Ooops! não tem mais nada na fila");
            System.exit(0);
        }
        
        while( !lista.isEmpty() ){
            tempAcao = lista.getAcao();		
            
            if(tempAcao != null){
		if (executarAcao(tempAcao)){
                    Relogio.increaseTime(tempAcao.getDuracao());
                    lista.setAcao(tempAcao.gerarProximo());
                } else {
                    /* Alterar */
                }
            }
        }
    }
    
    public void finalizar()
    {
        Estatistica.finalizar();
    }
    
    public static void main(String[] args) {
        Simulador simula = new Simulador();
        simula.simular();
        simula.finalizar();
    }
}

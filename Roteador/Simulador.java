/**
 *  Gataway padrão 127,1,1,0
 *  Ip's Internos 127,1,1,x | 0 > x < 5 
 * 
 */ 
package Roteador;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Simulador extends JFrame {
    static private JPanel JPanelTotal;
    static private JPanel [] computador = new JPanel[4]; 
    static private Color corCinza = Color.GRAY;
    static private Color corVerde = Color.GREEN;

                            
    
    public Simulador(){
        setTitle("Simulador");
        
        JPanelTotal = new JPanel();
        JPanelTotal.setBackground(Color.GRAY);
        JPanelTotal.setLayout(new GridLayout(1,5));
        
        computador[0] = new JPanel();
        computador[0].setSize(40, 130);
        computador[0].setBackground(corCinza);
        computador[0].setBounds(10, 10, 10, 10);
        JPanelTotal.add(computador[0]);
        
        computador[1] = new JPanel();
        computador[1].setSize(40, 130);
        computador[1].setBackground(corCinza);
        JPanelTotal.add(computador[1]);
        
        computador[2] = new JPanel();
        computador[2].setSize(40, 130);
        computador[2].setBackground(corCinza);
        JPanelTotal.add(computador[2]);
        
        computador[3] = new JPanel();
        computador[3].setSize(40, 130);
        computador[3].setBackground(corCinza);
        JPanelTotal.add(computador[3]);
        
        
        this.setBackground(Color.black);
        add(JPanelTotal);
        setSize(160,130);						//Já diz qual será o tamanho
	setVisible(true);
    
    }
    
    
    
    
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
        for(int a=0; a < Estatistica.getNumDeComputadores();a++){
           if( Estatistica.checkStatus(a+1))
           {
               computador[a].setBackground(corVerde);
           }
           else
           {
               computador[a].setBackground(corCinza);
           }
        }
        
                
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
            System.out.printf("Ooops! não tem mais nada na fila\n");
            System.exit(0);
        }
        
        while( !lista.isEmpty() ){
            tempAcao = lista.getAcao();		
            
            if(tempAcao != null){
		if (executarAcao(tempAcao)){
                    Relogio.setTime(tempAcao.getDuracao());
                    lista.setAcao(tempAcao.gerarProximo());
                } else {
                    /* Alterar */
                }
            }
        }
    }
    
    public void finalizar()
    {
        Estatistica.gerarRelatorio();
    }
    
    public static void main(String[] args) {
        Simulador simula = new Simulador();
        simula.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
        simula.setVisible(true);
        simula.simular();
        simula.finalizar();
    }
}

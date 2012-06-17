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
    static private JPanel painelComputadores;
    static protected JPanel [] computador = new JPanel[4];
    static private JPanel painelLigado;
    static private JPanel painelInternet;
    static private JPanel painelFundo;
    static private JPanel painelFundo2;
    static private Color corCinza = Color.GRAY;
    static private Color corVerde = Color.GREEN;
    static private Color corAmarela = Color.YELLOW;
    static private int velocidadeDaLed = 100;

                            
    
    public Simulador(){
        setTitle("Simulador");
        
        painelComputadores = new JPanel();
        painelComputadores.setBackground(Color.GRAY);
        painelComputadores.setLayout(new GridLayout(2,4));
        
        //Insindo a luz de Ligado e de Internet
        
        painelLigado = new JPanel();
        painelLigado.setBorder( javax.swing.BorderFactory.createTitledBorder("Ligado"));
        painelLigado.setBackground(Color.RED);
        painelLigado.setSize(40, 130);
        painelComputadores.add(painelLigado);
        
        painelInternet = new JPanel();
        painelInternet.setBorder( javax.swing.BorderFactory.createTitledBorder("Internet"));
        painelInternet.setBackground(Color.RED);
        painelInternet.setSize(40, 130);
        painelComputadores.add(painelInternet);
        
        painelFundo = new JPanel();
        painelFundo.setBackground(Color.white);
        painelFundo.setSize(40, 130);
        painelFundo2 = new JPanel();
        painelFundo2.setBackground(Color.white);
        painelFundo2.setSize(40, 130);
        painelComputadores.add(painelFundo);
        painelComputadores.add(painelFundo2);

        computador[0] = new JPanel();
        computador[0].setName("Computador 1");
        computador[0].setBorder( javax.swing.BorderFactory.createTitledBorder("Pc 1"));
        computador[0].setSize(40, 130);                    
        computador[0].setBackground(corCinza);
        computador[0].setBounds(10, 10, 10, 10);
        painelComputadores.add(computador[0]);
        
        computador[1] = new JPanel();
        computador[1].setName("Computador 2");
        computador[1].setBorder( javax.swing.BorderFactory.createTitledBorder("Pc 2"));
        computador[1].setSize(40, 130);
        computador[1].setBackground(corCinza);
        painelComputadores.add(computador[1]);
        
        computador[2] = new JPanel();
        computador[2].setName("Computador 3");
        computador[2].setSize(40, 130);
        computador[2].setBorder( javax.swing.BorderFactory.createTitledBorder("Pc 3"));
        computador[2].setBackground(corCinza);
        painelComputadores.add(computador[2]);
        
        computador[3] = new JPanel();
        computador[3].setName("Computador 4");
        computador[3].setBorder( javax.swing.BorderFactory.createTitledBorder("Pc 4"));
        computador[3].setSize(40, 130);
        computador[3].setBackground(corCinza);
        painelComputadores.add(computador[3]);
        
        this.setBackground(Color.black);
        add(painelComputadores);

        this.setResizable(false);
        setSize(400,130);						//Já diz qual será o tamanho
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
        int ipdestino[] = acao.getIPDestino();
        int ipOrigem[] = acao.getIPOrigem();
        if(ipOrigem[0] == 0)
            return true;
        if(ipOrigem[0] == 127 && ipOrigem[1] == 1 && ipOrigem[2] == 1 && Estatistica.checkStatus(ipOrigem[3])){
            computador[ipOrigem[3]-1].setBackground(corAmarela);
            try
            {
                Thread.sleep(velocidadeDaLed);
            } catch(Exception e) {
                //System.out.println("continuando");
            }
            if(computador[ipOrigem[3]-1].getBackground() != Color.GREEN)
                computador[ipOrigem[3]-1].setBackground(corVerde);
        }
        else
        {
        
                if(painelInternet.getBackground() != Color.RED )
                {
                    painelInternet.setBackground(corAmarela);
                    try
                    {
                        Thread.sleep(velocidadeDaLed);
                    } catch(Exception e) {
                        //System.out.println("continuando");
                    }
                    painelInternet.setBackground(corVerde);
                }
        }
        if( ipdestino[0] == 127 && ipdestino[1] == 1 && ipdestino[2] == 1 && Estatistica.checkStatus(ipdestino[3])){
            computador[ipdestino[3]-1].setBackground(corAmarela);
            try
            {
                Thread.sleep(velocidadeDaLed);
            } catch(Exception e) {
                //System.out.println("continuando");
            }
            if(computador[ipdestino[3]-1].getBackground() != Color.GREEN)
                computador[ipdestino[3]-1].setBackground(corVerde);
        }
            
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
        else{
            painelLigado.setBackground( Color.green);
            if( Estatistica.getVelocidadeDaInternet() > 0 )
                painelInternet.setBackground( Color.green);
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

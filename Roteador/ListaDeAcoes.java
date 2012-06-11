/**
 * Esse método contem uma série de eventos
 *  infileirados segundo a sua precedência e 
 *  de acordo com as necessidades do roteador,
 *  nela é possível adicionar, remover e até mesmo
 *  gerar eventos através de comandos por strings
 */

package Roteador;
import java.util.ArrayList;

public class ListaDeAcoes {
    protected ArrayList<Evento> acoes;
    
    /**
     * O construtor padrão
     *  apenas inicia a lista
     */
    public ListaDeAcoes() {
        acoes = new ArrayList<Evento>();
    }
    
    /**
     * Insere no final da lista a acao
     *  desde que este tenha um ip válido
     *  para isso leia 'comandos'
     * @param Acao acao
     */
    public void setAcao(Evento acao) {
        if(acao != null)
        {
            int[] ip = acao.getIPOrigem();
            if(  (ip[0] + ip[1] + ip[2] + ip[3] )  != 0 ) 
            {
                acoes.add(acoes.size(), acao);
                System.out.printf("Ação adicionada: %d\n", acoes.size());
            }
        }
        
        
    }
    
    /**
     * Esse método trata uma string que contém um comando
     *  inteiro, para isso ele deve respeitar um ip valido
     *  para isso leia 'comandos'
     * @param comando 
     */
    public void setAcaoPorComando(String comando)
    {
        String[] argumentos = comando.split(" ");
        //Verifica se é o mínimo aceitável
        switch(argumentos.length)
        {   
            case 2:
                    /**
                     * Se tem duas parte então ele é do tipo interno
                     * <Command> <IP destinho>
                     */
                    if("l".equals(argumentos[0])|| "ping".equals(argumentos[0]) || "d".equals(argumentos[0]))
                    {
                        //Cria um evento para ligar o computador
                        if( "l".equals(argumentos[0]) )
                        {
                            Evento temp = new Evento();
                            temp.setData(0.1);
                            
                            temp.setIpOrigem(new int[] {0,0,0,1});
                            
                            String[] ipDestino = argumentos[1].split(",");
                            temp.setIpDestino(new int[]{Integer.parseInt(ipDestino[0]),Integer.parseInt(ipDestino[1]),Integer.parseInt(ipDestino[2]),Integer.parseInt(ipDestino[3]) });  
                                
                            temp.setDuracao(0.1);
                            temp.setTamanhoPacote(0.1);                            
                            
                            this.setAcao(temp);
                        }
                        else
                        {
                            //Cria um evento que será para desligar o computador
                            if( "d".equals(argumentos[0]) )
                            {
                                Evento temp = new Evento();
                                temp.setData(0.1);
                                
                                temp.setIpOrigem(new int[] {0,0,0,2});
                                
                                String[] ipDestino = argumentos[1].split(",");
                                temp.setIpDestino(new int[]{Integer.parseInt(ipDestino[0]),Integer.parseInt(ipDestino[1]),Integer.parseInt(ipDestino[2]),Integer.parseInt(ipDestino[3]) });
                                
                                temp.setDuracao(0.1);
                                temp.setTamanhoPacote(0.1);
                                
                                this.setAcao(temp);
                            }
                            else
                            {
                                if( "ping".equals(argumentos[0]) )
                                {
                                    Evento temp = new Evento();
                                    temp.setData(0.1);
                                    
                                    temp.setIpOrigem(new int[] {0,0,0,3});
                                    String[] ipDestino = argumentos[1].split(",");
                                    temp.setIpDestino(new int[]{Integer.parseInt(ipDestino[0]),Integer.parseInt(ipDestino[1]),Integer.parseInt(ipDestino[2]),Integer.parseInt(ipDestino[3]) });  
                                    temp.setDuracao(0.1);
                                    temp.setTamanhoPacote(0.1);
                                    this.setAcao(temp);
                                }              
                            }
                        }
                    }
                    break;
            case 3:
                    /**
                     * Se o tamanho é tres então troca de pacotes
                     * <IP origim> <IP destinho> <tamanhoPacote>
                     * Se argumentos maiores que 7 tem pelomenos digitos o suficiente (1.1.1.1)
                     */
                    if(argumentos[0].length() >= 7 && argumentos[1].length() >= 7 )
                    {
                        Evento temp = new Evento();
                        temp.setData(0.1);
                        temp.setDuracao(0.1);
                       
                        String[] ipOrigem = argumentos[0].split(",");
                        temp.setIpOrigem(new int[]{Integer.parseInt(ipOrigem[0]),Integer.parseInt(ipOrigem[1]),Integer.parseInt(ipOrigem[2]),Integer.parseInt(ipOrigem[3]) });  
                        
                        String[] ipDestino = argumentos[1].split(",");
                        temp.setIpDestino(new int[]{Integer.parseInt(ipDestino[0]),Integer.parseInt(ipDestino[1]),Integer.parseInt(ipDestino[2]),Integer.parseInt(ipDestino[3]) });  
                        double aDouble = Double.parseDouble(argumentos[2]+".0");
                        temp.setTamanhoPacote(aDouble);
                        this.setAcao(temp);
                    }
                    break;
        }
       
    }
    
    /**
     * Método que recebe o pacote e permite inseri-lo
     *  na posicao que quiser
     * @param int posicao
     * @param Evento acao 
     */
    public void setAcao(int posicao, Evento acao){
        acoes.add(posicao, acao);
        System.out.printf("Total de acoes: %d\n", acoes.size());
    }
    
    /**
     * Retorna o evento que está na posicao
     *  0 da lista, pois é linear a execução
     * @return Evento
     */
    public Evento getAcao() {
	return acoes.remove(0);
    }
    
    /**
     * Método que permite pegar um método de qualquer
     *  posicao da fila
     * @param posicao
     * @return Evento
     */
    public Evento getAcao(int posicao) {
        return acoes.remove(posicao);
    }
    
    /**
     * Verifica se a lista está vazia
     * @return 
     */
    public boolean isEmpty(){
        int size;
        
        size = this.acoes.size();
        if (size == 0){
            return true;
        }
        return false;
    }
}
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
    protected double data;
    protected double duracao;
    protected int[] ipOriginem;
    protected int[] ipDestino;
    static protected int totalDePacotes;
    protected int idPacote;
    protected double tamanhoPacote;
    protected static Random geradorAleatorio;
    
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

package Roteador;




import java.util.ArrayList;

public class ListaDeAcoes {
    protected ArrayList<Evento> acoes;
    
    public ListaDeAcoes() {
        acoes = new ArrayList<Evento>();
    }
    
    /**
     * Insere no final da lista a acao
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
                        //Comando ligar computador
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
                            //Comando de desligar o computador
                            if( "d".equals(argumentos[0]) )
                            {
                                Evento temp = new Evento();
                                temp.setData(0.1);
                                
                                temp.setIpOrigem(new int[] {0,0,0,2});
                                
                                String[] ipDestino = argumentos[1].split(",");
                                temp.setIpDestino(new int[]{Integer.parseInt(ipDestino[0]),Integer.parseInt(ipDestino[1]),Integer.parseInt(ipDestino[2]),Integer.parseInt(ipDestino[3]) });
                                
                                temp.setDuracao(0.1);
                                temp.setTamanhoPacote(0.1);
                                
                                this.setAcao(temp); //Prioridade máxima
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
                                    this.setAcao(temp); //Prioridade máxima
                                }              
                            }
                        }
                    }
                    break;
            case 3:
                    /**
                     * se o tamanho é tres então troca de pacotes
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
    
    public void setAcao(int posicao, Evento acao){
        acoes.add(posicao, acao);
        System.out.printf("Total de acoes: %d\n", acoes.size());
    }
    
    public Evento getAcao() {
	return acoes.remove(0);
    }
    
    public Evento getAcao(int posicao) {
        return acoes.remove(posicao);
    }
       
    public boolean isEmpty(){
        int size;
        
        size = this.acoes.size();
        if (size == 0){
            return true;
        }
        return false;
    }
}
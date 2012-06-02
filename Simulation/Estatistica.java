/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulation;

/**
 *
 * @author glauco
 */
public class Estatistica {
    protected static int totalStatistica = 0; 
    protected static int numeroDeMensagens=0;
    protected static int numeroDeErros=0;
    protected static int numeroDeSucesso=0;
    
    public Estatistica()
    {

    }
    
   public static void mensagens(String mensagem)
   {
       totalStatistica++;
       numeroDeMensagens++;
       
   }
   
   public static void erro(String mensagem)
   {
       
   }
   
   public static void sucesso(String mensagem)
   {
       
   }
   
   public static void finalizar()
   {
   }
}

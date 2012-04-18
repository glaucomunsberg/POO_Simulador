package trabalhoSimulador;
import java.util.ArrayList;

public class ListaDeAcoes 
{
	protected ArrayList<Acao> acoes;
	
	public ListaDeAcoes()
	{
		acoes = new ArrayList<Acao>();
	}
	
	/**
	 * Insere no final da lista a acao
	 * @param Acao acao
	 */
	public void setAcao(Acao acao)
	{
		acoes.add(acao);
	}
	
	/**
	 * Remove a acao mais antiga
	 * @return Acao
	 */
	public Acao getAcao()
	{
		return acoes.remove(0);
	}
	
	public int getNumDeAcoes()
	{
		return acoes.size();
	}
}

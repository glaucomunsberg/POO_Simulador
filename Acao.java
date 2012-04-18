
/**
 * Acao é a classe que recebe e manipula uma acao
 * 	que será feita pelo simulador. Nela contem então
 * 	a sua descricao, o tempo de execução e o objeto
 * 	do qual deve ser manipulado em forma de pacote
 *  @author glaucoroberto, marlon, valesca
 *
 */

public class Acao
{
	protected int time;				//Recebe o tempo em segundos
	protected String descricao;		//Descricao da acao que fará
	protected Object pacote;		//Recebe o objeto da acao chamado de pacote
	
	/**
	 * Construtor que inicia tudo com zero e nulo;
	 */
	public Acao()
	{
		time = 0;
		descricao = "";
		pacote = new Object();
	}

	public Acao(int time)
	{
		this.setTime(time);
		descricao = "";
		pacote = new Object();
	}
	
	public Acao(int time, Object pacote)
	{
		this(time);
		this.setPacote(pacote);
	}
	
	public Acao(int time, Object pacote, String descricao)
	{
		this(time, pacote);
		this.setDescricao(descricao);
	}
	
	public int getTime()
	{
		return this.time;
	}
	
	public void setTime(int time)
	{
		this.time = time;
	}
	
	public String getDescricao()
	{
		return this.descricao;
	}
	
	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}
	
	/**
	 * retorna o objeto da acao
	 * @return Object temp
	 */
	public Object getPacote()
	{
		Object temp = pacote.getClass();
		return temp;
	}
	
	/**
	 * insere o pacote que pertencerá a acao
	 * @param Object objeto
	 */
	public void setPacote(Object objeto)
	{
		this.pacote = objeto;
	}
}
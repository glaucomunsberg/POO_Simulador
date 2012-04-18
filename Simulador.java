package trabalhoSimulador;

public abstract class Simulador 
{
	ListaDeAcoes listaDeAcoes = new ListaDeAcoes();
	
	/**
	 * Método de execução de todas as ações
	 */
	public final void iniciarSimulacao()
	{
		int time = 0;
		Acao tempAcao;
		
		if( !carregarListaDeSimulacao() )
		{
			System.out.printf("Houve um erro ao carregar as ações. Vamos abortar");
			System.exit(0);
		}
		
		do
		{
			tempAcao = new Acao( listaDeAcoes.getAcao() );
			
			if( tempAcao != null)
			{
				time += tempAcao.getTime();
				this.executarAcao(tempAcao); //deve geral algo segundo o retorno
				//Sei lá como funciona depois... threads?!
			}
			
			time--;
		}while(time != 0 );
	}
	
	/**
	 * O método deve carregar a lista de ações,
	 * 	ou seja deve trazer de um arquivo todas as
	 * 	acoes e as organizar de forma a adicioná-las
	 * 	a lista "listaDeAcoes" para ser executada
	 * 	pelo simulador
	 * @return Boolean { true - Se carregou corretamente | false - Se houve erro }
	 */
	public abstract boolean carregarListaDeSimulacao();
	
	/**
	 * Método que recebe o objeto que deve ser manipulado segundo a sua
	 * 	especificação
	 * @param Acao acao
	 * @return Boolean {true - se executou com sucesso | false - se não conseguiu executar }
	 */
	public abstract boolean executarAcao(Acao acao);
	
}

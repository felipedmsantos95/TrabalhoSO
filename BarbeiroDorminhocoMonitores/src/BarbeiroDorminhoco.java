import java.util.Random;

public class BarbeiroDorminhoco implements Runnable{
	
	private int cadeiraDeEspera;
	boolean cadeiraOcupada = false;
	int[] clientes;
	boolean barbeiroDorme = false;
	private String nome;
	private int cliNovos;
	int nClientes = 0;
	
	BarbeiroDorminhoco(String nome, int cadeiraDeEspera, int clientes)
	{
		cliNovos = clientes;
		this.nome = nome;
		this.cadeiraDeEspera = cadeiraDeEspera;
		System.out.println("O Barbeiro " + nome + " chegou no salão.");
	}
	
	public void Clientes()
	{
		Random r = new Random();
		nClientes = r.nextInt(cliNovos);
		clientes = new int[nClientes];
		
		for(int i = 0; nClientes < clientes.length; i++)
		{
			clientes[i] = i;
		}
		
	}
	
	//Não há clientes o barbeiro dorme
	public void BarbeiroDorme() throws InterruptedException
	{
		System.out.println("Não existe(m) clientes(s) no salão do Barbeiro" + nome + ".");
		System.out.println("O barbeiro " + nome + " está esperando a chegada de clientes.");
		Thread.sleep(2000);//Como não há clientes a thread espera por 2 segundos.
		System.out.println("A cadeira do Barbeiro " + nome + " está livre");
		
		Clientes();
	}
	
	public void BarbeiroAtende() throws InterruptedException
	{
		if(nClientes != 0)
		{
			if(nClientes > 1 && cadeiraOcupada == false)
			{
				System.out.println("Entrou(aram) " + nClientes + " cliente(s) no salão.");
			} else{
				System.out.println("Existe(m) " + nClientes + " cliente(s) esperando atendimento." + nome);
			}
			//Se há clientes 1 já pode ser atendido
			System.out.println("Um cliente ocupou a cadeira do Barbeiro " + nome);
			nClientes--;//cliente foi atendido, decrementa o número de clientes
			System.out.println("Um cliente está sendo atendido pelo barbeiro " + nome);
			cadeiraOcupada = true; // a cadeira do barbeiro está ocupada
			
			Thread.sleep(1000); // Esperando atendimento terminar
			
			//se o número de clientes é maior que o número de cadeiras de espera
			
			if(nClientes > cadeiraDeEspera)
			{
				
				//Verifica quantos clientes irão embora
				int cli = nClientes - cadeiraDeEspera;
				
				//Verifica quantos clientes esperam				
				nClientes = nClientes - cli;
				
				//Enquanto o contador for menor que o numero de clientes, o vetor é zerado nas posições
				for(int i = 0; i < clientes.length; i++)
				{
					clientes[i] = 0;
				}
				
				//Atualiza o total de clientes
				for (int j = 0; j < clientes.length; j++)
				{
					clientes[j] = j + 1;
				}
				
				System.out.println(cli + " clientes foram embora.");
				
				System.out.println(nClientes + " estão esperando.");
			}
			//Mostra qual barbeiro já atendeu
			System.out.println("Um cliente já foi atendido pelo Barbeiro " + nome);
		}
		else if(nClientes == 1)
		{
			//Mostra qual barbeiro está livre
			System.out.println("A cadeira do barbeiro " + nome + " está livre.");
			
			//Avisa qual barbeiro vai atender
			System.out.println("A cadeira do Barbeiro " + nome + " está ocupada, não existem clientes esperando");
			Thread.sleep(1000);
			nClientes--;
			
			//Mostra qual barbeiro já atendeu
			System.out.println("Um cliente já foi atendido pelo barbeiro " + nome);
		}
		else
		{
			System.out.println("A cadeira do barbeiro " + nome + " está livre");
			//libera as cadeiras da espera
			cadeiraOcupada = false;
			
		}
	}
	
	@Override
	
	public void run()//execução de thread
	{
		while(true)
		{
			if(nClientes <= 0)
			{
				try
				{
					BarbeiroDorme();//O barbeiro espera
				}
				catch(InterruptedException ex)
				{
					System.out.println(ex);
				}
			}
			else
			{
				try
				{
					BarbeiroAtende();
				}
				catch(InterruptedException ex)
				{
					System.out.println(ex);
				}
			}
		}
	}
}

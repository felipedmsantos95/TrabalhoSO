
public class BarbeiroMain {

	public static void main(String[] args) {
		BarbeiroDorminhoco barb1 = new BarbeiroDorminhoco("João", 3, 10); //Nome, cadeiras de espera e numero de clientes

		Thread threadbarb = new Thread(barb1);
		
		threadbarb.start();
	}

}

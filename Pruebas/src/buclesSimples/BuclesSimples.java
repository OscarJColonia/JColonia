package buclesSimples;

public class BuclesSimples {
	
	private String números;
	private int contador;
	
	public BuclesSimples() {
		números = "";
		contador = 23;
	}
	
	public void calcular() {
//		for(int i=23;i<=86;i++) {
//			números=números+i+"\n";
//		}
		
//		while(contador<=86) {
//			números=números+contador+"\n";
//			contador++;
//		}
		
//		do {
//			números=números+contador+"\n";
//			contador++;
//		}while(contador<=86);
		
		
	}

	public String getNúmeros () {
		return this.números;
	}
	
	public static void main(String[] args) {
		BuclesSimples hola = new BuclesSimples();
		hola.calcular();
		System.out.println(hola.getNúmeros());
	}

}

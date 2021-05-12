package arrays;

import java.lang.reflect.Array;

public class ArraysSimples {
	
	static String[] cantidades = {
            "«-040»", "«-039»", "«-038»", "«-037»", "«-036»", "«-035»", "«-034»", "«-033»", "«-032»", "«-031»",
            "«-030»", "«-029»", "«-028»", "«-027»", "«-026»", "«-025»", "«-024»", "«-023»", "«-022»", "«-021»",
            "«-020»", "«-019»", "«-018»", "«-017»", "«-016»", "«-015»", "«-014»", "«-013»", "«-012»", "«-011»",
            "«-010»", "«-009»", "«-008»", "«-007»", "«-006»", "«-005»", "«-004»", "«-003»", "«-002»", "«-001»",
            "«+000»", "«+001»", "«+002»", "«+003»", "«+004»", "«+005»", "«+006»", "«+007»", "«+008»", "«+009»",
            "«+010»", "«+011»", "«+012»", "«+013»", "«+014»", "«+015»", "«+016»", "«+017»", "«+018»", "«+019»",
            "«+020»", "«+021»", "«+022»", "«+023»", "«+024»", "«+025»", "«+026»", "«+027»", "«+028»", "«+029»",
            "«+030»", "«+031»", "«+032»", "«+033»", "«+034»", "«+035»", "«+036»", "«+037»", "«+038»", "«+039»",
            "«+040»" };
	
	static String hola[] = new String [4];
	static int longitud=Array.getLength(hola);
	static String resultado="";
	private String nombre = "Oscar Fernández";
	
	//Escribir los valores entre -10 y 10, en columna, empleando un bucle de tipo «for».
	
	public void calcular() {
		for(int i = 0;i<longitud;i++) {
			resultado = resultado + hola [i] + "\n";
		}
	}
	
	public String getNombre() {
		return this.nombre;
	}

	public static void main(String[] args) {
		ArraysSimples uno = new ArraysSimples();
		uno.calcular();
		System.out.println(resultado);
		uno.getNombre();
	}

}

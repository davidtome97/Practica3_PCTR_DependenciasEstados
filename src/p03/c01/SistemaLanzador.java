package src.p03.c01;



public class SistemaLanzador {
	public static void main(String[] args) {
		
		IParque parque = new Parque(); // TODoo
		char letra_puerta = 'A';
		int a = 0;
		System.out.println("¡Parque abierto!");
		
		for (int i = 0; i < Integer.parseInt(args[0]); i++) {
			
			String puerta = ""+((char) (letra_puerta++));
			// Creación de hilos de entrada
			ActividadEntradaPuerta entradas = new ActividadEntradaPuerta(puerta, parque);
			ActividadSalidaPuerta salida=new ActividadSalidaPuerta(puerta, parque);
			new Thread (entradas).start();
			new Thread (salida).start();
			
			// 
			// TODO
			//
			
			
		}
	}	
}
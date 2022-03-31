package src.p03.c01;

import java.util.ConcurrentModificationException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Parque implements IParque {


	// variables
	private int aforoMax;
	private int aforoMin;
	private int contadorPersonasTotales;
	private Hashtable<String, Integer> contadoresPersonasPuerta;
	private static Random generadorAleatorios =new Random();
	

	
	
	public Parque() {	// TODO
		contadorPersonasTotales = 0;
		contadoresPersonasPuerta = new Hashtable<String, Integer>();
		aforoMax=20;
		aforoMin=0;
	}


	@Override
	public synchronized void entrarAlParque(String puerta) {		// TODO
		
		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuerta.get(puerta) == null){
			contadoresPersonasPuerta.put(puerta, 0);
		}
		
		
		// TODO
		comprobarAntesDeEntrar();
		
		// Aumentamos el contador total y el individual
		contadorPersonasTotales=contadorPersonasTotales+1;	
		
		contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta)+1);
		
		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Entrada");
		
		// TODO
		
		checkInvariante();
		notifyAll();
		// TODO
		
	}
	

	
	// 
	// TODO Metodo salirDelParque
	//
	@Override
	public synchronized void salirDelParque(String puerta) {
		// TODO Auto-generated method stub
		if (contadoresPersonasPuerta.get(puerta) == null){
			contadoresPersonasPuerta.put(puerta, 0);
		}
		
		comprobarAntesDeSalir();
		
		contadorPersonasTotales=contadorPersonasTotales-1;	
		
		contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta)-1);
		
		imprimirInfo(puerta, "Salida");
		
		checkInvariante();
		notifyAll();
	
		
	}
	
	
	
	private void imprimirInfo (String puerta, String movimiento){
		System.out.println(movimiento + " por puerta " + puerta);
		System.out.println("--> Personas en el parque " + contadorPersonasTotales); //+ " tiempo medio de estancia: "  + tmedio);
		
		// Iteramos por todas las puertas e imprimimos sus entradas.
		for(String p: contadoresPersonasPuerta.keySet()){
			System.out.println("----> Por puerta " + p + " " + contadoresPersonasPuerta.get(p));
		}
		System.out.println(" ");
	}
	
	private int sumarContadoresPuerta() {
		int sumaContadoresPuerta = 0;
			Enumeration<Integer> iterPuertas = contadoresPersonasPuerta.elements();
			while (iterPuertas.hasMoreElements()) {
				sumaContadoresPuerta += iterPuertas.nextElement();
			}
		return sumaContadoresPuerta;
	}
	
	protected void checkInvariante() {
		assert sumarContadoresPuerta() == contadorPersonasTotales : "INV: La suma de contadores de las puertas debe ser igual al valor del contador del parte";
		assert contadorPersonasTotales >= aforoMin : "INV: la suma de personas tiene que ser mayor o igual al aforo minimo";
		assert contadorPersonasTotales <= aforoMax : "INV: la suma de personas tiene que ser menor o igual al aforo maximo";
		
	}

	
	protected  void comprobarAntesDeEntrar(){
		while(contadorPersonasTotales== aforoMax ) {
			
			try {
				wait();
				
			}catch(InterruptedException e) {
				Thread.currentThread().interrupt();
				Logger.getGlobal().log(Level.INFO,"interrupcion");
			}
			
		}
		
			
		
	}

	protected  void comprobarAntesDeSalir(){		
		while(contadorPersonasTotales == 0 )  {
		
			try {
				wait();
				
			}catch(InterruptedException e) {
				Thread.currentThread().interrupt();
				Logger.getGlobal().log(Level.INFO,"interrupcion");
			}
			
		}
		
	}





}

package ficheroRandom2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

	

public class FicheroRandom2 {
	static final int tamanho = 4 + (20 + 2) + 8 + 8 + 8 + 4 + 2;
	static final Articulo articuloVacio= new Articulo(0,"",0,0,0,0,' ');
	static void altas(Scanner in) throws IOException {
		RandomAccessFile raf= new RandomAccessFile("articulos.dat", "rw");
		int codigo=0; 
		Articulo articulo = new Articulo();
		do {
			System.out.println("Código del artículo: ");
		}while(!articulo.setCodigo(in.nextLine()));
		do {
			System.out.println("Denominación: ");
		}while(!articulo.setDenominacion(in.nextLine()));
		do {
			System.out.println("Stock mínimo: ");
		}while(!articulo.setsMinimo(in.nextLine()));
		do {
			System.out.println("Stock máximo: ");
		}while(!articulo.setsMaximo(in.nextLine()));
		do {
			System.out.println("Stock actual: ");
		}while(articulo.setsActual(in.nextLine()));
		do {
			System.out.println("Precio: ");
		}while(!articulo.setPrecio(in.nextLine()));
		long posicionArchivo = (long) codigo * tamanho;
		if(posicionArchivo<raf.length()) {
			raf.seek(posicionArchivo);
			if(raf.readInt()==0) {
				raf.seek(posicionArchivo);
				articulo.escribirFichero(raf);
			}
			else
				System.out.println("Error, posición ocupada");
			
		}
		else {
			while(posicionArchivo>raf.length()) {
				articuloVacio.escribirFichero(raf);
			}
			raf.seek(posicionArchivo);
			articulo.escribirFichero(raf);
		}
		raf.close();
	}
	static void bajas(Scanner in) throws IOException {
		RandomAccessFile raf = new RandomAccessFile("articulos.dat","rw");
		int posicion=0;
		System.out.println("Código artículo a borrar: ");
		try {
			posicion=Integer.parseInt(in.nextLine());
		}catch(NumberFormatException e) {}
		int posicionArchivo=posicion*tamanho;
		if(posicionArchivo>raf.length()) {
			System.out.println("No hay artículo con ese código");
		}
		else {
			raf.seek(posicionArchivo);
			if(raf.readInt()==0) {
				System.out.println("No hay artículo con es código");
			}
			else
//				Articulo articulo = new Articulo(raf.readInt(),raf.readUTF(),raf.readDouble(),raf.readDouble(),raf.readDouble(),raf.readFloat(),raf.readChar());
				System.out.println("Desea borrar el artículo: ");
				System.out.println();
		}
	}
	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(System.in);
		RandomAccessFile raf = new RandomAccessFile("articulos.dat", "rw");
		raf.close();
		int w=0;
		while (true) {
			System.out.println("1.-Altas");
	        System.out.println("2.-Bajas");
	        System.out.println("3.-Consultas");
	        System.out.println("4.-Visualizar");
	        System.out.println("5.-Modificaciones");
	        System.out.println("6.-Fin");
	        try {
	        	w=Integer.parseInt(in.nextLine());
	        }catch(NumberFormatException e) {
	        	System.out.println("Valor numérico");
	        }
	        switch (w) {
	        case 1:
	        	altas(in);
	        	break;
	        case 2:
	        	bajas(in);
	        	break;
	        case 3:
//	        	consultas(in);
	        	break;
	        case 4:
//	        	visualizar();
	        	break;
	        case 5:
//	        	modificar(in);
	        	break;
	        case 6:
	        	System.out.println("Fin de Programa.");
	        	break;
	        }
	        if (w == 6)
	        	break;
	        }
	    }
}

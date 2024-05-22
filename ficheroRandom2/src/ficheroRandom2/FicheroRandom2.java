package ficheroRandom2;

import java.io.EOFException;
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
			}while(!articulo.setsActual(in.nextLine()));
			do {
				System.out.println("Precio: ");
			}while(!articulo.setPrecio(in.nextLine()));
			System.out.println("Confirmar? (s/n)");
			if(in.nextLine()=="s") {
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
			}
			else {
				System.out.println("Cancelado");
			}
			System.out.println("Agregar otro artículo? (s/n)");
		}while(in.nextLine()=="s");
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
			else {
				Articulo articulo = new Articulo(raf.readInt(),raf.readUTF(),raf.readDouble(),raf.readDouble(),raf.readDouble(),raf.readFloat(),raf.readChar());
				System.out.println("Desea borrar el artículo: "+articulo+"(s para confirmar)");
				if(in.nextLine()=="s") {
					raf.seek(posicionArchivo);
					articuloVacio.escribirFichero(raf);
					System.out.println("Borrado completado");
				}
				else
					System.out.println("Borrado cancelado");
			}
		}
		raf.close();
	
		
	}
	static void listLimites(Scanner in) throws IOException {
		int li=0;
		int ls=0;
		int clineas=0;
		RandomAccessFile raf = new RandomAccessFile("articulos.dat","r");
		do {
			System.out.println("Limite superior: ");
			try {
				ls=Integer.parseInt(in.nextLine());
				
			}catch(NumberFormatException e) {
				System.out.println("Valor no numérico");
			}
			
				System.out.println("Límite inferior: ");
			try {
				li=Integer.parseInt(in.nextLine());
				
			}catch(NumberFormatException e) {
				System.out.println("Valor no numérico");
			}
			if(li*tamanho>raf.length())
				System.out.println(" El límite inferior está por encima del último artículo");
		}while(ls<li || (li*tamanho)>raf.length());
		int pagina=0;
		while (li < ls) {
            System.out.println("Codigo\tDenominación\tStock mínimo\tStock máximo\tStock actual\tPrecio\tAviso stock");
            System.out.println("-".repeat(90));
            clineas = 0;
            while (clineas < 4 && li < ls) {
                try {
                    raf.seek(li * tamanho);
                    if (raf.getFilePointer() * tamanho > raf.length()) {
                        break; // Evitar leer más allá del final del archivo
                    }
                    int codigo = raf.readInt();
                    if (codigo != 0) {
                        String denominacion = raf.readUTF();
                        double stockMin = raf.readDouble();
                        double stockMax = raf.readDouble();
                        double stockActual = raf.readDouble();
                        float precio = raf.readFloat();
                        char avisoStock = raf.readChar();

                        // Crear un objeto Articulo y mostrar sus detalles
                        Articulo articulo = new Articulo(codigo, denominacion, stockMin, stockMax, stockActual, precio, avisoStock);
                        System.out.println(articulo);
                        clineas++;
                    }
                } catch (EOFException e) {
                    break; // Salir del bucle si llegamos al final del archivo
                }
            }
            pagina++;
            System.out.println("\tPágina " + pagina);
            System.out.println("Presiona Enter para continuar...");
            in.nextLine();
        }
        raf.close();
    }

	
	static void listGeneral() throws IOException {
		RandomAccessFile raf = new RandomAccessFile("articulos.dat","r");
		while(raf.length()<raf.getFilePointer()) {
			
		}
	}
	static void listPedidos() {
		
	}
	static void menuVisualizar(Scanner in) throws IOException, EOFException {
		int opcion=0;
		do {
			System.out.println("1.Listado general");
			System.out.println("2.Listado entre límites");
			System.out.println("3.Listado de pedidos.");
			System.out.println("4.Volver al menú principal");
			try {
				opcion=Integer.parseInt(in.nextLine());
			}catch(NumberFormatException e) {}
		}while(opcion<1||opcion>4);
		switch(opcion) {
			case 1:
				listGeneral();
				break;
			case 2:
				listLimites(in);
				break;
			case 3:
				listPedidos();
				break;
			case 4:
				System.out.println("Volver al menú principal");
				break;
		}
		
	}
	public static void main(String[] args) throws IOException, EOFException {
		Scanner in = new Scanner(System.in);
		
		RandomAccessFile raf = new RandomAccessFile("articulos.dat", "rw");//Se abre este primera vez para asegurarse de que existe y no salga la FileNotFound Exception
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
		        	menuVisualizar(in);
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

//
package ficheroRandom2;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Articulo {
	private int codigo;
	private String denominacion;
	private double sActual;
	private double sMinimo=0;
	private double sMaximo=Double.MAX_VALUE;
	private float precio;
	private char aviso;
	final int LONGNOMBRE = 20;

	public Articulo(){};
	public Articulo(int codigo, String denominacion, double sActual, double sMinimo, double sMaximo, float precio, char aviso) {
		
		this.codigo=codigo;
		this.denominacion=denominacion;
		this.sActual=sActual;
		this.sMinimo=sMinimo;
		this.sMaximo=sMaximo;
		this.precio=precio;
		this.aviso=aviso;
		
	}
	public int getCodigo() {
		return codigo;
	}
	public boolean setCodigo(String codigoString) {
		int codigo=0;
		try {
			codigo=Integer.parseInt(codigoString);
		}catch(NumberFormatException e) {
			System.out.println("Valor no numérico");
			return false;
		}
		this.codigo = codigo;
		return true;
	}
	public String getDenominacion() {
		return denominacion;
	}
	public boolean setDenominacion(String denominacion) {
		if(denominacion.length()>20) {
			System.out.println("La denominación no puede tener más de 20 caracteres");
			return false;
		}
		else {
			denominacion=denominacion+" ".repeat(20-denominacion.length());
			this.denominacion = denominacion;
			return true;
		}
	}
	public double getsActual() {
		return sActual;
	}
	public boolean setsActual(String sActualAux) {
		double sActual=0;
		try {
			sActual=Double.parseDouble(sActualAux);
		}catch(NumberFormatException e) {
			System.out.println("Valor no numérico");
			return false;
		}
		this.sActual = sActual;
		setAviso();
		return true;
	}
	public double getsMinimo() {
		return sMinimo;
	}
	public boolean setsMinimo(String stockAux) {
		double stock=0;
		try {
			stock=Double.parseDouble(stockAux);
		}catch(NumberFormatException e) {
			System.out.println("Valor no numérico");
			return false;
		}
		if(stock>sMaximo) {
			System.out.println("El stock mínimo no puede ser mayor que el stock máximo");
			return false;
		}
		this.sMinimo = stock;
		setAviso();
		return true;
	}
	public double getsMaximo() {
		return sMaximo;
	}
	public boolean setsMaximo(String stockAux) {
		double stock=0;
		try {
			stock=Double.parseDouble(stockAux);
		}catch(NumberFormatException e) {
			System.out.println("Valor no numérico");
			return false;
		}
		if(stock<sMinimo) {
			System.out.println("El stock máximo no puede ser mayor que el stock mínimo");
			return false;
		}
		this.sMinimo = stock;
		setAviso();
		return true;
	}
	public float getPrecio() {
		return precio;
	}
	public boolean setPrecio(String precioAux) {
		float precio=0;
		try {
			precio=Float.parseFloat(precioAux);
		}catch(NumberFormatException e) {
			System.out.println("Valor no numérico");
			return false;
		}
		this.precio = precio;
		return true;
	}
	public char getAviso() {
		return aviso;
	}
	private void setAviso() {
		if(sActual>sMaximo)
			this.aviso = 'A';
		else if(sActual<sMinimo)
			this.aviso= 'B';
		else
			this.aviso= 'N';
	}
	public String toString() {
		return "Código: "+codigo+"\tDenomición: "+denominacion+"\tStock mínimo: "+sMinimo+"\tStock máximo: "+sMaximo+"\tStock actual: "+sActual+"\tPrecio: "+precio+"\tAviso: "+aviso;
	}
	public void escribirFichero(RandomAccessFile raf) throws IOException {
		raf.writeInt(codigo);
		raf.writeUTF(denominacion);
		raf.writeDouble(sMinimo);
		raf.writeDouble(sMaximo);
		raf.writeDouble(sActual);
		raf.writeFloat(precio);
		raf.writeChar(aviso);
	}
}


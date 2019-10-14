package Compilador;

public class Identificador {

	String nombre;
	String valor;
	String tipo;
	String alcance;
	int linea;
	int desde;
	int hasta;
	boolean correcta=true;
		
	public Identificador(String nombre, String valor, String tipo,int linea,String alcance) {
		this.nombre = nombre;
		this.valor = valor;
		this.tipo = tipo;
		this.linea=linea;
		this.alcance=alcance;
		this.desde=0;
		this.hasta=0;
	}
	public Identificador(String nombre, String valor, String tipo,int linea,String alcance, int desde, int hasta) {
		this.nombre = nombre;
		this.valor = valor;
		this.tipo = tipo;
		this.linea=linea;
		this.alcance=alcance;
		this.desde=desde;
		this.hasta=hasta;
	}
	public Identificador(String valor, String tipo) {
		this.valor = valor;
		this.tipo = tipo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public int getLinea() {
		return linea;
	}
	public void setLinea(int linea) {
		this.linea = linea;
	}
	public String getAlcance() {
		return alcance;
	}
	public void setAlcance(String alcance) {
		this.alcance = alcance;
	}
	public int getDesde() {
		return desde;
	}
	public void setDesde(int desde) {
		this.desde = desde;
	}
	public int getHasta() {
		return hasta;
	}
	public void setHasta(int Hasta) {
		this.hasta = hasta;
	}
	public boolean getCorrecta() {
		return correcta;
	}
	public void setCorrecta(boolean correcta) {
		this.correcta = correcta;
	}
	public String toString() {
		return "Identificador [nombre=" + nombre + ", valor=" + valor + ", tipo=" + tipo + "]";
	}
}
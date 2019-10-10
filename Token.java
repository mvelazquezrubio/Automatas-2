package Compilador;

public class Token 
{
	private String tipo;
	private String valor;
	private int NumTipo;
	private int linea;
	public Token(int Num,String t, String v) {
		tipo=t;
		valor=v;
		NumTipo=Num;
	}
	public Token(String t, String v, int lin) {
		tipo=t;
		valor=v;
		linea=lin;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getValor() {
		return valor;
	}
	public int getNumTipo() {
		return NumTipo;
	}
	public void setNumTipo(int numTipo) {
		NumTipo = numTipo;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public void setLinea(int linea) {
		this.linea = linea;
	}
	public int getLinea() {
		return linea;
	}
	public String toString() {
		return getTipo()+" --> "+getValor();
	}
}

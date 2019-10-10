package Compilador;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
public class GeneraTabla {
	static ArrayList<Identificador> TablaDeSimbolos = new ArrayList<Identificador>();
	String aux="";
	boolean ban=false;
	public GeneraTabla(){
		for(int i=0;i<Lexico.tokenAnalizados.size();i++){
			if((Lexico.tokenAnalizados.get(i).getTipo()).equals("Tipo de datos"))
			{
				if(Lexico.tokenAnalizados.get(i+2).getValor().equals("="))
				{
					String valor="";
					int aux=i; //guardamos la posicion de donde estaba el tipo y el identificador
					i+=3;
					while(!Lexico.tokenAnalizados.get(i).getValor().equals(";")) {
						valor+=Lexico.tokenAnalizados.get(i).getValor();
						i++;
					}
					TablaDeSimbolos.add(new Identificador(Lexico.tokenAnalizados.get(aux+1).getValor(),valor,Lexico.tokenAnalizados.get(aux).getValor(),Lexico.tokenAnalizados.get(aux+1).getLinea()));
				}
				else if(Lexico.tokenAnalizados.get(i+2).getValor().equals(";"))
				TablaDeSimbolos.add(new Identificador(Lexico.tokenAnalizados.get(i+1).getValor(),"",Lexico.tokenAnalizados.get(i).getValor(),Lexico.tokenAnalizados.get(i+1).getLinea()));
				//se guarda sin valor
			}
			int aux2;
			String valor;
			if(Lexico.tokenAnalizados.get(i).getValor().equals("=")){
				valor="";
				aux2=i; //guardamos la posicion de donde estaba el tipo y el identificador
				i++;
				while(!Lexico.tokenAnalizados.get(i).getValor().equals(";")) {
					valor+=Lexico.tokenAnalizados.get(i).getValor();
					i++;
				}
				//Se busca si la variable se guardo antes sin valor
				boolean encontrado=false;
				for(int j=0;j<TablaDeSimbolos.size();j++){
					Identificador ide = TablaDeSimbolos.get(j);
					if(ide.nombre.equals(Lexico.tokenAnalizados.get(aux2-1).getValor())){
						TablaDeSimbolos.get(j).valor=valor;
						encontrado=true;
					}
				}
				if(!encontrado)//Si no se encontro, lo guardamos pero con tipo de no declarada
					TablaDeSimbolos.add(new Identificador(Lexico.tokenAnalizados.get(aux2-1).getValor(),valor,"",Lexico.tokenAnalizados.get(aux2-1).getLinea()));
			}
		}
		for(int i=0;i<TablaDeSimbolos.size();i++){
			Identificador ide = TablaDeSimbolos.get(i);
			System.out.println("Nombre: "+ide.getNombre());
			System.out.println("Tipo: "+ide.getTipo());
			System.out.println("Valor: "+ide.getValor());
			System.out.println("Linea: "+ide.getLinea());
		}
	}
}

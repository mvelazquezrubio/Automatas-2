package Compilador;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
public class GeneraTabla {
	static ArrayList<Identificador> TablaDeSimbolos = new ArrayList<Identificador>();
	String aux="";
	boolean ban=false;
	int llaveabre, llavecierra, desde, hasta;
	public GeneraTabla(){
		llaveabre=0;
		llavecierra=0;
		desde=0;
		hasta=0;
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
						if(!Lexico.tokenAnalizados.get(i+1).getValor().equals(";"))
						valor+=" ";
						i++;
					}
					if(buscaAlcance(Lexico.tokenAnalizados.get(aux+1))=="Global")
					TablaDeSimbolos.add(new Identificador(Lexico.tokenAnalizados.get(aux+1).getValor(),valor,Lexico.tokenAnalizados.get(aux).getValor(),Lexico.tokenAnalizados.get(aux+1).getLinea(),"Global"));
					else
					TablaDeSimbolos.add(new Identificador(Lexico.tokenAnalizados.get(aux+1).getValor(),valor,Lexico.tokenAnalizados.get(aux).getValor(),Lexico.tokenAnalizados.get(aux+1).getLinea(),"Local",desde,hasta));
					
				}
				else if(Lexico.tokenAnalizados.get(i+2).getValor().equals(";")){
					if(buscaAlcance(Lexico.tokenAnalizados.get(i+1))=="Global")
						TablaDeSimbolos.add(new Identificador(Lexico.tokenAnalizados.get(i+1).getValor(),"",Lexico.tokenAnalizados.get(i).getValor(),Lexico.tokenAnalizados.get(i+1).getLinea(),"Global"));
					else
						TablaDeSimbolos.add(new Identificador(Lexico.tokenAnalizados.get(i+1).getValor(),"",Lexico.tokenAnalizados.get(i).getValor(),Lexico.tokenAnalizados.get(i+1).getLinea(),"Local",desde,hasta));	
				//se guarda sin valor
				}
			}
			int aux2;
			String valor;
			if(Lexico.tokenAnalizados.get(i).getValor().equals("=")){
				valor="";
				aux2=i; //guardamos la posicion de donde estaba el tipo y el identificador
				i++;
				while(!Lexico.tokenAnalizados.get(i).getValor().equals(";")) {
					valor+=Lexico.tokenAnalizados.get(i).getValor();
					if(!Lexico.tokenAnalizados.get(i+1).getValor().equals(";"))
					valor+=" ";
					i++;
				}
				//Se busca si la variable se declaro antes sin valor
				boolean encontrado=false;
				for(int j=0;j<TablaDeSimbolos.size();j++){
					Identificador ide = TablaDeSimbolos.get(j);
					if(ide.nombre.equals(Lexico.tokenAnalizados.get(aux2-1).getValor())){
						if(TablaDeSimbolos.get(j).getAlcance().equals("Local") && (Lexico.tokenAnalizados.get(aux2-1).getLinea()<TablaDeSimbolos.get(j).getDesde()
								|| Lexico.tokenAnalizados.get(aux2-1).getLinea()>TablaDeSimbolos.get(j).getHasta())){
							//Se esta queriendo utilizar fuera del rango
							//asi que se guardara como no declarada
						}else{
							TablaDeSimbolos.get(j).setValor(valor);;
							encontrado=true;
						}
					}
				}
				if(!encontrado)//Si no se encontro, lo guardamos pero sin tipo por no declarada
					TablaDeSimbolos.add(new Identificador(Lexico.tokenAnalizados.get(aux2-1).getValor(),valor,"",Lexico.tokenAnalizados.get(aux2-1).getLinea(),"No declarada"));
			}
		}
		llenarTabla();
	}
	public String buscaAlcance(Token token){
		llaveabre=0;
		llavecierra=0;
		String alcance="";
		for(int i=0;i<Lexico.tokenAnalizados.size();i++){
			//Primero contamos las llaves
			if(Lexico.tokenAnalizados.get(i).getValor().equals("{")){
				llaveabre++;
				desde=Lexico.tokenAnalizados.get(i).getLinea();
			}
			if(Lexico.tokenAnalizados.get(i).getValor().equals("}"))
				llavecierra++;
			
			if(Lexico.tokenAnalizados.get(i).equals(token)){//Si ya encontramos la variable
				//Buscamos hasta donde sera local
				int x=i;
				while(true){
					if(Lexico.tokenAnalizados.get(x).getValor().equals("}")){
					hasta=Lexico.tokenAnalizados.get(x).getLinea();
					break;
					}
					x++;
				}
				break;
			}
		}
		if((llaveabre-1) == llavecierra)
			alcance="Global";
		else
			alcance="Local";
		return alcance;
	}
	public void llenarTabla(){
		/*for(int i=0;i<TablaDeSimbolos.size();i++){
			Vista.modelo.insertRow(Vista.modelo.getRowCount(),new Object[]{
					TablaDeSimbolos.get(i).getNombre(),
					TablaDeSimbolos.get(i).getTipo(),
					TablaDeSimbolos.get(i).getValor(),
					TablaDeSimbolos.get(i).getLinea()});
		}*/
		
		System.out.println("DECLARADAS:");
		for(int i=0;i<TablaDeSimbolos.size();i++){
			System.out.println("Nombre:"+TablaDeSimbolos.get(i).getNombre());
			System.out.println("Tipo:"+TablaDeSimbolos.get(i).getTipo());
			System.out.println("Valor:"+TablaDeSimbolos.get(i).getValor());
			System.out.println("Posicion:"+TablaDeSimbolos.get(i).getLinea());
			System.out.println("Alcance:"+TablaDeSimbolos.get(i).getAlcance());
			System.out.println("Desde:"+TablaDeSimbolos.get(i).getDesde());
			System.out.println("Hasta:"+TablaDeSimbolos.get(i).getHasta());
		}
	}
	
}

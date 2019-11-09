package Compilador;
import java.util.*;
public class Intermedio {
	ArrayList<String> expresion = new ArrayList<String>();
	ArrayList<String> expresionEditada = new ArrayList<String>();
	ArrayList<String> aux = new ArrayList<String>();
	ArrayList<String> Tabla = new ArrayList<String>();
	public Intermedio(){
		//for(int i=0;i<GeneraTabla.TablaDeSimbolos.size();i++){
			//GuardaExpresion(GeneraTabla.TablaDeSimbolos.get(i).getValor());
			GuardaExpresion(GeneraTabla.TablaDeSimbolos.get(2).getValor());
			Cuadruplo();
			Tabla.add("="+" "+aux.get(0)+" "+GeneraTabla.TablaDeSimbolos.get(2).getNombre());
			
			System.out.print("Editada:");
			for(int i=0;i<expresionEditada.size();i++){
				System.out.print(expresionEditada.get(i));
			}
			System.out.println("\nTabla:");
			for(int i=0;i<Tabla.size();i++){
				System.out.println(Tabla.get(i));
			}
			/*System.out.println("\nAux:");
			for(int i=0;i<aux.size();i++){
				System.out.print(aux.get(i));
			}*/
		//}
	}
	
	public void GuardaExpresion(String exp){
		expresion.clear();
		Tabla.clear();
		StringTokenizer token = new StringTokenizer(exp);
		int x=token.countTokens();
		for(int i=0;i<x;i++){
			expresion.add(token.nextToken());
		}
	}
	
	public void Cuadruplo(){
		int cont=0;
		while(expresion.contains("/") || expresion.contains("*")){
		for(int i=0;i<expresion.size();i++){
			if(expresion.get(i).equals("/") || expresion.get(i).equals("*")){
				cont++;
				Tabla.add(expresion.get(i)+" "+expresion.get(i-1)+" "+expresion.get(i+1)+" "+"T"+cont);
				expresionEditada.remove(expresionEditada.size()-1);
				expresionEditada.add("T"+cont);
				continua(i+2);
				break;
			}
			else{
				expresionEditada.add(expresion.get(i));
			}
		}
		expresion.clear();
		expresion=(ArrayList)expresionEditada.clone();
		aux=(ArrayList)expresionEditada.clone();
		expresionEditada.clear();
		}
		expresion.clear();
		expresion=(ArrayList)aux.clone();
		while(expresion.contains("+") || expresion.contains("-")){
			for(int i=0;i<expresion.size();i++){
				if(expresion.get(i).equals("+") || expresion.get(i).equals("-")){
					cont++;
					Tabla.add(expresion.get(i)+" "+expresion.get(i-1)+" "+expresion.get(i+1)+" "+"T"+cont);
					expresionEditada.remove(expresionEditada.size()-1);
					expresionEditada.add("T"+cont);
					continua(i+2);
					break;
				}
				else{
					expresionEditada.add(expresion.get(i));
				}
			}
			expresion.clear();
			expresion=(ArrayList)expresionEditada.clone();
			aux=(ArrayList)expresionEditada.clone();
			expresionEditada.clear();
			}
	}
	public void continua(int pos){
		for(int i=pos;i<expresion.size();i++){
			expresionEditada.add(expresion.get(i));
		}
	}
}

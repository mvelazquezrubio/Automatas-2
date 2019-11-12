package Compilador;
import java.util.*;
public class Intermedio {
	ArrayList<String> expresion = new ArrayList<String>();
	ArrayList<String> expresionEditada = new ArrayList<String>();
	ArrayList<String> aux = new ArrayList<String>();
	ArrayList<String> Tabla = new ArrayList<String>();
	public Intermedio(){
		for(int j=0;j<GeneraTabla.TablaDeSimbolos.size();j++){
			if(GeneraTabla.TablaDeSimbolos.get(j).getValor().contains(" ")){
			GuardaExpresion(GeneraTabla.TablaDeSimbolos.get(j).getValor());
			//GuardaExpresion(GeneraTabla.TablaDeSimbolos.get(2).getValor());
			Cuadruplo();
			//Tabla.add("="+" "+aux.get(0)+" "+GeneraTabla.TablaDeSimbolos.get(2).getNombre());
			Tabla.add("=");
			Tabla.add(aux.get(0));
			Tabla.add("");
			Tabla.add(GeneraTabla.TablaDeSimbolos.get(j).getNombre());
			for(int i=0;i<Tabla.size();i+=4){
				Vista.modeloCuadruplo.insertRow(Vista.modeloCuadruplo.getRowCount(),new Object[]{
						Tabla.get(i),
						Tabla.get(i+1),
						Tabla.get(i+2),
						Tabla.get(i+3)});
			}
			Vista.modeloCuadruplo.insertRow(Vista.modeloCuadruplo.getRowCount(),new Object[]{
					"",
					"",
					"",
					""});
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
			}
		}
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
		aux=(ArrayList)expresion.clone();
		int cont=0;
		for(int i=0;i<expresion.size();i++){
			if(expresion.get(i).equals("/") || expresion.get(i).equals("*") || expresion.get(i).equals("+") || expresion.get(i).equals("-")){
				if(expresion.get(i+1).equals("-") || expresion.get(i+1).equals("+")){
					cont++;
					//Tabla.add(expresion.get(i+1)+" "+expresion.get(i+2)+" "+"T"+cont);
					Tabla.add(expresion.get(i+1));
					Tabla.add(expresion.get(i+2));
					Tabla.add("");
					Tabla.add("T"+cont);
					expresionEditada.add(expresion.get(i));
					expresionEditada.add("T"+cont);
					i+=2;
				}
				else
					expresionEditada.add(expresion.get(i));
			}
			else
			expresionEditada.add(expresion.get(i));
		}
		expresion=(ArrayList)expresionEditada.clone();
		aux=(ArrayList)expresionEditada.clone();
		expresionEditada.clear();
		while(expresion.contains("/") || expresion.contains("*")){
		for(int i=0;i<expresion.size();i++){
			if(expresion.get(i).equals("/") || expresion.get(i).equals("*")){
				cont++;
				//Tabla.add(expresion.get(i)+" "+expresion.get(i-1)+" "+expresion.get(i+1)+" "+"T"+cont);
				Tabla.add(expresion.get(i));
				Tabla.add(expresion.get(i-1));
				Tabla.add(expresion.get(i+1));
				Tabla.add("T"+cont);
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
					//Tabla.add(expresion.get(i)+" "+expresion.get(i-1)+" "+expresion.get(i+1)+" "+"T"+cont);
					Tabla.add(expresion.get(i));
					Tabla.add(expresion.get(i-1));
					Tabla.add(expresion.get(i+1));
					Tabla.add("T"+cont);
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

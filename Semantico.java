package Compilador;

import java.util.ArrayList;

public class Semantico {
	static ArrayList<Identificador> TablaDeSimbolos = new ArrayList<Identificador>();
	public Semantico(){
		//Validacion de variables utilizadas sin declarar
		ValidarDeclaracion();
		//Validacion de variables ya declaradas
		ValidarDuplicado();
	}
	public void ValidarDeclaracion(){
		for(int i=0;i<GeneraTabla.Variables.size();i++){
			Identificador ide = GeneraTabla.Variables.get(i);
			if(ide.getTipo().equals(""))
				Lexico.errores.add("Error en la linea "+ide.getLinea()+": La variable "+ide.getNombre()+" no ha sido declarada.");
		}
		
	}
	
	public void ValidarDuplicado(){
		for(int i=0;i<GeneraTabla.Variables.size()-1;i++){
			Identificador variable1= GeneraTabla.Variables.get(i);
			for(int j=i+1;j<GeneraTabla.Variables.size();j++){
				Identificador variable2= GeneraTabla.Variables.get(j);
				if(variable1.getNombre().equals(variable2.getNombre()) && (!variable2.getTipo().equals("") || !variable1.getTipo().equals(""))){
					//Son iguales, se debe verificar sus alcances
					if(variable1.getAlcance().equals("Global") && variable2.getAlcance().equals("Global")){
						Lexico.errores.add("Error en la linea "+variable2.getLinea()+": La variable "
								+variable2.getNombre()+" ya fue declarada en la linea "+variable1.getLinea());	
					}
					else if(variable1.getAlcance().equals("Global") && variable2.getAlcance().equals("Local")){
						Lexico.errores.add("Error en la linea "+variable2.getLinea()+": La variable "
								+variable2.getNombre()+" ya fue declarada en la linea "+variable1.getLinea());	
					}
					else if(variable1.getAlcance().equals("Local") && variable2.getAlcance().equals("Local")){
						if(variable1.getDesde()<variable2.desde)
							Lexico.errores.add("Error en la linea "+variable2.getLinea()+": La variable "
									+variable2.getNombre()+" ya fue declarada en la linea "+variable1.getLinea());	
					}
					
				}
			}
		}
	}
}

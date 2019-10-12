package Compilador;

import java.util.ArrayList;

public class Semantico {
	static ArrayList<Identificador> TablaDeSimbolos = new ArrayList<Identificador>();
	static ArrayList<String> ErroresSemanticos = new ArrayList<String>();
	public Semantico(){
		//Validacion de variables utilizadas sin declarar
		ValidarDeclaracion();
	}
	public void ValidarDeclaracion(){
		for(int i=0;i<GeneraTabla.Variables.size();i++){
			Identificador ide = GeneraTabla.Variables.get(i);
			if(ide.getTipo().equals(""))
				Lexico.errores.add("Error: La variable "+ide.getNombre()+" no ha sido declarada. Linea: "+ide.getLinea());
		}
	}
}

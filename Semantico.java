package Compilador;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Semantico {
	static ArrayList<Identificador> TablaDeSimbolos = new ArrayList<Identificador>();
	public Semantico(){
		//Validacion de variables utilizadas sin declarar
		ValidarDeclaracion();
		//Validacion de variables ya declaradas
		ValidarDuplicado();
		//Validar asignacion auna variable
		ValidarAsignacion();
		//Validar operando de tipos compatibles
		ValidarOperandos();
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
						if(variable1.getDesde()<variable2.getDesde())
							Lexico.errores.add("Error en la linea "+variable2.getLinea()+": La variable "
									+variable2.getNombre()+" ya fue declarada en la linea "+variable1.getLinea());	
					}
					
				}
			}
		}
	}
	
	public void ValidarAsignacion(){
		//Aqui validaremos solamente cuando se le iguala un valor a la variable
		//cuando se le asigan una expresion sera validado en el metodo de operadores
		StringTokenizer tokenizer;
		for(int i=0;i<GeneraTabla.Variables.size();i++){
			Identificador ide = GeneraTabla.Variables.get(i);
			tokenizer = new StringTokenizer(ide.getValor());
			//Validar variable booleana
			if(ide.getTipo().equals("boolean") && tokenizer.countTokens()==1){
				if(EsEntero(ide.getValor())){	
					Lexico.errores.add("Error en la linea "+buscaLinea(ide)+": La variable "+ide.getNombre()+" de tipo "+ide.getTipo()+" no se le puede asignar un valor int.");
				}else if(EsDouble(ide.getValor())){
					Lexico.errores.add("Error en la linea "+buscaLinea(ide)+": La variable "+ide.getNombre()+" de tipo "+ide.getTipo()+" no se le puede asignar un valor double.");
				}else if(!EsBooleana(ide.getValor()))
					Lexico.errores.add("Error en la linea "+buscaLinea(ide)+": La variable "+ide.getNombre()+" de tipo "+ide.getTipo()+" no se le puede asignar un valor cadena.");
			}
			//Validar variable entera
			else if(ide.getTipo().equals("int") && tokenizer.countTokens()==1){
				if(EsDouble(ide.getValor())){
					Lexico.errores.add("Error en la linea "+buscaLinea(ide)+": La variable "+ide.getNombre()+" de tipo "+ide.getTipo()+" no se le puede asignar un valor double.");
				}
				else if(EsBooleana(ide.getValor())){
					Lexico.errores.add("Error en la linea "+buscaLinea(ide)+": La variable "+ide.getNombre()+" de tipo "+ide.getTipo()+" no se le puede asignar un valor boolean.");
				}
				else if(!EsEntero(ide.getValor())){
					Lexico.errores.add("Error en la linea "+buscaLinea(ide)+": La variable "+ide.getNombre()+" de tipo "+ide.getTipo()+" no se le puede asignar un valor cadena.");
				}
			}
			//Validar variable double
			else if(ide.getTipo().equals("double") && tokenizer.countTokens()==1){
				if(EsEntero(ide.getValor())){
					Lexico.errores.add("Error en la linea "+buscaLinea(ide)+": La variable "+ide.getNombre()+" de tipo "+ide.getTipo()+" no se le puede asignar un valor int.");
				}
				else if(EsBooleana(ide.getValor())){
					Lexico.errores.add("Error en la linea "+buscaLinea(ide)+": La variable "+ide.getNombre()+" de tipo "+ide.getTipo()+" no se le puede asignar un valor boolean.");
				}
				else if(!EsDouble(ide.getValor())){
					Lexico.errores.add("Error en la linea "+buscaLinea(ide)+": La variable "+ide.getNombre()+" de tipo "+ide.getTipo()+" no se le puede asignar un valor cadena.");
				}
			}
			//variables string pueden asignar cualquier cadena y cualquier caracter
	}
}
	
	public void ValidarOperandos(){
		
	}
	
	public boolean EsEntero(String cadena) {
        boolean resultado;
        if(cadena.indexOf(".")==-1){//No trae punto es entero
        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }
        }else
        	resultado=false;
        return resultado;
    }
	
	public boolean EsDouble(String cadena) {
        boolean resultado;
        if(cadena.indexOf(".")!=-1){//trae punto es decimal
         try {
            Double.parseDouble(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }
        }else
        	resultado=false;
        return resultado;
    }
	
	public boolean EsBooleana(String cadena) {
		boolean resultado;
       if(cadena.equals("true")){
    	   resultado=true;
       }
       else if (cadena.equals("false")){
    	   resultado = true;
       }
       else
    	   resultado=false;
        return resultado;
    }
	
	public int buscaLinea(Identificador ide){
		int linea=0;
		for(int i=0;i<Lexico.tokenAnalizados.size();i++){
			if(Lexico.tokenAnalizados.get(i).getValor().equals(ide.getNombre())
					&& Lexico.tokenAnalizados.get(i+1).getValor().equals("=")
					&& Lexico.tokenAnalizados.get(i+2).getValor().equals(ide.getValor())){
				linea=Lexico.tokenAnalizados.get(i).getLinea();
			}
		}
		return linea;
	}
}

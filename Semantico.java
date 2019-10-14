package Compilador;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Semantico {
	public Semantico(){
		//Validacion de TablaDeSimbolos utilizadas sin declarar
		ValidarDeclaracion();
		//Validacion de TablaDeSimbolos ya declaradas
		ValidarDuplicado();
		//Validar asignacion auna variable
		ValidarAsignacion();
		//Validar operando de tipos compatibles
		ValidarOperandos();
		if(Lexico.errores.get(Lexico.errores.size()-1).equals("No hay errores sintacticos"))
			Lexico.errores.add("No hay errores semanticos");
		GeneraTabla.llenarTabla();
		
	}
	public void ValidarDeclaracion(){
		for(int i=0;i<GeneraTabla.TablaDeSimbolos.size();i++){
			Identificador ide = GeneraTabla.TablaDeSimbolos.get(i);
			if(ide.getTipo().equals("")){
				Lexico.errores.add("Error en la linea "+ide.getLinea()+": La variable "+ide.getNombre()+" no ha sido declarada.");
				GeneraTabla.TablaDeSimbolos.get(i).setCorrecta(false);
			}
		}
		
	}
	
	public void ValidarDuplicado(){
		for(int i=0;i<GeneraTabla.TablaDeSimbolos.size()-1;i++){
			Identificador variable1= GeneraTabla.TablaDeSimbolos.get(i);
			for(int j=i+1;j<GeneraTabla.TablaDeSimbolos.size();j++){
				Identificador variable2= GeneraTabla.TablaDeSimbolos.get(j);
				if(variable1.getNombre().equals(variable2.getNombre()) && (!variable2.getTipo().equals("") || !variable1.getTipo().equals(""))){
					//Son iguales, se debe verificar sus alcances
					if(variable1.getAlcance().equals("Global") && variable2.getAlcance().equals("Global")){
						Lexico.errores.add("Error en la linea "+variable2.getLinea()+": La variable "
								+variable2.getNombre()+" ya fue declarada en la linea "+variable1.getLinea());
						GeneraTabla.TablaDeSimbolos.get(j).setCorrecta(false);
					}
					else if(variable1.getAlcance().equals("Global") && variable2.getAlcance().equals("Local")){
						Lexico.errores.add("Error en la linea "+variable2.getLinea()+": La variable "
								+variable2.getNombre()+" ya fue declarada en la linea "+variable1.getLinea());
						GeneraTabla.TablaDeSimbolos.get(j).setCorrecta(false);
					}
					else if(variable1.getAlcance().equals("Local") && variable2.getAlcance().equals("Local")){
						if(variable1.getDesde()<variable2.getDesde())
							Lexico.errores.add("Error en la linea "+variable2.getLinea()+": La variable "
									+variable2.getNombre()+" ya fue declarada en la linea "+variable1.getLinea());	
						GeneraTabla.TablaDeSimbolos.get(j).setCorrecta(false);
					}
					
				}
			}
		}
	}
	
	public void ValidarAsignacion(){
		//Aqui validaremos solamente cuando se le iguala un valor a la variable
		//cuando se le asigan una expresion sera validado en el metodo de operadores
		StringTokenizer tokenizer;
		for(int i=0;i<GeneraTabla.TablaDeSimbolos.size();i++){
			Identificador ide = GeneraTabla.TablaDeSimbolos.get(i);
			tokenizer = new StringTokenizer(ide.getValor());
			//Validar variable booleana
			if(ide.getTipo().equals("boolean") && tokenizer.countTokens()==1){
				if(EsEntero(ide.getValor())){	
					Lexico.errores.add("Error en la linea "+buscaLinea(ide)+": La variable "+ide.getNombre()+" de tipo "+ide.getTipo()+" no se le puede asignar un valor int.");
					GeneraTabla.TablaDeSimbolos.get(i).setCorrecta(false);
				}else if(EsDouble(ide.getValor())){
					Lexico.errores.add("Error en la linea "+buscaLinea(ide)+": La variable "+ide.getNombre()+" de tipo "+ide.getTipo()+" no se le puede asignar un valor double.");
					GeneraTabla.TablaDeSimbolos.get(i).setCorrecta(false);
				}else if(!EsBooleana(ide.getValor())){
					Lexico.errores.add("Error en la linea "+buscaLinea(ide)+": La variable "+ide.getNombre()+" de tipo "+ide.getTipo()+" no se le puede asignar un valor cadena.");
					GeneraTabla.TablaDeSimbolos.get(i).setCorrecta(false);
				}
				}
			//Validar variable entera
			else if(ide.getTipo().equals("int") && tokenizer.countTokens()==1){
				if(EsDouble(ide.getValor())){
					Lexico.errores.add("Error en la linea "+buscaLinea(ide)+": La variable "+ide.getNombre()+" de tipo "+ide.getTipo()+" no se le puede asignar un valor double.");
					GeneraTabla.TablaDeSimbolos.get(i).setCorrecta(false);
				}
				else if(EsBooleana(ide.getValor())){
					Lexico.errores.add("Error en la linea "+buscaLinea(ide)+": La variable "+ide.getNombre()+" de tipo "+ide.getTipo()+" no se le puede asignar un valor boolean.");
					GeneraTabla.TablaDeSimbolos.get(i).setCorrecta(false);
				}
				else if(!EsEntero(ide.getValor())){
					Lexico.errores.add("Error en la linea "+buscaLinea(ide)+": La variable "+ide.getNombre()+" de tipo "+ide.getTipo()+" no se le puede asignar un valor cadena.");
					GeneraTabla.TablaDeSimbolos.get(i).setCorrecta(false);
				}
			}
			//Validar variable double
			else if(ide.getTipo().equals("double") && tokenizer.countTokens()==1){
				if(EsEntero(ide.getValor())){
					Lexico.errores.add("Error en la linea "+buscaLinea(ide)+": La variable "+ide.getNombre()+" de tipo "+ide.getTipo()+" no se le puede asignar un valor int.");
					GeneraTabla.TablaDeSimbolos.get(i).setCorrecta(false);
				}
				else if(EsBooleana(ide.getValor())){
					Lexico.errores.add("Error en la linea "+buscaLinea(ide)+": La variable "+ide.getNombre()+" de tipo "+ide.getTipo()+" no se le puede asignar un valor boolean.");
					GeneraTabla.TablaDeSimbolos.get(i).setCorrecta(false);
				}
				else if(!EsDouble(ide.getValor())){
					Lexico.errores.add("Error en la linea "+buscaLinea(ide)+": La variable "+ide.getNombre()+" de tipo "+ide.getTipo()+" no se le puede asignar un valor cadena.");
					GeneraTabla.TablaDeSimbolos.get(i).setCorrecta(false);
				}
			}
			//variables string pueden asignar cualquier cadena y cualquier caracter
	}
}
	
	public void ValidarOperandos(){
		ArrayList<String> tokensExpresion = new ArrayList<String>();
		for(int i =0;i<GeneraTabla.TablaDeSimbolos.size();i++){
			Identificador ide = GeneraTabla.TablaDeSimbolos.get(i);//obtenemos el valor
			StringTokenizer tokenizer = new StringTokenizer(ide.getValor());
			int z=tokenizer.countTokens();
			for(int j=0;j<z;j++){
				tokensExpresion.add(tokenizer.nextToken());//los metemos separados en array
			}
			if(ide.getTipo().equals("boolean") && tokensExpresion.size()>1){
				Lexico.errores.add("Error en la linea "+buscaLinea(ide)+": La variable "+ide.getNombre()+" de tipo "+ide.getTipo()+" no se le puede asignar una expresion.");
				GeneraTabla.TablaDeSimbolos.get(i).setCorrecta(false);
			}else if(ide.getTipo().equals("int") && tokensExpresion.size()>1){
				//Los recorremos para buscar que todos sean enteros
				for(int j=0;j<tokensExpresion.size();j++){
					if(!tokensExpresion.get(j).equals("+") && !tokensExpresion.get(j).equals("-") && !tokensExpresion.get(j).equals("/")
							&& !tokensExpresion.get(j).equals("*")){
						//Si no es un operador entonces es un operando
						if(!revisaOperandos(tokensExpresion.get(j),"int")){//si no es entero ni variable entera
							Lexico.errores.add("Error en la linea "+buscaLineaE(ide,tokensExpresion.get(0))+": Existen valores no enteros en la expresion");
							GeneraTabla.TablaDeSimbolos.get(i).setCorrecta(false);
						}
					}
				}
			}else if(ide.getTipo().equals("double") && tokensExpresion.size()>1){
				//Los recorremos para buscar que todos sean double
				for(int j=0;j<tokensExpresion.size();j++){
					if(!tokensExpresion.get(j).equals("+") && !tokensExpresion.get(j).equals("-") && !tokensExpresion.get(j).equals("/")
							&& !tokensExpresion.get(j).equals("*")){
						//Si no es un operador entonces es un operando
						if(!revisaOperandos(tokensExpresion.get(j),"double")){//si no es double ni variable double
							Lexico.errores.add("Error en la linea "+buscaLineaE(ide,tokensExpresion.get(0))+": Existen valores no double en la expresion");
							GeneraTabla.TablaDeSimbolos.get(i).setCorrecta(false);
						}
					}
				}
			}
			tokensExpresion.clear();
		}
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
	
	public int buscaLineaE(Identificador ide, String e){
		int linea=0;
		for(int i=0;i<Lexico.tokenAnalizados.size();i++){
			if(Lexico.tokenAnalizados.get(i).getValor().equals(ide.getNombre())
					&& Lexico.tokenAnalizados.get(i+1).getValor().equals("=")
					&& Lexico.tokenAnalizados.get(i+2).getValor().equals(e)){
				linea=Lexico.tokenAnalizados.get(i).getLinea();
			}
		}
		return linea;
	}
	
	public boolean revisaOperandos(String operando, String tipo){
		boolean tipoCorrecto=false;
		if(tipo.equals("int")){
		if(EsEntero(operando)){//verificamos que sea numero entero
			tipoCorrecto=true;
		}else{
			//buscamos si es un identificador entero
			for(int i=0; i<GeneraTabla.TablaDeSimbolos.size();i++){
				if(GeneraTabla.TablaDeSimbolos.get(i).getNombre().equals(operando)
						&& GeneraTabla.TablaDeSimbolos.get(i).getTipo().equals("int")){
					tipoCorrecto=true;
					break;
				}
			}
		}
		}else{
			if(EsDouble(operando)){//verificamos que sea numero entero
				tipoCorrecto=true;
			}else{
				//buscamos si es un identificador entero
				for(int i=0; i<GeneraTabla.TablaDeSimbolos.size();i++){
					if(GeneraTabla.TablaDeSimbolos.get(i).getNombre().equals(operando)
							&& GeneraTabla.TablaDeSimbolos.get(i).getTipo().equals("double")){
						tipoCorrecto=true;
						break;
					}
				}
			}
		}
		return tipoCorrecto;
	}
}

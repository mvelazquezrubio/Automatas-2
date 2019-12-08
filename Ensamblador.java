package Compilador;

public class Ensamblador {

	public Ensamblador(){
		for(int i=0; i<Intermedio.Tabla.size();i+=4){
			if(!(Intermedio.Tabla.get(i).equals("="))){
				System.out.println("MOV AL,"+Intermedio.Tabla.get(i+1));
				System.out.println("MOV BL,"+Intermedio.Tabla.get(i+2));
			}else
			{
				System.out.println("MOV "+Intermedio.Tabla.get(i+3)+", "+Intermedio.Tabla.get(i+1));
			}
			if(Intermedio.Tabla.get(i).equals("+")){
				System.out.println("ADD AL, BL");
				System.out.println("MOV "+Intermedio.Tabla.get(i+3)+", AL");
			}else if(Intermedio.Tabla.get(i).equals("-")){
				System.out.println("SUB AL, BL");
				System.out.println("MOV "+Intermedio.Tabla.get(i+3)+", AL");
			}else if(Intermedio.Tabla.get(i).equals("/")){
				System.out.println("MUL BL");
				System.out.println("MOV "+Intermedio.Tabla.get(i+3)+", AX");
			}else if(Intermedio.Tabla.get(i).equals("*")){
				System.out.println("DIV BL");
				System.out.println("MOV "+Intermedio.Tabla.get(i+3)+", AX");
			}
		}
	}
	
}

package Compilador;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.*;
public class Vista extends JFrame implements ActionListener{
	JMenuBar menuPrincipal;
	JMenu opcion,analisis;
	JRadioButton abrir;
	JFileChooser archivoSeleccionado;
	File archivo;
	JTabbedPane documentos, analizada, resultados;
	JTextArea Doc,Lex,Result,inter,objeto;
	JList<String> tokens;
	boolean ban=true;
	static DefaultTableModel modelo;
	String titulos[]={"Nombre","Tipo","Valor","Posicion","Alcance"};
	JTable tabla;
	JScrollPane Tabla;
	public Vista() {
		formatoWindows();
		inicializaciones();
		if(archivoSeleccionado.showOpenDialog(this)==JFileChooser.CANCEL_OPTION) 
			return;
		hazInterfaz();
		hazEscuchas();
	}
	public void inicializaciones() {
		/*Menu*/
		menuPrincipal=new JMenuBar();
		opcion=new JMenu("Archivo");
		analisis=new JMenu("Analisis");
		/*Opciones del menu*/
		/*Menu 1*/
		opcion.add(new JMenuItem("Guardar"));
		opcion.getItem(0).setEnabled(false);
		opcion.addSeparator();
		opcion.add(new JMenuItem("Modificar"));
		/*Menu 2*/
		analisis.add(new JMenuItem("Lexico"));
		analisis.addSeparator();
		analisis.add(new JMenuItem("Sintactico"));
		analisis.getItem(2).setEnabled(false);
		analisis.addSeparator();
		analisis.add(new JMenuItem("Semantico"));
		analisis.getItem(4).setEnabled(false);
		/*Ventana de archivo*/
		archivoSeleccionado= new JFileChooser("Abrir");
		archivoSeleccionado.setDialogTitle("Abrir");
		archivoSeleccionado.setFileSelectionMode(JFileChooser.FILES_ONLY);
		/*SubVentanas de documento, codigo y resultado*/
		Doc = new JTextArea();
		Doc.setFont(new Font("Consolas", Font.PLAIN, 12));
		Lex = new JTextArea();
		Lex.setFont(new Font("Consolas", Font.PLAIN, 12));
		Lex.setEnabled(false);
		Result = new JTextArea();
		Result.setFont(new Font("Consolas", Font.PLAIN, 12));
		Result.setEnabled(false);
		documentos = new JTabbedPane();
		analizada = new JTabbedPane();
		resultados = new JTabbedPane();
	}
	private void hazInterfaz() {
		setTitle("Analizador");
		Dimension dim;
		dim=getToolkit().getScreenSize().getSize();
		setSize(dim);
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setJMenuBar(menuPrincipal);
		menuPrincipal.add(opcion);
		menuPrincipal.add(analisis);
		documentos.setToolTipText("Aqui se muestra el codigo");
		archivo=archivoSeleccionado.getSelectedFile();
		documentos.addTab(archivo.getName().toString(),new JScrollPane(Doc));
		analizada.addTab("Compilador",new JScrollPane(Lex));
		resultados.addTab("Resultados",new JScrollPane(Result));
		modelo = new DefaultTableModel(null,titulos);//Modelo de las tablas
		tabla= new JTable(modelo);//tabla de simbolos
		Tabla = new JScrollPane(tabla);
		/*Llenado del documento en pantalla*/
		abrir();
		/*Posicionamiento de los componentes de texto en pantalla*/
		documentos.setBounds(1,1,665,473);
		add(documentos);
		analizada.setBounds(664, 1,687,473);
		add(analizada);
		resultados.setBounds(1,451,665,260);
		add(resultados);
		Tabla.setBounds(664, 475,685, 235);
		add(Tabla);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void hazEscuchas() {
		/*Escuchadores*/
		opcion.getItem(0).addActionListener(this);
		opcion.getItem(2).addActionListener(this);
		analisis.getItem(0).addActionListener(this);
		analisis.getItem(2).addActionListener(this);
		analisis.getItem(4).addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		/*Opciones de archivo*/
		if(e.getSource()==opcion.getItem(0)) {
			guardar();
			opcion.getItem(0).setEnabled(false);
			analisis.getItem(0).setEnabled(true);
		}
		if(e.getSource()==opcion.getItem(2)) {
			opcion.getItem(0).setEnabled(true);
			abrir();
		}
		/*Lexico*/
		if(e.getSource()==analisis.getItem(0)) {
			reinicia();
			limpiarTabla();
			new Lexico(archivo.getAbsolutePath());
			ban=false;//Es para evitar que se guarde sin darle modificar
			llena(Lex,Result,"");//Lleno las area de texto con lo analizado
			analisis.getItem(0).setEnabled(false);//Deshabilito el boton en este caso lexico
			if(Lexico.errores.get(0).equals("No hay errores lexicos"))//Si el analisis lexico se hizo correctamente se habilita el sintactico
				analisis.getItem(2).setEnabled(true);
		}
		/*Sintactico*/
		if(e.getSource()==analisis.getItem(2)) {
			new Sintactico();
			llena(Lex,Result,"");
			analisis.getItem(2).setEnabled(false);
			if(Lexico.errores.get(1).equals("No hay errores sintacticos"))//Si el analisis sintactico se hizo correctamente se habilita el semantico
				analisis.getItem(4).setEnabled(true);
		}
		/*Semantico*/
		if(e.getSource()==analisis.getItem(4)) {
			analisis.getItem(4).setEnabled(false);
			new GeneraTabla();
			new Semantico();
			llena(Lex,Result,"");
		}
	}
	public boolean guardar() {
		try {
			FileWriter fw = new FileWriter(archivo);
			BufferedWriter bf = new BufferedWriter(fw);
			bf.write(Doc.getText());
			bf.close();
			fw.close();
		}catch (Exception e) {
			System.out.println("No se ha podido modificar el archivo");
			return false;
		}
		return true;
	}
	public boolean abrir() {
		String texto="",linea;
		try {
			FileReader fr = new FileReader(archivo) ; 
			BufferedReader br= new BufferedReader(fr);
			while((linea=br.readLine())!=null) 
				texto+=linea+"\n";
			Doc.setText(texto);
			return true;
		}catch (Exception e) {
			archivo=null;
			JOptionPane.showMessageDialog(null, "El archivo no es de tipo texto", "Warning",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}
	public void llena(JTextArea cuadro, JTextArea result, String mensalida) {
		String muestra="",error="";
		if(mensalida.length()==0) {
		for(int i=0; i<Lexico.tokenAnalizados.size(); i++)
			muestra+=Lexico.tokenAnalizados.get(i)+"\n";
		for(int i=0; i<Lexico.errores.size(); i++)
			error+=Lexico.errores.get(i)+"\n";
		cuadro.setText(muestra);
		result.setText(error);
		}else {
			cuadro.setText(mensalida);
		}
	}
	public void formatoWindows() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
		}
	}
	
	public void  reinicia(){
		Lexico.renglon=1;
	}
	
	public void limpiarTabla(){
		while(tabla.getRowCount()!=0){
			((DefaultTableModel)tabla.getModel()).removeRow(0);
		}
	}
}

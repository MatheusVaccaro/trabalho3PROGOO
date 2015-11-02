package pucrs.progoo.reader;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import pucrs.progoo.model.Linha;

import java.util.ArrayList;




public class Leitura{
	
	//testes
	public static void main(String args[]){
		System.out.println("teste");
		try{
			ArrayList<Linha> lista = preparaLinha();
			
			System.out.println(lista.size());
			
			for(Linha linha: lista){
				System.out.println(linha);
			}		
		}
		catch(IOException e){
			System.out.println("Erro de E/S");
			e.printStackTrace();
		}
	}
	//

	public static ArrayList<Linha> preparaLinha() throws IOException{
		
		ArrayList<Linha> lista = new ArrayList<Linha>();		
		Path path = Paths.get("linhas.csv");
		
		System.out.println(path);
		File file = new File("C:\\Users\\Matheus\\git\\trabalho3PROGOO\\onibus-base\\linhas.csv");
		
		try(Scanner sc = new Scanner(Files.newBufferedReader(path, Charset.forName("utf-8")))){
			sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
			String header = sc.nextLine(); // pula cabecalho
			
			while(sc.hasNext()){
				String id = sc.next();
				String nome = sc.next();
				String codigo = sc.next();
				String tipo = sc.next();
				if(tipo.equals("O")){
					char tipoChar = tipo.charAt(0);
					Linha linha = new Linha(id, nome, codigo, tipoChar);
					lista.add(linha);					
				}
				
			}
		return lista;
		}
	}
	
		
	
	
}


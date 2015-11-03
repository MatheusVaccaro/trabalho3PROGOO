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
import pucrs.progoo.model.Coordenada;

import java.util.ArrayList;




public class Leitura{
	
	//testes
	public static void main(String args[]){
		System.out.println("teste");
		try{
			ArrayList<Linha> lista = preparaLinha();
			
			System.out.println(lista.size());
			
			for(Linha linha: lista){
				System.out.println(linha.getNome() + "\t" + linha.getCodigo() + "\t" + linha.getTipo() + "\t" + linha.getIdLinha());
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
		
		try(Scanner sc = new Scanner(Files.newBufferedReader(path, Charset.forName("utf-8")))){
			sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
			String header = sc.nextLine(); // pula cabecalho
			
			while(sc.hasNext()){
				String id = sc.next();
				String nome = sc.next();
				nome = nome.substring(1, nome.length()-2); //length()-2 pega a penultima posição da string
				String codigo = sc.next();
				codigo = codigo.substring(1, codigo.length()-2);
				String tipo = sc.next();
				tipo = tipo.substring(1, tipo.length()-2);

				if(tipo.equals("O")){
					char tipoChar = tipo.charAt(0);
					Linha linha = new Linha(id, nome, codigo, tipoChar);
					lista.add(linha);					
				}
				
			}
		return lista;
		}
	}
	
	
	public static ArrayList<Coordenada> preparaCoordenada(String idLinha) throws IOException{
		int idLinhaInt = Integer.parseInt(idLinha), idLinhaCoordenadaInt = 0;
		ArrayList<Coordenada> lista = new ArrayList<Coordenada>();		
		Path path = Paths.get("coordenadas.csv");
				
		try(Scanner sc = new Scanner(Files.newBufferedReader(path, Charset.forName("utf-8")))){
			sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
			String header = sc.nextLine(); // pula cabecalho
			
			do{
				sc.next(); //pula o Id da coordenada
				String latitude = sc.next();
				int latitudeInt = Integer.parseInt(latitude);
				String longitude = sc.next();
				int longitudeInt = Integer.parseInt(longitude);
				String idLinhaCoordenada = sc.next();
				idLinhaCoordenadaInt = Integer.parseInt(idLinhaCoordenada);
				
				if(idLinhaCoordenadaInt == idLinhaInt){
					Coordenada coordenada = new Coordenada(latitudeInt, longitudeInt);
					lista.add(coordenada);
				}
				
			}while(sc.hasNext() && idLinhaCoordenadaInt <= idLinhaInt);
				
		
		return lista;
		}
	}
	
	//esse método precisa da estrutura criada por preparaParadaLinha() para funcionar
	//obs: preparaParada() não foi implementado ainda
	public static ArrayList<Parada> preparaParada(String idLinha) throws IOException{
		int idLinhaInt = Integer.parseInt(idLinha), idLinhaCoordenadaInt = 0;
		ArrayList<Coordenada> lista = new ArrayList<Coordenada>();		
		Path path = Paths.get("paradas.csv");
				
		try(Scanner sc = new Scanner(Files.newBufferedReader(path, Charset.forName("utf-8")))){
			sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
			String header = sc.nextLine(); // pula cabecalho
			
			do{
				sc.next(); //pula o Id da coordenada
				String latitude = sc.next();
				int latitudeInt = Integer.parseInt(latitude);
				String longitude = sc.next();
				int longitudeInt = Integer.parseInt(longitude);
				String idLinhaCoordenada = sc.next();
				idLinhaCoordenadaInt = Integer.parseInt(idLinhaCoordenada);
				
				if(idLinhaCoordenadaInt == idLinhaInt){
					Coordenada coordenada = new Coordenada(latitudeInt, longitudeInt);
					lista.add(coordenada);
				}
				
			}while(sc.hasNext() && idLinhaCoordenadaInt <= idLinhaInt);
				
		
		return lista;
		}
	}
	
	
	
	//hashmap dos brother VAMO CLAN
	public static int[][] preparaParadaLinha() throws IOException{

		
		Path path = Paths.get("paradalinha.csv");
				
		try(Scanner sc = new Scanner(Files.newBufferedReader(path, Charset.forName("utf-8")))){
			sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
			String header = sc.nextLine(); // pula cabecalho
			
			while(sc.hasNext()){
				String idLinha = sc.next();
				int idLinhaInt = Integer.parseInt(idLinha);
				String idParada = sc.next();
				int idParadaInt = Integer.parseInt(idParada);				

				
				
			}
		return lista;
		}
	}
	
	
	//esse método vai usar os três criados acima, que terão chamada privada ao invés de pública futuramente
	public static ArrayList<Linha> constroiLinhas() throws IOException{
		ArrayList<Linha> lista = preparaLinha();
		
		
	}
	
	
}


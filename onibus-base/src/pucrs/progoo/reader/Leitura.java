package pucrs.progoo.reader;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import pucrs.progoo.model.Linha;
import pucrs.progoo.model.Coordenada;
import pucrs.progoo.model.Parada;

import java.util.ArrayList;






public class Leitura{
	
	//testes
	public static void main(String args[]){
		System.out.println("teste");
		try{
			Map<String, Linha> dic = geraLinhas();
					
			System.out.println(dic.size());
			
			for(String key: dic.keySet()){
				System.out.println(dic.get(key));
				Linha linha = dic.get(key);
				System.out.println(linha.getNome());
				System.out.println(linha.getCodigo());
				System.out.println(linha.getTipo());
				break;
			}
		}
		catch(IOException e){
			System.out.println("Erro de E/S");
			e.printStackTrace();
		}
	}
	//

	private static Map<String, Linha> preparaLinha() throws IOException{
		
		Map<String, Linha> dic = new HashMap<String, Linha>();		
		Path path = Paths.get("linhas.csv");
		
		try(Scanner sc = new Scanner(Files.newBufferedReader(path, Charset.forName("utf-8")))){
			sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
			String header = sc.nextLine(); // pula cabecalho
			
			while(sc.hasNext()){
				String id = sc.next();
				String nome = sc.next();
				nome = nome.substring(1, nome.length()-1); //length()-1 pega a penultima posição da string devido ao formato da informação provida pelo arquivo linhas.csv
				String codigo = sc.next();
				codigo = codigo.substring(1, codigo.length()-1);
				String tipo = sc.next();
				tipo = tipo.substring(1, tipo.length()-2); //neste caso, o formato de entrada no arquivo é, por exemplo, " 'O'' "

				if(tipo.equals("O")){
					char tipoChar = tipo.charAt(0);
					Linha linha = new Linha(id, nome, codigo, tipoChar);
					dic.put(id, linha);					
				}
				
			}
		return dic;
		}
	}
	
	
	private static void preparaCoordenada(Map<String, Linha> dic) throws IOException{
	
		Path path = Paths.get("coordenadas.csv");
				
		try(Scanner sc = new Scanner(Files.newBufferedReader(path, Charset.forName("utf-8")))){
			sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
			String header = sc.nextLine(); // pula cabecalho
			
			while(sc.hasNext()){
				sc.next(); //pula o Id da coordenada
				
				String latitude = sc.next();
				latitude = latitude.replaceAll(",", ".");
				double latitudeDouble = Double.parseDouble(latitude);
				
				String longitude = sc.next();
				longitude = longitude.replaceAll(",", ".");
				double longitudeDouble = Double.parseDouble(longitude);
				
				
				String idLinha = sc.next();
				
				/*
				 *A substring abaixo é feita porque o programa lê o idLinha como, por exemplo, "127816\r" e não permite que essa chave seja usada no hash map.
				 *A substring elimina esse "\r" da String.
				 */
				idLinha = idLinha.substring(0, idLinha.length()-1);
				
				Linha linha = dic.get(idLinha);
				if(linha != null){
					Coordenada novaCoordenada = new Coordenada(latitudeDouble, longitudeDouble);
					ArrayList<Coordenada> lista = linha.getCoordenadas();
					lista.add(novaCoordenada);
					dic.put(idLinha, linha);
				}	
			}
		}
	}

	
	private static Map<String, Parada> preparaParada() throws IOException{
		Map<String, Parada> dic = new LinkedHashMap<>();
		Path path = Paths.get("paradas.csv");
				
		try(Scanner sc = new Scanner(Files.newBufferedReader(path, Charset.forName("utf-8")))){
			sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
			String header = sc.nextLine(); // pula cabecalho
					
			while(sc.hasNext()){
				String idParada = sc.next();
				sc.next(); // pula o código da parada
				
				String longitude = sc.next();
				longitude = longitude.replaceAll(",", ".");
				double longitudeDouble = Double.parseDouble(longitude);
				
				String latitude = sc.next();
				latitude = latitude.replaceAll(",", ".");
				double latitudeDouble = Double.parseDouble(latitude);
				
				String terminal = sc.next();
				char terminalChar = terminal.charAt(1);
				
				Parada parada = new Parada(idParada, longitudeDouble, latitudeDouble, terminalChar);
				
				dic.put(idParada, parada);
			}
		
		return dic;
		}
	}
	
	

	private static Map<String, ArrayList<String>> preparaParadaLinha() throws IOException{

		Map<String, ArrayList<String>> dic = new HashMap<>();
		
		Path path = Paths.get("paradalinha.csv");
				
		try(Scanner sc = new Scanner(Files.newBufferedReader(path, Charset.forName("utf-8")))){
			sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
			String header = sc.nextLine(); // pula cabecalho
			
			String idLinha = sc.next(), novoIdLinha = null;
			
			while(sc.hasNext()){

				ArrayList<String> lista = new ArrayList<>();
				
				while(sc.hasNext()){
					
					String idParada = sc.next();
					idParada = idParada.substring(0, idParada.length()-1);
					lista.add(idParada);
					if(sc.hasNext()){
						novoIdLinha = sc.next();
					}
					
					if(!novoIdLinha.equals(idLinha)){
						dic.put(idLinha, lista);
						idLinha = novoIdLinha;
						break;
					}
					
					if(!sc.hasNext()){
						dic.put(idLinha, lista);
					}
				}
			}
		return dic;
		}
	}
	
	private static void insereParada(Map<String, ArrayList<String>> paradaLinha, Map<String, Parada> paradas, Map<String, Linha> linhas) throws IOException{
		
		for(String idLinha: paradaLinha.keySet()){
			Linha linha = linhas.get(idLinha);
			ArrayList<String> listaParadaLinha = paradaLinha.get(idLinha);
			Map<String, Parada> dicParada = linha.getParadas();
			
			for(int i = 0; i < listaParadaLinha.size(); i++){
				String idParada = listaParadaLinha.get(i);
				Parada parada = paradas.get(idParada);
				
				dicParada.put(idParada, parada);
				
			}
			linha.setParadas(dicParada);
		}
	}
	

	public static Map<String, Linha> geraLinhas() throws IOException{
		
		Map<String, Linha> dic = preparaLinha();
		preparaCoordenada(dic);
		Map<String, ArrayList<String>> paradaLinha = preparaParadaLinha();
		Map<String, Parada> parada = preparaParada();
		insereParada(paradaLinha, parada, dic);
		
		return dic;
	}
	
	
}


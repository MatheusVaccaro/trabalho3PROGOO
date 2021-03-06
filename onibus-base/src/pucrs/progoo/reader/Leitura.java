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
import pucrs.progoo.model.Parada;

import java.util.ArrayList;

import org.jxmapviewer.viewer.GeoPosition;

public class Leitura{
	//leitura de todas as linhas de �nibus
	private static Map<String, Linha> preparaLinha() throws IOException{
		Map<String, Linha> dic = new HashMap<String, Linha>();		
		Path path = Paths.get("linhas.csv");
		
		try(Scanner sc = new Scanner(Files.newBufferedReader(path, Charset.forName("utf-8")))){
			sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
			String header = sc.nextLine(); // pula cabecalho
		
			while(sc.hasNext()){
				String id = sc.next();
				String nome = sc.next();
				nome = nome.substring(1, nome.length()-1); //length()-1 pega a penultima posi��o da string devido ao formato da informa��o provida pelo arquivo linhas.csv
				String codigo = sc.next();
				codigo = codigo.substring(1, codigo.length()-1);
				String tipo = sc.next();
				tipo = Character.toString(tipo.charAt(1));
			
				if(tipo.equals("O")){
					char tipoChar = tipo.charAt(0);
					Linha linha = new Linha(id, nome, codigo, tipoChar);
					dic.put(id, linha);					
				}
				
			}
			return dic;
		}
	}
	
	//inser��o de todas as cordenadas em suas respectivas linhas de �nibus
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
				Linha linha = dic.get(idLinha);
				
				if(linha != null){
					GeoPosition novaCoordenada = new GeoPosition(latitudeDouble, longitudeDouble);
					ArrayList<GeoPosition> lista = linha.getCoordenadas();
					lista.add(novaCoordenada);
					dic.put(idLinha, linha);
				}	
			}
		}
	}
	
	//leitura de todas as paradas de �nibus
	public static Map<String, Parada> preparaParada() throws IOException{
		Map<String, Parada> dic = new HashMap<>();
		Path path = Paths.get("paradas.csv");
				
		try(Scanner sc = new Scanner(Files.newBufferedReader(path, Charset.forName("utf-8")))){
			sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
			String header = sc.nextLine(); // pula cabecalho
					
			while(sc.hasNext()){
				String idParada = sc.next();
				sc.next(); // pula o c�digo da parada
				
				String longitude = sc.next();
				longitude = longitude.replaceAll(",", ".");
				double longitudeDouble = Double.parseDouble(longitude);
				
				String latitude = sc.next();
				latitude = latitude.replaceAll(",", ".");
				double latitudeDouble = Double.parseDouble(latitude);
				
				String terminal = sc.next();
				char terminalChar = terminal.charAt(1);
				
				GeoPosition coordenadas = new GeoPosition(latitudeDouble, longitudeDouble);
				
				Parada parada = new Parada(idParada, coordenadas, terminalChar);
				
				dic.put(idParada, parada);
			}
		
		return dic;
		}
	}
	
	//leitura do arquivo que relaciona as linhas e suas respectivas paradas
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
	
	//inser��o da paradas de �nibus em suas respectivas linhas
	private static void insereParada(Map<String, ArrayList<String>> paradaLinha, Map<String, Parada> paradas, Map<String, Linha> linhas) throws IOException{
		for(String idLinha: paradaLinha.keySet()){
			Linha linha = linhas.get(idLinha);
			ArrayList<String> listaParadaLinha = paradaLinha.get(idLinha);
			Map<String, Parada> dicParada = linha.getParadas();
			
			for(int i = 0; i < listaParadaLinha.size(); i++){
				String idParada = listaParadaLinha.get(i);
				if(paradas.containsKey(idParada)){
					Parada parada = paradas.get(idParada);
					dicParada.put(idParada, parada);
				}
			}
			linha.setParadas(dicParada);
		}
	}
		
	//m�todo que realiza a leitura dos 4 arquivos de dados
	public static Map<String, Linha> geraLinhas() throws IOException{
		Map<String, Linha> dic = preparaLinha();
		preparaCoordenada(dic);
		Map<String, ArrayList<String>> paradaLinha = preparaParadaLinha();
		Map<String, Parada> parada = preparaParada();
		insereParada(paradaLinha, parada, dic);
		
		return dic;
	}	
}
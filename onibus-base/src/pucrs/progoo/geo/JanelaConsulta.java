/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pucrs.progoo.geo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

import pucrs.progoo.model.Coordenada;
import pucrs.progoo.model.Linha;
import pucrs.progoo.model.Parada;
import pucrs.progoo.reader.*;

import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.UIManager;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 *
 * @author Marcelo Cohen
 */
public class JanelaConsulta extends javax.swing.JFrame {

    private GerenciadorMapa gerenciador;
    private EventosMouse mouse;
    private JPanel painelMapa;
    private JPanel painelInferior;
    private Map<String, Linha> dicLinhas;
    private DefaultListModel<Linha> listaLinhas;
    private DefaultListModel<Linha> listaPadrao;
    private JList<Linha> list;
    private final Action action = new SwingAction();
    private JPanel panel;

    /**
     * Creates new form JanelaConsulta
     */
    public JanelaConsulta() {
    	super();    	
    	
        //initComponents();
    	try{
    		dicLinhas = Leitura.geraLinhas();
    	} catch(IOException e){
    		System.err.println("Erro na geraÁ„o das linhas de Ùnibus");
    	}
    	
    	listaLinhas = new DefaultListModel<>();
    	
    	for(String key: dicLinhas.keySet()){
    		Linha linha = dicLinhas.get(key);
    		listaLinhas.addElement(linha);
    	}
    	
        GeoPosition poa = new GeoPosition(-30.05, -51.18);
        gerenciador = new GerenciadorMapa(poa, GerenciadorMapa.FonteImagens.VirtualEarth);
        mouse = new EventosMouse();        		
        gerenciador.getMapKit().getMainMap().addMouseListener(mouse);
        gerenciador.getMapKit().getMainMap().addMouseMotionListener(mouse);       

        painelMapa = new JPanel();
        painelMapa.setLayout(new BorderLayout());
        painelMapa.add(gerenciador.getMapKit(), BorderLayout.CENTER);
                
        getContentPane().add(painelMapa, BorderLayout.CENTER);
        
        painelInferior = new JPanel();
        getContentPane().add(painelInferior, BorderLayout.SOUTH);
        
        //botıes
        JButton btnNewButton = new JButton("Consulta 1");
        btnNewButton.setAction(action);
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {   
        		panel.setVisible(true);
        		limparMapa(e);        		
        		consulta1(e); 
        		
        	}
        });
        painelInferior.add(btnNewButton);
        
        JButton btnNewButton2 = new JButton("Consulta 2");
        btnNewButton2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		panel.setVisible(false);
        		consulta2(e);
        	}
        });
        painelInferior.add(btnNewButton2);
        
        JButton btnNewButton3 = new JButton("Consulta 3");
        btnNewButton3.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		panel.setVisible(false);
        		consulta3(e);
        	}
        });
        painelInferior.add(btnNewButton3);
        
        JButton btnNewButton4 = new JButton("Consulta 4");
        btnNewButton4.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		panel.setVisible(false);
        		consulta4(e);
        	}
        });
        painelInferior.add(btnNewButton4);
        
        JButton btnNewButton5 = new JButton("Limpar Mapa");
        btnNewButton5.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		limparMapa(e);
        	}
        });
        painelInferior.add(btnNewButton5);
        
        painelInferior.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        
        //CriaÁ„o da lista de resultados/linhas
        panel = new JPanel();
        panel.addComponentListener(new ComponentAdapter() {        	        	
        });
        getContentPane().add(panel, BorderLayout.WEST);
        JScrollPane scrollPane = new JScrollPane();
        panel.add(scrollPane);
        list = new JList();
        list.setVisibleRowCount(32);
        list.setValueIsAdjusting(true);
        list.addComponentListener(new ComponentAdapter() {        	
        });        
        list.setBorder(null);
        scrollPane.setViewportView(list);
        panel.setVisible(false);

        this.list.setModel(listaLinhas);
        
        this.setSize(800,600);
      
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    }

    private void consulta1(java.awt.event.ActionEvent evt) {	
        // Lista para armazenar o resultado da consulta
        List<MyWaypoint> lstPoints = new ArrayList<>();

        Linha linha = list.getSelectedValue();
        Map<String, Parada> dicParadas = linha.getParadas();
        
        for(String idParada: dicParadas.keySet()){
        	Parada parada = dicParadas.get(idParada);
        	GeoPosition loc = new GeoPosition(parada.getLatitude(), parada.getLongitude());
        	lstPoints.add(new MyWaypoint(Color.BLACK, parada.getIdParada(), loc));
        }
        
        
        // Informa o resultado para o gerenciador
        gerenciador.setPontos(lstPoints);
        
        
        // Exemplo: criando um tra√ßado
        Tracado tr = new Tracado();
        
        ArrayList<Coordenada> listaCoordenadas = linha.getCoordenadas();
        
        for(Coordenada coordenada: listaCoordenadas){
        	GeoPosition loc = new GeoPosition(coordenada.getLatitude(), coordenada.getLongitude());
        	tr.addPonto(loc);
        	tr.setCor(Color.CYAN);
        }
        
        
        // E adicionando o tra√ßado...
        gerenciador.addTracado(tr);
        
        
        this.repaint();
        }
    
    
    private void consulta2(java.awt.event.ActionEvent evt) {

        // Para obter um ponto clicado no mapa, usar como segue:
    	GeoPosition pos = gerenciador.getPosicao();     
    	
    	
    	
        // Lista para armazenar o resultado da consulta
        List<MyWaypoint> lstPoints = new ArrayList<>();
        
        
        // Exemplo de uso:
        
    	String idParada = "4133";
        
        // Map<String, Linha> dicLinhas = Leitura.geraLinhas();
        
        //retirar esse sysout
        System.out.println("Linhas que passam na parada " + idParada);
        
        for(String idLinha: dicLinhas.keySet()){ //percorre as  linhas
        	Linha linha = dicLinhas.get(idLinha);
        	
        	Map<String, Parada> dicParadaDaLinha = linha.getParadas();
        	if(dicParadaDaLinha.containsKey(idParada)){ //significa que a Linha passa na Parada
        		
        		//n„o tenho certeza que operaÁ„o fazer aqui
        		//XXX mudar esse sysout
        		System.out.println(linha.getNome() + "\t" + linha.getIdLinha());
            	//
        	}
        }
    }
        

    
    
    private void consulta3(java.awt.event.ActionEvent evt) {

        // Para obter um ponto clicado no mapa, usar como segue:
    	GeoPosition pos = gerenciador.getPosicao();     

        // Lista para armazenar o resultado da consulta
        List<MyWaypoint> lstPoints = new ArrayList<>();
        
        if(pos != null){
	        
        	//Map<String, Linha> dicLinhas = Leitura.geraLinhas();
	        
	        //retirar esse sysout
	        System.out.println("Linhas que passam perto do ponto selecionado");
	        
	        for(String idLinha: dicLinhas.keySet()){ //percorre as  linhas
	        	Linha linha = dicLinhas.get(idLinha);
	        	ArrayList<Coordenada> coordenadas = linha.getCoordenadas();
	        	for(Coordenada coordenada: coordenadas){
	        		GeoPosition posicao = new GeoPosition(coordenada.getLatitude(), coordenada.getLongitude());
	        		if(AlgoritmosGeograficos.calcDistancia(pos, posicao) <= 0.1){
	        			System.out.println(linha.getNome() + " - " + linha.getIdLinha());
	        			
	        			Tracado tr = new Tracado();
	        	        
	        	        ArrayList<Coordenada> listaCoordenadas = linha.getCoordenadas();
	        	        
	        	        for(Coordenada coordenada2: listaCoordenadas){
	        	        	GeoPosition loc = new GeoPosition(coordenada2.getLatitude(), coordenada2.getLongitude());
	        	        	tr.addPonto(loc);
	        	        	tr.setCor(criaCor());
	        	        }
	        	        gerenciador.addTracado(tr);
	        	         
	        	        
	        	        this.repaint();
	        			
	        			break;
	        		}
	        	}
	        }
        }
    }

    
    
    private void consulta4(java.awt.event.ActionEvent evt) {

	    // Para obter um ponto clicado no mapa, usar como segue:
		GeoPosition pos = gerenciador.getPosicao();     
	
	    // Lista para armazenar o resultado da consulta
	    List<MyWaypoint> lstPoints = new ArrayList<>();
	    
	    // Exemplo de uso:
	    	Set<String> listaLinhasQuePassamNasParadas = new LinkedHashSet<>();
	    	ArrayList<String> listaParadas = new ArrayList<>();
	    	listaParadas.add("4133");
	    	listaParadas.add("3809");
	    	
	    	//Map<String, Linha> dicLinhas = Leitura.geraLinhas();
	    
	        for(String idLinha: dicLinhas.keySet()){ //percorre as  linhas
	        	Linha linha = dicLinhas.get(idLinha);
	        	
	        	Map<String, Parada> dicParadaDaLinha = linha.getParadas();
	        	
	        	for(String idParada: listaParadas){
		        	if(dicParadaDaLinha.containsKey(idParada)){ //significa que a Linha passa na Parada
		        		
		        		//n„o tenho certeza que operaÁ„o fazer aqui
		        		//XXX dar uma olhada aqui
		        		listaLinhasQuePassamNasParadas.add(linha.getNome() + " - " + linha.getIdLinha());
		            	//
		        	}
	        	}
	        }
	        System.out.println("Lista de Linhas que passam nas paradas " + listaParadas);
	        for(String palavra: listaLinhasQuePassamNasParadas){
	        	System.out.println(palavra);
	        }
	   } 
    
    
    /*private void consultaOriginal(java.awt.event.ActionEvent evt) {

        // Para obter um ponto clicado no mapa, usar como segue:
    	GeoPosition pos = gerenciador.getPosicao();     

        // Lista para armazenar o resultado da consulta
        List<MyWaypoint> lstPoints = new ArrayList<>();
        
        // Exemplo de uso:
        
        GeoPosition loc = new GeoPosition(-30.05, -51.18);  // ex: localiza√ß√£o de uma parada
        GeoPosition loc2 = new GeoPosition(-30.08, -51.22); // ex: localiza√ß√£o de uma parada
        lstPoints.add(new MyWaypoint(Color.BLUE, "Teste", loc));
        lstPoints.add(new MyWaypoint(Color.BLACK, "Teste 2", loc2));
        
        

        // Informa o resultado para o gerenciador
        gerenciador.setPontos(lstPoints);
        
        // Exemplo: criando um tra√ßado
        Tracado tr = new Tracado();
        // Adicionando as mesmas localiza√ß√µes de antes
        tr.addPonto(loc);
        tr.addPonto(loc2);
        tr.setCor(Color.CYAN);
        // E adicionando o tra√ßado...
        gerenciador.addTracado(tr);
        
        // Outro:
        GeoPosition loc3 = new GeoPosition(-30.02, -51.16);  
        GeoPosition loc4 = new GeoPosition(-30.01, -51.24);
        Tracado tr2 = new Tracado();
        tr2.addPonto(loc3);
        tr2.addPonto(loc4);
        tr2.setCor(Color.MAGENTA);
        // E adicionando o tra√ßado...
        gerenciador.addTracado(tr2);
        
        this.repaint();

    }*/
    
    private void limparMapa(java.awt.event.ActionEvent evt){
    	List<MyWaypoint> lstPoints = new ArrayList<>();
    	gerenciador.setPontos(lstPoints);
    	gerenciador.clear();
    	this.repaint();
    }
    
    private class EventosMouse extends MouseAdapter{
    	private int lastButton = -1;    	
    	
    	@Override
    	public void mousePressed(MouseEvent e) {
    		JXMapViewer mapa = gerenciador.getMapKit().getMainMap();
    		GeoPosition loc = mapa.convertPointToGeoPosition(e.getPoint());
//    		System.out.println(loc.getLatitude()+", "+loc.getLongitude());
    		lastButton = e.getButton();
    		// Bot√£o 3: seleciona localiza√ß√£o
    		if(lastButton==MouseEvent.BUTTON3) {  			
    			gerenciador.setPosicao(loc);
    			//gerenciador.getMapKit().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    			gerenciador.getMapKit().repaint();    			
    		}
    	}    
    } 	
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
	  public Color criaCor()
	  {
	    Random rand = new Random();
	 
	    float r = rand.nextFloat();
	    float g = rand.nextFloat();
	    float b = rand.nextFloat();	   	    
	 
	    Color cor = new Color(r, g, b);
		return cor;
	    
	}
 }

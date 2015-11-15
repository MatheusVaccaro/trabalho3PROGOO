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

import pucrs.progoo.model.Linha;
import pucrs.progoo.model.Parada;
import pucrs.progoo.reader.*;

import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.UIManager;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

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
    private Map<String, Parada> dicParadas;
    private DefaultListModel<Linha> listaLinhas;
    private DefaultListModel<Linha> listaLinhasSelecionadas;
    private DefaultListModel<Parada> listaParadasSelecionadas;
    private JList<Linha> listLinhas;
    private JList<Linha> listLinhasSelecionadas;
    private JList<Parada> listParadasSelecionadas;
    private JPanel panel;
    private JPanel panelResultados;

    /**
     * Creates new form JanelaConsulta
     */
    public JanelaConsulta() {
    	super();    	
    	
        //initComponents();
    	try{
    		dicLinhas = Leitura.geraLinhas();
    	} catch(IOException e){
    		System.err.println("Erro na gera��o das linhas de �nibus");
    	}
    	
    	try{
    		dicParadas = Leitura.preparaParada();
    	} catch(IOException e){
    		System.err.println("Erro na gera��o das paradas de �nibus");
    	}
    	
    	listaLinhas = new DefaultListModel<>();
    	for(String key: dicLinhas.keySet()){
    		Linha linha = dicLinhas.get(key);
    		listaLinhas.addElement(linha);
    	}
    	
    	listaLinhasSelecionadas = new DefaultListModel<>();
    	listaParadasSelecionadas = new DefaultListModel<>();
    	
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
        
        //bot�es
        ////////////////////////
        
        JButton teste = new JButton("4970");
        teste.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		teste(e);
        	}
        });
        painelInferior.add(teste);
        
        JButton paradas = new JButton("Exibir Paradas");
        paradas.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		exibirParadas(e);
        	}
        });
        painelInferior.add(paradas);
        
        JButton btnNewButton = new JButton("Consulta 1");       
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {   
        		limparMapa(e);        		
        		consulta1(e); 
        		
        	}
        });
        painelInferior.add(btnNewButton);
        
        JButton btnNewButton2 = new JButton("Consulta 2");
        btnNewButton2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		consulta2(e);
        	}
        });
        painelInferior.add(btnNewButton2);
        
        JButton btnNewButton3 = new JButton("Consulta 3");
        btnNewButton3.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		limparMapa(e);
        		consulta3(e);
        	}
        });
        painelInferior.add(btnNewButton3);
        
        JButton btnNewButton4 = new JButton("Consulta 4");
        btnNewButton4.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		consulta4(e);
        	}
        });
        painelInferior.add(btnNewButton4);
        
        JButton btnNewButtonAddParada = new JButton("Adicionar Parada");
        btnNewButtonAddParada.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		addParada(e);
        	}
        });
        painelInferior.add(btnNewButtonAddParada);
        
        JButton btnNewButtonRemoveParada = new JButton("Remover Parada");
        btnNewButtonRemoveParada.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		removeParada(e);
        	}
        });
        painelInferior.add(btnNewButtonRemoveParada);
        
        JButton btnNewButton5 = new JButton("Limpar Mapa");
        btnNewButton5.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		limparMapa(e);
        	}
        });
        painelInferior.add(btnNewButton5);
        
        painelInferior.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        
        //Cria��o da lista de resultados/linhas
        panel = new JPanel();
        panel.addComponentListener(new ComponentAdapter() {        	        	
        });
        getContentPane().add(panel, BorderLayout.WEST);
        JScrollPane scrollPane = new JScrollPane();
        panel.add(scrollPane);
        listLinhas = new JList<>();
        listLinhas.setVisibleRowCount(32);
        listLinhas.setValueIsAdjusting(true);
        listLinhas.addComponentListener(new ComponentAdapter() {        	
        });        
        listLinhas.setBorder(null);
        scrollPane.setViewportView(listLinhas);
        panel.setVisible(true);

        this.listLinhas.setModel(listaLinhas);
        
        ////////////////////////////////////////////
        
        panelResultados = new JPanel();
        panelResultados.addComponentListener(new ComponentAdapter() {        	        	
        });
        getContentPane().add(panelResultados, BorderLayout.EAST);
        panelResultados.setLayout(new BoxLayout(panelResultados, BoxLayout.X_AXIS));
        JScrollPane scrollPane_1 = new JScrollPane();
        panelResultados.add(scrollPane_1);
        listLinhasSelecionadas = new JList<>();
        listLinhasSelecionadas.setVisibleRowCount(32);
        listLinhasSelecionadas.setValueIsAdjusting(true);
        listLinhasSelecionadas.addComponentListener(new ComponentAdapter() {        	
        });        
        listLinhasSelecionadas.setBorder(null);
        scrollPane_1.setViewportView(listLinhasSelecionadas);
        panelResultados.setVisible(true);
        
        this.listLinhasSelecionadas.setModel(listaLinhasSelecionadas);
        
        JScrollPane scrollPane_2 = new JScrollPane();
        panelResultados.add(scrollPane_2);
        listParadasSelecionadas = new JList<>();
        listParadasSelecionadas.setVisibleRowCount(32);
        listParadasSelecionadas.setValueIsAdjusting(true);
        listParadasSelecionadas.addComponentListener(new ComponentAdapter() {        	
        });        
        listParadasSelecionadas.setBorder(null);
        scrollPane_2.setViewportView(listParadasSelecionadas);
        panelResultados.setVisible(true);
        
        this.listParadasSelecionadas.setModel(listaParadasSelecionadas);
        
        this.setSize(800,600);
      
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    }

    private void addParada(java.awt.event.ActionEvent evt) {
    	GeoPosition pos = gerenciador.getPosicao();     

        //La�o utilizado para identificar a parada clicada no mapa
        Parada parada = null;
        for(String paradaKey: dicParadas.keySet()){
        	Parada aux = dicParadas.get(paradaKey);
        	if(AlgoritmosGeograficos.calcDistancia(pos, aux.getCoordenadas()) <= 0.05){
        		parada = aux;
        		listaParadasSelecionadas.addElement(parada);
        		break;
        	}
        }
    }

    private void removeParada(java.awt.event.ActionEvent evt) {
    	Parada parada = listParadasSelecionadas.getSelectedValue();
    	listaParadasSelecionadas.removeElement(parada);
    }
    
    public void teste(java.awt.event.ActionEvent evt){
    	List<MyWaypoint> lstPoints = new ArrayList<>();
    	Parada parada = dicParadas.get("4970");
    	lstPoints.add(new MyWaypoint(Color.BLACK, parada.getIdParada(), parada.getCoordenadas()));
    	gerenciador.setPontos(lstPoints);
    }
 
    private void exibirParadas(java.awt.event.ActionEvent evt) {	
        List<MyWaypoint> lstPoints = new ArrayList<>();
        
        for(String idParada: dicParadas.keySet()){
        	Parada parada = dicParadas.get(idParada);
        	lstPoints.add(new MyWaypoint(Color.BLACK, parada.getIdParada(), parada.getCoordenadas()));
        }
        
        gerenciador.setPontos(lstPoints);
        this.repaint();
        }
    
    private void consulta1(java.awt.event.ActionEvent evt) {	
        // Lista para armazenar o resultado da consulta
        List<MyWaypoint> lstPoints = new ArrayList<>();

        Linha linha = listLinhas.getSelectedValue();
        Map<String, Parada> dicParadas = linha.getParadas();
        
        for(String idParada: dicParadas.keySet()){
        	Parada parada = dicParadas.get(idParada);
        	lstPoints.add(new MyWaypoint(Color.BLACK, parada.getIdParada(), parada.getCoordenadas()));
        }
        
        
        // Informa o resultado para o gerenciador
        gerenciador.setPontos(lstPoints);
        
        
        // Exemplo: criando um traçado
        Tracado tr = new Tracado();
        
        ArrayList<GeoPosition> listaCoordenadas = linha.getCoordenadas();
        
        for(GeoPosition coordenada: listaCoordenadas){
        	tr.addPonto(coordenada);
        	tr.setCor(Color.CYAN);
        }
        
        
        // E adicionando o traçado...
        gerenciador.addTracado(tr);
        
        
        this.repaint();
        }
      
    private void consulta2(java.awt.event.ActionEvent evt) {
    	listaLinhasSelecionadas.clear();
    	gerenciador.clear();
        // Para obter um ponto clicado no mapa, usar como segue:
    	GeoPosition pos = gerenciador.getPosicao();     

        List<MyWaypoint> lstPoints = new ArrayList<>();
        
        //La�o utilizado para identificar a parada clicada no mapa
        Parada parada = null;
        for(String paradaKey: dicParadas.keySet()){
        	Parada aux = dicParadas.get(paradaKey);
        	if(AlgoritmosGeograficos.calcDistancia(pos, aux.getCoordenadas()) <= 0.05){
        		parada = aux;
        		break;
        	}
        }
        
        if(parada == null){
    		JOptionPane.showMessageDialog(null,
    			    "Uma parada n�o foi selecionada ou n�o pode ser encontrada.",
    			    "Aviso",
    			    JOptionPane.WARNING_MESSAGE);
    		return;
    	}
        
        lstPoints.add(new MyWaypoint(Color.BLACK, parada.getIdParada(), parada.getCoordenadas()));
        
        for(String idLinha: dicLinhas.keySet()){ //percorre as  linhas
        	Linha linha = dicLinhas.get(idLinha);
        	Map<String, Parada> dicParadaDaLinha = linha.getParadas();
        	if(dicParadaDaLinha.containsKey(parada.getIdParada())){ //significa que a Linha passa na Parada
        		ArrayList<GeoPosition> listaCoordenadas = linha.getCoordenadas();
        		Color corAleatoria = criaCor();
        		Tracado tr = new Tracado();
        		for(GeoPosition coordenada: listaCoordenadas){
        			tr.addPonto(coordenada);
    	        	tr.setCor(corAleatoria);
        		}
        		listaLinhasSelecionadas.addElement(linha);        		
        	    /*    
        		GeoPosition locIn = listaCoordenadas.get(0);
        		GeoPosition locFim = listaCoordenadas.get(listaCoordenadas.size()-1);
        	       
        		lstPoints.add(new MyWaypoint(corAleatoria, linha.getIdLinha(), locIn));
        		lstPoints.add(new MyWaypoint(corAleatoria, linha.getIdLinha(), locFim));
        	     */ 
    	        gerenciador.addTracado(tr);
    	        gerenciador.setPontos(lstPoints);
    	        
    	        this.repaint();
	        }
        }
    }
         
    private void consulta3(java.awt.event.ActionEvent evt) {
    	listaLinhasSelecionadas.clear();
    	gerenciador.clear();
        // Para obter um ponto clicado no mapa, usar como segue:
    	GeoPosition pos = gerenciador.getPosicao();     
    	
    	if(pos == null){
    		JOptionPane.showMessageDialog(null,
    			    "Voc� precisa selecionar um ponto do mapa primeiro.",
    			    "Aviso",
    			    JOptionPane.WARNING_MESSAGE);
    		return;
    	}
        
        List<MyWaypoint> lstPoints = new ArrayList<>();
        
        if(pos != null){	        
	        for(String idLinha: dicLinhas.keySet()){ //percorre as  linhas
	        	Linha linha = dicLinhas.get(idLinha);
	        	ArrayList<GeoPosition> coordenadas = linha.getCoordenadas();
	        	for(GeoPosition coordenada: coordenadas){
	        		if(AlgoritmosGeograficos.calcDistancia(pos, coordenada) <= 0.05){
	        			listaLinhasSelecionadas.addElement(linha);
	        			
	        			Tracado tr = new Tracado();
	        	        
	        	        ArrayList<GeoPosition> listaCoordenadas = linha.getCoordenadas();
	        	        Color corAleatoria = criaCor();
	        	        for(GeoPosition coordenada2: listaCoordenadas){
	        	        	
	        	        	tr.addPonto(coordenada2);
	        	        	tr.setCor(corAleatoria);
	        	        }
	        	        gerenciador.addTracado(tr);
	        	        gerenciador.setPontos(lstPoints);
	        	        
	        	        this.repaint();
	        			
	        			break;
	        		}
	        	}
	        }
        }
    }

    private void consulta4(java.awt.event.ActionEvent evt) {
    	listaLinhasSelecionadas.clear();
    	gerenciador.clear();
    	Parada[] paradas = new Parada[listaParadasSelecionadas.size()];
    	listaParadasSelecionadas.copyInto(paradas);

    	if(listaParadasSelecionadas.size() == 0){
    		JOptionPane.showMessageDialog(null,
    			    "Voc� precisa selecionar uma parada no mapa primeiro.",
    			    "Aviso",
    			    JOptionPane.WARNING_MESSAGE);
    		return;
    	}
	    List<MyWaypoint> lstPoints = new ArrayList<>();
	    for(String linhaKey: dicLinhas.keySet()){
	    	Linha linha = dicLinhas.get(linhaKey);
	    	boolean possuiParadas = true;
	    	for(Parada parada: paradas){
	    		if(!linha.getParadas().containsKey(parada.getIdParada())){
	    			possuiParadas = false;
	    			break;
	    		}
	    	}
	    	if(possuiParadas == true){
	    		
	    		ArrayList<GeoPosition> listaCoordenadas = linha.getCoordenadas();
        		Color corAleatoria = criaCor();
        		Tracado tr = new Tracado();
        		for(GeoPosition coordenada: listaCoordenadas){
        			tr.addPonto(coordenada);
    	        	tr.setCor(corAleatoria);
        		}
        		listaLinhasSelecionadas.addElement(linha);        		
    	        gerenciador.addTracado(tr);
    	        this.repaint();
	    	}
	    }
	    for(Parada parada: paradas){
	    	lstPoints.add(new MyWaypoint(Color.BLACK, parada.getIdParada(), parada.getCoordenadas()));
	    }
	    gerenciador.setPontos(lstPoints);
	    this.repaint();
    }
     
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
    		// Botão 3: seleciona localização
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
	
    public Color criaCor(){
	    Random rand = new Random();
	 
	    float r = rand.nextFloat() / 2f + 0.5f;
	    float g = rand.nextFloat() / 2f + 0.5f;
	    float b = rand.nextFloat() / 2f + 0.5f;	   	    
	 
	    Color cor = new Color(r, g, b);
		return cor;
	    
		}
 	}

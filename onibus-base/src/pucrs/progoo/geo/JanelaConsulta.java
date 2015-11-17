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
import java.awt.Component;
import javax.swing.SwingConstants;

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
    	
        //inicialização das linhas de ônibus
    	try{
    		dicLinhas = Leitura.geraLinhas();
    	} catch(IOException e){
    		System.err.println("Erro na geração das linhas de ônibus");
    	}
    	
    	//inicialização das paradas de ônibus
    	try{
    		dicParadas = Leitura.preparaParada();
    	} catch(IOException e){
    		System.err.println("Erro na geração das paradas de ônibus");
    	}
    	
    	//inicialização dos listModels
    	listaLinhas = new DefaultListModel<>();
    	for(String key: dicLinhas.keySet()){
    		Linha linha = dicLinhas.get(key);
    		listaLinhas.addElement(linha);
    	}
    	listaLinhasSelecionadas = new DefaultListModel<>();
    	listaParadasSelecionadas = new DefaultListModel<>();
    	
    	///////////////////////////////////////////////////////
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
        ///////////////////////////////////////////////////////
        
        //botões        
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
        
        
        //Criação da lista de linhas
        panel = new JPanel();
        panel.addComponentListener(new ComponentAdapter() {        	        	
        });
        getContentPane().add(panel, BorderLayout.WEST);
        panel.setLayout(new BorderLayout(0, 0));
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
        
        JLabel lblNewLabel_2 = new JLabel("Lista de Linhas de \u00D4nibus de Porto Alegre:");
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblNewLabel_2, BorderLayout.NORTH);
        
        //tabela de resultados
        panelResultados = new JPanel();
        panelResultados.addComponentListener(new ComponentAdapter() {        	        	
        });
        getContentPane().add(panelResultados, BorderLayout.EAST);
        panelResultados.setVisible(true);
        panelResultados.setLayout(new BoxLayout(panelResultados, BoxLayout.Y_AXIS));
        
        //////
        
        JPanel panel_1 = new JPanel();
        panel_1.setAlignmentY(Component.TOP_ALIGNMENT);
        panel_1.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panelResultados.add(panel_1);
        panel_1.setLayout(new BorderLayout(0, 0));
        JScrollPane scrollPane_1 = new JScrollPane();
        panel_1.add(scrollPane_1);
        listLinhasSelecionadas = new JList<>();
        listLinhasSelecionadas.setValueIsAdjusting(true);
        listLinhasSelecionadas.addComponentListener(new ComponentAdapter() {        	
        });        
        listLinhasSelecionadas.setBorder(null);
        scrollPane_1.setViewportView(listLinhasSelecionadas);
        
        this.listLinhasSelecionadas.setModel(listaLinhasSelecionadas);
        
        JLabel lblNewLabel = new JLabel("Lista de Resultados de Consultas:");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel_1.add(lblNewLabel, BorderLayout.NORTH);
        
        //////
        
        JPanel panel_2 = new JPanel();
        panel_2.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panel_2.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        panelResultados.add(panel_2);
        panel_2.setLayout(new BorderLayout(0, 0));
        
        JScrollPane scrollPane_2 = new JScrollPane();
        panel_2.add(scrollPane_2);
        listParadasSelecionadas = new JList<>();
        listParadasSelecionadas.setValueIsAdjusting(true);
        listParadasSelecionadas.addComponentListener(new ComponentAdapter() {        	
        });        
        listParadasSelecionadas.setBorder(null);
        scrollPane_2.setViewportView(listParadasSelecionadas);
        
        this.listParadasSelecionadas.setModel(listaParadasSelecionadas);
        
        JLabel lblNewLabel_1 = new JLabel("Lista de Paradas Selecionadas:");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        panel_2.add(lblNewLabel_1, BorderLayout.NORTH);
        
        
        
        this.setSize(1240,720);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    }

    //método para adição de uma parada na lista de paradas selecionadas
    private void addParada(java.awt.event.ActionEvent evt) {
    	GeoPosition pos = gerenciador.getPosicao();     

        //Laço utilizado para identificar a parada clicada no mapa
        Parada parada = null;
        for(String paradaKey: dicParadas.keySet()){
        	Parada aux = dicParadas.get(paradaKey);
        	if(AlgoritmosGeograficos.calcDistancia(pos, aux.getCoordenadas()) <= 0.01){
        		parada = aux;
        		if(listaParadasSelecionadas.contains(parada)){
        			JOptionPane.showMessageDialog(null, "Este elemento já foi selecionado.", "Aviso", JOptionPane.WARNING_MESSAGE);
        		}
        		else	listaParadasSelecionadas.addElement(parada);
        		break;
        	}
        }
    }

    //método para remoção de uma parada da lista de paradas selecionadas
    private void removeParada(java.awt.event.ActionEvent evt) {
    	Parada parada = listParadasSelecionadas.getSelectedValue();
    	listaParadasSelecionadas.removeElement(parada);
    }
    
    //método para exibit todas as paradas de ônibus no mapa
    private void exibirParadas(java.awt.event.ActionEvent evt) {	
        List<MyWaypoint> lstPoints = new ArrayList<>();
        
        for(String idParada: dicParadas.keySet()){
        	Parada parada = dicParadas.get(idParada);
        	lstPoints.add(new MyWaypoint(Color.BLACK, "", parada.getCoordenadas()));
        }
        
        gerenciador.setPontos(lstPoints);
        this.repaint();
        }
    
    //pinta uma linha de ônibus selecionada em uma lista
    private void consulta1(java.awt.event.ActionEvent evt) {	
        List<MyWaypoint> lstPoints = new ArrayList<>();

        Linha linha = listLinhas.getSelectedValue();
        Map<String, Parada> dicParadas = linha.getParadas();
        
        //percorre todas as paradas de uma determinada linha e cria waypoints para elas no mapa
        for(String idParada: dicParadas.keySet()){
        	Parada parada = dicParadas.get(idParada);
        	lstPoints.add(new MyWaypoint(Color.BLACK, "", parada.getCoordenadas()));
        }
        
        gerenciador.setPontos(lstPoints);
        
        Tracado tr = new Tracado();
        ArrayList<GeoPosition> listaCoordenadas = linha.getCoordenadas();
        //pinta o trajeto de uma determinada linha de ônibus
        for(GeoPosition coordenada: listaCoordenadas){
        	tr.addPonto(coordenada);
        	tr.setCor(Color.CYAN);
        }
      
        gerenciador.addTracado(tr);
        this.repaint();
        }
     
    //pinta as linhas que passam em uma parada selecionada no mapa
    private void consulta2(java.awt.event.ActionEvent evt) {
    	listaLinhasSelecionadas.clear();
    	gerenciador.clear();

    	GeoPosition pos = gerenciador.getPosicao();     

        List<MyWaypoint> lstPoints = new ArrayList<>();
        
        //Laço utilizado para identificar a parada clicada no mapa
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
    			    "Uma parada não foi selecionada ou não pode ser encontrada.",
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
    	        gerenciador.addTracado(tr);
    	        gerenciador.setPontos(lstPoints);
    	        
    	        this.repaint();
	        }
        }
    }
    
    //pinta as linhas que passam próximas a um ponto selecionada no mapa
    private void consulta3(java.awt.event.ActionEvent evt) {
    	listaLinhasSelecionadas.clear();
    	gerenciador.clear();

    	GeoPosition pos = gerenciador.getPosicao();     
    	
    	if(pos == null){
    		JOptionPane.showMessageDialog(null,
    			    "Você precisa selecionar um ponto do mapa primeiro.",
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
    
    //pinta as linhas que passam em um conjunto de paradas simultaneamente
    private void consulta4(java.awt.event.ActionEvent evt) {
    	listaLinhasSelecionadas.clear();
    	gerenciador.clear();
    	
    	Parada[] paradas = new Parada[listaParadasSelecionadas.size()];
    	listaParadasSelecionadas.copyInto(paradas);

    	if(listaParadasSelecionadas.size() == 0){
    		JOptionPane.showMessageDialog(null,
    			    "Você precisa selecionar uma parada no mapa primeiro.",
    			    "Aviso",
    			    JOptionPane.WARNING_MESSAGE);
    		return;
    	}
    	
	    List<MyWaypoint> lstPoints = new ArrayList<>();
	    
	    //percorre todas as linhas de ônibus
	    for(String linhaKey: dicLinhas.keySet()){
	    	Linha linha = dicLinhas.get(linhaKey);
	    	boolean possuiParadas = true;
	    	//testa para descobrir se esta linha possui todas as paradas de ônibus selecionadas
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
	    
	    //remarca as paradas selcionadas no mapa
	    for(Parada parada: paradas){
	    	lstPoints.add(new MyWaypoint(Color.BLACK, parada.getIdParada(), parada.getCoordenadas()));
	    }
	    
	    gerenciador.setPontos(lstPoints);
	    this.repaint();
    }
    
    //método para apagar todas as linhas e paradas desenhadas no mapa
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
    		// BotÃ£o 3: seleciona localizaÃ§Ã£o
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
	
    //método para criação de cores aleatórias
    public Color criaCor(){
	    Random rand = new Random();
	 
	    float r = rand.nextFloat() / 2f + 0.5f;
	    float g = rand.nextFloat() / 2f + 0.5f;
	    float b = rand.nextFloat() / 2f + 0.5f;	   	    
	 
	    Color cor = new Color(r, g, b);
		return cor;
	    
		}
 	}

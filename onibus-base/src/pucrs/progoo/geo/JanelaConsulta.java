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
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;

//import pucrs.progoo.Linhas;






import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

import pucrs.progoo.model.Coordenada;
import pucrs.progoo.model.Linha;
import pucrs.progoo.model.Parada;
import pucrs.progoo.reader.*;

/**
 *
 * @author Marcelo Cohen
 */
public class JanelaConsulta extends javax.swing.JFrame {

    private GerenciadorMapa gerenciador;
    private EventosMouse mouse;
    
    private JPanel painelMapa;
    private JPanel painelLateral;
    

    /**
     * Creates new form JanelaConsulta
     */
    public JanelaConsulta() {
    	super();    	
        //initComponents();

        GeoPosition poa = new GeoPosition(-30.05, -51.18);
        gerenciador = new GerenciadorMapa(poa, GerenciadorMapa.FonteImagens.VirtualEarth);
        mouse = new EventosMouse();        		
        gerenciador.getMapKit().getMainMap().addMouseListener(mouse);
        gerenciador.getMapKit().getMainMap().addMouseMotionListener(mouse);       

        painelMapa = new JPanel();
        painelMapa.setLayout(new BorderLayout());
        painelMapa.add(gerenciador.getMapKit(), BorderLayout.CENTER);
                
        getContentPane().add(painelMapa, BorderLayout.CENTER);
        
        painelLateral = new JPanel();
        getContentPane().add(painelLateral, BorderLayout.WEST);
        
        JButton btnNewButton = new JButton("Consulta");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		consulta1(e);
        	}
        });
        painelLateral.add(btnNewButton);
        
        this.setSize(800,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    }

    private void consulta1(java.awt.event.ActionEvent evt) {

        // Para obter um ponto clicado no mapa, usar como segue:
    	GeoPosition pos = gerenciador.getPosicao();     

        // Lista para armazenar o resultado da consulta
        List<MyWaypoint> lstPoints = new ArrayList<>();
        
        // Exemplo de uso:
        try{
	        Map<String, Linha> dicLinhas = Leitura.geraLinhas();
	        
	        Linha linha = dicLinhas.get("128516");
	        Map<String, Parada> dicParadas = linha.getParadas();
	        
	        for(String idParada: dicParadas.keySet()){
	        	Parada parada = dicParadas.get(idParada);
	        	GeoPosition loc = new GeoPosition(parada.getLatitude(), parada.getLongitude());
	        	lstPoints.add(new MyWaypoint(Color.BLACK, parada.getIdParada(), loc));
	        }
	        
	        
	        // Informa o resultado para o gerenciador
	        gerenciador.setPontos(lstPoints);
	        
	        
	        // Exemplo: criando um traçado
	        Tracado tr = new Tracado();
	        
	        ArrayList<Coordenada> listaCoordenadas = linha.getCoordenadas();
	        
	        for(Coordenada coordenada: listaCoordenadas){
	        	GeoPosition loc = new GeoPosition(coordenada.getLatitude(), coordenada.getLongitude());
	        	tr.addPonto(loc);
	        	tr.setCor(Color.CYAN);
	        }
	        
	        
	        // E adicionando o traçado...
	        gerenciador.addTracado(tr);
	        
	        
	        this.repaint();
        }
        catch(IOException e){
        	e.printStackTrace();
        	System.out.println("Erro ao localizar paradas");
        }

    }
    
    private void consulta2(java.awt.event.ActionEvent evt) {

        // Para obter um ponto clicado no mapa, usar como segue:
    	GeoPosition pos = gerenciador.getPosicao();     

        // Lista para armazenar o resultado da consulta
        List<MyWaypoint> lstPoints = new ArrayList<>();
        
        // Exemplo de uso:
        try{
	        Map<String, Linha> dicLinhas = Leitura.geraLinhas();
	        
	        Linha linha = dicLinhas.get("128516");
	        Map<String, Parada> dicParadas = linha.getParadas();
	        
	        for(String idParada: dicParadas.keySet()){
	        	Parada parada = dicParadas.get(idParada);
	        	GeoPosition loc = new GeoPosition(parada.getLatitude(), parada.getLongitude());
	        	lstPoints.add(new MyWaypoint(Color.BLACK, parada.getIdParada(), loc));
	        }
	        
	        
	        // Informa o resultado para o gerenciador
	        gerenciador.setPontos(lstPoints);
	        
	        
	        // Exemplo: criando um traçado
	        Tracado tr = new Tracado();
	        
	        ArrayList<Coordenada> listaCoordenadas = linha.getCoordenadas();
	        
	        for(Coordenada coordenada: listaCoordenadas){
	        	GeoPosition loc = new GeoPosition(coordenada.getLatitude(), coordenada.getLongitude());
	        	tr.addPonto(loc);
	        	tr.setCor(Color.CYAN);
	        }
	        
	        
	        // E adicionando o traçado...
	        gerenciador.addTracado(tr);
	        
	        
	        this.repaint();
        }
        catch(IOException e){
        	e.printStackTrace();
        	System.out.println("Erro ao localizar paradas");
        }

    }
    
    /*private void consultaOriginal(java.awt.event.ActionEvent evt) {

        // Para obter um ponto clicado no mapa, usar como segue:
    	GeoPosition pos = gerenciador.getPosicao();     

        // Lista para armazenar o resultado da consulta
        List<MyWaypoint> lstPoints = new ArrayList<>();
        
        // Exemplo de uso:
        
        GeoPosition loc = new GeoPosition(-30.05, -51.18);  // ex: localização de uma parada
        GeoPosition loc2 = new GeoPosition(-30.08, -51.22); // ex: localização de uma parada
        lstPoints.add(new MyWaypoint(Color.BLUE, "Teste", loc));
        lstPoints.add(new MyWaypoint(Color.BLACK, "Teste 2", loc2));
        
        

        // Informa o resultado para o gerenciador
        gerenciador.setPontos(lstPoints);
        
        // Exemplo: criando um traçado
        Tracado tr = new Tracado();
        // Adicionando as mesmas localizações de antes
        tr.addPonto(loc);
        tr.addPonto(loc2);
        tr.setCor(Color.CYAN);
        // E adicionando o traçado...
        gerenciador.addTracado(tr);
        
        // Outro:
        GeoPosition loc3 = new GeoPosition(-30.02, -51.16);  
        GeoPosition loc4 = new GeoPosition(-30.01, -51.24);
        Tracado tr2 = new Tracado();
        tr2.addPonto(loc3);
        tr2.addPonto(loc4);
        tr2.setCor(Color.MAGENTA);
        // E adicionando o traçado...
        gerenciador.addTracado(tr2);
        
        this.repaint();

    }*/
    
    private class EventosMouse extends MouseAdapter
    {
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
}

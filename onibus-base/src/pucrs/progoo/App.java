/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pucrs.progoo;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jxmapviewer.viewer.GeoPosition;

import pucrs.progoo.geo.JanelaConsulta;

/**
 *
 * @author Marcelo Cohen
 */
public class App {
    public static void main(String[] args) throws IOException{            
        JanelaConsulta janela = new JanelaConsulta();
        janela.setVisible(true);      
    }
}

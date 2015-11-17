package pucrs.progoo.geo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jxmapviewer.JXMapKit;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;
import org.jxmapviewer.viewer.WaypointRenderer;

/**
 * Classe para gerenciar um mapa
 *
 * @author Marcelo Cohen
 */
public class GerenciadorMapa {

    final JXMapKit jXMapKit;
    private WaypointPainter<MyWaypoint> pontosPainter;
    
    private GeoPosition selCentro;
    
    private ArrayList<Tracado> linhas;
   
    public enum FonteImagens {

        OpenStreetMap, VirtualEarth
    };

    /*
     * Cria um gerenciador de mapas, a partir de uma posição e uma fonte de imagens
     * 
     * @param centro centro do mapa
     * @param fonte fonte das imagens (FonteImagens.OpenStreetMap ou FonteImagens.VirtualEarth)
     */
    public GerenciadorMapa(GeoPosition centro, FonteImagens fonte) {
        jXMapKit = new JXMapKit();
        TileFactoryInfo info = null;
        if (fonte == FonteImagens.OpenStreetMap) {
            info = new OSMTileFactoryInfo();            
        } else {
            info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
        }
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        jXMapKit.setTileFactory(tileFactory);

        // Ajustando a opacidade do mapa (50%)
        jXMapKit.getMainMap().setAlpha(0.5f);

        // Ajustando o nível de zoom do mapa
        jXMapKit.setZoom(7);
        // Informando o centro do mapa
        jXMapKit.setAddressLocation(centro);
        // Indicando que não desejamos ver um marcador nessa posição
        jXMapKit.setAddressLocationShown(false);              

        // Criando um objeto para "pintar" os pontos
        pontosPainter = new WaypointPainter<MyWaypoint>();               

        // Criando um objeto para desenhar os pontos
        pontosPainter.setRenderer(new WaypointRenderer<MyWaypoint>() {

            @Override
            public void paintWaypoint(Graphics2D g, JXMapViewer viewer, MyWaypoint wp) {

                // Desenha cada waypoint como um pequeno círculo            	
                Point2D point = viewer.getTileFactory().geoToPixel(wp.getPosition(), viewer.getZoom());
                int x = (int) point.getX();
                int y = (int) point.getY();
                
                // Seta a cor do waypoint                
                g.setColor(wp.getColor());
                g.fill(new Ellipse2D.Float(x - 3, y - 3, 6, 6));
                g.drawString(wp.getLabel(), x+4, y+4);
            }
        });
        
        // Criando um objeto para desenhar o traçado das linhas
        Painter<JXMapViewer> linhasPainter = new Painter<JXMapViewer>() {

			@Override
			public void paint(Graphics2D g, JXMapViewer map, int w, int h) {
				
				for(Tracado tr: linhas) {
					ArrayList<GeoPosition> pontos = tr.getPontos();
					Color cor = tr.getCor();
					int x[] = new int[pontos.size()];
					int y[] = new int[pontos.size()];
					for(int i=0; i<pontos.size(); i++) {
						Point2D point = map.convertGeoPositionToPoint(pontos.get(i));
						x[i] = (int) point.getX();
						y[i] = (int) point.getY();
					}
//					int xPoints[] = { 0, 20, 40, 100, 120 };
//					int yPoints[] = { 0, 20, 40, 100, 120 };
					g.setColor(cor);
					g.setStroke(new BasicStroke(2));
					g.drawPolyline(x, y, x.length);
				}				
			}
        	
        };

        // Criando um objeto para desenhar os elementos de interface
        // (ponto selecionado, etc)
        Painter<JXMapViewer> guiPainter = new Painter<JXMapViewer>() {
            public void paint(Graphics2D g, JXMapViewer map, int w, int h) {            	
            	if(selCentro == null)
            		return;                	
            	Point2D point = map.convertGeoPositionToPoint(selCentro);            	
            	int x = (int) point.getX();
                int y = (int) point.getY();                
            	g.setColor(Color.RED);
            	g.setStroke(new BasicStroke(2));            	            
            	g.draw(new Rectangle2D.Float(x - 6, y - 6, 12, 12));                
            }
        };
        
        // Um CompoundPainter permite combinar vários painters ao mesmo tempo...
        CompoundPainter cp = new CompoundPainter();
        cp.setPainters(pontosPainter, linhasPainter, guiPainter);        

        jXMapKit.getMainMap().setOverlayPainter(cp);       
        
        selCentro = null;        
        linhas = new ArrayList<Tracado>();
    }

    /*
     * Informa a localização de um ponto
     * @param ponto central
     */
    public void setPosicao(GeoPosition sel) {
    	this.selCentro = sel;
    }
    
    /*
     * Retorna a localização de um ponto
     * @returns ponto selecionado
     */
    public GeoPosition getPosicao() {
		return selCentro;
	}    
    
    /*
     * Informa os pontos a serem desenhados (precisa ser chamado a cada vez que mudar)
     * @param lista lista de pontos (objetos MyWaypoint)
     */
    public void setPontos(List<MyWaypoint> lista) {
        // Criando um conjunto de pontos
        Set<MyWaypoint> pontos = new HashSet<MyWaypoint>(lista);
        // Informando o conjunto ao painter
        pontosPainter.setWaypoints(pontos);
    }
    
    /*
     * Adiciona um novo traçado ao mapa (o traçado tem um conjunto de pontos e uma cor)
     */
    public void addTracado(Tracado tr) {
    	linhas.add(tr);
    }
    
    /*
     * Limpa os traçados atuais
     */
    public void clear() {
    	linhas.clear();
    }
    
    /*
     * Retorna o total de traçados
     */
    public int totalTracados() {
    	return linhas.size();
    }    

    /*
     * Retorna a referência ao objeto JXMapKit, para ajuste de parâmetros (se for o caso)
     * @returns referência para objeto JXMapKit em uso
     */
    public JXMapKit getMapKit() {
        return jXMapKit;
    }

}

package main.java.view;

import javafx.embed.swing.SwingNode;
import main.java.model.game.GameState;
import main.java.model.world.Country;
import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.DefaultView;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;
import org.graphstream.ui.view.util.DefaultMouseManager;

import javax.imageio.ImageIO;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class GraphView extends SingleGraph {
    private Viewer viewer;
    private ViewerPipe viewerpipe;
    private ViewPanel viewPanel;

    public GraphView(final String id) {
        super(id);
        // Initialize graph viewer
        this.viewer = new Viewer(this, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        this.viewer.enableAutoLayout();
        this.viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.EXIT);
        // Add ui attributes
        this.addAttribute("ui.quality");
        this.addAttribute("ui.antialias");
        System.setProperty("org.graphstream.ui.renderer",
                "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
    }

    public SwingNode newViewNode() {
        this.viewPanel = this.viewer.addDefaultView(false);

        // Add Viewer Pipe to poll new events
        this.viewerpipe = this.viewer.newViewerPipe();
        this.viewerpipe.addSink(this);
        new Thread(() -> {
            try {
                while (true)
                    this.viewerpipe.blockingPump();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // Disable dragging effect via mouse
        this.viewPanel.setMouseManager(new DefaultMouseManager() {
            @Override public void mouseDragged(MouseEvent mouseEvent) { }
            @Override public void mouseMoved(MouseEvent mouseEvent) { }
        });

        SwingNode node = new SwingNode();
        node.setContent(this.viewPanel);
        return node;
    }

    public void addStyleSheet(String styleSheetPath) {
        this.addAttribute("ui.stylesheet", "url('" + this
                .getClass().getClassLoader().getResource(styleSheetPath) + "')");
    }

    public void setNodeBackground(final String path) throws IOException {
        DefaultView view = (DefaultView) this.viewPanel;
        java.awt.Image bg = ImageIO.read(new File(path));
        view.setBackLayerRenderer((graphics, graph, px2Gu,widthPx, heightPx,
                                   minXGu, minYGu, maxXGu, maxYGu) ->
                graphics.drawImage(bg, (int) minXGu, (int) minYGu, null));
    }

    public void addViewerPipeEventListener(ViewerListener viewerListener) {
        this.viewerpipe.addViewerListener(viewerListener);
    }

    public static GraphView fromGameState(final GameState gameState) {
        GraphView graphView = new GraphView("World Map");
        // Add first player nodes to graph
        for (Country country : gameState.getWorld().getPlayerOne().getConqueredCountries()) {
            Node countryNode = graphView.addNode(String.valueOf(country.getId()));
            countryNode.addAttribute("ui.label",
                    String.valueOf(country.getUnits()) + " (" +
                            String.valueOf(country.getContinent().getId()) + ")");
            countryNode.addAttribute("ui.class", "player1");
        }
        // Add second player nodes to graph
        for (Country country : gameState.getWorld().getPlayerTwo().getConqueredCountries()) {
            Node countryNode = graphView.addNode(String.valueOf(country.getId()));
            countryNode.addAttribute("ui.label",
                    String.valueOf(country.getUnits()) + " (" +
                    String.valueOf(country.getContinent().getId()) + ")");
            countryNode.addAttribute("ui.class", "player2");
        }
        // Add unoccupied nodes
        for (Country country : gameState.getWorld().getCountries()) {
            if (graphView.getNode(String.valueOf(country.getId())) != null)
                continue;
            Node countryNode = graphView.addNode(String.valueOf(country.getId()));
            countryNode.addAttribute("ui.label", String.valueOf(0));
            countryNode.addAttribute("ui.class", "unoccupied");
        }
        // Add edges
        for (Country country : gameState.getWorld().getCountries())
            for (Country neighbor : country.getNeighbours()) {
                String edgeId = String.valueOf(country.getId())
                        + "-" + String.valueOf(neighbor.getId());
                try {
                    graphView.addEdge(edgeId, String.valueOf(country.getId()),
                            String.valueOf(neighbor.getId()));
                } catch (EdgeRejectedException ignored) { }
            }
        return graphView;
    }
}

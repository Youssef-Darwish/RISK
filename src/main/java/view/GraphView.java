package main.java.view;

import javafx.embed.swing.SwingNode;
import main.java.model.game.GameState;
import main.java.model.world.Country;
import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

public class GraphView extends SingleGraph {
    private Viewer viewer;

    public GraphView(final String id) {
        super(id);
        // Initialize graph viewer
        this.viewer = new Viewer(this, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        this.viewer.enableAutoLayout();
        this.viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.EXIT);
        // Add ui attributes
        this.addAttribute("ui.quality");
        this.addAttribute("ui.antialias");
        this.addAttribute("ui.stylesheet", "url('" + this
                .getClass().getClassLoader().getResource("main/resources/view/css/graph.css") + "')");
        System.setProperty("org.graphstream.ui.renderer",
                "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
    }

    public SwingNode getViewNode() {
        ViewPanel viewPanel = this.viewer.addDefaultView(false);
        SwingNode node = new SwingNode();
        node.setContent(viewPanel);
        return node;
    }

    public static GraphView fromGameState(final GameState gameState) {
        GraphView graphView = new GraphView("World Map");
        graphView.getNodeSet().clear();
        // Add first player nodes to graph
        for (Country country : gameState.getWorld().getPlayerOne().getConqueredCountries()) {
            Node countryNode = graphView.addNode(String.valueOf(country.getId()));
            countryNode.addAttribute("ui.label", String.valueOf(country.getUnits()));
            countryNode.addAttribute("ui.class", "player1");
        }
        // Add second player nodes to graph
        for (Country country : gameState.getWorld().getPlayerTwo().getConqueredCountries()) {
            Node countryNode = graphView.addNode(String.valueOf(country.getId()));
            countryNode.addAttribute("ui.label", String.valueOf(country.getUnits()));
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

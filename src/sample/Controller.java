package sample;

import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

public class Controller {
    @FXML
    private StackPane root;

    private Graph graph;

    @FXML
    public void initialize() {
        // Init graph
        this.graph = new SingleGraph("Test");
        this.graph.addAttribute("ui.quality");
        this.graph.addAttribute("ui.antialias");
        this.graph.addAttribute("ui.stylesheet", "url('" + this
                .getClass().getClassLoader().getResource("sample/graph.css") + "')");

        // Init viewer
        System.setProperty("org.graphstream.ui.renderer",
                "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        Viewer viewer = new Viewer(this.graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        ViewPanel view = viewer.addDefaultView(false);
        viewer.enableAutoLayout();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.EXIT);
        SwingNode node = new SwingNode();
        node.setContent(view);
        this.root.getChildren().add(node);

        // Fill graph
        Node n = graph.addNode("n1");
        n.addAttribute("ui.label", "n1");
        n.addAttribute("ui.class", "big");

        Node n2 = graph.addNode("n2");
        n2.addAttribute("ui.label", "n2");
        n2.addAttribute("ui.class", "big");

        Edge e = graph.addEdge("e1", "n1", "n2");

    }
}

package main.java.app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public class Main extends Application {
    private static FXMLLoader loader;

    public static void setLoader(FXMLLoader fxmlLoader) {
        loader = fxmlLoader;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        loader = new FXMLLoader(getClass().getResource("/main/resources/view/mainmenu.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Risk");
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.setResizable(false);

        Platform.setImplicitExit(true);
        primaryStage.setOnCloseRequest((ae) -> {
            Platform.exit();
            System.exit(0);
        });

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

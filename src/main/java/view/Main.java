package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Usuario;

public class Main extends Application {

        String versao = "-EM DESENVOLVIMENTO";


    @Override
        public void start(Stage primaryStage) throws Exception {
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            primaryStage.setTitle("SISTEMA DE PLANO ESTRATÉGICO - SIPES v" + versao);
            primaryStage.setScene(new Scene(root));
            primaryStage.setMaximized(false);
            primaryStage.setResizable(false);
            primaryStage.show();

        }

        public static void main(String[] args) { launch(args);}

}

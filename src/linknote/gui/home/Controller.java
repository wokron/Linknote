package linknote.gui.home;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private VBox pnItems = null;
    @FXML
    private Button btnOverview;

    @FXML
    private Button btnNotes;

    @FXML
    private Button btnFriends;

    @FXML
    private Button btnCates;

    @FXML
    private Button btnTrash;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSignout;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Pane pnlNotes;

    @FXML
    private Pane pnlFriend;

    @FXML
    private Pane pnlCates;

    @FXML
    private Pane pnlTrash;

    @FXML
    private Pane pnlSettings;

    @FXML
    private Pane pnlSignout;

    public Controller() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Node[] nodes = new Node[10];
        for (int i = 0; i < nodes.length; i++) {
            try {

                final int j = i;
                nodes[i] = FXMLLoader.load(getClass().getResource("Item.fxml"));

                //give the items some effect

                nodes[i].setOnMouseEntered(event -> {
                    nodes[j].setStyle("-fx-background-color : #EDFCED");
                });
                nodes[i].setOnMouseExited(event -> {
                    nodes[j].setStyle("-fx-background-color : #F8F8F8");
                });
                pnItems.getChildren().add(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnOverview) {
            pnlOverview.setStyle("-fx-background-color : #F8F8F8");
            pnlOverview.toFront();
        }
        if(actionEvent.getSource()==btnNotes)
        {
            pnlNotes.setStyle("-fx-background-color : #2CBD5F");
            pnlNotes.toFront();
        }
        if (actionEvent.getSource() == btnFriends) {
            pnlFriend.setStyle("-fx-background-color : #F8F8F8");
            pnlFriend.toFront();
        }
        if (actionEvent.getSource() == btnCates) {
            pnlCates.setStyle("-fx-background-color : #F8F8F8");
            pnlCates.toFront();
        }
        if(actionEvent.getSource()==btnTrash)
        {
            pnlTrash.setStyle("-fx-background-color : #F8F8F8");
            pnlTrash.toFront();
        }
        if(actionEvent.getSource()==btnSettings)
        {
            pnlSettings.setStyle("-fx-background-color : #F8F8F8");
            pnlSettings.toFront();
        }
        if(actionEvent.getSource()==btnSignout)
        {
            System.exit(0);
        }
    }


}

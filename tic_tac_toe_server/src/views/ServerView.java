package views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.util.Callback;
import common.Player;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import tic_tac_toe_server.Tic_tac_toe_server;

public class ServerView extends AnchorPane {

    protected final ImageView imageView;
    protected final ImageView imageView0;
    protected final Label label;
    protected final Label label0;
    protected final Label stateLabel;
    protected final Label serverStateLabel;
    protected final Button serverButton;
    protected final Label label1;
    protected final Label playerNumbers;
    protected final Label label3;
    protected final Line line;
    protected Tic_tac_toe_server conroller;

    public ServerView(Tic_tac_toe_server conroller) {

        this.conroller = conroller;

        imageView = new ImageView();
        imageView0 = new ImageView();
        label = new Label();
        label0 = new Label();
        stateLabel = new Label();
        serverStateLabel = new Label();
        serverButton = new Button();
        label1 = new Label();
        playerNumbers = new Label();
        label3 = new Label();
        line = new Line();

        setId("AnchorPane");
        setPrefHeight(400.0);
        setPrefWidth(500.0);
        setStyle("-fx-background-color: #2c3e50;");

        imageView.setFitHeight(296.0);
        imageView.setFitWidth(304.0);
        imageView.setLayoutX(-1.0);
        imageView.setLayoutY(43.0);
        imageView.setOpacity(0.13);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);

        imageView.setImage(new Image(getClass().getResourceAsStream("/images/background.png")));

        displayPlayerList();

        imageView0.setFitHeight(224.0);
        imageView0.setFitWidth(240.0);
        imageView0.setLayoutX(283.0);
        imageView0.setLayoutY(76.0);
        imageView0.setOpacity(0.13);
        imageView0.setPickOnBounds(true);
        imageView0.setPreserveRatio(true);
        imageView0.setImage(new Image(getClass().getResourceAsStream("/images/background.png")));

        label.setLayoutX(66.0);
        label.setLayoutY(70.0);
        label.setText("Tic Tac Toe");
        label.setTextFill(javafx.scene.paint.Color.valueOf("#eecf56"));
        label.setFont(new Font(32.0));

        label0.setLayoutX(95.0);
        label0.setLayoutY(120.0);
        label0.setText("Server");
        label0.setTextFill(javafx.scene.paint.Color.valueOf("#eecf56"));
        label0.setFont(new Font("System Bold", 32.0));
        
        stateLabel.setLayoutX(80.0);
        stateLabel.setLayoutY(170.0);
        stateLabel.setText("State : ");
        stateLabel.setTextFill(javafx.scene.paint.Color.valueOf("#eecf56"));
        stateLabel.setFont(new Font("System Bold", 20.0));
        
        serverStateLabel.setLayoutX(150.0);
        serverStateLabel.setLayoutY(170.0);
        serverStateLabel.setText("active");
        serverStateLabel.setTextFill(javafx.scene.paint.Color.valueOf("#00ff00"));
        serverStateLabel.setFont(new Font("System Bold", 20.0));

        serverButton.setLayoutX(112.0);
        serverButton.setLayoutY(210.0);
        serverButton.setPrefHeight(40.0);
        serverButton.setPrefWidth(70.0);
        serverButton.setStyle("-fx-background-color: #eecf56;");
        serverButton.setText("off");
        serverButton.setFont(new Font("System Bold", 16.0));
        serverButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (conroller.serverIsOpen()) {
                    serverButton.setText("on");
                    conroller.closeServer();
        serverStateLabel.setText("Inactive");
        serverStateLabel.setTextFill(javafx.scene.paint.Color.valueOf("#ff0000"));
                } else {
                    serverButton.setText("off");
                    conroller.openServer();
        serverStateLabel.setText("Active");
        serverStateLabel.setTextFill(javafx.scene.paint.Color.valueOf("#00ff00"));
                }
            }
        });

        label1.setLayoutX(85.0);
        label1.setLayoutY(313.0);
        label1.setText("we are serve for ");
        label1.setTextFill(javafx.scene.paint.Color.valueOf("#eecf56"));
        label1.setFont(new Font(17.0));

        playerNumbers.setLayoutX(105.0);
        playerNumbers.setLayoutY(346.0);
        playerNumbers.setText(conroller.playerCount() + "");
        playerNumbers.setTextFill(javafx.scene.paint.Color.valueOf("#eecf56"));
        playerNumbers.setFont(new Font(17.0));

        label3.setLayoutX(129.0);
        label3.setLayoutY(345.0);
        label3.setText("players");
        label3.setTextFill(javafx.scene.paint.Color.valueOf("#eecf56"));
        label3.setFont(new Font(17.0));

        line.setEndY(230.0);
        line.setFill(javafx.scene.paint.Color.valueOf("#edcd58"));
        line.setLayoutX(280.0);
        line.setLayoutY(168.0);
        line.setStartY(-170.0);
        line.setStroke(javafx.scene.paint.Color.valueOf("#eecf56"));
        line.setStrokeWidth(3.0);

        getChildren().add(imageView);
        getChildren().add(imageView0);
        getChildren().add(label);
        getChildren().add(label0);
        getChildren().add(serverStateLabel);
        getChildren().add(stateLabel);
        getChildren().add(serverButton);
        getChildren().add(label1);
        getChildren().add(playerNumbers);
        getChildren().add(label3);
        getChildren().add(line);

    }

    public void displayPlayerList() {

        Platform.runLater(()
                -> {
            ListView PlayerListView;
            ObservableList<Player> list = FXCollections.observableArrayList(conroller.getPlayers());
            PlayerListView = new ListView(list);
            PlayerListView.setCellFactory(new Callback<ListView<Player>, ListCell<Player>>() {
                @Override
                public ListCell<Player> call(ListView<Player> param) {
                    return new PlayerCell();
                }
            });
            PlayerListView.setLayoutX(280.0);
            PlayerListView.setPrefHeight(400.0);
            PlayerListView.setPrefWidth(230.0);
            PlayerListView.setStyle("-fx-background-color: #000000;");
            PlayerListView.setCenterShape(true);

            getChildren().add(PlayerListView);
        });
    }

    static class PlayerCell extends ListCell<Player> {

        HBox hbox = new HBox();
        Label username = new Label("(empty)");
        Label points = new Label("(empty)");
        ImageView active = new ImageView();
        Pane pane = new Pane();

        public PlayerCell() {
            super();
            hbox.getChildren().addAll(active, username, pane, points);
            HBox.setHgrow(pane, Priority.ALWAYS);
        }

        @Override
        public void updateItem(Player player, boolean empty) {
            super.updateItem(player, empty);

            if (player != null) {
                if (player.isIsActive()) {
                    active.setImage(new Image(getClass().getResourceAsStream("/images/dot.png")));

                } else {
                    active.setImage(new Image(getClass().getResourceAsStream("/images/dotB.png")));
                }
                active.setFitWidth(7.0);
                active.setFitHeight(7.0);
                double activeY = hbox.getHeight() / 2 + 5;
                HBox.setMargin(active, new Insets(activeY, 2.0, 2.0, 2.0));
                hbox.setSpacing(10.0);
                points.setText(player.getPoints() + "\t");
                username.setText(player.getUsername());
                setGraphic(hbox);
            }
        }
    }
}

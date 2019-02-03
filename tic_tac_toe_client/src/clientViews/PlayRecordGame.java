package clientViews;

import common.Game;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;
import tic_tac_toe_client.Tic_tac_toe_client;

public class PlayRecordGame extends AnchorPane {

    protected final VBox vBox;
    protected final HBox hBox;
    protected final HBox hBox0;
    protected final ImageView imageView;
    protected final HBox hBox1;
    protected final ImageView imageView0;
    protected final Label usernameLabel;
    protected final Label vsLabel;
    protected final Label vsUsernameLabel;
    protected final ImageView logoutImageView;
    protected final HBox hBox2;
    protected final HBox gridViewBox;
    protected GridPane gridPane;
    protected ColumnConstraints columnConstraints;
    protected ColumnConstraints columnConstraints0;
    protected ColumnConstraints columnConstraints1;
    protected RowConstraints rowConstraints;
    protected RowConstraints rowConstraints0;
    protected RowConstraints rowConstraints1;
    protected ImageView imageView2;
    protected ImageView imageView3;
    protected ImageView imageView4;
    protected ImageView imageView5;
    protected ImageView imageView6;
    protected ImageView imageView7;
    protected ImageView imageView8;
    protected ImageView imageView9;
    protected ImageView logoutImageView0;
    ListView listView;
    Tic_tac_toe_client controller;
    boolean isRecordPlaying = false;

    public PlayRecordGame(Tic_tac_toe_client controller) {

        this.controller = controller;
        vBox = new VBox();
        hBox = new HBox();
        hBox0 = new HBox();
        imageView = new ImageView();
        hBox1 = new HBox();
        imageView0 = new ImageView();
        usernameLabel = new Label();
        vsLabel = new Label();
        vsUsernameLabel = new Label();
        logoutImageView = new ImageView();
        hBox2 = new HBox();
        gridViewBox = new HBox();

        setId("AnchorPane");
        setPrefHeight(500.0);
        setPrefWidth(900.0);
        setStyle("-fx-background-color: #2c3e50;");

        AnchorPane.setBottomAnchor(vBox, 20.0);
        AnchorPane.setLeftAnchor(vBox, 20.0);
        AnchorPane.setRightAnchor(vBox, 20.0);
        AnchorPane.setTopAnchor(vBox, 20.0);
        vBox.setLayoutX(168.0);
        vBox.setLayoutY(37.0);
        vBox.setPrefHeight(200.0);
        vBox.setPrefWidth(100.0);

        hBox.setPrefHeight(35.0);
        hBox.setPrefWidth(860.0);
        hBox.setSpacing(300.0);

        hBox0.setPrefHeight(35.0);
        hBox0.setPrefWidth(500.0);
        hBox0.setSpacing(10.0);

        imageView.setFitHeight(30.0);
        imageView.setFitWidth(35.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getResourceAsStream("/images/left-arrow.png")));
        imageView.setOnMouseClicked((event) -> controller.openMultiPlayerScreen());

        hBox1.setSpacing(6.0);

        imageView0.setFitHeight(30.0);
        imageView0.setFitWidth(35.0);
        imageView0.setPickOnBounds(true);
        imageView0.setPreserveRatio(true);
        imageView0.setImage(new Image(getClass().getResourceAsStream("/images/user.png")));

        usernameLabel.setText(controller.getPlayer().getUsername());
        usernameLabel.setTextFill(javafx.scene.paint.Color.valueOf("#eecf56"));
        usernameLabel.setFont(new Font(20.0));

        vsLabel.setText("\tvs\t");
        vsLabel.setTextFill(javafx.scene.paint.Color.valueOf("#eecf56"));
        vsLabel.setFont(new Font(20.0));
        vsLabel.setVisible(false);

        vsUsernameLabel.setText("");
        vsUsernameLabel.setTextFill(javafx.scene.paint.Color.valueOf("#eecf56"));
        vsUsernameLabel.setFont(new Font(20.0));

        logoutImageView.setFitHeight(30.0);
        logoutImageView.setFitWidth(35.0);
        logoutImageView.setPickOnBounds(true);
        logoutImageView.setPreserveRatio(true);
        logoutImageView.setImage(new Image(getClass().getResourceAsStream("/images/logout.png")));
        logoutImageView.setOnMouseClicked((event) -> controller.logout());

        hBox2.setAlignment(javafx.geometry.Pos.CENTER);

        hBox2.setPrefHeight(428.0);
        hBox2.setPrefWidth(860.0);
        hBox2.setSpacing(60.0);

        gridViewBox.setPrefHeight(420.0);
        gridViewBox.setPrefWidth(420.0);

        createGridView();
        displayPlayerList();
        listView.setPrefHeight(200.0);
        listView.setPrefWidth(200.0);

        hBox0.getChildren().add(imageView);
        hBox1.getChildren().add(imageView0);
        hBox1.getChildren().add(usernameLabel);
        hBox1.getChildren().add(vsLabel);
        hBox1.getChildren().add(vsUsernameLabel);
        hBox0.getChildren().add(hBox1);
        hBox.getChildren().add(hBox0);
        hBox.getChildren().add(logoutImageView);
        vBox.getChildren().add(hBox);
        hBox2.getChildren().add(gridViewBox);
        hBox2.getChildren().add(listView);
        vBox.getChildren().add(hBox2);
        getChildren().add(vBox);

    }

    class UserNameCell extends ListCell<String> {

        HBox hbox = new HBox();
        Label usernameLabel = new Label("(empty)");

        public UserNameCell() {
            super();
            hbox.getChildren().addAll(usernameLabel);
            HBox.setHgrow(usernameLabel, Priority.ALWAYS);
        }

        @Override
        public void updateItem(String username, boolean empty) {
            super.updateItem(username, empty);
            usernameLabel.setText(username);
            setGraphic(hbox);
        }
    }

    public void displayPlayerList() {
        List<String> activePlayer = controller.getRecordedPlayers();
        if (activePlayer.size() > 0) {
            ObservableList<String> list = FXCollections.observableArrayList(activePlayer);
            listView = new ListView(list);
            listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
                @Override
                public ListCell<String> call(ListView<String> param) {
                    return new UserNameCell();
                }
            });
            listView.setOnMouseClicked((MouseEvent event) -> {
                onItemClick((String) listView.getSelectionModel().getSelectedItem());
            });
        } else {
            listView = new ListView();
        }
    }

    public void drawCell(int rowIndex, int columnIndex, char sumbol) {
        ImageView imageView0 = new ImageView();
        imageView0.setFitHeight(100.0);
        imageView0.setFitWidth(100.0);
        imageView0.setPickOnBounds(true);
        imageView0.setPreserveRatio(true);
        GridPane.setMargin(imageView0, new Insets(10.0, 10.0, 10.0, 20.0));
        gridPane.add(imageView0, columnIndex, rowIndex);
        if (sumbol == 'x') {
            imageView0.setImage(new Image(getClass().getResourceAsStream("/images/x.png")));
        } else {
            imageView0.setImage(new Image(getClass().getResourceAsStream("/images/o.png")));
        }
    }

    public void createGridView() {
        if (gridPane != null) {
            gridViewBox.getChildren().remove(gridPane);
        }
        gridPane = new GridPane();
        columnConstraints = new ColumnConstraints();
        columnConstraints0 = new ColumnConstraints();
        columnConstraints1 = new ColumnConstraints();
        rowConstraints = new RowConstraints();
        rowConstraints0 = new RowConstraints();
        rowConstraints1 = new RowConstraints();
        imageView2 = new ImageView();
        imageView3 = new ImageView();
        imageView4 = new ImageView();
        imageView5 = new ImageView();
        imageView6 = new ImageView();
        imageView7 = new ImageView();
        imageView8 = new ImageView();
        imageView9 = new ImageView();
        logoutImageView0 = new ImageView();

        AnchorPane.setBottomAnchor(gridPane, 23.0);
        AnchorPane.setLeftAnchor(gridPane, 147.0);
        AnchorPane.setRightAnchor(gridPane, 333.0);
        AnchorPane.setTopAnchor(gridPane, 56.0);
        gridPane.setAlignment(javafx.geometry.Pos.CENTER);

        gridPane.setGridLinesVisible(true);
        gridPane.setLayoutX(147.0);
        gridPane.setLayoutY(66.0);
        gridPane.setMaxHeight(USE_PREF_SIZE);

        gridPane.setMaxWidth(USE_PREF_SIZE);

        gridPane.setMinHeight(USE_PREF_SIZE);

        gridPane.setMinWidth(USE_PREF_SIZE);

        gridPane.setPrefHeight(420.0);
        gridPane.setPrefWidth(420.0);
        gridPane.setStyle("-fx-background-color: #eecf56");

        columnConstraints.setHgrow(javafx.scene.layout.Priority.SOMETIMES);

        columnConstraints.setMaxWidth(174.0);
        columnConstraints.setMinWidth(10.0);
        columnConstraints.setPrefWidth(140.0);

        columnConstraints0.setHgrow(javafx.scene.layout.Priority.SOMETIMES);

        columnConstraints0.setMaxWidth(228.0);
        columnConstraints0.setMinWidth(10.0);
        columnConstraints0.setPrefWidth(140.0);

        columnConstraints1.setHgrow(javafx.scene.layout.Priority.SOMETIMES);

        columnConstraints1.setMaxWidth(219.0);
        columnConstraints1.setMinWidth(10.0);
        columnConstraints1.setPrefWidth(140.0);

        rowConstraints.setMinHeight(10.0);
        rowConstraints.setPrefHeight(140.0);
        rowConstraints.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints0.setMinHeight(10.0);
        rowConstraints0.setPrefHeight(140.0);
        rowConstraints0.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints1.setMinHeight(10.0);
        rowConstraints1.setPrefHeight(140.0);
        rowConstraints1.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        imageView2.setFitHeight(100.0);
        imageView2.setFitWidth(100.0);
        imageView2.setPickOnBounds(true);
        imageView2.setPreserveRatio(true);
        GridPane.setMargin(imageView2, new Insets(10.0, 10.0, 10.0, 20.0));

        GridPane.setColumnIndex(imageView3, 1);
        imageView3.setFitHeight(100.0);
        imageView3.setFitWidth(100.0);
        imageView3.setPickOnBounds(true);
        imageView3.setPreserveRatio(true);
        GridPane.setMargin(imageView3, new Insets(10.0, 10.0, 10.0, 20.0));

        GridPane.setColumnIndex(imageView4, 2);
        imageView4.setFitHeight(100.0);
        imageView4.setFitWidth(100.0);
        imageView4.setPickOnBounds(true);
        imageView4.setPreserveRatio(true);
        GridPane.setMargin(imageView4, new Insets(10.0, 10.0, 10.0, 20.0));

        GridPane.setRowIndex(imageView5, 1);
        imageView5.setFitHeight(100.0);
        imageView5.setFitWidth(100.0);
        imageView5.setPickOnBounds(true);
        imageView5.setPreserveRatio(true);
        GridPane.setMargin(imageView5, new Insets(10.0, 10.0, 10.0, 20.0));

        GridPane.setColumnIndex(imageView6, 1);
        GridPane.setRowIndex(imageView6, 1);
        imageView6.setFitHeight(100.0);
        imageView6.setFitWidth(100.0);
        imageView6.setPickOnBounds(true);
        imageView6.setPreserveRatio(true);
        GridPane.setMargin(imageView6, new Insets(10.0, 10.0, 10.0, 20.0));

        GridPane.setColumnIndex(imageView7, 2);
        GridPane.setRowIndex(imageView7, 1);
        imageView7.setFitHeight(100.0);
        imageView7.setFitWidth(100.0);
        imageView7.setPickOnBounds(true);
        imageView7.setPreserveRatio(true);
        GridPane.setMargin(imageView7, new Insets(10.0, 10.0, 10.0, 20.0));

        GridPane.setRowIndex(imageView8, 2);
        imageView8.setFitHeight(100.0);
        imageView8.setFitWidth(100.0);
        imageView8.setPickOnBounds(true);
        imageView8.setPreserveRatio(true);
        GridPane.setMargin(imageView8, new Insets(10.0, 10.0, 10.0, 20.0));

        GridPane.setColumnIndex(imageView9, 1);
        GridPane.setRowIndex(imageView9, 2);
        imageView9.setFitHeight(100.0);
        imageView9.setFitWidth(100.0);
        imageView9.setPickOnBounds(true);
        imageView9.setPreserveRatio(true);
        GridPane.setMargin(imageView9, new Insets(10.0, 10.0, 10.0, 20.0));

        GridPane.setColumnIndex(logoutImageView0, 2);
        GridPane.setRowIndex(logoutImageView0, 2);
        logoutImageView0.setFitHeight(100.0);
        logoutImageView0.setFitWidth(100.0);
        logoutImageView0.setPickOnBounds(true);
        logoutImageView0.setPreserveRatio(true);
        GridPane.setMargin(logoutImageView0, new Insets(10.0, 10.0, 10.0, 20.0));

        gridPane.getColumnConstraints().add(columnConstraints);
        gridPane.getColumnConstraints().add(columnConstraints0);
        gridPane.getColumnConstraints().add(columnConstraints1);
        gridPane.getRowConstraints().add(rowConstraints);
        gridPane.getRowConstraints().add(rowConstraints0);
        gridPane.getRowConstraints().add(rowConstraints1);
        gridPane.getChildren().add(imageView2);
        gridPane.getChildren().add(imageView3);
        gridPane.getChildren().add(imageView4);
        gridPane.getChildren().add(imageView5);
        gridPane.getChildren().add(imageView6);
        gridPane.getChildren().add(imageView7);
        gridPane.getChildren().add(imageView8);
        gridPane.getChildren().add(imageView9);
        gridPane.getChildren().add(logoutImageView0);
        gridViewBox.getChildren().add(gridPane);
    }

    private void onItemClick(String selectedItem) {
        if (selectedItem != null) {
            isRecordPlaying = true;
            vsLabel.setVisible(true);
            vsUsernameLabel.setText(selectedItem);
            Game game = controller.getGameRecord(selectedItem);
            createGridView();
            listView.setDisable(true);
            new AnimationTimer() {
                int i = 0;
                long lastUpdate = 0;

                @Override
                public void handle(long now) {
                    if (now - lastUpdate >= 700_000_000) {
                        logoutImageView.setDisable(true);
                        imageView.setDisable(true);
                        drawCell(
                                game.getGameStates().get(i).getRowPosition(),
                                game.getGameStates().get(i).getColPosition(),
                                game.getGameStates().get(i).getSymbol());
                        i++;
                        if (i >= game.getGameStates().size()) {
                            stop();
                        }
                        lastUpdate = now;
                    }
                }

                @Override
                public void stop() {
                    super.stop();
                    if (game.getResult().equalsIgnoreCase("Draw")) {
                        Platform.runLater(()
                                -> controller.makeAlert("The result",
                                        "the result was " + game.getResult()));
                    } else {
                        Platform.runLater(()
                                -> controller.makeAlert("The result",
                                        "The winner was " + game.getResult()));
                    }
                    listView.setDisable(false);
                    vsLabel.setVisible(false);
                    vsUsernameLabel.setText("");
                    logoutImageView.setDisable(false);
                    imageView.setDisable(false);
                }
            }.start();
        }
    }
}

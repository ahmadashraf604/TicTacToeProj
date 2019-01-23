package clientViews;

import common.Player;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import static javafx.scene.layout.GridPane.getColumnIndex;
import static javafx.scene.layout.GridPane.getRowIndex;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import tic_tac_toe_client.Tic_tac_toe_client;

public class MultiPlayerScreen extends AnchorPane {

    protected final VBox vBox;
    protected final HBox hBox;
    protected final HBox titleInfoHBox;
    protected final HBox ProfileHBox;
    protected final Label usernameLabel;
    protected final Label otherPlayerName;
    protected final HBox scoreHBox;
    protected final Label scoreLabel;
    protected final Label myScoreValueLabel;
    protected final Group group;
    protected final ImageView profileImageView;
    protected final ImageView PlayRecordImageView;
    protected final ImageView recordImageView;
    protected final Group group0;
    protected final ImageView logoutImageView;
    protected final ImageView aboutImageView;
    protected final ImageView refreshImageView;
    protected final HBox hBoxBody;
    protected final HBox hBoxGridPane;
    protected final HBox hBoxListView;
    protected GridPane gridPane;
    protected ColumnConstraints columnConstraints;
    protected ColumnConstraints columnConstraints0;
    protected ColumnConstraints columnConstraints1;
    protected RowConstraints rowConstraints;
    protected RowConstraints rowConstraints0;
    protected RowConstraints rowConstraints1;
    protected ImageView gridImageView1;
    protected ImageView gridImageView2;
    protected ImageView gridImageView3;
    protected ImageView gridImageView4;
    protected ImageView gridImageView5;
    protected ImageView gridImageView6;
    protected ImageView gridImageView7;
    protected ImageView gridImageView8;
    protected ImageView gridImageView9;
    protected final VBox chatViewVBox;
    protected final VBox vBox1;
    protected final HBox hBox3;
    protected final Text receiverUsername;
    protected final TextArea textArea;
    protected final HBox chatHBox;
    protected final TextField sendMsgTextField;
    protected final ImageView sendMsgImageView;
    protected ImageView backgroundImageView;
    protected ListView playersListView;

    Stage stage;
    Tic_tac_toe_client controller;
    int rowIndex = 0;
    int columnIndex = 0;
    char cell[][] = new char[3][3];
    boolean isInvitationBtnClicked = false;

    public MultiPlayerScreen(Stage stage, Tic_tac_toe_client controller) {

        this.controller = controller;
        this.stage = stage;

        vBox = new VBox();
        hBox = new HBox();
        titleInfoHBox = new HBox();
        ProfileHBox = new HBox();
        usernameLabel = new Label();
        otherPlayerName = new Label();
        scoreHBox = new HBox();
        scoreLabel = new Label();
        myScoreValueLabel = new Label();
        group = new Group();
        profileImageView = new ImageView();
        PlayRecordImageView = new ImageView();
        recordImageView = new ImageView();
        group0 = new Group();
        logoutImageView = new ImageView();
        aboutImageView = new ImageView();
        refreshImageView = new ImageView();
        backgroundImageView = new ImageView();
        hBoxBody = new HBox();
        hBoxGridPane = new HBox();
        hBoxListView = new HBox();
        chatViewVBox = new VBox();
        vBox1 = new VBox();
        hBox3 = new HBox();
        receiverUsername = new Text();
        textArea = new TextArea();
        chatHBox = new HBox();
        sendMsgTextField = new TextField();
        sendMsgImageView = new ImageView();

        setId("AnchorPane");
        setPrefHeight(500.0);
        setPrefWidth(900.0);
        setStyle("-fx-background-color:  #2c3e50;");

        AnchorPane.setBottomAnchor(vBox, 20.0);
        AnchorPane.setLeftAnchor(vBox, 20.0);
        AnchorPane.setRightAnchor(vBox, 20.0);
        AnchorPane.setTopAnchor(vBox, 20.0);
        vBox.setLayoutY(17.0);
        vBox.setSpacing(7.0);

        hBox.setAlignment(javafx.geometry.Pos.CENTER);
        hBox.setSpacing(350.0);

        titleInfoHBox.setAlignment(javafx.geometry.Pos.CENTER);
        titleInfoHBox.setPrefWidth(450.0);
        titleInfoHBox.setSpacing(10.0);

        ProfileHBox.setPrefHeight(30.0);
        ProfileHBox.setPrefWidth(130.0);
        ProfileHBox.setSpacing(6.0);

        profileImageView.setFitHeight(30.0);
        profileImageView.setFitWidth(30.0);
        profileImageView.setPickOnBounds(true);
        profileImageView.setPreserveRatio(true);
        profileImageView.setImage(new Image(getClass().getResourceAsStream("/images/user.png")));

        usernameLabel.setText(controller.getPlayer().getUsername());
        usernameLabel.setTextFill(javafx.scene.paint.Color.valueOf("#eecf56"));
        usernameLabel.setFont(new Font(20.0));

        otherPlayerName.setTextFill(javafx.scene.paint.Color.valueOf("#eecf56"));
        otherPlayerName.setFont(new Font(20.0));

        scoreHBox.setPrefWidth(160.0);

        scoreLabel.setPrefHeight(30.0);
        scoreLabel.setPrefWidth(71.0);
        //scoreLabel.setLayoutX(34.0);
        scoreLabel.setText("Score :");
        scoreLabel.setTextFill(javafx.scene.paint.Color.valueOf("#edcd58"));
        scoreLabel.setFont(new Font(20.0));

        myScoreValueLabel.setPrefHeight(30.0);
        myScoreValueLabel.setLayoutX(3.0);
        myScoreValueLabel.setText(controller.getPlayer().getPoints() + "");
        myScoreValueLabel.setTextFill(javafx.scene.paint.Color.valueOf("#edcd58"));
        myScoreValueLabel.setFont(new Font(20.0));

        PlayRecordImageView.setFitHeight(30.0);
        PlayRecordImageView.setFitWidth(30.0);
        PlayRecordImageView.setLayoutX(38.0);
        PlayRecordImageView.setPickOnBounds(true);
        PlayRecordImageView.setPreserveRatio(true);
        PlayRecordImageView.setImage(new Image(getClass().getResourceAsStream("/images/start.png")));
        PlayRecordImageView.setOnMouseClicked((event) -> {
            controller.openPlayRecordScreen();
        });

        recordImageView.setFitHeight(30.0);
        recordImageView.setFitWidth(30.0);
        recordImageView.setPickOnBounds(true);
        recordImageView.setPreserveRatio(true);
        recordImageView.setImage(new Image(getClass().getResourceAsStream("/images/record.png")));
        recordImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!controller.recordGame()) {
                    controller.makeAlert("Tic Tac Toe", "You should start a game first!");
                }
            }
        });

        logoutImageView.setFitHeight(25.0);
        logoutImageView.setFitWidth(24.0);
        logoutImageView.setLayoutX(69.0);
        logoutImageView.setPickOnBounds(true);
        logoutImageView.setPreserveRatio(true);
        logoutImageView.setImage(new Image(getClass().getResourceAsStream("/images/logout.png")));
        logoutImageView.setOnMouseClicked((MouseEvent event) -> {
            controller.logout();
        });

        aboutImageView.setFitHeight(25.0);
        aboutImageView.setFitWidth(24.0);
        aboutImageView.setLayoutX(38.0);
        aboutImageView.setPickOnBounds(true);
        aboutImageView.setPreserveRatio(true);
        aboutImageView.setImage(new Image(getClass().getResourceAsStream("/images/about.png")));
        aboutImageView.setOnMouseClicked((event) -> {
            controller.openAboutUsScreen();
        });

        refreshImageView.setFitHeight(25.0);
        refreshImageView.setFitWidth(24.0);
        refreshImageView.setPickOnBounds(true);
        refreshImageView.setPreserveRatio(true);
        refreshImageView.setImage(new Image(getClass().getResourceAsStream("/images/refresh.png")));
        refreshImageView.setOnMouseClicked((MouseEvent event) -> {
            displayPlayerList();
        });

        hBoxBody.setAlignment(javafx.geometry.Pos.CENTER);
        hBoxBody.setSpacing(20.0);

        backgroundImageView.setImage(new Image(getClass().getResourceAsStream("/images/background.png")));
        backgroundImageView.setFitHeight(420.0);
        backgroundImageView.setFitWidth(420.0);
        backgroundImageView.setOpacity(0.13);

        hBoxGridPane.setAlignment(javafx.geometry.Pos.CENTER);
        hBoxGridPane.setPrefHeight(420.0);
        hBoxGridPane.setPrefWidth(420.0);

        hBoxListView.setAlignment(javafx.geometry.Pos.CENTER);
        hBoxListView.setPrefHeight(420.0);
        hBoxListView.setPrefWidth(170.0);
        hBoxListView.setStyle("-fx-background-color: " + controller.SCENE_FORGROUND
                + "; -fx-border-radius: 5; -fx-background-radius: 5;");
        hBoxListView.setPadding(new Insets(2.0));

        chatViewVBox.setAlignment(javafx.geometry.Pos.CENTER);
        chatViewVBox.setPrefHeight(420.0);
        chatViewVBox.setPrefWidth(225.0);
        chatViewVBox.setSpacing(10.0);
        chatViewVBox.setVisible(false);
        chatViewVBox.setStyle("-fx-background-radius: 7; -fx-border-radius: 7;");

        hBox3.setAlignment(javafx.geometry.Pos.CENTER);
        hBox3.setPrefHeight(30.0);
        hBox3.setSpacing(10.0);
        hBox3.setStyle("-fx-background-color: #eecf56; -fx-border-radius: 5; -fx-background-radius: 5;");

        receiverUsername.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        receiverUsername.setText("User name");

        textArea.setEditable(false);
        textArea.setFocusTraversable(false);
        textArea.setPrefHeight(333.0);
        textArea.setStyle("-fx-border-color: #fff; -fx-background-radius: 5; -fx-border-radius: 5;");

        chatHBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        chatHBox.setSpacing(10.0);
        chatHBox.setStyle("-fx-background-color: #fff; -fx-background-radius: 7;");

        sendMsgTextField.setPrefHeight(30.0);
        sendMsgTextField.setPrefWidth(180.0);

        sendMsgImageView.setFitHeight(25.0);
        sendMsgImageView.setFitWidth(25.0);
        sendMsgImageView.setPickOnBounds(true);
        sendMsgImageView.setPreserveRatio(true);
        sendMsgImageView.setImage(new Image(getClass().getResourceAsStream("/images/sendMsg.png")));
        sendMsgImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                controller.sendMsg(controller.getPlayer().getUsername(),
                        receiverUsername.getText(), sendMsgTextField.getText());
            }
        });

        displayPlayerList();

        ProfileHBox.getChildren().add(profileImageView);
        ProfileHBox.getChildren().add(usernameLabel);
        ProfileHBox.getChildren().add(otherPlayerName);
        titleInfoHBox.getChildren().add(ProfileHBox);
        scoreHBox.getChildren().add(scoreLabel);
        scoreHBox.getChildren().add(myScoreValueLabel);
        titleInfoHBox.getChildren().add(scoreHBox);
        group.getChildren().add(PlayRecordImageView);
        group.getChildren().add(recordImageView);
        titleInfoHBox.getChildren().add(group);
        hBox.getChildren().add(titleInfoHBox);
        group0.getChildren().add(logoutImageView);
        group0.getChildren().add(aboutImageView);
        group0.getChildren().add(refreshImageView);
        hBox.getChildren().add(group0);
        vBox.getChildren().add(hBox);
        hBox3.getChildren().add(receiverUsername);
        vBox1.getChildren().add(hBox3);
        vBox1.getChildren().add(textArea);
        chatViewVBox.getChildren().add(vBox1);
        chatHBox.getChildren().add(sendMsgTextField);
        chatHBox.getChildren().add(sendMsgImageView);
        chatViewVBox.getChildren().add(chatHBox);
        hBoxGridPane.getChildren().add(backgroundImageView);
        hBoxBody.getChildren().add(hBoxGridPane);
        hBoxBody.getChildren().add(chatViewVBox);
        hBoxBody.getChildren().add(hBoxListView);
        vBox.getChildren().add(hBoxBody);
        getChildren().add(vBox);

    }

    protected void setXO(MouseEvent mouseEvent) {
        if (getRowIndex((ImageView) mouseEvent.getSource()) == null) {
            rowIndex = 0;
        } else {
            rowIndex = getRowIndex((ImageView) mouseEvent.getSource());
        }
        if (getColumnIndex((ImageView) mouseEvent.getSource()) == null) {
            columnIndex = 0;
        } else {
            columnIndex = getColumnIndex((ImageView) mouseEvent.getSource());
        }
        if (cell[rowIndex][columnIndex] == '\0') {
            if (controller.isItMyTurn()) {
                if (controller.isbaginer()) {
                    controller.sendGameCell(rowIndex, columnIndex, 'x');
                } else {
                    controller.sendGameCell(rowIndex, columnIndex, 'o');
                }
            }
        }
    }

    public void startGame(String playerName) {
        setOtherPlayerName(playerName);
        if (gridPane != null) {
            //here we should delete gride pane 
            hBoxGridPane.getChildren().remove(gridPane);
        } else {
            hBoxGridPane.getChildren().remove(backgroundImageView);
        }
        //to auto refresh the list 
        displayPlayerList();
        columnConstraints = new ColumnConstraints();
        columnConstraints0 = new ColumnConstraints();
        columnConstraints1 = new ColumnConstraints();
        rowConstraints = new RowConstraints();
        rowConstraints0 = new RowConstraints();
        rowConstraints1 = new RowConstraints();
        gridImageView1 = new ImageView();
        gridImageView2 = new ImageView();
        gridImageView3 = new ImageView();
        gridImageView4 = new ImageView();
        gridImageView5 = new ImageView();
        gridImageView6 = new ImageView();
        gridImageView7 = new ImageView();
        gridImageView8 = new ImageView();
        gridImageView9 = new ImageView();
        gridPane = new GridPane();

        gridPane.setAlignment(javafx.geometry.Pos.CENTER);
        gridPane.setGridLinesVisible(true);
        gridPane.setMaxHeight(USE_PREF_SIZE);
        gridPane.setMaxWidth(USE_PREF_SIZE);
        gridPane.setMinHeight(USE_PREF_SIZE);
        gridPane.setMinWidth(USE_PREF_SIZE);
        gridPane.setPrefHeight(420.0);
        gridPane.setPrefWidth(420.0);
        gridPane.setStyle("-fx-background-color: #eecf56; -fx-background-radius: 7;");

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

        gridImageView1.setFitHeight(100.0);
        gridImageView1.setFitWidth(100.0);
        gridImageView1.setOnMousePressed(this::setXO);
        gridImageView1.setPickOnBounds(true);
        gridImageView1.setPreserveRatio(true);
        GridPane.setMargin(gridImageView1, new Insets(10.0, 10.0, 10.0, 20.0));

        GridPane.setColumnIndex(gridImageView2, 1);
        gridImageView2.setFitHeight(100.0);
        gridImageView2.setFitWidth(100.0);
        gridImageView2.setOnMousePressed(this::setXO);
        gridImageView2.setPickOnBounds(true);
        gridImageView2.setPreserveRatio(true);
        GridPane.setMargin(gridImageView2, new Insets(10.0, 10.0, 10.0, 20.0));

        GridPane.setColumnIndex(gridImageView3, 2);
        gridImageView3.setFitHeight(100.0);
        gridImageView3.setFitWidth(100.0);
        gridImageView3.setOnMousePressed(this::setXO);
        gridImageView3.setPickOnBounds(true);
        gridImageView3.setPreserveRatio(true);
        GridPane.setMargin(gridImageView3, new Insets(10.0, 10.0, 10.0, 20.0));

        GridPane.setRowIndex(gridImageView4, 1);
        gridImageView4.setFitHeight(100.0);
        gridImageView4.setFitWidth(100.0);
        gridImageView4.setOnMousePressed(this::setXO);
        gridImageView4.setPickOnBounds(true);
        gridImageView4.setPreserveRatio(true);
        GridPane.setMargin(gridImageView4, new Insets(10.0, 10.0, 10.0, 20.0));

        GridPane.setColumnIndex(gridImageView5, 1);
        GridPane.setRowIndex(gridImageView5, 1);
        gridImageView5.setFitHeight(100.0);
        gridImageView5.setFitWidth(100.0);
        gridImageView5.setOnMousePressed(this::setXO);
        gridImageView5.setPickOnBounds(true);
        gridImageView5.setPreserveRatio(true);
        GridPane.setMargin(gridImageView5, new Insets(10.0, 10.0, 10.0, 20.0));

        GridPane.setColumnIndex(gridImageView6, 2);
        GridPane.setRowIndex(gridImageView6, 1);
        gridImageView6.setFitHeight(100.0);
        gridImageView6.setFitWidth(100.0);
        gridImageView6.setOnMousePressed(this::setXO);
        gridImageView6.setPickOnBounds(true);
        gridImageView6.setPreserveRatio(true);
        GridPane.setMargin(gridImageView6, new Insets(10.0, 10.0, 10.0, 20.0));

        GridPane.setRowIndex(gridImageView7, 2);
        gridImageView7.setFitHeight(100.0);
        gridImageView7.setFitWidth(100.0);
        gridImageView7.setOnMousePressed(this::setXO);
        gridImageView7.setPickOnBounds(true);
        gridImageView7.setPreserveRatio(true);
        GridPane.setMargin(gridImageView7, new Insets(10.0, 10.0, 10.0, 20.0));

        GridPane.setColumnIndex(gridImageView8, 1);
        GridPane.setRowIndex(gridImageView8, 2);
        gridImageView8.setFitHeight(100.0);
        gridImageView8.setFitWidth(100.0);
        gridImageView8.setOnMousePressed(this::setXO);
        gridImageView8.setPickOnBounds(true);
        gridImageView8.setPreserveRatio(true);
        GridPane.setMargin(gridImageView8, new Insets(10.0, 10.0, 10.0, 20.0));

        GridPane.setColumnIndex(gridImageView9, 2);
        GridPane.setRowIndex(gridImageView9, 2);
        gridImageView9.setFitHeight(100.0);
        gridImageView9.setFitWidth(100.0);
        gridImageView9.setOnMousePressed(this::setXO);
        gridImageView9.setPickOnBounds(true);
        gridImageView9.setPreserveRatio(true);
        GridPane.setMargin(gridImageView9, new Insets(10.0, 10.0, 10.0, 20.0));

        gridPane.getColumnConstraints().add(columnConstraints);
        gridPane.getColumnConstraints().add(columnConstraints0);
        gridPane.getColumnConstraints().add(columnConstraints1);
        gridPane.getRowConstraints().add(rowConstraints);
        gridPane.getRowConstraints().add(rowConstraints0);
        gridPane.getRowConstraints().add(rowConstraints1);
        gridPane.getChildren().add(gridImageView1);
        gridPane.getChildren().add(gridImageView2);
        gridPane.getChildren().add(gridImageView3);
        gridPane.getChildren().add(gridImageView4);
        gridPane.getChildren().add(gridImageView5);
        gridPane.getChildren().add(gridImageView6);
        gridPane.getChildren().add(gridImageView7);
        gridPane.getChildren().add(gridImageView8);
        gridPane.getChildren().add(gridImageView9);
        hBoxGridPane.getChildren().add(gridPane);
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

    class PlayerCell extends ListCell<Player> {

        HBox hbox = new HBox();
        Label username = new Label("(empty)");
        Label points = new Label("(empty)");
        ImageView active = new ImageView();
        ImageView chat = new ImageView();
        ImageView game = new ImageView();
        Pane pane = new Pane();

        public PlayerCell() {
            super();
            hbox.getChildren().addAll(active, username, pane, points, chat, game);
            HBox.setHgrow(pane, Priority.ALWAYS);
        }

        @Override
        public void updateItem(Player player, boolean empty) {
            super.updateItem(player, empty);

            if (player != null) {
                active.setFitWidth(7.0);
                active.setFitHeight(7.0);
                double activeY = hbox.getHeight() / 2 + 5;
                HBox.setMargin(active, new Insets(activeY, 2.0, 2.0, 2.0));
                hbox.setSpacing(2.0);
                points.setText(player.getPoints() + "\t");
                username.setText(player.getUsername());
                chat.setFitWidth(20.0);
                chat.setFitHeight(17.0);
                chat.setImage(new Image(getClass().getResourceAsStream("/images/chat.png")));
                chat.setOnMouseClicked((MouseEvent event) -> {
                    receiverUsername.setText(player.getUsername());
                    chatViewVBox.setVisible(true);
                });
                game.setFitWidth(18.0);
                game.setFitHeight(17.0);
                if (player.isInGame()) {
                    active.setImage(new Image(getClass().getResourceAsStream("/images/dotY.png")));
                    game.setImage(new Image(getClass().getResourceAsStream("/images/gameY.png")));
                    game.setOnMouseClicked((event) -> {
                        controller.makeAlert("Error", "you can not play with "
                                + player.getUsername() + " now");
                    });
                } else {
                    active.setImage(new Image(getClass().getResourceAsStream("/images/dot.png")));
                    game.setImage(new Image(getClass().getResourceAsStream("/images/game.png")));
                    game.setOnMouseClicked((MouseEvent event) -> {
                        if (!isInvitationBtnClicked) {
                            isInvitationBtnClicked = true;
                            controller.sendInvition(player.getUsername());
                            controller.makeAlert("waiting", "Please wait until the other respond");
                        }
                    });
                }
                setGraphic(hbox);
            }
        }
    }

    public void displayPlayerList() {
        Platform.runLater(() -> {
            if (playersListView != null) {
                hBoxListView.getChildren().remove(playersListView);
            }
            List<Player> activePlayer = controller.getActivePlayer();
            if (activePlayer.size() > 0) {
                ObservableList<Player> list = FXCollections.observableArrayList(activePlayer);
                playersListView = new ListView(list);
                playersListView.setCellFactory(new Callback<ListView<Player>, ListCell<Player>>() {
                    @Override
                    public ListCell<Player> call(ListView<Player> param) {
                        return new PlayerCell();
                    }
                });
                playersListView.setPrefHeight(420.0);
                playersListView.setPrefWidth(170.0);
                playersListView.setStyle("-fx-border-radius: 7;"
                        + "-fx-border-color: " + controller.SCENE_FORGROUND + ";"
                        + " -fx-background-radius: 7; "
                        + "-fx-faint-focus-color: transparent;");
                hBoxListView.getChildren().add(playersListView);
            } else {
                playersListView = new ListView();
            }
        });
    }

    public void receiveMsg(String sender, String receiver, String message) {
        if (controller.getPlayer().getUsername().equals(sender)) {
            if (!receiver.equals(receiverUsername.getText())) {
                textArea.setText("");
            }
            receiverUsername.setText(receiver);
        } else {
            if (!sender.equals(receiverUsername.getText())) {
                textArea.setText("");
            }
            receiverUsername.setText(sender);
        }
        chatViewVBox.setVisible(true);
        sendMsgTextField.setText("");
        textArea.appendText(message + "\n");
    }

    public void endGame(int score) {
        myScoreValueLabel.setText(score + "");
        recordImageView.setImage(new Image(getClass().getResourceAsStream("/images/record.png")));
        displayPlayerList();
        if (gridPane != null) {
            //here we should delete gride pane 
            isInvitationBtnClicked = false;
            hBoxGridPane.getChildren().remove(gridPane);
            hBoxGridPane.getChildren().add(backgroundImageView);
        }
    }

    public void setRecording() {
        recordImageView.setImage(new Image(getClass().getResourceAsStream("/images/recordRed.png")));
    }

    public void setOtherPlayerName(String playerName) {
        otherPlayerName.setText(" VS " + playerName);
    }
    
    public void setIsInvitationBtnClicked(){
        isInvitationBtnClicked = false;
    }
}

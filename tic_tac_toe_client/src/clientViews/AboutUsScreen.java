package clientViews;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import tic_tac_toe_client.Tic_tac_toe_client;

public class AboutUsScreen extends AnchorPane {

    protected final HBox hBox;
    protected final Button aboutUs;
    protected final Button howToPlayOffline;
    protected final Button howToPlayOnline;
    protected final AnchorPane aboutAnchorPane;
    protected final ImageView aboutImageView;
    protected final Label label;
    protected final Label developersNamesL;
    protected final AnchorPane offlineAnchorPane;
    protected final Label label1;
    protected final ImageView offlineImageView;
    protected final AnchorPane onlineAnchorPane1;
    protected final Label label2;
    protected final ImageView onlineImageView;
    protected final ImageView logoutImageView;
    protected final ImageView backImageView;
    Tic_tac_toe_client controller;

    public AboutUsScreen(Tic_tac_toe_client controller) {

        this.controller = controller;
        hBox = new HBox();
        aboutUs = new Button();
        howToPlayOffline = new Button();
        howToPlayOnline = new Button();
        aboutAnchorPane = new AnchorPane();
        aboutImageView = new ImageView();
        label = new Label();
        developersNamesL = new Label();
        offlineAnchorPane = new AnchorPane();
        label1 = new Label();
        offlineImageView = new ImageView();
        onlineAnchorPane1 = new AnchorPane();
        label2 = new Label();
        onlineImageView = new ImageView();
        logoutImageView = new ImageView();
        backImageView = new ImageView();

        setId("AnchorPane");
        setPrefHeight(500.0);
        setPrefWidth(900.0);
        setStyle("-fx-background-color: #2c3e50;");
        getStyleClass().add("root");

        AnchorPane.setLeftAnchor(hBox, 30.0);
        AnchorPane.setRightAnchor(hBox, 30.0);
        AnchorPane.setTopAnchor(hBox, 65.0);
        hBox.setLayoutX(26.0);
        hBox.setLayoutY(66.0);
        hBox.setPrefWidth(911.0);
        hBox.setSpacing(4.0);

        aboutUs.setMnemonicParsing(false);
        aboutUs.setPrefHeight(39.0);
        aboutUs.setPrefWidth(284.0);
        aboutUs.setStyle("-fx-background-color: #eecf56;");
        aboutUs.setText("About us");
        aboutUs.setFont(new Font(18.0));
        aboutUs.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.openAboutUsScreenBtn();
            }
        });

        howToPlayOffline.setMnemonicParsing(false);
        howToPlayOffline.setPrefHeight(39.0);
        howToPlayOffline.setPrefWidth(321.0);
        howToPlayOffline.setStyle("-fx-background-color: #eecf56;");
        howToPlayOffline.setText("How to play offline Mode");
        howToPlayOffline.setFont(new Font(18.0));
        howToPlayOffline.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.openHowToPlayOfflineScreen();
            }
        });

        howToPlayOnline.setMnemonicParsing(false);
        howToPlayOnline.setPrefHeight(38.0);
        howToPlayOnline.setPrefWidth(287.0);
        howToPlayOnline.setStyle("-fx-background-color: #eecf56;");
        howToPlayOnline.setText("How to play online mode");
        howToPlayOnline.setFont(new Font(18.0));
        howToPlayOnline.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.openHowToPlayOnlineScreen();
            }
        });

        AnchorPane.setLeftAnchor(aboutAnchorPane, 100.0);
        AnchorPane.setRightAnchor(aboutAnchorPane, 100.0);
        aboutAnchorPane.setLayoutX(11.0);
        aboutAnchorPane.setLayoutY(124.0);
        aboutAnchorPane.setPrefHeight(349.0);
        aboutAnchorPane.setPrefWidth(731.0);
        aboutAnchorPane.setVisible(false);

        AnchorPane.setBottomAnchor(aboutImageView, 25.0);
        AnchorPane.setLeftAnchor(aboutImageView, 115.0);
        AnchorPane.setTopAnchor(aboutImageView, 30.0);
        aboutImageView.setFitHeight(294.0);
        aboutImageView.setFitWidth(387.0);
        aboutImageView.setLayoutX(206.0);
        aboutImageView.setLayoutY(14.0);
        aboutImageView.setPickOnBounds(true);
        aboutImageView.setPreserveRatio(true);
        aboutImageView.setImage(new Image(getClass().getResourceAsStream("/images/aboutImage.png")));

        AnchorPane.setLeftAnchor(label, 290.0);
        AnchorPane.setRightAnchor(label, 290.0);
        label.setLayoutX(407.0);
        label.setLayoutY(28.0);
        label.setText("About us");
        label.setTextFill(javafx.scene.paint.Color.valueOf("#eecf56"));
        label.setUnderline(true);
        label.setFont(new Font(27.0));

        AnchorPane.setRightAnchor(developersNamesL, 46.0);
        AnchorPane.setTopAnchor(developersNamesL, 105.0);
        developersNamesL.setAlignment(javafx.geometry.Pos.TOP_LEFT);
        developersNamesL.setLayoutX(510.0);
        developersNamesL.setLayoutY(90.0);
        developersNamesL.setPrefHeight(151.0);
        developersNamesL.setPrefWidth(208.0);
        developersNamesL.setText("Ahmed Ashraf\n"
                + "Anas Mohamed\n"
                + "Mohamed Tahoon\n"
                + "Aya Mohamed");
        developersNamesL.setTextFill(javafx.scene.paint.Color.valueOf("#eecf56"));
        developersNamesL.setFont(new Font(24.0));

        AnchorPane.setBottomAnchor(offlineAnchorPane, 30.0);
        AnchorPane.setLeftAnchor(offlineAnchorPane, 120.0);
        AnchorPane.setRightAnchor(offlineAnchorPane, 120.0);
        AnchorPane.setTopAnchor(offlineAnchorPane, 128.0);
        offlineAnchorPane.setLayoutX(13.0);
        offlineAnchorPane.setLayoutY(128.0);
        offlineAnchorPane.setPrefHeight(350.0);
        offlineAnchorPane.setPrefWidth(660.0);
        offlineAnchorPane.setVisible(false);

        AnchorPane.setLeftAnchor(label1, 0.0);
        AnchorPane.setTopAnchor(label1, 0.0);
        label1.setLayoutX(25.0);
        label1.setLayoutY(30.0);
        label1.setStyle("-fx-font-weight: bold;");
        label1.setText("How to play offline mode?");
        label1.setTextFill(javafx.scene.paint.Color.valueOf("#eecf56"));
        label1.setFont(new Font(18.0));

        AnchorPane.setBottomAnchor(offlineImageView, 20.0);
        AnchorPane.setTopAnchor(offlineImageView, 20.0);
        offlineImageView.setFitHeight(300.0);
        offlineImageView.setLayoutX(319.0);
        offlineImageView.setLayoutY(56.0);
        offlineImageView.setOpacity(0.32);
        offlineImageView.setPickOnBounds(true);
        offlineImageView.setPreserveRatio(true);
        offlineImageView.setImage(new Image(getClass().getResourceAsStream("/images/background.png")));

        AnchorPane.setBottomAnchor(onlineAnchorPane1, 30.0);
        AnchorPane.setLeftAnchor(onlineAnchorPane1, 120.0);
        AnchorPane.setRightAnchor(onlineAnchorPane1, 120.0);
        AnchorPane.setTopAnchor(onlineAnchorPane1, 128.0);
        onlineAnchorPane1.setLayoutX(8.0);
        onlineAnchorPane1.setLayoutY(143.0);
        onlineAnchorPane1.setPrefHeight(332.0);
        onlineAnchorPane1.setPrefWidth(674.0);

        AnchorPane.setLeftAnchor(label2, 0.0);
        AnchorPane.setTopAnchor(label2, 0.0);
        label2.setLayoutX(25.0);
        label2.setLayoutY(25.0);
        label2.setStyle("-fx-font-weight: bold;");
        label2.setText("How to play online mode?");
        label2.setTextFill(javafx.scene.paint.Color.valueOf("#eecf56"));
        label2.setFont(new Font(18.0));

        AnchorPane.setBottomAnchor(onlineImageView, 20.0);
        AnchorPane.setTopAnchor(onlineImageView, 20.0);
        onlineImageView.setFitHeight(300.0);
        onlineImageView.setFitWidth(857.0);
        onlineImageView.setLayoutX(327.0);
        onlineImageView.setLayoutY(79.0);
        onlineImageView.setOpacity(0.26);
        onlineImageView.setPickOnBounds(true);
        onlineImageView.setPreserveRatio(true);
        onlineImageView.setImage(new Image(getClass().getResourceAsStream("/images/background.png")));

        AnchorPane.setRightAnchor(logoutImageView, 30.0);
        AnchorPane.setTopAnchor(logoutImageView, 20.0);
        logoutImageView.setFitHeight(35.0);
        logoutImageView.setFitWidth(30.0);
        logoutImageView.setLayoutX(639.0);
        logoutImageView.setLayoutY(10.0);
        logoutImageView.setPickOnBounds(true);
        logoutImageView.setPreserveRatio(true);
        logoutImageView.setImage(new Image(getClass().getResourceAsStream("/images/logout.png")));
        logoutImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                controller.logout();
            }
        });
        AnchorPane.setLeftAnchor(backImageView, 30.0);
        AnchorPane.setTopAnchor(backImageView, 20.0);
        backImageView.setFitHeight(35.0);
        backImageView.setFitWidth(30.0);
        backImageView.setLayoutX(11.0);
        backImageView.setLayoutY(12.0);
        backImageView.setPickOnBounds(true);
        backImageView.setPreserveRatio(true);
        backImageView.setImage(new Image(getClass().getResourceAsStream("/images/left-arrow.png")));
        backImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                controller.openMultiPlayerScreen();
            }
        });

        hBox.getChildren().add(aboutUs);
        hBox.getChildren().add(howToPlayOffline);
        hBox.getChildren().add(howToPlayOnline);
        getChildren().add(hBox);
        aboutAnchorPane.getChildren().add(aboutImageView);
        aboutAnchorPane.getChildren().add(label);
        aboutAnchorPane.getChildren().add(developersNamesL);
        getChildren().add(aboutAnchorPane);
        offlineAnchorPane.getChildren().add(label1);
        offlineAnchorPane.getChildren().add(offlineImageView);
        getChildren().add(offlineAnchorPane);
        onlineAnchorPane1.getChildren().add(label2);
        onlineAnchorPane1.getChildren().add(onlineImageView);
        getChildren().add(onlineAnchorPane1);
        getChildren().add(logoutImageView);
        getChildren().add(backImageView);
    }
     public AnchorPane getAboutAnchorPane() {
        return aboutAnchorPane;
    }

    public AnchorPane getOfflineAnchorPane() {
        return offlineAnchorPane;
    }

    public AnchorPane getOnlineAnchorPane1() {
        return onlineAnchorPane1;
    }
}

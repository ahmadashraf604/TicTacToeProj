package clientViews;

import common.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import tic_tac_toe_client.Tic_tac_toe_client;

public class LoginScreen extends AnchorPane {

    protected final AnchorPane anchorPane;
    protected final TextField userNameTextField;
    protected final PasswordField passwordTextField;
    protected final Button signInBtn;
    protected final Label signInLabel;
    protected final Label userNameTextFieldError;
    protected final Label passwordTextFieldError;
    protected final Button signUpBtn;
    protected final ImageView backgroundImage;
    protected final Label welcomeLabel;
    protected final Label infoLabel;
    protected final Button playNowBtn;
    protected final Label withFriendsLabel;
    protected final ImageView arrowImage;
    protected final Label orLabel;
    protected final Label serverError;
    protected final Stage stage;
    protected final Label invalid;
    Tic_tac_toe_client controller;

    //constructor
    public LoginScreen(Stage stage, Tic_tac_toe_client controller) {

        this.controller = controller;
        anchorPane = new AnchorPane();
        invalid = new Label();
        serverError = new Label();
        userNameTextField = new TextField();
        passwordTextField = new PasswordField();
        userNameTextFieldError = new Label();
        passwordTextFieldError = new Label();
        signInBtn = new Button();
        signInLabel = new Label();
        signUpBtn = new Button();
        backgroundImage = new ImageView();
        welcomeLabel = new Label();
        infoLabel = new Label();
        playNowBtn = new Button();
        withFriendsLabel = new Label();
        arrowImage = new ImageView();
        orLabel = new Label();
        this.stage = stage;

        setMaxHeight(500.0);
        setMaxWidth(900.0);
        setMinHeight(500.0);
        setMinWidth(900.0);
        setPrefHeight(500.0);
        setPrefWidth(900.0);
        setStyle("-fx-background-color:  #2c3e50;");

        anchorPane.setLayoutX(557.0);
        anchorPane.setLayoutY(62.0);
        anchorPane.setPrefHeight(376.0);
        anchorPane.setPrefWidth(277.0);
        anchorPane.setStyle("-fx-background-color: #ecf0f1;");

        userNameTextField.setFocusTraversable(false);
        userNameTextField.setLayoutX(39.0);
        userNameTextField.setLayoutY(119.0);
        userNameTextField.setPrefHeight(25.0);
        userNameTextField.setPrefWidth(200.0);
        userNameTextField.setPromptText("Username ");
        userNameTextField.setStyle("-fx-prompt-text-fill: gray;");
        userNameTextField.setFocusTraversable(true);
        userNameTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    passwordTextField.requestFocus();
                }else if(event.getCode() == KeyCode.TAB){
                     passwordTextField.setFocusTraversable(true);
                }
            }
        });

        userNameTextFieldError.setFocusTraversable(false);
        userNameTextFieldError.setLayoutX(29.0);
        userNameTextFieldError.setLayoutY(140.0);
        userNameTextFieldError.setText("");
        userNameTextFieldError.setTextFill(javafx.scene.paint.Color.valueOf("#FF0000"));

        passwordTextField.setFocusTraversable(false);
        passwordTextField.setLayoutX(39.0);
        passwordTextField.setLayoutY(163.0);
        passwordTextField.setPrefHeight(25.0);
        passwordTextField.setPrefWidth(200.0);
        passwordTextField.setPromptText("Password");
        passwordTextField.setStyle("-fx-prompt-text-fill: gray;");
        passwordTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    singin();
                }
            }
        });

        passwordTextFieldError.setFocusTraversable(false);
        passwordTextFieldError.setLayoutX(29.0);
        passwordTextFieldError.setLayoutY(182.0);
        passwordTextFieldError.setPrefHeight(25.0);
        passwordTextFieldError.setText("");
        passwordTextFieldError.setTextFill(javafx.scene.paint.Color.valueOf("#FF0000"));

        invalid.setLayoutX(39.0);
        invalid.setLayoutY(195.0);
        invalid.setPrefHeight(25.0);
        invalid.setText("*invalid username or password");
        invalid.setVisible(false);
        invalid.setTextFill(javafx.scene.paint.Color.valueOf("#FF0000"));

        serverError.setAlignment(javafx.geometry.Pos.CENTER);
        serverError.setLayoutX(30.0);
        serverError.setLayoutY(308.0);
        serverError.setPrefHeight(60.0);
        serverError.setText("Server not available now!"
                + " try again later. ");
        serverError.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        serverError.setTextFill(javafx.scene.paint.Color.valueOf("#f20303"));
        serverError.setVisible(false);

        signInBtn.setLayoutX(39.0);
        signInBtn.setLayoutY(229.0);
        signInBtn.setMnemonicParsing(false);
        signInBtn.setPrefHeight(25.0);
        signInBtn.setPrefWidth(200.0);
        signInBtn.setStyle("-fx-background-color: #eecf56;");
        signInBtn.setText("Sign in ");
        signInBtn.setFont(new Font("System Bold",12.0));
        signInBtn.setTextFill(javafx.scene.paint.Color.valueOf("#2c3e50"));

        signInLabel.setLayoutX(95.0);
        signInLabel.setLayoutY(39.0);
        signInLabel.setPrefHeight(53.0);
        signInLabel.setPrefWidth(87.0);
        signInLabel.setText("Sign in ");
        signInLabel.setTextAlignment(javafx.scene.text.TextAlignment.JUSTIFY);
        signInLabel.setStyle("-fx-font-weight:  bold;");
        signInLabel.setTextFill(javafx.scene.paint.Color.valueOf("#666666"));
        signInLabel.setFont(new Font(24.0));

        signUpBtn.setLayoutX(39.0);
        signUpBtn.setLayoutY(263.0);
        signUpBtn.setMnemonicParsing(false);
        signUpBtn.setPrefHeight(25.0);
        signUpBtn.setPrefWidth(200.0);
        signUpBtn.setStyle("-fx-background-color: #eecf56;");
        signUpBtn.setText("Sign up ");
        signUpBtn.setFont(new Font("System Bold",12.0));
        signUpBtn.setTextFill(javafx.scene.paint.Color.valueOf("#2c3e50"));
        signUpBtn.setOpaqueInsets(new Insets(2.0));

        backgroundImage.setFitHeight(500.0);
        backgroundImage.setFitWidth(544.0);
        backgroundImage.setOpacity(0.13);
        backgroundImage.setPickOnBounds(true);
        backgroundImage.setPreserveRatio(true);
        backgroundImage.setImage(new Image(getClass().getResourceAsStream("/images/background.png")));

        welcomeLabel.setLayoutX(64.0);
        welcomeLabel.setLayoutY(118.0);
        welcomeLabel.setPrefHeight(60.0);
        welcomeLabel.setPrefWidth(373.0);
        welcomeLabel.setText("Welcome to Tic-tac toe");
        welcomeLabel.setFont(new Font("System Bold",32.0));
        welcomeLabel.setTextFill(javafx.scene.paint.Color.valueOf("#eecf56"));

        infoLabel.setLayoutX(85.0);
        infoLabel.setLayoutY(165.0);
        infoLabel.setPrefHeight(85.0);
        infoLabel.setPrefWidth(277.0);
        infoLabel.setText("The Tic Tac Toe game! Simple, Quick and"+"\n"+" Funny!");
        infoLabel.setTextFill(javafx.scene.paint.Color.valueOf("#eecf56"));
        infoLabel.setFont(new Font(15.0));

        playNowBtn.setLayoutX(70.0);
        playNowBtn.setLayoutY(276.0);
        playNowBtn.setMnemonicParsing(false);
        playNowBtn.setPrefHeight(34.0);
        playNowBtn.setPrefWidth(123.0);
        playNowBtn.setStyle("-fx-background-color: #EECF56;");
        playNowBtn.setText("Play Offline!");
        playNowBtn.setFont(new Font("System Bold",15.0));
        playNowBtn.setTextFill(javafx.scene.paint.Color.valueOf("#2c3e50"));

        withFriendsLabel.setLayoutX(251.0);
        withFriendsLabel.setLayoutY(276.0);
        withFriendsLabel.setPrefHeight(34.0);
        withFriendsLabel.setPrefWidth(123.0);
        withFriendsLabel.setText(" play with friends");
        withFriendsLabel.setTextFill(javafx.scene.paint.Color.valueOf("#eecf56"));
        withFriendsLabel.setFont(new Font(16.0));

        arrowImage.setFitHeight(134.0);
        arrowImage.setFitWidth(123.0);
        arrowImage.setLayoutX(389.0);
        arrowImage.setLayoutY(232.0);
        arrowImage.setPickOnBounds(true);
        arrowImage.setPreserveRatio(true);
        arrowImage.setImage(new Image(getClass().getResourceAsStream("/images/arrow2.png")));

        orLabel.setLayoutX(209.0);
        orLabel.setLayoutY(275.0);
        orLabel.setPrefHeight(34.0);
        orLabel.setPrefWidth(36.0);
        orLabel.setText("OR");
        orLabel.setTextFill(javafx.scene.paint.Color.valueOf("#eecf56"));
        orLabel.setFont(new Font(24.0));

        anchorPane.getChildren().add(userNameTextField);
        anchorPane.getChildren().add(userNameTextFieldError);
        anchorPane.getChildren().add(passwordTextField);
        anchorPane.getChildren().add(passwordTextFieldError);
        anchorPane.getChildren().add(signInBtn);
        anchorPane.getChildren().add(signInLabel);
        anchorPane.getChildren().add(signUpBtn);
        anchorPane.getChildren().add(invalid);
        anchorPane.getChildren().add(serverError);
        getChildren().add(anchorPane);
        getChildren().add(backgroundImage);
        getChildren().add(welcomeLabel);
        getChildren().add(infoLabel);
        getChildren().add(playNowBtn);
        getChildren().add(withFriendsLabel);
        getChildren().add(arrowImage);
        getChildren().add(orLabel);

        signInBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                singin();
            }
        });

        signUpBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.openRegisterScreen();
            }
        });

        playNowBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.openOfflineScreen();
            }
        });
    }

    public Label getServerErrorLabel() {
        return this.serverError;
    }

    public Label getInValidLabel() {
        return this.invalid;
    }

    private void singin() {
        Player player = controller.signin(userNameTextField.getText(), passwordTextField.getText());
        if (player != null) {
            controller.openMultiPlayerScreen();
        }
    }
}

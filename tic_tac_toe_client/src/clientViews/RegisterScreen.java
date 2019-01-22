package clientViews;

import common.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
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
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import tic_tac_toe_client.Tic_tac_toe_client;

public class RegisterScreen extends AnchorPane {

    protected final ImageView backgroundImage2;
    protected final AnchorPane anchorPane;
    protected final TextField emailTextField2;
    protected final PasswordField passwordTextField2;
    protected final Button signInButton2;
    protected final Label signUpLabel2;
    protected final Button signUpButton2;
    protected final TextField userNameTextField2;
    protected final Label userNameTextFieldError2;
    protected final Label emailTextFieldError2;
    protected final Label passwordTextFieldError2;
    protected final Label welcomeLabel2;
    protected final Label infoLabel2;
    Tic_tac_toe_client controller;
    Stage stage;

    public RegisterScreen(Stage stage, Tic_tac_toe_client controller) {

        this.stage = stage;
        this.controller = controller;
        backgroundImage2 = new ImageView();
        anchorPane = new AnchorPane();
        emailTextField2 = new TextField();
        passwordTextField2 = new PasswordField();
        signInButton2 = new Button();
        signUpLabel2 = new Label();
        signUpButton2 = new Button();
        userNameTextField2 = new TextField();
        userNameTextFieldError2 = new Label();
        emailTextFieldError2 = new Label();
        passwordTextFieldError2 = new Label();
        welcomeLabel2 = new Label();
        infoLabel2 = new Label();

        setId("AnchorPane");
        setMaxHeight(500.0);
        setMaxWidth(900.0);
        setMinHeight(500.0);
        setMinWidth(900.0);
        setPrefHeight(500.0);
        setPrefWidth(900.0);
        setStyle("-fx-background-color: #2c3e50;");

        backgroundImage2.setFitHeight(507.0);
        backgroundImage2.setFitWidth(500.0);
        backgroundImage2.setOpacity(0.13);
        backgroundImage2.setPickOnBounds(true);
        backgroundImage2.setPreserveRatio(true);
        backgroundImage2.setImage(new Image(getClass().getResourceAsStream("/images/background.png")));

        anchorPane.setLayoutX(579.0);
        anchorPane.setLayoutY(56.0);
        anchorPane.setPrefHeight(376.0);
        anchorPane.setPrefWidth(277.0);
        anchorPane.setStyle("-fx-background-color: #ecf0f1;");

        emailTextField2.setFocusTraversable(false);
        emailTextField2.setLayoutX(29.0);
        emailTextField2.setLayoutY(160.0);
        emailTextField2.setPrefHeight(25.0);
        emailTextField2.setPrefWidth(200.0);
        emailTextField2.setPromptText("Enter your email");
        emailTextField2.setStyle("-fx-prompt-text-fill: gray;");
        emailTextField2.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    passwordTextField2.requestFocus();
                }
            }
        });

        passwordTextField2.setFocusTraversable(false);
        passwordTextField2.setLayoutX(29.0);
        passwordTextField2.setLayoutY(211.0);
        passwordTextField2.setPrefHeight(25.0);
        passwordTextField2.setPrefWidth(200.0);
        passwordTextField2.setPromptText("Enter your password");
        passwordTextField2.setStyle("-fx-prompt-text-fill: gray;");
        passwordTextField2.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    signUpMethod();
                }
            }
        });

        signInButton2.setLayoutX(39.0);
        signInButton2.setLayoutY(305.0);
        signInButton2.setMnemonicParsing(false);
        signInButton2.setPrefHeight(25.0);
        signInButton2.setPrefWidth(200.0);
        signInButton2.setStyle("-fx-background-color: #eecf56; -fx-font-weight: bold;");
        signInButton2.setText("Sign in ");
        signInButton2.setTextFill(javafx.scene.paint.Color.valueOf("#666666"));

        signUpLabel2.setLayoutX(95.0);
        signUpLabel2.setLayoutY(39.0);
        signUpLabel2.setPrefHeight(53.0);
        signUpLabel2.setPrefWidth(87.0);
        signUpLabel2.setStyle("-fx-font-weight: bold;");
        signUpLabel2.setText("Sign up");
        signUpLabel2.setTextAlignment(javafx.scene.text.TextAlignment.JUSTIFY);
        signUpLabel2.setTextFill(javafx.scene.paint.Color.valueOf("#666666"));
        signUpLabel2.setFont(new Font(24.0));

        signUpButton2.setLayoutX(39.0);
        signUpButton2.setLayoutY(273.0);
        signUpButton2.setMnemonicParsing(false);
        signUpButton2.setPrefHeight(25.0);
        signUpButton2.setPrefWidth(200.0);
        signUpButton2.setStyle("-fx-background-color: #eecf56; -fx-font-weight: bold;");
        signUpButton2.setText("Sign up ");
        signUpButton2.setTextFill(javafx.scene.paint.Color.valueOf("#666666"));
        signUpButton2.setOpaqueInsets(new Insets(2.0));

        userNameTextField2.setFocusTraversable(false);
        userNameTextField2.setLayoutX(29.0);
        userNameTextField2.setLayoutY(108.0);
        userNameTextField2.setPrefHeight(25.0);
        userNameTextField2.setPrefWidth(200.0);
        userNameTextField2.setPromptText("Enter your username ");
        userNameTextField2.setStyle("-fx-prompt-text-fill: gray;");
        userNameTextField2.setFocusTraversable(true);
        userNameTextField2.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    emailTextField2.requestFocus();
                }
            }
        });

        userNameTextFieldError2.setLayoutX(39.0);
        userNameTextFieldError2.setLayoutY(133.0);
        userNameTextFieldError2.setText("");
        userNameTextFieldError2.setTextFill(javafx.scene.paint.Color.valueOf("#FF0000"));

        emailTextFieldError2.setLayoutX(39.0);
        emailTextFieldError2.setLayoutY(188.0);
        emailTextFieldError2.setText("");
        emailTextFieldError2.setTextFill(javafx.scene.paint.Color.valueOf("#FF0000"));

        passwordTextFieldError2.setLayoutX(39.0);
        passwordTextFieldError2.setLayoutY(236.0);
        passwordTextFieldError2.setText("");
        passwordTextFieldError2.setTextFill(javafx.scene.paint.Color.valueOf("#FF0000"));

        welcomeLabel2.setLayoutX(74.0);
        welcomeLabel2.setLayoutY(128.0);
        welcomeLabel2.setPrefHeight(60.0);
        welcomeLabel2.setPrefWidth(373.0);
        welcomeLabel2.setStyle("-fx-font-weight: bold;");
        welcomeLabel2.setText("Welcome to Tic-tac toe");
        welcomeLabel2.setTextFill(javafx.scene.paint.Color.valueOf("#eecf56"));
        welcomeLabel2.setFont(new Font(32.0));

        infoLabel2.setLayoutX(80.0);
        infoLabel2.setLayoutY(188.0);
        infoLabel2.setPrefHeight(85.0);
        infoLabel2.setPrefWidth(277.0);
        infoLabel2.setText("The Tic Tac Toe game! Simple, quick, funny!");
        infoLabel2.setTextFill(javafx.scene.paint.Color.valueOf("#eecf56"));
        infoLabel2.setFont(new Font(14.0));

        this.getChildren().add(backgroundImage2);
        anchorPane.getChildren().add(emailTextField2);
        anchorPane.getChildren().add(passwordTextField2);
        anchorPane.getChildren().add(signInButton2);
        anchorPane.getChildren().add(signUpLabel2);
        anchorPane.getChildren().add(signUpButton2);
        anchorPane.getChildren().add(userNameTextField2);
        anchorPane.getChildren().add(userNameTextFieldError2);
        anchorPane.getChildren().add(emailTextFieldError2);
        anchorPane.getChildren().add(passwordTextFieldError2);
        getChildren().add(anchorPane);
        getChildren().add(welcomeLabel2);
        getChildren().add(infoLabel2);

        signInButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LoginScreen root = new LoginScreen(stage, controller);
                Scene scene = new Scene(root, 900, 500);
                stage.setTitle("sign in");
                stage.setScene(scene);
                stage.show();
            }
        });

        signUpButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                userNameTextFieldError2.setText("");
                passwordTextFieldError2.setText("");
                emailTextFieldError2.setText("");
                boolean isRegistered = false;

                if (isValidEmail(emailTextField2.getText()) && isValidUserName(userNameTextField2.getText()) && isValidPassword(passwordTextField2.getText())) {
                    Player player = new Player();
                    player.setUsername(userNameTextField2.getText());
                    player.setPassword(passwordTextField2.getText());
                    player.setEmail(emailTextField2.getText());

                    isRegistered = controller.signup(player);

                    if (isRegistered) {
                        LoginScreen root = new LoginScreen(stage, controller);
                        Scene scene = new Scene(root, 900, 500);
                        stage.setTitle("sign in");
                        stage.setScene(scene);
                        stage.show();
                    }

                } else {
                    if (!isValidEmail(emailTextField2.getText())) {
                        emailTextFieldError2.setText("*please enter valid email");
                    }
                    if (!isValidUserName(userNameTextField2.getText())) {
                        userNameTextFieldError2.setText("*please enter valid username");
                    }
                    if (!isValidPassword(passwordTextField2.getText())) {
                        passwordTextFieldError2.setText("*please enter valid password");
                    }
                }
            }
        });
    }

    //validate email
    //xya@xyz.com
    public boolean isValidEmail(String email) {
        boolean isValid = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            isValid = false;
        }
        return isValid;
    }

    //validate username
    /*from 8 to 20 characters, no _ or . at the beginning, no __ or _. or ._ or .. inside, 
    allowed characters,  no _ or . at the end*/
    public boolean isValidUserName(String userName) {
        String regularExpression = "^(?=.{4,7}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
        boolean isValid = true;
        if (userName.matches(regularExpression)) {
            return isValid;
        } else {
            isValid = false;
        }
        return isValid;
    }

    //validate Password
    //not empty, equal to or more than 8 characters 
    public boolean isValidPassword(String password) {
        boolean isValid = true;
        if (!password.equals("") && password.length() >= 8) {
            return isValid;
        } else {
            isValid = false;
        }
        return isValid;
    }

    public void signUpMethod() {

        userNameTextFieldError2.setText("");
        passwordTextFieldError2.setText("");
        emailTextFieldError2.setText("");
        boolean isRegistered = false;

        if (isValidEmail(emailTextField2.getText()) && isValidUserName(userNameTextField2.getText()) && isValidPassword(passwordTextField2.getText())) {
            Player player = new Player();
            player.setUsername(userNameTextField2.getText());
            player.setPassword(passwordTextField2.getText());
            player.setEmail(emailTextField2.getText());

            isRegistered = controller.signup(player);

            if (isRegistered) {
                LoginScreen root = new LoginScreen(stage, controller);
                Scene scene = new Scene(root, 900, 500);
                stage.setTitle("sign in");
                stage.setScene(scene);
                stage.show();
            }

        } else {
            if (!isValidEmail(emailTextField2.getText())) {
                emailTextFieldError2.setText("*please enter valid email");
            }
            if (!isValidUserName(userNameTextField2.getText())) {
                userNameTextFieldError2.setText("*please enter valid username");
            }
            if (!isValidPassword(passwordTextField2.getText())) {
                passwordTextFieldError2.setText("*please enter valid password");
            }
        }
    }
}

package views;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public abstract class testBase extends AnchorPane {

    protected final HBox hBox;
    protected final ImageView imageView;
    protected final Label label;
    protected final ImageView imageView0;
    protected final Pane pane;

    public testBase() {

        hBox = new HBox();
        imageView = new ImageView();
        label = new Label();
        imageView0 = new ImageView();
        pane = new Pane();

        setId("AnchorPane");
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        hBox.setLayoutX(138.0);
        hBox.setLayoutY(94.0);
        hBox.setPrefHeight(50.0);
        hBox.setPrefWidth(300.0);
        hBox.setSpacing(100.0);

        imageView.setFitHeight(30.0);
        imageView.setFitWidth(30.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getResource("../images/dotYpng.png").toExternalForm()));

        label.setAlignment(javafx.geometry.Pos.CENTER);
        label.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        label.setText("Label");
        label.setWrapText(true);
        HBox.setMargin(label, new Insets(7.0, 0.0, 0.0, 0.0));

        imageView0.setFitHeight(30.0);
        imageView0.setFitWidth(30.0);
        imageView0.setPickOnBounds(true);
        imageView0.setPreserveRatio(true);
        hBox.setPadding(new Insets(10.0, 0.0, 0.0, 0.0));

        pane.setLayoutX(124.0);
        pane.setLayoutY(144.0);
        pane.setPrefHeight(200.0);
        pane.setPrefWidth(200.0);

        hBox.getChildren().add(imageView);
        hBox.getChildren().add(label);
        hBox.getChildren().add(imageView0);
        getChildren().add(hBox);
        getChildren().add(pane);

    }
}

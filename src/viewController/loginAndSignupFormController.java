package viewController;


import animatefx.animation.FadeIn;
import animatefx.animation.SlideInDown;
import animatefx.animation.SlideInUp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class loginAndSignupFormController {
    public BorderPane loginFormRoot;
    public AnchorPane loginPane;
    public AnchorPane signupPane;
    double x,y;

    public void initialize(){
        loginPane.setVisible(true);     loginPane.setDisable(false);
        signupPane.setVisible(false);   signupPane.setDisable(true);
    }

    public void closePaneOnMouseDragged(MouseEvent mouseEvent) {
        Stage stage= (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setX(mouseEvent.getScreenX() - x);
        stage.setY(mouseEvent.getScreenY() - y);
    }

    public void closePaneOnMousePressed(MouseEvent mouseEvent) {
        x= mouseEvent.getSceneX();
        y=mouseEvent.getSceneY();

    }

    public void closeOnClicked(MouseEvent mouseEvent) {
        Stage stage= (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void minimizeOnClicked(MouseEvent mouseEvent) {
        Stage stage= (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void createAccountOnClicked(MouseEvent mouseEvent) throws IOException {
        loginPane.setVisible(false);    loginPane.setDisable(true);
        signupPane.setVisible(true);    signupPane.setDisable(false);
        new SlideInUp(signupPane).play();

    }

    public void backToLogInClicked(MouseEvent mouseEvent) {
        loginPane.setVisible(true);     loginPane.setDisable(false);
        signupPane.setVisible(false);   signupPane.setDisable(true);
        new SlideInDown(loginPane).play();
    }

    public void btnLogInOnClicked(MouseEvent mouseEvent)  {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("../view/navigationForm.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) this.loginFormRoot.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            new FadeIn(parent).setSpeed(0.4).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
package ru.sapteh.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.model.User;
import ru.sapteh.service.UserService;

import java.io.IOException;
import java.util.Objects;


public class MainController {

    private final UserService userService;

    @FXML
    private Label alertText;
    @FXML
    private TextField firstNameText;
    @FXML
    private TextField lastNameText;
    @FXML
    private TextField ageText;

    public MainController() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        this.userService = new UserService(factory);
    }

    public void addedButton(ActionEvent activeEvent) {
        userService.save(new User(
                firstNameText.getText(),
                lastNameText.getText(),
                Integer.parseInt(ageText.getText())
        ));
        alertText.setTextFill(Color.GREEN);
        alertText.setText("Вы успешно добавили пользователя");
    }
    public void exitButton(ActionEvent activeEvent) {
        final Button source = (Button) activeEvent.getSource();
        source.getScene().getWindow().hide();
    }

    public void inputButton(ActionEvent actionEvent) {
        Button input = (Button) actionEvent.getSource();
        input.getScene().getWindow().hide();

        Stage stage = new Stage();

        Parent root = null;

        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/findUser.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setTitle("Find user");
        assert root != null;
        stage.setScene(new Scene(root));
        stage.show();}
}

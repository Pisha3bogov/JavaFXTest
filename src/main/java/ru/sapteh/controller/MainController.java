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
import ru.sapteh.model.Admin;
import ru.sapteh.service.AdminService;
import ru.sapteh.service.UserService;

import java.io.IOException;
import java.util.Objects;


public class MainController {

    private final AdminService adminService;

    @FXML
    public TextField loginTextField;

    @FXML
    public TextField passwordTextField;

    @FXML
    private Label alertText;

    public MainController() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        this.adminService = new AdminService(factory);
    }

    public void exitButton(ActionEvent activeEvent) {
        final Button source = (Button) activeEvent.getSource();
        source.getScene().getWindow().hide();
    }

    public void inputButton(ActionEvent actionEvent) {
        Admin admin = new Admin(loginTextField.getText(), passwordTextField.getText());
        final boolean isExists = adminService.findAll().stream().anyMatch(admin::equals);

        if(isExists) {
            Button input = (Button) actionEvent.getSource();
            input.getScene().getWindow().hide();

            openScene();
        } else {
            alertText.setTextFill(Color.RED);
            alertText.setText("Логин или пароль введены не верно");
            passwordTextField.clear();
        }
}

    public void openScene() {
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

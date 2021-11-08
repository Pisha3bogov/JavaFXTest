package ru.sapteh.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.model.User;
import ru.sapteh.service.UserService;

public class FindUserController {

    private final UserService userService;

    private final ObservableList<User> users = FXCollections.observableArrayList();

    public FindUserController() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        this.userService = new UserService(factory);
    }

    @FXML
    public TableView<User> userTableView;

    @FXML
    public TableColumn<User, Integer> idColumn;

    @FXML
    public TableColumn<User, String> firstNameColumn;

    @FXML
    public TableColumn<User,String> lastNameColumn;

    @FXML
    public TableColumn<User,Integer> ageColumn;

    @FXML
    public Label labelId;


    @FXML
    public TextField firstNameTextField;

    @FXML
    public TextField lastNameTextField;

    @FXML
    public TextField ageTextField;

    @FXML
    private void initialize(){
        initDate();

        idColumn.setCellValueFactory(u -> new SimpleObjectProperty<>(u.getValue().getId()));
        firstNameColumn.setCellValueFactory(u -> new SimpleObjectProperty<>(u.getValue().getFirst_name()));
        lastNameColumn.setCellValueFactory(u -> new SimpleObjectProperty<>(u.getValue().getLast_name()));
        ageColumn.setCellValueFactory(u -> new SimpleObjectProperty<>(u.getValue().getAge()));
        userTableView.setItems(users);
        userTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                {listenerTabUserDetails(newValue);}
        );
    }

    private void listenerTabUserDetails(User user){
        if(user != null) {
            labelId.setText(String.valueOf(user.getId()));
            firstNameTextField.setText(user.getFirst_name());
            lastNameTextField.setText(user.getLast_name());
            ageTextField.setText(String.valueOf(user.getAge()));
        } else {
            labelId.setText("");
            firstNameColumn.setText((""));
            lastNameColumn.setText("");
            ageColumn.setText("");
        }
    }

    private void initDate() {
        users.addAll(userService.findAll());
    }

    public void deleteButton(ActionEvent actionEvent) {
        final User user = userTableView.getSelectionModel().getSelectedItem();
        userService.delete(user);
        userTableView.getItems().remove(user);
        clearTextField();
    }

    public void updateButton(ActionEvent actionEvent) {
        int selectedIndex = userTableView.getSelectionModel().getSelectedIndex();
        final User user = userTableView.getSelectionModel().getSelectedItem();
        user.setFirst_name(firstNameTextField.getText());
        user.setLast_name(lastNameTextField.getText());
        user.setAge(Integer.parseInt(ageTextField.getText()));
        userTableView.getItems().set(selectedIndex, user);
        userService.update(user);
        clearTextField();
    }

    public void saveButton(ActionEvent actionEvent) {
        User user = new User(firstNameTextField.getText(),
                lastNameTextField.getText(),
                Integer.parseInt(ageTextField.getText()));
        userService.save(user);
        userTableView.getItems().add(user);
        clearTextField();
    }

    private void clearTextField () {
        firstNameTextField.clear();
        lastNameTextField.clear();
        ageTextField.clear();
        labelId.setText("");
    }
}

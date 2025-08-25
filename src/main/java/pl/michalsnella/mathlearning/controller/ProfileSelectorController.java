package pl.michalsnella.mathlearning.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pl.michalsnella.mathlearning.model.user.Avatar;
import pl.michalsnella.mathlearning.model.user.UserProfile;
import pl.michalsnella.mathlearning.service.user.UserService;
import pl.michalsnella.mathlearning.utils.SceneManager;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ProfileSelectorController {

    @FXML private ListView<UserProfile> profilesList;
    @FXML private Button selectButton, deleteButton, refreshButton, createButton;
    @FXML private ImageView avatarPreview;
    @FXML private Label profileName, profileDetails;

    private final UserService userService = new UserService();

    @FXML
    public void initialize() {
        profilesList.setCellFactory(list -> new ListCell<>() {
            @Override protected void updateItem(UserProfile item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.displayLabel());
            }
        });

        profilesList.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> {
            if (sel != null) showPreview(sel);
        });

        loadProfiles();
        updateButtonsState();
    }


    private void loadProfiles() {
        try {
            List<UserProfile> users = userService.listUsers();
            profilesList.setItems(FXCollections.observableArrayList(users));
            if (!users.isEmpty()) profilesList.getSelectionModel().selectFirst();
        } catch (IOException e) {
            showError("Nie udało się wczytać profili: " + e.getMessage());
            profilesList.setItems(FXCollections.observableArrayList());
        }
    }


    private void showPreview(UserProfile u) {
        profileName.setText(u.displayLabel());
        profileDetails.setText("Punkty: " + u.getPoints() + "\nID: " + u.getId());

        try {
            Avatar av = u.getAvatar() != null ? u.getAvatar() : Avatar.FOX;
            Image img = new Image(getClass().getResourceAsStream(av.resourcePath()), 160, 160, true, true);
            avatarPreview.setImage(img);
        } catch (Exception ex) {
            avatarPreview.setImage(null);
        }

        updateButtonsState();
    }


    private void updateButtonsState() {
        boolean hasSel = profilesList.getSelectionModel().getSelectedItem() != null;
        selectButton.setDisable(!hasSel);
        deleteButton.setDisable(!hasSel);
    }


    @FXML
    private void onRefresh() {
        loadProfiles();
    }


    @FXML
    private void onDelete() {
        UserProfile sel = profilesList.getSelectionModel().getSelectedItem();
        if (sel == null) return;

        Alert a = new Alert(Alert.AlertType.CONFIRMATION,
                "Czy na pewno usunąć profil „" + sel.getName() + "”?",
                ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> res = a.showAndWait();
        if (res.isPresent() && res.get() == ButtonType.OK) {
            try {
                userService.deleteUser(sel.getId());
                loadProfiles();
                avatarPreview.setImage(null);
                profileName.setText("");
                profileDetails.setText("");
            } catch (IOException e) {
                showError("Nie udało się usunąć profilu: " + e.getMessage());
            }
        }
    }


    @FXML
    private void onCreateNew() {
        try {
            NewProfileDialog.show(userService, profilesList.getScene().getWindow());
            loadProfiles();
        } catch (IOException e) {
            showError("Błąd podczas tworzenia profilu: " + e.getMessage());
        }
    }


    @FXML
    private void onSelect() {
        UserProfile sel = profilesList.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        try {
            userService.setActive(sel);
            SceneManager.switchTo("/fxml/main_menu.fxml");
        } catch (IOException e) {
            showError("Nie udało się ustawić aktywnego profilu: " + e.getMessage());
        } catch (Exception e) {
            showError("Nie udało się przełączyć sceny: " + e.getMessage());
        }
    }


    private void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).showAndWait();
    }
}

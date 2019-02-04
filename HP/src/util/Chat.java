package util;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;

/**
 * Created by Jenny on 13.02.2016.
 */
public class Chat {

    private TextArea chat;
    private TextField input;
    private VBox all;

    private Button icon;

    private HBox icons;

    private SimpleBooleanProperty notify;
    private boolean out;


    private Image buttonNotify, buttonImage;
    private ImagePattern notified, notNotified;

    public Chat() {
        this.chat = new TextArea();
        this.input = new TextField();
        this.out = false;
        this.notify = new SimpleBooleanProperty();

        initButtonImages();
        createIcon();
        chatArea();
        notificationListener();
        buttonHandle();
        icon.setBackground(new Background(new BackgroundFill(notNotified,CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void initButtonImages() {
        buttonImage = new Image((getClass().getResource("/hp/media/chat.png").toExternalForm()));
        notNotified = new ImagePattern(buttonImage);
        buttonNotify = new Image((getClass().getResource("/hp/media/chat_1.png").toExternalForm()));
        notified = new ImagePattern(buttonNotify);
    }

    public void createIcon() {
        this.icon = new Button();
        icon.setPrefSize(50,50);
        icon.setId("buttonChat");
    }

    public void notificationListener() {
        notify.addListener(( observable, oldValue, newValue ) -> {
            System.out.println("LISTENER AUFGERUFEN");
            if(notify.getValue()) {
                all.setVisible(true);
            }
        });
    }

    public void buttonHandle() {
        icon.setOnMouseClicked(e -> {
            if(out) {
                all.setVisible(false);
                setOut(false);
                } else {
                all.setVisible(true);
                setOut(true);
                setNotify(false);
                }
        });
    }

    public void chatArea() {
        chat.setEditable(false);
        chat.setMaxWidth(250);
        chat.setPrefHeight(450);
        chat.setId("chat");
        chat.setWrapText(true);



        input.setId("input");
        input.setMaxWidth(250);

        this.all = new VBox(chat, input);
    }

    /* Getter & Setter */
    public Button getIcon() {
        return icon;
    }

    public void setIcon(Button icon) {
        this.icon = icon;
    }

    public TextArea getChat() {
        return chat;
    }

    public void setChat(TextArea chat) {
        this.chat = chat;
    }

    public TextField getInput() {
        return input;
    }

    public void setInput(TextField input) {
        this.input = input;
    }

    public boolean getNotify() {
        return notify.get();
    }

    public SimpleBooleanProperty notifyProperty() {
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify.set(notify);
    }

    public VBox getAll() {
        return all;
    }

    public void setAll(VBox all) {
        this.all = all;
    }

    public boolean isOut() {
        return out;
    }

    public void setOut(boolean out) {
        this.out = out;
    }

    public HBox getIcons() {
        return icons;
    }

    public void setIcons(HBox icons) {
        this.icons = icons;
    }

    public Image getButtonNotify() {
        return buttonNotify;
    }

    public void setButtonNotify(Image buttonNotify) {
        this.buttonNotify = buttonNotify;
    }

    public Image getButtonImage() {
        return buttonImage;
    }

    public void setButtonImage(Image buttonImage) {
        this.buttonImage = buttonImage;
    }

    public ImagePattern getNotified() {
        return notified;
    }

    public void setNotified(ImagePattern notified) {
        this.notified = notified;
    }

    public ImagePattern getNotNotified() {
        return notNotified;
    }

    public void setNotNotified(ImagePattern notNotified) {
        this.notNotified = notNotified;
    }
}

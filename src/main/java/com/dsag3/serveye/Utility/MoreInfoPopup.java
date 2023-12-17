package com.dsag3.serveye.Utility;

import javafx.scene.control.Control;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import java.util.LinkedList;
import javafx.scene.control.Tooltip;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.util.Duration;

public class MoreInfoPopup {
    public static void open(StackPane stackPane) {
        // Create Fade Elements
        Pane overlay = new Pane(); // overlay
        BorderPane popup = new BorderPane(); // popup
        BorderPane popupTitlebar = new BorderPane(); // titlebar
        Label popupTitle = new Label("FAQs"); // titlebar label
        Button exitButton = new Button("X"); // exit
        ScrollPane faqScroll = new ScrollPane(); // scrollpane
        VBox faqScrollContent = new VBox(); // content container
        LinkedList<Label> faqLabels = new LinkedList<>(); // faq questions
        faqLabels.add(new Label("What is Serveye?"));
        faqLabels.add(new Label("How does Serveye work?"));
        faqLabels.add(new Label("Is Serveye free to use?"));
        faqLabels.add(new Label("Are my information anonymous?"));
        faqLabels.add(new Label("Can I see the results of the surveys?"));
        LinkedList<Label> faqInfo = new LinkedList<>(); // faq answers
        faqInfo.add(new Label("\"Serveye\" is a survey app designed for local establishments such as restaurants and other services. It allows users to provide feedback and opinions on their experiences, helping businesses improve and meet customer expectations."));
        faqInfo.add(new Label("Serveye connects users with local establishments through surveys tailored to each business. Users can share their thoughts on various aspects like service quality, ambiance, and more. Businesses, in turn, can access valuable insights to enhance customer satisfaction."));
        faqInfo.add(new Label("Yes, Serveye is entirely free for users. You can download the app, participate in surveys, and provide feedback without any cost."));
        faqInfo.add(new Label("Yes, user information and responses on Serveye are kept confidential and anonymous. Your privacy is important to us, and we ensure that your feedback is used only for the purpose of improving local businesses."));
        faqInfo.add(new Label("Serveye aims to foster transparency. While individual responses remain private, businesses may choose to share aggregated survey results with the community. This helps create an open feedback loop between customers and establishments."));
        FlowPane popupFooter = new FlowPane(); // footer
        Button email = new Button(); // email
        Button phone = new Button(); // phone
        Label workingHours = new Label("Support Hours: Monday to Friday, 9:00 AM to 5:00 PM"); // working hours
        // Add Styling
        overlay.getStyleClass().add("overlay");
        popup.setId("popup");
        popup.setMaxHeight((stackPane.getBoundsInLocal().getHeight())*0.85);
        popupTitlebar.setId("popup-titlebar");
        popupTitle.setId("popup-titlebar-label");
        exitButton.setId("popup-titlebar-exit");
        faqScroll.setId("faq-scroll");
        faqScroll.setFitToHeight(true);
        faqScroll.setFitToWidth(true);
        faqScrollContent.setId("scrollpane-content");
        faqScrollContent.setSpacing(12);
        for(int i = 0; i < faqLabels.size(); i++) {
            faqLabels.get(i).getStyleClass().add("faq-title");
            faqInfo.get(i).getStyleClass().add("faq-info");
            faqInfo.get(i).setWrapText(true);
            faqInfo.get(i).setMaxWidth(Double.MAX_VALUE);
            faqInfo.get(i).setMinHeight(Control.USE_PREF_SIZE);
        }
        popupFooter.setId("popup-contacts");
        popupFooter.setHgap(13);
        Tooltip emailTooltip = new Tooltip("Copy email to clipboard");
        emailTooltip.setShowDelay(Duration.millis(100));
        email.getStyleClass().add("popup-contacts-buttons");
        email.setId("popup-contacts-email");
        email.setText("");
        email.setTooltip(emailTooltip);
        Tooltip phoneTooltip = new Tooltip("Copy phone number to clipboard");
        phoneTooltip.setShowDelay(Duration.millis(100));
        phone.getStyleClass().add("popup-contacts-buttons");
        phone.setId("popup-contacts-phone");
        phone.setText("");
        phone.setTooltip(phoneTooltip);
        workingHours.setId("popup-contacts-service");
        // Add components
        popupTitlebar.setLeft(popupTitle);
        popupTitlebar.setRight(exitButton);
        popup.setTop(popupTitlebar);
        for(int i = 0; i < faqLabels.size(); i++) {
            faqScrollContent.getChildren().add(faqLabels.get(i));
            faqScrollContent.getChildren().add(faqInfo.get(i));
        }
        faqScroll.setContent(faqScrollContent);
        popup.setCenter(faqScroll);
        popupFooter.getChildren().add(email);
        popupFooter.getChildren().add(phone);
        popupFooter.getChildren().add(workingHours);
        popup.setBottom(popupFooter);
        stackPane.getChildren().add(overlay);
        stackPane.getChildren().add(popup);
        // Add Event Listeners
        // Copy to clipboard
        email.setOnAction(event -> {
            copyToClipboard("serveye23@gmail.com");
        });
        phone.setOnAction(event -> {
            copyToClipboard("639603823986");
        });
        // Close the popup
        overlay.setOnMouseClicked(event -> {
            stackPane.getChildren().remove(popup);
            stackPane.getChildren().remove(overlay);
        });
        exitButton.setOnAction(event -> {
            stackPane.getChildren().remove(popup);
            stackPane.getChildren().remove(overlay);
        });
    }

    static void copyToClipboard(String info) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(info);
        clipboard.setContent(content);
    }
}

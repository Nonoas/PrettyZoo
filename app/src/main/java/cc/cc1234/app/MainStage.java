package cc.cc1234.app;

import cc.cc1234.app.controller.MainViewController;
import cc.cc1234.app.util.FXMLs;
import github.nonoas.jfx.flat.ui.stage.AppStage;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;

/**
 * @author Nonoas
 * @version 1.0
 * @date 2025/7/21
 * @since 3.0.0
 */
public class MainStage extends AppStage{
    private static final MainStage INSTANCE = new MainStage();

    private MainStage() {
        setMinWidth(650);
        setMinHeight(540);
        getStage().setWidth(720);
        getStage().setHeight(560);

        MainViewController controller = FXMLs.getController("fxml/MainView.fxml");
        final BorderPane stackPane = controller.getRootStackPane();
        ToolBar toolBar = new ToolBar();
        toolBar.setMinHeight(35);
        registryDragger(toolBar);

        stackPane.setTop(toolBar);

        setContentView(stackPane);
        getStage().setOnShown(e -> {
            controller.checkForUpdate();
            controller.bindShortcutKey();
        });
    }

    public void addStylesheet(String stylesheet) {
        getStage().getScene().getStylesheets().add(stylesheet);
    }

    public static MainStage getInstance() {
        return INSTANCE;
    }
}

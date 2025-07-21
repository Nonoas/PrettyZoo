package cc.cc1234.app.context;

import javafx.scene.layout.BorderPane;

public class RootPaneContext {

    private static volatile BorderPane root;

    public static BorderPane get() {
        return root;
    }

    public static void set(BorderPane pane) {
        root = pane;
    }
}

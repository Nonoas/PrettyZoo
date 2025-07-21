package cc.cc1234;

import cc.cc1234.app.MainStage;
import cc.cc1234.app.context.HostServiceContext;
import cc.cc1234.app.context.PrimaryStageContext;
import cc.cc1234.app.facade.PrettyZooFacade;
import cc.cc1234.specification.config.PrettyZooConfigRepository;
import github.nonoas.jfx.flat.ui.theme.LightTheme;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.Taskbar;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

public class PrettyZooApplication extends Application {

    private static final PrettyZooFacade facade = new PrettyZooFacade();

    public static void main(String[] args) {
        facade.initZookeeperSystemProperties();
        initIconImage();
        Application.launch(args);
    }

    private static void initIconImage() {
        getIconStream()
                .ifPresent(inputStream -> {
                    try {
                        Taskbar.getTaskbar().setIconImage(ImageIO.read(inputStream));
                    } catch (UnsupportedOperationException e) {
                        // ignore not support platform, such as windows
                    } catch (IOException e) {
                        // ignore icon load failed
                    }
                });
    }

    private static Optional<InputStream> getIconStream() {
        InputStream stream = PrettyZooApplication.class.getClassLoader()
                .getSystemResourceAsStream("assets/logo/prettyzoo-logo.png");
        return Optional.ofNullable(stream);
    }

    @Override
    public void start(Stage primaryStage) {
        v2(primaryStage);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        facade.closeAll();
    }

    private void v2(Stage primaryStage) {
        primaryStage.close();
        MainStage mainStage = MainStage.getInstance();
        PrimaryStageContext.set(mainStage.getStage());
        HostServiceContext.set(getHostServices());

        String theme = facade.getThemeFromConfig();
        setUserAgentStylesheet(new LightTheme().getUserAgentStylesheet());
        mainStage.addStylesheet("assets/css/default/style.css");
        if (Objects.equals(theme, PrettyZooConfigRepository.THEME_DARK)) {
            mainStage.addStylesheet("assets/css/dark/style.css");
        }
        mainStage.setTitle("PrettyZoo");
        getIconStream().ifPresent(stream -> mainStage.addIcons(Collections.singleton(new Image(stream))));
        mainStage.show();
    }

}

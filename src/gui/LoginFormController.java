package gui;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class LoginFormController {
    public ContextMenu ar;
    public CheckBox s;
    public Button forgetPasswordButton;
    ContextMenu contextMenu = new ContextMenu();

    @FXML
    void leftMouseClick()
    {
        System.out.println("Clicl");
        boolean state=s.isSelected();
        s.setSelected(!state);
        Bounds boundsInScreen = s.localToScreen(s.getBoundsInLocal());
        //
        contextMenu.show(s,boundsInScreen.getCenterX(),boundsInScreen.getCenterY());
        //contextMenu.show();
    }


    public void leftMouseDrageEnter(MouseEvent mouseEvent) {

        double lx=305.0, ly=172.0;

        forgetPasswordButton.setTranslateX(ThreadLocalRandom.current().nextDouble(-lx+50, 550-lx));
        forgetPasswordButton.setTranslateY(ThreadLocalRandom.current().nextDouble(-ly+50, 400-ly));
        double x=forgetPasswordButton.getTranslateX(),y=forgetPasswordButton.getTranslateY();
        System.out.println(x+", "+y);
    }
    void x1(MouseEvent mouseEvent)
    {
        System.out.println("x");
    }
    @FXML
    void initialize() {
        // create menuitems
        javafx.scene.control.MenuItem menuItem1 = new javafx.scene.control.MenuItem("menu item 1");
        javafx.scene.control.MenuItem menuItem2 = new javafx.scene.control.MenuItem("menu item 2");
        javafx.scene.control.MenuItem menuItem3 = new javafx.scene.control.MenuItem("menu item 3");
       // menuItem1.addEventHandler(EventType.ROOT,x1);
        // add menu items to menu
        contextMenu.getItems().add(menuItem1);
        contextMenu.getItems().add(menuItem2);
        contextMenu.getItems().add(menuItem3);
        s.setContextMenu(contextMenu);
    }

    public void leftMouseExit(MouseEvent mouseEvent) throws AWTException {

        //Scene scene = forgetPasswordButton.getScene();
        //doublea a=new Robot().mouseMove(;
        //Bounds boundsInScreen = forgetPasswordButton.localToScreen(forgetPasswordButton.getBoundsInLocal());
       // new Robot().mouseMove((int)(boundsInScreen.getCenterX()), (int)(boundsInScreen.getCenterY()));

       // scene.
        //forgetPasswordButton.setTranslateX(0);
        //forgetPasswordButton.setTranslateY(0);
    }
}

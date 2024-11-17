package jsi;

import x.XScenario;
import x.XScene;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class JSIScene extends XScene {
    protected JSIScene(XScenario scenario) {
        super(scenario);
    }

    public abstract void handleMousePress(MouseEvent e);

    public abstract void handleMouseDrag(MouseEvent e);

    public abstract void handleMouseRelease(MouseEvent e);

    public abstract void handleKeyDown(KeyEvent e);

    public abstract void handleKeyUp(KeyEvent e);

    public abstract void updateSupportObjects();

    public abstract void renderWorldObjects(Graphics2D g2);

    public abstract void renderScreenObjects(Graphics2D g2);
}

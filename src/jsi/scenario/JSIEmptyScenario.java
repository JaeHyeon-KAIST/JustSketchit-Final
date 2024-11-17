package jsi.scenario;

import jsi.JSIScene;
import x.XApp;
import x.XScenario;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class JSIEmptyScenario extends XScenario {
    // singleton pattern
    private static JSIEmptyScenario mSingleton = null;

    public static JSIEmptyScenario getSingleton() {
        assert (JSIEmptyScenario.mSingleton != null);
        return mSingleton;
    }

    public static JSIEmptyScenario createSingleton(XApp app) {
        assert (JSIEmptyScenario.mSingleton == null);
        JSIEmptyScenario.mSingleton = new JSIEmptyScenario(app);
        return JSIEmptyScenario.mSingleton;
    }

    private JSIEmptyScenario(XApp app) {
        super(app);
    }

    @Override
    protected void addScenes() {
        this.addScene(EmptyScene.createSingleton(this));
    }

    public static class EmptyScene extends JSIScene {
        //  singleton
        private static EmptyScene mSingleton = null;

        public static EmptyScene getSingleton() {
            assert (EmptyScene.mSingleton != null);
            return EmptyScene.mSingleton;
        }

        public static EmptyScene createSingleton(XScenario scenario) {
            assert (EmptyScene.mSingleton == null);
            EmptyScene.mSingleton = new EmptyScene(scenario);
            return EmptyScene.mSingleton;
        }

        private EmptyScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {

        }

        @Override
        public void handleMouseDrag(MouseEvent e) {
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
        }

        @Override
        public void updateSupportObjects() {

        }

        @Override
        public void renderWorldObjects(Graphics2D g2) {

        }

        @Override
        public void renderScreenObjects(Graphics2D g2) {

        }

        @Override
        public void getReady() {

        }

        @Override
        public void wrapUp() {

        }
    }
}

package jsi.scenario;

import jsi.JSIApp;
import jsi.JSIScene;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class JSIColorReadyScenario extends XScenario {
    // singleton pattern
    private static JSIColorReadyScenario mSingleton = null;

    public static JSIColorReadyScenario getSingleton() {
        assert (JSIColorReadyScenario.mSingleton != null);
        return mSingleton;
    }

    public static JSIColorReadyScenario createSingleton(XApp app) {
        assert (JSIColorReadyScenario.mSingleton == null);
        JSIColorReadyScenario.mSingleton = new JSIColorReadyScenario(app);
        return JSIColorReadyScenario.mSingleton;
    }

    private JSIColorReadyScenario(XApp app) {
        super(app);
    }

    @Override
    protected void addScenes() {
        this.addScene(ColorReadyScene.createSingleton(this));
    }

    public static class ColorReadyScene extends JSIScene {
        //  singleton
        private static ColorReadyScene mSingleton = null;

        public static ColorReadyScene getSingleton() {
            assert (ColorReadyScene.mSingleton != null);
            return ColorReadyScene.mSingleton;
        }

        public static ColorReadyScene createSingleton(XScenario scenario) {
            assert (ColorReadyScene.mSingleton == null);
            ColorReadyScene.mSingleton = new ColorReadyScene(scenario);
            return ColorReadyScene.mSingleton;
        }

        private ColorReadyScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
            JSIApp jsi = (JSIApp) this.mScenario.getApp();
            XCmdToChangeScene.execute(jsi, JSIColorScenario.ColorScene.getSingleton(), this.mReturnScene);
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
            JSIApp jsi = (JSIApp) this.mScenario.getApp();
            int code = e.getKeyCode();
            if (code == KeyEvent.VK_C) {
                XCmdToChangeScene.execute(jsi, this.mReturnScene, null);
            }
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

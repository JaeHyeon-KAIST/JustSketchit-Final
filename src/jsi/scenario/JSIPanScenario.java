package jsi.scenario;

import jsi.JSIApp;
import jsi.JSIScene;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class JSIPanScenario extends XScenario {
    // singleton pattern
    private static JSIPanScenario mSingleton = null;

    public static JSIPanScenario getSingleton() {
        assert (JSIPanScenario.mSingleton != null);
        return mSingleton;
    }

    public static JSIPanScenario createSingleton(XApp app) {
        assert (JSIPanScenario.mSingleton == null);
        JSIPanScenario.mSingleton = new JSIPanScenario(app);
        return JSIPanScenario.mSingleton;
    }

    private JSIPanScenario(XApp app) {
        super(app);
    }

    @Override
    protected void addScenes() {
        this.addScene(PanScene.createSingleton(this));
    }

    public static class PanScene extends JSIScene {
        //  singleton
        private static PanScene mSingleton = null;

        public static PanScene getSingleton() {
            assert (PanScene.mSingleton != null);
            return PanScene.mSingleton;
        }

        public static PanScene createSingleton(XScenario scenario) {
            assert (PanScene.mSingleton == null);
            PanScene.mSingleton = new PanScene(scenario);
            return PanScene.mSingleton;
        }

        private PanScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {

        }

        @Override
        public void handleMouseDrag(MouseEvent e) {
            JSIApp jsi = (JSIApp) this.mScenario.getApp();
            jsi.getXform().translateTo(e.getPoint());
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            JSIApp jsi = (JSIApp) this.mScenario.getApp();
            jsi.getXform().setStartScreenPt(null);
            XCmdToChangeScene.execute(jsi, JSIPanReadyScenario.PanReadyScene.getSingleton(), this.mReturnScene);
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            JSIApp jsi = (JSIApp) this.mScenario.getApp();
            int code = e.getKeyCode();
            if (code == KeyEvent.VK_CONTROL) {
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

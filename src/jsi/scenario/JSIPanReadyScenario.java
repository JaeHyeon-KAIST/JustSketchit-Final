package jsi.scenario;

import jsi.JSIApp;
import jsi.JSIScene;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class JSIPanReadyScenario extends XScenario {
    // singleton pattern
    private static JSIPanReadyScenario mSingleton = null;

    public static JSIPanReadyScenario getSingleton() {
        assert (JSIPanReadyScenario.mSingleton != null);
        return mSingleton;
    }

    public static JSIPanReadyScenario createSingleton(XApp app) {
        assert (JSIPanReadyScenario.mSingleton == null);
        JSIPanReadyScenario.mSingleton = new JSIPanReadyScenario(app);
        return JSIPanReadyScenario.mSingleton;
    }

    private JSIPanReadyScenario(XApp app) {
        super(app);
    }

    @Override
    protected void addScenes() {
        this.addScene(PanReadyScene.createSingleton(this));
    }

    public static class PanReadyScene extends JSIScene {
        //  singleton
        private static PanReadyScene mSingleton = null;

        public static PanReadyScene getSingleton() {
            assert (PanReadyScene.mSingleton != null);
            return PanReadyScene.mSingleton;
        }

        public static PanReadyScene createSingleton(XScenario scenario) {
            assert (PanReadyScene.mSingleton == null);
            PanReadyScene.mSingleton = new PanReadyScene(scenario);
            return PanReadyScene.mSingleton;
        }

        private PanReadyScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
            JSIApp jsi = (JSIApp) this.mScenario.getApp();
            jsi.getXform().setStartScreenPt(e.getPoint());
            XCmdToChangeScene.execute(jsi, JSIPanScenario.PanScene.getSingleton(), this.mReturnScene);
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

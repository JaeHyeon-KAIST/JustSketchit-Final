package jsi.scenario;

import jsi.JSIApp;
import jsi.JSIScene;
import jsi.cmd.JSICmdToCreateSelectionBox;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class JSISelectReadyScenario extends XScenario {
    // singleton pattern
    private static JSISelectReadyScenario mSingleton = null;

    public static JSISelectReadyScenario getSingleton() {
        assert (JSISelectReadyScenario.mSingleton != null);
        return mSingleton;
    }

    public static JSISelectReadyScenario createSingleton(XApp app) {
        assert (JSISelectReadyScenario.mSingleton == null);
        JSISelectReadyScenario.mSingleton = new JSISelectReadyScenario(app);
        return JSISelectReadyScenario.mSingleton;
    }

    private JSISelectReadyScenario(XApp app) {
        super(app);
    }

    @Override
    protected void addScenes() {
        this.addScene(SelectReadyScene.createSingleton(this));
    }

    public static class SelectReadyScene extends JSIScene {
        //  singleton
        private static SelectReadyScene mSingleton = null;

        public static SelectReadyScene getSingleton() {
            assert (SelectReadyScene.mSingleton != null);
            return SelectReadyScene.mSingleton;
        }

        public static SelectReadyScene createSingleton(XScenario scenario) {
            assert (SelectReadyScene.mSingleton == null);
            SelectReadyScene.mSingleton = new SelectReadyScene(scenario);
            return SelectReadyScene.mSingleton;
        }

        private SelectReadyScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
            JSIApp jsi = (JSIApp) this.mScenario.getApp();
            Point pt = e.getPoint();

            JSICmdToCreateSelectionBox.execute(jsi, pt);

            XCmdToChangeScene.execute(jsi, JSISelectScenario.SelectScene.getSingleton(), this.mReturnScene);
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
            int code = e.getKeyCode();
            if (code == KeyEvent.VK_SHIFT) {
                JSIApp jsi = (JSIApp) this.mScenario.getApp();
                if (jsi.getPtCurveMgr().getSelectedPtCurves().isEmpty()) {
                    XCmdToChangeScene.execute(jsi, JSIDefaultScenario.ReadyScene.getSingleton(), this);
                } else {
                    XCmdToChangeScene.execute(jsi, JSISelectedScenario.SelectedScene.getSingleton(), null);
                }
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

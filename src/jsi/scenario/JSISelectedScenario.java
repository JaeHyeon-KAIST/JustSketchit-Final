package jsi.scenario;

import jsi.JSIApp;
import jsi.JSICanvas2D;
import jsi.JSIScene;
import jsi.cmd.JSICmdToCreateCurPtCurve;
import jsi.cmd.JSICmdToHome;
import jsi.cmd.JSICmdToIncreaseStrokeWidthForCurPtCurve;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class JSISelectedScenario extends XScenario {
    // singleton pattern
    private static JSISelectedScenario mSingleton = null;

    public static JSISelectedScenario getSingleton() {
        assert (JSISelectedScenario.mSingleton != null);
        return mSingleton;
    }

    public static JSISelectedScenario createSingleton(XApp app) {
        assert (JSISelectedScenario.mSingleton == null);
        JSISelectedScenario.mSingleton = new JSISelectedScenario(app);
        return JSISelectedScenario.mSingleton;
    }

    private JSISelectedScenario(XApp app) {
        super(app);
    }

    @Override
    protected void addScenes() {
        this.addScene(SelectedScene.createSingleton(this));
    }

    public static class SelectedScene extends JSIScene {
        //  singleton
        private static SelectedScene mSingleton = null;

        public static SelectedScene getSingleton() {
            assert (SelectedScene.mSingleton != null);
            return SelectedScene.mSingleton;
        }

        public static SelectedScene createSingleton(XScenario scenario) {
            assert (SelectedScene.mSingleton == null);
            SelectedScene.mSingleton = new SelectedScene(scenario);
            return SelectedScene.mSingleton;
        }

        private SelectedScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
            JSIApp jsi = (JSIApp) this.mScenario.getApp();
            Point pt = e.getPoint();

            JSICmdToCreateCurPtCurve.execute(jsi, pt);

            XCmdToChangeScene.execute(jsi, JSIDrawScenario.DrawScene.getSingleton(), this);
        }

        @Override
        public void handleMouseDrag(MouseEvent e) {

        }

        @Override
        public void handleMouseRelease(MouseEvent e) {

        }

        @Override
        public void handleKeyDown(KeyEvent e) {
            JSIApp jsi = (JSIApp) this.mScenario.getApp();
            int code = e.getKeyCode();
            switch (code) {
                case KeyEvent.VK_SHIFT:
                    XCmdToChangeScene.execute(jsi, JSISelectReadyScenario.SelectReadyScene.getSingleton(), this);
                    break;
                case KeyEvent.VK_UP:
                    JSICmdToIncreaseStrokeWidthForCurPtCurve.execute(jsi, JSICanvas2D.STROKE_WIDTH_INCREMENT);
                    break;
                case KeyEvent.VK_DOWN:
                    JSICmdToIncreaseStrokeWidthForCurPtCurve.execute(jsi, -JSICanvas2D.STROKE_WIDTH_INCREMENT);
                    break;
            }
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            JSIApp jsi = (JSIApp) this.mScenario.getApp();
            int code = e.getKeyCode();
            switch (code) {
                case KeyEvent.VK_H:
                    JSICmdToHome.execute(jsi);
                    break;
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

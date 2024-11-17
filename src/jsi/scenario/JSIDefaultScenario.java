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
import java.awt.geom.Point2D;

public class JSIDefaultScenario extends XScenario {
    // singleton pattern
    private static JSIDefaultScenario mSingleton = null;

    public static JSIDefaultScenario getSingleton() {
        assert (JSIDefaultScenario.mSingleton != null);
        return mSingleton;
    }

    public static JSIDefaultScenario createSingleton(XApp app) {
        assert (JSIDefaultScenario.mSingleton == null);
        JSIDefaultScenario.mSingleton = new JSIDefaultScenario(app);
        return JSIDefaultScenario.mSingleton;
    }

    private JSIDefaultScenario(XApp app) {
        super(app);
    }

    @Override
    protected void addScenes() {
        this.addScene(JSIDefaultScenario.ReadyScene.createSingleton(this));
    }

    public static class ReadyScene extends JSIScene {
        //  singleton
        private static ReadyScene mSingleton = null;

        public static ReadyScene getSingleton() {
            assert (ReadyScene.mSingleton != null);
            return ReadyScene.mSingleton;
        }

        public static ReadyScene createSingleton(XScenario scenario) {
            assert (ReadyScene.mSingleton == null);
            ReadyScene.mSingleton = new ReadyScene(scenario);
            return ReadyScene.mSingleton;
        }

        private ReadyScene(XScenario scenario) {
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

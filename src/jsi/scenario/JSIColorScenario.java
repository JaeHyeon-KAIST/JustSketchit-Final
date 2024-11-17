package jsi.scenario;

import jsi.JSIApp;
import jsi.JSIPtCurve;
import jsi.JSIScene;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class JSIColorScenario extends XScenario {
    // singleton pattern
    private static JSIColorScenario mSingleton = null;

    public static JSIColorScenario getSingleton() {
        assert (JSIColorScenario.mSingleton != null);
        return mSingleton;
    }

    public static JSIColorScenario createSingleton(XApp app) {
        assert (JSIColorScenario.mSingleton == null);
        JSIColorScenario.mSingleton = new JSIColorScenario(app);
        return JSIColorScenario.mSingleton;
    }

    private JSIColorScenario(XApp app) {
        super(app);
    }

    @Override
    protected void addScenes() {
        this.addScene(ColorScene.createSingleton(this));
    }

    public static class ColorScene extends JSIScene {
        //  singleton
        private static ColorScene mSingleton = null;

        public static ColorScene getSingleton() {
            assert (ColorScene.mSingleton != null);
            return ColorScene.mSingleton;
        }

        public static ColorScene createSingleton(XScenario scenario) {
            assert (ColorScene.mSingleton == null);
            ColorScene.mSingleton = new ColorScene(scenario);
            return ColorScene.mSingleton;
        }

        private ColorScene(XScenario scenario) {
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
            JSIApp jsi = (JSIApp) this.mScenario.getApp();
            Point pt = e.getPoint();

            Color c = jsi.getColorChooser().calcColor(
                pt, jsi.getJSICanvas2D().getWidth(),
                jsi.getJSICanvas2D().getHeight()
            );

            if (c != null) {
                if (jsi.getPtCurveMgr().getSelectedPtCurves().isEmpty()) {
                    jsi.getJSICanvas2D().setCurColorForPtCurve(c);

                } else {
                    for (JSIPtCurve ptCurve : jsi.getPtCurveMgr().getSelectedPtCurves()) {
                        ptCurve.setColor(c);
                    }
                    jsi.getPtCurveMgr().getPtCurves().addAll(jsi.getPtCurveMgr().getSelectedPtCurves());
                    jsi.getPtCurveMgr().getSelectedPtCurves().clear();
                }
                XCmdToChangeScene.execute(jsi, JSIColorReadyScenario.ColorReadyScene.getSingleton(), JSIDefaultScenario.ReadyScene.getSingleton());
            } else {
                XCmdToChangeScene.execute(jsi, JSIColorReadyScenario.ColorReadyScene.getSingleton(), this.mReturnScene);
            }

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

package jsi.scenario;

import jsi.JSIApp;
import jsi.JSIScene;
import jsi.cmd.JSICmdToAddCurPtCurveToPtCurves;
import jsi.cmd.JSICmdToUpdateCurPtCurve;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class JSIDrawScenario extends XScenario {
    // singleton pattern
    private static JSIDrawScenario mSingleton = null;

    public static JSIDrawScenario getSingleton() {
        assert (JSIDrawScenario.mSingleton != null);
        return mSingleton;
    }

    public static JSIDrawScenario createSingleton(XApp app) {
        assert (JSIDrawScenario.mSingleton == null);
        JSIDrawScenario.mSingleton = new JSIDrawScenario(app);
        return JSIDrawScenario.mSingleton;
    }

    private JSIDrawScenario(XApp app) {
        super(app);
    }

    @Override
    protected void addScenes() {
        this.addScene(DrawScene.createSingleton(this));
    }

    public static class DrawScene extends JSIScene {
        //  singleton
        private static DrawScene mSingleton = null;

        public static DrawScene getSingleton() {
            assert (DrawScene.mSingleton != null);
            return DrawScene.mSingleton;
        }

        public static DrawScene createSingleton(XScenario scenario) {
            assert (DrawScene.mSingleton == null);
            DrawScene.mSingleton = new DrawScene(scenario);
            return DrawScene.mSingleton;
        }

        private DrawScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
        }

        @Override
        public void handleMouseDrag(MouseEvent e) {
            JSIApp jsi = (JSIApp) this.mScenario.getApp();
            Point pt = e.getPoint();
            JSICmdToUpdateCurPtCurve.execute(jsi, pt);
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            JSIApp jsi = (JSIApp) this.mScenario.getApp();
            JSICmdToAddCurPtCurveToPtCurves.execute(jsi);

            XCmdToChangeScene.execute(jsi, this.mReturnScene, null);
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

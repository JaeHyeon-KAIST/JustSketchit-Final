package jsi.scenario;

import jsi.JSIApp;
import jsi.JSIScene;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class JSIZoomRotateScenario extends XScenario {
    // singleton pattern
    private static JSIZoomRotateScenario mSingleton = null;

    public static JSIZoomRotateScenario getSingleton() {
        assert (JSIZoomRotateScenario.mSingleton != null);
        return mSingleton;
    }

    public static JSIZoomRotateScenario createSingleton(XApp app) {
        assert (JSIZoomRotateScenario.mSingleton == null);
        JSIZoomRotateScenario.mSingleton = new JSIZoomRotateScenario(app);
        return JSIZoomRotateScenario.mSingleton;
    }

    private JSIZoomRotateScenario(XApp app) {
        super(app);
    }

    @Override
    protected void addScenes() {
        this.addScene(ZoomRotateScene.createSingleton(this));
    }

    public static class ZoomRotateScene extends JSIScene {
        //  singleton
        private static ZoomRotateScene mSingleton = null;

        public static ZoomRotateScene getSingleton() {
            assert (ZoomRotateScene.mSingleton != null);
            return ZoomRotateScene.mSingleton;
        }

        public static ZoomRotateScene createSingleton(XScenario scenario) {
            assert (ZoomRotateScene.mSingleton == null);
            ZoomRotateScene.mSingleton = new ZoomRotateScene(scenario);
            return ZoomRotateScene.mSingleton;
        }

        private ZoomRotateScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {

        }

        @Override
        public void handleMouseDrag(MouseEvent e) {
            JSIApp jsi = (JSIApp) this.mScenario.getApp();
            jsi.getXform().zoomRotateTo(e.getPoint());
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            JSIApp jsi = (JSIApp) this.mScenario.getApp();
            jsi.getXform().setStartScreenPt(null);
            XCmdToChangeScene.execute(jsi, JSIZoomRotateReadyScenario.ZoomRotateReadyScene.getSingleton(), this.mReturnScene);
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            JSIApp jsi = (JSIApp) this.mScenario.getApp();
            int code = e.getKeyCode();
            if (code == KeyEvent.VK_ALT) {
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

package jsi.scenario;

import jsi.JSIApp;
import jsi.JSIScene;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class JSIZoomRotateReadyScenario extends XScenario {
    // singleton pattern
    private static JSIZoomRotateReadyScenario mSingleton = null;

    public static JSIZoomRotateReadyScenario getSingleton() {
        assert (JSIZoomRotateReadyScenario.mSingleton != null);
        return mSingleton;
    }

    public static JSIZoomRotateReadyScenario createSingleton(XApp app) {
        assert (JSIZoomRotateReadyScenario.mSingleton == null);
        JSIZoomRotateReadyScenario.mSingleton = new JSIZoomRotateReadyScenario(app);
        return JSIZoomRotateReadyScenario.mSingleton;
    }

    private JSIZoomRotateReadyScenario(XApp app) {
        super(app);
    }

    @Override
    protected void addScenes() {
        this.addScene(ZoomRotateReadyScene.createSingleton(this));
    }

    public static class ZoomRotateReadyScene extends JSIScene {
        //  singleton
        private static ZoomRotateReadyScene mSingleton = null;

        public static ZoomRotateReadyScene getSingleton() {
            assert (ZoomRotateReadyScene.mSingleton != null);
            return ZoomRotateReadyScene.mSingleton;
        }

        public static ZoomRotateReadyScene createSingleton(XScenario scenario) {
            assert (ZoomRotateReadyScene.mSingleton == null);
            ZoomRotateReadyScene.mSingleton = new ZoomRotateReadyScene(scenario);
            return ZoomRotateReadyScene.mSingleton;
        }

        private ZoomRotateReadyScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
            JSIApp jsi = (JSIApp) this.mScenario.getApp();
            jsi.getXform().setStartScreenPt(e.getPoint());
            XCmdToChangeScene.execute(jsi, JSIZoomRotateScenario.ZoomRotateScene.getSingleton(), this.mReturnScene);
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

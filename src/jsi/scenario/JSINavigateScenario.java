package jsi.scenario;

import jsi.JSIApp;
import jsi.JSICanvas2D;
import jsi.JSIScene;
import jsi.JSIXform;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;

public class JSINavigateScenario extends XScenario {
    // singleton pattern
    private static JSINavigateScenario mSingleton = null;

    public static JSINavigateScenario getSingleton() {
        assert (JSINavigateScenario.mSingleton != null);
        return mSingleton;
    }

    public static JSINavigateScenario createSingleton(XApp app) {
        assert (JSINavigateScenario.mSingleton == null);
        JSINavigateScenario.mSingleton = new JSINavigateScenario(app);
        return JSINavigateScenario.mSingleton;
    }

    private JSINavigateScenario(XApp app) {
        super(app);
    }

    @Override
    protected void addScenes() {
        this.addScene(PanReadyScene.createSingleton(this));
        this.addScene(PanScene.createSingleton(this));
        this.addScene(ZoomRotateReadyScene.createSingleton(this));
        this.addScene(ZoomRotateScene.createSingleton(this));
    }

    public static class PanReadyScene extends JSIScene {
        //  singleton
        private static JSINavigateScenario.PanReadyScene mSingleton = null;

        public static JSINavigateScenario.PanReadyScene getSingleton() {
            assert (JSINavigateScenario.PanReadyScene.mSingleton != null);
            return JSINavigateScenario.PanReadyScene.mSingleton;
        }

        public static JSINavigateScenario.PanReadyScene createSingleton(XScenario scenario) {
            assert (JSINavigateScenario.PanReadyScene.mSingleton == null);
            JSINavigateScenario.PanReadyScene.mSingleton = new JSINavigateScenario.PanReadyScene(scenario);
            return JSINavigateScenario.PanReadyScene.mSingleton;
        }

        private PanReadyScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
            JSIApp jsi = (JSIApp) this.mScenario.getApp();
            jsi.getXform().setStartScreenPt(e.getPoint());
            XCmdToChangeScene.execute(jsi, JSINavigateScenario.PanScene.getSingleton(), this.mReturnScene);
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

    public static class PanScene extends JSIScene {
        //  singleton
        private static JSINavigateScenario.PanScene mSingleton = null;

        public static JSINavigateScenario.PanScene getSingleton() {
            assert (JSINavigateScenario.PanScene.mSingleton != null);
            return JSINavigateScenario.PanScene.mSingleton;
        }

        public static JSINavigateScenario.PanScene createSingleton(XScenario scenario) {
            assert (JSINavigateScenario.PanScene.mSingleton == null);
            JSINavigateScenario.PanScene.mSingleton = new JSINavigateScenario.PanScene(scenario);
            return JSINavigateScenario.PanScene.mSingleton;
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
            XCmdToChangeScene.execute(jsi, JSINavigateScenario.PanReadyScene.getSingleton(), this.mReturnScene);
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
            JSINavigateScenario scenario = JSINavigateScenario.getSingleton();
            scenario.drawPanCrossHair(g2);
        }

        @Override
        public void getReady() {

        }

        @Override
        public void wrapUp() {

        }
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
            XCmdToChangeScene.execute(jsi, JSINavigateScenario.ZoomRotateScene.getSingleton(), this.mReturnScene);
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
            XCmdToChangeScene.execute(jsi, JSINavigateScenario.ZoomRotateReadyScene.getSingleton(), this.mReturnScene);
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


    private void drawZoomRotateCrossHair(Graphics2D g2) {
        double r = JSICanvas2D.CROSS_HAIR_RADIUS;
        Point ctr = JSIXform.PIVOT_Pt;
        Line2D hLine = new Line2D.Double(ctr.x - r, ctr.y, ctr.x + r, ctr.y);
        Line2D vLine = new Line2D.Double(ctr.x, ctr.y - r, ctr.x, ctr.y + r);

        g2.setColor(JSICanvas2D.COLOR_CROSS_HAIR);
        g2.setStroke(JSICanvas2D.STROKE_CROSS_HAIR);
        g2.draw(hLine);
        g2.draw(vLine);
    }

    private void drawPanCrossHair(Graphics2D g2) {
        JSIApp jsi = (JSIApp) this.getApp();
        Point penPt = jsi.getPenMarkMgr().getLastPenMark().getLastPt();
        Line2D.Double hLine = new Line2D.Double(0.0, penPt.y, jsi.getJSICanvas2D().getWidth(), penPt.y);
        Line2D.Double vLine = new Line2D.Double(penPt.x, 0.0, penPt.x, jsi.getJSICanvas2D().getHeight());

        g2.setColor(JSICanvas2D.COLOR_CROSS_HAIR);
        g2.setStroke(JSICanvas2D.STROKE_CROSS_HAIR);
        g2.draw(hLine);
        g2.draw(vLine);
    }
}

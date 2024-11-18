package jsi.scenario;

import jsi.JSIApp;
import jsi.JSICanvas2D;
import jsi.JSIScene;
import jsi.JSISelectionBox;
import jsi.cmd.JSICmdToDestroySelectionBox;
import jsi.cmd.JSICmdToUpdateSelectedPtCurves;
import jsi.cmd.JSICmdToUpdateSelectionBox;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class JSISelectScenario extends XScenario {
    // singleton pattern
    private static JSISelectScenario mSingleton = null;

    public static JSISelectScenario getSingleton() {
        assert (JSISelectScenario.mSingleton != null);
        return mSingleton;
    }

    public static JSISelectScenario createSingleton(XApp app) {
        assert (JSISelectScenario.mSingleton == null);
        JSISelectScenario.mSingleton = new JSISelectScenario(app);
        return JSISelectScenario.mSingleton;
    }

    private JSISelectScenario(XApp app) {
        super(app);
    }

    @Override
    protected void addScenes() {
        this.addScene(SelectScene.createSingleton(this));
    }

    public static class SelectScene extends JSIScene {
        //  singleton
        private static SelectScene mSingleton = null;

        public static SelectScene getSingleton() {
            assert (SelectScene.mSingleton != null);
            return SelectScene.mSingleton;
        }

        public static SelectScene createSingleton(XScenario scenario) {
            assert (SelectScene.mSingleton == null);
            SelectScene.mSingleton = new SelectScene(scenario);
            return SelectScene.mSingleton;
        }

        private SelectScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {

        }

        @Override
        public void handleMouseDrag(MouseEvent e) {
            JSIApp jsi = (JSIApp) this.mScenario.getApp();
            Point pt = e.getPoint();

            JSICmdToUpdateSelectionBox.execute(jsi, pt);
            JSICmdToUpdateSelectedPtCurves.execute(jsi);
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            JSIApp jsi = (JSIApp) this.mScenario.getApp();
            JSICmdToDestroySelectionBox.execute(jsi);
            XCmdToChangeScene.execute(jsi, JSISelectReadyScenario.SelectReadyScene.getSingleton(), this.mReturnScene);
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            int code = e.getKeyCode();
            if (code == KeyEvent.VK_SHIFT) {
                JSIApp jsi = (JSIApp) this.mScenario.getApp();
                JSICmdToDestroySelectionBox.execute(jsi);
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
            JSISelectScenario scenario = (JSISelectScenario) this.mScenario;
            scenario.drawSelectionBox(g2);
        }

        @Override
        public void getReady() {

        }

        @Override
        public void wrapUp() {

        }
    }

    // fields
    private JSISelectionBox mSelectionBox = null;

    public JSISelectionBox getSelectionBox() {
        return this.mSelectionBox;
    }

    public void setSelectionBox(JSISelectionBox selectionBox) {
        this.mSelectionBox = selectionBox;
    }

    public void drawSelectionBox(Graphics2D g2) {
        if (this.getSelectionBox() != null) {
            g2.setColor(JSICanvas2D.COLOR_SELECTION_BOX);
            g2.setStroke(JSICanvas2D.STROKE_SELECTION_BOX);
            g2.draw(this.getSelectionBox());
        }
    }
}

package jsi;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class JSIEventListener implements MouseListener, MouseMotionListener, KeyListener {

    private JSIApp mJSI = null;

    public JSIEventListener(JSIApp jsi) {
        this.mJSI = jsi;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (this.mJSI.getPenMarkMgr().handleMousePress(e)) {
            JSIScene curScene = (JSIScene) mJSI.getScenarioMgr().getCurScene();
            curScene.handleMousePress(e);
            mJSI.getJSICanvas2D().repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (this.mJSI.getPenMarkMgr().handleMouseDrag(e)) {
            JSIScene curScene = (JSIScene) mJSI.getScenarioMgr().getCurScene();
            curScene.handleMouseDrag(e);
            mJSI.getJSICanvas2D().repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (this.mJSI.getPenMarkMgr().handleMouseRelease(e)) {
            JSIScene curScene = (JSIScene) mJSI.getScenarioMgr().getCurScene();
            curScene.handleMouseRelease(e);
            mJSI.getJSICanvas2D().repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        JSIScene curScene = (JSIScene) mJSI.getScenarioMgr().getCurScene();
        curScene.handleKeyDown(e);
        mJSI.getJSICanvas2D().repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        JSIScene curScene = (JSIScene) mJSI.getScenarioMgr().getCurScene();
        curScene.handleKeyUp(e);
        mJSI.getJSICanvas2D().repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }


    @Override
    public void mouseClicked(MouseEvent e) {
    }


    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }


    @Override
    public void mouseMoved(MouseEvent e) {
    }
}

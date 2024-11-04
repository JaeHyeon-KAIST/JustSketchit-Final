import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class JSIEventListener implements MouseListener, MouseMotionListener, KeyListener {

    private JSI mJSI = null;

    public JSIEventListener(JSI jsi) {
        this.mJSI = jsi;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point pt = e.getPoint();
        JSIPtCurve ptCurve = null;
        switch (mJSI.getMode()) {
            case DRAW:
                Point2D.Double worldPt = mJSI.getXform().calPtFromScreenToWorld(pt);
                ptCurve = new JSIPtCurve(worldPt, mJSI.getJSICanvas2D().getCurColorForPtCurve(), mJSI.getJSICanvas2D().getCurStrokeForPtCurve());
                mJSI.getmPtCurveMgr().setCurPtCurve(ptCurve);
                break;
            case SELECT:
                JSISelectionBox selectionBox = new JSISelectionBox(pt);
                mJSI.setSelectionBox(selectionBox);
                break;
            case SELECTED:
                mJSI.setSelectedPtCurvePanStartPoint(mJSI.getXform().calPtFromScreenToWorld(pt));
                break;
            case PAN:
                mJSI.getXform().setStartScreenPt(pt);
                break;
            case ZOOM_ROTATE:
                mJSI.getXform().setStartScreenPt(pt);
                break;
        }
        mJSI.getJSICanvas2D().repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point pt = e.getPoint();
        JSIPtCurve curPtCurve = mJSI.getmPtCurveMgr().getCurPtCurve();
        switch (mJSI.getMode()) {
            case DRAW:
                if (curPtCurve != null) {
                    // check if the distansce between the new screen point and the last screen point is greater than the threshold.
                    int size = curPtCurve.getPts().size();
                    Point2D.Double lastWorldPt = curPtCurve.getPts().get(size - 1);
                    Point lastScreenPt = mJSI.getXform().calPtFromWorldToScreen(lastWorldPt);
                    if (pt.distance(lastScreenPt) < JSIPtCurve.MIN_DIST_BTWN_PTS) {
                        return;
                    }
                    //add the current mouse point to the current pen mark.
                    Point2D.Double worldPt = mJSI.getXform().calPtFromScreenToWorld(pt);
                    curPtCurve.addPt(worldPt);
                }
                break;
            case SELECT:
                mJSI.getSelectionBox().update(pt);
                mJSI.updateSelectedPtCurves();
                break;
            case SELECTED:
                mJSI.setSelectedPtCurvePanEndPoint(mJSI.getXform().calPtFromScreenToWorld(pt));
                break;
            case PAN:
                mJSI.getXform().translateTo(pt);
                break;
            case ZOOM_ROTATE:
                mJSI.getXform().zoomRotateTo(pt);
                break;
        }
        mJSI.getJSICanvas2D().repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        JSIPtCurve curPtCurve = mJSI.getmPtCurveMgr().getCurPtCurve();
        switch (mJSI.getMode()) {
            case DRAW:
                if (curPtCurve != null) {
                    if (curPtCurve.getPts().size() == 1) { //tap
                        mJSI.getmPtCurveMgr().getPtCurves().addAll(mJSI.getmPtCurveMgr().getSelectedPtCurves());
                        mJSI.getmPtCurveMgr().getSelectedPtCurves().clear();
                    } else if (curPtCurve.getPts().size() >= 2) {
                        mJSI.getmPtCurveMgr().getPtCurves().add(curPtCurve);
                    }
                }
                mJSI.getmPtCurveMgr().setCurPtCurve(null);
                break;
            case SELECT:
                mJSI.setSelectionBox(null);
                break;
            case SELECTED:
                for (JSIPtCurve ptCurve : mJSI.getmPtCurveMgr().getSelectedPtCurves()) {
                    ptCurve.translatePt(
                        mJSI.getSelectedPtCurvePanEndPoint().x - mJSI.getSelectedPtCurvePanStartPoint().x,
                        mJSI.getSelectedPtCurvePanEndPoint().y - mJSI.getSelectedPtCurvePanStartPoint().y
                    );
                }
                mJSI.setSelectedPtCurvePanStartPoint(null);
                mJSI.setSelectedPtCurvePanEndPoint(null);
                break;
            case PAN:
                mJSI.getXform().setStartScreenPt(null);
                break;
            case ZOOM_ROTATE:
                mJSI.getXform().setStartScreenPt(null);
                break;
            case COLOR:
                Point pt = e.getPoint();
                Color c = mJSI.getColorChooser().calcColor(pt, mJSI.getJSICanvas2D().getWidth(), mJSI.getJSICanvas2D().getHeight());
                if (c != null) {
                    if (mJSI.getmPtCurveMgr().getSelectedPtCurves().isEmpty()) {
                        mJSI.getJSICanvas2D().setCurColorForPtCurve(c);
                    } else {
                        for (JSIPtCurve ptCurve : mJSI.getmPtCurveMgr().getSelectedPtCurves()) {
                            ptCurve.setColor(c);
                        }
                        mJSI.getmPtCurveMgr().getPtCurves().addAll(mJSI.getmPtCurveMgr().getSelectedPtCurves());
                        mJSI.getmPtCurveMgr().getSelectedPtCurves().clear();
                    }
                }
                break;
        }
        mJSI.getJSICanvas2D().repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_SHIFT:
                mJSI.setMode(JSI.Mode.SELECT);
                System.out.println("SelectionMode ON");
                break;
            case KeyEvent.VK_CONTROL:
                mJSI.setMode(JSI.Mode.PAN);
                System.out.println("PanMode ON");
                break;
            case KeyEvent.VK_ALT:
                mJSI.setMode(JSI.Mode.ZOOM_ROTATE);
                System.out.println("RotateMode ON");
                break;
            case KeyEvent.VK_UP:
                if (mJSI.getmPtCurveMgr().getSelectedPtCurves().isEmpty()) {
                    mJSI.getJSICanvas2D().increaseStrokeWidthForCurPtCurve(JSICanvas2D.STROKE_WIDTH_INCREMENT);
                } else {
                    for (JSIPtCurve ptCurve : mJSI.getmPtCurveMgr().getSelectedPtCurves()) {
                        ptCurve.increaseStrokeWidth(JSICanvas2D.STROKE_WIDTH_INCREMENT);
                    }
                }
                break;
            case KeyEvent.VK_DOWN:
                if (mJSI.getmPtCurveMgr().getSelectedPtCurves().isEmpty()) {
                    mJSI.getJSICanvas2D().increaseStrokeWidthForCurPtCurve(-JSICanvas2D.STROKE_WIDTH_INCREMENT);
                } else {
                    for (JSIPtCurve ptCurve : mJSI.getmPtCurveMgr().getSelectedPtCurves()) {
                        ptCurve.increaseStrokeWidth(-JSICanvas2D.STROKE_WIDTH_INCREMENT);
                    }
                }
                break;
            case KeyEvent.VK_C:
                mJSI.setMode(JSI.Mode.COLOR);
                System.out.println("ColorMode ON");
                break;
        }
        mJSI.getJSICanvas2D().repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_SHIFT:
                if (mJSI.getmPtCurveMgr().getSelectedPtCurves().isEmpty()) {
                    mJSI.setMode(JSI.Mode.DRAW);
                } else {
                    mJSI.setMode(JSI.Mode.SELECTED);
                }
                System.out.println("SelectionMode OFF");
                mJSI.setSelectionBox(null);
                mJSI.getJSICanvas2D().repaint();
                break;
            case KeyEvent.VK_CONTROL:
                if (mJSI.getmPtCurveMgr().getSelectedPtCurves().isEmpty()) {
                    mJSI.setMode(JSI.Mode.DRAW);
                } else {
                    mJSI.setMode(JSI.Mode.SELECTED);
                }
                System.out.println("PanMode OFF");
                break;
            case KeyEvent.VK_ESCAPE:
                mJSI.getmPtCurveMgr().getPtCurves().addAll(mJSI.getmPtCurveMgr().getSelectedPtCurves());
                mJSI.getmPtCurveMgr().getSelectedPtCurves().clear();
                mJSI.getJSICanvas2D().repaint();
                mJSI.setMode(JSI.Mode.DRAW);
                break;
            case KeyEvent.VK_BACK_SPACE:
                mJSI.getmPtCurveMgr().getSelectedPtCurves().clear();
                mJSI.getJSICanvas2D().repaint();
                mJSI.setMode(JSI.Mode.DRAW);
                break;
            case KeyEvent.VK_ALT:
                if (mJSI.getmPtCurveMgr().getSelectedPtCurves().isEmpty()) {
                    mJSI.setMode(JSI.Mode.DRAW);
                } else {
                    mJSI.setMode(JSI.Mode.SELECTED);
                }
                System.out.println("RotateMode OFF");
                break;
            case KeyEvent.VK_H:
                mJSI.getXform().home();
                break;
            case KeyEvent.VK_C:
                if (mJSI.getmPtCurveMgr().getSelectedPtCurves().isEmpty()) {
                    mJSI.setMode(JSI.Mode.DRAW);
                } else {
                    mJSI.setMode(JSI.Mode.SELECTED);
                }
                System.out.println("ColorMode OFF");
                break;
        }
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

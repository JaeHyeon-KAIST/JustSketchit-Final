package jsi;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.JFrame;

public class JSI {
    //introduce mode and quasi-modes.
    public enum Mode {
        DRAW,
        SELECT,
        SELECTED,
        PAN,
        ZOOM_ROTATE,
        COLOR
    }

    // fields
    private Mode mMode = JSI.Mode.DRAW;

    public Mode getMode() {
        return this.mMode;
    }

    public void setMode(Mode mode) {
        this.mMode = mode;
    }

    private JFrame mFrame = null;
    private JSICanvas2D mCanvas2D = null;

    public JSICanvas2D getJSICanvas2D() {
        return this.mCanvas2D;
    }

    private Point2D.Double selectedPtCurvePanStartPoint = null;

    public Point2D.Double getSelectedPtCurvePanStartPoint() {
        return this.selectedPtCurvePanStartPoint;
    }

    public void setSelectedPtCurvePanStartPoint(Point2D.Double pt) {
        this.selectedPtCurvePanStartPoint = pt;
    }

    private Point2D.Double selectedPtCurvePanEndPoint = null;

    public Point2D.Double getSelectedPtCurvePanEndPoint() {
        return this.selectedPtCurvePanEndPoint;
    }

    public void setSelectedPtCurvePanEndPoint(Point2D.Double pt) {
        this.selectedPtCurvePanEndPoint = pt;
    }

    private JSISelectionBox mSelectionBox = null;

    public JSISelectionBox getSelectionBox() {
        return this.mSelectionBox;
    }

    public void setSelectionBox(JSISelectionBox selectionBox) {
        this.mSelectionBox = selectionBox;
    }

    private JSIXform mXform = null;

    public JSIXform getXform() {
        return this.mXform;
    }

    private JSIColorChooser mColorChooser = null;

    public JSIColorChooser getColorChooser() {
        return this.mColorChooser;
    }

    private JSIEventListener mEventListener = null;

    private JSIPtCurveMgr mPtCurveMgr = null;

    public JSIPtCurveMgr getmPtCurveMgr() {
        return this.mPtCurveMgr;
    }

    //constructor
    public JSI() {
        // create a JFrame instance and make it visible.
        this.mFrame = new JFrame("JustSketchIt");
        this.mFrame.setSize(800, 600);
        this.mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create JSICanvas2D and add it to the frame.
        this.mCanvas2D = new JSICanvas2D(this);
        this.mFrame.add(this.mCanvas2D);

        // add this as a mouse listener to the canvas.
        this.mEventListener = new JSIEventListener(this);
        this.mCanvas2D.addMouseListener(this.mEventListener);
        this.mCanvas2D.addMouseMotionListener(this.mEventListener);
        this.mCanvas2D.addKeyListener(this.mEventListener);

        this.mPtCurveMgr = new JSIPtCurveMgr();

        // create the transform.
        this.mXform = new JSIXform();

        //add this as a key listener to the frame.
        this.mCanvas2D.setFocusable(true);
        this.mCanvas2D.addKeyListener(this.mEventListener);

        this.mFrame.setVisible(true);

        // create a color chooser.
        this.mColorChooser = new JSIColorChooser();
    }

    public static void main(String[] args) {
        //create a JSI instance.
        new JSI();
    }

    public void updateSelectedPtCurves() {
        AffineTransform at = this.mXform.getCurXformFromScreenToWorld();
        Shape worldSelectionBoxShape = at.createTransformedShape(this.mSelectionBox);

        ArrayList<JSIPtCurve> newlySelectedPtCurves = new ArrayList<JSIPtCurve>();
        for (JSIPtCurve ptCurve : this.mPtCurveMgr.getPtCurves()) {
            //if(this.mSelectionBox.intersects(ptCurve.getBounds()) || ptCurve.getBounds().isEmpty()) {
            if (worldSelectionBoxShape.intersects(ptCurve.getBounds()) || ptCurve.getBounds().isEmpty()) {
                for (Point2D.Double pt : ptCurve.getPts()) {
                    //if (this.mSelectionBox.contains(pt)) {
                    if (worldSelectionBoxShape.contains(pt)) {
                        newlySelectedPtCurves.add(ptCurve);
                        break;
                    }
                }
            }
        }
        mPtCurveMgr.getPtCurves().removeAll(newlySelectedPtCurves);
        mPtCurveMgr.getSelectedPtCurves().addAll(newlySelectedPtCurves);
    }
}

package jsi;

import x.XApp;
import x.XLogMgr;
import x.XScenarioMgr;

import java.awt.geom.Point2D;
import javax.swing.JFrame;

public class JSIApp extends XApp {
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

    public JSIPtCurveMgr getPtCurveMgr() {
        return this.mPtCurveMgr;
    }

    private XScenarioMgr mScenarioMgr = null;


    @Override
    public XScenarioMgr getScenarioMgr() {
        return this.mScenarioMgr;
    }

    private XLogMgr mLogMgr = null;

    @Override
    public XLogMgr getLogMgr() {
        return this.mLogMgr;
    }

    //constructor
    public JSIApp() {
        // create components.
        // 1) frame, 2) canvas, 3) other components. 4) event listeners 5) managers
        this.mFrame = new JFrame("JustSketchIt");
        this.mCanvas2D = new JSICanvas2D(this);
        this.mXform = new JSIXform();
        this.mColorChooser = new JSIColorChooser();
        this.mEventListener = new JSIEventListener(this);
        this.mPtCurveMgr = new JSIPtCurveMgr();
        this.mScenarioMgr = new JSIScenarioMgr(this);
        this.mLogMgr = new XLogMgr();
        this.mLogMgr.setPrintOn(true);

        // connect event listeners.
        this.mCanvas2D.addMouseListener(this.mEventListener);
        this.mCanvas2D.addMouseMotionListener(this.mEventListener);
        this.mCanvas2D.setFocusable(true);
        this.mCanvas2D.addKeyListener(this.mEventListener);

        // build and show visual components.
        this.mFrame.add(this.mCanvas2D);
        this.mFrame.setSize(800, 600);
        this.mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mFrame.setVisible(true);
    }

    public static void main(String[] args) {
        //create a JSI instance.
        new JSIApp();
    }
}

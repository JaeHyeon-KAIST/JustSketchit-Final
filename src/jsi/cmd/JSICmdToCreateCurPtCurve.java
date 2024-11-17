package jsi.cmd;

import jsi.JSIApp;
import jsi.JSIPtCurve;
import x.XApp;
import x.XLoggableCmd;

import java.awt.*;
import java.awt.geom.Point2D;

public class JSICmdToCreateCurPtCurve extends XLoggableCmd {
    // fields
    private Point mScreenPt = null;
    private Point2D.Double mWorldPt = null;

    // private constructor
    private JSICmdToCreateCurPtCurve(XApp app, Point pt) {
        super(app);
        this.mScreenPt = pt;
    }

    // JSICmdToCreateCurPtCurve.execute(jsi, pt);
    public static boolean execute(XApp app, Point pt) {
        JSICmdToCreateCurPtCurve cmd = new JSICmdToCreateCurPtCurve(app, pt);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        JSIApp jsi = (JSIApp) this.mApp;
        this.mWorldPt = jsi.getXform().calPtFromScreenToWorld(this.mScreenPt);
        JSIPtCurve ptCurve = new JSIPtCurve(this.mWorldPt,
            jsi.getJSICanvas2D().getCurColorForPtCurve(),
            jsi.getJSICanvas2D().getCurStrokeForPtCurve());
        jsi.getPtCurveMgr().setCurPtCurve(ptCurve);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        sb.append(this.mScreenPt).append("\t");
        sb.append(this.mWorldPt);
        return sb.toString();
    }
}

package jsi.cmd;

import java.awt.Point;
import java.awt.geom.Point2D;

import jsi.JSIApp;
import jsi.JSIPtCurve;
import x.XApp;
import x.XLoggableCmd;

public class JSICmdToUpdateCurPtCurve extends XLoggableCmd {
    // fields
    private Point mScreenPt = null;
    private Point2D.Double mWorldPt = null;

    // private contructor
    private JSICmdToUpdateCurPtCurve(XApp app, Point pt) {
        super(app);
        this.mScreenPt = pt;
    }

    // JSICmdToCreateCurPtCurve.execute(jsi, pt);
    public static boolean execute(XApp app, Point pt) {
        JSICmdToUpdateCurPtCurve cmd = new JSICmdToUpdateCurPtCurve(app, pt);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        JSIApp jsi = (JSIApp) this.mApp;
        JSIPtCurve curPtCurve = jsi.getPtCurveMgr().getCurPtCurve();
        int size = curPtCurve.getPts().size();
        Point2D.Double lastWorldPt = curPtCurve.getPts().get(size - 1);
        Point lastScreenPt = jsi.getXform().calPtFromWorldToScreen(
            lastWorldPt);
        if (this.mScreenPt.distance(lastScreenPt) <
            JSIPtCurve.MIN_DIST_BTWN_PTS) {
            return true;
        }
        this.mWorldPt = jsi.getXform().calPtFromScreenToWorld(this.mScreenPt);
        curPtCurve.addPt(this.mWorldPt);
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

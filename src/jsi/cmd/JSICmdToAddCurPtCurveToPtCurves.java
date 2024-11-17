package jsi.cmd;

import jsi.JSIApp;
import jsi.JSIPtCurve;
import x.XApp;
import x.XLoggableCmd;

public class JSICmdToAddCurPtCurveToPtCurves extends XLoggableCmd {
    // fields
    private int mNumOfPtCurvesBefore = Integer.MIN_VALUE;
    private int mNumOfPtCurvesAfter = Integer.MIN_VALUE;

    // private contructor
    private JSICmdToAddCurPtCurveToPtCurves(XApp app) {
        super(app);
    }

    // JSICmdToAddCurPtCurveToPtCurves.execute(jsi);
    public static boolean execute(XApp app) {
        JSICmdToAddCurPtCurveToPtCurves cmd =
            new JSICmdToAddCurPtCurveToPtCurves(app);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        JSIApp jsi = (JSIApp) this.mApp;
        JSIPtCurve curPtCurve = jsi.getPtCurveMgr().getCurPtCurve();
        if (curPtCurve.getPts().size() >= 2) {
            this.mNumOfPtCurvesBefore =
                jsi.getPtCurveMgr().getPtCurves().size();
            jsi.getPtCurveMgr().getPtCurves().add(curPtCurve);
            jsi.getPtCurveMgr().setCurPtCurve(null);
            this.mNumOfPtCurvesAfter =
                jsi.getPtCurveMgr().getPtCurves().size();
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        sb.append(this.mNumOfPtCurvesBefore).append("\t");
        sb.append(this.mNumOfPtCurvesAfter);
        return sb.toString();
    }
}

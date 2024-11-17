package jsi.cmd;

import jsi.JSIApp;
import x.XApp;
import x.XLoggableCmd;

import java.awt.*;


public class JSICmdToIncreaseStrokeWidthForCurPtCurve extends XLoggableCmd {
    // fields
    private float mWidthBefore = Float.NaN;
    private float mWidthDelta = Float.NaN;
    private float mWidthAfter = Float.NaN;

    // private constructor
    private JSICmdToIncreaseStrokeWidthForCurPtCurve(XApp app, float dw) {
        super(app);
        this.mWidthDelta = dw;
    }

    public static boolean execute(XApp app, float dw) {
        JSICmdToIncreaseStrokeWidthForCurPtCurve cmd = new JSICmdToIncreaseStrokeWidthForCurPtCurve(app, dw);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        JSIApp jsi = (JSIApp) this.mApp;
        BasicStroke bs = (BasicStroke) jsi.getJSICanvas2D().getCurStrokeForPtCurve();
        this.mWidthBefore = bs.getLineWidth();
        jsi.getJSICanvas2D().increaseStrokeWidthForCurPtCurve(this.mWidthDelta);
        bs = (BasicStroke) jsi.getJSICanvas2D().getCurStrokeForPtCurve();
        this.mWidthAfter = bs.getLineWidth();

        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        sb.append(this.mWidthBefore).append("\t");
        sb.append(this.mWidthDelta).append("\t");
        sb.append(this.mWidthAfter);
        return sb.toString();
    }
}

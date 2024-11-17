package jsi.cmd;

import java.awt.geom.AffineTransform;

import jsi.JSIApp;
import x.XApp;
import x.XLoggableCmd;

public class JSICmdToHome extends XLoggableCmd {
    // fields
    private AffineTransform mXformBefore = null;
    private AffineTransform mXformAfter = null;

    // private contructor
    private JSICmdToHome(XApp app) {
        super(app);
    }

    // JSICmdToHome.execute(jsi);
    public static boolean execute(XApp app) {
        JSICmdToHome cmd = new JSICmdToHome(app);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        JSIApp jsi = (JSIApp) this.mApp;
        this.mXformBefore = new AffineTransform(
            jsi.getXform().getCurXformFromWorldToScreen());
        jsi.getXform().home();
        this.mXformAfter = jsi.getXform().getCurXformFromWorldToScreen();
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        sb.append(this.mXformBefore).append("\t");
        sb.append(this.mXformAfter);
        return sb.toString();
    }
}

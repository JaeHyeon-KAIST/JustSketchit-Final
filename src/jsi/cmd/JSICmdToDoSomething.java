package jsi.cmd;

import jsi.JSIApp;
import x.XApp;
import x.XLoggableCmd;


public class JSICmdToDoSomething extends XLoggableCmd {
    // fields

    // private constructor
    // private JSICmdToDoSomething(XApp app, ...) {
    private JSICmdToDoSomething(XApp app) {
        super(app);
    }

    // JSICmdToDoSomething.execute(jsi, ...);
    public static boolean execute(XApp app) {
        JSICmdToDoSomething cmd = new JSICmdToDoSomething(app);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        JSIApp jsi = (JSIApp) this.mApp;

        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");

        return sb.toString();
    }
}

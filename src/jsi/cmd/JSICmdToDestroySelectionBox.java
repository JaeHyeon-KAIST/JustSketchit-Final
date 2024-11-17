package jsi.cmd;

import jsi.JSIApp;
import x.XApp;
import x.XLoggableCmd;


public class JSICmdToDestroySelectionBox extends XLoggableCmd {
    // fields

    // private constructor
    private JSICmdToDestroySelectionBox(XApp app) {
        super(app);
    }

    // JSICmdToDoSomething.execute(jsi, ...);
    public static boolean execute(XApp app) {
        JSICmdToDestroySelectionBox cmd = new JSICmdToDestroySelectionBox(app);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        JSIApp jsi = (JSIApp) this.mApp;
        jsi.setSelectionBox(null);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");

        return sb.toString();
    }
}

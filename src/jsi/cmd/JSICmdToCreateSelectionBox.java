package jsi.cmd;

import jsi.JSISelectionBox;
import jsi.scenario.JSISelectScenario;
import x.XApp;
import x.XLoggableCmd;

import java.awt.*;


public class JSICmdToCreateSelectionBox extends XLoggableCmd {
    // fields
    private Point mScreenPt = null;

    // private constructor
    private JSICmdToCreateSelectionBox(XApp app, Point pt) {
        super(app);
        this.mScreenPt = pt;
    }

    // JSICmdToDoSomething.execute(jsi, ...);
    public static boolean execute(XApp app, Point pt) {
        JSICmdToCreateSelectionBox cmd = new JSICmdToCreateSelectionBox(app, pt);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        JSISelectionBox selectionBox = new JSISelectionBox(this.mScreenPt);
        JSISelectScenario.getSingleton().setSelectionBox(selectionBox);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");

        return sb.toString();
    }
}

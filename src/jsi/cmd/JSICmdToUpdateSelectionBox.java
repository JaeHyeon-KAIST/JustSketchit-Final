package jsi.cmd;

import jsi.JSIApp;
import jsi.JSISelectionBox;
import jsi.scenario.JSISelectScenario;
import x.XApp;
import x.XLoggableCmd;

import java.awt.*;


public class JSICmdToUpdateSelectionBox extends XLoggableCmd {
    // fields
    private Point mScreenPt = null;
    private JSISelectionBox mSelectionBox = null;

    // private constructor
    private JSICmdToUpdateSelectionBox(XApp app, Point pt) {
        super(app);
        this.mScreenPt = pt;
    }

    // JSICmdToDoSomething.execute(jsi, ...);
    public static boolean execute(XApp app, Point pt) {
        JSICmdToUpdateSelectionBox cmd = new JSICmdToUpdateSelectionBox(app, pt);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        JSISelectScenario scenario = JSISelectScenario.getSingleton();
        scenario.getSelectionBox().update(this.mScreenPt);
        this.mSelectionBox = scenario.getSelectionBox();
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        sb.append(this.mSelectionBox.toString()).append("\t");

        return sb.toString();
    }
}

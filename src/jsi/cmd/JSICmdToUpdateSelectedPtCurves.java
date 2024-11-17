package jsi.cmd;

import jsi.JSIApp;
import jsi.JSIPtCurve;
import x.XApp;
import x.XLoggableCmd;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;


public class JSICmdToUpdateSelectedPtCurves extends XLoggableCmd {
    // fields

    // private constructor
    private JSICmdToUpdateSelectedPtCurves(XApp app) {
        super(app);
    }

    // JSICmdToDoSomething.execute(jsi, ...);
    public static boolean execute(XApp app) {
        JSICmdToUpdateSelectedPtCurves cmd = new JSICmdToUpdateSelectedPtCurves(app);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        JSIApp jsi = (JSIApp) this.mApp;

        AffineTransform at = jsi.getXform().getCurXformFromScreenToWorld();
        Shape worldSelectionBoxShape = at.createTransformedShape(jsi.getSelectionBox());

        ArrayList<JSIPtCurve> newlySelectedPtCurves = new ArrayList<JSIPtCurve>();
        System.out.println(jsi.getPtCurveMgr().getPtCurves().size());
        System.out.println(jsi.getPtCurveMgr().getSelectedPtCurves().size());
        for (JSIPtCurve ptCurve : jsi.getPtCurveMgr().getPtCurves()) {
            if (worldSelectionBoxShape.intersects(ptCurve.getBounds()) || ptCurve.getBounds().isEmpty()) {
                for (Point2D.Double pt : ptCurve.getPts()) {
                    //if (this.mSelectionBox.contains(pt)) {
                    if (worldSelectionBoxShape.contains(pt)) {
                        newlySelectedPtCurves.add(ptCurve);
                        break;
                    }
                }
            }
        }
        jsi.getPtCurveMgr().getPtCurves().removeAll(newlySelectedPtCurves);
        jsi.getPtCurveMgr().getSelectedPtCurves().addAll(newlySelectedPtCurves);

        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");

        return sb.toString();
    }
}

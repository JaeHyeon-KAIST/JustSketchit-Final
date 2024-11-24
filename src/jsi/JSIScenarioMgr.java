package jsi;

import jsi.scenario.*;
import x.XScenarioMgr;

public class JSIScenarioMgr extends XScenarioMgr {
    public JSIScenarioMgr(JSIApp jsi) {
        super(jsi);
    }

    @Override
    protected void addScenarios() {
        this.addScenario(JSIDefaultScenario.createSingleton(this.mApp));
        this.addScenario(JSIDrawScenario.createSingleton(this.mApp));
        this.addScenario(JSISelectReadyScenario.createSingleton(this.mApp));
        this.addScenario(JSISelectScenario.createSingleton(this.mApp));
        this.addScenario(JSISelectedScenario.createSingleton(this.mApp));
        this.addScenario(JSINavigateScenario.createSingleton(this.mApp));
//        this.addScenario(JSIPanReadyScenario.createSingleton(this.mApp));
//        this.addScenario(JSIPanScenario.createSingleton(this.mApp));
//        this.addScenario(JSIZoomRotateReadyScenario.createSingleton(this.mApp));
//        this.addScenario(JSIZoomRotateScenario.createSingleton(this.mApp));
//        this.addScenario(JSIColorReadyScenario.createSingleton(this.mApp));
        this.addScenario(JSIColorScenario.createSingleton(this.mApp));
    }

    @Override
    protected void setInitCurScene() {
        this.setCurScene(JSIDefaultScenario.ReadyScene.getSingleton());
    }
}

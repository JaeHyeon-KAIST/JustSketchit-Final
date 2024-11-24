package jsi;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class JSIPenMarkMgr {
    // constants
    private static final int MAX_NUM_PEN_MARKS = 10;

    // fields
    private ArrayList<JSIPenMark> mPanMarks = null;

    public ArrayList<JSIPenMark> getPanMarks() {
        return this.mPanMarks;
    }

    // constructor
    public JSIPenMarkMgr() {
        this.mPanMarks = new ArrayList<JSIPenMark>();
    }

    public void addPenMark(JSIPenMark panMark) {
        if (this.mPanMarks.size() >= MAX_NUM_PEN_MARKS) {
            this.mPanMarks.remove(0);
        }
        this.mPanMarks.add(panMark);
    }

    public JSIPenMark getLastPenMark() {
        if (this.mPanMarks.isEmpty()) {
            return null;
        }
        return this.mPanMarks.get(this.mPanMarks.size() - 1);
    }

    public JSIPenMark getRecentPenMark(int i) {
        int size = this.mPanMarks.size();
        if (size == 0 || i >= size) {
            return null;
        }
        return this.mPanMarks.get(size - i - 1);
    }

    public boolean handleMousePress(MouseEvent e) {
        Point pt = e.getPoint();
        JSIPenMark panMark = new JSIPenMark(pt);
        this.addPenMark(panMark);
        return true;
    }

    public boolean handleMouseDrag(MouseEvent e) {
        Point pt = e.getPoint();
        JSIPenMark panMark = this.getLastPenMark();
        if (panMark == null) {
            return false;
        }
        panMark.addPt(pt);
        return true;
    }

    public boolean handleMouseRelease(MouseEvent e) {
        return true;
    }
}

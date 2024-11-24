package jsi;

import java.awt.*;
import java.util.ArrayList;

public class JSIPenMark {
    // fields
    private ArrayList<Point> mPts = null;

    public ArrayList<Point> getPts() {
        return this.mPts;
    }

    private Rectangle mBoundingBox = null;

    public Rectangle getBoundingBox() {
        return this.mBoundingBox;
    }

    // constructor
    public JSIPenMark(Point pt) {
        this.mPts = new ArrayList<Point>();
        this.mPts.add(pt);
        this.mBoundingBox = new Rectangle(pt.x, pt.y, 0, 0);
    }

    public void addPt(Point pt) {
        this.mPts.add(pt);
        this.mBoundingBox.add(pt);
    }

    public Point getFirstPt() {
        return this.mPts.get(0);
    }

    public Point getLastPt() {
        return this.mPts.get(this.mPts.size() - 1);
    }
}

package jsi;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class JSIPtCurve {
    //constants
    public static final double MIN_DIST_BTWN_PTS = 5.0;

    //fields
    private ArrayList<Point2D.Double> mPts = null;

    public ArrayList<Point2D.Double> getPts() {
        return this.mPts;
    }

    private Rectangle2D.Double mBoundingBox = null;

    public Rectangle2D.Double getBounds() {
        return this.mBoundingBox;
    }

    private Color mColor = null;

    public Color getColor() {
        return this.mColor;
    }

    public void setColor(Color c) {
        this.mColor = c;
    }

    private Stroke mStroke = null;

    public Stroke getStroke() {
        return this.mStroke;
    }

    //constructor
    public JSIPtCurve(Point2D.Double pt, Color c, Stroke s) {
        this.mPts = new ArrayList<Point2D.Double>();
        this.mPts.add(pt);
        this.mBoundingBox = new Rectangle2D.Double(pt.x, pt.y, 0, 0);

        this.mColor = new Color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());

        BasicStroke bs = (BasicStroke) s;
        this.mStroke = new BasicStroke(bs.getLineWidth(), bs.getEndCap(), bs.getLineJoin());
    }

    public void addPt(Point2D.Double pt) {
        this.mPts.add(pt);
        this.mBoundingBox.add(pt);
    }

    public void translatePt(double dx, double dy) {
        for (Point2D.Double pt : this.mPts) {
            pt.x += dx;
            pt.y += dy;
        }
        this.mBoundingBox.x += dx;
        this.mBoundingBox.y += dy;
    }

    public void increaseStrokeWidth(float f) {
        BasicStroke bs = (BasicStroke) this.mStroke;
        float w = bs.getLineWidth();
        w += f;
        if (w < JSICanvas2D.STROKE_MIN_WIDTH) {
            w = JSICanvas2D.STROKE_MIN_WIDTH;
        }
        BasicStroke newStroke = new BasicStroke(w, bs.getEndCap(), bs.getLineJoin());
        this.mStroke = newStroke;
    }
}

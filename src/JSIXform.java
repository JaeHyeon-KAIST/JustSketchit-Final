import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

public class JSIXform {
    //constants
    public static final Point PIVOT_Pt = new Point(100, 100);
    public static final double MIN_START_ARM_LENGTH_FOR_SCALING = 100.0;

    //fields
    private AffineTransform mCurXformFromWorldToScreen = null;

    public AffineTransform getCurXformFromWorldToScreen() {
        return this.mCurXformFromWorldToScreen;
    }

    private AffineTransform mCurXformFromScreenToWorld = null;

    public AffineTransform getCurXformFromScreenToWorld() {
        return this.mCurXformFromScreenToWorld;
    }

    private AffineTransform mStartXformFromWorldToScreen = null;
    private Point mStartScreenPt = null;

    public void setStartScreenPt(Point pt) {
        this.mStartScreenPt = pt;
        this.mStartXformFromWorldToScreen.setTransform(this.mCurXformFromWorldToScreen);
    }

    //constructor
    public JSIXform() {
        this.mCurXformFromWorldToScreen = new AffineTransform(); // identity
        this.mCurXformFromScreenToWorld = new AffineTransform(); // identity
        this.mStartXformFromWorldToScreen = new AffineTransform();
    }

    //call whenever mCurXformFromWorldToScreen changes to have its
    //corresponding mCurXformFromScreenToWorld updated.
    public void updateCurXformFromScreenToWorld() {
        try {
            this.mCurXformFromScreenToWorld = this.mCurXformFromWorldToScreen.createInverse();
        } catch (NoninvertibleTransformException e) {
            System.out.println("NoninvertibleTransformException");
        }
    }

    public Point calPtFromWorldToScreen(Point2D.Double worldPt) {
        Point screenPt = new Point();
        this.mCurXformFromWorldToScreen.transform(worldPt, screenPt);
        return screenPt;
    }

    public Point2D.Double calPtFromScreenToWorld(Point screenPt) {
        Point2D.Double worldPt = new Point2D.Double();
        this.mCurXformFromScreenToWorld.transform(screenPt, worldPt);
        return worldPt;
    }

    public void translateTo(Point pt) {
        if (this.mStartScreenPt == null) {
            return;
        }

        this.mCurXformFromWorldToScreen.setTransform(this.mStartXformFromWorldToScreen);
        // call whenever mCurXformFromWorldToScreen changes.
        this.updateCurXformFromScreenToWorld();

        Point2D.Double worldPt0 = this.calPtFromScreenToWorld(this.mStartScreenPt);
        Point2D.Double worldPt1 = this.calPtFromScreenToWorld(pt);
        double dx = worldPt1.x - worldPt0.x;
        double dy = worldPt1.y - worldPt0.y;

        this.mCurXformFromWorldToScreen.translate(dx, dy);
        // call whenever mCurXformFromWorldToScreen changes.
        this.updateCurXformFromScreenToWorld();

    }

    public void zoomRotateTo(Point pt) {
        if (this.mStartScreenPt == null) {
            return;
        }

        this.mCurXformFromWorldToScreen.setTransform(this.mStartXformFromWorldToScreen);
        // call whenever mCurXformFromWorldToScreen changes.
        this.updateCurXformFromScreenToWorld();

        double d0 = JSIXform.PIVOT_Pt.distance(this.mStartScreenPt);
        if (d0 < JSIXform.MIN_START_ARM_LENGTH_FOR_SCALING) {
            return;
        }

        double d1 = JSIXform.PIVOT_Pt.distance(pt);
        double scale = d1 / d0;

        double ang0 = StrictMath.atan2(this.mStartScreenPt.y - JSIXform.PIVOT_Pt.y, this.mStartScreenPt.x - JSIXform.PIVOT_Pt.x);
        double ang1 = StrictMath.atan2(pt.y - JSIXform.PIVOT_Pt.y, pt.x - JSIXform.PIVOT_Pt.x);
        double angle = ang1 - ang0;

        Point2D.Double worldPivotPt = this.calPtFromScreenToWorld(JSIXform.PIVOT_Pt);
        // translate the screen coordinate system by (-worldPivotPt.x, -worldPivotPt.y)
        this.mCurXformFromWorldToScreen.translate(worldPivotPt.x, worldPivotPt.y);
        // rotate the screen coordinate system by -angle.
        this.mCurXformFromWorldToScreen.rotate(angle);
        // scale the screen coordinate system by 1/scale.
        this.mCurXformFromWorldToScreen.scale(scale, scale);
        //translate the screen coordinate system by (worldPivotPt.x, worldPivotPt.y)
        this.mCurXformFromWorldToScreen.translate(-worldPivotPt.x, -worldPivotPt.y);

        //call whenever mCurXformFromWorldToScreen changes.
        this.updateCurXformFromScreenToWorld();

    }

    public void home() {
        this.mCurXformFromWorldToScreen.setToIdentity();
        this.updateCurXformFromScreenToWorld();
    }
}

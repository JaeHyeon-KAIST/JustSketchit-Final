import java.awt.Point;
import java.awt.Rectangle;

public class JSISelectionBox extends Rectangle {
    //fields
    private Point mAnchorPoint = null;

    //constructor
    public JSISelectionBox(Point pt) {
        super(pt);
        this.mAnchorPoint = pt;
    }

    //update with a new point
    public void update(Point pt) {
        this.setRect(this.mAnchorPoint.x, this.mAnchorPoint.y, 0, 0);
        this.add(pt);
    }
}

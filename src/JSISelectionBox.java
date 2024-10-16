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
        int x = Math.min(this.mAnchorPoint.x, pt.x);
        int y = Math.min(this.mAnchorPoint.y, pt.y);
        int width = Math.abs(this.mAnchorPoint.x - pt.x);
        int height = Math.abs(this.mAnchorPoint.y - pt.y);
        this.setBounds(x, y, width, height);
    }
}

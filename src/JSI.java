import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.JFrame;

public class JSI implements MouseListener, MouseMotionListener, KeyListener {
    //introduce mode and quasi-modes.
    public enum Mode {
        DRAW,
        SELECT,
        SELECTED,
        PAN,
        ZOOM_ROTATE,
        COLOR
    }

    // fields
    private Mode mMode = JSI.Mode.DRAW;

    public Mode getMode() {
        return this.mMode;
    }

    private JFrame mFrame = null;
    private JSICanvas2D mCanvas2D = null;
    private JSIPtCurve mCurPtCurve = null;

    public JSIPtCurve getCurPtCurve() {
        return this.mCurPtCurve;
    }

    private ArrayList<JSIPtCurve> mPtCurves = null;

    public ArrayList<JSIPtCurve> getPtCurves() {
        return this.mPtCurves;
    }

    private ArrayList<JSIPtCurve> mSelectedPtCurves = null;

    public ArrayList<JSIPtCurve> getSelectedPtCurves() {
        return this.mSelectedPtCurves;
    }

    private JSISelectionBox mSelectionBox = null;

    public JSISelectionBox getSelectionBox() {
        return this.mSelectionBox;
    }

    private JSIXform mXform = null;

    public JSIXform getXform() {
        return this.mXform;
    }

    private JSIColorChooser mColorChooser = null;

    public JSIColorChooser getColorChooser() {
        return this.mColorChooser;
    }

    //constructor
    public JSI() {
        // create a JFrame instance and make it visible.
        this.mFrame = new JFrame("JustSketchIt");
        this.mFrame.setSize(800, 600);
        this.mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create JSICanvas2D and add it to the frame.
        this.mCanvas2D = new JSICanvas2D(this);
        this.mFrame.add(this.mCanvas2D);

        // add this as a mouse listener to the canvas.
        this.mCanvas2D.addMouseListener(this);
        this.mCanvas2D.addMouseMotionListener(this);

        this.mPtCurves = new ArrayList<JSIPtCurve>();

        // create a pen mark array.
        this.mSelectedPtCurves = new ArrayList<JSIPtCurve>();

        // create the transform.
        this.mXform = new JSIXform();

        //add this as a key listener to the frame.
        this.mCanvas2D.setFocusable(true);
        this.mCanvas2D.addKeyListener(this);

        this.mFrame.setVisible(true);

        // create a color chooser.
        this.mColorChooser = new JSIColorChooser();
    }

    public static void main(String[] args) {
        //create a JSI instance.
        new JSI();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //System.out.println("mouseClicked");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point pt = e.getPoint();
        switch (this.mMode) {
            case DRAW:
                Point2D.Double worldPt = this.mXform.calPtFromScreenToWorld(pt);
                this.mCurPtCurve = new JSIPtCurve(worldPt, this.mCanvas2D.getCurColorForPtCurve(), this.mCanvas2D.getCurStrokeForPtCurve());
                break;
            case SELECT:
                this.mSelectionBox = new JSISelectionBox(pt);
                break;
            case SELECTED:
                break;
            case PAN:
                this.mXform.setStartScreenPt(pt);
                break;
            case ZOOM_ROTATE:
                this.mXform.setStartScreenPt(pt);
                break;
        }
        this.mCanvas2D.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point pt = e.getPoint();
        switch (this.mMode) {
            case DRAW:
                if (this.mCurPtCurve != null) {
                    // check if the distansce between the new screen point and the last screen point is greater than the threshold.
                    int size = this.mCurPtCurve.getPts().size();
                    Point2D.Double lastWorldPt = this.mCurPtCurve.getPts().get(size - 1);
                    Point lastScreenPt = this.mXform.calPtFromWorldToScreen(lastWorldPt);
                    if (pt.distance(lastScreenPt) < JSIPtCurve.MIN_DIST_BTWN_PTS) {
                        return;
                    }
                    //add the current mouse point to the current pen mark.
                    Point2D.Double worldPt = this.mXform.calPtFromScreenToWorld(pt);
                    this.mCurPtCurve.addPt(worldPt);
                }
                break;
            case SELECT:
                this.mSelectionBox.update(pt);
                this.updateSelectedPtCurves();
                break;
            case SELECTED:
                break;
            case PAN:
                this.mXform.translateTo(pt);
                break;
            case ZOOM_ROTATE:
                this.mXform.zoomRotateTo(pt);
                break;
        }
        this.mCanvas2D.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (this.mMode) {
            case DRAW:
                if (this.mCurPtCurve != null) {
                    if (this.mCurPtCurve.getPts().size() == 1) { //tap
                        this.mPtCurves.addAll(this.mSelectedPtCurves);
                        this.mSelectedPtCurves.clear();
                    } else if (this.mCurPtCurve.getPts().size() >= 2) {
                        this.mPtCurves.add(this.mCurPtCurve);
                    }
                }
                this.mCurPtCurve = null;
                break;
            case SELECT:
                this.mSelectionBox = null;
                break;
            case SELECTED:
                break;
            case PAN:
                this.mXform.setStartScreenPt(null);
                break;
            case ZOOM_ROTATE:
                this.mXform.setStartScreenPt(null);
                break;
            case COLOR:
                Point pt = e.getPoint();
                Color c = this.mColorChooser.calcColor(pt, this.mCanvas2D.getWidth(), this.mCanvas2D.getHeight());
                if (c != null) {
                    if (this.mSelectedPtCurves.isEmpty()) {
                        this.mCanvas2D.setCurColorForPtCurve(c);
                    } else {
                        for (JSIPtCurve ptCurve : this.mSelectedPtCurves) {
                            ptCurve.setColor(c);
                        }
                        this.mPtCurves.addAll(this.mSelectedPtCurves);
                        this.mSelectedPtCurves.clear();
                    }
                }
                break;
        }
        this.mCanvas2D.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //System.out.println("mouseEntered");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //System.out.println("mouseExited");
    }

    private void updateSelectedPtCurves() {
        AffineTransform at = this.mXform.getCurXformFromScreenToWorld();
        Shape worldSelectionBoxShape = at.createTransformedShape(this.mSelectionBox);

        ArrayList<JSIPtCurve> newlySelectedPtCurves = new ArrayList<JSIPtCurve>();
        for (JSIPtCurve ptCurve : this.mPtCurves) {
            //if(this.mSelectionBox.intersects(ptCurve.getBounds()) || ptCurve.getBounds().isEmpty()) {
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
        this.mPtCurves.removeAll(newlySelectedPtCurves);
        this.mSelectedPtCurves.addAll(newlySelectedPtCurves);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //System.out.println("mouseMoved");
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_SHIFT:
                this.mMode = JSI.Mode.SELECT;
                System.out.println("SelectionMode ON");
                break;
            case KeyEvent.VK_CONTROL:
                this.mMode = JSI.Mode.PAN;
                System.out.println("PanMode ON");
                break;
            case KeyEvent.VK_ALT:
                this.mMode = JSI.Mode.ZOOM_ROTATE;
                System.out.println("RotateMode ON");
                break;
            case KeyEvent.VK_UP:
                if (this.mSelectedPtCurves.isEmpty()) {
                    this.mCanvas2D.increaseStrokeWidthForCurPtCurve(JSICanvas2D.STROKE_WIDTH_INCREMENT);
                } else {
                    for (JSIPtCurve ptCurve : this.mSelectedPtCurves) {
                        ptCurve.increaseStrokeWidth(JSICanvas2D.STROKE_WIDTH_INCREMENT);
                    }
                }
                break;
            case KeyEvent.VK_DOWN:
                if (this.mSelectedPtCurves.isEmpty()) {
                    this.mCanvas2D.increaseStrokeWidthForCurPtCurve(-JSICanvas2D.STROKE_WIDTH_INCREMENT);
                } else {
                    for (JSIPtCurve ptCurve : this.mSelectedPtCurves) {
                        ptCurve.increaseStrokeWidth(-JSICanvas2D.STROKE_WIDTH_INCREMENT);
                    }
                }
                break;
            case KeyEvent.VK_C:
                this.mMode = JSI.Mode.COLOR;
                System.out.println("ColorMode ON");
                break;
        }
        this.mCanvas2D.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_SHIFT:
                if (this.mSelectedPtCurves.isEmpty()) {
                    this.mMode = JSI.Mode.DRAW;
                } else {
                    this.mMode = JSI.Mode.SELECTED;
                }
                System.out.println("SelectionMode OFF");
                this.mSelectionBox = null;
                this.mCanvas2D.repaint();
                break;
            case KeyEvent.VK_CONTROL:
                if (this.mSelectedPtCurves.isEmpty()) {
                    this.mMode = JSI.Mode.DRAW;
                } else {
                    this.mMode = JSI.Mode.SELECTED;
                }
                System.out.println("PanMode OFF");
                break;
            case KeyEvent.VK_ESCAPE:
                this.mPtCurves.addAll(this.mSelectedPtCurves);
                this.mSelectedPtCurves.clear();
                this.mCanvas2D.repaint();
                this.mMode = JSI.Mode.DRAW;
                break;
            case KeyEvent.VK_BACK_SPACE:
                this.mSelectedPtCurves.clear();
                this.mCanvas2D.repaint();
                this.mMode = JSI.Mode.DRAW;
                break;
            case KeyEvent.VK_ALT:
                if (this.mSelectedPtCurves.isEmpty()) {
                    this.mMode = JSI.Mode.DRAW;
                } else {
                    this.mMode = JSI.Mode.SELECTED;
                }
                System.out.println("RotateMode OFF");
                break;
            case KeyEvent.VK_H:
                this.mXform.home();
                break;
            case KeyEvent.VK_C:
                if (this.mSelectedPtCurves.isEmpty()) {
                    this.mMode = JSI.Mode.DRAW;
                } else {
                    this.mMode = JSI.Mode.SELECTED;
                }
                System.out.println("ColorMode OFF");
                break;
        }
        this.mCanvas2D.repaint();
    }
}

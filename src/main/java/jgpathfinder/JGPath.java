package main.java.jgpathfinder;

import jgame.JGColor;
import jgame.JGPoint;
import jgame.impl.JGEngineInterface;

import java.util.LinkedList;

public class JGPath {
    private JGEngineInterface engine; // TODO: bad design?
    private LinkedList<JGPoint> path;

    /**
     * Create a new, empty JGPath.
     */
    public JGPath(JGEngineInterface engine) {
        this.engine = engine;
        path = new LinkedList<JGPoint>();
    }

    /**
     * Create a new copy of the given path.
     *
     * @param path The path you want a copy of
     */
    public JGPath(JGPath path) {
        this.path = new LinkedList<JGPoint>(path.path);
    }

    /**
     * Add a point at the end of the path.
     *
     * @param point The point (tile index) to be added
     */
    public void add(JGPoint point) {
        if (point == null) {
            return; // TODO: throw exception
        }

        path.add(point);
    }

    /**
     * Add specified point to the beginning of the path.
     *
     * @param point Tile index to be added
     */
    public void addFirst(JGPoint point) {
        if (point == null) {
            return; // TODO: throw exception
        }

        path.addFirst(point);
    }

    /**
     * Returns if the path has another point.
     *
     * @return If the path has another point
     */
    public boolean hasNext() {
        return (path.peek() != null);
    }

    /**
     * Retrieves and removes, the next point of the path.
     *
     * @return The tile index of the next point in the path
     */
    public JGPoint getNext() {
        if (!hasNext()) {
            return null; // TODO: throw exception
        }

        return path.poll();
    }

    /**
     * Retrieves, but does not remove, the next point of the path. Returns
     * null if there is no more points in the path.
     *
     * @return The tile index of the next point in the path
     */
    public JGPoint peek() {
        return path.peek();
    }

    /**
     * Paint the path by placing a circle in the center of each one.
     *
     * @param radius Radius of the circle
     * @param color Color of the circle
     */
    public void paint(int radius, JGColor color) {
        for (JGPoint p : path) {
            JGPoint coord = engine.getTileCoord(p);
            engine.drawOval(coord.x + engine.tileWidth()/2, coord.y + engine.tileHeight()/2,
                    radius, radius, true, true, radius, color);
        }
    }
}

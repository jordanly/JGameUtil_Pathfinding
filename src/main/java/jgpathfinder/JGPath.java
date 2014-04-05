package main.java.jgpathfinder;

import jgame.JGPoint;

import java.util.LinkedList;

public class JGPath {
    private LinkedList<JGPoint> path;

    public JGPath() {
        path = new LinkedList<JGPoint>();
    }

    public void add(JGPoint point) {
        if (point == null) {
            return; // TODO: throw exception
        }

        path.add(point);
    }

    public boolean hasNext() {
        return (path.peek() != null);
    }

    public JGPoint getNext() {
        if (!hasNext()) {
            return null; // TODO: throw exception
        }

        return path.poll();
    }
}

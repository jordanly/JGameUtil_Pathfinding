package main.java.jgpathfinder;

import jgame.JGPoint;

public interface JGPathFinderInterface {
    /**
     * Implement this interface to create a custom path-finding algorithm.
     *
     * @param source Start posisiton
     * @param target Destination position
     * @return A Path for an object to follow
     */
    public JGPath getPath(JGPoint source, JGPoint target);
}

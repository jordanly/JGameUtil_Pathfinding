package main.java.jgpathfinder;

import jgame.JGPoint;
import main.java.jgmap.JGTileMap;

public class JGPathfinder implements JGPathfinderInterface {
    private JGTileMap tileMap;

    public JGPathfinder(JGTileMap tileMap) {
        this.tileMap = tileMap;
    }

    @Override
    public JGPath getPath(JGPoint source, JGPoint target) {
        tileMap.setTile(source, "g");
        tileMap.setTile(target, "g");

        return null;
    }
}

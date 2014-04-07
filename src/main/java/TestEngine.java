package main.java;

import jgame.JGColor;
import jgame.JGPoint;
import jgame.platform.JGEngine;
import jgame.platform.StdGame;
import main.java.jgmap.JGTileMap;
import main.java.jgpathfinder.JGPathfinder;
import main.java.jgpathfinder.JGPathfinderHeuristic;
import main.java.jgpathfinder.JGPathfinderInterface;

import java.util.HashSet;
import java.util.Set;

public class TestEngine extends JGEngine {
    private JGTileMap tileMap;

    public TestEngine() {
        initEngineApplet();
    }

    public TestEngine(JGPoint size) {
        initEngine(size.x, size.y);
    }

    @Override
    public void initCanvas() {
        setCanvasSettings(25, 20, 32, 32, null, JGColor.black, null);
    }

    @Override
    public void initGame() {
        setFrameRate(45, 1);
        defineMedia("/main/resources/media.tbl");
        tileMap = new JGTileMap(this);

        for (int i = 0; i < pfTilesX(); i++) {
            for (int j = 0; j < pfTilesY(); j++) {
                setTile(i, j, "w");
            }
        }

        setTile(3, 4, "b");
        setTile(3, 5, "b");
        setTile(3, 6, "b");
        setTile(3, 7, "b");
        setTile(3, 8, "b");

        Set<Integer> blocked = new HashSet<Integer>();
        blocked.add(1);
        JGPathfinderInterface finder = new JGPathfinder(new JGTileMap(this, null, blocked),
                new JGPathfinderHeuristic(), this);
        finder.getPath(new JGPoint(1, 6), new JGPoint(7, 6));
    }

    @Override
    public void paintFrame() {
        for (int i = 0; i < pfTilesX(); i++) {
            for (int j = 0; j < pfTilesY(); j++) {
                drawRect(i * tileWidth(), j * tileHeight(), tileWidth(), tileHeight(), false, false, 1, JGColor.black);
            }
        }
    }

    public static void main(String[] args) {
        TestEngine testEngine = new TestEngine(StdGame.parseSizeArgs(args, 0));
    }
}

package main.java;

import jgame.JGColor;
import jgame.JGObject;
import jgame.JGPoint;
import jgame.platform.JGEngine;
import jgame.platform.StdGame;
import main.java.jgmap.JGTileMap;
import main.java.jgpathfinder.JGPath;
import main.java.jgpathfinder.JGPathfinder;
import main.java.jgpathfinder.JGPathfinderHeuristic;
import main.java.jgpathfinder.JGPathfinderInterface;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

public class TestEngine extends JGEngine {
    public static final String MEDIA_TBL_PATH = "/main/resources/media.tbl";
    private JGPath path;
    private JGTileMap tileMap;
    private Set<Integer> blocked;

    private JGPoint startPoint;
    private JGPoint endPoint;
    private long startTime;
    private long endTime;

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
        defineMedia(MEDIA_TBL_PATH);
        tileMap = new JGTileMap(this);
        clearGrid();

        startPoint = new JGPoint(12, 10); // default start point
        endPoint = new JGPoint(24, 19); // default end point

        blocked = new HashSet<Integer>();
        blocked.add(1);
    }

    @Override
    public void doFrame() {
        moveObjects();

        JGPoint tileInd = getTileIndex(getMouseX(), getMouseY());
        if (getMouseButton(MouseEvent.BUTTON1)) {
            setTile(tileInd, "b");
        } else if (getMouseButton(MouseEvent.BUTTON3)) {
            clearOldPath();
            JGPathfinderInterface finder = new JGPathfinder(new JGTileMap(this, null, blocked),
                    new JGPathfinderHeuristic(), this);

            startTime = System.currentTimeMillis();
            path = finder.getPath(startPoint, endPoint);
            endTime = System.currentTimeMillis();
            System.out.println("The time to execute took " + (endTime - startTime) + " milliseconds");
        } else if (getKey(KeyEvent.VK_SPACE)) {
            if (path != null) {
                new TestObject(toCenterOfTile(startPoint), path);
            }
        } else if (getKey(KeyEvent.VK_C)) {
            clearGrid();
            clearObjects();
        } else if (getKey(KeyEvent.VK_1)) {
            startPoint = getTileIndex(getMouseX(), getMouseY());
            setTile(startPoint.x, startPoint.y, "g");
        } else if (getKey(KeyEvent.VK_2)) {
            endPoint = getTileIndex(getMouseX(), getMouseY());
            setTile(endPoint.x, endPoint.y, "g");
        }

        clearButtons();
    }

    private void clearOldPath() {
        for (int i = 0; i < pfTilesX(); i++) {
            for (int j = 0; j < pfTilesY(); j++) {
                if (getTileCid(i, j) != 1) {
                    setTile(i, j, "w");
                }
            }
        }
    }

    private void clearGrid() {
        for (int i = 0; i < pfTilesX(); i++) {
            for (int j = 0; j < pfTilesY(); j++) {
                setTile(i, j, "w");
            }
        }
    }

    private void clearObjects() {
        removeObjects("TestObject", 2);
    }

    private void clearButtons() {
        clearLastKey();
        clearKey(KeyEvent.VK_SPACE);
        clearMouseButton(2);
        clearMouseButton(3);
    }

    @Override
    public void paintFrame() {
        drawGrid();
    }

    private void drawGrid() {
        for (int i = 0; i < pfTilesX(); i++) {
            for (int j = 0; j < pfTilesY(); j++) {
                drawRect(i * tileWidth(), j * tileHeight(), tileWidth(), tileHeight(), false, false, 1, JGColor.black);
            }
        }
    }

    private JGPoint toCenterOfTile(JGPoint tileIndex) {
        int x = tileIndex.x * tileWidth() + tileWidth()/2;
        int y = tileIndex.y * tileHeight() + tileHeight()/2;

        return new JGPoint(x, y);
    }

    private class TestObject extends JGObject {
        private JGPath path;

        public TestObject(JGPoint start, JGPath path) {
            super("TestObject", true, start.x, start.y, 2, null);
            this.path = new JGPath(path);
        }

        @Override
        public void move() {
            if (path.peek() != null) {
                JGPoint waypoint = toCenterOfTile(path.peek());

                if (((int) (x + 5) >= waypoint.x && (int) (x - 5) <= waypoint.x) &&
                        ((int) (y + 5) >= waypoint.y && (int) (y - 5) <= waypoint.y)) {
                    waypoint = path.getNext();
                }

                xdir = Double.compare(waypoint.x, x);
                ydir = Double.compare(waypoint.y, y);
                setDirSpeed(xdir, ydir, 2);
            } else {
                setSpeed(0);
            }
        }

        @Override
        public void paint() {
            drawOval(x, y, 16, 16, true, true, 10, JGColor.pink);
        }
    }

    public static void main(String[] args) {
        TestEngine testEngine = new TestEngine(StdGame.parseSizeArgs(args, 0));
    }
}

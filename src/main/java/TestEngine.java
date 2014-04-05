package main.java;

import jgame.JGColor;
import jgame.JGPoint;
import jgame.platform.JGEngine;
import jgame.platform.StdGame;
import main.java.jgmap.JGTileMap;

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
        setTile(3, 4, "g");
    }

    public static void main(String[] args) {
        TestEngine testEngine = new TestEngine(StdGame.parseSizeArgs(args, 0));
    }
}

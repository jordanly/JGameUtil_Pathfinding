package main.java;

import jgame.JGColor;
import jgame.JGPoint;
import jgame.platform.JGEngine;
import jgame.platform.StdGame;

public class TestEngine extends JGEngine {

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
    }

    public static void main(String[] args) {
        TestEngine testEngine = new TestEngine(StdGame.parseSizeArgs(args, 0));
    }
}

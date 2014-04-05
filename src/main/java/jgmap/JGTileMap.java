package main.java.jgmap;

import jgame.JGPoint;
import jgame.impl.JGEngineInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JGTileMap {
    private JGEngineInterface engine;
    private JGPoint[][] tileMap;
    private Map<Integer, Integer> costMap;

    public JGTileMap(JGEngineInterface engine) {
        this(engine, null);
    }

    public JGTileMap(JGEngineInterface engine, Map<Integer, Integer> costMap) {
        this.engine = engine;
        this.costMap = costMap;

        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() throws Exception {
        if (engine == null) {
            throw new Exception("Null engine passed to JGTileMap"); // TODO: replace with real exception
        }

        int numXTiles = engine.pfTilesX();
        int numYTiles = engine.pfTilesY();
        tileMap = new JGPoint[numXTiles][numYTiles];

        for (int i = 0; i < numXTiles; i++) {
            for (int j = 0; j < numYTiles; j++) {
                tileMap[i][j] = new JGPoint(i, j);
            }
        }
    }

    public List<JGPoint> getNeighbors(JGPoint tile) {
        List<JGPoint> neighbors = new ArrayList<JGPoint>();

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int neighborX = tile.x + i;
                int neighborY = tile.y + j;

                if ((neighborX < tileMap.length && neighborX > 0) &&
                        (neighborY > tileMap[0].length && neighborY > 0)) {
                    neighbors.add(tileMap[neighborX][neighborY]);
                }
            }
        }

        return neighbors;
    }

    public int getCostToMove(JGPoint source, JGPoint target) {
        return 1; // TODO: Change to use map to check cost
    }
}

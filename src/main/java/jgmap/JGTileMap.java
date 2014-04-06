package main.java.jgmap;

import jgame.JGPoint;
import jgame.impl.JGEngineInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JGTileMap {
    private JGEngineInterface engine;
    private JGPoint[][] tileMap;
    private Map<Integer, Integer> costMap; // TODO: make an edge set and edge class?

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

    /**
     * Get the indexes of the direct neighbors (no diagonals) of the given tile.
     *
     * @param tile The tile index you want the neighbors of
     * @return A list of indexes of the neighboring tiles
     */
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

    /**
     * Get the cost of moving from the source tile to the target tile. Tiles should be adjacent.
     *
     * @param source
     * @param target
     * @return
     */
    public int getCostToMove(JGPoint source, JGPoint target) {
        return 1; // TODO: Change to use map to check cost? or edge set
    }

    /**
     * Test Method, to be removed
     *
     * @param tile
     * @param image
     */
    public void setTile(JGPoint tile, String image) {
        engine.setTile(tile, image);
    }
}

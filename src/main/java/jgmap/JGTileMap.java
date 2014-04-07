package main.java.jgmap;

import jgame.JGPoint;
import jgame.impl.JGEngineInterface;

import java.util.*;

public class JGTileMap {
    private JGEngineInterface engine;
    private JGPoint[][] tileMap;
    private Map<Integer, Integer> costMap;
    private Set<Integer> blockedCIDs;

    public JGTileMap(JGEngineInterface engine) {
        this(engine, null, null);
    }

    public JGTileMap(JGEngineInterface engine, Map<Integer, Integer> costMap, Set<Integer> blockedCIDs) {
        this.engine = engine;

        if (costMap == null) {
            this.costMap = new HashMap<Integer, Integer>();
        }
        else {
            this.costMap = costMap;
        }
        if (blockedCIDs == null) {
            this.blockedCIDs = new HashSet<Integer>();
        }
        else {
            this.blockedCIDs = blockedCIDs;
        }

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

        int[] xDirs = new int[]{0, 1, 0, -1};
        int[] yDirs = new int[]{1, 0, -1, 0};
        for (int i = 0; i < xDirs.length; i++) {
            int neighborX = tile.x + xDirs[i];
            int neighborY = tile.y + yDirs[i];

            if ((neighborX < tileMap.length && neighborX > 0) &&
                    (neighborY < tileMap[0].length && neighborY > 0) &&
                    !isBlocked(tileMap[neighborX][neighborY])) {
                neighbors.add(tileMap[neighborX][neighborY]);
            }
        }

        return neighbors;
    }

    private boolean isBlocked(JGPoint tile) {
        return blockedCIDs.contains(engine.getTileCid(tile.x, tile.y));
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

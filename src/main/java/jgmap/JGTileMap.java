package main.java.jgmap;

import jgame.impl.JGEngineInterface;

public class JGTileMap {
    private JGEngineInterface engine;
    JGTile[][] tileMap;

    public JGTileMap(JGEngineInterface engine) {
        this.engine = engine;

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

        tileMap = new JGTile[numXTiles][numYTiles];

        for (int i = 0; i < numXTiles; i++) {
            for (int j = 0; j < numYTiles; j++) {
                tileMap[i][j] = new JGTile(i, j, engine.getTileCid(i, j));
            }
        }
    }
}

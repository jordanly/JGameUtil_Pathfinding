package main.java.jgpathfinder;

import jgame.JGPoint;
import jgame.impl.JGEngineInterface;
import main.java.jgmap.JGTileMap;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class JGPathfinder implements JGPathfinderInterface {
    private JGEngineInterface engine;
    private JGTileMap tileMap;
    private Node[][] nodeMap;

    public JGPathfinder(JGTileMap tileMap, JGEngineInterface engine) {
        this.tileMap = tileMap;
        this.engine = engine;

        nodeMap = new Node[engine.pfTilesX()][engine.pfTilesY()];
        for (int i = 0; i < engine.pfTilesX(); i++) {
            for (int j = 0; j < engine.pfTilesY(); j++) {
                nodeMap[i][j] = new Node(new JGPoint(i, j));
            }
        }
    }

    @Override
    public JGPath getPath(JGPoint source, JGPoint target) {
        PriorityQueue<Node> open = new PriorityQueue<Node>();
        HashSet<Node> closed = new HashSet<Node>();
        open.add(nodeMap[source.x][source.y]);

        Node current = null;
        while (open.peek() != null && open.peek() != nodeMap[target.x][target.y]) {
            current = open.poll();
            closed.add(current);

            for (JGPoint n :  tileMap.getNeighbors(current.index)) {
                Node neighbor = nodeMap[n.x][n.y];
                tileMap.setTile(neighbor.index, "gr");
                double cost = current.pathCost + tileMap.getCostToMove(current.index, neighbor.index);

                if (open.contains(neighbor) && cost < neighbor.pathCost) {
                    open.remove(neighbor);
                }
                if (closed.contains(neighbor) && cost < neighbor.pathCost) {
                    closed.remove(neighbor);
                }
                if (!open.contains(neighbor) && !closed.contains(neighbor)) {
                    neighbor.pathCost = cost;
                    neighbor.heuristicCost = getHeuristicCost(neighbor.index, target);
                    open.add(neighbor);
                    neighbor.setParent(current);
                }
            }
        }

        JGPath path = new JGPath();
        while (current.parent != null) {
            path.add(current.index);
            tileMap.setTile(current.index, "y");
            current = current.parent;
        }

        tileMap.setTile(source, "g");
        tileMap.setTile(target, "g");

        return path;
    }

    private double getHeuristicCost(JGPoint source, JGPoint target) {
        return distanceBetween(source, target); // TODO: closest heuristic, replace
    }

    private double distanceBetween(JGPoint source, JGPoint target) {
        double dx = Math.abs(source.x - target.x);
        double dy = Math.abs(source.y - target.y);

        final double D = 1; // TODO: find wtf d should be

        return D * (dx + dy);
    }

    private class Node implements Comparable {
        private JGPoint index;
        private Node parent;
        private double pathCost;
        private double heuristicCost;
        private int depth;

        public Node(JGPoint index) {
            this.index = index;
        }

        public void setParent(Node parent) {
            this.parent = parent;
            this.depth = parent.depth + 1;
        }

        @Override
        public int compareTo(Object o) {
            if (o == null || !(o instanceof Node)) {
                return 0; // TODO: throw exception?
            }

            Node target = (Node) o;

            double f = heuristicCost + pathCost;
            double targetF = target.heuristicCost + target.pathCost;

            return Double.compare(f, targetF);
        }
    }
}

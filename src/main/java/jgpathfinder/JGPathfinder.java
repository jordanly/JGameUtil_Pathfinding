package main.java.jgpathfinder;

import jgame.JGPoint;
import jgame.impl.JGEngineInterface;
import main.java.jgmap.JGTileMap;

import java.util.HashSet;
import java.util.PriorityQueue;

public class JGPathfinder implements JGPathfinderInterface {
    private JGEngineInterface engine;
    private JGPathfinderHeuristicInterface pathfinderHeuristic;
    private JGTileMap tileMap;
    private NodeMap nodeMap;

    public JGPathfinder(JGTileMap tileMap, JGPathfinderHeuristicInterface pathfinderHeuristic,
                        JGEngineInterface engine) {
        this.engine = engine;
        this.pathfinderHeuristic = pathfinderHeuristic;
        this.tileMap = tileMap;
        this.nodeMap = new NodeMap(engine);
    }

    @Override
    public JGPath getPath(JGPoint source, JGPoint target) {
        PriorityQueue<Node> open = new PriorityQueue<Node>();
        HashSet<Node> closed = new HashSet<Node>();
        open.add(nodeMap.getNode(source));

        Node current = null;
        while (open.peek() != nodeMap.getNode(target) && open.size() > 0) {
            current = open.poll();
            closed.add(current);

            for (JGPoint n :  tileMap.getNeighbors(current.index)) {
                Node neighbor = nodeMap.getNode(n);
                engine.setTile(neighbor.index, "gr");
                double cost = current.pathCost + tileMap.getCostToMove(current.index, neighbor.index);

                if (open.contains(neighbor) && cost < neighbor.pathCost) {
                    open.remove(neighbor);
                }
                if (closed.contains(neighbor) && cost < neighbor.pathCost) {
                    closed.remove(neighbor);
                }
                if (!open.contains(neighbor) && !closed.contains(neighbor)) {
                    neighbor.pathCost = cost;
                    neighbor.heuristicCost = pathfinderHeuristic.calculateHeuristicCost(neighbor.index, target);
                    open.add(neighbor);
                    neighbor.setParent(current);
                }
            }
        }

        if (open.peek() == null) {
            return null; // No path exists, TODO: throw exception?
        }

        JGPath path = new JGPath();
        current = open.poll();
        while (current.parent != null) {
            path.add(current.index);
            engine.setTile(current.index, "y");
            current = current.parent;
        }

        engine.setTile(source, "g");
        engine.setTile(target, "g");

        return path;
    }

    private class NodeMap {
        private Node[][] nodeMap;

        public NodeMap(JGEngineInterface engine) {
            int numXTiles = engine.pfTilesX();
            int numYTiles = engine.pfTilesY();

            nodeMap = new Node[numXTiles][numYTiles];
            for (int i = 0; i < numXTiles; i++) {
                for (int j = 0; j < numYTiles; j++) {
                    nodeMap[i][j] = new Node(new JGPoint(i, j));
                }
            }
        }

        public Node getNode(JGPoint index) {
            return nodeMap[index.x][index.y];
        }
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

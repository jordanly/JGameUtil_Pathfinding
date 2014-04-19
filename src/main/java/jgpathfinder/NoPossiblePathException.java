package main.java.jgpathfinder;

public class NoPossiblePathException extends Exception {
    public NoPossiblePathException() {
        super("No path found between source and target tiles");
    }
}

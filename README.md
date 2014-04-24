JGameUtil_Pathfinding
=====================

An A* tile-based pathfinding utility for the JGame library.

Overview
========

These are a series of classes designed to be used with the JGame library to find a path between two given map tile 
indices. This library consists of three interfaces: JGPathfinderInterface, JGTileMapInterface, and
JGPathfinderHeuristicInterface. These interfaces are extended into three concrete implementations: JGPathfinder,
JGTileMap, and JGPathfinderHeuristic. This allows for mixing-and-matching of our own provided classes and
perhaps another heuristic, tilemap, or even pathfinding algorithm as desired. There is also a JGPath class which 
holds the path found as a series of JGPoints that represent map tile indices.

This package also provides a sample test engine that allows you to test and see this library.

Requirements
============

This library needs JGame to function.

Interfaces
==========

As described above, there are three interfaces that allows developers extend or improve upon our own concrete 
implementation and still have it compatible with other components. They are:

  -JGTileMapInterface: The tilemap representation of your game map. It will be aware of blocked tiles and 
  the cost of moving on certain tiles.
  
  -JGPathfinderHeuristicInterface: The heuristic used in pathfinding.
  
  -JGPathfinderInterface: The actual pathfinder that uses the tilemap and heuristic provided to find a path
  between two points.
  
Concrete Classes
================

There are three concrete classes of the interfaces above provided. They are implemented as follows:

  -JGTileMap: The tilemap in this class is represented as a grid using the Manhatten Distance heuristic. Objects may
  only travel between direct neighbors (no diagonals).
  
  -JGPathfinderHeuristic: The heuristic used favors exploring tiles closer in distance to the goal.
  
  -JGPathfinder: This is the main concrete class that provides a standard way to find a path between two points. 
  This implementation is fairly quick for 80x60 tilemaps but can still be optimized.
  
Test Engine
===========

Finally, I have provided a quick test program that allows you to see the pathfinder in action. Place a desired 
start and end tiles using '1' and '2' keys respectively. Press the left mouse button to place blocked tiles. Press
the right mouse button to find a path between the start and end tiles. Press the space bar to create an object that 
will follow this path. Press 'c' to clear the map.

If you have any questions, please feel free to let me know by sending me a message on GitHub.

Resources Used
==============

http://www.cokeandcode.com/main/tutorials/path-finding/
http://theory.stanford.edu/~amitp/GameProgramming/

package graph;

import graph.WeightedGraph;
import maze.Juncture;
import maze.Maze;

/**
 * <P>
 * The MazeGraph is an extension of WeightedGraph. The constructor converts a
 * Maze into a graph.
 * </P>
 */
public class MazeGraph extends WeightedGraph<Juncture> {

	/*
	 * STUDENTS: SEE THE PROJECT DESCRIPTION FOR A MUCH MORE DETAILED
	 * EXPLANATION ABOUT HOW TO WRITE THIS CONSTRUCTOR
	 */

	/**
	 * <P>
	 * Construct the MazeGraph using the "maze" contained in the parameter to
	 * specify the vertices (Junctures) and weighted edges.
	 * </P>
	 * 
	 * <P>
	 * The Maze is a rectangular grid of "junctures", each defined by its X and
	 * Y coordinates, using the usual convention of (0, 0) being the upper left
	 * corner.
	 * </P>
	 * 
	 * <P>
	 * Each juncture in the maze should be added as a vertex to this graph.
	 * </P>
	 * 
	 * <P>
	 * For every pair of adjacent junctures (A and B) which are not blocked by a
	 * wall, two edges should be added: One from A to B, and another from B to
	 * A. The weight to be used for these edges is provided by the Maze. (The
	 * Maze methods getMazeWidth and getMazeHeight can be used to determine the
	 * number of Junctures in the maze. The Maze methods called "isWallAbove",
	 * "isWallToRight", etc. can be used to detect whether or not there is a
	 * wall between any two adjacent junctures. The Maze methods called
	 * "getWeightAbove", "getWeightToRight", etc. should be used to obtain the
	 * weights.)
	 * </P>
	 * 
	 * @param maze to be used as the source of information for adding vertices
	 *             and edges to this MazeGraph.
	 */
	public MazeGraph(Maze maze) {
		// adding vertexes to the graph
		for (int indexes = 0; indexes < maze.getMazeWidth(); indexes++) {
			for (int index = 0; index < maze.getMazeHeight(); index++) {
				Juncture junctures = new Juncture(indexes, index);
				this.addVertex(junctures);
			}
		}
		// adding edges to the maze if the area hasn't been filled up with an
		// edge or a wall isnt obstructing
		for (int indexes = 0; indexes < maze.getMazeWidth(); indexes++) {
			for (int index = 0; index < maze.getMazeHeight(); index++) {
				Juncture junc = new Juncture(indexes, index);
				if (!maze.isWallAbove(junc)) {
					this.addEdge(junc,
							new Juncture(junc.getX(), junc.getY() - 1),
							maze.getWeightAbove(junc));
				}
				if (!maze.isWallBelow(junc)) {
					this.addEdge(junc,
							new Juncture(junc.getX(), junc.getY() + 1),
							maze.getWeightBelow(junc));
				}
				if (!maze.isWallToLeft(junc)) {
					this.addEdge(junc,
							new Juncture(junc.getX() - 1, junc.getY()),
							maze.getWeightToLeft(junc));
				}
				if (!maze.isWallToRight(junc)) {
					this.addEdge(junc,
							new Juncture(junc.getX() + 1, junc.getY()),
							maze.getWeightToRight(junc));
				}
			}
		}
	}
}

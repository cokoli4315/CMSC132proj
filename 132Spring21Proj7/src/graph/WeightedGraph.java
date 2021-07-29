package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import org.w3c.dom.Node;

/**
 * <P>
 * This class represents a general "directed graph", which could be used for any
 * purpose. The graph is viewed as a collection of vertices, which are sometimes
 * connected by weighted, directed edges.
 * </P>
 * 
 * <P>
 * This graph will never store duplicate vertices.
 * </P>
 * 
 * <P>
 * The weights will always be non-negative integers.
 * </P>
 * 
 * <P>
 * The WeightedGraph will be capable of performing three algorithms:
 * Depth-First-Search, Breadth-First-Search, and Djikatra's.
 * </P>
 * 
 * <P>
 * The Weighted Graph will maintain a collection of "GraphAlgorithmObservers",
 * which will be notified during the performance of the graph algorithms to
 * update the observers on how the algorithms are progressing.
 * </P>
 */
public class WeightedGraph<V> {

	/*
	 * STUDENTS: You decide what data structure(s) to use to implement this
	 * class.
	 * 
	 * You may use any data structures you like, and any Java collections that
	 * we learned about this semester. Remember that you are implementing a
	 * weighted, directed graph.
	 */

	private Map<V, Set<V>> Graph;
	private Map<V, Map<V, Integer>> WeightedGraph;

	/*
	 * Collection of observers. Be sure to initialize this list in the
	 * constructor. The method "addObserver" will be called to populate this
	 * collection. Your graph algorithms (DFS, BFS, and Dijkstra) will notify
	 * these observers to let them know how the algorithms are progressing.
	 */
	private Collection<GraphAlgorithmObserver<V>> observerList;

	/**
	 * Initialize the data structures to "empty", including the collection of
	 * GraphAlgorithmObservers (observerList).
	 */
	public WeightedGraph() {
		// Setting obersversList, Graph and WeightedGraph to empty
		observerList = new ArrayList<>();
		Graph = new HashMap<V, Set<V>>();
		WeightedGraph = new HashMap<V, Map<V, Integer>>();

	}

	/**
	 * Add a GraphAlgorithmObserver to the collection maintained by this graph
	 * (observerList).
	 * 
	 * @param observer
	 */
	public void addObserver(GraphAlgorithmObserver<V> observer) {
		// added observer to the obsrverList collection
		observerList.add(observer);

	}

	/**
	 * Add a vertex to the graph. If the vertex is already in the graph, throw
	 * an IllegalArgumentException.
	 * 
	 * @param vertex vertex to be added to the graph
	 * @throws IllegalArgumentException if the vertex is already in the graph
	 */
	public void addVertex(V vertex) {
		// if the weightedGraph contains a certain vertex the method will throw
		// an illgal exception
		if (WeightedGraph.containsKey(vertex)) {
			throw new IllegalArgumentException();
			// else the method adds the vertex and a an empty data structure
			// into the weightedGraph
		} else {
			Graph.put(vertex, new HashSet<>());
			WeightedGraph.put(vertex, new HashMap<V, Integer>());
		}
	}

	/**
	 * Searches for a given vertex.
	 * 
	 * @param vertex the vertex we are looking for
	 * @return true if the vertex is in the graph, false otherwise.
	 */
	public boolean containsVertex(V vertex) {
		// if the vertex in the paramter is inside the weightedGraph then the
		// method returns true else it returns false
		if (WeightedGraph.containsKey(vertex)) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * <P>
	 * Add an edge from one vertex of the graph to another, with the weight
	 * specified.
	 * </P>
	 * 
	 * <P>
	 * The two vertices must already be present in the graph.
	 * </P>
	 * 
	 * <P>
	 * This method throws an IllegalArgumentExeption in three cases:
	 * </P>
	 * <P>
	 * 1. The "from" vertex is not already in the graph.
	 * </P>
	 * <P>
	 * 2. The "to" vertex is not already in the graph.
	 * </P>
	 * <P>
	 * 3. The weight is less than 0.
	 * </P>
	 * 
	 * @param from   the vertex the edge leads from
	 * @param to     the vertex the edge leads to
	 * @param weight the (non-negative) weight of this edge
	 * @throws IllegalArgumentException when either vertex is not in the graph,
	 *                                  or the weight is negative.
	 */
	public void addEdge(V from, V to, Integer weight) {
		// This method adds an edge into the maze if the weighted graph doesnt
		// contain the from vertex or the to vertex and the weight is greater
		// than or equals to 0 it returns the edge to be added with its weight
		if (WeightedGraph.containsKey(from) && WeightedGraph.containsKey(to)
				&& weight >= 0) {
			if (WeightedGraph.get(from).containsKey(to)) {
				WeightedGraph.get(from).remove(to);
				WeightedGraph.get(from).put(to, weight);
				// else the method returns a specifif edge with weight specified
			} else {
				WeightedGraph.get(from).put(to, weight);
			}
			// else if non of the previous conditions apply the method throws an
			// illegal Argument Exception
		} else if (!WeightedGraph.containsKey(from)
				&& !WeightedGraph.containsKey(to) && weight < 0) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * <P>
	 * Returns weight of the edge connecting one vertex to another. Returns null
	 * if the edge does not exist.
	 * </P>
	 * 
	 * <P>
	 * Throws an IllegalArgumentException if either of the vertices specified
	 * are not in the graph.
	 * </P>
	 * 
	 * @param from vertex where edge begins
	 * @param to   vertex where edge terminates
	 * @return weight of the edge, or null if there is no edge connecting these
	 *         vertices
	 * @throws IllegalArgumentException if either of the vertices specified are
	 *                                  not in the graph.
	 */

	public Integer getWeight(V from, V to) {
		// This emthod returns the weight of the edge if the weighted graph
		// contains the to from vertex
		if (WeightedGraph.containsKey(from) && WeightedGraph.containsKey(to)
				&& WeightedGraph.get(from).containsKey(to)) {
			return WeightedGraph.get(from).get(to);
			// else the method throws an illegal argument exception if the
			// wieghtedgraph doesnt contain the to and from vertex
		} else if (!WeightedGraph.containsKey(from)
				|| !WeightedGraph.containsKey(to)) {
			throw new IllegalArgumentException();
			// else returns null
		} else {
			return null;
		}

	}

	/**
	 * <P>
	 * This method will perform a Breadth-First-Search on the graph. The search
	 * will begin at the "start" vertex and conclude once the "end" vertex has
	 * been reached.
	 * </P>
	 * 
	 * <P>
	 * Before the search begins, this method will go through the collection of
	 * Observers, calling notifyBFSHasBegun on each one.
	 * </P>
	 * 
	 * <P>
	 * Just after a particular vertex is visited, this method will go through
	 * the collection of observers calling notifyVisit on each one (passing in
	 * the vertex being visited as the argument.)
	 * </P>
	 * 
	 * <P>
	 * After the "end" vertex has been visited, this method will go through the
	 * collection of observers calling notifySearchIsOver on each one, after
	 * which the method should terminate immediately, without processing further
	 * vertices.
	 * </P>
	 * 
	 * @param start vertex where search begins
	 * @param end   the algorithm terminates just after this vertex is visited
	 */
	public void DoBFS(V start, V end) {
		// for each loop to go through the observer list collection stating the
		// breadth first traversal has started
		for (GraphAlgorithmObserver<V> names : observerList) {
			names.notifyBFSHasBegun();
		}
		// created a set to store values that have been visited and Queue to
		// store values that havent been visited but are being accessed
		Set<V> visited = new HashSet<V>();
		Queue<V> discoveredSet = new LinkedList<>();
		// first add the starting vertex to the discoveredSet
		discoveredSet.add(start);
		// when the discoverd set isnt empty this loop runs
		while (discoveredSet.isEmpty() == false) {
			// the head of the linkedlist to be removed and added to the visited
			// set
			V curr = discoveredSet.remove();
			// if the visited set doesnt contain the head of the discovered set
			// then it adds the head of the discovered set into it
			if (!visited.contains(curr)) {
				visited.add(curr);
				// notifys that the head that was removed from the disocvered
				// set has been visited and placed into the visted Set
				for (GraphAlgorithmObserver<V> names : observerList) {
					names.notifyVisit(curr);
				}
				// search ends when the current value is equals to the ending
				// vertex
				if (curr.equals(end)) {
					// loops through observer list collection and calls on
					// search is over
					for (GraphAlgorithmObserver<V> names : observerList) {
						names.notifySearchIsOver();
					}
					return;
				}
				// neighbors to the current value which is the head of the
				// discovered set
				for (V values : WeightedGraph.get(curr).keySet()) {
					if (!visited.contains(values)) {
						discoveredSet.add(values);
					}

				}
			}

		}

	}

	/**
	 * <P>
	 * This method will perform a Depth-First-Search on the graph. The search
	 * will begin at the "start" vertex and conclude once the "end" vertex has
	 * been reached.
	 * </P>
	 * 
	 * <P>
	 * Before the search begins, this method will go through the collection of
	 * Observers, calling notifyDFSHasBegun on each one.
	 * </P>
	 * 
	 * <P>
	 * Just after a particular vertex is visited, this method will go through
	 * the collection of observers calling notifyVisit on each one (passing in
	 * the vertex being visited as the argument.)
	 * </P>
	 * 
	 * <P>
	 * After the "end" vertex has been visited, this method will go through the
	 * collection of observers calling notifySearchIsOver on each one, after
	 * which the method should terminate immediately, without visiting further
	 * vertices.
	 * </P>
	 * 
	 * @param start vertex where search begins
	 * @param end   the algorithm terminates just after this vertex is visited
	 */
	public void DoDFS(V start, V end) {
		// for each loop to go through the observer list collection stating the
		// depth first traversal has started
		for (GraphAlgorithmObserver<V> start1 : observerList) {
			start1.notifyDFSHasBegun();
		}
		// create stack and set for visited and unvisited values
		Stack<V> unvisited = new Stack<V>();
		Set<V> visited = new HashSet<V>();
		unvisited.push(start);
		while (unvisited.isEmpty() == false) {
			V curr = unvisited.pop();
			if (!visited.contains(curr)) {
				visited.add(curr);
				for (GraphAlgorithmObserver<V> visit : observerList) {
					visit.notifyVisit(curr);
				}
				if (curr.equals(end)) {
					for (GraphAlgorithmObserver<V> ends : observerList) {
						ends.notifySearchIsOver();

					}
					return;
				}

				for (V visit : WeightedGraph.get(curr).keySet()) {
					if (!visited.contains(visit)) {
						unvisited.push(visit);
					}

				}

			}

		}

	}

	/**
	 * <P>
	 * Perform Dijkstra's algorithm, beginning at the "start" vertex.
	 * </P>
	 * 
	 * <P>
	 * The algorithm DOES NOT terminate when the "end" vertex is reached. It
	 * will continue until EVERY vertex in the graph has been added to the
	 * finished set.
	 * </P>
	 * 
	 * <P>
	 * Before the algorithm begins, this method goes through the collection of
	 * Observers, calling notifyDijkstraHasBegun on each Observer.
	 * </P>
	 * 
	 * <P>
	 * Each time a vertex is added to the "finished set", this method goes
	 * through the collection of Observers, calling notifyDijkstraVertexFinished
	 * on each one (passing the vertex that was just added to the finished set
	 * as the first argument, and the optimal "cost" of the path leading to that
	 * vertex as the second argument.)
	 * </P>
	 * 
	 * <P>
	 * After all of the vertices have been added to the finished set, the
	 * algorithm will calculate the "least cost" path of vertices leading from
	 * the starting vertex to the ending vertex. Next, it will go through the
	 * collection of observers, calling notifyDijkstraIsOver on each one,
	 * passing in as the argument the "lowest cost" sequence of vertices that
	 * leads from start to end (I.e. the first vertex in the list will be the
	 * "start" vertex, and the last vertex in the list will be the "end"
	 * vertex.)
	 * </P>
	 * 
	 * @param start vertex where algorithm will start
	 * @param end   special vertex used as the end of the path reported to
	 *              observers via the notifyDijkstraIsOver method.
	 */

	public void DoDijsktra(V start, V end) {
		// looping through the observerList collection calling on the notify
		// djikstras has started
		for (GraphAlgorithmObserver<V> start1 : observerList) {
			start1.notifyDijkstraHasBegun();
		}
		// created a hashSetfor visited values a Map for cost of each vertex and
		// pred of each value
		Set<V> visited = new HashSet<V>();
		Map<V, Integer> cost = new HashMap<V, Integer>();
		Map<V, V> pred = new HashMap<V, V>();
		// adding thr start vertex to the visited set after we already visited
		// the start vertex in the beginnning of the graph
		for (V p : WeightedGraph.keySet()) {
			pred.put(p, null);
		}
		// setting the cost from start to start vertex as zero
		cost.put(start, 0);
		// looping through the different vertex inside of the keyset of the
		// weighted graph
		for (V star : WeightedGraph.keySet()) {
			if (!star.equals(start)) {
				cost.put(star, Integer.MAX_VALUE);
			}
		}
		// when the visited set doesn't go through all the vertices in the map
		// then the method keeps running till the values are equal
		while (visited.size() != WeightedGraph.keySet().size()) {
			// if the visited set doesnt contain all the values in the map then
			// the visited set adds all the vertices that havent been added into
			// the visited set
			int smallest = Integer.MAX_VALUE;
			V smallestVertex = start;
			// making a loop to go through the cost of the vertices inside the
			// cost map
			for (V vertices : cost.keySet()) {
				if (cost.get(vertices) < smallest&&(!visited.contains(vertices))) {
					smallest = cost.get(vertices);
					smallestVertex = vertices;

				}
			}
			// addss the start vertex each time it has been visited to the
			// visited set
			visited.add(smallestVertex);
			for (GraphAlgorithmObserver<V> observer : observerList) {
				observer.notifyDijkstraVertexFinished(smallestVertex, smallest);
			}
			// goes through the neighbors of the vertex already isnide the
			// visited set
			for (V smallestValue : WeightedGraph.get(smallestVertex).keySet()) {
				// if the cost of the smaallestvertex plus the weight in the
				// graph is less than the cost of the smallest value
				if (cost.get(smallestVertex) + WeightedGraph.get(smallestVertex)
						.get(smallestValue) < cost.get(smallestValue)) {
					// you se the cost as the cost of the smallest value
					cost.put(smallestValue,
							cost.get(smallestVertex) + WeightedGraph
									.get(smallestVertex).get(smallestValue));
					// set the predeccessor to the smallest vlaue to the
					// smallest vertex
					pred.put(smallestValue, smallestVertex);
				}
			}
		}
		// make a new linked list for the lowest cost that the graph will follow
		// to obtain the fastest route to the end
		List<V> lowestCost = new LinkedList<>();
		V curr = end;
		lowestCost.add(end);
		// while the start is not the end the graph keeps going over
		while (curr != start) {
			V predessecor = pred.get(curr);
			lowestCost.add(0, predessecor);
			curr = predessecor;
		}
		// calls the observer list collection and returns the lowest cost path
		// to the end of the maze
		for (GraphAlgorithmObserver<V> observer : observerList) {
			observer.notifyDijkstraIsOver(lowestCost);
		}

	}
}

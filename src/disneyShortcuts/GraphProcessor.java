package disneyShortcuts;

import edu.princeton.cs.algs4.DijkstraUndirectedSP;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.In;

/**
 * Contains Graph Logic. Reads in a graph from a CSV file.
 * 
 * @author Noah Ewell + Abbas Al-Younis
 */
public class GraphProcessor {
	// declare fields
	private String fileName = "src/disneyShortcuts/Resources/disneyGraph.txt";
	private In in = new In(fileName);
	private EdgeWeightedGraph graph = new EdgeWeightedGraph(in);
	private DijkstraUndirectedSP paths;
	private int destination;
	
	/**
	 * Constructor initializes the fields, and creates a new DijkstraUndirectedSP based on 
	 * <code>graph</code> and <code>source</code>.
	 * 
	 * @param source		the starting vertex
	 * @param destination	the final vertex
	 */
	public GraphProcessor(int source, int destination) {
		this.destination = destination;
		paths = new DijkstraUndirectedSP(graph, source);
	}
	
	/**
	 * Returns the shortest path between a source vertex and vertex v, based on DikjkstraUndirectedSP.
	 * 
	 * @return an iterable of edges in the graph
	 */
	public Iterable<Edge> pathTo() {
		return paths.pathTo(destination);
	}
	
}
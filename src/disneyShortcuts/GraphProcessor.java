package disneyShortcuts;

import edu.princeton.cs.algs4.DijkstraUndirectedSP;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.In;

/**
 * Contains Graph Logic. Reads in a graph from a CSV file.
 * 
 * @author Noah Ewell + Abbas Al-Younis
 */
public class GraphProcessor {
	String fileName = "src/disneyShortcuts/disneyGraph.txt";
	In in = new In(fileName);
	EdgeWeightedGraph graph = new EdgeWeightedGraph(in);
	int s = 1;
	DijkstraUndirectedSP path = new DijkstraUndirectedSP(graph, s);
	
	// TODO No clue how we're gonna use this class... We'll probably need to think of something else.
}

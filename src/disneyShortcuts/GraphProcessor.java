package disneyShortcuts;

import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;

/**
 * Contains Graph Logic. Reads in a graph from a CSV file.
 * 
 * @author Noah Ewell + Abbas Al-Younis
 */
public class GraphProcessor {
	String fileName = "src/disneyShortcuts/disneyGraph.txt";
	In in = new In(fileName);
	Graph graph = new Graph(in);
	int s = 1;
	BreadthFirstPaths bfs = new BreadthFirstPaths(graph, s);
}

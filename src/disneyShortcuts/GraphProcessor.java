package disneyShortcuts;

import java.util.ArrayList;
import java.util.List;

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
	private static String fileName = "src/disneyShortcuts/Resources/disneyGraph.txt";
	private static EdgeWeightedGraph graph = new EdgeWeightedGraph(new In(fileName));
	
	// TODO No clue how we're gonna use this class... We'll probably need to think of something else.
	
	public static Integer[] getPath(int source, int destination) {
		DijkstraUndirectedSP path = new DijkstraUndirectedSP(graph, source);
		List<Integer> list = new ArrayList<>();
		
		for (Edge e : path.pathTo(destination)) {
			int v = e.either();
			int w = e.other(v);
			list.add(v);
			list.add(w);
		}
		
		return list.toArray(new Integer[list.size()]);
	}
}

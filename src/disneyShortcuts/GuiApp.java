package disneyShortcuts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import edu.princeton.cs.algs4.DijkstraUndirectedSP;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.RedBlackBST;

/**
 * Creates and initializes GUI Components for Disney Shortcuts. 
 * Uses eventlisteners to interact with the user. 
 * Reads in ride information from a CSV file.
 * 
 * @author Noah Ewell + Abbas Al-Younis
 */
public class GuiApp extends JFrame {
	private static final long serialVersionUID = 6506778447729462611L;
	
	private JPanel mainContent;
	private JTextField disclaimerTextField;
	private JTextField txtStart;
	private JTextField txtDestination;
	private JComboBox<String> destinationComboBox;
	private JComboBox<String> startComboBox;
	private JButton submitButton;
	private JTextArea directionsTextArea;
	private JLabel disneyBackgroundImage;
	private JTextArea welcomeMessage;
	private JTextField txtInstructions;
	

	// TEST DRIVER
	public static void main(String[] args) {
		
		// Create Symbol Table
		Ride[] rides = getRides("src/disneyShortcuts/Resources/disneyRides.csv");
		RedBlackBST<Integer, Ride> st = new RedBlackBST<>();
		fillSymbolTable(st, rides);
		
		// Load GUI
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiApp frame = new GuiApp(st);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Creates the frame, initializes components, and creates events.
	 */
	public GuiApp(RedBlackBST<Integer, Ride> st) {
		// =========== CREATE FRAME & INITIALIZE COMPONENTS ==========
		setUp();

		setContentPane(mainContent);

		setBackgroundImage();

		createInstructionsTextArea();

		createDisclaimerTextField();

		createSubmitButton();

		createStartTextBox();

		createDestinationTextBox();

		createStartComboBox(st);

		createDestinationComboBox(st);
		
		createWelcomeTextArea();
		
		createInstructionsTextField();
		
		GroupLayout gl_mainContent = new GroupLayout(mainContent);
		
		configureGroupLayout(gl_mainContent);
	    
	    mainContent.setLayout(gl_mainContent);

		// ============== CREATE EVENTS ================
	    
	    // When Submit is clicked
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// TODO destinationComboBox.getSelectedItem();
				// TODO startComboBox.getSelectedItem();
				Ride[] rides = getRides("src/disneyShortcuts/Resources/disneyRides.csv");
				RedBlackBST<Integer, Ride> st = new RedBlackBST<>();
				fillSymbolTable(st, rides);
				
				String s = startComboBox.getSelectedItem().toString();
				String d = destinationComboBox.getSelectedItem().toString();
				
				int start = Integer.parseInt(s.replaceAll("[^0-9]", ""));
				int destination = Integer.parseInt(d.replaceAll("[^0-9]", ""));
				
				Integer[] path = GraphProcessor.getPath(start, destination);
				
				for (int i = path[0]; i < path.length; i++) {
					Ride r1 = st.get(i);
					Ride r2 = st.get((i+1));
					Graphics g = mainContent.getGraphics();
					Graphics2D g2d = (Graphics2D) g;
					g2d.setStroke(new BasicStroke(3));
					g2d.setColor(Color.RED);
					System.out.println("Drawing line from (" + r1.getX() + ", " + r1.getY() + ") to (" + r2.getX() + ", " + r2.getY() + ")");
					g2d.drawLine(r1.getX(), r1.getY(), r2.getX(), r2.getY());
				}
				
				
				// update directions
				directionsTextArea.setText("Directions Appear Here!");
			}
		});
		
		// Listens to mouse clicks (for troubleshooting)
		addMouseListener(new MouseAdapter() {
			int x1, x2, y1, y2;
//			int count = 0;
			@Override
			public void mousePressed(MouseEvent e) {
//				Point p = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), mainContent);
//				x1 = p.x;
//				y1 = p.y;
//				count++;
//				System.out.println(count + "-- X: " + x1 + " Y: " + y1);
				if (x1 == 0 && y1 == 0) {
					Point p2 = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), mainContent);
					x1 = p2.x;
					y1 = p2.y;
					System.out.println("1st -- X: " + x1 + " Y: " + y1);
				} else {
					Point p2 = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), mainContent);
					x2 = p2.x;
					y2 = p2.y;
					if (x1 > x2) {
						int length = x1 - x2;
						if (y1 > y2) {
							int width = y1 - y2;
							double lineDistance = Math.hypot(length, width);
							Graphics g = mainContent.getGraphics();
							Graphics2D g2d = (Graphics2D) g;
							g2d.setStroke(new BasicStroke(3));
							g2d.setColor(Color.RED);
							g2d.drawLine(x1, y1, x2, y2);
							System.out.println("2nd -- X: " + x2 + " Y: " + y2 + " Distance: " + lineDistance);
							x1 = x2 = y1 = y2 = 0;
						}
						else {
							int width = y2 - y1;
							double lineDistance = Math.hypot(length, width);
							Graphics g = mainContent.getGraphics();
							Graphics2D g2d = (Graphics2D) g;
							g2d.setStroke(new BasicStroke(3));
							g2d.setColor(Color.RED);
							g2d.drawLine(x1, y1, x2, y2);
							System.out.println("2nd -- X: " + x2 + " Y: " + y2 + " Distance: " + lineDistance);
							x1 = x2 = y1 = y2 = 0;
						}
					}
					else {
						int length = x2 - x1;
						if (y1 > y2) {
							int width = y1 - y2;
							double lineDistance = Math.hypot(length, width);
							Graphics g = mainContent.getGraphics();
							Graphics2D g2d = (Graphics2D) g;
							g2d.setStroke(new BasicStroke(3));
							g2d.setColor(Color.RED);
							g2d.drawLine(x1, y1, x2, y2);
							System.out.println("2nd -- X: " + x2 + " Y: " + y2 + " Distance: " + lineDistance);
							x1 = x2 = y1 = y2 = 0;
						}
						else {
							int width = y2 - y1;
							double lineDistance = Math.hypot(length, width);
							Graphics g = mainContent.getGraphics();
							Graphics2D g2d = (Graphics2D) g;
							g2d.setStroke(new BasicStroke(3));
							g2d.setColor(Color.RED);
							g2d.drawLine(x1, y1, x2, y2);
							System.out.println("2nd -- X: " + x2 + " Y: " + y2 + " Distance: " + lineDistance);
							x1 = x2 = y1 = y2 = 0;
						}
					}
				}
			}
		});
	}

	// ============ GUI COMPONENT METHODS ===========
	
	/**
	 * Creates the instructions banner.
	 */
	private void createInstructionsTextField() {
		txtInstructions = new JTextField();
		txtInstructions.setEditable(false);
		txtInstructions.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 14));
		txtInstructions.setText("Instructions");
		txtInstructions.setHorizontalAlignment(SwingConstants.CENTER);
		txtInstructions.setColumns(10);
	}

	/**
	 * Creates the welcome message.
	 */
	private void createWelcomeTextArea() {
		welcomeMessage = new JTextArea();
		welcomeMessage.setOpaque(false);
		welcomeMessage.setWrapStyleWord(true);
		welcomeMessage.setEditable(false);
		welcomeMessage.setForeground(Color.BLUE);
		welcomeMessage.setText("Welcome to... Disney Shortcuts!");
		welcomeMessage.setLineWrap(true);
		welcomeMessage.setFont(new Font("Copperplate Gothic Light", Font.BOLD | Font.ITALIC, 24));
	}

	/**
	 * Modifies positioning of various components in the group layout.
	 * 
	 * @param gl_mainContent
	 */
	private void configureGroupLayout(GroupLayout gl_mainContent) {
		gl_mainContent.setHorizontalGroup(
				gl_mainContent.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_mainContent.createSequentialGroup()
						.addGroup(gl_mainContent.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_mainContent.createSequentialGroup()
								.addComponent(disneyBackgroundImage)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_mainContent.createParallelGroup(Alignment.LEADING)
									.addComponent(submitButton, GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
									.addComponent(destinationComboBox, 0, 262, Short.MAX_VALUE)
									.addComponent(txtStart, GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
									.addComponent(directionsTextArea, GroupLayout.PREFERRED_SIZE, 262, GroupLayout.PREFERRED_SIZE)
									.addComponent(welcomeMessage, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 262, GroupLayout.PREFERRED_SIZE)
									.addComponent(txtInstructions, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
									.addComponent(startComboBox, Alignment.TRAILING, 0, 262, Short.MAX_VALUE)
									.addComponent(txtDestination, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)))
							.addComponent(disclaimerTextField, GroupLayout.DEFAULT_SIZE, 1166, Short.MAX_VALUE))
						.addContainerGap())
			);
			gl_mainContent.setVerticalGroup(
				gl_mainContent.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_mainContent.createSequentialGroup()
						.addGroup(gl_mainContent.createParallelGroup(Alignment.LEADING)
							.addComponent(disneyBackgroundImage, GroupLayout.PREFERRED_SIZE, 580, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_mainContent.createSequentialGroup()
								.addComponent(welcomeMessage, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
								.addGap(1)
								.addComponent(txtInstructions, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(directionsTextArea, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(txtStart, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(startComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(txtDestination, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(destinationComboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(submitButton, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(disclaimerTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			);
	}

	/**
	 * Creates the destination drop down menu.
	 * @param st 
	 */
	private void createDestinationComboBox(RedBlackBST<Integer, Ride> st) {
		destinationComboBox = new JComboBox<String>();
		destinationComboBox.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 11));
		
		for (Integer key : st.keys()) {
			String ride = st.get(key).getRideID() + " - " + st.get(key).getName();
			destinationComboBox.addItem(ride);
		}
	}

	/**
	 * Creates the start drop down menu.
	 */
	private void createStartComboBox(RedBlackBST<Integer, Ride> st) {
		startComboBox = new JComboBox<String>();
		startComboBox.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 11));

		for (Integer key : st.keys()) {
			String ride = st.get(key).getRideID() + " - " + st.get(key).getName();
			startComboBox.addItem(ride);
		}
	}

	/**
	 * Creates the destination text box.
	 */
	private void createDestinationTextBox() {
		txtDestination = new JTextField();
		txtDestination.setEditable(false);
		txtDestination.setText("Destination");
		txtDestination.setHorizontalAlignment(SwingConstants.CENTER);
		txtDestination.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 13));
		txtDestination.setColumns(10);
	}

	/**
	 * Creates the start text box.
	 */
	private void createStartTextBox() {
		txtStart = new JTextField();
		txtStart.setEditable(false);
		txtStart.setText("Start");
		txtStart.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 13));
		txtStart.setHorizontalAlignment(SwingConstants.CENTER);
		txtStart.setColumns(10);
	}

	/**
	 * Creates the submit button.
	 */
	private void createSubmitButton() {
		submitButton = new JButton("Submit");
		submitButton.setForeground(Color.WHITE);
		submitButton.setBorder(null);
		submitButton.setBackground(Color.BLUE);
		submitButton.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 13));
	}

	/**
	 * Creates the disclaimer text field at the bottom.
	 */
	private void createDisclaimerTextField() {
		disclaimerTextField = new JTextField();
		disclaimerTextField.setOpaque(false);
		disclaimerTextField.setEditable(false);
		disclaimerTextField.setForeground(Color.RED);
		disclaimerTextField.setBorder(null);
		disclaimerTextField.setHorizontalAlignment(SwingConstants.CENTER);
		disclaimerTextField.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 12));
		disclaimerTextField.setText(
				"Disclaimer: We are in no way affiliated with Disney. The above image is not owned by us. "
				+ "This was created for fun and not for distribution.");
		disclaimerTextField.setColumns(10);
	}

	/**
	 * Creates the directions text box.
	 * 
	 * @return the text box for directions to go in.
	 */
	private JTextArea createInstructionsTextArea() {
		directionsTextArea = new JTextArea();
		directionsTextArea.setOpaque(false);
		directionsTextArea.setEditable(false);
		directionsTextArea.setWrapStyleWord(true);
		directionsTextArea.setLineWrap(true);
		directionsTextArea.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 14));
		directionsTextArea.setText(
				"Please select the ride you're currently closest to and the ride you would like to go to "
				+ "and click submit.");
		return directionsTextArea;
	}

	/**
	 * Creates a new JLabel and sets the disneyBackgroundImage
	 * 
	 * @return the background image
	 */
	private JLabel setBackgroundImage() {
		disneyBackgroundImage = new JLabel("");
		disneyBackgroundImage.setBorder(null);
		disneyBackgroundImage.setIcon(
				new ImageIcon(GuiApp.class.getResource("/disneyShortcuts/Resources/disneyParkMap.jpg")));
		return disneyBackgroundImage;
	}

	/**
	 * Sets the close operation, boundaries, title, icon, and creates a new JPanel.
	 */
	private void setUp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 650);
		setResizable(false);
		setTitle("Disney Shortcuts (Not-Affiliated)");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(GuiApp.class.getResource("/disneyShortcuts/Resources/disneyCastle.png")));
		mainContent = new JPanel();
		mainContent.setOpaque(false);
		mainContent.setBackground(Color.WHITE);
		mainContent.setBorder(new EmptyBorder(5, 5, 5, 5));
	}
	
	// ============ UTILITY METHODS ==============
	
	/**
	 * Reads in all rides form csv
	 * 
	 * @param fileName
	 * @return Ride[] rides
	 */
	private static Ride[] getRides(String fileName) {
		List<Ride> rideList = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			reader.readLine();
			String line;
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split(",");
				rideList.add(new Ride(Integer.parseInt(tokens[0]), tokens[1], tokens[2], Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4])));
			}
		} catch (IOException e) {
			System.out.println("A problem occured reading in the songs.");
			e.printStackTrace();
		}
		return rideList.toArray(new Ride[rideList.size()]);
	}
	
	/**
	 * Fills the symbol table with Rides. Each ride gets a unique id from the range that
	 * is chronologically chosen. Each rides ID on the symbol table should match it's ID
	 * as a ride at Disneyland.
	 * 
	 * @param st symbol table
	 * @param games an array of games
	 */
	private static void fillSymbolTable(RedBlackBST<Integer, Ride> st, Ride[] rides) {
		int id = 0;
		for (Ride r : rides) {
			id++;
			st.put(id, r);
		}
	}
	
	/**
	 * Reads in an edgeweighted graph and computes the shortest path.
	 * 
	 * @return the shortest path between rides, considering weighted edges
	 */
	private static DijkstraUndirectedSP createGraph() {
		String fileName = "src/disneyShortcuts/Resources/disneyGraph.txt";
		In in = new In(fileName);
		EdgeWeightedGraph graph = new EdgeWeightedGraph(in);
		int s = 1; // TODO will need to be changed to coincide with whatever ride the user selects as a start
		DijkstraUndirectedSP path = new DijkstraUndirectedSP(graph, s);
		return path;
	}
	
	
}

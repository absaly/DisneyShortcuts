package disneyShortcuts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdAudio;

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
	private JButton clearButton;
	private JButton aboutButton;
	
	String[] knownBugs = new String[] {
			"Long paths may cause the buttons to disappear.",
			"Drawings may randomly clear themselves.",
	};
	
	String[] controls = new String[] {
			"C - Clears contents",
			"V - Changes to a random color",
			"Submit - Draws shortest path between locations",
			"Clear - Clears contents",
			"About - Loads project information"
	};
	
	private Color currentColor = Color.BLACK;
	
	// Starts Application
	public static void main(String[] args) throws FileNotFoundException {
		
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
	 * Creates components, organizes components, and creates events.
	 */
	public GuiApp(RedBlackBST<Integer, Ride> st) {
		
		// =========== CREATE COMPONENTS ==========
		
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
		
		createClearButton();
		
		createAboutButton();
		
		// ========== ORGANIZE COMPONENTS =========
		
		GroupLayout gl_mainContent = new GroupLayout(mainContent);
		
		configureGroupLayout(gl_mainContent);
	    
	    mainContent.setLayout(gl_mainContent);
	    mainContent.setFocusable(true);
	    mainContent.requestFocusInWindow();

		// ============== CREATE EVENTS ================
	    
	    addSubmitActionListener(st);
	    
	    // clear when C is pressed
	    clearKeybind();
	
		// change color when V is pressed
		randColorKeybind();
	}
	
	// ============ GUI COMPONENT METHODS ===========

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
									.addComponent(directionsTextArea, GroupLayout.PREFERRED_SIZE, 262, GroupLayout.PREFERRED_SIZE)
									.addComponent(startComboBox, 0, 262, Short.MAX_VALUE)
									.addComponent(destinationComboBox, 0, 262, Short.MAX_VALUE)
									.addGroup(gl_mainContent.createSequentialGroup()
										.addComponent(submitButton, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(clearButton, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(aboutButton, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE))
									.addComponent(txtDestination, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
									.addComponent(txtStart, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
									.addComponent(welcomeMessage, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 262, GroupLayout.PREFERRED_SIZE)
									.addComponent(txtInstructions, GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)))
							.addComponent(disclaimerTextField, GroupLayout.DEFAULT_SIZE, 1166, Short.MAX_VALUE))
						.addContainerGap())
			);
			gl_mainContent.setVerticalGroup(
				gl_mainContent.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_mainContent.createSequentialGroup()
						.addGroup(gl_mainContent.createParallelGroup(Alignment.LEADING, false)
							.addComponent(disneyBackgroundImage, GroupLayout.PREFERRED_SIZE, 580, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_mainContent.createSequentialGroup()
								.addComponent(welcomeMessage, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(txtInstructions, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(directionsTextArea)
								.addGap(18)
								.addComponent(txtStart, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(startComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(txtDestination, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(destinationComboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addGroup(gl_mainContent.createParallelGroup(Alignment.BASELINE)
									.addComponent(submitButton, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
									.addComponent(aboutButton, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
									.addComponent(clearButton, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(disclaimerTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			);
	}
	
	/**
	 * Creates an about button to display a popup.
	 * 
	 * @return about button
	 */
	private JButton createAboutButton() {
		aboutButton = new JButton("About");
		aboutButton.setOpaque(true);
		aboutButton.setForeground(Color.WHITE);
		aboutButton.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 13));
		aboutButton.setBorder(null);
		aboutButton.setBackground(Color.BLACK);
		
		aboutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String[] readMe = getReadMe();
				StringBuilder readMeDesc = new StringBuilder("DESCRIPTION: \n");
				for (String s : readMe) {
					readMeDesc.append(s).append("\n");
				}
				
				StringBuilder bugsInfo = new StringBuilder("KNOWN BUGS: \n");
				for (String s : knownBugs) {
					bugsInfo.append(s).append("\n");
				}
				
				StringBuilder controlsInfo = new StringBuilder("CONTROLS: \n");
				for (String s : controls) {
					controlsInfo.append(s).append("\n");
				}
				
				JOptionPane.showMessageDialog(null, readMeDesc + "\n" + bugsInfo + "\n" + controlsInfo, 
						"About Disney Shortcuts", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		return aboutButton;
	}

	
	/**
	 * Creates a clear button.
	 * 
	 * @return clear button
	 */
	private JButton createClearButton() {
		clearButton = new JButton("Clear");
		clearButton.setOpaque(true);
		clearButton.setForeground(Color.WHITE);
		clearButton.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 13));
		clearButton.setBorder(null);
		clearButton.setBackground(Color.RED);
		
		clearButton.setMnemonic(KeyEvent.VK_C);
		
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearContents();
			}
		});
		
		return clearButton;
	}
	
	
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
		submitButton.setOpaque(true);
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
		directionsTextArea.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 11));
		directionsTextArea.setText("Please select the ride you're currently closest to and the ride "
				+ "you would like to go to and click submit.");
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
	 * Plays a sound in the background.
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
		StdAudio.playInBackground("src/disneyShortcuts/Resources/backgroundMusic.wav");
	}
	
	// ============ UTILITY METHODS ==============
	
	/**
	 * Reads in all rides from a csv
	 * 
	 * @param fileName			the csv file with rides
	 * @return Ride[] rides		an array of the rides
	 */
	private static Ride[] getRides(String fileName) {
		List<Ride> rideList = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			reader.readLine();
			String line;
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split(",");
				if (tokens[2].contains("Main")) { // Avoids comma in "Main Street, USA".
					rideList.add(new Ride(Integer.parseInt(tokens[0]), tokens[1], tokens[2], 
							Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5])));
				}
				else {
					rideList.add(new Ride(Integer.parseInt(tokens[0]), tokens[1], tokens[2], 
							Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4])));
				}
			}
		} catch (IOException e) {
			System.out.println("A problem occured reading in the songs.");
			e.printStackTrace();
		}
		return rideList.toArray(new Ride[rideList.size()]);
	}
	
	
	/**
	 * Fills the symbol table with Rides. Each ride gets a unique id from the range that
	 * is chronologically chosen. Each rides ID on the symbol table should match its ID
	 * as a ride at Disneyland.
	 * 
	 * @param st 		symbol table
	 * @param rides 	an array of rides
	 */
	private static void fillSymbolTable(RedBlackBST<Integer, Ride> st, Ride[] rides) {
		int id = 0;
		for (Ride r : rides) {
			id++;
			st.put(id, r);
		}
	}
	
	/**
	 * Returns a string with the contents of the readMe broken up by sentences.
	 */
	private static String[] getReadMe() {
		List<String> list = new ArrayList<>();
		String fileName = "README.md";
		int count = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			reader.readLine();
			String line;
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split("\n");
				for (String s : tokens) {
					list.add(s);
					count++;
				}
			}
		} catch (IOException e) {
			System.err.println("Cannot read " + fileName);
			e.printStackTrace();
		}
		return list.toArray(new String[count]);
	}
	
	/**
	 * Clears paint and repeats instructions.
	 */
	private void clearContents() {
		mainContent.repaint();
		txtInstructions.setText("Instructions");
		directionsTextArea.setText("Please select the ride you're currently closest to and the ride "
				+ "you would like to go to and click submit.");
	}
	
	/**
	 * Adds set amount of delay to the program.
	 * 
	 * @param duration time in milliseconds
	 */
	private void sleep(int duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Adds action listener to the submit button.
	 * 
	 * @param st symbol table of rides
	 */
	private void addSubmitActionListener(RedBlackBST<Integer, Ride> st) {
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Creating graphics variables
				Graphics g = mainContent.getGraphics();
				Graphics2D g2d = (Graphics2D) g;
				g2d.setStroke(new BasicStroke(3));
				g2d.setColor(currentColor);
				
				// Getting the shortest path from the user's selections
				int source = startComboBox.getSelectedIndex() + 1;
				int destination = destinationComboBox.getSelectedIndex() + 1;
				
				// Creates shortest-paths graph and an iterable of paths
				GraphProcessor graphProccessor = new GraphProcessor(source, destination);
				Iterable<Edge> paths = graphProccessor.pathTo();
				
				// Drawing an edge of the shortest path for each iteration
				List<String> pathArr = new ArrayList<>();
				for (Edge p : paths) {
					int v1 = p.either();
					int v2 = p.other(v1);
					String path = (st.get(v1).getRideID() + " - " + st.get(v1).getName() + " ---> " 
							+ st.get(v2).getRideID() + " - " + st.get(v2).getName());
					pathArr.add(path);
					g2d.drawLine(st.get(v1).getX(), st.get(v1).getY(), st.get(v2).getX(), st.get(v2).getY());
					// Sleeps every 500ms to add a delay
					sleep(200);
				}
				
				// Modifies text in txtInstructions and directionsTextArea
				StringBuilder pathInfo = new StringBuilder("Directions: \n");		
				for (String s : pathArr) {
					pathInfo.append(s).append("\n");
				}
				txtInstructions.setText("Directions");
				directionsTextArea.setText(pathInfo.toString());
				
				// Plays audio to let the user know the path has been created.
				StdAudio.play("src/disneyShortcuts/Resources/magicSound.wav");
				
			}

		});
	} 
	
	/**
	 * Get random color when V is pressed.
	 */
	private void randColorKeybind() {
		mainContent.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_V, 0), "changeColors");
		mainContent.getActionMap().put("changeColors", new AbstractAction() {
			private static final long serialVersionUID = -274372000134457652L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Random rand = new Random();
				Color[] colors = {
									Color.BLUE,
									Color.RED,
									Color.YELLOW,
									Color.GREEN,
									Color.ORANGE,
									Color.PINK,
									Color.MAGENTA,
									Color.CYAN,
									Color.BLACK,
									new Color(150, 35, 31), // StdDraw.BOOK_RED
									new Color(9, 90, 166) // StdDraw.BOOK_BLUE
								  };
				Color randomColor = colors[rand.nextInt(0,10)];
				currentColor = randomColor;
				aboutButton.setBackground(currentColor);
			}
		});
	}
	
	/**
	 * Clears all drawings when C is pressed.
	 */
	private void clearKeybind() {
		mainContent.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0), "clearAction");
	    mainContent.getActionMap().put("clearAction", new AbstractAction() {
	        private static final long serialVersionUID = 118746117564857718L;

			@Override
	        public void actionPerformed(ActionEvent e) {
	            clearContents();
	        }
	    });
	}
}

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
import java.awt.Component;

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
	private Ride[] rides;
	private JTextArea directionsTextArea;
	private JLabel disneyBackgroundImage;
	private JTextArea welcomeMessage;
	private JTextField txtInstructions;

	// TEST DRIVER
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiApp frame = new GuiApp();
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
	public GuiApp() {
		// =========== CREATE FRAME & INITIALIZE COMPONENTS ==========
		setUp();

		setContentPane(mainContent);

		setBackgroundImage();

		createDirectionsTextBox();

		createDisclaimerTextField();

		createSubmitButton();

		createStartTextBox();

		createDestinationTextBox();

		createStartComboBox();

		createDestinationComboBox();
		
		createWelcomeTextArea();
		
		createInstructionsTextField();
		
		GroupLayout gl_mainContent = new GroupLayout(mainContent);
		
		configureGroupLayout(gl_mainContent);
	    
	    mainContent.setLayout(gl_mainContent);

		// ============== CREATE EVENTS ================
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO button action
			}
		});
		
		// Listens to mouse clicks
		addMouseListener(new MouseAdapter() {
			int x1, x2, y1, y2;
			
			@Override
			public void mousePressed(MouseEvent e) {
				if (x1 == 0 && y1 == 0) {
					Point p = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), mainContent);
					x1 = p.x;
					y1 = p.y;
					System.out.println("X: " + x1 + " Y: " + y1);
				} else {
					Point p = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), mainContent);
					x2 = p.x;
					y2 = p.y;
					Graphics g = mainContent.getGraphics();
					Graphics2D g2d = (Graphics2D) g;
					g2d.setStroke(new BasicStroke(3));
					g2d.setColor(Color.RED);
					g2d.drawLine(x1, y1, x2, y2);
					x1 = x2 = y1 = y2 = 0;
				}
			}
		});
	}

	/**
	 * Creates the instructions banner.
	 */
	private void createInstructionsTextField() {
		txtInstructions = new JTextField();
		txtInstructions.setEditable(false);
		txtInstructions.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 14));
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
	 */
	private void createDestinationComboBox() {
		destinationComboBox = new JComboBox<String>();
		destinationComboBox.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 11));

		rides = getRides("src/disneyShortcuts/Resources/disneyRides.csv");

		for (Ride r : rides) {
			String ride = r.getRideID() + " - " + r.getName();
			destinationComboBox.addItem(ride);
		}
	}

	/**
	 * Creates the start drop down menu.
	 */
	private void createStartComboBox() {
		startComboBox = new JComboBox<String>();
		startComboBox.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 11));

		rides = getRides("src/disneyShortcuts/Resources/disneyRides.csv");

		for (Ride r : rides) {
			String ride = r.getRideID() + " - " + r.getName();
			startComboBox.addItem(ride);
		}
	}

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
				rideList.add(new Ride(tokens[1], 0, 0, tokens[2], Integer.parseInt(tokens[0])));
			}
		} catch (IOException e) {
			System.out.println("A problem occured reading in the songs.");
			e.printStackTrace();
		}

		return rideList.toArray(new Ride[rideList.size()]);
	}

	/**
	 * Creates the destination text box.
	 */
	private void createDestinationTextBox() {
		txtDestination = new JTextField();
		txtDestination.setEditable(false);
		txtDestination.setText("Destination");
		txtDestination.setHorizontalAlignment(SwingConstants.CENTER);
		txtDestination.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
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
	private JTextArea createDirectionsTextBox() {
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
}

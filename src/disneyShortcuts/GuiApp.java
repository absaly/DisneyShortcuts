package disneyShortcuts;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import javax.swing.border.EmptyBorder;

/**
 * Contains GUI Logic. Reads in ride information from a CSV file.
 * 
 * @author Noah Ewell + Abbas Al-Younis
 */
public class GuiApp extends JFrame {

	private JPanel mainContent;
	private JTextField disclaimerTextField;
	private JTextField txtStart;
	private JTextField txtDestination;
	private JComboBox destinationComboBox;
	private JComboBox startComboBox;
	private JButton submitButton;
	private JPanel panel;
	private Ride[] rides;

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

		JLabel disneyBackgroundImage = setBackgroundImage();

		JTextArea directionsTextArea = createDirectionsTextBox();

		createDisclaimerTextField();

		createSubmitButton();

		createStartTextBox();

		createDestinationTextBox();

		createStartComboBox();

		createDestinationComboBox();
		
		

		GroupLayout gl_mainContent = new GroupLayout(mainContent);
		formatContent(disneyBackgroundImage, directionsTextArea, gl_mainContent);

		// ============== CREATE EVENTS ================
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO button action
			}
		});
		
		// Listens to mouse clicks
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int x1 = e.getX();
				int y1 = e.getY();
				System.out.println("X: " + x1 + " Y: " + y1);
				
				// testing out drawing but not working
//				Graphics g = mainContent.getGraphics();
//				g.drawLine(x1, y1, x2, y2);
				
			}
		});
	}

	/**
	 * Adds and formats the background images, text area, and other content to a
	 * group layout style.
	 * 
	 * @param disneyBackgroundImage the background image for the park.
	 * @param directionsTextArea    the text where directions will populate once
	 *                              submit is clicked.
	 * @param gl_mainContent        the other content for the page.
	 */
	private void formatContent(JLabel disneyBackgroundImage, JTextArea directionsTextArea, GroupLayout gl_mainContent) {
		gl_mainContent.setHorizontalGroup(gl_mainContent.createParallelGroup(Alignment.TRAILING).addGroup(gl_mainContent
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_mainContent.createParallelGroup(Alignment.LEADING).addGroup(gl_mainContent
						.createSequentialGroup()
						.addComponent(directionsTextArea, GroupLayout.PREFERRED_SIZE, 431, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addGroup(gl_mainContent.createParallelGroup(Alignment.LEADING, false)
								.addComponent(startComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(txtStart, GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
						.addGroup(gl_mainContent.createParallelGroup(Alignment.TRAILING)
								.addComponent(txtDestination, GroupLayout.PREFERRED_SIZE, 273,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(destinationComboBox, GroupLayout.PREFERRED_SIZE, 273,
										GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addComponent(submitButton, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE))
						.addComponent(disneyBackgroundImage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addComponent(disclaimerTextField, 1200, 1200, 1200))
				.addContainerGap(12, Short.MAX_VALUE)));
		gl_mainContent.setVerticalGroup(gl_mainContent.createParallelGroup(Alignment.LEADING).addGroup(gl_mainContent
				.createSequentialGroup().addComponent(disneyBackgroundImage).addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_mainContent.createParallelGroup(Alignment.LEADING)
						.addComponent(directionsTextArea, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 119,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(submitButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
						.addGroup(gl_mainContent.createSequentialGroup()
								.addGroup(gl_mainContent.createParallelGroup(Alignment.BASELINE)
										.addComponent(txtStart, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(txtDestination, GroupLayout.PREFERRED_SIZE, 21,
												GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_mainContent.createParallelGroup(Alignment.BASELINE)
										.addComponent(startComboBox, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(destinationComboBox, GroupLayout.PREFERRED_SIZE, 21,
												GroupLayout.PREFERRED_SIZE))))
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(disclaimerTextField,
						GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)));
		mainContent.setLayout(gl_mainContent);
	}

	/**
	 * Creates the destination drop down menu.
	 */
	private void createDestinationComboBox() {
		destinationComboBox = new JComboBox();
		destinationComboBox.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 11));

		rides = getRides("src/disneyShortcuts/Resources/disneyRides.csv");

		for (Ride r : rides) {
			String ride = "Ride: " + r.getName() + " ID: " + r.getRideID();
			destinationComboBox.addItem(ride);
		}
	}

	/**
	 * Creates the start drop down menu.
	 */
	private void createStartComboBox() {
		startComboBox = new JComboBox();
		startComboBox.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 11));

		rides = getRides("src/disneyShortcuts/Resources/disneyRides.csv");

		for (Ride r : rides) {
			String ride = "Ride: " + r.getName() + " ID: " + r.getRideID();
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
		submitButton.setBorder(null);
		submitButton.setBackground(new Color(0, 255, 255));
		submitButton.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 13));
	}

	/**
	 * Creates the disclaimer text field at the bottom.
	 */
	private void createDisclaimerTextField() {
		disclaimerTextField = new JTextField();
		disclaimerTextField.setBorder(null);
		disclaimerTextField.setHorizontalAlignment(SwingConstants.CENTER);
		disclaimerTextField.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 12));
		disclaimerTextField.setText(
				"Disclaimer: We are in no way affiliated with Disney. The above image is not owned by us. This was created for fun and not for distribution.");
		disclaimerTextField.setColumns(10);
	}

	/**
	 * Creates the directions text box.
	 * 
	 * @return the text box for directions to go in.
	 */
	private JTextArea createDirectionsTextBox() {
		JTextArea directionsTextArea = new JTextArea();
		directionsTextArea.setLineWrap(true);
		directionsTextArea.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 14));
		directionsTextArea.setText(
				"Please select the ride you're currently closest to and the ride you would like to go to and click submit.");
		return directionsTextArea;
	}

	/**
	 * Creates a new JLabel and sets the disneyBackgroundImage
	 * 
	 * @return the background image
	 */
	private JLabel setBackgroundImage() {
		JLabel disneyBackgroundImage = new JLabel("");
		disneyBackgroundImage.setBorder(null);
		disneyBackgroundImage.setIcon(
				new ImageIcon(GuiApp.class.getResource("/disneyShortcuts/Resources/disneyland-park-map-01.jpg")));
		return disneyBackgroundImage;
	}

	/**
	 * Sets the close operation, boundaries, title, icon, and creates a new JPanel.
	 */
	private void setUp() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1248, 900);
		setTitle("Disney Shortcuts (Not-Affiliated)");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(GuiApp.class.getResource("/disneyShortcuts/Resources/disneyCastle.png")));
		mainContent = new JPanel();
		mainContent.setBackground(Color.WHITE);
		mainContent.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainContent.add(new DrawPanel());
	}
}

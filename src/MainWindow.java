import java.awt.EventQueue;

import javax.swing.JFrame;

import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JRadioButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JToggleButton;
import javax.swing.JLabel;
import java.awt.Component;

public class MainWindow {

	private JFrame frame;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private long startTime;	
	private int clickCounter;
	private double max;
	private Timer timer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FlatDarkLaf.setup();
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		String reader = "0";
		try {
			reader = new String(Files.readAllBytes(Paths.get("config/max.txt")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(reader);
		this.max = reader.isEmpty() ? 0 : Double.valueOf(reader);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 650, 440);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setLocationRelativeTo(null);
		
		JButton btnMain = new JButton("CLIQUEZ-MOI");
		btnMain.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnMain.setBackground(new Color(217, 87, 0));
		frame.getContentPane().add(btnMain, BorderLayout.CENTER);
		
		JPanel panelEast = new JPanel();
		frame.getContentPane().add(panelEast, BorderLayout.EAST);
		GridBagLayout gbl_panelEast = new GridBagLayout();
		gbl_panelEast.columnWidths = new int[]{0, 0};
		gbl_panelEast.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panelEast.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panelEast.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelEast.setLayout(gbl_panelEast);
		
		JRadioButton rdbtnDebutant = new JRadioButton("Débutant");
		rdbtnDebutant.setActionCommand("1");
		rdbtnDebutant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnMain.setBackground(new Color(0, 153, 51));
			}
		});
		buttonGroup.add(rdbtnDebutant);
		GridBagConstraints gbc_rdbtnDebutant = new GridBagConstraints();
		gbc_rdbtnDebutant.insets = new Insets(20, 20, 10, 20);
		gbc_rdbtnDebutant.gridx = 0;
		gbc_rdbtnDebutant.gridy = 0;
		panelEast.add(rdbtnDebutant, gbc_rdbtnDebutant);
		
		JRadioButton rdbtnNormal = new JRadioButton("Normal");
		rdbtnNormal.setSelected(true);
		rdbtnNormal.setActionCommand("3");
		rdbtnNormal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnMain.setBackground(new Color(217, 87, 0));
			}
		});
		buttonGroup.add(rdbtnNormal);
		GridBagConstraints gbc_rdbtnNormal = new GridBagConstraints();
		gbc_rdbtnNormal.insets = new Insets(0, 0, 10, 0);
		gbc_rdbtnNormal.gridx = 0;
		gbc_rdbtnNormal.gridy = 1;
		panelEast.add(rdbtnNormal, gbc_rdbtnNormal);
		
		JRadioButton rdbtnExpert = new JRadioButton("Expert");
		rdbtnExpert.setActionCommand("5");
		rdbtnExpert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnMain.setBackground(new Color(204, 0, 0));
				
			}
		});
		buttonGroup.add(rdbtnExpert);
		GridBagConstraints gbc_rdbtnExpert = new GridBagConstraints();
		gbc_rdbtnExpert.insets = new Insets(0, 0, 10, 0);
		gbc_rdbtnExpert.gridx = 0;
		gbc_rdbtnExpert.gridy = 2;
		panelEast.add(rdbtnExpert, gbc_rdbtnExpert);
		
		JPanel panelSouth = new JPanel();
		frame.getContentPane().add(panelSouth, BorderLayout.SOUTH);
		
		
		JButton btnReinitialiser = new JButton("Réinitialiser");
		btnReinitialiser.setPreferredSize(new Dimension(120, 35));
		btnReinitialiser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnMain.setText("0");
				startTime = 0;
				clickCounter = 0;
			}
		});
		panelSouth.add(btnReinitialiser);
		panelSouth.add(Box.createHorizontalStrut(30));
		
		JToggleButton tglbtnBloquer = new JToggleButton("Bloquer");
		tglbtnBloquer.setPreferredSize(new Dimension(90, 35));
		panelSouth.add(tglbtnBloquer);
		panelSouth.add(Box.createHorizontalStrut(70));
		
		JLabel lblCPS = new JLabel("CPS : 0,00");
		lblCPS.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelSouth.add(lblCPS);
		
		Component horizontalStrut = Box.createHorizontalStrut(30);
		panelSouth.add(horizontalStrut);
		
		JLabel lblCPSMax = new JLabel("Max : " + String.format("%.2f", this.max));
		lblCPSMax.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelSouth.add(lblCPSMax);
		
		btnMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!(tglbtnBloquer.isSelected())) {
					clickCounter++;
					
					int increment = Integer.valueOf(buttonGroup.getSelection().getActionCommand());
					int clicks = btnMain.getText() == "CLIQUEZ-MOI" ? 0 : Integer.valueOf(btnMain.getText());
					int value = clicks + increment;
					btnMain.setText(String.valueOf(value));
					
		            if (startTime == 0) {
		               startTime = System.currentTimeMillis();
		            }
		            
		            timer = new Timer(50, new ActionListener() {
		                public void actionPerformed(ActionEvent e) {
		                    long elapsedTime = System.currentTimeMillis() - startTime;
		                    double elapsedSeconds = elapsedTime / 1000.0;

		                    double clicksPerSecond = 0;
		                    if (elapsedSeconds != 0) {
		                        clicksPerSecond = clickCounter / elapsedSeconds;
		                    }

		                    lblCPS.setText("CPS : " + String.format("%.2f", clicksPerSecond));
		                    
		                    if (clicksPerSecond > max && elapsedSeconds > 1) {
		                    	max = clicksPerSecond;
		                    	lblCPSMax.setText("Max : " + String.format("%.2f", max));
		                    	try (BufferedWriter bw = new BufferedWriter(new FileWriter("config/max.txt"))) {

		                            bw.write(String.valueOf(max));

		                        } catch (IOException e1) {
		                        	e1.printStackTrace();
		                        }
		                    }
		                }
		            });
		            timer.start();
				}
			}
		});
	}

}

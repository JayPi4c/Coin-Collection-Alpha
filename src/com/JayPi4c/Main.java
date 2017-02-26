package com.JayPi4c;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

public class Main extends JFrame implements ActionListener {

	private static final long serialVersionUID = -711977444860223056L;

	public static JButton Exit;
	public static JProgressBar initProgressBar;
	public static JRadioButton AddStatusRB;
	public static JRadioButton RemoveStatusRB;
	public static JRadioButton CheckStatusRB;
	public static JButton confirm;
	@SuppressWarnings("rawtypes")
	public static JComboBox CountryBox;
	@SuppressWarnings("rawtypes")
	public static JComboBox YearBox;
	@SuppressWarnings("rawtypes")
	public static JComboBox ValueBox;

	public static String n = System.getProperty("line.separator");

	public static String[] content;

	/* global variables */
	public static boolean firstStart;
	public static int countries;
	public static int specialCoinages;
	public static String[] countryNames;
	public static String[] Years = { "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008",
			"2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017" };
	public static String[] Values = { "0.01", "0.02", "0.05", "0.1", "0.2", "0.5", "1", "2" };
	public static int countries1999;
	public static int countries2001;
	public static int countries2007;
	public static int countries2008;
	public static int countries2009;
	public static int countries2011;
	public static int countries2014;
	public static int countries2015;

	public static void main(String[] args) throws IOException {
		preInit();

		init();

		Main Frame = new Main("CoinRegistry");
		Frame.setSize(400, 400);
		Frame.setLocationRelativeTo(null);
		Frame.setLayout(null);
		Frame.setVisible(true);
		groupButton();

	}

	public static boolean Visibility = true;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Main(String title) {
		super(title);

		CountryBox = new JComboBox(countryNames);
		CountryBox.setBounds(50, 50, 100, 20);
		CountryBox.setSelectedIndex(1);
		CountryBox.setVisible(Visibility);
		CountryBox.addActionListener(this);
		add(CountryBox);

		YearBox = new JComboBox(Years);
		YearBox.setBounds(50, 80, 100, 20);
		YearBox.setSelectedIndex(0);
		YearBox.setVisible(Visibility);
		YearBox.addActionListener(this);
		add(YearBox);

		ValueBox = new JComboBox(Values);
		ValueBox.setBounds(50, 110, 100, 20);
		ValueBox.setSelectedIndex(0);
		ValueBox.setVisible(Visibility);
		ValueBox.addActionListener(this);
		add(ValueBox);

		CheckStatusRB = new JRadioButton("Check Status", true);
		CheckStatusRB.setBounds(180, 50, 150, 20);
		CheckStatusRB.setVisible(Visibility);
		add(CheckStatusRB);

		AddStatusRB = new JRadioButton("Add Status", false);
		AddStatusRB.setBounds(180, 80, 150, 20);
		AddStatusRB.setVisible(Visibility);
		add(AddStatusRB);

		RemoveStatusRB = new JRadioButton("Remove Status", false);
		RemoveStatusRB.setBounds(180, 110, 150, 20);
		RemoveStatusRB.setVisible(Visibility);
		add(RemoveStatusRB);

		confirm = new JButton("Confirm");
		confirm.addActionListener(this);
		confirm.setBounds(180, 180, 100, 20);
		confirm.setVisible(Visibility);
		add(confirm);

		Exit = new JButton("Exit");
		Exit.addActionListener(this);
		Exit.setBounds(180, 220, 90, 20);
		Exit.setVisible(Visibility);
		add(Exit);

		initProgressBar = new JProgressBar(SwingConstants.VERTICAL);
		initProgressBar.setBounds(50, 300, 300, 15);
		initProgressBar.setVisible(!Visibility);
		add(initProgressBar);

	}

	private static void groupButton() {
		ButtonGroup bgrb = new ButtonGroup();
		bgrb.add(AddStatusRB);
		bgrb.add(CheckStatusRB);
		bgrb.add(RemoveStatusRB);
	}

	public static void preInit() throws IOException {
		try {
			FileReader PropFR = new FileReader("./src/com/JayPi4c/resource/properties.prop");
			BufferedReader PropBR = new BufferedReader(PropFR);

			int linesOfProperties = Integer.parseInt(PropBR.readLine());
			String[] properties = new String[linesOfProperties];
			for (int i = 0; i < linesOfProperties - 1; i++) {
				properties[i] = PropBR.readLine();
				// System.out.println(properties[i] + ", i: "+ i);
			}

			content = properties[0].split("=");
			if (content[1].contains("true")) { // if true: Pop dialog for Information
				firstStart = true;
			} else {
				firstStart = false;
			}

			content = properties[1].split("=");
			specialCoinages = Integer.parseInt(content[1]);

			content = properties[2].split("=");
			countries = Integer.parseInt(content[1]);

			content = properties[3].split("=");
			countries1999 = Integer.parseInt(content[1]);

			content = properties[4].split("=");
			countries2001 = Integer.parseInt(content[1]);

			content = properties[5].split("=");
			countries2007 = Integer.parseInt(content[1]);

			content = properties[6].split("=");
			countries2008 = Integer.parseInt(content[1]);

			content = properties[7].split("=");
			countries2009 = Integer.parseInt(content[1]);

			content = properties[8].split("=");
			countries2011 = Integer.parseInt(content[1]);

			content = properties[9].split("=");
			countries2014 = Integer.parseInt(content[1]);

			content = properties[10].split("=");
			countries2015 = Integer.parseInt(content[1]);

			countryNames = new String[countries];
			for (int i = 0; i < countries; i++) {
				content = properties[i + (linesOfProperties - countries) - 1].split("=");
				countryNames[i] = content[0];
			}

			PropBR.close();
			System.out.println("load 'properties.prop': Succesfully ");

		} catch (Exception ex) {
			System.out.println("load 'properties.prop': failed ");
			System.out.println("Fehlermeldung: " + ex);
		}
	}

	/**
	 * 
	 * @return
	 * 
	 * @author http://stackoverflow.com/questions/2837263/how-do-i-get-the-directory-that-the-currently-executing-jar-file-is-in
	 */
	private static String GetExecutionPath() {
		String absolutePath = new File(".").getAbsolutePath();
		// System.out.println("absolute path: "+ absolutePath);
		File file = new File(absolutePath);
		absolutePath = file.getParentFile().toString();
		return absolutePath;
	}

	/**
	 * 
	 * @throws IOException
	 */
	public static void init() throws IOException {
		/*
		 * File dataDir = new File(GetExecutionPath()+ "/data");
		 * System.out.println(dataDir.getAbsolutePath()); if(!dataDir.exists()){
		 * System.out.println(dataDir.getName() +" wird generiert!"); dataDir.mkdir();
		 * }else { System.out.println(dataDir.getName() + " existiert"); }
		 * 
		 * File f = new File(GetExecutionPath()+ "/data/Example.txt");
		 * System.out.println(f.getAbsolutePath()); if(!f.exists()){ f.createNewFile();
		 * }else{ System.out.println(f.getName() + " existiert"); }
		 */

		File dataDir = new File(GetExecutionPath() + "/data");
		System.out.println(dataDir.getAbsolutePath());
		if (!dataDir.exists()) {
			System.out.println(dataDir.getName() + " wird generiert!");
			dataDir.mkdir();
		} else {
			System.out.println(dataDir.getAbsolutePath() + " existiert bereits");
		}

		// initProgressBar.setMinimum(0);
		// initProgressBar.setMaximum(18);

		for (int i = 1999; i <= 2017; i++) {
			File CoinRegistry = new File(getPath(i));
			if (!CoinRegistry.exists()) {
				// CoinRegistry.createNewFile();
				genRegistry(CoinRegistry, i, getMembersFromYear(i));
				System.out.println(CoinRegistry.getAbsolutePath() + ": generated");

			} else {
				System.out.println("load: " + CoinRegistry.getAbsolutePath());
			}
		}

		/*
		 * else{ BufferedReader CR = new BufferedReader(new FileReader(CoinRegistry));
		 * String[] lines = new String [countries]; for(int j = 0; j <
		 * getMembersFromYear(i); j++){ lines[j] = CR.readLine(); String[] str =
		 * lines[j].split(";"); if(str.length != 8){
		 * System.out.println("'Coinage"+i+".co' is damaged and needs to reload ");
		 * System.out.println("Please don't play with the systemfiles"); genRegistry(i,
		 * getMembersFromYear(i)); } } CR.close();
		 * System.out.println("'coinages"+i+".co' is accessible"); }
		 */

		// initProgressBar.setValue(i);
		// initProgressBar.setString(initProgressBar.getString());
	}

	/*
	 * if(initProgressBar.getString().equals("100%")){
	 * initProgressBar.setVisible(false);
	 * 
	 * CountryBox.setVisible(true); YearBox.setVisible(true);
	 * ValueBox.setVisible(true); CheckStatusRB.setVisible(true);
	 * AddStatusRB.setVisible(true); RemoveStatusRB.setVisible(true);
	 * confirm.setVisible(true); Exit.setVisible(true); } else{
	 * System.out.println("progressBar: "+initProgressBar.getString());
	 * System.out.println("Oups, something went wrong; Pleaser contact developer!");
	 * }
	 */

	public static void genRegistry(File f, int year, int membersInYear) throws IOException {
		// File f = new File(getPath(year));
		System.out.println("Path: " + f.getAbsolutePath());
		f.createNewFile();

		FileWriter CoinFW = new FileWriter(f);
		BufferedWriter CoinBFW = new BufferedWriter(CoinFW);
		System.out.println("membersInYear: " + membersInYear);
		System.out.println(membersInYear);

		for (int i = 0; i < membersInYear; i++) { // loops through every country of the properties file
			for (int j = 0; j < 2; j++) {
				int k = (int) Math.pow(10, j);
				System.out.println("k: " + k);
				CoinBFW.write(i + "," + 0.01 * k + ",0;");
				CoinBFW.write(i + "," + 0.02 * k + ",0;");
				CoinBFW.write(i + "," + 0.05 * k + ",0;");
			}
			CoinBFW.write(i + "," + 1 + ",0;");
			CoinBFW.write(i + "," + 2 + ",0;");
			CoinBFW.write(n);

		}
		CoinBFW.close();
		CoinFW.close();
		JOptionPane.showOptionDialog(null, "creating 'coinages" + year + ".co': successfully", "Info",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, content[0]);
		System.out.println("creating 'coinages" + year + ".co': successfully");
	}

	/**
	 * 
	 * @param land    gives the function the information about the land of the coin
	 * @param value   gives the function the information about the value of the coin
	 * @param holding gives the function the information if the Owner has the coin
	 *                or remove the property of having it
	 * 
	 *                this Function will update the CoinRegistry in the correct line
	 *                to true
	 * 
	 * @author JayPi4c
	 */
	public static void updateCoinRegistry(String land, double value, boolean holding, int year) throws IOException {
		int multiplicator = lineMultiplicator(value);

		int countryKey;
		countryKey = countries + 1;
		for (int i = 0; i < getMembersFromYear(year); i++) {
			if (countryNames[i].equals(land)) {
				countryKey = i;
				break;
			}
		}

		File CoinRegistry = new File(getPath(year));
		FileReader CoinFR = new FileReader(CoinRegistry);
		BufferedReader CoinBR = new BufferedReader(CoinFR);

		String[] line = new String[getMembersFromYear(year)];
		for (int i = 0; i < getMembersFromYear(year); i++) {
			line[i] = CoinBR.readLine();
			// System.out.println(line[i]);
		}

		content = line[countryKey].split(";");

		// System.out.println(content[multiplicator]);
		String[] content_ = content[multiplicator].split(",");

		// System.out.println(content_[2]);
		if (holding) {
			content_[2] = "1";
		} else {
			content_[2] = "0";
		}

		String str = countryKey + "," + value + "," + content_[2] + ";";
		String out = "";
		for (int i = 0; i < 8; i++) {
			if (i == multiplicator) {
				out = out + str;
			} else
				out = out + content[i] + ";";
			// System.out.println(out);
		}

		line[countryKey] = out;
		for (int i = 0; i <= countryKey; i++) {
			// System.out.println(line[i]);
		}

		FileWriter CoinFW = new FileWriter(CoinRegistry);
		BufferedWriter CoinBFW = new BufferedWriter(CoinFW);

		for (int i = 0; i < line.length; i++) {
			CoinBFW.write(line[i]);
			CoinBFW.write(n);
		}

		CoinBFW.close();
		CoinBR.close();

	}

	public static int lineMultiplicator(double value) {
		int lineMultiplicator;
		int switchValue = (int) (value * 100);
		switch (switchValue) {
		case 1:
			lineMultiplicator = 0;
			break;
		case 2:
			lineMultiplicator = 1;
			break;
		case 5:
			lineMultiplicator = 2;
			break;
		case 10:
			lineMultiplicator = 3;
			break;
		case 20:
			lineMultiplicator = 4;
			break;
		case 50:
			lineMultiplicator = 5;
			break;
		case 100:
			lineMultiplicator = 6;
			break;
		case 200:
			lineMultiplicator = 7;
			break;
		default:
			System.out.println("OUPS, something went wrong!");
			lineMultiplicator = 42;
			break;
		}
		return lineMultiplicator;
	}

	/**
	 * 
	 * @param land  the country where the coin is from
	 * @param value the value of the coin
	 * @param year  the year of coining
	 * @return the status of holdung; if you own the coin it returns true else false
	 * @throws IOException
	 * 
	 * @version 1.0
	 * @author JayPi4c
	 */
	public static boolean getStatus(String land, double value, int year) throws IOException {
		int multiplicator = lineMultiplicator(value);

		int countryKey = countryKey(year, land);

		File CoinRegistry = new File(getPath(year));
		FileReader CoinFR = new FileReader(CoinRegistry);
		BufferedReader CoinBR = new BufferedReader(CoinFR);

		String[] line = new String[countryKey + 1];
		for (int i = 0; i <= countryKey; i++) {
			line[i] = CoinBR.readLine();
		}
		content = line[countryKey].split(";");

		content = content[multiplicator].split(",");

		CoinBR.close();
		if (content[2].equals("1")) {
			return true;
		} else
			return false;
	}

	public static int getMembersFromYear(int year) {
		int members;
		if (year < 2001)
			members = countries1999;
		else if (year < 2007)
			members = countries2001;
		else if (year < 2008)
			members = countries2007;
		else if (year < 2009)
			members = countries2008;
		else if (year < 2011)
			members = countries2009;
		else if (year < 2014)
			members = countries2011;
		else if (year < 2015)
			members = countries2014;
		else
			members = countries2015;

		return members;
	}

	public static String getPath(int year) {
		String path = GetExecutionPath() + "/data/coinages" + year + ".co";
		return path;
	}

	public static int countryKey(int year, String land) {
		int Key = 42;
		for (int i = 0; i < getMembersFromYear(year); i++) {
			if (countryNames[i].equals(land)) {
				Key = i;
				break;
			}
		}
		return Key;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == confirm) {
			if (CheckStatusRB.isSelected()) {
				if (getMembersFromYear(Integer.parseInt((String) YearBox.getSelectedItem())) > CountryBox
						.getSelectedIndex()) {
					try {
						if (getStatus((String) CountryBox.getSelectedItem(),
								Double.parseDouble((String) ValueBox.getSelectedItem()),
								Integer.parseInt((String) YearBox.getSelectedItem()))) {
							System.out.println("Die Münze befindet sich in deinem Besitz");
						} else
							System.out.println("Die Münze befindet sich nicht in deinem Besitz");
					} catch (NumberFormatException e1) {
						System.out.println("Fehlermeldung: " + e1);
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
						System.out.println("Fehlermeldung: " + e1);
					}
				} else {
					System.out.println("Das geht leider nicht: " + (String) CountryBox.getSelectedItem() + " war "
							+ (String) YearBox.getSelectedItem() + " noch nicht im Euro");
				}
			} else if (AddStatusRB.isSelected()) {
				if (getMembersFromYear(Integer.parseInt((String) YearBox.getSelectedItem())) > CountryBox
						.getSelectedIndex()) {
					try {
						updateCoinRegistry((String) CountryBox.getSelectedItem(),
								Double.parseDouble((String) ValueBox.getSelectedItem()), true,
								Integer.parseInt((String) YearBox.getSelectedItem()));
					} catch (NumberFormatException e1) {
						System.out.println("Fehlermeldung: " + e1);
						e1.printStackTrace();
					} catch (IOException e1) {
						System.out.println("Fehlermeldung: " + e1);
						e1.printStackTrace();
					}
				} else {
					System.out.println("Das geht leider nicht: " + (String) CountryBox.getSelectedItem() + " war "
							+ (String) YearBox.getSelectedItem() + " noch nicht im Euro");
				}
			} else if (RemoveStatusRB.isSelected()) {
				if (getMembersFromYear(Integer.parseInt((String) YearBox.getSelectedItem())) > CountryBox
						.getSelectedIndex()) {
					try {
						updateCoinRegistry((String) CountryBox.getSelectedItem(),
								Double.parseDouble((String) ValueBox.getSelectedItem()), false,
								Integer.parseInt((String) YearBox.getSelectedItem()));
					} catch (NumberFormatException e1) {
						System.out.println("Fehlermeldung: " + e1);
						e1.printStackTrace();
					} catch (IOException e1) {
						System.out.println("Fehlermeldung: " + e1);
						e1.printStackTrace();
					}
				} else {
					System.out.println("Das geht leider nicht: " + (String) CountryBox.getSelectedItem() + " war "
							+ (String) YearBox.getSelectedItem() + " noch nicht im Euro");
				}
			}
		}

		if (e.getSource() == Exit) {
			System.out.println("Exiting: succesfully!");
			System.exit(0);
		}
	}

}
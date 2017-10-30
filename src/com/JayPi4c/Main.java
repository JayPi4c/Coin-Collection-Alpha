package com.JayPi4c;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

public class Main extends JFrame implements ActionListener {

	private static final long serialVersionUID = -4580078515426732128L;
	public static final String n = System.getProperty("line.separator");

	public static JButton Exit;
	public static JButton reset;
	public static JLabel label;
	public static JLabel worth;
	public static JProgressBar initProgressBar;
	public static JRadioButton AddStatusRB;
	public static JRadioButton RemoveStatusRB;
	public static JRadioButton CheckStatusRB;
	public static JButton confirm;
	@SuppressWarnings("rawtypes")
	public static JComboBox GermanPerfecture;
	@SuppressWarnings("rawtypes")
	public static JComboBox CountryBox;
	@SuppressWarnings("rawtypes")
	public static JComboBox YearBox;
	@SuppressWarnings("rawtypes")
	public static JComboBox ValueBox;

	public static String[] content;
	public static String[] countryNames;
	public static String[] Years = { "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008",
			"2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017" };
	public static String[] Values = { "0.01", "0.02", "0.05", "0.1", "0.2", "0.5", "1", "2" };
	public static String[] Perfectures = { "A; Berlin", "D; München", "F; Stuttgart", "G; Karlsruhe", "J; Hamburg" };
	public static boolean firstStart;
	public static int countries;
	public static int specialCoinages;
	public static int countries1999;
	public static int countries2001;
	public static int countries2007;
	public static int countries2008;
	public static int countries2009;
	public static int countries2011;
	public static int countries2014;
	public static int countries2015;

	public static ResourceBundle texts;

	public static void main(String[] args) throws IOException {

		String language;
		String country;

		if (args.length != 2) {
			language = new String("en");
			country = new String("US");
		} else {
			language = new String(args[0]);
			country = new String(args[1]);
		}

		Locale currentLocale;

		currentLocale = new Locale(language, country);
		texts = ResourceBundle.getBundle("com.JayPi4c.MessagesBundle", currentLocale);

		preInit();
		init();

		Main frame = new Main(texts.getString("title"));
		frame.setSize(400, 500);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		groupButton();
	}

	public static void preInit() throws IOException {
		try {
			FileReader PropFR = new FileReader("./src/com/JayPi4c/resource/properties.prop");
			// FileReader PropFR = new FileReader("./properties.prop"); //Pfad zur prop
			// Datei im selben Ordner wie die .jar Datei
			BufferedReader PropBR = new BufferedReader(PropFR);
			String[] lines = PropBR.readLine().split("//");
			int linesOfProperties = Integer.parseInt(lines[0]);
			String[] properties = new String[linesOfProperties];
			for (int i = 0; i < linesOfProperties - 1; i++) {
				properties[i] = PropBR.readLine();
			}

			content = properties[0].split("=");
			if (content[1].contains("true")) {
				firstStart = true;
			} else
				firstStart = false;

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
			PropFR.close();
			System.out.println("load 'properties.prop': Successfully");

		} catch (Exception ex) {
			System.out.println("load 'properties.prop': failed");
			System.out.println("Error log: " + ex);
			System.out.println("Exiting ...");
			System.exit(0);
		}
	}

	public static void init() throws IOException {
		File dataDir = new File(getExecutionPath() + "/data");
		if (!dataDir.exists()) {
			dataDir.mkdir();
		}

		for (int i = 1999; i <= 2017; i++) {
			File CoinRegistry = new File(getPath(i));
			// System.out.println("filelenght: " + CoinRegistry.length());
			System.out.println("Coinregistry" + i + " exists: " + CoinRegistry.exists());
			if (!CoinRegistry.exists()) {
				System.out.println("generate: " + CoinRegistry.getAbsolutePath());
				genRegistry(i);
			} else {
				boolean damaged = false;

				if (CoinRegistry.length() == 0) {
					System.out.println("file is damaged!");
					System.out.println("Errorcode: 0; File is empty");
					damaged = true;
					genRegistry(i);
				}

				if (!damaged) {
					BufferedReader CR = new BufferedReader(new FileReader(CoinRegistry));
					String[] lines = new String[getMembersFromYear(i)];
					for (int j = 0; j < getMembersFromYear(i); j++) {
						lines[j] = CR.readLine();

						String[] str = lines[j].split(";");
						if (str.length != 8) {
							System.out.println("file is damaged!");
							System.out.println("Errorcode: 1; Line: " + j);
							damaged = true;
							break;
						}

						if (!damaged) {
							for (int k = 0; k < str.length; k++) {
								String[] part = str[k].split(",");
								if (part.length != 3) {
									System.out.println("file is damaged!");
									System.out.println("Errorcode: 2; Line" + (j + 1));
									damaged = true;
									break;
								}
								if (!part[0].equals("" + j)) {
									System.out.println("file is damaged!");
									System.out.println("Errorcode: 3; Line: " + (j + 1));
									damaged = true;
									break;
								}
							}
							if (damaged)
								break;

						}

					}
					CR.close();
					if (damaged) {
						rescueData(CoinRegistry, i);
					}
				}

				System.out.println("'coinages" + i + ".co' is accessible");

			}
			// System.out.println(CoinRegistry.getAbsolutePath() + ": accessible");
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == CountryBox) {
			if (CountryBox.getSelectedItem().equals("Deutschland"))
				GermanPerfecture.setVisible(true);
			else
				GermanPerfecture.setVisible(false);
		}

		if (e.getSource() == confirm) {
			String Country = (String) CountryBox.getSelectedItem();
			String Year = (String) YearBox.getSelectedItem();
			String Value = (String) ValueBox.getSelectedItem();

			if (CheckStatusRB.isSelected()) {
				if (getMembersFromYear(Integer.parseInt(Year)) > CountryBox.getSelectedIndex()) {
					try {
						if (getStatus(Country, Double.parseDouble(Value), Integer.parseInt(Year))) {
							System.out.println("The coin is in your possession");
							label.setText("<html>The coin '" + Country + "; " + Value + "; " + Year
									+ "' <font color='red'>is</font> in your possession</html>");
						} else {
							System.out.println("The coin is not in your possession");
							label.setText("<html>The coin '" + Country + "; " + Value + "; " + Year
									+ "' is <font color='red'>not</font> in your possession</html>");
						}
					} catch (NumberFormatException e1) {
						System.out.println("Error log: " + e1);
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
						System.out.println("Error log: " + e1);
					}
				} else {
					System.out.println(
							"Unfortunately that is not possible: " + Country + " was " + Year + " not yet in the euro");
					label.setText(
							"Unfortunately that is not possible: " + Country + " was " + Year + " not yet in the euro");
				}
			} else if (AddStatusRB.isSelected()) {
				if (getMembersFromYear(Integer.parseInt(Year)) > CountryBox.getSelectedIndex()) {
					try {
						updateRegistry(Country, Double.parseDouble(Value), Integer.parseInt(Year), true);
						System.out.println("added status successfully!");
						label.setText("added status of: " + Country + "; " + Value + "; " + Year + ": successfully!");
					} catch (NumberFormatException e1) {
						System.out.println("Error log: " + e1);
						e1.printStackTrace();
					} catch (IOException e1) {
						System.out.println("Error log: " + e1);
						e1.printStackTrace();
					}
				} else {
					System.out.println(
							"Unfortunately that is not possible: " + Country + " was " + Year + " not yet in the euro");
					label.setText(
							"Unfortunately that is not possible: " + Country + " was " + Year + " not yet in the euro");
				}
			} else if (RemoveStatusRB.isSelected()) {
				if (getMembersFromYear(Integer.parseInt(Year)) > CountryBox.getSelectedIndex()) {
					try {
						updateRegistry(Country, Double.parseDouble(Value), Integer.parseInt(Year), false);
						System.out.println("removed status successfully!");
						label.setText("removed status of: " + Country + "; " + Value + "; " + Year + ": successfully!");
					} catch (NumberFormatException e1) {
						System.out.println("Error log: " + e1);
						e1.printStackTrace();
					} catch (IOException e1) {
						System.out.println("Error log: " + e1);
						e1.printStackTrace();
					}
				} else {
					System.out.println(
							"Unfortunately that is not possible: " + Country + " was " + Year + " not yet in the euro");
					label.setText(
							"Unfortunately that is not possible: " + Country + " was " + Year + " not yet in the euro");
				}
			}
		}

		if (e.getSource() == Exit) {
			System.out.println("Exiting: successfully!");
			System.exit(0);
		}

		if (e.getSource() == reset) {
			int dialogButton = JOptionPane.showConfirmDialog(null, texts.getString("resetDialogQuestion"),
					texts.getString("resetDialogTitle"), JOptionPane.YES_NO_OPTION);

			if (dialogButton == JOptionPane.YES_OPTION) {
				System.out.println("All files are getting deleted!");
				try {
					resetCollection();
					System.out.println("reset is done!");
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			} else
				System.out.println("Nothing will happen");
		}

		try { // egal welcher button gedrückt wird, immer wird der Gesamtwert der Sammlung in
				// dem Featuretab aktualisiert
			worth.setText("The Value is:" + String.valueOf(getWorth()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static void resetCollection() throws IOException {

		File f = new File(getExecutionPath() + "/data");
		System.out.println(f.getAbsolutePath());

		for (String s : f.list())
			new File(f.getPath(), s).delete();

		if (f.exists())
			init();

		Object[] options = { "OK" };
		JOptionPane.showOptionDialog(null, texts.getString("resetDialogOutput"), texts.getString("resetDialogButton"),
				JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	}

	/**
	 * If a datafile is damaged try to rescue the data
	 * 
	 * @param file
	 * @param year
	 * @throws IOException
	 */
	public static void rescueData(File file, int year) throws IOException {

		String[] fullDataInParts = new String[getMembersFromYear(year) * 8]; // erstelle einen String, der alle Daten
																				// speichert, aufgeteilt in die Parts
																				// jeder Zeile
																				// jede Zeile(getMembersFromYear) mal
																				// die Anzahl Parts, die jede Zeile
																				// hat(8)
		// System.out.println("IndexSize: "+ fullDataInParts.length);
		for (int i = 0; i < fullDataInParts.length; i++) {
			fullDataInParts[i] = null;
		}

		BufferedReader BR = new BufferedReader(new FileReader(file));
		String[] textLine = new String[getMembersFromYear(year)];

		for (int i = 0; i < getMembersFromYear(year); i++) {
			textLine[i] = BR.readLine();
		}
		BR.close();

		for (int i = 0; i < textLine.length; i++) {// loop durch jede Zeile
			// System.out.println("textline:" + i + "; content:" + textLine[i]);
			// System.out.println("null: " + textLine[i]);
			if (textLine[i] != null && !textLine[i].equals("")) {
				String[] contentOfLine = textLine[i].split(";");
				for (int j = 0; j < contentOfLine.length; j++) {// loop durch jeden Part
					String[] contentOfPart;
					contentOfPart = contentOfLine[j].split(",");
					if (contentOfPart.length == 3) {
						// System.out.println("contentOfPart[0]: " +
						// (Integer.parseInt(contentOfPart[0])+1));
						// (System.out.println("contentOfPart[1]: " +
						// (getLineMultiplicator(Double.parseDouble(contentOfPart[1]))+1));
						// System.out.println(contentOfLine[j]);
						// Berechnung des Indexes: z = f(x,y) = (((x-1)*8)+y)-1, wobei x die Zeile ist
						// und y der Part in der Zeile #lineMultiplicator
						int indexPos = getIndexPos(Integer.parseInt(contentOfPart[0]) + 1,
								(getLineMultiplicator(Double.parseDouble(contentOfPart[1])) + 1));
						// System.out.println("IndexPosition: "+ indexPos);
						fullDataInParts[indexPos] = contentOfLine[j];
					}
				}
			}
		}

		for (int i = 0; i < fullDataInParts.length; i++) {
			// System.out.println("DataPart: " + fullDataInParts[i]);
			if (!(fullDataInParts[i] != null && !fullDataInParts[i].equals(""))) {
				int country = i / 8;
				int valueKey = i % 8;
				double value = getValueFromMultiplicator(valueKey);
				fullDataInParts[i] = country + "," + value + "," + 0;
				System.out.println(fullDataInParts[i] + " was generated!");
			}
		}

		// schreibe alle Daten wieder in die Datei

		BufferedWriter BW = new BufferedWriter(new FileWriter(file));
		for (int i = 0; i < fullDataInParts.length; i++) {
			BW.write(fullDataInParts[i]);
			BW.write(";");
			// System.out.println("fullDataInParts[" + i + "]: " + fullDataInParts[i]);
			// System.out.println( (i+1) + "%8 = " + ((i+1)%8));
			if ((i + 1) % 8 == 0)
				BW.write(n);
		}
		BW.close();

		System.out.println("rescued data: successfully");

		/*
		 * for(int i = 0; i < TextLine.length; i++){ String[] contentLine =
		 * TextLine[i].split(";"); for(int j = 0; j < contentLine.length; j++){ String[]
		 * contentPart = contentLine[j].split(","); if(!contentPart[0].equals(i)){
		 * System.out.println(contentLine[j] + " ist in der falschen Zeile!"); } }
		 * 
		 * }
		 */
	}

	public static boolean Visibility = true;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Main(String title) throws IOException {
		super(title);

		JLabel MainPanel = new JLabel();
		MainPanel.setName(texts.getString("mainPanelName"));

		CountryBox = new JComboBox(countryNames);
		CountryBox.setBounds(50, 50, 100, 20);
		CountryBox.setSelectedIndex(1);
		CountryBox.setVisible(Visibility);
		CountryBox.addActionListener(this);
		MainPanel.add(CountryBox);

		YearBox = new JComboBox(Years);
		YearBox.setBounds(50, 80, 100, 20);
		YearBox.setSelectedIndex(0);
		YearBox.setVisible(Visibility);
		YearBox.addActionListener(this);
		MainPanel.add(YearBox);

		ValueBox = new JComboBox(Values);
		ValueBox.setBounds(50, 110, 100, 20);
		ValueBox.setSelectedIndex(0);
		ValueBox.setVisible(Visibility);
		ValueBox.addActionListener(this);
		MainPanel.add(ValueBox);

		GermanPerfecture = new JComboBox(Perfectures);
		GermanPerfecture.setBounds(50, 140, 100, 20);
		GermanPerfecture.setSelectedIndex(0);
		GermanPerfecture.setVisible(Visibility);
		GermanPerfecture.addActionListener(this);
		MainPanel.add(GermanPerfecture);

		CheckStatusRB = new JRadioButton(texts.getString("checkStatus"), true);
		CheckStatusRB.setBounds(180, 50, 150, 20);
		CheckStatusRB.setVisible(Visibility);
		MainPanel.add(CheckStatusRB);

		AddStatusRB = new JRadioButton(texts.getString("addStatus"), false);
		AddStatusRB.setBounds(180, 80, 150, 20);
		AddStatusRB.setVisible(Visibility);
		MainPanel.add(AddStatusRB);

		RemoveStatusRB = new JRadioButton(texts.getString("removeStatus"), false);
		RemoveStatusRB.setBounds(180, 110, 150, 20);
		RemoveStatusRB.setVisible(Visibility);
		MainPanel.add(RemoveStatusRB);

		confirm = new JButton(texts.getString("confirm"));
		confirm.addActionListener(this);
		confirm.setBounds(180, 180, 100, 20);
		confirm.setVisible(Visibility);
		MainPanel.add(confirm);

		Exit = new JButton(texts.getString("exit"));
		Exit.addActionListener(this);
		Exit.setBounds(180, 220, 90, 20);
		Exit.setVisible(Visibility);
		MainPanel.add(Exit);

		initProgressBar = new JProgressBar(SwingConstants.VERTICAL);
		initProgressBar.setBounds(50, 300, 300, 15);
		initProgressBar.setVisible(!Visibility);
		MainPanel.add(initProgressBar);

		label = new JLabel(texts.getString("label"));
		label.setBounds(10, 330, 380, 20);
		label.setVisible(Visibility);
		MainPanel.add(label);

		JTabbedPane tableFeatures = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		tableFeatures.add("dingens 1", new JPanel());
		tableFeatures.add("Dingens 2", new JPanel());
		tableFeatures.setVisible(true);

		JPanel tables = new JPanel();
		tables.setName("tables");
		tables.add(tableFeatures);

		JPanel features = new JPanel();
		features.setName("features");

		worth = new JLabel("The Value is:" + String.valueOf(getWorth()));
		// worth.setBounds(x, y, width, height);
		worth.setVisible(true);
		features.add(worth);

		JPanel settings = new JPanel();
		settings.setName(texts.getString("settings"));

		reset = new JButton(texts.getString("reset"));
		reset.addActionListener(this);
		reset.setBounds(180, 220, 90, 20);
		reset.setVisible(Visibility);
		settings.add(reset);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.add(MainPanel.getName(), MainPanel);
		tabbedPane.add(tables.getName(), tables);
		tabbedPane.add(features.getName(), features);
		tabbedPane.add(settings.getName(), settings);
		tabbedPane.setVisible(true);

		add(tabbedPane);

	}

	public static void groupButton() {
		ButtonGroup bgrb = new ButtonGroup();
		bgrb.add(AddStatusRB);
		bgrb.add(CheckStatusRB);
		bgrb.add(RemoveStatusRB);
	}

	public static void genRegistry(int year) throws IOException {
		File file = new File(getExecutionPath() + "/data/Coinages" + year + ".co");
		file.createNewFile();

		BufferedWriter CoinBW = new BufferedWriter(new FileWriter(file));

		for (int i = 0; i < getMembersFromYear(year); i++) { // looped durch jedes Land, das in dem Jahr den Euro hatte
			System.out.println("write in line " + (i + 1));
			for (int j = 0; j < 2; j++) {
				int k = (int) Math.pow(10, j);
				CoinBW.write(i + "," + 0.01 * k + ",0;");
				CoinBW.write(i + "," + 0.02 * k + ",0;");
				CoinBW.write(i + "," + 0.05 * k + ",0;");
			}
			CoinBW.write(i + "," + 1 + ",0;");
			CoinBW.write(i + "," + 2 + ",0;");
			CoinBW.write(n);
		}
		CoinBW.close();
		System.out.println("generating successfully!");
	}

	public static void updateRegistry(String land, double value, int year, boolean holding) throws IOException {
		int multiplicator = getLineMultiplicator(value);
		int countryKey = countryKey(year, land);

		File CoinRegistry = new File(getPath(year));

		FileReader CoinFR = new FileReader(CoinRegistry);
		BufferedReader CoinBR = new BufferedReader(CoinFR);

		String[] line = new String[getMembersFromYear(year)]; // erstellen ein Array in der Größe der Anzahl der
																// EuroStaaten des Jahres
		for (int i = 0; i < getMembersFromYear(year); i++) { // fülle das Array mit jeder Zeile der angegebenen Datei
			line[i] = CoinBR.readLine();
		}
		content = line[countryKey].split(";");
		String[] content_ = content;
		content = content[multiplicator].split(",");

		if (holding) {
			content[2] = "1";
		} else
			content[2] = "0";

		String str = countryKey + "," + value + "," + content[2] + ";";
		String out = "";
		for (int i = 0; i < 8; i++) { // setze die einzelnen Teile der Zeile wieder zusammen
			if (i == multiplicator) {
				out = out + str;
			} else
				out = out + content_[i] + ";";
		}

		line[countryKey] = out; // line beinhaltet alle linien der Datei und nun wird die modifizierte Zeile
								// wieder in diese "Liste" eingefügt
		FileWriter CoinFW = new FileWriter(CoinRegistry);
		BufferedWriter CoinBW = new BufferedWriter(CoinFW);
		for (int i = 0; i < line.length; i++) { // loop durch jede Zeile der Datei und schreibe sie alle wieder in die
												// Datei
			CoinBW.write(line[i]);
			CoinBW.write(n);
		}
		CoinBW.close();
		CoinFW.close();
		CoinBR.close();
		CoinFR.close();
	}

	public static boolean getStatus(String land, double value, int year) throws IOException {
		int multiplicator = getLineMultiplicator(value);
		int countryKey = countryKey(year, land);

		File CoinRegistry = new File(getPath(year));
		FileReader CoinFR = new FileReader(CoinRegistry);
		BufferedReader CoinBR = new BufferedReader(CoinFR);

		String[] line = new String[countryKey + 1]; // Arrays basieren auf einem index, der mit 0 beginnt, allerdings
													// muss die größe die gesamte Zahl haben
		for (int i = 0; i <= countryKey; i++) {
			line[i] = CoinBR.readLine();
		}

		content = line[countryKey].split(";");

		content = content[multiplicator].split(",");

		CoinBR.close();
		CoinFR.close();
		if (content[2].equals("1")) {
			return true;
		} else
			return false;

	}

	float getWorth() throws IOException {
		float worth = 0;
		for (int year = 1999; year < 2017; year++) {
			File file = new File(getPath(year));
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			for (int i = 0; i < getMembersFromYear(year); i++) {
				String line = bufferedReader.readLine();
				String[] parts = line.split(";");
				for (int j = 0; j < parts.length; j++) {
					String[] part = parts[j].split(",");
					if (part[2].equals("1"))
						worth += Float.parseFloat(part[1]);
				}

			}
			bufferedReader.close();
		}
		return worth;
	}

	public static int countryKey(int year, String land) {
		int key = 42;
		for (int i = 0; i < getMembersFromYear(year); i++) {
			if (countryNames[i].equals(land)) {
				key = i;
				break;
			}
		}
		return key;
	}

	public static int getLineMultiplicator(double value) {
		int lineMultiplicator = 42;
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
		}
		return lineMultiplicator;
	}

	public static double getValueFromMultiplicator(int multiplicator) {
		double value = 0;
		switch (multiplicator) {
		case 0:
			value = 0.01;
			break;
		case 1:
			value = 0.02;
			break;
		case 2:
			value = 0.05;
			break;
		case 3:
			value = 0.1;
			break;
		case 4:
			value = 0.2;
			break;
		case 5:
			value = 0.5;
			break;
		case 6:
			value = 1;
			break;
		case 7:
			value = 2;
			break;
		}
		return value;
	}

	public static int getIndexPos(int x, int y) {
		int z = (((x - 1) * 8) + y) - 1;
		return z;
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

	private static String getExecutionPath() {
		String absolutePath = new File(".").getAbsolutePath();
		File file = new File(absolutePath);
		absolutePath = file.getParentFile().toString();
		return absolutePath;
	}

	public static String getPath(int year) {
		String path = getExecutionPath() + "/data/coinages" + year + ".co";
		return path;
	}

}

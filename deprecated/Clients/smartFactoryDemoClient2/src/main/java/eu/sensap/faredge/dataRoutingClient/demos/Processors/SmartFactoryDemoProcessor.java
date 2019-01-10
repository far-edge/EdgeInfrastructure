package eu.sensap.faredge.dataRoutingClient.demos.Processors;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import eu.sensap.faredge.dataRoutingClient.senml.SenmlDataObject;
import eu.sensap.faredge.dataRoutingClient.senml.SenmlPack;


public class SmartFactoryDemoProcessor {
	// Init variables
	private String splitter;
	private String csvFile;

	// local variables to the class
	private String baseName;
	private Number baseTime;
	private BufferedReader br = null;
	private ArrayList<String> headerFields = null;

	// TODO: check
	private String[] units = { "WH", "VA" }; // Units for measurements of SmartFactory demo. This will be dynamically
												// generated from registry. This can be skipped
	private ArrayList<String> senmlJsonStrings;
	private ArrayList<SenmlPack> senmlPacks;

	public SmartFactoryDemoProcessor(String fileURI, String splitter) {
		this.splitter = splitter;
		this.csvFile = fileURI;
	}

	public void parseFile() {
		int counter = 0;

		try {
			String line = "";
			setSenmlJsonStrings(new ArrayList<String>());
			setSenmlPacks(new ArrayList<SenmlPack>());
			
			br = new BufferedReader(new FileReader(csvFile));

			headerFields = new ArrayList<String>(parseRawStringToFields(br.readLine(), splitter));

			while ((line = br.readLine()) != null) {
				if (line.length() < 1) {
					continue;
				}

				// extract fields from line
				ArrayList<String> rawFields = parseRawStringToFields(line, splitter);

				// create senml records from line and header. A senml pack per raw
//				ArrayList<SenmlDataObject> senmlRecords = this.extractSenmlRecords(rawFields);			
								
				// fill senmlPack list
//				SenmlPack senmlPack = new SenmlPack();
//				senmlPack.addEntries(senmlRecords);
//				senmlPacks.add(senmlPack);
				
				// create senml records from line and header. Multiple senml packs per raw
				ArrayList<SenmlDataObject> senmlRecords = this.extractSeperateSenmlRecords(rawFields);	
				int count = senmlRecords.size();
				for (int i=0;i<count;i++)
				{
					SenmlPack senmlPack = new SenmlPack();
					senmlPack.addBaseEntry(senmlRecords.get(i));
					senmlPacks.add(senmlPack);
				}
				
				// create senml Json string for current record and to array list
				//String senmlJsonString = this.toSenmlJson(senmlRecords);
				//senmlJsonStrings.add(senmlJsonString);

				
				
//				System.out.println(senmlJsonString);

				counter++;

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("counter = " + counter);

	}

	private ArrayList<String> parseRawStringToFields(String raw, String splitter) {

		String[] fields = raw.split(splitter);
		ArrayList<String> fieldsArray = new ArrayList<String>(Arrays.asList(fields));

		// Alternative implementation
		// ArrayList<String> fieldsArray = new ArrayList<String>();
		// Collections.addAll(fieldsArray, fields);

		return fieldsArray;
	}

	private ArrayList<SenmlDataObject> extractSenmlRecords(ArrayList<String> rawFields) {
		int lines = 2;

		ArrayList<SenmlDataObject> records = new ArrayList<SenmlDataObject>();

		// create base record
		String baseName = rawFields.get(1);
		Number baseTime = stringToTimestamp(rawFields.get(0));
		SenmlDataObject baseRecord = new SenmlDataObject(baseName, baseTime, 10);
		records.add(baseRecord);
		try {

			// create other records
			for (int i = 0; i < lines; i++) {

				// Number time = rawFields.get(i+2)
				Number number;

				number = NumberFormat.getInstance().parse(rawFields.get(i + 2));

				SenmlDataObject rec = new SenmlDataObject(headerFields.get(i + 2), this.units[i], number);				
				records.add(rec);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return records;
	}
	private ArrayList<SenmlDataObject> extractSeperateSenmlRecords(ArrayList<String> rawFields) {
		int lines = 2;

		ArrayList<SenmlDataObject> records = new ArrayList<SenmlDataObject>();

		// create base record
		String baseName = rawFields.get(1);
		Number baseTime = stringToTimestamp(rawFields.get(0));
//		SenmlDataObject baseRecord = new SenmlDataObject(baseName, baseTime, 10);
//		records.add(baseRecord);
		try {

			// create other records
			for (int i = 0; i < lines; i++) {

				// Number time = rawFields.get(i+2)
				Number number;

				number = NumberFormat.getInstance().parse(rawFields.get(i + 2));

				SenmlDataObject rec = new SenmlDataObject(headerFields.get(i + 2), this.units[i], number);
				rec.setBaseName(baseName);
				rec.setBaseTime(baseTime);
				rec.setVersion(10);
				records.add(rec);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return records;
	}

	// convert string to Timestamp
	private Number stringToTimestamp(String strTime) {

		String format = "dd.MM.yyyy HH:mm";

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date parsedDate;
		try {
			parsedDate = dateFormat.parse(strTime);
			Number timestamp = parsedDate.getTime();
			return timestamp;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

	}

	// returns the SenML JSON of measurement
	private String toSenmlJson(ArrayList<SenmlDataObject> records) {

		// create a SenML pack
		SenmlPack senmlRoot = new SenmlPack();

		// add records
		senmlRoot.addEntries(records);

		return senmlRoot.convertToJson();
	}

	// Getters and Setters

	public String getBaseName() {
		return baseName;
	}

	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}

	public Number getBaseTime() {
		return baseTime;
	}

	public void setBaseTime(Number baseTime) {
		this.baseTime = baseTime;
	}

	public ArrayList<String> getSenmlJsonStrings() {
		return senmlJsonStrings;
	}

	public void setSenmlJsonStrings(ArrayList<String> senmlJsonStrings) {
		this.senmlJsonStrings = senmlJsonStrings;
	}

	public ArrayList<SenmlPack> getSenmlPacks() {
		return senmlPacks;
	}

	public void setSenmlPacks(ArrayList<SenmlPack> senmlPacks) {
		this.senmlPacks = senmlPacks;
	}
}

	package dataviewer2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class ProcessData {
	
	//static finals
    private final static String 	DEFAULT_COUNTRY = "United States";
    private final static String[] 	VISUALIZATION_MODES = { "Raw", "Extrema (within 10% of min/max)" };
    private final static int 		FILE_COUNTRY_IDX = 4;
    private final static int 		FILE_NUM_COLUMNS = 5;
    private final static int 		FILE_DATE_IDX = 0;
    private final static int 		FILE_STATE_IDX = 3;
    private final static int 		FILE_TEMPERATURE_IDX = 1;
    
    
    
	
    private final String m_dataFile;
    private List<List<Object>> m_dataRaw;
    private SortedSet<String> m_dataStates;
    private SortedSet<String> m_dataCountries;
    private SortedSet<Integer> m_dataYears;


    // user selections
    private String m_selectedCountry = DEFAULT_COUNTRY;
    private Integer m_selectedEndYear;
    private String m_selectedState;
    private Integer m_selectedStartYear;
    private String m_selectedVisualization = VISUALIZATION_MODES[0];
    
    
    
    
    
    
    private TreeMap<Integer, SortedMap<Integer,Double>> m_plotData = null;
	
	
    public ProcessData(String m_file) {
    	this.m_dataFile = m_file; 
    }
    
    public void loadData() throws FileNotFoundException {
        // reset the data storage in case this is a re-load
        m_dataRaw = new ArrayList<List<Object>>();
        m_dataStates = new TreeSet<String>();
        m_dataCountries = new TreeSet<String>();
        m_dataYears = new TreeSet<Integer>();
        m_plotData = null;

        try (Scanner scanner = new Scanner(new File(m_dataFile))) {
            boolean skipFirst = true;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if(!skipFirst) {
                    List<Object> record = getRecordFromLine(line);
                    if(record != null) {
                        m_dataRaw.add(record);
                    }
                }
                else {
                    skipFirst = false;
                }
            }
            // update selections (not including country) for the newly loaded data
            m_selectedState = m_dataStates.first();
            m_selectedStartYear = m_dataYears.first();
            m_selectedEndYear = m_dataYears.last();

            //info("loaded %d data records", m_dataRaw.size());
            //info("loaded data for %d states", m_dataStates.size());
            //info("loaded data for %d years [%d, %d]", m_dataYears.size(), m_selectedStartYear, m_selectedEndYear);
        }
    }
    
    
    
    
  //--------------------------------------------------------------------------------
    
    
    private List<Object> getRecordFromLine(String line) {
        List<String> rawValues = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                rawValues.add(rowScanner.next());
            }
        }
        m_dataCountries.add(rawValues.get(FILE_COUNTRY_IDX));
        if(rawValues.size() != FILE_NUM_COLUMNS) {
            //trace("malformed line '%s'...skipping", line);
            return null;
        }
        else if(!rawValues.get(FILE_COUNTRY_IDX).equals(m_selectedCountry)) {
            //trace("skipping non-USA record: %s", rawValues);
            return null;
        }
        else {
            //trace("processing raw data: %s", rawValues.toString());
        }
        try {
            // Parse these into more useful objects than String
            List<Object> values = new ArrayList<Object>(4);

            Integer year = parseYear(rawValues.get(FILE_DATE_IDX));
            if(year == null) {
                return null;
            }
            values.add(year);

            Integer month = parseMonth(rawValues.get(FILE_DATE_IDX));
            if(month == null) {
                return null;
            }
            values.add(month);
            values.add(Double.parseDouble(rawValues.get(FILE_TEMPERATURE_IDX)));
            //not going to use UNCERTAINTY yet
            //values.add(Double.parseDouble(rawValues.get(FILE_UNCERTAINTY_IDX)));
            values.add(rawValues.get(FILE_STATE_IDX));
            // since all are the same country
            //values.add(rawValues.get(FILE_COUNTRY_IDX));

            // if we got here, add the state to the list of states
            m_dataStates.add(rawValues.get(FILE_STATE_IDX));
            m_dataYears.add(year);
            return values;
        }
        catch(NumberFormatException e) {
            //trace("unable to parse data line, skipping...'%s'", line);
            return null;
        }
    }
    
    // PARSE ---------------------------------------------------------------------------------
    
    private Integer parseMonth(String dateString) {
        Integer ret = null;
        if(dateString.indexOf("/") != -1) {
            // Assuming something like 1/20/1823
            String[] parts = dateString.split("/");
            if(parts.length == 3) {
                ret = Integer.parseInt(parts[0]);
            }
        }
        else if(dateString.indexOf("-") != -1) {
            // Assuming something like 1823-01-20
            String[] parts = dateString.split("-");
            if(parts.length == 3) {
                ret = Integer.parseInt(parts[1]);
            }
        }
        else {
            throw new RuntimeException(String.format("Unexpected date delimiter: '%s'", dateString));
        }
        if(ret == null || ret.intValue() < 1 || ret.intValue() > 12) {
            //trace("Unable to parse month from date: '%s'", dateString);
            return null;
        }
        return ret;
    }
    
    
    private Integer parseYear(String dateString) {
        Integer ret = null;
        if(dateString.indexOf("/") != -1) {
            // Assuming something like 1/20/1823
            String[] parts = dateString.split("/");
            if(parts.length == 3) {
                ret = Integer.parseInt(parts[2]);
            }
        }
        else if(dateString.indexOf("-") != -1) {
            // Assuming something like 1823-01-20
            String[] parts = dateString.split("-");
            if(parts.length == 3) {
                ret = Integer.parseInt(parts[0]);
            }
        }
        else {
            throw new RuntimeException(String.format("Unexpected date delimiter: '%s'", dateString));
        }
        if(ret == null) {
            //trace("Unable to parse year from date: '%s'", dateString);
        }
        return ret;
    }
    
    // GETTERS --------------------------------------------------------------------------
    
    
    public String getM_selectedCountry() {
        return m_selectedCountry;
    }

    public Integer getM_selectedEndYear() {
        return m_selectedEndYear;
    }

    public String getM_selectedState() {
        return m_selectedState;
    }

    public Integer getM_selectedStartYear() {
        return m_selectedStartYear;
    }

    public String getM_selectedVisualization() {
        return m_selectedVisualization;
    }
}

package dataviewer2;

//import edu.du.dudraw.Draw;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class DataViewer {
    // Private constants (alphabetical)

//    private final static String 	DEFAULT_COUNTRY = "United States";
    private final static boolean	DO_DEBUG = true;
    private final static boolean	DO_TRACE = false;

//    private final static int 		FILE_COUNTRY_IDX = 4;
//    private final static int 		FILE_DATE_IDX = 0;
//    private final static int 		FILE_NUM_COLUMNS = 5;
//    private final static int 		FILE_STATE_IDX = 3;
//    private final static int 		FILE_TEMPERATURE_IDX = 1;
    private final static int 		FILE_UNCERTAINTY_IDX = 2;

    private final static String[] 	MONTH_NAMES = { "", // 1-based
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
    private final static int		RECORD_MONTH_IDX = 1;
    private final static int		RECORD_STATE_IDX = 3;
    private final static int		RECORD_TEMPERATURE_IDX = 2;
    private final static int		RECORD_YEAR_IDX = 0;


//    private final static String[] 	VISUALIZATION_MODES = { "Raw", "Extrema (within 10% of min/max)" };



    // Instance variables (alphabetized)

    // data storage
//    private final String m_dataFile;
//    private List<List<Object>> m_dataRaw;
//    private SortedSet<String> m_dataStates;
//    private SortedSet<String> m_dataCountries;
//    private SortedSet<Integer> m_dataYears;


    // user selections
//    private String m_selectedCountry = DEFAULT_COUNTRY;
//    private Integer m_selectedEndYear;
//    private String m_selectedState;
//    private Integer m_selectedStartYear;
//    private String m_selectedVisualization = VISUALIZATION_MODES[0];

private ProcessData pd;
    
    // plot-related data
    private TreeMap<Integer, SortedMap<Integer,Double>> m_plotData = null;
    private TreeMap<Integer,Double> m_plotMonthlyMaxValue = null;
    private TreeMap<Integer,Double> m_plotMonthlyMinValue = null;

    //private DataViewerHUD display;

    public DataViewer(String dataFile) throws FileNotFoundException {
    	
    	this.pd = new ProcessData(dataFile);

//<<<<<<< HEAD
//        this.display=new DataViewerHUD(this);
        
        pd.loadData();
//=======
        //this.display=new DataViewerHUD(this);
        
//>>>>>>> branch 'main' of https://github.com/Fiveknight127/OOPFinalProj.git

    }
    /**
     * Utility function to pull a year integer out of a date string.  Supports M/D/Y and Y-M-D formats only.
     *
     * @param dateString
     * @return
     */
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



//    private List<Object> getRecordFromLine(String line) {
//        List<String> rawValues = new ArrayList<String>();
//        try (Scanner rowScanner = new Scanner(line)) {
//            rowScanner.useDelimiter(",");
//            while (rowScanner.hasNext()) {
//                rawValues.add(rowScanner.next());
//            }
//        }
//        m_dataCountries.add(rawValues.get(FILE_COUNTRY_IDX));
//        if(rawValues.size() != FILE_NUM_COLUMNS) {
//            //trace("malformed line '%s'...skipping", line);
//            return null;
//        }
//        else if(!rawValues.get(FILE_COUNTRY_IDX).equals(m_selectedCountry)) {
//            //trace("skipping non-USA record: %s", rawValues);
//            return null;
//        }
//        else {
//            //trace("processing raw data: %s", rawValues.toString());
//        }
//        try {
//            // Parse these into more useful objects than String
//            List<Object> values = new ArrayList<Object>(4);
//
//            Integer year = parseYear(rawValues.get(FILE_DATE_IDX));
//            if(year == null) {
//                return null;
//            }
//            values.add(year);
//
//            Integer month = parseMonth(rawValues.get(FILE_DATE_IDX));
//            if(month == null) {
//                return null;
//            }
//            values.add(month);
//            values.add(Double.parseDouble(rawValues.get(FILE_TEMPERATURE_IDX)));
//            //not going to use UNCERTAINTY yet
//            //values.add(Double.parseDouble(rawValues.get(FILE_UNCERTAINTY_IDX)));
//            values.add(rawValues.get(FILE_STATE_IDX));
//            // since all are the same country
//            //values.add(rawValues.get(FILE_COUNTRY_IDX));
//
//            // if we got here, add the state to the list of states
//            m_dataStates.add(rawValues.get(FILE_STATE_IDX));
//            m_dataYears.add(year);
//            return values;
//        }
//        catch(NumberFormatException e) {
//            //trace("unable to parse data line, skipping...'%s'", line);
//            return null;
//        }
//    }

//    private Integer parseMonth(String dateString) {
//        Integer ret = null;
//        if(dateString.indexOf("/") != -1) {
//            // Assuming something like 1/20/1823
//            String[] parts = dateString.split("/");
//            if(parts.length == 3) {
//                ret = Integer.parseInt(parts[0]);
//            }
//        }
//        else if(dateString.indexOf("-") != -1) {
//            // Assuming something like 1823-01-20
//            String[] parts = dateString.split("-");
//            if(parts.length == 3) {
//                ret = Integer.parseInt(parts[1]);
//            }
//        }
//        else {
//            throw new RuntimeException(String.format("Unexpected date delimiter: '%s'", dateString));
//        }
//        if(ret == null || ret.intValue() < 1 || ret.intValue() > 12) {
//            //trace("Unable to parse month from date: '%s'", dateString);
//            return null;
//        }
//        return ret;
//    }

//    public void loadData() throws FileNotFoundException {
//        // reset the data storage in case this is a re-load
//        m_dataRaw = new ArrayList<List<Object>>();
//        m_dataStates = new TreeSet<String>();
//        m_dataCountries = new TreeSet<String>();
//        m_dataYears = new TreeSet<Integer>();
//        m_plotData = null;
//
//        try (Scanner scanner = new Scanner(new File(m_dataFile))) {
//            boolean skipFirst = true;
//            while (scanner.hasNextLine()) {
//                String line = scanner.nextLine();
//
//                if(!skipFirst) {
//                    List<Object> record = getRecordFromLine(line);
//                    if(record != null) {
//                        m_dataRaw.add(record);
//                    }
//                }
//                else {
//                    skipFirst = false;
//                }
//            }
//            // update selections (not including country) for the newly loaded data
//            m_selectedState = m_dataStates.first();
//            m_selectedStartYear = m_dataYears.first();
//            m_selectedEndYear = m_dataYears.last();
//
//            //info("loaded %d data records", m_dataRaw.size());
//            //info("loaded data for %d states", m_dataStates.size());
//            //info("loaded data for %d years [%d, %d]", m_dataYears.size(), m_selectedStartYear, m_selectedEndYear);
//        }
//    }

    public void updatePlotData() {
        //debug("raw data: %s", m_rawData.toString());
        // plot data is a map where the key is the Month, and the value is a sorted map where the key
        // is the year.
        m_plotData = new TreeMap<Integer,SortedMap<Integer,Double>>();
        for(int month = 1; month <= 12; month++) {
            // any year/months not filled in will be null
            m_plotData.put(month, new TreeMap<Integer,Double>());
        }
        // now run through the raw data and if it is related to the current state and within the current
        // years, put it in a sorted data structure, so that we
        // find min/max year based on data
        m_plotMonthlyMaxValue = new TreeMap<Integer,Double>();
        m_plotMonthlyMinValue = new TreeMap<Integer,Double>();
        // initialize
        for(int i = 1; i <= 12; i++) {
            m_plotMonthlyMaxValue.put(i, Double.MIN_VALUE);
            m_plotMonthlyMinValue.put(i, Double.MAX_VALUE);
        }
        for(List<Object> rec : pd.getM_dataRaw()) { //GET
            String state = (String)rec.get(RECORD_STATE_IDX);
            Integer year = (Integer)rec.get(RECORD_YEAR_IDX);

            // Check to see if they are the state and year range we care about
            if (state.equals(pd.getM_selectedState()) && //GET
                    ((year.compareTo(pd.getM_selectedStartYear()) >= 0 && year.compareTo(pd.getM_selectedEndYear()) <= 0))) {//GET

                // Ok, we need to add this to the list of values for the month
                Integer month = (Integer)rec.get(RECORD_MONTH_IDX);
                Double value = (Double)rec.get(RECORD_TEMPERATURE_IDX);

                if(!m_plotMonthlyMinValue.containsKey(month) || value.compareTo(m_plotMonthlyMinValue.get(month)) < 0) {
                    m_plotMonthlyMinValue.put(month, value);
                }
                if(!m_plotMonthlyMaxValue.containsKey(month) || value.compareTo(m_plotMonthlyMaxValue.get(month)) > 0) {
                    m_plotMonthlyMaxValue.put(month, value);
                }

                m_plotData.get(month).put(year, value);
            }
        }
        //debug("plot data: %s", m_plotData.toString());
    }






    //GETTERS
    public String getM_selectedCountry() {
        return pd.getM_selectedCountry();
    }

    public Integer getM_selectedEndYear() {
        return pd.getM_selectedEndYear();
    }

    public String getM_selectedState() {
        return pd.getM_selectedState();
    }

    public Integer getM_selectedStartYear() {
        return pd.getM_selectedStartYear();
    }

    public String getM_selectedVisualization() {
        return pd.getM_selectedVisualization();
    }

    public TreeMap<Integer, SortedMap<Integer, Double>> getM_plotData() {
        return m_plotData;
    }

    public TreeMap<Integer, Double> getM_plotMonthlyMaxValue() {
        return m_plotMonthlyMaxValue;
    }

    public TreeMap<Integer, Double> getM_plotMonthlyMinValue() {
        return m_plotMonthlyMinValue;
    }

    public SortedSet<String> getM_dataStates() {
        return pd.getM_dataStates();
    }

    public SortedSet<Integer> getM_dataYears() {
        return pd.getM_dataYears();
    }

    public SortedSet<String> getM_dataCountries() {
        return pd.getM_dataCountries();
    }
//
    public String[] getVisualizationModes(){
    	return pd.getVisualizationModes(); //FIX
        //this.pd.get

    	}

    public void setM_selectedCountry(String m_selectedCountry) {
    	pd.setM_selectedCountry(m_selectedCountry);
    }

    public void setM_selectedEndYear(Integer m_selectedEndYear) {
//        this.m_selectedEndYear = m_selectedEndYear;
    	pd.setM_selectedEndYear(m_selectedEndYear);
    }

    public void setM_selectedState(String m_selectedState) {
//        this.m_selectedState = m_selectedState;
    	pd.setM_selectedState(m_selectedState);
    }

    public void setM_selectedStartYear(Integer m_selectedStartYear) {
//        this.m_selectedStartYear = m_selectedStartYear;
    	pd.setM_selectedStartYear(m_selectedStartYear);
    }

    public void setM_selectedVisualization(String m_selectedVisualization) {
//        this.m_selectedVisualization = m_selectedVisualization;
    	pd.setM_selectedVisualization(m_selectedVisualization);
    }

    public void loadData(){
        try{
        this.pd.loadData();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}

package practice.data;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Data structure to store information about businesses, users, and reviews.
 */
public class YelpStore {

    // TODO: Define data members here.
    private TreeMap<String, TreeSet<ReviewDetails>> reviews;
    private TreeMap<String, BusinessDetails> business;
    private ReviewComp comp;
    private BusinessComp bcomp;
    private NeighbourhoodComp hcomp;
    private NameComp ncomp;
    private TreeMap<String, String> businessIdList;
    private TreeMap<String, String> sortedList;
    private TreeMap<String, String> users;


    /**
     * Constructor. Create an empty YelpStore.
     */
    public YelpStore() {
        comp = new ReviewComp();
        bcomp = new BusinessComp();
        hcomp = new NeighbourhoodComp();
        users = new TreeMap<>();
        businessIdList = new TreeMap<String, String>();
        ncomp = new NameComp(businessIdList);
        sortedList = new TreeMap<String, String>(ncomp);
        business = new TreeMap<String, BusinessDetails>();
        reviews = new TreeMap<>();
    }

    /**
     * Add a new review.
     *
     * @param businessId - ID of the business reviewed.
     * @param rating     - integer rating 1-5.
     * @param review     - text of the review.
     * @param date       - date of the review in the format yyyy-MM-dd, e.g., 2015-05-25.
     * @param userId     - ID of the user writing the review.
     * @return true if successful, false if unsuccessful because of invalid date or rating.
     */
    public boolean addReview(String businessId, int rating, String review, String date, String userId) {

        ReviewDetails rd = new ReviewDetails(businessId, rating, review, date, userId);
        SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = new Date();
        try {
            d1 = dd.parse(date);
        } catch (ParseException e) {
            return false;
        }
        try {
            if (rating > 5 || rating < 0)
                return false;

            if (reviews.containsKey(businessId))
                reviews.get(businessId).add(rd);
            else {
                TreeSet<ReviewDetails> mySet = new TreeSet<ReviewDetails>(comp);
                mySet.add(new ReviewDetails(businessId, rating, review, date, userId));
                reviews.put(businessId, mySet);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Add a new business. Assumes the business has no neighborhood information.
     *
     * @param businessId - ID of the business.
     * @param name       - name of the business.
     * @param city       - city where the business is located.
     * @param state      - state where the business is located.
     * @param lat        - latitude of business location.
     * @param lon        - longitude of business location.
     * @return true if successful.
     */
    public boolean addBusiness(String businessId, String name, String city, String state, double lat, double lon) {
        try {
            //bSet.add(new BusinessDetails(businessId, name, city, state, lat, lon));
            business.put(businessId, new BusinessDetails(businessId, name, city, state, lat, lon));
            businessIdList.put(businessId, name);
            //sortedList.putAll(businessIdList);
            for (String key : businessIdList.keySet()) {
                sortedList.put(key, businessIdList.get(key));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Add a new business.
     *
     * @param businessId    - ID of the business.
     * @param name          - name of the business.
     * @param city          - city where the business is located.
     * @param state         - state where the business is located.
     * @param lat           - latitude of business location.
     * @param lon           - longitude of business location.
     * @param neighborhoods - JSONArray containing a list of neighborhoods where the business is located.
     * @return true if successful.
     */
    public boolean addBusiness(String businessId, String name, String city, String state, double lat, double lon, JsonArray neighborhoods) {

        try {
            //bSet.add(new BusinessDetails(businessId, name, city, state, lat, lon, neighborhoods));
            business.put(businessId, new BusinessDetails(businessId, name, city, state, lat, lon, neighborhoods));
            businessIdList.put(businessId, name);
            sortedList.putAll(businessIdList);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Add a new business.
     *
     * @param businessId    - ID of the business.
     * @param name          - name of the business.
     * @param city          - city where the business is located.
     * @param state         - state where the business is located.
     * @param lat           - latitude of business location.
     * @param lon           - longitude of business location.
     * @param neighborhoods - comma separated String containing a list of neighborhoods
     * @return true if successful.
     */
    public boolean addBusiness(String businessId, String name, String city, String state, double lat, double lon, String neighborhoods) {
        try {
            //bSet.add(new BusinessDetails(businessId, name, city, state, lat, lon, neighborhoods));
            business.put(businessId, new BusinessDetails(businessId, name, city, state, lat, lon, neighborhoods));
            //businessIdList.add(businessId);
            businessIdList.put(businessId, name);
            sortedList.putAll(businessIdList);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Add a new user.
     *
     * @param userId - ID of the user.
     * @param name   - name of the user (e.g., Sami R.)
     * @return true if successful.
     */
    public boolean addUser(String userId, String name) {
        try {
            users.put(userId, name);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Return a string representation of the data store. Format must be as follows:
     * Business1 Name - City, State (lat, lon) (neighborhood1, neighborhood2)
     * Rating - User: Review
     * Rating - User: review
     * Business2 Name - City, State (lat, lon) (neighborhood1, neighborhood2)
     * Rating - User: Review
     * Rating - User: review
     * Adhere to the following rules in generating the output:
     * 1. Business Names must be sorted alphabetically.
     * 2. Ratings for a particular business must be sorted by date.
     * 3. Only reviews for businesses that have been added will be displayed.
     * 4. If a review is written by user U and no user U has been added to the store the review will appear with no name.
     *
     * @return string representation of the data store
     */
    public String toString() {

        String ret = "";
        String temp = "";
        
        for (String key : sortedList.keySet()) {
            ret += sortedList.get(key) + " - " + business.get(key).getCity() + ", " +
                    business.get(key).getState() + " (" + business.get(key).getLatitude() + ", " +
                    business.get(key).getLongitude() + ")" + " (";

            if (business.get(key).getNeighborhoods() != null)
                temp = business.get(key).getNeighborhoods().toString();
            if (temp.equals("[]"))
                temp = temp.replace("[]", "");
            if (temp.contains("\",\""))
                temp = temp.replace("\",\"", ", ");
            if (temp.startsWith("[\""))
                temp = temp.replace("[\"", "");
            if (temp.endsWith("\"]"))
                temp = temp.replace("\"]", "");

            String tokens[] = temp.split(", ");
            Arrays.sort(tokens);

            temp = tokens.toString();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < tokens.length; i++) {
                sb.append(tokens[i]);
                if (!(i == tokens.length - 1))
                    sb.append(", ");
            }
            ret += sb.toString();
            ret += ")";
            ret += System.lineSeparator();

            if (reviews.containsKey(key)) {
                Iterator<ReviewDetails> i = reviews.get(key).iterator();
                while (i.hasNext()) {
                    ReviewDetails rd = (ReviewDetails) i.next();
                    ret += rd.getRating() + " - ";
                    if (users.get(rd.getUserId()) != null)
                        ret += users.get(rd.getUserId());
                    ret += ": " + rd.getReview();
                    ret += System.lineSeparator();
                }
            }
            ret += System.lineSeparator();
        }
        return ret;
    }

    /**
     * Save the string representation of the data store to the file specified by fname.
     * This method must use the YelpStore toString method.
     *
     * @param fname - path specifying where to save the output.
     */
    public void printToFile(Path fname) {
        try {
            PrintWriter out = new PrintWriter(fname.toString());
            String result = toString();
            out.write(result);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

} 

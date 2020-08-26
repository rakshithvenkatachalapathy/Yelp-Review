package practice.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReviewDetails {

    private String businessId;
    private int rating;
    private String review;
    private String date;
    private String userId;

    public ReviewDetails(String businessId, int rating, String review, String date, String userId) {
        this.businessId = businessId;
        this.rating = rating;
        this.review = review;
        this.date = date;
        this.userId = userId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public int getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }

    public String getDate() {
        return date;
    }

    public String getUserId() {
        return userId;
    }


}

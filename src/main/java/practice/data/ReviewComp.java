package practice.data;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class ReviewComp implements Comparator<ReviewDetails> {
    @Override
    public int compare(ReviewDetails review1, ReviewDetails review2) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = df.parse(review1.getDate());
            Date date2 = df.parse(review2.getDate());

            if (date2.compareTo(date1) == 0) {
                if (review1.getUserId().compareTo(review2.getUserId()) == 0) {
                    return review1.getBusinessId().compareTo(review2.getBusinessId());
                } else {
                    return review1.getUserId().compareTo(review2.getUserId());
                }
            } else {
                return date1.compareTo(date2);
            }
        } catch (Exception e) {
            //--------------- If the date is empty --------------------------
            if (review1.getUserId().compareTo(review2.getUserId()) == 0) {
                return review1.getBusinessId().compareTo(review1.getBusinessId());
            } else {
                return review1.getUserId().compareTo(review2.getUserId());
            }
        }
    }

}

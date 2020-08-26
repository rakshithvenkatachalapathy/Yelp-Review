package practice.data;

import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

public class DataHelper {
    private YelpStore ydata;

    public DataHelper(YelpStore ydata) {
        this.ydata = ydata;
    }

    public void loadReview(String jsonFilename) throws FileNotFoundException {
        Scanner n = new Scanner(new File(jsonFilename));
        String businessID = "";
        int rating = 0;
        String text = "";
        String date = "";
        String userId = "";

        while (n.hasNextLine()) {
            String input = n.nextLine();

            try (JsonReader jsonReader = new JsonReader(new StringReader(input))) {
                jsonReader.beginObject();


                while (jsonReader.hasNext()) {
                    String name = jsonReader.nextName();
                    if (name.equals("business_id")) {
                        businessID = jsonReader.nextString();
                        System.out.println(businessID);
                    } else if (name.equals("stars")) {
                        rating = jsonReader.nextInt();
                    } else if (name.equals("text"))
                        text = jsonReader.nextString();
                    else if (name.equals("date"))
                        date = jsonReader.nextString();
                    else if (name.equals("user_id"))
                        userId = jsonReader.nextString();
                    else
                        jsonReader.skipValue();
                    if (jsonReader.peek().toString().equals("END_OBJECT")) {
                        ydata.addReview(businessID, rating, text, date, userId);
                        businessID = "";
                        rating = 0;
                        text = "";
                        date = "";
                        userId = "";
                    }

                }
                jsonReader.endObject();
            } catch (IOException e) {
                System.out.println("Could not read from file");
            }
        }

    }

    public void loadBusiness(String jsonFilename) throws FileNotFoundException {
        Scanner n = new Scanner(new File(jsonFilename));
        String businessID;
        String bName;
        String city;
        String state;
        String userId;
        double lat = 0;
        double lng = 0;
        String neighborhoods;
        boolean flag = false;

        while (n.hasNextLine()) {
            neighborhoods = "";
            businessID = "";
            bName = "";
            city = "";
            state = "";
            userId = "";
            String input = n.nextLine();

            try (JsonReader jsonReader = new JsonReader(new StringReader(input))) {
                jsonReader.beginObject();

                while (jsonReader.hasNext()) {
                    String name = jsonReader.nextName();
                    if (name.equals("business_id")) {
                        businessID = jsonReader.nextString();
                        System.out.println(businessID);
                    } else if (name.equals("name")) {
                        bName = jsonReader.nextString();
                    } else if (name.equals("city"))
                        city = jsonReader.nextString();
                    else if (name.equals("state"))
                        state = jsonReader.nextString();
                    else if (name.equals("longitude"))
                        lng = jsonReader.nextDouble();
                    else if (name.equals("latitude"))
                        lat = jsonReader.nextDouble();
                    else if (name.equals("neighborhoods")) {
                        jsonReader.beginArray();
                        if (!jsonReader.hasNext())
                            // ydata.addBusiness(businessID, bName, city, state, lat, lng);
                            flag = true;
                        while (jsonReader.hasNext()) {
                            neighborhoods = jsonReader.nextString() + " ";
                        }
                        jsonReader.endArray();
                    } else
                        jsonReader.skipValue();
                    if (jsonReader.peek().toString().equals("END_OBJECT")) {
                        if (flag)
                            ydata.addBusiness(businessID, bName, city, state, lat, lng);
                        else
                            ydata.addBusiness(businessID, bName, city, state, lat, lng, neighborhoods);
                        businessID = "";
                        bName = "";
                        city = "";
                        state = "";
                        lat = 0.0;
                        lng = 0.0;
                        flag = false;
                    }
                }
                jsonReader.endObject();
            } catch (IOException e) {
                System.out.println("Could not read from file");
            }
        }

    }

    public void loadUserInfo(String jsonFilename) throws FileNotFoundException {
        Scanner n = new Scanner(new File(jsonFilename));
        String uName = "";
        String userId = "";

        while (n.hasNextLine()) {
            String input = n.nextLine();
            try (JsonReader jsonReader = new JsonReader(new StringReader(input))) {
                jsonReader.beginObject();

                while (jsonReader.hasNext()) {
                    String name = jsonReader.nextName();
                    if (name.equals("user_id")) {
                        userId = jsonReader.nextString();
                        System.out.println(userId);
                    } else if (name.equals("name")) {
                        name = jsonReader.nextString();
                    } else
                        jsonReader.skipValue();
                    ydata.addUser(userId, uName);
                }
                jsonReader.endObject();
            } catch (IOException e) {
                System.out.println("Could not read from file");
            }
        }
    }
}

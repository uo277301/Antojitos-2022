package es.uniovi.eii.mainantojitos.db;

import android.os.Parcel;
import android.os.Parcelable;

public class RestaurantePojo implements Parcelable {
    private String address;
    private String[] awards;
    private String[] cuisine;
    private String[] dietaryRestrictions;
    private String email;
    private String[] hours;
    private String id;
    private String image;
    private boolean isClosed;
    private boolean isLongClosed;
    private String latitude;
    private String longitude;
    private String[] mealTypes;
    private String name;
    private int numberOfReviews;
    private String phone;
    private String priceLevel;
    private Object rankingDenominator;
    private int rankingPosition;
    private String rankingString;
    private float rating;
    private String[] reviews;
    private String type;
    private String webUrl;
    private String website;

    public RestaurantePojo(){}

    public RestaurantePojo(String address, String[] awards, String[] cuisine,
                           String[] dietaryRestrictions, String email, String[] hours, String id,
                           String image, boolean isClosed, boolean isLongClosed, String latitude,
                           String longitude, String[] mealTypes, String name, int numberOfReviews,
                           String phone, String priceLevel, Object rankingDenominator,
                           int rankingPosition, String rankingString, float rating, String[] reviews,
                           String type, String webUrl, String website) {
        this.address = address;
        this.awards = awards;
        this.cuisine = cuisine;
        this.dietaryRestrictions = dietaryRestrictions;
        this.email = email;
        this.hours = hours;
        this.id = id;
        this.image = image;
        this.isClosed = isClosed;
        this.isLongClosed = isLongClosed;
        this.latitude = latitude;
        this.longitude = longitude;
        this.mealTypes = mealTypes;
        this.name = name;
        this.numberOfReviews = numberOfReviews;
        this.phone = phone;
        this.priceLevel = priceLevel;
        this.rankingDenominator = rankingDenominator;
        this.rankingPosition = rankingPosition;
        this.rankingString = rankingString;
        this.rating = rating;
        this.reviews = reviews;
        this.type = type;
        this.webUrl = webUrl;
        this.website = website;
    }

    protected RestaurantePojo(Parcel in) {
        address = in.readString();
        awards = in.createStringArray();
        cuisine = in.createStringArray();
        dietaryRestrictions = in.createStringArray();
        email = in.readString();
        hours = in.createStringArray();
        id = in.readString();
        image = in.readString();
        isClosed = in.readByte() != 0;
        isLongClosed = in.readByte() != 0;
        latitude = in.readString();
        longitude = in.readString();
        mealTypes = in.createStringArray();
        name = in.readString();
        numberOfReviews = in.readInt();
        phone = in.readString();
        priceLevel = in.readString();
        rankingPosition = in.readInt();
        rankingString = in.readString();
        rating = in.readFloat();
        reviews = in.createStringArray();
        type = in.readString();
        webUrl = in.readString();
        website = in.readString();
    }

    public static final Creator<RestaurantePojo> CREATOR = new Creator<RestaurantePojo>() {
        @Override
        public RestaurantePojo createFromParcel(Parcel in) {
            return new RestaurantePojo(in);
        }

        @Override
        public RestaurantePojo[] newArray(int size) {
            return new RestaurantePojo[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String[] getAwards() {
        return awards;
    }

    public void setAwards(String[] awards) {
        this.awards = awards;
    }

    public String[] getCuisine() {
        return cuisine;
    }

    public void setCuisine(String[] cuisine) {
        this.cuisine = cuisine;
    }

    public String[] getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public void setDietaryRestrictions(String[] dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getHours() {
        return hours;
    }

    public void setHours(String[] hours) {
        this.hours = hours;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public boolean isLongClosed() {
        return isLongClosed;
    }

    public void setLongClosed(boolean longClosed) {
        isLongClosed = longClosed;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String[] getMealTypes() {
        return mealTypes;
    }

    public void setMealTypes(String[] mealTypes) {
        this.mealTypes = mealTypes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfReviews() {
        return numberOfReviews;
    }

    public void setNumberOfReviews(int numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(String priceLevel) {
        this.priceLevel = priceLevel;
    }

    public Object getRankingDenominator() {
        return rankingDenominator;
    }

    public void setRankingDenominator(Object rankingDenominator) {
        this.rankingDenominator = rankingDenominator;
    }

    public int getRankingPosition() {
        return rankingPosition;
    }

    public void setRankingPosition(int rankingPosition) {
        this.rankingPosition = rankingPosition;
    }

    public String getRankingString() {
        return rankingString;
    }

    public void setRankingString(String rankingString) {
        this.rankingString = rankingString;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String[] getReviews() {
        return reviews;
    }

    public void setReviews(String[] reviews) {
        this.reviews = reviews;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeStringArray(awards);
        dest.writeStringArray(cuisine);
        dest.writeStringArray(dietaryRestrictions);
        dest.writeString(email);
        dest.writeStringArray(hours);
        dest.writeString(id);
        dest.writeString(image);
        dest.writeByte((byte) (isClosed ? 1 : 0));
        dest.writeByte((byte) (isLongClosed ? 1 : 0));
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeStringArray(mealTypes);
        dest.writeString(name);
        dest.writeInt(numberOfReviews);
        dest.writeString(phone);
        dest.writeString(priceLevel);
        dest.writeInt(rankingPosition);
        dest.writeString(rankingString);
        dest.writeFloat(rating);
        dest.writeStringArray(reviews);
        dest.writeString(type);
        dest.writeString(webUrl);
        dest.writeString(website);
    }
}

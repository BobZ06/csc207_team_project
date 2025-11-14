package star_rate;

public class StarRateOutputData {
    final private int userStarRate;
    final private float average;

    public StarRateOutputData(int userStar, float avg){
        this.userStarRate = userStar;
        this.average = avg;
    }

    public int getUserStarRate(){
        return this.userStarRate;
    }
    public float getAverage(){
        return this.average;
    }
}

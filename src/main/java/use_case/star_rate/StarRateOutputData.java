package use_case.star_rate;

public class StarRateOutputData {
    final private float average;

    public StarRateOutputData(float avg){
        this.average = avg;
    }

    public float getAverage(){
        return this.average;
    }
}

package main.java.com.corportation.happyqa.dto;

/**
 * The ReleaseDTO class represents a release period defined by a start day,
 * a duration, and an end day. The end day is calculated based on the start
 * day and duration, where the start day is included in the calculation.
 */

public class ReleaseDTO {
    private int startDay;
    private int duration;
    private int endDay;

    /**
     * Initialize the ReleaseDTO Object based on start day & duration.
     * End date will be calculated based on the start date and duration.
     * The start date included in the calculation
     * @param startDay
     * @param duration
     */
    public ReleaseDTO(int startDay, int duration) {
        this.startDay=startDay;
        this.duration=duration;
        this.endDay = startDay+duration-1; //calcuate the End day
    }

    //Create the final solution ReleaseDTO object with the fields of start day, duration & end day.
    public ReleaseDTO(int startDay, int duration,int endDay){
        this.startDay=startDay;
        this.duration=duration;
        this.endDay=endDay;
    }
    //get the start day
    public int getStartDay() {
        return startDay;
    }
    //set the start day and calculate based on duration. Start day is included to calculate the end day
    public void setStartDay(int startDay) {
        this.startDay = startDay;
        this.endDay=startDay+duration-1;
    }
    //get the duration
    public int getDuration() {
        return duration;
    }
    //get the set the duration and end day
    public void setDuration(int duration) {
        this.duration = duration;
        this.endDay=startDay+duration-1;
    }

    //get the End day
    public int getEndDay() {
        return endDay;
    }
    //set the start day
    public void setEndDay(int endDay) {
        this.endDay = endDay;
    }

    //retrun the String combining Start Day & End day
    @Override
    public String toString(){
        return startDay+ " " + endDay;
    }


}

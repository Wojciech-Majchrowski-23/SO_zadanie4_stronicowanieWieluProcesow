package PagesHandler;

public class Reference {

    int pageNumber;
    int processMembership;
    int arrivalTime;

    public Reference(int pageNumber, int processMembership, int arrivalTime) {
        this.pageNumber = pageNumber;
        this.processMembership = processMembership;
        this.arrivalTime = arrivalTime;
    }

    public String toString() {
        return String.valueOf(pageNumber);
    }

}

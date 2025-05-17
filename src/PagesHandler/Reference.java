package PagesHandler;

public class Reference {

    public int pageNumber;
    public int processMembership;
    public int arrivalTime;

    public Reference(int pageNumber, int processMembership, int arrivalTime) {
        this.pageNumber = pageNumber;
        this.processMembership = processMembership;
        this.arrivalTime = arrivalTime;
    }

    public String toString() {
        return "{ [ processID: " + String.valueOf(processMembership) + " ], [ pageNumber: " + String.valueOf(pageNumber) + " ], [ arrTime: " + String.valueOf(arrivalTime) + " ] }\n";
    }

}

package PagesManagement;

import PagesHandler.MyProcess;
import java.util.ArrayList;

public class ProportionalAllocationAlgorithm implements PagesManagement{

    @Override
    public int[] allocationAlgorithm(ArrayList<MyProcess> processes, int numberOfAvailableFrames) {

        int [] allocationArray = new int[processes.size()];

        int sumOfPages = 0;
        for(MyProcess process : processes) {
            sumOfPages+=process.numberOfPages;
            //System.out.println("Process " + process.id + ": " + process.numberOfPages);
        }

        int biggestNumberOfPages = 0;

        for(int i = 0; i < processes.size(); i++) {
            double part = (double) processes.get(i).numberOfPages /sumOfPages;
            if(processes.get(i).numberOfPages > biggestNumberOfPages) {
                biggestNumberOfPages = processes.get(i).numberOfPages;
            }
            allocationArray[i] = (int)(part * numberOfAvailableFrames);
        }

        int sumOfFrames = 0;
        for(int i = 0; i < allocationArray.length; i++) {
            sumOfFrames += allocationArray[i];
        }

        while(sumOfFrames < numberOfAvailableFrames) {
            for(int i = 0; i < allocationArray.length; i++) {
                if(processes.get(i).numberOfPages == biggestNumberOfPages) {
                    allocationArray[i]++;
                    sumOfFrames++;
                }
                if(sumOfFrames == numberOfAvailableFrames) {
                    break;
                }
            }
        }

//        for(int i = 0; i < allocationArray.length; i++) {
//            System.out.println("Process " + i + ": " + "allocated frames: " + allocationArray[i]);
//        }

        return allocationArray;
    }

    public void printName(){
        System.out.println("ProportionalAllocationAlgorithm");
    }

}

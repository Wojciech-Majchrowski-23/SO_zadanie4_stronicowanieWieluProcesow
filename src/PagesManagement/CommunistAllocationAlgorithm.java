package PagesManagement;

import PagesHandler.MyProcess;

import java.util.ArrayList;

public class CommunistAllocationAlgorithm implements PagesManagement{

    @Override
    public int[] allocationAlgorithm(ArrayList<MyProcess> processes, int numberOfAvailableFrames) {

        int [] allocationArray = new int[processes.size()];

        for(int i = 0; i < processes.size(); i++){
            allocationArray[i] = (int)(numberOfAvailableFrames / processes.size());
        }

        int leftover = numberOfAvailableFrames % processes.size();

        for(int i = 0; i < leftover; i++){
            allocationArray[i]++;
        }

        return allocationArray;

    }

    public void printName(){
        System.out.println("CommunistAllocationAlgorithm");
    }
}

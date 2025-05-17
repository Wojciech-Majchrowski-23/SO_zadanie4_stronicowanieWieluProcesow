package PagesManagement;

import PagesHandler.MyProcess;

import java.util.ArrayList;

public interface PagesManagement {

    default int [] allocationAlgorithm(ArrayList<MyProcess> processes, int numberOfAvailableFrames){
        return null;
    }

}

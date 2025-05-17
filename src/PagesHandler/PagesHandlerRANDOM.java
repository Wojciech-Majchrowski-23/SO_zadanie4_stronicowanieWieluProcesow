package PagesHandler;

import PagesManagement.PagesManagement;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class PagesHandlerRANDOM implements PagesHandler {

    final int framesSize;

    public PagesHandlerRANDOM(int framesSize) {
        this.framesSize = framesSize;
    }

    @Override
    public void processPaging(ArrayList<Reference> referencesChain, ArrayList<MyProcess> processes, PagesManagement pagesManagement) {

        int [] allocationArray = pagesManagement.allocationAlgorithm(processes, framesSize);

        ArrayList<ArrayList<Reference>> frames = new ArrayList<>();
        for (int i = 0; i < processes.size(); i++) {
            frames.add(new ArrayList<>());
        }

        int pageErrorsCounter = 0;

        int szamotanieCounter = 0;
        int localSzamotanieCounter = 0;
        int szamotanieBar = 5;
        int szamotanieCooldown = 2;
        boolean szamotanieFlag = true;

        for (Reference newPage : referencesChain) {
            //System.out.println(newPage);
            if (!contains(newPage, frames.get(newPage.processMembership))) {
                pageErrorsCounter++;

                if(szamotanieFlag){
                    localSzamotanieCounter++;
                    if(localSzamotanieCounter == szamotanieBar){
                        szamotanieCounter++;
                        szamotanieFlag = false;
                        localSzamotanieCounter = 0;
                    }
                }else{
                    szamotanieCooldown--;
                    if(szamotanieCooldown == 0){
                        szamotanieFlag = true;
                        szamotanieCooldown = 3;
                    }
                }

                if (frames.get(newPage.processMembership).size() == allocationArray[newPage.processMembership]) {
                    int randomIndex = (int) (Math.random() * frames.get(newPage.processMembership).size());
                    frames.get(newPage.processMembership).set(randomIndex, newPage);
                }
                else{
                    frames.get(newPage.processMembership).add(newPage);
                }
            }
            else{
                if(localSzamotanieCounter > 0){
                    localSzamotanieCounter--;
                }
            }
//            for(int j = 0; j < frames.size(); j++){
//                printArrayList(frames.get(j));
//            }
        }
        System.out.printf("%-15s %25s %25s %n", "[ RANDOM ]" ,"[ Number of page errors: " + pageErrorsCounter + " ]", "[ Number of szamotanie: " + szamotanieCounter + " ]");
    }

    public void printArrayList(ArrayList<Reference> arrayList) {

        System.out.print("[");
        for(int i = 0; i < arrayList.size(); i++) {
            if(arrayList.get(i) != arrayList.getLast()) {
                System.out.print(arrayList.get(i).pageNumber + ", ");
            }
            else {
                System.out.print(arrayList.get(i).pageNumber + "]");
            }
        }
        System.out.println();
    }

    public boolean contains(Reference page, ArrayList<Reference> pages) {

        for(Reference element : pages) {
            if(page.pageNumber == element.pageNumber) {
                return true;
            }
        }
        return false;
    }
}

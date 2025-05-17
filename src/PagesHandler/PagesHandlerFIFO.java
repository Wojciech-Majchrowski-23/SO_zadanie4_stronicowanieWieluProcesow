package PagesHandler;

import PagesManagement.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class PagesHandlerFIFO implements PagesHandler {

    final int framesSize;

    public PagesHandlerFIFO(int framesSize) {
        this.framesSize = framesSize;
    }

    @Override
    public void processPaging(ArrayList<Reference> referencesChain, ArrayList<MyProcess> processes, PagesManagement pagesManagement) {

        int [] allocationArray = pagesManagement.allocationAlgorithm(processes, framesSize);

        ArrayList<Queue<Reference>> pagesQueues = new ArrayList<>();
        for (int i = 0; i < processes.size(); i++) {
            pagesQueues.add(new LinkedList<>());
        }
        int pageErrorsCounter = 0;

        int szamotanieCounter = 0;
        int localSzamotanieCounter = 0;
        int szamotanieBar = 5;
        int szamotanieCooldown = 2;
        boolean szamotanieFlag = true;

        for (Reference newPage : referencesChain) {
            //System.out.println(newPage);
            if (!contains(newPage, pagesQueues.get(newPage.processMembership /* to jest id. Na podstawie id procesu sa wrzucane do roznych kolejek */))) {

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

                if (pagesQueues.get(newPage.processMembership).size() == allocationArray[newPage.processMembership]) {
                    pagesQueues.get(newPage.processMembership).poll();
                }
                pagesQueues.get(newPage.processMembership).add(newPage);
            }
            else{
                if(localSzamotanieCounter > 0){
                    localSzamotanieCounter--;
                }
            }
//            for(int i = 0; i < pagesQueues.size(); i++){
//                printQueue(pagesQueues.get(i));
//            }
        }
        System.out.printf("%-15s %25s %25s %n", "[ FIFO ]", "[ Number of page errors: " + pageErrorsCounter + " ]", "[ Number of szamotanie: " + szamotanieCounter + " ]");
    }

    public void printQueue(Queue<Reference> pagesQueue) {

        Reference lastPage = null;
        for(Reference page : pagesQueue) {
            lastPage = page;
        }

        System.out.print("[");
        for(Reference page : pagesQueue) {
            if(page != lastPage) {
                System.out.print(page.pageNumber + ", ");
            }
            else{
                System.out.print(page.pageNumber + "]");
            }
        }
        System.out.println();
    }

    public boolean contains(Reference page, Queue<Reference> pagesQueue) {

        for(Reference element : pagesQueue) {
            if(page.pageNumber == element.pageNumber) {
                return true;
            }
        }
        return false;
    }
}

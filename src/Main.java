import PagesHandler.*;
import PagesManagement.CommunistAllocationAlgorithm;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        ProcessGenerator processGenerator = new ProcessGenerator();
        ArrayList<MyProcess> processes = processGenerator.generate();

        for(MyProcess process : processes) {
            System.out.print(process.toString());
        }
        System.out.println();

        ArrayList<Reference> GLOBAL_REFERENCES_CHAIN = createGlobalReferencesChain(processes);
//        for(Reference reference : GLOBAL_REFERENCES_CHAIN) {
//            System.out.println(reference.toString());
//        }

        int numberOfAvailableFrames = (4 * processGenerator.NUMBER_OF_PROCESSES) + 1;

        PagesHandler pagesHandler1 = new PagesHandlerFIFO(numberOfAvailableFrames);
        pagesHandler1.processPaging(GLOBAL_REFERENCES_CHAIN, processes, new CommunistAllocationAlgorithm());

        PagesHandler pagesHandler2 = new PagesHandlerRANDOM(numberOfAvailableFrames);
        pagesHandler2.processPaging(GLOBAL_REFERENCES_CHAIN, processes, new CommunistAllocationAlgorithm());

        PagesHandler pagesHandler3 = new PagesHandlerOPT(numberOfAvailableFrames);
        pagesHandler3.processPaging(GLOBAL_REFERENCES_CHAIN, processes, new CommunistAllocationAlgorithm());

        PagesHandler pagesHandler4 = new PagesHandlerLRU(numberOfAvailableFrames);
        pagesHandler4.processPaging(GLOBAL_REFERENCES_CHAIN, processes, new CommunistAllocationAlgorithm());

        PagesHandler pagesHandler5 = new PagesHandlerALRU(numberOfAvailableFrames);
        pagesHandler5.processPaging(GLOBAL_REFERENCES_CHAIN, processes, new CommunistAllocationAlgorithm());
    }

    public static int lastReferenceArrivalTime(ArrayList<MyProcess> processes){

        int lastReferenceArrivalTime = processes.getFirst().referencesChain.getLast().arrivalTime;

        for (int i = 1; i < processes.size(); i++){
            if(processes.get(i).referencesChain.getLast().arrivalTime > lastReferenceArrivalTime){
                lastReferenceArrivalTime = processes.get(i).referencesChain.getLast().arrivalTime;
            }
        }

        return lastReferenceArrivalTime;
    }

    public static ArrayList<Reference> createGlobalReferencesChain(ArrayList<MyProcess> processes){

        ArrayList<Reference> GLOBAL_REFERENCES_CHAIN = new ArrayList<Reference>();
        int arrivalTimeIncrease = 0;
        int lastReferenceArrivalTime = lastReferenceArrivalTime(processes);

        while(arrivalTimeIncrease < lastReferenceArrivalTime){

            for(int i = 0; i < processes.size(); i++){
                for(int j = 0; j < processes.get(i).referencesChain.size(); j++){
                    if(processes.get(i).referencesChain.get(j).arrivalTime == arrivalTimeIncrease){
                        GLOBAL_REFERENCES_CHAIN.add(processes.get(i).referencesChain.get(j));
                    }
                    if(processes.get(i).referencesChain.get(j).arrivalTime > arrivalTimeIncrease) break;
                }
            }

            arrivalTimeIncrease++;
        }
        return GLOBAL_REFERENCES_CHAIN;
    }
}

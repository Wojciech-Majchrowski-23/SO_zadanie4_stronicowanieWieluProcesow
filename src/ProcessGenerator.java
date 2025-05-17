import PagesHandler.MyProcess;
import PagesHandler.Reference;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class ProcessGenerator {

    public final static int NUMBER_OF_PROCESSES = 3;
    public final static int MAXIMUM_PAGES_NUMBER_FOR_SINGE_PROCESS = 10;
    public final static int MINIMUM_PAGES_NUMBER_FOR_SINGE_PROCESS = 5;
    public final static int REFERENCES_CHAIN_MAX_SIZE_FOR_SINGE_PROCESS = 10000 / NUMBER_OF_PROCESSES;
    public final static int MAXIMAL_TIME_STAMP_BETWEEN_REFERENCES_ARRIVAL = 3;
    public final static int GENERATION_CHOICE = 1;

    public ProcessGenerator() {}

    public ArrayList<MyProcess> generate() {

        ArrayList<MyProcess>processes = new ArrayList<>();


        for (int i = 0; i < NUMBER_OF_PROCESSES; i++) {

            Random rand = new Random();
            int numberOfPages = rand.nextInt(MAXIMUM_PAGES_NUMBER_FOR_SINGE_PROCESS - MINIMUM_PAGES_NUMBER_FOR_SINGE_PROCESS + 1) + MINIMUM_PAGES_NUMBER_FOR_SINGE_PROCESS;
            //int numberOfPages = 8;
            MyProcess process = new MyProcess(i, numberOfPages);

            //kazdy proces ma unikatowe strony uzaleznione od ID
            ArrayList<Integer>pages = new ArrayList<>();
            for(int j = 0; j < process.numberOfPages; j++) {
                pages.add(process.id * 10 + j);
            }

            process.setPages(pages);
            process.setReferencesChain(generateReferencesChain(GENERATION_CHOICE, process, REFERENCES_CHAIN_MAX_SIZE_FOR_SINGE_PROCESS));

            processes.add(process);
        }
        return processes;
    }

    public static ArrayList<Reference> generateReferencesChain(int choice, MyProcess process, int REFERENCES_CHAIN_MAX_SIZE_FOR_SINGE_PROCESS) {

        ArrayList<Reference> referencesChain = new ArrayList<>();

        int [] randomPages = new int[(int)(process.numberOfPages/3)];
        int every_thisNumberOfReference_localSubstring = REFERENCES_CHAIN_MAX_SIZE_FOR_SINGE_PROCESS / 5;
        int sizeOfSingleLocalSubstring = every_thisNumberOfReference_localSubstring / 2;
        int arrivalTime = process.id;

        boolean flag = false;
        int counter = 0;

        for(int i = 0; i < ProcessGenerator.REFERENCES_CHAIN_MAX_SIZE_FOR_SINGE_PROCESS; i++) {

            int randomPageIndex = (int)(Math.random() * process.numberOfPages);

            if(choice == 0) {
                referencesChain.add(new Reference(process.pages.get(randomPageIndex), process.id, arrivalTime+=(int)(Math.random()*MAXIMAL_TIME_STAMP_BETWEEN_REFERENCES_ARRIVAL)));
            }
            else{
                //tablica z 3 losowymi indeksami sposrod wszystkich
                //przez pewna czesc ciagu odwolan beda wystepowac tylko te 3 strony

                if(i % every_thisNumberOfReference_localSubstring == 0){
                    randomPages = generateRandomIndexesfPages(process);
                    flag = true;
                }

                if(flag && counter <= sizeOfSingleLocalSubstring ){

                    //wrzucam sobie randomowo jakas inna liczbe niz z tego podzbioru
                    if(counter % 7 != 0) {
                        referencesChain.add(new Reference(process.pages.get(randomPages[(int) (Math.random() * randomPages.length)]), process.id, arrivalTime+=(int)(Math.random()*MAXIMAL_TIME_STAMP_BETWEEN_REFERENCES_ARRIVAL)));
                    }
                    else{
                        referencesChain.add(new Reference(process.pages.get(randomPageIndex), process.id, arrivalTime+=(int)(Math.random()*MAXIMAL_TIME_STAMP_BETWEEN_REFERENCES_ARRIVAL)));
                    }
                    counter++;
                }
                else{
                    referencesChain.add(new Reference(process.pages.get(randomPageIndex), process.id, arrivalTime+=(int)(Math.random()*MAXIMAL_TIME_STAMP_BETWEEN_REFERENCES_ARRIVAL)));
                    counter = 0;
                    flag = false;
                }
            }
        }
        return referencesChain;
    }

    public static int [] generateRandomIndexesfPages(MyProcess process) {
        int [] randomPages = new int[(int)(process.numberOfPages / 2.3)];

        HashSet<Integer>validPages = new HashSet<>();

        for(int i = 0; i < randomPages.length; i++) {
            randomPages[i] = (int)(Math.random() * randomPages.length);
            validPages.add(randomPages[i]);
        }


        if(validPages.size() != randomPages.length) {
            generateRandomIndexesfPages(process);
        }

        return randomPages;
    }

}

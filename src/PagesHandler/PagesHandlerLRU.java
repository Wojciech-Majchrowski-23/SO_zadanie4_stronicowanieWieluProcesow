package PagesHandler;

import java.util.ArrayList;

public class PagesHandlerLRU implements PagesHandler {

    final int framesSize;

    public PagesHandlerLRU(int framesSize) {
        this.framesSize = framesSize;
    }

    @Override
    public void processPaging(ArrayList<Reference> referencesChain) {

        ArrayList<Reference> frames = new ArrayList<>();

        int pageErrorsCounter = 0;

        int szamotanieCounter = 0;
        int localSzamotanieCounter = 0;
        int szamotanieBar = 5;
        int szamotanieCooldown = 2;
        boolean szamotanieFlag = true;

        for (int i = 0; i < referencesChain.size(); i++) {
            //System.out.println(referencesChain.get(i));
            if (!contains(referencesChain.get(i), frames)) {

                pageErrorsCounter++;

                if (szamotanieFlag) {
                    localSzamotanieCounter++;
                    if (localSzamotanieCounter == szamotanieBar) {
                        szamotanieCounter++;
                        szamotanieFlag = false;
                        localSzamotanieCounter = 0;
                    }
                } else {
                    szamotanieCooldown--;
                    if (szamotanieCooldown == 0) {
                        szamotanieFlag = true;
                        szamotanieCooldown = 3;
                    }
                }

                if (frames.size() == framesSize) {
                    int bestIndex = findBestIndex(i, referencesChain, frames);
                    frames.set(bestIndex, referencesChain.get(i));
                } else {
                    frames.add(referencesChain.get(i));
                }
            }
            else{
                if(localSzamotanieCounter > 0){
                    localSzamotanieCounter--;
                }
            }
            //printArrayList(frames);
        }
        System.out.printf("%-15s %25s %25s %n", "[ LRU ]", "[ Number of page errors: " + pageErrorsCounter + " ]", "[ Number of szamotanie: " + szamotanieCounter + " ]");
    }

    public int findBestIndex(int startingIndex, ArrayList<Reference> referencesChain, ArrayList<Reference> frames) {

        int [] arrayChecker = new int[frames.size()];

        for(int j = 0; j < frames.size(); j++) {
            for(int i = startingIndex - 1; i >= 0; i--) {
                if(referencesChain.get(i).pageNumber == (frames.get(j).pageNumber)) {
                    arrayChecker[j] = startingIndex - i;
                    break;
                }
            }
        }

        int bestIndex = 0;

        for(int i = 1; i < arrayChecker.length; i++) {
            if(arrayChecker[i] > arrayChecker[bestIndex]) {
                bestIndex = i;
            }
        }

        return bestIndex;
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

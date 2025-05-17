package PagesHandler;

import java.util.ArrayList;

public class PagesHandlerALRU implements PagesHandler {

    final int framesSize;

    public PagesHandlerALRU(int framesSize) {
        this.framesSize = framesSize;
    }

    @Override
    public void processPaging(ArrayList<Reference> referencesChain) {

        ArrayList<Pair> frames = new ArrayList<>();

        int pageErrorsCounter = 0;

        int szamotanieCounter = 0;
        int localSzamotanieCounter = 0;
        int szamotanieBar = 5;
        int szamotanieCooldown = 2;
        boolean szamotanieFlag = true;

        for (int i = 0; i < referencesChain.size(); i++) {
            Reference reference = referencesChain.get(i);
            //System.out.println(reference);
            if (!contains(reference, frames)) {

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
                    int index = findGoodIndex(reference, frames);
                    frames.remove(index);
                    frames.add(new Pair(reference, true));
                } else {
                    frames.add(new Pair(reference, true));
                }
            }
            else {
                Pair p = findThatPage(frames, reference);
                if (p != null) {
                    p.chanceBit = true;
                }

                if(localSzamotanieCounter > 0){
                    localSzamotanieCounter--;
                }
            }
            //printArrayListOfPairs(frames);
        }
        System.out.printf("%-15s %25s %25s %n", "[ aLRU ]", "[ Number of page errors: " + pageErrorsCounter + " ]", "[ Number of szamotanie: " + szamotanieCounter + " ]");
    }

    public int findGoodIndex(Reference waitingReference, ArrayList<Pair> frames) {

        int index = -1;

        while(index < 0){
            for(int i = 0; i < frames.size(); i++){
                if(!frames.get(i).chanceBit){
                    index = i;
                    break;
                }else{
                    frames.get(i).chanceBit = false;
                }
            }
        }
        return index;
    }

    public boolean contains(Reference reference, ArrayList<Pair> frames) {

        for(int i = 0; i < frames.size(); i++){
            if(frames.get(i).reference.pageNumber == reference.pageNumber){
                return true;
            }
        }
        return false;
    }

    public Pair findThatPage(ArrayList<Pair> frames, Reference reference) {
        for(Pair p : frames){
            if(p.reference.pageNumber == reference.pageNumber){
                return p;
            }
        }
        return null;
    }

    public void printArrayListOfPairs(ArrayList<Pair> arrayListofPairs) {

        System.out.print("[");
        for(int i = 0; i < arrayListofPairs.size(); i++) {
            if(arrayListofPairs.get(i).reference != arrayListofPairs.getLast().reference) {
                System.out.print(arrayListofPairs.get(i).reference.pageNumber + ": " + arrayListofPairs.get(i).chanceBit + ", ");
            }
            else {
                System.out.print(arrayListofPairs.get(i).reference.pageNumber + ": " + arrayListofPairs.get(i).chanceBit + "]");
            }
        }
        System.out.println();
    }
}

class Pair {
    Reference reference;
    boolean chanceBit;

    public Pair(Reference reference, boolean chanceBit) {
        this.reference = reference;
        this.chanceBit = chanceBit;
    }
}

import PagesHandler.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        ProcessGenerator processGenerator = new ProcessGenerator();
        ArrayList<Process> processes = processGenerator.generate();

        System.out.println(processes.getFirst().toString());
        System.out.println();

        int numberOfAvailableFrames = 5;

        PagesHandler pagesHandler1 = new PagesHandlerFIFO(numberOfAvailableFrames);
        pagesHandler1.processPaging(processes.getFirst().referencesChain);

        PagesHandler pagesHandler2 = new PagesHandlerRANDOM(numberOfAvailableFrames);
        pagesHandler2.processPaging(processes.getFirst().referencesChain);

        PagesHandler pagesHandler3 = new PagesHandlerOPT(numberOfAvailableFrames);
        pagesHandler3.processPaging(processes.getFirst().referencesChain);

        PagesHandler pagesHandler4 = new PagesHandlerLRU(numberOfAvailableFrames);
        pagesHandler4.processPaging(processes.getFirst().referencesChain);

        PagesHandler pagesHandler5 = new PagesHandlerALRU(numberOfAvailableFrames);
        pagesHandler5.processPaging(processes.getFirst().referencesChain);
    }
}

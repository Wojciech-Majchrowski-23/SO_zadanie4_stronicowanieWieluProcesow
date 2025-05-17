package PagesHandler;

import PagesManagement.PagesManagement;

import java.util.ArrayList;

public interface PagesHandler {

    void processPaging(ArrayList<Reference>referencesChain, ArrayList<MyProcess> processes, PagesManagement pagesManagement);

}

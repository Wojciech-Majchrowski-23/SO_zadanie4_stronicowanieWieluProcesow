import PagesHandler.Reference;

import java.util.ArrayList;

public class Process {

    int id;
    int numberOfPages;
    ArrayList<Integer> pages;
    ArrayList<Reference> referencesChain;

    public Process(int id, int numberOfPages) {
        this.id = id;
        this.numberOfPages = numberOfPages;
    }

    public void setPages(ArrayList<Integer> pages) {
        this.pages = pages;
    }
    public void setReferencesChain(ArrayList<Reference> referencesChain) {
        this.referencesChain = referencesChain;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append("\n");

        sb.append("Pages: ");
        if (pages != null) {
            sb.append(pages.toString());
        } else {
            sb.append("null");
        }
        sb.append("\n");

        sb.append("References Chain: ");
        if (referencesChain != null) {
            sb.append(referencesChain.toString());
        } else {
            sb.append("null");
        }

        return sb.toString();
    }


}

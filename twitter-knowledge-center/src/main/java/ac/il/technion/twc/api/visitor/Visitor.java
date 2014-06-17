package ac.il.technion.twc.api.visitor;


import ac.il.technion.twc.api.TwitterQueryAPI;

/**
 * Created by Matan on 5/31/14.
 */
public interface Visitor {

    /**
     * Check out @Visitable accept javadoc.
     */
    void visit(TwitterQueryAPI twitter);

    /**
     * Clears the data that the current visitor holds (i.e data bases)
     */
    void clearData();
}

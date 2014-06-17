package ac.il.technion.twc.api.visitor;


/**
 * Created by Matan on 5/31/14.
 */
public interface Visitable {

    /**
     * @param visitor - Google this shit. Srsly.
     *                Fine.... Here you go.
     *                <url>http://stackoverflow.com/questions/2604169/could-someone-in-simple-terms-explain-to-me-the-visitor-patterns-purpose-with-e</url>
     */
    void accept(Visitor visitor);

}

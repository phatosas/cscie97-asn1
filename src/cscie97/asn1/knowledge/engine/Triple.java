package cscie97.asn1.knowledge.engine;

/**
 * Created with IntelliJ IDEA.
 * User: dkilleffer
 * Date: 9/8/13
 * Time: 11:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class Triple {


    /**
     *
     * Because of the in­memory nature of this implementation, to optimize memory usage, there should only
     * be one instance for each unique Triple, Node and Predicate object. This follows the FlyWeight
     * design pattern (see http://en.wikipedia.org/wiki/Flyweight_pattern).
     *
     */


    /**
     * Private non mutable association to the associated Subject instance.
     * May be "?" in the case where the Triple is intended to be used for querying for other triples.
     */
    private Node subject;

    /**
     * Retrieves the Subject Node of the Triple, which is the 1st and beginning string portion of the identifier.
     *
     * @return  the main Node subject of the Triple
     */
    public Node getSubject() {
        return subject;
    }


    /**
     * Private non mutable association to the associated Predicate instance.
     * May be "?" in the case where the Triple is intended to be used for querying for other triples.
     */
    private Predicate predicate;


    /**
     * Retrieves the Predicate of the Triple, which is the 2nd and middle string portion of the identifier.
     *
     * @return  the main Predicate of the Triple
     */
    public Predicate getPredicate() {
        return predicate;
    }

    /**
     * Private non mutable association to the associated Object instance.
     * May be "?" in the case where the Triple is intended to be used for querying for other triples.
     */
    private Node object;

    /**
     * Retrieve the object Node (e.g., "object"), which is the 3rd and final string portion of the identifier.
     *
     * @return  the object Node of the Triple
     */
    public Node getObject() {
        return object;
    }

    /**
     * Private unique non mutable identifier for the Triple. Of the form:
     * subject.identifier + “ “ + predicate.identifer + “ “ + object.identifier.
     */
    private String identifier;

    /**
     * Returns the Triple identifier, which is of the form:
     * subject.identifier + “ “ + predicate.identifer + “ “ + object.identifier.
     *
     * @return the string identifier for the Triple
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Class constructor.  If any passed arguments are null, assumes that the corresponding argument should be
     * equivalent to "?", meaning the wildcard character, and Triples containing question marks in their identifier
     * can be assumed to be "query" Triples.
     *
     * @param subject     string identifying the Subject node; may be "?" if the subject node is intended
     *                    to be a wildcard (for querying)
     * @param predicate   string identifying the Predicate node; may be "?" if the predicate is intended
     *                    to be a wildcard (for querying)
     * @param object      string identifying the Object node; may be "?" if the object node is intended
     *                    to be a wildcard (for querying)
     */
    public Triple(Node subject, Predicate predicate, Node object) {
        this.subject = (subject == null) ? new Node("?") : subject;
        this.predicate = (predicate == null) ? new Predicate("?") : predicate;
        this.object = (object == null) ? new Node("?") : object;
        this.identifier = this.subject.getIdentifier() + " " + this.predicate.getIdentifier() + " " + this.object.getIdentifier() + ".";
    }
}
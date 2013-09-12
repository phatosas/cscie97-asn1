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
     */
    private Node subject;

    /**
     * Retrieves the Subject Node
     *
     * @return  the main Node subject of the Triple
     */
    public Node getSubject() {
        return subject;
    }


    /**
     * Private non mutable association to the associated Predicate instance.
     */
    private Predicate predicate;


    /**
     * Retrieves the Predicate of the Triple
     *
     * @return  the main Predicate of the Triple
     */
    public Predicate getPredicate() {
        return predicate;
    }


    /**
     * Private non mutable association to the associated Object instance.
     */
    private Node object;

    /**
     * Retrieve the object Node (e.g., "object")
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


    public Triple(Node subject, Predicate predicate, Node object) {
        this.subject = subject;
        this.predicate = predicate;
        this.object = object;
        //this.identifier = subject.getIdentifier().toLowerCase() + " " + predicate.getIdentifier().toLowerCase() + " " + object.getIdentifier().toLowerCase() + "";
        this.identifier = subject.getIdentifier() + " " + predicate.getIdentifier() + " " + object.getIdentifier() + ".";
    }



}

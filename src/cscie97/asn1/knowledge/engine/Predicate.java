package cscie97.asn1.knowledge.engine;

/**
 * Created with IntelliJ IDEA.
 * User: dkilleffer
 * Date: 9/8/13
 * Time: 11:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class Predicate {

    /**
     *
     * Because of the in­memory nature of this implementation, to optimize memory usage, there should only
     * be one instance for each unique Triple, Node and Predicate object. This follows the FlyWeight
     * design pattern (see http://en.wikipedia.org/wiki/Flyweight_pattern).
     *
     */


    /**
     * Private unique (use Flyweight pattern) non mutable identifier for the Predicate.
     * Predicate identifiers are case insensitive.
     */
    private String identifier;

    /**
     * Returns the Predicate identifier.
     *
     * @return
     */
    public String getIdentifier() {
        return identifier;
    }

    public Predicate(String identifier) {
        this.identifier = identifier;
    }
}
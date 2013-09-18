package cscie97.asn1.knowledge.engine;

/**
 * Represents a Predicate, which is part of a Triple.  Each Triple only has one Predicate, which is the
 * "middle" part of the Triple identifier. There should only be 1 unique instance of each Predicate, and this
 * uniqueness is enforced by the KnowledgeGraph class.
 *
 * @author David Killeffer <rayden7@gmail.com>
 * @version 1.0
 * @see Triple
 */
public class Predicate {

    /**
     * Private unique (use Flyweight pattern) non mutable identifier for the Predicate.
     * Predicate identifiers are case insensitive.
     */
    private String identifier;

    /**
     * Returns the Predicate identifier as a string.
     *
     * @return  returns the Predicate identifier as a string
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Class constructor.  Sets the string identifier for the Node.
     *
     * @param identifier  the string that should uniquely identify this Node from all others;
     *                    uniqueness of Nodes is enforced by the KnowledgeGraph class
     */
    public Predicate(String identifier) {
        this.identifier = identifier;
    }
}
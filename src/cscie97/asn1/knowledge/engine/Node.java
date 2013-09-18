package cscie97.asn1.knowledge.engine;

/**
 * Represents a Node, which is part of a Triple.  A Node can represent the "subject" of a Triple,
 * or the "object".  There should only be 1 unique instance of each Node, and this uniqueness is enforced
 * by the KnowledgeGraph class.
 *
 * @author David Killeffer <rayden7@gmail.com>
 * @version 1.0
 * @see Triple
 */
public class Node {

    /**
     * Private unique (use Flyweight pattern) non mutable identifier for the Node.
     * Node identifiers are case insensitive.
     */
    private String identifier;

    /**
     * Returns the Node string identifier.
     *
     * @return  the string identifier for this Node
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
    public Node(String identifier) {
        this.identifier = identifier;
    }

}

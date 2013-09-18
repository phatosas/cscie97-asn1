package cscie97.asn1.knowledge.engine;

/**
 * Created with IntelliJ IDEA.
 * User: dkilleffer
 * Date: 9/8/13
 * Time: 11:09 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * A class to represent a Node, which is part of a Triple.  A Node can represent the "subject" of a Triple,
 * or the "object".
 *
 * @author David Killeffer <rayden7@gmail.com>
 * @version 1.0
 * @see Triple
 *
 */
public class Node {

    /**
     *
     * Because of the in-memory nature of this implementation, to optimize memory usage, there should only
     * be one instance for each unique Triple, Node and Predicate object. This follows the FlyWeight
     * design pattern (see http://en.wikipedia.org/wiki/Flyweight_pattern).
     *
     */

    /**
     * Private unique (use Flyweight pattern) non mutable identifier for the Node.
     * Node identifiers are case insensitive.
     *
     * As Nodes are intended only to be a part of a Triple,
     *
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
     * Class constructor.  Sets the string identifier.
     *
     * @param identifier  the string that should uniquely identify this Node from all others
     */
    public Node(String identifier) {
        this.identifier = identifier;
    }

}

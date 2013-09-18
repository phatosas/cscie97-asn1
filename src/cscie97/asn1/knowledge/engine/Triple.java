package cscie97.asn1.knowledge.engine;

/**
 * Represents a Triple, which is comprised of a "subject" (a Node), a Predicate, and an "object" (another Node).
 * Triples are used to explain how "subjects" and "objects" relate to each other by way of a Predicate.
 * Each Triple is stored in the KnowledgeGraph, and users may query the KnowledgeGraph for matching
 * Triples based on subject, predicate, and object parameters.  There should only be 1 unique instance of each
 * Triple, and this uniqueness is enforced by the KnowledgeGraph class.
 *
 * @author David Killeffer <rayden7@gmail.com>
 * @version 1.0
 * @see Triple
 */
public class Triple {

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
     * subject.identifier + " " + predicate.identifer + " " + object.identifier.
     */
    private String identifier;

    /**
     * Returns the Triple identifier, which is of the form:
     * subject.identifier + " " + predicate.identifer + " " + object.identifier.
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
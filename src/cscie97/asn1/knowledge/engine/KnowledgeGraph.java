package cscie97.asn1.knowledge.engine;

import java.util.List;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: dkilleffer
 * Date: 9/8/13
 * Time: 11:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class KnowledgeGraph {


    // borrowed Singleton implementation from Wikipedia: http://en.wikipedia.org/wiki/Singleton_pattern

    private static KnowledgeGraph instance = null;

    private KnowledgeGraph() {

        nodeMap = new HashMap<String,Node>();
        predicateMap = new HashMap<String,Predicate>();
        tripleMap = new HashMap<String,Triple>();
        queryMapSet = new HashMap<String,Set<Triple>>();

    }

    /**
     * This method returns a reference to the single static instance of the KnowledgeGraph.
     *
     * @return singleton instance of KnowledgeGraph
     */
    public static synchronized KnowledgeGraph getInstance() {
        if (instance == null) {
            instance = new KnowledgeGraph();
        }
        return instance;
    }


    /**
     * Private association for maintaining the active set of Nodes (i.e. Subjects and/or Objects).
     * Map key is the node identifier and value is the associated Node. Node identifiers
     * are case insensitive.
     */
    //private Map<String,Node> nodeMap;
    private Map<String,Node> nodeMap;

    /**
     * Private association for maintaining the active set of Predicates. Map key is the predicate
     * identifier and value is the associated Predicate. Predicate identifiers are case insensitive.
     */
    private Map<String,Predicate> predicateMap;

    /**
     * Private association for maintaining the active set of Triples. Map key is the Triple
     * identifier and value is the associated Triple.
     */
    private Map<String,Triple> tripleMap;

    /**
     * Private association for maintaining a fast query lookup map. Map key is the query
     * string (e.g. “Bill ? ?”), and value is a Set of matching Triples.
     */
    private Map<String,Set<Triple>> queryMapSet;




    /**
     * Public method for adding a list of Triples to the KnowledgeGraph.
     * The following associations must be updated: nodeMap, tripleMap, queryMapSet, predicateMap to reflect the
     * added Triple. There should be one Triple instance per unique Subject, Predicate,
     * Object combination, so that Triples are not duplicated.
     *
     * @param tripleList
     */
    public void importTriples(List<Triple> tripleList) { }

    /**
     * Use the queryMapSet to determine the Triples that match the given Query. If none are found return an empty Set.
     *
     * @param query
     * @return
     */
    public Set<Triple> executeQuery(Triple query) {
        return null;
    }


    /**
     * Return a Node Instance for the given node identifier. Use the nodeMap to look up the Node.
     * If the Node does not exist, create it and add it to the nodeMap. Node names are case insensitive.
     *
     * @param identifier
     * @return
     */
    public Node getNode(String identifier) {

        // does this identifier already exist in the KnowledgeGraph?
        if (nodeMap.keySet().contains(identifier.toLowerCase())) {
            return nodeMap.get(identifier.toLowerCase());
        }

        Node node = new Node(identifier);
        nodeMap.put(identifier.toLowerCase(), node);

        return node;
    }

    /**
     * Return a Predicate instance for the given identifier. Use the predicateMap to lookup the Predicate.
     * If the Predicate does not exist, create it and add it to the predicateMap. Predicate names
     * are case insensitive.
     *
     * @param identifier
     * @return
     */
    public Predicate getPredicate(String identifier) {

        // does this identifier already exist in the KnowledgeGraph?
        if (predicateMap.keySet().contains(identifier.toLowerCase())) {
            return predicateMap.get(identifier.toLowerCase());
        }

        Predicate predicate = new Predicate(identifier);
        predicateMap.put(identifier.toLowerCase(), predicate);

        return predicate;

        //return null;
    }

    /**
     * Return the Triple instance for the given Object, Predicate and Subject. Use the tripleMap to
     * lookup the Triple. If the Triple does not exist, create it and add it to the tripleMap and
     * update the queryMapSet.
     *
     * @param subject
     * @param predicate
     * @param object
     * @return
     */
    public Triple getTriple(Node subject, Predicate predicate, Node object) {

        String tripleIdentifier = subject.getIdentifier().toLowerCase() + " " +
                                  predicate.getIdentifier().toLowerCase() + " " +
                                  object.getIdentifier().toLowerCase() + ".";

        if (tripleMap.keySet().contains(tripleIdentifier)) {
            return tripleMap.get(tripleIdentifier);
        }

        Triple triple = new Triple(subject, predicate, object);
        tripleMap.put(tripleIdentifier, triple);

        return triple;

        //return null;
    }
}

package cscie97.asn1.knowledge.engine;

import java.util.*;

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
    public void importTriples(List<Triple> tripleList) {
        for (Triple triple : tripleList) {
            // the triple is new and doesn't exist yet, so add all required references as necessary
            if ( tripleMap.get(triple.getIdentifier().toLowerCase()) == null && triple.getIdentifier() != null) {

                if (nodeMap.get(triple.getSubject().getIdentifier().toLowerCase()) == null) {
                    nodeMap.put(triple.getSubject().getIdentifier().toLowerCase(), triple.getSubject());
                }

                if (nodeMap.get(triple.getObject().getIdentifier().toLowerCase()) == null) {
                    nodeMap.put(triple.getObject().getIdentifier().toLowerCase(), triple.getObject());
                }

                if (predicateMap.get(triple.getPredicate().getIdentifier().toLowerCase()) == null) {
                    predicateMap.put(triple.getPredicate().getIdentifier().toLowerCase(), triple.getPredicate());
                }

                tripleMap.put(triple.getIdentifier().toLowerCase(), triple);

                /* update queryMapSet - this pre-computes the possible queries that this Triple should match;
                there should in general be 8 permutations of queries that will match the given Triple.
                For example, if the triple is:

                "Joe has_friend Bill", then the various 8 permutations of query strings that should match this triple are:

                1) Joe has_friend Bill
                2) Joe has_friend ?
                3) Joe ? Bill
                4) Joe ? ?
                5) ? has_friend Bill
                6) ? has_friend ?
                7) ? ? Bill
                8) ? ? ?

                */

                HashSet<Triple> curTripleAsSet = new HashSet<Triple>(Arrays.asList(triple));

                // save a list of all the query strings that should potentially match this triple,
                // and update the queryMapSet with the current triple for each permutation so that subsequent
                // queries will quickly match this triple
                List<String> queryStringMatches = new ArrayList<String>(Arrays.asList(
                        // query format: 1) Joe has_friend Bill
                        triple.getSubject().getIdentifier().toLowerCase() + " " + triple.getPredicate().getIdentifier().toLowerCase() + " " + triple.getObject().getIdentifier().toLowerCase(),
                        // query format: 2) Joe has_friend ?
                        triple.getSubject().getIdentifier().toLowerCase() + " " + triple.getPredicate().getIdentifier().toLowerCase() + " ?",
                        // query format: 3) Joe ? Bill
                        triple.getSubject().getIdentifier().toLowerCase() + " ? " + triple.getObject().getIdentifier().toLowerCase(),
                        // query format: 4) Joe ? ?
                        triple.getSubject().getIdentifier().toLowerCase() + " ? ?",
                        // query format: 5) ? has_friend Bill
                        "? " + triple.getPredicate().getIdentifier().toLowerCase() + " " + triple.getObject().getIdentifier().toLowerCase(),
                        // query format: 6) ? has_friend ?
                        "? " + triple.getPredicate().getIdentifier().toLowerCase() + " ?",
                        // query format: 7) ? ? Bill
                        "? ? " + triple.getObject().getIdentifier().toLowerCase(),
                        // query format: 8) ? ? ?
                        "? ? ?"
                ));

                for (String queryString : queryStringMatches) {
                    Set<Triple> queryStringSetMatchingTriples = queryMapSet.get(queryString);
                    if (queryStringSetMatchingTriples == null) {
                        queryMapSet.put(queryString, new HashSet<Triple>(Arrays.asList(triple)) );
                    }
                    else {
                        queryStringSetMatchingTriples.add(triple);
                    }
                }


                /*
                String q1 = triple.getSubject().getIdentifier().toLowerCase() + " " + triple.getPredicate().getIdentifier().toLowerCase() + " " + triple.getObject().getIdentifier().toLowerCase();
                if (queryMapSet.get(q1) == null) { queryMapSet.put(q1, curTripleAsSet); }

                String q2 = triple.getSubject().getIdentifier().toLowerCase() + " " + triple.getPredicate().getIdentifier().toLowerCase() + " ?";
                if (queryMapSet.get(q2) == null) { queryMapSet.put(q2, curTripleAsSet); }

                String q3 = triple.getSubject().getIdentifier().toLowerCase() + " ? " + triple.getObject().getIdentifier().toLowerCase();
                if (queryMapSet.get(q3) == null) { queryMapSet.put(q3, curTripleAsSet); }

                String q4 = triple.getSubject().getIdentifier().toLowerCase() + " ? ?";
                if (queryMapSet.get(q4) == null) { queryMapSet.put(q4, curTripleAsSet); }

                String q5 = "? " + triple.getPredicate().getIdentifier().toLowerCase() + " " + triple.getObject().getIdentifier().toLowerCase();
                if (queryMapSet.get(q5) == null) { queryMapSet.put(q5, curTripleAsSet); }

                String q6 = "? " + triple.getPredicate().getIdentifier().toLowerCase() + " ?";
                if (queryMapSet.get(q6) == null) { queryMapSet.put(q6, curTripleAsSet); }

                String q7 = "? ? " + triple.getObject().getIdentifier().toLowerCase();
                if (queryMapSet.get(q7) == null) { queryMapSet.put(q7, curTripleAsSet); }

                String q8 = "? ? ?";

                Set<Triple> q8Map = queryMapSet.get(q8);
                if (q8Map == null) { queryMapSet.put(q8, new HashSet<Triple>(Arrays.asList(triple)) ); }
                else { q8Map.add(triple); }
                */
            }
        }
    }

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

        // TODO: also update queryMapSet

        return triple;

        //return null;
    }
}

package cscie97.asn1.knowledge.engine;

import java.util.HashMap;
import java.util.Set;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Arrays;

/**
 * An in-memory database of Triples, which allows for the importation of new Triples into the database as well as
 * queries on the database and returns matching Triples.  The KnowledgeGraph is a Singleton, so there is only ever
 * one instance to use.  Additionally, the KnowledgeGraph uses the Flyweight pattern to ensure that there is only ever
 * one unique instance of each Node, Predicate, or Triple so as to not needlessly duplicate things.
 *
 * @author David Killeffer <rayden7@gmail.com>
 * @version 1.0
 * @see Triple
 * @see QueryEngine
 * @see Importer
 */
public class KnowledgeGraph {

    // borrowed Singleton implementation from Wikipedia: http://en.wikipedia.org/wiki/Singleton_pattern

    private static KnowledgeGraph instance = null;

    /**
     * Class constructor.  Initially sets nodeMap, predicateMap, tripleMap, and queryMapSet to all be empty HashMaps.
     */
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
     * string (e.g. "Bill ? ?"), and value is a Set of matching Triples.
     */
    private Map<String,Set<Triple>> queryMapSet;


    /**
     * Public method for adding a list of Triples to the KnowledgeGraph.  Updates the associations for: nodeMap,
     * tripleMap, queryMapSet, and predicateMap to reflect the added Triple. There should be one Triple instance
     * per unique Subject, Predicate, Object combination, so that Triples are not duplicated (Flyweight pattern).
     *
     * @param tripleList  the list of Triples to add to the KnowledgeGraph
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

                addTripleToQueryMapSet(triple);
            }
        }
    }

    /**
     * Adds the given Triple to the queryMapSet, calculating all the permutations of queries for which the passed
     * Triple should be included in the result set.  For each Triple, there are a total of 8 unique queries that
     * should return the Triple.
     *
     * @param triple  the Triple to add to the queryMapSet
     */
    private void addTripleToQueryMapSet(Triple triple) {

        // the triple is new and doesn't exist yet, so add all required references as necessary
        if ( tripleMap.get(triple.getIdentifier().toLowerCase()) == null && triple.getIdentifier() != null) {

            String cleanedIdentifier = cleanTripleIdentifier(triple.getIdentifier());
            tripleMap.put(cleanedIdentifier, triple);

            /*
            update queryMapSet - this pre-computes the possible queries that this Triple should match; there should in
            general be 8 permutations of queries that will match the given Triple.  For example, if the triple is:

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

            // save a list of all the query strings that should potentially match this triple, and update the queryMapSet
            // with the current triple for each permutation so that subsequent queries will quickly match this triple
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
        }
    }

    /**
     * Takes a given Triple identifier, or a Triple query, and does the following:
     * <ul>
     *     <li>converts the string to lower-case</li>
     *     <li>removes any trailing period from the string</li>
     *     <li>replaces any occurrences of 2 or more spaces with a single space</li>
     * </ul>
     *
     * @param stringToParse  the Triple identifier string or Triple query string to clean
     * @return   a string that has the correct formatting for either uniquely identifying it as an existing
     *           Triple, or using as a Triple query
     */
    public String cleanTripleIdentifier(String stringToParse) {
        stringToParse = stringToParse.toLowerCase();
        stringToParse = stringToParse.replaceAll("\\.+$", "");  // trim off any trailing periods from the string
        stringToParse = stringToParse.replaceAll("\\s{2,}", " ");  // replace all occurrences of two or more consecutive spaces with a single space
        return stringToParse;
    }

    /**
     * Uses the queryMapSet to determine the Triples that match the given Query. Modifies the query by stripping off
     * any trailing periods, and condenses multiple sequential occurrences of a space character into a single space.
     * Queries are executed in a case-insensitive fashion.  If none are found return an empty Set.
     *
     * @param    query     the query string to search for matching Triples; question mark denotes a wildcard
     * @return             the Set of all Triples matching the input query
     */
    public Set<Triple> executeQuery(Triple query) {
        if (query != null && query.getIdentifier() != null && query.getIdentifier().length() > 0) {
            String realQuery = cleanTripleIdentifier(query.getIdentifier());
            if (realQuery != null && realQuery.length() > 0) {
                Set<Triple> foundResults = this.queryMapSet.get(realQuery);
                return foundResults;
            }
            return null;
        }
        return null;
    }


    /**
     * Return a Node Instance for the given node identifier. Use the nodeMap to look up the Node.
     * If the Node does not exist, create it and add it to the nodeMap. Node names are case insensitive.
     * Nodes may be used both as the "Subject" (first part) of a Triple, as well as the "Object" (last part)
     * of a Triple.
     *
     * @param identifier  the string identifier of the Node; will be stored in the map as lower-case
     * @return            the newly created Node object based on the input identifier
     */
    public Node getNode(String identifier) {
        // does this identifier already exist in the KnowledgeGraph for a pre-existing Node?
        if (nodeMap.keySet().contains(identifier.toLowerCase())) {
            return nodeMap.get(identifier.toLowerCase());
        }

        // this is a new Node that the KnowledgeGraph has not encountered before; add it to the map
        Node node = new Node(identifier);
        nodeMap.put(identifier.toLowerCase(), node);
        return node;
    }

    /**
     * Return a Predicate instance for the given identifier. Use the predicateMap to lookup the Predicate.
     * If the Predicate does not exist, create it and add it to the predicateMap. Predicate names
     * are case insensitive.
     *
     * @param identifier  the string identifier of the Predicate; will be stored in the map as lower-case
     * @return            the newly created Predicate object based on the input identifier
     */
    public Predicate getPredicate(String identifier) {
        // does this identifier already exist in the KnowledgeGraph for a pre-existing Predicate?
        if (predicateMap.keySet().contains(identifier.toLowerCase())) {
            return predicateMap.get(identifier.toLowerCase());
        }

        // this is a new Predicate that the KnowledgeGraph has not encountered before; add it to the map
        Predicate predicate = new Predicate(identifier);
        predicateMap.put(identifier.toLowerCase(), predicate);
        return predicate;
    }

    /**
     * Return the Triple instance for the given Object, Predicate and Subject. Use the tripleMap to
     * lookup the Triple. If the Triple is currently unknown to the KnowledgeGraph, creates it and
     * adds it to the tripleMap and update the queryMapSet.
     *
     * @param subject    the subject Node for the Triple
     * @param predicate  the Predicate for the Triple
     * @param object     the object Node for the Triple
     * @return           the Triple that was either found pre-existing or newly created
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
    }


    /**
     * For the given identifier, constructs a Triple object that can be used as the input parameter to the
     * executeQuery() method.  This is useful for constructing Triple objects whose identifiers represent queries
     * containing the "?" character (e.g., wildcard character).
     *
     * @param identifier    string used to construct a new Triple from; typically used to pass to the executeQuery()
     *                      method and may contain "?" characters in the Triple identifier
     * @return              a new Triple object corresponding to the String identifier
     * @throws Exception
     */
    public Triple getQueryTripleFromStringIdentifier(String identifier) throws Exception {

        if (identifier != null && identifier.length() > 0) {

            identifier = cleanTripleIdentifier(identifier);
            String[] parts = identifier.split("\\s");

            if (parts.length < 3) {
                throw new Exception("Triple identifier should have 3 parts, but only actually had ["+parts.length+"] parts: ["+identifier+"]");
            }
            else {
                // for parts of the passed identifier that contain "?", leave those as null objects;
                // when we construct the Triple, it will properly handle those nulls and equate them
                // to the wildcard character
                Node subject = null;
                Node object = null;
                Predicate predicate = null;

                if (!parts[0].equalsIgnoreCase("?")) {
                    // the first part should contain the first "Node"
                    subject = getNode(parts[0]);  // node/subjects: Joe, Sue, Mary, etc.
                }

                if (!parts[1].equalsIgnoreCase("?")) {
                    // the second part should be the Predicate
                    predicate = getPredicate(parts[1]);  // predicate: has_friend, plays, etc.
                }

                if (!parts[2].equalsIgnoreCase("?")) {
                    // last part should be the "object", also a Node
                    object = getNode(parts[2]);  // object (also a node): Bill, Sue, Mary, Ultimate_Frisbee
                }

                return new Triple(subject, predicate, object);
            }
        }
        return null;
    }
}
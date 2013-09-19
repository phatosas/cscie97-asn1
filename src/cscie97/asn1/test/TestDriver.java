package cscie97.asn1.test;

import cscie97.asn1.knowledge.engine.exception.ImportException;
import cscie97.asn1.knowledge.engine.exception.ParseException;
import cscie97.asn1.knowledge.engine.exception.QueryEngineException;
import cscie97.asn1.knowledge.engine.Importer;
import cscie97.asn1.knowledge.engine.QueryEngine;
import cscie97.asn1.knowledge.engine.Triple;
import cscie97.asn1.knowledge.engine.KnowledgeGraph;

/**
 * Test harness for the CSCI-E 97 Assignment 1.  Reads in a supplied importFile to load new Triples into the
 * KnowledgeGraph, and then executes a supplied query file to run queries against the KnowledgeGraph for those
 * new Triples.
 *
 * @author David Killeffer <rayden7@gmail.com>
 * @version 1.0
 * @see Triple
 * @see KnowledgeGraph
 * @see Importer
 * @see QueryEngine
 */
public class TestDriver {

    /**
     * Executes the primary test logic. Calls the {@link Importer#importTripleFile(String)} method, passing in
     * the name of the provided triple file (first argument).
     * After loading the input triples, invokes the {@link QueryEngine#executeQueryFilename(String)} method passing
     * in provided query file name (second argument).  Outputs all results to standard out.
     *
     * @param args  first argument should be an input file containing one Triple per line, second argument should
     *              be a query file containing one Triple query per line
     */
    public static void main(String[] args) {
        if (args.length == 2) {
            try {
                Importer.importTripleFile(args[0]);
                QueryEngine.executeQueryFilename(args[1]);
            }
            // if we catch a ParseException, either the original import of Triples failed or the Query on those
            // Triples; in either case, the entire program execution should fail and the errors in the original files
            // fixed before the program can be executed again
            catch (ParseException pe) {
                System.out.println(pe.getMessage());
                System.exit(1);
            }
            // if we catch an ImportException, the original load of Triples failed for some reason, so the program
            // should fail and exit
            catch (ImportException ie) {
                System.out.println(ie.getMessage());
                System.exit(1);
            }
            // if we catch an ImportException, the original load of Triples failed for some reason, so the program
            // should fail and exit
            catch (QueryEngineException qee) {
                System.out.println(qee.getMessage());
                System.exit(1);
            }
        }
        else {
            System.out.println("Arguments to TestDriver should be: 1) import Triple file, and 2) input Query file");
            System.exit(1);
        }
    }
}
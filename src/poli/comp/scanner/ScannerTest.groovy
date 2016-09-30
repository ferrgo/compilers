package poli.comp.scanner

import poli.comp.parser.GrammarSymbol

//import java.util.logging.FileHandler

/**
 * Created by root on 27/09/16.
 */
class ScannerTest extends groovy.util.GroovyTestCase {
    Scanner sc;


    void setUp() throws Exception{
        super.setUp()
        System.out.println("Setting up test files");
        File folder = new File("./testFiles");
        File [] listOfFiles = folder.listFiles();
        ArrayList<File> listOfTests = new ArrayList<File>();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
                listOfTests.add(listOfFiles[i]);
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
        System.out.println(listOfTests.toString());

        sc = new Scanner(listOfTests.get(2).toString())
    }

    void tearDown() {

    }

    void testGetNextToken() {
        Token now;
        now = sc.getNextToken();
        int count = 0
        while (now.getKind() != GrammarSymbol.EOT) {
            System.out.println(now.getKind().toString() + " ..... " + now.getSpelling().toString());
            now = sc.getNextToken()
        }

    }
}

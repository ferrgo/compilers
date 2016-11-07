package poli.comp.scanner

import poli.comp.parser.GrammarSymbol

//import java.util.logging.FileHandler

/**
 * Created by root on 27/09/16.
 */
class ScannerTest extends groovy.util.GroovyTestCase {
    ArrayList<File> listOfTestsOk;
    ArrayList<File> listOfTestsFail;
    final String pass = "Pass";
    final String fail = "Fail";

    void setUp() throws Exception{
        super.setUp()
        File passfolder = new File("./testFiles/Scanner/Pass/");
        File [] passFiles = passfolder.listFiles();
        listOfTestsOk = new ArrayList<File>();
        for (int i = 0; i < passFiles.length; i++) {
            if (passFiles[i].isFile()) {
                listOfTestsOk.add(passFiles[i]);
            }
        }
        File failfolder = new File("./testFiles/Scanner/Fail/");
        File [] failFiles = failfolder.listFiles();
        listOfTestsFail = new ArrayList<File>();
        for (int i = 0; i < failFiles.length; i++) {
            if (failFiles[i].isFile()) {
                listOfTestsFail.add(failFiles[i]);
            }
        }
        System.out.println("Initiating test ");

    }

    void tearDown() {
        System.out.println("Finished test ")
    }

    void testScannerWithArg() {
        poli.comp.scanner.Scanner sc;
        sc = new poli.comp.scanner.Scanner(listOfTestsOk.get(0).toString())
        assertNotNull(sc)//Should be instantiated
    }

    void testScannerWOArg(){
        poli.comp.scanner.Scanner sc;
        sc = new poli.comp.scanner.Scanner();
        assertNotNull(sc)//Should be instantiated
    }



    void testScannerSucces() {
        poli.comp.scanner.Scanner sc;
        String result = pass;
        for(String t : listOfTestsOk){
            System.out.println(t);
            sc = new poli.comp.scanner.Scanner(t);
            Token now;
            now = sc.getNextToken();
            while (now.getKind() != GrammarSymbol.EOT) {
//                System.out.println(now.getKind().toString() + " ..... " + now.getSpelling().toString());
                try{
                    now = sc.getNextToken();
                }catch (Exception e){
                    result = fail + "\nScanner result at: "+t.toString()+"\n"+e.toString();
                    System.out.println("This test failed for this program... bad program bad program");
                }
                assertEquals(pass, result)
            }
        }
    }

    void testScannerFail() {
        poli.comp.scanner.Scanner sc;
        String result = fail;
        for(String t : listOfTestsFail){
            System.out.println(t);
            sc = new poli.comp.scanner.Scanner(t);
            Token now;
            now = sc.getNextToken();
            while (now.getKind() != GrammarSymbol.EOT) {
              // System.out.println(now.getKind().toString() + " ..... " + now.getSpelling().toString());
                try{
                    now = sc.getNextToken();
                }catch (Exception e){
                    result = pass;
                    break;
                }
            }
            if (result!=pass) System.out.println("This test failed for this program... bad program bad program");
            assertEquals(pass, result)
        }
    }
}

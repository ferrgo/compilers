package poli.comp.checker

import poli.comp.parser.Parser
import poli.comp.util.AST.AST

/**
 * Created by hgferr on 11/11/16.
 */
class CheckerTest extends GroovyTestCase {
    ArrayList<File> listOfTestsOk;
    ArrayList<File> listOfTestsFail;
    final String pass = "Pass";
    final String fail = "Fail";

    void setUp() throws Exception{
        super.setUp()
        File passfolder = new File("./testFiles/Checker/Pass/");
        File [] passFiles = passfolder.listFiles();
        listOfTestsOk = new ArrayList<File>();
        for (int i = 0; i < passFiles.length; i++) {
            if (passFiles[i].isFile()) {
                listOfTestsOk.add(passFiles[i]);
            }
        }
        File failfolder = new File("./testFiles/Checker/Fail/");
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

    void testCheck() {
        Checker checker = new Checker();
        Parser p;

        String result = pass;
        for(String t : listOfTestsOk){
            System.out.println(t);
            p = new Parser(t);
            try{

                AST root = null;

                root = p.parse();

                checker.check(root);
            }catch (Exception e){
                result = fail + "\nParser result at: "+t.toString()+"\n"+e.toString();
                System.out.println("This test failed for this program... bad program bad program");
            }
        }
        assertEquals(pass, result);
    }
}

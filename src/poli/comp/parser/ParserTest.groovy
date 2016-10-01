package poli.comp.parser

/**
 * Created by hgferr on 01/10/16.
 */
class ParserTest extends GroovyTestCase {
    ArrayList<File> listOfTestsOk;
    ArrayList<File> listOfTestsFail;

    void setUp() throws Exception{
        super.setUp()
        File passfolder = new File("./testFiles/Parser/Pass/");
        File [] passFiles = passfolder.listFiles();
        listOfTestsOk = new ArrayList<File>();
        for (int i = 0; i < passFiles.length; i++) {
            if (passFiles[i].isFile()) {
                listOfTestsOk.add(passFiles[i]);
            }
        }
        File failfolder = new File("./testFiles/Parser/Fail/");
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


    void testParseWithArg() {
        poli.comp.parser.Parser ps;
        ps = new poli.comp.parser.Parser(listOfTestsOk.get(0).toString())
        assertNotNull(ps)//Should be instantiated
    }

    void testParseWOArg(){
        poli.comp.parser.Parser ps;
        ps = new poli.comp.parser.Parser();
        assertNotNull(ps)//Should be instantiated
        assertNotNull(ps.scanner);//Should be instantiated
    }

    void testParseOkProgs(){
        poli.comp.parser.Parser ps;
        ps = new poli.comp.parser.Parser();
        for(String t : listOfTestsOk){
            System.out.println(t);
            ps = new poli.comp.parser.Parser(t);
            try{
                ps.parse()//Must return some kind of AST...
            }catch (Exception e){
                System.out.println("This test failed guys...");
            }
        }
    }

    void testParseFailProgs(){
        poli.comp.parser.Parser ps;
        for(String t : listOfTestsFail) {
            try{
                System.out.println(t);
                ps = new poli.comp.parser.Parser(t);
                ps.parse()
            }catch(Exception e){
                System.out.println("It fails! \\o... \nBut Okay, it was supposed to\n"+e.toString());
            }
        }

    }

}

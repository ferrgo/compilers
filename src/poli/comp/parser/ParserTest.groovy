package poli.comp.parser

/**
 * Created by hgferr on 01/10/16.
 */
class ParserTest extends GroovyTestCase {
    ArrayList<File> listOfTestsOk;
    ArrayList<File> listOfTestsFail;
    final String pass = "Pass";
    final String fail = "Fail";

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
        String result = pass;
        for(String t : listOfTestsOk){
            System.out.println(t);
            ps = new poli.comp.parser.Parser(t);
            try{
                ps.parse()//Must return some kind of AST...
            }catch (Exception e){
                result = fail + "\nParser result at: "+t.toString()+"\n"+e.toString();
            }
        }
        assertEquals(pass, result)
    }

    void testParseFailProgs(){
        poli.comp.parser.Parser ps;
        String result = fail;
        for(String t : listOfTestsFail) {
            try{
                System.out.println(t);
                ps = new poli.comp.parser.Parser(t);
                ps.parse()
            }catch(Exception e){
                result = pass;
            }
        }
        assertEquals(pass, result)

    }

}

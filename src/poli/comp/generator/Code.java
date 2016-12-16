package poli.comp.generator;

import java.util.ArrayList;

/**
 * Created by hgferr on 15/12/16.
 */
public class Code {
    private ArrayList<Instruction> extern, data, text;

    public Code(){
        extern = new ArrayList<>();
        data = new ArrayList<>();
        text = new ArrayList<>();
    }

    public void add(Instruction i) {
        switch (i.getSection()) {
            case 1:
                extern.add(i);
                break;
            case 2:
                data.add(i);
                break;
            case 3:
                text.add(i);
                break;
            default:
                text.add(i); // When you like... who am I... You're text sectioned. DEAL WITH IT
                break;
        }
    }

    public String generate(){ //TODO modify to BufferedWriter(FileWriter(output.asm))
        StringBuilder asm = new StringBuilder();
        //--------- Extern section for "imports" from C -----------
        for (Instruction i : extern){
            asm.append("extern " + i.toString());
        }
        asm.append("\n");
        asm.append(";;;;;;;;;;;;;;;;;;;;;;;END OF EXTERN;;;;;;;;;;;;;;;;;;;\n");
        //--------- Data section
        asm.append("SECTION .data\n");
        for (Instruction i : data){
            asm.append("\t"+ i.toString() + "\n");
        }
        asm.append("\tintFormat: db \"%d\", 10, 0");
        asm.append("\n;;;;;;;;;;;;;;;;;;;;;;;;END OF DATA;;;;;;;;;;;;;;;;;;;;\n");
        //--------- Text section
        asm.append("SECTION .text\n");
        for (Instruction i : text){
            if(i.toString().contains("global")){
                asm.append("\t"+ i.toString() + "\n");
            }
            else if(i.toString().charAt(0)!='_'){
                asm.append("\t\t"+ i.toString() + "\n");
            } else {
                asm.append("\t"+ i.toString() + "\n");
            }
        }

        return asm.toString();
    }

}

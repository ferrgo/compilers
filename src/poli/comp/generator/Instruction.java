package poli.comp.generator;

public class Instruction{
	private String contents;
	private int section;

	public Instruction(String s, int section){
		this.section = section;
		this.contents=s;
	}

	public String getContents(){
		return this.contents;
	}

	public String toString(){
		return this.contents;
	}

	public int getSection() {
		return section;
	}
}

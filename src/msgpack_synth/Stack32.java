package msgpack_synth;

import synthesijer.hdl.HDLModule;
import synthesijer.hdl.HDLPort.DIR;
import synthesijer.hdl.HDLPrimitiveType;

public class Stack32 extends HDLModule{
	
	public void push(int n){
		
	}
	
	public int pop(){
		return 0;
	}

	public boolean hasItem;
		
	public Stack32(String... args){
		super("stack32", "clk", "reset");
		
		newPort("push_req",  DIR.IN,  HDLPrimitiveType.genSignedType(32));
		newPort("push_n",    DIR.IN,  HDLPrimitiveType.genSignedType(32, "WIDTH-1", "0"));
		newPort("push_busy", DIR.OUT, HDLPrimitiveType.genBitType());
		
		newPort("pop_req",    DIR.IN,  HDLPrimitiveType.genSignedType(32));
		newPort("pop_return", DIR.OUT,  HDLPrimitiveType.genSignedType(32, "WIDTH-1", "0"));
		newPort("pop_busy",   DIR.OUT, HDLPrimitiveType.genBitType());

		newPort("hasItem",      DIR.OUT, HDLPrimitiveType.genBitType());
	}

}

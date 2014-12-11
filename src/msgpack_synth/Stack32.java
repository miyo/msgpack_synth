package msgpack_synth;

import synthesijer.hdl.HDLModule;
import synthesijer.hdl.HDLPort.DIR;
import synthesijer.hdl.HDLPrimitiveType;

public class Stack32 extends HDLModule{
	
	public int[] data;
		
	public Stack32(String... args){
		super("stack8", "clk", "reset");
		newPort("data_address", DIR.IN,  HDLPrimitiveType.genSignedType(32));
		newPort("data_din",     DIR.IN,  HDLPrimitiveType.genSignedType(32, "WIDTH-1", "0"));
		newPort("data_dout",    DIR.OUT, HDLPrimitiveType.genSignedType(32, "WIDTH-1", "0"));
		newPort("data_we",      DIR.IN,  HDLPrimitiveType.genBitType());
		newPort("data_oe",      DIR.IN,  HDLPrimitiveType.genBitType());
	}

}

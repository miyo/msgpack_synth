package msgpack_synth;

import synthesijer.hdl.HDLModule;
import synthesijer.hdl.HDLPort.DIR;
import synthesijer.hdl.HDLPrimitiveType;

public class Fifo8 extends HDLModule{
	
	public byte[] data;
		
	public Fifo8(String... args){
		super("fifo8", "clk", "reset");
		newPort("data_address", DIR.IN,  HDLPrimitiveType.genSignedType(8));
		newPort("data_din",     DIR.IN,  HDLPrimitiveType.genSignedType(8, "WIDTH-1", "0"));
		newPort("data_dout",    DIR.OUT, HDLPrimitiveType.genSignedType(8, "WIDTH-1", "0"));
		newPort("data_we",      DIR.IN,  HDLPrimitiveType.genBitType());
		newPort("data_oe",      DIR.IN,  HDLPrimitiveType.genBitType());
	}

}

package msgpack_synth;

import synthesijer.hdl.HDLModule;
import synthesijer.hdl.HDLPort.DIR;
import synthesijer.hdl.HDLPrimitiveType;

public class Fifo8 extends HDLModule{
	
	public byte dequeue(){
		return 0;
	}
		
	public Fifo8(String... args){
		super("fifo8", "clk", "reset");
		newPort("dequeue_req",  DIR.IN,  HDLPrimitiveType.genBitType());
		newPort("dequeue_busy", DIR.OUT,  HDLPrimitiveType.genBitType());
		newPort("dequeue_return", DIR.OUT,  HDLPrimitiveType.genSignedType(8));
	}

}

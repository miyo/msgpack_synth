package msgpack_synth;

public class SimpleParser {
	
	private final Fifo8 read_port = new Fifo8();
	private final Stack32 op_stack = new Stack32();
	private final Stack32 len_stack = new Stack32();
	
	/// positive fixint	0xxxxxxx	0x00 - 0x7f
	// fixmap	1000xxxx	0x80 - 0x8f
	// fixarray	1001xxxx	0x90 - 0x9f
	// fixstr	101xxxxx	0xa0 - 0xbf
	// negative fixint	111xxxxx	0xe0 - 0xff
	
	public static final int MsgPack_NIL	= 0xc0;
	public static final int MsgPack_NEVER_USED = 0xc1;
	public static final int MsgPack_false = 0xc2;
	public static final int MsgPack_true = 0xc3;
	public static final int MsgPack_bin_8 = 0xc4;
	public static final int MsgPack_bin_16 = 0xc5;
	public static final int MsgPack_bin_32 = 0xc6;
	public static final int MsgPack_ext_8 = 0xc7;
	public static final int MsgPack_ext_16 = 0xc8;
	public static final int MsgPack_ext_32 = 0xc9;
	public static final int MsgPack_float_32 = 0xca;
	public static final int MsgPack_float_64 = 0xcb;
	public static final int MsgPack_uint_8 = 0xcc;
	public static final int MsgPack_uint_16 = 0xcd;
	public static final int MsgPack_uint_32 = 0xce;
	public static final int MsgPack_uint_64 = 0xcf;
	public static final int MsgPack_int_8 = 0xd0;
	public static final int MsgPack_int_16 = 0xd1;
	public static final int MsgPack_int_32 = 0xd2;
	public static final int MsgPack_int_64 = 0xd3;
	public static final int MsgPack_fixext_1 = 0xd4;
	public static final int MsgPack_fixext_2 = 0xd5;
	public static final int MsgPack_fixext_4 = 0xd6;
	public static final int MsgPack_fixext_8 = 0xd7;
	public static final int MsgPack_fixext_16 = 0xd8;
	public static final int MsgPack_str_8 = 0xd9;
	public static final int MsgPack_str_16 = 0xda;
	public static final int MsgPack_str_32 = 0xdb;
	public static final int MsgPack_array_16 = 0xdc;
	public static final int MsgPack_array_32 = 0xdd;
	public static final int MsgPack_map_16 = 0xde;
	public static final int MsgPack_map_32 = 0xdf;
	
	public static final int ARRAY_OP = 1;
	public static final int MAP_OP = 2;
	
	private void consume_data(int type, int num){
		for(int i = 0; i < num; i++){
			int d = read_port.data[0]; // consume
		}
	}

	private int get_length(int num){
		int len = 0;
		for(int i = 0; i < num; i++){
			len = (len << 8) + (((int)read_port.data[0]) & 0x000000FF);
		}
		return len;
	}

	private void read_data(){
		
		int d = (int)read_port.data[0];
		
		if(d < 127 && d >= -31){
			// "d" is consume as immediate value
			return;
		}

		if((d & 0xF0) == 0x80){
			// fixmap
			op_stack.data[0] = MAP_OP; // next, start map operation
			len_stack.data[0] = d & 0x0F;
			return;
		}
		
		if((d & 0xF0) == 0x90){
			// fixarray	1001xxxx	0x90 - 0x9f
			op_stack.data[0] = ARRAY_OP;
			len_stack.data[0] = d & 0x0F;
			return;
		}
		
		if((d & 0xE0) == 0xA0){
			// fixstr	101xxxxx	0xa0 - 0xbf
			int len = d & 0x1F;
			for(int i = 0; i < len; i++){
				d = read_port.data[0]; // consume specified length
			}
		}

		
		switch(d){
		case MsgPack_NIL:
			return;
		case MsgPack_NEVER_USED:
			return;
		case MsgPack_false:
			return;
		case MsgPack_true:
			return;
		case MsgPack_bin_8:
			consume_data(d, get_length(1));
			return;
		case MsgPack_bin_16:
			consume_data(d, get_length(2));
			return;
		case MsgPack_bin_32:
			consume_data(d, get_length(4));
			return;
		case MsgPack_ext_8:
			return;
		case MsgPack_ext_16:
			return;
		case MsgPack_ext_32:
			return;
		case MsgPack_float_32:
			consume_data(d, 4);
			return;
		case MsgPack_float_64:
			consume_data(d, 8);
			return;
		case MsgPack_uint_8:
			consume_data(d, 1);
			return;
		case MsgPack_uint_16:
			consume_data(d, 2);
			return;
		case MsgPack_uint_32:
			consume_data(d, 4);
			return;
		case MsgPack_uint_64:
			consume_data(d, 8);
			return;
		case MsgPack_int_8:
			consume_data(d, 1);
			return;
		case MsgPack_int_16:
			consume_data(d, 2);
			return;
		case MsgPack_int_32:
			consume_data(d, 4);
			return;
		case MsgPack_int_64:
			consume_data(d, 8);
			return;
		case MsgPack_fixext_1:
			return;
		case MsgPack_fixext_2:
			return;
		case MsgPack_fixext_4:
			return;
		case MsgPack_fixext_8:
			return;
		case MsgPack_fixext_16:
			return;
		case MsgPack_str_8:{
			consume_data(d, get_length(1));
			return;
		}
		case MsgPack_str_16:
			consume_data(d, get_length(2));
			return;
		case MsgPack_str_32:
			consume_data(d, get_length(4));
			return;
		case MsgPack_array_16:{
			op_stack.data[0] = ARRAY_OP; // next, start map operation
			len_stack.data[0] = get_length(2);
			return;
		}			
		case MsgPack_array_32:{
			op_stack.data[0] = ARRAY_OP; // next, start map operation
			len_stack.data[0] = get_length(4);
			return;
		}
		case MsgPack_map_16:{
			op_stack.data[0] = MAP_OP; // next, start map operation
			len_stack.data[0] = get_length(2);
			return;
		}
		case MsgPack_map_32:{
			op_stack.data[0] = MAP_OP; // next, start map operation
			len_stack.data[0] = get_length(4);
			return;
		}
		default: // not should be reached
			return;
		}
		
	}
	
	public void parse(){
		long num = 1L;
		while(num > 0){
			read_data();
			num--;
			if(num == 0){
				if(op_stack.hasItem){
					int op = op_stack.data[0]; // pop
					int len = len_stack.data[0]; // pop
					if(op == ARRAY_OP){
						num = (long)len; // N
					}else{
						num = ((long)len) << 1; // N * 2
					}
				}
			}
		}
	}

}

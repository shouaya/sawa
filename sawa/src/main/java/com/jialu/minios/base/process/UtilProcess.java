package com.jialu.minios.base.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.jialu.minios.utility.PropertiesUtil;
import com.jialu.minios.vo.MiniMeta;
import com.jialu.minios.vo.MiniPair;

public class UtilProcess {

	public static String random4Code() {
		StringBuffer sb = new StringBuffer();
		String str = "0123456789";
		Random r = new Random();
		for (int i = 0; i < 4; i++) {
			int num = r.nextInt(str.length());
			sb.append(str.charAt(num));
			str = str.replace((str.charAt(num) + ""), "");
		}
		return sb.toString();
	}
	
	public static List<MiniMeta> getPropertiesByName(String name){
		HashMap<String, String> properties = PropertiesUtil.getPropertyProperty(name);
		List<MiniMeta> metas = new ArrayList<MiniMeta>();
		if(properties != null){
			for(String key : properties.keySet()){
				MiniMeta meta = new MiniMeta();
				meta.setName(key);
				String[] values = properties.get(key).split(",");
				meta.setType(values[0]);
				meta.setNullable(values[1]);
				meta.setTitle(values[2]);
				metas.add(meta);
			}
		}
		return metas;
	}

	public static Object castValue(String name, MiniPair param) {
		List<MiniMeta> metas = getPropertiesByName(name.split("-")[0]);
		for(MiniMeta meta : metas){
			if(meta.getName().equals(param.getKey())){
				return toObject(meta.getType(), param.getValue());
			}
		}
		return null;
	}
	
	private static Object toObject( String name, String value ) {
	    if( Boolean.class.getName().equals(name) ) return Boolean.parseBoolean( value );
	    if( Byte.class.getName().equals(name) ) return Byte.parseByte( value );
	    if( Short.class.getName().equals(name) ) return Short.parseShort( value );
	    if( Integer.class.getName().equals(name) ) return Integer.parseInt( value );
	    if( Long.class.getName().equals(name) ) return Long.parseLong( value );
	    if( Float.class.getName().equals(name) ) return Float.parseFloat( value );
	    if( Double.class.getName().equals(name) ) return Double.parseDouble( value );
	    return value;
	}
}

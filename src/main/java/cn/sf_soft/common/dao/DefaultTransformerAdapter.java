package cn.sf_soft.common.dao;

import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.BasicTransformerAdapter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by henry on 2018/5/9.
 */
public class DefaultTransformerAdapter  extends BasicTransformerAdapter implements Serializable {
    public static final DefaultTransformerAdapter INSTANCE = new DefaultTransformerAdapter();

    private DefaultTransformerAdapter() {
    }

    public Object transformTuple(Object[] tuple, String[] aliases) {
        Map result = new HashMap(tuple.length);

        for(int i = 0; i < tuple.length; ++i) {
            String alias = aliases[i];
            if(alias != null) {
                result.put(underline2Camel(alias, true), tuple[i]);
            }
        }

        return result;
    }

    private Object readResolve() {
        return INSTANCE;
    }

    static public String underline2Camel(String line, boolean smallCamel){
        if(line==null||"".equals(line)){
            return "";
        }
        StringBuffer sb=new StringBuffer();
        Pattern pattern=Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher=pattern.matcher(line);
        while(matcher.find()){
            String word=matcher.group();
            sb.append(smallCamel&&matcher.start()==0?Character.toLowerCase(word.charAt(0)):Character.toUpperCase(word.charAt(0)));
            int index=word.lastIndexOf('_');
            if(index>0){
                sb.append(word.substring(1, index));
            }else{
                sb.append(word.substring(1));
            }
        }
        return sb.toString();
    }

    static public String camel2Underline(String line){
        if(line==null||"".equals(line)){
            return "";
        }
        StringBuffer sb=new StringBuffer();
        Pattern pattern=Pattern.compile("([a-z_\\d\\s]*)([A-Z]*)");
        Matcher matcher=pattern.matcher(line);
        while(matcher.find()){
            String group0=matcher.group(1);
            String group1=matcher.group(2);
            sb.append(group0);
            if(group1 != null && group1.length() > 0){
                sb.append("_" + group1.toLowerCase());
            }
        }
        return sb.toString();
    }
}

package cn.sf_soft.common.util;

import java.sql.Timestamp;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;
/**
 * the tools that mapping for TimeStamp
 * @author king
 */
public class TimestampAdapter extends XmlAdapter<Date, Timestamp> {

   public Date marshal(Timestamp t) {
     return new Date(t.getTime());
   }

   public Timestamp unmarshal(Date d) {
     return new Timestamp (d.getTime());
   }
   
}

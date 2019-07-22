package cn.sf_soft.common.util;

import java.sql.Types;

import org.hibernate.dialect.SQLServerDialect;

public class ExtendedSQLServerDialect extends SQLServerDialect {

	 public ExtendedSQLServerDialect() {   
	        super();   
//	        registerHibernateType(1, "string");   
//	        registerHibernateType(-9, "string");   
//	        registerHibernateType(-16, "string");   
//	        registerHibernateType(3, "double");
	        
	        registerHibernateType(Types.CHAR, "string");   
	        registerHibernateType(Types.NVARCHAR, "string");   
	        registerHibernateType(Types.LONGNVARCHAR, "string");   
	        registerHibernateType(Types.DECIMAL, "double");
	        

	    } 
}

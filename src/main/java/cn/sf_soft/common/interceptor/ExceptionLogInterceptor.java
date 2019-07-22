package cn.sf_soft.common.interceptor;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;

import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.user.model.SysUsers;

/**
 * Service层系统异常信息日志拦截器
 * 
 * @author king
 * @create 2013-9-10下午3:09:28
 */
public class ExceptionLogInterceptor {

	static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ExceptionLogInterceptor.class);

	public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {

		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < joinPoint.getArgs().length; i++) {
			sb.append(joinPoint.getArgs()[i] + ", ");
		}
		if(sb!=null &&sb.length()>2){
			sb.deleteCharAt(sb.length() - 2);
		}
		sb.append("]");
		
		Signature sign = joinPoint.getSignature();
//		logger.debug(String.format("访问方法 %s,参数 %s", joinPoint.getSignature().getDeclaringTypeName() + "."
//				+ joinPoint.getSignature().getName(), sb.toString()));
		try {
			return joinPoint.proceed();
		} catch (Exception e) {
			// 记录日志
			//SysUsers user = HttpSessionStore.getSessionUser();
			String log = String.format("访问方法 %s,参数 %s,错误详情：", joinPoint.getSignature().getDeclaringTypeName() + "."
					+ joinPoint.getSignature().getName(), sb.toString());
			logger.error(log, e);
			throw e;
		}

	}

}

package cn.sf_soft.support;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ClassUtils;
/**
 * @Auther: chenbiao
 * @Date: 2018/6/30 14:05
 * @Description:
 */

public class ApplicationUtil implements ApplicationContextAware{

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context)
            throws BeansException {
        ApplicationUtil.context = context;
    }
    public static ApplicationContext getContext(){
        return context;
    }

    /**
     * 根据class获得bean.
     *
     * @param clz
     *            Class
     * @return T
     */
    public static <T> T getBean(Class<T> clz) {
        return getContext().getBean(clz);
    }


    public static <T> T getSingleBean(Class<T> clz){
        Map<String, T> map = getContext().getBeansOfType(clz);
        if(null != map && !map.isEmpty()){
            for(String name : map.keySet()){
                T t = map.get(name);
                if(ClassUtils.getUserClass(t).getName().equals(clz.getName())){
                    return t;
                }
            }
        }
        return null;
    }


    /**
     * 根据id获得bean.
     *
     * @param name
     *            String
     * @return T
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) getContext().getBean(name);
    }

}

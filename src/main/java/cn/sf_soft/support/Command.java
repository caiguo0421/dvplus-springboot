package cn.sf_soft.support;

/**
 * @Auther: chenbiao
 * @Date: 2018/6/30 10:56
 * @Description: 执行服务，在保存前执行
 */
public interface Command<T> {

    /**
     * 获取自身与从对象间的关系
     * @return
     */
    EntityRelation getEntityRelation();

    /**
     * 在执行execute之前执行，可用作设置主键与主外键和冗余属性<p>
     * 执行顺序主对象往从对象依次执行
     * @see EntityRelation
     * @param entityProxy
     */
    void beforeExecute(EntityProxy<T> entityProxy);

    /**
     * 在执行beforeExecute之后执行，执行顺序从最末级对象往上执行
     * @see EntityRelation
     * @param entityProxy
     */
    void execute(EntityProxy<T> entityProxy);
}

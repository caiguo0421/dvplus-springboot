package cn.sf_soft.support;

import com.google.gson.JsonObject;

import java.util.List;

/**
 * @Auther: chenbiao
 * @Date: 2018/6/30 09:25
 * @Description: 对象代理类，用于存储对象的当前状态和原始状态
 */
public interface EntityProxy<T> {


    /**
     * 获取对象操作
     * @return 对象操作
     */
    Operation getOperation();

    /**
     * 获取实体对象类型
     * @return 对象类型
     */
    Class<?> getEntityClass();

    /**
     * 获取对象的主对象
     * @return 主对象
     */
    <E> EntityProxy<E> getMaster();

    /**
     * 根据指定的从对象名获取从对象集合
     * @param slaveClassName 从对象名(对象的简称)
     * @return 从对象集合
     */
    <E> List<EntityProxy<E>> getSlaves(String slaveClassName);

    /**
     * 获取所有从对象的类名简称
     * @return  从对象的类名简称
     */
    String[] getSlaveNames();

    /**
     * 是否存在从对象
     * @return
     */
    boolean hasSlave();

    /**
     * 获取原始的json
     * @return 原始json
     */
    JsonObject getJsonObject();

    /**
     * 获取更改后的对象
     * @return 改后的对象
     */
    T getEntity();

    /**
     * 获取原始对象
     * @return 原始对象
     */
    T getOriginalEntity();

    /**
     * 获取对象的服务
     * @param <T>
     * @return
     */
    <T> Command<T> getService();

    /**
     * 是否是只读对象
     * @return true：只读对象；false：非只读对象
     */
    boolean isReadOnly();

}

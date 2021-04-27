package com.wuda.foundation.lang;

import com.wuda.foundation.lang.datatype.DataType;
import com.wuda.foundation.lang.datatype.DataTypeRegistry;
import com.wuda.foundation.lang.datatype.mysql.MySQLDataType;
import com.wuda.foundation.lang.identify.BuiltinIdentifierType;
import com.wuda.foundation.lang.identify.IdentifierType;
import com.wuda.foundation.lang.identify.IdentifierTypeRegistry;
import com.wuda.foundation.lang.keygen.KeyGenerator;
import com.wuda.foundation.lang.tree.RootTreeNodeSelectCondition;
import com.wuda.foundation.lang.tree.TreeNode;

import java.util.Objects;

/**
 * Foundation框架的配置.在系统启动之前,必须先完成配置.
 *
 * @author wuda
 * @since 1.0.0
 */
public class FoundationConfiguration {

    /**
     * 禁止外部实例化.可以使用{@link #getGlobalSingletonInstance()}获取实例.
     */
    private FoundationConfiguration() {

    }

    /**
     * 单例的实例.
     */
    private static FoundationConfiguration instance;

    /**
     * 获取全局唯一的实例.
     *
     * @return 单例的实例.
     */
    public synchronized static FoundationConfiguration getGlobalSingletonInstance() {
        if (instance == null) {
            instance = new FoundationConfiguration();
            instance.configureBuiltinIdentifierType();
            instance.configureBuiltinDataType();
        }
        return instance;
    }

    /**
     * long类型Id生成器.
     */
    private KeyGenerator<Long> longKeyGenerator;
    /**
     * 根节点选择条件.
     */
    private RootTreeNodeSelectCondition<Long, TreeNode<Long>> rootTreeNodeSelectCondition = (treeNode -> treeNode.getPid().equals(Constant.NOT_EXISTS_ID));

    private IdentifierTypeRegistry identifierTypeRegistry = new IdentifierTypeRegistry();
    private DataTypeRegistry dataTypeRegistry = new DataTypeRegistry();

    /**
     * 设置key generator.
     *
     * @param longKeyGenerator key generator,用于生成long类型的key
     */
    public void setLongKeyGenerator(KeyGenerator<Long> longKeyGenerator) {
        this.longKeyGenerator = longKeyGenerator;
    }

    /**
     * 获取long类型的key generator.
     *
     * @return key generator,用于生成long类型的key
     */
    public KeyGenerator<Long> getLongKeyGenerator() {
        return Objects.requireNonNull(longKeyGenerator, "请先配置Id生成器");
    }

    /**
     * 获取{@link IdentifierTypeRegistry}.
     *
     * @return {@link IdentifierTypeRegistry}
     */
    public IdentifierTypeRegistry getIdentifierTypeRegistry() {
        return identifierTypeRegistry;
    }

    /**
     * 获取{@link DataTypeRegistry}.
     *
     * @return {@link DataTypeRegistry}
     */
    public DataTypeRegistry getDataTypeRegistry() {
        return dataTypeRegistry;
    }

    /**
     * 获取根节点选择器,根节点的数据类型为long类型。
     *
     * @return condition
     */
    public RootTreeNodeSelectCondition<Long, TreeNode<Long>> getRootTreeNodeSelectCondition() {
        return rootTreeNodeSelectCondition;
    }

    /**
     * 设置根节点选择器,根节点的数据类型为long类型。
     *
     * @param rootTreeNodeSelectCondition condition
     */
    public void setRootTreeNodeSelectCondition(RootTreeNodeSelectCondition<Long, TreeNode<Long>> rootTreeNodeSelectCondition) {
        this.rootTreeNodeSelectCondition = Objects.requireNonNull(rootTreeNodeSelectCondition);
    }

    /**
     * 注册{@link IdentifierType}.
     *
     * @param identifierType identifier type
     */
    public void registerIdentifierType(IdentifierType identifierType) {
        if (identifierType != null) {
            identifierTypeRegistry.register(identifierType);
        }
    }

    /**
     * 注册{@link DataType}.
     *
     * @param dataType data type
     */
    public void registerDataType(DataType dataType) {
        if (dataType != null) {
            dataTypeRegistry.register(dataType);
        }
    }

    /**
     * 配置{@link BuiltinIdentifierType}.
     */
    private void configureBuiltinIdentifierType() {
        BuiltinIdentifierType[] builtinIdentifierTypes = BuiltinIdentifierType.values();
        if (builtinIdentifierTypes.length > 0) {
            for (BuiltinIdentifierType builtinIdentifierType : builtinIdentifierTypes) {
                identifierTypeRegistry.register(builtinIdentifierType);
            }
        }
    }

    /**
     * 配置{@link BuiltinIdentifierType}.
     */
    private void configureBuiltinDataType() {
        MySQLDataType[] mySQLDataTypes = MySQLDataType.values();
        if (mySQLDataTypes.length > 0) {
            for (MySQLDataType mySQLDataType : mySQLDataTypes) {
                dataTypeRegistry.register(mySQLDataType);
            }
        }
    }
}

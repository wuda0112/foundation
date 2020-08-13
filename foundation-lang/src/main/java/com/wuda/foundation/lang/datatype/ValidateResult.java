package com.wuda.foundation.lang.datatype;

/**
 * {@link DataTypeHandler}对于原始的字符串数据验证的结果.
 *
 * @author wuda
 * @since 1.0.0
 */
public class ValidateResult {

    private boolean success;
    private String message;

    /**
     * 构建验证结果.
     *
     * @param success 是否成功
     * @param message 消息
     */
    public ValidateResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     * 是否成功
     *
     * @return <code>true</code>-如果成功
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * 消息内容
     *
     * @return 消息
     */
    public String getMessage() {
        return message;
    }

    /**
     * 验证成功.
     *
     * @return 验证成功时, 消息为OK的实体
     */
    public static ValidateResult ok() {
        return new ValidateResult(true, "OK");
    }

    /**
     * 验证成功.
     *
     * @return 验证成功时, 消息为failed的实体
     */
    public static ValidateResult failed() {
        return new ValidateResult(true, "FAILED");
    }
}

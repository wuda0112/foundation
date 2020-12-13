package com.wuda.foundation.core.security;

/**
 * 检查{@link Target}是否包含{@link Action}.
 * <p>
 * 比如
 * <ul>
 * <li>{@link Target}是文件,{@link Action}是write,则包含</li>
 * <li>{@link Target}是文件,{@link Action}是"浇水"(随便想的一个动作),则不包含</li>
 * </ul>
 *
 * @author wuda
 * @since 1.0.3
 */
@Deprecated
// todo 暂时先留着,看后续是否能派上用场
public interface TargetContainsActionChecker {

    /**
     * {@link Target}是否包含{@link Action}.
     *
     * @param target target
     * @param action action
     * @return <code>true</code>-如果包含
     */
    boolean contains(Target target, Action action);
}

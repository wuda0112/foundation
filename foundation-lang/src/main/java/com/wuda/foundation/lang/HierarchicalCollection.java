package com.wuda.foundation.lang;

import com.wuda.foundation.lang.identify.Identifier;

import java.io.File;
import java.util.List;

/**
 * 这个类的主要作用是为了表示类似于文件系统的结构. collection类似于文件夹,element类似于文件,
 * 在文件系统中,文件夹下有文件也有子文件夹,同时文件夹之间是有树形结构层次的.类似的,collection
 * 下有element也有sub collection,并且collection之间也有树形结构层次关系.
 * 还有一个很好的例子,比如我们Java程序的Package和Class的关系,Package下有Class也有sub package,
 * 想象下你的编程工具(比如Eclipse)展示的结构,也想象下文件系统的目录结构,就知道这个类所表示的结构了.
 *
 * @param <A> 集合中子集合的类型
 * @param <B> 集合中元素的类型
 * @param <C> 集合的唯一标记符的类型
 * @param <D> 集合中的元素的唯一标记符的类型
 * @author wuda
 * @since 1.0.3
 */
public interface HierarchicalCollection<A extends HierarchicalCollection<A, B, C, D>, B extends HierarchicalCollection.Element<C,D>, C, D> {

    /**
     * 这个集合的唯一标记,比如对于文件夹,路径就是它的唯一标记,
     * 一个路径唯一代表一个文件夹.
     *
     * @return 集合的唯一标记符
     */
    Identifier<C> identifier();

    /**
     * 列出该集合下的所有子集合(不包含更下级的集合).
     * 就好像是列出一个文件夹下的子文件夹(不包含更更下级的文件夹).
     *
     * @return 所有子集合
     */
    List<A> subCollections();

    /**
     * 列出该集合下的所有元素(不包含更下级的元素).
     * 就好像列出文件夹下的文件,和{@link File#listFiles()}的定义一样.
     *
     * @return 集合下的所有元素
     */
    List<B> listElements();

    /**
     * 集合中的元素.
     *
     * @param <C> 集合的唯一标记符的类型
     * @param <D> 集合中的元素的唯一标记符的类型
     */
    interface Element<C, D> {

        /**
         * 这个元素的唯一标记,比如对于文件,路径就是它的唯一标记,
         * 一个路径唯一代表一个文件.
         *
         * @return 元素的唯一标记符
         */
        Identifier<D> identifier();

        /**
         * 包含当前元素的集合的唯一标记.比如假设当前元素是文件,
         * 则返回的是包含这个文件的文件夹的唯一标记..
         *
         * @return 集合的唯一标记符
         */
        Identifier<C> getCollectionIdentifier();
    }
}

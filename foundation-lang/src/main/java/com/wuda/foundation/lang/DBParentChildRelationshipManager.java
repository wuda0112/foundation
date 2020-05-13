package com.wuda.foundation.lang;

import com.wuda.foundation.lang.utils.OrderBy;

import java.util.List;

/**
 * 在数据库中,我们经常会保存树形结构的数据,最常见的就是分类表,比如
 * <pre>
 *     CREATE TABLE category(
 *          category_id INT AUTO_INCREMENT PRIMARY KEY,
 *          name VARCHAR(20) NOT NULL,
 *          parent_id INT DEFAULT NULL
 *    );
 * </pre>
 * 这样的表结构设计,在数据库中数据是一个扁平的集合,但是完全可以生成一个树形结构,比如
 * <pre>
 *     中国
 *      - 湖南省
 *              - 长沙市
 *                      - 长沙县
 *                              - 泉塘
 *                      - 天心区
 *                      - 岳麓区
 *                      - 芙蓉区
 *              - 张家界市
 *                         - 桑植县
 *                                  - 澧源镇
 *                                          - 岩娅村
 *                                  - 瑞塔铺
 *                         - 慈利县
 *      - 广东省
 *              - 广州市
 *                      - 天河区
 *                      - 越秀区
 * </pre>
 * 这种表结构的设计,通常都有一个<i>parent_id</i>(也可能不是这个名词)指向父级.
 * 对于这种结构的表,我们可以统一处理这些结构化的关系,比如生成树形结构,检查上下级关系的正确性等等.
 *
 * @param <T> 该表对应的实体的类型.因为通常每个表都会有一个对应的entity模型
 * @see <a href="http://web.archive.org/web/20110606032941/http://dev.mysql.com/tech-resources/articles/hierarchical-data.html">Managing Hierarchical Data in MySQL</a>
 */
public interface DBParentChildRelationshipManager<T> {

    /**
     * 我们把表中每条记录看成一个节点,获取给定ID的节点所在的那棵子树的完整结构.
     * <ul>
     * <li>如果这个ID是根节点的ID,那么就是返回整棵树,比如上面的地区树形结构中,给定"中国"，那么所有行政地区都应该放回</li>
     * <li>如果这个节点是中间的一个节点,那么它的所有直系上级,和它的所有下级会被返回.比如上面的地区树形结构中,给定"长沙市",那么所有直系上级就是【中国,湖南省】,【广东省】就不是它的直系上级,
     * 它的所有下级,包括直接下级【长沙县,天心区,岳麓区,芙蓉区】,以及下级的下级【泉塘】</li>
     * </ul>
     *
     * @param table             数据库表名
     * @param id                表中主键ID的值,用树的概念来说,就是指定了一个节点,那么自然就限定了取树中的哪一棵子树
     * @param superiorColumn    表示上下级关系中,指向上级的列,比如parent_id
     * @param subordinateColumn 表示上下级关系中,代表下级的列,比如category_id
     * @param retrieveColumns   取回表中的哪些字段
     * @param orderByColumns    树形结构中,相同层级之间的排序
     * @return 整棵树
     */
    List<T> getSubTree(String table, Integer id, String superiorColumn, String subordinateColumn, List<String> retrieveColumns, List<OrderBy> orderByColumns);
}

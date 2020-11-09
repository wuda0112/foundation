package com.wuda.foundation.lang.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 可以展示树形结构的类.该类主要的用法就是方便把树形结构生成Json,
 * 供Html等前端程序展示树形结构,理解这个类最直接的方式就是使用jackson等Json库输出Json,
 * 查看该Json的格式就可以很好理解了.比如
 * <pre>
 * <block>
 * {
 * 	"node": {
 * 		"name": "湖南省",
 * 		"id": 2,
 * 		"pid": 1
 *        },
 * 	"children": [{
 * 		"node": {
 * 			"name": "张家界市",
 * 			"id": 3,
 * 			"pid": 2
 *        },
 * 		"children": [{
 * 			"node": {
 * 				"name": "桑植县",
 * 				"id": 5,
 * 				"pid": 3
 *            },
 * 			"children": null
 *        }]
 *    }, {
 * 		"node": {
 * 			"name": "长沙市",
 * 			"id": 4,
 * 			"pid": 2
 *        },
 * 		"children": null
 *    }]
 * }
 * </block>
 * </pre>
 *
 * @author wuda
 * @since 1.0.3
 */
public class Treeable<T extends Comparable<T>, N extends IdPidEntry<T>> implements Serializable {
    /**
     * 当前节点.
     */
    private N node;
    /**
     * 当前节点的下级.
     */
    private List<Treeable<T, N>> children;

    /**
     * set node.
     *
     * @param node node
     */
    public void setNode(N node) {
        this.node = node;
    }

    /**
     * get node.
     *
     * @return node
     */
    public N getNode() {
        return node;
    }

    /**
     * add child.
     *
     * @param child child
     */
    public void addChild(Treeable<T, N> child) {
        if (this.children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
    }

    /**
     * get children.
     *
     * @return children
     */
    public List<Treeable<T, N>> getChildren() {
        return children;
    }
}

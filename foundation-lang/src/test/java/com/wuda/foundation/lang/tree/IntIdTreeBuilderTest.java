package com.wuda.foundation.lang.tree;

import org.junit.Test;

import java.util.List;

public class IntIdTreeBuilderTest extends IntIdTreeTestBase {

    @Test
    public void test() {
        List<MyTreeNode> nodes = getNodes();
        Tree<Integer, MyTreeNode> tree = new MappedTree<>(china);
        IdPidEntryTreeBuilder<Integer, MyTreeNode> builder = new IdPidEntryTreeBuilder<>();
        builder.add(tree, nodes);
        TreeUtils.print(tree, china.getId());
    }
}

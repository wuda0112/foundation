package com.wuda.foundation.lang.tree;

import org.junit.Test;

import java.util.List;

public class IntIdTreeBuilderTest extends IntIdTreeTestBase {

    @Test
    public void test() {
        List<MyTreeNode> nodes = getNodes();
        generateTree(nodes);
    }

}

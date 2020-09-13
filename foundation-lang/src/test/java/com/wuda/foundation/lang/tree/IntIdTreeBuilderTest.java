package com.wuda.foundation.lang.tree;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.List;

public class IntIdTreeBuilderTest extends IntIdTreeTestBase {

    @Test
    public void test() {
        List<MyTreeNode> nodes = getNodes();
        MappedTree<Integer, MyTreeNode> tree = new MappedTree<>(china);
        IdPidEntryTreeBuilder<Integer, MyTreeNode> builder = new IdPidEntryTreeBuilder<>();
        builder.add(tree, nodes);
        TreeUtils.print(tree, china.getId());

        Treeable<Integer,MyTreeNode> treeable = tree.getRootTreeable();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(treeable);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

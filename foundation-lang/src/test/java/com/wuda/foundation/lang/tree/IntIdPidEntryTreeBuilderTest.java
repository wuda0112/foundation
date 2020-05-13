package com.wuda.foundation.lang.tree;

import org.junit.Test;

import java.util.List;
import java.util.function.Function;

public class IntIdPidEntryTreeBuilderTest extends IntIdTreeTestBase {

    @Test
    public void test() {
        Function<IntIdPidEntry, MyTreeElement> mapper = intIdPidElement -> new MyTreeElement(intIdPidElement.getId(), intIdPidElement.getName());
        List<IntIdPidEntry> elements = getElements();
        Tree<Integer, MyTreeElement> tree = new MappedTree<>(mapper.apply(china));
        IdPidEntryTreeBuilder<Integer, IntIdPidEntry, MyTreeElement> builder = new IdPidEntryTreeBuilder<>();
        builder.add(tree, elements, mapper);
        printf(tree, china.getId());
    }
}

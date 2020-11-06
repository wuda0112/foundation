package com.wuda.foundation.lang.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IdPidEntryTest extends IntIdTreeTestBase {

    public void getDescendantTest() {
        MyTreeNode root = us;

        List<MyTreeNode> treeNodes = getNodes();
        Collections.shuffle(treeNodes);
        Collections.shuffle(treeNodes);
        Collections.shuffle(treeNodes);

        List<MyTreeNode> descendant = new ArrayList<>();
        IdPidEntryUtils.getDescendant(root.getId(), treeNodes, descendant);

        List<MyTreeNode> list = new ArrayList<>(descendant);
        list.add(root);

        generateTree(list);
    }

    public void getAncestorTest() {
        MyTreeNode node = sangzhi;

        List<MyTreeNode> treeNodes = getNodes();
        Collections.shuffle(treeNodes);
        Collections.shuffle(treeNodes);
        Collections.shuffle(treeNodes);

        List<MyTreeNode> ancestor = new ArrayList<>();
        IdPidEntryUtils.getAncestor(node.getId(), treeNodes, ancestor);

        generateTree(ancestor);

    }

    public void getTopTest() {

        List<MyTreeNode> treeNodes = getNodes();
        Collections.shuffle(treeNodes);
        Collections.shuffle(treeNodes);
        Collections.shuffle(treeNodes);

        treeNodes.remove(china);
        treeNodes.remove(hunan_province);
        treeNodes.remove(zhangjiajie);

        List<MyTreeNode> topList = new ArrayList<>();
        Set<Integer> topIdSet = new HashSet<>();
        IdPidEntryUtils.getTop(treeNodes, topIdSet);
        Map<Integer, MyTreeNode> nodeByIdMap = IdPidEntryUtils.groupById(treeNodes);
        for (Integer id : topIdSet) {
            MyTreeNode myTreeNode = nodeByIdMap.get(id);
            topList.add(myTreeNode);
        }
        System.out.println(topList);

    }

    public static void main(String[] args) {
        IdPidEntryTest idPidEntryTest = new IdPidEntryTest();
        idPidEntryTest.getDescendantTest();
        idPidEntryTest.getAncestorTest();
        idPidEntryTest.getTopTest();
    }
}

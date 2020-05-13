package com.wuda.foundation.lang.tree;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class IntIdTreeTestBase {

    int rootId = Integer.MIN_VALUE / 2;

    IntIdPidEntry china = new IntIdPidEntry(1, rootId, "中国");

    IntIdPidEntry hunan_province = new IntIdPidEntry(2, 1, "湖南省");
    IntIdPidEntry zhangjj = new IntIdPidEntry(3, 2, "张家界市");
    IntIdPidEntry changsha = new IntIdPidEntry(4, 2, "长沙市");
    IntIdPidEntry sangzhi = new IntIdPidEntry(5, 3, "桑植县");

    IntIdPidEntry guangdong_province = new IntIdPidEntry(7, 1, "广东省");
    IntIdPidEntry guangzhou = new IntIdPidEntry(8, 7, "广州市");
    IntIdPidEntry tianhe = new IntIdPidEntry(12, 8, "天河区");
    IntIdPidEntry dongguan = new IntIdPidEntry(9, 7, "东莞市");

    IntIdPidEntry hubei_province = new IntIdPidEntry(10, 1, "湖北省");
    IntIdPidEntry yunan_province = new IntIdPidEntry(11, 1, "云南省");

    public List<IntIdPidEntry> getElements() {
        List<IntIdPidEntry> list = new ArrayList<>();
        list.add(china);
        list.add(hunan_province);
        list.add(zhangjj);
        list.add(changsha);
        list.add(sangzhi);
        list.add(guangdong_province);
        list.add(guangzhou);
        list.add(dongguan);
        list.add(hubei_province);
        list.add(yunan_province);
        return list;
    }

    @Getter
    @Setter
    static class IntIdPidEntry implements IdPidEntry<Integer> {

        String name;
        private int id;
        private int pid;

        IntIdPidEntry(int id, int pid, String name) {
            this.id = id;
            this.pid = pid;
            this.name = name;
        }

        @Override
        public Integer getId() {
            return id;
        }

        @Override
        public Integer getPid() {
            return pid;
        }
    }

    @Getter
    @Setter
    static class MyTreeElement implements TreeElement<Integer> {

        String name;
        private int id;

        MyTreeElement(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public Integer getIdentifier() {
            return id;
        }
    }

    /**
     * 打印,从给定的ID所代表的节点开始.
     *
     * @param tree tree
     * @param id   element id
     */
    void printf(Tree<Integer, MyTreeElement> tree, int id) {
        int depth = tree.getDepth(id);
        String tab = "\t";
        for (int i = 0; i < depth; i++) {
            tab += "\t";
        }
        MyTreeElement element = tree.get(id);
        System.out.println(tab + element.getName());
        Set<MyTreeElement> children = tree.getDirectChildren(id);
        if (children == null || children.size() == 0) {
            return;
        }
        Set<Integer> childrenIdSet = new HashSet<>(children.size());
        for(MyTreeElement child : children){
            childrenIdSet.add(child.getIdentifier());
        }
        for (int child : childrenIdSet) {
            printf(tree, child);
        }
    }
}

package com.wuda.foundation.lang.tree;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

class IntIdTreeTestBase {

    MyTreeNode china = new MyTreeNode(1, -1, "中国");

    MyTreeNode hunan_province = new MyTreeNode(2, 1, "湖南省");
    MyTreeNode zhangjiajie = new MyTreeNode(3, 2, "张家界市");
    MyTreeNode changsha = new MyTreeNode(4, 2, "长沙市");
    MyTreeNode sangzhi = new MyTreeNode(5, 3, "桑植县");

    MyTreeNode guangdong_province = new MyTreeNode(7, 1, "广东省");
    MyTreeNode guangzhou = new MyTreeNode(8, 7, "广州市");
    MyTreeNode tianhe = new MyTreeNode(12, 8, "天河区");
    MyTreeNode dongguan = new MyTreeNode(9, 7, "东莞市");

    MyTreeNode hubei_province = new MyTreeNode(10, 1, "湖北省");
    MyTreeNode yunan_province = new MyTreeNode(11, 1, "云南省");

    //------------------------------------------------------------------------------------------------------------------------------------------------

    MyTreeNode us = new MyTreeNode(101, -2, "US");

    MyTreeNode texas = new MyTreeNode(102, 101, "texas");
    MyTreeNode florida = new MyTreeNode(103, 101, "florida");

    public List<MyTreeNode> getNodes() {
        List<MyTreeNode> list = new ArrayList<>();
        list.add(china);
        list.add(hunan_province);
        list.add(zhangjiajie);
        list.add(sangzhi);
        list.add(changsha);
        list.add(guangdong_province);
        list.add(guangzhou);
        list.add(tianhe);
        list.add(dongguan);
        list.add(hubei_province);
        list.add(yunan_province);

        //-----------------------------------------------------------------
        list.add(texas);
        list.add(us);
        list.add(florida);
        return list;
    }

    @ToString
    @Getter
    @Setter
    static class MyTreeNode implements TreeNode<Integer> {

        String name;
        private int id;
        private int pid;

        MyTreeNode(int id, int pid, String name) {
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

    public void generateTree(List<MyTreeNode> nodes) {
        RootTreeNodeSelector rootTreeNodeSelector = new RootTreeNodeSelector();
        List<MyTreeNode> roots = rootTreeNodeSelector.select(myTreeNode -> myTreeNode.getPid() < 0, nodes);
        for(MyTreeNode root : roots){
            MappedTree<Integer, MyTreeNode> tree = new MappedTree<>(root);
            IdPidEntryTreeBuilder<Integer, MyTreeNode> builder = new IdPidEntryTreeBuilder<>();
            builder.add(tree, nodes);
            TreeUtils.print(tree, root.getId());

            Treeable<Integer, MyTreeNode> treeable = tree.toTreeable(hunan_province.id);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String json = objectMapper.writeValueAsString(treeable);
                System.out.println(json);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}

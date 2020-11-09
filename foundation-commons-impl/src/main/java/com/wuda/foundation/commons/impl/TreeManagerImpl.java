package com.wuda.foundation.commons.impl;

import com.wuda.foundation.commons.AbstractTreeManager;
import com.wuda.foundation.commons.CreateTreeNode;
import com.wuda.foundation.commons.DescribeTreeNode;
import com.wuda.foundation.commons.UpdateTreeNode;
import com.wuda.foundation.commons.impl.jooq.generation.tables.records.TreeNodeRecord;
import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.AlreadyExistsException;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;
import com.wuda.foundation.lang.IsDeleted;
import com.wuda.foundation.lang.RelatedDataExists;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.jooq.types.UByte;
import org.jooq.types.ULong;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.wuda.foundation.commons.impl.jooq.generation.tables.TreeNode.TREE_NODE;

public class TreeManagerImpl extends AbstractTreeManager implements JooqCommonDbOp {

    @Override
    public CreateResult createTreeNodeDbOp(CreateTreeNode createTreeNode, CreateMode createMode, Long opUserId) {
        SelectConditionStep<Record1<ULong>> existsRecordSelector = selectCondition(createTreeNode.getId(), createTreeNode.getParentId());
        return insertDispatcher(JooqContext.getDataSource(), createMode, TREE_NODE, treeNodeRecordForInsert(createTreeNode, opUserId), existsRecordSelector);
    }

    @Override
    public void updateTreeNodeDbOp(UpdateTreeNode updateTreeNode, Long opUserId) {

    }

    @Override
    protected void deleteTreeNodeDbOp(Long nodeId, Long opUserId) throws RelatedDataExists {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        dslContext.update(TREE_NODE)
                .set(TREE_NODE.IS_DELETED, TREE_NODE.TREE_NODE_ID)
                .where(TREE_NODE.TREE_NODE_ID.eq(ULong.valueOf(nodeId)))
                .execute();
    }

    /**
     * 用于唯一性查询的条件.
     *
     * @param parentNodeId 父节点
     * @param nodeId       子节点
     * @return {@link SelectConditionStep}
     */
    private SelectConditionStep<Record1<ULong>> selectCondition(Long nodeId, Long parentNodeId) {
        Configuration configuration = JooqContext.getConfiguration(JooqContext.getDataSource());
        return DSL.using(configuration)
                .select(TREE_NODE.TREE_NODE_ID)
                .from(TREE_NODE)
                .where(TREE_NODE.PARENT_TREE_NODE_ID.eq(ULong.valueOf(parentNodeId)))
                .and(TREE_NODE.TREE_NODE_ID.eq(ULong.valueOf(nodeId)))
                .and(TREE_NODE.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
    }

    private TreeNodeRecord treeNodeRecordForInsert(CreateTreeNode createTreeNode, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new TreeNodeRecord(ULong.valueOf(createTreeNode.getId()),
                ULong.valueOf(createTreeNode.getRootId()),
                ULong.valueOf(createTreeNode.getParentId()),
                UByte.valueOf(createTreeNode.getDepth()),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    @Override
    public int childCount(Long nodeId) {
        return JooqContext.getOrCreateDSLContext(JooqContext.getDataSource())
                .fetchCount(TREE_NODE,
                        TREE_NODE.PARENT_TREE_NODE_ID.eq(ULong.valueOf(nodeId))
                                .and(TREE_NODE.IS_DELETED.eq(notDeleted())));
    }

    @Override
    public List getChildren(Long nodeId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        Result<TreeNodeRecord> records = dslContext.selectFrom(TREE_NODE)
                .where(TREE_NODE.PARENT_TREE_NODE_ID.eq(ULong.valueOf(nodeId)))
                .and(TREE_NODE.IS_DELETED.eq(notDeleted()))
                .fetch();
        return copyFromItemCategoryRecords(records);
    }

    @Override
    public DescribeTreeNode getTreeNode(Long nodeId) {
        TreeNodeRecord record = getById(nodeId, TREE_NODE);
        return copyFromTreeNodeRecord(record);
    }

    @Override
    public List getDescendantOfRoot(Long rootId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        Result<TreeNodeRecord> records = dslContext.selectFrom(TREE_NODE)
                .where(TREE_NODE.ROOT_TREE_NODE_ID.eq(ULong.valueOf(rootId)))
                .and(TREE_NODE.IS_DELETED.eq(notDeleted()))
                .fetch();
        return copyFromItemCategoryRecords(records);
    }

    @Override
    public boolean checkNameExists(Long parentId, String childName) {
        return false;
    }

    private DescribeTreeNode copyFromTreeNodeRecord(TreeNodeRecord record) {
        DescribeTreeNode describeTreeNode = new DescribeTreeNode();
        describeTreeNode.setId(record.getTreeNodeId().longValue());
        describeTreeNode.setParentId(record.getParentTreeNodeId().longValue());
        describeTreeNode.setRootId(record.getRootTreeNodeId().longValue());
        describeTreeNode.setDepth(record.getDepth().intValue());
        describeTreeNode.setName("");
        describeTreeNode.setDescription("");
        return describeTreeNode;
    }

    private List<DescribeTreeNode> copyFromItemCategoryRecords(Result<TreeNodeRecord> records) {
        if (records == null || records.isEmpty()) {
            return null;
        }
        List<DescribeTreeNode> list = new ArrayList<>();
        for (TreeNodeRecord record : records) {
            list.add(copyFromTreeNodeRecord(record));
        }
        return list;
    }
}

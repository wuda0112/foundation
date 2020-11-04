package com.wuda.foundation.commons.impl;

import com.wuda.foundation.commons.AbstractTreeManager;
import com.wuda.foundation.commons.CreateTreeNode;
import com.wuda.foundation.commons.UpdateTreeNode;
import com.wuda.foundation.commons.impl.jooq.generation.tables.records.TreeNodeRecord;
import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.*;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.jooq.types.UByte;
import org.jooq.types.ULong;

import javax.sql.DataSource;
import java.time.LocalDateTime;

import static com.wuda.foundation.commons.impl.jooq.generation.tables.TreeNode.TREE_NODE;

public class TreeManagerImpl extends AbstractTreeManager implements JooqCommonDbOp {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public CreateResult createNodeDbOp(CreateTreeNode createTreeNode, CreateMode createMode, Long opUserId) {
        SelectConditionStep<Record1<ULong>> existsRecordSelector = selectCondition(createTreeNode.getId(), createTreeNode.getParentTreeNodeId());
        return insertDispatcher(dataSource, createMode, TREE_NODE, treeNodeRecordForInsert(createTreeNode, opUserId), existsRecordSelector);
    }

    @Override
    public void updateNodeDbOp(UpdateTreeNode updateTreeNode, Long opUserId) throws AlreadyExistsException {

    }

    @Override
    protected void deleteNodeDbOp(Long nodeId, Long opUserId) throws RelatedDataExists {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        int childrenCount = dslContext.fetchCount(TREE_NODE,
                TREE_NODE.PARENT_TREE_NODE_ID.eq(ULong.valueOf(nodeId))
                        .and(TREE_NODE.IS_DELETED.eq(notDeleted())));
        if (childrenCount > 0) {
            throw new RelatedDataExists("node id = " + nodeId + ",还有子节点,不能删除");
        }
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
        Configuration configuration = JooqContext.getConfiguration(dataSource);
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
                ULong.valueOf(createTreeNode.getRootTreeNodeId()),
                ULong.valueOf(createTreeNode.getParentTreeNodeId()),
                UByte.valueOf(createTreeNode.getDepth()),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }
}

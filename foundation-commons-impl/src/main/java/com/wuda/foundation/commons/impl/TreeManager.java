package com.wuda.foundation.commons.impl;

import com.wuda.foundation.commons.AbstractTreeManager;
import com.wuda.foundation.commons.CreateTreeNode;
import com.wuda.foundation.commons.UpdateTreeNode;
import com.wuda.foundation.commons.impl.jooq.generation.tables.records.TreeNodeRecord;
import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.AlreadyExistsException;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;
import com.wuda.foundation.lang.IsDeleted;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.jooq.types.ULong;

import javax.sql.DataSource;
import java.time.LocalDateTime;

import static com.wuda.foundation.commons.impl.jooq.generation.tables.TreeNode.TREE_NODE;

public class TreeManager extends AbstractTreeManager implements JooqCommonDbOp {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public CreateResult createNodeDbOp(CreateTreeNode createTreeNode, CreateMode createMode, Long opUserId) {
        SelectConditionStep<Record1<ULong>> existsRecordSelector = selectCondition(createTreeNode.getParentNodeId(), createTreeNode.getName());
        return insertDispatcher(dataSource, createMode, TREE_NODE, treeNodeRecordForInsert(createTreeNode, opUserId), existsRecordSelector);
    }

    @Override
    public void updateNodeDbOp(UpdateTreeNode updateTreeNode, Long opUserId) throws AlreadyExistsException {
        boolean updateName = false;
        if (updateTreeNode.getName() != null) {
            updateName = true;
            TreeNodeRecord treeNodeRecord = getNodeById(updateTreeNode.getId());
            if (treeNodeRecord.getName().equals(updateTreeNode.getName())) {
                updateName = false;
            } else {
                SelectConditionStep<Record1<ULong>> existsRecordSelector = selectCondition(treeNodeRecord.getParentNodeId().longValue(), updateTreeNode.getName());
                Record1<ULong> existsRecord = existsRecordSelector.fetchOne();
                if (existsRecord != null) {
                    throw new AlreadyExistsException("和当前节点平级的节点中已经存在给定名称的节点");
                }
            }
        }
        if (!updateName && updateTreeNode.getDescription() == null) {
            return;
        }
        TreeNodeRecord treeNodeRecord = new TreeNodeRecord();
        treeNodeRecord.setTreeNodeId(ULong.valueOf(updateTreeNode.getId()));
        if (updateName) {
            treeNodeRecord.setName(updateTreeNode.getName());
        }
        if (updateTreeNode.getDescription() != null) {
            treeNodeRecord.setDescription(updateTreeNode.getDescription());
        }
        attach(dataSource, treeNodeRecord);
        treeNodeRecord.update();
    }

    /**
     * 用于查询给定的父节点下是否有给定名称的子节点的查询语句.
     *
     * @param parentNodeId  父节点
     * @param childNodeName 子节点名称
     * @return {@link SelectConditionStep}
     */
    private SelectConditionStep<Record1<ULong>> selectCondition(Long parentNodeId, String childNodeName) {
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        return DSL.using(configuration)
                .select(TREE_NODE.TREE_NODE_ID)
                .from(TREE_NODE)
                .where(TREE_NODE.PARENT_NODE_ID.eq(ULong.valueOf(parentNodeId)))
                .and(TREE_NODE.NAME.eq(childNodeName))
                .and(TREE_NODE.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
    }

    private TreeNodeRecord getNodeById(Long id) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        return dslContext.selectFrom(TREE_NODE)
                .where(TREE_NODE.TREE_NODE_ID.eq(ULong.valueOf(id)))
                .fetchOne();
    }

    private TreeNodeRecord treeNodeRecordForInsert(CreateTreeNode createTreeNode, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new TreeNodeRecord(ULong.valueOf(createTreeNode.getId()),
                createTreeNode.getName(),
                createTreeNode.getDescription(),
                ULong.valueOf(createTreeNode.getParentNodeId()),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }
}

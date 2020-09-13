package com.wuda.foundation.commons.impl;

import com.wuda.foundation.commons.AbstractTreeManager;
import com.wuda.foundation.commons.CreateTreeNode;
import com.wuda.foundation.commons.DescribeTreeNode;
import com.wuda.foundation.commons.UpdateTreeNode;
import com.wuda.foundation.commons.impl.jooq.generation.tables.records.TreeNodeRecord;
import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.*;
import com.wuda.foundation.lang.identify.IdentifierType;
import com.wuda.foundation.lang.identify.IdentifierTypeRegistry;
import com.wuda.foundation.lang.identify.LongIdentifier;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.jooq.types.UByte;
import org.jooq.types.ULong;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.wuda.foundation.commons.impl.jooq.generation.tables.TreeNode.TREE_NODE;

public class TreeManagerImpl extends AbstractTreeManager implements JooqCommonDbOp {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public CreateResult createNodeDbOp(CreateTreeNode createTreeNode, CreateMode createMode, Long opUserId) {
        SelectConditionStep<Record1<ULong>> existsRecordSelector = selectCondition(createTreeNode.getOwner(), createTreeNode.getUse().getCode(), createTreeNode.getParentNodeId(), createTreeNode.getName());
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
                LongIdentifier owner = getOwner(treeNodeRecord);
                SelectConditionStep<Record1<ULong>> existsRecordSelector = selectCondition(owner, treeNodeRecord.getUse().byteValue(), treeNodeRecord.getParentNodeId().longValue(), updateTreeNode.getName());
                Record1<ULong> existsRecord = existsRecordSelector.fetchOne();
                if (existsRecord != null) {
                    throw new AlreadyExistsException("和当前节点平级的节点中已经存在名称为" + updateTreeNode.getName() + "的节点");
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

    private LongIdentifier getOwner(TreeNodeRecord record) {
        int ownerType = record.getOwnerType().intValue();
        IdentifierType identifierType = IdentifierTypeRegistry.defaultRegistry.lookup(ownerType);
        return new LongIdentifier(record.getOwnerIendtifier().longValue(), identifierType);
    }

    @Override
    protected void deleteNodeDbOp(Long nodeId, Long opUserId) throws RelatedDataExists {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        int childrenCount = dslContext.fetchCount(TREE_NODE,
                TREE_NODE.PARENT_NODE_ID.eq(ULong.valueOf(nodeId))
                        .and(TREE_NODE.IS_DELETED.eq(notDeleted())));
        if (childrenCount > 0) {
            throw new RelatedDataExists("node id = " + nodeId + ",还有子节点,不能删除");
        }
        dslContext.update(TREE_NODE)
                .set(TREE_NODE.IS_DELETED, TREE_NODE.TREE_NODE_ID)
                .where(TREE_NODE.TREE_NODE_ID.eq(ULong.valueOf(nodeId)))
                .execute();
    }

    @Override
    protected List<DescribeTreeNode> getAllNodesDbOp() {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        List<TreeNodeRecord> records = dslContext.selectFrom(TREE_NODE)
                .where(TREE_NODE.IS_DELETED.eq(notDeleted()))
                .fetch();
        return copyFrom(records);
    }

    private DescribeTreeNode copyFrom(TreeNodeRecord record) {
        LongIdentifier owner = getOwner(record);
        return new DescribeTreeNode(record.getTreeNodeId().longValue(), record.getParentNodeId().longValue(), record.getName(), record.getDescription(), owner, record.getUse().byteValue());
    }

    private List<DescribeTreeNode> copyFrom(List<TreeNodeRecord> records) {
        if (records == null || records.isEmpty()) {
            return new ArrayList<>(1);
        }
        List<DescribeTreeNode> list = new ArrayList<>(records.size());
        for (TreeNodeRecord record : records) {
            list.add(copyFrom(record));
        }
        return list;
    }

    /**
     * 用于查询给定的父节点下是否有给定名称的子节点的查询语句.
     *
     * @param parentNodeId  父节点
     * @param childNodeName 子节点名称
     * @return {@link SelectConditionStep}
     */
    private SelectConditionStep<Record1<ULong>> selectCondition(LongIdentifier owner, Byte use, Long parentNodeId, String childNodeName) {
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        return DSL.using(configuration)
                .select(TREE_NODE.TREE_NODE_ID)
                .from(TREE_NODE)
                .where(TREE_NODE.OWNER_TYPE.eq(UByte.valueOf(owner.getType().getCode())))
                .and(TREE_NODE.OWNER_IENDTIFIER.eq(ULong.valueOf(owner.getValue())))
                .and(TREE_NODE.USE.eq(UByte.valueOf(use)))
                .and(TREE_NODE.PARENT_NODE_ID.eq(ULong.valueOf(parentNodeId)))
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
                UByte.valueOf(createTreeNode.getOwner().getType().getCode()),
                ULong.valueOf(createTreeNode.getOwner().getValue()),
                UByte.valueOf(createTreeNode.getUse().getCode()),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }
}

package com.wuda.foundation.user.impl;

import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.InsertMode;
import com.wuda.foundation.lang.IsDeleted;
import com.wuda.foundation.lang.SingleInsertResult;
import com.wuda.foundation.user.AbstractIndividualUserManager;
import com.wuda.foundation.user.CreateIndividualUserGeneral;
import com.wuda.foundation.user.UpdateIndividualUserGeneral;
import com.wuda.foundation.user.impl.jooq.generation.tables.records.IndividualUserGeneralRecord;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.types.ULong;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.wuda.foundation.user.impl.jooq.generation.tables.IndividualUserGeneral.INDIVIDUAL_USER_GENERAL;

public class IndividualUserManagerImpl extends AbstractIndividualUserManager implements JooqCommonDbOp {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = Objects.requireNonNull(dataSource);
    }

    @Override
    protected SingleInsertResult createGeneralDbOp(CreateIndividualUserGeneral createIndividualUserGeneral, InsertMode insertMode, Long opUserId) {
        IndividualUserGeneralRecord record = individualUserGeneralRecordForInsert(createIndividualUserGeneral, opUserId);
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        SelectConditionStep<Record1<ULong>> existsRecordPKSelector = dslContext
                .select(INDIVIDUAL_USER_GENERAL.INDIVIDUAL_USER_GENERAL_ID)
                .from(INDIVIDUAL_USER_GENERAL)
                .where(INDIVIDUAL_USER_GENERAL.USER_ID.equal(ULong.valueOf(createIndividualUserGeneral.getUserId())))
                .and(INDIVIDUAL_USER_GENERAL.IS_DELETED.equal(ULong.valueOf(IsDeleted.NO.getValue())));
        return insertDispatcher(dataSource, insertMode, INDIVIDUAL_USER_GENERAL, record, existsRecordPKSelector);
    }

    @Override
    protected void directBatchInsertGeneralDbOp(List<CreateIndividualUserGeneral> createIndividualUserGenerals, Long opUserId) {
        batchInsert(dataSource, INDIVIDUAL_USER_GENERAL, individualUserGeneralRecordForInsert(createIndividualUserGenerals, opUserId));
    }

    @Override
    protected void updateGeneralDbOp(UpdateIndividualUserGeneral updateIndividualUserGeneral, Long opUserId) {
        IndividualUserGeneralRecord record = individualUserGeneralRecordForUpdate(updateIndividualUserGeneral, opUserId);
        attach(dataSource, record);
        record.update();
    }

    private IndividualUserGeneralRecord individualUserGeneralRecordForInsert(CreateIndividualUserGeneral createIndividualUserGeneral, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new IndividualUserGeneralRecord(ULong.valueOf(createIndividualUserGeneral.getId()),
                ULong.valueOf(createIndividualUserGeneral.getUserId()),
                createIndividualUserGeneral.getNickname(),
                createIndividualUserGeneral.getBiography(),
                createIndividualUserGeneral.getPicture(),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue())
        );
    }

    private List<IndividualUserGeneralRecord> individualUserGeneralRecordForInsert(List<CreateIndividualUserGeneral> createIndividualUserGenerals, Long opUserId) {
        List<IndividualUserGeneralRecord> list = new ArrayList<>(createIndividualUserGenerals.size());
        for (CreateIndividualUserGeneral createIndividualUserGeneral : createIndividualUserGenerals) {
            IndividualUserGeneralRecord record = individualUserGeneralRecordForInsert(createIndividualUserGeneral, opUserId);
            list.add(record);
        }
        return list;
    }

    private IndividualUserGeneralRecord individualUserGeneralRecordForUpdate(UpdateIndividualUserGeneral updateIndividualUserGeneral, Long opUserId) {
        IndividualUserGeneralRecord record = new IndividualUserGeneralRecord();
        record.setIndividualUserGeneralId(ULong.valueOf(updateIndividualUserGeneral.getId()));
        if (updateIndividualUserGeneral.getBiography() != null) {
            record.setBiography(updateIndividualUserGeneral.getBiography());
        }
        if (updateIndividualUserGeneral.getNickname() != null) {
            record.setNickname(updateIndividualUserGeneral.getNickname());
        }
        if (updateIndividualUserGeneral.getPicture() != null) {
            record.setPicture(updateIndividualUserGeneral.getPicture());
        }
        record.setLastModifyTime(LocalDateTime.now());
        record.setLastModifyUserId(ULong.valueOf(opUserId));
        return record;
    }
}

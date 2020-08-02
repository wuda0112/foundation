package com.wuda.foundation.user;

import com.wuda.foundation.lang.InsertMode;
import com.wuda.foundation.lang.SingleInsertResult;

import java.util.List;

public abstract class AbstractIndividualUserManager implements IndividualUserManager {
    @Override
    public SingleInsertResult createGeneral(CreateIndividualUserGeneral createIndividualUserGeneral, InsertMode insertMode, Long opUserId) {
        return createGeneralDbOp(createIndividualUserGeneral, insertMode, opUserId);
    }

    protected abstract SingleInsertResult createGeneralDbOp(CreateIndividualUserGeneral createIndividualUserGeneral, InsertMode insertMode, Long opUserId);

    @Override
    public void directBatchInsertGeneral(List<CreateIndividualUserGeneral> createIndividualUserGenerals, Long opUserId) {
        directBatchInsertGeneralDbOp(createIndividualUserGenerals, opUserId);
    }

    protected abstract void directBatchInsertGeneralDbOp(List<CreateIndividualUserGeneral> createIndividualUserGenerals, Long opUserId);

    @Override
    public void updateGeneral(UpdateIndividualUserGeneral updateIndividualUserGeneral, Long opUserId) {
        updateGeneralDbOp(updateIndividualUserGeneral, opUserId);
    }

    protected abstract void updateGeneralDbOp(UpdateIndividualUserGeneral updateIndividualUserGeneral, Long opUserId);
}

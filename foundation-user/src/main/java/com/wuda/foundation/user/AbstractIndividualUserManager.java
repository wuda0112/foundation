package com.wuda.foundation.user;

import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;

import java.util.List;

public abstract class AbstractIndividualUserManager implements IndividualUserManager {
    @Override
    public CreateResult createGeneral(CreateIndividualUserGeneral createIndividualUserGeneral, CreateMode createMode, Long opUserId) {
        return createGeneralDbOp(createIndividualUserGeneral, createMode, opUserId);
    }

    protected abstract CreateResult createGeneralDbOp(CreateIndividualUserGeneral createIndividualUserGeneral, CreateMode createMode, Long opUserId);

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

package com.wuda.foundation.security;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class PermissionGrantRequestTest {

    @Test
    public void test() {
        Subject subject_1 = new Subject() {
            @Override
            public Long getIdentifier() {
                return 1L;
            }

            @Override
            public SubjectType getType() {
                return null;
            }
        };
        SubjectPermissionGrantRequest one = new SubjectPermissionGrantRequest(subject_1, 1L);

        List<Long> actionList_1 = Arrays.asList(1L, 2L);
        SubjectPermissionGrantRequest two = new SubjectPermissionGrantRequest(subject_1, 1L, new HashSet<>(actionList_1));

        List<Long> actionList_2 = Arrays.asList(1L, 3L);
        SubjectPermissionGrantRequest three = new SubjectPermissionGrantRequest(subject_1, 1L, new HashSet<>(actionList_2));

        Subject subject_2 = new Subject() {
            @Override
            public Long getIdentifier() {
                return 2L;
            }

            @Override
            public SubjectType getType() {
                return null;
            }
        };
        List<Long> actionList_3 = Arrays.asList(1L, 3L);
        SubjectPermissionGrantRequest four = new SubjectPermissionGrantRequest(subject_2, 1L, new HashSet<>(actionList_3));

        List<SubjectPermissionGrantRequest> list = (List<SubjectPermissionGrantRequest>) PermissionGrantRequest.merge(Arrays.asList(one, two, three, four));
        System.out.println(list);
    }
}

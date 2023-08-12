package com.librebuy.core.jpa;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class P6SpyAppenderTest {


    @Test
    public void removeAliasesTest() {
        final P6SpyAppender p6spy = new P6SpyAppender();
        String hibernateQuery, expectedQuery;

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //                                 PROSTE ZAPYTANIA (Z JEDNEJ TABELI)                                         //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        hibernateQuery = "select u1_0.id,u1_0.hashed_password,u1_0.name,u1_0.profile_picture,u1_0.username,u1_0.version from application_user u1_0 where u1_0.username='admin'";
        expectedQuery = "select id,hashed_password,name,profile_picture,username,version from application_user where username='admin'";
        assertThat(p6spy.optimizeSqlAliases(hibernateQuery)).as("SIMPLE QUERY 1").isEqualTo(expectedQuery);
    }

}
/**
 * Copyright (c) 2010, Lukas Eder, lukas.eder@gmail.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * . Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * . Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * . Neither the name of the "jOOQ" nor the names of its contributors may be
 *   used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.jooq.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jooq.CaseConditionStep;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.SQLDialect;

class CaseConditionStepImpl<T> extends FieldImpl<T> implements CaseConditionStep<T> {

    /**
     * Generated UID
     */
    private static final long     serialVersionUID = -1735676153683257465L;
    private final List<Condition> conditions;
    private final List<Field<T>>  results;
    private Field<T> otherwise;

    public CaseConditionStepImpl(SQLDialect dialect, String name, Class<? extends T> type, Condition condition,
        Field<T> result) {

        super(dialect, name, type);

        this.conditions = new ArrayList<Condition>();
        this.results = new ArrayList<Field<T>>();

        when(condition, result);
    }

    private <Z> Field<Z> constant(Z value) {
        return new FunctionFactory(getDialect()).constant(value);
    }

    @Override
    public CaseConditionStep<T> when(Condition condition, T result) {
        return when(condition, constant(result));
    }

    @Override
    public CaseConditionStep<T> when(Condition condition, Field<T> result) {
        conditions.add(condition);
        results.add(result);

        return this;
    }

    @Override
    public Field<T> otherwise(T result) {
        return otherwise(constant(result));
    }

    @Override
    public Field<T> otherwise(Field<T> result) {
        this.otherwise = result;

        return this;
    }

    @Override
    public int bind(PreparedStatement stmt, int initialIndex) throws SQLException {
        int result = initialIndex;

        for (int i = 0; i < conditions.size(); i++) {
            result = conditions.get(i).bind(stmt, result);
            result = results.get(i).bind(stmt, result);
        }

        if (otherwise != null) {
            result = otherwise.bind(stmt, result);
        }
        return result;
    }

    @Override
    public String toSQLReference(boolean inlineParameters) {
        StringBuilder sb = new StringBuilder();
        sb.append("case");

        for (int i = 0; i < conditions.size(); i++) {
            sb.append(" when ");
            sb.append(conditions.get(i).toSQLReference(inlineParameters));
            sb.append(" then ");
            sb.append(results.get(i).toSQLReference(inlineParameters));
        }

        if (otherwise != null) {
            sb.append(" else ");
            sb.append(otherwise.toSQLReference(inlineParameters));
        }

        sb.append(" end");
        return sb.toString();
    }
}
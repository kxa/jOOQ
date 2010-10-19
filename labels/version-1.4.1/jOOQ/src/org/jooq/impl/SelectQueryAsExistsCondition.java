/**
 * Copyright (c) 2009, Lukas Eder, lukas.eder@gmail.com
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

import org.jooq.ExistsOperator;
import org.jooq.ResultProviderQuery;
import org.jooq.SQLDialect;

/**
 * @author Lukas Eder
 */
class SelectQueryAsExistsCondition extends AbstractCondition {

    private static final long            serialVersionUID = 5678338161136603292L;
    private final ResultProviderQuery<?> query;
    private final ExistsOperator         operator;

    SelectQueryAsExistsCondition(SQLDialect dialect, ResultProviderQuery<?> query, ExistsOperator operator) {
        super(dialect);

        this.query = query;
        this.operator = operator;
    }

    @Override
    public String toSQLReference(boolean inlineParameters) {
        StringBuilder sb = new StringBuilder();

        sb.append(operator.toSQL());
        sb.append(" (");
        sb.append(query.toSQLReference(inlineParameters));
        sb.append(")");

        return sb.toString();
    }

    @Override
    public int bind(PreparedStatement stmt, int initialIndex) throws SQLException {
        return query.bind(stmt, initialIndex);
    }
}
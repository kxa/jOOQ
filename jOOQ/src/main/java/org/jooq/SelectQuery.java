/**
 * Copyright (c) 2009-2012, Lukas Eder, lukas.eder@gmail.com
 * All rights reserved.
 *
 * This software is licensed to you under the Apache License, Version 2.0
 * (the "License"); You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
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
 * . Neither the name "jOOQ" nor the names of its contributors may be
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

package org.jooq;

import static org.jooq.SQLDialect.CUBRID;
import static org.jooq.SQLDialect.ORACLE;

import java.util.Collection;

import org.jooq.exception.DataAccessException;

/**
 * A query for data selection
 *
 * @author Lukas Eder
 */
public interface SelectQuery extends SimpleSelectQuery<Record> {

    /**
     * Add a list of select fields
     *
     * @param fields
     */
    @Support
    void addSelect(Field<?>... fields);

    /**
     * Add a list of select fields
     *
     * @param fields
     */
    @Support
    void addSelect(Collection<? extends Field<?>> fields);

    /**
     * Add "distinct" keyword to the select clause
     */
    @Support
    void setDistinct(boolean distinct);

    /**
     * Add tables to the table product
     *
     * @param from The added tables
     */
    @Support
    void addFrom(TableLike<?>... from);

    /**
     * Add tables to the table product
     *
     * @param from The added tables
     */
    @Support
    void addFrom(Collection<? extends TableLike<?>> from);

    /**
     * Joins the existing table product to a new table using a condition
     *
     * @param table The joined table
     * @param conditions The joining conditions
     */
    @Support
    void addJoin(TableLike<?> table, Condition... conditions);

    /**
     * Joins the existing table product to a new table using a condition
     *
     * @param table The joined table
     * @param type The type of join
     * @param conditions The joining conditions
     */
    @Support
    void addJoin(TableLike<?> table, JoinType type, Condition... conditions);

    /**
     * Joins the existing table product to a new table using a condition
     * <p>
     * This adds a <code>PARTITION BY</code> clause to the right hand side of a
     * <code>OUTER JOIN</code> expression.
     *
     * @param table The joined table
     * @param type The type of join
     * @param conditions The joining conditions
     * @param partitionBy The <code>PARTITION BY</code> expression
     * @see TablePartitionByStep
     */
    @Support(ORACLE)
    void addJoin(TableLike<?> table, JoinType type, Condition[] conditions, Field<?>[] partitionBy);

    /**
     * Joins the existing table product to a new table with a <code>USING</code>
     * clause
     * <p>
     * If this is not supported by your RDBMS, then jOOQ will try to simulate
     * this behaviour using the information provided in this query.
     *
     * @param table The joined table
     * @param fields The fields for the <code>USING</code> clause
     */
    @Support
    void addJoinUsing(TableLike<?> table, Collection<? extends Field<?>> fields);

    /**
     * Joins the existing table product to a new table with a <code>USING</code>
     * clause
     * <p>
     * If this is not supported by your RDBMS, then jOOQ will try to simulate
     * this behaviour using the information provided in this query.
     *
     * @param table The joined table
     * @param type The type of join
     * @param fields The fields for the <code>USING</code> clause
     */
    @Support
    void addJoinUsing(TableLike<?> table, JoinType type, Collection<? extends Field<?>> fields);

    /**
     * Joins the existing table product to a new table using a foreign key
     *
     * @param table The joined table
     * @param type The type of join
     * @see TableOnStep#onKey(ForeignKey)
     * @throws DataAccessException If there is no non-ambiguous key definition
     *             known to jOOQ
     */
    @Support
    void addJoinOnKey(TableLike<?> table, JoinType type) throws DataAccessException;

    /**
     * Joins the existing table product to a new table using a foreign key
     *
     * @param table The joined table
     * @param type The type of join
     * @param keyFields The foreign key fields
     * @see TableOnStep#onKey(ForeignKey)
     * @throws DataAccessException If there is no non-ambiguous key definition
     *             known to jOOQ
     */
    @Support
    void addJoinOnKey(TableLike<?> table, JoinType type, TableField<?, ?>... keyFields) throws DataAccessException;

    /**
     * Joins the existing table product to a new table using a foreign key
     *
     * @param table The joined table
     * @param type The type of join
     * @param key The foreign key
     * @see TableOnStep#onKey(ForeignKey)
     */
    @Support
    void addJoinOnKey(TableLike<?> table, JoinType type, ForeignKey<?, ?> key);

    /**
     * Adds grouping fields
     * <p>
     * Calling this with an empty argument list will result in an empty
     * <code>GROUP BY ()</code> clause being rendered.
     *
     * @param fields The grouping fields
     */
    @Support
    void addGroupBy(GroupField... fields);

    /**
     * Adds grouping fields
     * <p>
     * Calling this with an empty argument list will result in an empty
     * <code>GROUP BY ()</code> clause being rendered.
     *
     * @param fields The grouping fields
     */
    @Support
    void addGroupBy(Collection<? extends GroupField> fields);

    /**
     * Adds new conditions to the having clause of the query, connecting it to
     * existing conditions with the and operator.
     *
     * @param conditions The condition
     */
    @Support
    void addHaving(Condition... conditions);

    /**
     * Adds new conditions to the having clause of the query, connecting it to
     * existing conditions with the and operator.
     *
     * @param conditions The condition
     */
    @Support
    void addHaving(Collection<Condition> conditions);

    /**
     * Adds new conditions to the having clause of query, connecting them to
     * existing conditions with the provided operator
     *
     * @param operator The operator to use to add the conditions to the existing
     *            conditions
     * @param conditions The condition
     */
    @Support
    void addHaving(Operator operator, Condition... conditions);

    /**
     * Adds new conditions to the having clause of query, connecting them to
     * existing conditions with the provided operator
     *
     * @param operator The operator to use to add the conditions to the existing
     *            conditions
     * @param conditions The condition
     */
    @Support
    void addHaving(Operator operator, Collection<Condition> conditions);

    /**
     * Add an Oracle-style hint to the select clause
     * <p>
     * Example: <code><pre>
     * Factory create = new Factory();
     *
     * create.select(field1, field2)
     *       .hint("/*+ALL_ROWS&#42;/")
     *       .from(table1)
     *       .execute();
     * </pre></code>
     * <p>
     * You can also use this clause for any other database, that accepts hints
     * or options at the same syntactic location, e.g. for MySQL's
     * <code>SQL_CALC_FOUND_ROWS</code> option: <code><pre>
     * create.select(field1, field2)
     *       .hint("SQL_CALC_FOUND_ROWS")
     *       .from(table1)
     *       .fetch();
     * </pre></code>
     * <p>
     * The outcome of such a query is this: <code><pre>
     * SELECT [hint] field1, field2 FROM table1
     * </pre></code>
     * <p>
     * For SQL Server style table hints, see {@link Table#with(String)}
     *
     * @see Table#with(String)
     */
    @Support
    void addHint(String hint);

    /**
     * Add an Oracle-specific <code>CONNECT BY</code> clause to the query
     */
    @Support({ CUBRID, ORACLE })
    void addConnectBy(Condition condition);

    /**
     * Add an Oracle-specific <code>CONNECT BY NOCYCLE</code> clause to the
     * query
     */
    @Support({ CUBRID, ORACLE })
    void addConnectByNoCycle(Condition condition);

    /**
     * Add an Oracle-specific <code>START WITH</code> clause to the query's
     * <code>CONNECT BY</code> clause
     */
    @Support({ CUBRID, ORACLE })
    void setConnectByStartWith(Condition condition);

}

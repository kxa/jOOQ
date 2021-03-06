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

import static org.jooq.SQLDialect.ASE;
import static org.jooq.SQLDialect.CUBRID;
import static org.jooq.SQLDialect.DB2;
import static org.jooq.SQLDialect.DERBY;
import static org.jooq.SQLDialect.FIREBIRD;
import static org.jooq.SQLDialect.H2;
import static org.jooq.SQLDialect.HSQLDB;
import static org.jooq.SQLDialect.INGRES;
import static org.jooq.SQLDialect.MYSQL;
import static org.jooq.SQLDialect.ORACLE;
import static org.jooq.SQLDialect.POSTGRES;
import static org.jooq.SQLDialect.SQLITE;
import static org.jooq.SQLDialect.SQLSERVER;
import static org.jooq.SQLDialect.SYBASE;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

import org.jooq.impl.Executor;
import org.jooq.impl.Factory;
import org.jooq.types.Interval;
import org.jooq.util.oracle.OracleFactory;

/**
 * A field used in tables and conditions
 * <p>
 * Note that all fields qualify as {@link GroupField}, i.e. they can always be
 * used in <code>GROUP BY</code> clauses
 *
 * @param <T> The field type
 * @author Lukas Eder
 */
public interface Field<T> extends GroupField {

    // ------------------------------------------------------------------------
    // API
    // ------------------------------------------------------------------------

    /**
     * The name of the field.
     * <p>
     * The name is any of these:
     * <ul>
     * <li>The formal name of the field, if it is a <i>physical table/view
     * field</i></li>
     * <li>The alias of an <i>aliased field</i></li>
     * <li>A generated / unspecified value for any other <i>expression</i></li>
     * <li>The name of a parameter if it is a named {@link Param}</li>
     * </ul>
     */
    String getName();

    /**
     * The Java type of the field.
     */
    Class<T> getType();

    /**
     * The type of this field (might not be dialect-specific)
     */
    DataType<T> getDataType();

    /**
     * The dialect-specific type of this field
     */
    DataType<T> getDataType(Configuration configuration);

    /**
     * Create an alias for this field
     *
     * @param alias The alias name
     * @return The field alias
     */
    @Support
    Field<T> as(String alias);

    /**
     * Watch out! This is {@link Object#equals(Object)}, not a jOOQ feature! :-)
     */
    @Override
    boolean equals(Object other);

    /**
     * Whether this field represents a <code>null</code> literal.
     * <p>
     * This method is for JOOQ INTERNAL USE only!
     * <p>
     * This method was added to be able to recognise <code>null</code> literals
     * within jOOQ and handle them specially, as some SQL dialects have a rather
     * un-intuitive way of handling <code>null</code> values.
     */
    boolean isNullLiteral();

    // ------------------------------------------------------------------------
    // Type casts
    // ------------------------------------------------------------------------

    /**
     * Cast this field to the type of another field.
     * <p>
     * This results in the same as casting this field to
     * {@link DataType#getCastTypeName()}
     *
     * @param <Z> The generic type of the cast field
     * @param field The field whose type is used for the cast
     * @return The cast field
     * @see #cast(DataType)
     */
    @Support
    <Z> Field<Z> cast(Field<Z> field);

    /**
     * Cast this field to a dialect-specific data type.
     *
     * @param <Z> The generic type of the cast field
     * @param type
     */
    @Support
    <Z> Field<Z> cast(DataType<Z> type);

    /**
     * Cast this field to another type
     * <p>
     * The actual cast may not be accurate as the {@link DataType} has to be
     * "guessed" from the jOOQ-configured data types. Use
     * {@link #cast(DataType)} for more accurate casts.
     *
     * @param <Z> The generic type of the cast field
     * @param type The type that is used for the cast
     * @return The cast field
     * @see #cast(DataType)
     */
    @Support
    <Z> Field<Z> cast(Class<Z> type);

    // ------------------------------------------------------------------------
    // Conversion of field into a sort field
    // ------------------------------------------------------------------------

    /**
     * Create an ascending sort field from this field
     * <p>
     * This is the same as calling {@link #sort(SortOrder)} with
     * {@link SortOrder#ASC}
     *
     * @return This field as an ascending sort field
     */
    @Support
    SortField<T> asc();

    /**
     * Create a descending sort field from this field
     * <p>
     * This is the same as calling {@link #sort(SortOrder)} with
     * {@link SortOrder#DESC}
     *
     * @return This field as a descending sort field
     */
    @Support
    SortField<T> desc();

    /**
     * Create an ascending/descending sort field from this field
     *
     * @param order The sort order
     * @return This field as an ascending/descending sort field.
     */
    @Support
    SortField<T> sort(SortOrder order);

    /**
     * Create a sort field of the form <code><pre>
     * CASE [this] WHEN [sortList.get(0)] THEN 0
     *             WHEN [sortList.get(1)] THEN 1
     *             ...
     *             WHEN [sortList.get(n)] THEN n
     *                                    ELSE null
     * END ASC
     * </pre></code>
     * <p>
     * Note: You can use this in combination with {@link SortField#nullsFirst()}
     * or {@link SortField#nullsLast()} to specify whether the default should
     * have highest or lowest priority.
     *
     * @param sortList The list containing sort value preferences
     * @return The sort field
     */
    @Support
    SortField<Integer> sortAsc(Collection<T> sortList);

    /**
     * Create a sort field of the form <code><pre>
     * CASE [this] WHEN [sortList[0]] THEN 0
     *             WHEN [sortList[1]] THEN 1
     *             ...
     *             WHEN [sortList[n]] THEN n
     *                                ELSE null
     * END ASC
     * </pre></code>
     * <p>
     * Note: You can use this in combination with {@link SortField#nullsFirst()}
     * or {@link SortField#nullsLast()} to specify whether the default should
     * have highest or lowest priority.
     *
     * @param sortList The list containing sort value preferences
     * @return The sort field
     */
    @Support
    SortField<Integer> sortAsc(T... sortList);

    /**
     * Create a sort field of the form <code><pre>
     * CASE [this] WHEN [sortList.get(0)] THEN 0
     *             WHEN [sortList.get(1)] THEN 1
     *             ...
     *             WHEN [sortList.get(n)] THEN n
     *                                    ELSE null
     * END DESC
     * </pre></code>
     * <p>
     * Note: You can use this in combination with {@link SortField#nullsFirst()}
     * or {@link SortField#nullsLast()} to specify whether the default should
     * have highest or lowest priority.
     *
     * @param sortList The list containing sort value preferences
     * @return The sort field
     */
    @Support
    SortField<Integer> sortDesc(Collection<T> sortList);

    /**
     * Create a sort field of the form <code><pre>
     * CASE [this] WHEN [sortList[0]] THEN 0
     *             WHEN [sortList[1]] THEN 1
     *             ...
     *             WHEN [sortList[n]] THEN n
     *                                    ELSE null
     * END DESC
     * </pre></code>
     * <p>
     * Note: You can use this in combination with {@link SortField#nullsFirst()}
     * or {@link SortField#nullsLast()} to specify whether the default should
     * have highest or lowest priority.
     *
     * @param sortList The list containing sort value preferences
     * @return The sort field
     */
    @Support
    SortField<Integer> sortDesc(T... sortList);

    /**
     * Create a sort field of the form (in pseudo code)<code><pre>
     * CASE [this] WHEN [sortMap.key(0)] THEN sortMap.value(0)
     *             WHEN [sortMap.key(1)] THEN sortMap.value(1)
     *             ...
     *             WHEN [sortMap.key(n)] THEN sortMap.value(n)
     *                                   ELSE null
     * END DESC
     * </pre></code>
     * <p>
     * Note: You can use this in combination with {@link SortField#nullsFirst()}
     * or {@link SortField#nullsLast()} to specify whether the default should
     * have highest or lowest priority.
     *
     * @param sortMap The list containing sort value preferences
     * @return The sort field
     */
    @Support
    <Z> SortField<Z> sort(Map<T, Z> sortMap);

    // ------------------------------------------------------------------------
    // Arithmetic operations
    // ------------------------------------------------------------------------

    /**
     * Negate this field to get its negative value.
     * <p>
     * This renders the same on all dialects: <code><pre>-[this]</pre></code>
     */
    @Support
    Field<T> neg();

    /**
     * An arithmetic expression adding this to value.
     *
     * @see #add(Field)
     */
    @Support
    Field<T> add(Number value);

    /**
     * An arithmetic expression to add value to this.
     * <p>
     * The behaviour of this operation is as follows:
     * <table border="1">
     * <tr>
     * <th>Operand 1</th>
     * <th>Operand 2</th>
     * <th>Result Type</th>
     * </tr>
     * <tr>
     * <td>Numeric</td>
     * <td>Numeric</td>
     * <td>Numeric</td>
     * </tr>
     * <tr>
     * <td>Date / Time</td>
     * <td>Numeric</td>
     * <td>Date / Time</td>
     * </tr>
     * <tr>
     * <td>Date / Time</td>
     * <td>Interval</td>
     * <td>Date / Time</td>
     * </tr>
     * <tr>
     * <td>Interval</td>
     * <td>Interval</td>
     * <td>Interval</td>
     * </tr>
     * </table>
     */
    @Support
    Field<T> add(Field<?> value);

    /**
     * An arithmetic expression subtracting value from this.
     *
     * @see #sub(Field)
     */
    @Support
    Field<T> sub(Number value);

    /**
     * An arithmetic expression subtracting value from this.
     * <p>
     * <table border="1">
     * <tr>
     * <th>Operand 1</th>
     * <th>Operand 2</th>
     * <th>Result Type</th>
     * </tr>
     * <tr>
     * <td>Numeric</td>
     * <td>Numeric</td>
     * <td>Numeric</td>
     * </tr>
     * <tr>
     * <td>Date / Time</td>
     * <td>Numeric</td>
     * <td>Date / Time</td>
     * </tr>
     * <tr>
     * <td>Date / Time</td>
     * <td>Interval</td>
     * <td>Date / Time</td>
     * </tr>
     * <tr>
     * <td>Interval</td>
     * <td>Interval</td>
     * <td>Interval</td>
     * </tr>
     * </table>
     * <p>
     * In order to subtract one date time field from another, use any of these
     * methods:
     * <ul>
     * <li> {@link Factory#dateDiff(Field, Field)}</li>
     * <li> {@link Factory#timestampDiff(Field, Field)}</li>
     * </ul>
     */
    @Support
    Field<T> sub(Field<?> value);

    /**
     * An arithmetic expression multiplying this with value
     * <p>
     * <ul>
     * <li>If this is a numeric field, then the result is a number of the same
     * type as this field.</li>
     * <li>If this is an <code>INTERVAL</code> field, then the result is also an
     * <code>INTERVAL</code> field (see {@link Interval})</li>
     * </ul>
     */
    @Support
    Field<T> mul(Number value);

    /**
     * An arithmetic expression multiplying this with value
     * <p>
     * <ul>
     * <li>If this is a numeric field, then the result is a number of the same
     * type as this field.</li>
     * <li>If this is an <code>INTERVAL</code> field, then the result is also an
     * <code>INTERVAL</code> field (see {@link Interval})</li>
     * </ul>
     */
    @Support
    Field<T> mul(Field<? extends Number> value);

    /**
     * An arithmetic expression dividing this by value
     * <p>
     * <ul>
     * <li>If this is a numeric field, then the result is a number of the same
     * type as this field.</li>
     * <li>If this is an <code>INTERVAL</code> field, then the result is also an
     * <code>INTERVAL</code> field (see {@link Interval})</li>
     * </ul>
     */
    @Support
    Field<T> div(Number value);

    /**
     * An arithmetic expression dividing this by value
     * <p>
     * <ul>
     * <li>If this is a numeric field, then the result is a number of the same
     * type as this field.</li>
     * <li>If this is an <code>INTERVAL</code> field, then the result is also an
     * <code>INTERVAL</code> field (see {@link Interval})</li>
     * </ul>
     */
    @Support
    Field<T> div(Field<? extends Number> value);

    /**
     * An arithmetic expression getting the modulo of this divided by value
     * <p>
     * This renders the modulo operation where available:
     * <code><pre>[this] % [value]</pre></code> ... or the modulo function
     * elsewhere: <code><pre>mod([this], [value])</pre></code>
     */
    @Support
    Field<T> mod(Number value);

    /**
     * An arithmetic expression getting the modulo of this divided by value
     * <p>
     * This renders the modulo operation where available:
     * <code><pre>[this] % [value]</pre></code> ... or the modulo function
     * elsewhere: <code><pre>mod([this], [value])</pre></code>
     */
    @Support
    Field<T> mod(Field<? extends Number> value);

    // ------------------------------------------------------------------------
    // NULL predicates
    // ------------------------------------------------------------------------

    /**
     * Create a condition to check this field against <code>null</code>.
     * <p>
     * SQL: <code>this is null</code>
     */
    @Support
    Condition isNull();

    /**
     * Create a condition to check this field against <code>null</code>.
     * <p>
     * SQL: <code>this is not null</code>
     */
    @Support
    Condition isNotNull();

    // ------------------------------------------------------------------------
    // DISTINCT predicates
    // ------------------------------------------------------------------------

    /**
     * Create a condition to check if this field is <code>DISTINCT</code> from
     * another value
     * <p>
     * If this is not supported by the underlying database, jOOQ will render
     * this instead: <code><pre>
     * CASE WHEN [this] IS     NULL AND [value] IS     NULL THEN FALSE
     *      WHEN [this] IS     NULL AND [value] IS NOT NULL THEN TRUE
     *      WHEN [this] IS NOT NULL AND [value] IS     NULL THEN TRUE
     *      WHEN [this] =               [value]             THEN FALSE
     *      ELSE                                                 TRUE
     * END
     * </pre></code> SQL: <code>this is distinct from value</code>
     */
    @Support
    Condition isDistinctFrom(T value);

    /**
     * Create a condition to check if this field is <code>DISTINCT</code> from
     * another field
     * <p>
     * If this is not supported by the underlying database, jOOQ will render
     * this instead: <code><pre>
     * CASE WHEN [this] IS     NULL AND [field] IS     NULL THEN FALSE
     *      WHEN [this] IS     NULL AND [field] IS NOT NULL THEN TRUE
     *      WHEN [this] IS NOT NULL AND [field] IS     NULL THEN TRUE
     *      WHEN [this] =               [field]             THEN FALSE
     *      ELSE                                                 TRUE
     * END
     * </pre></code> SQL: <code>this is distinct from field</code>
     */
    @Support
    Condition isDistinctFrom(Field<T> field);

    /**
     * Create a condition to check if this field is <code>NOT DISTINCT</code>
     * from another value
     * <p>
     * If this is not supported by the underlying database, jOOQ will render
     * this instead: <code><pre>
     * CASE WHEN [this] IS     NULL AND [value] IS     NULL THEN TRUE
     *      WHEN [this] IS     NULL AND [value] IS NOT NULL THEN FALSE
     *      WHEN [this] IS NOT NULL AND [value] IS     NULL THEN FALSE
     *      WHEN [this] =               [value]             THEN TRUE
     *      ELSE                                                 FALSE
     * END
     * </pre></code> SQL: <code>this is not distinct from value</code>
     */
    @Support
    Condition isNotDistinctFrom(T value);

    /**
     * Create a condition to check if this field is <code>NOT DISTINCT</code>
     * from another field
     * <p>
     * If this is not supported by the underlying database, jOOQ will render
     * this instead: <code><pre>
     * CASE WHEN [this] IS     NULL AND [field] IS     NULL THEN TRUE
     *      WHEN [this] IS     NULL AND [field] IS NOT NULL THEN FALSE
     *      WHEN [this] IS NOT NULL AND [field] IS     NULL THEN FALSE
     *      WHEN [this] =               [value]             THEN TRUE
     *      ELSE                                                 FALSE
     * END
     * </pre></code> SQL: <code>this is not distinct from field</code>
     */
    @Support
    Condition isNotDistinctFrom(Field<T> field);

    // ------------------------------------------------------------------------
    // LIKE_REGEX predicates
    // ------------------------------------------------------------------------

    /**
     * Create a condition to regex-pattern-check this field against a pattern
     * <p>
     * The SQL:2008 standard specifies a <code>&lt;regex like predicate></code>
     * of the following form: <code><pre>
     * &lt;regex like predicate> ::=
     *   &lt;row value predicand> &lt;regex like predicate part 2>
     *
     * &lt;regex like predicate part 2> ::=
     *  [ NOT ] LIKE_REGEX &lt;XQuery pattern> [ FLAG &lt;XQuery option flag> ]
     * </pre></code>
     * <p>
     * This particular <code>LIKE_REGEX</code> operator comes in several
     * flavours for various databases. jOOQ supports regular expressions as
     * follows:
     * <table border="1">
     * <tr>
     * <th>SQL dialect</th>
     * <th>SQL syntax</th>
     * <th>Pattern syntax</th>
     * <th>Documentation</th>
     * </tr>
     * <tr>
     * <td>{@link SQLDialect#ASE}</td>
     * <td>-</td>
     * <td>-</td>
     * <td>-</td>
     * </tr>
     * <tr>
     * <td>{@link SQLDialect#CUBRID}</td>
     * <td><code>[search] REGEXP [pattern]</code></td>
     * <td>POSIX</td>
     * <td><a href=
     * "http://www.cubrid.org/manual/841/en/REGEXP%20Conditional%20Expression"
     * >http://www.cubrid.org/manual/841/en/REGEXP Conditional Expression</a></td>
     * </tr>
     * <tr>
     * <td>{@link SQLDialect#DB2}</td>
     * <td>-</td>
     * <td>-</td>
     * <td>-</td>
     * </tr>
     * <tr>
     * <td>{@link SQLDialect#DERBY}</td>
     * <td>-</td>
     * <td>-</td>
     * <td>-</td>
     * </tr>
     * <tr>
     * <td>{@link SQLDialect#H2}</td>
     * <td><code>[search] REGEXP [pattern]</code></td>
     * <td>Java</td>
     * <td><a href=
     * "http://www.h2database.com/html/grammar.html#condition_right_hand_side"
     * >http
     * ://www.h2database.com/html/grammar.html#condition_right_hand_side</a></td>
     * </tr>
     * <tr>
     * <td>{@link SQLDialect#HSQLDB}</td>
     * <td><code>REGEXP_MATCHES([search], [pattern])</td>
     * <td>Java</td>
     * <td><a
     * href="http://hsqldb.org/doc/guide/builtinfunctions-chapt.html#N13577"
     * >http://hsqldb.org/doc/guide/builtinfunctions-chapt.html#N13577</a></td>
     * </tr>
     * <tr>
     * <td>{@link SQLDialect#INGRES}</td>
     * <td>-</td>
     * <td>-</td>
     * <td>-</td>
     * </tr>
     * <tr>
     * <td>{@link SQLDialect#MYSQL}</td>
     * <td><code>[search] REGEXP [pattern]</code></td>
     * <td>POSIX</td>
     * <td><a href=
     * "http://dev.mysql.com/doc/refman/5.6/en/regexp.html">http://dev
     * .mysql.com/doc/refman/5.6/en/regexp.html</a ></td>
     * </tr>
     * <tr>
     * <td>{@link SQLDialect#ORACLE}</td>
     * <td><code>REGEXP_LIKE([search], [pattern])</code></td>
     * <td>POSIX</td>
     * <td><a href=
     * "http://docs.oracle.com/cd/E14072_01/server.112/e10592/conditions007.htm#sthref1994"
     * >http://docs.oracle.com/cd/E14072_01/server.112/e10592/conditions007.htm#
     * sthref1994</a></td>
     * </tr>
     * <tr>
     * <td>{@link SQLDialect#POSTGRES}</td>
     * <td><code>[search] ~ [pattern]</code></td>
     * <td>POSIX</td>
     * <td><a href=
     * "http://www.postgresql.org/docs/9.1/static/functions-matching.html#FUNCTIONS-POSIX-REGEXP"
     * >http://www.postgresql.org/docs/9.1/static/functions-matching.html#
     * FUNCTIONS-POSIX-REGEXP</a></td>
     * </tr>
     * <tr>
     * <td>{@link SQLDialect#SQLITE}</td>
     * <td><code>[search] REGEXP [pattern]</code></td>
     * <td>? This module has to be loaded explicitly</td>
     * <td><a href="http://www.sqlite.org/lang_expr.html">http://www.sqlite.org/
     * lang_expr.html</a></td>
     * </tr>
     * <tr>
     * <td>{@link SQLDialect#SQLSERVER}</td>
     * <td>-</td>
     * <td>-</td>
     * <td>-</td>
     * </tr>
     * <tr>
     * <td>{@link SQLDialect#SYBASE}</td>
     * <td><code>[search] REGEXP [pattern]</code></td>
     * <td>Perl</td>
     * <td><a href=
     * "http://infocenter.sybase.com/help/topic/com.sybase.help.sqlanywhere.12.0.1/dbreference/like-regexp-similarto.html"
     * >http://infocenter.sybase.com/help/topic/com.sybase.help.sqlanywhere.12.0
     * .1/dbreference/like-regexp-similarto.html</a></td>
     * </tr>
     * </table>
     *
     * @see #likeRegex(String)
     */
    @Support({ CUBRID, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLITE, SYBASE })
    Condition likeRegex(String pattern);

    /**
     * Create a condition to regex-pattern-check this field against a pattern
     * <p>
     * See {@link #likeRegex(String)} for more details
     *
     * @see #likeRegex(String)
     */
    @Support({ CUBRID, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLITE, SYBASE })
    Condition likeRegex(Field<String> pattern);

    /**
     * Create a condition to regex-pattern-check this field against a pattern
     * <p>
     * See {@link #likeRegex(String)} for more details
     *
     * @see #likeRegex(String)
     */
    @Support({ CUBRID, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLITE, SYBASE })
    Condition notLikeRegex(String pattern);

    /**
     * Create a condition to regex-pattern-check this field against a pattern
     * <p>
     * See {@link #likeRegex(String)} for more details
     *
     * @see #likeRegex(Field)
     */
    @Support({ CUBRID, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLITE, SYBASE })
    Condition notLikeRegex(Field<String> pattern);

    // ------------------------------------------------------------------------
    // LIKE predicates
    // ------------------------------------------------------------------------

    /**
     * Create a condition to pattern-check this field against a value
     * <p>
     * SQL: <code>this like value</code>
     */
    @Support
    Condition like(Field<String> value);

    /**
     * Create a condition to pattern-check this field against a value
     * <p>
     * SQL: <code>this like value escape 'e'</code>
     */
    @Support
    Condition like(Field<String> value, char escape);

    /**
     * Create a condition to pattern-check this field against a value
     * <p>
     * SQL: <code>this like value</code>
     */
    @Support
    Condition like(String value);

    /**
     * Create a condition to pattern-check this field against a value
     * <p>
     * SQL: <code>this like value escape 'e'</code>
     */
    @Support
    Condition like(String value, char escape);

    /**
     * Create a condition to case-insensitively pattern-check this field against
     * a field
     * <p>
     * This translates to <code>this ilike field</code> in
     * {@link SQLDialect#POSTGRES}, or to
     * <code>lower(this) like lower(field)</code> in all other dialects.
     */
    @Support
    Condition likeIgnoreCase(Field<String> field);

    /**
     * Create a condition to case-insensitively pattern-check this field against
     * a field
     * <p>
     * This translates to <code>this ilike field</code> in
     * {@link SQLDialect#POSTGRES}, or to
     * <code>lower(this) like lower(field)</code> in all other dialects.
     */
    @Support
    Condition likeIgnoreCase(Field<String> field, char escape);

    /**
     * Create a condition to case-insensitively pattern-check this field against
     * a value
     * <p>
     * This translates to <code>this ilike value</code> in
     * {@link SQLDialect#POSTGRES}, or to
     * <code>lower(this) like lower(value)</code> in all other dialects.
     */
    @Support
    Condition likeIgnoreCase(String value);

    /**
     * Create a condition to case-insensitively pattern-check this field against
     * a value
     * <p>
     * This translates to <code>this ilike value</code> in
     * {@link SQLDialect#POSTGRES}, or to
     * <code>lower(this) like lower(value)</code> in all other dialects.
     */
    @Support
    Condition likeIgnoreCase(String value, char escape);

    /**
     * Create a condition to pattern-check this field against a field
     * <p>
     * SQL: <code>this not like field</code>
     */
    @Support
    Condition notLike(Field<String> field);

    /**
     * Create a condition to pattern-check this field against a field
     * <p>
     * SQL: <code>this not like field escape 'e'</code>
     */
    @Support
    Condition notLike(Field<String> field, char escape);

    /**
     * Create a condition to pattern-check this field against a value
     * <p>
     * SQL: <code>this not like value</code>
     */
    @Support
    Condition notLike(String value);

    /**
     * Create a condition to pattern-check this field against a value
     * <p>
     * SQL: <code>this not like value escape 'e'</code>
     */
    @Support
    Condition notLike(String value, char escape);

    /**
     * Create a condition to case-insensitively pattern-check this field against
     * a field
     * <p>
     * This translates to <code>this not ilike field</code> in
     * {@link SQLDialect#POSTGRES}, or to
     * <code>lower(this) not like lower(field)</code> in all other dialects.
     */
    @Support
    Condition notLikeIgnoreCase(Field<String> field);

    /**
     * Create a condition to case-insensitively pattern-check this field against
     * a field
     * <p>
     * This translates to <code>this not ilike field</code> in
     * {@link SQLDialect#POSTGRES}, or to
     * <code>lower(this) not like lower(field)</code> in all other dialects.
     */
    @Support
    Condition notLikeIgnoreCase(Field<String> field, char escape);

    /**
     * Create a condition to case-insensitively pattern-check this field against
     * a value
     * <p>
     * This translates to <code>this not ilike value</code> in
     * {@link SQLDialect#POSTGRES}, or to
     * <code>lower(this) not like lower(value)</code> in all other dialects.
     */
    @Support
    Condition notLikeIgnoreCase(String value);

    /**
     * Create a condition to case-insensitively pattern-check this field against
     * a value
     * <p>
     * This translates to <code>this not ilike value</code> in
     * {@link SQLDialect#POSTGRES}, or to
     * <code>lower(this) not like lower(value)</code> in all other dialects.
     */
    @Support
    Condition notLikeIgnoreCase(String value, char escape);

    /**
     * Convenience method for {@link #like(String, char)} including proper
     * adding of wildcards and escaping
     * <p>
     * SQL: <code>this like ('%' || escape(value, '\') || '%') escape '\'</code>
     * <p>
     * Note: This also works with numbers, for instance
     * <code>val(1133).contains(13)</code>
     * <p>
     * If you're using {@link SQLDialect#POSTGRES}, then you can use this method
     * also to express the "ARRAY contains" operator. For example: <code><pre>
     * // Use this expression
     * val(new Integer[] { 1, 2, 3 }).contains(new Integer[] { 1, 2 })
     *
     * // ... to render this SQL
     * ARRAY[1, 2, 3] @> ARRAY[1, 2]
     * </pre></code>
     * <p>
     * Note, this does not correspond to the Oracle Text <code>CONTAINS()</code>
     * function. Refer to {@link OracleFactory#contains(Field, String)} instead.
     *
     * @see Factory#escape(String, char)
     * @see #like(String, char)
     */
    @Support
    Condition contains(T value);

    /**
     * Convenience method for {@link #like(String, char)} including proper
     * adding of wildcards and escaping
     * <p>
     * SQL: <code>this like ('%' || escape(value, '\') || '%') escape '\'</code>
     * <p>
     * Note: This also works with numbers, for instance
     * <code>val(1133).contains(13)</code>
     * <p>
     * If you're using {@link SQLDialect#POSTGRES}, then you can use this method
     * also to express the "ARRAY contains" operator. For example: <code><pre>
     * // Use this expression
     * val(new Integer[] { 1, 2, 3 }).contains(new Integer[] { 1, 2 })
     *
     * // ... to render this SQL
     * ARRAY[1, 2, 3] @> ARRAY[1, 2]
     * </pre></code>
     * <p>
     * Note, this does not correspond to the Oracle Text <code>CONTAINS()</code>
     * function. Refer to {@link OracleFactory#contains(Field, String)} instead.
     *
     * @see Factory#escape(Field, char)
     * @see #like(Field, char)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    Condition contains(Field<T> value);

    /**
     * Convenience method for {@link #like(String, char)} including proper
     * adding of wildcards and escaping
     * <p>
     * SQL: <code>this like (escape(value, '\') || '%') escape '\'</code>
     * <p>
     * Note: This also works with numbers, for instance
     * <code>val(1133).startsWith(11)</code>
     *
     * @see Factory#escape(String, char)
     * @see #like(String, char)
     */
    @Support
    Condition startsWith(T value);

    /**
     * Convenience method for {@link #like(String, char)} including proper
     * adding of wildcards and escaping
     * <p>
     * SQL: <code>this like (escape(value, '\') || '%') escape '\'</code>
     * <p>
     * Note: This also works with numbers, for instance
     * <code>val(1133).startsWith(11)</code>
     *
     * @see Factory#escape(Field, char)
     * @see #like(Field, char)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    Condition startsWith(Field<T> value);

    /**
     * Convenience method for {@link #like(String, char)} including proper
     * adding of wildcards and escaping
     * <p>
     * SQL: <code>this like ('%' || escape(value, '\')) escape '\'</code>
     * <p>
     * Note: This also works with numbers, for instance
     * <code>val(1133).endsWith(33)</code>
     *
     * @see Factory#escape(String, char)
     * @see #like(String, char)
     */
    @Support
    Condition endsWith(T value);

    /**
     * Convenience method for {@link #like(String, char)} including proper
     * adding of wildcards and escaping
     * <p>
     * SQL: <code>this like ('%' || escape(value, '\')) escape '\'</code>
     * <p>
     * Note: This also works with numbers, for instance
     * <code>val(1133).endsWith(33)</code>
     *
     * @see Factory#escape(Field, char)
     * @see #like(Field, char)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    Condition endsWith(Field<T> value);

    // ------------------------------------------------------------------------
    // IN predicates
    // ------------------------------------------------------------------------

    /**
     * Create a condition to check this field against several values
     * <p>
     * SQL: <code>this in (values...)</code>
     */
    @Support
    Condition in(Collection<T> values);

    /**
     * Create a condition to check this field against several values
     * <p>
     * SQL: <code>this in (values...)</code>
     */
    @Support
    Condition in(T... values);

    /**
     * Create a condition to check this field against several values
     * <p>
     * SQL: <code>this in (values...)</code>
     */
    @Support
    Condition in(Field<?>... values);

    /**
     * Create a condition to check this field against a subquery
     * <p>
     * Note that the subquery must return exactly one field. This is not checked
     * by jOOQ and will result in syntax errors in the database, if not used
     * correctly.
     * <p>
     * SQL: <code>this in (select...)</code>
     */
    @Support
    Condition in(Select<? extends Record1<T>> query);

    /**
     * Create a condition to check this field against several values
     * <p>
     * Note that if any of the passed values is <code>NULL</code>, then the
     * condition will be <code>NULL</code> (or <code>false</code>, depending on
     * the dialect) as well. This is standard SQL behaviour.
     * <p>
     * SQL: <code>this not in (values...)</code>
     */
    @Support
    Condition notIn(Collection<T> values);

    /**
     * Create a condition to check this field against several values
     * <p>
     * Note that if any of the passed values is <code>NULL</code>, then the
     * condition will be <code>NULL</code> (or <code>false</code>, depending on
     * the dialect) as well. This is standard SQL behaviour.
     * <p>
     * SQL: <code>this not in (values...)</code>
     */
    @Support
    Condition notIn(T... values);

    /**
     * Create a condition to check this field against several values
     * <p>
     * Note that if any of the passed values is <code>NULL</code>, then the
     * condition will be <code>NULL</code> (or <code>false</code>, depending on
     * the dialect) as well. This is standard SQL behaviour.
     * <p>
     * SQL: <code>this not in (values...)</code>
     */
    @Support
    Condition notIn(Field<?>... values);

    /**
     * Create a condition to check this field against a subquery
     * <p>
     * Note that the subquery must return exactly one field. This is not checked
     * by jOOQ and will result in syntax errors in the database, if not used
     * correctly.
     * <p>
     * Note that if any of the passed values is <code>NULL</code>, then the
     * condition will be <code>NULL</code> (or <code>false</code>, depending on
     * the dialect) as well. This is standard SQL behaviour.
     * <p>
     * SQL: <code>this not in (select...)</code>
     */
    @Support
    Condition notIn(Select<? extends Record1<T>> query);

    // ------------------------------------------------------------------------
    // BETWEEN predicates
    // ------------------------------------------------------------------------

    /**
     * Create a condition to check this field against some bounds
     * <p>
     * This is the same as calling <code>between(minValue).and(maxValue)</code>
     * <p>
     * SQL: <code>this between minValue and maxValue</code>
     */
    @Support
    Condition between(T minValue, T maxValue);

    /**
     * Create a condition to check this field against some bounds
     * <p>
     * This is the same as calling <code>between(minValue).and(maxValue)</code>
     * <p>
     * SQL: <code>this between minValue and maxValue</code>
     */
    @Support
    Condition between(Field<T> minValue, Field<T> maxValue);

    /**
     * Create a condition to check this field against some bounds
     * <p>
     * This is the same as calling
     * <code>betweenSymmetric(minValue).and(maxValue)</code>
     * <p>
     * SQL: <code>this between symmetric minValue and maxValue</code>
     */
    @Support
    Condition betweenSymmetric(T minValue, T maxValue);

    /**
     * Create a condition to check this field against some bounds
     * <p>
     * This is the same as calling
     * <code>betweenSymmetric(minValue).and(maxValue)</code>
     * <p>
     * SQL: <code>this between symmetric minValue and maxValue</code>
     */
    @Support
    Condition betweenSymmetric(Field<T> minValue, Field<T> maxValue);

    /**
     * Create a condition to check this field against some bounds
     * <p>
     * This is the same as calling
     * <code>notBetween(minValue).and(maxValue)</code>
     * <p>
     * SQL: <code>this not between minValue and maxValue</code>
     */
    @Support
    Condition notBetween(T minValue, T maxValue);

    /**
     * Create a condition to check this field against some bounds
     * <p>
     * This is the same as calling
     * <code>notBetween(minValue).and(maxValue)</code>
     * <p>
     * SQL: <code>this not between minValue and maxValue</code>
     */
    @Support
    Condition notBetween(Field<T> minValue, Field<T> maxValue);

    /**
     * Create a condition to check this field against some bounds
     * <p>
     * This is the same as calling
     * <code>notBetweenSymmetric(minValue).and(maxValue)</code>
     * <p>
     * SQL: <code>this not between symmetric minValue and maxValue</code>
     */
    @Support
    Condition notBetweenSymmetric(T minValue, T maxValue);

    /**
     * Create a condition to check this field against some bounds
     * <p>
     * This is the same as calling
     * <code>notBetweenSymmetric(minValue).and(maxValue)</code>
     * <p>
     * SQL: <code>this not between symmetric minValue and maxValue</code>
     */
    @Support
    Condition notBetweenSymmetric(Field<T> minValue, Field<T> maxValue);

    /**
     * Create a condition to check this field against some bounds
     * <p>
     * SQL: <code>this between minValue and maxValue</code>
     */
    @Support
    BetweenAndStep<T> between(T minValue);

    /**
     * Create a condition to check this field against some bounds
     * <p>
     * SQL: <code>this between minValue and maxValue</code>
     */
    @Support
    BetweenAndStep<T> between(Field<T> minValue);

    /**
     * Create a condition to check this field against some bounds
     * <p>
     * SQL: <code>this between symmetric minValue and maxValue</code>
     */
    @Support
    BetweenAndStep<T> betweenSymmetric(T minValue);

    /**
     * Create a condition to check this field against some bounds
     * <p>
     * SQL: <code>this between symmetric minValue and maxValue</code>
     */
    @Support
    BetweenAndStep<T> betweenSymmetric(Field<T> minValue);

    /**
     * Create a condition to check this field against some bounds
     * <p>
     * SQL: <code>this not between minValue and maxValue</code>
     */
    @Support
    BetweenAndStep<T> notBetween(T minValue);

    /**
     * Create a condition to check this field against some bounds
     * <p>
     * SQL: <code>this not between minValue and maxValue</code>
     */
    @Support
    BetweenAndStep<T> notBetween(Field<T> minValue);

    /**
     * Create a condition to check this field against some bounds
     * <p>
     * SQL: <code>this not between symmetric minValue and maxValue</code>
     */
    @Support
    BetweenAndStep<T> notBetweenSymmetric(T minValue);

    /**
     * Create a condition to check this field against some bounds
     * <p>
     * SQL: <code>this not between symmetric minValue and maxValue</code>
     */
    @Support
    BetweenAndStep<T> notBetweenSymmetric(Field<T> minValue);

    // ------------------------------------------------------------------------
    // Comparison predicates
    // ------------------------------------------------------------------------

    /**
     * Compare this field with a value using a dynamic comparator
     *
     * @param comparator The comparator to use for comparing this field with a
     *            value
     * @param value The value to compare this field with
     * @return A comparison predicate
     */
    @Support
    Condition compare(Comparator comparator, T value);

    /**
     * Compare this field with another field using a dynamic comparator
     *
     * @param comparator The comparator to use for comparing this field with
     *            another field
     * @param field The field to compare this field with
     * @return A comparison predicate
     */
    @Support
    Condition compare(Comparator comparator, Field<T> field);

    /**
     * <code>this = value</code>
     * <p>
     * If <code>value == null</code>, then this will return a condition
     * equivalent to {@link #isNull()} for convenience. SQL's ternary
     * <code>NULL</code> logic is rarely of use for Java programmers.
     */
    @Support
    Condition equal(T value);

    /**
     * <code>this = field</code>
     */
    @Support
    Condition equal(Field<T> field);

    /**
     * <code>this = (Select<?> ...)</code>
     */
    @Support
    Condition equal(Select<? extends Record1<T>> query);

    /**
     * <code>this = [quantifier] (Select<?> ...)</code>
     *
     * @see Factory#all(Field)
     * @see Factory#all(Select)
     * @see Factory#all(Object...)
     * @see Factory#any(Field)
     * @see Factory#any(Select)
     * @see Factory#any(Object...)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Condition equal(QuantifiedSelect<? extends Record1<T>> query);

    /**
     * <code>this = value</code>
     * <p>
     * If <code>value == null</code>, then this will return a condition
     * equivalent to {@link #isNull()} for convenience. SQL's ternary
     * <code>NULL</code> logic is rarely of use for Java programmers.
     *
     * @see #equal(Object)
     */
    @Support
    Condition eq(T value);

    /**
     * <code>this = field</code>
     *
     * @see #equal(Field)
     */
    @Support
    Condition eq(Field<T> field);

    /**
     * <code>this = (Select<?> ...)</code>
     *
     * @see #equal(Select)
     */
    @Support
    Condition eq(Select<? extends Record1<T>> query);

    /**
     * <code>this = [quantifier] (Select<?> ...)</code>
     *
     * @see Factory#all(Field)
     * @see Factory#all(Select)
     * @see Factory#all(Object...)
     * @see Factory#any(Field)
     * @see Factory#any(Select)
     * @see Factory#any(Object...)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Condition eq(QuantifiedSelect<? extends Record1<T>> query);

    /**
     * <code>this != value</code>
     * <p>
     * If <code>value == null</code>, then this will return a condition
     * equivalent to {@link #isNotNull()} for convenience. SQL's ternary
     * <code>NULL</code> logic is rarely of use for Java programmers.
     */
    @Support
    Condition notEqual(T value);

    /**
     * <code>this != field</code>
     */
    @Support
    Condition notEqual(Field<T> field);

    /**
     * <code>this != (Select<?> ...)</code>
     */
    @Support
    Condition notEqual(Select<? extends Record1<T>> query);

    /**
     * <code>this != [quantifier] (Select<?> ...)</code>
     *
     * @see Factory#all(Field)
     * @see Factory#all(Select)
     * @see Factory#all(Object...)
     * @see Factory#any(Field)
     * @see Factory#any(Select)
     * @see Factory#any(Object...)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Condition notEqual(QuantifiedSelect<? extends Record1<T>> query);

    /**
     * <code>this != value</code>
     * <p>
     * If <code>value == null</code>, then this will return a condition
     * equivalent to {@link #isNotNull()} for convenience. SQL's ternary
     * <code>NULL</code> logic is rarely of use for Java programmers.
     *
     * @see #notEqual(Object)
     */
    @Support
    Condition ne(T value);

    /**
     * <code>this != field</code>
     *
     * @see #notEqual(Field)
     */
    @Support
    Condition ne(Field<T> field);

    /**
     * <code>this != (Select<?> ...)</code>
     *
     * @see #notEqual(Select)
     */
    @Support
    Condition ne(Select<? extends Record1<T>> query);

    /**
     * <code>this != [quantifier] (Select<?> ...)</code>
     *
     * @see Factory#all(Field)
     * @see Factory#all(Select)
     * @see Factory#all(Object...)
     * @see Factory#any(Field)
     * @see Factory#any(Select)
     * @see Factory#any(Object...)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Condition ne(QuantifiedSelect<? extends Record1<T>> query);

    /**
     * <code>this < value</code>
     */
    @Support
    Condition lessThan(T value);

    /**
     * <code>this < field</code>
     */
    @Support
    Condition lessThan(Field<T> field);

    /**
     * <code>this < (Select<?> ...)</code>
     */
    @Support
    Condition lessThan(Select<? extends Record1<T>> query);

    /**
     * <code>this < [quantifier] (Select<?> ...)</code>
     *
     * @see Factory#all(Field)
     * @see Factory#all(Select)
     * @see Factory#all(Object...)
     * @see Factory#any(Field)
     * @see Factory#any(Select)
     * @see Factory#any(Object...)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Condition lessThan(QuantifiedSelect<? extends Record1<T>> query);

    /**
     * <code>this < value</code>
     *
     * @see #lessThan(Object)
     */
    @Support
    Condition lt(T value);

    /**
     * <code>this < field</code>
     *
     * @see #lessThan(Field)
     */
    @Support
    Condition lt(Field<T> field);

    /**
     * <code>this < (Select<?> ...)</code>
     *
     * @see #lessThan(Select)
     */
    @Support
    Condition lt(Select<? extends Record1<T>> query);

    /**
     * <code>this < [quantifier] (Select<?> ...)</code>
     *
     * @see Factory#all(Field)
     * @see Factory#all(Select)
     * @see Factory#all(Object...)
     * @see Factory#any(Field)
     * @see Factory#any(Select)
     * @see Factory#any(Object...)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Condition lt(QuantifiedSelect<? extends Record1<T>> query);

    /**
     * <code>this <= value</code>
     */
    @Support
    Condition lessOrEqual(T value);

    /**
     * <code>this <= field</code>
     */
    @Support
    Condition lessOrEqual(Field<T> field);

    /**
     * <code>this <= (Select<?> ...)</code>
     */
    @Support
    Condition lessOrEqual(Select<? extends Record1<T>> query);

    /**
     * <code>this <= [quantifier] (Select<?> ...)</code>
     *
     * @see Factory#all(Field)
     * @see Factory#all(Select)
     * @see Factory#all(Object...)
     * @see Factory#any(Field)
     * @see Factory#any(Select)
     * @see Factory#any(Object...)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Condition lessOrEqual(QuantifiedSelect<? extends Record1<T>> query);

    /**
     * <code>this <= value</code>
     *
     * @see #lessOrEqual(Object)
     */
    @Support
    Condition le(T value);

    /**
     * <code>this <= field</code>
     *
     * @see #lessOrEqual(Field)
     */
    @Support
    Condition le(Field<T> field);

    /**
     * <code>this <= (Select<?> ...)</code>
     *
     * @see #lessOrEqual(Select)
     */
    @Support
    Condition le(Select<? extends Record1<T>> query);

    /**
     * <code>this <= [quantifier] (Select<?> ...)</code>
     *
     * @see Factory#all(Field)
     * @see Factory#all(Select)
     * @see Factory#all(Object...)
     * @see Factory#any(Field)
     * @see Factory#any(Select)
     * @see Factory#any(Object...)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Condition le(QuantifiedSelect<? extends Record1<T>> query);

    /**
     * <code>this > value</code>
     */
    @Support
    Condition greaterThan(T value);

    /**
     * <code>this > field</code>
     */
    @Support
    Condition greaterThan(Field<T> field);

    /**
     * <code>this > (Select<?> ...)</code>
     */
    @Support
    Condition greaterThan(Select<? extends Record1<T>> query);

    /**
     * <code>this > [quantifier] (Select<?> ...)</code>
     *
     * @see Factory#all(Field)
     * @see Factory#all(Select)
     * @see Factory#all(Object...)
     * @see Factory#any(Field)
     * @see Factory#any(Select)
     * @see Factory#any(Object...)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Condition greaterThan(QuantifiedSelect<? extends Record1<T>> query);

    /**
     * <code>this > value</code>
     *
     * @see #greaterThan(Object)
     */
    @Support
    Condition gt(T value);

    /**
     * <code>this > field</code>
     *
     * @see #greaterThan(Field)
     */
    @Support
    Condition gt(Field<T> field);

    /**
     * <code>this > (Select<?> ...)</code>
     *
     * @see #greaterThan(Select)
     */
    @Support
    Condition gt(Select<? extends Record1<T>> query);

    /**
     * <code>this > [quantifier] (Select<?> ...)</code>
     *
     * @see Factory#all(Field)
     * @see Factory#all(Select)
     * @see Factory#all(Object...)
     * @see Factory#any(Field)
     * @see Factory#any(Select)
     * @see Factory#any(Object...)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Condition gt(QuantifiedSelect<? extends Record1<T>> query);

    /**
     * <code>this >= value</code>
     */
    @Support
    Condition greaterOrEqual(T value);

    /**
     * <code>this >= field</code>
     */
    @Support
    Condition greaterOrEqual(Field<T> field);

    /**
     * <code>this >= (Select<?> ...)</code>
     */
    @Support
    Condition greaterOrEqual(Select<? extends Record1<T>> query);

    /**
     * <code>this >= [quantifier] (Select<?> ...)</code>
     *
     * @see Factory#all(Field)
     * @see Factory#all(Select)
     * @see Factory#all(Object...)
     * @see Factory#any(Field)
     * @see Factory#any(Select)
     * @see Factory#any(Object...)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Condition greaterOrEqual(QuantifiedSelect<? extends Record1<T>> query);

    /**
     * <code>this >= value</code>
     *
     * @see #greaterOrEqual(Object)
     */
    @Support
    Condition ge(T value);

    /**
     * <code>this >= field</code>
     *
     * @see #greaterOrEqual(Field)
     */
    @Support
    Condition ge(Field<T> field);

    /**
     * <code>this >= (Select<?> ...)</code>
     *
     * @see #greaterOrEqual(Select)
     */
    @Support
    Condition ge(Select<? extends Record1<T>> query);

    /**
     * <code>this >= [quantifier] (Select<?> ...)</code>
     *
     * @see Factory#all(Field)
     * @see Factory#all(Select)
     * @see Factory#all(Object...)
     * @see Factory#any(Field)
     * @see Factory#any(Select)
     * @see Factory#any(Object...)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Condition ge(QuantifiedSelect<? extends Record1<T>> query);

    /**
     * Create a condition to check this field against known string literals for
     * <code>true</code>
     * <p>
     * SQL:
     * <code>lcase(this) in ("1", "y", "yes", "true", "on", "enabled")</code>
     */
    @Support
    Condition isTrue();

    /**
     * Create a condition to check this field against known string literals for
     * <code>false</code>
     * <p>
     * SQL:
     * <code>lcase(this) in ("0", "n", "no", "false", "off", "disabled")</code>
     */
    @Support
    Condition isFalse();

    /**
     * <code>lower(this) = lower(value)</code>
     */
    @Support
    Condition equalIgnoreCase(String value);

    /**
     * <code>lower(this) = lower(value)</code>
     */
    @Support
    Condition equalIgnoreCase(Field<String> value);

    /**
     * <code>lower(this) != lower(value)</code>
     */
    @Support
    Condition notEqualIgnoreCase(String value);

    /**
     * <code>lower(this) != lower(value)</code>
     */
    @Support
    Condition notEqualIgnoreCase(Field<String> value);

    // ------------------------------------------------------------------------
    // Pre-2.0 API. This API is maintained for backwards-compatibility. It will
    // be removed in the future. Consider using equivalent methods from
    // org.jooq.Factory
    // ------------------------------------------------------------------------

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#sign(Field)
     */
    @Support
    Field<Integer> sign();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#abs(Field)
     */
    @Support
    Field<T> abs();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#round(Field)
     */
    @Support
    Field<T> round();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#round(Field, int)
     */
    @Support
    Field<T> round(int decimals);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#floor(Field)
     */
    @Support
    Field<T> floor();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#ceil(Field)
     */
    @Support
    Field<T> ceil();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#sqrt(Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<BigDecimal> sqrt();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#exp(Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<BigDecimal> exp();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#ln(Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<BigDecimal> ln();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#log(Field, int)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<BigDecimal> log(int base);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#power(Field, Number)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<BigDecimal> power(Number exponent);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#acos(Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<BigDecimal> acos();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#asin(Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<BigDecimal> asin();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#atan(Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<BigDecimal> atan();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#atan2(Field, Number)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<BigDecimal> atan2(Number y);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#atan2(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<BigDecimal> atan2(Field<? extends Number> y);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#cos(Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<BigDecimal> cos();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#sin(Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<BigDecimal> sin();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#tan(Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<BigDecimal> tan();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#cot(Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<BigDecimal> cot();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#sinh(Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<BigDecimal> sinh();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#cosh(Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<BigDecimal> cosh();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#tanh(Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<BigDecimal> tanh();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#coth(Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<BigDecimal> coth();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#deg(Field)
     */
    @Support
    Field<BigDecimal> deg();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#rad(Field)
     */
    @Support
    Field<BigDecimal> rad();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#count(Field)
     */
    @Support
    Field<Integer> count();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#countDistinct(Field)
     */
    @Support
    Field<Integer> countDistinct();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#max(Field)
     */
    @Support
    Field<T> max();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#min(Field)
     */
    @Support
    Field<T> min();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#sum(Field)
     */
    @Support
    Field<BigDecimal> sum();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#avg(Field)
     */
    @Support
    Field<BigDecimal> avg();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#median(Field)
     */
    @Support({ HSQLDB, ORACLE, SYBASE })
    Field<BigDecimal> median();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#stddevPop(Field)
     */
    @Support({ ASE, CUBRID, DB2, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<BigDecimal> stddevPop();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#stddevSamp(Field)
     */
    @Support({ ASE, CUBRID, DB2, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<BigDecimal> stddevSamp();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#varPop(Field)
     */
    @Support({ ASE, CUBRID, DB2, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<BigDecimal> varPop();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#varSamp(Field)
     */
    @Support({ ASE, CUBRID, DB2, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<BigDecimal> varSamp();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#count(Field)
     * @see AggregateFunction#over()
     */
    @Support({ CUBRID, DB2, POSTGRES, ORACLE, SQLSERVER, SYBASE })
    WindowPartitionByStep<Integer> countOver();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#max(Field)
     * @see AggregateFunction#over()
     */
    @Support({ CUBRID, DB2, POSTGRES, ORACLE, SQLSERVER, SYBASE })
    WindowPartitionByStep<T> maxOver();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#min(Field)
     * @see AggregateFunction#over()
     */
    @Support({ CUBRID, DB2, POSTGRES, ORACLE, SQLSERVER, SYBASE })
    WindowPartitionByStep<T> minOver();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#sum(Field)
     * @see AggregateFunction#over()
     */
    @Support({ CUBRID, DB2, POSTGRES, ORACLE, SQLSERVER, SYBASE })
    WindowPartitionByStep<BigDecimal> sumOver();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#avg(Field)
     * @see AggregateFunction#over()
     */
    @Support({ CUBRID, DB2, POSTGRES, ORACLE, SQLSERVER, SYBASE })
    WindowPartitionByStep<BigDecimal> avgOver();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#firstValue(Field)
     * @see AggregateFunction#over()
     */
    @Support({ DB2, POSTGRES, ORACLE, SYBASE })
    WindowIgnoreNullsStep<T> firstValue();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#lastValue(Field)
     * @see AggregateFunction#over()
     */
    @Support({ DB2, POSTGRES, ORACLE, SYBASE })
    WindowIgnoreNullsStep<T> lastValue();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#lead(Field)
     * @see AggregateFunction#over()
     */
    @Support({ DB2, POSTGRES, ORACLE })
    WindowIgnoreNullsStep<T> lead();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#lead(Field, int)
     * @see AggregateFunction#over()
     */
    @Support({ DB2, POSTGRES, ORACLE })
    WindowIgnoreNullsStep<T> lead(int offset);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#lead(Field, int, Object)
     * @see AggregateFunction#over()
     */
    @Support({ DB2, POSTGRES, ORACLE })
    WindowIgnoreNullsStep<T> lead(int offset, T defaultValue);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#lead(Field, int, Field)
     * @see AggregateFunction#over()
     */
    @Support({ DB2, POSTGRES, ORACLE })
    WindowIgnoreNullsStep<T> lead(int offset, Field<T> defaultValue);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#lag(Field)
     * @see AggregateFunction#over()
     */
    @Support({ DB2, POSTGRES, ORACLE })
    WindowIgnoreNullsStep<T> lag();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#lag(Field, int)
     * @see AggregateFunction#over()
     */
    @Support({ DB2, POSTGRES, ORACLE })
    WindowIgnoreNullsStep<T> lag(int offset);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#lag(Field, int, Object)
     * @see AggregateFunction#over()
     */
    @Support({ DB2, POSTGRES, ORACLE })
    WindowIgnoreNullsStep<T> lag(int offset, T defaultValue);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#lag(Field, int, Field)
     * @see AggregateFunction#over()
     */
    @Support({ DB2, POSTGRES, ORACLE })
    WindowIgnoreNullsStep<T> lag(int offset, Field<T> defaultValue);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#stddevPop(Field)
     * @see AggregateFunction#over()
     */
    @Support({ CUBRID, DB2, POSTGRES, ORACLE, SQLSERVER, SYBASE })
    WindowPartitionByStep<BigDecimal> stddevPopOver();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#stddevSamp(Field)
     * @see AggregateFunction#over()
     */
    @Support({ CUBRID, DB2, POSTGRES, ORACLE, SQLSERVER, SYBASE })
    WindowPartitionByStep<BigDecimal> stddevSampOver();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#varPop(Field)
     * @see AggregateFunction#over()
     */
    @Support({ CUBRID, DB2, POSTGRES, ORACLE, SQLSERVER, SYBASE })
    WindowPartitionByStep<BigDecimal> varPopOver();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#varSamp(Field)
     * @see AggregateFunction#over()
     */
    @Support({ CUBRID, DB2, POSTGRES, ORACLE, SQLSERVER, SYBASE })
    WindowPartitionByStep<BigDecimal> varSampOver();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#upper(Field)
     */
    @Support
    Field<String> upper();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#lower(Field)
     */
    @Support
    Field<String> lower();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#trim(Field)
     */
    @Support
    Field<String> trim();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#rtrim(Field)
     */
    @Support
    Field<String> rtrim();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#ltrim(Field)
     */
    @Support
    Field<String> ltrim();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#rpad(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<String> rpad(Field<? extends Number> length);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#rpad(Field, int)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<String> rpad(int length);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#rpad(Field, Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<String> rpad(Field<? extends Number> length, Field<String> character);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#rpad(Field, int, char)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<String> rpad(int length, char character);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#lpad(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<String> lpad(Field<? extends Number> length);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#lpad(Field, int)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<String> lpad(int length);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#lpad(Field, Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<String> lpad(Field<? extends Number> length, Field<String> character);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#lpad(Field, int, char)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<String> lpad(int length, char character);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#repeat(Field, int)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<String> repeat(Number count);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#repeat(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<String> repeat(Field<? extends Number> count);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#replace(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    Field<String> replace(Field<String> search);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#replace(Field, String)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    Field<String> replace(String search);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#replace(Field, Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    Field<String> replace(Field<String> search, Field<String> replace);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#replace(Field, String, String)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    Field<String> replace(String search, String replace);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#position(Field, String)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<Integer> position(String search);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#position(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<Integer> position(Field<String> search);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#ascii(Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    Field<Integer> ascii();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#concat(Field...)
     */
    @Support
    Field<String> concat(Field<?>... fields);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#concat(String...)
     */
    @Support
    Field<String> concat(String... values);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#substring(Field, int)
     */
    @Support
    Field<String> substring(int startingPosition);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#substring(Field, Field)
     */
    @Support
    Field<String> substring(Field<? extends Number> startingPosition);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#substring(Field, int, int)
     */
    @Support
    Field<String> substring(int startingPosition, int length);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#substring(Field, Field, Field)
     */
    @Support
    Field<String> substring(Field<? extends Number> startingPosition, Field<? extends Number> length);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#length(Field)
     */
    @Support
    Field<Integer> length();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#charLength(Field)
     */
    @Support
    Field<Integer> charLength();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#bitLength(Field)
     */
    @Support
    Field<Integer> bitLength();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#octetLength(Field)
     */
    @Support
    Field<Integer> octetLength();

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#extract(Field, DatePart)
     */
    @Support
    Field<Integer> extract(DatePart datePart);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#greatest(Field, Field...)
     */
    @Support
    Field<T> greatest(T... others);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#greatest(Field, Field...)
     */
    @Support
    Field<T> greatest(Field<?>... others);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#least(Field, Field...)
     */
    @Support
    Field<T> least(T... others);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#least(Field, Field...)
     */
    @Support
    Field<T> least(Field<?>... others);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#nvl(Field, Object)
     */
    @Support
    Field<T> nvl(T defaultValue);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#nvl(Field, Field)
     */
    @Support
    Field<T> nvl(Field<T> defaultValue);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#nvl2(Field, Object, Object)
     */
    @Support
    <Z> Field<Z> nvl2(Z valueIfNotNull, Z valueIfNull);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#nvl2(Field, Field, Field)
     */
    @Support
    <Z> Field<Z> nvl2(Field<Z> valueIfNotNull, Field<Z> valueIfNull);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#nullif(Field, Object)
     */
    @Support
    Field<T> nullif(T other);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#nullif(Field, Field)
     */
    @Support
    Field<T> nullif(Field<T> other);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#decode(Object, Object, Object)
     */
    @Support
    <Z> Field<Z> decode(T search, Z result);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#decode(Object, Object, Object, Object...)
     */
    @Support
    <Z> Field<Z> decode(T search, Z result, Object... more);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#decode(Field, Field, Field)
     */
    @Support
    <Z> Field<Z> decode(Field<T> search, Field<Z> result);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#decode(Field, Field, Field, Field...)
     */
    @Support
    <Z> Field<Z> decode(Field<T> search, Field<Z> result, Field<?>... more);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#coalesce(Object, Object...)
     */
    @Support
    Field<T> coalesce(T option, T... options);

    /**
     * This method is part of the pre-2.0 API. This API is maintained for
     * backwards-compatibility. It may be removed in the future. Consider using
     * equivalent methods from {@link Executor}
     *
     * @see Factory#coalesce(Field, Field...)
     */
    @Support
    Field<T> coalesce(Field<T> option, Field<?>... options);

}

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
package org.jooq.impl;

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
import static org.jooq.impl.DefaultConfiguration.DEFAULT_CONFIGURATION;
import static org.jooq.impl.Term.ROW_NUMBER;
import static org.jooq.impl.Utils.combine;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSetMetaData;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.AggregateFunction;
import org.jooq.ArrayRecord;
import org.jooq.Case;
import org.jooq.Condition;
import org.jooq.DataType;
import org.jooq.DatePart;
import org.jooq.Field;
import org.jooq.GroupConcatOrderByStep;
import org.jooq.GroupField;
import org.jooq.Name;
import org.jooq.OrderedAggregateFunction;
import org.jooq.Param;
import org.jooq.QuantifiedSelect;
import org.jooq.Query;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Record11;
import org.jooq.Record12;
import org.jooq.Record13;
import org.jooq.Record14;
import org.jooq.Record15;
import org.jooq.Record16;
import org.jooq.Record17;
import org.jooq.Record18;
import org.jooq.Record19;
import org.jooq.Record2;
import org.jooq.Record20;
import org.jooq.Record21;
import org.jooq.Record22;
import org.jooq.Record3;
import org.jooq.Record4;
import org.jooq.Record5;
import org.jooq.Record6;
import org.jooq.Record7;
import org.jooq.Record8;
import org.jooq.Record9;
import org.jooq.Result;
import org.jooq.Row1;
import org.jooq.Row10;
import org.jooq.Row11;
import org.jooq.Row12;
import org.jooq.Row13;
import org.jooq.Row14;
import org.jooq.Row15;
import org.jooq.Row16;
import org.jooq.Row17;
import org.jooq.Row18;
import org.jooq.Row19;
import org.jooq.Row2;
import org.jooq.Row20;
import org.jooq.Row21;
import org.jooq.Row22;
import org.jooq.Row3;
import org.jooq.Row4;
import org.jooq.Row5;
import org.jooq.Row6;
import org.jooq.Row7;
import org.jooq.Row8;
import org.jooq.Row9;
import org.jooq.RowN;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.SelectSelectStep;
import org.jooq.Support;
import org.jooq.Table;
import org.jooq.UDTRecord;
import org.jooq.WindowIgnoreNullsStep;
import org.jooq.WindowOverStep;
import org.jooq.conf.RenderNameStyle;
import org.jooq.conf.Settings;
import org.jooq.exception.SQLDialectNotSupportedException;
import org.jooq.types.DayToSecond;

/**
 * A factory providing implementations to the org.jooq interfaces
 * <p>
 * This factory is the main entry point for client code, to access jOOQ classes
 * and functionality. Here, you can instanciate all of those objects that cannot
 * be accessed through other objects. For example, to create a {@link Field}
 * representing a constant value, you can write:
 * <p>
 * <code><pre>
 * Field&lt;String&gt; field = Factory.val("Hello World")
 * </pre></code>
 * <p>
 * Also, some SQL clauses cannot be expressed easily with DSL, for instance the
 * EXISTS clause, as it is not applied on a concrete object (yet). Hence you
 * should write
 * <p>
 * <code><pre>
 * Condition condition = Factory.exists(Factory.select(...));
 * </pre></code>
 * <p>
 * <h3>Factory and static imports</h3> For increased fluency and readability of
 * your jOOQ client code, it is recommended that you static import all methods
 * from the <code>Factory</code>. For example: <code><pre>
 * import static org.jooq.impl.Factory.*;
 *
 * public class Main {
 *   public static void main(String[] args) {
 *     Factory.select(val("Hello"), inline("World"));
 *     // Factory.val ^^^           ^^^^^^ Factory.inline
 *   }
 * }
 * </pre></code>
 *
 * @author Lukas Eder
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class Factory {

    // -------------------------------------------------------------------------
    // XXX Static subselect factory methods
    // -------------------------------------------------------------------------

    /**
     * Create a new DSL subselect statement.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * select(fields)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#select(Collection)
     */
    @Support
    public static SelectSelectStep<Record> select(Collection<? extends Field<?>> fields) {
        return new SelectImpl<Record>(DEFAULT_CONFIGURATION).select(fields);
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * select(field1, field2)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#select(Field...)
     */
    @Support
    public static SelectSelectStep<Record> select(Field<?>... fields) {
        return new SelectImpl<Record>(DEFAULT_CONFIGURATION).select(fields);
    }

// [jooq-tools] START [select]
    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #select(Field...)}, except that it declares
     * additional record-level typesafety, which is needed by
     * {@link Field#in(Select)}, {@link Field#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * select(field1)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#select(Field...)
     * @see #select(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1> SelectSelectStep<Record1<T1>> select(Field<T1> field1) {
        return (SelectSelectStep) select(new Field[] { field1 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #select(Field...)}, except that it declares
     * additional record-level typesafety, which is needed by
     * {@link Row2#in(Select)}, {@link Row2#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * select(field1, field2)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#select(Field...)
     * @see #select(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2> SelectSelectStep<Record2<T1, T2>> select(Field<T1> field1, Field<T2> field2) {
        return (SelectSelectStep) select(new Field[] { field1, field2 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #select(Field...)}, except that it declares
     * additional record-level typesafety, which is needed by
     * {@link Row3#in(Select)}, {@link Row3#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * select(field1, field2, field3)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#select(Field...)
     * @see #select(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3> SelectSelectStep<Record3<T1, T2, T3>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3) {
        return (SelectSelectStep) select(new Field[] { field1, field2, field3 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #select(Field...)}, except that it declares
     * additional record-level typesafety, which is needed by
     * {@link Row4#in(Select)}, {@link Row4#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * select(field1, field2, field3, field4)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#select(Field...)
     * @see #select(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4> SelectSelectStep<Record4<T1, T2, T3, T4>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4) {
        return (SelectSelectStep) select(new Field[] { field1, field2, field3, field4 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #select(Field...)}, except that it declares
     * additional record-level typesafety, which is needed by
     * {@link Row5#in(Select)}, {@link Row5#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * select(field1, field2, field3, field4, field5)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#select(Field...)
     * @see #select(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5> SelectSelectStep<Record5<T1, T2, T3, T4, T5>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5) {
        return (SelectSelectStep) select(new Field[] { field1, field2, field3, field4, field5 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #select(Field...)}, except that it declares
     * additional record-level typesafety, which is needed by
     * {@link Row6#in(Select)}, {@link Row6#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * select(field1, field2, field3, .., field5, field6)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#select(Field...)
     * @see #select(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6> SelectSelectStep<Record6<T1, T2, T3, T4, T5, T6>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6) {
        return (SelectSelectStep) select(new Field[] { field1, field2, field3, field4, field5, field6 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #select(Field...)}, except that it declares
     * additional record-level typesafety, which is needed by
     * {@link Row7#in(Select)}, {@link Row7#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * select(field1, field2, field3, .., field6, field7)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#select(Field...)
     * @see #select(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7> SelectSelectStep<Record7<T1, T2, T3, T4, T5, T6, T7>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7) {
        return (SelectSelectStep) select(new Field[] { field1, field2, field3, field4, field5, field6, field7 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #select(Field...)}, except that it declares
     * additional record-level typesafety, which is needed by
     * {@link Row8#in(Select)}, {@link Row8#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * select(field1, field2, field3, .., field7, field8)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#select(Field...)
     * @see #select(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8> SelectSelectStep<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8) {
        return (SelectSelectStep) select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #select(Field...)}, except that it declares
     * additional record-level typesafety, which is needed by
     * {@link Row9#in(Select)}, {@link Row9#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * select(field1, field2, field3, .., field8, field9)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#select(Field...)
     * @see #select(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> SelectSelectStep<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9) {
        return (SelectSelectStep) select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #select(Field...)}, except that it declares
     * additional record-level typesafety, which is needed by
     * {@link Row10#in(Select)}, {@link Row10#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * select(field1, field2, field3, .., field9, field10)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#select(Field...)
     * @see #select(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> SelectSelectStep<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10) {
        return (SelectSelectStep) select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #select(Field...)}, except that it declares
     * additional record-level typesafety, which is needed by
     * {@link Row11#in(Select)}, {@link Row11#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * select(field1, field2, field3, .., field10, field11)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#select(Field...)
     * @see #select(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> SelectSelectStep<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11) {
        return (SelectSelectStep) select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #select(Field...)}, except that it declares
     * additional record-level typesafety, which is needed by
     * {@link Row12#in(Select)}, {@link Row12#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * select(field1, field2, field3, .., field11, field12)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#select(Field...)
     * @see #select(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> SelectSelectStep<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12) {
        return (SelectSelectStep) select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #select(Field...)}, except that it declares
     * additional record-level typesafety, which is needed by
     * {@link Row13#in(Select)}, {@link Row13#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * select(field1, field2, field3, .., field12, field13)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#select(Field...)
     * @see #select(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> SelectSelectStep<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13) {
        return (SelectSelectStep) select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #select(Field...)}, except that it declares
     * additional record-level typesafety, which is needed by
     * {@link Row14#in(Select)}, {@link Row14#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * select(field1, field2, field3, .., field13, field14)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#select(Field...)
     * @see #select(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> SelectSelectStep<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14) {
        return (SelectSelectStep) select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #select(Field...)}, except that it declares
     * additional record-level typesafety, which is needed by
     * {@link Row15#in(Select)}, {@link Row15#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * select(field1, field2, field3, .., field14, field15)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#select(Field...)
     * @see #select(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> SelectSelectStep<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15) {
        return (SelectSelectStep) select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #select(Field...)}, except that it declares
     * additional record-level typesafety, which is needed by
     * {@link Row16#in(Select)}, {@link Row16#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * select(field1, field2, field3, .., field15, field16)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#select(Field...)
     * @see #select(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> SelectSelectStep<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16) {
        return (SelectSelectStep) select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #select(Field...)}, except that it declares
     * additional record-level typesafety, which is needed by
     * {@link Row17#in(Select)}, {@link Row17#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * select(field1, field2, field3, .., field16, field17)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#select(Field...)
     * @see #select(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> SelectSelectStep<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17) {
        return (SelectSelectStep) select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #select(Field...)}, except that it declares
     * additional record-level typesafety, which is needed by
     * {@link Row18#in(Select)}, {@link Row18#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * select(field1, field2, field3, .., field17, field18)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#select(Field...)
     * @see #select(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> SelectSelectStep<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18) {
        return (SelectSelectStep) select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #select(Field...)}, except that it declares
     * additional record-level typesafety, which is needed by
     * {@link Row19#in(Select)}, {@link Row19#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * select(field1, field2, field3, .., field18, field19)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#select(Field...)
     * @see #select(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> SelectSelectStep<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19) {
        return (SelectSelectStep) select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #select(Field...)}, except that it declares
     * additional record-level typesafety, which is needed by
     * {@link Row20#in(Select)}, {@link Row20#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * select(field1, field2, field3, .., field19, field20)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#select(Field...)
     * @see #select(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> SelectSelectStep<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20) {
        return (SelectSelectStep) select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #select(Field...)}, except that it declares
     * additional record-level typesafety, which is needed by
     * {@link Row21#in(Select)}, {@link Row21#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * select(field1, field2, field3, .., field20, field21)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#select(Field...)
     * @see #select(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> SelectSelectStep<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21) {
        return (SelectSelectStep) select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #select(Field...)}, except that it declares
     * additional record-level typesafety, which is needed by
     * {@link Row22#in(Select)}, {@link Row22#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * select(field1, field2, field3, .., field21, field22)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#select(Field...)
     * @see #select(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> SelectSelectStep<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22) {
        return (SelectSelectStep) select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22 });
    }

// [jooq-tools] END [select]

    /**
     * Create a new DSL subselect statement.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * selectDistinct(fields)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#selectDistinct(Collection)
     */
    @Support
    public static SelectSelectStep<Record> selectDistinct(Collection<? extends Field<?>> fields) {
        return new SelectImpl<Record>(DEFAULT_CONFIGURATION, true).select(fields);
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * selectDistinct(field1, field2)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#selectDistinct(Field...)
     */
    @Support
    public static SelectSelectStep<Record> selectDistinct(Field<?>... fields) {
        return new SelectImpl<Record>(DEFAULT_CONFIGURATION, true).select(fields);
    }

// [jooq-tools] START [selectDistinct]
    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #selectDistinct(Field...)}, except that it
     * declares additional record-level typesafety, which is needed by
     * {@link Field#in(Select)}, {@link Field#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * selectDistinct(field1)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#selectDistinct(Field...)
     * @see #selectDistinct(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1> SelectSelectStep<Record1<T1>> selectDistinct(Field<T1> field1) {
        return (SelectSelectStep) selectDistinct(new Field[] { field1 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #selectDistinct(Field...)}, except that it
     * declares additional record-level typesafety, which is needed by
     * {@link Row2#in(Select)}, {@link Row2#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * selectDistinct(field1, field2)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#selectDistinct(Field...)
     * @see #selectDistinct(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2> SelectSelectStep<Record2<T1, T2>> selectDistinct(Field<T1> field1, Field<T2> field2) {
        return (SelectSelectStep) selectDistinct(new Field[] { field1, field2 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #selectDistinct(Field...)}, except that it
     * declares additional record-level typesafety, which is needed by
     * {@link Row3#in(Select)}, {@link Row3#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * selectDistinct(field1, field2, field3)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#selectDistinct(Field...)
     * @see #selectDistinct(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3> SelectSelectStep<Record3<T1, T2, T3>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3) {
        return (SelectSelectStep) selectDistinct(new Field[] { field1, field2, field3 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #selectDistinct(Field...)}, except that it
     * declares additional record-level typesafety, which is needed by
     * {@link Row4#in(Select)}, {@link Row4#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * selectDistinct(field1, field2, field3, field4)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#selectDistinct(Field...)
     * @see #selectDistinct(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4> SelectSelectStep<Record4<T1, T2, T3, T4>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4) {
        return (SelectSelectStep) selectDistinct(new Field[] { field1, field2, field3, field4 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #selectDistinct(Field...)}, except that it
     * declares additional record-level typesafety, which is needed by
     * {@link Row5#in(Select)}, {@link Row5#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * selectDistinct(field1, field2, field3, field4, field5)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#selectDistinct(Field...)
     * @see #selectDistinct(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5> SelectSelectStep<Record5<T1, T2, T3, T4, T5>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5) {
        return (SelectSelectStep) selectDistinct(new Field[] { field1, field2, field3, field4, field5 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #selectDistinct(Field...)}, except that it
     * declares additional record-level typesafety, which is needed by
     * {@link Row6#in(Select)}, {@link Row6#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * selectDistinct(field1, field2, field3, .., field5, field6)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#selectDistinct(Field...)
     * @see #selectDistinct(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6> SelectSelectStep<Record6<T1, T2, T3, T4, T5, T6>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6) {
        return (SelectSelectStep) selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #selectDistinct(Field...)}, except that it
     * declares additional record-level typesafety, which is needed by
     * {@link Row7#in(Select)}, {@link Row7#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * selectDistinct(field1, field2, field3, .., field6, field7)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#selectDistinct(Field...)
     * @see #selectDistinct(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7> SelectSelectStep<Record7<T1, T2, T3, T4, T5, T6, T7>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7) {
        return (SelectSelectStep) selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #selectDistinct(Field...)}, except that it
     * declares additional record-level typesafety, which is needed by
     * {@link Row8#in(Select)}, {@link Row8#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * selectDistinct(field1, field2, field3, .., field7, field8)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#selectDistinct(Field...)
     * @see #selectDistinct(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8> SelectSelectStep<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8) {
        return (SelectSelectStep) selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #selectDistinct(Field...)}, except that it
     * declares additional record-level typesafety, which is needed by
     * {@link Row9#in(Select)}, {@link Row9#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * selectDistinct(field1, field2, field3, .., field8, field9)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#selectDistinct(Field...)
     * @see #selectDistinct(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> SelectSelectStep<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9) {
        return (SelectSelectStep) selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #selectDistinct(Field...)}, except that it
     * declares additional record-level typesafety, which is needed by
     * {@link Row10#in(Select)}, {@link Row10#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * selectDistinct(field1, field2, field3, .., field9, field10)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#selectDistinct(Field...)
     * @see #selectDistinct(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> SelectSelectStep<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10) {
        return (SelectSelectStep) selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #selectDistinct(Field...)}, except that it
     * declares additional record-level typesafety, which is needed by
     * {@link Row11#in(Select)}, {@link Row11#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * selectDistinct(field1, field2, field3, .., field10, field11)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#selectDistinct(Field...)
     * @see #selectDistinct(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> SelectSelectStep<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11) {
        return (SelectSelectStep) selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #selectDistinct(Field...)}, except that it
     * declares additional record-level typesafety, which is needed by
     * {@link Row12#in(Select)}, {@link Row12#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * selectDistinct(field1, field2, field3, .., field11, field12)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#selectDistinct(Field...)
     * @see #selectDistinct(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> SelectSelectStep<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12) {
        return (SelectSelectStep) selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #selectDistinct(Field...)}, except that it
     * declares additional record-level typesafety, which is needed by
     * {@link Row13#in(Select)}, {@link Row13#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * selectDistinct(field1, field2, field3, .., field12, field13)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#selectDistinct(Field...)
     * @see #selectDistinct(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> SelectSelectStep<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13) {
        return (SelectSelectStep) selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #selectDistinct(Field...)}, except that it
     * declares additional record-level typesafety, which is needed by
     * {@link Row14#in(Select)}, {@link Row14#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * selectDistinct(field1, field2, field3, .., field13, field14)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#selectDistinct(Field...)
     * @see #selectDistinct(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> SelectSelectStep<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14) {
        return (SelectSelectStep) selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #selectDistinct(Field...)}, except that it
     * declares additional record-level typesafety, which is needed by
     * {@link Row15#in(Select)}, {@link Row15#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * selectDistinct(field1, field2, field3, .., field14, field15)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#selectDistinct(Field...)
     * @see #selectDistinct(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> SelectSelectStep<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15) {
        return (SelectSelectStep) selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #selectDistinct(Field...)}, except that it
     * declares additional record-level typesafety, which is needed by
     * {@link Row16#in(Select)}, {@link Row16#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * selectDistinct(field1, field2, field3, .., field15, field16)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#selectDistinct(Field...)
     * @see #selectDistinct(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> SelectSelectStep<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16) {
        return (SelectSelectStep) selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #selectDistinct(Field...)}, except that it
     * declares additional record-level typesafety, which is needed by
     * {@link Row17#in(Select)}, {@link Row17#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * selectDistinct(field1, field2, field3, .., field16, field17)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#selectDistinct(Field...)
     * @see #selectDistinct(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> SelectSelectStep<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17) {
        return (SelectSelectStep) selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #selectDistinct(Field...)}, except that it
     * declares additional record-level typesafety, which is needed by
     * {@link Row18#in(Select)}, {@link Row18#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * selectDistinct(field1, field2, field3, .., field17, field18)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#selectDistinct(Field...)
     * @see #selectDistinct(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> SelectSelectStep<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18) {
        return (SelectSelectStep) selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #selectDistinct(Field...)}, except that it
     * declares additional record-level typesafety, which is needed by
     * {@link Row19#in(Select)}, {@link Row19#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * selectDistinct(field1, field2, field3, .., field18, field19)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#selectDistinct(Field...)
     * @see #selectDistinct(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> SelectSelectStep<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19) {
        return (SelectSelectStep) selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #selectDistinct(Field...)}, except that it
     * declares additional record-level typesafety, which is needed by
     * {@link Row20#in(Select)}, {@link Row20#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * selectDistinct(field1, field2, field3, .., field19, field20)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#selectDistinct(Field...)
     * @see #selectDistinct(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> SelectSelectStep<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20) {
        return (SelectSelectStep) selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #selectDistinct(Field...)}, except that it
     * declares additional record-level typesafety, which is needed by
     * {@link Row21#in(Select)}, {@link Row21#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * selectDistinct(field1, field2, field3, .., field20, field21)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#selectDistinct(Field...)
     * @see #selectDistinct(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> SelectSelectStep<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21) {
        return (SelectSelectStep) selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21 });
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * This is the same as {@link #selectDistinct(Field...)}, except that it
     * declares additional record-level typesafety, which is needed by
     * {@link Row22#in(Select)}, {@link Row22#equal(Select)} and other predicate
     * building methods taking subselect arguments.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * selectDistinct(field1, field2, field3, .., field21, field22)
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Executor#selectDistinct(Field...)
     * @see #selectDistinct(Field...)
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> SelectSelectStep<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22) {
        return (SelectSelectStep) selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22 });
    }

// [jooq-tools] END [selectDistinct]

    /**
     * Create a new DSL subselect statement.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * selectZero()
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Factory#zero()
     * @see Executor#selectZero()
     */
    @Support
    public static SelectSelectStep<Record1<Integer>> selectZero() {
        return new SelectImpl<Record1<Integer>>(DEFAULT_CONFIGURATION).select(zero());
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * selectOne()
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Factory#one()
     * @see Executor#selectOne()
     */
    @Support
    public static SelectSelectStep<Record1<Integer>> selectOne() {
        return new SelectImpl<Record1<Integer>>(DEFAULT_CONFIGURATION).select(one());
    }

    /**
     * Create a new DSL subselect statement.
     * <p>
     * Unlike {@link Select} factory methods in the {@link Executor} API, this
     * creates an unattached, and thus not directly renderable or executable
     * <code>SELECT</code> statement. You can use this statement in two ways:
     * <ul>
     * <li>As a subselect within another select</li>
     * <li>As a statement, after attaching it using
     * {@link Select#attach(org.jooq.Configuration)}</li>
     * </ul>
     * <p>
     * Example: <code><pre>
     * import static org.jooq.impl.Factory.*;
     *
     * // [...]
     *
     * selectCount()
     *  .from(table1)
     *  .join(table2).on(field1.equal(field2))
     *  .where(field1.greaterThan(100))
     *  .orderBy(field2);
     * </pre></code>
     *
     * @see Factory#count()
     * @see Executor#selectCount()
     */
    @Support
    public static SelectSelectStep<Record1<Integer>> selectCount() {
        return new SelectImpl<Record1<Integer>>(DEFAULT_CONFIGURATION).select(count());
    }

    // -------------------------------------------------------------------------
    // XXX Quantified comparison predicate expressions
    // -------------------------------------------------------------------------

    /**
     * Create an <code>ALL</code> quantified select to be used in quantified
     * comparison predicate expressions.
     *
     * @see Field#equal(QuantifiedSelect)
     * @see Field#notEqual(QuantifiedSelect)
     * @see Field#greaterThan(QuantifiedSelect)
     * @see Field#greaterOrEqual(QuantifiedSelect)
     * @see Field#lessThan(QuantifiedSelect)
     * @see Field#lessOrEqual(QuantifiedSelect)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static <R extends Record> QuantifiedSelect<R> all(Select<R> select) {
        return new QuantifiedSelectImpl<R>(Quantifier.ALL, select);
    }

    /**
     * Create an <code>ALL</code> quantified select to be used in quantified
     * comparison predicate expressions.
     * <p>
     * This is natively supported by {@link SQLDialect#POSTGRES}. Other dialects
     * will render a subselect unnesting the array.
     *
     * @see Field#equal(QuantifiedSelect)
     * @see Field#notEqual(QuantifiedSelect)
     * @see Field#greaterThan(QuantifiedSelect)
     * @see Field#greaterOrEqual(QuantifiedSelect)
     * @see Field#lessThan(QuantifiedSelect)
     * @see Field#lessOrEqual(QuantifiedSelect)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static <T> QuantifiedSelect<Record1<T>> all(T... array) {
        return all(val(array));
    }

    /**
     * Create an <code>ALL</code> quantified select to be used in quantified
     * comparison predicate expressions.
     * <p>
     * This is natively supported by {@link SQLDialect#POSTGRES}. Other dialects
     * will render a subselect unnesting the array.
     *
     * @see Field#equal(QuantifiedSelect)
     * @see Field#notEqual(QuantifiedSelect)
     * @see Field#greaterThan(QuantifiedSelect)
     * @see Field#greaterOrEqual(QuantifiedSelect)
     * @see Field#lessThan(QuantifiedSelect)
     * @see Field#lessOrEqual(QuantifiedSelect)
     */
    @Support({ H2, HSQLDB, POSTGRES })
    public static <T> QuantifiedSelect<Record1<T>> all(Field<T[]> array) {
        return new QuantifiedSelectImpl<Record1<T>>(Quantifier.ALL, array);
    }

    /**
     * Create an <code>ANY</code> quantified select to be used in quantified
     * comparison predicate expressions.
     *
     * @see Field#equal(QuantifiedSelect)
     * @see Field#notEqual(QuantifiedSelect)
     * @see Field#greaterThan(QuantifiedSelect)
     * @see Field#greaterOrEqual(QuantifiedSelect)
     * @see Field#lessThan(QuantifiedSelect)
     * @see Field#lessOrEqual(QuantifiedSelect)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static <R extends Record> QuantifiedSelect<R> any(Select<R> select) {
        return new QuantifiedSelectImpl<R>(Quantifier.ANY, select);
    }

    /**
     * Create an <code>ANY</code> quantified select to be used in quantified
     * comparison predicate expressions.
     * <p>
     * This is natively supported by {@link SQLDialect#POSTGRES}. Other dialects
     * will render a subselect unnesting the array.
     *
     * @see Field#equal(QuantifiedSelect)
     * @see Field#notEqual(QuantifiedSelect)
     * @see Field#greaterThan(QuantifiedSelect)
     * @see Field#greaterOrEqual(QuantifiedSelect)
     * @see Field#lessThan(QuantifiedSelect)
     * @see Field#lessOrEqual(QuantifiedSelect)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static <T> QuantifiedSelect<Record1<T>> any(T... array) {
        return any(val(array));
    }

    /**
     * Create an <code>ANY</code> quantified select to be used in quantified
     * comparison predicate expressions.
     * <p>
     * This is natively supported by {@link SQLDialect#POSTGRES}. Other dialects
     * will render a subselect unnesting the array.
     *
     * @see Field#equal(QuantifiedSelect)
     * @see Field#notEqual(QuantifiedSelect)
     * @see Field#greaterThan(QuantifiedSelect)
     * @see Field#greaterOrEqual(QuantifiedSelect)
     * @see Field#lessThan(QuantifiedSelect)
     * @see Field#lessOrEqual(QuantifiedSelect)
     */
    @Support({ H2, HSQLDB, POSTGRES })
    public static <T> QuantifiedSelect<Record1<T>> any(Field<T[]> array) {
        return new QuantifiedSelectImpl<Record1<T>>(Quantifier.ANY, array);
    }

    // -------------------------------------------------------------------------
    // XXX Conversion of objects into tables
    // -------------------------------------------------------------------------

    /**
     * A synonym for {@link Select#asTable()}. It might look a bit more fluent
     * like this, to some users
     *
     * @see Select#asTable()
     */
    public static <R extends Record> Table<R> table(Select<R> select) {
        return select.asTable();
    }

    /**
     * A synonym for {@link #unnest(List)}
     *
     * @see #unnest(List)
     */
    @Support
    public static Table<?> table(List<?> list) {
        return table(list.toArray());
    }

    /**
     * A synonym for {@link #unnest(Object[])}
     *
     * @see #unnest(Object[])
     */
    @Support
    public static Table<?> table(Object[] array) {
        return table(val(array));
    }

    /**
     * A synonym for {@link #unnest(ArrayRecord)}
     *
     * @see #unnest(ArrayRecord)
     */
    @Support(ORACLE)
    public static Table<?> table(ArrayRecord<?> array) {
        return table(val(array));
    }

    /**
     * A synonym for {@link #unnest(Field)}
     *
     * @see #unnest(Field)
     */
    @Support({ H2, HSQLDB, POSTGRES, ORACLE })
    public static Table<?> table(Field<?> cursor) {
        return unnest(cursor);
    }

    /**
     * Create a table from a list of values
     * <p>
     * This is equivalent to the <code>TABLE</code> function for H2, or the
     * <code>UNNEST</code> function in HSQLDB and Postgres
     * <p>
     * For Oracle, use {@link #table(ArrayRecord)} instead, as Oracle knows only
     * typed arrays
     * <p>
     * In all other dialects, unnesting of arrays is simulated using several
     * <code>UNION ALL</code> connected subqueries.
     */
    @Support
    public static Table<?> unnest(List<?> list) {
        return unnest(list.toArray());
    }

    /**
     * Create a table from an array of values
     * <p>
     * This is equivalent to the <code>TABLE</code> function for H2, or the
     * <code>UNNEST</code> function in HSQLDB and Postgres
     * <p>
     * For Oracle, use {@link #table(ArrayRecord)} instead, as Oracle knows only
     * typed arrays
     * <p>
     * In all other dialects, unnesting of arrays is simulated using several
     * <code>UNION ALL</code> connected subqueries.
     */
    @Support
    public static Table<?> unnest(Object[] array) {
        return unnest(val(array));
    }

    /**
     * Create a table from an array of values
     * <p>
     * This wraps the argument array in a <code>TABLE</code> function for
     * Oracle. Currently, only Oracle knows typed arrays
     */
    @Support(ORACLE)
    public static Table<?> unnest(ArrayRecord<?> array) {
        return unnest(val(array));
    }

    /**
     * Create a table from a field. The supplied field can have any of these
     * types:
     * <ul>
     * <li> {@link Result}: For <code>CURSOR</code> or <code>REF CURSOR</code>
     * fields, typically fetched from stored functions or from nested tables</li>
     * <li> {@link ArrayRecord}: For Oracle-style <code>VARRAY</code> types.</li>
     * <li> {@link Object}[]: Array types, for other RDBMS's ARRAY types (e.g.
     * H2, HSQLDB, and Postgres)</li>
     * <li> {@link Object}: Any other type that jOOQ will try to convert in an
     * array first, before converting that array into a table</li>
     * </ul>
     * <p>
     * This functionality has only limited scope when used in H2, as ARRAY types
     * involved with stored functions can only be of type <code>Object[]</code>.
     * Such arrays are converted into <code>VARCHAR</code> arrays by jOOQ.
     * <p>
     * In all dialects where arrays are not supported, unnesting of arrays is
     * simulated using several <code>UNION ALL</code> connected subqueries.
     */
    @Support({ H2, HSQLDB, POSTGRES, ORACLE })
    public static Table<?> unnest(Field<?> cursor) {
        if (cursor == null) {
            throw new IllegalArgumentException();
        }

        // The field is an actual CURSOR or REF CURSOR returned from a stored
        // procedure or from a NESTED TABLE
        else if (cursor.getType() == Result.class) {
            return new FunctionTable<Record>(cursor);
        }

        // The field is an Oracle-style VARRAY constant
        else if (ArrayConstant.class.isAssignableFrom(cursor.getClass())) {
            return new ArrayTable(cursor);
        }

        // The field is an Oracle-style VARRAY field
        else if (ArrayRecord.class.isAssignableFrom(cursor.getDataType().getType())) {
            return new ArrayTable(cursor);
        }

        // The field is a regular array
        else if (cursor.getType().isArray() && cursor.getType() != byte[].class) {
            return new ArrayTable(cursor);
        }

        // The field has any other type. Try to make it an array
        throw new SQLDialectNotSupportedException("Converting arbitrary types into array tables is currently not supported");
    }

    // -------------------------------------------------------------------------
    // XXX SQL identifiers
    // -------------------------------------------------------------------------

    /**
     * Create a new SQL identifier using a qualified name.
     * <p>
     * Use this method to construct syntax-safe, SQL-injection-safe SQL
     * identifiers for use in plain SQL where {@link QueryPart} objects are
     * accepted. For instance, this can be used with any of these methods:
     * <ul>
     * <li> {@link #field(String, QueryPart...)}</li>
     * <li> {@link #field(String, Class, QueryPart...)}</li>
     * <li> {@link #field(String, DataType, QueryPart...)}</li>
     * </ul>
     * <p>
     * An example: <code><pre>
     * // This qualified name here
     * name("book", "title");
     *
     * // ... will render this SQL on SQL Server with RenderNameStyle.QUOTED set
     * [book].[title]
     * </pre></code>
     *
     * @param qualifiedName The SQL identifier's qualified name parts
     * @return A {@link QueryPart} that will render the SQL identifier
     */
    public static Name name(String... qualifiedName) {
        return new NameImpl(qualifiedName);
    }

    /**
     * Create a qualified schema, given its schema name
     * <p>
     * This constructs a schema reference given the schema's qualified name.
     * jOOQ will render the schema name according to your
     * {@link Settings#getRenderNameStyle()} settings. Choose
     * {@link RenderNameStyle#QUOTED} to prevent syntax errors and/or SQL
     * injection.
     * <p>
     * Example: <code><pre>
     * // This schema...
     * schemaByName("MY_SCHEMA");
     *
     * // ... will render this SQL on SQL Server with RenderNameStyle.QUOTED set
     * [MY_SCHEMA]
     * </pre></code>
     *
     * @param name The schema's reference name.
     * @return A schema referenced by <code>name</code>
     */
    @Support
    public static Schema schemaByName(String name) {
        return new SchemaImpl(name);
    }

    /**
     * Create a qualified table, given its table name
     * <p>
     * This constructs a table reference given the table's qualified name. jOOQ
     * will render the table name according to your
     * {@link Settings#getRenderNameStyle()} settings. Choose
     * {@link RenderNameStyle#QUOTED} to prevent syntax errors and/or SQL
     * injection.
     * <p>
     * Example: <code><pre>
     * // This table...
     * tableByName("MY_SCHEMA", "MY_TABLE");
     *
     * // ... will render this SQL on SQL Server with RenderNameStyle.QUOTED set
     * [MY_SCHEMA].[MY_TABLE]
     * </pre></code>
     *
     * @param qualifiedName The various parts making up your table's reference
     *            name.
     * @return A table referenced by <code>tableName</code>
     */
    @Support
    public static Table<Record> tableByName(String... qualifiedName) {
        return new QualifiedTable(qualifiedName);
    }

    /**
     * Create a qualified field, given its (qualified) field name.
     * <p>
     * This constructs a field reference given the field's qualified name. jOOQ
     * will render the field name according to your
     * {@link Settings#getRenderNameStyle()} settings. Choose
     * {@link RenderNameStyle#QUOTED} to prevent syntax errors and/or SQL
     * injection.
     * <p>
     * Example: <code><pre>
     * // This field...
     * fieldByName("MY_SCHEMA", "MY_TABLE", "MY_FIELD");
     *
     * // ... will render this SQL on SQL Server with RenderNameStyle.QUOTED set
     * [MY_SCHEMA].[MY_TABLE].[MY_FIELD]
     * </pre></code>
     * <p>
     * Another example: <code><pre>
     * create.select(field("length({1})", Integer.class, fieldByName("TITLE")))
     *       .from(tableByName("T_BOOK"))
     *       .fetch();
     *
     * // ... will execute this SQL on SQL Server:
     * select length([TITLE]) from [T_BOOK]
     * </pre></code>
     *
     * @param qualifiedName The various parts making up your field's reference
     *            name.
     * @return A field referenced by <code>fieldName</code>
     */
    @Support
    public static Field<Object> fieldByName(String... qualifiedName) {
        return fieldByName(Object.class, qualifiedName);
    }

    /**
     * Create a qualified field, given its (qualified) field name.
     * <p>
     * This constructs a field reference given the field's qualified name. jOOQ
     * will render the field name according to your
     * {@link Settings#getRenderNameStyle()} settings. Choose
     * {@link RenderNameStyle#QUOTED} to prevent syntax errors and/or SQL
     * injection.
     * <p>
     * Example: <code><pre>
     * // This field...
     * fieldByName("MY_SCHEMA", "MY_TABLE", "MY_FIELD");
     *
     * // ... will render this SQL on SQL Server with RenderNameStyle.QUOTED set
     * [MY_SCHEMA].[MY_TABLE].[MY_FIELD]
     * </pre></code>
     * <p>
     * Another example: <code><pre>
     * create.select(field("length({1})", Integer.class, fieldByName("TITLE")))
     *       .from(tableByName("T_BOOK"))
     *       .fetch();
     *
     * // ... will execute this SQL on SQL Server:
     * select length([TITLE]) from [T_BOOK]
     * </pre></code>
     *
     * @param qualifiedName The various parts making up your field's reference
     *            name.
     * @param type The type of the returned field
     * @return A field referenced by <code>fieldName</code>
     */
    @Support
    public static <T> Field<T> fieldByName(Class<T> type, String... qualifiedName) {
        return fieldByName(getDataType(type), qualifiedName);
    }

    /**
     * Create a qualified field, given its (qualified) field name.
     * <p>
     * This constructs a field reference given the field's qualified name. jOOQ
     * will render the field name according to your
     * {@link Settings#getRenderNameStyle()} settings. Choose
     * {@link RenderNameStyle#QUOTED} to prevent syntax errors and/or SQL
     * injection.
     * <p>
     * Example: <code><pre>
     * // This field...
     * fieldByName("MY_SCHEMA", "MY_TABLE", "MY_FIELD");
     *
     * // ... will render this SQL on SQL Server with RenderNameStyle.QUOTED set
     * [MY_SCHEMA].[MY_TABLE].[MY_FIELD]
     * </pre></code>
     * <p>
     * Another example: <code><pre>
     * create.select(field("length({1})", Integer.class, fieldByName("TITLE")))
     *       .from(tableByName("T_BOOK"))
     *       .fetch();
     *
     * // ... will execute this SQL on SQL Server:
     * select length([TITLE]) from [T_BOOK]
     * </pre></code>
     *
     * @param qualifiedName The various parts making up your field's reference
     *            name.
     * @param type The type of the returned field
     * @return A field referenced by <code>fieldName</code>
     */
    @Support
    public static <T> Field<T> fieldByName(DataType<T> type, String... qualifiedName) {
        return new QualifiedField<T>(type, qualifiedName);
    }

    // -------------------------------------------------------------------------
    // XXX Plain SQL object factory
    // -------------------------------------------------------------------------

    /**
     * A custom SQL clause that can render arbitrary table expressions.
     * <p>
     * A plain SQL table is a table that can contain user-defined plain SQL,
     * because sometimes it is easier to express things directly in SQL, for
     * instance complex, but static subqueries or tables from different schemas.
     * <p>
     * Example
     * <p>
     * <code><pre>
     * String sql = "SELECT * FROM USER_TABLES WHERE OWNER = 'MY_SCHEMA'";
     * </pre></code>
     * <p>
     * The provided SQL must evaluate as a table whose type can be dynamically
     * discovered using JDBC's {@link ResultSetMetaData} methods. That way, you
     * can be sure that calling methods, such as {@link Table#getFields()} will
     * list the actual fields returned from your result set.
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @param sql The SQL
     * @return A table wrapping the plain SQL
     */
    @Support
    public static Table<Record> table(String sql) {
        return table(sql, new Object[0]);
    }

    /**
     * A custom SQL clause that can render arbitrary table expressions.
     * <p>
     * A plain SQL table is a table that can contain user-defined plain SQL,
     * because sometimes it is easier to express things directly in SQL, for
     * instance complex, but static subqueries or tables from different schemas.
     * There must be as many binding variables contained in the SQL, as passed
     * in the bindings parameter
     * <p>
     * Example
     * <p>
     * <code><pre>
     * String sql = "SELECT * FROM USER_TABLES WHERE OWNER = ?";
     * Object[] bindings = new Object[] { "MY_SCHEMA" };
     * </pre></code>
     * <p>
     * The provided SQL must evaluate as a table whose type can be dynamically
     * discovered using JDBC's {@link ResultSetMetaData} methods. That way, you
     * can be sure that calling methods, such as {@link Table#getFields()} will
     * list the actual fields returned from your result set.
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @param sql The SQL
     * @return A table wrapping the plain SQL
     */
    @Support
    public static Table<Record> table(String sql, Object... bindings) {
        return new SQLTable(sql, bindings);
    }

    /**
     * A custom SQL clause that can render arbitrary table expressions.
     * <p>
     * A plain SQL table is a table that can contain user-defined plain SQL,
     * because sometimes it is easier to express things directly in SQL, for
     * instance complex, but static subqueries or tables from different schemas.
     * <p>
     * Example
     * <p>
     * <code><pre>
     * String sql = "SELECT * FROM USER_TABLES WHERE {0}";
     * QueryPart[] parts = new QueryPart[] { USER_TABLES.OWNER.equal("MY_SCHEMA") };
     * </pre></code>
     * <p>
     * The provided SQL must evaluate as a table whose type can be dynamically
     * discovered using JDBC's {@link ResultSetMetaData} methods. That way, you
     * can be sure that calling methods, such as {@link Table#getFields()} will
     * list the actual fields returned from your result set.
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @param sql The SQL clause, containing {numbered placeholders} where query
     *            parts can be injected
     * @param parts The {@link QueryPart} objects that are rendered at the
     *            {numbered placeholder} locations
     * @return A table wrapping the plain SQL
     */
    @Support
    public static Table<Record> table(String sql, QueryPart... parts) {
        return new SQLTable(sql, parts);
    }

    /**
     * A PlainSQLField is a field that can contain user-defined plain SQL,
     * because sometimes it is easier to express things directly in SQL, for
     * instance complex proprietary functions. There must not be any binding
     * variables contained in the SQL.
     * <p>
     * Example:
     * <p>
     * <code><pre>
     * String sql = "DECODE(MY_FIELD, 1, 100, 200)";
     * </pre></code>
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @param sql The SQL
     * @return A field wrapping the plain SQL
     */
    @Support
    public static Field<Object> field(String sql) {
        return field(sql, new Object[0]);
    }

    /**
     * A PlainSQLField is a field that can contain user-defined plain SQL,
     * because sometimes it is easier to express things directly in SQL, for
     * instance complex proprietary functions. There must be as many binding
     * variables contained in the SQL, as passed in the bindings parameter
     * <p>
     * Example:
     * <p>
     * <code><pre>
     * String sql = "DECODE(MY_FIELD, ?, ?, ?)";
     * Object[] bindings = new Object[] { 1, 100, 200 };</pre></code>
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @param sql The SQL
     * @param bindings The bindings for the field
     * @return A field wrapping the plain SQL
     */
    @Support
    public static Field<Object> field(String sql, Object... bindings) {
        return field(sql, Object.class, bindings);
    }

    /**
     * A PlainSQLField is a field that can contain user-defined plain SQL,
     * because sometimes it is easier to express things directly in SQL, for
     * instance complex proprietary functions. There must not be any binding
     * variables contained in the SQL.
     * <p>
     * Example:
     * <p>
     * <code><pre>
     * String sql = "DECODE(MY_FIELD, 1, 100, 200)";
     * </pre></code>
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @param sql The SQL
     * @param type The field type
     * @return A field wrapping the plain SQL
     */
    @Support
    public static <T> Field<T> field(String sql, Class<T> type) {
        return field(sql, type, new Object[0]);
    }

    /**
     * A PlainSQLField is a field that can contain user-defined plain SQL,
     * because sometimes it is easier to express things directly in SQL, for
     * instance complex proprietary functions. There must be as many binding
     * variables contained in the SQL, as passed in the bindings parameter
     * <p>
     * Example:
     * <p>
     * <code><pre>
     * String sql = "DECODE(MY_FIELD, ?, ?, ?)";
     * Object[] bindings = new Object[] { 1, 100, 200 };</pre></code>
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @param sql The SQL
     * @param type The field type
     * @param bindings The bindings for the field
     * @return A field wrapping the plain SQL
     */
    @Support
    public static <T> Field<T> field(String sql, Class<T> type, Object... bindings) {
        return field(sql, getDataType(type), bindings);
    }

    /**
     * A PlainSQLField is a field that can contain user-defined plain SQL,
     * because sometimes it is easier to express things directly in SQL, for
     * instance complex proprietary functions. There must not be any binding
     * variables contained in the SQL.
     * <p>
     * Example:
     * <p>
     * <code><pre>
     * String sql = "DECODE(MY_FIELD, 1, 100, 200)";
     * </pre></code>
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @param sql The SQL
     * @param type The field type
     * @return A field wrapping the plain SQL
     */
    @Support
    public static <T> Field<T> field(String sql, DataType<T> type) {
        return field(sql, type, new Object[0]);
    }

    /**
     * A PlainSQLField is a field that can contain user-defined plain SQL,
     * because sometimes it is easier to express things directly in SQL, for
     * instance complex proprietary functions. There must be as many binding
     * variables contained in the SQL, as passed in the bindings parameter
     * <p>
     * Example:
     * <p>
     * <code><pre>
     * String sql = "DECODE(MY_FIELD, ?, ?, ?)";
     * Object[] bindings = new Object[] { 1, 100, 200 };</pre></code>
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @param sql The SQL
     * @param type The field type
     * @param bindings The bindings for the field
     * @return A field wrapping the plain SQL
     */
    @Support
    public static <T> Field<T> field(String sql, DataType<T> type, Object... bindings) {
        return new SQLField<T>(sql, type, bindings);
    }

    /**
     * A custom SQL clause that can render arbitrary SQL elements.
     * <p>
     * This is useful for constructing more complex SQL syntax elements wherever
     * <code>Field</code> types are expected. An example for this is MySQL's
     * <code>GROUP_CONCAT</code> aggregate function, which has MySQL-specific
     * keywords that are hard to reflect in jOOQ's DSL: <code><pre>
     * GROUP_CONCAT([DISTINCT] expr [,expr ...]
     *       [ORDER BY {unsigned_integer | col_name | expr}
     *           [ASC | DESC] [,col_name ...]]
     *       [SEPARATOR str_val])
     *       </pre></code>
     * <p>
     * The above MySQL function can be expressed as such: <code><pre>
     * field("GROUP_CONCAT(DISTINCT {0} ORDER BY {1} ASC DEPARATOR '-')", expr1, expr2);
     * </pre></code>
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses! One way to escape
     * literals is to use {@link #name(String...)} and similar methods
     *
     * @param sql The SQL clause, containing {numbered placeholders} where query
     *            parts can be injected
     * @param parts The {@link QueryPart} objects that are rendered at the
     *            {numbered placeholder} locations
     * @return A field wrapping the plain SQL
     */
    public static Field<Object> field(String sql, QueryPart... parts) {
        return new SQLField<Object>(sql, SQLDataType.OTHER, parts);
    }

    /**
     * A custom SQL clause that can render arbitrary SQL elements.
     * <p>
     * This is useful for constructing more complex SQL syntax elements wherever
     * <code>Field</code> types are expected. An example for this is MySQL's
     * <code>GROUP_CONCAT</code> aggregate function, which has MySQL-specific
     * keywords that are hard to reflect in jOOQ's DSL: <code><pre>
     * GROUP_CONCAT([DISTINCT] expr [,expr ...]
     *       [ORDER BY {unsigned_integer | col_name | expr}
     *           [ASC | DESC] [,col_name ...]]
     *       [SEPARATOR str_val])
     *       </pre></code>
     * <p>
     * The above MySQL function can be expressed as such: <code><pre>
     * field("GROUP_CONCAT(DISTINCT {0} ORDER BY {1} ASC DEPARATOR '-')", expr1, expr2);
     * </pre></code>
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses! One way to escape
     * literals is to use {@link #name(String...)} and similar methods
     *
     * @param sql The SQL clause, containing {numbered placeholders} where query
     *            parts can be injected
     * @param type The field type
     * @param parts The {@link QueryPart} objects that are rendered at the
     *            {numbered placeholder} locations
     * @return A field wrapping the plain SQL
     */
    public static <T> Field<T> field(String sql, Class<T> type, QueryPart... parts) {
        return new SQLField<T>(sql, getDataType(type), parts);
    }

    /**
     * A custom SQL clause that can render arbitrary SQL elements.
     * <p>
     * This is useful for constructing more complex SQL syntax elements wherever
     * <code>Field</code> types are expected. An example for this is MySQL's
     * <code>GROUP_CONCAT</code> aggregate function, which has MySQL-specific
     * keywords that are hard to reflect in jOOQ's DSL: <code><pre>
     * GROUP_CONCAT([DISTINCT] expr [,expr ...]
     *       [ORDER BY {unsigned_integer | col_name | expr}
     *           [ASC | DESC] [,col_name ...]]
     *       [SEPARATOR str_val])
     *       </pre></code>
     * <p>
     * The above MySQL function can be expressed as such: <code><pre>
     * field("GROUP_CONCAT(DISTINCT {0} ORDER BY {1} ASC DEPARATOR '-')", expr1, expr2);
     * </pre></code>
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses! One way to escape
     * literals is to use {@link #name(String...)} and similar methods
     *
     * @param sql The SQL clause, containing {numbered placeholders} where query
     *            parts can be injected
     * @param type The field type
     * @param parts The {@link QueryPart} objects that are rendered at the
     *            {numbered placeholder} locations
     * @return A field wrapping the plain SQL
     */
    public static <T> Field<T> field(String sql, DataType<T> type, QueryPart... parts) {
        return new SQLField<T>(sql, type, parts);
    }

    /**
     * <code>function()</code> can be used to access native functions that are
     * not yet or insufficiently supported by jOOQ
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @param name The function name (without parentheses)
     * @param type The function return type
     * @param arguments The function arguments
     */
    @Support
    public static <T> Field<T> function(String name, Class<T> type, Field<?>... arguments) {
        return function(name, getDataType(type), nullSafe(arguments));
    }

    /**
     * <code>function()</code> can be used to access native functions that are
     * not yet or insufficiently supported by jOOQ
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @param name The function name (without parentheses)
     * @param type The function return type
     * @param arguments The function arguments
     */
    @Support
    public static <T> Field<T> function(String name, DataType<T> type, Field<?>... arguments) {
        return new Function<T>(name, type, nullSafe(arguments));
    }

    /**
     * Create a new condition holding plain SQL. There must not be any binding
     * variables contained in the SQL
     * <p>
     * Example:
     * <p>
     * <code><pre>
     * String sql = "(X = 1 and Y = 2)";</pre></code>
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @param sql The SQL
     * @return A condition wrapping the plain SQL
     */
    @Support
    public static Condition condition(String sql) {
        return condition(sql, new Object[0]);
    }

    /**
     * Create a new condition holding plain SQL. There must be as many binding
     * variables contained in the SQL, as passed in the bindings parameter
     * <p>
     * Example:
     * <p>
     * <code><pre>
     * String sql = "(X = ? and Y = ?)";
     * Object[] bindings = new Object[] { 1, 2 };</pre></code>
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @param sql The SQL
     * @param bindings The bindings
     * @return A condition wrapping the plain SQL
     */
    @Support
    public static Condition condition(String sql, Object... bindings) {
        return new SQLCondition(sql, bindings);
    }

    /**
     * A custom SQL clause that can render arbitrary SQL elements.
     * <p>
     * This is useful for constructing more complex SQL syntax elements wherever
     * <code>Condition</code> types are expected. An example for this are
     * Postgres's various operators, some of which are missing in the jOOQ API.
     * For instance, the "overlap" operator for arrays:
     * <code><pre>ARRAY[1,4,3] && ARRAY[2,1]</pre></code>
     * <p>
     * The above Postgres operator can be expressed as such: <code><pre>
     * condition("{0} && {1}", array1, array2);
     * </pre></code>
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses! One way to escape
     * literals is to use {@link #name(String...)} and similar methods
     *
     * @param sql The SQL
     * @param parts The {@link QueryPart} objects that are rendered at the
     *            {numbered placeholder} locations
     * @return A condition wrapping the plain SQL
     */
    @Support
    public static Condition condition(String sql, QueryPart... parts) {
        return new SQLCondition(sql, parts);
    }

    // -------------------------------------------------------------------------
    // XXX Global Condition factory
    // -------------------------------------------------------------------------

    /**
     * Return a <code>Condition</code> that will always evaluate to true
     */
    @Support
    public static Condition trueCondition() {
        return new TrueCondition();
    }

    /**
     * Return a <code>Condition</code> that will always evaluate to false
     */
    @Support
    public static Condition falseCondition() {
        return new FalseCondition();
    }

    /**
     * Create an exists condition.
     * <p>
     * <code>EXISTS ([query])</code>
     */
    @Support
    public static Condition exists(Select<?> query) {
        return new SelectQueryAsExistsCondition(query, ExistsOperator.EXISTS);
    }

    /**
     * Create a not exists condition.
     * <p>
     * <code>NOT EXISTS ([query])</code>
     */
    @Support
    public static Condition notExists(Select<?> query) {
        return new SelectQueryAsExistsCondition(query, ExistsOperator.NOT_EXISTS);
    }

    /**
     * Invert a condition
     * <p>
     * This is the same as calling {@link Condition#not()}
     */
    @Support
    public static Condition not(Condition condition) {
        return condition.not();
    }

    // -------------------------------------------------------------------------
    // XXX Global Field and Function factory
    // -------------------------------------------------------------------------

    /**
     * Initialise a {@link Case} statement. Decode is used as a method name to
     * avoid name clashes with Java's reserved literal "case"
     *
     * @see Case
     */
    @Support
    public static Case decode() {
        return new CaseImpl();
    }

    /**
     * Gets the Oracle-style
     * <code>DECODE(expression, search, result[, search , result]... [, default])</code>
     * function
     *
     * @see #decode(Field, Field, Field, Field[])
     */
    @Support
    public static <Z, T> Field<Z> decode(T value, T search, Z result) {
        return decode(value, search, result, new Object[0]);
    }

    /**
     * Gets the Oracle-style
     * <code>DECODE(expression, search, result[, search , result]... [, default])</code>
     * function
     *
     * @see #decode(Field, Field, Field, Field[])
     */
    @Support
    public static <Z, T> Field<Z> decode(T value, T search, Z result, Object... more) {
        return decode(val(value), val(search), val(result), vals(more).toArray(new Field[0]));
    }

    /**
     * Gets the Oracle-style
     * <code>DECODE(expression, search, result[, search , result]... [, default])</code>
     * function
     *
     * @see #decode(Field, Field, Field, Field[])
     */
    @Support
    public static <Z, T> Field<Z> decode(Field<T> value, Field<T> search, Field<Z> result) {
        return decode(nullSafe(value), nullSafe(search), nullSafe(result), new Field[0]);
    }

    /**
     * Gets the Oracle-style
     * <code>DECODE(expression, search, result[, search , result]... [, default])</code>
     * function
     * <p>
     * Returns the dialect's equivalent to DECODE:
     * <ul>
     * <li>Oracle <a
     * href="http://www.techonthenet.com/oracle/functions/decode.php">DECODE</a></li>
     * </ul>
     * <p>
     * Other dialects: <code><pre>
     * CASE WHEN [this = search] THEN [result],
     *     [WHEN more...         THEN more...]
     *     [ELSE more...]
     * END
     * </pre></code>
     *
     * @param value The value to decode
     * @param search the mandatory first search parameter
     * @param result the mandatory first result candidate parameter
     * @param more the optional parameters. If <code>more.length</code> is even,
     *            then it is assumed that it contains more search/result pairs.
     *            If <code>more.length</code> is odd, then it is assumed that it
     *            contains more search/result pairs plus a default at the end.     *
     */
    @Support
    public static <Z, T> Field<Z> decode(Field<T> value, Field<T> search, Field<Z> result, Field<?>... more) {
        return new Decode<T, Z>(nullSafe(value), nullSafe(search), nullSafe(result), nullSafe(more));
    }

    /**
     * Cast a value to the type of another field.
     *
     * @param <T> The generic type of the cast field
     * @param value The value to cast
     * @param as The field whose type is used for the cast
     * @return The cast field
     */
    @Support
    public static <T> Field<T> cast(Object value, Field<T> as) {
        return val(value, as).cast(as);
    }

    /**
     * Cast null to the type of another field.
     *
     * @param <T> The generic type of the cast field
     * @param as The field whose type is used for the cast
     * @return The cast field
     */
    @Support
    public static <T> Field<T> castNull(Field<T> as) {
        return NULL().cast(as);
    }

    /**
     * Cast a value to another type
     *
     * @param <T> The generic type of the cast field
     * @param value The value to cast
     * @param type The type that is used for the cast
     * @return The cast field
     */
    @Support
    public static <T> Field<T> cast(Object value, Class<T> type) {
        return val(value, type).cast(type);
    }

    /**
     * Cast null to a type
     *
     * @param <T> The generic type of the cast field
     * @param type The type that is used for the cast
     * @return The cast field
     */
    @Support
    public static <T> Field<T> castNull(DataType<T> type) {
        return NULL().cast(type);
    }

    /**
     * Cast a value to another type
     *
     * @param <T> The generic type of the cast field
     * @param value The value to cast
     * @param type The type that is used for the cast
     * @return The cast field
     */
    @Support
    public static <T> Field<T> cast(Object value, DataType<T> type) {
        return val(value, type).cast(type);
    }

    /**
     * Cast null to a type
     *
     * @param <T> The generic type of the cast field
     * @param type The type that is used for the cast
     * @return The cast field
     */
    @Support
    public static <T> Field<T> castNull(Class<T> type) {
        return NULL().cast(type);
    }

    /**
     * Cast all fields that need casting
     *
     * @param <T> The generic field type
     * @param type The type to cast to
     * @param fields The fields to be cast to a uniform type
     * @return The cast fields
     */
    static <T> Field<T>[] castAll(Class<T> type, Field<?>... fields) {
        Field<?>[] castFields = new Field<?>[fields.length];

        for (int i = 0; i < fields.length; i++) {
            castFields[i] = fields[i].cast(type);
        }

        return (Field<T>[]) castFields;
    }

    /**
     * Gets the Oracle-style <code>COALESCE(value1, value2, ... , value n)</code>
     * function
     *
     * @see #coalesce(Field, Field...)
     */
    @Support
    public static <T> Field<T> coalesce(T value, T... values) {
        return coalesce(val(value), vals(values).toArray(new Field[0]));
    }

    /**
     * Gets the Oracle-style <code>COALESCE(field1, field2, ... , field n)</code>
     * function
     * <p>
     * Returns the dialect's equivalent to COALESCE:
     * <ul>
     * <li>Oracle <a
     * href="http://www.techonthenet.com/oracle/functions/coalesce.php">COALESCE</a>
     * </li>
     * </ul>
     */
    @Support
    public static <T> Field<T> coalesce(Field<T> field, Field<?>... fields) {
        return function("coalesce", nullSafeDataType(field), nullSafe(combine(field, fields)));
    }

    /**
     * Gets the Oracle-style NVL(value, defaultValue) function
     *
     * @see #nvl(Field, Field)
     */
    @Support
    public static <T> Field<T> nvl(T value, T defaultValue) {
        return nvl(val(value), val(defaultValue));
    }

    /**
     * Gets the Oracle-style NVL(value, defaultValue) function
     *
     * @see #nvl(Field, Field)
     */
    @Support
    public static <T> Field<T> nvl(T value, Field<T> defaultValue) {
        return nvl(val(value), nullSafe(defaultValue));
    }

    /**
     * Gets the Oracle-style NVL(value, defaultValue) function
     *
     * @see #nvl(Field, Field)
     */
    @Support
    public static <T> Field<T> nvl(Field<T> value, T defaultValue) {
        return nvl(nullSafe(value), val(defaultValue));
    }

    /**
     * Gets the Oracle-style NVL(value, defaultValue) function
     * <p>
     * Returns the dialect's equivalent to NVL:
     * <ul>
     * <li>DB2 <a href=
     * "http://publib.boulder.ibm.com/infocenter/db2luw/v9r7/index.jsp?topic=/com.ibm.db2.luw.sql.ref.doc/doc/r0052627.html"
     * >NVL</a></li>
     * <li>Derby <a
     * href="http://db.apache.org/derby/docs/10.7/ref/rreffunccoalesce.html"
     * >COALESCE</a></li>
     * <li>H2 <a
     * href="http://www.h2database.com/html/functions.html#ifnull">IFNULL</a></li>
     * <li>HSQLDB <a
     * href="http://hsqldb.org/doc/2.0/guide/builtinfunctions-chapt.html"
     * >NVL</a></li>
     * <li>MySQL <a href=
     * "http://dev.mysql.com/doc/refman/5.0/en/control-flow-functions.html"
     * >IFNULL</a></li>
     * <li>Oracle <a
     * href="http://www.techonthenet.com/oracle/functions/nvl.php">NVL</a></li>
     * <li>Postgres <a href=
     * "http://www.postgresql.org/docs/8.1/static/functions-conditional.html"
     * >COALESCE</a></li>
     * <li>SQLite <a
     * href="http://www.sqlite.org/lang_corefunc.html#ifnull">IFNULL</a></li>
     * </ul>
     */
    @Support
    public static <T> Field<T> nvl(Field<T> value, Field<T> defaultValue) {
        return new Nvl<T>(nullSafe(value), nullSafe(defaultValue));
    }

    /**
     * Gets the Oracle-style NVL2(value, valueIfNotNull, valueIfNull) function
     *
     * @see #nvl2(Field, Field, Field)
     */
    @Support
    public static <Z> Field<Z> nvl2(Field<?> value, Z valueIfNotNull, Z valueIfNull) {
        return nvl2(nullSafe(value), val(valueIfNotNull), val(valueIfNull));
    }

    /**
     * Gets the Oracle-style NVL2(value, valueIfNotNull, valueIfNull) function
     *
     * @see #nvl2(Field, Field, Field)
     */
    @Support
    public static <Z> Field<Z> nvl2(Field<?> value, Z valueIfNotNull, Field<Z> valueIfNull) {
        return nvl2(nullSafe(value), val(valueIfNotNull), nullSafe(valueIfNull));
    }

    /**
     * Gets the Oracle-style NVL2(value, valueIfNotNull, valueIfNull) function
     *
     * @see #nvl2(Field, Field, Field)
     */
    @Support
    public static <Z> Field<Z> nvl2(Field<?> value, Field<Z> valueIfNotNull, Z valueIfNull) {
        return nvl2(nullSafe(value), nullSafe(valueIfNotNull), val(valueIfNull));
    }

    /**
     * Gets the Oracle-style NVL2(value, valueIfNotNull, valueIfNull) function
     * <p>
     * Returns the dialect's equivalent to NVL2:
     * <ul>
     * <li>Oracle <a
     * href="http://www.techonthenet.com/oracle/functions/nvl2.php">NVL2</a></li>
     * </ul>
     * <p>
     * Other dialects:
     * <code>CASE WHEN [value] IS NULL THEN [valueIfNull] ELSE [valueIfNotNull] END</code>
     */
    @Support
    public static <Z> Field<Z> nvl2(Field<?> value, Field<Z> valueIfNotNull, Field<Z> valueIfNull) {
        return new Nvl2<Z>(nullSafe(value), nullSafe(valueIfNotNull), nullSafe(valueIfNull));
    }

    /**
     * Gets the Oracle-style NULLIF(value, other) function
     *
     * @see #nullif(Field, Field)
     */
    @Support
    public static <T> Field<T> nullif(T value, T other) {
        return nullif(val(value), val(other));
    }

    /**
     * Gets the Oracle-style NULLIF(value, other) function
     *
     * @see #nullif(Field, Field)
     */
    @Support
    public static <T> Field<T> nullif(T value, Field<T> other) {
        return nullif(val(value), nullSafe(other));
    }

    /**
     * Gets the Oracle-style NULLIF(value, other) function
     *
     * @see #nullif(Field, Field)
     */
    @Support
    public static <T> Field<T> nullif(Field<T> value, T other) {
        return nullif(nullSafe(value), val(other));
    }

    /**
     * Gets the Oracle-style NULLIF(value, other) function
     * <p>
     * Returns the dialect's equivalent to NULLIF:
     * <ul>
     * <li>Oracle <a
     * href="http://www.techonthenet.com/oracle/functions/nullif.php">NULLIF</a></li>
     * </ul>
     * <p>
     */
    @Support
    public static <T> Field<T> nullif(Field<T> value, Field<T> other) {
        return function("nullif", nullSafeDataType(value), nullSafe(value), nullSafe(other));
    }

    // -------------------------------------------------------------------------
    // XXX String function factory
    // -------------------------------------------------------------------------

    /**
     * Get the upper(field) function
     *
     * @see #upper(Field)
     */
    @Support
    public static Field<String> upper(String value) {
        return upper(val(value));
    }

    /**
     * Get the upper(field) function
     * <p>
     * This renders the upper function in all dialects:
     * <code><pre>upper([field])</pre></code>
     */
    @Support
    public static Field<String> upper(Field<String> field) {
        return function("upper", SQLDataType.VARCHAR, nullSafe(field));
    }

    /**
     * Get the lower(field) function
     *
     * @see #lower(Field)
     */
    @Support
    public static Field<String> lower(String value) {
        return lower(val(value, String.class));
    }

    /**
     * Get the lower(field) function
     * <p>
     * This renders the lower function in all dialects:
     * <code><pre>lower([field])</pre></code>
     */
    @Support
    public static Field<String> lower(Field<String> value) {
        return function("lower", SQLDataType.VARCHAR, nullSafe(value));
    }

    /**
     * Get the trim(field) function
     *
     * @see #trim(Field)
     */
    @Support
    public static Field<String> trim(String value) {
        return trim(val(value, String.class));
    }

    /**
     * Get the trim(field) function
     * <p>
     * This renders the trim function where available:
     * <code><pre>trim([field])</pre></code> ... or simulates it elsewhere using
     * rtrim and ltrim: <code><pre>ltrim(rtrim([field]))</pre></code>
     */
    @Support
    public static Field<String> trim(Field<String> field) {
        return new Trim(nullSafe(field));
    }

    /**
     * Get the rtrim(field) function
     *
     * @see #rtrim(Field)
     */
    @Support
    public static Field<String> rtrim(String value) {
        return rtrim(val(value));
    }

    /**
     * Get the rtrim(field) function
     * <p>
     * This renders the rtrim function in all dialects:
     * <code><pre>rtrim([field])</pre></code>
     */
    @Support
    public static Field<String> rtrim(Field<String> field) {
        return new RTrim(nullSafe(field));
    }

    /**
     * Get the ltrim(field) function
     *
     * @see #ltrim(Field)
     */
    @Support
    public static Field<String> ltrim(String value) {
        return ltrim(val(value, String.class));
    }

    /**
     * Get the ltrim(field) function
     * <p>
     * This renders the ltrim function in all dialects:
     * <code><pre>ltrim([field])</pre></code>
     */
    @Support
    public static Field<String> ltrim(Field<String> field) {
        return new LTrim(nullSafe(field));
    }

    /**
     * Get the rpad(field, length) function
     *
     * @see #rpad(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLITE, SQLSERVER, SYBASE })
    public static Field<String> rpad(Field<String> field, int length) {
        return rpad(nullSafe(field), val(length));
    }

    /**
     * Get the rpad(field, length) function
     * <p>
     * This renders the rpad function where available:
     * <code><pre>rpad([field], [length])</pre></code> ... or simulates it
     * elsewhere using concat, repeat, and length, which may be simulated as
     * well, depending on the RDBMS:
     * <code><pre>concat([field], repeat(' ', [length] - length([field])))</pre></code>
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLITE, SQLSERVER, SYBASE })
    public static Field<String> rpad(Field<String> field, Field<? extends Number> length) {
        return new Rpad(nullSafe(field), nullSafe(length));
    }

    /**
     * Get the rpad(field, length, character) function
     *
     * @see #rpad(Field, Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLITE, SQLSERVER, SYBASE })
    public static Field<String> rpad(Field<String> field, int length, char character) {
        return rpad(field, length, Character.toString(character));
    }

    /**
     * Get the rpad(field, length, character) function
     *
     * @see #rpad(Field, Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLITE, SQLSERVER, SYBASE })
    public static Field<String> rpad(Field<String> field, int length, String character) {
        return rpad(nullSafe(field), val(length), val(character, String.class));
    }

    /**
     * Get the rpad(field, length, character) function
     * <p>
     * This renders the rpad function where available:
     * <code><pre>rpad([field], [length])</pre></code> ... or simulates it
     * elsewhere using concat, repeat, and length, which may be simulated as
     * well, depending on the RDBMS:
     * <code><pre>concat([field], repeat([character], [length] - length([field])))</pre></code>
     * <p>
     * In {@link SQLDialect#SQLITE}, this is simulated as such:
     * <code><pre>[field] || replace(replace(substr(quote(zeroblob(([length] + 1) / 2)), 3, ([length] - length([field]))), '''', ''), '0', [character])</pre></code>
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLITE, SQLSERVER, SYBASE })
    public static Field<String> rpad(Field<String> field, Field<? extends Number> length, Field<String> character) {
        return new Rpad(nullSafe(field), nullSafe(length), nullSafe(character));
    }

    /**
     * Get the lpad(field, length) function
     *
     * @see #lpad(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLITE, SQLSERVER, SYBASE })
    public static Field<String> lpad(Field<String> field, int length) {
        return lpad(nullSafe(field), val(length));
    }

    /**
     * Get the lpad(field, length) function
     * <p>
     * This renders the lpad function where available:
     * <code><pre>lpad([field], [length])</pre></code> ... or simulates it
     * elsewhere using concat, repeat, and length, which may be simulated as
     * well, depending on the RDBMS:
     * <code><pre>concat(repeat(' ', [length] - length([field])), [field])</pre></code>
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLITE, SQLSERVER, SYBASE })
    public static Field<String> lpad(Field<String> field, Field<? extends Number> length) {
        return new Lpad(nullSafe(field), nullSafe(length));
    }

    /**
     * Get the lpad(field, length, character) function
     *
     * @see #lpad(Field, Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLITE, SQLSERVER, SYBASE })
    public static Field<String> lpad(Field<String> field, int length, char character) {
        return lpad(field, length, Character.toString(character));
    }

    /**
     * Get the lpad(field, length, character) function
     *
     * @see #lpad(Field, Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLITE, SQLSERVER, SYBASE })
    public static Field<String> lpad(Field<String> field, int length, String character) {
        return lpad(nullSafe(field), val(length), val(character, String.class));
    }

    /**
     * Get the lpad(field, length, character) function
     * <p>
     * This renders the lpad function where available:
     * <code><pre>lpad([field], [length])</pre></code> ... or simulates it
     * elsewhere using concat, repeat, and length, which may be simulated as
     * well, depending on the RDBMS:
     * <code><pre>concat(repeat([character], [length] - length([field])), [field])</pre></code>
     * <p>
     * In {@link SQLDialect#SQLITE}, this is simulated as such:
     * <code><pre>replace(replace(substr(quote(zeroblob(([length] + 1) / 2)), 3, ([length] - length([field]))), '''', ''), '0', [character]) || [field]</pre></code>
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLITE, SQLSERVER, SYBASE })
    public static Field<String> lpad(Field<String> field, Field<? extends Number> length, Field<String> character) {
        return new Lpad(nullSafe(field), nullSafe(length), nullSafe(character));
    }

    /**
     * Get the repeat(field, count) function
     *
     * @see #repeat(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLITE, SQLSERVER, SYBASE })
    public static Field<String> repeat(String field, int count) {
        return repeat(val(field, String.class), val(count));
    }

    /**
     * Get the repeat(field, count) function
     *
     * @see #repeat(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLITE, SQLSERVER, SYBASE })
    public static Field<String> repeat(String field, Field<? extends Number> count) {
        return repeat(val(field, String.class), nullSafe(count));
    }

    /**
     * Get the repeat(count) function
     *
     * @see #repeat(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLITE, SQLSERVER, SYBASE })
    public static Field<String> repeat(Field<String> field, int count) {
        return repeat(nullSafe(field), val(count));
    }

    /**
     * Get the repeat(field, count) function
     * <p>
     * This renders the repeat or replicate function where available:
     * <code><pre>repeat([field], [count]) or
     * replicate([field], [count])</pre></code> ... or simulates it elsewhere
     * using rpad and length, which may be simulated as well, depending on the
     * RDBMS:
     * <code><pre>rpad([field], length([field]) * [count], [field])</pre></code>
     * <p>
     * In {@link SQLDialect#SQLITE}, this is simulated as such:
     * <code><pre>replace(substr(quote(zeroblob(([count] + 1) / 2)), 3, [count]), '0', [field])</pre></code>
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLITE, SQLSERVER, SYBASE })
    public static Field<String> repeat(Field<String> field, Field<? extends Number> count) {
        return new Repeat(nullSafe(field), nullSafe(count));
    }

    /**
     * Convenience method for {@link #replace(Field, String, String)} to escape
     * data for use with {@link Field#like(Field, char)}
     * <p>
     * Essentially, this escapes <code>%</code> and <code>_</code> characters
     *
     * @see #replace(Field, String, String)
     * @see Field#like(Field, char)
     */
    @Support
    public static String escape(String value, char escape) {
        String esc = "" + escape;
        return value.replace(esc, esc + esc).replace("%", esc + "%").replace("_", esc + "_");
    }

    /**
     * Convenience method for {@link #replace(Field, String, String)} to escape
     * data for use with {@link Field#like(Field, char)}
     * <p>
     * Essentially, this escapes <code>%</code> and <code>_</code> characters
     *
     * @see #replace(Field, String, String)
     * @see Field#like(Field, char)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static Field<String> escape(Field<String> field, char escape) {
        Field<String> replace = field;

        String esc = "" + escape;
        replace = replace(replace, inline(esc), inline(esc + esc));
        replace = replace(replace, inline("%"), inline(esc + "%"));
        replace = replace(replace, inline("_"), inline(esc + "_"));

        return replace;
    }

    /**
     * Get the replace(field, search) function
     *
     * @see #replace(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static Field<String> replace(Field<String> field, String search) {
        return replace(nullSafe(field), val(search, String.class));
    }

    /**
     * Get the replace(field, search) function
     * <p>
     * This renders the replace or str_replace function where available:
     * <code><pre>replace([field], [search]) or
     * str_replace([field], [search])</pre></code> ... or simulates it elsewhere
     * using the three-argument replace function:
     * <code><pre>replace([field], [search], '')</pre></code>
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static Field<String> replace(Field<String> field, Field<String> search) {
        return new Replace(nullSafe(field), nullSafe(search));
    }

    /**
     * Get the replace(field, search, replace) function
     *
     * @see #replace(Field, Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static Field<String> replace(Field<String> field, String search, String replace) {
        return replace(nullSafe(field), val(search, String.class), val(replace, String.class));
    }

    /**
     * Get the replace(field, search, replace) function
     * <p>
     * This renders the replace or str_replace function:
     * <code><pre>replace([field], [search]) or
     * str_replace([field], [search])</pre></code>
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static Field<String> replace(Field<String> field, Field<String> search, Field<String> replace) {
        return new Replace(nullSafe(field), nullSafe(search), nullSafe(replace));
    }

    /**
     * Get the position(in, search) function
     *
     * @see #position(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<Integer> position(String in, String search) {
        return position(val(in, String.class), val(search, String.class));
    }

    /**
     * Get the position(in, search) function
     *
     * @see #position(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<Integer> position(String in, Field<String> search) {
        return position(val(in, String.class), nullSafe(search));
    }

    /**
     * Get the position(in, search) function
     *
     * @see #position(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<Integer> position(Field<String> in, String search) {
        return position(nullSafe(in), val(search, String.class));
    }

    /**
     * Get the position(in, search) function
     * <p>
     * This renders the position or any equivalent function:
     * <code><pre>position([search] in [in]) or
     * locate([in], [search]) or
     * locate([search], [in]) or
     * instr([in], [search]) or
     * charindex([search], [in])</pre></code>
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<Integer> position(Field<String> in, Field<String> search) {
        return new Position(nullSafe(search), nullSafe(in));
    }

    /**
     * Get the ascii(field) function
     *
     * @see #ascii(Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<Integer> ascii(String field) {
        return ascii(val(field, String.class));
    }

    /**
     * Get the ascii(field) function
     * <p>
     * This renders the ascii function:
     * <code><pre>ascii([field])</pre></code>
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<Integer> ascii(Field<String> field) {
        return new Ascii(nullSafe(field));
    }

    /**
     * Get the concat(value[, value, ...]) function
     *
     * @see #concat(Field...)
     */
    @Support
    public static Field<String> concat(String... values) {
        return concat(vals((Object[]) values).toArray(new Field[0]));
    }

    /**
     * Get the concat(field[, field, ...]) function
     * <p>
     * This creates <code>fields[0] || fields[1] || ...</code> as an
     * expression, or <code>concat(fields[0], fields[1], ...)</code>,
     * depending on the dialect.
     * <p>
     * If any of the given fields is not a {@link String} field, they are cast
     * to <code>Field&lt;String&gt;</code> first using {@link #cast(Object, Class)}
     */
    @Support
    public static Field<String> concat(Field<?>... fields) {
        return new Concat(nullSafe(fields));
    }

    /**
     * Get the substring(field, startingPosition) function
     *
     * @see #substring(Field, Field)
     */
    @Support
    public static Field<String> substring(Field<String> field, int startingPosition) {
        return substring(nullSafe(field), val(startingPosition));
    }

    /**
     * Get the substring(field, startingPosition) function
     * <p>
     * This renders the substr or substring function:
     * <code><pre>substr([field], [startingPosition]) or
     * substring([field], [startingPosition])</pre></code>
     */
    @Support
    public static Field<String> substring(Field<String> field, Field<? extends Number> startingPosition) {
        return new Substring(nullSafe(field), nullSafe(startingPosition));
    }

    /**
     * Get the substring(field, startingPosition, length) function
     *
     * @see #substring(Field, Field, Field)
     */
    @Support
    public static Field<String> substring(Field<String> field, int startingPosition, int length) {
        return substring(nullSafe(field), val(startingPosition), val(length));
    }

    /**
     * Get the substring(field, startingPosition, length) function
     * <p>
     * This renders the substr or substring function:
     * <code><pre>substr([field], [startingPosition], [length]) or
     * substring([field], [startingPosition], [length])</pre></code>
     */
    @Support
    public static Field<String> substring(Field<String> field, Field<? extends Number> startingPosition, Field<? extends Number> length) {
        return new Substring(nullSafe(field), nullSafe(startingPosition), nullSafe(length));
    }

    /**
     * Get the length of a <code>VARCHAR</code> type. This is a synonym for
     * {@link #charLength(String)}
     *
     * @see #charLength(String)
     */
    @Support
    public static Field<Integer> length(String value) {
        return length(val(value, String.class));
    }

    /**
     * Get the length of a <code>VARCHAR</code> type. This is a synonym for
     * {@link #charLength(Field)}
     *
     * @see #charLength(Field)
     */
    @Support
    public static Field<Integer> length(Field<String> field) {
        return charLength(field);
    }

    /**
     * Get the char_length(field) function
     * <p>
     * This translates into any dialect
     */
    @Support
    public static Field<Integer> charLength(String value) {
        return charLength(val(value));
    }

    /**
     * Get the char_length(field) function
     * <p>
     * This translates into any dialect
     */
    @Support
    public static Field<Integer> charLength(Field<String> field) {
        return new Function<Integer>(Term.CHAR_LENGTH, SQLDataType.INTEGER, nullSafe(field));
    }

    /**
     * Get the bit_length(field) function
     * <p>
     * This translates into any dialect
     */
    @Support
    public static Field<Integer> bitLength(String value) {
        return bitLength(val(value));
    }

    /**
     * Get the bit_length(field) function
     * <p>
     * This translates into any dialect
     */
    @Support
    public static Field<Integer> bitLength(Field<String> field) {
        return new Function<Integer>(Term.BIT_LENGTH, SQLDataType.INTEGER, nullSafe(field));
    }

    /**
     * Get the octet_length(field) function
     * <p>
     * This translates into any dialect
     */
    @Support
    public static Field<Integer> octetLength(String value) {
        return octetLength(val(value, String.class));
    }

    /**
     * Get the octet_length(field) function
     * <p>
     * This translates into any dialect
     */
    @Support
    public static Field<Integer> octetLength(Field<String> field) {
        return new Function<Integer>(Term.OCTET_LENGTH, SQLDataType.INTEGER, nullSafe(field));
    }

    // ------------------------------------------------------------------------
    // XXX Hash function factory
    // ------------------------------------------------------------------------

    /**
     * Get the MySQL-specific <code>MD5()</code> function
     * <p>
     * These are the implementations for various databases:
     * <p>
     * <table border="1">
     * <tr>
     * <th>Database</th>
     * <th>Implementation</th>
     * </tr>
     * <tr>
     * <td>MySQL</td>
     * <td><code>MD5( ... )</code></td>
     * </tr>
     * <tr>
     * <td>Oracle</td>
     * <td>
     * <code>LOWER(RAWTOHEX(SYS.DBMS_CRYPTO.HASH(UTL_RAW.CAST_TO_RAW( ... ), SYS.DBMS_CRYPTO.HASH_MD5)))</code>
     * </td>
     * </tr>
     * </table>
     */
    @Support({ MYSQL, ORACLE })
    public static Field<String> md5(String string) {
        return md5(val(string));
    }

    /**
     * Get the MySQL-specific <code>MD5()</code> function
     * <p>
     * These are the implementations for various databases:
     * <p>
     * <table border="1">
     * <tr>
     * <th>Database</th>
     * <th>Implementation</th>
     * </tr>
     * <tr>
     * <td>MySQL</td>
     * <td><code>MD5( ... )</code></td>
     * </tr>
     * <tr>
     * <td>Oracle</td>
     * <td>
     * <code>LOWER(RAWTOHEX(SYS.DBMS_CRYPTO.HASH(UTL_RAW.CAST_TO_RAW( ... ), SYS.DBMS_CRYPTO.HASH_MD5)))</code>
     * </td>
     * </tr>
     * </table>
     */
    @Support({ MYSQL, ORACLE })
    public static Field<String> md5(Field<String> string) {
        return new MD5(nullSafe(string));
    }

    // ------------------------------------------------------------------------
    // XXX Date and time functions
    // ------------------------------------------------------------------------

    /**
     * Get the current_date() function
     * <p>
     * This translates into any dialect
     */
    @Support
    public static Field<Date> currentDate() {
        return new CurrentDate();
    }

    /**
     * Get the current_time() function
     * <p>
     * This translates into any dialect
     */
    @Support
    public static Field<Time> currentTime() {
        return new CurrentTime();
    }

    /**
     * Get the current_timestamp() function
     * <p>
     * This translates into any dialect
     */
    @Support
    public static Field<Timestamp> currentTimestamp() {
        return new CurrentTimestamp();
    }

    /**
     * Get the date difference in number of days
     * <p>
     * This translates into any dialect
     *
     * @see Field#sub(Field)
     */
    @Support
    public static Field<Integer> dateDiff(Date date1, Date date2) {
        return dateDiff(val(date1), val(date2));
    }

    /**
     * Get the date difference in number of days
     * <p>
     * This translates into any dialect
     *
     * @see Field#sub(Field)
     */
    @Support
    public static Field<Integer> dateDiff(Field<Date> date1, Date date2) {
        return dateDiff(nullSafe(date1), val(date2));
    }

    /**
     * Add an interval to a date
     * <p>
     * This translates into any dialect
     *
     * @see Field#add(Number)
     */
    @Support
    public static Field<Date> dateAdd(Date date, Number interval) {
        return dateAdd(val(date), val(interval));
    }

    /**
     * Add an interval to a date
     * <p>
     * This translates into any dialect
     *
     * @see Field#add(Field)
     */
    @Support
    public static Field<Date> dateAdd(Field<Date> date, Field<? extends Number> interval) {
        return nullSafe(date).add(interval);
    }

    /**
     * Get the date difference in number of days
     * <p>
     * This translates into any dialect
     *
     * @see Field#sub(Field)
     */
    @Support
    public static Field<Integer> dateDiff(Date date1, Field<Date> date2) {
        return dateDiff(val(date1), nullSafe(date2));
    }

    /**
     * Get the date difference in number of days
     * <p>
     * This translates into any dialect
     *
     * @see Field#sub(Field)
     */
    @Support
    public static Field<Integer> dateDiff(Field<Date> date1, Field<Date> date2) {
        return new DateDiff(nullSafe(date1), nullSafe(date2));
    }

    /**
     * Add an interval to a timestamp
     * <p>
     * This translates into any dialect
     *
     * @see Field#add(Number)
     */
    @Support
    public static Field<Timestamp> timestampAdd(Timestamp timestamp, Number interval) {
        return timestampAdd(val(timestamp), val(interval));
    }

    /**
     * Add an interval to a timestamp
     * <p>
     * This translates into any dialect
     *
     * @see Field#add(Field)
     */
    @Support
    public static Field<Timestamp> timestampAdd(Field<Timestamp> timestamp, Field<? extends Number> interval) {
        return nullSafe(timestamp).add(interval);
    }

    /**
     * Get the timestamp difference as a <code>INTERVAL DAY TO SECOND</code>
     * type
     * <p>
     * This translates into any dialect
     *
     * @see Field#sub(Field)
     */
    @Support
    public static Field<DayToSecond> timestampDiff(Timestamp timestamp1, Timestamp timestamp2) {
        return timestampDiff(val(timestamp1), val(timestamp2));
    }

    /**
     * Get the timestamp difference as a <code>INTERVAL DAY TO SECOND</code>
     * type
     * <p>
     * This translates into any dialect
     *
     * @see Field#sub(Field)
     */
    @Support
    public static Field<DayToSecond> timestampDiff(Field<Timestamp> timestamp1, Timestamp timestamp2) {
        return timestampDiff(nullSafe(timestamp1), val(timestamp2));
    }

    /**
     * Get the timestamp difference as a <code>INTERVAL DAY TO SECOND</code>
     * type
     * <p>
     * This translates into any dialect
     *
     * @see Field#sub(Field)
     */
    @Support
    public static Field<DayToSecond> timestampDiff(Timestamp timestamp1, Field<Timestamp> timestamp2) {
        return timestampDiff(val(timestamp1), nullSafe(timestamp2));
    }

    /**
     * Get the timestamp difference as a <code>INTERVAL DAY TO SECOND</code>
     * type
     * <p>
     * This translates into any dialect
     *
     * @see Field#sub(Field)
     */
    @Support
    public static Field<DayToSecond> timestampDiff(Field<Timestamp> timestamp1, Field<Timestamp> timestamp2) {
        return new TimestampDiff(nullSafe(timestamp1), nullSafe(timestamp2));
    }

    // -------------------------------------------------------------------------
    // [#470] TRUNC(datetime) will be implemented in a future release
    // -------------------------------------------------------------------------

    /**
     * This is not yet implemented
     */
    static Field<Date> trunc(Date date) {
        return trunc(date, DatePart.DAY);
    }

    /**
     * This is not yet implemented
     */
    static Field<Date> trunc(Date date, DatePart part) {
        return trunc(val(date), part);
    }

    /**
     * This is not yet implemented
     */
    static Field<Timestamp> trunc(Timestamp timestamp) {
        return trunc(timestamp, DatePart.DAY);
    }

    /**
     * This is not yet implemented
     */
    static Field<Timestamp> trunc(Timestamp timestamp, DatePart part) {
        return trunc(val(timestamp), part);
    }

    /**
     * This is not yet implemented
     */
    static <T extends java.util.Date> Field<T> trunc(Field<T> date) {
        return trunc(date, DatePart.DAY);
    }

    /**
     * This is not yet implemented
     */
    @SuppressWarnings("unused")
    static <T extends java.util.Date> Field<T> trunc(Field<T> date, DatePart part) {
        throw new UnsupportedOperationException("This is not yet implemented");
    }

    // -------------------------------------------------------------------------

    /**
     * Get the extract(field, datePart) function
     * <p>
     * This translates into any dialect
     */
    @Support
    public static Field<Integer> extract(java.util.Date value, DatePart datePart) {
        return extract(val(value), datePart);
    }

    /**
     * Get the extract(field, datePart) function
     * <p>
     * This translates into any dialect
     */
    @Support
    public static Field<Integer> extract(Field<? extends java.util.Date> field, DatePart datePart) {
        return new Extract(nullSafe(field), datePart);
    }

    /**
     * Get the year part of a date
     * <p>
     * This is the same as calling {@link #extract(java.util.Date, DatePart)}
     * with {@link DatePart#YEAR}
     */
    @Support
    public static Field<Integer> year(java.util.Date value) {
        return extract(value, DatePart.YEAR);
    }

    /**
     * Get the year part of a date
     * <p>
     * This is the same as calling {@link #extract(Field, DatePart)}
     * with {@link DatePart#YEAR}
     */
    @Support
    public static Field<Integer> year(Field<? extends java.util.Date> field) {
        return extract(field, DatePart.YEAR);
    }

    /**
     * Get the month part of a date
     * <p>
     * This is the same as calling {@link #extract(java.util.Date, DatePart)}
     * with {@link DatePart#MONTH}
     */
    @Support
    public static Field<Integer> month(java.util.Date value) {
        return extract(value, DatePart.MONTH);
    }

    /**
     * Get the month part of a date
     * <p>
     * This is the same as calling {@link #extract(Field, DatePart)}
     * with {@link DatePart#MONTH}
     */
    @Support
    public static Field<Integer> month(Field<? extends java.util.Date> field) {
        return extract(field, DatePart.MONTH);
    }

    /**
     * Get the day part of a date
     * <p>
     * This is the same as calling {@link #extract(java.util.Date, DatePart)}
     * with {@link DatePart#DAY}
     */
    @Support
    public static Field<Integer> day(java.util.Date value) {
        return extract(value, DatePart.DAY);
    }

    /**
     * Get the day part of a date
     * <p>
     * This is the same as calling {@link #extract(Field, DatePart)}
     * with {@link DatePart#DAY}
     */
    @Support
    public static Field<Integer> day(Field<? extends java.util.Date> field) {
        return extract(field, DatePart.DAY);
    }

    /**
     * Get the hour part of a date
     * <p>
     * This is the same as calling {@link #extract(java.util.Date, DatePart)}
     * with {@link DatePart#HOUR}
     */
    @Support
    public static Field<Integer> hour(java.util.Date value) {
        return extract(value, DatePart.HOUR);
    }

    /**
     * Get the hour part of a date
     * <p>
     * This is the same as calling {@link #extract(Field, DatePart)}
     * with {@link DatePart#HOUR}
     */
    @Support
    public static Field<Integer> hour(Field<? extends java.util.Date> field) {
        return extract(field, DatePart.HOUR);
    }

    /**
     * Get the minute part of a date
     * <p>
     * This is the same as calling {@link #extract(java.util.Date, DatePart)}
     * with {@link DatePart#MINUTE}
     */
    @Support
    public static Field<Integer> minute(java.util.Date value) {
        return extract(value, DatePart.MINUTE);
    }

    /**
     * Get the minute part of a date
     * <p>
     * This is the same as calling {@link #extract(Field, DatePart)}
     * with {@link DatePart#MINUTE}
     */
    @Support
    public static Field<Integer> minute(Field<? extends java.util.Date> field) {
        return extract(field, DatePart.MINUTE);
    }

    /**
     * Get the second part of a date
     * <p>
     * This is the same as calling {@link #extract(java.util.Date, DatePart)}
     * with {@link DatePart#SECOND}
     */
    @Support
    public static Field<Integer> second(java.util.Date value) {
        return extract(value, DatePart.SECOND);
    }

    /**
     * Get the second part of a date
     * <p>
     * This is the same as calling {@link #extract(Field, DatePart)}
     * with {@link DatePart#SECOND}
     */
    @Support
    public static Field<Integer> second(Field<? extends java.util.Date> field) {
        return extract(field, DatePart.SECOND);
    }

    // ------------------------------------------------------------------------
    // XXX Construction of special grouping functions
    // ------------------------------------------------------------------------

    /**
     * Create a ROLLUP(field1, field2, .., fieldn) grouping field
     * <p>
     * This has been observed to work with the following databases:
     * <ul>
     * <li>CUBRID (simulated using the GROUP BY .. WITH ROLLUP clause)</li>
     * <li>DB2</li>
     * <li>MySQL (simulated using the GROUP BY .. WITH ROLLUP clause)</li>
     * <li>Oracle</li>
     * <li>SQL Server</li>
     * <li>Sybase SQL Anywhere</li>
     * </ul>
     * <p>
     * Please check the SQL Server documentation for a very nice explanation of
     * <code>CUBE</code>, <code>ROLLUP</code>, and <code>GROUPING SETS</code>
     * clauses in grouping contexts: <a
     * href="http://msdn.microsoft.com/en-US/library/bb522495.aspx"
     * >http://msdn.microsoft.com/en-US/library/bb522495.aspx</a>
     *
     * @param fields The fields that are part of the <code>ROLLUP</code>
     *            function
     * @return A field to be used in a <code>GROUP BY</code> clause
     */
    @Support({ CUBRID, DB2, MYSQL, ORACLE, SQLSERVER, SYBASE })
    public static GroupField rollup(Field<?>... fields) {
        return new Rollup(nullSafe(fields));
    }

    /**
     * Create a CUBE(field1, field2, .., fieldn) grouping field
     * <p>
     * This has been observed to work with the following databases:
     * <ul>
     * <li>DB2</li>
     * <li>Oracle</li>
     * <li>SQL Server</li>
     * <li>Sybase SQL Anywhere</li>
     * </ul>
     * <p>
     * Please check the SQL Server documentation for a very nice explanation of
     * <code>CUBE</code>, <code>ROLLUP</code>, and <code>GROUPING SETS</code>
     * clauses in grouping contexts: <a
     * href="http://msdn.microsoft.com/en-US/library/bb522495.aspx"
     * >http://msdn.microsoft.com/en-US/library/bb522495.aspx</a>
     *
     * @param fields The fields that are part of the <code>CUBE</code>
     *            function
     * @return A field to be used in a <code>GROUP BY</code> clause
     */
    @Support({ DB2, ORACLE, SQLSERVER, SYBASE })
    public static GroupField cube(Field<?>... fields) {
        return function("cube", Object.class, nullSafe(fields));
    }

    /**
     * Create a GROUPING SETS(field1, field2, .., fieldn) grouping field where
     * each grouping set only consists of a single field.
     * <p>
     * This has been observed to work with the following databases:
     * <ul>
     * <li>DB2</li>
     * <li>Oracle</li>
     * <li>SQL Server</li>
     * <li>Sybase SQL Anywhere</li>
     * </ul>
     * <p>
     * Please check the SQL Server documentation for a very nice explanation of
     * <code>CUBE</code>, <code>ROLLUP</code>, and <code>GROUPING SETS</code>
     * clauses in grouping contexts: <a
     * href="http://msdn.microsoft.com/en-US/library/bb522495.aspx"
     * >http://msdn.microsoft.com/en-US/library/bb522495.aspx</a>
     *
     * @param fields The fields that are part of the <code>GROUPING SETS</code>
     *            function
     * @return A field to be used in a <code>GROUP BY</code> clause
     */
    @Support({ DB2, ORACLE, SQLSERVER, SYBASE })
    public static GroupField groupingSets(Field<?>... fields) {
        List<Field<?>>[] array = new List[fields.length];

        for (int i = 0; i < fields.length; i++) {
            array[i] = Arrays.<Field<?>>asList(fields[i]);
        }

        return groupingSets(array);
    }

    /**
     * Create a GROUPING SETS((field1a, field1b), (field2a), .., (fieldna,
     * fieldnb)) grouping field
     * <p>
     * This has been observed to work with the following databases:
     * <ul>
     * <li>DB2</li>
     * <li>Oracle</li>
     * <li>SQL Server</li>
     * <li>Sybase SQL Anywhere</li>
     * </ul>
     * <p>
     * Please check the SQL Server documentation for a very nice explanation of
     * <code>CUBE</code>, <code>ROLLUP</code>, and <code>GROUPING SETS</code>
     * clauses in grouping contexts: <a
     * href="http://msdn.microsoft.com/en-US/library/bb522495.aspx"
     * >http://msdn.microsoft.com/en-US/library/bb522495.aspx</a>
     *
     * @param fieldSets The fields that are part of the <code>GROUPING SETS</code>
     *            function
     * @return A field to be used in a <code>GROUP BY</code> clause
     */
    @Support({ DB2, ORACLE, SQLSERVER, SYBASE })
    public static GroupField groupingSets(Field<?>[]... fieldSets) {
        List<Field<?>>[] array = new List[fieldSets.length];

        for (int i = 0; i < fieldSets.length; i++) {
            array[i] = Arrays.asList(fieldSets[i]);
        }

        return groupingSets(array);
    }

    /**
     * Create a GROUPING SETS((field1a, field1b), (field2a), .., (fieldna,
     * fieldnb)) grouping field
     * <p>
     * This has been observed to work with the following databases:
     * <ul>
     * <li>DB2</li>
     * <li>Oracle</li>
     * <li>SQL Server</li>
     * <li>Sybase SQL Anywhere</li>
     * </ul>
     * <p>
     * Please check the SQL Server documentation for a very nice explanation of
     * <code>CUBE</code>, <code>ROLLUP</code>, and <code>GROUPING SETS</code>
     * clauses in grouping contexts: <a
     * href="http://msdn.microsoft.com/en-US/library/bb522495.aspx"
     * >http://msdn.microsoft.com/en-US/library/bb522495.aspx</a>
     *
     * @param fieldSets The fields that are part of the <code>GROUPING SETS</code>
     *            function
     * @return A field to be used in a <code>GROUP BY</code> clause
     */
    @Support({ DB2, ORACLE, SQLSERVER, SYBASE })
    public static GroupField groupingSets(Collection<Field<?>>... fieldSets) {
        WrappedList[] array = new WrappedList[fieldSets.length];

        for (int i = 0; i < fieldSets.length; i++) {
            array[i] = new WrappedList(new FieldList(fieldSets[i]));
        }

        return new Function<Object>("grouping sets", SQLDataType.OTHER, array);
    }

    /**
     * Create a GROUPING(field) aggregation field to be used along with
     * <code>CUBE</code>, <code>ROLLUP</code>, and <code>GROUPING SETS</code>
     * groupings
     * <p>
     * This has been observed to work with the following databases:
     * <ul>
     * <li>DB2</li>
     * <li>Oracle</li>
     * <li>SQL Server</li>
     * <li>Sybase SQL Anywhere</li>
     * </ul>
     *
     * @param field The function argument
     * @return The <code>GROUPING</code> aggregation field
     * @see #cube(Field...)
     * @see #rollup(Field...)
     */
    @Support({ DB2, ORACLE, SQLSERVER, SYBASE })
    public static Field<Integer> grouping(Field<?> field) {
        return function("grouping", Integer.class, nullSafe(field));
    }

    /**
     * Create a GROUPING_ID(field1, field2, .., fieldn) aggregation field to be
     * used along with <code>CUBE</code>, <code>ROLLUP</code>, and
     * <code>GROUPING SETS</code> groupings
     * <p>
     * This has been observed to work with the following databases:
     * <ul>
     * <li>Oracle</li>
     * <li>SQL Server</li>
     * </ul>
     *
     * @param fields The function arguments
     * @return The <code>GROUPING_ID</code> aggregation field
     * @see #cube(Field...)
     * @see #rollup(Field...)
     */
    @Support({ ORACLE, SQLSERVER})
    public static Field<Integer> groupingId(Field<?>... fields) {
        return function("grouping_id", Integer.class, nullSafe(fields));
    }

    // ------------------------------------------------------------------------
    // XXX Bitwise operations
    // ------------------------------------------------------------------------

    /**
     * The MySQL <code>BIT_COUNT(field)</code> function, counting the number of
     * bits that are set in this number.
     *
     * @see #bitCount(Field)
     */
    @Support({ CUBRID, FIREBIRD, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SYBASE, SQLITE })
    public static Field<Integer> bitCount(Number value) {
        return bitCount(val(value));
    }

    /**
     * The MySQL <code>BIT_COUNT(field)</code> function, counting the number of
     * bits that are set in this number.
     * <p>
     * This function is simulated in most other databases like this (for a
     * TINYINT field): <code><pre>
     * ([field] &   1) +
     * ([field] &   2) >> 1 +
     * ([field] &   4) >> 2 +
     * ([field] &   8) >> 3 +
     * ([field] &  16) >> 4 +
     *  ...
     * ([field] & 128) >> 7
     * </pre></code>
     * <p>
     * More efficient algorithms are very welcome
     */
    @Support({ CUBRID, FIREBIRD, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SYBASE, SQLITE })
    public static Field<Integer> bitCount(Field<? extends Number> field) {
        return new BitCount(nullSafe(field));
    }

    /**
     * The bitwise not operator.
     *
     * @see #bitNot(Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> bitNot(T value) {
        return bitNot(val(value));
    }

    /**
     * The bitwise not operator.
     * <p>
     * Most dialects natively support this using <code>~[field]</code>. jOOQ
     * simulates this operator in some dialects using <code>-[field] - 1</code>
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> bitNot(Field<T> field) {
        return new Neg<T>(nullSafe(field), ExpressionOperator.BIT_NOT);
    }

    /**
     * The bitwise and operator.
     *
     * @see #bitAnd(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> bitAnd(T value1, T value2) {
        return bitAnd(val(value1), val(value2));
    }

    /**
     * The bitwise and operator.
     *
     * @see #bitAnd(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> bitAnd(T value1, Field<T> value2) {
        return bitAnd(val(value1), nullSafe(value2));
    }

    /**
     * The bitwise and operator.
     *
     * @see #bitAnd(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> bitAnd(Field<T> value1, T value2) {
        return bitAnd(nullSafe(value1), val(value2));
    }

    /**
     * The bitwise and operator.
     * <p>
     * This is not supported by Derby, Ingres
     * <p>
     * This renders the and operation where available:
     * <code><pre>[field1] & [field2]</pre></code>
     * ... or the and function elsewhere:
     * <code><pre>bitand([field1], [field2])</pre></code>
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> bitAnd(Field<T> field1, Field<T> field2) {
        return new Expression<T>(ExpressionOperator.BIT_AND, nullSafe(field1), nullSafe(field2));
    }

    /**
     * The bitwise not and operator.
     *
     * @see #bitNand(Field, Field)
     * @see #bitNot(Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> bitNand(T value1, T value2) {
        return bitNand(val(value1), val(value2));
    }

    /**
     * The bitwise not and operator.
     *
     * @see #bitNand(Field, Field)
     * @see #bitNot(Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> bitNand(T value1, Field<T> value2) {
        return bitNand(val(value1), nullSafe(value2));
    }

    /**
     * The bitwise not and operator.
     *
     * @see #bitNand(Field, Field)
     * @see #bitNot(Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> bitNand(Field<T> value1, T value2) {
        return bitNand(nullSafe(value1), val(value2));
    }

    /**
     * The bitwise not and operator.
     * <p>
     * This is not supported by Derby, Ingres
     * <p>
     * This renders the not and operation where available:
     * <code><pre>~([field1] & [field2])</pre></code>
     * ... or the not and function elsewhere:
     * <code><pre>bitnot(bitand([field1], [field2]))</pre></code>
     *
     * @see #bitNot(Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> bitNand(Field<T> field1, Field<T> field2) {
        return new Expression<T>(ExpressionOperator.BIT_NAND, nullSafe(field1), nullSafe(field2));
    }

    /**
     * The bitwise or operator.
     *
     * @see #bitOr(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> bitOr(T value1, T value2) {
        return bitOr(val(value1), val(value2));
    }

    /**
     * The bitwise or operator.
     *
     * @see #bitOr(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> bitOr(T value1, Field<T> value2) {
        return bitOr(val(value1), nullSafe(value2));
    }

    /**
     * The bitwise or operator.
     *
     * @see #bitOr(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> bitOr(Field<T> value1, T value2) {
        return bitOr(nullSafe(value1), val(value2));
    }

    /**
     * The bitwise or operator.
     * <p>
     * This is not supported by Derby, Ingres
     * <p>
     * This renders the or operation where available:
     * <code><pre>[field1] | [field2]</pre></code>
     * ... or the or function elsewhere:
     * <code><pre>bitor([field1], [field2])</pre></code>
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> bitOr(Field<T> field1, Field<T> field2) {
        return new Expression<T>(ExpressionOperator.BIT_OR, nullSafe(field1), nullSafe(field2));
    }

    /**
     * The bitwise not or operator.
     *
     * @see #bitNor(Field, Field)
     * @see #bitNot(Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> bitNor(T value1, T value2) {
        return bitNor(val(value1), val(value2));
    }
    /**
     * The bitwise not or operator.
     *
     * @see #bitNor(Field, Field)
     * @see #bitNot(Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> bitNor(T value1, Field<T> value2) {
        return bitNor(val(value1), nullSafe(value2));
    }
    /**
     * The bitwise not or operator.
     *
     * @see #bitNor(Field, Field)
     * @see #bitNot(Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> bitNor(Field<T> value1, T value2) {
        return bitNor(nullSafe(value1), val(value2));
    }

    /**
     * The bitwise not or operator.
     * <p>
     * This is not supported by Derby, Ingres
     * <p>
     * This renders the not or operation where available:
     * <code><pre>~([field1] | [field2])</pre></code>
     * ... or the not or function elsewhere:
     * <code><pre>bitnot(bitor([field1], [field2]))</pre></code>
     *
     * @see #bitNot(Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> bitNor(Field<T> field1, Field<T> field2) {
        return new Expression<T>(ExpressionOperator.BIT_NOR, nullSafe(field1), nullSafe(field2));
    }

    /**
     * The bitwise xor operator.
     *
     * @see #bitXor(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> bitXor(T value1, T value2) {
        return bitXor(val(value1), val(value2));
    }

    /**
     * The bitwise xor operator.
     *
     * @see #bitXor(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> bitXor(T value1, Field<T> value2) {
        return bitXor(val(value1), nullSafe(value2));
    }

    /**
     * The bitwise xor operator.
     *
     * @see #bitXor(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> bitXor(Field<T> value1, T value2) {
        return bitXor(nullSafe(value1), val(value2));
    }

    /**
     * The bitwise xor operator.
     * <p>
     * This is not supported by Derby, Ingres
     * <p>
     * This renders the or operation where available:
     * <code><pre>[field1] ^ [field2]</pre></code>
     * ... or the xor function elsewhere:
     * <code><pre>bitxor([field1], [field2])</pre></code>
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> bitXor(Field<T> field1, Field<T> field2) {
        return new Expression<T>(ExpressionOperator.BIT_XOR, nullSafe(field1), nullSafe(field2));
    }

    /**
     * The bitwise not xor operator.
     *
     * @see #bitXNor(Field, Field)
     * @see #bitNot(Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> bitXNor(T value1, T value2) {
        return bitXNor(val(value1), val(value2));
    }

    /**
     * The bitwise not xor operator.
     *
     * @see #bitXNor(Field, Field)
     * @see #bitNot(Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> bitXNor(T value1, Field<T> value2) {
        return bitXNor(val(value1), nullSafe(value2));
    }

    /**
     * The bitwise not xor operator.
     *
     * @see #bitXNor(Field, Field)
     * @see #bitNot(Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> bitXNor(Field<T> value1, T value2) {
        return bitXNor(nullSafe(value1), val(value2));
    }

    /**
     * The bitwise not xor operator.
     * <p>
     * This is not supported by Derby, Ingres
     * <p>
     * This renders the or operation where available:
     * <code><pre>~([field1] ^ [field2])</pre></code>
     * ... or the not xor function elsewhere:
     * <code><pre>bitnot(bitxor([field1], [field2]))</pre></code>
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> bitXNor(Field<T> field1, Field<T> field2) {
        return new Expression<T>(ExpressionOperator.BIT_XNOR, nullSafe(field1), nullSafe(field2));
    }

    /**
     * The bitwise left shift operator.
     *
     * @see #shl(Field, Field)
     * @see #power(Field, Number)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> shl(T value1, T value2) {
        return shl(val(value1), val(value2));
    }

    /**
     * The bitwise left shift operator.
     *
     * @see #shl(Field, Field)
     * @see #power(Field, Number)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> shl(T value1, Field<T> value2) {
        return shl(val(value1), nullSafe(value2));
    }

    /**
     * The bitwise left shift operator.
     *
     * @see #shl(Field, Field)
     * @see #power(Field, Number)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> shl(Field<T>value1, T value2) {
        return shl(nullSafe(value1), val(value2));
    }

    /**
     * The bitwise left shift operator.
     * <p>
     * Some dialects natively support this using <code>[field1] << [field2]</code>.
     * jOOQ simulates this operator in some dialects using
     * <code>[field1] * power(2, [field2])</code>, where power might also be simulated.
     *
     * @see #power(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> shl(Field<T> field1, Field<T> field2) {
        return new Expression<T>(ExpressionOperator.SHL, nullSafe(field1), nullSafe(field2));
    }

    /**
     * The bitwise right shift operator.
     *
     * @see #shr(Field, Field)
     * @see #power(Field, Number)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> shr(T value1, T value2) {
        return shr(val(value1), val(value2));
    }

    /**
     * The bitwise right shift operator.
     *
     * @see #shr(Field, Field)
     * @see #power(Field, Number)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> shr(T value1, Field<T> value2) {
        return shr(val(value1), nullSafe(value2));
    }

    /**
     * The bitwise right shift operator.
     *
     * @see #shr(Field, Field)
     * @see #power(Field, Number)
     */
    @Support({ ASE, CUBRID, DB2, FIREBIRD, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> shr(Field<T> value1, T value2) {
        return shr(nullSafe(value1), val(value2));
    }

    /**
     * The bitwise right shift operator.
     * <p>
     * Some dialects natively support this using <code>[field1] >> [field2]</code>.
     * jOOQ simulates this operator in some dialects using
     * <code>[field1] / power(2, [field2])</code>, where power might also be simulated.
     *
     * @see #power(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, H2, FIREBIRD, HSQLDB, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE, SQLITE })
    public static <T extends Number> Field<T> shr(Field<T> field1, Field<T> field2) {
        return new Expression<T>(ExpressionOperator.SHR, nullSafe(field1), nullSafe(field2));
    }

    // ------------------------------------------------------------------------
    // XXX Mathematical functions
    // ------------------------------------------------------------------------

    /**
     * Get the rand() function
     */
    @Support
    public static Field<BigDecimal> rand() {
        return new Rand();
    }

    /**
     * Find the greatest among all values
     * <p>
     * This function has no equivalent in Adaptive Server, Derby, SQL Server and
     * Sybase SQL Anywhere. Its current simulation implementation has
     * <code>O(2^n)</code> complexity and should be avoided for
     * <code>n &gt; 5</code>! Better implementation suggestions are very
     * welcome.
     *
     * @see #greatest(Field, Field...)
     */
    @Support
    public static <T> Field<T> greatest(T value, T... values) {
        return greatest(val(value), vals(values).toArray(new Field[0]));
    }

    /**
     * Find the greatest among all values
     * <p>
     * This function has no equivalent in Adaptive Server, Derby, SQL Server and
     * Sybase SQL Anywhere. Its current simulation implementation has
     * <code>O(2^n)</code> complexity and should be avoided for
     * <code>n &gt; 5</code>! Better implementation suggestions are very
     * welcome.
     */
    @Support
    public static <T> Field<T> greatest(Field<T> field, Field<?>... others) {
        return new Greatest<T>(nullSafeDataType(field), nullSafe(combine(field, others)));
    }

    /**
     * Find the least among all values
     * <p>
     * This function has no equivalent in Adaptive Server, Derby, SQL Server and
     * Sybase SQL Anywhere. Its current simulation implementation has
     * <code>O(2^n)</code> complexity and should be avoided for
     * <code>n &gt; 5</code>! Better implementation suggestions are very
     * welcome.
     *
     * @see #least(Field, Field...)
     */
    @Support
    public static <T> Field<T> least(T value, T... values) {
        return least(val(value), vals(values).toArray(new Field[0]));
    }

    /**
     * Find the least among all values
     * <p>
     * This function has no equivalent in Adaptive Server, Derby, SQL Server and
     * Sybase SQL Anywhere. Its current simulation implementation has
     * <code>O(2^n)</code> complexity and should be avoided for
     * <code>n &gt; 5</code>! Better implementation suggestions are very
     * welcome.
     */
    @Support
    public static <T> Field<T> least(Field<T> field, Field<?>... others) {
        return new Least<T>(nullSafeDataType(field), nullSafe(combine(field, others)));
    }

    /**
     * Get the sign of a numeric field: sign(field)
     *
     * @see #sign(Field)
     */
    @Support
    public static Field<Integer> sign(Number value) {
        return sign(val(value));
    }

    /**
     * Get the sign of a numeric field: sign(field)
     * <p>
     * This renders the sign function where available:
     * <code><pre>sign([field])</pre></code>
     * ... or simulates it elsewhere (without bind variables on values -1, 0, 1):
     * <code><pre>
     * CASE WHEN [this] > 0 THEN 1
     *      WHEN [this] < 0 THEN -1
     *      ELSE 0
     * END
     */
    @Support
    public static Field<Integer> sign(Field<? extends Number> field) {
        return new Sign(nullSafe(field));
    }

    /**
     * Get the absolute value of a numeric field: abs(field)
     *
     * @see #abs(Field)
     */
    @Support
    public static <T extends Number> Field<T> abs(T value) {
        return abs(val(value));
    }

    /**
     * Get the absolute value of a numeric field: abs(field)
     * <p>
     * This renders the same on all dialects:
     * <code><pre>abs([field])</pre></code>
     */
    @Support
    public static <T extends Number> Field<T> abs(Field<T> field) {
        return function("abs", nullSafeDataType(field), nullSafe(field));
    }

    /**
     * Get rounded value of a numeric field: round(field)
     *
     * @see #round(Field)
     */
    @Support
    public static <T extends Number> Field<T> round(T value) {
        return round(val(value));
    }

    /**
     * Get rounded value of a numeric field: round(field)
     * <p>
     * This renders the round function where available:
     * <code><pre>round([field]) or
     * round([field], 0)</pre></code>
     * ... or simulates it elsewhere using floor and ceil
     */
    @Support
    public static <T extends Number> Field<T> round(Field<T> field) {
        return new Round<T>(nullSafe(field));
    }

    /**
     * Get rounded value of a numeric field: round(field, decimals)
     *
     * @see #round(Field, int)
     */
    @Support
    public static <T extends Number> Field<T> round(T value, int decimals) {
        return round(val(value), decimals);
    }

    /**
     * Get rounded value of a numeric field: round(field, decimals)
     * <p>
     * This renders the round function where available:
     * <code><pre>round([field], [decimals])</pre></code>
     * ... or simulates it elsewhere using floor and ceil
     */
    @Support
    public static <T extends Number> Field<T> round(Field<T> field, int decimals) {
        return new Round<T>(nullSafe(field), decimals);
    }

    /**
     * Get the largest integer value not greater than [this]
     *
     * @see #floor(Field)
     */
    @Support
    public static <T extends Number> Field<T> floor(T value) {
        return floor(val(value));
    }

    /**
     * Get the largest integer value not greater than [this]
     * <p>
     * This renders the floor function where available:
     * <code><pre>floor([this])</pre></code>
     * ... or simulates it elsewhere using round:
     * <code><pre>round([this] - 0.499999999999999)</pre></code>
     */
    @Support
    public static <T extends Number> Field<T> floor(Field<T> field) {
        return new Floor<T>(nullSafe(field));
    }

    /**
     * Get the smallest integer value not less than [this]
     *
     * @see #ceil(Field)
     */
    @Support
    public static <T extends Number> Field<T> ceil(T value) {
        return ceil(val(value));
    }

    /**
     * Get the smallest integer value not less than [field]
     * <p>
     * This renders the ceil or ceiling function where available:
     * <code><pre>ceil([field]) or
     * ceiling([field])</pre></code>
     * ... or simulates it elsewhere using round:
     * <code><pre>round([field] + 0.499999999999999)</pre></code>
     */
    @Support
    public static <T extends Number> Field<T> ceil(Field<T> field) {
        return new Ceil<T>(nullSafe(field));
    }

    /**
     * Truncate a number to a given number of decimals
     *
     * @see #trunc(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static <T extends Number> Field<T> trunc(T number) {
        return trunc(val(number), inline(0));
    }

    /**
     * Truncate a number to a given number of decimals
     *
     * @see #trunc(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static <T extends Number> Field<T> trunc(T number, int decimals) {
        return trunc(val(number), inline(decimals));
    }

    /**
     * Truncate a number to a given number of decimals
     *
     * @see #trunc(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static <T extends Number> Field<T> trunc(Field<T> number, int decimals) {
        return trunc(nullSafe(number), inline(decimals));
    }

    /**
     * Truncate a number to a given number of decimals
     *
     * @see #trunc(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static <T extends Number> Field<T> trunc(T number, Field<Integer> decimals) {
        return trunc(val(number), nullSafe(decimals));
    }

    /**
     * Truncate a number to a given number of decimals
     * <p>
     * This function truncates <code>number</code> to the amount of decimals
     * specified in <code>decimals</code>. Passing <code>decimals = 0</code> to
     * this function is the same as using {@link #floor(Field)}. Passing
     * positive values for <code>decimal</code> has a similar effect as
     * {@link #round(Field, int)}. Passing negative values for
     * <code>decimal</code> will truncate <code>number</code> to a given power
     * of 10. Some examples
     * <table border="1">
     * <tr>
     * <th>Function call</th>
     * <th>yields...</th>
     * </tr>
     * <tr>
     * <td>trunc(125.815)</td>
     * <td>125</td>
     * </tr>
     * <tr>
     * <td>trunc(125.815, 0)</td>
     * <td>125</td>
     * </tr>
     * <tr>
     * <td>trunc(125.815, 1)</td>
     * <td>125.8</td>
     * </tr>
     * <tr>
     * <td>trunc(125.815, 2)</td>
     * <td>125.81</td>
     * </tr>
     * <tr>
     * <td>trunc(125.815, -1)</td>
     * <td>120</td>
     * </tr>
     * <tr>
     * <td>trunc(125.815, -2)</td>
     * <td>100</td>
     * </tr>
     * </table>
     *
     * @see #trunc(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static <T extends Number> Field<T> trunc(Field<T> number, Field<Integer> decimals) {
        return new Trunc<T>(nullSafe(number), nullSafe(decimals));
    }

    /**
     * Get the sqrt(field) function
     *
     * @see #sqrt(Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> sqrt(Number value) {
        return sqrt(val(value));
    }

    /**
     * Get the sqrt(field) function
     * <p>
     * This renders the sqrt function where available:
     * <code><pre>sqrt([field])</pre></code> ... or simulates it elsewhere using
     * power (which in turn may also be simulated using ln and exp functions):
     * <code><pre>power([field], 0.5)</pre></code>
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> sqrt(Field<? extends Number> field) {
        return new Sqrt(nullSafe(field));
    }

    /**
     * Get the exp(field) function, taking this field as the power of e
     *
     * @see #exp(Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> exp(Number value) {
        return exp(val(value));
    }

    /**
     * Get the exp(field) function, taking this field as the power of e
     * <p>
     * This renders the same on all dialects:
     * <code><pre>exp([field])</pre></code>
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> exp(Field<? extends Number> field) {
        return function("exp", SQLDataType.NUMERIC, nullSafe(field));
    }

    /**
     * Get the ln(field) function, taking the natural logarithm of this field
     *
     * @see #ln(Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> ln(Number value) {
        return ln(val(value));
    }

    /**
     * Get the ln(field) function, taking the natural logarithm of this field
     * <p>
     * This renders the ln or log function where available:
     * <code><pre>ln([field]) or
     * log([field])</pre></code>
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> ln(Field<? extends Number> field) {
        return new Ln(nullSafe(field));
    }

    /**
     * Get the log(field, base) function
     *
     * @see #log(Field, int)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> log(Number value, int base) {
        return log(val(value), base);
    }

    /**
     * Get the log(field, base) function
     * <p>
     * This renders the log function where available:
     * <code><pre>log([field])</pre></code> ... or simulates it elsewhere (in
     * most RDBMS) using the natural logarithm:
     * <code><pre>ln([field]) / ln([base])</pre></code>
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> log(Field<? extends Number> field, int base) {
        return new Ln(nullSafe(field), base);
    }

    /**
     * Get the power(field, exponent) function
     *
     * @see #power(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> power(Number value, Number exponent) {
        return power(val(value), val(exponent));
    }

    /**
     * Get the power(field, exponent) function
     *
     * @see #power(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> power(Field<? extends Number> field, Number exponent) {
        return power(nullSafe(field), val(exponent));
    }

    /**
     * Get the power(field, exponent) function
     *
     * @see #power(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> power(Number value, Field<? extends Number> exponent) {
        return power(val(value), nullSafe(exponent));
    }

    /**
     * Get the power(field, exponent) function
     * <p>
     * This renders the power function where available:
     * <code><pre>power([field], [exponent])</pre></code> ... or simulates it
     * elsewhere using ln and exp:
     * <code><pre>exp(ln([field]) * [exponent])</pre></code>
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> power(Field<? extends Number> field, Field<? extends Number> exponent) {
        return new Power(nullSafe(field), nullSafe(exponent));
    }

    /**
     * Get the arc cosine(field) function
     *
     * @see #acos(Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> acos(Number value) {
        return acos(val(value));
    }

    /**
     * Get the arc cosine(field) function
     * <p>
     * This renders the acos function where available:
     * <code><pre>acos([field])</pre></code>
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> acos(Field<? extends Number> field) {
        return function("acos", SQLDataType.NUMERIC, nullSafe(field));
    }

    /**
     * Get the arc sine(field) function
     *
     * @see #asin(Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> asin(Number value) {
        return asin(val(value));
    }

    /**
     * Get the arc sine(field) function
     * <p>
     * This renders the asin function where available:
     * <code><pre>asin([field])</pre></code>
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> asin(Field<? extends Number> field) {
        return function("asin", SQLDataType.NUMERIC, nullSafe(field));
    }

    /**
     * Get the arc tangent(field) function
     *
     * @see #atan(Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> atan(Number value) {
        return atan(val(value));
    }

    /**
     * Get the arc tangent(field) function
     * <p>
     * This renders the atan function where available:
     * <code><pre>atan([field])</pre></code>
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> atan(Field<? extends Number> field) {
        return function("atan", SQLDataType.NUMERIC, nullSafe(field));
    }

    /**
     * Get the atan2(field, y) function
     *
     * @see #atan2(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> atan2(Number x, Number y) {
        return atan2(val(x), val(y));
    }

    /**
     * Get the atan2(field, y) function
     *
     * @see #atan2(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> atan2(Number x, Field<? extends Number> y) {
        return atan2(val(x), nullSafe(y));
    }

    /**
     * Get the atan2(field, y) function
      *
     * @see #atan2(Field, Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> atan2(Field<? extends Number> x, Number y) {
        return atan2(nullSafe(x), val(y));
    }

    /**
     * Get the atan2(field, y) function
     * <p>
     * This renders the atan2 or atn2 function where available:
     * <code><pre>atan2([x], [y]) or
     * atn2([x], [y])</pre></code>
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> atan2(Field<? extends Number> x, Field<? extends Number> y) {
        return new Function<BigDecimal>(Term.ATAN2, SQLDataType.NUMERIC, nullSafe(x), nullSafe(y));
    }

    /**
     * Get the cosine(field) function
     *
     * @see #cos(Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> cos(Number value) {
        return cos(val(value));
    }

    /**
     * Get the cosine(field) function
     * <p>
     * This renders the cos function where available:
     * <code><pre>cos([field])</pre></code>
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> cos(Field<? extends Number> field) {
        return function("cos", SQLDataType.NUMERIC, nullSafe(field));
    }

    /**
     * Get the sine(field) function
     *
     * @see #sin(Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> sin(Number value) {
        return sin(val(value));
    }

    /**
     * Get the sine(field) function
     * <p>
     * This renders the sin function where available:
     * <code><pre>sin([field])</pre></code>
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> sin(Field<? extends Number> field) {
        return function("sin", SQLDataType.NUMERIC, nullSafe(field));
    }

    /**
     * Get the tangent(field) function
     *
     * @see #tan(Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> tan(Number value) {
        return tan(val(value));
    }

    /**
     * Get the tangent(field) function
     * <p>
     * This renders the tan function where available:
     * <code><pre>tan([field])</pre></code>
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> tan(Field<? extends Number> field) {
        return function("tan", SQLDataType.NUMERIC, nullSafe(field));
    }

    /**
     * Get the cotangent(field) function
     *
     * @see #cot(Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> cot(Number value) {
        return cot(val(value));
    }

    /**
     * Get the cotangent(field) function
     * <p>
     * This renders the cot function where available:
     * <code><pre>cot([field])</pre></code> ... or simulates it elsewhere using
     * sin and cos: <code><pre>cos([field]) / sin([field])</pre></code>
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> cot(Field<? extends Number> field) {
        return new Cot(nullSafe(field));
    }

    /**
     * Get the hyperbolic sine function: sinh(field)
     *
     * @see #sinh(Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> sinh(Number value) {
        return sinh(val(value));
    }

    /**
     * Get the hyperbolic sine function: sinh(field)
     * <p>
     * This renders the sinh function where available:
     * <code><pre>sinh([field])</pre></code> ... or simulates it elsewhere using
     * exp: <code><pre>(exp([field] * 2) - 1) / (exp([field] * 2))</pre></code>
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> sinh(Field<? extends Number> field) {
        return new Sinh(nullSafe(field));
    }

    /**
     * Get the hyperbolic cosine function: cosh(field)
     *
     * @see #cosh(Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> cosh(Number value) {
        return cosh(val(value));
    }

    /**
     * Get the hyperbolic cosine function: cosh(field)
     * <p>
     * This renders the cosh function where available:
     * <code><pre>cosh([field])</pre></code> ... or simulates it elsewhere using
     * exp: <code><pre>(exp([field] * 2) + 1) / (exp([field] * 2))</pre></code>
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> cosh(Field<? extends Number> field) {
        return new Cosh(nullSafe(field));
    }

    /**
     * Get the hyperbolic tangent function: tanh(field)
     *
     * @see #tanh(Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> tanh(Number value) {
        return tanh(val(value));
    }

    /**
     * Get the hyperbolic tangent function: tanh(field)
     * <p>
     * This renders the tanh function where available:
     * <code><pre>tanh([field])</pre></code> ... or simulates it elsewhere using
     * exp:
     * <code><pre>(exp([field] * 2) - 1) / (exp([field] * 2) + 1)</pre></code>
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> tanh(Field<? extends Number> field) {
        return new Tanh(nullSafe(field));
    }

    /**
     * Get the hyperbolic cotangent function: coth(field)
     *
     * @see #coth(Field)
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> coth(Number value) {
        return coth(val(value));
    }

    /**
     * Get the hyperbolic cotangent function: coth(field)
     * <p>
     * This is not supported by any RDBMS, but simulated using exp exp:
     * <code><pre>(exp([field] * 2) + 1) / (exp([field] * 2) - 1)</pre></code>
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<BigDecimal> coth(Field<? extends Number> field) {
        field = nullSafe(field);
        return exp(field.mul(2)).add(1).div(exp(field.mul(2)).sub(1));
    }

    /**
     * Calculate degrees from radians from this field
     *
     * @see #deg(Field)
     */
    @Support
    public static Field<BigDecimal> deg(Number value) {
        return deg(val(value));
    }

    /**
     * Calculate degrees from radians from this field
     * <p>
     * This renders the degrees function where available:
     * <code><pre>degrees([field])</pre></code> ... or simulates it elsewhere:
     * <code><pre>[field] * 180 / PI</pre></code>
     */
    @Support
    public static Field<BigDecimal> deg(Field<? extends Number> field) {
        return new Degrees(nullSafe(field));
    }

    /**
     * Calculate radians from degrees from this field
     *
     * @see #rad(Field)
     */
    @Support
    public static Field<BigDecimal> rad(Number value) {
        return rad(val(value));
    }

    /**
     * Calculate radians from degrees from this field
     * <p>
     * This renders the degrees function where available:
     * <code><pre>degrees([field])</pre></code> ... or simulates it elsewhere:
     * <code><pre>[field] * PI / 180</pre></code>
     */
    @Support
    public static Field<BigDecimal> rad(Field<? extends Number> field) {
        return new Radians(nullSafe(field));
    }

    // -------------------------------------------------------------------------
    // Pseudo-fields and functions for use in the context of a CONNECT BY clause
    // -------------------------------------------------------------------------

    /**
     * Retrieve the Oracle-specific <code>LEVEL</code> pseudo-field (to be used
     * along with <code>CONNECT BY</code> clauses)
     */
    @Support({ CUBRID, ORACLE })
    public static Field<Integer> level() {
        return field("level", Integer.class);
    }

    /**
     * Retrieve the Oracle-specific <code>CONNECT_BY_ISCYCLE</code> pseudo-field
     * (to be used along with <code>CONNECT BY</code> clauses)
     */
    @Support({ CUBRID, ORACLE })
    public static Field<Boolean> connectByIsCycle() {
        return field("connect_by_iscycle", Boolean.class);
    }

    /**
     * Retrieve the Oracle-specific <code>CONNECT_BY_ISLEAF</code> pseudo-field
     * (to be used along with <code>CONNECT BY</code> clauses)
     */
    @Support({ CUBRID, ORACLE })
    public static Field<Boolean> connectByIsLeaf() {
        return field("connect_by_isleaf", Boolean.class);
    }

    /**
     * Retrieve the Oracle-specific <code>CONNECT_BY_ROOT</code> pseudo-column
     * (to be used along with <code>CONNECT BY</code> clauses)
     */
    @Support({ CUBRID, ORACLE })
    public static <T> Field<T> connectByRoot(Field<T> field) {
        return field("{connect_by_root} {0}", nullSafe(field).getDataType(), field);
    }

    /**
     * Retrieve the Oracle-specific
     * <code>SYS_CONNECT_BY_PATH(field, separator)</code> function (to be used
     * along with <code>CONNECT BY</code> clauses).
     */
    @Support({ CUBRID, ORACLE })
    public static Field<String> sysConnectByPath(Field<?> field, String separator) {
        return field("{sys_connect_by_path}({0}, {1})", String.class, field, inline(separator));
    }

    /**
     * Add the Oracle-specific <code>PRIOR</code> unary operator before a field
     * (to be used along with <code>CONNECT BY</code> clauses)
     */
    @Support({ CUBRID, ORACLE })
    public static <T> Field<T> prior(Field<T> field) {
        return field("{prior} {0}", nullSafe(field).getDataType(), field);
    }

    // -------------------------------------------------------------------------
    // XXX Aggregate functions
    // -------------------------------------------------------------------------

    /**
     * Get the count(*) function
     */
    @Support
    public static AggregateFunction<Integer> count() {
        return count(field("*", Integer.class));
    }

    /**
     * Get the count(field) function
     */
    @Support
    public static AggregateFunction<Integer> count(Field<?> field) {
        return new Function<Integer>("count", SQLDataType.INTEGER, nullSafe(field));
    }

    /**
     * Get the count(distinct field) function
     */
    @Support
    public static AggregateFunction<Integer> countDistinct(Field<?> field) {
        return new Function<Integer>("count", true, SQLDataType.INTEGER, nullSafe(field));
    }

    /**
     * Get the count(distinct field1, field2) function
     * <p>
     * Some dialects support several expressions in the <code>COUNT(DISTINCT expr1, expr2)</code> aggregate function.
     */
    @Support({ HSQLDB, MYSQL })
    public static AggregateFunction<Integer> countDistinct(Field<?>... fields) {
        return new Function<Integer>("count", true, SQLDataType.INTEGER, nullSafe(fields));
    }

    /**
     * Get the max value over a field: max(field)
     */
    @Support
    public static <T> AggregateFunction<T> max(Field<T> field) {
        return new Function<T>("max", nullSafeDataType(field), nullSafe(field));
    }

    /**
     * Get the max value over a field: max(distinct field)
     */
    @Support
    public static <T> AggregateFunction<T> maxDistinct(Field<T> field) {
        return new Function<T>("max", true, nullSafeDataType(field), nullSafe(field));
    }

    /**
     * Get the min value over a field: min(field)
     */
    @Support
    public static <T> AggregateFunction<T> min(Field<T> field) {
        return new Function<T>("min", nullSafeDataType(field), nullSafe(field));
    }

    /**
     * Get the min value over a field: min(distinct field)
     */
    @Support
    public static <T> AggregateFunction<T> minDistinct(Field<T> field) {
        return new Function<T>("min", true, nullSafeDataType(field), nullSafe(field));
    }

    /**
     * Get the sum over a numeric field: sum(field)
     */
    @Support
    public static AggregateFunction<BigDecimal> sum(Field<? extends Number> field) {
        return new Function<BigDecimal>("sum", SQLDataType.NUMERIC, nullSafe(field));
    }

    /**
     * Get the sum over a numeric field: sum(distinct field)
     */
    @Support
    public static AggregateFunction<BigDecimal> sumDistinct(Field<? extends Number> field) {
        return new Function<BigDecimal>("sum", true, SQLDataType.NUMERIC, nullSafe(field));
    }

    /**
     * Get the average over a numeric field: avg(field)
     */
    @Support
    public static AggregateFunction<BigDecimal> avg(Field<? extends Number> field) {
        return new Function<BigDecimal>("avg", SQLDataType.NUMERIC, nullSafe(field));
    }

    /**
     * Get the average over a numeric field: avg(distinct field)
     */
    @Support
    public static AggregateFunction<BigDecimal> avgDistinct(Field<? extends Number> field) {
        return new Function<BigDecimal>("avg", true, SQLDataType.NUMERIC, nullSafe(field));
    }

    /**
     * Get the median over a numeric field: median(field)
     */
    @Support({ HSQLDB, ORACLE, SYBASE })
    public static AggregateFunction<BigDecimal> median(Field<? extends Number> field) {
        return new Function<BigDecimal>("median", SQLDataType.NUMERIC, nullSafe(field));
    }

    /**
     * Get the population standard deviation of a numeric field: stddev_pop(field)
     */
    @Support({ ASE, CUBRID, DB2, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static AggregateFunction<BigDecimal> stddevPop(Field<? extends Number> field) {
        return new Function<BigDecimal>(Term.STDDEV_POP, SQLDataType.NUMERIC, nullSafe(field));
    }

    /**
     * Get the sample standard deviation of a numeric field: stddev_samp(field)
     */
    @Support({ ASE, CUBRID, DB2, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static AggregateFunction<BigDecimal> stddevSamp(Field<? extends Number> field) {
        return new Function<BigDecimal>(Term.STDDEV_SAMP, SQLDataType.NUMERIC, nullSafe(field));
    }

    /**
     * Get the population variance of a numeric field: var_pop(field)
     */
    @Support({ ASE, CUBRID, DB2, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static AggregateFunction<BigDecimal> varPop(Field<? extends Number> field) {
        return new Function<BigDecimal>(Term.VAR_POP, SQLDataType.NUMERIC, nullSafe(field));
    }

    /**
     * Get the sample variance of a numeric field: var_samp(field)
     */
    @Support({ ASE, CUBRID, DB2, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static AggregateFunction<BigDecimal> varSamp(Field<? extends Number> field) {
        return new Function<BigDecimal>(Term.VAR_SAMP, SQLDataType.NUMERIC, nullSafe(field));
    }

    /**
     * Get the <code>REGR_SLOPE</code> linear regression function
     * <p>
     * The linear regression functions fit an ordinary-least-squares regression
     * line to a set of number pairs. You can use them as both aggregate and
     * window functions, where this is supported.
     * <p>
     * Note that {@link SQLDialect#DB2} does not support linear regression
     * window functions.
     */
    @Support({ DB2, POSTGRES, ORACLE, SYBASE })
    public static AggregateFunction<BigDecimal> regrSlope(Field<? extends Number> y, Field<? extends Number> x) {
        return new Function<BigDecimal>("regr_slope", SQLDataType.NUMERIC, nullSafe(y), nullSafe(x));
    }

    /**
     * Get the <code>REGR_INTERCEPT</code> linear regression function
     * <p>
     * The linear regression functions fit an ordinary-least-squares regression
     * line to a set of number pairs. You can use them as both aggregate and
     * window functions, where this is supported.
     * <p>
     * Note that {@link SQLDialect#DB2} does not support linear regression
     * window functions.
     */
    @Support({ DB2, POSTGRES, ORACLE, SYBASE })
    public static AggregateFunction<BigDecimal> regrIntercept(Field<? extends Number> y, Field<? extends Number> x) {
        return new Function<BigDecimal>("regr_intercept", SQLDataType.NUMERIC, nullSafe(y), nullSafe(x));
    }

    /**
     * Get the <code>REGR_COUNT</code> linear regression function
     * <p>
     * The linear regression functions fit an ordinary-least-squares regression
     * line to a set of number pairs. You can use them as both aggregate and
     * window functions, where this is supported.
     * <p>
     * Note that {@link SQLDialect#DB2} does not support linear regression
     * window functions.
     */
    @Support({ DB2, POSTGRES, ORACLE, SYBASE })
    public static AggregateFunction<BigDecimal> regrCount(Field<? extends Number> y, Field<? extends Number> x) {
        return new Function<BigDecimal>("regr_count", SQLDataType.NUMERIC, nullSafe(y), nullSafe(x));
    }

    /**
     * Get the <code>REGR_R2</code> linear regression function
     * <p>
     * The linear regression functions fit an ordinary-least-squares regression
     * line to a set of number pairs. You can use them as both aggregate and
     * window functions, where this is supported.
     * <p>
     * Note that {@link SQLDialect#DB2} does not support linear regression
     * window functions.
     */
    @Support({ DB2, POSTGRES, ORACLE, SYBASE })
    public static AggregateFunction<BigDecimal> regrR2(Field<? extends Number> y, Field<? extends Number> x) {
        return new Function<BigDecimal>("regr_r2", SQLDataType.NUMERIC, nullSafe(y), nullSafe(x));
    }

    /**
     * Get the <code>REGR_AVGX</code> linear regression function
     * <p>
     * The linear regression functions fit an ordinary-least-squares regression
     * line to a set of number pairs. You can use them as both aggregate and
     * window functions, where this is supported.
     * <p>
     * Note that {@link SQLDialect#DB2} does not support linear regression
     * window functions.
     */
    @Support({ DB2, POSTGRES, ORACLE, SYBASE })
    public static AggregateFunction<BigDecimal> regrAvgX(Field<? extends Number> y, Field<? extends Number> x) {
        return new Function<BigDecimal>("regr_avgx", SQLDataType.NUMERIC, nullSafe(y), nullSafe(x));
    }

    /**
     * Get the <code>REGR_AVGY</code> linear regression function
     * <p>
     * The linear regression functions fit an ordinary-least-squares regression
     * line to a set of number pairs. You can use them as both aggregate and
     * window functions, where this is supported.
     * <p>
     * Note that {@link SQLDialect#DB2} does not support linear regression
     * window functions.
     */
    @Support({ DB2, POSTGRES, ORACLE, SYBASE })
    public static AggregateFunction<BigDecimal> regrAvgY(Field<? extends Number> y, Field<? extends Number> x) {
        return new Function<BigDecimal>("regr_avgy", SQLDataType.NUMERIC, nullSafe(y), nullSafe(x));
    }

    /**
     * Get the <code>REGR_SXX</code> linear regression function
     * <p>
     * The linear regression functions fit an ordinary-least-squares regression
     * line to a set of number pairs. You can use them as both aggregate and
     * window functions, where this is supported.
     * <p>
     * Note that {@link SQLDialect#DB2} does not support linear regression
     * window functions.
     */
    @Support({ DB2, POSTGRES, ORACLE, SYBASE })
    public static AggregateFunction<BigDecimal> regrSXX(Field<? extends Number> y, Field<? extends Number> x) {
        return new Function<BigDecimal>("regr_sxx", SQLDataType.NUMERIC, nullSafe(y), nullSafe(x));
    }

    /**
     * Get the <code>REGR_SYY</code> linear regression function
     * <p>
     * The linear regression functions fit an ordinary-least-squares regression
     * line to a set of number pairs. You can use them as both aggregate and
     * window functions, where this is supported.
     * <p>
     * Note that {@link SQLDialect#DB2} does not support linear regression
     * window functions.
     */
    @Support({ DB2, POSTGRES, ORACLE, SYBASE })
    public static AggregateFunction<BigDecimal> regrSYY(Field<? extends Number> y, Field<? extends Number> x) {
        return new Function<BigDecimal>("regr_syy", SQLDataType.NUMERIC, nullSafe(y), nullSafe(x));
    }

    /**
     * Get the <code>REGR_SXY</code> linear regression function
     * <p>
     * The linear regression functions fit an ordinary-least-squares regression
     * line to a set of number pairs. You can use them as both aggregate and
     * window functions, where this is supported.
     * <p>
     * Note that {@link SQLDialect#DB2} does not support linear regression
     * window functions.
     */
    @Support({ DB2, POSTGRES, ORACLE, SYBASE })
    public static AggregateFunction<BigDecimal> regrSXY(Field<? extends Number> y, Field<? extends Number> x) {
        return new Function<BigDecimal>("regr_sxy", SQLDataType.NUMERIC, nullSafe(y), nullSafe(x));
    }

    /**
     * Get the aggregated concatenation for a field.
     * <p>
     * This is natively supported by {@link SQLDialect#ORACLE}. It is simulated
     * by the following dialects:
     * <ul>
     * <li> {@link SQLDialect#CUBRID}: Using <code>GROUP_CONCAT()</code></li>
     * <li> {@link SQLDialect#DB2}: Using <code>XMLAGG()</code></li>
     * <li> {@link SQLDialect#H2}: Using <code>GROUP_CONCAT()</code></li>
     * <li> {@link SQLDialect#HSQLDB}: Using <code>GROUP_CONCAT()</code></li>
     * <li> {@link SQLDialect#MYSQL}: Using <code>GROUP_CONCAT()</code></li>
     * <li> {@link SQLDialect#POSTGRES}: Using <code>STRING_AGG()</code></li>
     * <li> {@link SQLDialect#SYBASE}: Using <code>LIST()</code></li>
     * </ul>
     *
     * @see #groupConcat(Field)
     */
    @Support({ CUBRID, DB2, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SYBASE })
    public static OrderedAggregateFunction<String> listAgg(Field<?> field) {
        return new Function<String>(Term.LIST_AGG, SQLDataType.VARCHAR, nullSafe(field));
    }

    /**
     * Get the aggregated concatenation for a field.
     * <p>
     * This is natively supported by {@link SQLDialect#ORACLE}. It is simulated
     * by the following dialects:
     * <ul>
     * <li> {@link SQLDialect#CUBRID}: Using <code>GROUP_CONCAT</code></li>
     * <li> {@link SQLDialect#DB2}: Using <code>XMLAGG()</code></li>
     * <li> {@link SQLDialect#H2}: Using <code>GROUP_CONCAT</code></li>
     * <li> {@link SQLDialect#HSQLDB}: Using <code>GROUP_CONCAT</code></li>
     * <li> {@link SQLDialect#MYSQL}: Using <code>GROUP_CONCAT</code></li>
     * <li> {@link SQLDialect#POSTGRES}: Using <code>STRING_AGG()</code></li>
     * <li> {@link SQLDialect#SYBASE}: Using <code>LIST()</code></li>
     * </ul>
     *
     * @see #groupConcat(Field)
     */
    @Support({ CUBRID, DB2, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SYBASE })
    public static OrderedAggregateFunction<String> listAgg(Field<?> field, String separator) {
        return new Function<String>(Term.LIST_AGG, SQLDataType.VARCHAR, nullSafe(field), inline(separator));
    }

    /**
     * Get the aggregated concatenation for a field.
     * <p>
     * This is natively supported by
     * <ul>
     * <li> {@link SQLDialect#CUBRID}</li>
     * <li> {@link SQLDialect#H2}</li>
     * <li> {@link SQLDialect#HSQLDB}</li>
     * <li> {@link SQLDialect#MYSQL}</li>
     * </ul>
     * <p>
     * It is simulated by the following dialects:
     * <ul>
     * <li> {@link SQLDialect#DB2}: Using <code>XMLAGG()</code></li>
     * <li> {@link SQLDialect#ORACLE}: Using <code>LISTAGG()</code></li>
     * <li> {@link SQLDialect#POSTGRES}: Using <code>STRING_AGG()</code></li>
     * <li> {@link SQLDialect#SYBASE}: Using <code>LIST()</code></li>
     * </ul>
     *
     * @see #listAgg(Field)
     */
    @Support({ CUBRID, DB2, H2, HSQLDB, MYSQL, ORACLE, POSTGRES, SYBASE })
    public static GroupConcatOrderByStep groupConcat(Field<?> field) {
        return new GroupConcat(nullSafe(field));
    }

    /**
     * Get the aggregated concatenation for a field.
     * <p>
     * This is natively supported by
     * <ul>
     * <li> {@link SQLDialect#CUBRID}</li>
     * <li> {@link SQLDialect#H2}</li>
     * <li> {@link SQLDialect#HSQLDB}</li>
     * <li> {@link SQLDialect#MYSQL}</li>
     * </ul>
     * <p>
     * It is simulated by the following dialects:
     * <ul>
     * <li> {@link SQLDialect#SYBASE}: Using <code>LIST()</code></li>
     * <li> {@link SQLDialect#POSTGRES}: Using <code>STRING_AGG()</code></li>
     * </ul>
     *
     * @see #listAgg(Field)
     */
    @Support({ CUBRID, H2, HSQLDB, MYSQL, POSTGRES, SYBASE })
    public static GroupConcatOrderByStep groupConcatDistinct(Field<?> field) {
        return new GroupConcat(nullSafe(field), true);
    }

    // -------------------------------------------------------------------------
    // XXX Window functions
    // -------------------------------------------------------------------------

    /**
     * The <code>row_number() over ([analytic clause])</code> function.
     * <p>
     * Window functions are supported in DB2, Postgres, Oracle, SQL Server and
     * Sybase.
     * <p>
     * Newer versions of {@link SQLDialect#DERBY} and {@link SQLDialect#H2} also
     * support the <code>ROW_NUMBER() OVER()</code> window function without any
     * window clause. See the respective docs for details.
     * {@link SQLDialect#HSQLDB} can simulate this function using
     * <code>ROWNUM()</code>
     */
    @Support({ CUBRID, DB2, DERBY, H2, HSQLDB, POSTGRES, ORACLE, SQLSERVER, SYBASE })
    public static WindowOverStep<Integer> rowNumber() {
        return new Function<Integer>(ROW_NUMBER, SQLDataType.INTEGER);
    }

    /**
     * The <code>rank_over() over ([analytic clause])</code> function.
     * <p>
     * Window functions are supported in DB2, Postgres, Oracle, SQL Server and
     * Sybase.
     */
    @Support({ CUBRID, DB2, POSTGRES, ORACLE, SQLSERVER, SYBASE })
    public static WindowOverStep<Integer> rank() {
        return new Function<Integer>("rank", SQLDataType.INTEGER);
    }

    /**
     * The <code>dense_rank() over ([analytic clause])</code> function.
     * <p>
     * Window functions are supported in DB2, Postgres, Oracle, SQL Server and
     * Sybase.
     */
    @Support({ CUBRID, DB2, POSTGRES, ORACLE, SQLSERVER, SYBASE })
    public static WindowOverStep<Integer> denseRank() {
        return new Function<Integer>("dense_rank", SQLDataType.INTEGER);
    }

    /**
     * The <code>precent_rank() over ([analytic clause])</code> function.
     * <p>
     * Window functions are supported in DB2, Postgres, Oracle, SQL Server and
     * Sybase.
     */
    @Support({ POSTGRES, ORACLE, SYBASE })
    public static WindowOverStep<BigDecimal> percentRank() {
        return new Function<BigDecimal>("percent_rank", SQLDataType.NUMERIC);
    }

    /**
     * The <code>cume_dist() over ([analytic clause])</code> function.
     * <p>
     * Window functions are supported in DB2, Postgres, Oracle, SQL Server and
     * Sybase.
     */
    @Support({ POSTGRES, ORACLE, SYBASE })
    public static WindowOverStep<BigDecimal> cumeDist() {
        return new Function<BigDecimal>("cume_dist", SQLDataType.NUMERIC);
    }

    /**
     * The <code>ntile([number]) over ([analytic clause])</code> function.
     * <p>
     * Window functions are supported in DB2, Postgres, Oracle, SQL Server and
     * Sybase.
     */
    @Support({ POSTGRES, ORACLE, SQLSERVER })
    public static WindowOverStep<Integer> ntile(int number) {
        return new Function<Integer>("ntile", SQLDataType.INTEGER, field("" + number, Integer.class));
    }

    /**
     * The <code>first_value(field) over ([analytic clause])</code> function.
     * <p>
     * Window functions are supported in DB2, Postgres, Oracle, SQL Server and
     * Sybase.
     */
    @Support({ DB2, POSTGRES, ORACLE, SYBASE })
    public static <T> WindowIgnoreNullsStep<T> firstValue(Field<T> field) {
        return new Function<T>("first_value", nullSafeDataType(field), nullSafe(field));
    }

    /**
     * The <code>last_value(field) over ([analytic clause])</code> function.
     * <p>
     * Window functions are supported in DB2, Postgres, Oracle, SQL Server and
     * Sybase.
     */
    @Support({ DB2, POSTGRES, ORACLE, SYBASE })
    public static <T> WindowIgnoreNullsStep<T> lastValue(Field<T> field) {
        return new Function<T>("last_value", nullSafeDataType(field), nullSafe(field));
    }

    /**
     * The <code>lead(field) over ([analytic clause])</code> function.
     * <p>
     * Window functions are supported in DB2, Postgres, Oracle, SQL Server and
     * Sybase.
     */
    @Support({ DB2, POSTGRES, ORACLE })
    public static <T> WindowIgnoreNullsStep<T> lead(Field<T> field) {
        return new Function<T>("lead", nullSafeDataType(field), nullSafe(field));
    }

    /**
     * The <code>lead(field, offset) over ([analytic clause])</code> function.
     * <p>
     * Window functions are supported in DB2, Postgres, Oracle, SQL Server and
     * Sybase.
     */
    @Support({ DB2, POSTGRES, ORACLE })
    public static <T> WindowIgnoreNullsStep<T> lead(Field<T> field, int offset) {
        return new Function<T>("lead", nullSafeDataType(field), nullSafe(field), inline(offset));
    }

    /**
     * The
     * <code>lead(field, offset, defaultValue) over ([analytic clause])</code>
     * function.
     * <p>
     * Window functions are supported in DB2, Postgres, Oracle, SQL Server and
     * Sybase.
     */
    @Support({ DB2, POSTGRES, ORACLE })
    public static <T> WindowIgnoreNullsStep<T> lead(Field<T> field, int offset, T defaultValue) {
        return lead(nullSafe(field), offset, val(defaultValue));
    }

    /**
     * The
     * <code>lead(field, offset, defaultValue) over ([analytic clause])</code>
     * function.
     * <p>
     * Window functions are supported in DB2, Postgres, Oracle, SQL Server and
     * Sybase.
     */
    @Support({ DB2, POSTGRES, ORACLE })
    public static <T> WindowIgnoreNullsStep<T> lead(Field<T> field, int offset, Field<T> defaultValue) {
        return new Function<T>("lead", nullSafeDataType(field), nullSafe(field), inline(offset), nullSafe(defaultValue));
    }

    /**
     * The <code>lag(field) over ([analytic clause])</code> function.
     * <p>
     * Window functions are supported in DB2, Postgres, Oracle, SQL Server and
     * Sybase.
     */
    @Support({ DB2, POSTGRES, ORACLE })
    public static <T> WindowIgnoreNullsStep<T> lag(Field<T> field) {
        return new Function<T>("lag", nullSafeDataType(field), nullSafe(field));
    }

    /**
     * The <code>lag(field, offset) over ([analytic clause])</code> function.
     * <p>
     * Window functions are supported in DB2, Postgres, Oracle, SQL Server and
     * Sybase.
     */
    @Support({ DB2, POSTGRES, ORACLE })
    public static <T> WindowIgnoreNullsStep<T> lag(Field<T> field, int offset) {
        return new Function<T>("lag", nullSafeDataType(field), nullSafe(field), inline(offset));
    }

    /**
     * The
     * <code>lag(field, offset, defaultValue) over ([analytic clause])</code>
     * function.
     * <p>
     * Window functions are supported in DB2, Postgres, Oracle, SQL Server and
     * Sybase.
     */
    @Support({ DB2, POSTGRES, ORACLE })
    public static <T> WindowIgnoreNullsStep<T> lag(Field<T> field, int offset, T defaultValue) {
        return lag(nullSafe(field), offset, val(defaultValue));
    }

    /**
     * The
     * <code>lag(field, offset, defaultValue) over ([analytic clause])</code>
     * function.
     * <p>
     * Window functions are supported in DB2, Postgres, Oracle, SQL Server and
     * Sybase.
     */
    @Support({ DB2, POSTGRES, ORACLE })
    public static <T> WindowIgnoreNullsStep<T> lag(Field<T> field, int offset, Field<T> defaultValue) {
        return new Function<T>("lag", nullSafeDataType(field), nullSafe(field), inline(offset), nullSafe(defaultValue));
    }

    // -------------------------------------------------------------------------
    // XXX Bind values
    // -------------------------------------------------------------------------

    /**
     * Create a named parameter with a generic type ({@link Object} /
     * {@link SQLDataType#OTHER}) and no initial value.
     * <p>
     * Try to avoid this method when using any of these databases, as these
     * databases may have trouble inferring the type of the bind value. Use
     * typed named parameters instead, using {@link #param(String, Class)} or
     * {@link #param(String, DataType)}
     * <ul>
     * <li> {@link SQLDialect#DB2}</li>
     * <li> {@link SQLDialect#DERBY}</li>
     * <li> {@link SQLDialect#H2}</li>
     * <li> {@link SQLDialect#HSQLDB}</li>
     * <li> {@link SQLDialect#INGRES}</li>
     * <li> {@link SQLDialect#SYBASE}</li>
     * </ul>
     *
     * @see #param(String, Object)
     */
    @Support
    public static Param<Object> param(String name) {
        return param(name, Object.class);
    }

    /**
     * Create a named parameter with a defined type and no initial value.
     *
     * @see #param(String, Object)
     */
    @Support
    public static <T> Param<T> param(String name, Class<T> type) {
        return param(name, SQLDataType.getDataType(null, type));
    }

    /**
     * Create a named parameter with a defined type and no initial value.
     *
     * @see #param(String, Object)
     */
    @Support
    public static <T> Param<T> param(String name, DataType<T> type) {
        return new Val<T>(null, type, name);
    }

    /**
     * Create a named parameter with an initial value.
     * <p>
     * Named parameters are useful for several use-cases:
     * <ul>
     * <li>They can be used with Spring's <code>JdbcTemplate</code>, which
     * supports named parameters. Use
     * {@link Executor#renderNamedParams(QueryPart)} to render
     * parameter names in SQL</li>
     * <li>Named parameters can be retrieved using a well-known name from
     * {@link Query#getParam(String)} and {@link Query#getParams()}.</li>
     * </ul>
     *
     * @see Query#getParam(String)
     * @see Query#getParams()
     * @see Executor#renderNamedParams(QueryPart)
     */
    @Support
    public static <T> Param<T> param(String name, T value) {
        return new Val<T>(value, val(value).getDataType(), name);
    }

    /**
     * A synonym for {@link #val(Object)} to be used in Scala and Groovy, where
     * <code>val</code> is a reserved keyword.
     *
     * @see #val(Object)
     */
    @Support
    public static <T> Field<T> value(T value) {
        return val(value);
    }

    /**
     * A synonym for {@link #val(Object, Class)} to be used in Scala and Groovy, where
     * <code>val</code> is a reserved keyword.
     *
     * @see #val(Object, Class)
     */
    @Support
    public static <T> Field<T> value(Object value, Class<T> type) {
        return val(value, type);
    }

    /**
     * A synonym for {@link #val(Object, Field)} to be used in Scala and Groovy, where
     * <code>val</code> is a reserved keyword.
     *
     * @see #val(Object, Field)
     */
    @Support
    public static <T> Field<T> value(Object value, Field<T> field) {
        return val(value, field);
    }

    /**
     * A synonym for {@link #val(Object, DataType)} to be used in Scala and Groovy, where
     * <code>val</code> is a reserved keyword.
     *
     * @see #val(Object, DataType)
     */
    @Support
    public static <T> Field<T> value(Object value, DataType<T> type) {
        return val(value, type);
    }

    /**
     * Create a bind value, that is always inlined.
     * <p>
     * The resulting bind value is always inlined, regardless of the
     * {@link Settings#getStatementType()} property of the rendering factory.
     * Unlike with {@link #field(String)}, you can expect <code>value</code> to
     * be properly escaped for SQL syntax correctness and SQL injection
     * prevention. For example:
     * <ul>
     * <li><code>inline("abc'def")</code> renders <code>'abc''def'</code></li>
     * <li><code>field("abc'def")</code> renders <code>abc'def</code></li>
     * </ul>
     *
     * @see #val(Object)
     */
    @Support
    public static <T> Param<T> inline(T value) {
        // [#1309] TODO, make this type-safe
        Param<T> val = (Param<T>) val(value);
        val.setInline(true);
        return val;
    }

    /**
     * Create a bind value, that is always inlined.
     * <p>
     * This is a convenience method for {@link #inline(Object)}, returning
     * <code>Field&lt;String&gt;</code>, rather than
     * <code>Field&lt;Character&gt;</code>
     *
     * @see #inline(Object)
     */
    @Support
    public static Param<String> inline(char character) {
        return inline("" + character);
    }

    /**
     * Create a bind value, that is always inlined.
     * <p>
     * This is a convenience method for {@link #inline(Object)}, returning
     * <code>Field&lt;String&gt;</code>, rather than
     * <code>Field&lt;Character&gt;</code>
     *
     * @see #inline(Object)
     */
    @Support
    public static Param<String> inline(Character character) {
        return inline((character == null) ? null : ("" + character));
    }

    /**
     * Create a bind value, that is always inlined.
     * <p>
     * This is a convenience method for {@link #inline(Object)}, returning
     * <code>Field&lt;String&gt;</code>, rather than
     * <code>Field&lt;CharSequence&gt;</code>
     *
     * @see #inline(Object)
     */
    @Support
    public static Param<String> inline(CharSequence character) {
        return (Param) inline((Object) ((character == null) ? null : ("" + character)));
    }

    /**
     * Create a bind value, that is always inlined.
     * <p>
     * The resulting bind value is always inlined, regardless of the
     * {@link Settings#getStatementType()} property of the rendering factory.
     * Unlike with {@link #field(String, Class)}, you can expect
     * <code>value</code> to be properly escaped for SQL syntax correctness and
     * SQL injection prevention. For example:
     * <ul>
     * <li><code>inline("abc'def")</code> renders <code>'abc''def'</code></li>
     * <li><code>field("abc'def")</code> renders <code>abc'def</code></li>
     * </ul>
     *
     * @see #val(Object, Class)
     */
    @Support
    public static <T> Param<T> inline(Object value, Class<T> type) {
        // [#1309] TODO, make this type-safe
        Param<T> val = (Param<T>) val(value, type);
        val.setInline(true);
        return val;
    }

    /**
     * Create a bind value, that is always inlined.
     * <p>
     * The resulting bind value is always inlined, regardless of the
     * {@link Settings#getStatementType()} property of the rendering factory.
     * Unlike with {@link #field(String, DataType)}, you can expect
     * <code>value</code> to be properly escaped for SQL syntax correctness and
     * SQL injection prevention. For example:
     * <ul>
     * <li><code>inline("abc'def")</code> renders <code>'abc''def'</code></li>
     * <li><code>field("abc'def")</code> renders <code>abc'def</code></li>
     * </ul>
     *
     * @see #val(Object, Field)
     */
    @Support
    public static <T> Param<T> inline(Object value, Field<T> field) {
        // [#1309] TODO, make this type-safe
        Param<T> val = (Param<T>) val(value, field);
        val.setInline(true);
        return val;
    }

    /**
     * Create a bind value, that is always inlined.
     * <p>
     * The resulting bind value is always inlined, regardless of the
     * {@link Settings#getStatementType()} property of the rendering factory.
     * Unlike with {@link #field(String, DataType)}, you can expect
     * <code>value</code> to be properly escaped for SQL syntax correctness and
     * SQL injection prevention. For example:
     * <ul>
     * <li><code>inline("abc'def")</code> renders <code>'abc''def'</code></li>
     * <li><code>field("abc'def")</code> renders <code>abc'def</code></li>
     * </ul>
     *
     * @see #val(Object, DataType)
     */
    @Support
    public static <T> Param<T> inline(Object value, DataType<T> type) {
        // [#1309] TODO, make this type-safe
        Param<T> val = (Param<T>) val(value, type);
        val.setInline(true);
        return val;
    }

    /**
     * Get a bind value
     * <p>
     * jOOQ tries to derive the RDBMS {@link DataType} from the provided Java
     * type <code>&lt;T&gt;</code>. This may not always be accurate, which can
     * lead to problems in some strongly typed RDMBS, especially when value is
     * <code>null</code>. These databases are namely:
     * <ul>
     * <li>{@link SQLDialect#DERBY}</li>
     * <li>{@link SQLDialect#DB2}</li>
     * <li>{@link SQLDialect#H2}</li>
     * <li>{@link SQLDialect#HSQLDB}</li>
     * <li>{@link SQLDialect#INGRES}</li>
     * <li>{@link SQLDialect#SYBASE}</li>
     * </ul>
     * <p>
     * If you need more type-safety, please use {@link #val(Object, DataType)}
     * instead, and provide the precise RDMBS-specific data type, that is
     * needed.
     *
     * @param <T> The generic value type
     * @param value The constant value
     * @return A field representing the constant value
     */
    @Support
    public static <T> Field<T> val(T value) {

        // Prevent errors due to type erasure and unchecked invocation
        if (value instanceof Field<?>) {
            return (Field<T>) value;
        }

        // Default behaviour
        else {
            Class<?> type = (value == null) ? Object.class : value.getClass();
            return (Field<T>) val(value, getDataType(type));
        }
    }

    /**
     * Get a bind value with an associated type, taken from a field
     *
     * @param <T> The generic value type
     * @param value The constant value
     * @param type The data type to enforce upon the value
     * @return A field representing the constant value
     * @see #val(Object, DataType)
     */
    @Support
    public static <T> Field<T> val(Object value, Class<T> type) {
        return val(value, getDataType(type));
    }

    /**
     * Get a bind value with an associated type, taken from a field
     *
     * @param <T> The generic value type
     * @param value The constant value
     * @param field The field whose data type to enforce upon the value
     * @return A field representing the constant value
     * @see #val(Object, DataType)
     */
    @Support
    public static <T> Field<T> val(Object value, Field<T> field) {
        return val(value, nullSafeDataType(field));
    }

    /**
     * Get a bind value with an associated type
     * <p>
     * This will try to bind <code>value</code> as <code>type</code> in a
     * <code>PreparedStatement</code>. If <code>value</code> and
     * <code>type</code> are not compatible, jOOQ will first try to convert and
     * then to cast <code>value</code> to <code>type</code>.
     *
     * @param <T> The generic value type
     * @param value The constant value
     * @param type The data type to enforce upon the value
     * @return A field representing the constant value
     */
    @Support
    public static <T> Field<T> val(Object value, DataType<T> type) {

        // Prevent errors due to type erasure and unchecked invocation
        if (value instanceof Field<?>) {
            return (Field<T>) value;
        }

        // Advanced data types have dedicated constant types
        else if (value instanceof UDTRecord) {
            return new UDTConstant((UDTRecord) value);
        }
        else if (value instanceof ArrayRecord) {
            return new ArrayConstant((ArrayRecord) value);
        }

        // The default behaviour
        else {
            return new Val<T>(type.convert(value), type);
        }
    }

    /**
     * Get a list of bind values and fields
     */
    @Support
    public static List<Field<?>> vals(Object... values) {
        FieldList result = new FieldList();

        if (values != null) {
            for (Object value : values) {

                // Fields can be mixed with constant values
                if (value instanceof Field<?>) {
                    result.add((Field<?>) value);
                }
                else {
                    result.add(val(value));
                }
            }
        }

        return result;
    }

    /**
     * Get a list of bind values for a matching list of fields
     */
    @Support
    public static List<Field<?>> vals(Object[] values, Field<?>[] fields) {
        FieldList result = new FieldList();

        if (values != null && fields != null) {
            for (int i = 0; i < values.length && i < fields.length; i++) {
                result.add(val(values[i], fields[i]));
            }
        }

        return result;
    }

    /**
     * Get a list of bind values for a matching list of types
     */
    @Support
    public static List<Field<?>> vals(Object[] values, Class<?>[] types) {
        FieldList result = new FieldList();

        if (values != null && types != null) {
            for (int i = 0; i < values.length && i < types.length; i++) {
                result.add(val(values[i], types[i]));
            }
        }

        return result;
    }

    /**
     * Get a list of bind values for a matching list of types
     */
    @Support
    public static List<Field<?>> vals(Object[] values, DataType<?>[] types) {
        FieldList result = new FieldList();

        if (values != null && types != null) {
            for (int i = 0; i < values.length && i < types.length; i++) {
                result.add(val(values[i], types[i]));
            }
        }

        return result;
    }

// [jooq-tools] START [row-value]
    /**
     * Create a row value expression of degree <code>1</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1> Row1<T1> row(T1 t1) {
        return row(val(t1));
    }

    /**
     * Create a row value expression of degree <code>2</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2> Row2<T1, T2> row(T1 t1, T2 t2) {
        return row(val(t1), val(t2));
    }

    /**
     * Create a row value expression of degree <code>3</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3> Row3<T1, T2, T3> row(T1 t1, T2 t2, T3 t3) {
        return row(val(t1), val(t2), val(t3));
    }

    /**
     * Create a row value expression of degree <code>4</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4> Row4<T1, T2, T3, T4> row(T1 t1, T2 t2, T3 t3, T4 t4) {
        return row(val(t1), val(t2), val(t3), val(t4));
    }

    /**
     * Create a row value expression of degree <code>5</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5> Row5<T1, T2, T3, T4, T5> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
        return row(val(t1), val(t2), val(t3), val(t4), val(t5));
    }

    /**
     * Create a row value expression of degree <code>6</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6> Row6<T1, T2, T3, T4, T5, T6> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
        return row(val(t1), val(t2), val(t3), val(t4), val(t5), val(t6));
    }

    /**
     * Create a row value expression of degree <code>7</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7> Row7<T1, T2, T3, T4, T5, T6, T7> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
        return row(val(t1), val(t2), val(t3), val(t4), val(t5), val(t6), val(t7));
    }

    /**
     * Create a row value expression of degree <code>8</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8> Row8<T1, T2, T3, T4, T5, T6, T7, T8> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
        return row(val(t1), val(t2), val(t3), val(t4), val(t5), val(t6), val(t7), val(t8));
    }

    /**
     * Create a row value expression of degree <code>9</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9) {
        return row(val(t1), val(t2), val(t3), val(t4), val(t5), val(t6), val(t7), val(t8), val(t9));
    }

    /**
     * Create a row value expression of degree <code>10</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
        return row(val(t1), val(t2), val(t3), val(t4), val(t5), val(t6), val(t7), val(t8), val(t9), val(t10));
    }

    /**
     * Create a row value expression of degree <code>11</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11) {
        return row(val(t1), val(t2), val(t3), val(t4), val(t5), val(t6), val(t7), val(t8), val(t9), val(t10), val(t11));
    }

    /**
     * Create a row value expression of degree <code>12</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12) {
        return row(val(t1), val(t2), val(t3), val(t4), val(t5), val(t6), val(t7), val(t8), val(t9), val(t10), val(t11), val(t12));
    }

    /**
     * Create a row value expression of degree <code>13</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13) {
        return row(val(t1), val(t2), val(t3), val(t4), val(t5), val(t6), val(t7), val(t8), val(t9), val(t10), val(t11), val(t12), val(t13));
    }

    /**
     * Create a row value expression of degree <code>14</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14) {
        return row(val(t1), val(t2), val(t3), val(t4), val(t5), val(t6), val(t7), val(t8), val(t9), val(t10), val(t11), val(t12), val(t13), val(t14));
    }

    /**
     * Create a row value expression of degree <code>15</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15) {
        return row(val(t1), val(t2), val(t3), val(t4), val(t5), val(t6), val(t7), val(t8), val(t9), val(t10), val(t11), val(t12), val(t13), val(t14), val(t15));
    }

    /**
     * Create a row value expression of degree <code>16</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16) {
        return row(val(t1), val(t2), val(t3), val(t4), val(t5), val(t6), val(t7), val(t8), val(t9), val(t10), val(t11), val(t12), val(t13), val(t14), val(t15), val(t16));
    }

    /**
     * Create a row value expression of degree <code>17</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17) {
        return row(val(t1), val(t2), val(t3), val(t4), val(t5), val(t6), val(t7), val(t8), val(t9), val(t10), val(t11), val(t12), val(t13), val(t14), val(t15), val(t16), val(t17));
    }

    /**
     * Create a row value expression of degree <code>18</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18) {
        return row(val(t1), val(t2), val(t3), val(t4), val(t5), val(t6), val(t7), val(t8), val(t9), val(t10), val(t11), val(t12), val(t13), val(t14), val(t15), val(t16), val(t17), val(t18));
    }

    /**
     * Create a row value expression of degree <code>19</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19) {
        return row(val(t1), val(t2), val(t3), val(t4), val(t5), val(t6), val(t7), val(t8), val(t9), val(t10), val(t11), val(t12), val(t13), val(t14), val(t15), val(t16), val(t17), val(t18), val(t19));
    }

    /**
     * Create a row value expression of degree <code>20</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20) {
        return row(val(t1), val(t2), val(t3), val(t4), val(t5), val(t6), val(t7), val(t8), val(t9), val(t10), val(t11), val(t12), val(t13), val(t14), val(t15), val(t16), val(t17), val(t18), val(t19), val(t20));
    }

    /**
     * Create a row value expression of degree <code>21</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21) {
        return row(val(t1), val(t2), val(t3), val(t4), val(t5), val(t6), val(t7), val(t8), val(t9), val(t10), val(t11), val(t12), val(t13), val(t14), val(t15), val(t16), val(t17), val(t18), val(t19), val(t20), val(t21));
    }

    /**
     * Create a row value expression of degree <code>22</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21, T22 t22) {
        return row(val(t1), val(t2), val(t3), val(t4), val(t5), val(t6), val(t7), val(t8), val(t9), val(t10), val(t11), val(t12), val(t13), val(t14), val(t15), val(t16), val(t17), val(t18), val(t19), val(t20), val(t21), val(t22));
    }

// [jooq-tools] END [row-value]

    /**
     * Create a row value expression of degree <code>N > 8</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Support
    public static RowN row(Object... values) {
        return row(vals(values).toArray(new Field[0]));
    }

// [jooq-tools] START [row-field]
    /**
     * Create a row value expression of degree <code>1</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1> Row1<T1> row(Field<T1> t1) {
        return new RowImpl(t1);
    }

    /**
     * Create a row value expression of degree <code>2</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2> Row2<T1, T2> row(Field<T1> t1, Field<T2> t2) {
        return new RowImpl(t1, t2);
    }

    /**
     * Create a row value expression of degree <code>3</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3> Row3<T1, T2, T3> row(Field<T1> t1, Field<T2> t2, Field<T3> t3) {
        return new RowImpl(t1, t2, t3);
    }

    /**
     * Create a row value expression of degree <code>4</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4> Row4<T1, T2, T3, T4> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4) {
        return new RowImpl(t1, t2, t3, t4);
    }

    /**
     * Create a row value expression of degree <code>5</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5> Row5<T1, T2, T3, T4, T5> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5) {
        return new RowImpl(t1, t2, t3, t4, t5);
    }

    /**
     * Create a row value expression of degree <code>6</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6> Row6<T1, T2, T3, T4, T5, T6> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6) {
        return new RowImpl(t1, t2, t3, t4, t5, t6);
    }

    /**
     * Create a row value expression of degree <code>7</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7> Row7<T1, T2, T3, T4, T5, T6, T7> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7) {
        return new RowImpl(t1, t2, t3, t4, t5, t6, t7);
    }

    /**
     * Create a row value expression of degree <code>8</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8> Row8<T1, T2, T3, T4, T5, T6, T7, T8> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8) {
        return new RowImpl(t1, t2, t3, t4, t5, t6, t7, t8);
    }

    /**
     * Create a row value expression of degree <code>9</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9) {
        return new RowImpl(t1, t2, t3, t4, t5, t6, t7, t8, t9);
    }

    /**
     * Create a row value expression of degree <code>10</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
        return new RowImpl(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    /**
     * Create a row value expression of degree <code>11</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11) {
        return new RowImpl(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
    }

    /**
     * Create a row value expression of degree <code>12</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12) {
        return new RowImpl(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
    }

    /**
     * Create a row value expression of degree <code>13</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13) {
        return new RowImpl(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
    }

    /**
     * Create a row value expression of degree <code>14</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14) {
        return new RowImpl(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
    }

    /**
     * Create a row value expression of degree <code>15</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15) {
        return new RowImpl(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
    }

    /**
     * Create a row value expression of degree <code>16</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16) {
        return new RowImpl(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
    }

    /**
     * Create a row value expression of degree <code>17</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17) {
        return new RowImpl(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
    }

    /**
     * Create a row value expression of degree <code>18</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18) {
        return new RowImpl(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
    }

    /**
     * Create a row value expression of degree <code>19</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19) {
        return new RowImpl(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
    }

    /**
     * Create a row value expression of degree <code>20</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20) {
        return new RowImpl(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
    }

    /**
     * Create a row value expression of degree <code>21</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21) {
        return new RowImpl(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
    }

    /**
     * Create a row value expression of degree <code>22</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Generated("This method was generated using jOOQ-tools")
    @Support
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21, Field<T22> t22) {
        return new RowImpl(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
    }

// [jooq-tools] END [row-field]

    /**
     * Create a row value expression of degree <code>N > 8</code>
     * <p>
     * Note: Not all databases support row value expressions, but many row value
     * expression operations can be simulated on all databases. See relevant row
     * value expression method Javadocs for details.
     */
    @Support
    public static RowN row(Field<?>... values) {
        return new RowImpl(values);
    }

    // -------------------------------------------------------------------------
    // [#915] TODO: These are experimental VALUES() table constructors
    // -------------------------------------------------------------------------

    static <T1> Table<Record1<T1>> values(Row1<T1>... rows) {
        return new Values<Record1<T1>>(rows);
    }

    static <T1, T2> Table<Record2<T1, T2>> values(Row2<T1, T2>... rows) {
        return new Values<Record2<T1, T2>>(rows);
    }

    static <T1, T2, T3> Table<Record3<T1, T2, T3>> values(Row3<T1, T2, T3>... rows) {
        return new Values<Record3<T1, T2, T3>>(rows);
    }

    static <T1, T2, T3, T4> Table<Record4<T1, T2, T3, T4>> values(Row4<T1, T2, T3, T4>... rows) {
        return new Values<Record4<T1, T2, T3, T4>>(rows);
    }

    static <T1, T2, T3, T4, T5> Table<Record5<T1, T2, T3, T4, T5>> values(Row5<T1, T2, T3, T4, T5>... rows) {
        return new Values<Record5<T1, T2, T3, T4, T5>>(rows);
    }

    static <T1, T2, T3, T4, T5, T6> Table<Record6<T1, T2, T3, T4, T5, T6>> values(Row6<T1, T2, T3, T4, T5, T6>... rows) {
        return new Values<Record6<T1, T2, T3, T4, T5, T6>>(rows);
    }

    static <T1, T2, T3, T4, T5, T6, T7> Table<Record7<T1, T2, T3, T4, T5, T6, T7>> values(Row7<T1, T2, T3, T4, T5, T6, T7>... rows) {
        return new Values<Record7<T1, T2, T3, T4, T5, T6, T7>>(rows);
    }

    static <T1, T2, T3, T4, T5, T6, T7, T8> Table<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> values(Row8<T1, T2, T3, T4, T5, T6, T7, T8>... rows) {
        return new Values<Record8<T1, T2, T3, T4, T5, T6, T7, T8>>(rows);
    }

    static Table<Record> values(RowN... rows) {
        return new Values<Record>(rows);
    }

    // -------------------------------------------------------------------------
    // XXX Literals
    // -------------------------------------------------------------------------

    /**
     * Get the null field
     */
    static Field<?> NULL() {
        return field("null");
    }

    /**
     * Null-safety of a field
     */
    protected static <T> Field<T> nullSafe(Field<T> field) {
        return field == null ? val((T) null) : field;
    }

    /**
     * Null-safety of a field
     */
    protected static Field<?>[] nullSafe(Field<?>... fields) {
        Field<?>[] result = new Field<?>[fields.length];

        for (int i = 0; i < fields.length; i++) {
            result[i] = nullSafe(fields[i]);
        }

        return result;
    }

    /**
     * Get a default data type if a field is null
     */
    protected static <T> DataType<T> nullSafeDataType(Field<T> field) {
        return (DataType<T>) (field == null ? SQLDataType.OTHER : field.getDataType());
    }

    /**
     * A <code>0</code> literal.
     * <p>
     * This is useful for mathematical functions or for
     * <code>EXISTS (SELECT 0 ...)</code> or <code>PARTITION BY 0</code> clauses
     * and similar constructs. The <code>0</code> literal will not generate a
     * bind variable.
     *
     * @return A <code>0</code> literal as a <code>Field</code>
     */
    @Support
    public static Field<Integer> zero() {
        return inline(0);
    }

    /**
     * A <code>1</code> literal.
     * <p>
     * This is useful for mathematical functions or for
     * <code>EXISTS (SELECT 1 ...)</code> or <code>PARTITION BY 1</code> clauses
     * and similar constructs. The <code>1</code> literal will not generate a
     * bind variable.
     *
     * @return A <code>1</code> literal as a <code>Field</code>
     */
    @Support
    public static Field<Integer> one() {
        return inline(1);
    }

    /**
     * A <code>2</code> literal.
     * <p>
     * This is useful for mathematical functions. The <code>1</code> literal
     * will not generate a bind variable.
     *
     * @return A <code>2</code> literal as a <code>Field</code>
     */
    @Support
    public static Field<Integer> two() {
        return inline(2);
    }

    /**
     * The <code>PI</code> literal.
     * <p>
     * This will be any of the following:
     * <ul>
     * <li>The underlying RDBMS' <code>PI</code> literal or <code>PI()</code> function</li>
     * <li>{@link Math#PI}</li>
     * </ul>
     */
    @Support
    public static Field<BigDecimal> pi() {
        return new Pi();
    }

    /**
     * The <code>E</code> literal (Euler number)
     * <p>
     * This will be any of the following:
     * <ul>
     * <li>The underlying RDBMS' <code>E</code> literal or <code>E()</code> function</li>
     * <li>{@link Math#E}</li>
     * </ul>
     */
    @Support
    public static Field<BigDecimal> e() {
        return new Euler();
    }

    // -------------------------------------------------------------------------
    // XXX other functions
    // -------------------------------------------------------------------------

    /**
     * Get the current_user() function
     * <p>
     * This translates into any dialect
     */
    @Support({ ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, HSQLDB, INGRES, MYSQL, ORACLE, POSTGRES, SQLSERVER, SYBASE })
    public static Field<String> currentUser() {
        return new CurrentUser();
    }

    /**
     * Get the default data type for the {@link Executor}'s underlying
     * {@link SQLDialect} and a given Java type.
     * <p>
     * This is a convenience method for calling
     * {@link FieldTypeHelper#getDataType(SQLDialect, Class)}
     *
     * @param <T> The generic type
     * @param type The Java type
     * @return The <code>Factory</code>'s underlying default data type.
     */
    @SuppressWarnings("deprecation")
    @Support
    public static <T> DataType<T> getDataType(Class<T> type) {
        return FieldTypeHelper.getDataType(SQLDialect.SQL99, type);
    }

    /**
     * No instances
     */
    protected Factory() {}
}

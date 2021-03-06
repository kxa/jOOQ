/**
 * This class is generated by jOOQ
 */
package org.jooq.test.db2.generatedclasses.tables;

/**
 * This class is generated by jOOQ.
 */
@java.lang.SuppressWarnings("all")
public class TDates extends org.jooq.impl.UpdatableTableImpl<org.jooq.test.db2.generatedclasses.tables.records.TDatesRecord> {

	private static final long serialVersionUID = 1097361201;

	/**
	 * The singleton instance of LUKAS.T_DATES
	 */
	public static final org.jooq.test.db2.generatedclasses.tables.TDates T_DATES = new org.jooq.test.db2.generatedclasses.tables.TDates();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<org.jooq.test.db2.generatedclasses.tables.records.TDatesRecord> getRecordType() {
		return org.jooq.test.db2.generatedclasses.tables.records.TDatesRecord.class;
	}

	/**
	 * The table column <code>LUKAS.T_DATES.ID</code>
	 * <p>
	 * This column is part of the table's PRIMARY KEY
	 */
	public static final org.jooq.TableField<org.jooq.test.db2.generatedclasses.tables.records.TDatesRecord, java.lang.Integer> ID = createField("ID", org.jooq.impl.SQLDataType.INTEGER, T_DATES);

	/**
	 * The table column <code>LUKAS.T_DATES.D</code>
	 */
	public static final org.jooq.TableField<org.jooq.test.db2.generatedclasses.tables.records.TDatesRecord, java.sql.Date> D = createField("D", org.jooq.impl.SQLDataType.DATE, T_DATES);

	/**
	 * The table column <code>LUKAS.T_DATES.T</code>
	 */
	public static final org.jooq.TableField<org.jooq.test.db2.generatedclasses.tables.records.TDatesRecord, java.sql.Time> T = createField("T", org.jooq.impl.SQLDataType.TIME, T_DATES);

	/**
	 * The table column <code>LUKAS.T_DATES.TS</code>
	 */
	public static final org.jooq.TableField<org.jooq.test.db2.generatedclasses.tables.records.TDatesRecord, java.sql.Timestamp> TS = createField("TS", org.jooq.impl.SQLDataType.TIMESTAMP, T_DATES);

	/**
	 * The table column <code>LUKAS.T_DATES.D_INT</code>
	 */
	public static final org.jooq.TableField<org.jooq.test.db2.generatedclasses.tables.records.TDatesRecord, java.lang.Integer> D_INT = createField("D_INT", org.jooq.impl.SQLDataType.INTEGER, T_DATES);

	/**
	 * The table column <code>LUKAS.T_DATES.TS_BIGINT</code>
	 */
	public static final org.jooq.TableField<org.jooq.test.db2.generatedclasses.tables.records.TDatesRecord, java.lang.Long> TS_BIGINT = createField("TS_BIGINT", org.jooq.impl.SQLDataType.BIGINT, T_DATES);

	/**
	 * No further instances allowed
	 */
	private TDates() {
		super("T_DATES", org.jooq.test.db2.generatedclasses.Lukas.LUKAS);
	}

	@Override
	public org.jooq.UniqueKey<org.jooq.test.db2.generatedclasses.tables.records.TDatesRecord> getMainKey() {
		return org.jooq.test.db2.generatedclasses.Keys.T_DATES__PK_T_DATES;
	}

	@Override
	@SuppressWarnings("unchecked")
	public java.util.List<org.jooq.UniqueKey<org.jooq.test.db2.generatedclasses.tables.records.TDatesRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<org.jooq.test.db2.generatedclasses.tables.records.TDatesRecord>>asList(org.jooq.test.db2.generatedclasses.Keys.T_DATES__PK_T_DATES);
	}
}

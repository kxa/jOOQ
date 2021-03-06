/**
 * This class is generated by jOOQ
 */
package org.jooq.test.oracle3.generatedclasses.tables.interfaces;

/**
 * This class is generated by jOOQ.
 */
@java.lang.SuppressWarnings("all")
@javax.persistence.Entity
@javax.persistence.Table(name = "T_DATES", schema = "TEST")
public interface T_DATES_INTERFACE extends java.lang.Cloneable, java.io.Serializable {

	/**
	 * The table column <code>TEST.T_DATES.ID</code>
	 * <p>
	 * This column is part of the table's PRIMARY KEY
	 */
	public void setID(java.lang.Integer value);

	/**
	 * The table column <code>TEST.T_DATES.ID</code>
	 * <p>
	 * This column is part of the table's PRIMARY KEY
	 */
	@javax.persistence.Id
	@javax.persistence.Column(name = "ID", unique = true, nullable = false, precision = 7)
	public java.lang.Integer getID();

	/**
	 * The table column <code>TEST.T_DATES.D</code>
	 */
	public void setD(java.sql.Date value);

	/**
	 * The table column <code>TEST.T_DATES.D</code>
	 */
	@javax.persistence.Column(name = "D", length = 7)
	public java.sql.Date getD();

	/**
	 * The table column <code>TEST.T_DATES.T</code>
	 */
	public void setT(java.sql.Timestamp value);

	/**
	 * The table column <code>TEST.T_DATES.T</code>
	 */
	@javax.persistence.Column(name = "T", length = 11)
	public java.sql.Timestamp getT();

	/**
	 * The table column <code>TEST.T_DATES.TS</code>
	 */
	public void setTS(java.sql.Timestamp value);

	/**
	 * The table column <code>TEST.T_DATES.TS</code>
	 */
	@javax.persistence.Column(name = "TS", length = 11)
	public java.sql.Timestamp getTS();

	/**
	 * The table column <code>TEST.T_DATES.D_INT</code>
	 */
	public void setD_INT(java.lang.Integer value);

	/**
	 * The table column <code>TEST.T_DATES.D_INT</code>
	 */
	@javax.persistence.Column(name = "D_INT", precision = 7)
	public java.lang.Integer getD_INT();

	/**
	 * The table column <code>TEST.T_DATES.TS_BIGINT</code>
	 */
	public void setTS_BIGINT(java.lang.Long value);

	/**
	 * The table column <code>TEST.T_DATES.TS_BIGINT</code>
	 */
	@javax.persistence.Column(name = "TS_BIGINT", precision = 18)
	public java.lang.Long getTS_BIGINT();

	/**
	 * The table column <code>TEST.T_DATES.I_Y</code>
	 */
	public void setI_Y(org.jooq.types.YearToMonth value);

	/**
	 * The table column <code>TEST.T_DATES.I_Y</code>
	 */
	@javax.persistence.Column(name = "I_Y", precision = 2)
	public org.jooq.types.YearToMonth getI_Y();

	/**
	 * The table column <code>TEST.T_DATES.I_D</code>
	 */
	public void setI_D(org.jooq.types.DayToSecond value);

	/**
	 * The table column <code>TEST.T_DATES.I_D</code>
	 */
	@javax.persistence.Column(name = "I_D", precision = 2, scale = 6)
	public org.jooq.types.DayToSecond getI_D();
}

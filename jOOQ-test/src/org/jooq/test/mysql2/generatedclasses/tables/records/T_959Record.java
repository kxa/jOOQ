/**
 * This class is generated by jOOQ
 */
package org.jooq.test.mysql2.generatedclasses.tables.records;

/**
 * This class is generated by jOOQ.
 */
@java.lang.SuppressWarnings("all")
@javax.persistence.Entity
@javax.persistence.Table(name = "t_959", schema = "test2")
public class T_959Record extends org.jooq.impl.TableRecordImpl<org.jooq.test.mysql2.generatedclasses.tables.records.T_959Record> implements org.jooq.Record1<org.jooq.test.mysql2.generatedclasses.enums.T_959JavaKeywords> {

	private static final long serialVersionUID = -710290876;

	/**
	 * Setter for <code>test2.t_959.java_keywords</code>. 
	 */
	public void setJavaKeywords(org.jooq.test.mysql2.generatedclasses.enums.T_959JavaKeywords value) {
		setValue(org.jooq.test.mysql2.generatedclasses.tables.T_959.T_959.JAVA_KEYWORDS, value);
	}

	/**
	 * Getter for <code>test2.t_959.java_keywords</code>. 
	 */
	@javax.persistence.Column(name = "java_keywords", length = 12)
	public org.jooq.test.mysql2.generatedclasses.enums.T_959JavaKeywords getJavaKeywords() {
		return getValue(org.jooq.test.mysql2.generatedclasses.tables.T_959.T_959.JAVA_KEYWORDS);
	}

	// -------------------------------------------------------------------------
	// Foreign key navigation methods
	// -------------------------------------------------------------------------

	// -------------------------------------------------------------------------
	// Record1 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row1<org.jooq.test.mysql2.generatedclasses.enums.T_959JavaKeywords> fieldsRow() {
		return org.jooq.impl.Factory.row(field1());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row1<org.jooq.test.mysql2.generatedclasses.enums.T_959JavaKeywords> valuesRow() {
		return org.jooq.impl.Factory.row(value1());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<org.jooq.test.mysql2.generatedclasses.enums.T_959JavaKeywords> field1() {
		return org.jooq.test.mysql2.generatedclasses.tables.T_959.T_959.JAVA_KEYWORDS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.test.mysql2.generatedclasses.enums.T_959JavaKeywords value1() {
		return getJavaKeywords();
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached T_959Record
	 */
	public T_959Record() {
		super(org.jooq.test.mysql2.generatedclasses.tables.T_959.T_959);
	}
}

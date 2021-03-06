/**
 * This class is generated by jOOQ
 */
package org.jooq.test.oracle.generatedclasses.multi_schema.tables.records;

/**
 * This class is generated by jOOQ.
 */
@java.lang.SuppressWarnings("all")
@javax.persistence.Entity
@javax.persistence.Table(name = "T_AUTHOR", schema = "MULTI_SCHEMA")
public class TAuthorRecord extends org.jooq.impl.UpdatableRecordImpl<org.jooq.test.oracle.generatedclasses.multi_schema.tables.records.TAuthorRecord> implements org.jooq.Record6<java.lang.Integer, java.lang.String, java.lang.String, java.sql.Date, java.lang.Integer, java.lang.Object> {

	private static final long serialVersionUID = 878357535;

	/**
	 * Setter for <code>MULTI_SCHEMA.T_AUTHOR.ID</code>. 
	 */
	public void setId(java.lang.Integer value) {
		setValue(org.jooq.test.oracle.generatedclasses.multi_schema.tables.TAuthor.T_AUTHOR.ID, value);
	}

	/**
	 * Getter for <code>MULTI_SCHEMA.T_AUTHOR.ID</code>. 
	 */
	@javax.persistence.Id
	@javax.persistence.Column(name = "ID", unique = true, nullable = false, precision = 7)
	public java.lang.Integer getId() {
		return getValue(org.jooq.test.oracle.generatedclasses.multi_schema.tables.TAuthor.T_AUTHOR.ID);
	}

	/**
	 * Setter for <code>MULTI_SCHEMA.T_AUTHOR.FIRST_NAME</code>. 
	 */
	public void setFirstName(java.lang.String value) {
		setValue(org.jooq.test.oracle.generatedclasses.multi_schema.tables.TAuthor.T_AUTHOR.FIRST_NAME, value);
	}

	/**
	 * Getter for <code>MULTI_SCHEMA.T_AUTHOR.FIRST_NAME</code>. 
	 */
	@javax.persistence.Column(name = "FIRST_NAME", length = 50)
	public java.lang.String getFirstName() {
		return getValue(org.jooq.test.oracle.generatedclasses.multi_schema.tables.TAuthor.T_AUTHOR.FIRST_NAME);
	}

	/**
	 * Setter for <code>MULTI_SCHEMA.T_AUTHOR.LAST_NAME</code>. 
	 */
	public void setLastName(java.lang.String value) {
		setValue(org.jooq.test.oracle.generatedclasses.multi_schema.tables.TAuthor.T_AUTHOR.LAST_NAME, value);
	}

	/**
	 * Getter for <code>MULTI_SCHEMA.T_AUTHOR.LAST_NAME</code>. 
	 */
	@javax.persistence.Column(name = "LAST_NAME", nullable = false, length = 50)
	public java.lang.String getLastName() {
		return getValue(org.jooq.test.oracle.generatedclasses.multi_schema.tables.TAuthor.T_AUTHOR.LAST_NAME);
	}

	/**
	 * Setter for <code>MULTI_SCHEMA.T_AUTHOR.DATE_OF_BIRTH</code>. 
	 */
	public void setDateOfBirth(java.sql.Date value) {
		setValue(org.jooq.test.oracle.generatedclasses.multi_schema.tables.TAuthor.T_AUTHOR.DATE_OF_BIRTH, value);
	}

	/**
	 * Getter for <code>MULTI_SCHEMA.T_AUTHOR.DATE_OF_BIRTH</code>. 
	 */
	@javax.persistence.Column(name = "DATE_OF_BIRTH", length = 7)
	public java.sql.Date getDateOfBirth() {
		return getValue(org.jooq.test.oracle.generatedclasses.multi_schema.tables.TAuthor.T_AUTHOR.DATE_OF_BIRTH);
	}

	/**
	 * Setter for <code>MULTI_SCHEMA.T_AUTHOR.YEAR_OF_BIRTH</code>. 
	 */
	public void setYearOfBirth(java.lang.Integer value) {
		setValue(org.jooq.test.oracle.generatedclasses.multi_schema.tables.TAuthor.T_AUTHOR.YEAR_OF_BIRTH, value);
	}

	/**
	 * Getter for <code>MULTI_SCHEMA.T_AUTHOR.YEAR_OF_BIRTH</code>. 
	 */
	@javax.persistence.Column(name = "YEAR_OF_BIRTH", precision = 7)
	public java.lang.Integer getYearOfBirth() {
		return getValue(org.jooq.test.oracle.generatedclasses.multi_schema.tables.TAuthor.T_AUTHOR.YEAR_OF_BIRTH);
	}

	/**
	 * Setter for <code>MULTI_SCHEMA.T_AUTHOR.ADDRESS</code>. 
	 */
	public void setAddress(java.lang.Object value) {
		setValue(org.jooq.test.oracle.generatedclasses.multi_schema.tables.TAuthor.T_AUTHOR.ADDRESS, value);
	}

	/**
	 * Getter for <code>MULTI_SCHEMA.T_AUTHOR.ADDRESS</code>. 
	 */
	@javax.persistence.Column(name = "ADDRESS", length = 1)
	public java.lang.Object getAddress() {
		return getValue(org.jooq.test.oracle.generatedclasses.multi_schema.tables.TAuthor.T_AUTHOR.ADDRESS);
	}

	// -------------------------------------------------------------------------
	// Foreign key navigation methods
	// -------------------------------------------------------------------------

	/**
	 * Fetch a list of <code>MULTI_SCHEMA.T_BOOK</code> referencing this <code>MULTI_SCHEMA.T_AUTHOR</code>
	 */
	public org.jooq.Result<org.jooq.test.oracle.generatedclasses.multi_schema.tables.records.TBookRecord> fetchTBookListByAuthorId() {
		return create()
			.selectFrom(org.jooq.test.oracle.generatedclasses.multi_schema.tables.TBook.T_BOOK)
			.where(org.jooq.test.oracle.generatedclasses.multi_schema.tables.TBook.T_BOOK.AUTHOR_ID.equal(getValue(org.jooq.test.oracle.generatedclasses.multi_schema.tables.TAuthor.T_AUTHOR.ID)))
			.fetch();
	}

	/**
	 * Fetch a list of <code>MULTI_SCHEMA.T_BOOK</code> referencing this <code>MULTI_SCHEMA.T_AUTHOR</code>
	 */
	public org.jooq.Result<org.jooq.test.oracle.generatedclasses.multi_schema.tables.records.TBookRecord> fetchTBookListByCoAuthorId() {
		return create()
			.selectFrom(org.jooq.test.oracle.generatedclasses.multi_schema.tables.TBook.T_BOOK)
			.where(org.jooq.test.oracle.generatedclasses.multi_schema.tables.TBook.T_BOOK.CO_AUTHOR_ID.equal(getValue(org.jooq.test.oracle.generatedclasses.multi_schema.tables.TAuthor.T_AUTHOR.ID)))
			.fetch();
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.Integer> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record6 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row6<java.lang.Integer, java.lang.String, java.lang.String, java.sql.Date, java.lang.Integer, java.lang.Object> fieldsRow() {
		return org.jooq.impl.Factory.row(field1(), field2(), field3(), field4(), field5(), field6());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row6<java.lang.Integer, java.lang.String, java.lang.String, java.sql.Date, java.lang.Integer, java.lang.Object> valuesRow() {
		return org.jooq.impl.Factory.row(value1(), value2(), value3(), value4(), value5(), value6());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1() {
		return org.jooq.test.oracle.generatedclasses.multi_schema.tables.TAuthor.T_AUTHOR.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return org.jooq.test.oracle.generatedclasses.multi_schema.tables.TAuthor.T_AUTHOR.FIRST_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field3() {
		return org.jooq.test.oracle.generatedclasses.multi_schema.tables.TAuthor.T_AUTHOR.LAST_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.sql.Date> field4() {
		return org.jooq.test.oracle.generatedclasses.multi_schema.tables.TAuthor.T_AUTHOR.DATE_OF_BIRTH;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field5() {
		return org.jooq.test.oracle.generatedclasses.multi_schema.tables.TAuthor.T_AUTHOR.YEAR_OF_BIRTH;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Object> field6() {
		return org.jooq.test.oracle.generatedclasses.multi_schema.tables.TAuthor.T_AUTHOR.ADDRESS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value2() {
		return getFirstName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value3() {
		return getLastName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.sql.Date value4() {
		return getDateOfBirth();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value5() {
		return getYearOfBirth();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Object value6() {
		return getAddress();
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached TAuthorRecord
	 */
	public TAuthorRecord() {
		super(org.jooq.test.oracle.generatedclasses.multi_schema.tables.TAuthor.T_AUTHOR);
	}
}

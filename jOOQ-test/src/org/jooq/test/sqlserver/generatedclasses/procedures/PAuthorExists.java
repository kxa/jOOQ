/**
 * This class is generated by jOOQ
 */
package org.jooq.test.sqlserver.generatedclasses.procedures;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = "http://jooq.sourceforge.net",
                            comments = "This class is generated by jOOQ")
public class PAuthorExists extends org.jooq.impl.StoredProcedureImpl {

	private static final long serialVersionUID = 188346073;


	/**
	 * An uncommented item
	 */
	public static final org.jooq.Parameter<java.lang.String> AUTHOR_NAME = new org.jooq.impl.ParameterImpl<java.lang.String>("author_name", org.jooq.impl.SQLDataType.VARCHAR);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.Parameter<java.lang.Integer> RESULT = new org.jooq.impl.ParameterImpl<java.lang.Integer>("result", org.jooq.impl.SQLDataType.INTEGER);

	/**
	 * Create a new procedure call instance
	 */
	public PAuthorExists() {
		super(org.jooq.SQLDialect.SQLSERVER, "p_author_exists", org.jooq.test.sqlserver.generatedclasses.Dbo.DBO);

		addInParameter(AUTHOR_NAME);
		addInOutParameter(RESULT);
	}

	public void setAuthorName(java.lang.String value) {
		setValue(AUTHOR_NAME, value);
	}

	public void setResult(java.lang.Integer value) {
		setValue(RESULT, value);
	}

	public java.lang.Integer getResult() {
		return getValue(RESULT);
	}
}
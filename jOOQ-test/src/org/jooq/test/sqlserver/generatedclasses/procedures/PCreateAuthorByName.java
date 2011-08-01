/**
 * This class is generated by jOOQ
 */
package org.jooq.test.sqlserver.generatedclasses.procedures;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = "http://jooq.sourceforge.net",
                            comments = "This class is generated by jOOQ")
public class PCreateAuthorByName extends org.jooq.impl.StoredProcedureImpl {

	private static final long serialVersionUID = 709417067;


	/**
	 * An uncommented item
	 */
	public static final org.jooq.Parameter<java.lang.String> FIRST_NAME = new org.jooq.impl.ParameterImpl<java.lang.String>("first_name", org.jooq.impl.SQLDataType.VARCHAR);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.Parameter<java.lang.String> LAST_NAME = new org.jooq.impl.ParameterImpl<java.lang.String>("last_name", org.jooq.impl.SQLDataType.VARCHAR);

	/**
	 * Create a new procedure call instance
	 */
	public PCreateAuthorByName() {
		super(org.jooq.SQLDialect.SQLSERVER, "p_create_author_by_name", org.jooq.test.sqlserver.generatedclasses.Dbo.DBO);

		addInParameter(FIRST_NAME);
		addInParameter(LAST_NAME);
	}

	public void setFirstName(java.lang.String value) {
		setValue(FIRST_NAME, value);
	}

	public void setLastName(java.lang.String value) {
		setValue(LAST_NAME, value);
	}
}
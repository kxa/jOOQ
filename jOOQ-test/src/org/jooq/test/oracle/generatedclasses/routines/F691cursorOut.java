/**
 * This class is generated by jOOQ
 */
package org.jooq.test.oracle.generatedclasses.routines;

/**
 * This class is generated by jOOQ.
 */
public class F691cursorOut extends org.jooq.impl.AbstractRoutine<org.jooq.Result<org.jooq.Record>> {

	private static final long serialVersionUID = 1769162574;


	/**
	 * An uncommented item
	 */
	public static final org.jooq.Parameter<org.jooq.Result<org.jooq.Record>> RETURN_VALUE = createParameter("RETURN_VALUE", org.jooq.impl.SQLDataType.RESULT);

	/**
	 * Create a new routine call instance
	 */
	public F691cursorOut() {
		super(org.jooq.SQLDialect.ORACLE, "F691CURSOR_OUT", org.jooq.test.oracle.generatedclasses.Test.TEST, org.jooq.impl.SQLDataType.RESULT);

		setReturnParameter(RETURN_VALUE);
	}
}
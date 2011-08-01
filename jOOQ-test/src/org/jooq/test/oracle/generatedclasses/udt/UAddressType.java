/**
 * This class is generated by jOOQ
 */
package org.jooq.test.oracle.generatedclasses.udt;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = "http://jooq.sourceforge.net",
                            comments = "This class is generated by jOOQ")
public class UAddressType extends org.jooq.impl.UDTImpl<org.jooq.test.oracle.generatedclasses.udt.records.UAddressTypeRecord> {

	private static final long serialVersionUID = 447103526;

	/**
	 * The singleton instance of U_ADDRESS_TYPE
	 */
	public static final org.jooq.test.oracle.generatedclasses.udt.UAddressType U_ADDRESS_TYPE = new org.jooq.test.oracle.generatedclasses.udt.UAddressType();

	/**
	 * The class holding records for this type
	 */
	private static final java.lang.Class<org.jooq.test.oracle.generatedclasses.udt.records.UAddressTypeRecord> __RECORD_TYPE = org.jooq.test.oracle.generatedclasses.udt.records.UAddressTypeRecord.class;

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<org.jooq.test.oracle.generatedclasses.udt.records.UAddressTypeRecord> getRecordType() {
		return __RECORD_TYPE;
	}

	/**
	 * An uncommented item
	 */
	public static final org.jooq.UDTField<org.jooq.test.oracle.generatedclasses.udt.records.UAddressTypeRecord, org.jooq.test.oracle.generatedclasses.udt.records.UStreetTypeRecord> STREET = new org.jooq.impl.UDTFieldImpl<org.jooq.test.oracle.generatedclasses.udt.records.UAddressTypeRecord, org.jooq.test.oracle.generatedclasses.udt.records.UStreetTypeRecord>("STREET", org.jooq.test.oracle.generatedclasses.udt.UStreetType.U_STREET_TYPE.getDataType(), U_ADDRESS_TYPE);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.UDTField<org.jooq.test.oracle.generatedclasses.udt.records.UAddressTypeRecord, java.lang.String> ZIP = new org.jooq.impl.UDTFieldImpl<org.jooq.test.oracle.generatedclasses.udt.records.UAddressTypeRecord, java.lang.String>("ZIP", org.jooq.impl.SQLDataType.VARCHAR, U_ADDRESS_TYPE);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.UDTField<org.jooq.test.oracle.generatedclasses.udt.records.UAddressTypeRecord, java.lang.String> CITY = new org.jooq.impl.UDTFieldImpl<org.jooq.test.oracle.generatedclasses.udt.records.UAddressTypeRecord, java.lang.String>("CITY", org.jooq.impl.SQLDataType.VARCHAR, U_ADDRESS_TYPE);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.UDTField<org.jooq.test.oracle.generatedclasses.udt.records.UAddressTypeRecord, java.lang.String> COUNTRY = new org.jooq.impl.UDTFieldImpl<org.jooq.test.oracle.generatedclasses.udt.records.UAddressTypeRecord, java.lang.String>("COUNTRY", org.jooq.impl.SQLDataType.VARCHAR, U_ADDRESS_TYPE);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.UDTField<org.jooq.test.oracle.generatedclasses.udt.records.UAddressTypeRecord, java.sql.Date> SINCE = new org.jooq.impl.UDTFieldImpl<org.jooq.test.oracle.generatedclasses.udt.records.UAddressTypeRecord, java.sql.Date>("SINCE", org.jooq.impl.SQLDataType.DATE, U_ADDRESS_TYPE);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.UDTField<org.jooq.test.oracle.generatedclasses.udt.records.UAddressTypeRecord, java.lang.Integer> CODE = new org.jooq.impl.UDTFieldImpl<org.jooq.test.oracle.generatedclasses.udt.records.UAddressTypeRecord, java.lang.Integer>("CODE", org.jooq.impl.SQLDataType.INTEGER, U_ADDRESS_TYPE);

	/**
	 * No further instances allowed
	 */
	private UAddressType() {
		super("U_ADDRESS_TYPE", org.jooq.test.oracle.generatedclasses.Test.TEST);
	}
}
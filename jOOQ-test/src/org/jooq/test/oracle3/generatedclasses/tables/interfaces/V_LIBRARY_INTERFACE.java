/**
 * This class is generated by jOOQ
 */
package org.jooq.test.oracle3.generatedclasses.tables.interfaces;

/**
 * This class is generated by jOOQ.
 */
@java.lang.SuppressWarnings("all")
@javax.persistence.Entity
@javax.persistence.Table(name = "V_LIBRARY", schema = "TEST")
public interface V_LIBRARY_INTERFACE extends java.io.Serializable, java.lang.Cloneable {

	/**
	 * The table column <code>TEST.V_LIBRARY.AUTHOR</code>
	 */
	public void setAUTHOR(java.lang.String value);

	/**
	 * The table column <code>TEST.V_LIBRARY.AUTHOR</code>
	 */
	@javax.persistence.Column(name = "AUTHOR", length = 101)
	public java.lang.String getAUTHOR();

	/**
	 * The table column <code>TEST.V_LIBRARY.TITLE</code>
	 */
	public void setTITLE(java.lang.String value);

	/**
	 * The table column <code>TEST.V_LIBRARY.TITLE</code>
	 */
	@javax.persistence.Column(name = "TITLE", nullable = false, length = 400)
	public java.lang.String getTITLE();
}

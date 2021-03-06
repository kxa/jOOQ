/**
 * This class is generated by jOOQ
 */
package org.jooq.examples.mysql.sakila.tables.records;

/**
 * This class is generated by jOOQ.
 */
@java.lang.SuppressWarnings("all")
public class FilmRecord extends org.jooq.impl.UpdatableRecordImpl<org.jooq.examples.mysql.sakila.tables.records.FilmRecord> implements org.jooq.Record13<java.lang.Short, java.lang.String, java.lang.String, java.sql.Date, java.lang.Byte, java.lang.Byte, java.lang.Byte, java.math.BigDecimal, java.lang.Short, java.math.BigDecimal, org.jooq.examples.mysql.sakila.enums.FilmRating, java.lang.String, java.sql.Timestamp> {

	private static final long serialVersionUID = 1114244180;

	/**
	 * Setter for <code>sakila.film.film_id</code>. 
	 */
	public void setFilmId(java.lang.Short value) {
		setValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.FILM_ID, value);
	}

	/**
	 * Getter for <code>sakila.film.film_id</code>. 
	 */
	public java.lang.Short getFilmId() {
		return getValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.FILM_ID);
	}

	/**
	 * Setter for <code>sakila.film.title</code>. 
	 */
	public void setTitle(java.lang.String value) {
		setValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.TITLE, value);
	}

	/**
	 * Getter for <code>sakila.film.title</code>. 
	 */
	public java.lang.String getTitle() {
		return getValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.TITLE);
	}

	/**
	 * Setter for <code>sakila.film.description</code>. 
	 */
	public void setDescription(java.lang.String value) {
		setValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.DESCRIPTION, value);
	}

	/**
	 * Getter for <code>sakila.film.description</code>. 
	 */
	public java.lang.String getDescription() {
		return getValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.DESCRIPTION);
	}

	/**
	 * Setter for <code>sakila.film.release_year</code>. 
	 */
	public void setReleaseYear(java.sql.Date value) {
		setValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.RELEASE_YEAR, value);
	}

	/**
	 * Getter for <code>sakila.film.release_year</code>. 
	 */
	public java.sql.Date getReleaseYear() {
		return getValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.RELEASE_YEAR);
	}

	/**
	 * Setter for <code>sakila.film.language_id</code>. 
	 */
	public void setLanguageId(java.lang.Byte value) {
		setValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.LANGUAGE_ID, value);
	}

	/**
	 * Link this record to a given {@link org.jooq.examples.mysql.sakila.tables.records.LanguageRecord}
	 */
	public void setLanguageId(org.jooq.examples.mysql.sakila.tables.records.LanguageRecord value) {
		if (value == null) {
			setValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.LANGUAGE_ID, null);
		}
		else {
			setValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.LANGUAGE_ID, value.getValue(org.jooq.examples.mysql.sakila.tables.Language.LANGUAGE.LANGUAGE_ID));
		}
	}

	/**
	 * Getter for <code>sakila.film.language_id</code>. 
	 */
	public java.lang.Byte getLanguageId() {
		return getValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.LANGUAGE_ID);
	}

	/**
	 * Setter for <code>sakila.film.original_language_id</code>. 
	 */
	public void setOriginalLanguageId(java.lang.Byte value) {
		setValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.ORIGINAL_LANGUAGE_ID, value);
	}

	/**
	 * Link this record to a given {@link org.jooq.examples.mysql.sakila.tables.records.LanguageRecord}
	 */
	public void setOriginalLanguageId(org.jooq.examples.mysql.sakila.tables.records.LanguageRecord value) {
		if (value == null) {
			setValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.ORIGINAL_LANGUAGE_ID, null);
		}
		else {
			setValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.ORIGINAL_LANGUAGE_ID, value.getValue(org.jooq.examples.mysql.sakila.tables.Language.LANGUAGE.LANGUAGE_ID));
		}
	}

	/**
	 * Getter for <code>sakila.film.original_language_id</code>. 
	 */
	public java.lang.Byte getOriginalLanguageId() {
		return getValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.ORIGINAL_LANGUAGE_ID);
	}

	/**
	 * Setter for <code>sakila.film.rental_duration</code>. 
	 */
	public void setRentalDuration(java.lang.Byte value) {
		setValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.RENTAL_DURATION, value);
	}

	/**
	 * Getter for <code>sakila.film.rental_duration</code>. 
	 */
	public java.lang.Byte getRentalDuration() {
		return getValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.RENTAL_DURATION);
	}

	/**
	 * Setter for <code>sakila.film.rental_rate</code>. 
	 */
	public void setRentalRate(java.math.BigDecimal value) {
		setValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.RENTAL_RATE, value);
	}

	/**
	 * Getter for <code>sakila.film.rental_rate</code>. 
	 */
	public java.math.BigDecimal getRentalRate() {
		return getValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.RENTAL_RATE);
	}

	/**
	 * Setter for <code>sakila.film.length</code>. 
	 */
	public void setLength(java.lang.Short value) {
		setValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.LENGTH, value);
	}

	/**
	 * Getter for <code>sakila.film.length</code>. 
	 */
	public java.lang.Short getLength() {
		return getValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.LENGTH);
	}

	/**
	 * Setter for <code>sakila.film.replacement_cost</code>. 
	 */
	public void setReplacementCost(java.math.BigDecimal value) {
		setValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.REPLACEMENT_COST, value);
	}

	/**
	 * Getter for <code>sakila.film.replacement_cost</code>. 
	 */
	public java.math.BigDecimal getReplacementCost() {
		return getValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.REPLACEMENT_COST);
	}

	/**
	 * Setter for <code>sakila.film.rating</code>. 
	 */
	public void setRating(org.jooq.examples.mysql.sakila.enums.FilmRating value) {
		setValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.RATING, value);
	}

	/**
	 * Getter for <code>sakila.film.rating</code>. 
	 */
	public org.jooq.examples.mysql.sakila.enums.FilmRating getRating() {
		return getValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.RATING);
	}

	/**
	 * Setter for <code>sakila.film.special_features</code>. 
	 */
	public void setSpecialFeatures(java.lang.String value) {
		setValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.SPECIAL_FEATURES, value);
	}

	/**
	 * Getter for <code>sakila.film.special_features</code>. 
	 */
	public java.lang.String getSpecialFeatures() {
		return getValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.SPECIAL_FEATURES);
	}

	/**
	 * Setter for <code>sakila.film.last_update</code>. 
	 */
	public void setLastUpdate(java.sql.Timestamp value) {
		setValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.LAST_UPDATE, value);
	}

	/**
	 * Getter for <code>sakila.film.last_update</code>. 
	 */
	public java.sql.Timestamp getLastUpdate() {
		return getValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.LAST_UPDATE);
	}

	// -------------------------------------------------------------------------
	// Foreign key navigation methods
	// -------------------------------------------------------------------------

	/**
	 * Fetch a list of <code>sakila.film_actor</code> referencing this <code>sakila.film</code>
	 */
	public org.jooq.Result<org.jooq.examples.mysql.sakila.tables.records.FilmActorRecord> fetchFilmActorList() {
		return create()
			.selectFrom(org.jooq.examples.mysql.sakila.tables.FilmActor.FILM_ACTOR)
			.where(org.jooq.examples.mysql.sakila.tables.FilmActor.FILM_ACTOR.FILM_ID.equal(getValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.FILM_ID)))
			.fetch();
	}

	/**
	 * Fetch a list of <code>sakila.film_category</code> referencing this <code>sakila.film</code>
	 */
	public org.jooq.Result<org.jooq.examples.mysql.sakila.tables.records.FilmCategoryRecord> fetchFilmCategoryList() {
		return create()
			.selectFrom(org.jooq.examples.mysql.sakila.tables.FilmCategory.FILM_CATEGORY)
			.where(org.jooq.examples.mysql.sakila.tables.FilmCategory.FILM_CATEGORY.FILM_ID.equal(getValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.FILM_ID)))
			.fetch();
	}

	/**
	 * Fetch a list of <code>sakila.inventory</code> referencing this <code>sakila.film</code>
	 */
	public org.jooq.Result<org.jooq.examples.mysql.sakila.tables.records.InventoryRecord> fetchInventoryList() {
		return create()
			.selectFrom(org.jooq.examples.mysql.sakila.tables.Inventory.INVENTORY)
			.where(org.jooq.examples.mysql.sakila.tables.Inventory.INVENTORY.FILM_ID.equal(getValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.FILM_ID)))
			.fetch();
	}

	/**
	 * Fetch a <code>sakila.language</code> referenced by this <code>sakila.film</code>
	 */
	public org.jooq.examples.mysql.sakila.tables.records.LanguageRecord fetchLanguageByLanguageId() {
		return create()
			.selectFrom(org.jooq.examples.mysql.sakila.tables.Language.LANGUAGE)
			.where(org.jooq.examples.mysql.sakila.tables.Language.LANGUAGE.LANGUAGE_ID.equal(getValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.LANGUAGE_ID)))
			.fetchOne();
	}

	/**
	 * Fetch a <code>sakila.language</code> referenced by this <code>sakila.film</code>
	 */
	public org.jooq.examples.mysql.sakila.tables.records.LanguageRecord fetchLanguageByOriginalLanguageId() {
		return create()
			.selectFrom(org.jooq.examples.mysql.sakila.tables.Language.LANGUAGE)
			.where(org.jooq.examples.mysql.sakila.tables.Language.LANGUAGE.LANGUAGE_ID.equal(getValue(org.jooq.examples.mysql.sakila.tables.Film.FILM.ORIGINAL_LANGUAGE_ID)))
			.fetchOne();
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.Short> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record13 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row13<java.lang.Short, java.lang.String, java.lang.String, java.sql.Date, java.lang.Byte, java.lang.Byte, java.lang.Byte, java.math.BigDecimal, java.lang.Short, java.math.BigDecimal, org.jooq.examples.mysql.sakila.enums.FilmRating, java.lang.String, java.sql.Timestamp> fieldsRow() {
		return org.jooq.impl.Factory.row(field1(), field2(), field3(), field4(), field5(), field6(), field7(), field8(), field9(), field10(), field11(), field12(), field13());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row13<java.lang.Short, java.lang.String, java.lang.String, java.sql.Date, java.lang.Byte, java.lang.Byte, java.lang.Byte, java.math.BigDecimal, java.lang.Short, java.math.BigDecimal, org.jooq.examples.mysql.sakila.enums.FilmRating, java.lang.String, java.sql.Timestamp> valuesRow() {
		return org.jooq.impl.Factory.row(value1(), value2(), value3(), value4(), value5(), value6(), value7(), value8(), value9(), value10(), value11(), value12(), value13());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Short> field1() {
		return org.jooq.examples.mysql.sakila.tables.Film.FILM.FILM_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return org.jooq.examples.mysql.sakila.tables.Film.FILM.TITLE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field3() {
		return org.jooq.examples.mysql.sakila.tables.Film.FILM.DESCRIPTION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.sql.Date> field4() {
		return org.jooq.examples.mysql.sakila.tables.Film.FILM.RELEASE_YEAR;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Byte> field5() {
		return org.jooq.examples.mysql.sakila.tables.Film.FILM.LANGUAGE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Byte> field6() {
		return org.jooq.examples.mysql.sakila.tables.Film.FILM.ORIGINAL_LANGUAGE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Byte> field7() {
		return org.jooq.examples.mysql.sakila.tables.Film.FILM.RENTAL_DURATION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.math.BigDecimal> field8() {
		return org.jooq.examples.mysql.sakila.tables.Film.FILM.RENTAL_RATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Short> field9() {
		return org.jooq.examples.mysql.sakila.tables.Film.FILM.LENGTH;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.math.BigDecimal> field10() {
		return org.jooq.examples.mysql.sakila.tables.Film.FILM.REPLACEMENT_COST;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<org.jooq.examples.mysql.sakila.enums.FilmRating> field11() {
		return org.jooq.examples.mysql.sakila.tables.Film.FILM.RATING;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field12() {
		return org.jooq.examples.mysql.sakila.tables.Film.FILM.SPECIAL_FEATURES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.sql.Timestamp> field13() {
		return org.jooq.examples.mysql.sakila.tables.Film.FILM.LAST_UPDATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Short value1() {
		return getFilmId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value2() {
		return getTitle();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value3() {
		return getDescription();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.sql.Date value4() {
		return getReleaseYear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Byte value5() {
		return getLanguageId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Byte value6() {
		return getOriginalLanguageId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Byte value7() {
		return getRentalDuration();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.math.BigDecimal value8() {
		return getRentalRate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Short value9() {
		return getLength();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.math.BigDecimal value10() {
		return getReplacementCost();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.examples.mysql.sakila.enums.FilmRating value11() {
		return getRating();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value12() {
		return getSpecialFeatures();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.sql.Timestamp value13() {
		return getLastUpdate();
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached FilmRecord
	 */
	public FilmRecord() {
		super(org.jooq.examples.mysql.sakila.tables.Film.FILM);
	}
}

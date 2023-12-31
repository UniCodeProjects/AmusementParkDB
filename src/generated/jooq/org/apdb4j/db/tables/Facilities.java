/*
 * This file is generated by jOOQ.
 */
package org.apdb4j.db.tables;


import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.apdb4j.db.AmusementPark;
import org.apdb4j.db.Keys;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Facilities extends TableImpl<Record> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>amusement_park.facilities</code>
     */
    public static final Facilities FACILITIES = new Facilities();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<Record> getRecordType() {
        return Record.class;
    }

    /**
     * The column <code>amusement_park.facilities.FacilityID</code>.
     */
    public final TableField<Record, String> FACILITYID = createField(DSL.name("FacilityID"), SQLDataType.CHAR(6).nullable(false), this, "");

    /**
     * The column <code>amusement_park.facilities.Name</code>.
     */
    public final TableField<Record, String> NAME = createField(DSL.name("Name"), SQLDataType.VARCHAR(256).nullable(false), this, "");

    /**
     * The column <code>amusement_park.facilities.OpeningTime</code>.
     */
    public final TableField<Record, LocalTime> OPENINGTIME = createField(DSL.name("OpeningTime"), SQLDataType.LOCALTIME.nullable(false), this, "");

    /**
     * The column <code>amusement_park.facilities.ClosingTime</code>.
     */
    public final TableField<Record, LocalTime> CLOSINGTIME = createField(DSL.name("ClosingTime"), SQLDataType.LOCALTIME.nullable(false), this, "");

    /**
     * The column <code>amusement_park.facilities.AvgRating</code>.
     */
    public final TableField<Record, BigDecimal> AVGRATING = createField(DSL.name("AvgRating"), SQLDataType.DECIMAL(2, 1).nullable(false), this, "");

    /**
     * The column <code>amusement_park.facilities.NumReviews</code>.
     */
    public final TableField<Record, Integer> NUMREVIEWS = createField(DSL.name("NumReviews"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>amusement_park.facilities.Type</code>.
     */
    public final TableField<Record, String> TYPE = createField(DSL.name("Type"), SQLDataType.VARCHAR(256).nullable(false), this, "");

    /**
     * The column <code>amusement_park.facilities.IsShop</code>.
     */
    public final TableField<Record, Byte> ISSHOP = createField(DSL.name("IsShop"), SQLDataType.TINYINT.nullable(false), this, "");

    private Facilities(Name alias, Table<Record> aliased) {
        this(alias, aliased, null);
    }

    private Facilities(Name alias, Table<Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>amusement_park.facilities</code> table reference
     */
    public Facilities(String alias) {
        this(DSL.name(alias), FACILITIES);
    }

    /**
     * Create an aliased <code>amusement_park.facilities</code> table reference
     */
    public Facilities(Name alias) {
        this(alias, FACILITIES);
    }

    /**
     * Create a <code>amusement_park.facilities</code> table reference
     */
    public Facilities() {
        this(DSL.name("facilities"), null);
    }

    public <O extends Record> Facilities(Table<O> child, ForeignKey<O, Record> key) {
        super(child, key, FACILITIES);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : AmusementPark.AMUSEMENT_PARK;
    }

    @Override
    public UniqueKey<Record> getPrimaryKey() {
        return Keys.KEY_FACILITIES_PRIMARY;
    }

    @Override
    public List<UniqueKey<Record>> getUniqueKeys() {
        return Arrays.asList(Keys.KEY_FACILITIES_IDFACILITY);
    }

    @Override
    public Facilities as(String alias) {
        return new Facilities(DSL.name(alias), this);
    }

    @Override
    public Facilities as(Name alias) {
        return new Facilities(alias, this);
    }

    @Override
    public Facilities as(Table<?> alias) {
        return new Facilities(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Facilities rename(String name) {
        return new Facilities(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Facilities rename(Name name) {
        return new Facilities(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Facilities rename(Table<?> name) {
        return new Facilities(name.getQualifiedName(), null);
    }
}

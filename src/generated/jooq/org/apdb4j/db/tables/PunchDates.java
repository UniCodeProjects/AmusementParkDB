/*
 * This file is generated by jOOQ.
 */
package org.apdb4j.db.tables;


import java.time.LocalDate;

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
public class PunchDates extends TableImpl<Record> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>amusement_park.punch_dates</code>
     */
    public static final PunchDates PUNCH_DATES = new PunchDates();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<Record> getRecordType() {
        return Record.class;
    }

    /**
     * The column <code>amusement_park.punch_dates.Date</code>.
     */
    public final TableField<Record, LocalDate> DATE = createField(DSL.name("Date"), SQLDataType.LOCALDATE.nullable(false), this, "");

    private PunchDates(Name alias, Table<Record> aliased) {
        this(alias, aliased, null);
    }

    private PunchDates(Name alias, Table<Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>amusement_park.punch_dates</code> table reference
     */
    public PunchDates(String alias) {
        this(DSL.name(alias), PUNCH_DATES);
    }

    /**
     * Create an aliased <code>amusement_park.punch_dates</code> table reference
     */
    public PunchDates(Name alias) {
        this(alias, PUNCH_DATES);
    }

    /**
     * Create a <code>amusement_park.punch_dates</code> table reference
     */
    public PunchDates() {
        this(DSL.name("punch_dates"), null);
    }

    public <O extends Record> PunchDates(Table<O> child, ForeignKey<O, Record> key) {
        super(child, key, PUNCH_DATES);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : AmusementPark.AMUSEMENT_PARK;
    }

    @Override
    public UniqueKey<Record> getPrimaryKey() {
        return Keys.KEY_PUNCH_DATES_PRIMARY;
    }

    @Override
    public PunchDates as(String alias) {
        return new PunchDates(DSL.name(alias), this);
    }

    @Override
    public PunchDates as(Name alias) {
        return new PunchDates(alias, this);
    }

    @Override
    public PunchDates as(Table<?> alias) {
        return new PunchDates(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public PunchDates rename(String name) {
        return new PunchDates(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public PunchDates rename(Name name) {
        return new PunchDates(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public PunchDates rename(Table<?> name) {
        return new PunchDates(name.getQualifiedName(), null);
    }
}

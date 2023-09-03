/*
 * This file is generated by jOOQ.
 */
package org.apdb4j.db.tables;


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
public class Picture extends TableImpl<Record> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>amusement_park.picture</code>
     */
    public static final Picture PICTURE = new Picture();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<Record> getRecordType() {
        return Record.class;
    }

    /**
     * The column <code>amusement_park.picture.Path</code>.
     */
    public final TableField<Record, String> PATH = createField(DSL.name("Path"), SQLDataType.VARCHAR(256).nullable(false), this, "");

    /**
     * The column <code>amusement_park.picture.FacilityID</code>.
     */
    public final TableField<Record, String> FACILITYID = createField(DSL.name("FacilityID"), SQLDataType.CHAR(6).nullable(false), this, "");

    private Picture(Name alias, Table<Record> aliased) {
        this(alias, aliased, null);
    }

    private Picture(Name alias, Table<Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>amusement_park.picture</code> table reference
     */
    public Picture(String alias) {
        this(DSL.name(alias), PICTURE);
    }

    /**
     * Create an aliased <code>amusement_park.picture</code> table reference
     */
    public Picture(Name alias) {
        this(alias, PICTURE);
    }

    /**
     * Create a <code>amusement_park.picture</code> table reference
     */
    public Picture() {
        this(DSL.name("picture"), null);
    }

    public <O extends Record> Picture(Table<O> child, ForeignKey<O, Record> key) {
        super(child, key, PICTURE);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : AmusementPark.AMUSEMENT_PARK;
    }

    @Override
    public UniqueKey<Record> getPrimaryKey() {
        return Keys.KEY_PICTURE_PRIMARY;
    }

    @Override
    public List<ForeignKey<Record, ?>> getReferences() {
        return Arrays.asList(Keys.FKREPRESENT);
    }

    private transient Facility _facility;

    /**
     * Get the implicit join path to the <code>amusement_park.facility</code>
     * table.
     */
    public Facility facility() {
        if (_facility == null)
            _facility = new Facility(this, Keys.FKREPRESENT);

        return _facility;
    }

    @Override
    public Picture as(String alias) {
        return new Picture(DSL.name(alias), this);
    }

    @Override
    public Picture as(Name alias) {
        return new Picture(alias, this);
    }

    @Override
    public Picture as(Table<?> alias) {
        return new Picture(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Picture rename(String name) {
        return new Picture(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Picture rename(Name name) {
        return new Picture(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Picture rename(Table<?> name) {
        return new Picture(name.getQualifiedName(), null);
    }
}

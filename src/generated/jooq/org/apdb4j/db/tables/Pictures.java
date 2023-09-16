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
public class Pictures extends TableImpl<Record> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>amusement_park.pictures</code>
     */
    public static final Pictures PICTURES = new Pictures();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<Record> getRecordType() {
        return Record.class;
    }

    /**
     * The column <code>amusement_park.pictures.Path</code>.
     */
    public final TableField<Record, String> PATH = createField(DSL.name("Path"), SQLDataType.VARCHAR(256).nullable(false), this, "");

    /**
     * The column <code>amusement_park.pictures.ParkServiceID</code>.
     */
    public final TableField<Record, String> PARKSERVICEID = createField(DSL.name("ParkServiceID"), SQLDataType.CHAR(6).nullable(false), this, "");

    private Pictures(Name alias, Table<Record> aliased) {
        this(alias, aliased, null);
    }

    private Pictures(Name alias, Table<Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>amusement_park.pictures</code> table reference
     */
    public Pictures(String alias) {
        this(DSL.name(alias), PICTURES);
    }

    /**
     * Create an aliased <code>amusement_park.pictures</code> table reference
     */
    public Pictures(Name alias) {
        this(alias, PICTURES);
    }

    /**
     * Create a <code>amusement_park.pictures</code> table reference
     */
    public Pictures() {
        this(DSL.name("pictures"), null);
    }

    public <O extends Record> Pictures(Table<O> child, ForeignKey<O, Record> key) {
        super(child, key, PICTURES);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : AmusementPark.AMUSEMENT_PARK;
    }

    @Override
    public UniqueKey<Record> getPrimaryKey() {
        return Keys.KEY_PICTURES_PRIMARY;
    }

    @Override
    public List<ForeignKey<Record, ?>> getReferences() {
        return Arrays.asList(Keys.FKREPRESENT);
    }

    private transient ParkServices _parkServices;

    /**
     * Get the implicit join path to the
     * <code>amusement_park.park_services</code> table.
     */
    public ParkServices parkServices() {
        if (_parkServices == null)
            _parkServices = new ParkServices(this, Keys.FKREPRESENT);

        return _parkServices;
    }

    @Override
    public Pictures as(String alias) {
        return new Pictures(DSL.name(alias), this);
    }

    @Override
    public Pictures as(Name alias) {
        return new Pictures(alias, this);
    }

    @Override
    public Pictures as(Table<?> alias) {
        return new Pictures(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Pictures rename(String name) {
        return new Pictures(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Pictures rename(Name name) {
        return new Pictures(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Pictures rename(Table<?> name) {
        return new Pictures(name.getQualifiedName(), null);
    }
}

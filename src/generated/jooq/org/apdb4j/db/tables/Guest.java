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
public class Guest extends TableImpl<Record> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>amusement_park.guest</code>
     */
    public static final Guest GUEST = new Guest();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<Record> getRecordType() {
        return Record.class;
    }

    /**
     * The column <code>amusement_park.guest.GuestID</code>.
     */
    public final TableField<Record, String> GUESTID = createField(DSL.name("GuestID"), SQLDataType.CHAR(72).nullable(false), this, "");

    /**
     * The column <code>amusement_park.guest.Email</code>.
     */
    public final TableField<Record, String> EMAIL = createField(DSL.name("Email"), SQLDataType.VARCHAR(256).nullable(false), this, "");

    /**
     * The column <code>amusement_park.guest.Name</code>.
     */
    public final TableField<Record, String> NAME = createField(DSL.name("Name"), SQLDataType.VARCHAR(256).nullable(false), this, "");

    /**
     * The column <code>amusement_park.guest.Surname</code>.
     */
    public final TableField<Record, String> SURNAME = createField(DSL.name("Surname"), SQLDataType.VARCHAR(256).nullable(false), this, "");

    private Guest(Name alias, Table<Record> aliased) {
        this(alias, aliased, null);
    }

    private Guest(Name alias, Table<Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>amusement_park.guest</code> table reference
     */
    public Guest(String alias) {
        this(DSL.name(alias), GUEST);
    }

    /**
     * Create an aliased <code>amusement_park.guest</code> table reference
     */
    public Guest(Name alias) {
        this(alias, GUEST);
    }

    /**
     * Create a <code>amusement_park.guest</code> table reference
     */
    public Guest() {
        this(DSL.name("guest"), null);
    }

    public <O extends Record> Guest(Table<O> child, ForeignKey<O, Record> key) {
        super(child, key, GUEST);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : AmusementPark.AMUSEMENT_PARK;
    }

    @Override
    public UniqueKey<Record> getPrimaryKey() {
        return Keys.KEY_GUEST_PRIMARY;
    }

    @Override
    public List<UniqueKey<Record>> getUniqueKeys() {
        return Arrays.asList(Keys.KEY_GUEST_FKGUEST_OWNERSHIP_ID);
    }

    @Override
    public List<ForeignKey<Record, ?>> getReferences() {
        return Arrays.asList(Keys.FKGUEST_OWNERSHIP_FK);
    }

    private transient Account _account;

    /**
     * Get the implicit join path to the <code>amusement_park.account</code>
     * table.
     */
    public Account account() {
        if (_account == null)
            _account = new Account(this, Keys.FKGUEST_OWNERSHIP_FK);

        return _account;
    }

    @Override
    public Guest as(String alias) {
        return new Guest(DSL.name(alias), this);
    }

    @Override
    public Guest as(Name alias) {
        return new Guest(alias, this);
    }

    @Override
    public Guest as(Table<?> alias) {
        return new Guest(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Guest rename(String name) {
        return new Guest(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Guest rename(Name name) {
        return new Guest(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Guest rename(Table<?> name) {
        return new Guest(name.getQualifiedName(), null);
    }
}

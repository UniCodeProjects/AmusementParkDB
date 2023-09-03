/*
 * This file is generated by jOOQ.
 */
package org.apdb4j.db.tables;


import java.math.BigDecimal;
import java.time.LocalDate;
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
public class Contract extends TableImpl<Record> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>amusement_park.contract</code>
     */
    public static final Contract CONTRACT = new Contract();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<Record> getRecordType() {
        return Record.class;
    }

    /**
     * The column <code>amusement_park.contract.ContractID</code>.
     */
    public final TableField<Record, String> CONTRACTID = createField(DSL.name("ContractID"), SQLDataType.CHAR(65).nullable(false), this, "");

    /**
     * The column <code>amusement_park.contract.BeginDate</code>.
     */
    public final TableField<Record, LocalDate> BEGINDATE = createField(DSL.name("BeginDate"), SQLDataType.LOCALDATE.nullable(false), this, "");

    /**
     * The column <code>amusement_park.contract.EndDate</code>.
     */
    public final TableField<Record, LocalDate> ENDDATE = createField(DSL.name("EndDate"), SQLDataType.LOCALDATE, this, "");

    /**
     * The column <code>amusement_park.contract.Salary</code>.
     */
    public final TableField<Record, BigDecimal> SALARY = createField(DSL.name("Salary"), SQLDataType.DECIMAL(7, 2).nullable(false), this, "");

    /**
     * The column <code>amusement_park.contract.EmployerNID</code>.
     */
    public final TableField<Record, String> EMPLOYERNID = createField(DSL.name("EmployerNID"), SQLDataType.VARCHAR(256).nullable(false), this, "");

    /**
     * The column <code>amusement_park.contract.EmployeeNID</code>.
     */
    public final TableField<Record, String> EMPLOYEENID = createField(DSL.name("EmployeeNID"), SQLDataType.VARCHAR(256).nullable(false), this, "");

    private Contract(Name alias, Table<Record> aliased) {
        this(alias, aliased, null);
    }

    private Contract(Name alias, Table<Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>amusement_park.contract</code> table reference
     */
    public Contract(String alias) {
        this(DSL.name(alias), CONTRACT);
    }

    /**
     * Create an aliased <code>amusement_park.contract</code> table reference
     */
    public Contract(Name alias) {
        this(alias, CONTRACT);
    }

    /**
     * Create a <code>amusement_park.contract</code> table reference
     */
    public Contract() {
        this(DSL.name("contract"), null);
    }

    public <O extends Record> Contract(Table<O> child, ForeignKey<O, Record> key) {
        super(child, key, CONTRACT);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : AmusementPark.AMUSEMENT_PARK;
    }

    @Override
    public UniqueKey<Record> getPrimaryKey() {
        return Keys.KEY_CONTRACT_PRIMARY;
    }

    @Override
    public List<ForeignKey<Record, ?>> getReferences() {
        return Arrays.asList(Keys.FKHIRING, Keys.FKEMPLOYEMENT);
    }

    private transient Staff _fkhiring;
    private transient Staff _fkemployement;

    /**
     * Get the implicit join path to the <code>amusement_park.staff</code>
     * table, via the <code>FKhiring</code> key.
     */
    public Staff fkhiring() {
        if (_fkhiring == null)
            _fkhiring = new Staff(this, Keys.FKHIRING);

        return _fkhiring;
    }

    /**
     * Get the implicit join path to the <code>amusement_park.staff</code>
     * table, via the <code>FKemployement</code> key.
     */
    public Staff fkemployement() {
        if (_fkemployement == null)
            _fkemployement = new Staff(this, Keys.FKEMPLOYEMENT);

        return _fkemployement;
    }

    @Override
    public Contract as(String alias) {
        return new Contract(DSL.name(alias), this);
    }

    @Override
    public Contract as(Name alias) {
        return new Contract(alias, this);
    }

    @Override
    public Contract as(Table<?> alias) {
        return new Contract(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Contract rename(String name) {
        return new Contract(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Contract rename(Name name) {
        return new Contract(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Contract rename(Table<?> name) {
        return new Contract(name.getQualifiedName(), null);
    }
}

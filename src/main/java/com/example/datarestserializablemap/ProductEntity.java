package com.example.datarestserializablemap;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import java.io.Serializable;
import java.util.Map;

/**
 * @author Petar Tahchiev
 * @since 1.5
 */
@Entity
public class ProductEntity {

    public static final String NAME = "product";

    @Id
    private Long id;

    @ElementCollection(targetClass = Serializable.class)
    @CollectionTable(name = (ProductEntity.NAME + "_dynamic_attribute"), joinColumns = { @JoinColumn(name = (ProductEntity.NAME + "_id")) }, indexes = {
                    @Index(name = (("idx_" + ProductEntity.NAME) + "_dynamic_attribute"), columnList = (ProductEntity.NAME
                                    + "_id")) }, foreignKey = @ForeignKey(name = (("fk_" + ProductEntity.NAME) + "_dynamic_attribute")))
    @MapKeyColumn(name = "code")
    @JsonDeserialize(contentUsing = StringDeserializer.class, contentAs = String.class)
    private Map<String, Serializable> dynamicAttribute;

    /* getters/setters */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, Serializable> getDynamicAttribute() {
        return dynamicAttribute;
    }

    public void setDynamicAttribute(Map<String, Serializable> dynamicAttribute) {
        this.dynamicAttribute = dynamicAttribute;
    }
}

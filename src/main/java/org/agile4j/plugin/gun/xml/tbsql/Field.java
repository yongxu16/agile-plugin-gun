//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.10.14 at 11:23:31 PM CST 
//


package org.agile4j.plugin.gun.xml.tbsql;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="allowSubType" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="final" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="identity" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="longname" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="nullable" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="primarykey" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="ref" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "field")
public class Field {

    @XmlAttribute(name = "allowSubType", required = true)
    protected boolean allowSubType;
    @XmlAttribute(name = "final", required = true)
    protected boolean _final;
    @XmlAttribute(name = "id", required = true)
    protected String id;
    @XmlAttribute(name = "identity", required = true)
    protected boolean identity;
    @XmlAttribute(name = "longname", required = true)
    protected String longname;
    @XmlAttribute(name = "nullable", required = true)
    protected boolean nullable;
    @XmlAttribute(name = "primarykey", required = true)
    protected boolean primarykey;
    @XmlAttribute(name = "ref", required = true)
    protected String ref;
    @XmlAttribute(name = "type", required = true)
    protected String type;

    /**
     * Gets the value of the allowSubType property.
     * 
     */
    public boolean isAllowSubType() {
        return allowSubType;
    }

    /**
     * Sets the value of the allowSubType property.
     * 
     */
    public void setAllowSubType(boolean value) {
        this.allowSubType = value;
    }

    /**
     * Gets the value of the final property.
     * 
     */
    public boolean isFinal() {
        return _final;
    }

    /**
     * Sets the value of the final property.
     * 
     */
    public void setFinal(boolean value) {
        this._final = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the identity property.
     * 
     */
    public boolean isIdentity() {
        return identity;
    }

    /**
     * Sets the value of the identity property.
     * 
     */
    public void setIdentity(boolean value) {
        this.identity = value;
    }

    /**
     * Gets the value of the longname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLongname() {
        return longname;
    }

    /**
     * Sets the value of the longname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLongname(String value) {
        this.longname = value;
    }

    /**
     * Gets the value of the nullable property.
     * 
     */
    public boolean isNullable() {
        return nullable;
    }

    /**
     * Sets the value of the nullable property.
     * 
     */
    public void setNullable(boolean value) {
        this.nullable = value;
    }

    /**
     * Gets the value of the primarykey property.
     * 
     */
    public boolean isPrimarykey() {
        return primarykey;
    }

    /**
     * Sets the value of the primarykey property.
     * 
     */
    public void setPrimarykey(boolean value) {
        this.primarykey = value;
    }

    /**
     * Gets the value of the ref property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRef() {
        return ref;
    }

    /**
     * Sets the value of the ref property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRef(String value) {
        this.ref = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}
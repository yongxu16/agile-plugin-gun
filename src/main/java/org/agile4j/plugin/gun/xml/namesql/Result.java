//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.10.13 at 01:28:55 PM CST 
//


package org.agile4j.plugin.gun.xml.namesql;

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
 *       &lt;attribute name="column" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="javaType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="jdbcType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="longname" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="property" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "result")
public class Result {

    @XmlAttribute(name = "column")
    protected String column;
    @XmlAttribute(name = "javaType")
    protected String javaType;
    @XmlAttribute(name = "jdbcType")
    protected String jdbcType;
    @XmlAttribute(name = "longname")
    protected String longname;
    @XmlAttribute(name = "property")
    protected String property;

    /**
     * Gets the value of the column property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColumn() {
        return column;
    }

    /**
     * Sets the value of the column property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColumn(String value) {
        this.column = value;
    }

    /**
     * Gets the value of the javaType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJavaType() {
        return javaType;
    }

    /**
     * Sets the value of the javaType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJavaType(String value) {
        this.javaType = value;
    }

    /**
     * Gets the value of the jdbcType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJdbcType() {
        return jdbcType;
    }

    /**
     * Sets the value of the jdbcType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJdbcType(String value) {
        this.jdbcType = value;
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
     * Gets the value of the property property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProperty() {
        return property;
    }

    /**
     * Sets the value of the property property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProperty(String value) {
        this.property = value;
    }

}
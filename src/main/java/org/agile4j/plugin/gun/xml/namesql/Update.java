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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *       &lt;sequence>
 *         &lt;element ref="{}sql"/>
 *         &lt;element ref="{}parameterMap"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="longname" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="method" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "sql",
    "parameterMap"
})
@XmlRootElement(name = "update")
public class Update {

    @XmlElement(required = true)
    protected Sql sql;
    @XmlElement(required = true)
    protected ParameterMap parameterMap;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "longname")
    @XmlSchemaType(name = "anySimpleType")
    protected String longname;
    @XmlAttribute(name = "method")
    protected String method;

    /**
     * Gets the value of the sql property.
     * 
     * @return
     *     possible object is
     *     {@link Sql }
     *     
     */
    public Sql getSql() {
        return sql;
    }

    /**
     * Sets the value of the sql property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sql }
     *     
     */
    public void setSql(Sql value) {
        this.sql = value;
    }

    /**
     * Gets the value of the parameterMap property.
     * 
     * @return
     *     possible object is
     *     {@link ParameterMap }
     *     
     */
    public ParameterMap getParameterMap() {
        return parameterMap;
    }

    /**
     * Sets the value of the parameterMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParameterMap }
     *     
     */
    public void setParameterMap(ParameterMap value) {
        this.parameterMap = value;
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
     * Gets the value of the method property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMethod() {
        return method;
    }

    /**
     * Sets the value of the method property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMethod(String value) {
        this.method = value;
    }

}
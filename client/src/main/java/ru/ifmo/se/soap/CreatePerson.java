
package ru.ifmo.se.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for createPerson complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createPerson"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="personDto" type="{http://soap.se.ifmo.ru/}personDto" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createPerson", propOrder = {
    "personDto"
})
public class CreatePerson {

    @XmlElement(namespace = "")
    protected PersonDto personDto;

    /**
     * Gets the value of the personDto property.
     * 
     * @return
     *     possible object is
     *     {@link PersonDto }
     *     
     */
    public PersonDto getPersonDto() {
        return personDto;
    }

    /**
     * Sets the value of the personDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonDto }
     *     
     */
    public void setPersonDto(PersonDto value) {
        this.personDto = value;
    }

}

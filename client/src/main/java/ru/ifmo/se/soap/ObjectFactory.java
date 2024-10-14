
package ru.ifmo.se.soap;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ru.ifmo.se.soap package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SearchPersons_QNAME = new QName("http://soap.se.ifmo.ru/", "searchPersons");
    private final static QName _SearchPersonsResponse_QNAME = new QName("http://soap.se.ifmo.ru/", "searchPersonsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.ifmo.se.soap
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SearchPersons }
     * 
     */
    public SearchPersons createSearchPersons() {
        return new SearchPersons();
    }

    /**
     * Create an instance of {@link SearchPersonsResponse }
     * 
     */
    public SearchPersonsResponse createSearchPersonsResponse() {
        return new SearchPersonsResponse();
    }

    /**
     * Create an instance of {@link PersonListRequestDto }
     * 
     */
    public PersonListRequestDto createPersonListRequestDto() {
        return new PersonListRequestDto();
    }

    /**
     * Create an instance of {@link Person }
     * 
     */
    public Person createPerson() {
        return new Person();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchPersons }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SearchPersons }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.se.ifmo.ru/", name = "searchPersons")
    public JAXBElement<SearchPersons> createSearchPersons(SearchPersons value) {
        return new JAXBElement<SearchPersons>(_SearchPersons_QNAME, SearchPersons.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchPersonsResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SearchPersonsResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.se.ifmo.ru/", name = "searchPersonsResponse")
    public JAXBElement<SearchPersonsResponse> createSearchPersonsResponse(SearchPersonsResponse value) {
        return new JAXBElement<SearchPersonsResponse>(_SearchPersonsResponse_QNAME, SearchPersonsResponse.class, null, value);
    }

}

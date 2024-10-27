
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

    private final static QName _CreatePerson_QNAME = new QName("http://soap.se.ifmo.ru/", "createPerson");
    private final static QName _CreatePersonResponse_QNAME = new QName("http://soap.se.ifmo.ru/", "createPersonResponse");
    private final static QName _DeletePersonById_QNAME = new QName("http://soap.se.ifmo.ru/", "deletePersonById");
    private final static QName _DeletePersonByIdResponse_QNAME = new QName("http://soap.se.ifmo.ru/", "deletePersonByIdResponse");
    private final static QName _FindPersonById_QNAME = new QName("http://soap.se.ifmo.ru/", "findPersonById");
    private final static QName _FindPersonByIdResponse_QNAME = new QName("http://soap.se.ifmo.ru/", "findPersonByIdResponse");
    private final static QName _SearchPersons_QNAME = new QName("http://soap.se.ifmo.ru/", "searchPersons");
    private final static QName _SearchPersonsResponse_QNAME = new QName("http://soap.se.ifmo.ru/", "searchPersonsResponse");
    private final static QName _UpdatePerson_QNAME = new QName("http://soap.se.ifmo.ru/", "updatePerson");
    private final static QName _UpdatePersonResponse_QNAME = new QName("http://soap.se.ifmo.ru/", "updatePersonResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.ifmo.se.soap
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CreatePerson }
     * 
     */
    public CreatePerson createCreatePerson() {
        return new CreatePerson();
    }

    /**
     * Create an instance of {@link CreatePersonResponse }
     * 
     */
    public CreatePersonResponse createCreatePersonResponse() {
        return new CreatePersonResponse();
    }

    /**
     * Create an instance of {@link DeletePersonById }
     * 
     */
    public DeletePersonById createDeletePersonById() {
        return new DeletePersonById();
    }

    /**
     * Create an instance of {@link DeletePersonByIdResponse }
     * 
     */
    public DeletePersonByIdResponse createDeletePersonByIdResponse() {
        return new DeletePersonByIdResponse();
    }

    /**
     * Create an instance of {@link FindPersonById }
     * 
     */
    public FindPersonById createFindPersonById() {
        return new FindPersonById();
    }

    /**
     * Create an instance of {@link FindPersonByIdResponse }
     * 
     */
    public FindPersonByIdResponse createFindPersonByIdResponse() {
        return new FindPersonByIdResponse();
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
     * Create an instance of {@link UpdatePerson }
     * 
     */
    public UpdatePerson createUpdatePerson() {
        return new UpdatePerson();
    }

    /**
     * Create an instance of {@link UpdatePersonResponse }
     * 
     */
    public UpdatePersonResponse createUpdatePersonResponse() {
        return new UpdatePersonResponse();
    }

    /**
     * Create an instance of {@link PersonDto }
     * 
     */
    public PersonDto createPersonDto() {
        return new PersonDto();
    }

    /**
     * Create an instance of {@link Person }
     * 
     */
    public Person createPerson() {
        return new Person();
    }

    /**
     * Create an instance of {@link PersonListRequestDto }
     * 
     */
    public PersonListRequestDto createPersonListRequestDto() {
        return new PersonListRequestDto();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePerson }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CreatePerson }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.se.ifmo.ru/", name = "createPerson")
    public JAXBElement<CreatePerson> createCreatePerson(CreatePerson value) {
        return new JAXBElement<CreatePerson>(_CreatePerson_QNAME, CreatePerson.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePersonResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CreatePersonResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.se.ifmo.ru/", name = "createPersonResponse")
    public JAXBElement<CreatePersonResponse> createCreatePersonResponse(CreatePersonResponse value) {
        return new JAXBElement<CreatePersonResponse>(_CreatePersonResponse_QNAME, CreatePersonResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeletePersonById }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DeletePersonById }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.se.ifmo.ru/", name = "deletePersonById")
    public JAXBElement<DeletePersonById> createDeletePersonById(DeletePersonById value) {
        return new JAXBElement<DeletePersonById>(_DeletePersonById_QNAME, DeletePersonById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeletePersonByIdResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DeletePersonByIdResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.se.ifmo.ru/", name = "deletePersonByIdResponse")
    public JAXBElement<DeletePersonByIdResponse> createDeletePersonByIdResponse(DeletePersonByIdResponse value) {
        return new JAXBElement<DeletePersonByIdResponse>(_DeletePersonByIdResponse_QNAME, DeletePersonByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindPersonById }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FindPersonById }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.se.ifmo.ru/", name = "findPersonById")
    public JAXBElement<FindPersonById> createFindPersonById(FindPersonById value) {
        return new JAXBElement<FindPersonById>(_FindPersonById_QNAME, FindPersonById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindPersonByIdResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FindPersonByIdResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.se.ifmo.ru/", name = "findPersonByIdResponse")
    public JAXBElement<FindPersonByIdResponse> createFindPersonByIdResponse(FindPersonByIdResponse value) {
        return new JAXBElement<FindPersonByIdResponse>(_FindPersonByIdResponse_QNAME, FindPersonByIdResponse.class, null, value);
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

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdatePerson }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UpdatePerson }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.se.ifmo.ru/", name = "updatePerson")
    public JAXBElement<UpdatePerson> createUpdatePerson(UpdatePerson value) {
        return new JAXBElement<UpdatePerson>(_UpdatePerson_QNAME, UpdatePerson.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdatePersonResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UpdatePersonResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.se.ifmo.ru/", name = "updatePersonResponse")
    public JAXBElement<UpdatePersonResponse> createUpdatePersonResponse(UpdatePersonResponse value) {
        return new JAXBElement<UpdatePersonResponse>(_UpdatePersonResponse_QNAME, UpdatePersonResponse.class, null, value);
    }

}

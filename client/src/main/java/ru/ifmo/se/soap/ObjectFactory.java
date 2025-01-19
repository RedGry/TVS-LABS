
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

    private final static QName _DeleteFileById_QNAME = new QName("http://soap.se.ifmo.ru/", "deleteFileById");
    private final static QName _DeleteFileByIdResponse_QNAME = new QName("http://soap.se.ifmo.ru/", "deleteFileByIdResponse");
    private final static QName _DownloadFile_QNAME = new QName("http://soap.se.ifmo.ru/", "downloadFile");
    private final static QName _DownloadFileById_QNAME = new QName("http://soap.se.ifmo.ru/", "downloadFileById");
    private final static QName _DownloadFileByIdResponse_QNAME = new QName("http://soap.se.ifmo.ru/", "downloadFileByIdResponse");
    private final static QName _DownloadFileResponse_QNAME = new QName("http://soap.se.ifmo.ru/", "downloadFileResponse");
    private final static QName _GetFileNameById_QNAME = new QName("http://soap.se.ifmo.ru/", "getFileNameById");
    private final static QName _GetFileNameByIdResponse_QNAME = new QName("http://soap.se.ifmo.ru/", "getFileNameByIdResponse");
    private final static QName _ListFiles_QNAME = new QName("http://soap.se.ifmo.ru/", "listFiles");
    private final static QName _ListFilesResponse_QNAME = new QName("http://soap.se.ifmo.ru/", "listFilesResponse");
    private final static QName _UploadFile_QNAME = new QName("http://soap.se.ifmo.ru/", "uploadFile");
    private final static QName _UploadFileResponse_QNAME = new QName("http://soap.se.ifmo.ru/", "uploadFileResponse");
    private final static QName _UploadFileArg1_QNAME = new QName("", "arg1");
    private final static QName _DownloadFileResponseReturn_QNAME = new QName("", "return");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.ifmo.se.soap
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DeleteFileById }
     * 
     */
    public DeleteFileById createDeleteFileById() {
        return new DeleteFileById();
    }

    /**
     * Create an instance of {@link DeleteFileByIdResponse }
     * 
     */
    public DeleteFileByIdResponse createDeleteFileByIdResponse() {
        return new DeleteFileByIdResponse();
    }

    /**
     * Create an instance of {@link DownloadFile }
     * 
     */
    public DownloadFile createDownloadFile() {
        return new DownloadFile();
    }

    /**
     * Create an instance of {@link DownloadFileById }
     * 
     */
    public DownloadFileById createDownloadFileById() {
        return new DownloadFileById();
    }

    /**
     * Create an instance of {@link DownloadFileByIdResponse }
     * 
     */
    public DownloadFileByIdResponse createDownloadFileByIdResponse() {
        return new DownloadFileByIdResponse();
    }

    /**
     * Create an instance of {@link DownloadFileResponse }
     * 
     */
    public DownloadFileResponse createDownloadFileResponse() {
        return new DownloadFileResponse();
    }

    /**
     * Create an instance of {@link GetFileNameById }
     * 
     */
    public GetFileNameById createGetFileNameById() {
        return new GetFileNameById();
    }

    /**
     * Create an instance of {@link GetFileNameByIdResponse }
     * 
     */
    public GetFileNameByIdResponse createGetFileNameByIdResponse() {
        return new GetFileNameByIdResponse();
    }

    /**
     * Create an instance of {@link ListFiles }
     * 
     */
    public ListFiles createListFiles() {
        return new ListFiles();
    }

    /**
     * Create an instance of {@link ListFilesResponse }
     * 
     */
    public ListFilesResponse createListFilesResponse() {
        return new ListFilesResponse();
    }

    /**
     * Create an instance of {@link UploadFile }
     * 
     */
    public UploadFile createUploadFile() {
        return new UploadFile();
    }

    /**
     * Create an instance of {@link UploadFileResponse }
     * 
     */
    public UploadFileResponse createUploadFileResponse() {
        return new UploadFileResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteFileById }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DeleteFileById }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.se.ifmo.ru/", name = "deleteFileById")
    public JAXBElement<DeleteFileById> createDeleteFileById(DeleteFileById value) {
        return new JAXBElement<DeleteFileById>(_DeleteFileById_QNAME, DeleteFileById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteFileByIdResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DeleteFileByIdResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.se.ifmo.ru/", name = "deleteFileByIdResponse")
    public JAXBElement<DeleteFileByIdResponse> createDeleteFileByIdResponse(DeleteFileByIdResponse value) {
        return new JAXBElement<DeleteFileByIdResponse>(_DeleteFileByIdResponse_QNAME, DeleteFileByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadFile }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DownloadFile }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.se.ifmo.ru/", name = "downloadFile")
    public JAXBElement<DownloadFile> createDownloadFile(DownloadFile value) {
        return new JAXBElement<DownloadFile>(_DownloadFile_QNAME, DownloadFile.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadFileById }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DownloadFileById }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.se.ifmo.ru/", name = "downloadFileById")
    public JAXBElement<DownloadFileById> createDownloadFileById(DownloadFileById value) {
        return new JAXBElement<DownloadFileById>(_DownloadFileById_QNAME, DownloadFileById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadFileByIdResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DownloadFileByIdResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.se.ifmo.ru/", name = "downloadFileByIdResponse")
    public JAXBElement<DownloadFileByIdResponse> createDownloadFileByIdResponse(DownloadFileByIdResponse value) {
        return new JAXBElement<DownloadFileByIdResponse>(_DownloadFileByIdResponse_QNAME, DownloadFileByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadFileResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DownloadFileResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.se.ifmo.ru/", name = "downloadFileResponse")
    public JAXBElement<DownloadFileResponse> createDownloadFileResponse(DownloadFileResponse value) {
        return new JAXBElement<DownloadFileResponse>(_DownloadFileResponse_QNAME, DownloadFileResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFileNameById }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetFileNameById }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.se.ifmo.ru/", name = "getFileNameById")
    public JAXBElement<GetFileNameById> createGetFileNameById(GetFileNameById value) {
        return new JAXBElement<GetFileNameById>(_GetFileNameById_QNAME, GetFileNameById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFileNameByIdResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetFileNameByIdResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.se.ifmo.ru/", name = "getFileNameByIdResponse")
    public JAXBElement<GetFileNameByIdResponse> createGetFileNameByIdResponse(GetFileNameByIdResponse value) {
        return new JAXBElement<GetFileNameByIdResponse>(_GetFileNameByIdResponse_QNAME, GetFileNameByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListFiles }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ListFiles }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.se.ifmo.ru/", name = "listFiles")
    public JAXBElement<ListFiles> createListFiles(ListFiles value) {
        return new JAXBElement<ListFiles>(_ListFiles_QNAME, ListFiles.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListFilesResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ListFilesResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.se.ifmo.ru/", name = "listFilesResponse")
    public JAXBElement<ListFilesResponse> createListFilesResponse(ListFilesResponse value) {
        return new JAXBElement<ListFilesResponse>(_ListFilesResponse_QNAME, ListFilesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadFile }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UploadFile }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.se.ifmo.ru/", name = "uploadFile")
    public JAXBElement<UploadFile> createUploadFile(UploadFile value) {
        return new JAXBElement<UploadFile>(_UploadFile_QNAME, UploadFile.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadFileResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UploadFileResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.se.ifmo.ru/", name = "uploadFileResponse")
    public JAXBElement<UploadFileResponse> createUploadFileResponse(UploadFileResponse value) {
        return new JAXBElement<UploadFileResponse>(_UploadFileResponse_QNAME, UploadFileResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     */
    @XmlElementDecl(namespace = "", name = "arg1", scope = UploadFile.class)
    public JAXBElement<byte[]> createUploadFileArg1(byte[] value) {
        return new JAXBElement<byte[]>(_UploadFileArg1_QNAME, byte[].class, UploadFile.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     */
    @XmlElementDecl(namespace = "", name = "return", scope = DownloadFileResponse.class)
    public JAXBElement<byte[]> createDownloadFileResponseReturn(byte[] value) {
        return new JAXBElement<byte[]>(_DownloadFileResponseReturn_QNAME, byte[].class, DownloadFileResponse.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     */
    @XmlElementDecl(namespace = "", name = "return", scope = DownloadFileByIdResponse.class)
    public JAXBElement<byte[]> createDownloadFileByIdResponseReturn(byte[] value) {
        return new JAXBElement<byte[]>(_DownloadFileResponseReturn_QNAME, byte[].class, DownloadFileByIdResponse.class, ((byte[]) value));
    }

}

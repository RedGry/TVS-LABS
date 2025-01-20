package ru.ifmo.se.juddi;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.juddi.api_v3.AccessPointType;
import org.apache.juddi.v3.client.config.UDDIClient;
import org.apache.juddi.v3.client.transport.Transport;
import org.apache.juddi.v3.client.transport.TransportException;
import org.uddi.api_v3.*;
import org.uddi.v3_service.UDDIInquiryPortType;
import org.uddi.v3_service.UDDIPublicationPortType;
import org.uddi.v3_service.UDDISecurityPortType;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

public class JuddiClient {
    public static final String DEFAULT_CONFIG_PATH = "META-INF/uddi.xml";
    public static final String DEFAULT_NODE_NAME = "default";

    public static final String HOST_PROP = "serverName";
    public static final String PORT_PROP = "serverPort";

    private final UDDISecurityPortType security;
    private final UDDIPublicationPortType publisher;
    private final UDDIInquiryPortType inquire;
    private final GetAuthToken authTokenReqData;

    public JuddiClient(String host,
                       Integer port,
                       String username,
                       String password,
                       String configPath,
                       String transportNodeName) throws ConfigurationException, TransportException {
        Properties nodeProperties = new Properties();
        nodeProperties.put(HOST_PROP, host);
        nodeProperties.put(PORT_PROP, port.toString());

        UDDIClient client = new UDDIClient(configPath, nodeProperties);
        Transport transport = client.getTransport(transportNodeName);
        this.security = transport.getUDDISecurityService();
        this.publisher = transport.getUDDIPublishService();
        this.inquire = transport.getUDDIInquiryService();

        GetAuthToken getAuthTokenMyPub = new GetAuthToken();
        getAuthTokenMyPub.setUserID(username);
        getAuthTokenMyPub.setCred(password);
        this.authTokenReqData = getAuthTokenMyPub;
    }

    public JuddiClient(String host, Integer port, String username, String password) throws ConfigurationException, TransportException {
        this(host, port, username, password, DEFAULT_CONFIG_PATH, DEFAULT_NODE_NAME);
    }

    public JuddiClient(String host, Integer port) throws ConfigurationException, TransportException {
        this(host, port, null, null, DEFAULT_CONFIG_PATH, DEFAULT_NODE_NAME);
    }

    public AuthToken provideAuthToken() throws RemoteException {
        return this.security.getAuthToken(this.authTokenReqData);
    }

    public void discardAuthToken(AuthToken token) throws RemoteException {
        security.discardAuthToken(new DiscardAuthToken(token.getAuthInfo()));
    }

    public String registerBusiness(String businessName, AuthToken authToken) throws RemoteException {
        BusinessEntity businessEntity = new BusinessEntity();
        businessEntity.getName().add(new Name(businessName, Locale.ENGLISH.getDisplayLanguage()));
        SaveBusiness sb = new SaveBusiness();
        sb.getBusinessEntity().add(businessEntity);
        sb.setAuthInfo(authToken.getAuthInfo());
        return this.publisher.saveBusiness(sb).getBusinessEntity().get(0).getBusinessKey();
    }

    public String registerService(String businessKey, String serviceName, AuthToken authToken) throws RemoteException {
        BusinessService service = new BusinessService();
        service.setBusinessKey(businessKey);
        service.getName().add(new Name(serviceName, Locale.ENGLISH.getDisplayLanguage()));
        SaveService ss = new SaveService();
        ss.getBusinessService().add(service);
        ss.setAuthInfo(authToken.getAuthInfo());
        return this.publisher.saveService(ss).getBusinessService().get(0).getServiceKey();
    }

    public BindingDetail bindService(String serviceKey, URL serviceUrl, AuthToken authToken)
            throws RemoteException {
        AccessPoint accessPoint = new AccessPoint(serviceUrl.toString(), AccessPointType.WSDL_DEPLOYMENT.toString());
        BindingTemplate bindingTemplate = new BindingTemplate();
        bindingTemplate.setServiceKey(serviceKey);
        bindingTemplate.setAccessPoint(accessPoint);
        BindingTemplate bindingTemplateSoap = UDDIClient.addSOAPtModels(bindingTemplate);

        SaveBinding saveBindingData = new SaveBinding();
        saveBindingData.setAuthInfo(authToken.getAuthInfo());
        saveBindingData.getBindingTemplate().add(bindingTemplateSoap);
        return this.publisher.saveBinding(saveBindingData);
    }

    public String findBusinessInfo(String businessName) throws RemoteException {
        FindBusiness findBusinessData = new FindBusiness();
        findBusinessData.getName().add(new Name(businessName, Locale.ENGLISH.getDisplayLanguage()));
        BusinessList businessList = this.inquire.findBusiness(findBusinessData);
        if (businessList == null
                || businessList.getBusinessInfos() == null
                || CollectionUtils.isEmpty(businessList.getBusinessInfos().getBusinessInfo())) {
            return null;
        }

        List<BusinessInfo> businessInfos = businessList.getBusinessInfos().getBusinessInfo();
        return businessInfos.get(0).getBusinessKey();
    }

    public String findServiceInfo(String businessKey, String serviceName) throws RemoteException {
        FindService findServiceData = new FindService();
        findServiceData.setBusinessKey(businessKey);
        findServiceData.getName().add(new Name(serviceName, Locale.ENGLISH.getDisplayLanguage()));
        ServiceList serviceList = this.inquire.findService(findServiceData);
        if (serviceList == null
                || serviceList.getServiceInfos() == null
                || CollectionUtils.isEmpty(serviceList.getServiceInfos().getServiceInfo())) {
            return null;
        }

        List<ServiceInfo> serviceInfos = serviceList.getServiceInfos().getServiceInfo();
        return serviceInfos.get(0).getServiceKey();
    }

    public BindingDetail findServiceBindingInfo(String serviceKey) throws RemoteException {
        FindBinding findBindingData = new FindBinding();
        findBindingData.setServiceKey(serviceKey);
        return this.inquire.findBinding(findBindingData);
    }

    public static List<String> getUrlsFromBinding(BindingDetail bindingDetail) {
        List<String> urls = new ArrayList<>();
        for (BindingTemplate bindingTemplate : bindingDetail.getBindingTemplate()) {
            AccessPoint accessPoint = bindingTemplate.getAccessPoint();
            urls.add(accessPoint.getValue());
        }

        return urls;
    }
}

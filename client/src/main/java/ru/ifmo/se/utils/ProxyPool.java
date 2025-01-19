package ru.ifmo.se.utils;

import ru.ifmo.se.soap.PersonService;
import ru.ifmo.se.soap.PersonWebService;

import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProxyPool {
    private final BlockingQueue<PersonWebService> proxyPool;

    public ProxyPool(String wsdlUrl, int poolSize) throws Exception {
        proxyPool = new LinkedBlockingQueue<>(poolSize);
        URL url = new URL(wsdlUrl);
        PersonService service = new PersonService(url);

        for (int i = 0; i < poolSize; i++) {
            proxyPool.add(service.getPersonWebServicePort());
        }
    }

    public PersonWebService acquireProxy() throws InterruptedException {
        return proxyPool.take();
    }

    public void releaseProxy(PersonWebService proxy) {
        proxyPool.offer(proxy);
    }
}

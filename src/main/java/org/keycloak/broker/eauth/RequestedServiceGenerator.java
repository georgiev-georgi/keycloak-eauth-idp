package org.keycloak.broker.eauth;

import org.keycloak.saml.common.exceptions.ProcessingException;
import org.keycloak.saml.common.util.StaxUtil;

import javax.xml.stream.XMLStreamWriter;

/**
 * User: ggeorgiev
 * Date: 16.02.2023
 * Time: 17:14
 */
public class RequestedServiceGenerator  extends AbstractExtensionGenerator {
    @Override
    public void write(XMLStreamWriter writer) throws ProcessingException {
        StaxUtil.writeStartElement(writer, PREFIX, "RequestedService", NAMESPACE);
        StaxUtil.writeNameSpace(writer, PREFIX, NAMESPACE);
        addElement(writer, "Service", "2.16.100.1.1.24.1.5.1.1.1");//TODO:Trqbva da moje da se konfigurira!!!!
        addElement(writer, "Provider", "2.16.100.1.1.24.1.5");//TODO:Trqbva da moje da se konfigurira!!!!
        addElement(writer, "LevelOfAssurance", "HIGH");//TODO:Trqbva da moje da se konfigurira!!!!
        StaxUtil.writeEndElement(writer);
        StaxUtil.flush(writer);
    }
}

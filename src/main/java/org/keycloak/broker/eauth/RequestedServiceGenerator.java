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
    public RequestedServiceGenerator(EauthIdentityProviderConfig config) {
        super(config);
    }

    @Override
    public void write(XMLStreamWriter writer) throws ProcessingException {
        StaxUtil.writeStartElement(writer, PREFIX, "RequestedService", NAMESPACE);
        StaxUtil.writeNameSpace(writer, PREFIX, NAMESPACE);
        addElement(writer, "Service", config.getRequestedServiceService());
        addElement(writer, "Provider", config.getRequestedServiceProvider());
        addElement(writer, "LevelOfAssurance", config.getRequestedServiceLevelOfAssurance());
        StaxUtil.writeEndElement(writer);
        StaxUtil.flush(writer);
    }
}

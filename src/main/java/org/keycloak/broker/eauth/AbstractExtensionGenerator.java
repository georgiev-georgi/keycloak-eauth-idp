package org.keycloak.broker.eauth;

import org.keycloak.saml.SamlProtocolExtensionsAwareBuilder;
import org.keycloak.saml.common.exceptions.ProcessingException;
import org.keycloak.saml.common.util.StaxUtil;

import javax.xml.stream.XMLStreamWriter;

/**
 * User: ggeorgiev
 * Date: 16.02.2023
 * Time: 17:30
 */
public abstract class AbstractExtensionGenerator implements SamlProtocolExtensionsAwareBuilder.NodeGenerator {
    protected static String PREFIX = "egovbga";
    protected static String NAMESPACE = "urn:bg:egov:eauth:2.0:saml:ext";
    protected EauthIdentityProviderConfig config;
    public AbstractExtensionGenerator(EauthIdentityProviderConfig config) {
        this.config = config;
    }

    protected void addElement(XMLStreamWriter writer, String key, String value) throws ProcessingException {
        StaxUtil.writeStartElement(writer, PREFIX, key, NAMESPACE);
        StaxUtil.writeCharacters(writer, value);
        StaxUtil.writeEndElement(writer);
    }
}

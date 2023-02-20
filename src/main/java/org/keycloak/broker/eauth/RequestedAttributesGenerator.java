package org.keycloak.broker.eauth;

import org.keycloak.saml.common.exceptions.ProcessingException;
import org.keycloak.saml.common.util.StaxUtil;

import javax.xml.stream.XMLStreamWriter;

/**
 * User: ggeorgiev
 * Date: 16.02.2023
 * Time: 17:14
 */
public class RequestedAttributesGenerator extends AbstractExtensionGenerator {
    @Override
    public void write(XMLStreamWriter writer) throws ProcessingException {
        StaxUtil.writeStartElement(writer, PREFIX, "RequestedAttributes", NAMESPACE);
        StaxUtil.writeNameSpace(writer, PREFIX, NAMESPACE);

        //TODO:Hardcoded attribute. Moje da ima poveche ot edin attribute. Da se konfigurira!!!!
        writeRequestedAttribute(writer, "email", "urn:egov:bg:eauth:2.0:attributes:email", "urn:oasis:names:tc:saml2:2.0:attrname-format:uri", "true", "urn:egov:bg:eauth:2.0:attributes:email");

        StaxUtil.writeEndElement(writer);
        StaxUtil.flush(writer);
    }
    private void writeRequestedAttribute(XMLStreamWriter writer, String friendlyName, String name, String format, String required, String value) throws ProcessingException {
        StaxUtil.writeStartElement(writer, PREFIX, "RequestedAttribute", NAMESPACE);
        StaxUtil.writeAttribute(writer, "FriendlyName", friendlyName);
        StaxUtil.writeAttribute(writer, "Name", name);
        StaxUtil.writeAttribute(writer, "NameFormat", format);
        StaxUtil.writeAttribute(writer, "isRequired", required);
        addElement(writer, "AttributeValue", value);
        StaxUtil.writeEndElement(writer);

    }

}

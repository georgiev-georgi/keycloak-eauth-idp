package org.keycloak.broker.eauth;

import org.keycloak.saml.common.exceptions.ProcessingException;
import org.keycloak.saml.common.util.StaxUtil;
import org.keycloak.utils.StringUtil;

import javax.xml.stream.XMLStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * User: ggeorgiev
 * Date: 16.02.2023
 * Time: 17:14
 */
public class RequestedAttributesGenerator extends AbstractExtensionGenerator {

    public RequestedAttributesGenerator(EauthIdentityProviderConfig config) {
        super(config);
    }

    @Override
    public void write(XMLStreamWriter writer) throws ProcessingException {
        String attributes = config.getRequestedAttributes();
        if (StringUtil.isBlank(attributes)) {
            return;
        }


        StaxUtil.writeStartElement(writer, PREFIX, "RequestedAttributes", NAMESPACE);
        StaxUtil.writeNameSpace(writer, PREFIX, NAMESPACE);


        List<Map<String, String>> generated = generateAttributes(attributes);
        for (Map<String, String> g : generated) {
            writeRequestedAttribute(writer, g);
        }

        StaxUtil.writeEndElement(writer);
        StaxUtil.flush(writer);
    }
    private void writeRequestedAttribute(XMLStreamWriter writer, Map<String, String> attributes) throws ProcessingException {
        StaxUtil.writeStartElement(writer, PREFIX, "RequestedAttribute", NAMESPACE);
        for (String a : attributes.keySet()) {
            if (a.equalsIgnoreCase("value")) {
                continue;
            }
            StaxUtil.writeAttribute(writer, a, attributes.get(a));
        }
        String value = attributes.entrySet().stream().filter(r -> r.getKey().equalsIgnoreCase("value")).findFirst().map(r -> r.getValue()).orElseThrow(() -> new RuntimeException("Value should be preset ! Attributes: "  + attributes));
        addElement(writer, "AttributeValue", value);
        StaxUtil.writeEndElement(writer);
    }
    private List<Map<String, String>> generateAttributes(String attr) {
        attr = attr.replaceAll("\r\n", "\n");
        attr = attr.replaceAll("\r", "\n");
        String[] parts = attr.split("\n");
        List<Map<String, String>> result = new ArrayList<>();
        for (String p : parts) {
            Map<String, String> map = Arrays
                    .stream(p.split(";"))
                    .map(r -> r.trim())
                    .map(r -> r.split("="))
                    .filter(r -> r.length == 2)
                    .collect(Collectors.toMap(r -> r[0], r -> r[1]));
            if (map.size() > 0) {
                result.add(map);
            }

        }
        return result;
    }

}

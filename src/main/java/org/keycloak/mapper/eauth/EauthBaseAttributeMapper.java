package org.keycloak.mapper.eauth;

import org.keycloak.broker.eauth.EauthIdentityProviderFactory;
import org.keycloak.broker.provider.BrokeredIdentityContext;
import org.keycloak.broker.saml.SAMLEndpoint;
import org.keycloak.broker.saml.mappers.UserAttributeMapper;
import org.keycloak.dom.saml.v2.assertion.AssertionType;
import org.keycloak.dom.saml.v2.assertion.AttributeStatementType;
import org.keycloak.dom.saml.v2.assertion.AttributeType;
import org.keycloak.models.IdentityProviderMapperModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.saml.common.constants.JBossSAMLURIConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * User: ggeorgiev
 * Date: 23.02.2023
 * Time: 13:23
 */
public class EauthBaseAttributeMapper extends UserAttributeMapper {
    protected List<ProviderConfigProperty> configProperties = new ArrayList<>();
    private static final String[] cp = new String[]{EauthIdentityProviderFactory.PROVIDER_ID};

    protected void addAllAttributes() {
        addAttributeNameProperty();
        addFriendlyNameProperty();;
        addNameFormatProperty();
        addUserAttributeProperty();
    }
    protected void addAllButUserAttributeProperty() {
        addAttributeNameProperty();
        addFriendlyNameProperty();
        addNameFormatProperty();
    }


    protected void addAttributeNameProperty() {
        ProviderConfigProperty property;
        property = new ProviderConfigProperty();
        property.setName(ATTRIBUTE_NAME);
        property.setLabel("Attribute Name");
        property.setHelpText("Name of attribute to search for in assertion.  You can leave this blank and specify a friendly name instead.");
        property.setType(ProviderConfigProperty.STRING_TYPE);
        configProperties.add(property);
    }
    protected void addFriendlyNameProperty() {
        ProviderConfigProperty property = new ProviderConfigProperty();
        property.setName(ATTRIBUTE_FRIENDLY_NAME);
        property.setLabel("Friendly Name");
        property.setHelpText("Friendly name of attribute to search for in assertion.  You can leave this blank and specify a name instead.");
        property.setType(ProviderConfigProperty.STRING_TYPE);
        configProperties.add(property);
    }
    protected void addNameFormatProperty() {
        ProviderConfigProperty property = new ProviderConfigProperty();
        property.setName(ATTRIBUTE_NAME_FORMAT);
        property.setLabel("Name Format");
        property.setHelpText("Name format of attribute to specify in the RequestedAttribute element. Default to basic format.");
        property.setType(ProviderConfigProperty.LIST_TYPE);
        property.setOptions(NAME_FORMATS);
        property.setDefaultValue(JBossSAMLURIConstants.ATTRIBUTE_FORMAT_BASIC.name());
        configProperties.add(property);
    }
    protected void addUserAttributeProperty() {
        ProviderConfigProperty property = new ProviderConfigProperty();
        property.setName(USER_ATTRIBUTE);
        property.setLabel("User Attribute Name");
        property.setHelpText("User attribute name to store saml attribute.  Use email, lastName, and firstName to map to those predefined user properties.");
        property.setType(ProviderConfigProperty.STRING_TYPE);
        configProperties.add(property);
    }




    @Override
    public String[] getCompatibleProviders() {
        return cp;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configProperties;
    }



    protected String getAttributeNameFromMapperModel(IdentityProviderMapperModel mapperModel) {
        String attributeName = mapperModel.getConfig().get(ATTRIBUTE_NAME);
        if (attributeName == null) {
            attributeName = mapperModel.getConfig().get(ATTRIBUTE_FRIENDLY_NAME);
        }
        return attributeName;
    }
    protected Predicate<AttributeStatementType.ASTChoiceType> elementWith(String attributeName) {
        return attributeType -> {
            AttributeType attribute = attributeType.getAttribute();
            return Objects.equals(attribute.getName(), attributeName)
                    || Objects.equals(attribute.getFriendlyName(), attributeName);
        };
    }
    protected List<String> findAttributeValuesInContext(String attributeName, BrokeredIdentityContext context) {
        AssertionType assertion = (AssertionType) context.getContextData().get(SAMLEndpoint.SAML_ASSERTION);

        return assertion.getAttributeStatements().stream()
                .flatMap(statement -> statement.getAttributes().stream())
                .filter(elementWith(attributeName))
                .flatMap(attributeType -> attributeType.getAttribute().getAttributeValue().stream())
                .filter(Objects::nonNull)
                .map(Object::toString)
                .collect(Collectors.toList());
    }
    protected List<String> toList(String val) {
        List<String> res = new ArrayList<>();
        res.add(val);
        return res;
    }
}

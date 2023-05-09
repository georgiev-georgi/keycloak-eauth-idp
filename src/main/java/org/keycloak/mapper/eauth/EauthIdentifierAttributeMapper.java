package org.keycloak.mapper.eauth;

import org.keycloak.broker.provider.BrokeredIdentityContext;
import org.keycloak.models.IdentityProviderMapperModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;

import java.util.List;

/**
 * User: ggeorgiev
 * Date: 22.02.2023
 * Time: 15:28
 */
public class EauthIdentifierAttributeMapper extends EauthBaseAttributeMapper {
    private static final String IDENTIFIER_ATTRIBUTE = "personal_id";
    private static final String IDENTIFIER_TYPE_ATTRIBUTE = "personal_id_type";

    public EauthIdentifierAttributeMapper() {
        addAllButUserAttributeProperty();
    }

    @Override
    public String getDisplayType() {
        return "Eauth Person Identifier and Type Mapper";
    }

    @Override
    public String getId() {
        return "eauth-person-identifier-attribute-mapper";
    }

    @Override
    public void preprocessFederatedIdentity(KeycloakSession session, RealmModel realm, IdentityProviderMapperModel mapperModel, BrokeredIdentityContext context) {
        String attributeName = getAttributeNameFromMapperModel(mapperModel);

        List<String> attributeValuesInContext = findAttributeValuesInContext(attributeName, context);
        if (!attributeValuesInContext.isEmpty()) {
            String id = attributeValuesInContext.get(0);
            if (id != null && id.startsWith("PNOBG-")) {
                String identifier = id.replace("PNOBG-", "");
                context.setUserAttribute(IDENTIFIER_ATTRIBUTE, toList(identifier));
                context.setUserAttribute(IDENTIFIER_TYPE_ATTRIBUTE, toList("NATIONAL_ID"));
            }
        }
    }

}

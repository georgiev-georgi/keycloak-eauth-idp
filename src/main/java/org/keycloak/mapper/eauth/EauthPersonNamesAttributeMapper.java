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
public class EauthPersonNamesAttributeMapper extends EauthBaseAttributeMapper {

    private static final String FIRST_NAME = "firstName";
    private static final String SECOND_NAME = "middle_name";
    private static final String LAST_NAME = "lastName";

    public EauthPersonNamesAttributeMapper() {
        addAllButUserAttributeProperty();
    }

    @Override
    public String getDisplayType() {
        return "Eauth Person Names Attributes Mapper";
    }

    @Override
    public String getId() {
        return "eauth-person-names-attribute-mapper";
    }

    @Override
    public void preprocessFederatedIdentity(KeycloakSession session, RealmModel realm, IdentityProviderMapperModel mapperModel, BrokeredIdentityContext context) {
        String attributeName = getAttributeNameFromMapperModel(mapperModel);

        List<String> attributeValuesInContext = findAttributeValuesInContext(attributeName, context);
        if (!attributeValuesInContext.isEmpty()) {
            String personNames = attributeValuesInContext.get(0);
            String[] nameParts = personNames.split(" ");
            if (nameParts.length == 3) {
                context.setUserAttribute(FIRST_NAME, toList(nameParts[0]));
                context.setUserAttribute(SECOND_NAME, toList(nameParts[1]));
                context.setUserAttribute(LAST_NAME, toList(nameParts[2]));
            } else {
                context.setUserAttribute(FIRST_NAME, toList(personNames));
            }
        }
    }
}

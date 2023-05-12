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
public class EauthUsernameByEmailAttributeMapper extends EauthBaseAttributeMapper {
    public EauthUsernameByEmailAttributeMapper() {
        addAllButUserAttributeProperty();
    }

    @Override
    public String getId() {
        return "eauth-username-by-email-attribute-mapper";
    }
    @Override
    public String getDisplayType() {
        return "Eauth Read Username by Email Mapper";
    }

    @Override
    public void preprocessFederatedIdentity(KeycloakSession session, RealmModel realm, IdentityProviderMapperModel mapperModel, BrokeredIdentityContext context) {
        String attributeName = getAttributeNameFromMapperModel(mapperModel);

        List<String> attributeValuesInContext = findAttributeValuesInContext(attributeName, context);
        if (!attributeValuesInContext.isEmpty()) {
            String email = attributeValuesInContext.get(0);
            context.setUsername(email);
        }
    }
}

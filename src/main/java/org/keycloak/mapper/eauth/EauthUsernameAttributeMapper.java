package org.keycloak.mapper.eauth;

import org.keycloak.broker.eauth.EauthIdentityProviderFactory;
import org.keycloak.broker.provider.BrokeredIdentityContext;
import org.keycloak.broker.saml.mappers.UserAttributeMapper;
import org.keycloak.models.IdentityProviderMapperModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.utils.KeycloakModelUtils;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.saml.common.constants.JBossSAMLURIConstants;
import org.keycloak.saml.common.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ggeorgiev
 * Date: 22.02.2023
 * Time: 15:28
 */
public class EauthUsernameAttributeMapper extends UserAttributeMapper {
    private static final String[] cp = new String[]{EauthIdentityProviderFactory.PROVIDER_ID};
    private static final List<ProviderConfigProperty> configProperties = new ArrayList<>();
    @Override
    public String[] getCompatibleProviders() {
        return cp;
    }
    @Override
    public String getDisplayType() {
        return "Eauth Username Attribute Mapper";
    }

    @Override
    public String getId() {
        return "eauth-username-attribute-mapper";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configProperties;
    }

    @Override
    public void preprocessFederatedIdentity(KeycloakSession session, RealmModel realm, IdentityProviderMapperModel mapperModel, BrokeredIdentityContext context) {
        String email = context.getUsername();
        UserModel user = KeycloakModelUtils.findUserByNameOrEmail(session, realm, email);
        if (user != null) {
            context.setUsername(user.getUsername());
        } else {
            context.setUsername(email.split("@")[0]);
        }



    }
}

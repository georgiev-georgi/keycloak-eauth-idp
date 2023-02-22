package org.keycloak.mapper.eauth;

import org.keycloak.broker.eauth.EauthIdentityProviderFactory;
import org.keycloak.broker.saml.mappers.UserAttributeMapper;

/**
 * User: ggeorgiev
 * Date: 22.02.2023
 * Time: 15:28
 */
public class EauthUserAttributeMapper extends UserAttributeMapper {
    private static final String[] cp = new String[]{EauthIdentityProviderFactory.PROVIDER_ID};

    @Override
    public String[] getCompatibleProviders() {
        return cp;
    }

    @Override
    public String getId() {
        return "eauth-user-attribute-mapper";
    }
}

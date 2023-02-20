package org.keycloak.broker.eauth;

import org.keycloak.broker.saml.SAMLIdentityProviderConfig;
import org.keycloak.models.IdentityProviderModel;

public class EauthIdentityProviderConfig extends SAMLIdentityProviderConfig {
    public static final String REQUESTED_SERVICE_SERVICE = "requestedServiceService";

    public EauthIdentityProviderConfig() {
    }

    public EauthIdentityProviderConfig(IdentityProviderModel identityProviderModel) {
        super(identityProviderModel);
    }

    public String getRequestedServiceService() {
        return getConfig().get(REQUESTED_SERVICE_SERVICE);
    }

    public void setRequestedServiceService(String code) {
        getConfig().put(REQUESTED_SERVICE_SERVICE, code);
    }

}

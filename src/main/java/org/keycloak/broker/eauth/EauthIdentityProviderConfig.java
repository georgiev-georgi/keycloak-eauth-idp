package org.keycloak.broker.eauth;

import org.keycloak.broker.saml.SAMLIdentityProviderConfig;
import org.keycloak.models.IdentityProviderModel;

public class EauthIdentityProviderConfig extends SAMLIdentityProviderConfig {
    public static final String REQUESTED_SERVICE_SERVICE = "requestedServiceService";
    public static final String REQUESTED_SERVICE_PROVIDER = "requestedServiceProvider";
    public static final String REQUESTED_SERVICE_LEVEL_OF_ASSURANCE = "requestedServiceLevelOfAssurance";
    public static final String REQUESTED_ATTRIBUTES = "requestedAttributes";

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

    public String getRequestedServiceProvider() {
        return getConfig().get(REQUESTED_SERVICE_PROVIDER);
    }

    public void setRequestedServiceProvider(String code) {
        getConfig().put(REQUESTED_SERVICE_PROVIDER, code);
    }
    public String getRequestedServiceLevelOfAssurance() {
        return getConfig().get(REQUESTED_SERVICE_LEVEL_OF_ASSURANCE);
    }

    public void setRequestedServiceLevelOfAssurance(String code) {
        getConfig().put(REQUESTED_SERVICE_LEVEL_OF_ASSURANCE, code);
    }

    public String getRequestedAttributes() {
        return getConfig().get(REQUESTED_ATTRIBUTES);
    }

    public void setRequestedAttributes(String code) {
        getConfig().put(REQUESTED_ATTRIBUTES, code);
    }
}

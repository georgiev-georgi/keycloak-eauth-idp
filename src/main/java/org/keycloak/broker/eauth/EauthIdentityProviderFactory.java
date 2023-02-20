package org.keycloak.broker.eauth;

import org.keycloak.Config;
import org.keycloak.broker.saml.SAMLIdentityProviderFactory;
import org.keycloak.models.IdentityProviderModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.saml.validators.DestinationValidator;

/**
 * User: ggeorgiev
 * Date: 16.02.2023
 * Time: 15:10
 */
public class EauthIdentityProviderFactory extends SAMLIdentityProviderFactory {

    public static final String PROVIDER_ID = "eauth-saml";

    private DestinationValidator destinationValidator;

    @Override
    public String getId() {

        return PROVIDER_ID;
    }

    @Override
    public String getName() {

        return "eAuth SAML";
    }

    @Override
    public EauthIdentityProvider create(KeycloakSession session, IdentityProviderModel model) {
        return new EauthIdentityProvider(session, new EauthIdentityProviderConfig(model), destinationValidator);
    }

    @Override
    public void init(Config.Scope config) {
        super.init(config);

        this.destinationValidator = DestinationValidator.forProtocolMap(config.getArray("knownProtocols"));
    }

    @Override
    public EauthIdentityProviderConfig createConfig() {
        return new EauthIdentityProviderConfig();
    }
}
